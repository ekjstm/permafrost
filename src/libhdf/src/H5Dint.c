/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright by The HDF Group.                                               *
 * Copyright by the Board of Trustees of the University of Illinois.         *
 * All rights reserved.                                                      *
 *                                                                           *
 * This file is part of HDF5.  The full HDF5 copyright notice, including     *
 * terms governing use, modification, and redistribution, is contained in    *
 * the files COPYING and Copyright.html.  COPYING can be found at the root   *
 * of the source code distribution tree; Copyright.html can be found at the  *
 * root level of an installed copy of the electronic HDF5 document set and   *
 * is linked from the top-level documents page.  It can also be found at     *
 * http://hdfgroup.org/HDF5/doc/Copyright.html.  If you do not have          *
 * access to either file, you may request a copy from help@hdfgroup.org.     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/****************/
/* Module Setup */
/****************/

#define H5D_PACKAGE		/*suppress error about including H5Dpkg	  */

/* Interface initialization */
#define H5_INTERFACE_INIT_FUNC	H5D_init_interface


/***********/
/* Headers */
/***********/
#include "H5private.h"		/* Generic Functions			*/
#include "H5Dpkg.h"		/* Datasets 				*/
#include "H5Eprivate.h"		/* Error handling		  	*/
#include "H5FOprivate.h"        /* File objects                         */
#include "H5HLprivate.h"	/* Local heaps				*/
#include "H5Iprivate.h"		/* IDs			  		*/
#include "H5Lprivate.h"		/* Links		  		*/
#include "H5MMprivate.h"	/* Memory management			*/


/****************/
/* Local Macros */
/****************/


/******************/
/* Local Typedefs */
/******************/

/* Struct for holding callback info during H5D_flush operation */
typedef struct {
    const H5F_t *f;             /* Pointer to file being flushed */
    hid_t dxpl_id;              /* DXPL for I/O operations */
    unsigned flags;             /* Flags for flush operation */
} H5D_flush_ud_t;


/********************/
/* Local Prototypes */
/********************/

/* General stuff */
static herr_t H5D_init_storage(H5D_t *dataset, hbool_t full_overwrite, hid_t dxpl_id);
static H5D_shared_t *H5D_new(hid_t dcpl_id, hbool_t creating, hbool_t vl_type);
static herr_t H5D_init_type(H5F_t *file, const H5D_t *dset, hid_t type_id,
    const H5T_t *type);
static herr_t H5D_init_space(H5F_t *file, const H5D_t *dset, const H5S_t *space);
static herr_t H5D_update_oh_info(H5F_t *file, hid_t dxpl_id, H5D_t *dset);
static herr_t H5D_open_oid(H5D_t *dataset, hid_t dxpl_id);
static herr_t H5D_flush_real(H5D_t *dataset, hid_t dxpl_id, unsigned flags);


/*********************/
/* Package Variables */
/*********************/

/* Define a "default" dataset transfer property list cache structure to use for default DXPLs */
H5D_dxpl_cache_t H5D_def_dxpl_cache;

/* Declare a free list to manage blocks of VL data */
H5FL_BLK_DEFINE(vlen_vl_buf);

/* Declare a free list to manage other blocks of VL data */
H5FL_BLK_DEFINE(vlen_fl_buf);


/*****************************/
/* Library Private Variables */
/*****************************/


/*******************/
/* Local Variables */
/*******************/

/* Declare a free list to manage the H5D_t and H5D_shared_t structs */
H5FL_DEFINE_STATIC(H5D_t);
H5FL_DEFINE_STATIC(H5D_shared_t);

/* Declare the external PQ free list for the sieve buffer information */
H5FL_BLK_EXTERN(sieve_buf);

/* Declare the external free list to manage the H5D_chunk_info_t struct */
H5FL_EXTERN(H5D_chunk_info_t);

/* Define a static "default" dataset structure to use to initialize new datasets */
static H5D_shared_t H5D_def_dset;



/*-------------------------------------------------------------------------
 * Function:	H5D_init
 *
 * Purpose:	Initialize the interface from some other layer.
 *
 * Return:	Success:	non-negative
 *
 *		Failure:	negative
 *
 * Programmer:	Quincey Koziol
 *              Saturday, March 4, 2000
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_init(void)
{
    herr_t ret_value=SUCCEED;   /* Return value */

    FUNC_ENTER_NOAPI(H5D_init, FAIL)
    /* FUNC_ENTER() does all the work */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_init() */


/*--------------------------------------------------------------------------
NAME
   H5D_init_interface -- Initialize interface-specific information
USAGE
    herr_t H5D_init_interface()

RETURNS
    Non-negative on success/Negative on failure
DESCRIPTION
    Initializes any interface-specific data or routines.
NOTES
    Care must be taken when using the H5P functions, since they can cause
    a deadlock in the library when the library is attempting to terminate -QAK

--------------------------------------------------------------------------*/
static herr_t
H5D_init_interface(void)
{
    H5P_genplist_t *def_dcpl;               /* Default Dataset Creation Property list */
    herr_t          ret_value                = SUCCEED;   /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_init_interface)

    /* Initialize the atom group for the dataset IDs */
    if(H5I_register_type(H5I_DATASET, (size_t)H5I_DATASETID_HASHSIZE, H5D_RESERVED_ATOMS, (H5I_free_t)H5D_close)<H5I_FILE)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to initialize interface")

    /* Reset the "default dataset" information */
    HDmemset(&H5D_def_dset, 0, sizeof(H5D_shared_t));

    /* Get the default dataset creation property list values and initialize the
     * default dataset with them.
     */
    if(NULL == (def_dcpl = (H5P_genplist_t *)H5I_object(H5P_LST_DATASET_CREATE_g)))
        HGOTO_ERROR(H5E_DATASET, H5E_BADTYPE, FAIL, "can't get default dataset creation property list")

    /* Get the default data storage method */
    if(H5P_get(def_dcpl, H5D_CRT_LAYOUT_NAME, &H5D_def_dset.layout.type) < 0)
         HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, FAIL, "can't retrieve layout")

    /* Get the default dataset creation properties */
    if(H5P_get(def_dcpl, H5D_CRT_EXT_FILE_LIST_NAME, &H5D_def_dset.dcpl_cache.efl) < 0)
        HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, FAIL, "can't retrieve external file list")
    if(H5P_get(def_dcpl, H5D_CRT_FILL_VALUE_NAME, &H5D_def_dset.dcpl_cache.fill) < 0)
        HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, FAIL, "can't retrieve fill value")
    if(H5P_get(def_dcpl, H5D_CRT_DATA_PIPELINE_NAME, &H5D_def_dset.dcpl_cache.pline) < 0)
        HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, FAIL, "can't retrieve pipeline filter")

    /* Reset the "default DXPL cache" information */
    HDmemset(&H5D_def_dxpl_cache, 0, sizeof(H5D_dxpl_cache_t));

    /* Get the default DXPL cache information */
    if(H5D_get_dxpl_cache_real(H5P_DATASET_XFER_DEFAULT, &H5D_def_dxpl_cache) < 0)
        HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, FAIL, "can't retrieve default DXPL info")

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_init_interface() */


/*-------------------------------------------------------------------------
 * Function:	H5D_term_interface
 *
 * Purpose:	Terminate this interface.
 *
 * Return:	Success:	Positive if anything was done that might
 *				affect other interfaces; zero otherwise.
 *
 * 		Failure:	Negative.
 *
 * Programmer:	Robb Matzke
 *              Friday, November 20, 1998
 *
 *-------------------------------------------------------------------------
 */
int
H5D_term_interface(void)
{
    int		n=0;

    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5D_term_interface)

    if(H5_interface_initialize_g) {
	if((n=H5I_nmembers(H5I_DATASET))>0) {
            /* The dataset API uses the "force" flag set to true because it
             * is using the "file objects" (H5FO) API functions to track open
             * objects in the file.  Using the H5FO code means that dataset
             * IDs can have reference counts >1, when an existing dataset is
             * opened more than once.  However, the H5I code does not attempt
             * to close objects with reference counts>1 unless the "force" flag
             * is set to true.
             *
             * At some point (probably after the group and datatypes use the
             * the H5FO code), the H5FO code might need to be switched around
             * to storing pointers to the objects being tracked (H5D_t, H5G_t,
             * etc) and reference count those itself instead of relying on the
             * reference counting in the H5I layer.  Then, the "force" flag can
             * be put back to false.
             *
             * Setting the "force" flag to true for all the interfaces won't
             * work because the "file driver" (H5FD) APIs use the H5I reference
             * counting to avoid closing a file driver out from underneath an
             * open file...
             *
             * QAK - 5/13/03
             */
	    H5I_clear_type(H5I_DATASET, TRUE);
	} else {
	    H5I_dec_type_ref(H5I_DATASET);
	    H5_interface_initialize_g = 0;
	    n = 1; /*H5I*/
	}
    }
    FUNC_LEAVE_NOAPI(n)
} /* end H5D_term_interface() */


/*-------------------------------------------------------------------------
 * Function:	H5D_create_named
 *
 * Purpose:	Internal routine to create a new dataset.
 *
 * Return:	Success:	Non-NULL, pointer to new dataset object.
 *
 *		Failure:	NULL
 *
 * Programmer:	Quincey Koziol
 *		Thursday, April 5, 2007
 *
 *-------------------------------------------------------------------------
 */
H5D_t *
H5D_create_named(const H5G_loc_t *loc, const char *name, hid_t type_id,
    const H5S_t *space, hid_t lcpl_id, hid_t dcpl_id, hid_t dapl_id,
    hid_t dxpl_id)
{
    H5O_obj_create_t ocrt_info;         /* Information for object creation */
    H5D_obj_create_t dcrt_info;         /* Information for dataset creation */
    H5D_t	   *ret_value;          /* Return value */

    FUNC_ENTER_NOAPI(H5D_create_named, NULL)

    /* Check arguments */
    HDassert(loc);
    HDassert(name && *name);
    HDassert(type_id != H5P_DEFAULT);
    HDassert(space);
    HDassert(lcpl_id != H5P_DEFAULT);
    HDassert(dcpl_id != H5P_DEFAULT);
    HDassert(dapl_id != H5P_DEFAULT);
    HDassert(dxpl_id != H5P_DEFAULT);

    /* Set up dataset creation info */
    dcrt_info.type_id = type_id;
    dcrt_info.space = space;
    dcrt_info.dcpl_id = dcpl_id;

    /* Set up object creation information */
    ocrt_info.obj_type = H5O_TYPE_DATASET;
    ocrt_info.crt_info = &dcrt_info;
    ocrt_info.new_obj = NULL;

    /* Create the new dataset and link it to its parent group */
    if(H5L_link_object(loc, name, &ocrt_info, lcpl_id, dapl_id, dxpl_id) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "unable to create and link to dataset")
    HDassert(ocrt_info.new_obj);

    /* Set the return value */
    ret_value = (H5D_t *)ocrt_info.new_obj;

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_create_named() */


/*-------------------------------------------------------------------------
 * Function:    H5D_get_space_status
 *
 * Purpose:     Returns the status of data space allocation.
 *
 * Return:
 *              Success:        Non-negative
 *
 *              Failture:       Negative
 *
 * Programmer:  Raymond Lu
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_get_space_status(H5D_t *dset, H5D_space_status_t *allocation, hid_t dxpl_id)
{
    H5S_t      *space;              /* Dataset's dataspace */
    hsize_t     space_allocated;    /* The number of bytes allocated for chunks */
    hssize_t    total_elem;         /* The total number of elements in dataspace */
    size_t      type_size;          /* The size of the datatype for the dataset */
    hsize_t     full_size;          /* The number of bytes in the dataset when fully populated */
    herr_t      ret_value = SUCCEED;

    FUNC_ENTER_NOAPI_NOINIT(H5D_get_space_status)

    HDassert(dset);

    /* Get the dataset's dataspace */
    space = dset->shared->space;
    HDassert(space);

    /* Get the total number of elements in dataset's dataspace */
    if((total_elem=H5S_GET_EXTENT_NPOINTS(space)) < 0)
	HGOTO_ERROR(H5E_DATASET, H5E_CANTCOUNT, FAIL, "unable to get # of dataspace elements")

    /* Get the size of the dataset's datatype */
    if((type_size = H5T_get_size(dset->shared->type)) == 0)
	HGOTO_ERROR(H5E_DATASET, H5E_CANTCOUNT, FAIL, "unable to get size of datatype")

    /* Compute the maximum size of the dataset in bytes */
    H5_CHECK_OVERFLOW(total_elem,hssize_t,hsize_t);
    full_size=((hsize_t)total_elem)*type_size;

    /* Difficult to error check, since the error value is 0 and 0 is a valid value... :-/ */
    space_allocated = H5D_get_storage_size(dset, dxpl_id);

    /* Decide on how much of the space is allocated */
    if(space_allocated == 0)
        *allocation = H5D_SPACE_STATUS_NOT_ALLOCATED;
    else if(space_allocated == full_size)
        *allocation = H5D_SPACE_STATUS_ALLOCATED;
    else {
        /* Should only happen for chunked datasets currently */
        HDassert(dset->shared->layout.type == H5D_CHUNKED);

        *allocation = H5D_SPACE_STATUS_PART_ALLOCATED;
    } /* end else */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_get_space_status() */


/*-------------------------------------------------------------------------
 * Function:	H5D_new
 *
 * Purpose:	Creates a new, empty dataset structure
 *
 * Return:	Success:	Pointer to a new dataset descriptor.
 *
 *		Failure:	NULL
 *
 * Programmer:	Quincey Koziol
 *		Monday, October 12, 1998
 *
 *-------------------------------------------------------------------------
 */
static H5D_shared_t *
H5D_new(hid_t dcpl_id, hbool_t creating, hbool_t vl_type)
{
    H5P_genplist_t  *plist;             /* Property list created */
    H5D_shared_t    *new_dset = NULL;   /* New dataset object */
    H5D_shared_t    *ret_value;         /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_new)

    /* Allocate new shared dataset structure */
    if(NULL == (new_dset = H5FL_MALLOC(H5D_shared_t)))
        HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, NULL, "memory allocation failed")

    /* Copy the default dataset information */
    HDmemcpy(new_dset, &H5D_def_dset, sizeof(H5D_shared_t));

    /* If we are using the default dataset creation property list, during creation
     * don't bother to copy it, just increment the reference count
     */
    if(!vl_type && creating && dcpl_id == H5P_DATASET_CREATE_DEFAULT) {
        if(H5I_inc_ref(dcpl_id) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINC, NULL, "Can't increment default DCPL ID")
        new_dset->dcpl_id = dcpl_id;
    } /* end if */
    else {
        /* Get the property list */
        if(NULL == (plist = (H5P_genplist_t *)H5I_object(dcpl_id)))
            HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, NULL, "not a property list")

        new_dset->dcpl_id = H5P_copy_plist(plist);
    } /* end else */

    /* Set return value */
    ret_value = new_dset;

