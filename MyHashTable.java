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
import java.util.LinkedList;
import java.util.Set; 
  
// A node of chains 
class HashNode<K, V> 
{ 
   int key; 
    String value; 
   
  
    // Reference to next node 
    HashNode<K, V> next; 
  
    // Constructor 
    public HashNode(int hashedKey, String keyStr) 
    { 
        this.key = hashedKey; 
        this.value = keyStr; 
    } 
} 
  
// Class to represent entire hash table 
class MyHashTable<K, V> 
{ 
    // bucketArray is used to store array of chains 
    private HashNode<K, V>[] bucketArray; 
  
    // Current capacity of array list 
    private static int numBuckets; 
  
    // Current size of array list 
    private int size; 
    
    private  int moreThan16=0;
  
    // Constructor (Initializes capacity, size and 
    // empty chains. 
    public MyHashTable(int size) 
    { 
        bucketArray = new HashNode[size]; 
        numBuckets = size; 
       
  
        // Create empty chains 
        for (int i = 0; i < numBuckets; i++) 
            bucketArray[i]=null; 
    } 
  
    public static void main(String[] args) 
    { 
    	
        MyHashTable<String, String> map = new MyHashTable<>(20047); 

      
        map.readAndAdd("src/lab11_scrabble/wordsList.txt");

        System.out.println(map.size()); 
        String ss ="note";
        System.out.println("get "+ss+": "+map.get(ss)); 
        ss ="no";
        System.out.println("get "+ss+": "+map.get(ss)); 
        ss ="on";
        System.out.println("get "+ss+": "+map.get(ss)); 
     //   System.out.println("remove "+ss+": "+map.remove(ss)); 
     //   System.out.println(map.size()); 
//
//        System.out.println(map.get("whoev"));
//        System.out.println(map.remove("iever"));
//        System.out.println(map.size()); 
//        System.out.println(map.isEmpty()); 
        
       map.getSizesOfChains();
        System.out.println("moreThan16:"+map.moreThan16); 
//        System.out.println("key".substring(1));
//        String s= "key";
//
//        System.out.println("k ey  ".strip());
          map.findPermutation("Keynote");
        //  System.out.println("key".substring(0,1));
    } 
    
    public int size() { return size; } 
    public boolean isEmpty() { return size() == 0; } 
  
    // This implements hash function to find index 
    // for a key 
    private int getBucketIndex(String keyStr) 
    { 
        int hashCode = hashKey(normalize(keyStr)); 
        int index = hashCode % numBuckets; 
        return index; 
    } 
    public int[] getSizesOfChains() {
    	int[] sizes = new int[numBuckets];
    	int i=0;
    	for(HashNode<K,V> n: bucketArray) {
    		sizes[i] = getSizeOfSingleChain(i);
    		i++;
    	}
    	//System.out.println(i);
    	return sizes;
    }
    public int getSizeOfSingleChain(int index) {
    	HashNode<K,V> current = bucketArray[index];
    	int i =0;
    	while(current!=null) {
    		current = current.next;
    		i++;
    	}
    	//System.out.println(index+": "+i);
    	if(i>16) moreThan16++;
    	return i;
    }
    // Method to remove a given key 
    public String remove(String key) 
    { 
        // Apply hash function to find index for given key 
        int bucketIndex = getBucketIndex(key); 
  
        // Get head of chain 
        HashNode<K, V> head = bucketArray[bucketIndex]; 
  
        // Search for key in its chain 
        HashNode<K, V> prev = null; 
        while (head != null) 
        { 
            // If Key found 
            if (head.value.equals(key)) 
                break; 
  
            // Else keep moving in chain 
            prev = head; 
            head = head.next; 
        } 
  
        // If key was not there 
        if (head == null) 
            return null; 
  
        // Reduce size 
        size--; 
  
        // Remove key 
        if (prev != null) 
            prev.next = head.next; 
        else
            bucketArray[bucketIndex]=head.next; 
  
        return head.value; 
    } 
  
    // Returns value for a key 
    public String get(String key) 
    { 
    	
        // Find head of chain for given key 
        int bucketIndex = getBucketIndex(key); 
        HashNode<K, V> head = bucketArray[bucketIndex]; 
  
        // Search key in chain 
        while (head != null) 
        { 
            if (head.value.equals(key)) 
                return head.value; 
            head = head.next; 
        } 
  
        // If key not found 
        return null; 
    } 
  
    // Adds a key value pair to hash 
    public void add(int hashedKey, String keyStr) 
    { 
        // Find head of chain for given key 
    	keyStr=keyStr.toLowerCase();
       int bucketIndex = getBucketIndex(keyStr); 
        HashNode<K, V> head = bucketArray[bucketIndex]; 
      
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
        HashNode<K, V> newNode = new HashNode<K, V>(hashedKey, keyStr); 
        newNode.next = head; 
        bucketArray[bucketIndex]=newNode; 
    } 
  
