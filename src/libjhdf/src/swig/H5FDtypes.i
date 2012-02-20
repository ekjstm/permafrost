%include "henums.i"
/*
 * Types of allocation requests. The values larger than H5FD_MEM_DEFAULT
 * should not change other than adding new types to the end. These numbers
 * might appear in files.
 */
%rename H5FD_mem_t FileDriverAllocationType;
typedef enum H5FD_mem_t {
    H5FD_MEM_NOLIST	= -1,			/*must be negative*/
    H5FD_MEM_DEFAULT	= 0,			/*must be zero*/
    H5FD_MEM_SUPER      = 1,
    H5FD_MEM_BTREE      = 2,
    H5FD_MEM_DRAW       = 3,
    H5FD_MEM_GHEAP      = 4,
    H5FD_MEM_LHEAP      = 5,
    H5FD_MEM_OHDR       = 6,
    H5FD_MEM_NTYPES				/*must be last*/
} H5FD_mem_t;
HENUM_OUTPUT_TYPEMAP(H5FD_MEM_ENUM, FileDriverAllocationType);
HENUM_ARRIN_TYPEMAP(H5FD_MEM_ENUM, FileDriverAllocationType);
HENUM_ARROUT_TYPEMAP(H5FD_MEM_ENUM, FileDriverAllocationType);

/* Flags for tracking where reads/writes/seeks occur */
#define H5FD_LOG_LOC_READ   0x0001
#define H5FD_LOG_LOC_WRITE  0x0002
#define H5FD_LOG_LOC_SEEK   0x0004
#define H5FD_LOG_LOC_IO     (H5FD_LOG_LOC_READ|H5FD_LOG_LOC_WRITE|H5FD_LOG_LOC_SEEK)
/* Flags for tracking number of times each byte is read/written */
#define H5FD_LOG_FILE_READ  0x0008
#define H5FD_LOG_FILE_WRITE 0x0010
#define H5FD_LOG_FILE_IO    (H5FD_LOG_FILE_READ|H5FD_LOG_FILE_WRITE)
/* Flag for tracking "flavor" (type) of information stored at each byte */
#define H5FD_LOG_FLAVOR     0x0020
/* Flags for tracking total number of reads/writes/seeks */
#define H5FD_LOG_NUM_READ   0x0040
#define H5FD_LOG_NUM_WRITE  0x0080
#define H5FD_LOG_NUM_SEEK   0x0100
#define H5FD_LOG_NUM_IO     (H5FD_LOG_NUM_READ|H5FD_LOG_NUM_WRITE|H5FD_LOG_NUM_SEEK)
/* Flags for tracking time spent in open/read/write/seek/close */
#define H5FD_LOG_TIME_OPEN  0x0200      /* Not implemented yet */
#define H5FD_LOG_TIME_READ  0x0400      /* Not implemented yet */
#define H5FD_LOG_TIME_WRITE 0x0800      /* Partially implemented (need to track total time) */
#define H5FD_LOG_TIME_SEEK  0x1000      /* Partially implemented (need to track total time & track time for seeks during reading) */
#define H5FD_LOG_TIME_CLOSE 0x2000      /* Fully implemented */
#define H5FD_LOG_TIME_IO    (H5FD_LOG_TIME_OPEN|H5FD_LOG_TIME_READ|H5FD_LOG_TIME_WRITE|H5FD_LOG_TIME_SEEK|H5FD_LOG_TIME_CLOSE)
/* Flag for tracking allocation of space in file */
#define H5FD_LOG_ALLOC      0x4000
#define H5FD_LOG_ALL        (H5FD_LOG_ALLOC|H5FD_LOG_TIME_IO|H5FD_LOG_NUM_IO|H5FD_LOG_FLAVOR|H5FD_LOG_FILE_IO|H5FD_LOG_LOC_IO)
