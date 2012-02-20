/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.basic.IDataspace;
import permafrost.hdf.basic.DataspaceExtent;
import permafrost.hdf.libhdf.DataspaceLib;

/**
 * Implementation of Dataspace object.
 *
 */
public class Dataspace extends AbstractResource implements IDataspace {

    /** Implements the native handle for Dataspace. */
    private static final class DataspaceReference extends NativeReference {

        /**
         * Creates a new DatasetReference object.
         *
         * @param referent The vm referent.
         */
        public DataspaceReference(Dataspace referent) {
            super(referent);
        }

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.NativeReference#doDispose()
         */
        @Override
        protected void doDispose() {
            if (this.hid > 0) {
                int status = DataspaceLib.H5Sclose(this.hid);
                assert status >= 0 : "Untrapped error in native code.";
                this.hid = 0;
            }            
        }        
    }
    
    private int rank = -1;
    private final Selector selector;
    
    
    /**
     * Creates a new Dataspace object.
     *
     * @param hid he native resource id.
     */
    public Dataspace(int hid) {
        super(hid);
        this.setReference(new DataspaceReference(this));
        this.selector = new Selector(this);
    }

   

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataspace#copy()
     */
    @Override
    public Dataspace copy() {
        assert this.isInitialized() : "Native reference not initialized";
        int sid = DataspaceLib.H5Scopy(this.hid);
        assert sid > 0 : "Untrapped error in native code.";
        Dataspace other = new Dataspace(sid);
        return (other);
    }
   

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataspace#getExtents()
     */
    @Override
    public DataspaceExtent getExtents() {
        assert this.isInitialized() : "Native reference not initialized";
        if (rank < 0) this.getRank();
        long[] dims = new long[this.rank];
        long[] maxdims = new long[this.rank];
        int status = DataspaceLib.H5Sget_simple_extent_dims(this.hid, dims, maxdims);
        assert status >= 0 : "Untrapped error in native code.";
        DataspaceExtent tuple = new DataspaceExtent(dims, maxdims);
        return (tuple);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataspace#getRank()
     */
    @Override
    public int getRank() {
        if (this.rank >= 0) return (this.rank);
        
        assert this.isInitialized() : "Native reference not initialized";
        int xrank = DataspaceLib.H5Sget_simple_extent_ndims(this.hid);
        assert xrank >= 0 : "Untrapped error in native code.";
        this.rank = xrank;
        return (xrank);
    }
        

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataspace#getSelection()
     */
    @Override
    public Selector getSelection() {
      return (this.selector);
    }

    public static Dataspace create(long[] dims, long[] maxdims) {
        if (dims.length != maxdims.length) {
            throw new IllegalArgumentException("Length of dimension specification arrays must match.");
        }
        int sid = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assert sid > 0 : "Untrapped error in native code.";
        Dataspace space = new Dataspace(sid);
        return (space);
    }
}
