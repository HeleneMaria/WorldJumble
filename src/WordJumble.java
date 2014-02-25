import java.io.*;
import java.util.*;

/**
 * 
 * @author Hélène MARIA
 *
 */
public class WordJumble {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//*************VARIABLES*************//
		String input ="";	
		Scanner sc = new Scanner(System.in);
		char[] inputChar;
		int index=2;
		//First char of the input string
		String s1;
		//Second char of the input string
		char c2;
		//We use a linkedlist finalList to register all the possible words creating from the input (cause the number of elements can be more than Integer.MAX_VALUE)
		LinkedList<String> finalList = new LinkedList<String>();
		Set<String> checkedList = new HashSet<String>();
		//*************GET THE WORDS OF THE DICTIONNARY*************//
		//Get the list of words from the file american-words.60
		ArrayList<String> spellingList = fileReading();	
		//*************GET THE INPUT STRING*************//
		System.out.print("Enter the string : ");		
		input=sc.next();		
		inputChar = input.toCharArray();
		s1= String.valueOf( inputChar[0]);
		c2= inputChar[1];
		
		//*************ALGORITHM*************//
		//We add the first letter in the final list
		finalList.add(s1);
		//For every letter in the input string, we create all the anagrams possible from each words in the final List adding with the letter
		while(index<input.length()+1){
			int size = finalList.size();
			for(int i=0;i<size;i++){
				finalList.addAll(Anagrams(finalList.get(i),c2, spellingList));		
			}
			//we add the letter only if there are words beginning with that letters
			if(checkSpellingSubstring(String.valueOf(c2),spellingList))
				finalList.add(String.valueOf(c2));
			if(index<input.length())
				c2=inputChar[index];
			index++;
		}
		
		//copy all the words containing in the file in the collection checkedList
		for(int i=0;i<finalList.size();i++){
			if(checkSpelling(finalList.get(i),spellingList))
				checkedList.add(finalList.get(i));
		}
		
		//*************DISPLAY THE RESULT*************//
		//Print the words that are checked based on the file
		System.out.println("\nWord Jumble of "+input+" are :");
		Iterator iter = checkedList.iterator();
		while (iter.hasNext()) {
		  System.out.println(iter.next());
		}
	}
	
	/**
	 * 
	 * @param s string to test
	 * @param c char to add to the string
	 * @param spellingList the list to check if the word is containing in it
	 * @return a array of String comprising the anagrams creating by the string s and the char c
	 * We just insert the letter in each position possible in the string, check that it is substring of one of the word in the list, and add it to the returned list
	 */
	public static Vector<String> Anagrams(String s, char c, ArrayList<String> spellingList){
		Vector<String> anagrams = new Vector<String>();
		char[] temp = new char[s.length()+1];
		char[] myString = s.toCharArray();
		for(int i=0;i<s.length()+1;i++){
			//For duplicates
			if(i<s.length() && myString[i]==c ){
				continue;
			}
			for(int j=0;j<s.length();j++){
				if(j<i)
					temp[j]=myString[j];
				if(j>=i)
					temp[j+1]=myString[j];				
			}
			temp[i]=c;
			//We add the word only if one of the word in the list begins by it
			if(checkSpellingSubstring(String.valueOf(temp),spellingList))
				anagrams.add(String.valueOf(temp));
		}
		return anagrams;
	}
	
	
	//Check that the word is a beginning of at least a string in the file by doing a binary search in the array spellingList
	/**
	 * 
	 * @param word to check
	 * @param spellingList list of all the words
	 * @return true if the word is a substring of at least one of the words in the list
	 */
	public static boolean checkSpellingSubstring(String word, ArrayList<String> spellingList){
		//spellingList is sorted - binary search
		int low=0;
		int high= spellingList.size()-1;
		int mid=(low+high)/2;
		int sizeToCompare = spellingList.get(mid).length();
		if(word.length()<sizeToCompare) sizeToCompare=word.length();
		//if the words in the list begin by the searched word
		while(low<=high && spellingList.get(mid).substring(0,sizeToCompare).compareTo(word)!=0){
			if(spellingList.get(mid).substring(0,sizeToCompare).compareTo(word)<0){
				low=mid+1;
			}else{
				high=mid-1;
			}			
			mid=(low+high)/2;
			sizeToCompare = spellingList.get(mid).length();
			if(word.length()<sizeToCompare) sizeToCompare=word.length();
		}
		if(low>high)
			return false;
		
		return true;
	}
	
	/**
	 * 
	 * @param word to check
	 * @param spellingList list of all the words
	 * @return true if the word is in the list, false otherwise
	 */
	public static boolean checkSpelling(String word, ArrayList<String> spellingList){
		//spellingList is sorted - binary search
		int low=0;
		int high= spellingList.size()-1;
		int mid=(low+high)/2;
		//if the word is in the list
		while(low<=high && spellingList.get(mid).compareTo(word)!=0){
			if(spellingList.get(mid).compareTo(word)<0){
				low=mid+1;
			}else{
				high=mid-1;
			}			
			mid=(low+high)/2;
		}
		if(low>high)
			return false;
		
		return true;
	}
	
	/**
	 * 
	 * @return an array of string containing all the word comprised in our dictionnary, the file american-words.60
	 */
	public static ArrayList<String> fileReading(){
		BufferedReader bis=null;
		ArrayList<String> spellingList = new ArrayList<String>();
		try {
			bis=new BufferedReader(new FileReader("scowl-7.1\\final\\american-words.60"));	
			//Go through all the lines in the file, and register each one (if not null) in the arraylist spellingList
			 for (;;) {
			        String line = bis.readLine();
			        if (line == null)
			            break;
			        spellingList.add(line);			        
			     }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return spellingList;
	}

}
