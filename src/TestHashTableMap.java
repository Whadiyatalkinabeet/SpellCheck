import java.util.Iterator;

/** Program testing hash table based map implementation */
public class TestHashTableMap {
	
	public static void main(String[] args) throws MapException {
		if (test1()) {
			System.out.println("   Test 1 succeeded");
		} else {
			System.out.println("***Test 1 failed");
		}
		if (test2()) {
			System.out.println("   Test 2 succeeded");
		} else {
			System.out.println("***Test 2 failed");
		}
		if (test3()) {
			System.out.println("   Test 3 succeeded");
		} else {
			System.out.println("***Test 3 failed");
		}
		if (test4()) {
			System.out.println("   Test 4 succeeded");
		} else {
			System.out.println("***Test 4 failed");
		}
		if (test5()) {
			System.out.println("   Test 5 succeeded");
		} else {
			System.out.println("***Test 5 failed");
		}
		if (test6()) {
			System.out.println("   Test 6 succeeded");
		} else {
			System.out.println("***Test 6 failed");
		}
		if (test7()) {
			System.out.println("   Test 7 succeeded");
		} else {
			System.out.println("***Test 7 failed");
		}
		if (test8()) {
			System.out.println("   Test 8 succeeded");
		} else {
			System.out.println("***Test 8 failed");
		}
		runDifferentLoadFactors();
	
	}
	
	/** Test 1: try empty constructor */
	public static boolean test1() {
		// Test 1: try empty constructor
		try {
			try
			{
				@SuppressWarnings("unused")
				HashTableMap h = new HashTableMap();
				return false;
			}
			catch (MapException e) {
				return true;
			}
		} catch (Exception e1) {
			return true;
		}
	}
	
	/** Test 2: add and find an entry */
	public static boolean test2() {
		try {
			StringHashCode sHC = new StringHashCode();
			float maxLF = (float) 0.5;
			HashTableMap h = new HashTableMap(sHC,maxLF);
			h.insert("R3C1");
			if (!h.find("R3C1")) {
				return false;
			} else {
				return true;
			}
		} catch (MapException e) {
			return false;
		}
	}

	/** Test 3: look for an inexistent key */
	private static boolean test3() {
		try {
			StringHashCode sHC = new StringHashCode();
			float maxLF = (float) 0.5;
			HashTableMap h = new HashTableMap(sHC,maxLF);
			h.insert("R3C1");
			if (h.find("R4C4")) {
				return false;
			} else {
				return true;
			}
		} catch (MapException e) {
			return false;
		}
	}

	/** Test 4: try to delete a nonexistent entry. Should throw an exception. */
	private static boolean test4() {
		StringHashCode sHC = new StringHashCode();
		float maxLF = (float) 0.5;
		HashTableMap h = new HashTableMap(sHC,maxLF);
		try {
			h.insert("R3C1");
		} catch (MapException e1) {
			return false;
		}
		try {
			h.remove("R6C8");
			return false;
		} catch (MapException e) {
			return true;		}
	}
	
	/** Test 5: delete an actual entry. Should not throw an exception. */
	private static boolean test5() {
		try {
			StringHashCode sHC = new StringHashCode();
			float maxLF = (float) 0.5;
			HashTableMap h = new HashTableMap(sHC,maxLF);
			h.insert("R1C1");
			h.insert("R3C1");
			h.remove( "R3C1");
			if ( h.numberOfElements() != 1) {
				return false;
			}
			if (!h.find("R3C1") ) {
				return true;
			} else {
				return false;
			}
		} catch (MapException e) {
			return false;
		}
	}
	
	/** Test 6: insert 200 different values into the Map and check that all these values are in the Map */
	private static boolean test6() {
		boolean result = true;
		try {
			StringHashCode sHC = new StringHashCode();
			float maxLF = (float) 0.5;
			HashTableMap h = new HashTableMap(sHC,maxLF);
			String s;
			for (int k = 0; k < 200; ++k) {
				s = (new Integer(k)).toString();
				h.insert("R"+s+"C"+s);
			}
			if ( h.numberOfElements() != 200) {
				result = false;
			}
			for (int k = 0; k < 200; ++k) {
				s = (new Integer(k)).toString();
				if (!h.find("R"+s+"C"+s) ) {
					result = false;
				}
			}
		} catch (MapException e) {
			return false;
		}
		return result;
	}
	
	/** Test 7: Delete a lot of entries from the  Map */
	private static boolean test7() {
		boolean result = true;
		try
		{
			String s;
			StringHashCode sHC = new StringHashCode();
			float maxLF = (float) 0.5;
			HashTableMap h = new HashTableMap(sHC,maxLF);
			for (int k = 0; k < 200; ++k) {
				s = (new Integer(k)).toString();
				h.insert("R"+s+"C"+s);
			}
			if ( h.numberOfElements() != 200) {
				result = false;
			}
			for ( int k = 0; k < 200; ++k )
			{
				s = (new Integer(k)).toString();
				h.remove("R"+s+"C"+s);
			}
			if ( h.numberOfElements() != 0) {
				result = false;
			}
			for (int k = 0; k < 200; ++k) {
				s = (new Integer(k)).toString();
				if (h.find("R"+s+"C"+s) ) {
					result = false; }	    
			}
		}
		catch (MapException e) 
		{
			result = false;
		}
		return result;
	}

	/** Test 8: Get iterator over all keys */
	private static boolean test8() {
		
		boolean result = true;
		String s;
		StringHashCode sHC = new StringHashCode();
		float maxLF = (float) 0.5;
		HashTableMap h = new HashTableMap(sHC,maxLF);
		try
		{
			for (int k = 0; k < 100; k++) 
			{
				s = (new Integer(k)).toString();	       
				h.insert("R"+s+"C"+s);
			}

			for (int k = 10; k < 30; k++) 
			{
				s = (new Integer(k)).toString();
				h.remove("R"+s+"C"+s);
			}
		}
		catch(MapException e)
		{
			result = false;
		}
		Iterator<String> it = h.elements();
		int count = 0;

		while (it.hasNext()){
			count++;
			it.next();
		}
		if ( h.numberOfElements() != 80) {
			result = false;
		}
		if ( count != 80) {
			System.out.println(count + " " + h.numberOfElements());
			result = false;
		}
		return result;
	}
	
	/** Runs the hash table at different load factors and print out the average probe numbers versus the running time.
	    The average probe number should go up as the max load factor goes up.
	 */
	private static void runDifferentLoadFactors() {

		StringHashCode sHC = new StringHashCode();
		float maxLF = (float) 0.5;
		HashTableMap h = new HashTableMap(sHC,maxLF);
		String s;
		long startTime,finishTime;
		double time;

		while (maxLF < 0.99 ){
			startTime = System.currentTimeMillis();
			h = new HashTableMap(sHC,maxLF);

			try{
				for (int k = 0; k < 10000; ++k) {
					s = (new Integer(k)).toString();
					h.insert(s+"C"+s);
				}
				for ( int k = 0; k < 10000; ++k ){
					s = (new Integer(k)).toString();
					h.remove(s+"C"+s);
				}
				finishTime = System.currentTimeMillis();
				time = finishTime - startTime;
				System.out.println(String.format("For load factor %9f, average num. of  probes is %9f time in milseconds is %9f",maxLF,h.averNumProbes(),time));
				maxLF = maxLF+ (float) 0.05;
			}
			catch (MapException e) {
				System.out.print("Failure");
			}
		}
	}


}




