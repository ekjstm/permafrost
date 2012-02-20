/* Define atomic datatypes */
#define H5S_ALL         0
#define H5S_UNLIMITED  -1

/* Define user-level maximum number of dimensions */
#define H5S_MAX_RANK    32

/* Different types of dataspaces */
%rename H5S_class_t DataspaceClassType;
typedef enum H5S_class_t {
    H5S_NO_CLASS         = -1,  /*error                                      */
    H5S_SCALAR           = 0,   /*scalar variable                            */
    H5S_SIMPLE           = 1,   /*simple data space                          */
    H5S_NULL             = 2    /*null data space                         */
} H5S_class_t;

/* Different ways of combining selections */
%rename H5S_seloper_t DataspaceSelectionOPType;
typedef enum H5S_seloper_t {
    H5S_SELECT_NOOP      = -1,  /* error                                     */
    H5S_SELECT_SET       = 0,   /* Select "set" operation 		     */
    H5S_SELECT_OR,              /* Binary "or" operation for hyperslabs
                                 * (add new selection to existing selection)
                                 * Original region:  AAAAAAAAAA
                                 * New region:             BBBBBBBBBB
                                 * A or B:           CCCCCCCCCCCCCCCC
                                 */
    H5S_SELECT_AND,             /* Binary "and" operation for hyperslabs
                                 * (only leave overlapped regions in selection)
                                 * Original region:  AAAAAAAAAA
                                 * New region:             BBBBBBBBBB
                                 * A and B:                CCCC
                                 */
    H5S_SELECT_XOR,             /* Binary "xor" operation for hyperslabs
                                 * (only leave non-overlapped regions in selection)
                                 * Original region:  AAAAAAAAAA
                                 * New region:             BBBBBBBBBB
                                 * A xor B:          CCCCCC    CCCCCC
                                 */
    H5S_SELECT_NOTB,            /* Binary "not" operation for hyperslabs
                                 * (only leave non-overlapped regions in original selection)
                                 * Original region:  AAAAAAAAAA
                                 * New region:             BBBBBBBBBB
                                 * A not B:          CCCCCC
                                 */
    H5S_SELECT_NOTA,            /* Binary "not" operation for hyperslabs
                                 * (only leave non-overlapped regions in new selection)
                                 * Original region:  AAAAAAAAAA
                                 * New region:             BBBBBBBBBB
                                 * B not A:                    CCCCCC
                                 */
    H5S_SELECT_APPEND,          /* Append elements to end of point selection */
    H5S_SELECT_PREPEND,         /* Prepend elements to beginning of point selection */
    H5S_SELECT_INVALID          /* Invalid upper bound on selection operations */
} H5S_seloper_t;

/* Enumerated type for the type of selection */
%rename H5S_sel_type DataspaceSelectionType;
typedef enum {
    H5S_SEL_ERROR	= -1, 	/* Error			*/
    H5S_SEL_NONE	= 0,    /* Nothing selected 		*/
    H5S_SEL_POINTS	= 1,    /* Sequence of points selected	*/
    H5S_SEL_HYPERSLABS  = 2,    /* "New-style" hyperslab selection defined	*/
    H5S_SEL_ALL		= 3,    /* Entire extent selected	*/
    H5S_SEL_N		= 4	/*THIS MUST BE LAST		*/
}H5S_sel_type;

/* TODO This doesn't belong here. */
%typemap(jni) const hssize_t* INPUT "jlong"
%typemap(jtype) const hssize_t* INPUT "long"
%typemap(jstype) const hssize_t* INPUT "long"
%typemap(javain) const hssize_t* INPUT "$javainput"
%typemap(in) const hssize_t* INPUT
%{ $1 = ($1_ltype)&$input; /* FIXME here */ %}
%typemap(freearg) const hssize_t* INPUT ""