done:
    if(ret_value == NULL)
        if(new_dset != NULL) {
            if(new_dset->dcpl_id != 0)
                (void)H5I_dec_ref(new_dset->dcpl_id);
            H5FL_FREE(H5D_shared_t, new_dset);
        } /* end if */

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_new() */


/*-------------------------------------------------------------------------
 * Function:	H5D_init_type
 *
 * Purpose:	Copy a datatype for a dataset's use, performing all the
 *              necessary adjustments, etc.
 *
 * Return:	Success:    SUCCEED
 *		Failure:    FAIL
 *
 * Programmer:	Quincey Koziol
 *		Thursday, June 24, 2004
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5D_init_type(H5F_t *file, const H5D_t *dset, hid_t type_id, const H5T_t *type)
{
    htri_t relocatable;                 /* Flag whether the type is relocatable */
    htri_t immutable;                   /* Flag whether the type is immutable */
    hbool_t use_latest_format;          /* Flag indicating the newest file format should be used */
    herr_t ret_value = SUCCEED;         /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_init_type)

    /* Sanity checking */
    HDassert(file);
    HDassert(dset);
    HDassert(type);

    /* Check whether the datatype is relocatable */
    if((relocatable = H5T_is_relocatable(type)) < 0)
        HGOTO_ERROR(H5E_DATATYPE, H5E_CANTINIT, FAIL, "can't check datatype?")

    /* Check whether the datatype is immutable */
    if((immutable = H5T_is_immutable(type)) < 0)
        HGOTO_ERROR(H5E_DATATYPE, H5E_CANTINIT, FAIL, "can't check datatype?")

    /* Get the file's 'use the latest version of the format' flag */
    use_latest_format = H5F_USE_LATEST_FORMAT(file);

    /* Copy the datatype if it's a custom datatype or if it'll change when it's location is changed */
    if(!immutable || relocatable || use_latest_format) {
        /* Copy datatype for dataset */
        if((dset->shared->type = H5T_copy(type, H5T_COPY_ALL)) == NULL)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTCOPY, FAIL, "can't copy datatype")

        /* Mark any datatypes as being on disk now */
        if(H5T_set_loc(dset->shared->type, file, H5T_LOC_DISK) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "can't set datatype location")

        /* Set the latest format, if requested */
        if(use_latest_format)
            if(H5T_set_latest_version(dset->shared->type) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set latest version of datatype")

        /* Get a datatype ID for the dataset's datatype */
	if((dset->shared->type_id = H5I_register(H5I_DATATYPE, dset->shared->type)) < 0)
	    HGOTO_ERROR(H5E_DATASET, H5E_CANTREGISTER, FAIL, "unable to register type")
    } /* end if */
    /* Not a custom datatype, just use it directly */
    else {
        if(H5I_inc_ref(type_id) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINC, FAIL, "Can't increment datatype ID")

        /* Use existing datatype */
        dset->shared->type_id = type_id;
        dset->shared->type = (H5T_t *)type; /* (Cast away const OK - QAK) */
    } /* end else */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_init_type() */


