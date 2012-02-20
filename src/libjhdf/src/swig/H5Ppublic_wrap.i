%module PropertiesLib
%include "typemaps.i"

%import "H5types.i"
%import "H5Dtypes.i"
%import "H5Ftypes.i"
%import "H5Ttypes.i"
%include "H5Ptypes.i"
%include "H5FDtypes.i"
%import "H5Ztypes.i"
%include "buffers.i"
%include "henums.i"
%include "strings.i"

%header %{
#include "H5Ppublic.h"
#include "H5FDcore.h"
#include "H5FDfamily.h"
#include "H5FDlog.h"
#include "H5FDmulti.h"
#include "H5FDsec2.h"
#include "H5FDstdio.h"
#ifdef H5_HAVE_PARALLEL
#include "H5FDmpi.h"
#endif
#ifdef H5_HAVE_WINDOWS
#include "H5FDwindows.h"
#endif
#ifdef H5_HAVE_DIRECT
#include "H5FDdirect.h"
#endif
#define HARR_NOARRAYS 1
%}
%include "harrays.i"


%immutable;
%rename H5P_CLS_ROOT_g H5P_ROOT_CLASS;
%rename H5P_CLS_OBJECT_CREATE_g H5P_OBJECT_CREATE_CLASS;
%rename H5P_CLS_FILE_CREATE_g H5P_FILE_CREATE_CLASS;
%rename H5P_CLS_FILE_ACCESS_g H5P_FILE_ACCESS_CLASS;
%rename H5P_CLS_DATASET_CREATE_g H5P_DATASET_CREATE_CLASS;
%rename H5P_CLS_DATASET_ACCESS_g H5P_DATASET_ACCESS_CLASS;
%rename H5P_CLS_DATASET_XFER_g H5P_DATASET_XFER_CLASS;
%rename H5P_CLS_FILE_MOUNT_g H5P_FILE_MOUNT_CLASS;
%rename H5P_CLS_GROUP_CREATE_g H5P_GROUP_CREATE_CLASS;
%rename H5P_CLS_GROUP_ACCESS_g H5P_GROUP_ACCESS_CLASS;
%rename H5P_CLS_DATATYPE_CREATE_g H5P_DATATYPE_CREATE_CLASS;
%rename H5P_CLS_DATATYPE_ACCESS_g H5P_DATATYPE_ACCESS_CLASS;
%rename H5P_CLS_STRING_CREATE_g H5P_STRING_CREATE_CLASS;
%rename H5P_CLS_ATTRIBUTE_CREATE_g H5P_ATTRIBUTE_CREATE_CLASS;
%rename H5P_CLS_OBJECT_COPY_g H5P_OBJECT_COPY_CLASS;
%rename H5P_CLS_LINK_CREATE_g H5P_LINK_CREATE_CLASS;
%rename H5P_CLS_LINK_ACCESS_g H5P_LINK_ACCESS_CLASS;
extern hid_t H5P_CLS_ROOT_g;
extern hid_t H5P_CLS_OBJECT_CREATE_g;
extern hid_t H5P_CLS_FILE_CREATE_g;
extern hid_t H5P_CLS_FILE_ACCESS_g;
extern hid_t H5P_CLS_DATASET_CREATE_g;
extern hid_t H5P_CLS_DATASET_ACCESS_g;
extern hid_t H5P_CLS_DATASET_XFER_g;
extern hid_t H5P_CLS_FILE_MOUNT_g;
extern hid_t H5P_CLS_GROUP_CREATE_g;
extern hid_t H5P_CLS_GROUP_ACCESS_g;
extern hid_t H5P_CLS_DATATYPE_CREATE_g;
extern hid_t H5P_CLS_DATATYPE_ACCESS_g;
extern hid_t H5P_CLS_STRING_CREATE_g;
extern hid_t H5P_CLS_ATTRIBUTE_CREATE_g;
extern hid_t H5P_CLS_OBJECT_COPY_g;
extern hid_t H5P_CLS_LINK_CREATE_g;
extern hid_t H5P_CLS_LINK_ACCESS_g;

%rename H5P_LST_FILE_CREATE_g H5P_FILE_CREATE_LIST;
%rename H5P_LST_FILE_ACCESS_g H5P_FILE_ACCESS_LIST;
%rename H5P_LST_DATASET_CREATE_g H5P_DATASET_CREATE_LIST;
%rename H5P_LST_DATASET_ACCESS_g H5P_DATASET_ACCESS_LIST;
%rename H5P_LST_DATASET_XFER_g H5P_DATASET_XFER_LIST;
%rename H5P_LST_FILE_MOUNT_g H5P_FILE_MOUNT_LIST;
%rename H5P_LST_GROUP_CREATE_g H5P_GROUP_CREATE_LIST;
%rename H5P_LST_GROUP_ACCESS_g H5P_GROUP_ACCESS_LIST;
%rename H5P_LST_DATATYPE_CREATE_g H5P_DATATYPE_CREATE_LIST;
%rename H5P_LST_DATATYPE_ACCESS_g H5P_DATATYPE_ACCESS_LIST;
%rename H5P_LST_ATTRIBUTE_CREATE_g H5P_ATTRIBUTE_CREATE_LIST;
%rename H5P_LST_OBJECT_COPY_g H5P_OBJECT_COPY_LIST;
%rename H5P_LST_LINK_CREATE_g H5P_LINK_CREATE_LIST;
%rename H5P_LST_LINK_ACCESS_g H5P_LINK_ACCESS_LIST;
extern hid_t H5P_LST_FILE_CREATE_g;
extern hid_t H5P_LST_FILE_ACCESS_g;
extern hid_t H5P_LST_DATASET_CREATE_g;
extern hid_t H5P_LST_DATASET_ACCESS_g;
extern hid_t H5P_LST_DATASET_XFER_g;
extern hid_t H5P_LST_FILE_MOUNT_g;
extern hid_t H5P_LST_GROUP_CREATE_g;
extern hid_t H5P_LST_GROUP_ACCESS_g;
extern hid_t H5P_LST_DATATYPE_CREATE_g;
extern hid_t H5P_LST_DATATYPE_ACCESS_g;
extern hid_t H5P_LST_ATTRIBUTE_CREATE_g;
extern hid_t H5P_LST_OBJECT_COPY_g;
extern hid_t H5P_LST_LINK_CREATE_g;
extern hid_t H5P_LST_LINK_ACCESS_g;
%mutable;

/* Define property list class callback function pointer types */
/*
typedef herr_t (*H5P_cls_create_func_t)(hid_t prop_id, void *create_data);
typedef herr_t (*H5P_cls_copy_func_t)(hid_t new_prop_id, hid_t old_prop_id,
                                      void *copy_data);
typedef herr_t (*H5P_cls_close_func_t)(hid_t prop_id, void *close_data);
*/
/* Define property list callback function pointer types */
/*
typedef herr_t (*H5P_prp_cb1_t)(const char *name, size_t size, void *value);
typedef herr_t (*H5P_prp_cb2_t)(hid_t prop_id, const char *name, size_t size, void *value);
*/
/*
typedef H5P_prp_cb1_t H5P_prp_create_func_t;
typedef H5P_prp_cb2_t H5P_prp_set_func_t;
typedef H5P_prp_cb2_t H5P_prp_get_func_t;
typedef H5P_prp_cb2_t H5P_prp_delete_func_t;
typedef H5P_prp_cb1_t H5P_prp_copy_func_t;
typedef int (*H5P_prp_compare_func_t)(const void *value1, const void *value2, size_t size);
typedef H5P_prp_cb1_t H5P_prp_close_func_t;
*/
/* Define property list iteration function type */
/*
typedef herr_t (*H5P_iterate_t)(hid_t id, const char *name, void *iter_data);
*/


/**
 * Verifies that all required filters are available.
 *
 * <p>H5Pall_filters_avail verifies that all of the filters set in the
 * dataset creation property list dcpl_id are currently available.</p>
 *
 * @param dcpl_id Dataset creation property list identifier.
 *
 * @return Returns TRUE if all filters are available and FALSE if one
 * or more is not currently available. Returns FAIL, a negative value,
 * on error.
 *
 * @ingroup plDsCreate
 */
htri_t H5Pall_filters_avail(hid_t dcpl_id);

/**
 * Terminates access to a property list.
 *
 * <p>H5Pclose terminates access to a property list. All property
 * lists should be closed when the application is finished accessing
 * them. This frees resources used by the property list.</p>
 *
 * @param plist_id Identifier of the property list to terminate access to.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGeneral
 */
herr_t H5Pclose(hid_t plist_id);

/**
 * Closes an existing property list class.
 * <p>Removes a property list class from the library.</p>
 *
 * <p>Existing property lists of this class will continue to exist,
 * but new ones are not able to be created. </p>
 *
 * @param plist_id Property list class to close.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
herr_t H5Pclose_class(hid_t plist_id);

/**
 * Copies an existing property list to create a new property list.
 *
 * <p>H5Pcopy copies an existing property list to create a new
 * property list. The new property list has the same properties and
 * values as the original property list.</p>
 *
 * @param plist_id Identifier of property list to duplicate.
 * 
 * @return Returns a property list identifier if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGeneral
 */
hid_t H5Pcopy(hid_t plist_id);

/**
 * Copies a property from one list or class to another.
 *
 * <p>H5Pcopy_prop copies a property from one property list or class
 * to another.</p>
 *
 * <p>If a property is copied from one class to another, all the
 * property information will be first deleted from the destination
 * class and then the property information will be copied from the
 * source class into the destination class.</p>
 *
 * <p>If a property is copied from one list to another, the property
 * will be first deleted from the destination list (generating a call
 * to the close callback for the property, if one exists) and then the
 * property is copied from the source list to the destination list
 * (generating a call to the copy callback for the property, if one
 * exists).</p>
 *
 * <p>If the property does not exist in the class or list, this call
 * is equivalent to calling H5Pregister or H5Pinsert (for a class or
 * list, as appropriate) and the create callback will be called in the
 * case of the property being copied into a list (if such a callback
 * exists for the property). </p>
 *
 * @param dst_id Identifier of the destination property list or class.
 * @param src_id Identifier of the source property list or class.
 * @param name Name of the property to copy.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
herr_t H5Pcopy_prop(hid_t dst_id, hid_t src_id, const char *name);

/**
 * Creates a new property as an instance of a property list class.
 *
 * <p>H5Pcreate creates a new property as an instance of some property
 * list class. The new property list is initialized with default
 * values for the specified class. The classes are:</p>
 <dl>
    <dt>H5P_OBJECT_CREATE</dt>
    <dd>Properties for object creation during the object copying
    process. See H5Pset_copy_object.</dd>
    <dt>H5P_FILE_CREATE</dt>
    <dd>Properties for file creation. See Files in the HDF User's
    Guide for details about the file creation properties. </dd>
    <dt>H5P_FILE_ACCESS</dt>
    <dd>Properties for file access. See Files in the HDF User's Guide
    for details about the file creation properties. </dd>
    <dt>H5P_DATASET_CREATE</dt>
    <dd>Properties for dataset creation. See Datasets in the HDF
    User's Guide for details about dataset creation properties. </dd>
    <dt>H5P_DATASET_ACCESS</dt>
    <dd>Properties for dataset access. </dd>
    <dt>H5P_DATASET_XFER</dt>
    <dd>Properties for raw data transfer. See Datasets in the HDF
    User's Guide for details about raw data transfer properties. </dd>
    <dt>H5P_MOUNT</dt>
    <dd>Properties for file mounting. With this parameter, H5Pcreate
    creates and returns a new mount property list initialized with
    default values. </dd>
    <dt>H5P_GROUP_CREATE</dt>
    <dd>Properties for group creation during the object copying
    process. See H5Pset_copy_object. </dd>
    <dt>H5P_GROUP_ACCESS</dt>
    <dd>Properties for group access during the object copying
    process. See H5Pset_copy_object. </dd>
    <dt>H5P_DATATYPE_CREATE</dt>
    <dd>Properties for datatype creation during the object copying
    process. See H5Pset_copy_object. </dd>
    <dt>H5P_DATATYPE_ACCESS</dt>
    <dd>Properties for datatype access during the object copying
    process. See H5Pset_copy_object. </dd>
    <dt>H5P_ATTRIBUTE_CREATE</dt>
    <dd>Properties for attribute creation during the object copying
    process. See H5Pset_copy_object. </dd>
    <dt>H5P_OBJECT_COPY</dt>
    <dd>Properties governing the object copying process. See
    H5Pset_copy_object.</dd>
 </dl>
 *
 * <p>This property list must eventually be closed with H5Pclose;
 * otherwise, errors are likely to occur.</p>
 *
 * @param cls_id The class of the property list to create.
 *
 * @return Returns a property list identifier (plist) if successful;
 * otherwise Fail (-1).
 *
 * @ingroup plGeneral
 */
hid_t H5Pcreate(hid_t cls_id);

/*nodoc*
 * Creates a new property list class.
 *
 * <p>H5Pcreate_class registers a new property list class with the
 * library. The new property list class can inherit from an existing
 * property list class or may be derived from the default "empty"
 * class. New classes with inherited properties from existing classes
 * may not remove those existing properties, only add or remove their
 * own class properties.</p>
 *
 * <p>The create routine is called when a new property list of this
 * class is being created. The create routine is called after any
 * registered create function is called for each property value. If
 * the create routine returns a negative value, the new list is not
 * returned to the user and the property list creation routine returns
 * an error value.</p>
 *
 * <p>The copy routine is called when an existing property list of
 * this class is copied. The copy routine is called after any
 * registered copy function is called for each property value. If the
 * copy routine returns a negative value, the new list is not returned
 * to the user and the property list copy routine returns an error
 * value.</p>
 *
 * <p>The close routine is called when a property list of this class
 * is being closed. The close routine is called before any registered
 * close function is called for each property value. If the close
 * routine returns a negative value, the property list close routine
 * returns an error value but the property list is still closed.</p>
 *
 * @param class Property list class to inherit from.
 * @param name Name of property list class to register.
 * @param create Callback routine called when a property list is created.
 * @param copy Callback routine called when a property list is copied.
 * @param close Callback routine called when a property list is being closed.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
//hid_t H5Pcreate_class(hid_t parent, const char *name,
//		      H5P_cls_create_func_t cls_create, void *create_data,
//		      H5P_cls_copy_func_t cls_copy, void *copy_data,
//		      H5P_cls_close_func_t cls_close, void *close_data);

/**
 * Compares two property lists or classes for equality.
 *
 * <p>H5Pequal compares two property lists or classes to determine
 * whether they are equal to one another.</p>
 *
 * <p>Either both id1 and id2 must be property lists or both must be
 * classes; comparing a list to a class is an error. </p>
 *
 * @param id1 First property object to be compared.
 * @param id2 Second property object to be compared.
 *
 * @return Returns TRUE (positive) if equal; FALSE (zero) if unequal,
 * or a negative value on failure.
 *
 * @ingroup plPList
 */
htri_t H5Pequal(hid_t id1, hid_t id2);

/**
 * Queries whether a property name exists in a property list or class.
 *
 * <p>H5Pexist determines whether a property exists within a property
 * list or class.</p>
 *
 * @param plist_id Identifier for the property list to query.
 * @param name Name of property to check for.
 *
 * @return Returns a positive value if the property exists in the
 * property object; zero if the property does not exist or a negative
 * value on failure.
 *
 * @ingroup plPList
 */
htri_t H5Pexist(hid_t plist_id, const char *name);

/**
 * Determines whether fill value is defined.
 *
 * <p>H5Pfill_value_defined determines whether a fill value is defined
 * in the dataset creation property list plist_id.</p>
 *
 * <p>Valid values returned in status are as follows:</p>
 <dl>
    <dt>H5D_FILL_VALUE_UNDEFINED</dt>
    <dd>Fill value is undefined.</dd>
    <dt>H5D_FILL_VALUE_DEFAULT</dt>
    <dd>Fill value is the library default.</dd>
    <dt>H5D_FILL_VALUE_USER_DEFINED</dt>
    <dd>Fill value is defined by the application.</dd>
 </dl>
 *
 * <p>Note:<br/> H5Pfill_value_defined is designed for use in concert
 * with the dataset fill value properties functions H5Pget_fill_value
 * and H5Pget_fill_time.</p>
 *
 * <p>See H5Dcreate for further cross-references. </p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param status (Output) Status of fill value in property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply H5D_FILL_ENUM* OUTPUT {H5D_fill_value_t* status}
herr_t H5Pfill_value_defined(hid_t plist, H5D_fill_value_t *status);
%clear H5D_fill_value_t* status;

/**
 * Queries the value of a property.
 *
 * <p>H5Pget retrieves a copy of the value for a property in a
 * property list. If there is a get callback routine registered for
 * this property, the copy of the value of the property will first be
 * passed to that routine and any changes to the copy of the value
 * will be used when returning the property value from this
 * routine.</p>
 *
 * <p>This routine may be called for zero-sized properties with the
 * value set to NULL. The get routine will be called with a NULL value
 * if the callback exists.</p>
 *
 * <p>The property name must exist or this routine will fail.</p>
 *
 * <p>If the get callback routine returns an error, value will not be
 * modified. </p>
 *
 * @param plid Identifier of the property list to query.
 * @param name Name of property to query.
 * @param value Pointer to a location to which to copy the value of of
 * the property.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
%apply void* BUFF {void* value}
herr_t H5Pget(hid_t plist_id, const char *name, void *value);


/**
 * Retrieves the current settings for alignment properties from a file
 * access property list.
 *
 * <p>H5Pget_alignment retrieves the current settings for alignment
 * properties from a file access property list. The threshold and/or
 * alignment pointers may be null pointers (NULL).</p>
 *
 * @param plist Identifier of a file access property list.
 * @param threshold (Output) Pointer to location of return threshold value.
 * @param alignment (Output) Pointer to location of return alignment value.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply unsigned long* OUTPUT {hsize_t* threshold}
%apply unsigned long* OUTPUT {hsize_t* alignment}
herr_t H5Pget_alignment(hid_t fapl_id, hsize_t *threshold/*out*/,
                                       hsize_t *alignment/*out*/);

/**
 * Retrieves the timing for storage space allocation.
 *
 * <p>H5Pget_alloc_time retrieves the timing for allocating storage
 * space for a dataset's raw data. This property is set in the dataset
 * creation property list plist_id.</p>
 *
 * <p>The timing setting is returned in alloc_time as one of the
 * following values:</p>
 <dl>
    <dt>H5D_ALLOC_TIME_DEFAULT</dt>
    <dd>Uses the default allocation time, based on the dataset storage
    method. See the alloc_time description in H5Pset_alloc_time for
    default allocation times for various storage methods.</dd>
    <dt>H5D_ALLOC_TIME_EARLY</dt>
    <dd>All space is allocated when the dataset is created.</dd>
    <dt>H5D_ALLOC_TIME_INCR</dt>
    <dd>Space is allocated incrementally as data is written to the
    dataset.</dd>
    <dt>H5D_ALLOC_TIME_LATE</dt>
    <dd>All space is allocated when data is first written to the dataset.</dd>
 </dl>
 *
 * <p>Note:<br/> H5Pget_alloc_time is designed to work in concert with
 * the dataset fill value and fill value write time properties, set
 * with the functions H5Pget_fill_value and H5Pget_fill_time.</p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param alloc_time When to allocate dataset storage space.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply H5D_ATIME_ENUM* OUTPUT {H5D_alloc_time_t* alloc_time}
herr_t H5Pget_alloc_time(hid_t plist_id, H5D_alloc_time_t* alloc_time/*out*/);
%clear H5D_alloc_time_t* alloc_time;

/**
 * Retrieves tracking and indexing settings for attribute creation order.
 *
 * <p>H5Pget_attr_creation_order retrieves the settings for tracking
 * and indexing attribute creation order on an object.</p>
 *
 * <p>ocpl_id is a dataset or group creation property list
 * identifier. The term ocpl, for object creation property list, is
 * used when different types of objects may be involved.</p>
 *
 * <p>crt_order_flags returns flags with the following meanings:</p>
<dl>
   <dt>H5P_CRT_ORDER_TRACKED</dt>
   <dd>Attribute creation order is tracked but not necessarily indexed.</dd>
   <dt>H5P_CRT_ORDER_INDEXED</dt>
   <dd>Attribute creation order is indexed (requires
   H5P_CRT_ORDER_TRACKED). </dd>
 </dl>
 *
 * <p>If crt_order_flags is returned with a valueof 0 (zero),
 * attribute creation order is neither tracked nor indexed.</p>
 *
 * @param ocpl_id Object (group or dataset) creation property list
 * identifier.
 * @param crt_order_flags (Output) Flags specifying whether to track
 * and index attribute creation order.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
%apply unsigned* OUTPUT {unsigned* crt_order_flags}
herr_t H5Pget_attr_creation_order(hid_t plist_id, unsigned *crt_order_flags);
%clear unsigned* crt_order_flags;

/**
 * Retrieves attribute storage phase change thresholds.
 *
 * <p>H5Pget_attr_phase_change retrieves threshold values for
 * attribute storage on an object. These thresholds determine the
 * point at which attribute storage changes from compact storage
 * (i.e., storage in the object header) to dense storage (i.e.,
 * storage in a heap and indexed with a B-tree).</p>
 *
 * <p>In the general case, attributes are initially kept in compact
 * storage. When the number of attributes exceeds max_compact,
 * attribute storage switches to dense storage. If the number of
 * attributes subsequently falls below min_dense, the attributes are
 * returned to compact storage.</p>
 *
 * <p>If max_compact is set to 0 (zero), dense storage always
 * used.</p>
 *
 * <p>ocpl_id is a dataset or group creation property list
 * identifier. The term ocpl, for object creation property list, is
 * used when different types of objects may be involved. </p>
 *
 * @param ocpl_id Object (dataset or group) creation property list identifier.
 * @param max_compact (Output) Maximum number of attributes to be
 * stored in compact storage. (Default: 8)
 * @param min_dense Minimum number of attributes to be stored in dense
 * storage. (Default: 6)
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
%apply unsigned* OUTPUT {unsigned* max_compact}
%apply unsigned* OUTPUT {unsigned* min_dense}
herr_t H5Pget_attr_phase_change(hid_t plist_id, unsigned *max_compact,
				unsigned *min_dense);
%clear unsigned* max_compact;
%clear unsigned* min_dense;

/**
 * Gets B-tree split ratios for a dataset transfer property list.
 *
 * <p>H5Pget_btree_ratios returns the B-tree split ratios for a
 * dataset transfer property list.</p>
 *
 * <p>The B-tree split ratios are returned through the non-NULL
 * arguments left, middle, and right, as set by the
 * H5Pset_btree_ratios function. </p>
 *
 * @param plist The dataset transfer property list identifier.
 * @param left (Output) The B-tree split ratio for left-most nodes.
 * @param right (Output) The B-tree split ratio for right-most nodes
 * and lone nodes.
 * @param middle (Output) The B-tree split ratio for all other nodes.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
%apply double* OUTPUT {double* left}
%apply double* OUTPUT {double* middle}
%apply double* OUTPUT {double* right}
herr_t H5Pget_btree_ratios(hid_t plist_id, double *left/*out*/,
                           double *middle/*out*/, double *right/*out*/);
%clear double* left;
%clear double* middle;
%clear double* right;

/**
 * Reads buffer settings.
 *
 * <p>H5Pget_buffer reads values previously set with
 * H5Pset_buffer.</p>
 *
 * @param plist_id Identifier for the dataset transfer property list.
 * @param tconv (Output) Address of the pointer to
 * application-allocated type conversion buffer.
 * @param bkg (Output) Address of the pointer to application-allocated
 * background buffer.
 *
 * @return Returns buffer size, in bytes, if successful; otherwise 0
 * on failure.
 *
 * @ingroup plDsAccess
 */
size_t H5Pget_buffer(hid_t plist_id, void **tconv/*out*/, void **bkg/*out*/);

/**
 * Queries the meta data cache and raw data chunk cache parameters.
 *
 * <p>H5Pget_cache retrieves the maximum possible number of elements
 * in the meta data cache and raw data chunk cache, the maximum
 * possible number of bytes in the raw data chunk cache, and the
 * preemption policy value.</p>
 *
 * <p>Any (or all) arguments may be null pointers, in which case the
 * corresponding datum is not returned. </p>
 *
 * @param plist_id Identifier of the file access property list.
 * @param mdc_nelmts (Output) Number of elements (objects) in the meta
 * data cache.
 * @param rdcc_nelmts (Output) Number of elements (objects) in the raw
 * data chunk cache.
 * @param rdcc_nbytes (Output) Total size of the raw data chunk cache,
 * in bytes.
 * @param rdcc_w0 (Output) Preemption policy.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply int* OUTPUT {int* mdc_nelmts}
%apply unsigned long* OUTPUT {size_t* rdcc_nelmts}
%apply unsigned long* OUTPUT {size_t* rdcc_nbytes}
%apply double* OUTPUT {double* rdcc_w0}
herr_t H5Pget_cache(hid_t plist_id, int *mdc_nelmts/*out*/,
       size_t *rdcc_nelmts/*out*/,
       size_t *rdcc_nbytes/*out*/, double *rdcc_w0);
%clear int* mdc_nelmts;
%clear size_t* rdcc_nelmts;
%clear size_t* rdcc_nbytes;
%clear double* rdcc_w0;

