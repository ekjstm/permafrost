/**
 *
 */
package permafrost.hdf.basic;

import permafrost.hdf.libhdf.DataspaceLibConstants;

/**
 * Interface for Dataspace objects.
 *
 */
public interface IDataspace extends IHResource {     
    
    /**
     * Gets the rank of the dataspace. 
     * 
     * <p>The rank is the number of dimensions in the dataspace.</p>
     * 
     * @return The number of dimensions in the dataspace.
     */
    public int getRank();
    
    /**
     * Gets the sizes of the individual dimensions of the dataspace.
     * 
     * <p>Returns an tuple of arrays of length {@link #getRank()} where each element
     * represents the current size of a dimension of the dataspace.  The first
     * array is the current sizes of the individual dimensions of the dataspace.
     * The second array is the maximum sizes of the individual dimensions of 
     * the dataspace. If the size of a dimension is unbounded, the value of the 
     * corresponding array element will be 
     * {@link DataspaceLibConstants#H5S_UNLIMITED}.</p>
     * 
     * @return The  sizes of the dimensions of the dataspace.
     */
    public DataspaceExtent getExtents();
      
    
    /**
     * Gets the current portion of the dataspace that is selected.
     * 
     * <p>Newly opened dataspaces are completely selected by default.</p>
     * 
     * @return The currently selected portion of the dataspace.
     */
    public ISelector getSelection();
       
    /**
     * Clones the dataspace. 
     * 
     * @return A new copy of the dataspace.
     */
    public IDataspace copy();
    
}
