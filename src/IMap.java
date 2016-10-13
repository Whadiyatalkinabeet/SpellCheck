

import java.util.Iterator;

/** Interface your map has to implement */
public interface IMap {

	/** Inserts an entry to the map. Throws exception if key is
	 *	already present */
	public void insert(String key);

	/** Removes the entry with the specified key from the map. Throws
	 *	MapException if no entry with key in the map */
	public void remove(String key)throws MapException;

	/** Returns true if there is entry with specified key in the
	 * map */
	public boolean find(String key);

	/** Returns the number of elements stored in the map */
	public int numberOfElements();
	
	/** Returns iterator object over all elements stored in the
	 * map */
	public Iterator<String> elements();

}

