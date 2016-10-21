import java.util.Iterator;


public class HashTableMap implements IMap, IHashTableMonitor{
	
	private IHashCode inputCode;
	private float maxLoadFactor;
	private float nElements = 0;
	private String[] HashTable = new String[7];
	private StringHashCode hCode = new StringHashCode();
	private int probes = 0;
	private int reHashCounter = 0;
	private int primeCode = 5;
	
	public HashTableMap() throws MapException{}

	public HashTableMap (IHashCode inputCode, float maxLoadFactor){
		this.inputCode = inputCode;
		this.maxLoadFactor = maxLoadFactor;
	} 
	
	private int doubleHash(String key){
		int doublehashCode = 0;
		
		doublehashCode = primeCode + (hCode.giveCode(key) % HashTable.length);
		
		return doublehashCode;
	}
	
	public void insert(String key) {
		
		//System.out.println((maxLoadFactor * HashTable.length) + " " +  nElements + " filled of " + HashTable.length + " spaces");
		
		if (getLoadFactor() < getMaxLoadFactor()) {
			
		
			
				if (HashTable[hCode.giveCode(key) % HashTable.length]  == null || HashTable[hCode.giveCode(key) % HashTable.length].equals("-1")){
					HashTable[hCode.giveCode(key) % HashTable.length] = key;
					//System.out.println(key + "  added using single hash");
					nElements += 1f;
					
					return;
				}
				else  {
					for (int i = 1; i < HashTable.length; i++){
						if (HashTable[(doubleHash(key) * i) % HashTable.length] == null || HashTable[(doubleHash(key) * i) % HashTable.length].equals("-1")) {
							HashTable[(doubleHash(key) * i) % HashTable.length] = key;
							//System.out.println(key + "  added using double * " + i + " hash");
							nElements += 1f;
							return;
						}
						else{
							probes += 1;
						}
					}
				}
				
		}
		
			
			reHashCounter += 1;
			nElements = 0;
			reHash();
			
		
	}
	
	public int reHashCounter(){
		return reHashCounter;
	}
	
	public void print(){
		int i;
		
		/*for (i = 0; i < HashTable.length; i++){
			System.out.println(HashTable[i] + " " + i);
		}*/
		
		System.out.println(nElements);
		System.out.println("183719.0");
		System.out.println(getLoadFactor());
		System.out.println(reHashCounter);
		
		
	}
	
	private void reHash(){
		int newArraySize = 2 * HashTable.length;
		int i;
		
		while (!(isPrime(primeCode))){
			primeCode += 1;
		}
		
		while (!(isPrime(newArraySize))){
			newArraySize += 1;
		}
		//System.out.println(newArraySize);
		
		String[] HashTableClone = HashTable.clone();
		HashTable  = new String[newArraySize];
		
		for (i = 0; i < HashTableClone.length; i++){
			if (HashTableClone[i] != null){
			insert(HashTableClone[i]);
			//nElements += 1;
			}
		}
		
		
		
	}
	
	
	
	private Boolean isPrime(int newArraySize){// write reference 
		
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
		if (HashTable[hCode.giveCode(key) % HashTable.length] != null && HashTable[hCode.giveCode(key) % HashTable.length].equals(key)){
			HashTable[hCode.giveCode(key) % HashTable.length] = "-1";
			System.out.println("removed " + key);
		}
		else {
			for (int i = 1; i < HashTable.length; i++){
				//System.out.println(HashTable[(doubleHash(key) * i) % HashTable.length] + " " + i + " " + (doubleHash(key) * i) % HashTable.length);
				
				if (HashTable[(doubleHash(key) * i) % HashTable.length] != null && HashTable[(doubleHash(key) * i) % HashTable.length].equals(key)) {
					HashTable[(doubleHash(key) * i) % HashTable.length] = "-1";
					System.out.println("removed " + key);
				}
				else {
					System.out.println(key + " does not exist");
					return;
					
				}
				
				
			}
			return;
		}
	}

	
	public boolean find(String key) {
		//System.out.println(HashTable[hCode.giveCode(key) % HashTable.length] );
		
		if (HashTable[hCode.giveCode(key) % HashTable.length] != null && HashTable[hCode.giveCode(key) % HashTable.length].equals(key)){
			return true;
		}
		else {
			for (int i = 1; i < HashTable.length; i++){
				//System.out.println(HashTable[(doubleHash(key) * i) % HashTable.length] + " " + i + " " + (doubleHash(key) * i) % HashTable.length);
				
				if (HashTable[(doubleHash(key) * i) % HashTable.length] != null && HashTable[(doubleHash(key) * i) % HashTable.length].equals(key)) {
					return true;
				}
				else {
					return false;
				}
				
				
			}
			return false;
		}
		
		
	}


	
	public int numberOfElements() {
		return (int)nElements;
	}

	
	public Iterator<String> elements() {
		
		return null;
	}

	
	public float getMaxLoadFactor() {
		return maxLoadFactor;
	}

	
	public float getLoadFactor() {
		return (nElements / HashTable.length);
	}

	@Override
	public float averNumProbes() {
		// TODO Auto-generated method stub
		return probes;
	}

}