/**
 * Retrieves the character encoding used to create a string.
 *
 * <p>H5Pget_char_encoding retrieves the character encoding used to
 * encode strings or object names that are created with the property
 * list plist_id.</p>
 *
 * <p>Valid values for encoding are defined in H5Tpublic.h and include
 * the following:</p>
 <dl>
    <dt>H5T_CSET_ASCII</dt>
    <dd>US ASCII</dd>
    <dt>H5T_CSET_UTF8</dt>
    <dd>UTF-8 Unicode encoding</dd>
 </dl>
 *
 * @param plist_id Property list identifier.
 * @param encoding String encoding character set.
 *
 * @return Returns a non-negative valule if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plStrCreate
 */
%apply H5T_CSET_ENUM* OUTPUT {H5T_cset_t *encoding}
herr_t H5Pget_char_encoding(hid_t plist_id, H5T_cset_t *encoding /*out*/);
%clear H5T_cset_t *encoding;

/**
 * Retrieves the size of chunks for the raw data of a chunked layout dataset.
 *
 * <p>H5Pget_chunk retrieves the size of chunks for the raw data of a
 * chunked layout dataset. This function is only valid for dataset
 * creation property lists. At most, max_ndims elements of dims will
 * be initialized.</p>
 *
 * @param plist_id Identifier of property list to query.
 * @param max_ndims Size of the dims array.
 * @param dims (Output) Array to store the chunk dimensions.
 *
 * @return Returns chunk dimensionality if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply long long OUTPUT[] {hsize_t dim[]}
int H5Pget_chunk(hid_t plist_id, int max_ndims, hsize_t dim[]/*out*/);
%clear hsize_t dim[];

/**
 * Returns the property list class for a property list.
 *
 * <p>H5Pget_class returns the property list class for the property
 * list identified by the plist parameter. Valid property list classes
 * are defined in the description of H5Pcreate.</p>
 *
 * @param plist_id Identifier of property list to query.
 *
 * @return Returns a property list class if successful. Otherwise
 * returns H5P_NO_CLASS (-1).
 *
 * @ingroup plGeneral
 */
hid_t H5Pget_class(hid_t plist_id);

/**
 * Retrieves the name of a class.
 *
 * <p>H5Pget_class_name retrieves the name of a generic property list
 * class. The pointer to the name must be freed by the user after each
 * successful call.</p>
 *
 * @param pcid Identifier of the property class to query.
 *
 * @return Returns a pointer to an allocated string containing the
 * class name if successful; otherwise NULL.
 *
 * @ingroup plPList
 */
%newobject H5Pget_class_name;
char* H5Pget_class_name(hid_t pclass_id);

/**
 * Retrieves the parent class of a property class.
 *
 * <p>H5Pget_class_parent retrieves an identifier for the parent class
 * of a property class.</p>
 *
 * @param pclass_id Identifier of the property class to query.
 *
 * @return Returns a valid parent class object identifier if
 * successful; otherwise a negative value.
 *
 * @ingroup plPList
 */
hid_t H5Pget_class_parent(hid_t pclass_id);

/**
 * Retrieves the properties to be used when an object is copied.
 *
 * <p>H5Pget_copy_object retrieves the properties currently specified
 * in the object copy property list ocp_plist_id, which will be
 * invoked when a new copy is made of an existing object.</p>
 *
 * <p>copy_options is a bit map indicating the flags, or properties,
 * governing object copying that are set in the property list
 * ocp_plist_id.</p>
 *
 * <p>The available flags are described in H5Pset_copy_object. </p>
 *
 * @param ocp_plist_id Object copy property list identifier.
 * @param copy_options Copy option(s) set in the object copy property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
%apply unsigned* OUTPUT {unsigned* crt_intmd}
herr_t H5Pget_copy_object(hid_t plist_id, unsigned *crt_intmd /*out*/);
%clear unsigned* crt_intmd;

/**
 * Determines whether property is set to enable creating missing
 * intermediate groups.
 *
 * <p>t_create_intermediate_group determines whether the link creation
 * property list lcpl_id is set to allow functions that create objects
 * in groups different from the current working group to create
 * intermediate groups that may be missing in the path of a new or
 * moved object.</p>
 *
 * <p>ctions that create objects in or move objects to a group other
 * than the current working group make use of this
 * property. H5Gcreate_anon and H5Lmove are examles of such
 * functions.</p>
 *
 * <p>crt_intermed_group is true, missing intermediate groups will be
 * created; if crt_intermed_group is false, missing intermediate
 * groups will not be created. </p>
 *
 * @param lcpl_id Link creation property list identifier.
 * @param crt_intermed_group Flag specifying whether to create
 * intermediate groups upon creation of an object.
 *
 * @return Returns a non-negative valule if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
%apply unsigned* OUTPUT {unsigned* crt_intmed}
herr_t H5Pget_create_intermediate_group(hid_t plist_id, unsigned* crt_intmed);
%clear unsigned* crt_intmed;


/**
 * Retrieves a data transform expression.
 *
 * <p>Pget_data_transform retrieves the data transform expression
 * previously set in the dataset transfer property list plist_id by
 * H5Pset_data_transform.</p>
 *
 * <p>get_data_transform can be used to both retrieve the transform
 * expression and to query its size.</p>
 *
 * <p>expression is non-NULL, up to size bytes of the data transform
 * expression are written to the buffer. If expression is NULL, size
 * is ignored and the function does not write anything to the
 * buffer. The function always returns the size of the data transform
 * expression.</p>
 *
 * <p>0 is returned for the size of the expression, no data transform
 * expression exists for the property list.</p>
 *
 * <p>an error occurs, the buffer pointed to by expression is
 * unchanged and the function returns a negative value. </p>
 *
 * @param plist_id Identifier of the property list or class.
 * @param expression (Output) Pointer to memory where the transform
 * expression will be copied.
 * @param size Number of bytes of the transform expression to copy to.
 *
 * @return Returns the size of the transform expression if successful;
 * otherwise returns a negative value.
 *
 * @ingroup plDsAccess
 */
%apply signed char OUTPUT[] {char* expression}
ssize_t H5Pget_data_transform(hid_t plist_id, char* expression /*out*/, 
			      size_t size);
%clear char* expression;

/**
 * Returns low-lever driver identifier.
 *
 * <p>H5Pget_driver returns the identifier of the low-level file
 * driver associated with the file access property list or data
 * transfer property list plist_id.</p>
 *
 * <p>Valid driver identifiers with the standard HDF5 library
 * distribution include the following:</p>
 <ul>
    <li>H5FD_CORE</li>
    <li>H5FD_DIRECT</li>
    <li>H5FD_FAMILY</li>
    <li>H5FD_LOG</li>
    <li>H5FD_MPIO</li>
    <li>H5FD_MULTI</li>
    <li>H5FD_SEC2</li>
    <li>H5FD_STDIO</li>
    <li>H5FD_WINDOWS (Windows only)</li>
 </ul>
 *
 * <p>If a user defines and registers custom drivers or if additional
 * drivers are defined in an HDF5 distribution, this list will be
 * longer.</p>
 *
 * <p>The Windows driver, H5FD_WINDOWS, is available only on Windows
 * systems.</p>
 *
 * <p>The returned driver identifier is only valid as long as the file
 * driver remains registered. </p>
 *
 * @param plist_id File access or data transfer property list identifier.
 *
 * @return Returns a valid low-level driver identifier if
 * successful. Otherwise returns a negative value.
 *
 * @ingroup plFAccess
 */
hid_t H5Pget_driver(hid_t plist_id);

/*nodoc*
 * Returns a pointer to file driver information.
 *
 * <p>H5Pget_driver_info returns a pointer to file driver-specific
 * information for the low-level driver associated with the file
 * access or data transfer property list plist_id.</p>
 *
 * <p>The pointer returned by this function points to an
 * "uncopied" struct. Driver-specific versions of that struct
 * are defined for each low-level driver in the relevant source code
 * file H5FD*.c. For example, the struct used for the MULTI driver is
 * H5FD_multi_fapl_t defined in H5FDmulti.c.</p>
 *
 * <p>If no driver-specific properties have been registered,
 * H5Pget_driver_info returns NULL. </p>
 *
 * @param plist_id File access or data transfer property list identifier.
 *
 * @return Returns a pointer to a struct containing low-level driver
 * information. Otherwise returns NULL. NULL is also returned if no
 * driver-specific properties have been registered. No error is pushed
 * on the stack in this case.
 *
 * @ingroup plFAccess
 */
//void* H5Pget_driver_info(hid_t plist_id);

/**
 * Returns the data transfer mode.
 *
 * <p>H5Pget_dxpl_mpio queries the data transfer mode currently set in
 * the data transfer property list dxpl_id.</p>
 *
 * <p>Upon return, xfer_mode contains the data transfer mode, if it is
 * non-null.</p>
 *
 * <p>H5Pget_dxpl_mpio is not a collective function. </p>
 *
 * @param dxpl_id Data transfer property list identifier.
 * @param xfer_mode (Output) Data transfer mode.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pget_dxpl_mpio(hid_t dxpl_id, H5FD_mpio_xfer_t *xfer_mode/*out*/);
#endif 

/**
 * Returns multi-file data transfer property list information.
 *
 * <p>H5Pget_dxpl_multi returns the data transfer property list
 * information for the multi-file driver.</p>
 *
 * @param dxpl_id Data transfer property list identifier.
 * @param memb_dxpl (Output) Array of data access property lists.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pget_dxpl_multi(hid_t dxpl_id, hid_t *memb_dxpl/*out*/);
#endif

/**
 * Determines whether error-detection is enabled for dataset reads.
 *
 * <p>H5Pget_edc_check queries the dataset transfer property list
 * plist to determine whether error detection is enabled for data read
 * operations.</p>
 *
 * @param dxpl_id Dataset transfer property list identifier.
 *
 * @return Returns H5Z_ENABLE_EDC or H5Z_DISABLE_EDC if successful;
 * otherwise returns a negative value.
 *
 * @ingroup plDsAccess
 */
H5Z_EDC_t H5Pget_edc_check(hid_t dxpl_id);

/**
 * Retrieves prefix applied to external link paths.
 *
 * <p>H5Pget_elink_prefix retrieves the prefix applied to the path of
 * any external links traversed.</p>
 *
 * <p>When an external link is traversed, the prefix is retrieved from
 * the link access property list lapl_id, returned in the
 * user-allocated buffer pointed to by prefix, and prepended to the
 * filename stored in the external link.</p>
 *
 * <p>The size in bytes of the prefix, including the NULL terminator,
 * is specified in size. If size is unknown, a preliminary
 * H5Pget_elink_prefix call with the pointer prefix set to NULL will
 * return the size of the prefix without the NULL terminator.</p>
 *
 * @param lapl_id Link access property list identifier.
 * @param prefix (Output) Prefix applied to external link paths.
 * @param size Size of prefix, including null terminator.
 *
 * @return If successful, returns a non-negative value specifying the
 * size in bytes of the prefix without the NULL terminator; otherwise
 * returns a negative value.
 *
 * @ingroup plLnkAccess
 */
%apply signed char OUTPUT[] {char* prefix}
ssize_t H5Pget_elink_prefix(hid_t plist_id, char *prefix, size_t size);
%clear char* prefix;

/**
 * Queries data required to estimate required local heap or object header size.
 *
 * <p>H5Pget_est_link_info queries a group creation property list,
 * gcpl_id, for its "estimated number of links" and
 * "estimated average name length" settings.</p>
 *
 * <p>The estimated number of links anticipated to be inserted into a
 * group created with this property list is returned in
 * est_num_entries.</p>
 *
 * <p>The estimated average length of the anticipated link names is
 * returned in est_name_len.</p>
 *
 * <p>The values for these two settings are multiplied to compute the
 * initial local heap size (for old-style groups, if the local heap
 * size hint is not set) or the initial object header size for
 * (new-style compact groups; see "Group styles in
 * HDF5"). Accurately setting these parameters will help reduce
 * wasted file space.</p>
 *
 * <p>A value of 0 (zero) in est_num_entries will prevent a group from
 * being created in the compact format.</p>
 *
 * <p>See "Group styles in HDF5" in the H5G API introduction for
 * a discussion of the available types of HDF5 group structures. </p>
 *
 * @param gcpl_id Group creation property list identifier.
 * @param est_num_entries (Output) Estimated number of links to be
 * inserted into group.
 * @param est_name_len (Output) Estimated average length of link names
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGrpCreate
 */
%apply unsigned* OUTPUT {unsigned *est_num_entries}
%apply unsigned* OUTPUT {unsigned *est_name_len}
herr_t H5Pget_est_link_info(hid_t plist_id, unsigned *est_num_entries /*out*/, 
			    unsigned *est_name_len /*out*/);
%clear unsigned *est_num_entries;
%clear unsigned *est_name_len;

/**
 * Returns information about an external file.
 *
 * <p>H5Pget_external returns information about an external file. The
 * external file is specified by its index, idx, which is a number
 * from zero to N-1, where N is the value returned by
 * H5Pget_external_count. At most name_size characters are copied into
 * the name array. If the external file name is longer than name_size
 * with the null terminator, the return value is not null terminated
 * (similar to strncpy()).</p>
 *
 * <p>If name_size is zero or name is the null pointer, the external
 * file name is not returned. If offset or size are null pointers then
 * the corresponding information is not returned. </p>
 *
 * @param plist Identifier of a dataset creation property list.
 * @param idx External file index.
 * @param name_size Maximum length of name array.
 * @param name (Output) Name of the external file.
 * @param offset (Output) Pointer to a location to return an offset value.
 * @param size (Output) Pointer to a location to return the size of
 * the external file data.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply signed char OUTPUT[] {char* name}
%apply unsigned long* OUTPUT {off_t* offset}
%apply unsigned long* OUTPUT {hsize_t* size}
herr_t H5Pget_external(hid_t plist_id, unsigned idx, size_t name_size,
		       char *name/*out*/, off_t *offset/*out*/, 
		       hsize_t *size/*out*/);
%clear char* name;
%clear off_t* offset;
%clear hsize_t* size;

/**
 * Returns the number of external files for a dataset.
 *
 * <p>H5Pget_external_count returns the number of external files for
 * the specified dataset.</p>
 *
 * @param dcpl_id Identifier of a dataset creation property list.
 *
 * @return Returns the number of external files if successful;
 * otherwise returns a negative value.
 *
 * @ingroup plDsCreate
 */
int H5Pget_external_count(hid_t dcpl_id);

/**
 * Retrieves a data offset from the file access property list.
 *
 * <p>H5Pget_family_offset retrieves the value of offset from the file
 * access property list fapl_id so that the user application can
 * retrieve a file handle for low-level access to a particular member
 * of a family of files. The file handle is retrieved with a separate
 * call to H5Fget_vfd_handle (or, in special circumstances, to
 * H5FDget_vfd_handle; see Virtual File Layer and List of VFL
 * Functions in HDF5 Technical Notes).</p>
 *
 * <p>The data offset returned in offset is the offset of the data in
 * the HDF5 file that is stored on disk in the selected member file in
 * a family of files.</p>
 *
 * <p>Use of this function is only appropriate for an HDF5 file
 * written as a family of files with the FAMILY file driver. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param offset (Output) Offset in bytes within the HDF5 file.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply long long* OUTPUT {hsize_t* offset}
herr_t H5Pget_family_offset(hid_t fapl_id, hsize_t *offset);
%clear hsize_t* offset;

/**
 * Queries core file driver properties.
 *
 * <p>H5Pget_fapl_core queries the H5FD_CORE driver properties as set
 * by H5Pset_fapl_core.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param increment (Output) Size, in bytes, of memory increments.
 * @param backing_store (Output) Boolean flag indicating whether to
 * write the file contents to disk when the file is closed.
 * 
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply unsigned int* OUTPUT {size_t *increment}
%apply unsigned int* OUTPUT {hbool_t *backing_store}
herr_t H5Pget_fapl_core(hid_t fapl_id, size_t *increment/*out*/,
			hbool_t *backing_store/*out*/);
%clear size_t *increment;
%clear hbool_t *backing_store;

/**
 * Retrieves direct I/O driver settings.
 *
 * <p>H5Pget_fapl_direct retrieves the required memory alignment
 * (alignment), file system block size (block_size), and copy buffer
 * size (cbuf_size) settings for the direct I/O driver, H5FD_DIRECT,
 * from the file access property list fapl_id.</p>
 *
 * <p>See H5Pset_fapl_direct for discussion of these values,
 * requirements, and important considerations. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param alignment (Output) Required memory alignment boundary.
 * @param block_size (Output) File system block size.
 * @param cbuf_size (Output) Copy buffer size.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
#ifdef H5_HAVE_DIRECT
herr_t H5Pget_fapl_direct(hid_t fapl_id, size_t *boundary/*out*/, 
			size_t *block_size/*out*/, size_t *cbuf_size/*out*/);
#endif

/**
 * Returns file access property list information.
 *
 * <p>H5Pget_fapl_family returns file access property list for use
 * with the family driver. This information is returned through the
 * output parameters.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param memb_size (Output) Size in bytes of each file member.
 * @param memb_fapl_id (Output) Identifier of file access property
 * list for each family member.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply long long* OUTPUT {hsize_t* memb_size}
%apply int* OUTPUT {hid_t* memb_fapl_id}
herr_t H5Pget_fapl_family(hid_t fapl_id, hsize_t *memb_size/*out*/,
			  hid_t *memb_fapl_id/*out*/);
%clear hsize_t* memb_size;
%clear hid_t* memb_fapl_id;

/**
 * Returns MPI communicator information.
 *
 * <p>If the file access property list is set to the H5FD_MPIO driver,
 * H5Pget_fapl_mpio returns the MPI communicator and information
 * through the comm and info pointers, if those values are
 * non-null.</p>
 *
 * <p>Neither comm nor info is copied, so they are valid only until
 * the file access property list is either modified or closed. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param comm (Output) MPI-2 communicator.
 * @param info (Output) MPI-2 info object.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pget_fapl_mpio(hid_t fapl_id, MPI_Comm *comm/*out*/, 
  			MPI_Info *info/*out*/);
#endif


/**
 * Returns MPI communicator information.
 *
 * <p>If the file access property list is set to the H5FD_MPIO driver,
 * H5Pget_fapl_mpiposix returns the MPI communicator through the comm
 * pointer, if those values are non-null.</p>
 *
 * <p>comm is not copied, so it is valid only until the file access
 * property list is either modified or closed. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param comm (Output) MPI-2 communicator.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pget_fapl_mpiposix(hid_t fapl_id, MPI_Comm *comm/*out*/, 
			    hbool_t *use_gpfs/*out*/);
#endif

/**
 * Returns information about the multi-file access property list.
 *
 * <p>H5Pget_fapl_multi returns information about the multi-file
 * access property list.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param memb_map (Output) Maps memory usage types to other memory
 * usage types.
 * @param memb_fapl (Output) Property list for each memory usage type.
 * @param memb_name (Output) Name generator for names of member files.
 * @param memb_addr (Output) The offsets within the virtual address
 * space, from 0 (zero) to HADDR_MAX, at which each type of data
 * storage begins.
 * @param relax (Output) Allows read-only access to incomplete file
 * sets when TRUE.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply H5FD_MEM_ENUM OUTPUT[] {H5FD_mem_t *memb_map}
%apply int OUTPUT[] {hid_t* memb_fapl}
%apply char** HSTRARR_OUTPUT {char **memb_name}
%apply long long OUTPUT[] {haddr_t* memb_addr}
%apply unsigned int* OUTPUT {hbool_t* relax}
herr_t H5Pget_fapl_multi(hid_t fapl_id, H5FD_mem_t *memb_map/*out*/,
			 hid_t *memb_fapl/*out*/, char **memb_name/*out*/,
			 haddr_t *memb_addr/*out*/, hbool_t *relax/*out*/);
%clear hid_t* memb_fapl;
%clear H5FD_mem_t *memb_map;
%clear char **memb_name;
%clear haddr_t* memb_addr;
%clear hbool_t* relax;

/**
 * Returns the file close degree.
 *
 * <p>H5Pget_fclose_degree returns the current setting of the file
 * close degree property fc_degree in the file access property list
 * fapl_id.</p>
 *
 * <p>The value of fc_degree determines how aggressively H5Fclose
 * deals with objects within a file that remain open when H5Fclose is
 * called to close that file. fc_degree can have any one of four valid
 * values as described in H5Pset_fclose_degree. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param fc_degree (Output) Pointer to a location to which to return
 * the file close degree property, the value of fc_degree.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply H5F_CLOSE_ENUM* OUTPUT {H5F_close_degree_t* degree}
herr_t H5Pget_fclose_degree(hid_t fapl_id, H5F_close_degree_t *degree);
%clear H5F_close_degree_t* degree;

/** 
 * Retrieves the time when fill value are written to a dataset.
 *
 * <p>H5Pget_fill_time examines the dataset creation property list
 * plist_id to determine when fill values are to be written to a
 * dataset.</p>
 *
 * <p>Valid values returned in fill_time are as follows:</p>
 <dl>
    <dt>H5D_FILL_TIME_IFSET</dt>
    <dd>Fill values are written to the dataset when storage space is
    allocated only if there is a user-defined fill value, i.e., one
    set with H5Pset_fill_value.  (Default)</dd>
    <dt>H5D_FILL_TIME_ALLOC</dt>
    <dd>Fill values are written to the dataset when storage space is
    allocated.</dd>
    <dt>H5D_FILL_TIME_NEVER</dt>
    <dd>Fill values are never written to the dataset.</dd>
 </dl>
 *
 * <p>Note:<br/> H5Pget_fill_time is designed to work in coordination
 * with the dataset fill value and dataset storage allocation time
 * properties, retrieved with the functions H5Pget_fill_value and
 * H5Pget_alloc_time.</p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param fill_time (Output) Setting for the timing of writing fill
 * values to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply H5D_FTIME_ENUM* OUTPUT {H5D_fill_time_t* fill_time}
herr_t H5Pget_fill_time(hid_t plist_id, H5D_fill_time_t *fill_time/*out*/);
%clear H5D_fill_time_t* fill_time;

/**
 * Retrieves a dataset fill value.
 *
 * <p>H5Pget_fill_value returns the dataset fill value defined in the
 * dataset creation property list plist_id.</p>
 *
 * <p>The fill value is returned through the value pointer and will be
 * converted to the datatype specified by type_id. This datatype may
 * differ from the fill value datatype in the property list, but the
 * HDF5 library must be able to convert between the two datatypes.</p>
 *
 * <p>If the fill value is undefined, i.e., set to NULL in the
 * property list, H5Pget_fill_value will return an
 * error. H5Pfill_value_defined should be used to check for this
 * condition before H5Pget_fill_value is called.</p>
 *
 * <p>Memory must be allocated by the calling application. </p>
 *
 * <p>Note:<br/> H5Pget_fill_value is designed to coordinate with the
 * dataset storage allocation time and fill value write time
 * properties, which can be retrieved with the functions
 * H5Pget_alloc_time and H5Pget_fill_time, respectively.</p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param type_id Datatype identifier for the value passed via value.
 * @param value (Output) Pointer to buffer to contain the returned fill value.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply void* BUFF {const void* value}
herr_t H5Pget_fill_value(hid_t plist_id, hid_t type_id,  void *value/*out*/);
%clear void* value;

/**
 *
 * Returns information about a filter in a pipeline.
 *
 *
 * <p>H5Pget_filter1 returns information about a filter, specified by
 * its filter number, in a filter pipeline, specified by the property
 * list with which it is associated.</p>
 *
 * <p>If plist is a dataset creation property list, the pipeline is a
 * permanent filter pipeline; if plist is a dataset transfer property
 * list, the pipeline is a transient filter pipeline.</p>
 *
 * <p>idx is a value between zero and N-1, as described in
 * H5Pget_nfilters. The function will return a negative value if the
 * filter number is out of range.</p>
 *
 * <p>The structure of the flags argument is discussed in
 * H5Pset_filter.</p>
 *
 * <p>On input, cd_nelmts indicates the number of entries in the
 * cd_values array, as allocated by the caller; on return,cd_nelmts
 * contains the number of values defined by the filter.</p>
 *
 * <p>If name is a pointer to an array of at least namelen bytes, the
 * filter name will be copied into that array. The name will be null
 * terminated if namelen is large enough. The filter name returned
 * will be the name appearing in the file, the name registered for the
 * filter, or an empty string.</p>
 *
 * <p>On successful return, filter identifiers are as follows:</p>
 <dl>
    <dt>H5Z_FILTER_DEFLATE</dt>
    <dd>Data compression filter, employing the gzip algorithm</dd>
    <dt>H5Z_FILTER_SHUFFLE</dt>
    <dd>Data shuffling filter</dd>
    <dt>H5Z_FILTER_FLETCHER32</dt>
    <dd>Error detection filter, employing the Fletcher32 checksum
    algorithm</dd>
    <dt>H5Z_FILTER_SZIP</dt>
    <dd>Data compression filter, employing the SZIP algorithm</dd>
    <dt>H5Z_FILTER_NBIT</dt>
    <dd>Data compression filter, employing the N-bit algorithm</dd>
    <dt>H5Z_FILTER_SCALEOFFSET</dt>
    <dd>Data compression filter, employing the scale-offset algorithm</dd>
 </dl>
 *
 * <p>Note:<br/> This function currently supports only the permanent
 * filter pipeline; plist must be a dataset creation property
 * list.</p>
 *
 * @param plist Property list identifier.
 * @param idx Sequence number within the filter pipeline of the filter
 * for which information is sought.
 * @param flags (Output) Bit vector specifying certain general
 * properties of the filter.
 * @param cd_nelmts (Input/Output) Number of elements in cd_values.
 * @param cd_values (Output) Auxiliary data for the filter.
 * @param namelen Anticipated number of characters in name.
 * @param name (Output) Name of the filter.
 *
 * @return Returns the filter identifier if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply unsigned int* OUTPUT {unsigned int* flags}
%apply long* INOUT {size_t* cd_nelmts}
%apply unsigned OUTPUT[] {unsigned cd_values[]}
%apply signed char OUTPUT[] {char name[]}
%javamethodmodifiers H5Pget_filter1 "@Deprecated\n   public"
H5Z_filter_t H5Pget_filter1(hid_t plist_id, unsigned idx,
       unsigned int *flags/*out*/,
       size_t* cd_nelmts/*out*/,
       unsigned cd_values[]/*out*/,
       size_t namelen, char name[]);
