//
//
//package lab11_scrabble;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.Set;
//import java.util.TreeSet;
//
//import javax.swing.text.html.HTMLDocument.Iterator; 
//  
//// A node of chains 
//
//  
//// Class to represent entire hash table 
//class MyHashTable_def<K, V> extends MyHashTable
//{ 
//    // bucketArray is used to store array of chains 
//    private HashNode<K, V>[] bucketArray; 
//  
//    // Current capacity of array list 
//    private static int numBuckets; 
//  
//    // Current size of array list 
//    private int size; 
//    
//    int moreThan16=0;
//  
//    // Constructor (Initializes capacity, size and 
//    // empty chains. 
//    public MyHashTable_def(int size) 
//    { 
//    	super(size);
//        bucketArray = new HashNode[size]; 
//        numBuckets = size; 
//       
//  
//        // Create empty chains 
//        for (int i = 0; i < numBuckets; i++) 
//            bucketArray[i]=null; 
//    } 
//
//    public int size() { return size; } 
//    public boolean isEmpty() { return size() == 0; } 
//  
//    // This implements hash function to find index 
//    // for a key 
//    private int getBucketIndex(String keyStr) 
//    { 
//        int hashCode = hashKey(normalize(keyStr)); 
//        int index = hashCode % numBuckets; 
//        return index; 
//    } 
//    public int[] getSizesOfChains() {
//    	int[] sizes = new int[numBuckets];
//    	int i=0;
//    	for(HashNode<K,V> n: bucketArray) {
//    		sizes[i] = getSizeOfSingleChain(i);
//    		i++;
//    	}
//    	//System.out.println(i);
//    	return sizes;
//    }
//    public int getSizeOfSingleChain(int index) {
//    	HashNode<K,V> current = bucketArray[index];
//    	int i =0;
//    	while(current!=null) {
//    		current = current.next;
//    		i++;
//    	}
//    	//System.out.println(index+": "+i);
//    	if(i>16) moreThan16++;
//    	return i;
//    }
//    // Method to remove a given key 
//    public String remove(String key) 
//    { 
//        // Apply hash function to find index for given key 
//        int bucketIndex = getBucketIndex(key); 
//  
//        // Get head of chain 
//        HashNode<K, V> head = bucketArray[bucketIndex]; 
//  
//        // Search for key in its chain 
//        HashNode<K, V> prev = null; 
//        while (head != null) 
//        { 
//            // If Key found 
//            if (head.value.equals(key)) 
//                break; 
//  
//            // Else keep moving in chain 
//            prev = head; 
//            head = head.next; 
//        } 
//  
//        // If key was not there 
//        if (head == null) 
//            return null; 
//  
//        // Reduce size 
//        size--; 
//  
//        // Remove key 
//        if (prev != null) 
//            prev.next = head.next; 
//        else
//            bucketArray[bucketIndex]=head.next; 
//  
//        return head.value; 
//    } 
//  
//    // Returns value for a key 
//    public String get(String key) 
//    { 
//    	
//        // Find head of chain for given key 
//        int bucketIndex = getBucketIndex(key); 
//        HashNode<K, V> head = bucketArray[bucketIndex]; 
//  
//        // Search key in chain 
//        while (head != null) 
//        { 
//            if (normalize(head.value).equals(normalize(key))) 
//                return head.value; 
//            head = head.next; 
//        } 
//  
//        // If key not found 
//        return null; 
//    } 
//    
//  
//    // Adds a key value pair to hash 
//    public void add(int hashedKey, String keyStr) 
//    { 
//        // Find head of chain for given key 
//    	keyStr = keyStr.toLowerCase();
//       int bucketIndex = getBucketIndex(keyStr); 
//        HashNode<K, V> head = bucketArray[bucketIndex]; 
//      
//        // Check if key is already present 
//        while (head != null) 
//        { 
//            if (head.value.equals(keyStr)) 
//            { 
//                //already exists
//                return; 
//            } 
//            head = head.next; 
//        } 
////  
//        // Insert key in chain 
//        size++; 
//        head = bucketArray[bucketIndex]; 
//        HashNode<K, V> newNode = new HashNode<K, V>(hashedKey, keyStr); 
//        newNode.next = head; 
//        bucketArray[bucketIndex]=newNode; 
//    } 
//  
//  
//    public String normalize(String s) {
//    	s=s.toLowerCase();
//        char temp[] = s.toCharArray(); 
//        Arrays.sort(temp); 
//        return new String(temp); 
//    }
//    public int hashKey(String keyStr) {
//    	int key=0;
//    	int prime1=53;
//    	for(int i=0;i<keyStr.length();i++) {
//    		key += (int)(keyStr.charAt(i))*prime1*i;
//    	}
//    	//int numOfBuckets = 139;
//    	
//    	return key;
//    }
//    public int keyToIndex(int key) {
//    	return key%numBuckets;
//    }
//    public void readAndAdd(String fileLocation)  
//	{  
//		try{  
//			File file=new File(fileLocation);    //creates a new file instance  
////			FileReader fr=new FileReader(file);   //reads the file  
////			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
////			for(String line="";(line=br.readLine())!=null;){  
////				System.out.println(line); 
////				String[] words = line.split(",");
////				for(String word:words) {
////					System.out.println(word);
////					
////				}
////			}  
//			FileReader fr=new FileReader(file);   //reads the file  
//			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
//			for(String line="";(line=br.readLine())!=null;){  
//				line =  line.replaceAll("\\s","");
//				String line_norm = normalize(line);
//				int k = hashKey(normalize(line_norm));
//				add(k,line);	
//			//	System.out.println(line+": "+line_norm+": "+k);  //strip of space!!
//			
//			}  
//			fr.close();    //closes the stream and release the resources  
//		}catch(IOException e){  
//			e.printStackTrace();  
//		}  
//		
//	}  
//} 