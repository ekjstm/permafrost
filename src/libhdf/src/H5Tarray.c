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

/*
 * Module Info: This module contains the functionality for array datatypes in
 *      the H5T interface.
 */

#define H5T_PACKAGE		/*suppress error about including H5Tpkg	  */

/* Interface initialization */
#define H5_INTERFACE_INIT_FUNC	H5T_init_array_interface


#include "H5private.h"		/* Generic Functions			*/
#include "H5Eprivate.h"		/* Error handling			*/
#include "H5Iprivate.h"		/* IDs					*/
#include "H5Tpkg.h"		/* Datatypes				*/


/*--------------------------------------------------------------------------
NAME
   H5T_init_array_interface -- Initialize interface-specific information
USAGE
    herr_t H5T_init_array_interface()

RETURNS
    Non-negative on success/Negative on failure
DESCRIPTION
    Initializes any interface-specific data or routines.  (Just calls
    H5T_init_iterface currently).

--------------------------------------------------------------------------*/
static herr_t
H5T_init_array_interface(void)
{
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5T_init_array_interface);

    FUNC_LEAVE_NOAPI(H5T_init());
} /* H5T_init_array_interface() */


/*-------------------------------------------------------------------------
 * Function:	H5Tarray_create2
 *
 * Purpose:	Create a new array data type based on the specified BASE_TYPE.
 *		The type is an array with NDIMS dimensionality and the size of the
 *      array is DIMS. The total member size should be relatively small.
 *      Array datatypes are currently limited to H5S_MAX_RANK number of
 *      dimensions and must have the number of dimensions set greater than
 *      0. (i.e. 0 > ndims <= H5S_MAX_RANK)  All dimensions sizes must be greater
 *      than 0 also.
 *
 * Return:	Success:	ID of new array data type
 *		Failure:	Negative
 *
 * Programmer:	Quincey Koziol
 *              Thursday, Oct 17, 2007
 *
 *-------------------------------------------------------------------------
 */
hid_t
H5Tarray_create2(hid_t base_id, unsigned ndims, const hsize_t dim[/* ndims */])
{
    H5T_t	*base;		/* base data type	*/
    H5T_t	*dt;		/* new array data type	*/
    unsigned    u;              /* local index variable */
    hid_t	ret_value;	/* return value	*/

    FUNC_ENTER_API(H5Tarray_create2, FAIL)
    H5TRACE3("i", "iIu*h", base_id, ndims, dim);

    /* Check args */
    if(ndims < 1 || ndims > H5S_MAX_RANK)
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "invalid dimensionality")
    if(!dim)
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "no dimensions specified")
    for(u = 0; u < ndims; u++)
        if(!(dim[u] > 0))
            HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "zero-sized dimension specified")
    if(NULL == (base = H5I_object_verify(base_id, H5I_DATATYPE)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not an valid base datatype")

    /* Create the actual array datatype */
    if((dt = H5T_array_create(base, ndims, dim)) == NULL)
        HGOTO_ERROR(H5E_DATATYPE, H5E_CANTREGISTER, FAIL, "unable to create datatype")

    /* Atomize the type */
    if((ret_value = H5I_register(H5I_DATATYPE, dt)) < 0)
        HGOTO_ERROR(H5E_DATATYPE, H5E_CANTREGISTER, FAIL, "unable to register datatype")

done:
    FUNC_LEAVE_API(ret_value)
}   /* end H5Tarray_create2() */


/*-------------------------------------------------------------------------
 * Function:	H5T_array_create
 *
 * Purpose:	Internal routine to create a new array data type based on the
 *      specified BASE_TYPE.  The type is an array with NDIMS dimensionality
 *      and the size of the array is DIMS.
 *      Array datatypes are currently limited to H5S_MAX_RANK number
 *      of dimensions.
 *
 * Return:	Success:	ID of new array data type
 *		Failure:	Negative
 *
 * Programmer:	Quincey Koziol
 *              Thursday, Oct 26, 2000
 *
 *-------------------------------------------------------------------------
 */
H5T_t *
H5T_array_create(H5T_t *base, unsigned ndims, const hsize_t dim[/* ndims */])
{
    H5T_t	*ret_value;	/* new array data type	*/
    unsigned    u;              /* local index variable */

    FUNC_ENTER_NOAPI(H5T_array_create, NULL)

    HDassert(base);
    HDassert(ndims <= H5S_MAX_RANK);
    HDassert(dim);

    /* Build new type */
    if(NULL == (ret_value = H5T_alloc()))
        HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, NULL, "memory allocation failed")
    ret_value->shared->type = H5T_ARRAY;

    /* Copy the base type of the array */
    ret_value->shared->parent = H5T_copy(base, H5T_COPY_ALL);

    /* Set the array parameters */
    ret_value->shared->u.array.ndims = ndims;

    /* Copy the array dimensions & compute the # of elements in the array */
    for(u = 0, ret_value->shared->u.array.nelem = 1; u < ndims; u++) {
        H5_ASSIGN_OVERFLOW(ret_value->shared->u.array.dim[u], dim[u], hsize_t, size_t);
        ret_value->shared->u.array.nelem *= (size_t)dim[u];
    } /* end for */

    /* Set the array's size (number of elements * element datatype's size) */
    ret_value->shared->size = ret_value->shared->parent->shared->size * ret_value->shared->u.array.nelem;

    /* Set the "force conversion" flag if the base datatype indicates */
    if(base->shared->force_conv == TRUE)
        ret_value->shared->force_conv = TRUE;

    /* Array datatypes need a later version of the datatype object header message */
    ret_value->shared->version = MAX(base->shared->version, H5O_DTYPE_VERSION_2);

done:
    FUNC_LEAVE_NOAPI(ret_value)
}   /* end H5T_array_create */


