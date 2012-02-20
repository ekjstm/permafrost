/**
 *
 */
package permafrost.hdf.basic;

/**
 * Selection interface.
 *
 */
public interface ISelector {

    /**
     * Selects the entire dataspace.
     * 
     * @return A selector representing a selection of the entire dataspace.
     */
    public ISelector selectAll();
    
    /**
     * Selects nothing.
     * 
     * @return A selector representing a selection of nothing.
     */
    public ISelector selectNone();
    
    /**
     * Selects specific elements in the dataspace.
     * 
     * <p>Point vectors are expected to have length equal to 
     * the rank of the dataspace.</p>
     * 
     * @param pts The elements in the selection.
     * 
     * @return A selector representing the selection.
     */
    public ISelector selectElements(long[][] pts);
    
    
}
