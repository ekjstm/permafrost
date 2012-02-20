/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * Thrown when HDF data does not match the expected format.
 *
 */
public class DataFormatException extends NativeRuntimeException {

    /** */
    private static final long serialVersionUID = 3537554545806729233L;

    /**
     * Creates a new DataFormatException object.
     *
     * @param message The detailed error message.
     */
    public DataFormatException(String message) {
        super(message);
    }

    /**
     * Creates a new DataFormatException object.
     *
     * @param message The detailed error message.
     * @param cause The cause (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

}
