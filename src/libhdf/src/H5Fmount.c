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

#define H5F_PACKAGE		/*suppress error about including H5Fpkg	  */

/* Interface initialization */
#define H5_INTERFACE_INIT_FUNC	H5F_init_mount_interface


/* Packages needed by this file... */
#include "H5private.h"		/* Generic Functions			*/
#include "H5Eprivate.h"		/* Error handling		  	*/
#include "H5Fpkg.h"             /* File access				*/
#include "H5Gprivate.h"		/* Groups				*/
#include "H5Iprivate.h"		/* IDs			  		*/
#include "H5Pprivate.h"		/* Property lists			*/
#include "H5MMprivate.h"	/* Memory management			*/

/* PRIVATE PROTOTYPES */
static herr_t H5F_mount(H5G_loc_t *loc, const char *name, H5F_t *child,
    hid_t plist_id, hid_t dxpl_id);
static herr_t H5F_unmount(H5G_loc_t *loc, const char *name, hid_t dxpl_id);
static void H5F_mount_count_ids_recurse(H5F_t *f, unsigned *nopen_files, unsigned *nopen_objs);


/*--------------------------------------------------------------------------
NAME
   H5F_init_mount_interface -- Initialize interface-specific information
USAGE
    herr_t H5F_init_mount_interface()

RETURNS
    Non-negative on success/Negative on failure
DESCRIPTION
    Initializes any interface-specific data or routines.  (Just calls
    H5F_init() currently).

--------------------------------------------------------------------------*/
static herr_t
H5F_init_mount_interface(void)
{
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5F_init_mount_interface)

    FUNC_LEAVE_NOAPI(H5F_init())
} /* H5F_init_mount_interface() */


/*-------------------------------------------------------------------------
 * Function:	H5F_close_mounts
 *
 * Purpose:	Close all mounts for a given file
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Quincey Koziol
 *              Saturday, July  2, 2005
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5F_close_mounts(H5F_t *f)
{
    unsigned u;                 /* Local index */
    herr_t ret_value=SUCCEED;   /* Return value */

    FUNC_ENTER_NOAPI(H5F_close_mounts, FAIL)

    HDassert(f);

    /* Unmount all child files */
    for (u = 0; u < f->mtab.nmounts; u++) {
        /* Detach the child file from the parent file */
        f->mtab.child[u].file->mtab.parent = NULL;

        /* Close the internal group maintaining the mount point */
        if(H5G_close(f->mtab.child[u].group) < 0)
            HGOTO_ERROR(H5E_FILE, H5E_CANTCLOSEOBJ, FAIL, "can't close child group")

        /* Close the child file */
        if(H5F_try_close(f->mtab.child[u].file) < 0)
            HGOTO_ERROR(H5E_FILE, H5E_CANTCLOSEFILE, FAIL, "can't close child file")
    } /* end if */
    f->mtab.nmounts = 0;

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5F_close_mounts() */


