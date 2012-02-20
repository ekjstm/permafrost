%module AttributeLib
%include "typemaps.i"
%import "H5types.i"
%include "buffers.i"
%include "H5Atypes.i"

%header %{
#include <errno.h>
#include "H5Ppublic.h"
#include "H5Apublic.h"

#define HARR_NOARRAYS 1
%}

%include "harrays.i"

//typedef herr_t (*H5A_operator_t)(hid_t location_id/*in*/, 
//				 const char *attr_name/*in*/, 
//				 void *operator_data/*in,out*/);
/**
 * Closes the specified attribute.
 *
 * <p>H5Aclose terminates access to the attribute specified by attr_id by 
 * releasing the identifier.</p>
 *
 * <p>Further use of a released attribute identifier is illegal; a function 
 * using such an identifier will fail.</p>
 *
 * @param attr_id Attribute to release access to.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5Aclose(hid_t attr_id);

/**
 * Creates a dataset as an attribute of another group, dataset, or named 
 * datatype.
 *
 * <p>H5Acreate1 creates the attribute attr_name  attached to the object 
 * specified with loc_id. loc_id can be a group, dataset, or named datatype 
 * identifier.</p>
 *
 * <p> The attribute name specified in attr_name must be unique. Attempting to 
 * create an attribute with the same name as an already existing attribute 
 * will fail, leaving the pre-existing attribute in place. To overwrite an 
 * existing attribute with a new attribute of the same name, first call 
 * H5Adelete then recreate the attribute with H5Acreate1.</p>
 *
 * <p>The datatype and dataspace identifiers of the attribute, type_id and 
 * space_id, respectively, are created with the H5T and H5S interfaces, 
 * respectively.</p>
 * 
 * <p>Currently only simple dataspaces are allowed for attribute 
 * dataspaces.</p>
 *
 * <p>The attribute creation property list, acpl_id, is currently unused; it 
 * may be used in the future for optional attribute properties. At this time, 
 * H5P_DEFAULT is the only accepted value.</p>
 *
 * <p>The attribute identifier returned from this function must be released 
 * with H5Aclose or resource leaks will develop.</p>
 *
 * @param loc_id Object (dataset, group, or named datatype) for the attribute to be attached to.
 * @param name Name of attribute to create.
 * @param type_id Identifier of datatype for attribute.
 * @param space_id Identifier of dataspace for attribute.
 * @param acpl_id Identifier of creation property list. (Currently unused; the only accepted value is H5P_DEFAULT.)
 *
 * @return Returns an attribute identifier if successful; otherwise returns a negative value.
 */
%contract H5Acreate1(hid_t loc_id, const char *name, 
		  hid_t type_id, hid_t space_id, hid_t acpl_id) {
require:
acpl_id == H5P_DEFAULT;
}
%javamethodmodifiers H5Acreate1 "@Deprecated\n   public"
hid_t  H5Acreate1(hid_t loc_id, const char *name, 
		  hid_t type_id, hid_t space_id, hid_t acpl_id);


/**
 * Creates an attribute attached to a specified object.
 *
 * <p>H5Acreate2 creates an attribute, attr_name, which is attached to the 
 * object specified by the identifier loc_id.</p>
 *
 * <p>The attribute name, attr_name, must be unique for the object.</p>
 *
 * <p>The attribute is created with the specified datatype and dataspace, 
 * type_id and space_id, which are created with the H5T and H5S interfaces 
 * respectively.</p>
 *
 * <p>The attribute creation and access property lists are currently unused, 
 * but will be used in the future for optional attribute creation and access 
 * properties. These property lists should currently be NULL.</p>
 *
 * The attribute identifier returned by this function must be released with 
 * H5Aclose or resource leaks will develop.</p>
 *
 * @param loc_id Object (dataset, group, or named datatype) for the attribute to be attached to.
 * @param attr_name Name of attribute to create.
 * @param type_id Identifier of datatype for attribute.
 * @param space_id Identifier of dataspace for attribute.
 * @param acpl_id Identifier of attribute creation property list. (Currently unused; the only accepted value is H5P_DEFAULT/NULL.)
 * @param aapl_id Identifier of attribute access property list. (Currently unused; the only accepted value is H5P_DEFAULT/NULL.)
 *
 * @return Returns an attribute identifier if successful; otherwise returns a negative value.
 */