/*-------------------------------------------------------------------------
 * Function:	H5D_init_space
 *
 * Purpose:	Copy a dataspace for a dataset's use, performing all the
 *              necessary adjustments, etc.
 *
 * Return:	Success:    SUCCEED
 *		Failure:    FAIL
 *
 * Programmer:	Quincey Koziol
 *		Tuesday, July 24, 2007
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5D_init_space(H5F_t *file, const H5D_t *dset, const H5S_t *space)
{
    hbool_t use_latest_format;          /* Flag indicating the newest file format should be used */
    herr_t ret_value = SUCCEED;         /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_init_space)

    /* Sanity checking */
    HDassert(file);
    HDassert(dset);
    HDassert(space);

    /* Get the file's 'use the latest version of the format' flag */
    use_latest_format = H5F_USE_LATEST_FORMAT(file);

    /* Copy dataspace for dataset */
    if(NULL == (dset->shared->space = H5S_copy(space, FALSE, TRUE)))
        HGOTO_ERROR(H5E_DATASET, H5E_CANTCOPY, FAIL, "can't copy dataspace")

    /* Set the latest format, if requested */
    if(use_latest_format)
        if(H5S_set_latest_version(dset->shared->space) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set latest version of datatype")

    /* Set the dataset's dataspace to 'all' selection */
    if(H5S_select_all(dset->shared->space, TRUE) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "unable to set all selection")

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_init_space() */


/*-------------------------------------------------------------------------
 * Function:	H5D_update_oh_info
 *
 * Purpose:	Create and fill object header for dataset
 *
 * Return:	Success:    SUCCEED
 *		Failure:    FAIL
 *
 * Programmer:	Bill Wendling
 *		Thursday, October 31, 2002
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5D_update_oh_info(H5F_t *file, hid_t dxpl_id, H5D_t *dset)
{
    H5O_t              *oh = NULL;      /* Pointer to dataset's object header */
    size_t              ohdr_size = H5D_MINHDR_SIZE;    /* Size of dataset's object header */
    H5O_loc_t          *oloc = NULL;    /* Dataset's object location */
    H5O_layout_t       *layout;         /* Dataset's layout information */
    H5T_t              *type;           /* Dataset's datatype */
    hbool_t             use_latest_format;      /* Flag indicating the newest file format should be used */
    H5O_fill_t		*fill_prop;     /* Pointer to dataset's fill value information */
    H5D_fill_value_t	fill_status;    /* Fill value status */
    hbool_t             fill_changed = FALSE;      /* Flag indicating the fill value was changed */
    herr_t ret_value = SUCCEED;         /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_update_oh_info)

    /* Sanity checking */
    HDassert(file);
    HDassert(dset);

    /* Set some local variables, for convenience */
    oloc = &dset->oloc;
    layout = &dset->shared->layout;
    type = dset->shared->type;
    fill_prop = &dset->shared->dcpl_cache.fill;

    /* Get the file's 'use the latest version of the format' flag */
    use_latest_format = H5F_USE_LATEST_FORMAT(file);

    /* Retrieve "defined" status of fill value */
    if(H5P_is_fill_value_defined(fill_prop, &fill_status) < 0)
        HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, FAIL, "can't tell if fill value defined")

    /* Special case handling for variable-length types */
    if(H5T_detect_class(type, H5T_VLEN)) {
        /* If the default fill value is chosen for variable-length types, always write it */
        if(fill_prop->fill_time == H5D_FILL_TIME_IFSET && fill_status == H5D_FILL_VALUE_DEFAULT) {
            /* Update dataset creation property */
            fill_prop->fill_time = H5D_FILL_TIME_ALLOC;

            /* Note that the fill value changed */
            fill_changed = TRUE;
        } /* end if */

        /* Don't allow never writing fill values with variable-length types */
        if(fill_prop->fill_time == H5D_FILL_TIME_NEVER)
            HGOTO_ERROR(H5E_DATASET, H5E_UNSUPPORTED, FAIL, "Dataset doesn't support VL datatype when fill value is not defined")
    } /* end if */

    /* Determine whether fill value is defined or not */
    if(fill_status == H5D_FILL_VALUE_DEFAULT || fill_status == H5D_FILL_VALUE_USER_DEFINED) {
        /* Convert fill value buffer to dataset's datatype */
        if(fill_prop->buf && fill_prop->size > 0 && H5O_fill_convert(fill_prop, type, &fill_changed, dxpl_id) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to convert fill value to dataset type")

	fill_prop->fill_defined = TRUE;
    } else if(fill_status == H5D_FILL_VALUE_UNDEFINED) {
 	fill_prop->fill_defined = FALSE;
    } else
        HGOTO_ERROR(H5E_DATASET, H5E_CANTGET, FAIL, "unable to determine if fill value is defined")

    /* Check for invalid fill & allocation time setting */
    if(fill_prop->fill_defined == FALSE && fill_prop->fill_time == H5D_FILL_TIME_ALLOC)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "fill value writing on allocation set, but no fill value defined")

    /* Check if the fill value info changed */
    if(fill_changed) {
        H5P_genplist_t     *dc_plist;               /* Dataset's creation property list */

        /* Get dataset's property list object */
        HDassert(dset->shared->dcpl_id != H5P_DATASET_CREATE_DEFAULT);
        if(NULL == (dc_plist = (H5P_genplist_t *)H5I_object(dset->shared->dcpl_id)))
            HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "can't get dataset creation property list")

        /* Update dataset creation property */
        if(H5P_set(dc_plist, H5D_CRT_FILL_VALUE_NAME, fill_prop) < 0)
            HGOTO_ERROR(H5E_PLIST, H5E_CANTSET, FAIL, "can't set fill value info")
    } /* end if */

    /* Add the dataset's raw data size to the size of the header, if the raw data will be stored as compact */
    if(layout->type == H5D_COMPACT)
        ohdr_size += layout->u.compact.size;

    /* Create an object header for the dataset */
    if(H5O_create(file, dxpl_id, ohdr_size, dset->shared->dcpl_id, oloc/*out*/) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to create dataset object header")

    /* Get a pointer to the object header itself */
    if((oh = H5O_protect(oloc, dxpl_id)) == NULL)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTPROTECT, FAIL, "unable to protect dataset object header")

    /* Write new fill value message */
    if(H5O_msg_append_oh(file, dxpl_id, oh, H5O_FILL_NEW_ID, H5O_MSG_FLAG_CONSTANT, 0, fill_prop) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to update new fill value header message")

    /* If there is valid information for the old fill value struct, add it */
    /* (only if we aren't trying to write the latest version of the file format) */
    if(fill_prop->buf && !use_latest_format) {
        H5O_fill_t old_fill_prop;       /* Copy of fill value property, for writing as "old" fill value */

        /* Shallow copy the fill value property */
        /* (we only want to make certain that the shared component isnt' modified) */
        HDmemcpy(&old_fill_prop, fill_prop, sizeof(old_fill_prop));

        /* Reset shared component info */
        H5O_msg_reset_share(H5O_FILL_ID, &old_fill_prop);

        /* Write old fill value */
        if(H5O_msg_append_oh(file, dxpl_id, oh, H5O_FILL_ID, H5O_MSG_FLAG_CONSTANT, 0, &old_fill_prop) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to update old fill value header message")
    } /* end if */

    /* Update the datatype and dataspace header messages */
    if(H5O_msg_append_oh(file, dxpl_id, oh, H5O_DTYPE_ID, H5O_MSG_FLAG_CONSTANT, 0, type) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to update datatype header message")
    if(H5S_append(file, dxpl_id, oh, dset->shared->space) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to update dataspace header message")

    /* Update the filters message, if this is a chunked dataset */
    if(layout->type == H5D_CHUNKED) {
        H5O_pline_t     *pline;         /* Dataset's I/O pipeline information */

        pline = &dset->shared->dcpl_cache.pline;
        if(pline->nused > 0 && H5O_msg_append_oh(file, dxpl_id, oh, H5O_PLINE_ID, H5O_MSG_FLAG_CONSTANT, 0, pline) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to update filter header message")
    } /* end if */

    /*
     * Allocate storage if space allocate time is early; otherwise delay
     * allocation until later.
     */
    if(fill_prop->alloc_time == H5D_ALLOC_TIME_EARLY)
        if(H5D_alloc_storage(file, dxpl_id, dset, H5D_ALLOC_CREATE, FALSE) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to initialize storage")

    /* Update external storage message, if it's used */
    if(dset->shared->dcpl_cache.efl.nused > 0) {
        H5O_efl_t *efl = &dset->shared->dcpl_cache.efl; /* Dataset's external file list */
        H5HL_t *heap;                           /* Pointer to local heap for EFL file names */
        size_t heap_size = H5HL_ALIGN(1);
        size_t u;

        /* Determine size of heap needed to stored the file names */
        for(u = 0; u < efl->nused; ++u)
            heap_size += H5HL_ALIGN(HDstrlen(efl->slot[u].name) + 1);

        /* Create the heap for the EFL file names */
        if(H5HL_create(file, dxpl_id, heap_size, &efl->heap_addr/*out*/) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to create EFL file name heap")

        /* Pin the heap down in memory */
        if(NULL == (heap = H5HL_protect(file, dxpl_id, efl->heap_addr, H5AC_WRITE)))
            HGOTO_ERROR(H5E_DATASET, H5E_CANTPROTECT, FAIL, "unable to protect EFL file name heap")

        /* Insert "empty" name first */
        if((size_t)(-1) == H5HL_insert(file, dxpl_id, heap, (size_t)1, "")) {
            H5HL_unprotect(file, dxpl_id, heap, efl->heap_addr);
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINSERT, FAIL, "unable to insert file name into heap")
        } /* end if */

        for(u = 0; u < efl->nused; ++u) {
            size_t offset;      /* Offset of file name in heap */

            /* Insert file name into heap */
            if((size_t)(-1) == (offset = H5HL_insert(file, dxpl_id, heap,
                        HDstrlen(efl->slot[u].name) + 1, efl->slot[u].name))) {
                H5HL_unprotect(file, dxpl_id, heap, efl->heap_addr);
                HGOTO_ERROR(H5E_DATASET, H5E_CANTINSERT, FAIL, "unable to insert file name into heap")
            } /* end if */

            /* Store EFL file name offset */
            HDassert(0 == efl->slot[u].name_offset);
            efl->slot[u].name_offset = offset;
        } /* end for */

        /* Release the heap */
        if(H5HL_unprotect(file, dxpl_id, heap, efl->heap_addr) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTUNPROTECT, FAIL, "unable to unprotect EFL file name heap")
        heap = NULL;

        /* Insert EFL message into dataset object header */
        if(H5O_msg_append_oh(file, dxpl_id, oh, H5O_EFL_ID, H5O_MSG_FLAG_CONSTANT, 0, efl) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to update external file list message")
    } /* end if */

    /* Create layout message */
    /* (Don't make layout message constant unless allocation time is early, since space may not be allocated) */
    /* (Note: this is relying on H5D_alloc_storage not calling H5O_msg_write during dataset creation) */
    if(H5O_msg_append_oh(file, dxpl_id, oh, H5O_LAYOUT_ID, ((fill_prop->alloc_time == H5D_ALLOC_TIME_EARLY && H5D_COMPACT != layout->type) ? H5O_MSG_FLAG_CONSTANT : 0), 0, layout) < 0)
         HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to update layout")

#ifdef H5O_ENABLE_BOGUS
{
    H5P_genplist_t     *dc_plist;               /* Dataset's creation property list */

    /* Get dataset's property list object */
    if(NULL == (dc_plist = (H5P_genplist_t *)H5I_object(dset->shared->dcpl_id)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "can't get dataset creation property list")

    /* Check whether to add a "bogus" message */
    if(H5P_exist_plist(dc_plist, H5O_BOGUS_MSG_FLAGS_NAME) > 0) {
        uint8_t bogus_flags = 0;        /* Flags for creating "bogus" message */

        /* Retrieve "bogus" message flags */
        if(H5P_get(dc_plist, H5O_BOGUS_MSG_FLAGS_NAME, &bogus_flags) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTGET, FAIL, "can't get bogus message options")

        /* Add a "bogus" message (for error testing). */
        if(H5O_bogus_oh(file, dxpl_id, oh, (unsigned)bogus_flags) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to create 'bogus' message")
    } /* end if */
}
#endif /* H5O_ENABLE_BOGUS */

    /* Add a modification time message, if using older format. */
    if(!use_latest_format)
        if(H5O_touch_oh(file, dxpl_id, oh, TRUE) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to update modification time message")

done:
    /* Release pointer to object header itself */
    if(oloc != NULL && oh != NULL)
        if(H5O_unprotect(oloc, oh) < 0)
            HDONE_ERROR(H5E_DATASET, H5E_CANTUNPROTECT, FAIL, "unable to unprotect dataset object header")

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_update_oh_info() */


/*-------------------------------------------------------------------------
 * Function:	H5D_create
 *
 * Purpose:	Creates a new dataset with name NAME in file F and associates
 *		with it a datatype TYPE for each element as stored in the
 *		file, dimensionality information or dataspace SPACE, and
 *		other miscellaneous properties CREATE_PARMS.  All arguments
 *		are deep-copied before being associated with the new dataset,
 *		so the caller is free to subsequently modify them without
 *		affecting the dataset.
 *
 * Return:	Success:	Pointer to a new dataset
 *
 *		Failure:	NULL
 *
 * Programmer:	Robb Matzke
 *		Thursday, December  4, 1997
 *
 *-------------------------------------------------------------------------
 */
H5D_t *
H5D_create(H5F_t *file, hid_t type_id, const H5S_t *space, hid_t dcpl_id,
    hid_t dxpl_id)
{
    const H5T_t         *type;                  /* Datatype for dataset */
    H5D_t		*new_dset = NULL;
    int		        i, ndims;
    unsigned            chunk_ndims = 0;        /* Dimensionality of chunk */
    H5P_genplist_t 	*dc_plist = NULL;       /* New Property list */
    hbool_t             has_vl_type = FALSE;    /* Flag to indicate a VL-type for dataset */
    hbool_t             chunk_init = FALSE;     /* Flag to indicate that chunk information was initialized */
    H5G_loc_t           dset_loc;               /* Dataset location */
    unsigned		u;                      /* Local index variable */
    H5D_t		*ret_value;             /* Return value */

    FUNC_ENTER_NOAPI(H5D_create, NULL)

    /* check args */
    HDassert(file);
    HDassert(H5I_DATATYPE==H5I_get_type(type_id));
    HDassert(space);
    HDassert(H5I_GENPROP_LST==H5I_get_type(dcpl_id));
    HDassert(H5I_GENPROP_LST==H5I_get_type(dxpl_id));

    /* Get the dataset's datatype */
    if(NULL == (type = (const H5T_t *)H5I_object(type_id)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, NULL, "not a datatype")

    /* Check if the datatype is "sensible" for use in a dataset */
    if(H5T_is_sensible(type) != TRUE)
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, NULL, "datatype is not sensible")

    /* Check if the datatype is/contains a VL-type */
    if(H5T_detect_class(type, H5T_VLEN))
        has_vl_type=TRUE;

    /* Check if the dataspace has an extent set (or is NULL) */
    if(!(H5S_has_extent(space)) )
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, NULL, "dataspace extent has not been set.")

    /* Initialize the dataset object */
    if(NULL == (new_dset = H5FL_CALLOC(H5D_t)))
        HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, NULL, "memory allocation failed")

    /* Set up & reset dataset location */
    dset_loc.oloc = &(new_dset->oloc);
    dset_loc.path = &(new_dset->path);
    H5G_loc_reset(&dset_loc);

    /* Initialize the shared dataset space */
    if(NULL == (new_dset->shared = H5D_new(dcpl_id, TRUE, has_vl_type)))
        HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, NULL, "memory allocation failed")

    /* Copy & initialize datatype for dataset */
    if(H5D_init_type(file, new_dset, type_id, type) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "can't copy datatype")

    /* Copy & initialize dataspace for dataset */
    if(H5D_init_space(file, new_dset, space) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "can't copy dataspace")

    /* Set the dataset's checked_filters flag to enable writing */
    new_dset->shared->checked_filters = TRUE;

    /* Check if the dataset has a non-default DCPL & get important values, if so */
    if(new_dset->shared->dcpl_id != H5P_DATASET_CREATE_DEFAULT) {
        H5D_layout_t    *layout;        /* Dataset's layout information */
        H5O_pline_t     *pline;         /* Dataset's I/O pipeline information */
        H5O_fill_t      *fill;          /* Dataset's fill value info */

        /* Check if the filters in the DCPL can be applied to this dataset */
        if(H5Z_can_apply(new_dset->shared->dcpl_id, new_dset->shared->type_id) < 0)
            HGOTO_ERROR(H5E_ARGS, H5E_CANTINIT, NULL, "I/O filters can't operate on this dataset")

        /* Make the "set local" filter callbacks for this dataset */
        if(H5Z_set_local(new_dset->shared->dcpl_id, new_dset->shared->type_id) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "unable to set local filter parameters")

        /* Get new dataset's property list object */
        if(NULL == (dc_plist = (H5P_genplist_t *)H5I_object(new_dset->shared->dcpl_id)))
            HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, NULL, "can't get dataset creation property list")

        /* Retrieve the properties we need */
        pline = &new_dset->shared->dcpl_cache.pline;
        if(H5P_get(dc_plist, H5D_CRT_DATA_PIPELINE_NAME, pline) < 0)
            HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, NULL, "can't retrieve pipeline filter")
        layout = &new_dset->shared->layout.type;
        if(H5P_get(dc_plist, H5D_CRT_LAYOUT_NAME, layout) < 0)
            HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, NULL, "can't retrieve layout")
        if(pline->nused > 0 && H5D_CHUNKED != *layout)
            HGOTO_ERROR(H5E_DATASET, H5E_BADVALUE, NULL, "filters can only be used with chunked layout")
        fill = &new_dset->shared->dcpl_cache.fill;
        if(H5P_get(dc_plist, H5D_CRT_FILL_VALUE_NAME, fill) < 0)
            HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, NULL, "can't retrieve fill value info")

        /* Check if the alloc_time is the default and error out */
        if(fill->alloc_time == H5D_ALLOC_TIME_DEFAULT)
            HGOTO_ERROR(H5E_DATASET, H5E_BADVALUE, NULL, "invalid space allocation state")

        /* Don't allow compact datasets to allocate space later */
        if(*layout == H5D_COMPACT && fill->alloc_time != H5D_ALLOC_TIME_EARLY)
            HGOTO_ERROR(H5E_DATASET, H5E_BADVALUE, NULL, "compact dataset must have early space allocation")

        /* If MPI VFD is used, no filter support yet. */
        if(IS_H5FD_MPI(file) && pline->nused > 0)
            HGOTO_ERROR(H5E_DATASET, H5E_UNSUPPORTED, NULL, "Parallel I/O does not support filters yet")

        /* Chunked datasets are non-default, so retrieve their info here */
        if(H5P_get(dc_plist, H5D_CRT_CHUNK_DIM_NAME, &chunk_ndims) < 0)
            HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, NULL, "can't retrieve chunk dimensions")

        /* Get the dataset's external file list information */
        if(H5P_get(dc_plist, H5D_CRT_EXT_FILE_LIST_NAME, &new_dset->shared->dcpl_cache.efl) < 0)
            HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, NULL, "can't retrieve external file list")
    } /* end if */

    /* Set the latest version of the pline & fill messages, if requested */
    if(H5F_USE_LATEST_FORMAT(file)) {
        /* Set the latest version for the I/O pipeline message */
        if(H5Z_set_latest_version(&new_dset->shared->dcpl_cache.pline) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, NULL, "can't set latest version of I/O filter pipeline")

        /* Set the latest version for the fill value message */
        if(H5O_fill_set_latest_version(&new_dset->shared->dcpl_cache.fill) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, NULL, "can't set latest version of fill value")
    } /* end if */

    /* Check if this dataset is going into a parallel file and set space allocation time */
    if(IS_H5FD_MPI(file))
        new_dset->shared->dcpl_cache.fill.alloc_time = H5D_ALLOC_TIME_EARLY;

    switch(new_dset->shared->layout.type) {
        case H5D_CONTIGUOUS:
            {
                hssize_t tmp_size;                      /* Temporary holder for raw data size */
                hsize_t	dim[H5O_LAYOUT_NDIMS];	        /* Current size of data in elements */
                hsize_t	max_dim[H5O_LAYOUT_NDIMS];      /* Maximum size of data in elements */

                /*
                 * The maximum size of the dataset cannot exceed the storage size.
                 * Also, only the slowest varying dimension of a simple data space
                 * can be extendible (currently only for external data storage).
                 */
                new_dset->shared->layout.u.contig.addr = HADDR_UNDEF;        /* Initialize to no address */

                if((ndims = H5S_get_simple_extent_dims(new_dset->shared->space, dim, max_dim)) < 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "unable to initialize contiguous storage")
                for(i = 1; i < ndims; i++)
                    if(max_dim[i] > dim[i])
                        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "only the first dimension can be extendible")
                if(new_dset->shared->dcpl_cache.efl.nused > 0) {
                    hsize_t max_points = H5S_get_npoints_max(new_dset->shared->space);
                    hsize_t max_storage = H5O_efl_total_size(&new_dset->shared->dcpl_cache.efl);

                    if(H5S_UNLIMITED == max_points) {
                        if(H5O_EFL_UNLIMITED != max_storage)
                            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "unlimited data space but finite storage")
                    } else if(max_points * H5T_get_size(type) < max_points) {
                        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "data space * type size overflowed")
                    } else if(max_points * H5T_get_size(type) > max_storage) {
                        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "data space size exceeds external storage size")
                    }

                    /* Set the I/O functions for this layout type */
                    new_dset->shared->io_ops.readvv = H5D_efl_readvv;
                    new_dset->shared->io_ops.writevv = H5D_efl_writevv;
                } /* end if */
                else {
                    if(ndims > 0 && max_dim[0] > dim[0])
                        HGOTO_ERROR(H5E_DATASET, H5E_UNSUPPORTED, NULL, "extendible contiguous non-external dataset")

                    /* Set the I/O functions for this layout type */
                    new_dset->shared->io_ops.readvv = H5D_contig_readvv;
                    new_dset->shared->io_ops.writevv = H5D_contig_writevv;
                } /* end else */

                /* Compute the total size of a chunk */
                tmp_size = H5S_GET_EXTENT_NPOINTS(new_dset->shared->space) * H5T_get_size(new_dset->shared->type);
                H5_ASSIGN_OVERFLOW(new_dset->shared->layout.u.contig.size, tmp_size, hssize_t, hsize_t);

                /* Get the sieve buffer size for this dataset */
                new_dset->shared->cache.contig.sieve_buf_size = H5F_SIEVE_BUF_SIZE(file);
            } /* end case */
            break;

        case H5D_CHUNKED:
            {
                hsize_t	max_dim[H5O_LAYOUT_NDIMS];      /* Maximum size of data in elements */

                /* Set up layout information */
                if((ndims = H5S_GET_EXTENT_NDIMS(new_dset->shared->space)) < 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTGET, NULL, "unable to get rank")
                new_dset->shared->layout.u.chunk.ndims = (unsigned)ndims + 1;
                HDassert((unsigned)(new_dset->shared->layout.u.chunk.ndims) <= NELMTS(new_dset->shared->layout.u.chunk.dim));

                /* Initialize to no address */
                new_dset->shared->layout.u.chunk.addr = HADDR_UNDEF;

                /*
                 * Chunked storage allows any type of data space extension, so we
                 * don't even bother checking.
                 */
                if(chunk_ndims != (unsigned)ndims)
                    HGOTO_ERROR(H5E_DATASET, H5E_BADVALUE, NULL, "dimensionality of chunks doesn't match the data space")
                if(new_dset->shared->dcpl_cache.efl.nused > 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_BADVALUE, NULL, "external storage not supported with chunked layout")

                /*
                 * The chunk size of a dimension with a fixed size cannot exceed
                 * the maximum dimension size
                 */
                if(H5P_get(dc_plist, H5D_CRT_CHUNK_SIZE_NAME, new_dset->shared->layout.u.chunk.dim) < 0)
                    HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, NULL, "can't retrieve chunk size")
                new_dset->shared->layout.u.chunk.dim[new_dset->shared->layout.u.chunk.ndims-1] = H5T_get_size(new_dset->shared->type);

                if(H5S_get_simple_extent_dims(new_dset->shared->space, NULL, max_dim) < 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "unable to query maximum dimensions")
                for(u = 0; u < new_dset->shared->layout.u.chunk.ndims - 1; u++)
                    if(max_dim[u] != H5S_UNLIMITED && max_dim[u] < new_dset->shared->layout.u.chunk.dim[u])
                        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "chunk size must be <= maximum dimension size for fixed-sized dimensions")

                /* Compute the total size of a chunk */
                for(u = 1, new_dset->shared->layout.u.chunk.size = new_dset->shared->layout.u.chunk.dim[0]; u < new_dset->shared->layout.u.chunk.ndims; u++)
                    new_dset->shared->layout.u.chunk.size *= new_dset->shared->layout.u.chunk.dim[u];

                /* Set the I/O functions for this layout type */
                new_dset->shared->io_ops.readvv = H5D_istore_readvv;
                new_dset->shared->io_ops.writevv = H5D_istore_writevv;

                /* Initialize the chunk cache for the dataset */
                if(H5D_istore_init(file, new_dset) < 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "can't initialize chunk cache")

                /* Indicate that the chunk information was initialized */
                chunk_init = TRUE;
            } /* end case */
            break;

        case H5D_COMPACT:
            {
                hssize_t tmp_size;      /* Temporary holder for raw data size */
                hsize_t	comp_data_size;

                /*
                 * Compact dataset is stored in dataset object header message of
                 * layout.
                 */
                tmp_size = H5S_GET_EXTENT_NPOINTS(space) * H5T_get_size(new_dset->shared->type);
                H5_ASSIGN_OVERFLOW(new_dset->shared->layout.u.compact.size, tmp_size, hssize_t, size_t);

                /* Verify data size is smaller than maximum header message size
                 * (64KB) minus other layout message fields.
                 */
                comp_data_size = H5O_MESG_MAX_SIZE - H5O_layout_meta_size(file, &(new_dset->shared->layout));
                if(new_dset->shared->layout.u.compact.size > comp_data_size)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "compact dataset size is bigger than header message maximum size")

                /* Set the I/O functions for this layout type */
                new_dset->shared->io_ops.readvv = H5D_compact_readvv;
                new_dset->shared->io_ops.writevv = H5D_compact_writevv;
            } /* end case */
            break;

        default:
            HGOTO_ERROR(H5E_DATASET, H5E_UNSUPPORTED, NULL, "not implemented yet")
    } /* end switch */ /*lint !e788 All appropriate cases are covered */

    /* Update the dataset's object header info. */
    if(H5D_update_oh_info(file, dxpl_id, new_dset) != SUCCEED)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, NULL, "can't update the metadata cache")

    /* Add the dataset to the list of opened objects in the file */
    if(H5FO_top_incr(new_dset->oloc.file, new_dset->oloc.addr) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINC, NULL, "can't incr object ref. count")
    if(H5FO_insert(new_dset->oloc.file, new_dset->oloc.addr, new_dset->shared, TRUE) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINSERT, NULL, "can't insert dataset into list of open objects")    

    new_dset->shared->fo_count = 1;

    /* Success */
    ret_value = new_dset;

