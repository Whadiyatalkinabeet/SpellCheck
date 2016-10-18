import java.util.Iterator;
import java.util.LinkedList;

public class HashTableMap implements IMap{
	
	private IHashCode inputCode;
	private float maxLoadFactor;
	private float LoadFactor;
	
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
		
		for(i = 0; i < c.length; i++){
			doublehashCode += c[i]*(7^i);
		}
		
		doublehashCode = 5 - (doublehashCode % 5);
		
		return doublehashCode;
	}
	
	public void insert(String key) {
		Boolean flag = false;
		
		while (flag = false){
			if (HashTable[hCode.giveCode(key)].isEmpty()){
				HashTable[hCode.giveCode(key)] = key;
				flag = true;
			}
			else if (HashTable[doubleHash(key)].isEmpty()){
				HashTable[doubleHash(key)] = key;
				flag = true;
			}
		}
	}

	
	public void remove(String key) throws MapException {
		
	}

	
	public boolean find(String key) {
		
		return false;
	}

	
	public int numberOfElements() {
		
		return 0;
	}

	
	public Iterator<String> elements() {
		
		return null;
	}

}