hid_t H5Acreate2(hid_t loc_id, const char *attr_name, hid_t type_id,
    hid_t space_id, hid_t acpl_id, hid_t aapl_id);

/**
 * Creates an attribute attached to a specified object.
 *
 * <p>H5Acreate_by_name creates an attribute, attr_name, which is attached to 
 * the object specified by loc_id and obj_name.</p>
 *
 * <p>loc_id is a location identifier; obj_name is the object name relative 
 * to loc_id. If loc_id fully specifies the object to which the attribute is 
 * to be attached, obj_name should be '.' (a dot).</p>
 *
 * <p>The attribute name, attr_name, must be unique for the object.</p>
 *
 * <p>The attribute is created with the specified datatype and dataspace, 
 * type_id and space_id, which are created with the H5T and H5S interfaces 
 * respectively.</p>
 *
 * <p>The attribute creation and access property lists are currently unused, 
 * but will be used in the future for optional attribute creation and access 
 * properties. These property lists should currently be NULL.</p>
 *
 * <p>The link access property list, lapl_id, may provide information 
 * regarding the properties of links required to access the object, obj_name. 
 * See "Link Access Properties" in the H5P APIs.</p>
 *
 * <p>The attribute identifier returned by this function must be released with 
 * H5Aclose or resource leaks will develop.</p>
 *
 * @param loc_id Object (dataset, group, or named datatype) for the attribute to be attached to.
 * @param obj_name Name, relative to loc_id, of object that attribute is to be attached to.
 * @param attr_name Name of attribute to create.
 * @param type_id Identifier of datatype for attribute.
 * @param space_id Identifier of dataspace for attribute.
 * @param acpl_id Identifier of attribute creation property list. (Currently unused; the only accepted value is H5P_DEFAULT/NULL.)
 * @param aapl_id Identifier of attribute access property list. (Currently unused; the only accepted value is H5P_DEFAULT/NULL.)
 * @param lapl_id Identifier of Link access property list.
 *
 * @return Returns an attribute identifier if successful; otherwise returns a negative value.
 */
hid_t H5Acreate_by_name(hid_t loc_id, const char *obj_name, 
			const char *attr_name, hid_t type_id, hid_t space_id, 
			hid_t acpl_id, hid_t aapl_id, hid_t lapl_id);

