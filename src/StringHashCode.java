
public class StringHashCode implements IHashCode{

	
	public int giveCode(Object o) {
		
		int hashCode = 0;
		
		int i;
		
		String sb = new String(o.toString());
		
		char[] c = sb.toCharArray();
		
		for(i = 0; i < c.length; i++){
			
			hashCode += c[i]*(33^i);
			
		}
		
		return hashCode;
		
	}

}
