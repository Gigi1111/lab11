package lab12_scrabble_cheater;

import java.util.LinkedList;

public interface MyBag {
			
		    public String normalize(String s);
			public String add(String c);
			public String remove(String s);
			public String grab();
			public boolean contains(String s);
			public boolean isEmpty();
			public int size();
			public String toString();
			
			public LinkedList<String> getSubstringsOfLength(int k);
			//normally from n diff elements pick k is n!/(k!*(n-k)!)
			//but since we may use same elements 
			public int getHowManyCombiThereIs(int k);
			public int fac(int n) ;
			public void charCount();
}