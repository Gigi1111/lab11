package lab12_scrabble_cheater;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class MyBagWithLinkedList implements MyBag{
	String s_old;
//	store letters
	LinkedList<String> list = new LinkedList<String>();
	//store substrings
	static LinkedList<LinkedList<String>> arr;
	int sizeOfDifferentElements=0;
	public MyBagWithLinkedList(String str) {
		s_old = normalize(str);
		for(char c : s_old.toCharArray()) {
			add(""+c);
		}
		arr=new LinkedList<LinkedList<String>>();
	}

    public String normalize(String s) {
    	s=s.toLowerCase();
        char temp[] = s.toCharArray(); 
        Arrays.sort(temp); 
        return new String(temp); 
    }
	public String add(String c) {
		
		//check if c is a new element or not
		if(!list.contains(c)) sizeOfDifferentElements++;
		list.add(c);
		
		return c;
	}
	public String remove(String s) {
		
		if(contains(s)) {
			 for (String i : list) {
			        if (i.equals(s)) {
			            list.remove(i);
			            break;
			        }
		}
			 return s;
		}
		return null;
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
//			 for (String s : list) {
//			        all+=list.get(i)+"  ";
//			 }
			 all+=list.toString();
		}
		
		return all;
	}
//	 public static void main(String args[])  {
//		MyBag bag = new MyBag("married");
//		System.out.println(bag.toString());
////		System.out.println("grabbed: "+bag.grab());
////		System.out.println(bag.toString());
//		int size = bag.size();
//		System.out.println("Substrings of "+bag.s_old+" :");
//		for(int i =2; i<=size;i++) {//get 2,3...,size lengths of substrings
//			LinkedList<String> substringsOfCertainLength= bag.getSubstringsOfLength(i);
//			arr.add(substringsOfCertainLength);
//			for(int j=0;j<substringsOfCertainLength.size();j++) {
//				System.out.println(substringsOfCertainLength.get(j));
//				
//			}
//		}
//		
//	}
	
	public LinkedList<String> getSubstringsOfLength(int k){
		int size = size();
		
		LinkedList<String> sub = new LinkedList<String>();
		
		int combi_num = getHowManyCombiThereIs(sizeOfDifferentElements,k);//from picking k elements from n
		//System.out.println("combi_num for "+k+" elements: "+combi_num);
		//bags allow dupe
		//li
		//aab->aa ab     -1
		//abc-> ab ac bc
		//aaab ->aa ab             
		//abcd ->ab ac ad bc bd cd
		
		int contains=0;
		while(sub.size()<combi_num && contains<100) {
			String temp="";
			MyBagWithLinkedList bag_temp = new MyBagWithLinkedList(s_old);
			for(int i=0;i<k;i++) {
				temp+=bag_temp.grab();
			}
			temp = normalize(temp);
		//	System.out.println("temp:"+temp);
			if(!sub.contains(temp)) {
				sub.add(temp);
			//	System.out.println("added");
			}else contains++;
			
		}
		
		
		return sub;
	}
	//normally from n diff elements pick k is n!/(k!*(n-k)!)
	//but since we may use same elements 
	public int getHowManyCombiThereIs(int n, int k) {
		int i=0;
		n=size();
		if(n<k) return 0;
	//	System.out.println("n,k :"+n+", "+k);
		int n_fac=factorial(n);
		int k_fac=factorial(k);
		int nminusk_fac = factorial(n-k);
		//System.out.println("n,k,n_fac,k_fac,nminusk_fac");
		//System.out.println(n+", "+k+", "+n_fac+", "+k_fac+", "+nminusk_fac);
		return n_fac/(k_fac*nminusk_fac);
	}
	public int factorial(int n) {
		if(n<=1) return 1;
		return factorial(n-1)*n;
	}
}