/*-------------------------------------------------------------------------
 * Function:	H5Tget_array_ndims
 *
 * Purpose:	Query the number of dimensions for an array datatype.
 *
 * Return:	Success:	Number of dimensions of the array datatype
 *		Failure:	Negative
 *
 * Programmer:	Quincey Koziol
 *              Monday, November 6, 2000
 *
 *-------------------------------------------------------------------------
 */
int
H5Tget_array_ndims(hid_t type_id)
{
    H5T_t *dt;		    /* pointer to array data type	*/
    int	ret_value;	    /* return value			*/

    FUNC_ENTER_API(H5Tget_array_ndims, FAIL)
    H5TRACE1("Is", "i", type_id);

    /* Check args */
    if(NULL == (dt = H5I_object_verify(type_id,H5I_DATATYPE)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not a datatype object")
    if(dt->shared->type != H5T_ARRAY)
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not an array datatype")

    /* Retrieve the number of dimensions */
    ret_value = H5T_get_array_ndims(dt);

done:
    FUNC_LEAVE_API(ret_value)
}   /* end H5Tget_array_ndims */


/*-------------------------------------------------------------------------
 * Function:	H5T_get_array_ndims
 *
 * Purpose:	Private function for H5T_get_array_ndims.  Query the number
 *              of dimensions for an array datatype.
 *
 * Return:	Success:	Number of dimensions of the array datatype
 *		Failure:	Negative
 *
 * Programmer:	Raymond Lu
 *              October 10, 2002
 *
 *-------------------------------------------------------------------------
 */
int
H5T_get_array_ndims(const H5T_t *dt)
{
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5T_get_array_ndims)

    HDassert(dt);
    HDassert(dt->shared->type == H5T_ARRAY);

    /* Retrieve the number of dimensions */
    FUNC_LEAVE_NOAPI(dt->shared->u.array.ndims)
}   /* end H5T_get_array_ndims */


/*-------------------------------------------------------------------------
 * Function:	H5Tget_array_dims2
 *
 * Purpose:	Query the sizes of dimensions for an array datatype.
 *
 * Return:	Success:	Number of dimensions of the array type
 *		Failure:	Negative
 *
 * Programmer:	Quincey Koziol
 *              Thursday, October 17, 2007
 *
 *-------------------------------------------------------------------------
 */
int
H5Tget_array_dims2(hid_t type_id, hsize_t dims[])
{
    H5T_t *dt;		/* pointer to array data type	*/
    int	ret_value;	/* return value			*/

    FUNC_ENTER_API(H5Tget_array_dims2, FAIL)
    H5TRACE2("Is", "i*h", type_id, dims);

    /* Check args */
    if(NULL == (dt = H5I_object_verify(type_id,H5I_DATATYPE)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not a datatype object")
    if(dt->shared->type != H5T_ARRAY)
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not an array datatype")

    /* Retrieve the sizes of the dimensions */
    if((ret_value = H5T_get_array_dims(dt, dims)) < 0)
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "unable to get dimension sizes")
done:
    FUNC_LEAVE_API(ret_value)
}   /* end H5Tget_array_dims2() */


/*-------------------------------------------------------------------------
 * Function:	H5T_get_array_dims
 *
 * Purpose:	Private function for H5T_get_array_dims.  Query the sizes
 *              of dimensions for an array datatype.
 *
 * Return:	Success:	Number of dimensions of the array type
 *		Failure:	Negative
 *
 * Programmer:  Raymond Lu
 *              October 10, 2002
 *
 *-------------------------------------------------------------------------
 */
int
H5T_get_array_dims(const H5T_t *dt, hsize_t dims[])
{
    unsigned u;         /* Local index variable */
    int	ret_value;	/* return value			*/

    FUNC_ENTER_NOAPI(H5T_get_array_dims, FAIL)

    HDassert(dt);
    HDassert(dt->shared->type == H5T_ARRAY);

    /* Retrieve the sizes of the dimensions */
    if(dims)
        for(u = 0; u < dt->shared->u.array.ndims; u++)
            dims[u] = dt->shared->u.array.dim[u];

    /* Pass along the array rank as the return value */
    ret_value = dt->shared->u.array.ndims;

done:
    FUNC_LEAVE_NOAPI(ret_value)
}   /* end H5T_get_array_dims */

#ifndef H5_NO_DEPRECATED_SYMBOLS

/*-------------------------------------------------------------------------
 * Function:	H5Tarray_create1
 *
 * Purpose:	Create a new array data type based on the specified BASE_TYPE.
 *		The type is an array with NDIMS dimensionality and the size of the
 *      array is DIMS. The total member size should be relatively small.
 *      Array datatypes are currently limited to H5S_MAX_RANK number of
 *      dimensions and must have the number of dimensions set greater than
 *      0. (i.e. 0 > ndims <= H5S_MAX_RANK)  All dimensions sizes must be greater
 *      than 0 also.
 *
 * Return:	Success:	ID of new array data type
 *		Failure:	Negative
 *
 * Programmer:	Quincey Koziol
 *              Thursday, Oct 26, 2000
 *
 *-------------------------------------------------------------------------
 */
hid_t
H5Tarray_create1(hid_t base_id, int ndims, const hsize_t dim[/* ndims */],
    const int UNUSED perm[/* ndims */])
{
    H5T_t	*base;		/* base data type	*/
    H5T_t	*dt;		/* new array data type	*/
    unsigned    u;              /* local index variable */
    hid_t	ret_value;	/* return value	*/

    FUNC_ENTER_API(H5Tarray_create1, FAIL)
    H5TRACE4("i", "iIs*h*Is", base_id, ndims, dim, perm);

    /* Check args */
    if(ndims < 1 || ndims > H5S_MAX_RANK)
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "invalid dimensionality")
    if(!dim)
        HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "no dimensions specified")
    for(u = 0; u < (unsigned)ndims; u++)
        if(!(dim[u] > 0))
            HGOTO_ERROR(H5E_ARGS, H5E_BADVALUE, FAIL, "zero-sized dimension specified")
    if(NULL == (base = H5I_object_verify(base_id, H5I_DATATYPE)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not an valid base datatype")

    /* Create the actual array datatype */
    if((dt = H5T_array_create(base, (unsigned)ndims, dim)) == NULL)
        HGOTO_ERROR(H5E_DATATYPE, H5E_CANTREGISTER, FAIL, "unable to create datatype")

    /* Atomize the type */
    if((ret_value = H5I_register(H5I_DATATYPE, dt)) < 0)
        HGOTO_ERROR(H5E_DATATYPE, H5E_CANTREGISTER, FAIL, "unable to register datatype")

done:
    FUNC_LEAVE_API(ret_value)
}   /* end H5Tarray_create1() */


/*-------------------------------------------------------------------------
 * Function:	H5Tget_array_dims1
 *
 * Purpose:	Query the sizes of dimensions for an array datatype.
 *
 * Return:	Success:	Number of dimensions of the array type
 *		Failure:	Negative
 *
 * Programmer:	Quincey Koziol
 *              Monday, November 6, 2000
 *
 *-------------------------------------------------------------------------
 */
int
H5Tget_array_dims1(hid_t type_id, hsize_t dims[], int UNUSED perm[])
{
    H5T_t *dt;		/* pointer to array data type	*/
    int	ret_value;	/* return value			*/

    FUNC_ENTER_API(H5Tget_array_dims1, FAIL)
    H5TRACE3("Is", "i*h*Is", type_id, dims, perm);

    /* Check args */
    if(NULL == (dt = H5I_object_verify(type_id,H5I_DATATYPE)))
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not a datatype object")
    if(dt->shared->type != H5T_ARRAY)
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "not an array datatype")

    /* Retrieve the sizes of the dimensions */
    if((ret_value = H5T_get_array_dims(dt, dims)) < 0)
        HGOTO_ERROR(H5E_ARGS, H5E_BADTYPE, FAIL, "unable to get dimension sizes")
done:
    FUNC_LEAVE_API(ret_value)
}   /* end H5Tget_array_dims1() */

#endif /* H5_NO_DEPRECATED_SYMBOLS */

