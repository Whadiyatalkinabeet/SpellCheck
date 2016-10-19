import java.util.Iterator;


public class HashTableMap implements IMap, IHashTableMonitor{
	
	private IHashCode inputCode;
	private float maxLoadFactor;
	private float nElements = 0;
	private String[] HashTable = new String[7];
	private StringHashCode hCode = new StringHashCode();
	
	public HashTableMap() throws MapException{}

	public HashTableMap (IHashCode inputCode, float maxLoadFactor){
		this.inputCode = inputCode;
		this.maxLoadFactor = maxLoadFactor;
	} 
	
	private int doubleHash(String key){
		int doublehashCode = 0;
		
		doublehashCode = 5 + (hCode.giveCode(key));
		
		return doublehashCode;
	}
	
	public void insert(String key) {
		
		//System.out.println((maxLoadFactor * HashTable.length) + " " +  nElements + " filled of " + HashTable.length + " spaces");
		
		if ((nElements) < (maxLoadFactor * HashTable.length)) {
			
		
			
				if (HashTable[hCode.giveCode(key) % HashTable.length]  == null){
					HashTable[hCode.giveCode(key) % HashTable.length] = key;
					System.out.println(key + "  added using single hash");
					nElements += 1f;
					
					return;
				}
				else  {
					for (int i = 1; i < HashTable.length; i++){
						if (HashTable[(doubleHash(key) * i) % HashTable.length] == null) {
							HashTable[(doubleHash(key) * i) % HashTable.length] = key;
							System.out.println(key + "  added using double * " + i + " hash");
							nElements += 1f;
							return;
						}
					}
				}
		}
		
			System.out.println("max load factor reached");
			for (int i = 0; i < HashTable.length; i++){
				System.out.println(i+1 + " " + HashTable[i]);
			}
			
			
			//reHash();
			
		
	}
	
	public void print(){
		int i;
		
		for (i = 0; i < HashTable.length; i++){
			System.out.println(HashTable[i] + " " + i);
		}
		
		for (i = 0; i < 4; i++){
			System.out.println(HashTable[i] + " " + i);
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
		if (HashTable[hCode.giveCode(key) % HashTable.length].equals(key)){
			return true;
		}
		else {
			for (int i = 1; i < HashTable.length; i++){
				if (HashTable[(doubleHash(key) * i) % HashTable.length].equals(key)) {
					return true;
				}
			}
		}
		
		return false;
		
	}


	
	public int numberOfElements() {
	
	int counter = 0;

		return counter;
	}

	
	public Iterator<String> elements() {
		
		return null;
	}

	
	public float getMaxLoadFactor() {
		
		return maxLoadFactor;
	}

	@Override
	public float getLoadFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float averNumProbes() {
		// TODO Auto-generated method stub
		return 0;
	}

}
