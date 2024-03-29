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

#define H5O_PACKAGE		/*suppress error about including H5Opkg	  */
#define H5G_PACKAGE		/*suppress error about including H5Gpkg	  */


/***********/
/* Headers */
/***********/
#include "H5private.h"		/* Generic Functions			*/
#include "H5Eprivate.h"		/* Error handling		  	*/
#include "H5Gpkg.h"             /* Groups				*/
#include "H5Iprivate.h"		/* IDs			  		*/
#include "H5Opkg.h"             /* Object headers			*/


/****************/
/* Local Macros */
/****************/


/******************/
/* Local Typedefs */
/******************/


/********************/
/* Local Prototypes */
/********************/

static htri_t H5O_group_isa(H5O_t *loc);
static hid_t H5O_group_open(const H5G_loc_t *obj_loc, hid_t dxpl_id);
static void *H5O_group_create(H5F_t *f, void *_crt_info, H5G_loc_t *obj_loc,
    hid_t dxpl_id);
static H5O_loc_t *H5O_group_get_oloc(hid_t obj_id);


/*********************/
/* Package Variables */
/*********************/


/*****************************/
/* Library Private Variables */
/*****************************/


/*******************/
/* Local Variables */
/*******************/

/* This message derives from H5O object class */
const H5O_obj_class_t H5O_OBJ_GROUP[1] = {{
    H5O_TYPE_GROUP,		/* object type			*/
    "group",			/* object name, for debugging	*/
    NULL,			/* get 'copy file' user data	*/
    NULL,			/* free 'copy file' user data	*/
    H5O_group_isa, 		/* "isa" message		*/
    H5O_group_open, 		/* open an object of this class */
    H5O_group_create, 		/* create an object of this class */
    H5O_group_get_oloc 		/* get an object header location for an object */
}};


/*-------------------------------------------------------------------------
 * Function:	H5O_group_isa
 *
 * Purpose:	Determines if an object has the requisite messages for being
 *		a group.
 *
 * Return:	Success:	TRUE if the required group messages are
 *				present; FALSE otherwise.
 *
 *		Failure:	FAIL if the existence of certain messages
 *				cannot be determined.
 *
 * Programmer:	Robb Matzke
 *              Monday, November  2, 1998
 *
 *-------------------------------------------------------------------------
 */
static htri_t
H5O_group_isa(struct H5O_t *oh)
{
    htri_t	stab_exists;            /* Whether the 'stab' message is in the object header */
    htri_t	linfo_exists;           /* Whether the 'linfo' message is in the object header */
    htri_t	ret_value;              /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5O_group_isa)

    HDassert(oh);

    /* Check for any of the messages that indicate a group */
    if((stab_exists = H5O_msg_exists_oh(oh, H5O_STAB_ID)) < 0)
	HGOTO_ERROR(H5E_SYM, H5E_CANTINIT, FAIL, "unable to read object header")
    if((linfo_exists = H5O_msg_exists_oh(oh, H5O_LINFO_ID)) < 0)
	HGOTO_ERROR(H5E_SYM, H5E_CANTINIT, FAIL, "unable to read object header")

    ret_value = (stab_exists > 0 || linfo_exists > 0);

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5O_group_isa() */


/*-------------------------------------------------------------------------
 * Function:	H5O_group_open
 *
 * Purpose:	Open a group at a particular location
 *
 * Return:	Success:	Open object identifier
 *		Failure:	Negative
 *
 * Programmer:	Quincey Koziol
 *              Monday, November  6, 2006
 *
 *-------------------------------------------------------------------------
 */
static hid_t
H5O_group_open(const H5G_loc_t *obj_loc, hid_t dxpl_id)
{
    H5G_t       *grp = NULL;            /* Group opened */
    hid_t	ret_value;              /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5O_group_open)

    HDassert(obj_loc);

    /* Open the group */
    if(NULL == (grp = H5G_open(obj_loc, dxpl_id)))
        HGOTO_ERROR(H5E_SYM, H5E_CANTOPENOBJ, FAIL, "unable to open group")

    /* Register an ID for the group */
    if((ret_value = H5I_register(H5I_GROUP, grp)) < 0)
        HGOTO_ERROR(H5E_ATOM, H5E_CANTREGISTER, FAIL, "unable to register group")

done:
    if(ret_value < 0)
        if(grp && H5G_close(grp) < 0)
            HDONE_ERROR(H5E_SYM, H5E_CLOSEERROR, FAIL, "unable to release group")

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5O_group_open() */


/*-------------------------------------------------------------------------
 * Function:	H5O_group_create
 *
 * Purpose:	Create a group in a file
 *
 * Return:	Success:	Pointer to the group data structure
 *		Failure:	NULL
 *
 * Programmer:	Quincey Koziol
 *              Tuesday, April 10, 2007
 *
 *-------------------------------------------------------------------------
 */