/*-------------------------------------------------------------------------
 * Function:	H5F_mount
 *
 * Purpose:	Mount file CHILD onto the group specified by LOC and NAME,
 *		using mount properties in PLIST.  CHILD must not already be
 *		mouted and must not be a mount ancestor of the mount-point.
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Robb Matzke
 *              Tuesday, October  6, 1998
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5F_mount(H5G_loc_t *loc, const char *name, H5F_t *child,
	  hid_t UNUSED plist_id, hid_t dxpl_id)
{
    H5G_t	*mount_point = NULL;	/*mount point group		*/
    H5F_t	*ancestor = NULL;	/*ancestor files		*/
    H5F_t	*parent = NULL;		/*file containing mount point	*/
    unsigned	lt, rt, md;		/*binary search indices		*/
    int		cmp;			/*binary search comparison value*/
    H5G_loc_t   mp_loc;                 /* entry of moint point to be opened */
    H5G_name_t  mp_path;            	/* Mount point group hier. path */
    H5O_loc_t   mp_oloc;            	/* Mount point object location */
    H5G_loc_t   root_loc;               /* Group location of root of file to mount */
    herr_t	ret_value = SUCCEED;	/*return value			*/

    FUNC_ENTER_NOAPI_NOINIT(H5F_mount)

    HDassert(loc);
    HDassert(name && *name);
    HDassert(child);
    HDassert(TRUE == H5P_isa_class(plist_id, H5P_FILE_MOUNT));

    /* Set up dataset location to fill in */
    mp_loc.oloc = &mp_oloc;
    mp_loc.path = &mp_path;
    H5G_loc_reset(&mp_loc);

    /*
     * Check that the child isn't mounted, that the mount point exists, that
     * the mount point wasn't reached via external link, that
     * the parent & child files have the same file close degree, and
     * that the mount wouldn't introduce a cycle in the mount tree.
     */
    if(child->mtab.parent)
        HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "file is already mounted")
    if(H5G_loc_find(loc, name, &mp_loc/*out*/, H5P_DEFAULT, dxpl_id) < 0)
        HGOTO_ERROR(H5E_SYM, H5E_NOTFOUND, FAIL, "group not found")
    /* If the mount location is holding its file open, that file will close
     * and remove the mount as soon as we exit this function.  Prevent the
     * user from doing this.
     */
    if(mp_loc.oloc->holding_file != FALSE)
        HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "mount path cannot contain links to external files")    

    /* Open the mount point group */
    if(NULL == (mount_point = H5G_open(&mp_loc, dxpl_id)))
        HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "mount point not found")

    /* Retrieve information from the mount point group */
    /* (Some of which we had before but was reset in mp_loc when the group
     *  "took over" the group location - QAK)
     */
    parent = H5G_fileof(mount_point);
    HDassert(parent);
    mp_loc.oloc = H5G_oloc(mount_point);
    HDassert(mp_loc.oloc);
    mp_loc.path = H5G_nameof(mount_point);
    HDassert(mp_loc.path);
    for(ancestor = parent; ancestor; ancestor = ancestor->mtab.parent) {
	if(ancestor == child)
	    HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "mount would introduce a cycle")
    } /* end for */

    /* Make certain that the parent & child files have the same "file close degree" */
    if(parent->shared->fc_degree != child->shared->fc_degree)
        HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "mounted file has different file close degree than parent")

    /*
     * Use a binary search to locate the position that the child should be
     * inserted into the parent mount table.  At the end of this paragraph
     * `md' will be the index where the child should be inserted.
     */
    lt = md = 0;
    rt = parent->mtab.nmounts;
    cmp = -1;
    while(lt < rt && cmp) {
        H5O_loc_t	*oloc;		/*temporary symbol table entry	*/

	md = (lt + rt) / 2;
	oloc = H5G_oloc(parent->mtab.child[md].group);
	cmp = H5F_addr_cmp(mp_loc.oloc->addr, oloc->addr);
	if(cmp < 0)
	    rt = md;
	else if(cmp > 0)
	    lt = md + 1;
    } /* end while */
    if(cmp > 0)
        md++;
    if(!cmp)
	HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "mount point is already in use")

    /* Make room in the table */
    if(parent->mtab.nmounts >= parent->mtab.nalloc) {
	unsigned n = MAX(16, 2 * parent->mtab.nalloc);
	H5F_mount_t *x = H5MM_realloc(parent->mtab.child,
				      n * sizeof(parent->mtab.child[0]));
	if(!x)
	    HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, FAIL, "memory allocation failed for mount table")
	parent->mtab.child = x;
	parent->mtab.nalloc = n;
    } /* end if */

    /* Insert into table */
    HDmemmove(parent->mtab.child + md + 1, parent->mtab.child + md,
            (parent->mtab.nmounts-md) * sizeof(parent->mtab.child[0]));
    parent->mtab.nmounts++;
    parent->mtab.child[md].group = mount_point;
    parent->mtab.child[md].file = child;
    child->mtab.parent = parent;

    /* Set the group's mountpoint flag */
    if(H5G_mount(parent->mtab.child[md].group) < 0)
        HGOTO_ERROR(H5E_FILE, H5E_CANTCLOSEOBJ, FAIL, "unable to set group mounted flag")

    /* Get the group location for the root group in the file to unmount */
    if(NULL == (root_loc.oloc = H5G_oloc(child->shared->root_grp)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "unable to get object location for root group")
    if(NULL == (root_loc.path = H5G_nameof(child->shared->root_grp)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "unable to get path for root group")

    /* Search the open IDs and replace names for mount operation */
    /* We pass H5G_UNKNOWN as object type; search all IDs */
    if(H5G_name_replace(NULL, H5G_NAME_MOUNT, mp_loc.oloc->file,
            mp_loc.path->full_path_r, root_loc.oloc->file, root_loc.path->full_path_r,
            dxpl_id) < 0)
	HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "unable to replace name")

done:
    if(ret_value < 0) {
        if(mount_point) {
            if(H5G_close(mount_point) < 0)
                HDONE_ERROR(H5E_FILE, H5E_CANTCLOSEOBJ, FAIL, "unable to close mounted group")
        } /* end if */
        else {
            if(H5G_loc_free(&mp_loc) < 0)
                HDONE_ERROR(H5E_SYM, H5E_CANTRELEASE, FAIL, "unable to free mount location")
        } /* end else */
    } /* end if */

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5F_mount() */


/*-------------------------------------------------------------------------
 * Function:	H5F_unmount
 *
 * Purpose:	Unmount the child which is mounted at the group specified by
 *		LOC and NAME or fail if nothing is mounted there.  Neither
 *		file is closed.
 *
 *		Because the mount point is specified by name and opened as a
 *		group, the H5G_namei() will resolve it to the root of the
 *		mounted file, not the group where the file is mounted.
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Robb Matzke
 *              Tuesday, October  6, 1998
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5F_unmount(H5G_loc_t *loc, const char *name, hid_t dxpl_id)
{
    H5G_t	*child_group = NULL;	/* Child's group in parent mtab	*/
    H5F_t	*child = NULL;		/*mounted file			*/
    H5F_t	*parent = NULL;		/*file where mounted		*/
    H5O_loc_t   *mnt_oloc;            	/* symbol table entry for root of mounted file */
    H5G_name_t  mp_path;            	/* Mount point group hier. path */
    H5O_loc_t   mp_oloc;            	/* Mount point object location  */
    H5G_loc_t   mp_loc;                 /* entry used to open mount point*/
    hbool_t     mp_loc_setup = FALSE;   /* Whether mount point location is set up */
    H5G_loc_t   root_loc;               /* Group location of root of file to unmount */
    int         child_idx;              /* Index of child in parent's mtab */
    herr_t	ret_value = SUCCEED;	/*return value			*/

    FUNC_ENTER_NOAPI_NOINIT(H5F_unmount)

    HDassert(loc);
    HDassert(name && *name);

    /* Set up mount point location to fill in */
    mp_loc.oloc = &mp_oloc;
    mp_loc.path = &mp_path;
    H5G_loc_reset(&mp_loc);

    /*
     * Get the mount point, or more precisely the root of the mounted file.
     * If we get the root group and the file has a parent in the mount tree,
     * then we must have found the mount point.
     */
    if(H5G_loc_find(loc, name, &mp_loc/*out*/, H5P_DEFAULT, dxpl_id) < 0)
        HGOTO_ERROR(H5E_SYM, H5E_NOTFOUND, FAIL, "group not found")
    mp_loc_setup = TRUE;
    child = mp_loc.oloc->file;
    mnt_oloc = H5G_oloc(child->shared->root_grp);
    child_idx = -1;

    if(child->mtab.parent && H5F_addr_eq(mp_oloc.addr, mnt_oloc->addr)) {
        unsigned	u;		/*counters			*/

	/*
	 * We've been given the root group of the child.  We do a reverse
	 * lookup in the parent's mount table to find the correct entry.
	 */
	parent = child->mtab.parent;
	for(u = 0; u < parent->mtab.nmounts; u++) {
	    if(parent->mtab.child[u].file == child) {
                /* Found the correct index */
                child_idx = u;
                break;
	    } /* end if */
	} /* end for */
    } else {
        unsigned lt, rt, md = 0;        /*binary search indices		*/
        int 	cmp;		        /*binary search comparison value*/

	/*
	 * We've been given the mount point in the parent.  We use a binary
	 * search in the parent to locate the mounted file, if any.
	 */
	parent = child; /*we guessed wrong*/
	lt = 0;
        rt = parent->mtab.nmounts;
	cmp = -1;
	while(lt < rt && cmp) {
	    md = (lt + rt) / 2;
	    mnt_oloc = H5G_oloc(parent->mtab.child[md].group);
	    cmp = H5F_addr_cmp(mp_oloc.addr, mnt_oloc->addr);
	    if (cmp<0)
		rt = md;
	    else
		lt = md + 1;
	} /* end while */
	if(cmp)
	    HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "not a mount point")

        /* Found the correct index, set the info about the child */
        child_idx = md;
        H5G_loc_free(&mp_loc);
        mp_loc_setup = FALSE;
        mp_loc.oloc = mnt_oloc;
        mp_loc.path = H5G_nameof(parent->mtab.child[md].group);
        child = parent->mtab.child[child_idx].file;
    } /* end else */
    HDassert(child_idx >= 0);

    /* Save the information about the child from the mount table */
    child_group = parent->mtab.child[child_idx].group;

    /* Get the group location for the root group in the file to unmount */
    if(NULL == (root_loc.oloc = H5G_oloc(child->shared->root_grp)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "unable to get object location for root group")
    if(NULL == (root_loc.path = H5G_nameof(child->shared->root_grp)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "unable to get path for root group")

    /* Search the open IDs replace names to reflect unmount operation */
    if(H5G_name_replace(NULL, H5G_NAME_UNMOUNT, mp_loc.oloc->file,
            mp_loc.path->full_path_r, root_loc.oloc->file, root_loc.path->full_path_r,
            dxpl_id) < 0)
        HGOTO_ERROR(H5E_SYM, H5E_CANTINIT, FAIL, "unable to replace name")

    /* Eliminate the mount point from the table */
    HDmemmove(parent->mtab.child + child_idx, parent->mtab.child + child_idx + 1,
            (parent->mtab.nmounts-child_idx) * sizeof(parent->mtab.child[0]));
    parent->mtab.nmounts -= 1;

    /* Unmount the child file from the parent file */
    if(H5G_unmount(child_group) < 0)
        HGOTO_ERROR(H5E_FILE, H5E_CANTCLOSEOBJ, FAIL, "unable to reset group mounted flag")
    if(H5G_close(child_group) < 0)
        HGOTO_ERROR(H5E_FILE, H5E_CANTCLOSEOBJ, FAIL, "unable to close unmounted group")

    /* Detach child file from parent & see if it should close */
    child->mtab.parent = NULL;
    if(H5F_try_close(child) < 0)
        HGOTO_ERROR(H5E_FILE, H5E_CANTCLOSEFILE, FAIL, "unable to close unmounted file")

done:
    /* Free the mount point location's information, if it's been set up */
    if(mp_loc_setup)
        H5G_loc_free(&mp_loc);

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5F_unmount() */


/*-------------------------------------------------------------------------
 * Function:	H5F_has_mount
 *
 * Purpose:	Check if a file has mounted files within it.
 *
 * Return:	Success:	TRUE/FALSE
 *		Failure:	(can't happen)
 *
 * Programmer:	Quincey Koziol
 *              Thursday, January  2, 2002
 *
 *-------------------------------------------------------------------------
 */
hbool_t
H5F_has_mount(const H5F_t *file)
{
    hbool_t ret_value;   /* Return value */

    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5F_has_mount)

    HDassert(file);

    if(file->mtab.nmounts > 0)
        ret_value = TRUE;
    else
        ret_value = FALSE;

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5F_has_mount() */


/*-------------------------------------------------------------------------
 * Function:	H5F_is_mount
 *
 * Purpose:	Check if a file is mounted within another file.
 *
 * Return:	Success:	TRUE/FALSE
 *		Failure:	(can't happen)
 *
 * Programmer:	Quincey Koziol
 *              Thursday, January  2, 2002
 *
 *-------------------------------------------------------------------------
 */
hbool_t
H5F_is_mount(const H5F_t *file)
{
    hbool_t ret_value;   /* Return value */

    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5F_is_mount)

    HDassert(file);

    if(file->mtab.parent != NULL)
        ret_value = TRUE;
    else
        ret_value = FALSE;

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5F_is_mount() */


/*-------------------------------------------------------------------------
 * Function:	H5Fmount
 *
 * Purpose:	Mount file CHILD_ID onto the group specified by LOC_ID and
 *		NAME using mount properties PLIST_ID.
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Robb Matzke
 *              Tuesday, October  6, 1998
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5Fmount(hid_t loc_id, const char *name, hid_t child_id, hid_t plist_id)
{
    H5G_loc_t	loc;
    H5F_t	*child = NULL;
    herr_t      ret_value=SUCCEED;       /* Return value */

    FUNC_ENTER_API(H5Fmount, FAIL)
    H5TRACE4("e", "i*sii", loc_id, name, child_id, plist_id);

    /* Check arguments */
    if(H5G_loc(loc_id, &loc) < 0)
	HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not a location")
    if(!name || !*name)
	HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "no name")
    if(NULL == (child = H5I_object_verify(child_id,H5I_FILE)))
	HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not a file")
    if(H5P_DEFAULT == plist_id)
        plist_id = H5P_FILE_MOUNT_DEFAULT;
    else
        if(TRUE != H5P_isa_class(plist_id, H5P_FILE_MOUNT))
            HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not property list")

    /* Do the mount */
    if(H5F_mount(&loc, name, child, plist_id, H5AC_dxpl_id) < 0)
	HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "unable to mount file")