%clear unsigned int* flags;
%clear size_t* cd_nelmts;
%clear unsigned cd_values[];
%clear char name[];

/**
 * Returns information about a filter in a pipeline.
 *
 * <p>H5Pget_filter2 returns information about a filter, specified by
 * its filter number, in a filter pipeline, specified by the property
 * list with which it is associated.</p>
 *
 * <p>If plist is a dataset creation property list, the pipeline is a
 * permanent filter pipeline; if plist is a dataset transfer property
 * list, the pipeline is a transient filter pipeline.</p>
 *
 * <p>idx is a value between zero and N-1, as described in
 * H5Pget_nfilters. The function will return a negative value if the
 * filter number is out of range.</p>
 *
 * <p>The structure of the flags argument is discussed in
 * H5Pset_filter.</p>
 *
 * <p>On input, cd_nelmts indicates the number of entries in the
 * cd_values array, as allocated by the caller; on return,cd_nelmts
 * contains the number of values defined by the filter.</p>
 *
 * <p>If name is a pointer to an array of at least namelen bytes, the
 * filter name will be copied into that array. The name will be null
 * terminated if namelen is large enough. The filter name returned
 * will be the name appearing in the file, the name registered for the
 * filter, or an empty string.</p>
 *
 * <p>filter_config is the bit field described in
 * H5Zget_filter_info.</p>
 *
 * <p>On successful return, filter identifiers are as follows:</p>
 <dl>
    <dt>H5Z_FILTER_DEFLATE</dt>
    <dd>Data compression filter, employing the gzip algorithm</dd>
    <dt>H5Z_FILTER_SHUFFLE</dt>
    <dd>Data shuffling filter</dd>
    <dt>H5Z_FILTER_FLETCHER32</dt>
    <dd>Error detection filter, employing the Fletcher32 checksum
    algorithm</dd>
    <dt>H5Z_FILTER_SZIP</dt>
    <dd>Data compression filter, employing the SZIP algorithm</dd>
    <dt>H5Z_FILTER_NBIT</dt>
    <dd>Data compression filter, employing the N-bit algorithm</dd>
    <dt>H5Z_FILTER_SCALEOFFSET</dt>
    <dd>Data compression filter, employing the scale-offset algorithm</dd>
 </dl>
 *
 * <p>Note:<br/> This function currently supports only the permanent
 * filter pipeline; plist must be a dataset creation property
 * list.</p>
 *
 * @param plist Property list identifier.
 * @param idx Sequence number within the filter pipeline of the filter
 * for which information is sought.
 * @param flags (Output) Bit vector specifying certain general
 * properties of the filter.
 * @param cd_nelmts (Output) Number of elements in cd_values.
 * @param cd_values (Output) Auxiliary data for the filter.
 * @param namelen Anticipated number of characters in name.
 * @param name (Output) Name of the filter.
 * @param filter_config (Output) Bit field, as described in
 * H5Zget_filter_info.
 *
 * @return Returns the filter identifier if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply unsigned int* OUTPUT {unsigned int* flags}
%apply long* INOUT {size_t* cd_nelmts}
%apply unsigned OUTPUT[] {unsigned cd_values[]}
%apply signed char OUTPUT[] {char name[]}
%apply unsigned int* OUTPUT {unsigned int* filter_config}
H5Z_filter_t H5Pget_filter2(hid_t plist_id, unsigned filter,
       unsigned int *flags/*out*/,
       size_t *cd_nelmts/*out*/,
       unsigned cd_values[]/*out*/,
       size_t namelen, char name[],
       unsigned int* filter_config /*out*/);
%clear unsigned int* flags;
%clear size_t* cd_nelmts;
%clear unsigned cd_values[];
%clear char name[];
%clear unsigned int* filter_config;

/**
 * Returns information about the specified filter.
 *
 * <p>H5Pget_filter_by_id1 returns information about the filter
 * specified in filter_id, a filter identifier.</p>
 *
 * <p>plist_id must identify a dataset creation property list and
 * filter_id will be in a permanent filter pipeline.</p>
 *
 * <p>The filter_id and flags parameters are used in the same manner
 * as described in the discussion of H5Pset_filter.</p>
 *
 * <p>Aside from the fact that they are used for output, the
 * parameters cd_nelmts and cd_values[] are used in the same manner as
 * described in the discussion of H5Pset_filter. On input, the
 * cd_nelmts parameter indicates the number of entries in the
 * cd_values[] array allocated by the calling program; on exit it
 * contains the number of values defined by the filter.</p>
 *
 * <p>On input, the name_len parameter indicates the number of
 * characters allocated for the filter name by the calling program in
 * the array name[]. On exit it contains the length in characters of
 * name of the filter. On exit name[] contains the name of the filter
 * with one character of the name in each element of the array.</p>
 *
 * <p>If the filter specified in filter_id is not set for the property
 * list, an error will be returned and H5Pget_filter_by_id1 will
 * fail. </p>
 *
 * @param plist_id Property list identifier.
 * @param filter_id Filter identifier.
 * @param flags (Output) Bit vector specifying certain general
 * properties of the filter.
 * @param cd_nelmts (Output) Number of elements in cd_values.
 * @param cd_values (Output) Auxiliary data for the filter.
 * @param namelen (Output) Length of filter name and number of
 * elements in name.
 * @param name (Output) Name of filter.
 *
 * @return Returns the filter identifier if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply unsigned int* OUTPUT {unsigned int* flags}
%apply long* INOUT {size_t* cd_nelmts}
%apply unsigned OUTPUT[] {unsigned cd_values[]}
%apply signed char OUTPUT[] {char name[]}
%javamethodmodifiers H5Pget_filter_by_id1 "@Deprecated\n   public"
H5Z_filter_t H5Pget_filter_by_id1(hid_t plist_id, H5Z_filter_t id,
       unsigned int *flags/*out*/,
       size_t *cd_nelmts/*out*/,
       unsigned cd_values[]/*out*/,
       size_t namelen, char name[]);
%clear unsigned int* flags;
%clear size_t* cd_nelmts;
%clear unsigned cd_values[];
%clear char name[];

/**
 * Returns information about the specified filter.
 *
 * <p>H5Pget_filter_by_id2 returns information about the filter
 * specified in filter_id, a filter identifier.</p>
 *
 * <p>plist_id must identify a dataset creation property list and
 * filter_id will be in a permanent filter pipeline.</p>
 *
 * <p>The filter_id and flags parameters are used in the same manner
 * as described in the discussion of H5Pset_filter.</p>
 *
 * <p>Aside from the fact that they are used for output, the
 * parameters cd_nelmts and cd_values[] are used in the same manner as
 * described in the discussion of H5Pset_filter. On input, the
 * cd_nelmts parameter indicates the number of entries in the
 * cd_values[] array allocated by the calling program; on exit it
 * contains the number of values defined by the filter.</p>
 *
 * <p>On input, the name_len parameter indicates the number of
 * characters allocated for the filter name by the calling program in
 * the array name[]. On exit it contains the length in characters of
 * name of the filter. On exit name[] contains the name of the filter
 * with one character of the name in each element of the array.</p>
 *
 * <p>filter_config is the bit field described in
 * H5Zget_filter_info.</p>
 *
 * <p>If the filter specified in filter_id is not set for the property
 * list, an error will be returned and H5Pget_filter_by_id2 will
 * fail. </p>
 *
 * @param plist_id Property list identifier.
 * @param filter_id Filter identifier.
 * @param flags (Output) Bit vector specifying certain general
 * properties of the filter.
 * @param cd_nelmts (Output) Number of elements in cd_values.
 * @param cd_values (Output) Auxiliary data for the filter.
 * @param namelen (Output) Length of filter name and number of
 * elements in name.
 * @param filter_config (Output) Bit field, as described in
 * H5Zget_filter_info.
  *
 * @return Returns the filter identifier if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply unsigned int* OUTPUT {unsigned int* flags}
%apply long* INOUT {size_t* cd_nelmts}
%apply unsigned OUTPUT[] {unsigned cd_values[]}
%apply signed char OUTPUT[] {char name[]}
%apply unsigned int* OUTPUT {unsigned int* filter_config}
H5Z_filter_t H5Pget_filter_by_id2(hid_t plist_id, H5Z_filter_t id,
       unsigned int *flags/*out*/, size_t *cd_nelmts/*out*/,
       unsigned cd_values[]/*out*/, size_t namelen, char name[]/*out*/,
       unsigned int* filter_config/*out*/);
%clear unsigned int* flags;
%clear size_t* cd_nelmts;
%clear unsigned cd_values[];
%clear char name[];
%clear unsigned int* filter_config;

/**
 * Returns garbage collecting references setting.
 *
 * <p>H5Pget_gc_references returns the current setting for the garbage
 * collection references property from the specified file access
 * property list. The garbage collection references property is set by
 * H5Pset_gc_references.</p>
 *
 * @param plist File access property list identifier.
 * @param gc_ref (Output) Flag returning the state of reference
 * garbage collection. A returned value of 1 indicates that garbage
 * collection is on while 0 indicates that garbage collection is off.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply unsigned* OUTPUT {unsigned* gc_ref}
herr_t H5Pget_gc_references(hid_t fapl_id, unsigned *gc_ref/*out*/);

/**
 * Retrieves number of I/O vectors to be read/written in hyperslab I/O.
 *
 * <p>H5Pset_hyper_vector_size retrieves the number of I/O vectors to
 * be accumulated in memory before being issued to the lower levels of
 * the HDF5 library for reading or writing the actual data.</p>
 *
 * <p>The number of I/O vectors set in the dataset transfer property
 * list dxpl_id is returned in vector_size. Unless the default value
 * is in use, vector_size was previously set with a call to
 * H5Pset_hyper_vector_size. </p>
 *
 * @param dxpl_id Dataset transfer property list identifier.
 * @param vector_size (Output) Number of I/O vectors to accumulate in
 * memory for I/O operations.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
%apply unsigned long* OUTPUT {size_t* size}
herr_t H5Pget_hyper_vector_size(hid_t fapl_id, size_t *size/*out*/);
%clear size_t* size;

/**
 * Queries the 1/2 rank of an indexed storage B-tree.
 *
 * <p>H5Pget_istore_k queries the 1/2 rank of an indexed storage
 * B-tree. The argument ik may be the null pointer (NULL). This
 * function is only valid for file creation property lists.</p>
 *
 * <p>See H5Pset_istore_k for details. </p>
 *
 * @param plist_id Identifier of property list to query.
 * @param ik (Output) Pointer to location to return the chunked
 * storage B-tree 1/2 rank.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
%apply unsigned* OUTPUT {unsigned* ik}
herr_t H5Pget_istore_k(hid_t plist_id, unsigned *ik/*out*/);

/**
 * Returns the layout of the raw data for a dataset.
 *
 * <p>H5Pget_layout returns the layout of the raw data for a
 * dataset. This function is only valid for dataset creation property
 * lists.</p>
 *
 * <p>Returns the layout type of a dataset creation property list if
 * successful. Valid return values are:</p>
 <dl>
    <dt>H5D_COMPACT</dt>
    <dd>Raw data is stored in the object header in the file. </dd>
    <dt>H5D_CONTIGUOUS</dt>
    <dd>Raw data is stored separately from the object header in one
    contiguous chunk in the file. </dd>
    <dt>H5D_CHUNKED</dt>
    <dd>Raw data is stored separately from the object header in chunks
    in separate locations in the file. </dd>
 </dl>
 * <p>Note that a compact storage layout may affect writing data to
 * the dataset with parallel applications. See note in H5Dwrite
 * documentation for details. </p>
 *
 * @param dcpl_id Identifier for property list to query.
 *
 * @return Returns the layout type (a non-negative value) of a dataset
 * creation property list if successful. Otherwise, returns a negative
 * value indicating failure.
 *
 * @ingroup plDsCreate
 */
H5D_layout_t H5Pget_layout(hid_t dcpl_id);


/**
 * Retrieves library version bounds settings that indirectly control
 * the format versions used when creating objects.
 *
 * <p>H5Pget_libver_bounds retrieves the lower and upper bounds on the
 * HDF5 Library versions that indirectly determine the object formats
 * versions used when creating objects in the file.</p>
 *
 * <p>This property is retrieved from the file access property list
 * specified by fapl_id. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param low (Output) The earliest version of the library that will
 * be used for writing objects. The library version indirectly
 * specifies the earliest object format version that can be used when
 * creating objects in the file. Currently, HDF_LIBVER_EARLIEST and
 * HDF_LIBVER_LATEST are the only valid values for low.
 * @param high (Output) The latest version of the library that will be
 * used for writing objects. The library version indirectly specifies
 * the latest object format version that can be used when creating
 * objects in the file.Currently, HDF_LIBVER_LATEST is the only valid
 * value for high.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply H5F_LIBVER_ENUM* OUTPUT {H5F_libver_t* low}
%apply H5F_LIBVER_ENUM* OUTPUT {H5F_libver_t* high}
herr_t H5Pget_libver_bounds(hid_t plist_id, 
			    H5F_libver_t *low,
			    H5F_libver_t *high);
%clear H5F_libver_t* low;
%clear H5F_libver_t* high;

/**
 * Queries whether link creation order is tracked and/or indexed in a group.
 *
 * <p>H5Pget_link_creation_order queries the group creation property
 * list, gcpl_id, and returns a flag indicating whether link creation
 * order is tracked and/or indexed in a group.</p>
 *
 * <p>See H5Pset_link_creation_order for a list of valid creation
 * order flags, as passed in crt_order_flags, and their meanings. </p>
 *
 * @param gcpl_id Group creation property list identifier
 * @param crt_order_flags (Ouput) Creation order flag(s)
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGrpCreate
 */
%apply unsigned* OUTPUT {unsigned* crt_order_flags}
herr_t H5Pget_link_creation_order(hid_t plist_id,  unsigned* crt_order_flags);
%clear unsigned* crt_order_flags;

/**
 * Queries the settings for conversion between compact and dense groups.
 *
 * <p>H5Pget_link_phase_change queries the maximum number of entries
 * for a compact group and the minimum number links to require before
 * converting a group to a dense form.</p>
 *
 * <p>In the compact format, links are stored as messages in the
 * group's header. In the dense format, links are stored in a fractal
 * heap and indexed with a version 2 B-tree.</p>
 *
 * <p>max_compact is the maximum number of links to store as header
 * messages in the group header before converting the group to the
 * dense format. Groups that are in the compact format and exceed this
 * number of links are automatically converted to the dense
 * format.</p>
 *
 * <p>min_dense is the minimum number of links to store in the dense
 * format. Groups which are in dense format and in which the number of
 * links falls below this number are automatically converted back to
 * the compact format.</p>
 *
 * <p>In the compact format, links are stored as messages in the
 * group's header. In the dense format, links are stored in a fractal
 * heap and indexed with a version 2 B-tree.</p>
 *
 * <p>See H5Pset_link_phase_change for a discussion of traditional,
 * compact, and dense group storage. </p>
 *
 * @param gcpl_id Group creation property list identifier.
 * @param max_compact (Output) Maximum number of links for compact storage.
 * @param min_dense (Output) Minimum number of links for dense storage.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGrpCreate
 */
%apply unsigned* OUTPUT {unsigned* max_compact}
%apply unsigned* OUTPUT {unsigned* min_dense}
herr_t H5Pget_link_phase_change(hid_t plist_id, unsigned *max_compact /*out*/, 
				unsigned *min_dense /*out*/);
%clear unsigned* max_compact;
%clear unsigned* min_dense;

/**
 * Queries the local heap size hint for original-style groups.
 *
 * <p>H5Pget_local_heap_size_hint queries the group creation property
 * list, gcpl_id, for the local heap size hint, size_hint, for
 * original-style groups, i.e., for groups of the style used prior to
 * HDF5 Release 1.8.0.</p>
 *
 * <p>See H5Pset_local_heap_size_hint for further discussion. </p>
 *
 * @param gcpl_id Group creation property list identifier.
 * @param size_hint (Output) Hint for size of local heap.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGrpCreate
 */
%apply unsigned long* OUTPUT {size_t *size_hint}
herr_t H5Pget_local_heap_size_hint(hid_t plist_id, size_t *size_hint /*out*/);
%clear size_t *size_hint;

/**
 * Get the current initial metadata cache configuration from the
 * indicated File Access Property List.
 *
 * <p>H5Pget_mdc_config gets the initial metadata cache configuration
 * contained in a file access property list and loads it into the
 * instance of H5AC_cache_config_t pointed to by the config_ptr
 * parameter. This configuration is used when the file is opened.</p>
 *
 * <p>Note that the version field of *config_ptr must be initialized;
 * this allows the library to support old versions of the
 * H5AC_cache_config_t structure.</p>
 *
 * <p>See the overview of the metadata cache in the special topics
 * section of the user guide for details on the configuration data
 * returned. </p>
 *
 * @param plist_id Identifier of the file access property list.
 * @param config_ptr (Input/Ouput) Pointer to the instance of
 * H5AC_cache_config_t in which the current metadata cache
 * configuration is to be reported. 
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plMeta
 */
herr_t H5Pget_mdc_config(hid_t plist_id, H5AC_cache_config_t* config_ptr);

/**
 * Returns the current metadata block size setting.
 *
 * <p>H5Pget_meta_block_size returns the current minimum size, in
 * bytes, of new metadata block allocations. This setting is retrieved
 * from the file access property list fapl_id.</p>
 *
 * <p>This value is set by H5Pset_meta_block_size and is retrieved
 * from the file access property list fapl_id. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param size (Output) Minimum size, in bytes, of metadata block allocations.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply long long* OUTPUT {hsize_t* size}
herr_t H5Pget_meta_block_size(hid_t fapl_id, hsize_t* size/*out*/);
%clear hsize_t* size;

/**
 * Retrieves type of data property for MULTI driver.
 *
 * <p>H5Pget_multi_type retrieves the type of data setting from the
 * file access or data transfer property list fapl_id. This enables a
 * user application to specify the type of data the application wishes
 * to access so that the application can retrieve a file handle for
 * low-level access to the particular member of a set of MULTI files
 * in which that type of data is stored. The file handle is retrieved
 * with a separate call to H5Fget_vfd_handle (or, in special
 * circumstances, to H5FDget_vfd_handle; see Virtual File Layer and
 * List of VFL Functions in HDF5 Technical Notes).</p>
 *
 * <p>The type of data returned in type will be one of those listed in
 * the discussion of the type parameter in the the description of the
 * function H5Pset_multi_type.</p>
 *
 * <p>Use of this function is only appropriate for an HDF5 file
 * written as a set of files with the MULTI file driver. </p>
 *
 * @param fapl_id File access property list or data transfer property
 * list identifier.
 * @param type (Output) Type of data.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply H5FD_MEM_ENUM* OUTPUT {H5FD_mem_t* type}
herr_t H5Pget_multi_type(hid_t fapl_id, H5FD_mem_t *type);
%clear H5FD_mem_t* type;

/**
 * Returns the number of filters in the pipeline.
 *
 * <p>H5Pget_nfilters returns the number of filters defined in the
 * filter pipeline associated with the property list plist_id.</p>
 *
 * <p>In each pipeline, the filters are numbered from 0 through N-1,
 * where N is the value returned by this function. During output to
 * the file, the filters are applied in increasing order; during input
 * from the file, they are applied in decreasing order.</p>
 *
 * <p>H5Pget_nfilters returns the number of filters in the pipeline,
 * including zero (0) if there are none. </p>
 *
 * <p>Note:<br/> This function currently supports only the permanent
 * filter pipeline; plist_id must be a dataset creation property
 * list.</p>
 *
 * @param plist_id Property list identifier.
 *
 * @return Returns the number of filters in the pipeline if
 * successful; otherwise returns a negative value.
 *
 * @ingroup plDsCreate
 */
int H5Pget_nfilters(hid_t plist_id);

/**
 * Gets the maximum number of link traversals.
 *
 * <p>H5Pget_nlinks retrieves the maximum number of soft or
 * user-defined link traversals allowed, nlinks, before the library
 * assumes it has found a cycle and aborts the traversal. This value
 * is retrieved from the link access property list lapl_id.</p>
 *
 * <p>The limit on the number soft or user-defined link traversals is
 * designed to terminate link traversal if one or more links form a
 * cycle. User control is provided because some files may have
 * legitimate paths formed of large numbers of soft or user-defined
 * links. This property can be used to allow traversal of as many
 * links as desired.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param nlinks (Output) Maximum number of links to traverse.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plLnkAccess
 */
%apply unsigned long* OUTPUT {size_t *nlinks}
herr_t H5Pget_nlinks(hid_t plist_id, size_t *nlinks);
%clear size_t *nlinks;

/**
 * Queries number of properties in property list or class.
 *
 * <p>H5Pget_nprops retrieves the number of properties in a property
 * list or class. If a property class identifier is given, the number
 * of registered properties in the class is returned in nprops. If a
 * property list identifier is given, the current number of properties
 * in the list is returned in nprops.</p>
 *
 * @param id Identifier of property object to query.
 * @param nprops (Output) Number of properties in object.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
%apply unsigned long* OUTPUT {size_t* nprops}
herr_t H5Pget_nprops(hid_t id, size_t *nprops);

/**
 * Checks status of the dataset transfer property list.
 *
 * <p>H5Pget_preserve checks the status of the dataset transfer
 * property list.</p>
 *
 * @param dxpl_id Identifier for the dataset transfer property list.
 *
 * @return Returns TRUE or FALSE if successful; otherwise returns a
 * negative value.
 *
 * @ingroup plDsAccess
 */
int H5Pget_preserve(hid_t dxpl_id);

/**
 * Determines whether times associated with an object are being recorded.
 *
 * <p>H5get_obj_track_times queries the object creation property list,
 * ocpl_id, to determine whether object times are being recorded.</p>
 *
 * <p>If track_times is returned as TRUE, times are being recorded; if
 * track_times is returned as FALSE, times are not being recorded.</p>
 *
 * <p>Time data can be retrieved with H5Oget_info, which will return
 * it in the H5O_info_t struct.</p>
 *
 * <p>If times are not tracked, they will be reported as follows when
 * queried: 12:00 AM UDT, Jan. 1, 1970</p>
 *
 * <p>See H5Pset_obj_track_times for further discussion. </p>
 *
 * @param ocpl_id Object creation property list identifier.
 * @param track_times (Output) Boolean value, TRUE or FALSE, specifying
 * whether object times are being recorded.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
%apply unsigned* OUTPUT {hbool_t* track_times}
herr_t H5Pget_obj_track_times(hid_t plist_id, hbool_t* track_times);
%clear hbool_t* track_times;

/**
 * Retrieves the configuration settings for a shared message index.
 *
 * <p>H5Pget_shared_mesg_index retrieves the message type and minimum
 * message size settings from the file creation property list fcpl_id
 * for the shared object header message index specified by
 * index_num.</p>
 *
 * <p>index_num specifies the index. index_num is zero-indexed, so in
 * a file with three indexes, they will be numbered 0, 1, and 2.</p>
 *
 * <p>mesg_type_flags and min_mesg_size will contain, respectively,
 * the types of messages and the minimum size, in bytes, of messages
 * that can be stored in this index.</p>
 *
 * <p>Valid message types are described in
 * H5Pset_shared_mesg_index. </p>
 *
 * @param fcpl_id File creation property list identifier.
 * @param index_num Index being configured.
 * @param mesg_type_flags (Output) Types of messages that may be
 * stored in this index.
 * @param min_mesg_size (Output) Minimum message size.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
%apply unsigned* OUTPUT {unsigned *mesg_type_flags}
%apply unsigned* OUTPUT {unsigned *min_mesg_size}
herr_t H5Pget_shared_mesg_index(hid_t plist_id, unsigned index_num, 
				unsigned *mesg_type_flags, 
				unsigned *min_mesg_size);
%clear unsigned *mesg_type_flags;
%clear unsigned *min_mesg_size;

/**
 * Retrieves number of shared object header message indexes in file
 * creation property list.
 *
 * <p>H5Pget_shared_mesg_nindexes retrieves the number of shared
 * object header message indexes in the specified file creation
 * property list fcpl_id.</p>
 *
 * <p>If the value of nindexes is 0 (zero), shared object header
 * messages are disabled in files created with this property
 * list. </p>
 *
 * @param fcpl_id File creation property list.
 * @param nindexes (Output) Number of shared object header message
 * indexes available in files created with this property list
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
%apply unsigned int* OUTPUT {unsigned int* nindexes}
herr_t H5Pget_shared_mesg_nindexes(hid_t plist_id, unsigned int* nindexes);
%clear unsigned int* nindexes;

/**
 * Retrieves shared object header message phase change information.
 *
 * <p>H5Pget_shared_mesg_phase_change retrieves the threshold values
 * for storage of shared object header message indexes in a
 * file. These phase change thresholds determine the point at which
 * the index storage mechanism changes from a more compact list format
 * to a more performance-oriented B-tree format, and vice-versa.</p>
 *
 * <p>By default, a shared object header message index is initially
 * stored as a compact list. When the number of messages in an index
 * exceeds the specified max_list threshold, storage switches to a
 * B-tree format for impoved performance. If the number of messages
 * subsequently falls below the min_btree threshold, the index will
 * revert to the list format.</p>
 *
 * <p>If max_compact is set to 0 (zero), shared object header message
 * indexes in the file will always be stored as B-trees.</p>
 *
 * <p>fcpl_id specifies the file creation property list. </p>
 *
 * @param fcpl_id File creation property list identifier.
 * @param max_compact (Output) Threshold above which storage of a
 * shared object header message index shifts from list to B-tree.
 * @param min_btree (Output) Threshold below which storage of a shared
 * object header message index reverts to list format
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
%apply unsigned int* OUTPUT {unsigned int* max_list}
%apply unsigned int* OUTPUT {unsigned int* min_btree}
herr_t H5Pget_shared_mesg_phase_change(hid_t plist_id, unsigned int* max_list, 
				       unsigned int* min_btree);
%clear unsigned int* max_list;
%clear unsigned int* min_btree;

/**
 * Returns maximum data sieve buffer size.
 *
 * <p>H5Pget_sieve_buf_size retrieves, size, the current maximum size
 * of the data sieve buffer.</p>
 *
 * <p>This value is set by H5Pset_sieve_buf_size and is retrieved from
 * the file access property list fapl_id. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param size (Output) Maximum size, in bytes, of data sieve buffer.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply unsigned long* OUTPUT {size_t* size}
herr_t H5Pget_sieve_buf_size(hid_t fapl_id, size_t *size/*out*/);
%clear size_t* size;

