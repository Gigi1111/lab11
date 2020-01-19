package lab11_scrabble;

public class ScrabbleCheater11 {

	public ScrabbleCheater11() {

	}
	public static boolean isPrime(int num) {
		   if(num<=1)
		       return false;
		   for(int i=2;i<=Math.sqrt(num);i++){
		       if(num%i==0)
		           return false;
		   }
		   return true;
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
	  
		 MyHashTable11 htable = new MyHashTable11(7591,"src/lab11_scrabble/wordsList_collins2019.txt"); 
	    	
	        	System.out.print("All the words in the bucket where the word "+ " \"against\" " + " is located: ");
	        	System.out.println();
	        	htable.getWordsFromSameBucket("against");
	        	System.out.println();
	        	System.out.println();
	        	htable.findPermutation("against");
	        
	        	System.out.println();
	        	System.out.print("All the words in the bucket where the word "+ " \"airport\" " + " is located: ");
	        	System.out.println();
	        	htable.getWordsFromSameBucket("airport");
	        	System.out.println();
	        	System.out.println();
	        	htable.findPermutation("airport");
	        	
	        	System.out.println();
	        	System.out.print("All the words in the bucket where the word "+ " \"between\" " + " is located: ");
	        	System.out.println();
	        	htable.getWordsFromSameBucket("between");
	        	System.out.println();
	        	System.out.println();
	        	htable.findPermutation("between");

	          	System.out.println();
		        System.out.print("All the words in the bucket where the word "+ "\"married\"" + " is located: ");
		        System.out.println();htable.getWordsFromSameBucket("married");
		        System.out.println();
		        System.out.println();
		        htable.findPermutation("married");
		        
		        System.out.println();
	        	System.out.print("All the words in the bucket where the word "+ " \"ashbdap\" " + " is located: ");
	        	System.out.println();
	        	htable.getWordsFromSameBucket("ashbdap");
	        	System.out.println();
	        	System.out.println();
	        	htable.findPermutation("ashbdap");
	        	
	        	System.out.println(htable.remove("speaned"));
	    } 
}