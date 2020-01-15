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

import javax.swing.text.html.HTMLDocument.Iterator; 
  
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
    { //mmm
    	
        MyHashTable<String, String> map = new MyHashTable<>(20047); 

        map.readAndAdd("src/lab11_scrabble/wordsList.txt");

//        System.out.println(map.size()); 
//        String ss ="note";
//        System.out.println("get "+ss+": "+map.get(ss)); 
//        ss ="no";
//        System.out.println("get "+ss+": "+map.get(ss)); 
//        ss ="on";
//        System.out.println("get "+ss+": "+map.get(ss)); 
     //   System.out.println("remove "+ss+": "+map.remove(ss)); 
     //   System.out.println(map.size()); 
//
//        System.out.println(map.get("whoev"));
//        System.out.println(map.remove("iever"));
//        System.out.println(map.size()); 
//        System.out.println(map.isEmpty()); 
        
//       map.getSizesOfChains();
//        System.out.println("moreThan16:"+map.moreThan16); 
//
          
        map.findPermutation("silent");
        map.findSubset("silent");
      //    System.out.println( map.get("od"));
//          System.out.println( map.isPermutation("keynote","on"));
//          System.out.println( map.isPermutation("keynote","no"));
//          System.out.println( map.isPermutation("keynote","ton"));
          map.getWordsFromSameBucket("dog");
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
   
    public LinkedList<String> getPermutation(String s) {///gggp

   	LinkedList<String> list = new  LinkedList<String>();
   		s="01234";
    	int size = s.length();
    	list = recur(size, s,list); 
    	
    	return list;
    }
    public LinkedList<String> recur(int size, String s,LinkedList<String> list) {
    	
    	//0 1, 01
    	//0 1 2, 01 02 12, 012
    	for(int i=size; i>0;i--) {
//    		System.out.println("size:"+size+"; "+s.length());
//    		
//    		j=1 
//    	    		prev=0,
//    	    		for(int k=prev;k<s.length()-(i-j);k++) {
//    	    			re(k+1,i,s); if(j==i) return 
//    	    		}
    		
//	    	if(s.length()==size && list.size()<1) {
//	    		list.add(s);
//	    	}
//	    	else if(i==1) {
//	    		for(int j=0;j<s.length();j++) {
//		    		list.add(s.substring(j,j+1));
//	    		}
//	    	}else if(i==2){//0123 01 02 03 , 12 13, 23, 4!/2!2!=6
//	    		for(int j=0;j<s.length()-(i-1);j++) {//3-2=1
//	    			for(int p=j+1;p<s.length();p++) {
//	    			//	System.out.println("j/p"+j+"/"+p);
//	    				list.add(""+s.charAt(j)+s.charAt(p));
//	    			}
//	    		}
//	    	}
//	    	else if(i==3){//0123 , 012 013 023 , 123  4!/3!1!=4
//	    		for(int j=0;j<s.length()-(i-1);j++) {//3-2=1
//	    			for(int p=j+1;p<s.length()-(i-2);p++) {
//	    				for(int y=p+1;y<s.length()-(i-3);y++) {
//	    			//	System.out.println("j/p"+j+"/"+p);
//	    				list.add(""+s.charAt(j)+s.charAt(p)+s.charAt(y));
//	    				}
//	    			}
//	    		}
//	    		
//	    	}else if(i==4){//0123 , 012 013 023 , 123  4!/3!1!=4
//	    		for(int j=0;j<s.length()-(i-1);j++) {//3-2=1
//	    			for(int p=j+1;p<s.length()-(i-2);p++) {
//	    				for(int y=p+1;y<s.length()-(i-3);y++) {
//	    					for(int z=y+1;z<s.length()-(i-4);z++) {
//	    			//	System.out.println("j/p"+j+"/"+p);
//	    				list.add(""+s.charAt(j)+s.charAt(p)+s.charAt(y)+s.charAt(z));
//	    					}
//	    				}
//	    			}
//	    		}
//	    		
    	
	    			
	    	}
	    	
    	
    	
    	return list;
    }
    public LinkedList<String> findPermutation(String s){
    	LinkedList<String> list = new LinkedList<String>();
    		
    	HashNode<K,V> head = bucketArray[getBucketIndex(s)];
    	while(head!=null) {
    		//System.out.println("head.value:"+head.value);
    		if(normalize(s).equals(normalize(head.value))) {
    			list.add(head.value);
    		}
    		
    		head = head.next;
    	}
    	System.out.println("anagrams for "+s+" :");
    	for(String ss:list) {
    		System.out.print(ss+"  ");
    	}
    	System.out.println("\n*************************");
    	return list;
    }
    public void findSubset(String str){//fff
    	//Set<String> permutations = new Set<String>();
    	String s_norm= normalize(str);
    	int k = hashKey(s_norm);
    	
    	//permutation
    	str="dog";
    	
    	LinkedList<String> list = getPermutation(str);
    	System.out.println(str+"'s list.size:"+list.size());
    	HashNode<K,V> head;
    	
    	HashSet<String> permSet = new HashSet<String>();
    	 
        
    
       
		for(String s: list) {
			System.out.println(".."+s);
	    	head = bucketArray[getBucketIndex(s)];
	    	while(head!=null) {
	    		//System.out.println("head.value:"+head.value);
	    		if(normalize(s).equals(normalize(head.value))) permSet.add(head.value);
	    		
	    		head = head.next;
	    	}
		}
    	
		 for(String s: permSet){
	           System.out.println("__"+s);
	        }
		
//    	return permutations;
    }
    public boolean isPrime(int n) {
    	return true;
    }
    //if s2 is a permutation of s1
//    public boolean isSubset(String s1,String s2) {//ppp
//    	int size1=s1.length();
//    	int size2=s2.length();
//    	
//    	//if more letters, return false
//    	if(size2>size1) return false;
//    	
//    	String s1Norm=normalize(s1);
//    	String s2Norm=normalize(s2);
//    	
//    	//if same amount of letters, check normalize
//    	if(size2==size1) return s1Norm.equals(s2Norm);
//    	
//    	//if s2 has less letters, check using contains
//    	for(int i=0;i<s2.length();i++) {//watch out for how many char eg. keynote nono or none
//    		if(s1Norm.contains(s2Norm.substring(i,i+1))) {
//    			int index = s1Norm.indexOf(s2Norm.substring(i,i+1));
//    			if(index==0)
//    				s1Norm = s1Norm.substring(1);
//    			else if(index>0 && index<s1Norm.length()-1)
//    				s1Norm = s1Norm.substring(0,index)+s1Norm.substring(index+1);
//    			else if(index==s1Norm.length()-1)
//    				s1Norm = s1Norm.substring(0,index);
//    			
//    		}else return false;
//    	}
//    	
//    	return true;
//    }
    public LinkedList<String> getWordsFromSameBucket(String s) {
    //	s="key";
//    	
    	LinkedList<String> list = new LinkedList<String>();
    	
    	HashNode<K,V> head = 	bucketArray[getBucketIndex(s)];
    	while(head!=null) {
    		System.out.print(head.value+"  ");
    		head=head.next;
    	}
    	return list;
    	
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