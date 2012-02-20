%include "enums.swg"
%include "henums.i"
%javaconst(1);

/* TODO need to check these typedefs against platform-specific conf. */

/*
 * Status return values.  Failed integer functions in HDF5 result almost
 * always in a negative value (unsigned failing functions sometimes return
 * zero for failure) while successfull return is non-negative (often zero).
 * The negative failure value is most commonly -1, but don't bet on it.  The
 * proper way to detect failure is something like:
 *
 * 	if ((dset = H5Dopen (file, name))<0) {
 *	    fprintf (stderr, "unable to open the requested dataset\n");
 *	}
 */
typedef int herr_t;

/*
 * Boolean type.  Successful return values are zero (false) or positive
 * (true). The typical true value is 1 but don't bet on it.  Boolean
 * functions cannot fail.  Functions that return `htri_t' however return zero
 * (false), positive (true), or negative (failure). The proper way to test
 * for truth from a htri_t function is:
 *
 * 	if ((retval = H5Tcommitted(type))>0) {
 *	    printf("data type is committed\n");
 *	} else if (!retval) {
 * 	    printf("data type is not committed\n");
 *	} else {
 * 	    printf("error determining whether data type is committed\n");
 *	}
 */
typedef unsigned int hbool_t;
typedef int htri_t;

/* From gcc sys/types.h */
typedef long ssize_t;
typedef long time_t;

typedef unsigned long long hsize_t;
typedef long hssize_t;
typedef long haddr_t;

/* Type of atoms to return to users */
typedef int hid_t;

/* Not sure if this is right. */
typedef unsigned long off_t;

/* Common iteration orders */
%rename H5_iter_order_t IteratorOrderType;
typedef enum {
    H5_ITER_UNKNOWN = -1,       /* Unknown order */
    H5_ITER_INC,                /* Increasing order */
    H5_ITER_DEC,                /* Decreasing order */
    H5_ITER_NATIVE,             /* No particular order, whatever is fastest */
    H5_ITER_N		        /* Number of iteration orders */
} H5_iter_order_t;
HENUM_OUTPUT_TYPEMAP(H5_ITERORDER_ENUM, IteratorOrderType);

/*
 * The types of indices on links in groups/attributes on objects.
 * Primarily used for "<do> <foo> by index" routines and for iterating over
 * links in groups/attributes on objects.
 */
%rename H5_index_t IndexType;
typedef enum H5_index_t {
    H5_INDEX_UNKNOWN = -1,	/* Unknown index type			*/
    H5_INDEX_NAME,		/* Index on names 			*/
    H5_INDEX_CRT_ORDER,		/* Index on creation order 		*/
    H5_INDEX_N			/* Number of indices defined 		*/
} H5_index_t;
HENUM_OUTPUT_TYPEMAP(H5_INDEX_ENUM, IndexType);

/*
 * Storage info struct used by H5O_info_t and H5F_info_t
 */
%rename H5_ih_info_t IndexHeapInfo;
typedef struct H5_ih_info_t {
    hsize_t     index_size;     /* btree and/or list */
    hsize_t     heap_size;
} H5_ih_info_t;
