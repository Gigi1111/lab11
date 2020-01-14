//numOfBucket 5303, Hashtable or HashMap<Integer, List<String>)
//normalize (1)allsmall, soft letter
//get, use a tray of letters->hash find anagram filter out non-seven letter words, or do the delux one that also find 
//permutation
//chain of 100 or less is ok too

package lab11_scrabble;
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
    private LinkedList<HashNode<K, V>> bucketArray; 
  
    // Current capacity of array list 
    private int numBuckets; 
  
    // Current size of array list 
    private int size; 
  
    // Constructor (Initializes capacity, size and 
    // empty chains. 
    public MyHashTable() 
    { 
        bucketArray = new LinkedList<>(); 
        numBuckets = 10; 
        size = 0; 
  
        // Create empty chains 
        for (int i = 0; i < numBuckets; i++) 
            bucketArray.add(null); 
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
  
    // Method to remove a given key 
    public V remove(K key) 
    { 
        // Apply hash function to find index for given key 
        int bucketIndex = getBucketIndex(key); 
  
        // Get head of chain 
        HashNode<K, V> head = bucketArray.get(bucketIndex); 
  
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
            bucketArray.set(bucketIndex, head.next); 
  
        return head.value; 
    } 
  
    // Returns value for a key 
    public V get(K key) 
    { 
        // Find head of chain for given key 
        int bucketIndex = getBucketIndex(key); 
        HashNode<K, V> head = bucketArray.get(bucketIndex); 
  
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
        HashNode<K, V> head = bucketArray.get(bucketIndex); 
  
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
        head = bucketArray.get(bucketIndex); 
        HashNode<K, V> newNode = new HashNode<K, V>(key, value); 
        newNode.next = head; 
        bucketArray.set(bucketIndex, newNode); 
  
        // If load factor goes beyond threshold, then 
        // double hash table size 
        if ((1.0*size)/numBuckets >= 0.7) 
        { 
            ArrayList<HashNode<K, V>> temp = bucketArray; 
            bucketArray = new ArrayList<>(); 
            numBuckets = 2 * numBuckets; 
            size = 0; 
            for (int i = 0; i < numBuckets; i++) 
                bucketArray.add(null); 
  
            for (HashNode<K, V> headNode : temp) 
            { 
                while (headNode != null) 
                { 
                    add(headNode.key, headNode.value); 
                    headNode = headNode.next; 
                } 
            } 
        } 
    } 
  
    // Driver method to test Map class 
    public static void main(String[] args) 
    { 
        MyHashTable<String, String>map = new MyHashTable<>(); 
        //get bca -> normalized to abc(in order), generate hash key-> put, get String[] of permutations
        map.add("abc","abc" );
        map.add("abc","bca" ); 
        map.add("abc","bac" );
        map.add("abc","cba" ); 
        map.add("coder","coder" ); 
        map.add("this","coder" ); 
        System.out.println(map.size()); 
        System.out.println(map.get("abc")); 
        System.out.println(map.size()); 
        System.out.println(map.isEmpty()); 
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
} 