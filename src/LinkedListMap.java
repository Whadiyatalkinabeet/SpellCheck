import java.util.Iterator;
import java.util.LinkedList;


public class LinkedListMap implements IMap {
	
	private LinkedList<String> M = new LinkedList<>();
	
	
	public LinkedListMap(){}
	

	public void insert(String key){
		if (!(find(key))){
			M.add(key);
		}
		else{
			return;
		}
	}
	
	public void remove(String key) throws MapException {
		M.remove(key);
	}

	
	public boolean find(String key) {
		return M.contains(key);
	}

	public int numberOfElements() {
		return M.size();
	}

	public Iterator<String> elements() {
		return M.iterator();
	}


	

	
	
	

}
