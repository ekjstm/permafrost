/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * Thrown when an error occurs in the HDF virtual file system.
 *
 */
public class HIOException extends NativeRuntimeException {

    /** Serialization version id. */
    private static final long serialVersionUID = -4094571577231357374L;

    /**
     * Creates a new HIOException object.
     *
     * @param message The detailed error message.
     */
    public HIOException(String message) {
        super(message);
    }

    /**
     * Creates a new HIOException object.
     *
     * @param message The detailed error message.
     * @param cause The cause (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public HIOException(String message, Throwable cause) {
        super(message, cause);
    }

}
