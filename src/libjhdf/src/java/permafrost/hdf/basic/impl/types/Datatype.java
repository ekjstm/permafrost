/**
 *
 */
package permafrost.hdf.basic.impl.types;

import org.apache.log4j.Logger;

import permafrost.hdf.basic.IDatatype;
import permafrost.hdf.basic.impl.AbstractResource;
import permafrost.hdf.basic.impl.Cleaner;
import permafrost.hdf.basic.impl.NativeReference;
import permafrost.hdf.libhdf.DatatypeClassType;
import permafrost.hdf.libhdf.DatatypeLib;



/**
 * Implementation of {@link IDatatype}.
 *
 */
public class Datatype extends AbstractResource implements IDatatype {

    /** Implements the native handle for Datatypes. */
    private static final class DatatypeReference extends NativeReference {       
        
        /**
         * Creates a new DatasetReference object.
         *
         * @param referent The vm referent.
         */
        public DatatypeReference(Datatype referent) {
            super(referent);
        }

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.NativeReference#doDispose()
         */
        @Override
        protected void doDispose() {
            if (this.hid > 0) {                
                int status = DatatypeLib.H5Tclose(this.hid);
                assert status >= 0 : "Untrapped error in native code.";                
                this.hid = 0;
            }            
        }     
                
    }
    
    /**
     * Creates a new Datatype object.
     *
     * @param hid The native resource id.
     */
    protected Datatype(int hid) {
        super(hid);        
        this.setReference(new DatatypeReference(this));
    }
    
    /**
     * 
     * Creates a new Datatype object.
     *
     * @param hid The native resource id.
     * @param name The name of the group. 
     */
    protected Datatype(int hid, String name) {
        super(hid, name);
        this.setReference(new DatatypeReference(this));
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatype#getClassType()
     */
    @Override
    public DatatypeClassType getClassType() {
        assert this.isInitialized() : "Native reference not initialized";
        DatatypeClassType type = DatatypeLib.H5Tget_class(this.hid);
        assert type.swigValue() >= 0 : "Untrapped error in native code.";
        return (type);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatype#getSize()
     */
    @Override
    public long getSize() {
        assert this.isInitialized() : "Native reference not initialized";
        long size = DatatypeLib.H5Tget_size(this.hid);
        assert size >= 0 : "Untrapped error in native code.";
        return (size);
    }               

}