done:
    if(!ret_value && new_dset && new_dset->shared) {
        if(new_dset->shared) {
            if(new_dset->shared->layout.type == H5D_CHUNKED && chunk_init) {
                if(H5D_istore_dest(new_dset,H5AC_dxpl_id) < 0)
                    HDONE_ERROR(H5E_DATASET, H5E_CANTRELEASE, NULL, "unable to destroy chunk cache")
            } /* end if */
            if(new_dset->shared->space) {
                if(H5S_close(new_dset->shared->space) < 0)
                    HDONE_ERROR(H5E_DATASET, H5E_CLOSEERROR, NULL, "unable to release dataspace")
            } /* end if */
            if(new_dset->shared->type) {
                if(H5I_dec_ref(new_dset->shared->type_id) < 0)
                    HDONE_ERROR(H5E_DATASET, H5E_CLOSEERROR, NULL, "unable to release datatype")
            } /* end if */
            if(H5F_addr_defined(new_dset->oloc.addr)) {
                if(H5O_close(&(new_dset->oloc)) < 0)
                    HDONE_ERROR(H5E_DATASET, H5E_CLOSEERROR, NULL, "unable to release object header")
                if(file) {
                    if(H5O_delete(file, dxpl_id, new_dset->oloc.addr) < 0)
                        HDONE_ERROR(H5E_DATASET, H5E_CANTDELETE, NULL, "unable to delete object header")
                } /* end if */
            } /* end if */
            if(new_dset->shared->dcpl_id != 0) {
                if(H5I_dec_ref(new_dset->shared->dcpl_id) < 0)
                    HDONE_ERROR(H5E_DATASET, H5E_CANTDEC, NULL, "unable to decrement ref count on property list")
            } /* end if */
            H5FL_FREE(H5D_shared_t, new_dset->shared);
        } /* end if */
        new_dset->oloc.file = NULL;
        H5FL_FREE(H5D_t, new_dset);
    } /* end if */

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_create() */


/*
 *-------------------------------------------------------------------------
 * Function:	H5D_open
 *
 * Purpose:	Checks if dataset is already open, or opens a dataset for
 *              access.
 *
 * Return:	Success:	Dataset ID
 *		Failure:	FAIL
 *
 * Programmer:	Quincey Koziol
 *		Friday, December 20, 2002
 *
 *-------------------------------------------------------------------------
 */
H5D_t *
H5D_open(const H5G_loc_t *loc, hid_t dxpl_id)
{
    H5D_shared_t    *shared_fo = NULL;
    H5D_t           *dataset = NULL;
    H5D_t           *ret_value;              /* Return value */

    FUNC_ENTER_NOAPI(H5D_open, NULL)

    /* check args */
    HDassert(loc);

    /* Allocate the dataset structure */
    if(NULL == (dataset = H5FL_CALLOC(H5D_t)))
        HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, NULL, "memory allocation failed")

    /* Shallow copy (take ownership) of the object location object */
    if(H5O_loc_copy(&(dataset->oloc), loc->oloc, H5_COPY_SHALLOW) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTCOPY, NULL, "can't copy object location")

    /* Shallow copy (take ownership) of the group hier. path */
    if(H5G_name_copy(&(dataset->path), loc->path, H5_COPY_SHALLOW) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTCOPY, NULL, "can't copy path")

    /* Check if dataset was already open */
    if((shared_fo = (H5D_shared_t *)H5FO_opened(dataset->oloc.file, dataset->oloc.addr)) == NULL) {
        /* Clear any errors from H5FO_opened() */
        H5E_clear_stack(NULL);

        /* Open the dataset object */
        if(H5D_open_oid(dataset, dxpl_id) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_NOTFOUND, NULL, "not found")

        /* Add the dataset to the list of opened objects in the file */
        if(H5FO_insert(dataset->oloc.file, dataset->oloc.addr, dataset->shared, FALSE) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINSERT, NULL, "can't insert dataset into list of open objects")

        /* Increment object count for the object in the top file */
        if(H5FO_top_incr(dataset->oloc.file, dataset->oloc.addr) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINC, NULL, "can't increment object count")

        /* We're the first dataset to use the the shared info */
        dataset->shared->fo_count = 1;
    } /* end if */
    else {
        /* Point to shared info */
        dataset->shared = shared_fo;

        /* Increment # of datasets using shared information */
        shared_fo->fo_count++;

        /* Check if the object has been opened through the top file yet */
        if(H5FO_top_count(dataset->oloc.file, dataset->oloc.addr) == 0) {
            /* Open the object through this top file */
            if(H5O_open(&(dataset->oloc)) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_CANTOPENOBJ, NULL, "unable to open object header")
        } /* end if */

        /* Increment object count for the object in the top file */
        if(H5FO_top_incr(dataset->oloc.file, dataset->oloc.addr) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINC, NULL, "can't increment object count")
    } /* end else */

    ret_value = dataset;

