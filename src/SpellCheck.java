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
		
		HashTableMap HashMap = new HashTableMap(shc, 0.75f); // create new hash table
		
		//long startTime,finishTime;
		//startTime = System.currentTimeMillis();
		
		BufferedInputStream dict,file;
			
			dict  = new BufferedInputStream(new FileInputStream(args[0]));
			
			FileWordRead readDict = new FileWordRead(dict);

			file  = new BufferedInputStream(new FileInputStream(args[1]));
			
			FileWordRead readFile = new FileWordRead(file);
			
		try{
			
			while (readDict.hasNextWord()){
				
				//M1.insert(readDict.nextWord());
				HashMap.insert(readDict.nextWord()); //try to insert the read word into the hash table
				
			}
			
			dict.close();
			
			
			}
		catch (IOException e){ // catch exceptions caused by file input/output errors
			
			System.out.println("Check your file name");
			System.exit(0);
			
		}
	
		String word;
		
		while(readFile.hasNextWord()){// until we reach the last word in the file
			
			word = readFile.nextWord();//read the next word
		
			if (!(HashMap.find(word))){// if the word is not in the hash table then try all the modifications
				
				reversal(HashMap, word); 
				substitution(HashMap, word);
				omission(HashMap, word);
				insertion(HashMap, word);
				
			}
			
			
		}
		
		//finishTime = System.currentTimeMillis();
	
		file.close();
		
		//System.out.println(finishTime - startTime);
		}

	public static void reversal(HashTableMap M1, String word) {//reverses each adjacent character pair
		
		Boolean delete = false;
		
		int i;
		
		char temp;
		
		LinkedList<String> DuplicateRemover = new LinkedList<>();//will store correct modified words
		
		char[] charSwapper = word.toCharArray();//split the word into separate characters
		
		
		for (i = 0; i < charSwapper.length - 1; i++ ){// for each character in the word
			//swap adjacent characters
			temp = charSwapper[i];
			charSwapper[i] = charSwapper[i+1];
			charSwapper[i+1] = temp;
			
			String SwappedWord = new String(charSwapper);//turn char array back into string for equality testing

			if (M1.find(SwappedWord) && !(word.equals(SwappedWord)) && !(DuplicateRemover.contains(word + " => " + SwappedWord))) {
				
				//if the newly modified word is contained in the hash table, not just the same as the misspelled word and not contained in our linked list then add the word to the linked list
				DuplicateRemover.add(word + " => " + SwappedWord);
				delete = true;
			}
			
			else {
				//otherwise swap the adjacent characters back
				temp = charSwapper[i];
				charSwapper[i] = charSwapper[i+1];
				charSwapper[i+1] = temp;
			}
		}
		
		if (delete == true){
			//if there has been a successful swap, print the contents of the linked list
			for (String string: DuplicateRemover){
				
				System.out.println(string);
				
			}
			
		}
		
		return;
	}

	public static void omission(HashTableMap M1, String word) {//deletes one character
		
		Boolean delete = false;
		
		int i;
		
		LinkedList<String> DuplicateRemover = new LinkedList<>();
		
		StringBuilder MisspeltWord = new StringBuilder(word);//create a string builder to modify the word

		for (i = 0; i < MisspeltWord.length(); i++) {
			
			char placeholder = MisspeltWord.charAt(i);//make a copy of the character to be deleted so we can place it back later
			
			MisspeltWord.deleteCharAt(i);//remove the character
			
			String wordOmission = MisspeltWord.toString();//turn the string builder back into a string for equality testing

			if (M1.find(wordOmission)  && !(DuplicateRemover.contains(word + " => " + wordOmission))) {//same test as before
				
				DuplicateRemover.add(word + " => " + wordOmission);
				delete = true;
				
			}
			
			MisspeltWord.insert(i, placeholder);//replace the deleted character so we can continue
			
		}
		
		if (delete == true){
			
			for (String string: DuplicateRemover){
				System.out.println(string);
				
			}
			
		}
		
		return;
	}

	public static void insertion(HashTableMap M1, String word) {//inserts one character
		
		Boolean delete = false;
		
		int i, j;
		
		LinkedList<String> DuplicateRemover = new LinkedList<>();
		
		char[] alphabet = new char[26];//load the alphabet into an array of characters

		for (char ch = 'a'; ch <= 'z'; ++ch) {
			
			alphabet[ch - 'a'] = ch;
			
		}

		StringBuilder MisspeltWord = new StringBuilder(word);

		for (i = 0; i < MisspeltWord.length() + 1; i++) {//for each character in the word
			
			for (j = 0; j < alphabet.length; j++) {//for each letter in the alphabet
				
				MisspeltWord.insert(i, alphabet[j]);//insert each letter of the alphabet into a position of the word
				
				String wordInsertion = MisspeltWord.toString();

				if (M1.find(wordInsertion)  && !(DuplicateRemover.contains(word + " => " + wordInsertion))) {
					
					DuplicateRemover.add(word + " => " + wordInsertion);
					
					delete = true;
					
				}
				
				MisspeltWord.deleteCharAt(i);//remove the inserted word so we can continue
			}
		}
		
		if (delete == true){
			
			for (String string: DuplicateRemover){
				
				System.out.println(string);
				
			}
			
		}
		
		return;

	}

	public static void substitution(HashTableMap M1, String word) {//replaces a character in the word with a character from the alphabet
		
		Boolean delete = false;
		
		int i, j;
		
		LinkedList<String> DuplicateRemover = new LinkedList<>();
		
		char charholder;//this will hold the character to be replaced to we can put it back later
		
		char[] alphabet = new char[26];

		for (char ch = 'a'; ch <= 'z'; ++ch) {
			
			alphabet[ch - 'a'] = ch;
			
		}

		StringBuilder MisspeltWord = new StringBuilder(word);

		for (i = 0; i < MisspeltWord.length(); i++) {//for each character in the word

			for (j = 0; j < alphabet.length; j++) {//for each letter in the alphabet

				charholder = MisspeltWord.charAt(i);

				MisspeltWord.setCharAt(i, alphabet[j]);//substitute a character in the word with each letter of the alphabet

				String wordSubstitution = MisspeltWord.toString();
				
				if (M1.find(wordSubstitution) && !(word.equals(wordSubstitution))  && !(DuplicateRemover.contains(word + " => " + wordSubstitution))) {
					
					DuplicateRemover.add(word + " => " + wordSubstitution);
					
					delete = true;
					
				}
				
				MisspeltWord.setCharAt(i, charholder);//replace the character with the original one to we can continue
				
			}

		}
		
		if (delete == true){
			
			for (String string: DuplicateRemover){
				
				System.out.println(string);
				
			}
		}
		
		return;
	}
}
