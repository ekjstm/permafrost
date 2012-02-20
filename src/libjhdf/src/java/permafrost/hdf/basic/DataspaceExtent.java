/**
 *
 */
package permafrost.hdf.basic;

/**
 * Represents the extent of a dataspace.
 *
 */
public class DataspaceExtent {
        
    /** The current extent of the dataspace. */
    public final long[] extents;
    
    /** The maximum extent of the dataspace. */
    public final long[] maxExtents;
    
    /**
     * Creates a new DataspaceExtent object.
     *
     * @param extents The first vector component.
     * @param maxExtents The second vector component.
     */
    public DataspaceExtent(long[] extents, long[] maxExtents) {
        super();
        this.extents = extents;
        this.maxExtents = maxExtents;
    }
    
    /**
     * Calculates the number of elements 
     * in the current extent of the dataspace.
     * 
     * @return The number of elements in the current extent of the dataspace.
     */
    public long numElements() {
        long num = 1;
        for (long extent : extents) {
            num *= extent;
        }
        return (num);
    }
    
    /**
     * Gets the number of dimensions in the 
     * current extent of the dataspace.
     * 
     * @return The number of dimensions in the current extent of the dataspace.
     */
    public int rank() {
        return (this.extents.length);
    }
    
    /**
     * Gets the current extent of the dataspace.
     * 
     * @return The current extent of the dataspace.
     */
    public long[] getExtents() {
        return (this.extents);
    }
    
    /**
     * Gets the maximum extent of the dataspace.
     * 
     * @return The maximum extent of the dataspace.
     */
    public long[] getMaxExtents() {
        return (this.extents);
    }
    
}