/**
 * Queries the size of a property value in bytes.
 *
 * <p>H5Pget_size retrieves the size of a property's value in
 * bytes. This function operates on both property lists and property
 * classes</p>
 *
 * <p>Zero-sized properties are allowed and return 0. </p>
 *
 * @param id Identifier of property object to query.
 * @param name Name of property to query.
 * @param size (Output) Size of property in bytes.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
%apply unsigned long* OUTPUT {size_t* size}
herr_t H5Pget_size(hid_t id, const char *name, size_t *size);
%clear size_t* size;

/**
 * Retrieves the size of the offsets and lengths used in an HDF5 file.
 *
 * <p>H5Pget_sizes retrieves the size of the offsets and lengths used
 * in an HDF5 file. This function is only valid for file creation
 * property lists.</p>
 *
 * @param plist Identifier of property list to query.
 * @param sizeof_addr (Output) Pointer to location to return offset
 * size in bytes.
 * @param sizeof_size (Output) Pointer to location to return length
 * size in bytes.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
%apply unsigned long* OUTPUT {size_t* sizeof_addr}
%apply unsigned long* OUTPUT {size_t* sizeof_size}
herr_t H5Pget_sizes(hid_t plist_id, size_t *sizeof_addr/*out*/,
                                    size_t *sizeof_size/*out*/);
%clear size_t* sizeof_addr;
%clear size_t* sizeof_size;

/**
 * Retrieves the current small data block size setting.
 *
 * <p>H5Pget_small_data_block_size retrieves the current setting for
 * the size of the small data block.</p>
 *
 * <p>If the returned value is zero (0), the small data block
 * mechanism has been disabled for the file. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param size (Output) Maximum size, in bytes, of the small data block.
 *
 * @return Returns a non-negative value if successful; otherwise a
 * negative value.
 *
 * @ingroup plFAccess
 */
%apply long long* OUTPUT {hsize_t* size}
herr_t H5Pget_small_data_block_size(hid_t fapl_id, hsize_t *size/*out*/);
%clear hsize_t* size;

/**
 * Retrieves the size of the symbol table B-tree 1/2 rank and the
 * symbol table leaf node 1/2 size.
 *
 * <p>H5Pget_sym_k retrieves the size of the symbol table B-tree 1/2
 * rank and the symbol table leaf node 1/2 size. This function is only
 * valid for file creation property lists. If a parameter valued is
 * set to NULL, that parameter is not retrieved. See the description
 * for H5Pset_sym_k for more information. </p>
 *
 * @param plist Property list to query.
 * @param ik (Output) Pointer to location to return the symbol table's
 * B-tree 1/2 rank.
 * @param lk (Output) Pointer to location to return the symbol
 * table's leaf node 1/2 size.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
%apply unsigned* OUTPUT {unsigned* ik}
%apply unsigned* OUTPUT {unsigned* lk}
herr_t H5Pget_sym_k(hid_t plist_id, unsigned *ik/*out*/, unsigned *lk/*out*/);
%clear unsigned* ik;
%clear unsigned* lk;

/**
 * Gets user-defined data type conversion callback function.
 *
 * <p>H5Pget_type_conv_cb gets the user-defined data type conversion
 * callback function func in the dataset transfer property list
 * plist.</p>
 *
 * <p>The parameter op_data is a pointer to user-defined input data
 * for the callback function.</p>
 *
 * <p>The callback function func defines the actions an application is
 * to take when there is an exception during data type conversion.</p>
 *
 * <p>Please refer to the function H5Pset_type_conv_cb for more
 * details. </p>
 *
 * @param plist Dataset transfer property list identifier.
 * @param func (Output) User-defined type conversion callback function.
 * @param op_data (Output) User-defined input data for the callback function.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
 //herr_t H5Pget_type_conv_cb(hid_t dxpl_id, H5T_conv_except_func_t *op, 
 //			   void** operate_data);

/**
 * Retrieves the size of a user block.
 *
 * <p>H5Pget_userblock retrieves the size of a user block in a file
 * creation property list.</p>
 *
 * @param plist Identifier for property list to query.
 * @param size (Output) Pointer to location to return user-block size.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate 
 */
%apply long long* OUTPUT {hsize_t* size}
herr_t H5Pget_userblock(hid_t plist_id, hsize_t *size);
%clear hsize_t* size;

/**
 * Retrieves the version information of various objects for a file
 * creation property list.
 *
 * <p>H5Pget_version retrieves the version information of various
 * objects for a file creation property list. Any pointer parameters
 * which are passed as NULL are not queried.</p>
 *
 * @param plist Identifier of the file creation property list.
 * @param super (Output) Pointer to location to return super block
 * version number.
 * @param freelist (Output) Pointer to location to return global
 * freelist version number.
 * @param stab (Output) Pointer to location to return symbol table
 * version number.
 * @param shhdr (Output) Pointer to location to return shared object
 * header version number.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
%apply unsigned* OUTPUT {unsigned* super}
%apply unsigned* OUTPUT {unsigned* freelist}
%apply unsigned* OUTPUT {unsigned* stab}
%apply unsigned* OUTPUT {unsigned* shhdr}
herr_t H5Pget_version(hid_t plist_id, unsigned *super/*out*/,
		      unsigned *freelist/*out*/, unsigned *stab/*out*/,
		      unsigned *shhdr/*out*/);
%clear unsigned* super;
%clear unsigned* freelist;
%clear unsigned* stab;
%clear unsigned* shhdr;

/*nodoc*
 * Gets the memory manager for variable-length datatype allocation in
 * H5Dread and H5Dvlen_reclaim.
 *
 * <p>H5Pget_vlen_mem_manager is the companion function to
 * H5Pset_vlen_mem_manager, returning the parameters set by that
 * function.</p>
 *
 * @param plist Identifier for the dataset transfer property list.
 * @param alloc (Output) User's allocate routine, or NULL for system malloc.
 * @param alloc_info (Output) Extra parameter for user's allocation
 * routine. Contents are ignored if preceding parameter is NULL.
 * @param free (Output) User's free routine, or NULL for system free.
 * @param free_info (Output) Extra parameter for user's free
 * routine. Contents are ignored if preceding parameter is NULL.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
/* herr_t H5Pget_vlen_mem_manager(hid_t plist_id, */
/* 			       H5MM_allocate_t *alloc_func, */
/* 			       void **alloc_info, */
/* 			       H5MM_free_t *free_func, */
/* 			       void **free_info); */

/*nodoc*
 * Registers a temporary property with a property list.
 *
 * <p>H5Pinsert1 create a new property in a property list. The
 * property will exist only in this property list and copies made from
 * it.</p>
 *
 * <p>The initial property value must be provided in value and the
 * property value will be set accordingly.</p>
 *
 * <p>The name of the property must not already exist in this list, or
 * this routine will fail.</p>
 *
 * <p>The set and get callback routines may be set to NULL if they are
 * not needed.</p>
 *
 * <p>Zero-sized properties are allowed and do not store any data in
 * the property list. The default value of a zero-size property may be
 * set to NULL. They may be used to indicate the presence or absence
 * of a particular piece of information.</p>
 *
 * <p>The set routine is called before a new value is copied into the
 * property. The set routine may modify the value pointer to be set and those
 * changes will be used when setting the property's value. If the set
 * routine returns a negative value, the new property value is not
 * copied into the property and the set routine returns an error
 * value. The set routine will be called for the initial value.</p>
 *
 * <p>Note:<br/> The set callback function may be useful to range check the
 * value being set for the property or may perform some transformation
 * or translation of the value set. The get callback would then
 * reverse the transformation or translation. A single get or set
 * callback could handle multiple properties by performing different
 * actions based on the property name or other properties in the
 * property list.</p>
 *
 * <p>The get routine is called when a value is retrieved from a
 * property value. The get routine may modify the value to be returned
 * from the query and those changes will be preserved. If the get
 * routine returns a negative value, the query routine returns an
 * error value. </p>
 *
 * <p>The delete routine is called when a property is being deleted
 * from a property list. The delete routine may modify the value
 * passed in, but the value is not used by the library when the delete
 * routine returns. If the delete routine returns a negative value,
 * the property list delete routine returns an error value but the
 * property is still deleted.</p>
 *
 * <p>The copy routine is called when a new property list with this
 * property is being created through a copy operation. The copy
 * routine may modify the value to be set and those changes will be
 * stored as the new value of the property. If the copy routine
 * returns a negative value, the new property value is not copied into
 * the property and the copy routine returns an error value.</p>
 *
 * <p>The close routine is called when a property list with this
 * property is being closed. The close routine may modify the value
 * passed in, the value is not used by the library when the close
 * routine returns. If the close routine returns a negative value, the
 * property list close routine returns an error value but the property
 * list is still closed.</p>
 *
 * <p>Note:<br/> There is no create callback routine for temporary property
 * list objects; the initial value is assumed to have any necessary
 * setup already performed on it. </p>
 *
 * @param plid Property list identifier to create temporary property
 * within.
 * @param name Name of property to create.
 * @param size Size of property in bytes.
 * @param value Initial value for the property.
 * @param set Callback routine called before a new value is copied
 * into the property's value.
 * @param get Callback routine called when a property value is
 * retrieved from the property.
 * @param delete Callback routine called when a property is deleted
 * from a property list.
 * @param copy Callback routine called when a property is copied from
 * an existing property list.
 * @param close Callback routine called when a property list is being
 * closed and the property value will be disposed of.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 *
 * @deprecated
 */
//%javamethodmodifiers H5Pinsert1 "@Deprecated\n   public"
//herr_t H5Pinsert1(hid_t plist_id, const char *name, size_t size,
//    void *value, H5P_prp_set_func_t prp_set, H5P_prp_get_func_t prp_get,
//    H5P_prp_delete_func_t prp_delete, H5P_prp_copy_func_t prp_copy,
//    H5P_prp_close_func_t prp_close);

/*nodoc*
 * Registers a temporary property with a property list.
 *
 * <p>H5Pinsert2 create a new property in a property list. The
 * property will exist only in this property list and copies made from
 * it.</p>
 *
 * <p>The initial property value must be provided in value and the
 * property value will be set accordingly.</p>
 *
 * <p>The name of the property must not already exist in this list, or
 * this routine will fail.</p>
 *
 * <p>The set and get callback routines may be set to NULL if they are
 * not needed.</p>
 *
 * <p>Zero-sized properties are allowed and do not store any data in
 * the property list. The default value of a zero-size property may be
 * set to NULL. They may be used to indicate the presence or absence
 * of a particular piece of information.</p>
 *
 * <p>The set routine is called before a new value is copied into the
 * property. The set routine may modify the value pointer to be set
 * and those changes will be used when setting the property's
 * value. If the set routine returns a negative value, the new
 * property value is not copied into the property and the set routine
 * returns an error value. The set routine will be called for the
 * initial value.</p>
 *
 * <p>Note:<br/> The set callback function may be useful to range check the
 * value being set for the property or may perform some transformation
 * or translation of the value set. The get callback would then
 * reverse the transformation or translation. A single get or set
 * callback could handle multiple properties by performing different
 * actions based on the property name or other properties in the
 * property list.</p>
 *
 * <p>The get routine is called when a value is retrieved from a
 * property value.The get routine may modify the value to be returned
 * from the query and those changes will be preserved. If the get
 * routine returns a negative value, the query routine returns an
 * error value.</p>
 *
 * <p>The delete routine is called when a property is being deleted
 * from a property list.The delete routine may modify the value passed
 * in, but the value is not used by the library when the delete
 * routine returns. If the delete routine returns a negative value,
 * the property list delete routine returns an error value but the
 * property is still deleted.</p>
 *
 * <p>The copy routine is called when a new property list with this
 * property is being created through a copy operation. The copy
 * routine may modify the value to be set and those changes will be
 * stored as the new value of the property. If the copy routine
 * returns a negative value, the new property value is not copied into
 * the property and the copy routine returns an error value.</p>
 *
 * <p>The compare routine is called when a property list with this
 * property is compared to another property list with the same
 * property. The compare routine may not modify the values. The
 * compare routine should return a positive value if value1 is greater
 * than value2, a negative value if value2 is greater than value1 and
 * zero if value1 and value2 are equal.</p>
 *
 * <p>The close routine is called when a property list with this
 * property is being closed. The close routine may modify the value
 * passed in, the value is not used by the library when the close
 * routine returns. If the close routine returns a negative value, the
 * property list close routine returns an error value but the property
 * list is still closed.</p>
 *
 * <p>Note:<br/> There is no create callback routine for temporary
 * property list objects; the initial value is assumed to have any
 * necessary setup already performed on it. </p>
 *
 * @param plid Property list identifier to create temporary property within.
 * @param name Name of property to create.
 * @param size Size of property in bytes.
 * @param value Initial value for the property.
 * @param set Callback routine called before a new value is copied
 * into the property's value.
 * @param get Callback routine called when a property value is
 * retrieved from the property.
 * @param delete Callback routine called when a property is deleted
 * from a property list.
 * @param copy Callback routine called when a property is copied from
 * an existing property list.
 * @param compare Callback routine called when a property is compared
 * with another property list.
 * @param close Callback routine called when a property list is being
 * closed and the property value will be disposed of.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
//herr_t H5Pinsert2(hid_t plist_id, const char *name, size_t size,
//    void *value, H5P_prp_set_func_t prp_set, H5P_prp_get_func_t prp_get,
//    H5P_prp_delete_func_t prp_delete, H5P_prp_copy_func_t prp_copy,
//    H5P_prp_compare_func_t prp_cmp, H5P_prp_close_func_t prp_close);


/**
 * Determines whether a property list is a member of a class.
 *
 * <p>H5Pisa_class checks to determine whether a property list is a
 * member of the specified class.</p>
 *
 * @param plist_id Identifier of the property list.
 * @param pclass_id Identifier of the property class.
 *
 * @return Success: TRUE (positive) if equal; FALSE (zero) if
 * unequal. Failure: a negative value
 *
 * @ingroup plPList
 */
htri_t H5Pisa_class(hid_t plist_id, hid_t pclass_id);


/*nodoc*
 * Iterates over properties in a property class or list.
 *
 * <p>H5Piterate iterates over the properties in the property object
 * specified in id, which may be either a property list or a property
 * class, performing a specified operation on each property in
 * turn.</p>
 *
 * <p>For each property in the object, iter_func and the additional
 * information specified below are passed to the H5P_iterate_t
 * operator function.</p>
 *
 * <p>The iteration begins with the idx-th property in the object; the
 * next element to be processed by the operator is returned in idx. If
 * idx is NULL, the iterator starts at the first property; since no
 * stopping point is returned in this case, the iterator cannot be
 * restarted if one of the calls to its operator returns
 * non-zero. </p>
 *
 * <p>The operation receives the property list or class identifier for
 * the object being iterated over, id, the name of the current
 * property within the object, name, and the pointer to the operator
 * data passed in to H5Piterate, iter_data.</p>
 *
 * <p>The valid return values from an operator are as follows:</p>
 <dl>
  <dt>Zero</dt>
  <dd>Causes the iterator to continue, returning zero when all
  properties have been processed</dd>
  <dt>Positive</dt>
  <dd>Causes the iterator to immediately return that positive value,
  indicating short-circuit success. The iterator can be restarted at
  the index of the next property</dd>
  <dt>Negative</dt>
  <dd>Causes the iterator to immediately return that value, indicating
  failure. The iterator can be restarted at the index of the next
  property</dd>
 </dl>
 *
 * <p>H5Piterate assumes that the properties in the object identified
 * by id remain unchanged through the iteration. If the membership
 * changes during the iteration, the function's behavior is
 * undefined. </p>
 *
 * @param id Identifier of property object to iterate over.
 * @param idx (Input/Output) Index of the property to begin with.
 * @param iter_func Function pointer to function to be called with
 * each property iterated over.
 * @param iter_data (Input/Output) Pointer to iteration data from user.
 *
 * @return Returns the return value of the last call to iter_func if
 * it was non-zero; zero if all properties have been processed or a
 * negative value on failure.
 *
 * @ingroup plPList
 */
//int H5Piterate(hid_t id, int *idx, H5P_iterate_t iter_func, void *iter_data);


/**
 * Modifies a filter in the filter pipeline.
 *
 * <p>H5Pmodify_filter modifies the specified filter_id in the filter
 * pipeline. plist must be a dataset creation property list and the
 * modified filter will be in a permanent filter pipeline.</p>
 *
 * <p>The filter_id, flags cd_nelmts[], and cd_values parameters are
 * used in the same manner and accept the same values as described in
 * the discussion of H5Pset_filter. </p>
 *
 * <p>Note:<br/> This function currently supports only the permanent
 * filter pipeline; plist_id must be a dataset creation property
 * list.</p>
 *
 * @param plist_id Property list identifier.
 * @param filter_id Filter to be modified.
 * @param flags Bit vector specifying certain general properties of
 * the filter.
 * @param cd_nelmts Number of elements in cd_values.
 * @param cd_values Auxiliary data for the filter.
 * 
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply unsigned int INPUT[] {const unsigned int cd_values[]}
herr_t H5Pmodify_filter(hid_t plist_id, H5Z_filter_t filter,
        unsigned int flags, size_t cd_nelmts,
        const unsigned int cd_values[/*cd_nelmts*/]);
%clear const unsigned int cd_values[];


