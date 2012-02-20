%include "stdint.i"

%header %{
#include "H5public.h"
%}

/* Flags for object copy (H5Ocopy) */
#define H5O_COPY_SHALLOW_HIERARCHY_FLAG (0x0001)   /* Copy only immediate members */
#define H5O_COPY_EXPAND_SOFT_LINK_FLAG  (0x0002)   /* Expand soft links into new objects */
#define H5O_COPY_EXPAND_EXT_LINK_FLAG   (0x0004)   /* Expand external links into new objects */
#define H5O_COPY_EXPAND_REFERENCE_FLAG	(0x0008)   /* Copy objects that are pointed by references */
#define H5O_COPY_WITHOUT_ATTR_FLAG      (0x0010)   /* Copy object without copying attributes */
#define H5O_COPY_PRESERVE_NULL_FLAG     (0x0020)   /* Copy NULL messages (empty space) */
#define H5O_COPY_ALL                    (0x003F)   /* All object copying flags (for internal checking) */

/* Flags for shared message indexes.
 * Pass these flags in using the mesg_type_flags parameter in
 * H5P_set_shared_mesg_index.
 * (Developers: These flags correspond to object header message type IDs,
 * but we need to assign each kind of message to a different bit so that
 * one index can hold multiple types.)
 */
#define H5O_SHMESG_NONE_FLAG    0x0000          /* No shared messages */
#define H5O_SHMESG_SDSPACE_FLAG 0x0002          /* Simple Dataspace Message.  */
#define H5O_SHMESG_DTYPE_FLAG   0x0008          /* Datatype Message.  */
#define H5O_SHMESG_FILL_FLAG    0x0020          /* Fill Value Message. */
#define H5O_SHMESG_PLINE_FLAG   0x0800          /* Filter pipeline message.  */
#define H5O_SHMESG_ATTR_FLAG    0x1000          /* Attribute Message.  */
#define H5O_SHMESG_ALL_FLAG     (H5O_SHMESG_SDSPACE_FLAG | H5O_SHMESG_DTYPE_FLAG | H5O_SHMESG_FILL_FLAG | H5O_SHMESG_PLINE_FLAG | H5O_SHMESG_ATTR_FLAG)

/* Object header status flag definitions */
#define H5O_HDR_CHUNK0_SIZE             0x03    /* 2-bit field indicating # of bytes to store the size of chunk 0's data */
#define H5O_HDR_ATTR_CRT_ORDER_TRACKED  0x04    /* Attribute creation order is tracked */
#define H5O_HDR_ATTR_CRT_ORDER_INDEXED  0x08    /* Attribute creation order has index */
#define H5O_HDR_ATTR_STORE_PHASE_CHANGE 0x10    /* Non-default attribute storage phase change values stored */
#define H5O_HDR_STORE_TIMES             0x20    /* Store access, modification, change & birth times for object */
#define H5O_HDR_ALL_FLAGS       (H5O_HDR_CHUNK0_SIZE | H5O_HDR_ATTR_CRT_ORDER_TRACKED | H5O_HDR_ATTR_CRT_ORDER_INDEXED | H5O_HDR_ATTR_STORE_PHASE_CHANGE | H5O_HDR_STORE_TIMES)

/* Maximum shared message values.  Number of indexes is 8 to allow room to add
 * new types of messages.
 */
#define H5O_SHMESG_MAX_NINDEXES 8
#define H5O_SHMESG_MAX_LIST_SIZE 5000


%rename H5O_type_t ObjectType;
/* Types of objects in file */
typedef enum H5O_type_t {
    H5O_TYPE_UNKNOWN = -1,	/* Unknown object type		*/
    H5O_TYPE_GROUP,	        /* Object is a group		*/
    H5O_TYPE_DATASET,		/* Object is a dataset		*/
    H5O_TYPE_NAMED_DATATYPE, 	/* Object is a named data type	*/
    H5O_TYPE_NTYPES             /* Number of different object types (must be last!) */
} H5O_type_t;
HENUM_OUTPUT_TYPEMAP(H5O_TYPE_ENUM, ObjectType);


/** Information struct for object 
 * (for H5Oget_info/H5Oget_info_by_name/H5Oget_info_by_idx) 
 */
%immutable;
%rename H5O_info_t ObjectInfo;
%apply long long {hsize_t num_attrs};
%apply long long {hsize_t total};
%apply long long {hsize_t meta};
%apply long long {hsize_t mesg};
%apply long long {hsize_t free};
%apply long long {hsize_t total};
typedef struct H5O_info_t {
    unsigned long 	fileno;		/**< File number that object is located in */
    haddr_t 		addr;		/**< Object address in file	*/
    H5O_type_t 		type;		/**< Basic object type (group, dataset, etc.) */
    unsigned 		rc;		/**< Reference count of object    */
    time_t		atime;		/**< Access time			*/
    time_t		mtime;		/**< Modification time		*/
    time_t		ctime;		/**< Change time			*/
    time_t		btime;		/**< Birth time			*/
    hsize_t 		num_attrs;	/**< # of attributes attached to object */
    struct {
        unsigned version;		/**< Version number of header format in file */
        unsigned nmesgs;		/**< Number of object header messages */
        unsigned nchunks;		/**< Number of object header chunks */
        unsigned flags;                 /**< Object header status flags */
        struct {
            hsize_t total;		/**< Total space for storing object header in file */
            hsize_t meta;		/**< Space within header for object header metadata information */
            hsize_t mesg;		/**< Space within header for actual message information */
            hsize_t free;		/**< Free space within object header */
        } space;
        struct {
            uint64_t present;		/**< Flags to indicate presence of message type in header */
            uint64_t shared;		/**< Flags to indicate message type is shared in header */
        } mesg;
    } hdr;
  /** Extra metadata storage for obj & attributes */
    struct {
        H5_ih_info_t   obj;             /**< v1-v2 B-tree & local-fractal heap for groups, B-tree for chunked datasets */
        H5_ih_info_t   attr;            /**< v2 B-tree & heap for attributes */
    } meta_size;
} H5O_info_t;
%mutable;
%clear hsize_t num_attrs;
%clear hsize_t total;
%clear hsize_t meta;
%clear hsize_t mesg;
%clear hsize_t free;
%clear hsize_t total;

/** Typedef for message creation indexes */
typedef uint32_t H5O_msg_crt_idx_t; 

%immutable;
%rename H5O_stat_t ObjectStatus;
%apply long long {hsize_t nmesgs};
%apply long long {hsize_t nchunks};
typedef struct H5O_stat_t {
    hsize_t size;               /* Total size of object header in file */
    hsize_t free;               /* Free space within object header */
    unsigned nmesgs;            /* Number of object header messages */
    unsigned nchunks;           /* Number of object header chunks */
} H5O_stat_t;
%mutable;
%clear hsize_t nmesgs;
%clear hsize_t nchunks;
