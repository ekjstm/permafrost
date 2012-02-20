/**
 * 
 */
package permafrost.hdf.libhdf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


/**
 * Static methods used to pass strings to and from native code.
 *
 */
public class StringUtils {

	/**
	 * Not used.
	 */
	private StringUtils() {
		super();
	}

	/**
	 * Creates a String object from a zero-terminated byte array.
	 *  
	 * @param nullTerm zero-terminated byte array.
	 * 
	 * @return new String object based on the given byte array.
	 */
	public static String fromNullTerm(byte[] nullTerm) {
		for (int n=nullTerm.length-1; n>=0; n--) {
			if (nullTerm[n] != 0) {
				return (new String(nullTerm, 0, n+1));
			}
		}
		return "";
	}
	
	public static String fromNullTerm(ByteBuffer nullTerm) {
	    nullTerm.rewind();
	    for (int n=nullTerm.capacity()-1; n>=0; n--) {
            if (nullTerm.get(n) != 0) {
                byte[] bytes = new byte[n+1];
                nullTerm.get(bytes, 0, n+1);
                return (new String(bytes));
            }
        }
        return "";
	}
	
	public static byte[] toNullTerm(String str) {
	    return (toNullTerm(str, Charset.defaultCharset()));
	}
	
	public static byte[] toNullTerm(String str, Charset cs) {
	    byte[] data = str.getBytes(cs);
	    byte[] nullTerm = new byte[data.length + 1];
	    System.arraycopy(data, 0, nullTerm, 0, data.length);
	    return (nullTerm);
	}
	
	
}
