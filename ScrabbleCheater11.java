package lab11_scrabble;

public class ScrabbleCheater11 {

	public ScrabbleCheater11() {

	}
	
	
	/*
	 *Driver method for the Scrabble Cheater Basic Edition (Lab 11).
	 *Instantiates a new Dictionary, HashTable and Scrabble Cheater.
	 *Prints statistical information about the HashTable and then
	 *looks for words in the same bucket(index) as a given word. 
	 *In the end prints only the permutations(anagrams) of that word.
	 */
	
	 public static void main(String[] args) 
	    { 
	//	 ScrabbleCheater11 cheat = new ScrabbleCheater11();
	//	 Dictionary11 dict = new Dictionary11(1,"C:\\words\\words-279k.txt");
	     MyHashTable11<Integer, String> map = new MyHashTable11<>(75011,"src/lab11_scrabble/wordsList_collins2019.txt"); 
	        	
	     	
	        
	        	System.out.println();
	        	System.out.print("All the words in the bucket where the word "+ " \"against\" " + " is located: ");
	        	System.out.println();
	        	map.getWordsFromSameBucket("against");
	        	System.out.println();
	        	System.out.println();
	        	map.findPermutation("against");
	        
	        	System.out.println();
	        	System.out.print("All the words in the bucket where the word "+ " \"airport\" " + " is located: ");
	        	System.out.println();
	        	map.getWordsFromSameBucket("airport");
	        	System.out.println();
	        	System.out.println();
	        	map.findPermutation("airport");
	        	
	        	System.out.println();
	        	System.out.print("All the words in the bucket where the word "+ " \"between\" " + " is located: ");
	        	System.out.println();
	        	map.getWordsFromSameBucket("between");
	        	System.out.println();
	        	System.out.println();
	        	map.findPermutation("between");

	          	System.out.println();
		        System.out.print("All the words in the bucket where the word "+ "\"married\"" + " is located: ");
		        System.out.println();map.getWordsFromSameBucket("married");
		        System.out.println();
		        System.out.println();
		        map.findPermutation("married");
		        
		        System.out.println();
	        	System.out.print("All the words in the bucket where the word "+ " \"ashbdap\" " + " is located: ");
	        	System.out.println();
	        	map.getWordsFromSameBucket("ashbdap");
	        	System.out.println();
	        	System.out.println();
	        	map.findPermutation("ashbdap");

	    } 
}