done:
    FUNC_LEAVE_API(ret_value)
} /* end H5Fmount() */


/*-------------------------------------------------------------------------
 * Function:	H5Funmount
 *
 * Purpose:	Given a mount point, dissassociate the mount point's file
 *		from the file mounted there.  Do not close either file.
 *
 *		The mount point can either be the group in the parent or the
 *		root group of the mounted file (both groups have the same
 *		name).  If the mount point was opened before the mount then
 *		it's the group in the parent, but if it was opened after the
 *		mount then it's the root group of the child.
 *
 * Return:	Non-negative on success/Negative on failure
 *
 * Programmer:	Robb Matzke
 *              Tuesday, October  6, 1998
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5Funmount(hid_t loc_id, const char *name)
{
    H5G_loc_t	loc;
    herr_t      ret_value=SUCCEED;       /* Return value */

    FUNC_ENTER_API(H5Funmount, FAIL)
    H5TRACE2("e", "i*s", loc_id, name);

    /* Check args */
    if(H5G_loc(loc_id, &loc) < 0)
	HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not a location")
    if(!name || !*name)
	HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "no name")

    /* Unmount */
    if (H5F_unmount(&loc, name, H5AC_dxpl_id) < 0)
	HGOTO_ERROR(H5E_FILE, H5E_MOUNT, FAIL, "unable to unmount file")

