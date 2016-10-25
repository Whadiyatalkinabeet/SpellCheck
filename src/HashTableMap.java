import java.util.ArrayList;
import java.util.Iterator;



public class HashTableMap implements IMap, IHashTableMonitor{
	
	
	private IHashCode inputCode;
	private float maxLoadFactor;
	private int nElements = 0;
	private String[] HashTable = new String[7];
	private StringHashCode hCode = new StringHashCode();
	private double numberOfProbes = 0;
	private int primeCode = 5;
	private double numberOfOperations = 0;
	
	

	public HashTableMap() throws MapException{
		
		throw new MapException("Invalid Parameters");
		
	}
	

	public HashTableMap (IHashCode inputCode, float maxLoadFactor) {
		
		this.inputCode = inputCode;
		this.maxLoadFactor = maxLoadFactor;
		
	} 
	
	private int doubleHash(String key){// Gets the secondary hash code
		
		int doublehashCode = 0;
		
		doublehashCode = primeCode - (hCode.giveCode(key) % primeCode) ;
		
		return doublehashCode;
	}
	
	public void insert(String key) throws MapException {//	inserts a key into the hashtable
		
		
		
		if (getLoadFactor() < getMaxLoadFactor()) {//	checks to see if rehashing is required
			//PRIMARY HASH CODE
			int firstCode = hCode.giveCode(key) % HashTable.length;//	Sets the primary hash code to a variable to recalculation is not required
			
				if (HashTable[firstCode]  == null || HashTable[firstCode].equals("-1")){//	checks to see if the space is empty or contains a dummy value
				
					HashTable[firstCode] = key; //inserts key into space
					
					nElements += 1;
				
					numberOfOperations  += 1;
					
					return;
				}
			
				else  {
					//SECONDARY HASH CODE
					for (int i = 1; i < HashTable.length; i++){
						
						int secondCode = ((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length; // try the secondary hash code multiplied by i
						
						if ( HashTable[secondCode] == null || HashTable[secondCode].equals("-1")) {//	checks for a free space
							
							HashTable[secondCode] = key;	//inserts key
						    
							nElements += 1;
							
							numberOfOperations  += 1;
							
							return;
							
						}
						
						else {
							numberOfProbes += 1;
						}
					}
				}
				
		}
		
		else{
			//REHASH
			nElements = 0;
			
			reHash();
			
			insert(key);//	inserts the key that was read before rehashing
			
			return;
		}
	}
	
	
	
	
	
	private void reHash() throws MapException{// called when load factor > max load factor
		
		int newArraySize = 2 * HashTable.length; //store a number twice the size of the array
		
		int i;
		
		while (!(isPrime(primeCode))){//gets the new prime number for the secondary hash function
			
			primeCode += 1;
			
		}
		
		while (!(isPrime(newArraySize))){//gets the new prime number closest to twice the original array size
			
			newArraySize += 1;
			
		}
		
		
		String[] HashTableClone = HashTable.clone(); // make a clone of the original array
		
		HashTable  = new String[newArraySize];
		
		for (i = 0; i < HashTableClone.length; i++){
			
			if (HashTableClone[i] != null){//checks to see if there is a word
				
				int reHashFirstCode = hCode.giveCode(HashTableClone[i]) % HashTable.length; //calculate a primary hash code
				
				if (HashTable[reHashFirstCode]  == null || HashTable[reHashFirstCode].equals("-1")){// check for a free space or dummy value
					
					HashTable[reHashFirstCode] = HashTableClone[i];// insert word into new hash table
					
					nElements += 1;
					
					numberOfOperations  += 1;
					
					
				}
				
				else  {
					
					for (int i1 = 1; i1 < HashTableClone.length; i1++){// does the same as above but with the secondary hash function
						
						int reHashSecondCode = ((hCode.giveCode(HashTableClone[i]) % HashTable.length) + (doubleHash(HashTableClone[i]) * i1)) % HashTable.length; 
						
						if ( HashTable[reHashSecondCode] == null || HashTable[reHashSecondCode].equals("-1")) {
							
							HashTable[reHashSecondCode] = HashTableClone[i];
						    
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
	
	
	
	private Boolean isPrime(int newArraySize){// reference - http://www.mkyong.com/java/how-to-determine-a-prime-number-in-java/
		
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

	
	public void remove(String key) throws MapException  {// removes a key from the array otherwise throws an exception
		
		int firstRemoveCode = hCode.giveCode(key) % HashTable.length;
		
		if (HashTable[firstRemoveCode] != null && HashTable[firstRemoveCode].equals(key)){//tries to find key using primary hash function
			
			HashTable[firstRemoveCode] = "-1";
		
			numberOfOperations  += 1;
			
			nElements -= 1;
	}
		
		else {
		
			for (int i = 1; i < HashTable.length; i++){
			
				int secondRemoveCode = ((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length;//tries to find key using secondary hash function
			
				if ((HashTable[secondRemoveCode] != null && HashTable[secondRemoveCode].equals(key))) {
				
					HashTable[secondRemoveCode] = "-1";
				
					numberOfOperations  += 1;
				
					nElements -= 1;
				
					return;
				}
			}
		
			throw new MapException(key + " does not exist");
		
		}
	}

	
	public boolean find(String key) {//returns true if key exists in array
		
		int firstFindCode = hCode.giveCode(key) % HashTable.length;// tries to find using primary hash function
		
		if (HashTable[firstFindCode] != null && HashTable[firstFindCode].equals(key)){
			
			numberOfOperations  += 1;
			
			return true;
		}
		
		else {
			for (int i = 1; i < HashTable.length; i++){
				
				int secondFindCode = ((hCode.giveCode(key) % HashTable.length) + (doubleHash(key) * i)) % HashTable.length;//tries to find using secondary hash function
				
				if (HashTable[secondFindCode] != null && HashTable[secondFindCode].equals(key)) {
					
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

	

	public int numberOfElements() {//returns number of occupied spaces in array
		
		return (int)nElements;
		
	}

	public float getMaxLoadFactor() {//returns maximum load factor
		
		return maxLoadFactor;
		
	}

	public float getLoadFactor() {//returns the load factor
		
		return ((float) nElements / (float) HashTable.length);
		
	}

	public float averNumProbes() {//returns the average number of probes
		
		return ((float) numberOfProbes / (float) numberOfOperations);
		
	}
	
	public Iterator<String> elements() {//returns an iterator
		//convert the array into an array list to retrieve the iterator
		ArrayList<String> arrayList = new ArrayList<String>();
		
		for (int i = 0; i < HashTable.length; i++){
			if (!(HashTable[i] == null || HashTable[i].equals("-1"))){
				arrayList.add(HashTable[i]);
			}
		}
		
		return arrayList.iterator();
	}

}