done:
    if(ret_value == NULL) {
        /* Free the location--casting away const*/
        if(dataset) {
            if(shared_fo == NULL)   /* Need to free shared fo */
                H5FL_FREE(H5D_shared_t, dataset->shared);

            H5O_loc_free(&(dataset->oloc));
            H5G_name_free(&(dataset->path));

            H5FL_FREE(H5D_t, dataset);
        }
        if(shared_fo)
            shared_fo->fo_count--;
    } /* end if */

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_open() */


/*-------------------------------------------------------------------------
 * Function:	H5D_open_oid
 *
 * Purpose:	Opens a dataset for access.
 *
 * Return:	Dataset pointer on success, NULL on failure
 *
 * Programmer:	Quincey Koziol
 *		Monday, October 12, 1998
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5D_open_oid(H5D_t *dataset, hid_t dxpl_id)
{
    H5P_genplist_t *plist;              /* Property list */
    H5O_pline_t  *pline;                /* I/O pipeline information */
    H5O_fill_t *fill_prop;              /* Pointer to dataset's fill value info */
    unsigned alloc_time_state;          /* Allocation time state */
    herr_t ret_value = SUCCEED;		/* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_open_oid)

    /* check args */
    HDassert(dataset);

    /* (Set the 'vl_type' parameter to FALSE since it doesn't matter from here) */
    if(NULL == (dataset->shared = H5D_new(H5P_DATASET_CREATE_DEFAULT, FALSE, FALSE)))
        HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, FAIL, "memory allocation failed")

    /* Open the dataset object */
    if(H5O_open(&(dataset->oloc)) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTOPENOBJ, FAIL, "unable to open")

    /* Get the type and space */
    if(NULL == (dataset->shared->type = (H5T_t *)H5O_msg_read(&(dataset->oloc), H5O_DTYPE_ID, NULL, dxpl_id)))
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to load type info from dataset header")
    if(NULL == (dataset->shared->space = H5S_read(&(dataset->oloc), dxpl_id)))
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to load space info from dataset header")

    /* Get a datatype ID for the dataset's datatype */
    if((dataset->shared->type_id = H5I_register(H5I_DATATYPE, dataset->shared->type)) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTREGISTER, FAIL, "unable to register type")

    /* Get dataset creation property list object */
    if(NULL == (plist = (H5P_genplist_t *)H5I_object(dataset->shared->dcpl_id)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "can't get dataset creation property list")

    /* Get the optional filters message */
    pline = &dataset->shared->dcpl_cache.pline;
    if(NULL != H5O_msg_read(&(dataset->oloc), H5O_PLINE_ID, pline, dxpl_id)) {
        if(H5P_set(plist, H5D_CRT_DATA_PIPELINE_NAME, pline) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set pipeline")
    } /* end if */
    else
        H5E_clear_stack(NULL);

    /*
     * Get the raw data layout info.  It's actually stored in two locations:
     * the storage message of the dataset (dataset->storage) and certain
     * values are copied to the dataset create plist so the user can query
     * them.
     */
    if(NULL == H5O_msg_read(&(dataset->oloc), H5O_LAYOUT_ID, &(dataset->shared->layout), dxpl_id))
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to read data layout message")
    if(H5P_set(plist, H5D_CRT_LAYOUT_NAME, &dataset->shared->layout.type) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set layout")
    switch(dataset->shared->layout.type) {
        case H5D_CONTIGUOUS:
            /* Compute the size of the contiguous storage for versions of the
             * layout message less than version 3 because versions 1 & 2 would
             * truncate the dimension sizes to 32-bits of information. - QAK 5/26/04
             */
            if(dataset->shared->layout.version < 3) {
                hssize_t tmp_size;                      /* Temporary holder for raw data size */

                tmp_size = H5S_GET_EXTENT_NPOINTS(dataset->shared->space) * H5T_get_size(dataset->shared->type);
                H5_ASSIGN_OVERFLOW(dataset->shared->layout.u.contig.size, tmp_size, hssize_t, hsize_t);
            } /* end if */

            /* Set the I/O functions for this layout type */
            dataset->shared->io_ops.readvv = H5D_contig_readvv;
            dataset->shared->io_ops.writevv = H5D_contig_writevv;

            /* Get the sieve buffer size for this dataset */
            dataset->shared->cache.contig.sieve_buf_size = H5F_SIEVE_BUF_SIZE(dataset->oloc.file);
            break;

        case H5D_CHUNKED:
            /*
             * Chunked storage.  The creation plist's dimension is one less than
             * the chunk dimension because the chunk includes a dimension for the
             * individual bytes of the datatype.
             */
            {
                unsigned     chunk_ndims;           /* Dimensionality of chunk */

                chunk_ndims  = dataset->shared->layout.u.chunk.ndims - 1;

                if(H5P_set(plist, H5D_CRT_CHUNK_DIM_NAME, &chunk_ndims) < 0)
                     HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set chunk dimensions")
                if(H5P_set(plist, H5D_CRT_CHUNK_SIZE_NAME, dataset->shared->layout.u.chunk.dim) < 0)
                     HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set chunk size")

                /* Initialize the chunk cache for the dataset */
                if(H5D_istore_init(dataset->oloc.file, dataset) < 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "can't initialize chunk cache")
            }

            /* Set the I/O functions for this layout type */
            dataset->shared->io_ops.readvv = H5D_istore_readvv;
            dataset->shared->io_ops.writevv = H5D_istore_writevv;
            break;

        case H5D_COMPACT:
            /* Set the I/O functions for this layout type */
            dataset->shared->io_ops.readvv = H5D_compact_readvv;
            dataset->shared->io_ops.writevv = H5D_compact_writevv;
            break;

        default:
            HGOTO_ERROR(H5E_DATASET, H5E_UNSUPPORTED, FAIL, "not implemented yet")
    } /* end switch */ /*lint !e788 All appropriate cases are covered */

    /* Point at dataset's copy, to cache it for later */
    fill_prop = &dataset->shared->dcpl_cache.fill;

    /* Try to get the new fill value message from the object header */
    if(NULL == H5O_msg_read(&(dataset->oloc), H5O_FILL_NEW_ID, fill_prop, dxpl_id)) {
        H5E_clear_stack(NULL);

	/* For backward compatibility, try to retrieve the old fill value message */
        if(NULL == H5O_msg_read(&(dataset->oloc), H5O_FILL_ID, fill_prop, dxpl_id)) {
            H5E_clear_stack(NULL);

            /* Set the space allocation time appropriately, based on the type of dataset storage */
            switch(dataset->shared->layout.type) {
                case H5D_COMPACT:
                    fill_prop->alloc_time = H5D_ALLOC_TIME_EARLY;
                    break;

                case H5D_CONTIGUOUS:
                    fill_prop->alloc_time = H5D_ALLOC_TIME_LATE;
                    break;

                case H5D_CHUNKED:
                    fill_prop->alloc_time = H5D_ALLOC_TIME_INCR;
                    break;

                default:
                    HGOTO_ERROR(H5E_DATASET, H5E_UNSUPPORTED, FAIL, "not implemented yet")
            } /* end switch */ /*lint !e788 All appropriate cases are covered */
        } /* end if */

        /* If "old" fill value size is 0 (undefined), map it to -1 */
        if(fill_prop->size == 0)
            fill_prop->size = (size_t)-1;
    } /* end if */
    alloc_time_state = 0;
    if((dataset->shared->layout.type == H5D_COMPACT && fill_prop->alloc_time == H5D_ALLOC_TIME_EARLY)
            || (dataset->shared->layout.type == H5D_CONTIGUOUS && fill_prop->alloc_time == H5D_ALLOC_TIME_LATE)
            || (dataset->shared->layout.type == H5D_CHUNKED && fill_prop->alloc_time == H5D_ALLOC_TIME_INCR))
        alloc_time_state = 1;

    /* Set revised fill value properties, if they are different from the defaults */
    if(H5P_fill_value_cmp(&H5D_def_dset.dcpl_cache.fill, fill_prop, sizeof(H5O_fill_t))) {
        if(H5P_set(plist, H5D_CRT_FILL_VALUE_NAME, fill_prop) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set fill value")
        if(H5P_set(plist, H5D_CRT_ALLOC_TIME_STATE_NAME, &alloc_time_state) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set allocation time state")
    } /* end if */

    /* Get the external file list message, which might not exist.  Space is
     * also undefined when space allocate time is H5D_ALLOC_TIME_LATE. */
    if((dataset->shared->layout.type == H5D_CONTIGUOUS && !H5F_addr_defined(dataset->shared->layout.u.contig.addr))
            || (dataset->shared->layout.type == H5D_CHUNKED && !H5F_addr_defined(dataset->shared->layout.u.chunk.addr))) {
        if(NULL != H5O_msg_read(&(dataset->oloc), H5O_EFL_ID, &dataset->shared->dcpl_cache.efl, dxpl_id)) {
            if(H5P_set(plist, H5D_CRT_EXT_FILE_LIST_NAME, &dataset->shared->dcpl_cache.efl) < 0)
            	HGOTO_ERROR(H5E_DATASET, H5E_CANTSET, FAIL, "can't set external file list")

            /* Override the I/O functions for this layout type */
            dataset->shared->io_ops.readvv = H5D_efl_readvv;
            dataset->shared->io_ops.writevv = H5D_efl_writevv;
        } /* end if */
        else
            H5E_clear_stack(NULL);
    } /* end if */

    /*
     * Make sure all storage is properly initialized.
     * This is important only for parallel I/O where the space must
     * be fully allocated before I/O can happen.
     */
    if((H5F_get_intent(dataset->oloc.file) & H5F_ACC_RDWR)
            && ((dataset->shared->layout.type == H5D_CONTIGUOUS && !H5F_addr_defined(dataset->shared->layout.u.contig.addr))
                || (dataset->shared->layout.type == H5D_CHUNKED && !H5F_addr_defined(dataset->shared->layout.u.chunk.addr)))
            && IS_H5FD_MPI(dataset->oloc.file)) {
        if(H5D_alloc_storage(dataset->oloc.file, dxpl_id, dataset, H5D_ALLOC_OPEN, FALSE) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to initialize file storage")
    } /* end if */

done:
    if(ret_value < 0) {
        if(H5F_addr_defined(dataset->oloc.addr)) {
            if(H5O_close(&(dataset->oloc)) < 0)
                HDONE_ERROR(H5E_DATASET, H5E_CLOSEERROR, FAIL, "unable to release object header")
        } /* end if */
        if(dataset->shared->space) {
            if(H5S_close(dataset->shared->space) < 0)
                HDONE_ERROR(H5E_DATASET, H5E_CLOSEERROR, FAIL, "unable to release dataspace")
        } /* end if */
        if(dataset->shared->type) {
            if(dataset->shared->type_id > 0) {
                if(H5I_dec_ref(dataset->shared->type_id) < 0)
                    HDONE_ERROR(H5E_DATASET, H5E_CLOSEERROR, FAIL, "unable to release datatype")
            } /* end if */
            else {
                if(H5T_close(dataset->shared->type) < 0)
                    HDONE_ERROR(H5E_DATASET, H5E_CLOSEERROR, FAIL, "unable to release datatype")
            } /* end else */
        } /* end if */
    } /* end if */

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_open_oid() */


/*-------------------------------------------------------------------------
 * Function:	H5D_close
 *
 * Purpose:	Insures that all data has been saved to the file, closes the
 *		dataset object header, and frees all resources used by the
 *		descriptor.
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Robb Matzke
 *		Thursday, December  4, 1997
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_close(H5D_t *dataset)
{
    unsigned free_failed = FALSE;
    herr_t ret_value = SUCCEED;      /* Return value */

    FUNC_ENTER_NOAPI(H5D_close, FAIL)

    /* check args */
    HDassert(dataset && dataset->oloc.file && dataset->shared);
    HDassert(dataset->shared->fo_count >0);

    /* Dump debugging info */
#ifdef H5D_ISTORE_DEBUG
    H5D_istore_stats(dataset, FALSE);
#endif /* H5F_ISTORE_DEBUG */

    dataset->shared->fo_count--;
    if(dataset->shared->fo_count == 0) {
        /* Flush the dataset's information */
        if(H5D_flush_real(dataset, H5AC_dxpl_id, H5F_FLUSH_NONE) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_WRITEERROR, FAIL, "unable to flush cached dataset info")

        /* Free the data sieve buffer, if it's been allocated */
        if(dataset->shared->cache.contig.sieve_buf) {
            HDassert(dataset->shared->layout.type != H5D_COMPACT);      /* We should never have a sieve buffer for compact storage */

            dataset->shared->cache.contig.sieve_buf = (unsigned char *)H5FL_BLK_FREE(sieve_buf,dataset->shared->cache.contig.sieve_buf);
        } /* end if */

        /* Free cached information for each kind of dataset */
        switch(dataset->shared->layout.type) {
            case H5D_CONTIGUOUS:
                break;

            case H5D_CHUNKED:
                /* Check for skip list for iterating over chunks during I/O to close */
                if(dataset->shared->cache.chunk.sel_chunks) {
                    HDassert(H5SL_count(dataset->shared->cache.chunk.sel_chunks) == 0);
                    H5SL_close(dataset->shared->cache.chunk.sel_chunks);
                    dataset->shared->cache.chunk.sel_chunks = NULL;
                } /* end if */

                /* Check for cached single chunk dataspace */
                if(dataset->shared->cache.chunk.single_space) {
                    (void)H5S_close(dataset->shared->cache.chunk.single_space);
                    dataset->shared->cache.chunk.single_space = NULL;
                } /* end if */

                /* Check for cached single element chunk info */
                if(dataset->shared->cache.chunk.single_chunk_info) {
                    (void)H5FL_FREE(H5D_chunk_info_t, dataset->shared->cache.chunk.single_chunk_info);
                    dataset->shared->cache.chunk.single_chunk_info = NULL;
                } /* end if */

                /* Flush and destroy chunks in the cache */
                if(H5D_istore_dest(dataset, H5AC_dxpl_id) < 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTRELEASE, FAIL, "unable to destroy chunk cache")
                break;

            case H5D_COMPACT:
                /* Free the buffer for the raw data for compact datasets */
                dataset->shared->layout.u.compact.buf = H5MM_xfree(dataset->shared->layout.u.compact.buf);
                break;

            default:
                HDassert("not implemented yet" && 0);
#ifdef NDEBUG
                HGOTO_ERROR(H5E_IO, H5E_UNSUPPORTED, FAIL, "unsupported storage layout")
#endif /* NDEBUG */
        } /* end switch */ /*lint !e788 All appropriate cases are covered */

        /*
        * Release datatype, dataspace and creation property list -- there isn't
        * much we can do if one of these fails, so we just continue.
        */
        free_failed = (H5I_dec_ref(dataset->shared->type_id) < 0 || H5S_close(dataset->shared->space) < 0 ||
                          H5I_dec_ref(dataset->shared->dcpl_id) < 0);

        /* Remove the dataset from the list of opened objects in the file */
        if(H5FO_top_decr(dataset->oloc.file, dataset->oloc.addr) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTRELEASE, FAIL, "can't decrement count for object")
        if(H5FO_delete(dataset->oloc.file, H5AC_dxpl_id, dataset->oloc.addr) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTRELEASE, FAIL, "can't remove dataset from list of open objects")

        /* Close the dataset object */
        /* (This closes the file, if this is the last object open) */
        if(H5O_close(&(dataset->oloc)) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CLOSEERROR, FAIL, "unable to release object header")

        /*
         * Free memory.  Before freeing the memory set the file pointer to NULL.
         * We always check for a null file pointer in other H5D functions to be
         * sure we're not accessing an already freed dataset (see the HDassert()
         * above).
         */
        dataset->oloc.file = NULL;

        H5FL_FREE(H5D_shared_t, dataset->shared);
    } /* end if */
    else {
        /* Decrement the ref. count for this object in the top file */
        if(H5FO_top_decr(dataset->oloc.file, dataset->oloc.addr) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTRELEASE, FAIL, "can't decrement count for object")

        /* Check reference count for this object in the top file */
        if(H5FO_top_count(dataset->oloc.file, dataset->oloc.addr) == 0)
            if(H5O_close(&(dataset->oloc)) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to close")
    } /* end else */

   /* Release the dataset's path info */
   if(H5G_name_free(&(dataset->path)) < 0)
       free_failed = TRUE;

    /* Free the dataset's memory structure */
    H5FL_FREE(H5D_t, dataset);

    /* Check if anything failed in the middle... */
    if(free_failed)
	HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "couldn't free a component of the dataset, but the dataset was freed anyway.")

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_close() */


/*-------------------------------------------------------------------------
 * Function:	H5D_oloc
 *
 * Purpose:	Returns a pointer to the object location for a dataset.
 *
 * Return:	Success:	Ptr to location
 *		Failure:	NULL
 *
 * Programmer:	Robb Matzke
 *              Friday, April 24, 1998
 *
 *-------------------------------------------------------------------------
 */
H5O_loc_t *
H5D_oloc(H5D_t *dataset)
{
    /* Use FUNC_ENTER_NOAPI_NOINIT_NOFUNC here to avoid performance issues */
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5D_oloc)

    FUNC_LEAVE_NOAPI(dataset ? &(dataset->oloc) : (H5O_loc_t *)NULL)
} /* end H5D_oloc() */


/*-------------------------------------------------------------------------
 * Function:	H5D_nameof
 *
 * Purpose:	Returns a pointer to the group hier. path for a dataset.
 *
 * Return:	Success:	Ptr to entry
 *		Failure:	NULL
 *
 * Programmer:	Quincey Koziol
 *              Monday, September 12, 2005
 *
 *-------------------------------------------------------------------------
 */
H5G_name_t *
H5D_nameof(H5D_t *dataset)
{
    /* Use FUNC_ENTER_NOAPI_NOINIT_NOFUNC here to avoid performance issues */
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5D_nameof)

    FUNC_LEAVE_NOAPI(dataset ? &(dataset->path) : (H5G_name_t *)NULL)
} /* end H5D_nameof() */


/*-------------------------------------------------------------------------
 * Function:	H5D_typeof
 *
 * Purpose:	Returns a pointer to the dataset's datatype.  The datatype
 *		is not copied.
 *
 * Return:	Success:	Ptr to the dataset's datatype, uncopied.
 *
 *		Failure:	NULL
 *
 * Programmer:	Robb Matzke
 *              Thursday, June  4, 1998
 *
 *-------------------------------------------------------------------------
 */
H5T_t *
H5D_typeof(const H5D_t *dset)
{
    /* Use FUNC_ENTER_NOAPI_NOINIT_NOFUNC here to avoid performance issues */
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5D_typeof)

    HDassert(dset);
    HDassert(dset->shared->type);

    FUNC_LEAVE_NOAPI(dset->shared->type)
} /* end H5D_typeof() */


/*-------------------------------------------------------------------------
 * Function:  H5D_get_file
 *
 * Purpose:   Returns the dataset's file pointer.
 *
 * Return:    Success:        Ptr to the dataset's file pointer.
 *
 *            Failure:        NULL
 *
 * Programmer:        Quincey Koziol
 *              Thursday, October 22, 1998
 *
 *-------------------------------------------------------------------------
 */
static H5F_t *
H5D_get_file(const H5D_t *dset)
{
    /* Use FUNC_ENTER_NOAPI_NOINIT_NOFUNC here to avoid performance issues */
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5D_get_file)

    HDassert(dset);
    HDassert(dset->oloc.file);

    FUNC_LEAVE_NOAPI(dset->oloc.file)
} /* end H5D_get_file() */


/*-------------------------------------------------------------------------
 * Function:	H5D_alloc_storage
 *
 * Purpose:	Allocate storage for the raw data of a dataset.
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Robb Matzke
 *              Friday, January 16, 1998
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_alloc_storage(H5F_t *f, hid_t dxpl_id, H5D_t *dset/*in,out*/, H5D_time_alloc_t time_alloc,
    hbool_t full_overwrite)
{
    struct H5O_layout_t *layout;        /* The dataset's layout information */
    hbool_t must_init_space = FALSE;    /* Flag to indicate that space should be initialized */
    hbool_t addr_set = FALSE;           /* Flag to indicate that the dataset's storage address was set */
    herr_t      ret_value = SUCCEED;    /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_alloc_storage)

    /* check args */
    HDassert(f);
    HDassert(dset);

    /* If the data is stored in external files, don't set an address for the layout
     * We assume that external storage is already
     * allocated by the caller, or at least will be before I/O is performed.
     */
    if(!(H5S_NULL == H5S_GET_EXTENT_TYPE(dset->shared->space) || dset->shared->dcpl_cache.efl.nused > 0)) {
        /* Get a pointer to the dataset's layout information */
        layout = &(dset->shared->layout);

        switch(layout->type) {
            case H5D_CONTIGUOUS:
                if(!H5F_addr_defined(layout->u.contig.addr)) {
                    /* Reserve space in the file for the entire array */
                    if(H5D_contig_create(f, dxpl_id, layout/*out*/) < 0)
                        HGOTO_ERROR(H5E_IO, H5E_CANTINIT, FAIL, "unable to initialize contiguous storage")

                    /* Indicate that we set the storage addr */
                    addr_set = TRUE;

                    /* Indicate that we should initialize storage space */
                    must_init_space = TRUE;
                } /* end if */
                break;

            case H5D_CHUNKED:
                if(!H5F_addr_defined(layout->u.chunk.addr)) {
                    /* Create the root of the B-tree that describes chunked storage */
                    if(H5D_istore_create(f, dxpl_id, layout/*out*/) < 0)
                        HGOTO_ERROR(H5E_IO, H5E_CANTINIT, FAIL, "unable to initialize chunked storage")

                    /* Indicate that we set the storage addr */
                    addr_set = TRUE;

                    /* Indicate that we should initialize storage space */
                    must_init_space = TRUE;
                } /* end if */

                /* If space allocation is set to 'early' and we are extending
		 * the dataset, indicate that space should be allocated, so the
                 * B-tree gets expanded. -QAK
                 */
		if(dset->shared->dcpl_cache.fill.alloc_time == H5D_ALLOC_TIME_EARLY
                        && time_alloc == H5D_ALLOC_EXTEND)
		    must_init_space = TRUE;
                break;

            case H5D_COMPACT:
                /* Check if space is already allocated */
                if(layout->u.compact.buf==NULL) {
                    /* Reserve space in layout header message for the entire array. */
                    HDassert(layout->u.compact.size > 0);
                    if(NULL == (layout->u.compact.buf = H5MM_malloc(layout->u.compact.size)))
                        HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, FAIL, "unable to allocate memory for compact dataset")
                    if(!full_overwrite)
                        HDmemset(layout->u.compact.buf, 0, layout->u.compact.size);
                    layout->u.compact.dirty = TRUE;

                    /* Indicate that we set the storage addr */
                    addr_set = TRUE;

                    /* Indicate that we should initialize storage space */
                    must_init_space = TRUE;
                } /* end if */
                break;

            default:
                HDassert("not implemented yet" && 0);
#ifdef NDEBUG
                HGOTO_ERROR(H5E_IO, H5E_UNSUPPORTED, FAIL, "unsupported storage layout")
#endif /* NDEBUG */
        } /* end switch */ /*lint !e788 All appropriate cases are covered */

        /* Check if we need to initialize the space */
        if(must_init_space) {
            if(layout->type == H5D_CHUNKED) {
                /* If we are doing incremental allocation and the B-tree got
                 * created during a H5Dwrite call, don't initialize the storage
                 * now, wait for the actual writes to each block and let the
                 * low-level chunking routines handle initialize the fill-values.
                 * Otherwise, pass along the space initialization call and let
                 * the low-level chunking routines sort out whether to write
                 * fill values to the chunks they allocate space for.  Yes,
                 * this is icky. -QAK
                 */
                if(!(dset->shared->dcpl_cache.fill.alloc_time == H5D_ALLOC_TIME_INCR && time_alloc == H5D_ALLOC_WRITE))
                    if(H5D_init_storage(dset, full_overwrite, dxpl_id) < 0)
                        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to initialize dataset with fill value")
            } /* end if */
            else {
                H5D_fill_value_t	fill_status;    /* The fill value status */

                /* Check the dataset's fill-value status */
                if(H5P_is_fill_value_defined(&dset->shared->dcpl_cache.fill, &fill_status) < 0)
                    HGOTO_ERROR(H5E_PLIST, H5E_CANTGET, FAIL, "can't tell if fill value defined")

                /* If we are filling the dataset on allocation or "if set" and
                 * the fill value _is_ set, do that now */
                if(dset->shared->dcpl_cache.fill.fill_time == H5D_FILL_TIME_ALLOC ||
                        (dset->shared->dcpl_cache.fill.fill_time == H5D_FILL_TIME_IFSET && fill_status == H5D_FILL_VALUE_USER_DEFINED)) {
                    if(H5D_init_storage(dset, full_overwrite, dxpl_id) < 0)
                        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to initialize dataset with fill value")
                } /* end if */
            } /* end else */
        } /* end if */

        /* If we set the address (and aren't in the middle of creating the
         *      dataset), mark the layout header message for later writing to
         *      the file.  (this improves forward compatibility).
         */
        /* (The layout message is already in the dataset's object header, this
         *      operation just sets the address and makes it constant)
         */
        if(time_alloc != H5D_ALLOC_CREATE && addr_set)
            dset->shared->layout_dirty = TRUE;
    } /* end if */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_alloc_storage() */


