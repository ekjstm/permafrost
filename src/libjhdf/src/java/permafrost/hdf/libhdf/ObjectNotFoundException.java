/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * Thrown when an attempt to find an object with a specific name fails because
 * the object does not exist.
 *
 */
public class ObjectNotFoundException extends HIOException {

    /** Serialization version id. */
    private static final long serialVersionUID = -1654561902124362996L;

    /**
     * Creates a new ObjectNotFoundException object.
     *
     * @param message
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new ObjectNotFoundException object.
     *
     * @param message
     * @param cause
     */
    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