static void *
H5O_group_create(H5F_t *f, void *_crt_info, H5G_loc_t *obj_loc, hid_t dxpl_id)
{
    H5G_obj_create_t *crt_info = (H5G_obj_create_t *)_crt_info; /* Group creation parameters */
    H5G_t *grp = NULL;          /* New group created */
    void *ret_value;            /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5O_group_create)

    /* Sanity checks */
    HDassert(f);
    HDassert(crt_info);
    HDassert(obj_loc);

    /* Create the the group */
    if(NULL == (grp = H5G_create(f, crt_info->gcpl_id, dxpl_id)))
        HGOTO_ERROR(H5E_SYM, H5E_CANTINIT, NULL, "unable to create group")

    /* Set up the new group's location */
    if(NULL == (obj_loc->oloc = H5G_oloc(grp)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, NULL, "unable to get object location of group")
    if(NULL == (obj_loc->path = H5G_nameof(grp)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, NULL, "unable to get path of group")

    /* Set the return value */
    ret_value = grp;

done:
    if(ret_value == NULL)
        if(grp && H5G_close(grp) < 0)
            HDONE_ERROR(H5E_SYM, H5E_CLOSEERROR, NULL, "unable to release group")

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5O_group_create() */


/*-------------------------------------------------------------------------
 * Function:	H5O_group_get_oloc
 *
 * Purpose:	Retrieve the object header location for an open object
 *
 * Return:	Success:	Pointer to object header location
 *		Failure:	NULL
 *
 * Programmer:	Quincey Koziol
 *              Monday, November  6, 2006
 *
 *-------------------------------------------------------------------------
 */
static H5O_loc_t *
H5O_group_get_oloc(hid_t obj_id)
{
    H5G_t       *grp;                   /* Group opened */
    H5O_loc_t	*ret_value;             /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5O_group_get_oloc)

    /* Get the group */
    if(NULL ==  (grp = H5I_object(obj_id)))
        HGOTO_ERROR(H5E_OHDR, H5E_BADATOM, NULL, "couldn't get object from ID")

    /* Get the group's object header location */
    if(NULL == (ret_value = H5G_oloc(grp)))
        HGOTO_ERROR(H5E_OHDR, H5E_CANTGET, NULL, "unable to get object location from object")

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5O_group_get_oloc() */


/*-------------------------------------------------------------------------
 * Function:    H5O_group_bh_info
 *
 * Purpose:     Retrieve storage for 1.8 btree and heap
 *		Retrieve storage for 1.6 btree and heap via H5G_stab_bh_info()
 *
 * Return:      Non-negative on success/Negative on failure
 *
 * Programmer:  Vailin Choi
 *              July 12 2007
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5O_group_bh_info(H5F_t *f, hid_t dxpl_id, H5O_t *oh, H5_ih_info_t *bh_info)
{
    H5O_linfo_t         linfo;          	/* Link info message */
    H5HF_t              *fheap = NULL;          /* Fractal heap handle */
    herr_t      	ret_value = SUCCEED;  	/* Return value */

    FUNC_ENTER_NOAPI(H5O_group_bh_info, FAIL)

    /* Sanity check */
    HDassert(f);
    HDassert(oh);
    HDassert(bh_info);

    /* Check for "new style" group info */
    if(NULL == H5O_msg_read_real(f, dxpl_id, oh, H5O_LINFO_ID, &linfo)) { 
        H5O_stab_t          stab;       	/* Info about symbol table */

        /* Must be "old style" group, clear error stack */
	H5E_clear_stack(NULL);

        /* Get symbol table message */
        if(NULL == H5O_msg_read_real(f, dxpl_id, oh, H5O_STAB_ID, &stab))
	    HGOTO_ERROR(H5E_SYM, H5E_CANTGET, FAIL, "can't find LINFO nor STAB messages")
	
        /* Get symbol table size info */
        if(H5G_stab_bh_size(f, dxpl_id, &stab, bh_info) < 0)
            HGOTO_ERROR(H5E_SYM, H5E_CANTGET, FAIL, "can't retrieve symbol table size info")
    } /* end if */
    else { /* LINFO */
        /* Get creation order B-tree size, if available */
	if(H5F_addr_defined(linfo.corder_bt2_addr))
            if(H5B2_iterate_size(f, dxpl_id, H5G_BT2_CORDER, linfo.corder_bt2_addr, &bh_info->index_size) < 0)
                HGOTO_ERROR(H5E_BTREE, H5E_CANTGET, FAIL, "can't retrieve B-tree storage info")

        /* Get name order B-tree size, if available */
        if(H5F_addr_defined(linfo.name_bt2_addr))
            if(H5B2_iterate_size(f, dxpl_id, H5G_BT2_NAME, linfo.name_bt2_addr, &bh_info->index_size) < 0)
                HGOTO_ERROR(H5E_BTREE, H5E_CANTGET, FAIL, "can't retrieve B-tree storage info")

        /* Get fractal heap size, if available */
        if(H5F_addr_defined(linfo.fheap_addr)) {
            /* Open the fractal heap for links */
            if(NULL == (fheap = H5HF_open(f, dxpl_id, linfo.fheap_addr)))
                HGOTO_ERROR(H5E_HEAP, H5E_CANTOPENOBJ, FAIL, "unable to open fractal heap")

            /* Get heap storage size */
            if(H5HF_size(fheap, dxpl_id, &bh_info->heap_size) < 0)
                HGOTO_ERROR(H5E_HEAP, H5E_CANTGET, FAIL, "can't retrieve fractal heap storage info")

            /* Release the fractal heap */
            if(H5HF_close(fheap, dxpl_id) < 0)
                HGOTO_ERROR(H5E_HEAP, H5E_CLOSEERROR, FAIL, "can't close fractal heap")
            fheap = NULL;
        } /* end if */
    } /* end else */

done:
    /* Release resources */
    if(fheap && H5HF_close(fheap, dxpl_id) < 0)
        HDONE_ERROR(H5E_HEAP, H5E_CLOSEERROR, FAIL, "can't close fractal heap")

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5O_group_bh_info() */

