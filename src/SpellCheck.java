import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;



/** Main class for the Spell-Checker program */
public class SpellCheck {
	
	

	public static void main(String[] args) throws java.io.IOException{
		if (args.length != 2 ) {
			System.out.println("Usage: SpellCheck dictionaryFile.txt inputFile.txt ");
			System.exit(0);
		}
		
		LinkedListMap M1 = new LinkedListMap();
		LinkedListMap words = new LinkedListMap();
		
		

		try{
			
			BufferedInputStream dict,file;
			
			dict  = new BufferedInputStream(new FileInputStream("banana.txt"));
			FileWordRead readDict = new FileWordRead(dict);

			file  = new BufferedInputStream(new FileInputStream("checkTest.txt"));
			FileWordRead readFile = new FileWordRead(file);
			
			
			while (readDict.hasNextWord()){
				
				M1.insert(readDict.nextWord());
				
			}
			
			while (readFile.hasNextWord()){
				
				words.insert(readFile.nextWord());
				
			}
			
			
			dict.close();
			file.close();
			
			}
		catch (IOException e){ // catch exceptions caused by file input/output errors
			System.out.println("Check your file name");
			System.exit(0);
		}
		
		
		
		trythis(M1, words);
		
	}


	public static void trythis(LinkedListMap M1, LinkedListMap words) {
		
		
		
		while(words.numberOfElements() != 0){
			if(reversal(M1, words)){
				words.removeFirst();
			}
			
			if (substitution(M1, words)){
				words.removeFirst();
			}
			
			if(omission(M1, words)){
				words.removeFirst();
			}
			
			if (insertion(M1, words)){
				words.removeFirst();
			}
			
		
		}
	}
		
	
	
	public static Boolean reversal(LinkedListMap M1, LinkedListMap words){
		Boolean delete = false;
		
		
		StringBuilder MisspeltWord = new StringBuilder(words.peekFirst());
		
		MisspeltWord.reverse();
		
		String word = MisspeltWord.toString();
			
			if (M1.find(word)){
				System.out.println(words.peekFirst() + "==>" + word + "		reversal");
				delete = true;
				
			}
			
		return delete;	
			
		
	
	}
	
	public static Boolean omission(LinkedListMap M1, LinkedListMap words){
		Boolean delete = false;
		int i;
		
		StringBuilder MisspeltWord = new StringBuilder(words.peekFirst());
		
		for (i = 0; i < MisspeltWord.length(); i++){
			char placeholder = MisspeltWord.charAt(i);
			MisspeltWord.deleteCharAt(i);
			String word = MisspeltWord.toString();
			
			if (M1.find(word)){
				System.out.println(words.peekFirst() + "==>" + word + "		omission");
				delete = true;
			}
			MisspeltWord.insert(i, placeholder);
		}
		
		return delete;
	}
	
	public static Boolean insertion(LinkedListMap M1, LinkedListMap words){
		Boolean delete = false;
		int i, j;
		
		char[] alphabet = new char[26];
		
		for(char ch = 'a'; ch <= 'z'; ++ch){
			alphabet[ch-'a']=ch;
		} 
		
		StringBuilder MisspeltWord = new StringBuilder(words.peekFirst());
		
		for (i = 0; i < MisspeltWord.length() + 1; i++){
			for (j = 0; j < alphabet.length; j++){
				MisspeltWord.insert(i, alphabet[j]);
				String word = MisspeltWord.toString();
				
				if(M1.find(word)){
					System.out.println(words.peekFirst() + "==>" + word + "		insertion");
					delete = true;
				}
				MisspeltWord.deleteCharAt(i);
			}
		}
		
		return delete;
		
	}
	
	public static Boolean substitution(LinkedListMap M1, LinkedListMap words){
		Boolean delete = false;
		
		int i, j;
		char charholder;
		char[] alphabet = new char[26];
		
		for(char ch = 'a'; ch <= 'z'; ++ch){
			alphabet[ch-'a']=ch;
		} 
		
		StringBuilder MisspeltWord = new StringBuilder(words.peekFirst());
		
		
		for (i = 0; i < MisspeltWord.length(); i++){
			
			for (j = 0; j < alphabet.length; j++){
				
				charholder = MisspeltWord.charAt(i);
				
				
					MisspeltWord.setCharAt(i,alphabet[j]);
					
				
				
				String word = MisspeltWord.toString();
				if(M1.find(word) && !(words.peekFirst().equals(word))){
						System.out.println(words.peekFirst() + "==>" + word + "		substitution");
						delete = true;
						MisspeltWord.setCharAt(i, charholder);
				}	
			}
			
		}
		
		return delete;
	}
}
		
		

	
	
	