/*-------------------------------------------------------------------------
 * Function:	H5D_init_storage
 *
 * Purpose:	Initialize the data for a new dataset.  If a selection is
 *		defined for SPACE then initialize only that part of the
 *		dataset.
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Robb Matzke
 *              Monday, October  5, 1998
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5D_init_storage(H5D_t *dset, hbool_t full_overwrite, hid_t dxpl_id)
{
    herr_t		ret_value = SUCCEED;    /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_init_storage)

    HDassert(dset);

    switch (dset->shared->layout.type) {
        case H5D_COMPACT:
            /* If we will be immediately overwriting the values, don't bother to clear them */
            if(!full_overwrite) {
                /* Fill the compact dataset storage */
                if(H5D_compact_fill(dset, dxpl_id) < 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to initialize compact dataset storage")
            } /* end if */
            break;

        case H5D_CONTIGUOUS:
            /* Don't write default fill values to external files */
            /* If we will be immediately overwriting the values, don't bother to clear them */
            if((dset->shared->dcpl_cache.efl.nused == 0 || dset->shared->dcpl_cache.fill.buf) && !full_overwrite)
                if(H5D_contig_fill(dset, dxpl_id) < 0)
                    HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to allocate all chunks of dataset")
            break;

        case H5D_CHUNKED:
            /*
             * Allocate file space
             * for all chunks now and initialize each chunk with the fill value.
             */
            if(H5D_istore_allocate(dset, dxpl_id, full_overwrite) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to allocate all chunks of dataset")
            break;

        default:
            HDassert("not implemented yet" && 0);
#ifdef NDEBUG
            HGOTO_ERROR(H5E_IO, H5E_UNSUPPORTED, FAIL, "unsupported storage layout")
#endif /* NDEBUG */
    } /* end switch */ /*lint !e788 All appropriate cases are covered */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_init_storage() */


/*-------------------------------------------------------------------------
 * Function:	H5D_get_storage_size
 *
 * Purpose:	Determines how much space has been reserved to store the raw
 *		data of a dataset.
 *
 * Return:	Success:	Number of bytes reserved to hold raw data.
 *
 *		Failure:	0
 *
 * Programmer:	Robb Matzke
 *              Wednesday, April 21, 1999
 *
 *-------------------------------------------------------------------------
 */
hsize_t
H5D_get_storage_size(H5D_t *dset, hid_t dxpl_id)
{
    hsize_t	ret_value;

    FUNC_ENTER_NOAPI_NOINIT(H5D_get_storage_size)

    switch(dset->shared->layout.type) {
        case H5D_CHUNKED:
            if(dset->shared->layout.u.chunk.addr == HADDR_UNDEF)
                ret_value = 0;
            else
                ret_value = H5D_istore_allocated(dset, dxpl_id);
            break;

        case H5D_CONTIGUOUS:
            /* Datasets which are not allocated yet are using no space on disk */
            if(dset->shared->layout.u.contig.addr == HADDR_UNDEF)
                ret_value = 0;
            else
                 ret_value = dset->shared->layout.u.contig.size;
            break;

        case H5D_COMPACT:
            ret_value = dset->shared->layout.u.compact.size;
            break;

        default:
            HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, 0, "not a dataset type")
    } /*lint !e788 All appropriate cases are covered */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_get_storage_size() */


/*-------------------------------------------------------------------------
 * Function:	H5D_get_offset
 *
 * Purpose:	Private function for H5D_get_offset.  Returns the address
 *              of dataset in file.
 *
 * Return:	Success:        the address of dataset
 *
 *		Failure:	HADDR_UNDEF
 *
 * Programmer:  Raymond Lu
 *              November 6, 2002
 *
 *-------------------------------------------------------------------------
 */
haddr_t
H5D_get_offset(const H5D_t *dset)
{
    haddr_t     base_addr;
    H5F_t       *f;
    haddr_t	ret_value = HADDR_UNDEF;

    FUNC_ENTER_NOAPI_NOINIT(H5D_get_offset)

    HDassert(dset);

    switch(dset->shared->layout.type) {
        case H5D_CHUNKED:
        case H5D_COMPACT:
            break;

        case H5D_CONTIGUOUS:
            /* If dataspace hasn't been allocated or dataset is stored in
             * an external file, the value will be HADDR_UNDEF. */
            f =  H5D_get_file(dset);
            base_addr = H5F_get_base_addr(f);

            /* If there's user block in file, returns the absolute dataset offset
             * from the beginning of file. */
            if(base_addr != HADDR_UNDEF)
                ret_value = dset->shared->layout.u.contig.addr + base_addr;
            else
                ret_value = dset->shared->layout.u.contig.addr;
            break;

        default:
            HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, HADDR_UNDEF, "unknown dataset layout type")
    } /*lint !e788 All appropriate cases are covered */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_get_offset() */


/*-------------------------------------------------------------------------
 * Function:	H5D_iterate
 *
 * Purpose:	Internal version of H5Diterate()
 *
 * Return:	Returns the return value of the last operator if it was non-zero,
 *          or zero if all elements were processed. Otherwise returns a
 *          negative value.
 *
 * Programmer:	Quincey Koziol
 *              Tuesday, November 22, 2005
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_iterate(void *buf, hid_t type_id, const H5S_t *space, H5D_operator_t op,
        void *operator_data)
{
    herr_t ret_value;

    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5D_iterate)

    /* Check args */
    HDassert(buf);
    HDassert(H5I_DATATYPE == H5I_get_type(type_id));
    HDassert(space);
    HDassert(H5S_has_extent(space));
    HDassert(op);

    ret_value = H5S_select_iterate(buf, type_id, space, op, operator_data);

    FUNC_LEAVE_NOAPI(ret_value)
}   /* end H5D_iterate() */


