package lab11_scrabble;

public class Dictionary {
	MyHashTable_def<Integer, String> dict ;
	public Dictionary(int size, String filelocation){

      dict = new MyHashTable_def<>(size,filelocation); 
        

	}
	public void getDefinition(String str) {
		System.out.println(dict.get(str));
	}
	public static void main(String args[])  
	{  
		Dictionary dict = new Dictionary(110047,"src/lab11_scrabble/wordsList_collins2019_def.txt");
        dict.getDefinition("KEY");
	}  
}
