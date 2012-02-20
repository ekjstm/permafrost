/*
 * Filter identifiers.  Values 0 through 255 are for filters defined by the
 * HDF5 library.  Values 256 through 511 are available for testing new
 * filters. Subsequent values should be obtained from the HDF5 development
 * team at hdf5dev@ncsa.uiuc.edu.  These values will never change because they
 * appear in the HDF5 files.
 */
typedef int H5Z_filter_t;
#define H5Z_FILTER_ERROR	(-1)	/*no filter			*/
#define H5Z_FILTER_NONE		0	/*reserved indefinitely		*/
#define H5Z_FILTER_ALL	 	0	/*symbol to remove all filters in H5Premove_filter		*/
#define H5Z_FILTER_DEFLATE	1 	/*deflation like gzip	     	*/
#define H5Z_FILTER_SHUFFLE      2       /*shuffle the data              */
#define H5Z_FILTER_FLETCHER32   3       /*fletcher32 checksum of EDC       */
#define H5Z_FILTER_SZIP         4       /*szip compression               */
#define H5Z_FILTER_NBIT         5       /*nbit compression              */
#define H5Z_FILTER_SCALEOFFSET  6       /*scale+offset compression      */
#define H5Z_FILTER_RESERVED     256	/*filter ids below this value are reserved */
#define H5Z_FILTER_MAX		65535	/*maximum filter id		*/
#define H5Z_MAX_NFILTERS        32      /* Maximum number of filters allowed in a pipeline (should probably be allowed to be an unlimited amount) */

/* Flags for filter definition */
#define H5Z_FLAG_DEFMASK	0x00ff	/*definition flag mask		*/
#define H5Z_FLAG_MANDATORY      0x0000  /*filter is mandatory		*/
#define H5Z_FLAG_OPTIONAL	0x0001	/*filter is optional		*/

/* Additional flags for filter invocation */
#define H5Z_FLAG_INVMASK	0xff00	/*invocation flag mask		*/
#define H5Z_FLAG_REVERSE	0x0100	/*reverse direction; read	*/
#define H5Z_FLAG_SKIP_EDC	0x0200	/*skip EDC filters for read	*/

/* Special parameters for szip compression */
/* [These are aliases for the similar definitions in szlib.h, which we can't
 * include directly due to the duplication of various symbols with the zlib.h
 * header file] */
#define H5_SZIP_ALLOW_K13_OPTION_MASK   1
#define H5_SZIP_CHIP_OPTION_MASK        2
#define H5_SZIP_EC_OPTION_MASK          4
#define H5_SZIP_NN_OPTION_MASK          32
#define H5_SZIP_MAX_PIXELS_PER_BLOCK    32

/* Special parameters for ScaleOffset filter*/
%rename H5Z_SO_scale_type_t FilterScaleOffsetType;
typedef enum H5Z_SO_scale_type_t {
    H5Z_SO_FLOAT_DSCALE = 0,
    H5Z_SO_FLOAT_ESCALE = 1,
    H5Z_SO_INT          = 2
} H5Z_SO_scale_type_t;

/* Values to decide if EDC is enabled for reading data */
%rename H5Z_EDC_t FilterErrorDetectionType;
typedef enum H5Z_EDC_t {
    H5Z_ERROR_EDC       = -1,   /* error value */
    H5Z_DISABLE_EDC     = 0,
    H5Z_ENABLE_EDC      = 1,
    H5Z_NO_EDC          = 2     /* must be the last */
} H5Z_EDC_t;

/* Bit flags for H5Zget_filter_info */
#define H5Z_FILTER_CONFIG_ENCODE_ENABLED (0x0001)
#define H5Z_FILTER_CONFIG_DECODE_ENABLED (0x0002)

/* Return values for filter callback function */
%rename H5Z_cb_return_t FilterCallbackReturnType;
typedef enum H5Z_cb_return_t {
    H5Z_CB_ERROR  = -1,
    H5Z_CB_FAIL   = 0,    /* I/O should fail if filter fails. */
    H5Z_CB_CONT   = 1,    /* I/O continues if filter fails.   */
    H5Z_CB_NO     = 2
} H5Z_cb_return_t;

/* Structure for filter callback property */
%rename H5Z_cb_t FilterCallbackType;
typedef struct H5Z_cb_t {
    H5Z_filter_func_t func;
    void*              op_data;
} H5Z_cb_t;

/*
 * The filter table maps filter identification numbers to structs that
 * contain a pointers to the filter function and timing statistics.
 */
%rename H5Z_class_t FilterClassType;
%immutable;
typedef struct H5Z_class_t {
    H5Z_filter_t id;		/* Filter ID number			     */
    const char	*name;		/* Comment for debugging		     */
    H5Z_can_apply_func_t can_apply; /* The "can apply" callback for a filter */
    H5Z_set_local_func_t set_local; /* The "set local" callback for a filter */
    H5Z_func_t filter;		/* The actual filter function		     */
} H5Z_class_t;
%mutable;