/**
 * Deletes an attribute from a location specified by the location id and 
 * attribute name.
 *
 * <p>H5Adelete removes the attribute specified by its name, attr_name, from a 
 * dataset, group, or named datatype. This function should not be used when 
 * attribute identifiers are open on loc_id as it may cause the internal 
 * indexes of the attributes to change and future writes to the open 
 * attributes to produce incorrect results.</p>
 *
 * @param loc_id Identifier of the dataset, group, or named datatype to have the attribute deleted from.
 * @param name Name of the attribute to delete.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5Adelete(hid_t loc_id, const char *name);

/**
 * Removes an attribute from a location specified by the path to the object 
 * and the attribute name. 
 *
 * <p>H5Adelete_by_name removes the attribute attr_name from an object 
 * specified by location and name, loc_id and obj_name, respectively.</p>
 *
 * <p>If loc_id fully specifies the object from which the attribute is to be 
 * removed, obj_name should be '.' (a dot).</p>
 *
 * <p>The link access property list, lapl_id, may provide information 
 * regarding the properties of links required to access the object, obj_name. 
 * See "Link Access Properties" in the H5P APIs.</p>
 *
 * @param loc_id Location or object identifier; may be dataset or group.
 * @param obj_name Name of object, relative to location, from which attribute is to be removed.
 * @param attr_name Name of attribute to delete.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5Adelete_by_name(hid_t loc_id, const char *obj_name, 
			 const char *attr_name, hid_t lapl_id);

%apply long long {hsize_t n}
/**
 * Deletes an attribute from a location specified by the object id and the 
 * index of the attribute.
 *
 * <p>H5Adelete_by_idx removes an attribute, specified by its location in an 
 * index, from an object.</p>
 *
 * <p>The object from which the attribute is to be removed is specified by a 
 * location identifier and name, loc_id and obj_name, respectively. If loc_id 
 * fully specifies the object from which the attribute is to be removed, 
 * obj_name should be '.' (a dot).</p>
 *
 * <p>The attribute to be removed is specified by a position in an index, n. 
 * The type of index is specified by idx_type and may be H5_INDEX_NAME, for an 
 * alpha-numeric index by name, or H5_INDEX_CRT_ORDER, for an index by 
 * creation order. The order in which the index is to be traversed is 
 * specified by order and may be H5_ITER_INC (increment) for top-down 
 * iteration, H5_ITER_DEC (decrement) for bottom-up iteration, or 
 * H5_ITER_NATIVE, in which case HDF5 will iterate in the fastest-available 
 * order. For example, if idx_type, order, and n are set to H5_INDEX_NAME, 
 * H5_ITER_INC, and 5, respectively, the fifth attribute by alpha-numeric 
 * order of attribute names will be removed.</p>
 * 
 * <p>For a discussion of idx_type and order, the valid values of those 
 * parameters, and the use of n, see the description of H5Aiterate2</p>
 *
 * <p>The link access property list, lapl_id, may provide information 
 * regarding the properties of links required to access the object, obj_name. 
 * See Link Access Properties in the H5P APIs.</p>
 *
 * @param loc_id Location or object identifier; may be dataset or group.
 * @param obj_name Name of object, relative to location, from which attribute is to be removed.
 * @param idx_type Iteration type of index.
 * @param order Order in which to iterate over index.
 * @param n Offset within index.
 * @param lapl_id Link access property list.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t  H5Adelete_by_idx(hid_t loc_id, const char *obj_name,
    H5_index_t idx_type, H5_iter_order_t order, hsize_t n, hid_t lapl_id);

%clear hsize_t n;


/**
 * Determines whether an attribute with a given name exists on an object 
 * specified by object id.
 *
 * <p>H5Aexists determines whether the attribute attr_name exists on the 
 * object specified by obj_id.</p>
 *
 * @param obj_id Object identifier.
 * @param attr_name Attribute name.
 *
 * @return When successful, returns a positive value, for TRUE, or 0 (zero), for FALSE. Otherwise returns a negative value.
 */
htri_t H5Aexists(hid_t obj_id, const char *attr_name);

/**
 * Determines whether an attribute with a given name exists on an object 
 * specified by the pathe to the object.
 * 
 * <p>H5Aexists_by_name determines whether the attribute attr_name exists on 
 * an object. That object is specified by its location and name, loc_id and 
 * obj_name, respectively.</p>
 *
 * <p>loc_id specifies a location in the file containing the object. obj_name 
 * is the name of the object to which the attribute is attached and can be a 
 * relative name, relative to loc_id, or an absolute name, based in the root 
 * group of the file. If loc_id fully specifies the object, obj_name should be 
 * '.' (a dot).</p>
 *
 * <p>The link access property list, lapl_id, may provide information 
 * regarding the properties of links required to access obj_name. See 
 "Link Access Properties in the H5P APIs.</p>
 *
 * @param obj_id Location identifier.
 * @param obj_name Object name. Either relative to loc_id, absolute from the file's root group, or '.' (a dot).
 * @param attr_name Attribute name.
 * @param lapl_id Link access property list identifier.
 *
 * @return When successful, returns a positive value, for TRUE, or 0 (zero), for FALSE. Otherwise returns a negative value.
 */
