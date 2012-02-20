/**
 * 
 */
package permafrost.hdf.basic;

/**
 *
 */
public class NoSuchObjectException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final private int oid;
	
	/**
	 * 
	 */
	public NoSuchObjectException(int oid) {
		this(Integer.toString(oid), oid);		
	}

	/**
	 * @param message
	 */
	public NoSuchObjectException(String message, int oid) {
		super(message);		
		this.oid = oid;
	}

	public int getObjectId() {
		return (this.oid);
	}
	

}
