package lab11_scrabble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
  
	
  
// Class to represent entire hash table 
class MyHashTable11 
{ 
	// A node of chains 
		class HashNode 
		{ 
			int key; 
			String value; 
	   
	  
			// Reference to next node 
			HashNode next; 
	  
			// Constructor 
			public HashNode(int hashedKey, String keyStr) 
			{ 
				this.key = hashedKey; 
				this.value = keyStr; 
			} 
		} 
    // bucketArray is used to store array of chains 
    private HashNode[] bucketArray; 
  
    // Size of array ( prime number ) 
    private static int numBuckets; 
  
    Map<String,Integer> points;
    // Current size of array list 
    private int size; 
    
    // Collisions
    int collision = 0;
    
    // empty buckets
    int empty=0;
    
    // Collisions with more than 16 elements
    int moreThan16=0;
    
    // Collisions with more than 100 elements
    int moreThan100=0;
    
    // Finding the longest chain
    int longestChain = 0;
    
    // Constructor 
    // Initializes capacity, size and empty chains. 
    public MyHashTable11(int size, String filelocation) 
    { 
        bucketArray = new HashNode[size]; 
        numBuckets = size; 
       
        // Create empty chains 
        for (int i = 0; i < numBuckets; i++) 
            bucketArray[i]=null;
        
        readAndAdd(filelocation);

       System.out.println("Total words in the dictionary with 7 letters: "+size());    
       System.out.println("How many buckets: "+numBuckets); 
       
       
       //   System.out.println("Array Size ( the prime number we used ) : " + numBuckets); 
        System.out.println("Load Factor : "+((float)size())/numBuckets); 
        
        
        getSizesOfChains();
        System.out.println("The longest chain is: " + longestChain);
        System.out.println("Collisions (more than 1 word hashed to same value): " +collision );
        System.out.println("Empty Buckets: " +empty); 
        System.out.println("collisions + non-empty Buckets: " +(numBuckets - empty + collision)); 
        System.out.println("Collisions with more than 16 chains:" +moreThan16); 
        System.out.println("Collisions with more than 100 chains:" +moreThan100); 
        
    } 
    public MyHashTable11() 
    { 
        
    } 

    public int size() { return size; } 
    public boolean isEmpty() { return size() == 0; } 
  
    // Method to remove a given key 
    public String remove(String key) 
    { 
        // Apply hash function to find index for given key 
        int bucketIndex = getBucketIndex(key); 
  
        // Get head of chain 
        HashNode head = bucketArray[bucketIndex]; 
  
        // Search for key in its chain 
        HashNode prev = null; 
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
        HashNode head = bucketArray[bucketIndex]; 
  
        // Search key in chain 
        while (head != null) 
        { 
            if (head.value.toLowerCase().equals(key.toLowerCase())) 
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
    	keyStr = keyStr.toLowerCase();
       int bucketIndex = getBucketIndex(keyStr); 
        HashNode head = bucketArray[bucketIndex]; 
      
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
        
        //if the location already stores something there, collision happens
        if(head!=null)
        	collision++;
        
        HashNode newNode = new HashNode(hashedKey, keyStr); 
        newNode.next = head; 
        bucketArray[bucketIndex]=newNode; 
    } 

    // This implements hash function to find index 
    // for a key 
    protected int getBucketIndex(String keyStr) 
    { 
        int hashCode = hashKey(normalize(keyStr)); 
        int index = hashCode % numBuckets; 
        return index; 
    } 
    public int[] getSizesOfChains() {
    	int[] sizes = new int[numBuckets];
    	int i=0;
    	for(HashNode n: bucketArray) {
    		sizes[i] = getSizeOfSingleChain(i);
    		i++;
    	}
    	//System.out.println(i);
    	return sizes;
    }
    public int getSizeOfSingleChain(int index) {
    	HashNode current = bucketArray[index];
    	int i =0;
    	
    	while(current!=null) {
    		current = current.next;
    		i++;
    	}
    	if(longestChain<i) { 	// longest chain is initialized 0 as a field
    		longestChain = i;
    	}
    //	if(i>10) {
    //	longestChain = i;
    //		System.out.println("The longest chain is: "+i);
    //	}
    	//System.out.println(index+": "+i);
    	if(i==0) empty++;
    	if(i>16) {
    		System.out.println("over16: "+i);
    		moreThan16++;
    	}
    	if(i>100) moreThan100++;
    	return i;
    }
    
    public LinkedList<String> findPermutation(String s){
    	LinkedList<String> list = new LinkedList<String>();
    		
    	HashNode head = bucketArray[getBucketIndex(s)];
    	while(head!=null) {
    		if(normalize(s).equals(normalize(head.value))) {
    			list.add(head.value);
    		}
    		
    		head = head.next;
    	}
    	System.out.println("Anagrams for "+s+":");
    	for(String ss:list) {
    		System.out.print(ss+"  ");
    	}
    	System.out.println("\n*************************");
    	return list;
    }

    // Looks up a single index in the hash table and
    // returns the words in it
    public LinkedList<String> getWordsFromSameBucket(String s) {
    
    	LinkedList<String> list = new LinkedList<String>();
    	
    	HashNode head = bucketArray[getBucketIndex(s)];
    	while(head!=null) {
    		System.out.print(head.value+"  ");
    		head=head.next;
    	}
    	return list;
    }
    
    // Takes a string and lowercases it.
    // Creates a new temp char array out of the string
    // Sorts the char array and returns a new string out of it
    public String normalize(String s) {
    	s=s.toLowerCase();
        char temp[] = s.toCharArray(); 
        Arrays.sort(temp); 
        return new String(temp); 
    } // end normalize

    public void readAndAdd(String fileLocation)  
	{  
		try{  
			File file=new File(fileLocation);    //creates a new file instance  
			FileReader fr=new FileReader(file);   //reads the file  
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
			for(String line="";(line=br.readLine())!=null;){  
				if(line.length()==7) {
				line =  line.replaceAll("\\s","");
				
				String line_norm = normalize(line);
				int k = hashKey(normalize(line_norm));
				add(k,line);	
			
			}  
			}
			fr.close();    //closes the stream and release the resources  
		}catch(IOException e){  
			e.printStackTrace();  
		}  
	} // end readAndAdd
    
    // Hashing function. Maps an Integer to
    // an index in the array and returns it
    public int hashKey(String keyStr) {
    	long key=0;

    	long prime1=94447;
    	for(int i=0;i<keyStr.length();i++) {
    		if(key>0 && i>0)
    			key = key*(long)(keyStr.charAt(i))*prime1*i % numBuckets;
    		else
    			key = (long)(keyStr.charAt(i)) % numBuckets;
    	}

    	return (int) key % numBuckets;
    } // end hashKey

} 