htri_t H5Aexists_by_name(hid_t obj_id, const char *obj_name, 
			 const char *attr_name, hid_t lapl_id);

/**
 * Gets an attribute creation property list identifier.
 *
 * <p> H5Aget_create_plist returns an identifier for the attribute creation 
 * property list associated with the attribute specified by attr_id.</p>
 *
 * <p>The creation property list identifier should be released 
 * with H5Pclose.</p>
 * 
 * @param attr_id Identifier of the attribute.
 *
 * @return Returns an identifier for the attribute's creation property list if successful. Otherwise returns a negative value.
 */
hid_t H5Aget_create_plist(hid_t attr_id);

/**
 * Retrieves attribute information, by attribute identifier.
 *
 * <p>H5Aget_info retrieves attribute information, locating the attribute with 
 * an attribute identifier, attr_id, which is the identifier returned by 
 * H5Aopen or H5Aopen_by_idx. The attribute information is returned in the 
 * ainfo struct.</p>
 *
 * <p>The ainfo struct is defined as follows:</p>
 * <code>
   typedef struct {
      hbool_t             corder_valid;   
      H5O_msg_crt_idx_t   corder;         
      H5T_cset_t          cset;           
      hsize_t             data_size;      
   } H5A_info_t;   
   </code>
 * <p>corder_valid indicates whether the creation order data is valid for this 
 * attribute. Note that if creation order is not being tracked, no creation 
 * order data will be valid. Valid values are TRUE and FALSE.</p>
 *
 * <p>corder is a positive integer containing the creation order of the 
 * attribute. This value is 0-based, so, for example, the third attribute 
 * created will have a corder value of 2.</p>
 *
 * <p>cset indicates the character set used for the attribute's name; valid 
 * values are defined in H5Tpublic.h and include the following:</p>
 * <dl>
 * <dt>H5T_CSET_ASCII</dt><dd>US ASCII</dd>
 * <dt>H5T_CSET_UTF8</dt><dd>UTF-8 Unicode encoding</dd>
 * </dl>
 * <p>This value is set with H5Pset_char_encoding.</p>
 *
 * <p>data_size indicates the size, in the number of characters, of the 
 * attribute.</p>
 *
 * @param attr_id Identifier of the attribute.
 * @param ainfo (Output) Attribute information struct.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5Aget_info(hid_t attr_id, H5A_info_t *ainfo /*out*/);

%apply long long {hsize_t n}
/**
 * Retrieves attribute information, by attribute index position.
 *
 * <p>H5Aget_info_by_idx retrieves information for an attribute that is 
 * attached to an object, which is specified by its location and name, 
 * loc_id and obj_name, respectively. The attribute is located by its index 
 * position and the attribute information is returned in the ainfo struct.</p>
 *
 * <p>If loc_id fully specifies the object to which the attribute is attached, 
 * obj_name should be '.' (a dot).</p>
 *
 * <p>The attribute is located by means of an index type, an index traversal 
 * order, and a position in the index, idx_type, order and n, respectively. 
 * These parameters and their valid values are discussed in the description of 
 * H5Aiterate2.</p>
 *
 * <p>The ainfo struct, which will contain the returned attribute information, 
 * is described in H5Aget_info.</p>
 *
 * <p>The link access property list, lapl_id, may provide information 
 * regarding the properties of links required to access the object, obj_name. 
 * See "Link Access Properties" in the H5P APIs. </p>
 *
 * @param loc_id Location of object to which attribute is attached.
 * @param obj_name Name of object to which attribute is attached, relative to location.
 * @param idx_type Iteration type of index.
 * @param order Order in which to iterate over index.
 * @param n Attribute's position in index.
 * @param ainfo (Output) Struct containing returned attribute information.
 * @param lapl_id Link access property list.
 * @param Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5Aget_info_by_idx(hid_t loc_id, const char *obj_name,
			  H5_index_t idx_type, H5_iter_order_t order, 
			  hsize_t n, H5A_info_t *ainfo /*out*/, hid_t lapl_id);
