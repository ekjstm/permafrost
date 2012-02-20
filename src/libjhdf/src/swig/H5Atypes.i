%import "H5Otypes.i"
%import "H5Ttypes.i"

/** Information struct for attribute (for H5Aget_info/H5Aget_info_by_idx) */
%rename H5A_info_t AttributeInfo;
%apply long long {hsize_t data_size}
typedef struct {
    hbool_t             corder_valid;   /**< Indicate if creation order is valid */
    H5O_msg_crt_idx_t   corder;         /**< Creation order                 */
    H5T_cset_t          cset;           /**< Character set of attribute name */
    hsize_t             data_size;      /**< Size of raw data		  */
} H5A_info_t;
%clear hsize_t data_size;