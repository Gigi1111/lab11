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
  
// A node of chains 
class HashNode<K, V> 
{ 
    K key; 
    V value; 
  
    // Reference to next node 
    HashNode<K, V> next; 
  
    // Constructor 
    public HashNode(K key, V value) 
    { 
        this.key = key; 
        this.value = value; 
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
  
    public int size() { return size; } 
    public boolean isEmpty() { return size() == 0; } 
  
    // This implements hash function to find index 
    // for a key 
    private int getBucketIndex(K key) 
    { 
        int hashCode = key.hashCode(); 
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
    	
    	return sizes;
    }
    public int getSizeOfSingleChain(int index) {
    	HashNode<K,V> current = bucketArray[index];
    	int i =0;
    	while(current!=null) {
    		current = current.next;
    		i++;
    	}
    	System.out.println(i);
    	return i;
    }
    // Method to remove a given key 
    public V remove(K key) 
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
            if (head.key.equals(key)) 
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
    public V get(K key) 
    { 
        // Find head of chain for given key 
        int bucketIndex = getBucketIndex(key); 
        HashNode<K, V> head = bucketArray[bucketIndex]; 
  
        // Search key in chain 
        while (head != null) 
        { 
            if (head.key.equals(key)) 
                return head.value; 
            head = head.next; 
        } 
  
        // If key not found 
        return null; 
    } 
  
    // Adds a key value pair to hash 
    public void add(K key, V value) 
    { 
        // Find head of chain for given key 
        int bucketIndex = getBucketIndex(key); 
        HashNode<K, V> head = bucketArray[bucketIndex]; 
  
        // Check if key is already present 
        while (head != null) 
        { 
            if (head.key.equals(key)) 
            { 
                head.value = value; 
                return; 
            } 
            head = head.next; 
        } 
  
        // Insert key in chain 
        size++; 
        head = bucketArray[bucketIndex]; 
        HashNode<K, V> newNode = new HashNode<K, V>(key, value); 
        newNode.next = head; 
        bucketArray[bucketIndex]=newNode; 
  
        // If load factor goes beyond threshold, then 
        // double hash table size 
//        if ((1.0*size)/numBuckets >= 0.7) 
//        { 
//            HashNode<K, V>[numBuckets] temp = bucketArray; 
//            bucketArray = new HashNode<K, V>[numBuckets*2](); 
//            numBuckets = 2 * numBuckets; 
//            size = 0; 
//            for (int i = 0; i < numBuckets; i++) 
//                bucketArray.add(null); 
//  
//            for (HashNode<K, V> headNode : temp) 
//            { 
//                while (headNode != null) 
//                { 
//                    add(headNode.key, headNode.value); 
//                    headNode = headNode.next; 
//                } 
//            } 
//        } 
    } 
  
    // Driver method to test Map class 
    public static void main(String[] args) 
    { 
        MyHashTable<String, String>map = new MyHashTable<>(numBuckets); 
        //get bca -> normalized to abc(in order), generate hash key-> put, get String[] of permutations
//        map.add("abc","abc" );
//        map.add("abc","bca" ); 
//        map.add("abc","bac" );
//        map.add("abc","cba" ); 
//        map.add("coder","coder" ); 
//        map.add("this","coder" ); 
//        System.out.println(map.size()); 
//        System.out.println(map.get("abc")); 
//        System.out.println(map.size()); 
//        System.out.println(map.isEmpty()); 
        map.readAndAdd("src/lab11_scrabble/wordsList.txt");
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
    	for(int i=size2-1;i>=0;i--) {
    		
    	}
    	
    	return true;
    }
    public String normalize(String s) {
    	s=s.toLowerCase();
        char temp[] = s.toCharArray(); 
        Arrays.sort(temp); 
        return new String(temp); 
    }
    public int hashKey(String keyStr) {
    	int key=0;
    	int prime1=31;
    	for(int i=0;i<keyStr.length();i++) {
    		key += ((int)keyStr.charAt(i))*i%prime1;
    	}
    	int numOfBuckets = 139;
    	return key%numOfBuckets;
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
				System.out.println(line);    
			}  
			fr.close();    //closes the stream and release the resources  
		}catch(IOException e){  
			e.printStackTrace();  
		}  
		
	}  
} 