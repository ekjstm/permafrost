/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * Native functions for {@link ObjectLibExtension}.
 *
 */
class ObjectLibExtensionJNI {

    /**
     * Opens an object given an index. 
     * 
     * @param loc_id The native id of the base location.
     * @param group_name The name of the parent group, relative to loc_id.
     * @param idx_type The type of indexing used.
     * @param order The order of indexing used.
     * @param n The object index to open.
     * @param lapl_id Native id of property list to use accessing the group link.
     * @param type (Output) The type of the object opened.
     * @param name (Output) The name of the object opened.
     * 
     * @return Native id of the newly opened object.
     */
    public static final native int JHOopen_by_idx(
            int loc_id, 
            String group_name, 
            int idx_type, 
            int order, 
            long n, 
            int lapl_id, 
            int[] type, 
            String[] name
    );
    
}
