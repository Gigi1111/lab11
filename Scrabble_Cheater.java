package lab11_scrabble;

import java.util.LinkedList;

public class Scrabble_Cheater {

	static LinkedList<String> words;
	public Scrabble_Cheater() {
		
		initializeWords();
	}
	
	
	 public static void main(String[] args) 
	    { 
		 Scrabble_Cheater cheat = new Scrabble_Cheater();
	        MyHashTable<Integer, String> map = new MyHashTable<>(110047,"src/lab11_scrabble/wordsList_collins2019.txt"); 
	        Dictionary dict = new Dictionary(110047,"src/lab11_scrabble/wordsList_collins2019_def.txt");
	        
//	        map.findPermutation("married");
////	        map.findPermutation("rabbies");
////	        map.findPermutation("Parsley");
	      
	        //String rand = cheat.generateRandomLetters(7);
	        String rand = cheat.generateFromScrabbleDistribution(7);
	        LinkedList<String> forDict=map.findSubset(rand);
//	      LinkedList<String> forDict=map.findSubset("anagram");
	        cheat.lookUpSubset(forDict,dict);
	         // map.getWordsFromSameBucket("dog");
	    } 
	 public String generateRandomLetters(int numOfLetters) {

	       char[] arr =new char[numOfLetters]; 
	        for(int i=0;i<numOfLetters;i++) {
	        	int rnd = (int) (Math.random() * 26);
	            char base = 'a';            
	        	arr[i]=(char) (base + rnd % 26);
	        }
	        return new String(arr);
	 }
	 public String generateFromScrabbleDistribution(int numOfLetters) {

	       String str=""; 
	        for(int i=0;i<numOfLetters;i++) {
	        	int r=(int)(Math.random()*words.size());
	        	str+=words.get(r);
	        	words.remove(r);
	        }
	        return str;
	 }
	 public void lookUpSubset(LinkedList<String> forDict,Dictionary dict) {
		  System.out.println("=========Definition=========");
			 System.out.println("");
	      for(String word:forDict) {
	    	  dict.getDefinition(word);
	      }
	 }
	   
	 public static void initializeWords() {
		 words=new LinkedList<String>();
		 for(int i=0;i<12;i++)
				words.add("e");
			for(int i=0;i<9;i++) {
				words.add("a");
				words.add("i");}
			for(int i=0;i<8;i++)
				words.add("o");
			for(int i=0;i<6;i++) {
				words.add("n");
				words.add("r");
				words.add("t");
			}
			for(int i=0;i<4;i++) {
				words.add("l");
				words.add("s");
				words.add("u");
				words.add("d");
			}
			for(int i=0;i<3;i++) 
				words.add("g");
			for(int i=0;i<2;i++) {
				words.add("b");
				words.add("c");
				words.add("m");
				words.add("p");
				words.add("f");
				words.add("h");
				words.add("v");
				words.add("w");
				words.add("y");
			}
			for(int i=0;i<1;i++) {
				words.add("k");
				words.add("j");
				words.add("x");
				words.add("q");
				words.add("z");
			}
	}
}