%clear hsize_t n;

/**
 * Retrieves attribute information, by attribute name.
 *
 * <p>H5Aget_info_by_name retrieves information for an attribute, attr_name, 
 * that is attached to an object, specified by its location and name, loc_id 
 * and obj_name, respectively. The attribute information is returned in the 
 * ainfo struct.</p>
 *
 * <p>If loc_id fully specifies the object to which the attribute is attached, 
 * obj_name should be '.' (a dot).</p>
 *
 * <p>The ainfo struct is described in H5Aget_info.</p>
 *
 * <p>The link access property list, lapl_id, may provide information 
 * regarding the properties of links required to access the object, obj_name. 
 * See Link Access Properties in the H5P APIs. </p>
 *
 * @param loc_id Location of object to which attribute is attached.
 * @param obj_name Name of object to which attribute is attached, relative to location.
 * @param attr_name Attribute name.
 * @param ainfo (Output) Struct containing returned attribute information.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5Aget_info_by_name(hid_t loc_id, const char *obj_name,
			   const char *attr_name, H5A_info_t *ainfo /*out*/, 
			   hid_t lapl_id);


%apply signed char OUTPUT[] {char* buf}
/**
 * Gets the name of an attribute.
 *
 * <p>H5Aget_name retrieves the name of an attribute specified by the 
 * identifier, attr_id. Up to buf_size characters are stored in buf followed 
 * by a \0 string terminator. If the name of the attribute is longer than 
 * (buf_size -1), the string terminator is stored in the last position of the 
 * buffer to properly terminate the string.</p>
 *
 * <p>If the user only wants to find out the size of this name, the values 0 
 * and NULL can be passed in for the parameters bufsize and buf. </p>
 *
 * @param attr_id Identifier of the attribute.
 * @param buf_size The size of the buffer to store the name in.
 * @param buf (Output) Buffer to store name in.
 *
 * @return Returns the length of the attribute's name, which may be longer than buf_size, if successful. Otherwise returns a negative value.
 */
ssize_t H5Aget_name(hid_t attr_id, size_t buf_size, char *buf);
%clear char* buf;

%apply long long {hsize_t n}
%apply signed char OUTPUT[] {char* name}
/**
 * Gets the name of an attribute, by attribute index position.
 *
 * <p>H5Aget_name_by_idx retrieves the name of an attribute that is attached 
 * to an object, which is specified by its location and name, loc_id and 
 * obj_name, respectively. The attribute is located by its index position, 
 * the size of the name is specified in size, and the attribute name is 
 * returned in name.</p>
 * 
 * <p>If loc_id fully specifies the object to which the attribute is attached, 
 * obj_name should be '.' (a dot).</p>
 *
 * <p>The attribute is located by means of an index type, an index traversal 
 * order, and a position in the index, idx_type, order and n, respectively. 
 * These parameters and their valid values are discussed in the description of 
 * H5Aiterate2.</p>
 *
 * <p>If the attribute name's size is unknown, the values 0 and NULL can be 
 * passed in for the parameters size and name. The function's return value 
 * will provide the correct value for size.</p>
 *
 * <p>The link access property list, lapl_id, may provide information 
 * regarding the properties of links required to access the object, obj_name. 
 * See Link Access Propertiesin the H5P APIs.</p>
 *
 * @param loc_id Location of object to which attribute is attached.
 * @param obj_name Name of object to which attribute is attached, relative to location.
 * @param idx_type Iterator type of index.
 * @param order Index traversal order.
 * @param n Attribute's position in index.
 * @param name (Output) Attribute name.
 * @param size Size, in bytes, of attribute name.
 * @param lapl_id Link access property list.
 *
 * @return Returns attribute name size, in bytes, if successful; otherwise returns a negative value.
 */
