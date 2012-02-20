/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.libhdf.GroupLib;
import permafrost.hdf.libhdf.PropertiesLib;

/**
 * Implementation of the HDF5 group object.
 *
 */
public class Group extends AbstractGroup {

    /** Implements the native handle for Group. */
    private static final class GroupReference extends NativeReference {

        /**
         * Creates a new GroupReference object.
         *
         * @param referent The vm referent.
         */
        public GroupReference(Group referent) {
            super(referent);
        }

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.NativeReference#doDispose()
         */
        @Override
        protected void doDispose() {
            if (this.hid > 0) {
                int status = GroupLib.H5Gclose(this.hid);
                assert status >= 0 : "Untrapped error in native code.";
                this.hid = 0;
            }            
        }        
    }
    
    /**
     * Creates a new Group object.
     * 
     * @param hid The native resource id.
     */
    protected Group(int hid) {
       super(hid);
       this.setReference(new GroupReference(this));
    }
    
    /**
     * Creates a new Group object.
     * 
     * @param hid The native resource id.
     * @param name The name of the group. 
     */
    protected Group(int hid, String name) {
       super(hid, name);
       this.setReference(new GroupReference(this));
    }      
    
    /**
     * Opens an existing group.
     * 
     * @param ref The resource identifier for the parent group.
     * @param name The name of the group to open.
     * 
     * @return A new Group proxy for the given path.
     */
    protected static Group open(int ref, String name) {
        assert ref > 0 : "Illegal value for native reference.";
        int gid = GroupLib.H5Gopen2(ref, name, PropertiesLib.H5P_DEFAULT);
        assert gid > 0 : "Untrapped error in native code.";
        Group group = new Group(gid, name);             
        return (group);
    }
    
    /**
     * Creates a new group.
     * 
     * @param ref The resource identifier for the parent group.
     * @param name The name of the group to create.
     * 
     * @return A new Group proxy for the given path.
     */
    protected static Group create(int ref, String name) {
        assert ref > 0 : "Illegal value for native reference.";
        int gid = GroupLib.H5Gcreate2(
                ref, 
                name, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        assert gid > 0 : "Untrapped error in native code.";
        Group group = new Group(gid, name);       
        return (group);
    }
    
    
}
