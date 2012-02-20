%import H5types.i
%include "henums.i"

#define H5F_ACC_RDONLY	(0x0000)	/*absence of rdwr => rd-only */
#define H5F_ACC_RDWR	(0x0001)	/*open for read and write    */
#define H5F_ACC_TRUNC	(0x0002)	/*overwrite existing files   */
#define H5F_ACC_EXCL	(0x0004)	/*fail if file already exists*/
#define H5F_ACC_DEBUG	(0x0008)	/*print debug info	     */
#define H5F_ACC_CREAT	(0x0010)	/*create non-existing files  */

/* Flags for H5Fget_obj_count() & H5Fget_obj_ids() calls */
#define H5F_OBJ_FILE	(0x0001)       /* File objects */
#define H5F_OBJ_DATASET	(0x0002)       /* Dataset objects */
#define H5F_OBJ_GROUP	(0x0004)       /* Group objects */
#define H5F_OBJ_DATATYPE (0x0008)      /* Named datatype objects */
#define H5F_OBJ_ATTR    (0x0010)       /* Attribute objects */
#define H5F_OBJ_ALL 	(H5F_OBJ_FILE|H5F_OBJ_DATASET|H5F_OBJ_GROUP|H5F_OBJ_DATATYPE|H5F_OBJ_ATTR)
#define H5F_OBJ_LOCAL   (0x0020)       /* Restrict search to objects opened 
					* through current file ID (as opposed 
					* to objects opened through any file 
					* ID accessing this file). */



/* The difference between a single file and a set of mounted files */
%rename H5F_scope_t FileScopeType;
typedef enum H5F_scope_t {
    H5F_SCOPE_LOCAL	= 0,	/*specified file handle only		*/
    H5F_SCOPE_GLOBAL	= 1,	/*entire virtual file			*/
    H5F_SCOPE_DOWN      = 2	/*for internal use only			*/
} H5F_scope_t;
HENUM_OUTPUT_TYPEMAP(H5F_SCOPE_ENUM, FileScopeType);

/* How does file close behave?
 * H5F_CLOSE_DEFAULT - Use the degree pre-defined by underlining VFL
 * H5F_CLOSE_WEAK    - file closes only after all opened objects are closed
 * H5F_CLOSE_SEMI    - if no opened objects, file is close; otherwise, file
		       close fails
 * H5F_CLOSE_STRONG  - if there are opened objects, close them first, then
		       close file
 */
%rename H5F_close_degree_t FileCloseBehaviorType;
typedef enum H5F_close_degree_t {
    H5F_CLOSE_DEFAULT   = 0,
    H5F_CLOSE_WEAK      = 1,
    H5F_CLOSE_SEMI      = 2,
    H5F_CLOSE_STRONG    = 3
} H5F_close_degree_t;
HENUM_OUTPUT_TYPEMAP(H5F_CLOSE_ENUM, FileCloseBehaviorType);

/* Current "global" information about file */
/* (just size info currently) */
%rename H5F_info_t FileInfo;
%rename H5F_info_t_sohm FileSharedObjectHeaderMessage;
typedef struct H5F_info_t {
    hsize_t super_ext_size;	/* Superblock extension size */
    struct {
	hsize_t hdr_size;       /* Shared object header message header size */
	H5_ih_info_t msgs_info; /* Shared object header message index & 
                                 * heap size */
    } sohm;
} H5F_info_t;

/* Library's file format versions */
%rename H5F_libver_t FileLibVersionType;
typedef enum H5F_libver_t {
    H5F_LIBVER_EARLIEST,        /* Use the earliest possible format for storing objects */
    H5F_LIBVER_LATEST           /* Use the latest possible format available for storing objects*/
} H5F_libver_t;
HENUM_OUTPUT_TYPEMAP(H5F_LIBVER_ENUM, FileLibVersionType);
