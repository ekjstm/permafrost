/**
 * 
 */
package permafrost.hdf.basic;

/**
 *
 */
public class NoSuchMemberException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int oid = -1;
	private String membername = "";
	
	/**
	 * 
	 */
	public NoSuchMemberException(int oid, String membername) {
		this(oid, membername, membername);
		
	}

	/**
	 * @param message
	 */
	public NoSuchMemberException(int oid, String membername, String message) {
		super(message);		
		this.oid = oid;
		this.membername = membername;
	}

	/**
	 * @return Returns the membername.
	 */
	public final String getMembername() {
		return membername;
	}

	/**
	 * @return Returns the oid.
	 */
	public final int getOid() {
		return oid;
	}	

}