/*-------------------------------------------------------------------------
 * Function:	H5D_vlen_reclaim
 *
 * Purpose:	Frees the buffers allocated for storing variable-length data
 *      in memory.  Only frees the VL data in the selection defined in the
 *      dataspace.  The dataset transfer property list is required to find the
 *      correct allocation/free methods for the VL data in the buffer.
 *
 * Return:	Non-negative on success, negative on failure
 *
 * Programmer:	Quincey Koziol
 *              Tuesday, November 22, 2005
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_vlen_reclaim(hid_t type_id, H5S_t *space, hid_t plist_id, void *buf)
{
    H5T_vlen_alloc_info_t _vl_alloc_info;       /* VL allocation info buffer */
    H5T_vlen_alloc_info_t *vl_alloc_info = &_vl_alloc_info;   /* VL allocation info */
    herr_t ret_value;

    FUNC_ENTER_NOAPI(H5D_vlen_reclaim, FAIL)

    /* Check args */
    HDassert(H5I_DATATYPE == H5I_get_type(type_id));
    HDassert(space);
    HDassert(H5P_isa_class(plist_id, H5P_DATASET_XFER));
    HDassert(buf);

    /* Get the allocation info */
    if(H5T_vlen_get_alloc_info(plist_id,&vl_alloc_info) < 0)
        HGOTO_ERROR(H5E_DATATYPE, H5E_CANTGET, FAIL, "unable to retrieve VL allocation info")

    /* Call H5D_iterate with args, etc. */
    ret_value = H5D_iterate(buf, type_id, space ,H5T_vlen_reclaim, vl_alloc_info);

done:
    FUNC_LEAVE_NOAPI(ret_value)
}   /* end H5D_vlen_reclaim() */


/*-------------------------------------------------------------------------
 * Function:	H5D_vlen_get_buf_size_alloc
 *
 * Purpose:	This routine makes certain there is enough space in the temporary
 *      buffer for the new data to read in.  All the VL data read in is actually
 *      placed in this buffer, overwriting the previous data.  Needless to say,
 *      this data is not actually usable.
 *
 * Return:	Non-negative on success, negative on failure
 *
 * Programmer:	Quincey Koziol
 *              Tuesday, August 17, 1999
 *
 *-------------------------------------------------------------------------
 */
void *
H5D_vlen_get_buf_size_alloc(size_t size, void *info)
{
    H5D_vlen_bufsize_t *vlen_bufsize = (H5D_vlen_bufsize_t *)info;
    void *ret_value;    /* Return value */

    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5D_vlen_get_buf_size_alloc)

    /* Get a temporary pointer to space for the VL data */
    if((vlen_bufsize->vl_tbuf = H5FL_BLK_REALLOC(vlen_vl_buf, vlen_bufsize->vl_tbuf, size)) != NULL)
        vlen_bufsize->size += size;

    /* Set return value */
    ret_value = vlen_bufsize->vl_tbuf;

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_vlen_get_buf_size_alloc() */


/*-------------------------------------------------------------------------
 * Function:	H5D_vlen_get_buf_size
 *
 * Purpose:	This routine checks the number of bytes required to store a single
 *      element from a dataset in memory, creating a selection with just the
 *      single element selected to read in the element and using a custom memory
 *      allocator for any VL data encountered.
 *          The *size value is modified according to how many bytes are
 *      required to store the element in memory.
 *
 * Implementation: This routine actually performs the read with a custom
 *      memory manager which basically just counts the bytes requested and
 *      uses a temporary memory buffer (through the H5FL API) to make certain
 *      enough space is available to perform the read.  Then the temporary
 *      buffer is released and the number of bytes allocated is returned.
 *      Kinda kludgy, but easier than the other method of trying to figure out
 *      the sizes without actually reading the data in... - QAK
 *
 * Return:	Non-negative on success, negative on failure
 *
 * Programmer:	Quincey Koziol
 *              Tuesday, August 17, 1999
 *
 *-------------------------------------------------------------------------
 */
/* ARGSUSED */
herr_t
H5D_vlen_get_buf_size(void UNUSED *elem, hid_t type_id, unsigned UNUSED ndim, const hsize_t *point, void *op_data)
{
    H5D_vlen_bufsize_t *vlen_bufsize = (H5D_vlen_bufsize_t *)op_data;
    H5T_t *dt;                          /* Datatype for operation */
    herr_t ret_value = SUCCEED;         /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_vlen_get_buf_size)

    HDassert(op_data);
    HDassert(H5I_DATATYPE == H5I_get_type(type_id));

    /* Check args */
    if(NULL == (dt = (H5T_t *)H5I_object(type_id)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not a datatype")

    /* Make certain there is enough fixed-length buffer available */
    if(NULL == (vlen_bufsize->fl_tbuf = H5FL_BLK_REALLOC(vlen_fl_buf, vlen_bufsize->fl_tbuf, H5T_get_size(dt))))
        HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, FAIL, "can't resize tbuf")

    /* Select point to read in */
    if(H5Sselect_elements(vlen_bufsize->fspace_id, H5S_SELECT_SET, (size_t)1, point) < 0)
        HGOTO_ERROR(H5E_DATASPACE, H5E_CANTCREATE, FAIL, "can't select point")

    /* Read in the point (with the custom VL memory allocator) */
    if(H5Dread(vlen_bufsize->dataset_id, type_id, vlen_bufsize->mspace_id, vlen_bufsize->fspace_id, vlen_bufsize->xfer_pid, vlen_bufsize->fl_tbuf) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_READERROR, FAIL, "can't read point")

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_vlen_get_buf_size() */


/*-------------------------------------------------------------------------
 * Function:	H5D_check_filters
 *
 * Purpose:	Check if the filters have be initialized for the dataset
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Quincey Koziol
 *		Thursday, October 11, 2007
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_check_filters(H5D_t *dataset)
{
    H5O_fill_t *fill;                   /* Dataset's fill value */
    herr_t ret_value = SUCCEED;         /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_check_filters)

    /* Check args */
    HDassert(dataset);

    /* Check if the filters in the DCPL will need to encode, and if so, can they?
     *
     * Filters need encoding if fill value is defined and a fill policy is set
     * that requires writing on an extend.
     */
    fill = &dataset->shared->dcpl_cache.fill;
    if(!dataset->shared->checked_filters) {
        H5D_fill_value_t fill_status;       /* Whether the fill value is defined */

        /* Retrieve the "defined" status of the fill value */
        if(H5P_is_fill_value_defined(fill, &fill_status) < 0)
            HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "Couldn't retrieve fill value from dataset.")

        /* See if we can check the filter status */
        if(fill_status == H5D_FILL_VALUE_DEFAULT || fill_status == H5D_FILL_VALUE_USER_DEFINED) {
            if(fill->fill_time == H5D_FILL_TIME_ALLOC ||
                    (fill->fill_time == H5D_FILL_TIME_IFSET && fill_status == H5D_FILL_VALUE_USER_DEFINED)) {
                /* Filters must have encoding enabled. Ensure that all filters can be applied */
                if(H5Z_can_apply(dataset->shared->dcpl_id, dataset->shared->type_id) < 0)
                    HGOTO_ERROR(H5E_PLINE, H5E_CANAPPLY, FAIL, "can't apply filters")

                dataset->shared->checked_filters = TRUE;
            } /* end if */
        } /* end if */
    } /* end if */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_check_filters() */


