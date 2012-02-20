/**
 * 
 */
package permafrost.hdf.libhdf;

/**
 * Base class for runtime exceptions thrown by the libjhdf native code.
 */
public class NativeRuntimeException extends RuntimeException {

	/** Serialization version id. */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Creates a new NativeRuntimeException object.
	 * 
	 * @param message The detailed error message.
	 */
	public NativeRuntimeException(String message) {
		super(message);					
	}

	/**
	 * Creates a new NativeRuntimeException object.
	 * 
	 * @param message The detailed error message.
	 * @param cause The cause (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public NativeRuntimeException(String message, Throwable cause) {
		super(message, cause);			
	}

}
