import java.util.NoSuchElementException;

public class Iteratorz implements java.util.Iterator{
	
	private String[] HashTable;
	private int j = 0;
	private int i = 0;
	
	public Iteratorz (String[] HashTable) {
		this.HashTable = HashTable;
	}
	
	public boolean hasNext() {
		return (j < HashTable.length);
	}
	

	
	public Object next() {
		
		while(HashTable[j].equals("-1")){
			j++;
		}
		
		return HashTable[j++];
	
		
		//throw new NoSuchElementException("No Elements in Array");
			
			
			
	}

}
