package lab11_scrabble;
import java.io.*;
import java.util.LinkedList;  

public class Dictionary {

	public static void main(String args[])  
	{  
		try{  
			File file=new File("src/lab11_scrabble/wordsList.txt");    //creates a new file instance  
			FileReader fr=new FileReader(file);   //reads the file  
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
			for(String line="";(line=br.readLine())!=null;){  
				System.out.println(line);    
			}  
			fr.close();    //closes the stream and release the resources  
		}catch(IOException e){  
			e.printStackTrace();  
		}  
	}  
}
