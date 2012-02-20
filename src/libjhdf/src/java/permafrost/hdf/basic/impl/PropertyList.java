/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.libhdf.PropertiesLib;

/**
 * PropertyList wrapper class.
 *
 */
public class PropertyList extends AbstractResource {    
    
    
    /** Implements the native handle for Attribute. */
    private static final class PListReference extends NativeReference {

        /**
         * Creates a new AttributeReference object.
         *
         * @param referent The vm referent.
         */
        public PListReference(PropertyList referent) {
            super(referent);
        }

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.NativeReference#doDispose()
         */
        @Override
        protected void doDispose() {
            if (this.hid > 0) {
                int status = PropertiesLib.H5Pclose(this.hid);
                assert status >= 0 : "Untrapped error in native code.";
                this.hid = 0;
            }            
        }        
    }
    
    
    public static final PropertyList Default = new PropertyList(PropertiesLib.H5P_DEFAULT) {

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.AbstractResource#dispose()
         */
        @Override
        public void dispose() {
            /* Do nothing. */
        }

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.AbstractResource#isInitialized()
         */
        @Override
        protected boolean isInitialized() {
            return (true);
        }
        
        
        
    };
    
    /**
     * Creates a new PropertyList object.
     *
     * @param hid
     */
    public PropertyList(int hid) {
        super(hid);
        this.setReference(new PListReference(this));
    }
    

}
