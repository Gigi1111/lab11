package lab12_scrabble_cheater;

import java.util.LinkedList;

public interface MyBag_Interface {
			String input_str="";
//			store letters
			LinkedList<String> letters = new LinkedList<String>();
			
		    public String normalize(String s);
			public String add(String c);
			public String remove(String s);
			public String grab();
			public boolean contains(String s);
			public boolean isEmpty();
			public int size();
			public String toString();
}