/*nodoc*
 * Registers a permanent property with a property list class.
 *
 * <p>H5Pregister1 registers a new property with a property list
 * class. The property will exist in all property list objects of
 * class created after this routine finishes. The name of the property
 * must not already exist, or this routine will fail. The default
 * property value must be provided and all new property lists created
 * with this property will have the property value set to the default
 * value. Any of the callback routines may be set to NULL if they are
 * not needed.</p>
 *
 * <p>Zero-sized properties are allowed and do not store any data in
 * the property list. These may be used as flags to indicate the
 * presence or absence of a particular piece of information. The
 * default pointer for a zero-sized property may be set to NULL. The
 * property create and close callbacks are called for zero-sized
 * properties, but the set and get callbacks are never called.</p>
 *
 * <p>The create routine is called when a new property list with this
 * property is being created. The create routine may modify the value
 * to be set and those changes will be stored as the initial value of
 * the property. If the create routine returns a negative value, the
 * new property value is not copied into the property and the create
 * routine returns an error value.</p>
 *
 * <p>The set routine is called before a new value is copied into the
 * property. The set routine may modify the value pointer to be set
 * and those changes will be used when setting the property's
 * value. If the set routine returns a negative value, the new
 * property value is not copied into the property and the set routine
 * returns an error value. The set routine will not be called for the
 * initial value, only the create routine will be called.</p>
 *
 * <p>Note:<br/> The set callback function may be useful to range
 * check the value being set for the property or may perform some
 * transformation or translation of the value set. The get callback
 * would then reverse the transformation or translation. A single get
 * or set callback could handle multiple properties by performing
 * different actions based on the property name or other properties in
 * the property list.</p>
 *
 * <p>The get routine is called when a value is retrieved from a
 * property value. The get routine may modify the value to be returned
 * from the query and those changes will be returned to the calling
 * routine. If the set routine returns a negative value, the query
 * routine returns an error value.</p>
 *
 * <p>The delete routine is called when a property is being deleted
 * from a property list. The delete routine may modify the value
 * passed in, but the value is not used by the library when the delete
 * routine returns. If the delete routine returns a negative value,
 * the property list delete routine returns an error value but the
 * property is still deleted.</p>
 *
 * <p>The copy routine is called when a new property list with this
 * property is being created through a copy operation. The copy
 * routine may modify the value to be set and those changes will be
 * stored as the new value of the property. If the copy routine
 * returns a negative value, the new property value is not copied into
 * the property and the copy routine returns an error value.</p>
 *
 * <p>The close routine is called when a property list with this
 * property is being closed. The close routine may modify the value
 * passed in, but the value is not used by the library when the close
 * routine returns. If the close routine returns a negative value, the
 * property list close routine returns an error value but the property
 * list is still closed.</p>
 *
 * @param class Property list class to register permanent property
 * within.
 * @param name Name of property to register.
 * @param size Size of property in bytes.
 * @param default Default value for property in newly created property
 * lists.
 * @param prp_create Callback routine called when a property list is being
 * created and the property value will be initialized.
 * @param prp_set Callback routine called before a new value is copied
 * into the property's value.
 * @param prp_get Callback routine called when a property value is
 * retrieved from the property.
 * @param prp_delete Callback routine called when a property is deleted
 * from a property list.
 * @param prp_copy Callback routine called when a property is copied from
 * a property list.
 * @param prp_close Callback routine called when a property list is being
 * closed and the property value will be disposed of.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
//%javamethodmodifiers H5Pregister1 "@Deprecated\n   public"
//herr_t H5Pregister1(hid_t cls_id, const char *name, size_t size,
//    void *def_value, H5P_prp_create_func_t prp_create,
//    H5P_prp_set_func_t prp_set, H5P_prp_get_func_t prp_get,
//    H5P_prp_delete_func_t prp_del, H5P_prp_copy_func_t prp_copy,
//    H5P_prp_close_func_t prp_close);

/*nodoc*
 * Registers a permanent property with a property list class.
 *
 * <p>H5Pregister2 registers a new property with a property list
 * class. The property will exist in all property list objects of
 * class created after this routine finishes. The name of the property
 * must not already exist, or this routine will fail. The default
 * property value must be provided and all new property lists created
 * with this property will have the property value set to the default
 * value. Any of the callback routines may be set to NULL if they are
 * not needed.</p>
 *
 * <p>Zero-sized properties are allowed and do not store any data in
 * the property list. These may be used as flags to indicate the
 * presence or absence of a particular piece of information. The
 * default pointer for a zero-sized property may be set to NULL. The
 * property create and close callbacks are called for zero-sized
 * properties, but the set and get callbacks are never called.</p>
 *
 * <p>The create routine is called when a new property list with this
 * property is being created. The create routine may modify the value
 * to be set and those changes will be stored as the initial value of
 * the property. If the create routine returns a negative value, the
 * new property value is not copied into the property and the create
 * routine returns an error value.</p>
 *
 * <p>The set routine is called before a new value is copied into the
 * property. The set routine may modify the value pointer to be set
 * and those changes will be used when setting the property's
 * value. If the set routine returns a negative value, the new
 * property value is not copied into the property and the set routine
 * returns an error value. The set routine will not be called for the
 * initial value, only the create routine will be called.</p>
 *
 * <p>Note:<br/> The set callback function may be useful to range check the
 * value being set for the property or may perform some transformation
 * or translation of the value set. The get callback would then
 * reverse the transformation or translation. A single get or set
 * callback could handle multiple properties by performing different
 * actions based on the property name or other properties in the
 * property list.</p>
 *
 * <p>The get routine is called when a value is retrieved from a
 * property value. The get routine may modify the value to be returned
 * from the query and those changes will be returned to the calling
 * routine. If the set routine returns a negative value, the query
 * routine returns an error value.</p>
 *
 * <p>The delete routine is called when a property is being deleted
 * from a property list. The delete routine may modify the value
 * passed in, but the value is not used by the library when the delete
 * routine returns. If the delete routine returns a negative value,
 * the property list delete routine returns an error value but the
 * property is still deleted.</p>
 *
 * <p>The copy routine is called when a new property list with this
 * property is being created through a copy operation.The copy routine
 * may modify the value to be set and those changes will be stored as
 * the new value of the property. If the copy routine returns a
 * negative value, the new property value is not copied into the
 * property and the copy routine returns an error value.</p>
 *
 * <p>The compare routine is called when a property list with this
 * property is compared to another property list with the same
 * property. The compare routine may not modify the values. The
 * compare routine should return a positive value if value1 is greater
 * than value2, a negative value if value2 is greater than value1 and
 * zero if value1 and value2 are equal.</p>
 *
 * <p>The close routine is called when a property list with this
 * property is being closed. The close routine may modify the value
 * passed in, but the value is not used by the library when the close
 * routine returns. If the close routine returns a negative value, the
 * property list close routine returns an error value but the property
 * list is still closed.</p>
 *
 * @param class Property list class to register permanent property
 * within.
 * @param name Name of property to register.
 * @param size Size of property in bytes.
 * @param default Default value for property in newly created property
 * lists.
 * @param prp_create Callback routine called when a property list is being
 * created and the property value will be initialized.
 * @param prp_set Callback routine called before a new value is copied
 * into the property's value.
 * @param prp_get Callback routine called when a property value is
 * retrieved from the property.
 * @param prp_delete Callback routine called when a property is
 * deleted from a property list.
 * @param copy Callback routine called when a property is copied from
 * a property list.
 * @param prp_compare Callback routine called when a property is
 * compared with another property list.
 * @param prp_close Callback routine called when a property list is
 * being closed and the property value will be disposed of.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
//herr_t H5Pregister2(hid_t cls_id, const char *name, size_t size,
//    void *def_value, H5P_prp_create_func_t prp_create,
//    H5P_prp_set_func_t prp_set, H5P_prp_get_func_t prp_get,
//    H5P_prp_delete_func_t prp_del, H5P_prp_copy_func_t prp_copy,
//    H5P_prp_compare_func_t prp_cmp, H5P_prp_close_func_t prp_close);


/**
 * Removes a property from a property list.
 *
 * <p>H5Premove removes a property from a property list.</p>
 *
 * <p>Both properties which were in existence when the property list
 * was created (i.e. properties registered with H5Pregister) and
 * properties added to the list after it was created (i.e. added with
 * H5Pinsert1) may be removed from a property list. Properties do not
 * need to be removed from a property list before the list itself is
 * closed; they will be released automatically when H5Pclose is
 * called.</p>
 *
 * <p>If a close callback exists for the removed property, it will be
 * called before the property is released. </p>
 *
 * @param plist_id Identifier of the property list to modify.
 * @param name Name of property to remove.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
herr_t H5Premove(hid_t plist_id, const char *name);


/**
 * Delete one or more filters in the filter pipeline.
 *
 * <p>H5Premove_filter removes the specified filter from the filter
 * pipeline in the dataset creation property list plist.</p>
 *
 * <p>The filter parameter specifies the filter to be removed. Valid
 * values for use in filter are as follows:</p>
 <dl>
    <dt>H5Z_FILTER_ALL</dt>
    <dd>Removes all filters from the permanent filter pipeline.</dd>    
    <dt>H5Z_FILTER_DEFLATE</dt>
    <dd>Data compression filter, employing the gzip algorithm.</dd>
    <dt>H5Z_FILTER_SHUFFLE</dt>
    <dd>Data shuffling filter.</dd>
    <dt>H5Z_FILTER_FLETCHER32.</dt>
    <dd>Error detection filter, employing the Fletcher32 checksum
    algorithm.</dd>
    <dt>H5Z_FILTER_SZIP</dt>
    <dd>Data compression filter, employing the SZIP algorithm.</dd>
 </dl>
 *
 * <p>Additionally, user-defined filters can be removed with this
 * routine by passing the filter identifier with which they were
 * registered with the HDF5 Library.</p>
 *
 * <p>Attempting to remove a filter that is not in the permanent
 * filter pipeline is an error. </p>
 *
 * <p>Note:<br/> This function currently supports only the permanent filter
 * pipeline; plist must be a dataset creation property list.</p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param filter Filter to be deleted.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Premove_filter(hid_t plist_id, H5Z_filter_t filter);


/**
 * Sets a property list value.
 *
 * <p>H5Pset sets a new value for a property in a property list. If
 * there is a set callback routine registered for this property, the
 * value will be passed to that routine and any changes to the value
 * will be used when setting the property value. The information
 * pointed to by the value pointer (possibly modified by the set
 * callback) is copied into the property list value and may be changed
 * by the application making the H5Pset call without affecting the
 * property value.</p>
 *
 * <p>The property name must exist or this routine will fail.</p>
 *
 * <p>If the set callback routine returns an error, the property value
 * will not be modified.</p>
 *
 * <p>This routine may not be called for zero-sized properties and
 * will return an error in that case. </p>
 *
 * @param plist_id Property list identifier to modify.
 * @param name Name of property to modify.
 * @param value Pointer to value to set the property to.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
%apply void* BUFF {void* value}
herr_t H5Pset(hid_t plist_id, const char *name, void *value);
%clear void* value;

/**
 * Sets alignment properties of a file access property list.
 *
 * <p>H5Pset_alignment sets the alignment properties of a file access
 * property list so that any file object greater than or equal in size
 * to threshold bytes will be aligned on an address which is a
 * multiple of alignment. The addresses are relative to the end of the
 * user block; the alignment is calculated by subtracting the user
 * block size from the absolute file address and then adjusting the
 * address to be a multiple of alignment.</p>
 *
 * <p>Default values for threshold and alignment are one, implying no
 * alignment. Generally the default values will result in the best
 * performance for single-process access to the file. For MPI IO and
 * other parallel systems, choose an alignment which is a multiple of
 * the disk block size. </p>
 *
 * @param fapl_id Identifier for a file access property list.
 * @param threshold Threshold value. Note that setting the threshold
 * value to 0 (zero) has the effect of a special case, forcing
 * everything to be aligned.
 * @param alignment Alignment value.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply long long {hsize_t threshold}
%apply long long {hsize_t alignment}
herr_t H5Pset_alignment(hid_t fapl_id, hsize_t threshold, hsize_t alignment);
%clear hsize_t threshold;
%clear hsize_t alignment;

/**
 * Sets the timing for storage space allocation.
 *
 * <p>H5Pset_alloc_time sets up the timing for the allocation of
 * storage space for a dataset's raw data. This property is set in the
 * dataset creation property list plist_id.</p>
 *
 * <p>Timing is specified in alloc_time with one of the following
 * values:</p>
 <dl>
    <dt>H5D_ALLOC_TIME_DEFAULT</dt>
    <dd>Allocate dataset storage space at the default time. (Defaults
    differ by storage method.)</dd>
    <dt>H5D_ALLOC_TIME_EARLY</dt>
    <dd>Allocate all space when the dataset is created. (Default for
    compact datasets.)</dd>
    <dt>H5D_ALLOC_TIME_INCR</dt>
    <dd>Allocate space incrementally, as data is written to the
    dataset. (Default for chunked storage datasets.)
    <ul>
       <li>Chunked datasets:<br/>
       Storage space allocation for each chunk is deferred until data
       is written to the chunk.</li>
       <li>Contiguous datasets:<br/> Incremental storage space
       allocation for contiguous data is treated as late
       allocation.</li>
       <li>Compact datasets:<br/>
       Incremental allocation is not allowed with compact datasets;
       H5Pset_alloc_time will return an error.</li>
   </ul>
   <dt>H5D_ALLOC_TIME_LATE</dt>
   <dd>Allocate all space when data is first written to the
   dataset. (Default for contiguous datasets.)</dd>
 </dl>
 *
 * <p>Note: H5Pset_alloc_time is designed to work in concert with the
 * dataset fill value and fill value write time properties, set with
 * the functions H5Pset_fill_value and H5Pset_fill_time.</p>
 *
 * <p>See H5Dcreate for further cross-references. </p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param alloc_time When to allocate dataset storage space.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_alloc_time(hid_t plist_id, H5D_alloc_time_t alloc_time);


/**
 * Sets tracking and indexing of attribute creation order.
 *
 * <p>H5Pset_attr_creation_order sets flags specifying whether to
 * track and index attribute creation order on an object.</p>
 *
 * <p>ocpl_id is a dataset or group creation property list
 * identifier. The term ocpl, for object creation property list, is
 * used when different types of objects may be involved.</p>
 *
 * <p>crt_order_flags contains flags with the following meanings:</p>
 <dl>
    <dt>H5P_CRT_ORDER_TRACKED</dt>
    <dd>Attribute creation order is tracked but not necessarily
    indexed.</dd>
    <dt>H5P_CRT_ORDER_INDEXED</dt>
    <dd>Attribute creation order is indexed (requires
    H5P_CRT_ORDER_TRACKED).</dd>
 </dl>
 *
 * <p>Default behavior is that attribute creation order is neither
 * tracked nor indexed. </p>
 *
 * @param ocpl_id Object creation property list identifier.
 * @param crt_order_flags Flags specifying whether to track and index
 * attribute creation order.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
herr_t H5Pset_attr_creation_order(hid_t ocpl_id, unsigned crt_order_flags);

/**
 * Sets attribute storage phase change thresholds.
 *
 * <p>H5Pset_attr_phase_change sets threshold values for attribute
 * storage on an object. These thresholds determine the point at which
 * attribute storage changes from compact storage (i.e., storage in
 * the object header) to dense storage (i.e., storage in a heap and
 * indexed with a B-tree).</p>
 *
 * <p>In the general case, attributes are initially kept in compact
 * storage. When the number of attributes exceeds max_compact (default: 8),
 * attribute storage switches to dense storage. If the number of
 * attributes subsequently falls below min_dense (default: 6), the
 * attributes are returned to compact storage.</p>
 *
 * <p>If max_compact is set to 0 (zero), dense storage always
 * used.</p>
 *
 * <p>ocpl_id is a dataset or group creation property list
 * identifier. The term ocpl, for object creation property list, is
 * used when different types of objects may be involved. </p>
 *
 * @param ocpl_id Object (group or dataset) creation property list
 * identifier.
 * @param max_compact Maximum number of attributes to be stored in
 * compact storage.
 * @param min_dense Minimum number of attributes to be stored in dense
 * storage.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
herr_t H5Pset_attr_phase_change(hid_t ocpl_id, unsigned max_compact, 
				unsigned min_dense);

/**
 * Sets B-tree split ratios for a dataset transfer property list.
 *
 * <p>H5Pset_btree_ratios sets the B-tree split ratios for a dataset
 * transfer property list. The split ratios determine what percent of
 * children go in the first node when a node splits.</p>
 *
 * <p>The ratio left is used when the splitting node is the left-most
 * node at its level in the tree; the ratio right is used when the
 * splitting node is the right-most node at its level; and the ratio
 * middle is used for all other cases.</p>
 *
 * <p>A node which is the only node at its level in the tree uses the
 * ratio right when it splits.</p>
 *
 * <p>All ratios are real numbers between 0 and 1, inclusive. </p>
 *
 * @param plist_id The dataset transfer property list identifier.
 * @param left The B-tree split ratio for left-most nodes.
 * @param middle The B-tree split ratio for all other nodes.
 * @param right The B-tree split ratio for right-most nodes and lone nodes.
 * 
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
herr_t H5Pset_btree_ratios(hid_t plist_id, double left, 
                           double middle, double right);

/**
 * Sets type conversion and background buffers.
 *
 * <p>Given a dataset transfer property list, H5Pset_buffer sets the
 * maximum size for the type conversion buffer and background buffer
 * and optionally supplies pointers to application-allocated
 * buffers. If the buffer size is smaller than the entire amount of
 * data being transferred between the application and the file, and a
 * type conversion buffer or background buffer is required, then strip
 * mining will be used.</p>
 *
 * <p>Note that there are minimum size requirements for the
 * buffer. Strip mining can only break the data up along the first
 * dimension, so the buffer must be large enough to accommodate a
 * complete slice that encompasses all of the remaining
 * dimensions. For example, when strip mining a 100x200x300 hyperslab
 * of a simple data space, the buffer must be large enough to hold
 * 1x200x300 data elements. When strip mining a 100x200x300x150
 * hyperslab of a simple data space, the buffer must be large enough
 * to hold 1x200x300x150 data elements.</p>
 *
 * <p>If tconv and/or bkg are null pointers, then buffers will be
 * allocated and freed during the data transfer.</p>
 *
 * <p>The default value for the maximum buffer is 1 Mb. </p>
 *
 * @param plist_id Identifier for the dataset transfer property list.
 * @param size Size, in bytes, of the type conversion and background
 * buffers.
 * @param tconv Pointer to application-allocated type conversion buffer.
 * @param bkg Pointer to application-allocated background buffer.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
%apply void* BUFF {void* tconv}
%apply void* BUFF {void* bkg}
herr_t H5Pset_buffer(hid_t plist_id, size_t size, void *tconv, void *bkg);


/**
 * Sets the meta data cache and raw data chunk cache parameters.
 *
 * <p>H5Pset_cache sets the number of elements (objects) in the meta
 * data cache and the number of elements, the total number of bytes,
 * and the preemption policy value in the raw data chunk cache.</p>
 *
 * <p>The plist_id is a file access property list. The number of
 * elements (objects) in the meta data cache and the raw data chunk
 * cache are mdc_nelmts and rdcc_nelmts, respectively. The total size
 * of the raw data chunk cache and the preemption policy are
 * rdcc_nbytes and rdcc_w0.</p>
 *
 * <p>Any (or all) of the H5Pget_cache pointer arguments may be null
 * pointers.</p>
 *
 * <p>The rdcc_w0 value should be between 0 and 1 inclusive and
 * indicates how much chunks that have been fully read are favored for
 * preemption. A value of zero means fully read chunks are treated no
 * differently than other chunks (the preemption is strictly LRU)
 * while a value of one means fully read chunks are always preempted
 * before other chunks. </p>
 *
 * @param plist_id Identifier of the file access property list.
 * @param mdc_nelmts Number of elements (objects) in the meta data cache.
 * @param rdcc_nelmts Number of elements (objects) in the raw data
 * chunk cache.
 * @param rdcc_nbytes Total size of the raw data chunk cache, in bytes.
 * @param rdcc_w0 Preemption policy.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_cache(hid_t plist_id, int mdc_nelmts, size_t rdcc_nelmts,
		    size_t rdcc_nbytes, double rdcc_w0);

/**
 * Sets the character encoding used to encode a string.
 *
 * <p>H5Pset_char_encoding sets the character encoding used to encode
 * strings or object names that are created with the property list
 * plist_id.</p>
 *
 * <p>Valid values for encoding include the following:</p>
 <dl>
    <dt>H5T_CSET_ASCII</dt>
    <dd>US ASCII</dd>
    <dt>H5T_CSET_UTF8</dt>
    <dd>UTF-8 Unicode encoding</dd>
 </dl>
 *
 * @param plist_id Property list identifier.
 * @param encoding String encoding character set.
 *
 * @return Returns a non-negative valule if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plStrCreate
 */
herr_t H5Pset_char_encoding(hid_t plist_id, H5T_cset_t encoding);

/**
 * Sets the size of the chunks used to store a chunked layout dataset.
 *
 * <p>H5Pset_chunk sets the size of the chunks used to store a chunked
 * layout dataset. This function is only valid for dataset creation
 * property lists.</p>
 *
 * <p>The ndims parameter currently must be the same size as the rank
 * of the dataset.</p>
 *
 * <p>The values of the dim array define the size of the chunks to
 * store the dataset's raw data. The unit of measure for dim values is
 * dataset elements.</p>
 *
 * <p>As a side-effect of this function, the layout of the dataset is
 * changed to H5D_CHUNKED, if it is not already so set. (See
 * H5Pset_layout.)</p>
 *
 * <p>Note regarding fixed-size datasets:<br/> Chunk size cannot
 * exceed the size of a fixed-size dataset. For example, a dataset
 * consisting of a 5x4 fixed-size array cannot be defined with 10x10
 * chunks. </p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param ndims The number of dimensions of each chunk.
 * @param dim An array defining the size, in dataset elements, of each
 * chunk.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply long long INPUT[] {const hsize_t dim[]}
herr_t H5Pset_chunk(hid_t plist_id, int ndims, const hsize_t dim[]);
%clear const hsize_t dim[];


/**
 * Sets properties to be used when an object is copied.
 *
 * <p>H5Pset_copy_object sets properties in the object copy property
 * list ocp_plist_id that will be invoked when a new copy is made of
 * an existing object.</p>
 *
 * <p>plist_id is the object copy property list and specifies the
 * properties governing the copying of the object.</p>
 *
 * <p>Several flags, as described in the following table, are
 * available for inclusion in the object copy property list. Note that
 * only the default action for each property is currently available to
 * user applications.</p>
 <dl>
    <dt>H5O_COPY_SHALLOW_HIERARCHY_FLAG</dt>
    <dd>Copy only immediate members of a group. The default behavior,
    without this flag is to recursively copy all objects below the
    group.</dd>
    <dt>H5O_COPY_EXPAND_SOFT_LINK_FLAG</dt>
    <dd>Expand soft links into new objects. The default behavior,
    without this flag is to keep soft links as they are.</dd>
    <dt>H5O_COPY_EXPAND_EXT_LINK_FLAG</dt>
    <dd>Expand external link into new objects. The default behavior,
    without this flag is to keep external links as they are.</dd>
    <dt>H5O_COPY_EXPAND_REFERENCE_FLAG</dt>
    <dd>Copy objects that are pointed by references. The default
    behavior, without this flag is to update only the values of object
    references.</dd>
    <dt>H5O_COPY_WITHOUT_ATTR_FLAG</dt>
    <dd>Copy object without copying attributes. The default behavior,
    without this flag is to copy the object along with all its
    attributes.</dd>
 </dl>
 *
 * @param plist_id Object copy property list identifier.
 * @param cpy_operations Copy option(s) to be set.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate 
 */
herr_t H5Pset_copy_object(hid_t plist_id, unsigned cpy_operations);

/**
 * Specifies in property list whether to create missing intermediate groups.
 *
 * <p>H5Pset_create_intermediate_group specifies whether to set the
 * link creation property list lcpl_id so that calls to functions that
 * create objects in groups different from the current working group
 * will create intermediate groups that may be missing in the path of
 * a new or moved object.</p>
 *
 * <p>Functions that create objects in or move objects to a group
 * other than the current working group make use of this
 * property. H5Gcreate_anon and H5Lmove are examles of such
 * functions.</p>
 *
 * <p>If crt_intermed_group is positive, the H5G_CRT_INTMD_GROUP will
 * be added to lcpl_id (if it is not already there). Missing
 * intermediate groups will be created upon calls to functions such as
 * those listed above that use lcpl_id.</p>
 *
 * <p>If crt_intermed_group is non-positive, the H5G_CRT_INTMD_GROUP,
 * if present, will be removed from lcpl_id. Missing intermediate
 * groups will not be created upon calls to functions such as those
 * listed above that use lcpl_id. </p>
 *
 * @param lcpl_id Link creation property list identifier.
 * @param crt_intmd Flag specifying whether to create
 * intermediate groups upon the creation of an object.
 *
 * @return Returns a non-negative valule if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
herr_t H5Pset_create_intermediate_group(hid_t lcpl_id, unsigned crt_intmd);

/**
 * Sets a data transform expression.
 *
 * <p>H5Pset_data_transform sets the data transform to be used for
 * reading and writing data. This function operates on the dataset
 * transfer property lists plist_id.</p>
 *
 * <p>The expression parameter is a string containing an algebraic
 * expression, such as (5/9.0)*(x-32) or x*(x-5). When a dataset is
 * read or written with this property list, the transform expression
 * is applied with the x being replaced by the values in the
 * dataset. When reading data, the values in the file are not changed
 * and the transformed data is returned to the user.</p>
 *
 * <p>Data transforms can only be applied to integer or floating-point
 * datasets. Order of operations is obeyed and the only supported
 * operations are +, -, *, and /. Parentheses can be nested
 * arbitrarily and can be used to change precedence.</p>
 *
 * <p>When writing data back to the dataset, the transformed data is
 * written to the file and there is no way to recover the original
 * values to which the transform was applied. </p>
 *
 * @param plist_id Identifier of the property list or class.
 * @param expression Pointer to the null-terminated data transform
 * expression.
 *
 * @return Returns a non-negative valule if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
herr_t H5Pset_data_transform(hid_t plist_id, const char* expression);

/**
 * Sets compression method and compression level.
 *
 * <p>H5Pset_deflate sets the compression method for a dataset
 * creation property list to H5D_COMPRESS_DEFLATE and the compression
 * level to level, which should be a value from zero to nine,
 * inclusive.</p>
 *
 * <p>Lower compression levels are faster but result in less
 * compression.</p>
 *
 * <p>This is the same algorithm as used by the GNU gzip program. </p>
 *
 * @param dcpl_id Identifier for the dataset creation property list.
 * @param aggression Compression level.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_deflate(hid_t dcpl_id, unsigned aggression);

/*nodoc*
 * Sets a file driver.
 *
 * <p>H5Pset_driver sets the file driver, new_driver_id, for a file
 * access or data transfer property list, plist_id, and supplies an
 * optional struct containing the driver-specific properties,
 * new_driver_info.</p>
 *
 * <p>The driver properties will be copied into the property list and
 * the reference count on the driver will be incremented, allowing the
 * caller to close the driver identifier but still use the property
 * list. </p>
 *
 * @param plist_id File access or data transfer property list identifier.
 * @param driver_id Driver identifier.
 * @param driver_info Optional struct containing driver properties.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
//%apply void* BUFF {const void* driver_info}
//herr_t H5Pset_driver(hid_t plist_id, hid_t driver_id, const void *driver_info);

/**
 * Sets data transfer mode.
 *
 * <p>H5Pset_dxpl_mpio sets the data transfer property list dxpl_id to
 * use transfer mode xfer_mode. The property list can then be used to
 * control the I/O transfer mode during data I/O operations.</p>
 *
 * <p>Valid transfer modes are as follows:</p>
 <dl>
    <dt>H5FD_MPIO_INDEPENDENT</dt>
    <dd>Use independent I/O access (default). </dd>
    <dt>H5FD_MPIO_COLLECTIVE</dt>
    <dd>Use collective I/O access. </dd>
 </dl>
 *
 * @param dxpl_id Data transfer property list identifier.
 * @param xfer_mode Transfer mode.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pset_dxpl_mpio(hid_t dxpl_id, H5FD_mpio_xfer_t xfer_mode);
#endif

/**
 * Sets a flag specifying linked-chunk I/O or multi-chunk I/O.
 *
 * <p>H5Pset_dxpl_mpio_chunk_opt specifies whether I/O is to be
 * performed as linked-chunk I/O or as multi-chunk I/O. This function
 * overrides the HDF5 Library's internal algorithm for determining
 * which mechanism to use.</p>
 *
 * <p>When an application uses collective I/O with chunked storage,
 * the HDF5 Library normally uses an internal algorithm to determine
 * whether that I/O activity should be conducted as one linked-chunk
 * I/O or as multi-chunk I/O. H5Pset_dxpl_mpio_chunk_opt is provided
 * so that an application can override the library's alogorithm in
 * circumstances where the library might lack the information needed
 * to make an optimal desision.</p>
 *
 * <p>H5Pset_dxpl_mpio_chunk_opt works by setting one of the following
 * flags in the parameter opt_mode:</p>
 <dl>
    <dt>H5FD_MPIO_CHUNK_ONE_IO</dt>
    <dd>Do one link chunked I/O.</dd>
    <dt>H5FD_MPIO_CHUNK_MULTI_IO</dt>
    <dd>Do multi-chunked I/O.</dd>
 </dl>
 *
 * <p>This function works by setting a corresponding property in the
 * dataset transfer property list dxpl_id.</p>
 *
 * <p>The library perform I/O in the specified manner unless it
 * determines that the low-level MPI IO package does not support the
 * requested behavior; in such cases, the HDF5 Library will internally
 * use independent I/O.</p>
 *
 * <p>Use of this function is optional. </p>
 *
 * @param dxpl_id Data transfer property list identifier.
 * @param opt_mode Optimization flag specifying linked-chunk I/O or
 * multi-chunk I/O.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pset_dxpl_mpio_chunk_opt(hid_t dxpl_id, 
				  H5FD_mpio_chunk_opt_t opt_mode);
#endif

/**
 * Sets a numeric threshold for linked-chunk I/O.
 *
 * <p>H5Pset_dxpl_mpio_chunk_opt_num sets a numeric threshold for the
 * use of linked-chunk I/O.</p>
 *
 * <p>The library will calculate the average number of chunks selected
 * by each process when doing collective access with chunked
 * storage. If the number is greater than the threshold set in
 * num_chunk_per_proc, the library will use linked-chunk I/O;
 * otherwise, a separate I/O process will be invoked for each chunk
 * (multi-chunk I/O). </p>
 *
 * @param dxpl_id Data transfer property list identifier.
 * @param num_chunk_per_proc Numeric threshold for performing
 * linked-chunk I/O.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pset_dxpl_mpio_chunk_opt_num(hid_t dxpl_id, 
				      unsigned num_chunk_per_proc);
#endif

/**
 * Sets a ratio threshold for collective I/O.
 *
 * <p>H5Pset_dxpl_mpio_chunk_opt_ratio sets a threshold for the use of
 * collective I/O based on the ratio of processes with collective
 * access to a dataset with chunked storage. The decision whether to
 * use collective I/O is made on a per-chunk basis.</p>
 *
 * <p>The library will calculate the percentage of the total number of
 * processes, the ratio, that hold selections in each chunk. If that
 * percentage is greater than the threshold set in
 * percent_proc_per_chunk, the library will do collective I/O for this
 * chunk; otherwise, independent I/O will be done for the chunk. </p>
 *
 * @param dxpl_id Data transfer property list identifier.
 * @param percent_proc_per_chunk Percent threshold, on the number of
 * processes holding selections per chunk, for performing linked-chunk
 * I/O.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pset_dxpl_mpio_chunk_opt_ratio(hid_t dxpl_id, 
					unsigned percent_num_proc_per_chunk);
#endif

/**
 * Sets a flag governing the use of independent versus collective I/O.
 *
 * <p>H5Pset_dxpl_mpio_collective_opt enables an application to
 * specify that the HDF5 Library will use independent I/O internally
 * when the dataset transfer property list dxpl_id is set for
 * collective I/O, i.e., with H5FD_MPIO_COLLECTIVE specified. This
 * allows the application greater control over low-level I/O while
 * maintaining the collective interface at the application level.</p>
 *
 * <p>H5Pset_dxpl_mpio_collective_opt works by setting one of the
 * following flags in the parameter opt_mode:</p>
 <dl>
    <dt>H5FD_MPIO_COLLECTIVE_IO</dt>
    <dd>Use collective I/O. (Default)</dd>
    <dt>H5FD_MPIO_INDIVIDUAL_IO</dt>
    <dd>Use independent I/O.</dd>
 </dl>
 *
 * <p>This function should be used only when H5FD_MPIO_COLLECTIVE has
 * been set through H5Pset_dxpl_mpio. In such situations, normal
 * behavior would be to use low-level collective I/O functions, but
 * the library will use low-level MPI independent I/O functions when
 * H5FD_MPIO_INDIVIDUAL_IO is set.</p>
 *
 * <p>Use of this function is optional. </p>
 *
 * @param dxpl_id Data transfer property list identifier.
 * @param opt_mode Optimization flag specifying the use of independent
 * or collective I/O.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pset_dxpl_mpio_collective_opt(hid_t dxpl_id, 
				       H5FD_mpio_collective_opt_t opt_mode);
#endif

/**
 * Sets the data transfer property list for the multi-file driver.
 *
 * <p>H5Pset_dxpl_multi sets the data transfer property list dxpl_id
 * to use the multi-file driver for each memory usage type
 * memb_dxpl[].</p>
 *
 * <p>H5Pset_dxpl_multi can only be used after the member map has been
 * set with H5Pset_fapl_multi. </p>
 *
 * @param dxpl_id Data transfer property list identifier.
 * @param memb_dxpl Array of data access property lists.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pset_dxpl_multi(hid_t dxpl_id, const hid_t *memb_dxpl);
#endif

/**
 * Sets whether to enable error-detection when reading a dataset.
 *
 * <p>H5Pset_edc_check sets the dataset transfer property list plist
 * to enable or disable error detection when reading data.</p>
 *
 * <p>Whether error detection is enabled or disabled is specified in
 * the check parameter. Valid values are as follows:</p>
 <ul>
    <li>H5Z_ENABLE_EDC (default)</li>
    <li>H5Z_DISABLE_EDC</li>
 </ul>
 *
 * <p>The error detection algorithm used is the algorithm previously
 * specified in the corresponding dataset creation property list.
 * </p>
 *
 * <p>This function does not affect the use of error detection when
 * writing data.  </p>
 *
 * <p>Note:<br/> The initial error detection implementation,
 * Fletcher32 checksum, supports error detection for chunked datasets
 * only.</p>
 *
 * @param plist_id Dataset transfer property list identifier.
 * @param check Specifies whether error checking is enabled or
 * disabled for dataset read operations.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
herr_t H5Pset_edc_check(hid_t plist_id, H5Z_EDC_t check);


/**
 * Sets prefix to be applied to external link paths.
 *
 * <p>H5Pset_elink_prefix sets the prefix to be applied to the path of
 * any external links traversed. The prefix is prepended to the
 * filename stored in the external link.</p>
 *
 * <p>The prefix is specified in the user-allocated buffer prefix and
 * set in the link access property list lapl_id. The buffer should not
 * be freed until the property list has been closed.</p>
 *
 * @param lapl_id Link access property list identifier.
 * @param prefix Prefix to be applied to external link paths.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plLnkAccess
 */