ssize_t H5Aget_name_by_idx(hid_t loc_id, const char *obj_name,
			   H5_index_t idx_type, H5_iter_order_t order, 
			   hsize_t n, char *name /*out*/, size_t size, 
			   hid_t lapl_id);
%clear char* name;
%clear hsize_t n;

/**
 * Determines the number of attributes attached to an object.
 *
 * <p>H5Aget_num_attrs returns the number of attributes attached to the object 
 * specified by its identifier, loc_id. The object can be a group, dataset, or 
 * named datatype.</p>
 *
 * @param loc_id Identifier of a group, dataset, or named datatype.
 *
 * @return Returns the number of attributes if successful; otherwise returns a negative value.
 */
%javamethodmodifiers H5Aget_num_attrs "@Deprecated\n   public"
int H5Aget_num_attrs(hid_t loc_id);

/**
 * Gets a copy of the dataspace for an attribute.
 *
 * <p>H5Aget_space retrieves a copy of the dataspace for an attribute. The 
 * dataspace identifier returned from this function must be released with 
 * H5Sclose or resource leaks will develop.</p>
 *
 * @param attr_id Identifier of the attribute.
 * 
 * @return Returns attribute dataspace identifier if successful; otherwise returns a negative value.
 */
hid_t H5Aget_space(hid_t attr_id);

%apply long long {hsize_t}
/**
 * Returns the amount of storage required for an attribute.
 *
 * <p>H5Aget_storage_size returns the amount of storage that is required for 
 * the specified attribute, attr_id.</p>
 *
 * @param attr_id Identifier of the attribute to query.
 * 
 * @return Returns the amount of storage size allocated for the attribute; otherwise returns 0 (zero).
 */
hsize_t H5Aget_storage_size(hid_t attr_id);
%clear hsize_t;

/**
 * Gets a copy of the datatype of an attribute.
 *
 * <p>H5Aget_type retrieves a copy of the datatype for an attribute.</p>
 *
 * The datatype is reopened if it is a named type before returning it 
 * to the application. The datatypes returned by this function are always 
 * read-only. If an error occurs when atomizing the return datatype, then the 
 * datatype is closed.</p>
 *
 * <p>The datatype identifier returned from this function must be released 
 * with H5Tclose or resource leaks will develop. </p>
 *
 * @param attr_id Identifier of an attribute.
 * 
 * @return Returns a datatype identifier if successful; otherwise returns a negative value.
 */
hid_t   H5Aget_type(hid_t attr_id);

/**
 * Opens an attribute for an object specified by object identifier and attribute name.
 *
 * <p>H5Aopen opens an existing attribute, attr_name, that is attached to an 
 * object specified an object identifier, object_id.</p>
 *
 * <p>The attribute access property list, aapl_id, is currently unused and 
 * should currently be NULL.</p>
 *
 * <p>This function, H5Aopen_by_idx, or H5Aopen_by_name must be called before 
 * an attribute can be accessed for any further purpose, including reading, 
 * writing, or any modification.</p>
 *
 * <p>The attribute identifier returned by this function must be released with 
 * H5Aclose or resource leaks will develop. </p>
 *
 * @param obj_id Identifer for object to which attribute is attached.
 * @param attr_name Name of attribute to open.
 * @param aapl_id Attribute access property list.
 *
 * @return Returns an attribute identifier if successful; otherwise returns a negative value.
 */
hid_t H5Aopen(hid_t obj_id, const char *attr_name, hid_t aapl_id);

