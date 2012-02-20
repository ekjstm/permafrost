%header %{
#include "hdf5.h"
%}
%include "stdint.i"

%import "H5Ttypes.i"


/* Maximum length of a link's name */
/* (encoded in a 32-bit unsigned integer) */
#define H5L_MAX_LINK_NAME_LEN   ((uint32_t)(-1))  /* (4GB - 1) */

/* Macro to indicate operation occurs on same location */
#define H5L_SAME_LOC 0

/* Current version of the H5L_class_t struct */
#define H5L_LINK_CLASS_T_VERS 0


/* Link class types.
 * Values less than 64 are reserved for the HDF5 library's internal use.
 * Values 64 to 255 are for "user-defined" link class types; these types are
 * defined by HDF5 but their behavior can be overridden by users.
 * Users who want to create new classes of links should contact the HDF5
 * development team at hdfhelp@ncsa.uiuc.edu .
 * These values can never change because they appear in HDF5 files. 
 */
%rename H5L_type_t LinkType;
typedef enum {
    H5L_TYPE_ERROR = (-1),      /* Invalid link type id         */
    H5L_TYPE_HARD = 0,          /* Hard link id                 */
    H5L_TYPE_SOFT = 1,          /* Soft link id                 */
    H5L_TYPE_EXTERNAL = 64,     /* External link id             */
    H5L_TYPE_MAX = 255	        /* Maximum link type id         */
} H5L_type_t;
HENUM_OUTPUT_TYPEMAP(H5L_TYPE_ENUM, LinkType);

#define H5L_TYPE_BUILTIN_MAX H5L_TYPE_SOFT      /* Maximum value link value for "built-in" link types */

#define H5L_TYPE_UD_MIN      H5L_TYPE_EXTERNAL  /* Link ids at or above this value are "user-defined" link types. */

/* Information struct for link (for H5Lget_info/H5Lget_info_by_idx) */
%rename H5L_info_t LinkInfo;
%immutable;
typedef struct {
    H5L_type_t          type;           /* Type of link                   */
    hbool_t             corder_valid;   /* Indicate if creation order is valid */
    int64_t             corder;         /* Creation order                 */
    H5T_cset_t          cset;           /* Character set of link name     */
    union {
        haddr_t         address;        /* Address hard link points to    */
        size_t          val_size;       /* Size of a soft link or UD link value */
    } u;
} H5L_info_t;
%mutable;

/* User-defined link types */

//typedef struct {
//    int version;                    /* Version number of this struct      */
//    H5L_type_t id;                  /* Link type ID                       */
//    const char *comment;            /* Comment for debugging              */
//    H5L_create_func_t create_func;  /* Callback during link creation      */
//    H5L_move_func_t move_func;      /* Callback after moving link         */
//    H5L_copy_func_t copy_func;      /* Callback after copying link        */
//    H5L_traverse_func_t trav_func;  /* Callback during link traversal     */
//    H5L_delete_func_t del_func;     /* Callback for link deletion         */
//    H5L_query_func_t query_func;    /* Callback for queries               */
//} H5L_class_t;
