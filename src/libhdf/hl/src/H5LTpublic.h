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

#ifndef _H5LTpublic_H
#define _H5LTpublic_H

typedef enum H5LT_lang_t {
    H5LT_LANG_ERR = -1, /*this is the first*/
    H5LT_DDL      = 0,  /*for DDL*/
    H5LT_C        = 1,  /*for C*/
    H5LT_FORTRAN  = 2,  /*for Fortran*/
    H5LT_NO_LANG  = 3   /*this is the last*/
} H5LT_lang_t;

#ifdef __cplusplus
extern "C" {
#endif

/*-------------------------------------------------------------------------
 *
 * Make dataset functions
 *
 *-------------------------------------------------------------------------
 */

H5_HLDLL herr_t  H5LTmake_dataset( hid_t loc_id,
                         const char *dset_name,
                         int rank,
                         const hsize_t *dims,
                         hid_t type_id,
                         const void *buffer );

H5_HLDLL herr_t  H5LTmake_dataset_char( hid_t loc_id,
                              const char *dset_name,
                              int rank,
                              const hsize_t *dims,
                              const char *buffer );

H5_HLDLL herr_t  H5LTmake_dataset_short( hid_t loc_id,
                               const char *dset_name,
                               int rank,
                               const hsize_t *dims,
                               const short *buffer );

H5_HLDLL herr_t  H5LTmake_dataset_int( hid_t loc_id,
                             const char *dset_name,
                             int rank,
                             const hsize_t *dims,
                             const int *buffer );

H5_HLDLL herr_t  H5LTmake_dataset_long( hid_t loc_id,
                              const char *dset_name,
                              int rank,
                              const hsize_t *dims,
                              const long *buffer );

H5_HLDLL herr_t  H5LTmake_dataset_float( hid_t loc_id,
                               const char *dset_name,
                               int rank,
                               const hsize_t *dims,
                               const float *buffer );

H5_HLDLL herr_t  H5LTmake_dataset_double( hid_t loc_id,
                                const char *dset_name,
                                int rank,
                                const hsize_t *dims,
                                const double *buffer );

H5_HLDLL herr_t  H5LTmake_dataset_string( hid_t loc_id,
                               const char *dset_name,
                               const char *buf );


/*-------------------------------------------------------------------------
 *
 * Read dataset functions
 *
 *-------------------------------------------------------------------------
 */

H5_HLDLL herr_t  H5LTread_dataset( hid_t loc_id,
                         const char *dset_name,
                         hid_t type_id,
                         void *buffer );

H5_HLDLL herr_t  H5LTread_dataset_char( hid_t loc_id,
                              const char *dset_name,
                              char *buffer );

H5_HLDLL herr_t  H5LTread_dataset_short( hid_t loc_id,
                               const char *dset_name,
                               short *buffer );

H5_HLDLL herr_t  H5LTread_dataset_int( hid_t loc_id,
                             const char *dset_name,
                             int *buffer );

H5_HLDLL herr_t  H5LTread_dataset_long( hid_t loc_id,
                              const char *dset_name,
                              long *buffer );

H5_HLDLL herr_t  H5LTread_dataset_float( hid_t loc_id,
                               const char *dset_name,
                               float *buffer );

H5_HLDLL herr_t  H5LTread_dataset_double( hid_t loc_id,
                                const char *dset_name,
                                double *buffer );

H5_HLDLL herr_t  H5LTread_dataset_string( hid_t loc_id,
                                const char *dset_name,
                                char *buf );

/*-------------------------------------------------------------------------
 *
 * Query dataset functions
 *
 *-------------------------------------------------------------------------
 */


H5_HLDLL herr_t  H5LTget_dataset_ndims( hid_t loc_id,
                             const char *dset_name,
                             int *rank );

H5_HLDLL herr_t  H5LTget_dataset_info( hid_t loc_id,
                             const char *dset_name,
                             hsize_t *dims,
                             H5T_class_t *type_class,
                             size_t *type_size );

H5_HLDLL herr_t  H5LTfind_dataset( hid_t loc_id, const char *name );



/*-------------------------------------------------------------------------
 *
 * Set attribute functions
 *
 *-------------------------------------------------------------------------
 */


H5_HLDLL herr_t  H5LTset_attribute_string( hid_t loc_id,
                                 const char *obj_name,
                                 const char *attr_name,
                                 const char *attr_data );

H5_HLDLL herr_t  H5LTset_attribute_char( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               const char *buffer,
                               size_t size );

H5_HLDLL herr_t  H5LTset_attribute_uchar( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               const unsigned char *buffer,
                               size_t size );

H5_HLDLL herr_t  H5LTset_attribute_short( hid_t loc_id,
                              const char *obj_name,
                              const char *attr_name,
                              const short *buffer,
                              size_t size );

H5_HLDLL herr_t  H5LTset_attribute_ushort( hid_t loc_id,
                              const char *obj_name,
                              const char *attr_name,
                              const unsigned short *buffer,
                              size_t size );

H5_HLDLL herr_t  H5LTset_attribute_int( hid_t loc_id,
                              const char *obj_name,
                              const char *attr_name,
                              const int *buffer,
                              size_t size );

H5_HLDLL herr_t  H5LTset_attribute_uint( hid_t loc_id,
                              const char *obj_name,
                              const char *attr_name,
                              const unsigned int *buffer,
                              size_t size );

H5_HLDLL herr_t  H5LTset_attribute_long( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               const long *buffer,
                               size_t size );

H5_HLDLL herr_t  H5LTset_attribute_long_long( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               const long_long *buffer,
                               size_t size );

H5_HLDLL herr_t  H5LTset_attribute_ulong( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               const unsigned long *buffer,
                               size_t size );

H5_HLDLL herr_t  H5LTset_attribute_float( hid_t loc_id,
                                const char *obj_name,
                                const char *attr_name,
                                const float *buffer,
                                size_t size );

H5_HLDLL herr_t  H5LTset_attribute_double( hid_t loc_id,
                                 const char *obj_name,
                                 const char *attr_name,
                                 const double *buffer,
                                 size_t size );

/*-------------------------------------------------------------------------
 *
 * Get attribute functions
 *
 *-------------------------------------------------------------------------
 */

H5_HLDLL herr_t  H5LTget_attribute( hid_t loc_id,
                          const char *obj_name,
                          const char *attr_name,
                          hid_t mem_type_id,
                          void *data );

H5_HLDLL herr_t  H5LTget_attribute_string( hid_t loc_id,
                                 const char *obj_name,
                                 const char *attr_name,
                                 char *data );

H5_HLDLL herr_t  H5LTget_attribute_char( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               char *data );

H5_HLDLL herr_t  H5LTget_attribute_uchar( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               unsigned char *data );

H5_HLDLL herr_t  H5LTget_attribute_short( hid_t loc_id,
                                const char *obj_name,
                                const char *attr_name,
                                short *data );

H5_HLDLL herr_t  H5LTget_attribute_ushort( hid_t loc_id,
                                const char *obj_name,
                                const char *attr_name,
                                unsigned short *data );

H5_HLDLL herr_t  H5LTget_attribute_int( hid_t loc_id,
                              const char *obj_name,
                              const char *attr_name,
                              int *data );

H5_HLDLL herr_t  H5LTget_attribute_uint( hid_t loc_id,
                              const char *obj_name,
                              const char *attr_name,
                              unsigned int *data );

H5_HLDLL herr_t  H5LTget_attribute_long( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               long *data );

H5_HLDLL herr_t  H5LTget_attribute_long_long( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               long_long *data );

H5_HLDLL herr_t  H5LTget_attribute_ulong( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               unsigned long *data );

H5_HLDLL herr_t  H5LTget_attribute_float( hid_t loc_id,
                                const char *obj_name,
                                const char *attr_name,
                                float *data );

H5_HLDLL herr_t  H5LTget_attribute_double( hid_t loc_id,
                                 const char *obj_name,
                                 const char *attr_name,
                                 double *data );


/*-------------------------------------------------------------------------
 *
 * Query attribute functions
 *
 *-------------------------------------------------------------------------
 */


H5_HLDLL herr_t  H5LTget_attribute_ndims( hid_t loc_id,
                                const char *obj_name,
                                const char *attr_name,
                                int *rank );

H5_HLDLL herr_t  H5LTget_attribute_info( hid_t loc_id,
                               const char *obj_name,
                               const char *attr_name,
                               hsize_t *dims,
                               H5T_class_t *type_class,
                               size_t *type_size );





/*-------------------------------------------------------------------------
 *
 * General functions
 *
 *-------------------------------------------------------------------------
 */

H5_HLDLL hid_t H5LTtext_to_dtype(const char *text, H5LT_lang_t lang_type);
H5_HLDLL herr_t H5LTdtype_to_text(hid_t dtype, char *str, H5LT_lang_t lang_type, size_t *len);


/*-------------------------------------------------------------------------
 *
 * Utility functions
 *
 *-------------------------------------------------------------------------
 */

H5_HLDLL herr_t  H5LTfind_attribute( hid_t loc_id, const char *name );


#ifdef __cplusplus
}
#endif

#endif

