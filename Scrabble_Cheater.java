package lab11_scrabble;

import java.util.LinkedList;

public class Scrabble_Cheater {

	
	 public static void main(String[] args) 
	    { 
	    	
	        MyHashTable<Integer, String> map = new MyHashTable<>(110047,"src/lab11_scrabble/wordsList_collins2019.txt"); 
	        Dictionary dict = new Dictionary(110047,"src/lab11_scrabble/wordsList_collins2019_def.txt");
	        
//	        map.findPermutation("married");
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

	        LinkedList<String> forDict=map.findSubset(rand);
//	      LinkedList<String> forDict=map.findSubset("anagram");
	      System.out.println("=========Definition=========");
			 System.out.println("");
	      for(String word:forDict) {
	    	  dict.getDefinition(word);
	      }
	         // map.getWordsFromSameBucket("dog");
	    } 
	    
}
