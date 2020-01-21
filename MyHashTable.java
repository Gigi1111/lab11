//numOfBucket 5303, Hashtable or HashMap<Integer, List<String>)
//normalize (1)allsmall, soft letter
//get, use a tray of letters->hash find anagram filter out non-seven letter words, or do the delux one that also find 
//permutation
//chain of 100 or less is ok too

package lab12_scrabble_cheater;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.text.html.HTMLDocument.Iterator; 
  
// A node of chains 
class HashNode 
{ 
   int key; 
    String value; 
   String def;
  
    // Reference to next node 
    HashNode next; 
  
    // Constructor 
    public HashNode(int hashedKey, String keyStr, String def) 
    { 
        this.key = hashedKey; 
        this.value = keyStr; 
        this.def = def;
    } 
} 
  
// Class to represent entire hash table 
class MyHashTable 
{
    // bucketArray is used to store array of chains 
    private HashNode[] bucketArray; 
  
    // Current capacity of array list 
    private static int numBuckets; 
    
    //substrings of a given string
    LinkedList<LinkedList<String>> substringList;
    
    
    Map<String,Integer> points;
    // Current size of array list 
    private int size; 
    
    int moreThan16=0;
    int moreThan100=0;
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
        initializePoints();
//        System.out.println(points.get("i"));
        System.out.println("num of entry : "+size());    
        System.out.println("map size : "+numBuckets); 
       // System.out.println("load : "+((float)size())/numBuckets); 
        
