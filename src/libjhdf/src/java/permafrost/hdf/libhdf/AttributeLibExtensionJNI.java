/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * TODO add type documentation
 *
 */
public class AttributeLibExtensionJNI {

    public static native int JHAopen_by_idx(
            int loc_id,  
            String obj_name,
            int idx_type, 
            int order, 
            long n, 
            int aapl_id, 
            int lapl_id,
            String[] name
            );
    
}