herr_t H5Pset_elink_prefix(hid_t lapl_id, const char *prefix);

/**
 * Sets estimated number of links and length of link names in a group.
 *
 * <p>H5Pset_est_link_info inserts two settings into the group
 * creation property list gcpl_id: the estimated number of links that
 * are expected to be inserted into a group created with the property
 * list and the estimated average length of those link names.</p>
 *
 * <p>The estimated number of links is passed in est_num_entries.</p>
 *
 * <p>The estimated average length of the anticipated link names is
 * passed in est_name_len.</p>
 *
 * <p>The values for these two settings are multiplied to compute the
 * initial local heap size (for old-style groups, if the local heap
 * size hint is not set) or the initial object header size for
 * (new-style compact groups; see "Group styles in
 * HDF5"). Accurately setting these parameters will help reduce
 * wasted file space.</p>
 *
 * <p>If a group is expected to have many links and to be stored in
 * dense format, set est_num_entries to 0 (zero) for maximum
 * efficiency. This will prevent the group from being created in the
 * compact format.</p>
 *
 * <p>See "Group styles in HDF5" in the H5G API introduction for a
 * discussion of the available types of HDF5 group structures. </p>
 *
 * @param gcpl_id Group creation property list identifier.
 * @param est_num_entries Estimated number of links to be inserted
 * into group.
 * @param est_name_len Estimated average length of link names.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGrpCreate
 */
herr_t H5Pset_est_link_info(hid_t gcpl_id, unsigned est_num_entries, 
			    unsigned est_name_len);

/**
 * Adds an external file to the list of external files.
 *
 * <p>The first call to H5Pset_external sets the external storage
 * property in the property list, thus designating that the dataset
 * will be stored in one or more non-HDF5 file(s) external to the HDF5
 * file. This call also adds the file name as the first file in the
 * list of external files. Subsequent calls to the function add the
 * named file as the next file in the list.</p>
 *
 * <p>If a dataset is split across multiple files, then the files
 * should be defined in order. The total size of the dataset is the
 * sum of the size arguments for all the external files. If the total
 * size is larger than the size of a dataset then the dataset can be
 * extended (provided the data space also allows the extending).</p>
 *
 * <p>The size argument specifies the number of bytes reserved for
 * data in the external file. If size is set to H5F_UNLIMITED, the
 * external file can be of unlimited size and no more files can be
 * added to the external files list.</p>
 *
 * <p>All of the external files for a given dataset must be specified
 * with H5Pset_external before H5Dcreate is called to create the
 * dataset. If one these files does not exist on the system when
 * H5Dwrite is called to write data to it, the library will create the
 * file. </p>
 *
 * @param plist_id Identifier of a dataset creation property list.
 * @param name Name of an external file.
 * @param offset Offset, in bytes, from the beginning of the file to
 * the location in the file where the data starts.
 * @param size Number of bytes reserved in the file for the data.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply long long {hsize_t size}
herr_t H5Pset_external(hid_t plist_id, const char *name, 
		       off_t offset, hsize_t size);
%clear hsize_t size;

/**
 * Sets offset property for low-level access to a file in a family of files.
 *
 * <p>H5Pset_family_offset sets the offset property in the file access
 * property list fapl_id so that the user application can retrieve a
 * file handle for low-level access to a particular member of a family
 * of files. The file handle is retrieved with a separate call to
 * H5Fget_vfd_handle (or, in special circumstances, to
 * H5FDget_vfd_handle; see Virtual File Layer and List of VFL
 * Functions in HDF5 Technical Notes).</p>
 *
 * <p>The value of offset is an offset in bytes from the beginning of
 * the HDF5 file, identifying a user-determined location within the
 * HDF5 file. The file handle the user application is seeking is for
 * the specific member-file in the associated family of files to which
 * this offset is mapped.</p>
 *
 * <p>Use of this function is only appropriate for an HDF5 file
 * written as a family of files with the FAMILY file driver. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param offset Offset in bytes within the HDF5 file.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply long long {hsize_t offset}
herr_t H5Pset_family_offset(hid_t fapl_id, hsize_t offset);
%clear hsize_t offset;

/**
 * Modifies the file access property list to use the in-memory
 * H5FD_CORE driver.
 *
 * <p>H5Pset_fapl_core modifies the file access property list to use
 * the H5FD_CORE driver.</p>
 *
 * <p>The H5FD_CORE driver enables an application to work with a file
 * in memory, speeding reads and writes as no disk access is
 * made. File contents are stored only in memory until the file is
 * closed. The backing_store parameter determines whether file
 * contents are ever written to disk.</p>
 *
 * <p>increment specifies the increment by which allocated memory is
 * to be increased each time more memory is required.</p>
 *
 * <p>While using H5Fcreate to create a core file, if the
 * backing_store is set to 1 (TRUE), the file contents are flushed to
 * a file with the same name as this core file when the file is closed
 * or access to the file is terminated in memory.</p>
 *
 * <p>The application is allowed to open an existing file with
 * H5FD_CORE driver. While using H5Fopen to open an existing file, if
 * the backing_store is set to 1 and the flags for H5Fopen is set to
 * H5F_ACC_RDWR, any change to the file contents are saved to the file
 * when the file is closed. If backing_store is set to 0 and the flags
 * for H5Fopen is set to H5F_ACC_RDWR, any change to the file contents
 * will be lost when the file is closed. If the flags for H5Fopen is
 * set to H5F_ACC_RDONLY, no change to the file is allowed either in
 * memory or on file. </p>
 *
 * <p>Note:<br/> Currently this driver cannot create or open family or
 * multi files.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param increment Size, in bytes, of memory increments.
 * @param backing_store Boolean flag indicating whether to write the
 * file contents to disk when the file is closed.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_fapl_core(hid_t fapl_id, size_t increment,
				hbool_t backing_store);

/**
 * Sets the file access property list to use the direct I/O
 * H5FD_DIRECT driver.
 *
 * <p>H5Pset_fapl_direct sets the file access property list, fapl_id,
 * to use the direct I/O driver, H5FD_DIRECT. With this driver, data
 * is written to or read from the file synchronously without being
 * cached by the system.</p>
 *
 * <p>File systems usually require the data address in memory, the
 * file address, and the size of the data to be aligned. The HDF5
 * Library's direct I/O driver is able to handle unaligned data,
 * though that will consume some additional memory resources and may
 * slow performance. To get better performance, use the system
 * function posix_memalign to align the data buffer in memory and the
 * HDF5 function H5Pset_alignment to align the data in the file. Be
 * aware, however, that aligned data I/O may cause the HDF5 file to be
 * bigger than the actual data size would otherwise require because
 * the alignment may leave some holes in the file.</p>
 *
 * <p>alignment specifies the required alignment boundary in
 * memory.</p>
 *
 * <p>block_size specifies the file system block size. A value of 0
 * (zero) means to use HDF5 Library's default value of 4KB.</p>
 *
 * <p>cbuf_size specifies the copy buffer size.</p>
 *
 * <p>Note:<br/> On an SGI Altix Linux 2.6 system, the memory
 * alignment must be a multiple of 512 bytes, and the file system
 * block size is 4KB. The maximum size for the copy buffer has to be a
 * multiple of the file system block size. The HDF5 Library's default
 * maximum copy buffer size is 16MB. This copy buffer is used by the
 * library's internal algorithm to copy data in fragments between an
 * application's unaligned buffer and the file. The buffer's size may
 * affect I/O performance.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param alignment Required memory alignment boundary.
 * @param block_size File system block size.
 * @param cbuf_size Copy buffer size.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
#ifdef H5_HAVE_DIRECT
herr_t H5Pset_fapl_direct(hid_t fapl_id, size_t alignment, 
				 size_t block_size, size_t cbuf_size);
#endif

/**
 * Sets the file access property list to use the H5FD_FAMILY family driver.
 *
 * <p>H5Pset_fapl_family sets the file access property list
 * identifier, fapl_id, to use the family driver.</p>
 *
 * <p>memb_size is the size in bytes of each file member and is used
 * only when creating a new file.</p>
 *
 * <p>memb_fapl_id is the identifier of the file access property list
 * to be used for each family member. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param memb_size Size in bytes of each file member.
 * @param memb_fapl_id Identifier of file access property list for
 * each family member.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply unsigned long {hsize_t memb_size}
herr_t H5Pset_fapl_family(hid_t fapl_id, hsize_t memb_size,
			  hid_t memb_fapl_id);
%clear hsize_t memb_size;

/**
 * Sets the file access property list to use the H5FD_LOG logging driver.
 *
 * <p>H5Pset_fapl_log modifies the file access property list to use
 * the logging driver H5FD_LOG.</p>
 *
 * <p>logfile is the name of the file in which the logging entries are
 * to be recorded.</p>
 *
 * <p>The actions to be logged are specified in the parameter flags
 * using the pre-defined constants described below. 
 * Multiple flags can be set through the use of an logical OR
 * contained in parentheses. For example, logging read and write
 * locations would be specified as
 * (H5FD_LOG_LOC_READ|H5FD_LOG_LOC_WRITE).</p>
 *
 <dl>
    <dt>H5FD_LOG_LOC_READ</dt>
    <dd>Track the location and length of every read operation.</dd>
    <dt>H5FD_LOG_LOC_WRITE</dt>
    <dd>Track the location and length of every write operation.</dd>
    <dt>H5FD_LOG_LOC_SEEK</dt>
    <dd>Track the location and length of every seek operation.</dd>
    <dt>H5FD_LOG_LOC_IO</dt>
    <dd>Track all I/O locations and lengths. The logical equivalent of
    (H5FD_LOG_LOC_READ | H5FD_LOG_LOC_WRITE | H5FD_LOG_LOC_SEEK).</dd>
 </dl>
 <dl>
    <dt>H5FD_LOG_FILE_READ</dt>
    <dd>Track the number of times each byte is read.</dd>
    <dt>H5FD_LOG_FILE_WRITE</dt>
    <dd>Track the number of times each byte is written.</dd>
    <dt>H5FD_LOG_FILE_IO</dt>
    <dd>Track the number of times each byte is read and written. The
    logical equivalent of (H5FD_LOG_FILE_READ |
    H5FD_LOG_FILE_WRITE).</dd>
 </dl>
 <dl>
    <dt>H5FD_LOG_FLAVOR</dt>
    <dd>Track the type, or flavor, of information stored at each byte.</dd>
 </dl>
 <dl>
    <dt>H5FD_LOG_NUM_READ</dt>
    <dd>Track the total number of read operations that occur.</dd>
    <dt>H5FD_LOG_NUM_WRITE</dt>
    <dd>Track the total number of write operations that occur.</dd>
    <dt>H5FD_LOG_NUM_SEEK</dt>
    <dd>Track the total number of seek operations that occur.</dd>
    <dt>H5FD_LOG_NUM_IO</dt>
    <dd>Track the total number of all types of I/O operations. The
    logical equivalent of (H5FD_LOG_NUM_READ | H5FD_LOG_NUM_WRITE |
    H5FD_LOG_NUM_SEEK).</dd>
 </dl>
 <dl>
    <dt>H5FD_LOG_TIME_OPEN</dt>
    <dd>Track the time spent in open operations. (Not implemented.)</dd>
    Not implemented in this release: open and read
    Partially implemented: write and seek
    Fully implemented: close
    <dt>H5FD_LOG_TIME_READ</dt>
    <dd>Track the time spent in read operations. (Not implemented.)</dd>
    <dt>H5FD_LOG_TIME_WRITE</dt>
    <dd>Track the time spent in write operations. (Partially implemented.)</dd>
    <dt>H5FD_LOG_TIME_SEEK</dt>
    <dd>Track the time spent in seek operations. (Not implemented.)</dd>
    <dt>H5FD_LOG_TIME_CLOSE</dt>
    <dd>Track the time spent in close operations.</dd>
    <dt>H5FD_LOG_TIME_IO</dt>
    <dd>Track the time spent in each of the above operations. The
    logical equivalent of (H5FD_LOG_TIME_OPEN | H5FD_LOG_TIME_READ |
    H5FD_LOG_TIME_WRITE | H5FD_LOG_TIME_SEEK |
    H5FD_LOG_TIME_CLOSE)</dd>
 </dl>
 <dl>
    <dt>H5FD_LOG_ALLOC</dt>
    <dd>Track the allocation of space in the file.</dd>
 </dl>
 <dl>
    <dt>H5FD_LOG_ALL</dt>
    <dd>Track everything. The logical equivalent of (H5FD_LOG_ALLOC |
    H5FD_LOG_TIME_IO | H5FD_LOG_NUM_IO | H5FD_LOG_FLAVOR
    |H5FD_LOG_FILE_IO | H5FD_LOG_LOC_IO).</dd>
 </dl>
 *
 * <p>The logging driver can track the number of times each byte in
 * the file is read from or written to (using H5FD_LOG_FILE_READ and
 * H5FD_LOG_FILE_WRITE) and what kind of data is at that location
 * (e.g., meta data, raw data; using H5FD_LOG_FLAVOR). This
 * information is tracked in a buffer of size buf_size, which must be
 * at least the size in bytes of the file to be logged.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param logfile Name of the log file.
 * @param flags Flags specifying the types of logging activity.
 * @param buf_size The size of the logging buffer.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_fapl_log(hid_t fapl_id, const char *logfile, 
		       unsigned flags, size_t buf_size);

/**
 * Stores MPI IO communicator information to the file access property
 * list.
 *
 * <p>H5Pset_fapl_mpio stores the user-supplied MPI IO parameters
 * comm, for communicator, and info, for information, in the file
 * access property list fapl_id. That property list can then be used
 * to create and/or open the file.</p>
 *
 * <p>H5Pset_fapl_mpio is available only in the parallel HDF5 library
 * and is not a collective function.</p>
 *
 * <p>comm is the MPI communicator to be used for file open as defined
 * in MPI_FILE_OPEN of MPI-2. This function does not create a
 * duplicated communicator. Modifications to comm after this function
 * call returns may have an undetermined effect on the access property
 * list. Users should not modify the communicator while it is defined
 * in a property list.</p>
 *
 * <p>info is the MPI info object to be used for file open as defined
 * in MPI_FILE_OPEN of MPI-2. This function does not create a
 * duplicated info object. Any modification to the info object after
 * this function call returns may have an undetermined effect on the
 * access property list. Users should not modify the info while it is
 * defined in a property list. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param comm MPI-2 communicator.
 * @param info MPI-2 info object.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pset_fapl_mpio(hid_t fapl_id, MPI_Comm comm, MPI_Info info);
#endif

/**
 * Stores MPI IO communicator information to a file access property list.
 *
 * <p>H5Pset_fapl_mpiposix stores the user-supplied MPI IO parameter
 * comm, for communicator, in the file access property list
 * fapl_id. That property list can then be used to create and/or open
 * the file.</p>
 *
 * <p>H5Pset_fapl_mpiposix is available only in the parallel HDF5
 * library and is not a collective function.</p>
 *
 * <p>comm is the MPI communicator to be used for file open as defined
 * in MPI_FILE_OPEN of MPI-2. This function does not create a
 * duplicated communicator. Modifications to comm after this function
 * call returns may have an undetermined effect on the access property
 * list. Users should not modify the communicator while it is defined
 * in a property list. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param comm MPI-2 communicator.
 * TODO Document use_gpfs.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
#ifdef H5_HAVE_PARALLEL
herr_t H5Pset_fapl_mpiposix(hid_t fapl_id, MPI_Comm comm, hbool_t use_gpfs);
#endif

/**
 * Sets the file access property list to use the H5FD_MULTI multi-file
 * driver.
 *
 * <p>H5Pset_fapl_multi sets the file access property list fapl_id to
 * use the multi-file driver.</p>
 *
 * <p>The multi-file driver enables different types of HDF5 data and
 * metadata to be written to separate files. These files are viewed by
 * the HDF5 library and the application as a single virtual HDF5 file
 * with a single HDF5 file address space. The types of data that can
 * be broken out into separate files include raw data, the superblock,
 * B-tree data, global heap data, local heap data, and object
 * headers. At the programmer's discretion, two or more types of data
 * can be written to the same file while other types of data are
 * written to separate files.</p>
 *
 * <p>The array memb_map maps memory usage types to other memory usage
 * types and is the mechanism that allows the caller to specify how
 * many files are created. The array contains H5FD_MEM_NTYPES entries,
 * which are either the value H5FD_MEM_DEFAULT or a memory usage
 * type. The number of unique values determines the number of files
 * that are opened.</p>
 *
 * <p>The array memb_fapl contains a property list for each memory
 * usage type that will be associated with a file.</p>
 *
 * <p>The array memb_name should be a name generator (a printf-style
 * format with a %s which will be replaced with the name passed to
 * H5FDopen, usually from H5Fcreate or H5Fopen).</p>
 *
 * <p>The array memb_addr specifies the offsets within the virtual
 * address space, from 0 (zero) to HADDR_MAX, at which each type of
 * data storage begins.</p>
 *
 * <p>If relax is set to TRUE (or 1), then opening an existing file
 * for read-only access will not fail if some file members are
 * missing. This allows a file to be accessed in a limited sense if
 * just the meta data is available.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param memb_map Maps memory usage types to other memory usage
 * types. If null, the default member map contains the value
 * H5FD_MEM_DEFAULT for each element.
 * @param memb_fapl Property list for each memory usage type. If null,
 * the default value is H5P_DEFAULT for each element.
 * @param memb_name Name generator for names of member files. If null,
 * the default string is %s-X.h5 where X is one of the following
 * letters:
 <ul>
    <li>s for H5FD_MEM_SUPER</li>
    <li>b for H5FD_MEM_BTREE</li>
    <li>r for H5FD_MEM_DRAW</li>
    <li>g for H5FD_MEM_GHEAP</li>
    <li>l for H5FD_MEM_LHEAP</li>
    <li>o for H5FD_MEM_OHDR</li>
 </ul>
 * @param memb_addr The offsets within the virtual address space, from
 * 0 (zero) to HADDR_MAX, at which each type of data storage
 * begins. If null, the default value is HADDR_UNDEF for each element.
 * @param relax Allows read-only access to incomplete file sets when TRUE.
 * 
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply int INPUT[] {const hid_t* memb_fapl}
%apply long long INPUT[] {haddr_t* memb_addr}
%apply H5FD_MEM_ENUM INPUT[] {const H5FD_mem_t *memb_map}
%apply void* HSTRARR_INPUT {const char* const *memb_name}
herr_t H5Pset_fapl_multi(hid_t fapl_id, const H5FD_mem_t *memb_map,
			 const hid_t *memb_fapl, const char* const *memb_name,
			 const haddr_t *memb_addr, hbool_t relax);
%clear const hid_t* memb_fapl;
%clear haddr_t* memb_addr;
%clear const H5FD_mem_t *memb_map;
%clear const char* const *memb_name;

/**
 * Sets the file access property list to use the H5FD_SEC2 sec2 driver.
 *
 * @param fapl_id File access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_fapl_sec2(hid_t fapl_id);

/**
 * Sets the file access property list to split metadata and data.
 *
 * <p>H5Pset_fapl_split is a compatibility function that enables the
 * multi-file driver to emulate the split driver from HDF5 Releases
 * 1.0 and 1.2. The split file driver stored metadata and raw data in
 * separate files but provided no mechanism for separating types of
 * metadata.</p>
 *
 * <p>fapl_id is a file access property list identifier.</p>
 *
 * <p>meta_ext is the filename extension for the metadata file. The
 * extension is appended to the name passed to H5FDopen, usually from
 * H5Fcreate or H5Fopen, to form the name of the metadata file. If the
 * string %s is used in the extension, it works like the name
 * generator as in H5Pset_fapl_multi.</p>
 *
 * <p>meta_plist_id is the file access property list identifier for
 * the metadata file.</p>
 *
 * <p>raw_ext is the filename extension for the raw data file. The
 * extension is appended to the name passed to H5FDopen, usually from
 * H5Fcreate or H5Fopen, to form the name of the rawdata file. If the
 * string %s is used in the extension, it works like the name
 * generator as in H5Pset_fapl_multi.</p>
 *
 * <p>raw_plist_id is the file access property list identifier for the
 * raw data file.</p>
 *
 * <p>If a user wishes to check to see whether this driver is in use,
 * the user must call H5Pget_driver and compare the returned value to
 * the string H5FD_MULTI. A positive match will confirm that the multi
 * driver is in use; HDF5 provides no mechanism to determine whether
 * it was called as the special case invoked by H5Pset_fapl_split.
 * </p>
 *
 * @param fapl_id File access property list identifier.
 * @param meta_ext Metadata filename extension.
 * @param meta_plist_id File access property list identifier for the
 * metadata file.
 * @param raw_ext Raw data filename extension.
 * @param raw_plist_id File access property list identifier for the
 * raw data file.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_fapl_split(hid_t fapl_id, const char *meta_ext,
			 hid_t meta_plist_id, const char *raw_ext,
			 hid_t raw_plist_id);

/**
 * Sets the file access property list to use the H5FD_STDIO standard
 * I/O driver.
 * 
 * @param fapl_id File access property list identifier.
 * 
 * Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Pset_fapl_stdio(hid_t fapl_id);

/**
 * Sets the file access property list to use the H5FD_WINDOWS Windows
 * I/O driver on Windows systems.
 *
 *
 * <p>Since the HDF5 Library uses this driver, H5FD_WINDOWS, by
 * default on Windows systems, it is not normally necessary for a user
 * application to call H5Pset_fapl_windows. While it is not
 * recommended, there may be times when a user chooses to set a
 * different HDF5 driver, such as the standard I/O driver (H5FD_STDIO)
 * or the sec2 driver (H5FD_SEC2), in a Windows
 * application. H5Pset_fapl_windows is provided so that the
 * application can return to the Windows I/O driver when the time
 * comes.</p>
 *
 * <p>Only the Windows driver is tested on Windows systems; other
 * drivers are used at the application's and the user's risk.</p>
 *
 * <p>Furthermore, the Windows driver is tested and available only on
 * Windows systems; it is not available on non-Windows systems. </p>
 *
 * @param fapl_id File access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
#ifdef H5_HAVE_WINDOWS
herr_t H5Pset_fapl_windows(hid_t fapl_id);
#endif

/**
 * Sets the file close degree.
 *
 * <p>H5Pset_fclose_degree sets the file close degree property
 * degree in the file access property list fapl_id. </p>
 *
 * <p>The value of degree determines how aggressively H5Fclose
 * deals with objects within a file that remain open when H5Fclose is
 * called to close that file.  fc_degree can have any one of four
 * valid values: </p>
 <dl>
    <dt>H5F_CLOSE_WEAK</dt>
    <dd>If an object in the file is open, access to the file
    identifier is terminated on H5Fclose but actual file close is delayed until
    all objects in file are closed. If no object is open in the file,
    the file itself is closed. </dd>
    <dt>H5F_CLOSE_SEMI</dt>
    <dd>If an object in the file is open, H5Fclose returns FAILURE. If
    no object is open in the file, the file itself is closed.</dd>
    <dt>H5F_CLOSE_STRONG</dt>
    <dd>If an object in the file is open, H5Fclose closes all open
    objects remaining in the file, then file iteself is closed.</dd>
    <dt>H5F_CLOSE_DEFAULT</dt>
    <dd>The VFL driver chooses the behavior for H5Fclose.  Currently,
    all VFL drivers set this value to H5F_CLOSE_WEAK, except for the
    MPI-I/O driver, which sets it to H5F_CLOSE_SEMI.</dd>
 </dl>
 *
 * @param fapl_id File access property list identifier.
 * @param degree Pointer to a location containing the file close
 * degree property, the value of fc_degree.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_fclose_degree(hid_t fapl_id, H5F_close_degree_t degree);


/**
 * Sets the time when fill values are written to a dataset.
 *
 * <p>H5Pset_fill_time sets up the timing for writing fill values to a
 * dataset. This property is set in the dataset creation property list
 * plist_id.</p>
 *
 * <p>Timing is specified in fill_time with one of the following
 * values:</p>
 <dl>
    <dt>H5D_FILL_TIME_IFSET</dt>
    <dd>Write fill values to the dataset when storage space is
    allocated only if there is a user-defined fill value, i.e., one
    set with H5Pset_fill_value.  (Default)</dd>
    <dt>H5D_FILL_TIME_ALLOC</dt>
    <dd>Write fill values to the dataset when storage space is
    allocated.</dd>
    <dt>H5D_FILL_TIME_NEVER</dt>
    <dd>Never write fill values to the dataset.</dd>    
 </dl>
 *
 * <p>Note:<br/> H5Pset_fill_time is designed for coordination with
 * the dataset fill value and dataset storage allocation time
 * properties, set with the functions H5Pset_fill_value and
 * H5Pset_alloc_time.</p>
 *
 * <p>See H5Dcreate for further cross-references. </p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param fill_time When to write fill values to a dataset.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_fill_time(hid_t plist_id, H5D_fill_time_t fill_time);


/**
 * Sets the fill value for a dataset.
 *
 * <p>H5Pset_fill_value sets the fill value for a dataset in the
 * dataset creation property list.</p>
 *
 * <p>value is interpreted as being of datatype type_id. This datatype
 * may differ from that of the dataset, but the HDF5 library must be
 * able to convert value to the dataset datatype when the dataset is
 * created.</p>
 *
 * <p>The default fill value is 0 (zero), which is interpreted
 * according to the actual dataset datatype.</p>
 *
 * <p>Setting value to NULL indicates that the fill value is to be
 * undefined. </p>
 *
 * <p>Notes:<br/> Applications sometimes write data only to portions
 * of an allocated dataset. It is often useful in such cases to fill
 * the unused space with a known fill value. This function allows the
 * user application to set that fill value; the functions H5Dfill and
 * H5Pset_fill_time, respectively, provide the ability to apply the
 * fill value on demand or to set up its automatic application.</p>
 *
 * <p>A fill value should be defined so that it is appropriate for the
 * application. While the HDF5 default fill value is 0 (zero), it is
 * often appropriate to use another value. It might be useful, for
 * example, to use a value that is known to be impossible for the
 * application to legitimately generate.</p>
 *
 * <p>H5Pset_fill_value is designed to work in concert with
 * H5Pset_alloc_time and H5Pset_fill_time. H5Pset_alloc_time and
 * H5Pset_fill_time govern the timing of dataset storage allocation
 * and fill value write operations and can be important in tuning
 * application performance.</p>
 *
 * <p>See H5Dcreate for further cross-references. </p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param type_id Datatype of value.
 * @param value Pointer to buffer containing value to use as fill
 * value.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply void* BUFF {const void* value}
herr_t H5Pset_fill_value(hid_t plist_id, hid_t type_id, const void *value);

/**
 * Adds a filter to the filter pipeline.
 *
 * <p>H5Pset_filter adds the specified filter_id and corresponding
 * properties to the end of an output filter pipeline. If plist is a
 * dataset creation property list, the filter is added to the
 * permanent filter pipeline; if plist is a dataset transfer property
 * list, the filter is added to the transient filter pipeline.</p>
 *
 * <p>The array cd_values contains cd_nelmts integers which are
 * auxiliary data for the filter. The integer values will be stored in
 * the dataset object header as part of the filter information.</p>
 *
 * <p>The flags argument is a bit vector with the following fields
 * specifying certain general properties of the filter: </p>
 <dl>
    <dt>H5Z_FLAG_OPTIONAL</dt>
    <dd><p>If this bit is set then the filter is optional. If the
    filter fails (see below) during an H5Dwrite operation then the
    filter is just excluded from the pipeline for the chunk for which
    it failed; the filter will not participate in the pipeline during
    an H5Dread of the chunk. This is commonly used for compression
    filters: if the filter result would be larger than the input, then
    the compression filter returns failure and the uncompressed data
    is stored in the file. If this bit is clear and a filter fails,
    then H5Dwrite or H5Dread also fails.</p>

    <p>This flag should not be set for the Fletcher32 checksum filter
    as it will bypass the checksum filter without reporting checksum
    errors to an application.</p></dd>
 </dl>
 *
 * <p>The filter_id parameter specifies the filter to be set. Valid
 * filter identifiers are as follows:</p>
 <dl>
    <dt>H5Z_FILTER_DEFLATE</dt>
    <dd>Data compression filter, employing the gzip algorithm</dd>
    <dt>H5Z_FILTER_SHUFFLE</dt>
    <dd>Data shuffling filter</dd>
    <dt>H5Z_FILTER_FLETCHER32</dt>
    <dd>Error detection filter, employing the Fletcher32 checksum
    algorithm</dd>
    <dt>H5Z_FILTER_SZIP</dt>
    <dd>Data compression filter, employing the SZIP algorithm</dd>
 </dl>
 *
 * <p>Also see H5Pset_edc_check and H5Pset_filter_callback. </p>
 *
 * <p>Notes:<br/> This function currently supports only the permanent
 * filter pipeline; plist must be a dataset creation property
 * list.</p>
 *
 * <p>If multiple filters are set for a property list, they will be
 * applied to each chunk in the order in which they were set. </p>
 *
 * @param plist_id Property list identifier.
 * @param filter_id Filter identifier for the filter to be added to
 * the pipeline.
 * @param flags Bit vector specifying certain general properties of
 * the filter.
 * @param cd_nelmts Number of elements in cd_values.
 * @param cd_values Auxiliary data for the filter.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
%apply unsigned int INPUT[] {const unsigned int c_values[]}
herr_t H5Pset_filter(hid_t plist_id, H5Z_filter_t filter,
		     unsigned int flags, size_t cd_nelmts,
		     const unsigned int c_values[]);
%clear const unsigned int c_values[];


/*nodoc*
 * Sets user-defined filter callback function.
 *
 * <p>H5Pset_filter_callback sets the user-defined filter callback
 * function func in the dataset transfer property list plist.</p>
 *
 * <p>The parameter op_data is a pointer to user-defined input data
 * for the callback function and will be passed through to the
 * callback function.</p>
 *
 * <p>The callback function func defines the actions an application is
 * to take when a filter fails.</p>
 *
 * @param plist_id Dataset transfer property list identifier.
 * @param func User-defined filter callback function.
 * @param op_data User-defined input data for the callback function.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
//herr_t H5Pset_filter_callback(hid_t plist_id, H5Z_filter_func_t func,
//			      void* op_data);

/**
 * Sets the dataset creation property list to use the Fletcher32
 * checksum filter.
 *
 * <p>Note:<br/> The initial error detection implementation supports
 * error detection for chunked datasets only.</p>
 *
 * @param dcpl_id Dataset creation property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_fletcher32(hid_t dcpl_id);

/**
 * Sets flag for garbage collecting orphaned references.
 *
 * <p>H5Pset_gc_references sets the flag for garbage collecting
 * references for the file.</p>
 *
 * <p>Dataset region references and other reference types use space in
 * an HDF5 file's global heap. If garbage collection is on and the
 * user passes in an uninitialized value in a reference structure, the
 * heap might get corrupted. When garbage collection is off, however,
 * and the user re-uses a reference, the previous heap block will be
 * orphaned and not returned to the free heap space.</p>
 *
 * <p>When garbage collection is on, the user must initialize the
 * reference structures to 0 or risk heap corruption.</p>
 *
 * <p>The default value for garbage collecting references is off. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param gc_ref Flag setting reference garbage collection to on (1)
 * or off (0).
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_gc_references(hid_t fapl_id, unsigned gc_ref);

/**
 * Sets number of I/O vectors to be read/written in hyperslab I/O.
 *
 * <p>H5Pset_hyper_vector_size sets the number of I/O vectors to be
 * accumulated in memory before being issued to the lower levels of
 * the HDF5 library for reading or writing the actual data.</p>
 *
 * <p>The I/O vectors are hyperslab offset and length pairs and are
 * generated during hyperslab I/O.</p>
 *
 * <p>The number of I/O vectors is passed in vector_size to be set in
 * the dataset transfer property list dxpl_id. vector_size must be
 * greater than 1 (one).</p>
 *
 * <p>H5Pset_hyper_vector_size is an I/O optimization function;
 * increasing vector_size should provide better performance, but the
 * library will use more memory during hyperslab I/O. The default
 * value of vector_size is 1024. </p>
 *
 * @param dxpl_id Dataset transfer property list identifier.
 * @param vector_size Number of I/O vectors to accumulate in memory
 * for I/O operations. Must be greater than 1 (one). The default value
 * is 1024.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
herr_t H5Pset_hyper_vector_size(hid_t dxpl_id, size_t vector_size);

/**
 * Sets the size of the parameter used to control the B-trees for
 * indexing chunked datasets.
 *
 * <p>H5Pset_istore_k sets the size of the parameter used to control
 * the B-trees for indexing chunked datasets. This function is only
 * valid for file creation property lists.</p>
 *
 * <p>ik is one half the rank of a tree that stores chunked raw
 * data. On average, such a tree will be 75% full, or have an average
 * rank of 1.5 times the value of ik. </p>
 *
 * @param plist_id File creation property list identifier.
 * @param ik 1/2 rank of chunked storage B-tree.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
herr_t H5Pset_istore_k(hid_t plist_id, unsigned ik);

/**
 * Sets the type of storage used to store the raw data for a dataset.
 *
 * <p>H5Pset_layout sets the type of storage used to store the raw
 * data for a dataset. This function is only valid for dataset
 * creation property lists.</p>
 *
 * <p>Valid values for layout are: </p>
 <dl>
    <dt>H5D_COMPACT</dt>
    <dd>Store raw data in the dataset object header in file. This
    should only be used for very small amounts of raw data. The
    current limit is approximately 64K (HDF5 Release 1.6). </dd>
    <dt>H5D_CONTIGUOUS</dt>
    <dd>Store raw data separately from the object header in one large
    chunk in the file. </dd>
    <dt>H5D_CHUNKED</dt>
    <dd>Store raw data separately from the object header as chunks of
    data in separate locations in the file.</dd>
 </dl>
 *
 * <p>Note that a compact storage layout may affect writing data to
 * the dataset with parallel applications. See note in H5Dwrite
 * documentation for details.</p>
 *
 * @param plist_id Dataset creation property list identifier.
 * @param layout Type of storage layout for raw data.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_layout(hid_t plist_id, H5D_layout_t layout);


/**
 * Sets bounds on library versions, and indirectly format versions, to
 * be used when creating objects.
 *
 * <p>H5Pset_libver_bounds controls the versions of the object formats
 * that will be used when creating objects in a file. The object
 * format versions are determined indirectly from the HDF5 Library
 * versions specified in the call.</p>
 *
 * <p>This property is set in the file access property list specified
 * by fapl_id. </p>
 *
 * <p>The lower bound on the range of possible library versions used
 * to create the object is controlled by the low parameter. Currently,
 * low must be one of two pre-defined values:</p>
 *
 * <p>Setting low to HDF_LIBVER_EARLIEST will result in objects being
 * created using the earliest possible format for each object. Note
 * that earliest possible is different from earliest, as some features
 * introduced in library versions later than 1.0.0 resulted in updates
 * to object formats. With low=HDF_LIBVER_EARLIEST, if the application
 * creates an object that requires a feature introduced in library
 * versions later than 1.0.0, the earliest possible version that
 * supports the requested feature will be used.</p>
 *
 * <p>Setting low to HDF_LIBVER_LATEST will result in objects being
 * created using the latest available format for each object. This
 * setting means that objects will be created with the latest format
 * versions available (within the range of library versions specified
 * in the call), and can take advantage of the latest features and
 * performance enhancements. Objects written with the
 * HDF_LIBVER_LATEST setting for low may be accessible to a smaller
 * range of library versions than would be the case if the
 * HDF_LIBVER_EARLIEST value had been used.</p>
 *
 * <p>The upper bound on the range of possible library versions used
 * to create the object is controlled by the high parameter.</p>
 *
 * <p>Currently, high must be set to the pre-defined value
 * HDF_LIBVER_LATEST. HDF_LIBVER_LATEST corresponds to the version of
 * the HDF5 Library in use. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param low The earliest version of the library that will be used
 * for writing objects. Must be HDF_LIBVER_EARLIEST or
 * HDF_LIBVER_LATEST.
 * @param high The latest version of the library that will be used for
 * writing objects. Must be HDF_LIBVER_LATEST.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_libver_bounds(hid_t fapl_id, H5F_libver_t low, H5F_libver_t high);


/**
 * Sets creation order tracking and indexing for links in a group.
 *
 * <p>H5Pset_link_creation_order sets flags in a group creation
 * property list, gcpl_id, for tracking and/or indexing links on
 * creation order.</p>
 *
 * <p>The following flags are passed in crt_order_flags:</p>
 <dl>
    <dt>H5P_CRT_ORDER_TRACKED</dt>
    <dd>Specifies to track creation order.</dd>
    <dt>H5P_CRT_ORDER_INDEXED</dt>
    <dd>Specifies to index links in the group on creation order.</dd>
 </dl>
 *
 * <p>If only H5P_CRT_ORDER_TRACKED is set, HDF5 will track link
 * creation order in any group created with the group creation
 * property list gcpl_id. If both H5P_CRT_ORDER_TRACKED and
 * H5P_CRT_ORDER_INDEXED are set, HDF5 will track link creation order
 * in the group and index links on that property. </p>
 *
 * @param gcpl_id Group creation property list identifier.
 * @param crt_order_flags Creation order flags.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGrpCreate
 */
