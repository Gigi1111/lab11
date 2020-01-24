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
	int[] charCount = new int[13];//1-12
	
	public MyBagWithLinkedList(String str) {
		s_old = normalize(str);
		for(char c : s_old.toCharArray()) {
			add(""+c);
		}
		arr=new LinkedList<LinkedList<String>>();
	}
	public void charCount() {
		for(int i=0; i<charCount.length;i++) {
			charCount[i]=0;
		}
		for(int i = 0 ; i < s_old.length() ;) {
			char c = s_old.charAt(i);
			int count = s_old.length() - s_old.replaceAll(""+s_old.charAt(i),"").length();
			i+=count;//aaabc
			for(int j=1; j<charCount.length && j<=count;j++) {
				charCount[j]++;
			}
		}
	
//		for(int k=1; k<charCount.length ;k++) {
//			if(charCount[k]!=0)
//				System.out.println(k+": "+charCount[k]);
//		}
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
	 
	
	public LinkedList<String> getSubstringsOfLength(int k){
		int size = size();
		
		LinkedList<String> sub = new LinkedList<String>();
		
		int combi_num = getHowManyCombiThereIs(k);//from picking k elements from n
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
	public int getHowManyCombiThereIs(int k) {
		//charCount
		//if k = 5, 5 same, 4 same, 3 same, 2 same, all diff
		//if k = 4, 4 same, 3 same, 2 same , all diff
		//if k =3, 3 same, 2 same, all diff
		//if k =2, 2 same, all diff
		charCount();
		int combi=0;
		int str_length = s_old.length();
	//	System.out.println("s_old: "+s_old);
		if(str_length<k) return 0;
		
		//if no repetitive elements
		if(charCount[2]==0) return C(charCount[1],k);
		if(k==str_length) return 1;
		
		//5:(5 same, 4 same, 3 same (3 same 2 diff, 3 same 2 same), 2 same (2 s 3 dif, 2 s 2s 1)
		for(int i=k;i>=1;i--) {// how many same
//			if(i==3 && charCount[i]!=0)
//				combi+=C(charCount[i],1);//+C(charCount[i-1],1)*C(charCount[i-2],1)+C(charCount[i-2],3);
//			else if(i==2 && charCount[i]!=0){//2 same, 1 diff
//				combi+=C(charCount[i],1);
//			}
//			else if(i==1){//i==1
//				combi+=C(charCount[i],k);
//			}
			//choose k, k has i same char
			if(i>1 && k-i>0) {//in 3 2 same aab           2        1
				//aaccb 3, aac,  acc,acb, // ccb,aab,
				int c1=0;//aaccc 4, aacc, accc
				//aaccc 3, aac, acc, ccc
				//"aabcc" aabc, abcc, aacc
				for(int y=1;i-y>=1;y++) {//i-1...=1
					c1 += C(charCount[i],1)*C(charCount[i-y]-1,k-i);
				//	System.out.println("c1+"+C(charCount[i],1)*C(charCount[i-y]-1,k-i));
					if(i*2<=k) {
						c1 += C(charCount[i],2)*C(charCount[i-y]-1,k-i*2);
						
					}
				}
			//	System.out.println("k/i/cci:"+k+"/"+i+"/"+C(charCount[i],1)+" if plus: "+ c1);
				combi+=c1;
				
			}else{//i==k || i==1
				//aaccb 2, aa,ac,ab, cc,cb
				int c2 = C(charCount[i],i==k?1:k);
			//	System.out.println("k/i:"+k+"/"+i+" else plus: "+ c2);
				
				combi+=c2;
			
			}
//			for(int t=k-i;t>=1;t--) {
//				combi+=C(charCount[i],1)*;
//			}
//			for(int t=i;t>1;t--) {
//				combi+=C(charCount[t],)
//			}
		}
//		System.out.println("k: "+k+"; combi: "+combi);
		return combi;
		//return n_fac/(k_fac*nminusk_fac);
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
			arr.add(substringsOfCertainLength);
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
