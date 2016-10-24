import java.util.ArrayList;
import java.util.Iterator;



public class HashTableMap implements IMap, IHashTableMonitor{
	
	//-----------------------------------------------//
	private IHashCode inputCode;
	private float maxLoadFactor;
	private int nElements = 0;
	private String[] HashTable = new String[7];
	private StringHashCode hCode = new StringHashCode();
	private double numberOfProbes = 0;
	private int reHashCounter = 0;
	private int primeCode = 5;
	private double numberOfOperations = 0;
	
	
	//-----------------------------------------------//
	
	public HashTableMap() throws MapException{
		throw new MapException("Invalid Parameters");
	}
	

	public HashTableMap (IHashCode inputCode, float maxLoadFactor) {
		
		this.inputCode = inputCode;
		this.maxLoadFactor = maxLoadFactor;
	} 
	
	private int doubleHash(String key){
		int doublehashCode = 0;
		
		doublehashCode = primeCode - (hCode.giveCode(key) % primeCode) ;
		
		return doublehashCode;
	}
	
	public void insert(String key) throws MapException {
		
		//System.out.println((maxLoadFactor * HashTable.length) + " " +  nElements + " filled of " + HashTable.length + " spaces");
		
		if (getLoadFactor() < getMaxLoadFactor()) {
				//System.out.println(key);
				if (HashTable[hCode.giveCode(key) % HashTable.length]  == null || HashTable[hCode.giveCode(key) % HashTable.length].equals("-1")){
					HashTable[hCode.giveCode(key) % HashTable.length] = key;
					//System.out.println(key + "  added using single hash");
					nElements += 1;
					numberOfOperations  += 1;
					
					return;
				}
				else  {
					for (int i = 1; i < HashTable.length; i++){
						
						if ( HashTable[((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length] == null || HashTable[((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length].equals("-1")) {
							HashTable[((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length] = key;
						    //System.out.println(key + "  added using double * " + i + " hash");
							nElements += 1;
							numberOfOperations  += 1;
							return;
						}
						
						else {
							numberOfProbes += 1;
						}
						
					
					}
					
					
				}
				
		}else{
			String holder = key;
			reHashCounter += 1;
			nElements = 0;
			//System.out.println("REHASH");
			//print();
			reHash();
			//print();
			insert(key);
			return;
		}
	}
	
	public int reHashCounter(){
		return reHashCounter;
	}
	
	
	
	private void reHash() throws MapException{
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
				if (HashTable[hCode.giveCode(HashTableClone[i]) % HashTable.length]  == null || HashTable[hCode.giveCode(HashTableClone[i]) % HashTable.length].equals("-1")){
					HashTable[hCode.giveCode(HashTableClone[i]) % HashTable.length] = HashTableClone[i];
					//System.out.println(HashTableClone[i] + "  added using single hash REHASH");
					nElements += 1;
					numberOfOperations  += 1;
					
					
				}
				else  {
					for (int i1 = 1; i1 < HashTableClone.length; i1++){
						
						if ( HashTable[((hCode.giveCode(HashTableClone[i]) % HashTable.length) + (doubleHash(HashTableClone[i]) * i1)) % HashTable.length] == null || HashTable[((hCode.giveCode(HashTableClone[i]) % HashTable.length) + (doubleHash(HashTableClone[i]) * i1)) % HashTable.length].equals("-1")) {
							HashTable[((hCode.giveCode(HashTableClone[i]) % HashTable.length) + (doubleHash(HashTableClone[i]) * i1)) % HashTable.length] = HashTableClone[i];
						    //System.out.println(HashTableClone[i] + "  added using double * " + i1 + " hash REHASH");
							nElements += 1;
							numberOfOperations  += 1;
							break;
						}
						
						else {
							numberOfProbes += 1;
						}
						
					
					}
					
					
				}
			}
		}
		return;
		
		
		
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

	
	public void remove(String key) throws MapException  {
	if (HashTable[hCode.giveCode(key) % HashTable.length] != null && HashTable[hCode.giveCode(key) % HashTable.length].equals(key)){
		HashTable[hCode.giveCode(key) % HashTable.length] = "-1";
		//System.out.println("removed " + key);
		numberOfOperations  += 1;
		nElements -= 1;
	}
	else {
		for (int i = 1; i < HashTable.length; i++){
			//System.out.println(HashTable[(doubleHash(key) * i) % HashTable.length] + " " + i + " " + (doubleHash(key) * i) % HashTable.length);
			
			if ((HashTable[((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length] != null && HashTable[((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length].equals(key))) {
				HashTable[((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length] = "-1";
				//System.out.println("removed " + key);
				numberOfOperations  += 1;
				nElements -= 1;
				return;
			}
			
		}
		
		throw new MapException(key + " does not exist");
		
	}

	
	}

	
	public boolean find(String key) {
		
		
		if (HashTable[hCode.giveCode(key) % HashTable.length] != null && HashTable[hCode.giveCode(key) % HashTable.length].equals(key)){
			numberOfOperations  += 1;
			return true;
		}
		else {
			for (int i = 1; i < HashTable.length; i++){
				//System.out.println(HashTable[(doubleHash(key) * i) % HashTable.length] + " " + (doubleHash(key) * i) % HashTable.length + " " + key);
				
				if (HashTable[((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length] != null && HashTable[((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length].equals(key)) {
					numberOfOperations  += 1;
					return true;
				}
				else if (i == HashTable.length){
					return false;
				}
			}
			return false;
		}
		
		
	}

	public void print(){
		System.out.println("nElements - " + nElements + " Length - " + HashTable.length);
		
	}

	
	public int numberOfElements() {
		return (int)nElements;
	}

	
	

	
	public float getMaxLoadFactor() {
		return maxLoadFactor;
	}

	
	public float getLoadFactor() {
		
		return ((float) nElements / (float) HashTable.length);
	}

	
	public float averNumProbes() {
		
	return ((float) numberOfProbes / (float) numberOfOperations);
	}
	
	public Iterator<String> elements() {
		ArrayList<String> arrayList = new ArrayList<String>();
		
		
		for (int i = 0; i < HashTable.length; i++){
			if (!(HashTable[i] == null || HashTable[i].equals("-1"))){
				arrayList.add(HashTable[i]);
			}
		}
		
		
		
		
		return arrayList.iterator();
	}


}