%apply long long {hsize_t n}
/**
 * Opens an attribute, by attribute index position.
 *
 * <p>H5Aopen_by_idx opens an existing attribute that is attached to an object 
 * specified by location and name, loc_id and obj_name, respectively. If 
 * loc_id fully specifies the object to which the attribute is attached, 
 * obj_name should be '.' (a dot).</p>
 *
 * <p>The attribute is identified by an index type, an index traversal order, 
 * and a position in the index, idx_type, order and n, respectively. These 
 * parameters and their valid values are discussed in the description of 
 * H5Aiterate2.</p>
 *
 * <p>The attribute access property list, aapl_id, is currently unused and 
 * should currently be NULL.</p>
 *
 * <p>The link access property list, lapl_id, may provide information 
 * regarding the properties of links required to access the object, obj_name. 
 * See "Link Access Properties" in the H5P APIs.</p>
 *
 * <p>This function, H5Aopen, or H5Aopen_by_name must be called before
 * an attribute can be accessed for any further purpose, including
 * reading, writing, or any modification.</p>
 *
 * <p>The attribute identifier returned by this function must be
 * released with H5Aclose or resource leaks will develop. </p>
 *
 * @param loc_id Location of object to which attribute is attached.
 * @param obj_name Name of object to which attribute is attached, relative to location.
 * @param idx_type Iteration type of index.
 * @param order Index traversal order.
 * @param n Attribute's position in index.
 * @param aapl_id Attribute access property list.
 * @param lapl_id Link access property list.
 *
 * @return Returns an attribute identifier if successful; otherwise returns a negative value.
 */
hid_t H5Aopen_by_idx(hid_t loc_id, const char *obj_name, 
		       H5_index_t idx_type, H5_iter_order_t order, hsize_t n, 
		       hid_t aapl_id, hid_t lapl_id);
%clear hsize_t n;

/**
 * Opens an attribute for an object by object name and attribute name.
 *
 * <p>H5Aopen_by_name opens an existing attribute, attr_name, that is
 * attached to an object specified by location and name, loc_id and
 * obj_name, respectively.</p>
 *
 * <p>loc_id specifies a location from which the target object can be
 * located and obj_name is an object name relative to loc_id. If
 * loc_id fully specifies the object to which the attribute is
 * attached, obj_name should be '.' (a dot).</p>
 *
 * <p> The attribute access property list, aapl_id, is currently unused
 * and should currently be NULL.</p>
 *
 * <p>The link access property list, lapl_id, may provide information
 * regarding the properties of links required to access the object,
 * obj_name. See "Link Access Properties" in the H5P APIs.</p>
 *
 * <p>This function, H5Aopen, or H5Aopen_by_idx must be called before
 * an attribute can be accessed for any further purpose, including
 * reading, writing, or any modification.</p>
 *
 * <p>The attribute identifier returned by this function must be
 * released with H5Aclose or resource leaks will develop. </p>
 *
 * @param loc_id  Location from which to find object to which attribute is attached.
 * @param obj_name Name of object to which attribute is attached, relative to loc_id.
 * @param attr_name Name of attribute to open.
 * @param aapl_id Attribute access property list. (Currently unused; should be passed in as NULL or H5P_DEFAULT.) 
 * @param lapl_id Link access property list.
 *
 * @return Returns an attribute identifier if successful; otherwise returns a negative value.
 */
hid_t H5Aopen_by_name(hid_t loc_id, const char *obj_name,
		      const char *attr_name, hid_t aapl_id, hid_t lapl_id);


//%apply int *OUTPUT {unsigned* attr_num}
//herr_t  H5Aiterate(hid_t loc_id, unsigned *attr_num, H5A_operator_t op, void *op_data);

/**
 * Opens an object attribute, specified by the attribute index.
 *
 * <p>H5Aopen_idx opens an attribute which is attached to the object
 * specified with loc_id. The location object may be either a group,
 * dataset, or named datatype, all of which may have any sort of
 * attribute. The attribute specified by the index, idx, indicates the
 * attribute to access. The value of idx is a 0-based, non-negative
 * integer. The attribute identifier returned from this function must
 * be released with H5Aclose or resource leaks will develop.</p>
 *
 * @param loc_id Identifier of the group, dataset, or named datatype attribute to be attached to.
 * @param idx Index of the attribute to open.
 *
 * @return Returns attribute identifier if successful; otherwise returns a negative value.
 */
