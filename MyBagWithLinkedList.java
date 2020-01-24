package lab12_scrabble_cheater;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class MyBagWithLinkedList implements MyBag{
	String s_input;
//	store letters
	LinkedList<String> list = new LinkedList<String>();
	//store substrings
	static LinkedList<LinkedList<String>> substrList;
	int[] charCount = new int[13];//1-12
	
	public MyBagWithLinkedList(String str) {
		s_input = normalize(str);
		for(char c : s_input.toCharArray()) {
			add(""+c);
		}
		substrList=new LinkedList<LinkedList<String>>();
	}
	public void charCount() {
		for(int i=0; i<charCount.length;i++) {
			charCount[i]=0;
		}
		for(int i = 0 ; i < s_input.length() ;) {
			char c = s_input.charAt(i);
			int count = s_input.length() - s_input.replaceAll(""+s_input.charAt(i),"").length();
			i+=count;//aaabc
			for(int j=1; j<charCount.length && j<=count;j++) {
				charCount[j]++;
			}
		}
	}
    public String normalize(String s) {
    	s=s.toLowerCase();
        char temp[] = s.toCharArray(); 
        Arrays.sort(temp); 
        return new String(temp); 
    }
	public boolean add(String c) {
		return list.add(c);
	}
	public boolean remove(String s) {
		return list.remove(s);
	}
	public String grab() {
		int size = size();
		int r = (int)(Math.random()*size);
			String temp =list.get(r);
			list.remove(r);
		return temp;
	}
	
	public boolean contains(String s) {
		return list.contains(s);
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	public int size() {
		int size = list.size();
		return size;
	}
	public String toString() {
		String all = "Bag elements: ";
		if(!isEmpty()) {
			 all+=list.toString();
			 return all;
		}		
		return "Bag is empty";
	}
	 
	
	public LinkedList<String> getSubstringsOfLength(int k){
		int size = size();
		
		LinkedList<String> sub = new LinkedList<String>();
		
		//from picking k elements from n
		int combi_num = getHowManyCombiThereIs(k);

		int contains=0;
		while(sub.size()<combi_num && contains<100) {
			String temp="";
			MyBagWithLinkedList bag_temp = new MyBagWithLinkedList(s_input);
			for(int i=0;i<k;i++) {
				temp+=bag_temp.grab();
			}
			temp = normalize(temp);
			if(!sub.contains(temp)) {
				sub.add(temp);
			}else contains++;	
		}
		return sub;
	}
	//normally from n diff elements pick k is n!/(k!*(n-k)!)
	//but since we may use same elements 
	public int getHowManyCombiThereIs(int k) {
		//if k = 5, 5 same, 4 same, 3 same, 2 same, all diff
		//if k = 4, 4 same, 3 same, 2 same , all diff
		//if k =3, 3 same, 2 same, all diff
		//if k =2, 2 same, all diff
		charCount();
		int combi=0;
		int str_length = s_input.length();
	//	System.out.println("s_input: "+s_input);
		if(str_length<k) return 0;
		
		//if no repetitive elements
		if(charCount[2]==0) return C(charCount[1],k);
		if(k==str_length) return 1;
		
		//5:(5 same, 4 same, 3 same (3 same 2 diff, 3 same 2 same), 2 same (2 s 3 dif, 2 s 2s 1)
		for(int i=k;i>=1;i--) {// how many same
			//choose k, k has i same char
			if(i>1 && k-i>0) {
				int c1=0;
				for(int y=1;i-y>=1;y++) {//i-1...=1
					c1 += C(charCount[i],1)*C(charCount[i-y]-1,k-i);
					if(i*2<=k) {
						c1 += C(charCount[i],2)*C(charCount[i-y]-1,k-i*2);
					}
				}
				combi+=c1;
			}else{//i==k || i==1
				int c2 = C(charCount[i],i==k?1:k);
				combi+=c2;
			}
		}
		return combi;
	}
	public static void main(String args[])  {
		 String str= "parsley";
		MyBag bag = new MyBagWithLinkedList(str);
		System.out.println(bag.toString());
//		System.out.println("grabbed: "+bag.grab());
//		System.out.println(bag.toString());
		int size = bag.size();
		System.out.println("Substrings of "+str+" :");
		for(int i =2; i<=size;i++) {//get 2,3...,size lengths of substrings
			LinkedList<String> substringsOfCertainLength= bag.getSubstringsOfLength(i);
			substrList.add(substringsOfCertainLength);
			for(int j=0;j<substringsOfCertainLength.size();j++) {
				System.out.println(substringsOfCertainLength.get(j));
				
			}
		}
		bag.charCount();
		
	}
	public int C(int n, int m) {
		if(n==0) return 0;
		return fac(n)/(fac(m)*fac(n-m));
	}
	public int fac(int n) {
		if(n<=1) return 1;
		return fac(n-1)*n;
	}
	
}
