/**
 *
 */
package permafrost.hdf.basic.impl;

import java.text.MessageFormat;

import permafrost.hdf.basic.ISelector;
import permafrost.hdf.libhdf.DataspaceLib;
import permafrost.hdf.libhdf.DataspaceSelectionOPType;

/**
 * TODO add type documentation
 *
 */
public class Selector implements ISelector {

    private final Dataspace space;
    
    /**
     * Creates a new Selector object.
     *
     */
    public Selector(Dataspace space) {
        super();
        this.space = space;
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.ISelector#selectAll()
     */
    @Override
    public Selector selectAll() {
        assert this.space.isInitialized() : "Native reference not initialized";
        int status = DataspaceLib.H5Sselect_all(this.space.getHId());
        assert status >= 0 : "Untrapped error in native code.";
        return (this);
    }

            
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.ISelector#selectElements(long[][])
     */
    @Override
    public Selector selectElements(long[][] pts) {
        assert this.space.isInitialized() : "Native reference not initialized";
        int rank = this.space.getRank();
        int nElements = pts.length;
        
        for (int n=0; n<pts.length; n++) {
            long[] ptn = pts[n];
            if (ptn.length != rank) throw new IllegalArgumentException(
                    MessageFormat.format("Coordinate vector must have same rank as data space. " +
                            "(Expected {1,number}, recieved {2,number} at element {3,number}", 
                            rank, ptn.length, n)
            );            
        }
        
        int status = DataspaceLib.H5Sselect_elements(this.space.getHId(), 
                DataspaceSelectionOPType.H5S_SELECT_SET, 
                nElements, 
                pts);
        assert status >= 0 : "Untrapped error in native code.";
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.ISelector#selectNone()
     */
    @Override
    public Selector selectNone() {
        assert this.space.isInitialized() : "Native reference not initialized";
        int status = DataspaceLib.H5Sselect_none(this.space.getHId());
        assert status >= 0 : "Untrapped error in native code.";
        return (this);
    }
    
   
    
}