%javamethodmodifiers H5Aopen_idx "@Deprecated\n   public"
hid_t   H5Aopen_idx(hid_t loc_id, unsigned idx);

/**
 * Opens an object attribute specified by the attribute name.
 *
 * <p>H5Aopen_name opens an attribute specified by its name, name,
 * which is attached to the object specified with loc_id. The location
 * object may be either a group, dataset, or named datatype, which may
 * have any sort of attribute. The attribute identifier returned from
 * this function must be released with H5Aclose or resource leaks will
 * develop.</p>
 *
 * @param loc_id Identifier of a group, dataset, or named datatype that attribute is attached to.
 * @param name Attribute name.
 * 
 * @return Returns attribute identifier if successful; otherwise returns a negative value.
 */
%javamethodmodifiers H5Aopen_name "@Deprecated\n   public"
hid_t   H5Aopen_name(hid_t loc_id, const char *name);

/**
 * Reads data from an attribute.
 *
 * <p>H5Aread reads an attribute, specified with attr_id. The
 * attribute's memory datatype is specified with mem_type_id. The
 * entire attribute is read into buf from the file.
 *
 * <p>Datatype conversion takes place at the time of a read or write
 * and is automatic. See the Data Conversion section of The Data Type
 * Interface (H5T) in the HDF5 User's Guide for a discussion of data
 * conversion, including the range of conversions currently supported
 * by the HDF5 libraries. </p>
 *
 * @param attr_id Identifier of an attribute to read.
 * @param mem_type_id Identifier of the attribute datatype (in memory).
 * @param buf (Output) Buffer for data to be read.
 */
%apply void* BUFF {void* buf}
herr_t  H5Aread(hid_t attr_id, hid_t type_id, void *buf);

/**
 * Renames an attribute specified by the attribute id.
 * 
 * <p>H5Arename changes the name of the attribute located at loc_id.</p>
 *
 * <p>The old name, old_name, is changed to the new name, 
 * new_name. </p>
 *
 * @param loc_id  Location of the attribute.
 * @param old_name Name of the attribute to be changed.
 * @param new_name New name for the attribute.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t  H5Arename(hid_t loc_id, const char *old_name, const char *new_name);

/**
 * Renames an attribute specified by the name of the attribute.
 *
 * <p>H5Arename_by_name changes the name of attribute that is attached
 * to the object specified by loc_id and obj_name. The attribute named
 * old_attr_name is renamed new_attr_name.
 *
 * <p> The link access property list, lapl_id, may provide information
 * regarding the properties of links required to access the object,
 * obj_name. See "Link Access Properties" in the H5P APIs. </p>
 *
 * @param loc_id Location or object identifier; may be dataset or group.
 * @param obj_name Name of object, relative to location, whose attribute is to be renamed.
 * @param old_attr_name Prior attribute name.
 * @param new_attr_name New attribute name.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t  H5Arename_by_name(hid_t loc_id, const char *obj_name,
			  const char *old_attr_name, 
			  const char *new_attr_name, 
			  hid_t lapl_id);

/**
 * Writes data to an attribute.
 *
 * <p>H5Awrite writes an attribute, specified with attr_id. The
 * attribute's memory datatype is specified with mem_type_id. The
 * entire attribute is written from buf to the file.</p>
 *
 * <p>Datatype conversion takes place at the time of a read or write
 * and is automatic. See the Data Conversion section of The Data Type
 * Interface (H5T) in the HDF5 User's Guide for a discussion of data
 * conversion, including the range of conversions currently supported
 * by the HDF5 libraries.</p>
 *
 * @param attr_id Identifier of an attribute to write.
 * @param mem_type_id Identifier of the attribute datatype (in memory).
 * @param buf Data to be written.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply void* BUFF {void* buf}
herr_t  H5Awrite(hid_t attr_id, hid_t type_id, const void *buf);


















