import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;




/** Main class for the Spell-Checker program */
public class SpellCheck {

	public static void main(String[] args) throws java.io.IOException, MapException{
		if (args.length != 2 ) {
			System.out.println("Usage: SpellCheck dictionaryFile.txt inputFile.txt ");
			System.exit(0);
		}
		
		//LinkedListMap M1 = new LinkedListMap();
		StringHashCode shc = new StringHashCode();
		HashTableMap HashMap = new HashTableMap(shc, 0.75f);
		long startTime,finishTime;
		startTime = System.currentTimeMillis();
		BufferedInputStream dict,file;
			
			dict  = new BufferedInputStream(new FileInputStream(args[0]));
			FileWordRead readDict = new FileWordRead(dict);

			file  = new BufferedInputStream(new FileInputStream(args[1]));
			FileWordRead readFile = new FileWordRead(file);
			
		try{
			
			while (readDict.hasNextWord()){
				
				//M1.insert(readDict.nextWord());
				
					HashMap.insert(readDict.nextWord());
			}
			
			
			
			
			
			dict.close();
			
			
			}
		catch (IOException e){ // catch exceptions caused by file input/output errors
			System.out.println("Check your file name");
			System.exit(0);
		}
	
		String word;
		
		while(readFile.hasNextWord()){
			
			word = readFile.nextWord();
		
			if (!(HashMap.find(word))){
				
				reversal(HashMap, word);
				substitution(HashMap, word);
				omission(HashMap, word);
				insertion(HashMap, word);
				
			}
			
			
		}
		
	
			
		finishTime = System.currentTimeMillis();
	
		
		
		file.close();
		
		System.out.println(finishTime - startTime);
		}

	public static Boolean reversal(HashTableMap M1, String word) {
		Boolean delete = false;
		int i;
		char temp;
		
		LinkedList<String> Worders = new LinkedList<>();
		
		String MisspeltWord = word;
		
		char[] wordChar = MisspeltWord.toCharArray();
		
		
		for (i = 0; i < wordChar.length - 1; i++ ){

			temp = wordChar[i];
			wordChar[i] = wordChar[i+1];
			wordChar[i+1] = temp;
			
			String SwappedWord = new String(wordChar);

			if (M1.find(SwappedWord) && !(word.equals(SwappedWord)) && !(Worders.contains(word + " => " + SwappedWord + "		" + "reversal"))) {
				//System.out.println(word + "==>" + SwappedWord + "		reversal");
				Worders.add(word + " => " + SwappedWord + "		" + "reversal");
				delete = true;
			}
			else {
				temp = wordChar[i];
				wordChar[i] = wordChar[i+1];
				wordChar[i+1] = temp;
			}
		}
		if (delete == true){
			for (String string: Worders){
				System.out.println(string);
			}
		}
		return delete;
		

	}

	public static Boolean omission(HashTableMap M1, String word) {
		Boolean delete = false;
		int i;
		LinkedList<String> Worders = new LinkedList<>();
		StringBuilder MisspeltWord = new StringBuilder(word);

		for (i = 0; i < MisspeltWord.length(); i++) {
			char placeholder = MisspeltWord.charAt(i);
			MisspeltWord.deleteCharAt(i);
			String wordOmission = MisspeltWord.toString();

			if (M1.find(wordOmission)  && !(Worders.contains(word + " => " + wordOmission + "		" + "omission"))) {
				//System.out.println(word + "==>" + wordOmission + "		omission");
				Worders.add(word + " => " + wordOmission + "		" + "omission");
				delete = true;
			}
			MisspeltWord.insert(i, placeholder);
		}
		if (delete == true){
			for (String string: Worders){
				System.out.println(string);
			}
		}
		return delete;
	}

	public static Boolean insertion(HashTableMap M1, String word) {
		Boolean delete = false;
		int i, j;
		LinkedList<String> Worders = new LinkedList<>();
		char[] alphabet = new char[26];

		for (char ch = 'a'; ch <= 'z'; ++ch) {
			alphabet[ch - 'a'] = ch;
		}

		StringBuilder MisspeltWord = new StringBuilder(word);

		for (i = 0; i < MisspeltWord.length() + 1; i++) {
			for (j = 0; j < alphabet.length; j++) {
				MisspeltWord.insert(i, alphabet[j]);
				String wordInsertion = MisspeltWord.toString();

				if (M1.find(wordInsertion)  && !(Worders.contains(word + " => " + wordInsertion + "		" + "omission"))) {
					//System.out.println(word + "==>" + wordInsertion + "		insertion");
					Worders.add(word + " => " + wordInsertion + "		" + "insertion");
					delete = true;
				}
				MisspeltWord.deleteCharAt(i);
			}
		}
		if (delete == true){
			for (String string: Worders){
				System.out.println(string);
			}
		}
		return delete;

	}

	public static Boolean substitution(HashTableMap M1, String word) {
		Boolean delete = false;
		LinkedList<String> Worders = new LinkedList<>();
		int i, j;
		char charholder;
		char[] alphabet = new char[26];

		for (char ch = 'a'; ch <= 'z'; ++ch) {
			alphabet[ch - 'a'] = ch;
		}

		StringBuilder MisspeltWord = new StringBuilder(word);

		for (i = 0; i < MisspeltWord.length(); i++) {

			for (j = 0; j < alphabet.length; j++) {

				charholder = MisspeltWord.charAt(i);

				MisspeltWord.setCharAt(i, alphabet[j]);

				String wordSubstitution = MisspeltWord.toString();
				
				if (M1.find(wordSubstitution) && !(word.equals(wordSubstitution))  && !(Worders.contains(word + " => " + wordSubstitution + "		" + "substitution"))) {
					//System.out.println(word + "==>" + wordSubstitution + "		substitution");
					Worders.add(word + " => " + wordSubstitution + "		" + "substitution");
					delete = true;
					
				}
				MisspeltWord.setCharAt(i, charholder);
			}

		}
		if (delete == true){
			for (String string: Worders){
				System.out.println(string);
			}
		}
		
		return delete;
	}
}
