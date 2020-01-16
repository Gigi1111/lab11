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
    
    int moreThan16=0;
  
    // Constructor (Initializes capacity, size and 
    // empty chains. 
    public MyHashTable(int size, String filelocation) 
    { 
        bucketArray = new HashNode[size]; 
        numBuckets = size; 
       
  
        // Create empty chains 
        for (int i = 0; i < numBuckets; i++) 
            bucketArray[i]=null;
        
        readAndAdd(filelocation);
        System.out.println("map size : "+size()); 
        getSizesOfChains();
        System.out.println("moreThan16:"+moreThan16); 
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
            if (normalize(head.value).equals(normalize(key))) 
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
   
    public LinkedList<String> getSubset(String s) {///gggp
    	LinkedList<LinkedList<String>> list = new LinkedList<LinkedList<String>>();
    	LinkedList<String> list_formatted = new  LinkedList<String>();
   		//s="0123456";
    	int size = s.length();
    
    	list.add(new LinkedList<String>());
    	agreg=0;
    	 for(int i=1; i<=s.length();i++) {
    		list =recur(0,i,1, s,list); 
    		 list.add(new LinkedList<String>());
    	}
    	 int n=(int) Math.pow(2,s.length());
//    //	 System.out.println("agreg "+agreg+" is "+""+(n-1)+" :"+(agreg==n-1));
//    	for(LinkedList<String> li: list) {
//    		for(String ss:li)
//    			System.out.print(ss);
//    		System.out.println();
//    	}
    
    	for(int i=0;i<list.size();i++) {
    		//list_formatted.add(new String());
    		String temp="";
    		for(int j=0;j<list.get(i).size();j++) {
    			temp +=list.get(i).get(j);
    		}
    		if(temp.length()>=1)
    			list_formatted.add(temp);
    	}
    	return list_formatted;
    }
    int agreg =0;
    public LinkedList<LinkedList<String>> recur(int prev, int i,int j, String s,LinkedList<LinkedList<String>> list) {
    	
    	//	System.out.println("**prev:"+prev+"; i:"+i+"; j:"+j+"; s:"+s);
    	    		for(int k=prev;k<s.length()-(i-j);k++) {
//    	    			System.out.println("begin");
//    	        		System.out.println("for(int k="+prev+";k<"+s.length()+"-("+i+"-"+j+");k++) {");
    	    			
    	    			list.getLast().add(""+s.charAt(k));
//    	    			for(LinkedList<String> li: list) {
//    	    	    		for(String ss:li)
//    	    	    			System.out.print(ss);
//    	    	    		System.out.println();
//    	    	    	}
    	    			if(i-j>0)
    	    				recur(k+1,i,j+1,s,list);
    	    			else if(i-j==0) {
//    	    				System.out.println("i-j==0");
//    	    				System.out.println("for(int k="+prev+";k<"+s.length()+"-("+i+"-"+j+");k++) {");
        	    			
    	    				LinkedList<String> temp = list.getLast();
    	    				list.add(new LinkedList<String>());
    	    				 if(k+1<(s.length()-(i-j)) && temp.size()-3>=0) {
    	    					// System.out.println("*");
	    	    				for(int y=temp.size()-3;y<temp.size()-1;y++) {
	    	    					list.getLast().add(temp.get(y));
	    	    				}
    	    				 }
    	    			}
    	    		
    	    			//System.out.println("--");	
    	    		}
    	    		//System.out.println("-----");	
    	   
    	    if(prev==0 && j==1 && i>1) {
    	    	for(int t=0;t<list.size();t++) {
    	    		if(list.get(t).size()==0) {
    	    			//System.out.println("--removeempty---");
    	    			list.remove(t);
    	    			t--;
    	    		}
    	    	}
    	    	
//    	    	System.out.println("--inside---");	
//    	    	System.out.println("for(int k="+prev+";k<"+s.length()+"-("+i+"-"+j+");k++) {");
//    			//=fac(s.length())/(fac(i-1)*fac(s.length()-(i-1)))
//    	    	System.out.println("agreg:"+agreg);	
    	    	
    	    	for(;agreg<list.size();agreg++) {
    	    		int diff = i - list.get(agreg).size();
    	    		if(diff!=0 && agreg>s.length()) {
    	    			if(agreg-1>=0) {
    	    				LinkedList<String> temp = list.get(agreg-1);
	    	    			for(int e=diff;e>0;e--) {
	    	    				if(e-1>=0)
	    	    					list.get(agreg).addFirst(temp.get(e-1));
	    	    			}
    	    			}
    	    		}
    	    	}
    	    }
    		return list;
    }
    public int fac(int n) {
        if (n > 12) throw new IllegalArgumentException(n + " is out of range");
        return (1 > n) ? 1 : n * fac(n - 1);
    }
  
    public LinkedList<String> findPermutation(String s){
    	LinkedList<String> list = new LinkedList<String>();
    		
    	HashNode<K,V> head = bucketArray[getBucketIndex(s)];
    	while(head!=null) {
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
    	
    	LinkedList<String> list = getSubset(str);
    //	System.out.println(str+"'s list.size:"+list.size());
    	HashNode<K,V> head;
    	
    	HashSet<String> permSet = new HashSet<String>();
    	for(String s: list) {
	//		System.out.println(".."+s);
	    	head = bucketArray[getBucketIndex(s)];
	    	while(head!=null) {
	    		//System.out.println("head.value:"+head.value);
	    		if(normalize(s).equals(normalize(head.value))) permSet.add(head.value);
	    		
	    		head = head.next;
	    	}
		}
    	 System.out.println("\""+str+"\""+" can create:");
    	 //
//    	 TreeSet myTreeSet = new TreeSet();
//    	 myTreeSet.addAll(permSet);
//    	 System.out.println(myTreeSet);
    	 LinkedList<LinkedList<String>> sort =new LinkedList<LinkedList<String>>();
    	// System.out.println(sort.get(0));
		 for(String s: permSet){
			 while(sort.size()<str.length()+1) {
				 sort.add(new LinkedList<String>());
			 }
			 sort.get(s.length()).add(s);
	     }
		 int changeLine=0;
		 for(int i=2;i<sort.size();i++){
			 if(sort.get(i).size()>0)System.out.print(i+" letters: ");
			 for(int j=0;j<sort.get(i).size();j++){
				 System.out.print(sort.get(i).get(j)+"  ");
			 }
			 if(sort.get(i).size()>0) System.out.println();
	     }
		 
		 System.out.println();
		 System.out.println("=========end=========");
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