done:
    FUNC_LEAVE_API(ret_value)
} /* end H5Funmount() */


/*-------------------------------------------------------------------------
 * Function:	H5F_mount_count_ids_recurse
 *
 * Purpose:	Helper routine for counting number of open IDs in mount
 *              hierarchy.
 *
 * Return:	<none>
 *
 * Programmer:	Quincey Koziol
 *              Tuesday, July 19, 2005
 *
 *-------------------------------------------------------------------------
 */
static void
H5F_mount_count_ids_recurse(H5F_t *f, unsigned *nopen_files, unsigned *nopen_objs)
{
    unsigned u;                         /* Local index value */

    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5F_mount_count_ids_recurse)

    /* Sanity check */
    HDassert(f);
    HDassert(nopen_files);
    HDassert(nopen_objs);

    /* If this file is still open, increment number of file IDs open */
    if(f->file_id > 0)
        *nopen_files += 1;

    /* Increment number of open objects in file
     * (Reduced by number of mounted files, we'll add back in the mount point's
     *  groups later, if they are open)
     */
    *nopen_objs += (f->nopen_objs - f->mtab.nmounts);

    /* Iterate over files mounted in this file and add in their open ID counts also */
    for(u = 0; u < f->mtab.nmounts; u++) {
        /* Increment the open object count if the mount point group has an open ID */
        if(H5G_get_shared_count(f->mtab.child[u].group) > 1)
            *nopen_objs += 1;

        H5F_mount_count_ids_recurse(f->mtab.child[u].file, nopen_files, nopen_objs);
    } /* end for */

    FUNC_LEAVE_NOAPI_VOID
} /* end H5F_mount_count_ids_recurse() */


/*-------------------------------------------------------------------------
 * Function:	H5F_mount_count_ids
 *
 * Purpose:	Count the number of open file & object IDs in a mount hierarchy
 *
 * Return:	SUCCEED/FAIL
 *
 * Programmer:	Quincey Koziol
 *              Tues, July 19, 2005
 *
 *-------------------------------------------------------------------------
 */
herr_t
H5F_mount_count_ids(H5F_t *f, unsigned *nopen_files, unsigned *nopen_objs)
{
    herr_t      ret_value = SUCCEED;       /* Return value */

    FUNC_ENTER_NOAPI(H5F_mount_count_ids, FAIL)

    /* Sanity check */
    HDassert(f);
    HDassert(nopen_files);
    HDassert(nopen_objs);

    /* Find the top file in the mounting hierarchy */
    while(f->mtab.parent)
        f = f->mtab.parent;

    /* Count open IDs in the hierarchy */
    H5F_mount_count_ids_recurse(f, nopen_files, nopen_objs);

done:
    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5F_mount_count_ids() */