        getSizesOfChains();
        System.out.println("moreThan16:"+moreThan16); 
        //System.out.println("moreThan100:"+moreThan100); 
        
    } 
    public MyHashTable() 
    { 
        
    } 

    public int size() { return size; } 
    public boolean isEmpty() { return size() == 0; } 
  
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
    	//System.out.println(index+": "+i);
    	if(i>16) {
    	//	System.out.println("over16: "+i);
    		moreThan16++;
    	}
    	if(i>100) moreThan100++;
    	return i;
    }
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
    public String getDef(String key) 
    { 
    	
        // Find head of chain for given key 
        int bucketIndex = getBucketIndex(key); 
        HashNode head = bucketArray[bucketIndex]; 
  
        // Search key in chain 
        while (head != null) 
        { 
            if (head.value.toLowerCase().equals(key.toLowerCase())) 
                return head.def; 
            head = head.next; 
        } 
  
        // If key not found 
        return "can't find "+key; 
    } 
  
    // Adds a key value pair to hash 
    public void add(String keyStr) 
    { 
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
        HashNode newNode = new HashNode(hashedKey, keyStr.toLowerCase(), def); 
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
    	LinkedList<String> validWords = new LinkedList<String>();
    	LinkedList<String> substring= new LinkedList<String>();
    	
    	if(s.contains("-")) {
    		for(int i=0;i<26;i++) {
				String temp=s.replaceFirst("-", ""+(char)('a'+i));
				
				if(temp.contains("-")) {
					for(int t=0;t<26;t++) {
						String temp2=temp.replaceFirst("-", ""+(char)('a'+t));
						substring.add(temp2);
					}
					
				}else {
				substring.add(temp);
			}
    		}
	    	
    	}else { substring.add(s);}
    	for(int i=0;i<substring.size();i++) {
    	HashNode head = bucketArray[getBucketIndex(substring.get(i))];
    	while(head!=null) {
    		if(normalize(substring.get(i)).equals(normalize(head.value))) {
    			validWords.add(head.value);
    		}
    		
    		head = head.next;
    	}
    	}
//    	System.out.println("anagrams for "+s+" :");
//    	for(String ss:list) {
//    		System.out.print(ss+"  ");
//    	}
//    	System.out.println("\n*************************");
    	return validWords;
    }
    public void findSubstringsWithBag(String str){
    	substringList=new LinkedList<LinkedList<String>>();
    	MyBag bag = new MyBag(str);
    	System.out.println(bag.toString());
    	
	int size = bag.size();
	System.out.println("Substrings of "+bag.s_old+" :");
	for(int i =2; i<=size;i++) {//get 2,3...,size lengths of substrings
		LinkedList<String> substringsOfCertainLength= bag.getSubstringsOfLength(i);
		substringList.add(substringsOfCertainLength);
		for(int j=0;j<substringsOfCertainLength.size();j++) {
			System.out.print(substringsOfCertainLength.get(j)+"  ");
			
		}
		System.out.println();
	}
    
    }
   
    public void matchSubsetWithBlank(String s, Set permSet) {
    	//System.out.println("yes and :"+s.indexOf("-"));
    	LinkedList<String> possibilities = new LinkedList<String>();
    	
    	char base = 'a';
    	int count = s.length() - s.replaceAll("-","").length();
    	
    	while((int)base<=(int)'z') {
    		possibilities.add(s.replace("-",(""+base)));
    		base = (char) (base+1);
    		//System.out.println("base:"+base);
    		
    		 if(count==2) {
    			char base2 = 'a';
    			while((int)base2<=(int)'z') {
    				String temp = possibilities.getLast();
    				temp = temp.replace("-",(""+base2));
    				possibilities.set(possibilities.size()-1,temp);
    	    		base++;
    			}
    		}
    	}
    	
		HashNode head;
    		for(String p: possibilities) {
			//	System.out.println("_"+p);
		    	head = bucketArray[getBucketIndex(p)];
		    	while(head!=null) {
		    		//System.out.println("head.value:"+head.value);
		    		if(normalize(p).equals(normalize(head.value))) permSet.add(head.value);
		    		
		    		head = head.next;
		    	}
    		}
    }
    public boolean isPrime(int n) {
    	return true;
    }
  
    public LinkedList<String> getWordsFromSameBucket(String s) {
    
    	LinkedList<String> list = new LinkedList<String>();
    	
    	HashNode head = bucketArray[getBucketIndex(s)];
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
    
    public int keyToIndex(int key) {
    	return key%numBuckets;
    }
    public void readAndAdd(String fileLocation)  
	{  
		try{  
			File file=new File(fileLocation);    //creates a new file instance  
			FileReader fr=new FileReader(file);   //reads the file  
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
			for(String line="";(line=br.readLine())!=null;){  
//				line =  line.replaceAll("\\s","");
//				String line_norm = normalize(line);
//				int k = hashKey(normalize(line_norm));
				add(line);
			//	System.out.println(line+": "+line_norm+": "+k);  //strip of space!!
			
			}  
			fr.close();    //closes the stream and release the resources  
		}catch(IOException e){  
			e.printStackTrace();  
		}  
		
	} 
    public void initializePoints() {
    	 points= new HashMap<String,Integer>();
        points.put("q",10);points.put("z",10); 
        points.put("j",8);points.put("x",8); 
        points.put("k",5);
        points.put("f",4);points.put("h",4);points.put("v",4);points.put("w",4);points.put("y",4);
        points.put("b",3);points.put("c",3);points.put("m",3);points.put("p",3);
        points.put("g",2);points.put("d",2);
        points.put("l",1); points.put("s",1); points.put("u",1); points.put("n",1); points.put("r",1); points.put("t",1); points.put("o",1); points.put("a",1); points.put("i",1); points.put("e",1); 
    }
//    public static void main(String[] args) {//30637 , 39989,25163
//    	 MyHashTable map = new MyHashTable(57787,"src/lab11_scrabble/wordsList_collins2019_def.txt"); 
////	       map.findSubset("contrabnd-");
//    	//map.findSubset("abac");
////	      System.out.println( map.get("symphony"));
////	      System.out.println( map.get("messiah"));
////	      System.out.println( map.get("counterfeit"));
////	      System.out.println( map.get("contraband"));
////	      map.findSubstringsWithBag("abac");
//	      map.getValidWordsFromSubstrings("parsley");
//	      map.getValidWordsFromSubstrings("married");
//	      while(true) {
//	    	  System.out.print("Start cheating: ");
//        	  Scanner in = new Scanner(System.in);
//              String s = in.nextLine();
//              map.getValidWordsFromSubstrings(s);
//              map.lookUpWords();
//	      }
//    }
    public void lookUpWords() {
    	System.out.println("- You Entered dictionary -");
    	 System.out.print("Get definition: ");
   	  Scanner in = new Scanner(System.in);
         String s = in.nextLine();
    	while(!s.equals("q")) {
        	  
              String def = this.getDef(s);
              for(int i=0;i<def.length();i++) {
            	  if((int)def.charAt(i)==9) {
            		  if(i+1<def.length())
            			  def=def.substring(i+1);
            		  break;
            	  }
              }
              System.out.println("Def: "+def);
              System.out.println("- Type \"q\" to leave dictionary -");
              System.out.print("Get definition: ");
               s = in.nextLine();  
    	}
    }
    public void getValidWordsFromSubstrings(String str) {
    	findSubstringsWithBag(str);//substringList
    	if(!substringList.isEmpty()) {
    		System.out.println("------- "+str+" can spell "+" -------");
			
    		for(LinkedList<String> ithLengthSubstring:substringList) {
    			if(!ithLengthSubstring.isEmpty()) {
    				boolean dividerPrinted=false;
    				int items=0;
    				for(String s : ithLengthSubstring) {
    					LinkedList<String> perm;
//    					if(s.contains("-")) {
//    						LinkedList<String> blankstrings=new LinkedList<String>();
//    						for(int i=0;i<26;i++) {
//    							String temp=s.replaceFirst("-", ""+(char)('a'+i));
//    							if(temp.contains("-")) {
//    								for(int t=0;t<26;t++) {
//    	    							temp=temp.replaceFirst("-", ""+(char)('a'+t));
//    								}
//    							}
//    							blankstrings.add(temp);
//    						}
//    						perm=findPermutation(s);
//    					}else {
    						perm=findPermutation(s);
//    					}
    					if(!perm.isEmpty()) {
    						if(!dividerPrinted) {
    							System.out.println("------- "+s.length()+" letters -------");
    							dividerPrinted=true;
    						}
    						for(String s_p: perm) {
    							if(items!=0 && items%7==0)
    								System.out.println();
    							System.out.print(s_p+"  ");
    							items++;
    						}
    						
    					}
    				}
    				System.out.println();
    			}
    		}
    	}
    }
    public boolean isPermutation(String s1, String s2) {
		return normalize(s1).equals(normalize(s2));
    }
    public int hashKey(String keyStr) {
    	long key=0;
    	//57787
    	//94447: 16
    	//80677 : 17 ; 80527: 18 80051/80513；19
    	long prime1=94447;//39989;
    	for(int i=0;i<keyStr.length();i++) {
    		if(key>0 && i>0)
    			key = key*(long)(keyStr.charAt(i))*prime1*i%numBuckets;
    		else
    			key = (long)(keyStr.charAt(i))%numBuckets;
    	}
    	//int numOfBuckets = 139;
    	
    	return (int)key%numBuckets;
    }
} 