package lab12_scrabble_cheater;

import java.util.Scanner;

public class Scrabble_Cheater {
	static MyBag scrabble_letters;
	public Scrabble_Cheater(){
		//instantiate MyBag of scrabble letters for english
		//1 kjxqz , 2 bcmpfhvwy, 3g 
		//4 lsud,6 nrt,8 o,9 ai,12 e,2 blank
		scrabble_letters = new MyBagWithLinkedList("--kjxqzbcmpfhvwybcmpfhvwyggglsudlsudlsudlsudnrtnrtnrtnrtnrtnrtooooooooaiaiaiaiaiaiaiaiaieeeeeeeeeeee");
	}
	public static void main(String[] args) {//30637 , 39989,25163
		Scrabble_Cheater cheater = new Scrabble_Cheater();
	MyHashTable map = new MyHashTable(57787,"src/lab12_scrabble_cheater/wordsList_collins2019_def.txt"); 
//     map.findSubset("contrabnd-");
	//map.findSubset("abac");
//    System.out.println( map.get("symphony"));
//    System.out.println( map.get("messiah"));
//    System.out.println( map.get("counterfeit"));
//    System.out.println( map.get("contraband"));
//    map.findSubstringsWithBag("abac");
//    map.getValidWordsFromSubstrings("parsley");
//    map.getValidWordsFromSubstrings("married");
    while(true) {
    System.out.println("Press Enter to randomly generate 7 letters");
  	  System.out.print("Start cheating: ");
  	  Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        if(s.isEmpty()) {
        	s=cheater.generateRandom(7);
        }
        map.getValidWordsFromSubstrings(s);
        map.lookUpWords();
    }
	}
	public String generateRandom(int numOfLetters) {
		String temp = ""; 
		for(int i=0;i<numOfLetters;i++) {
			 temp+=scrabble_letters.grab();
		 }
		return temp;
	}
}