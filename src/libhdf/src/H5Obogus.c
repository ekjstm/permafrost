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

/*-------------------------------------------------------------------------
 *
 * Created:             H5Obogus.c
 *                      Jan 21 2003
 *                      Quincey Koziol <koziol@ncsa.uiuc.edu>
 *
 * Purpose:             "bogus" message.  This message is guaranteed to never
 *                      be found in a valid HDF5 file and is only used to
 *                      generate a test file which verifies the library's
 *                      correct operation when parsing unknown object header
 *                      messages.
 *
 *-------------------------------------------------------------------------
 */

#define H5O_PACKAGE		/*suppress error about including H5Opkg	  */

#include "H5private.h"		/* Generic Functions			*/
#include "H5Eprivate.h"		/* Error handling		  	*/
#include "H5MMprivate.h"	/* Memory management			*/
#include "H5Opkg.h"             /* Object headers			*/

#ifdef H5O_ENABLE_BOGUS

/* PRIVATE PROTOTYPES */
static void *H5O_bogus_decode(H5F_t *f, hid_t dxpl_id, unsigned mesg_flags, const uint8_t *p);
static herr_t H5O_bogus_encode(H5F_t *f, hbool_t disable_shared, uint8_t *p, const void *_mesg);
static size_t H5O_bogus_size(const H5F_t *f, hbool_t disable_shared, const void *_mesg);
static herr_t H5O_bogus_debug(H5F_t *f, hid_t dxpl_id, const void *_mesg, FILE * stream,
			     int indent, int fwidth);

/* This message derives from H5O message class */
const H5O_msg_class_t H5O_MSG_BOGUS[1] = {{
    H5O_BOGUS_ID,            	/*message id number             */
    "bogus",                 	/*message name for debugging    */
    0,     	                /*native message size           */
    0,				/* messages are sharable?       */
    H5O_bogus_decode,        	/*decode message                */
    H5O_bogus_encode,        	/*encode message                */
    NULL,          	        /*copy the native value         */
    H5O_bogus_size,          	/*raw message size              */
    NULL,         	        /*free internal memory          */
    NULL,		        /*free method			*/
    NULL,		        /* file delete method		*/
    NULL,			/* link method			*/
    NULL,			/*set share method		*/
    NULL,		    	/*can share method		*/
    NULL,			/* pre copy native value to file */
    NULL,			/* copy native value to file    */
    NULL,			/* post copy native value to file    */
    NULL,			/* get creation index		*/
    NULL,			/* set creation index		*/
    H5O_bogus_debug         	/*debug the message             */
}};


/*-------------------------------------------------------------------------
 * Function:    H5O_bogus_decode
 *
 * Purpose:     Decode a "bogus" message and return a pointer to a new
 *              native message struct.
 *
 * Return:      Success:        Ptr to new message in native struct.
 *
 *              Failure:        NULL
 *
 * Programmer:  Quincey Koziol
 *              koziol@ncsa.uiuc.edu
 *              Jan 21 2003
 *
 *-------------------------------------------------------------------------
 */
static void *
H5O_bogus_decode(H5F_t UNUSED *f, hid_t UNUSED dxpl_id, unsigned UNUSED mesg_flags,
    const uint8_t *p)
{
    H5O_bogus_t *mesg = NULL;
    void *ret_value;            /* Return value */

    FUNC_ENTER_NOAPI_NOINIT(H5O_bogus_decode)

    /* check args */
    HDassert(f);
    HDassert(p);

    /* Allocate the bogus message */
    if(NULL == (mesg = H5MM_calloc(sizeof(H5O_bogus_t))))
	HGOTO_ERROR(H5E_RESOURCE, H5E_NOSPACE, NULL, "memory allocation failed")

    /* decode */
    UINT32DECODE(p, mesg->u);

    /* Validate the bogus info */
    if(mesg->u != H5O_BOGUS_VALUE)
	HGOTO_ERROR(H5E_OHDR, H5E_BADVALUE, NULL, "invalid bogus value :-)")

    /* Set return value */
    ret_value = mesg;

done:
    if(ret_value == NULL && mesg != NULL)
        H5MM_xfree(mesg);

    FUNC_LEAVE_NOAPI(ret_value)
} /* end H5O_bogus_decode() */


/*-------------------------------------------------------------------------
 * Function:    H5O_bogus_encode
 *
 * Purpose:     Encodes a "bogus" message.
 *
 * Return:      Non-negative on success/Negative on failure
 *
 * Programmer:  Quincey Koziol
 *              koziol@ncsa.uiuc.edu
 *              Jan 21 2003
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5O_bogus_encode(H5F_t UNUSED *f, hbool_t UNUSED disable_shared, uint8_t *p, const void UNUSED *mesg)
{
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5O_bogus_encode)

    /* check args */
    HDassert(f);
    HDassert(p);
    HDassert(mesg);

    /* encode */
    UINT32ENCODE(p, H5O_BOGUS_VALUE);

    FUNC_LEAVE_NOAPI(SUCCEED)
} /* end H5O_bogus_encode() */


/*-------------------------------------------------------------------------
 * Function:    H5O_bogus_size
 *
 * Purpose:     Returns the size of the raw message in bytes not
 *              counting the message typ or size fields, but only the data
 *              fields.  This function doesn't take into account
 *              alignment.
 *
 * Return:      Success:        Message data size in bytes w/o alignment.
 *
 *              Failure:        Negative
 *
 * Programmer:  Quincey Koziol
 *              koziol@ncsa.uiuc.edu
 *              Jan 21 2003
 *
 *-------------------------------------------------------------------------
 */
static size_t
H5O_bogus_size(const H5F_t UNUSED *f, hbool_t UNUSED disable_shared, const void UNUSED *mesg)
{
    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5O_bogus_size)

    FUNC_LEAVE_NOAPI(4)
} /* end H5O_bogus_size() */


/*-------------------------------------------------------------------------
 * Function:    H5O_bogus_debug
 *
 * Purpose:     Prints debugging info for the message.
 *
 * Return:      Non-negative on success/Negative on failure
 *
 * Programmer:  Quincey Koziol
 *              koziol@ncsa.uiuc.edu
 *              Jan 21 2003
 *
 * Modifications:
 *
 *-------------------------------------------------------------------------
 */
static herr_t
H5O_bogus_debug(H5F_t UNUSED *f, hid_t UNUSED dxpl_id, const void *_mesg, FILE *stream,
	       int indent, int fwidth)
{
    const H5O_bogus_t	*mesg = (const H5O_bogus_t *)_mesg;

    FUNC_ENTER_NOAPI_NOINIT_NOFUNC(H5O_bogus_debug)

    /* check args */
    HDassert(f);
    HDassert(mesg);
    HDassert(stream);
    HDassert(indent >= 0);
    HDassert(fwidth >= 0);

    HDfprintf(stream, "%*s%-*s `%u'\n", indent, "", fwidth,
            "Bogus Value:", mesg->u);

    FUNC_LEAVE_NOAPI(SUCCEED)
} /* end H5O_bogus_debug() */
#endif /* H5O_ENABLE_BOGUS */