/*-------------------------------------------------------------------------
 * Function:	H5D_set_extent
 *
 * Purpose:	Based on H5D_extend, allows change to a lower dimension,
 *		calls H5S_set_extent and H5D_istore_prune_by_extent instead
 *
 * Return:	Non-negative on success, negative on failure
 *
 * Programmer:  Pedro Vicente, pvn@ncsa.uiuc.edu
 *              April 9, 2002
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_set_extent(H5D_t *dset, const hsize_t *size, hid_t dxpl_id)
{
    H5S_t   *space;                     /* Dataset's dataspace */
    int     rank;	                /* Dataspace # of dimensions */
    hsize_t curr_dims[H5O_LAYOUT_NDIMS];	/* Current dimension sizes */
    htri_t changed;                     /* Whether the dataspace changed size */
    herr_t  ret_value = SUCCEED;        /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_set_extent)

    /* Check args */
    HDassert(dset);
    HDassert(size);

    /* Check if we are allowed to modify this file */
    if(0 == (H5F_get_intent(dset->oloc.file) & H5F_ACC_RDWR))
	HGOTO_ERROR(H5E_DATASET, H5E_WRITEERROR, FAIL, "no write intent on file")

    /* Check if the filters in the DCPL will need to encode, and if so, can they? */
    if(H5D_check_filters(dset) < 0)
        HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "can't apply filters")

    /* Get the data space */
    space = dset->shared->space;

    /* Check if we are shrinking or expanding any of the dimensions */
    if((rank = H5S_get_simple_extent_dims(space, curr_dims, NULL)) < 0)
	HGOTO_ERROR(H5E_DATASET, H5E_CANTGET, FAIL, "can't get dataset dimensions")

    /* Modify the size of the data space */
    if((changed = H5S_set_extent(space, size)) < 0)
	HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to modify size of data space")

    /* Don't bother updating things, unless they've changed */
    if(changed) {
        hbool_t shrink = FALSE;             /* Flag to indicate a dimension has shrank */
        hbool_t expand = FALSE;             /* Flag to indicate a dimension has grown */
        unsigned u;                         /* Local index variable */

        /* Determine if we are shrinking and/or expanding any dimensions */
        for(u = 0; u < (unsigned)rank; u++) {
            if(size[u] < curr_dims[u])
                shrink = TRUE;
            if(size[u] > curr_dims[u])
                expand = TRUE;
        } /* end for */

        /*-------------------------------------------------------------------------
         * Modify the dataset storage
         *-------------------------------------------------------------------------
         */
        /* Update the index values for the cached chunks for this dataset */
        if(H5D_CHUNKED == dset->shared->layout.type)
            if(H5D_istore_update_cache(dset, dxpl_id) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_WRITEERROR, FAIL, "unable to update cached chunk indices")

	/* Allocate space for the new parts of the dataset, if appropriate */
        if(expand && dset->shared->dcpl_cache.fill.alloc_time == H5D_ALLOC_TIME_EARLY)
            if(H5D_alloc_storage(dset->oloc.file, dxpl_id, dset, H5D_ALLOC_EXTEND, FALSE) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_CANTINIT, FAIL, "unable to initialize dataset storage")


        /*-------------------------------------------------------------------------
         * Remove chunk information in the case of chunked datasets
         * This removal takes place only in case we are shrinking the dateset
         *-------------------------------------------------------------------------
         */
        if(shrink && H5D_CHUNKED == dset->shared->layout.type) {
            H5D_io_info_t io_info;              /* Dataset I/O info */
            H5D_dxpl_cache_t _dxpl_cache;       /* Data transfer property cache buffer */
            H5D_dxpl_cache_t *dxpl_cache = &_dxpl_cache;   /* Data transfer property cache */

            /* Fill the DXPL cache values for later use */
            if(H5D_get_dxpl_cache(dxpl_id, &dxpl_cache) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_CANTGET, FAIL, "can't fill dxpl cache")

            /* Construct dataset I/O info */
            H5D_BUILD_IO_INFO(&io_info, dset, dxpl_cache, dxpl_id, NULL);

            /* Remove excess chunks */
            if(H5D_istore_prune_by_extent(&io_info, curr_dims) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_WRITEERROR, FAIL, "unable to remove chunks ")

            /* Reset the elements outsize the new dimensions, but in existing chunks */
            if(H5D_istore_initialize_by_extent(&io_info) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_WRITEERROR, FAIL, "unable to initialize chunks ")
        } /* end if */

        /* Mark the dataspace as dirty, for later writing to the file */
        dset->shared->space_dirty = TRUE;
    } /* end if */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_set_extent() */


/*-------------------------------------------------------------------------
 * Function:    H5D_flush_real
 *
 * Purpose:     Flush any dataset information cached in memory
 *
 * Return:	Success:	Non-negative
 *		Failure:	Negative
 *
 * Programmer:  Quincey Koziol
 *              December 6, 2007
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5D_flush_real(H5D_t *dataset, hid_t dxpl_id, unsigned flags)
{
    H5O_t *oh = NULL;                   /* Pointer to dataset's object header */
    herr_t ret_value = SUCCEED;         /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_flush_real)

    /* Check args */
    HDassert(dataset);

    /* Check for metadata changes that will require updating the object's modification time */
    if(dataset->shared->layout_dirty || dataset->shared->space_dirty) {
        unsigned update_flags = H5O_UPDATE_TIME;        /* Modification time flag */

        /* Get a pointer to the dataset's object header */
        if((oh = H5O_protect(&dataset->oloc, dxpl_id)) == NULL)
            HGOTO_ERROR(H5E_DATASET, H5E_CANTPROTECT, FAIL, "unable to protect dataset object header")

        /* Update the layout on disk, if it's been changed */
        if(dataset->shared->layout_dirty) {
            if(H5O_msg_write_oh(dataset->oloc.file, dxpl_id, oh, H5O_LAYOUT_ID, H5O_MSG_FLAG_CONSTANT, update_flags, &dataset->shared->layout) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_WRITEERROR, FAIL, "unable to update layout message")
            dataset->shared->layout_dirty = FALSE;

            /* Reset the "update the modification time" flag, so we only do it once */
            update_flags = 0;
        } /* end if */

        /* Update the dataspace on disk, if it's been changed */
        if(dataset->shared->space_dirty) {
            if(H5S_write(dataset->oloc.file, dxpl_id, oh, update_flags, dataset->shared->space) < 0)
                HGOTO_ERROR(H5E_DATASET, H5E_WRITEERROR, FAIL, "unable to update file with new dataspace")
            dataset->shared->space_dirty = FALSE;

            /* Reset the "update the modification time" flag, so we only do it once */
            update_flags = 0;
        } /* end if */

        /* _Somebody_ should have update the modification time! */
        HDassert(update_flags == 0);
    } /* end if */

    /* Flush the raw data buffer, if we have a dirty one */
    if(dataset->shared->cache.contig.sieve_buf && dataset->shared->cache.contig.sieve_dirty) {
        HDassert(dataset->shared->layout.type != H5D_COMPACT);      /* We should never have a sieve buffer for compact storage */

        /* Write dirty data sieve buffer to file */
        if(H5F_block_write(dataset->oloc.file, H5FD_MEM_DRAW, dataset->shared->cache.contig.sieve_loc,
                dataset->shared->cache.contig.sieve_size, dxpl_id, dataset->shared->cache.contig.sieve_buf) < 0)
            HGOTO_ERROR(H5E_IO, H5E_WRITEERROR, FAIL, "block write failed")

        /* Reset sieve buffer dirty flag */
        dataset->shared->cache.contig.sieve_dirty = FALSE;
    } /* end if */

    /* Flush cached information for each kind of dataset */
    switch(dataset->shared->layout.type) {
        case H5D_CONTIGUOUS:
            break;

        case H5D_CHUNKED:
            /* Flush the raw data cache */
            if(H5D_istore_flush(dataset, dxpl_id, flags & H5F_FLUSH_INVALIDATE) < 0)
                HGOTO_ERROR(H5E_CACHE, H5E_CANTFLUSH, FAIL, "unable to flush raw data cache")
            break;

        case H5D_COMPACT:
            if(dataset->shared->layout.u.compact.dirty) {
                if(H5O_msg_write(&(dataset->oloc), H5O_LAYOUT_ID, 0, H5O_UPDATE_TIME, &(dataset->shared->layout), dxpl_id) < 0)
                    HGOTO_ERROR(H5E_FILE, H5E_CANTINIT, FAIL, "unable to update layout message")
                dataset->shared->layout.u.compact.dirty = FALSE;
            } /* end if */
            break;

        default:
            HDassert("not implemented yet" && 0);
#ifdef NDEBUG
            HGOTO_ERROR(H5E_IO, H5E_UNSUPPORTED, FAIL, "unsupported storage layout")
#endif /* NDEBUG */
    } /* end switch */ /*lint !e788 All appropriate cases are covered */

done:
    /* Release pointer to object header */
    if(oh != NULL)
        if(H5O_unprotect(&(dataset->oloc), oh) < 0)
            HDONE_ERROR(H5E_DATASET, H5E_CANTUNPROTECT, FAIL, "unable to unprotect dataset object header")

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_flush_real() */


/*-------------------------------------------------------------------------
 * Function:    H5D_flush_cb
 *
 * Purpose:     Flush any dataset information cached in memory
 *
 * Return:	Success:	Non-negative
 *		Failure:	Negative
 *
 * Programmer:  Quincey Koziol
 *              November 8, 2007
 *
 *-------------------------------------------------------------------------
 */
static int
H5D_flush_cb(void *_dataset, hid_t UNUSED id, void *_udata)
{
    H5D_t       *dataset = (H5D_t *)_dataset;   /* Dataset pointer */
    H5D_flush_ud_t *udata = (H5D_flush_ud_t *)_udata;   /* User data for callback */
    int         ret_value = H5_ITER_CONT;       /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5D_flush_cb)

    /* Check args */
    HDassert(dataset);

    /* Check for dataset in same file */
    if(udata->f == dataset->oloc.file) {
        /* Flush the dataset's information */
        if(H5D_flush_real(dataset, udata->dxpl_id, udata->flags) < 0)
            HGOTO_ERROR(H5E_DATASET, H5E_WRITEERROR, H5_ITER_ERROR, "unable to flush cached dataset info")
    } /* end if */

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_flush_cb() */


/*-------------------------------------------------------------------------
 * Function:    H5D_flush
 *
 * Purpose:     Flush any dataset information cached in memory
 *
 * Return:	Success:	Non-negative
 *		Failure:	Negative
 *
 * Programmer:  Ray Lu
 *              August 14, 2002
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5D_flush(const H5F_t *f, hid_t dxpl_id, unsigned flags)
{
    H5D_flush_ud_t udata;               /* User data for callback */
    herr_t      ret_value = SUCCEED;    /* Return value */

    FUNC_ENTER_NOAPI(H5D_flush, FAIL)

    /* Check args */
    HDassert(f);

    /* Set user data for callback */
    udata.f = f;
    udata.dxpl_id = dxpl_id;
    udata.flags = flags;

    /* Iterate over all the open datasets */
    H5I_search(H5I_DATASET, H5D_flush_cb, &udata);

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5D_flush() */

