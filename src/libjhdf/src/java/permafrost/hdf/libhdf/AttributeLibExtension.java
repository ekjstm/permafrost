/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * TODO add type documentation
 *
 */
public class AttributeLibExtension {

    public static int JHAopen_by_idx(
            int loc_id, 
            String group_name, 
            IndexType idx_type, 
            IteratorOrderType order, 
            long n, 
            int aapl_id,
            int lapl_id,  
            String[] name
    ) {       
        return AttributeLibExtensionJNI.JHAopen_by_idx(
                loc_id, 
                group_name, 
                idx_type.swigValue(),
                order.swigValue(), 
                n, 
                aapl_id,
                lapl_id, 
                name
        );        
    }
    
}