    // Driver method to test Map class 
   
    public LinkedList<String> getPermutation(String s) {
    	s="ke";
    	LinkedList<LinkedList<String>> list = new  LinkedList<LinkedList<String>>();
    	int size = s.length();
//    	char[] chars = s.toCharArray();
    	list = recur(size, s,list);
    	
    	System.out.println("list.size:"+list.getLast().size());
    	LinkedList<String> result =new LinkedList<String>();
    	return result;
    }
    public LinkedList<LinkedList<String>> recur(int size, String s,LinkedList<LinkedList<String>> list) {
    	System.out.println("size:"+size+"; "+s.length());
    	
    	if(s.length()!=0) {
    		if(list.size()==0) list.add(new LinkedList<String>());
    	
	    	for(int i=0;i<2;i++) {
	    		if(i==0) { //only add the char when i==1	    			
	    		//	list.getLast().add(s.substring(0,1));
	    			if(s.length()>1) {
	    				recur(size, s.substring(1),list);
	    			}
//	    			else if (s.length()==1)
//	    				recur(size, s.substring(0),list);
	    		}
	    		else if(i==1) { //only add the char when i==1	    			
	    			list.getLast().add(s.substring(0,1));
	    			if(s.length()>1)
		    			recur(size, s.substring(1),list);
//	    			else if(s.length()==1)
//	    				recur(size, s.substring(0),list);
	    		}
	    		
	    	}
    	}
    	if(s.length()==1) {
    		list.add(new LinkedList<String>());
    	}
    	return list;
    }
    public void findPermutation(String s){
    	//Set<String> permutations = new Set<String>();
    	String s_norm= normalize(s);
    	int k = hashKey(s_norm);
    	
    	//permutation
    	LinkedList<String> perms = getPermutation(s);
    	
    	HashNode<K,V> head = bucketArray[getBucketIndex(s)];
    	while(head!=null) {
    		//System.out.println("head.value:"+head.value);
    		if(isPermutation(s,head.value)) System.out.println("perm:"+head.value);
    		
    		head = head.next;
    	}
    	head = bucketArray[getBucketIndex("key")];
    	while(head!=null) {
    		//System.out.println("head.value:"+head.value);
    		if(isPermutation("key",head.value)) System.out.println("perm:"+head.value);
    		//System.out.println(get("key"));
    		head = head.next;
    	}
//    	return permutations;
    }
    public boolean isPrime(int n) {
    	return true;
    }
    //if s2 is a permutation of s1
    public boolean isPermutation(String s1,String s2) {
    	int size1=s1.length();
    	int size2=s2.length();
    	
    	//if more letters, return false
    	if(size2>size1)return false;
    	
    	String s1Norm=normalize(s1);
    	String s2Norm=normalize(s2);
    	//if same amount of letters, check normalize
    	if(size2==size1) return s1Norm.equals(s2Norm);
    	
    	//if s2 has less letters, check using contains
//    	for(int i=size2-1;i>=0;i--) {
//    		
//    	}
    	
    	return false;
    }
    public String normalize(String s) {
    	s=s.toLowerCase();
        char temp[] = s.toCharArray(); 
        Arrays.sort(temp); 
        return new String(temp); 
    }
    public int hashKey(String keyStr) {
    	int key=0;
    	int prime1=53;
    	for(int i=0;i<keyStr.length();i++) {
    		key += (int)(keyStr.charAt(i))*prime1*i;
    	}
    	//int numOfBuckets = 139;
    	
    	return key;
    }
    public int keyToIndex(int key) {
    	return key%numBuckets;
    }
    public void readAndAdd(String fileLocation)  
	{  
		try{  
			File file=new File(fileLocation);    //creates a new file instance  
//			FileReader fr=new FileReader(file);   //reads the file  
//			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
//			for(String line="";(line=br.readLine())!=null;){  
//				System.out.println(line); 
//				String[] words = line.split(",");
//				for(String word:words) {
//					System.out.println(word);
//					
//				}
//			}  
			FileReader fr=new FileReader(file);   //reads the file  
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
			for(String line="";(line=br.readLine())!=null;){  
				line =  line.replaceAll("\\s","");
				String line_norm = normalize(line);
				int k = hashKey(normalize(line_norm));
				add(k,line);	
			//	System.out.println(line+": "+line_norm+": "+k);  //strip of space!!
			
			}  
			fr.close();    //closes the stream and release the resources  
		}catch(IOException e){  
			e.printStackTrace();  
		}  
		
	}  
} 