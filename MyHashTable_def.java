//numOfBucket 5303, Hashtable or HashMap<Integer, List<String>)
//normalize (1)allsmall, soft letter
//get, use a tray of letters->hash find anagram filter out non-seven letter words, or do the delux one that also find 
//permutation
//chain of 100 or less is ok too

package lab11_scrabble;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.text.html.HTMLDocument.Iterator; 
  
// A node of chains 
class HashNode_def<K, V> 
{ 
   int key; 
    String value; 
    String def;
   
  
    // Reference to next node 
    HashNode_def<K, V> next; 
  
    // Constructor 
    public HashNode_def(int hashedKey, String keyStr, String def) 
    { 
        this.key = hashedKey; 
        this.value = keyStr; 
        this.def = def;
    } 
} 
  
// Class to represent entire hash table 
class MyHashTable_def<K, V> extends MyHashTable
{ 
    // bucketArray is used to store array of chains 
    private HashNode_def<K, V>[] bucketArray; 
  
    // Current capacity of array list 
    private static int numBuckets; 
  
    // Current size of array list 
    private int size; 
    
    int moreThan16=0;
  
    // Constructor (Initializes capacity, size and 
    // empty chains. 
    public MyHashTable_def(int size, String filelocation) 
    { 
    	super();
        bucketArray = new HashNode_def[size]; 
        numBuckets = size; 
       
  
        // Create empty chains 
        for (int i = 0; i < numBuckets; i++) 
            bucketArray[i]=null;
        
        readAndAdd(filelocation);
        System.out.println("dict size : "+size()); 
        getSizesOfChains();
        System.out.println("moreThan16:"+moreThan16); 
        System.out.println("-------------------------------");
    } 
    public static void main(String args[])  
	{  
    	MyHashTable_def  dict = new MyHashTable_def(110047,"src/lab11_scrabble/wordsList_collins2019_def.txt");
        System.out.println(dict.get("camp"));
	}  
    
    protected int getBucketIndex(String keyStr) 
    { 
        int hashCode = hashKey(normalize(keyStr)); 
        int index = hashCode % numBuckets; 
        return index; 
    } 
    public int[] getSizesOfChains() {
    	int[] sizes = new int[numBuckets];
    	int i=0;
    	for(HashNode_def<K,V> n: bucketArray) {
    		sizes[i] = getSizeOfSingleChain(i);
    		i++;
    	}
    	//System.out.println(i);
    	return sizes;
    }
    public int getSizeOfSingleChain(int index) {
    	HashNode_def<K,V> current = bucketArray[index];
    	int i =0;
    	while(current!=null) {
    		current = current.next;
    		i++;
    	}
    	//System.out.println(index+": "+i);
    	if(i>16) moreThan16++;
    	return i;
    }
   
  
    // Returns value for a key 
    public String get(String key) 
    { 
    	
        // Find head of chain for given key 
        int bucketIndex = getBucketIndex(key); 
        HashNode_def<K, V> head = bucketArray[bucketIndex]; 
  
        // Search key in chain 
        while (head != null) 
        { 
        	if (head.value.toLowerCase().equals(key.toLowerCase())
        ) 
                return head.value+"  "+head.def; 
            head = head.next; 
        } 
  
        // If key not found 
        return null; 
    } 
    
  
    // Adds a key value pair to hash 
    public void add(String keyStr) 
    { 
    	//System.out.println(keyStr);
    	//System.out.println(keyStr);
    	boolean breakLine=false;
    	String def="";
    	char base = 'A';
    	//check current capital, next >= 1 space, then 
    	for(int i=0;i<keyStr.length()-1;i++) {
    		int cur = keyStr.charAt(i)-base;
    		int next = keyStr.charAt(i+1)-base;
    		if((cur<26 && cur>=0) && (next>26||next<0)) {
    			def=keyStr.substring(i+1);
    			keyStr=keyStr.substring(0,i+1);
    			keyStr=keyStr.replaceAll("\\s","");
    			breakLine=true;
    			break;
    		}
    	}
    	if(!breakLine) {
    	//	System.out.println("nuhu");
    		return;
    	}
    	
    	//System.out.println(keyStr);
    	
    	//System.out.println(keyStr+def);
    	
    	int hashedKey = hashKey(normalize(keyStr));
    	
        // Find head of chain for given key 
       int bucketIndex = getBucketIndex(keyStr.toLowerCase()); 
        HashNode_def<K, V> head = bucketArray[bucketIndex]; 
      
        // Check if key is already present 
        while (head != null) 
        { 
            if (head.value.equals(keyStr)) 
            { 
                //already exists
                return; 
            } 
            head = head.next; 
        } 
//  
        // Insert key in chain 
        size++; 
        head = bucketArray[bucketIndex]; 
        HashNode_def<K, V> newNode = new HashNode_def<K, V>(hashedKey, keyStr, def); 
        newNode.next = head; 
        bucketArray[bucketIndex]=newNode; 
    } 
  
    public void readAndAdd(String fileLocation)  
	{  
		try{  
			File file=new File(fileLocation);    //creates a new file instance  
			FileReader fr=new FileReader(file);   //reads the file  
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
			for(String line="";(line=br.readLine())!=null;){  
				
				//String line_norm = normalize(line);
				//int k = hashKey(normalize(line_norm));
					
				//System.out.println(line);  //strip of space!!
				add(line);
			}  
			fr.close();    //closes the stream and release the resources  
		}catch(IOException e){  
			e.printStackTrace();  
		}  
		
	}  
} 