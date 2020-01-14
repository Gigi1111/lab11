//package lab11_scrabble;
//import java.io.*;
//import java.util.LinkedList;  
//
//public class Dictionary {
//
//	public Dictionary(String filelocation){
//		MyHashTable ht = new MyHashTable();
//		try{  
//			File file=new File(filelocation);    //creates a new file instance  
//			FileReader fr=new FileReader(file);   //reads the file  
//			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
//			for(String line="";(line=br.readLine())!=null;){  
//				ht.add(ht.hashKey(ht.normalize(line)),line);
//				ht.get(key)
//				
//			}  
//			fr.close();    //closes the stream and release the resources  
//		}catch(IOException e){  
//			e.printStackTrace();  
//		}  
//	}
//	public static void main(String args[])  
//	{  
//		Dictionary d = new Dictionary("src/lab11_scrabble/wordsList.txt");
//	}  
//}