herr_t H5Pset_link_creation_order(hid_t gcpl_id, unsigned crt_order_flags);

/**
 * Sets the parameters for conversion between compact and dense groups.
 *
 * <p>H5Pset_link_phase_change sets the maximum number of entries for
 * a compact group and the minimum number of links to allow before
 * converting a dense group to back to the compact format.</p>
 *
 * <p>max_compact is the maximum number of links to store as header
 * messages in the group header as before converting the group to the
 * dense format. Groups that are in compact format and in which the
 * exceed this number of links rises above this threshold are
 * automatically converted to dense format. The default threshold 
 * is eight.</p>
 *
 * <p>min_dense is the minimum number of links to store in the dense
 * format. Groups which are in dense format and in which the number of
 * links falls below this theshold are automatically converted to
 * compact format. The default threshold is six.</p>
 *
 * <p>See "Group styles in HDF5" in the H5G API introduction for a
 * discussion of the available types of HDF5 group structures. </p>
 *
 * @param gcpl_id Group creation property list identifier.
 * @param max_compact Maximum number of links for compact storage.
 * @param min_dense Minimum number of links for dense storage.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGrpCreate
 */
herr_t H5Pset_link_phase_change(hid_t gcpl_id, unsigned max_compact, 
				unsigned min_dense);
/**
 * Sets the local heap size hint for original-style groups.
 *
 * <p>H5Pset_local_heap_size_hint sets the local heap size hint,
 * size_hint, in the group creation property list, gcpl_id, for
 * original-style groups.</p>
 *
 * <p>The original style of HDF5 groups, the only style available
 * prior to HDF5 Release 1.8.0, was well-suited for moderate-sized
 * groups but was not optimized for either very small or very large
 * groups. This style remains the default, but in HDF5 Release 1.8.0,
 * two new grouping styles were introduced: compact groups to
 * accomodate zero to small numbers of members and dense groups for
 * thousands or tens of thousands of members (or millions, if that's
 * what your application requires!).</p>
 *
 * <p>The local heap size hint, size_hint, is a performance tuning
 * parameter for original-style groups. As indicated above, an HDF5
 * group may have zero, a handful, or tens of thousands of
 * members. Since the original style of HDF5 groups stored the
 * metadata for all of these group members in a uniform format in a
 * local heap, the size of that metadata (and hence, the size of the
 * local heap) could vary wildly from group to group. To intelligently
 * allocate space and to avoid unnecessary fragmentation of the local
 * heap, it can be valuable to provide the library with a hint as to
 * its likely eventual size. This can be particularly valuable when it
 * is known that a group will eventually have a great many members. It
 * can also be useful in conserving space in a file when it is known
 * that certain groups will never have any members. </p>
 *
 * @param gcpl_id Group creation property list identifier.
 * @param size_hint Hint for size of local heap.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plGrpCreate
 */
herr_t H5Pset_local_heap_size_hint(hid_t gcpl_id, size_t size_hint);

/**
 * Set the initial metadata cache configuration in the indicated File
 * Access Property List to the supplied value.
 *
 * <p>H5Pset_mdc_config attempts to set the initial metadata cache
 * configuration to the supplied value. It will fail if an invalid
 * configuration is detected. This configuration is used when the file
 * is opened.</p>
 *
 * <p>See the overview of the metadata cache in the special topics
 * section of the user manual for details on what is being
 * configured.</p>
 *
 * @param fapl_id File access property list identifier.
 * @param config_ptr Pointer to the instance of H5AC_cache_config_t
 * containing the desired configuration. 
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plMeta
 */
herr_t H5Pset_mdc_config(hid_t fapl_id, H5AC_cache_config_t* config_ptr);

/**
 * Sets the minimum metadata block size.
 *
 * <p>H5Pset_meta_block_size sets the minimum size, in bytes, of
 * metadata block allocations when H5FD_FEAT_AGGREGATE_METADATA is set
 * by a VFL driver.</p>
 *
 * <p>Each raw metadata block is initially allocated to be of the
 * given size. Specific metadata objects (e.g., object headers, local
 * heaps, B-trees) are then sub-allocated from this block.</p>
 *
 * <p>The default setting is 2048 bytes, meaning that the library will
 * attempt to aggregate metadata in at least 2K blocks in the
 * file. Setting the value to 0 (zero) with this function will turn
 * off metadata aggregation, even if the VFL driver attempts to use
 * the metadata aggregation strategy.</p>
 *
 * <p>Metadata aggregation reduces the number of small data objects in
 * the file that would otherwise be required for metadata. The
 * aggregated block of metadata is usually written in a single write
 * action and always in a contiguous block, potentially significantly
 * improving library and application performance. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param size Minimum size, in bytes, of metadata block allocations.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
%apply unsigned long {hsize_t size}
herr_t H5Pset_meta_block_size(hid_t fapl_id, hsize_t size);
%clear hsize_t size;

/**
 * Sets type of data property for MULTI driver.
 *
 * <p>H5Pset_multi_type sets the type of data property in the file
 * access property list fapl_id. This enables a user application to
 * specify the type of data the application wishes to access so that
 * the application can retrieve a file handle for low-level access to
 * the particular member of a set of MULTI files in which that type of
 * data is stored. The file handle is retrieved with a separate call
 * to H5Fget_vfd_handle (or, in special circumstances, to
 * H5FDget_vfd_handle; see Virtual File Layer and List of VFL
 * Functions in HDF5 Technical Notes).</p>
 *
 * <p>The type of data specified in type may be one of the
 * following:</p>
 <dl>
    <dt>H5FD_MEM_SUPER<dt>
    <dd>Super block data</dd>
    <dt>H5FD_MEM_BTREE</dt>
    <dd>B-tree data</dd>
    <dt>H5FD_MEM_DRAW</dt>
    <dd>Dataset raw data</dd>
    <dt>H5FD_MEM_GHEAP</dt>
    <dd>Global heap data</dd>
    <dt>H5FD_MEM_LHEAP</dt>
    <dd>Local Heap data</dd>
    <dt>H5FD_MEM_OHDR</dt>
    <dd>Object header data</dd>
 </dl>
 *
 * <p>Use of this function is appropriate only for an HDF5 file
 * written as a set of files with the MULTI file driver. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param type Type of data.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_multi_type(hid_t fapl_id, H5FD_mem_t type);

/**
 * Sets the dataset creation property list to use the H5Z_FILTER_NBIT
 * N-Bit filter.
 *
 * <p>The N-Bit filter allows datatypes that have explicit precision
 * and offset values to be packed, by removing any bits that would
 * normally be used for padding.</p>
 *
 * <p>The N-Bit filter is used effectively for compressing data of an
 * N-Bit datatype as well as a compound and an array datatype with
 * N-Bit fields. However, the datatype classes of the N-Bit datatype
 * or the N-Bit field of the compound datatype or the array datatype
 * are limited to integer or floating-point.</p>
 *
 * <p>The N-Bit filter supports complex situations where a compound
 * datatype contains member(s) of compound datatype or an array
 * datatype that has compound datatype as the base type. However, it
 * does not support the situation where an array datatype has
 * variable-length or variable-length string as its base datatype. But
 * the filter does support the situation where variable-length or
 * variable-length string is a member of a compound datatype.</p>
 *
 * <p>For all other HDF5 datatypes such as time, string, bitfield,
 * opaque, reference, enum, and variable length, the N-Bit filter
 * allows them to pass through like an no-op.</p>
 *
 * <p>Like other I/O filters supported by the HDF5 library,
 * application using the N-Bit filter must store data with chunked
 * storage.</p>
 *
 * <p>By nature, the N-Bit filter should not be used together with
 * other I/O filters. </p>
 *
 * @param plist_id Dataset creation property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_nbit(hid_t plist_id);

/**
 * Sets maximum number of soft or user-defined link traversals allowed.
 *
 * <p>H5Pset_nlinks sets the maximum number of soft or user-defined
 * link traversals allowed, nlinks, before the library assumes it has
 * found a cycle and aborts the traversal. This value is set in the
 * link access property list lapl_id.</p>
 *
 * <p>The limit on the number soft or user-defined link traversals is
 * designed to terminate link traversal if one or more links form a
 * cycle. User control is provided because some files may have
 * legitimate paths formed of large numbers of soft or user-defined
 * links. This property can be used to allow traversal of as many
 * links as desired.</p>
 *
 * @param lapl_id File access property list identifier.
 * @param nlinks Maximum number of links to traverse.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plLnkAccess
 */
herr_t H5Pset_nlinks(hid_t lapl_id, size_t nlinks);

