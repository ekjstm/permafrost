/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * LibJHDF extension functions for {@link ObjectLib}.
 *
 */
public class ObjectLibExtension {

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
    public static int JHOopen_by_idx(
            int loc_id, 
            String group_name, 
            IndexType idx_type, 
            IteratorOrderType order, 
            long n, 
            int lapl_id, 
            IdentifierType[] type, 
            String[] name
    ) {
        int[] tmpType = {-1};
        try {
          return ObjectLibExtensionJNI.JHOopen_by_idx(
                  loc_id, 
                  group_name, 
                  idx_type.swigValue(),
                  order.swigValue(), 
                  n, 
                  lapl_id, 
                  tmpType, 
                  name
          );
        } finally {
          type[0] = permafrost.hdf.libhdf.IdentifierType.swigToEnum(tmpType[0]);
        }
    }
}
