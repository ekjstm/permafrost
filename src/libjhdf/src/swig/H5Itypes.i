%include "enums.swg"
%include "henums.i"

/*
 * Group values allowed.  Start with `1' instead of `0' because it makes the
 * tracing output look better when hid_t values are large numbers.  Change the
 * GROUP_BITS in H5I.c if the MAXID gets larger than 32 (an assertion will
 * fail otherwise).
 *
 * When adding groups here, add a section to the 'misc19' test in test/tmisc.c
 * to verify that the H5I{inc|dec|get}_ref() routines work correctly with in.
 *
 */
%rename H5I_type_t IdentifierType;
typedef enum {
    H5I_BADID		= (-1),	/*invalid Group				    */
    H5I_FILE		= 1,	/*group ID for File objects		    */
    H5I_GROUP,		        /*group ID for Group objects		    */
    H5I_DATATYPE,	        /*group ID for Datatype objects		    */
    H5I_DATASPACE,	        /*group ID for Dataspace objects	    */
    H5I_DATASET,	        /*group ID for Dataset objects		    */
    H5I_ATTR,		        /*group ID for Attribute objects	    */
    H5I_REFERENCE,	        /*group ID for Reference objects	    */
    H5I_VFL,			/*group ID for virtual file layer	    */
    H5I_GENPROP_CLS,            /*group ID for generic property list classes */
    H5I_GENPROP_LST,            /*group ID for generic property lists       */

    H5I_NGROUPS		        /*number of valid groups, MUST BE LAST!	    */
} H5I_type_t;
HENUM_OUTPUT_TYPEMAP(H5I_TYPE_ENUM, IdentifierType);