/**
 * Sets the dataset transfer property list status to TRUE or FALSE.
 *
 * <p>H5Pset_preserve sets the dataset transfer property list status
 * to TRUE or FALSE.</p>
 *
 * <p>When reading or writing compound data types and the destination
 * is partially initialized and the read/write is intended to
 * initialize the other members, one must set this property to
 * TRUE. Otherwise the I/O pipeline treats the destination datapoints
 * as completely uninitialized. </p>
 *
 * @param dxpl_id Identifier for the dataset transfer property list.
 * @param status Status of for the dataset transfer property list
 * (TRUE/FALSE).
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
herr_t H5Pset_preserve(hid_t dxpl_id, hbool_t status);

/**
 * Sets the behavior for recording of times associated with an object.
 *
 * <p>H5Pset_obj_track_times sets a property in the object creation
 * property list, ocpl_id, that governs the recording of times
 * associated with an object.</p>
 *
 * <p>If track_times is TRUE, the following times will be
 * recorded:</p>
 <dl>
    <dt>Birth time</dt>
    <dd>The time the object was created</dd>
    <dt>Access time</dt>
    <dd>The last time that metadata or raw data was read from the object</dd>
    <dt>Modification time</dt>
    <dd>The last time data for this object was changed (by writing raw
    data to a dataset or inserting, modifying, or deleting a link in a
    group)</dd>
    <dt>Change time</dt>
    <dd>The last time metadata for this object was written (by adding,
    modifying, or deleting an attribute on an object; extending the
    size of a dataset; et cetera).</dd>
 </dl>
 *
 * <p>If track_times is FALSE, time data will not be recorded.</p>
 *
 * <p>Time data can be retrieved with H5Oget_info, which will return
 * it in the H5O_info_t struct.</p>
 *
 * <p>If times are not tracked, they will be reported as 12:00 AM UDT,
 * Jan. 1, 1970 when queried. That date and time are commonly used to
 * represent the beginning of the UNIX epoch. </p>
 *
 * @param ocpl_id Object creation property list identifier.
 * @param track_times Boolean value, TRUE or FALSE, specifying whether
 * object times are to be tracked.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plOCreate
 */
herr_t H5Pset_obj_track_times(hid_t ocpl_id, hbool_t track_times);

/**
 * Sets the dataset creation property list to use the
 * H5Z_FILTER_SCALEOFFSET Scale-Offset filter.
 *
 * <p>H5Pset_scaleoffset sets the Scale-Offset filter,
 * H5Z_FILTER_SCALEOFFSET, for a dataset.</p>
 *
 * <p>Generally speaking, Scale-Offset compression performs a scale
 * and/or offset operation on each data value and truncates the
 * resulting value to a minimum number of bits (MinBits) before
 * storing it. The current Scale-Offset filter supports integer and
 * floating-point datatype.</p>
 *
 * <p>For integer datatype, the parameter scale_type should be set to
 * H5Z_SO_INT (2). The parameter scale_factor denotes MinBits. If the
 * user sets it to H5Z_SO_INT_MINBITS_DEFAULT (0) or less than 0, the
 * filter will calculate MinBits. If scale_factor is set to a positive
 * integer, the filter does not do any calculation and just uses the
 * number as MinBits. However, if the user gives a MinBits that is
 * less than what would be generated by the filter, the compression
 * will be lossy. Also, the MinBits supplied by the user cannot exceed
 * the number of bits to store one value of the dataset datatype.</p>
 *
 * <p>For floating-point datatype, the filter adopts the GRiB data
 * packing mechanism, which offers two alternate methods: E-scaling
 * and D-scaling. Both methods are lossy compression. If the parameter
 * scale_type is set to H5Z_SO_FLOAT_DSCALE (0), the filter will use
 * the D-scaling method; if it is set to H5Z_SO_FLOAT_ESCALE (1), the
 * filter will use the E-scaling method. Since only the D-scaling
 * method is implemented, scale_type should be set to
 * H5Z_SO_FLOAT_DSCALE or 0.</p>
 *
 * <p>When the D-scaling method is used, the original data is "D"
 * scaled--multiplied by 10 to the power of scale_factor, and the
 * "significant" part of the value is moved to the left of the decimal
 * point. Care should be taken in setting the decimal scale_factor so
 * that the integer part will have enough precision to contain the
 * appropriate information of the data value. For example, if
 * scale_factor is set to 2, the number 104.561 will be 10456.1 after
 * "D" scaling. The last digit 1 is not "significant" and is thrown
 * off in the process of rounding. The user should make sure that
 * after "D" scaling and rounding, the data values are within the
 * range that can be represented by the integer (same size as the
 * floating-point type).</p>
 *
 * <p>Valid values for scale_type are as follows:</p>
 <dl>
    <dt>H5Z_SO_FLOAT_DSCALE</dt>
    <dd>Floating-point type, using variable MinBits method</dd>
    <dt>H5Z_SO_FLOAT_ESCALE
    <dd>Floating-point type, using fixed MinBits method</dd>
    <dt>H5Z_SO_INT</dt>
    <dd>Integer type</dd>
 </dl>
 *
 * <p>The meaning of scale_factor varies according to the value
 * assigned to scale_type:</p>
 <dl>
    <dt>H5Z_SO_FLOAT_DSCALE</dt>
    <dd>Denotes the decimal scale factor for D-scaling and can be
    positive, negative or zero. This is the current implementation of
    the library.</dd>
    <dt>H5Z_SO_FLOAT_ESCALE</dt>
    <dd>Denotes MinBits for E-scaling and must be a positive
    integer. This is not currently implemented by the library.</dd>
    <dt>H5Z_SO_INT</dt>
    <dd>Denotes MinBits and it should be a positive integer or
    H5Z_SO_INT_MINBITS_DEFAULT (0). If it is less than 0, the library
    will reset it to 0.</dd>
 </dl>
 *
 * <p>Like other I/O filters supported by the HDF5 library,
 * application using the Scale-Offset filter must store data with
 * chunked storage. </p>
 *
 * @param dcpl_id Dataset creation property list identifier.
 * @param scale_type Flag indicating compression method.
 * @param scale_factor Parameter related to scale.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_scaleoffset(hid_t dcpl_id, H5Z_SO_scale_type_t scale_type, 
			  int scale_factor);

/**
 * Configures the specified shared object header message index.
 *
 * <p>H5Pset_shared_mesg_index is used to configure the specified
 * shared object header message index, setting the types of messages
 * that may be stored in the index and the minimum size of each
 * message.</p>
 *
 * <p>fcpl_id specifies the file creation property list.</p>
 *
 * <p>index_num specifies the index to be configured. index_num is
 * zero-indexed, so in a file with three indexes, they will be
 * numbered 0, 1, and 2.</p>
 *
 * <p>mesg_type_flags and min_mesg_size specify, respectively, the
 * types and minimum size of messages that can be stored in this
 * index.</p>
 *
 * <p>Valid message types are as follows:</p>
 <dl>
    <dt>H5O_SHMESG_NONE_FLAG</dt>
    <dd>No shared messages</dd>
    <dt>H5O_SHMESG_SDSPACE_FLAG</dt>
    <dd>Simple dataspace message</dd>
    <dt>H5O_SHMESG_DTYPE_FLAG</dt>
    <dd>Datatype message</dd>
    <dt>H5O_SHMESG_FILL_FLAG</dt>
    <dd>Fill value message</dd>
    <dt>H5O_SHMESG_PLINE_FLAG</dt>
    <dd>Filter pipeline message</dd>
    <dt>H5O_SHMESG_ATTR_FLAG</dt>
    <dd>Attribute message</dd>
    <dt>H5O_SHMESG_ALL_FLAG</dt>
    <dd>All message types; i.e., equivalent to
    (H5O_SHMESG_SDSPACE_FLAG | H5O_SHMESG_DTYPE_FLAG |
    H5O_SHMESG_FILL_FLAG | H5O_SHMESG_PLINE_FLAG |
    H5O_SHMESG_ATTR_FLAG)</dd>
 </dl>
 *
 * @param fcpl_id File creation property list identifier.
 * @param index_num Index being configured.
 * @param mesg_type_flags Types of messages that should be stored in
 * this index.
 * @param min_mesg_size Minimum message size.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
herr_t H5Pset_shared_mesg_index(hid_t fcpl_id, unsigned index_num, 
				unsigned mesg_type_flags, 
				unsigned min_mesg_size);

/**
 * Sets number of shared object header message indexes.
 *
 * <p>H5Pset_shared_mesg_nindexes sets the number of shared object
 * header message indexes in the specified file creation property
 * list.</p>
 *
 * <p>This setting determines the number of shared object header
 * message indexes that will be available in files created with this
 * property list. These indexes can then be configured with
 * H5Pset_shared_mesg_index.</p>
 *
 * <p>If nindexes is set to 0 (zero), shared object header messages
 * are disabled in files created with this property list.</p>
 *
 * @param fcpl_id File creation property list identifier.
 * @param nindexes Number of shared object header message indexes to
 * be available in files created with this property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
herr_t H5Pset_shared_mesg_nindexes(hid_t fcpl_id, unsigned nindexes);

/**
 * Sets shared object header message storage phase change thresholds.
 *
 * <p>H5Pset_shared_mesg_phase_change sets threshold values for
 * storage of shared object header message indexes in a file. These
 * phase change thresholds determine the point at which the index
 * storage mechanism changes from a more compact list format to a more
 * performance-oriented B-tree format, and vice-versa.</p>
 *
 * <p>By default, a shared object header message index is initially
 * stored as a compact list. When the number of messages in an index
 * exceeds the threshold value of max_list, storage switches to a
 * B-tree for impoved performance. If the number of messages
 * subsequently falls below the min_btree threshold, the index will
 * revert to the list format.</p>
 *
 * <p>If max_list is set to 0 (zero), shared object header message
 * indexes in the file will be created as B-trees and will never
 * revert to lists. </p>
 *
 * @param fcpl_id File creation property list identifier.
 * @param max_list Threshold above which storage of a shared object
 * header message index shifts from list to B-tree.
 * @param min_btree Threshold below which storage of a shared object
 * header message index reverts to list format.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
herr_t H5Pset_shared_mesg_phase_change(hid_t fcpl_id, unsigned max_list, 
				       unsigned min_btree);

/**
 * Sets the dataset creation property list to use the the
 * H5Z_FILTER_SHUFFLE shuffle filter.
 *
 * <p>H5Pset_shuffle sets the shuffle filter, H5Z_FILTER_SHUFFLE, in
 * the dataset creation property list plist_id.  </p>
 *
 * <p>The shuffle filter de-interlaces a block of data by reordering
 * the bytes. All the bytes from one consistent byte position of each
 * data element are placed together in one block; all bytes from a
 * second consistent byte position of each data element are placed
 * together a second block; etc. For example, given three data
 * elements of a 4-byte datatype stored as 012301230123, shuffling
 * will re-order data as 000111222333. This can be a valuable step in
 * an effective compression algorithm because the bytes in each byte
 * position are often closely related to each other and putting them
 * together can increase the compression ratio.</p>
 *
 * <p>As implied above, the primary value of the shuffle filter lies
 * in its coordinated use with a compression filter; it does not
 * provide data compression when used alone. When the shuffle filter
 * is applied to a dataset immediately prior to the use of a
 * compression filter, the compression ratio achieved is often
 * superior to that achieved by the use of a compression filter
 * without the shuffle filter. </p>
 *
 * @param dcpl_id Dataset creation property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_shuffle(hid_t dcpl_id);

/**
 * Sets the maximum size of the data sieve buffer.
 *
 * <p>H5Pset_sieve_buf_size sets size, the maximum size in bytes of
 * the data sieve buffer, which is used by file drivers that are
 * capable of using data sieving.</p>
 *
 * <p>The data sieve buffer is used when performing I/O on datasets in
 * the file. Using a buffer which is large enough to hold several
 * pieces of the dataset being read in for hyperslab selections boosts
 * performance by quite a bit.</p>
 *
 * <p>The default value is set to 64KB, indicating that file I/O for
 * raw data reads and writes will occur in at least 64KB
 * blocks. Setting the value to 0 with this API function will turn off
 * the data sieving, even if the VFL driver attempts to use that
 * strategy. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param size Maximum size, in bytes, of data sieve buffer.
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value.
 *
 * @ingroup plFAccess
 */
herr_t H5Pset_sieve_buf_size(hid_t fapl_id, size_t size);

/**
 * Sets the byte size of the offsets and lengths used to address
 * objects in an HDF5 file.
 *
 * <p>H5Pset_sizes sets the byte size of the offsets and lengths used
 * to address objects in an HDF5 file. This function is only valid for
 * file creation property lists. Passing in a value of 0 for one of
 * the sizeof_... parameters retains the current value. The default
 * value for both values is the same as sizeof(hsize_t) in the library
 * (normally 8 bytes). Valid values currently are 2, 4, 8 and 16.</p>
 *
 * @param fcpl_id File creation property list identifier.
 * @param sizeof_addr Size of an object offset in bytes.
 * @param sizeof_size Size of an object length in bytes.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
herr_t H5Pset_sizes(hid_t fcpl_id, size_t sizeof_addr, size_t sizeof_size);

/**
 * Sets the size of a contiguous block reserved for small datasets.
 *
 * <p>H5Pset_small_data_block_size reserves blocks of size bytes for
 * the contiguous storage of the raw data portion of small
 * datasets. The HDF5 library then writes the raw data from small
 * datasets to this reserved space, thus reducing unnecessary
 * discontinuities within blocks of meta data and improving I/O
 * performance.</p>
 *
 * <p>A small data block is actually allocated the first time a
 * qualifying small dataset is written to the file. Space for the raw
 * data portion of this small dataset is suballocated within the small
 * data block. The raw data from each subsequent small dataset is also
 * written to the small data block until it is filled; additional
 * small data blocks are allocated as required.</p>
 *
 * <p>The HDF5 library employs an algorithm that determines whether
 * I/O performance is likely to benefit from the use of this mechanism
 * with each dataset as storage space is allocated in the file. A
 * larger size will result in this mechanism being employed with
 * larger datasets.</p>
 *
 * <p>The small data block size is set as an allocation property in
 * the file access property list identified by fapl_id.</p>
 *
 * <p>Setting size to zero (0) disables the small data block
 * mechanism. </p>
 *
 * @param fapl_id File access property list identifier.
 * @param size Maximum size, in bytes, of the small data block. The
 * default size is 2048 bytes.
 *
 * @return Returns a non-negative value if successful; otherwise a
 * negative value.
 *
 * @ingroup plFAccess
 */
%apply unsigned long {hsize_t size}
herr_t H5Pset_small_data_block_size(hid_t fapl_id, hsize_t size);
%clear hsize_t size;

/**
 * Sets the size of parameters used to control the symbol table nodes.
 *
 * <p>H5Pset_sym_k sets the size of parameters used to control the
 * symbol table nodes. This function is only valid for file creation
 * property lists. Passing in a value of 0 for one of the parameters
 * retains the current value.</p>
 *
 * <p>ik is one half the rank of a tree that stores a symbol table for
 * a group. Internal nodes of the symbol table are on average 75%
 * full. That is, the average rank of the tree is 1.5 times the value
 * of ik.</p>
 *
 * <p>lk is one half of the number of symbols that can be stored in a
 * symbol table node. A symbol table node is the leaf of a symbol
 * table tree which is used to store a group. When symbols are
 * inserted randomly into a group, the group's symbol table nodes are
 * 75% full on average. That is, they contain 1.5 times the number of
 * symbols specified by lk. </p>
 *
 * @param fcpl_id File creation property list identifier.
 * @param ik Symbol table tree half-rank.
 * @param lk Symbol table node half-size.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
herr_t H5Pset_sym_k(hid_t fcpl_id, unsigned ik, unsigned lk);

/**
 * Sets the dataset creation property list to use the H5Z_FILTER_SZIP
 * SZIP compression filter.
 *
 * <p>H5Pset_szip sets an SZIP compression filter, H5Z_FILTER_SZIP,
 * for a dataset. SZIP is a compression method designed for use with
 * scientific data.</p>
 *
 * <p>Before proceeding, be aware that there are factors that affect
 * your rights and ability to use SZIP compression. See the documents
 * at SZIP Compression in HDF5 for important information regarding
 * terms of use and the SZIP copyright notice, for further discussion
 * of SZIP compression in HDF5, and for a list of SZIP-related
 * references.</p>
 *
 * <p>In the text below, the term pixel refers to an HDF5 data
 * element. This terminology derives from SZIP compression's use with
 * image data, where pixel referred to an image pixel.</p>
 *
 * <p>The SZIP bits_per_pixel value (see Notes, below) is
 * automatically set, based on the HDF5 datatype. SZIP can be used
 * with atomic datatypes that may have size of 8, 16, 32, or 64
 * bits. Specifically, a dataset with a datatype that is 8-, 16-, 32-,
 * or 64-bit signed or unsigned integer; char; or 32- or 64-bit float
 * can be compressed with SZIP. See Notes, below, for further
 * discussion of the the SZIP bits_per_pixel setting.</p>
 *
 * <p>SZIP compression cannot be applied to compound datatypes, array
 * datatypes, variable-length datatypes, enumerations, or any other
 * user-defined datatypes. If an SZIP filter is set up for a dataset
 * containing a non-allowed datatype, H5Pset_szip will succeed but the
 * subsequent call to H5Dcreate will fail; the conflict is detected
 * only when the property list is used.</p>
 *
 * <p>The following mutually exclusive SZIP options are passed in an
 * options mask, options_mask, as follows.</p>
 <dl>
    <dt>H5_SZIP_EC_OPTION_MASK</dt>
    <dd>Selects entropy coding method.</dd>
    <dt>H5_SZIP_NN_OPTION_MASK</dt>
    <dd>Selects nearest neighbor coding method.</dd>
 </dl>
 *
 * <p>The following guidelines can be used in determining which option
 * to select:</p>
 <ul>
    <li>The entropy coding method, specified by
    H5_SZIP_EC_OPTION_MASK, is best suited for data that has been
    processed. The EC method works best for small numbers.</li>
    <li>The nearest neighbor coding method, specified by
    H5_SZIP_NN_OPTION_MASK, preprocesses the data then the applies EC
    method as above. </li>
 </ul>
 *
 * <p>Other factors may affect results, but the above criteria
 * provides a good starting point for optimizing data compression.</p>
 *
 * <p>SZIP compresses data block by block, with a user-tunable block
 * size. This block size is passed in the parameter pixels_per_block
 * and must be even and not greater than 32, with typical values being
 * 8, 10, 16, or 32. This parameter affects compression ratio; the
 * more pixel values vary, the smaller this number should be to
 * achieve better performance.</p>
 *
 * <p>In HDF5, compression can be applied only to chunked datasets. If
 * pixels_per_block is bigger than the total number of elements in a
 * dataset chunk, H5Pset_szip will succeed but the subsequent call to
 * H5Dcreate will fail; the conflict is detected only when the
 * property list is used.</p>
 *
 * <p>To achieve optimal performance for SZIP compression, it is
 * recommended that a chunk's fastest-changing dimension be equal to N
 * times pixels_per_block where N is the maximum number of blocks per
 * scan line allowed by the SZIP library. In the current version of
 * SZIP, N is set to 128.</p>
 *
 * <p>H5Pset_szip will fail if SZIP encoding is disabled in the
 * available copy of the SZIP library. H5Zget_filter_info can be
 * employed to avoid such a failure. </p>
 *
 * <p>Notes:<br/> The following notes are of interest primarily to
 * those who have used SZIP compression outside of the HDF5
 * context.</p>
 *
 * <p>In non-HDF5 applications, SZIP typically requires that the user
 * application supply additional parameters:</p>
 <ul>
    <li>pixels_in_object, the number of pixels in the object to be
    compressed</li>
    <li>bits_per_pixel, the number of bits per pixel</li>
    <li>pixels_per_scanline, the number of pixels per scan line</li>
 </ul>
 *
 * <p>These values need not be independently supplied in the HDF5
 * environment as they are derived from the datatype and dataspace,
 * which are already known. In particular, HDF5 sets pixels_in_object
 * to the number of elements in a chunk and bits_per_pixel to the size
 * of the element or pixel datatype. The following algorithm is used
 * to set pixels_per_scanline:</p>
 <ol>
    <li>If the size of a chunk's fastest-changing dimension, size, is
    greater than 4K, set pixels_per_scanline to 128 times
    pixels_per_block.</li>
    <li>If size is less than 4K but greater than pixels_per_block, set
    pixels_per_scanline to the minimum of size and 128 times
    pixels_per_block.</li>
    <li>If size is less than pixels_per_block but greater than the
    number elements in the chunk, set pixels_per_scanline to the
    minimum of the number elements in the chunk and 128 times
    pixels_per_block. </li>
 </ol>
 *
 * <p>The HDF5 datatype may have precision that is less than the full
 * size of the data element, e.g., an 11-bit integer can be defined
 * using H5Tset_precision. To a certain extent, SZIP can take
 * advantage of the precision of the datatype to improve
 * compression:</p>
 <ol>
    <li>If the HDF5 datatype size is 24-bit or less and the offset of
    the bits in the HDF5 datatype is zero (see H5Tset_offset or
    H5Tget_offset), the data is the in lowest N bits of the data
    element. In this case, the SZIP bits_per_pixel is set to the
    precision of the HDF5 datatype.</li>
    <li>If the offset is not zero, the SZIP bits_per_pixel will be set
    to the number of bits in the full size of the data element.</li>
    <li>If the HDF5 datatype precision is 25-bit to 32-bit, the SZIP
    bits_per_pixel will be set to 32.</li>
    <li>If the HDF5 datatype precision is 33-bit to 64-bit, the SZIP
    bits_per_pixel will be set to 64. </li>
 </ol>
 *
 * <p>HDF5 always modifies the options mask provided by the user to
 * set up usage of RAW_OPTION_MASK, ALLOW_K13_OPTION_MASK, and one of
 * LSB_OPTION_MASK or MSB_OPTION_MASK, depending on endianness of the
 * datatype. </p>
 *
 * @param dcpl_id Dataset creation property list identifier.
 * @param options_mask A bit-mask conveying the desired SZIP
 * options. Valid values are H5_SZIP_EC_OPTION_MASK <em>or</em>
 * H5_SZIP_NN_OPTION_MASK.
 * @param pixels_per_block IN: The number of pixels or data elements
 * in each data block.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsCreate
 */
herr_t H5Pset_szip(hid_t dcpl_id, unsigned options_mask, 
		   unsigned pixels_per_block);

/**
 * Sets user-defined data type conversion callback function.
 *
 * <p>H5Pset_type_conv_cb sets the user-defined data type conversion
 * callback function func in the dataset transfer property list
 * plist.</p>
 *
 * <p>The parameter op_data is a pointer to user-defined input data
 * for the callback function and will be passed through to the
 * callback function.</p>
 *
 * <p>The callback function func defines the actions an application is
 * to take when there is an exception during data type conversion.</p>
 *
 * @param dxpl_id Dataset transfer property list identifier.
 * @param op User-defined type conversion callback function.
 * @param op_data User-defined input data for the callback function.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
//herr_t H5Pset_type_conv_cb(hid_t dxpl_id, H5T_conv_except_func_t op, 
//			   void* op_data);

/**
 * Sets the size of the user block.
 *
 * <p>H5Pset_userblock sets the user block size of a file creation
 * property list. The default user block size is 0; it may be set to
 * any power of 2 equal to 512 or greater (512, 1024, 2048, etc.).</p>
 *
 * @param fcpl_id File creation property list identifier.
 * @param size Size of the user-block in bytes.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plFCreate
 */
%apply unsigned long {hsize_t size}
herr_t H5Pset_userblock(hid_t fcpl_id, hsize_t size);
%clear hsize_t size;

/**
 * Sets the memory manager for variable-length datatype allocation in
 * H5Dread and H5Dvlen_reclaim.
 *
 * <p>H5Pset_vlen_mem_manager sets the memory manager for
 * variable-length datatype allocation in H5Dread and free in
 * H5Dvlen_reclaim.</p>
 *
 * <p>The alloc and free parameters identify the memory management
 * routines to be used. If the user has defined custom memory
 * management routines, alloc and/or free should be set to make those
 * routine calls (i.e., the name of the routine is used as the value
 * of the parameter); if the user prefers to use the system's malloc
 * and/or free, the alloc and free parameters, respectively, should be
 * set to NULL </p>
 *
 * <p>In summary, if the user has defined custom memory management
 * routines, the name(s) of the routines are passed in the alloc and
 * free parameters and the custom routines' parameters are passed in
 * the alloc_info and free_info parameters. If the user wishes to use
 * the system malloc and free functions, the alloc and/or free
 * parameters are set to NULL and the alloc_info and free_info
 * parameters are ignored.</p>
 *
 * @param dxpl_id Identifier for the dataset transfer property list.
 * @param alloc User's allocate routine or NULL for system malloc.
 * @param alloc_info Extra parameter for user's allocation
 * routine. Contents are ignored if preceding parameter is NULL.
 * @param free User's free routine or NULL for system free.
 * @param free_info Extra parameter for user's free routine. Contents
 * are ignored if preceding parameter is NULL.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plDsAccess
 */
/* herr_t H5Pset_vlen_mem_manager(hid_t plist_id, */
/* 			       H5MM_allocate_t alloc_func, void* alloc_info,  */
/* 			       H5MM_free_t free_func, void* free_info); */

/**
 * Removes a property from a property list class.
 *
 * <p>H5Punregister removes a property from a property list class.</p>
 *
 * <p>Future property lists created of that class will not contain
 * this property; existing property lists containing this property are
 * not affected. </p>
 *
 * @param class Property list class from which to remove permanent property.
 * @Param name Name of property to remove.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup plPList
 */
//herr_t H5Punregister(hid_t pclass_id, const char *name);
