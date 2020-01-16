package lab11_scrabble;

public class Scrabble_Cheater {

	 public static void main(String[] args) 
	    { 
	    	
	        MyHashTable<String, String> map = new MyHashTable<>(110047,"src/lab11_scrabble/wordsList_collins2019.txt"); 
	
	        
	
////	        map.findPermutation("married");
////	        map.findPermutation("rabbies");
////	        map.findPermutation("Parsley");
	       int numOfLetters=7;
	      
	       char[] arr =new char[numOfLetters]; 
	        for(int i=0;i<numOfLetters;i++) {
	        	int rnd = (int) (Math.random() * 26);
	            char base = 'a';            
	        	arr[i]=(char) (base + rnd % 26);
	        }
	        String rand=new String(arr);
	       
	        map.findSubset(rand);
	     
	         // map.getWordsFromSameBucket("dog");
	    } 
	    
}
