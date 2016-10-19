import java.util.Iterator;
import java.math.*;

public class HashTableMap implements IMap{
	
	private IHashCode inputCode;
	private float maxLoadFactor;
	
	
	private String[] HashTable = new String[7];
	private StringHashCode hCode = new StringHashCode();
	
	public HashTableMap() throws MapException{}

	public HashTableMap (IHashCode inputCode, float maxLoadFactor){
		this.inputCode = inputCode;
		this.maxLoadFactor = maxLoadFactor;
	} 
	
	private int doubleHash(String key){
		int doublehashCode = 0;
		char[] c = key.toCharArray();
		int i;
		
		doublehashCode = 5 + (hCode.giveCode(key));
		
		return doublehashCode;
	}
	
	public void insert(String key) {
		
		
		if (getLoadFactor() < maxLoadFactor) {
		//if ( ((numberOfElements())/ HashTable.length) < maxLoadFactor){
			
				if (HashTable[hCode.giveCode(key) % HashTable.length]  == null){
					HashTable[hCode.giveCode(key) % HashTable.length] = key;
					System.out.println(key + "  added using single hash");
					return;
				}
				else if (HashTable[doubleHash(key) % 5] == null) {
					
					HashTable[doubleHash(key) % 5] = key;
					System.out.println(key + "  added using double hash");
					return;
				}
				else  {
					for (int i = 1; i < HashTable.length; i++){
						if (HashTable[(doubleHash(key) * i) % 5] == null) {
							HashTable[(doubleHash(key) * i) % 5] = key;
							System.out.println(key + "  added using double * " + i + " hash");
							return;
						}
					}
				}
		}
		else {
			System.out.println("max load factor reached");
			for (int i = 0; i < HashTable.length; i++){
				System.out.println(i+1 + " " + HashTable[i]);
			}
			System.exit(0);
		}
}
	
	private void reHash(){
		int newArraySize = 2 * HashTable.length;
		
		while (!(isPrime(newArraySize))){
			newArraySize += 1;
		}
		System.out.println(newArraySize);
		HashTable = new String[newArraySize];
		
	}
	
	private Boolean isPrime(int newArraySize){
		
		if (newArraySize % 2 == 0){
			return false;
		}
		for(int i = 3; i*i < newArraySize; i += 2){
			if (newArraySize % i == 0){
				return false;
			}
		}
		return true;
	}


	
	public void remove(String key) throws MapException {
		
	}

	
	public boolean find(String key) {
		
		return false;
	}

	private float getLoadFactor(){
		float LoadFactor;
		
		LoadFactor =  ((numberOfElements())/ HashTable.length);
		
		return LoadFactor;
		
	}
	
	public int numberOfElements() {
	int i;
	int counter = 0;
	for (i = 0; i < HashTable.length; i++){
		if (HashTable[i] != null){
			counter += 1;
		}
	}
		return counter;
	}

	
	public Iterator<String> elements() {
		
		return null;
	}

}
