%include "stdint.i"

%header %{
  #define HARR_NOARRAYS 1
%}
%include "harrays.i"
/*
 * An object has a certain type. The first few numbers are reserved for use
 * internally by HDF5. Users may add their own types with higher values.  The
 * values are never stored in the file -- they only exist while an
 * application is running.  An object may satisfy the `isa' function for more
 * than one type.
 */
%rename H5G_obj_t GroupObjectType;
typedef enum H5G_obj_t {
    H5G_UNKNOWN = -1,		/* Unknown object type		*/
    H5G_GROUP,		        /* Object is a group		*/
    H5G_DATASET,		/* Object is a dataset		*/
    H5G_TYPE,			/* Object is a named data type	*/
    H5G_LINK,		        /* Object is a symbolic link	*/
    H5G_UDLINK,		        /* Object is a user-defined link */
    H5G_RESERVED_5,		/* Reserved for future use	*/
    H5G_RESERVED_6,		/* Reserved for future use	*/
    H5G_RESERVED_7		/* Reserved for future use	*/
} H5G_obj_t;
HENUM_OUTPUT_TYPEMAP(H5G_OTYPE_ENUM, GroupObjectType);

/* Types of links */
%rename H5G_link_t GroupLinkType;
typedef enum H5G_link_t {
    H5G_LINK_ERROR	= -1,
    H5G_LINK_HARD	= 0,
    H5G_LINK_SOFT	= 1
} H5G_link_t;
HENUM_OUTPUT_TYPEMAP(H5G_LTYPE_ENUM, GroupLinkType);

/* Information about an object */
%rename H5G_stat_t GroupStatus;
%immutable;
%apply unsigned long OUTPUT[] {unsigned long fileno[2]}
%apply unsigned long OUTPUT[] {unsigned long objno[2]}    
typedef struct H5G_stat_t {
    unsigned long 	fileno[2];	/*file number			*/
    unsigned long 	objno[2];	/*object number			*/
    unsigned 		nlink;		/*number of hard links to object*/
    H5G_obj_t 		type;		/*basic object type		*/
    time_t		mtime;		/*modification time		*/
    size_t		linklen;	/*symbolic link value length	*/
    H5O_stat_t          ohdr;           /* Object header information    */
} H5G_stat_t;
%mutable;

/* Types of link storage for groups */
%rename H5G_storage_type_t GroupStoreageType;
typedef enum H5G_storage_type_t {
    H5G_STORAGE_TYPE_UNKNOWN = -1,	/* Unknown link storage type	*/
    H5G_STORAGE_TYPE_SYMBOL_TABLE,      /* Links in group are stored with a "symbol table" */
                                        /* (this is sometimes called "old-style" groups) */
    H5G_STORAGE_TYPE_COMPACT,		/* Links are stored in object header */
    H5G_STORAGE_TYPE_DENSE 		/* Links are stored in fractal heap & indexed with v2 B-tree */
} H5G_storage_type_t;
HENUM_OUTPUT_TYPEMAP(H5G_STORE_ENUM, GroupStorageType);

/* Information struct for group (for H5Gget_info/H5Gget_info_by_name/H5Gget_info_by_idx) */
%rename H5G_info_t GroupInfo;
%immutable;
%apply long long {hsize_t nlinks}
%apply long long {int64_t max_corder}
typedef struct H5G_info_t {
    H5G_storage_type_t 	storage_type;	/* Type of storage for links in group*/
    hsize_t 	nlinks;		        /* Number of links in group */
    int64_t     max_corder;             /* Current max. creation order value for group */
} H5G_info_t;
%mutable;
%clear hsize_t nlinks;
%clear int64_t max_corder;
