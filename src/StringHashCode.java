
public class StringHashCode implements IHashCode{

	
	public int giveCode(Object o) {
		int hashCode = 0;
		String sb = new String(o.toString());
		
		char[] c = sb.toCharArray();
		
		int i;
		
		for(i = 0; i < c.length; i++){
			hashCode += c[i]*(33^i);
		}
		
		hashCode %= 7;
		return hashCode;
	}

	

}
