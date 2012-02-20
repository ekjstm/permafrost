%module LinkLib
%include "typemaps.i"
%include "various.i"
%include "H5types.i"
%include "H5Ltypes.i"
%include "buffers.i"


%header %{
#include <errno.h>
#include "H5Lpublic.h"
#include "H5Lcallbacks.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"


/**
 * Copies a link from one location to another.
 *
 * <p>H5Lcopy copies the link specified by src_name from the file or
 * group specified by src_loc_id to the destination location
 * dest_loc_id. The new copy of the link is created with the name
 * dest_name.</p>
 *
 * <p>The destination location, as specified in dest_loc_id, may be a
 * group in the current file. If dest_loc_id is the file identifier,
 * the copy is placed in the file's root group.</p>
 *
 * <p>The new link is created with the creation and access property
 * lists specified by lcpl_id and lapl_id. The interpretation of
 * lcpl_id is limited in the manner described in the next
 * paragraph.</p>
 *
 * <p>H5Lcopy retains the creation time and the target of the original
 * link. However, since the link may be renamed, the character
 * encoding is that specified in lcpl_id rather than that of the
 * original link. Other link creation properties are ignored.</p>
 *
 * <p>If the link is a soft link, also known as a symbolic link, its
 * target is interpreted relative to the location of the copy.</p>
 *
 * <p>Several properties are available to govern the behavior of
 * H5Lcopy. These properties are set in the link creation and access
 * property lists, lcpl_id and lapl_id, respectively. The property
 * controlling creation of missing intermediate groups is set in the
 * link creation property list with H5Pset_create_intermediate_group;
 * this function ignores any other properties in the link creation
 * property list. Properties controlling character encoding, link
 * traversals, and external link prefixes are set in the link access
 * property list with H5Pset_char_encoding, H5Pset_nlinks, and
 * H5Pset_elink_prefix.</p>
 *
 * <p>H5Lcopy does not affect the object that the link points to.</p>
 *
 * @param src_loc Location identifier of the source link.
 * @param src_name Name of the link to be copied.
 * @param dst_loc Location identifier specifying the destination
 * of the copy.
 * @param dst_name Name to be assigned to the new copy.
 * @param lcpl_id Link creation property list identifier.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Lcopy(hid_t src_loc, const char *src_name, 
	       hid_t dst_loc, const char *dst_name, 
	       hid_t lcpl_id, hid_t lapl_id);

/**
 * Creates a soft link to an object in a different file.
 *
 * <p>H5Lcreate_external creates a new soft link to an external
 * object, which is an object in a different HDF5 file from the
 * location of the link.</p>
 *
 * <p>file_name identifies the file containing the target object;
 * object_name specifies the path to the target object within that
 * file. object_name must start at the target file's root group
 * but is not interpreted until lookup time.</p>
 *
 * <p>link_loc_id and link_name specify the location and name,
 * respectively, of the new external link. link_name is interpreted
 * relative to link_loc_id.</p>
 *
 * <p>lcpl_id and lapl_id are the link creation and access property
 * lists associated with the new link.</p>
 *
 * <p>An external link behaves similarly to a soft link, and like a
 * soft link in an HDF5 file, it may dangle: the target object need
 * not exist at the time that the link is created. Both are also known
 * as symbolic links as they use a name to point to an object; hard
 * links employ an object's address in the file.</p>
 *
 * @param file_name Name of the file containing the target
 * object. Neither the file nor the target object is required to
 * exist. May be the file the link is being created in.
 * @param obj_name Path within the target file to the target
 * object.
 * @param link_loc_id The file or group identifier for the new link.
 * @param link_name The name of the new link.
 * @param lcpl_id Link creation property list identifier.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Lcreate_external(const char *file_name, const char *obj_name,
			  hid_t link_loc_id, const char *link_name, 
			  hid_t lcpl_id, hid_t lapl_id);

/**
 * Creates a hard link to an object.
 *
 * <p>H5Lcreate_hard creates a new hard link to a pre-existing object
 * in an HDF5 file. The new link may be one of many that point to that
 * object.</p>
 *
 * <p>cur_loc and cur_name specify the location and name,
 * respectively, of the target object, i.e., the object that the new
 * hard link points to.</p>
 *
 * <p>dst_loc and link_name specify the location and name,
 * respectively, of the new hard link.</p>
 *
 * <p>dst_name and dst_name are interpreted relative to src_loc
 * and dst_loc, respectively.</p>
 *
 * <p>The target object must already exist in the file.</p>
 *
 * <p>lcpl_id and lapl_id are the link creation and access property
 * lists associated with the new link.</p>
 *
 * <p>Hard and soft links are for use only if the target object is in
 * the current file. If the desired target object is in a different
 * file from the new link, an external link may be created with
 * H5Lcreate_external.</p>
 *
 * <p>The HDF5 library keeps a count of all hard links pointing to an
 * object; if the hard link count reaches zero (0), the object will be
 * deleted from the file. Creating new hard links to an object will
 * prevent it from being deleted if other links are removed. The
 * library maintains no similar count for soft links and they can
 * dangle.</p>
 *
 * @param cur_loc The file or group identifier for the target object.
 * @param cur_name Name of the target object, which must already exist.
 * @param dst_loc The file or group identifier for the new link.
 * @param dst_name The name of the new link.
 * @param lcpl_id Link creation property list identifier.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Lcreate_hard(hid_t cur_loc, const char *cur_name,
		      hid_t dst_loc, const char *dst_name, 
		      hid_t lcpl_id, hid_t lapl_id);

/**
 * Creates a soft link to an object.
 *
 * <p>H5Lcreate_soft creates a new soft link to an object in an HDF5
 * file. The new link may be one of many that point to that
 * object.</p>
 *
 * <p>cur)name specifies the path to the target object, i.e., the
 * object that the new soft link points to. target_path can be
 * anything and is interpreted at lookup time. This path may be
 * absolute in the file or relative to dst_loc.</p>
 *
 * <p>dst_loc_id and dst_name specify the location and name,
 * respectively, of the new soft link. link_name is interpreted
 * relative to link_loc_id</p>
 *
 * <p>For example, the following statement specifies that the target
 * path is <code>./foo</code>, the location of the new link is
 * <code>./x/y/bar</code>, and the name of the new link is
 * <code>new_link</code>:<br/>
 <code>
    status = H5Lcreate_soft(./foo, ./x/y/bar/, new_link,
    H5P_DEFAULT, H5P_DEFAULT)
 </code>
 * <p>A subsequent request for <code>./x/y/bar/new_link</code> would
 * look up the object <code>./x/y/bar/foo</code>.</p>
 *
 * <p>cur_name specifies the path to the target object, i.e., the
 * object that the new soft link points to. target_path can be
 * anything and is interpreted at lookup time. This path may be
 * absolute in the file or relative to link_loc_id. For instance, if
 * cur_name is <code>./foo</code>, link_loc_id specifies
 * <code>./x/y/bar</code>, and a request is made for
 * <code>./x/y/bar/link_name</code>, then the actual object looked up
 * is <code>./x/y/bar/foo</code>.</p>
 *
 * <p>H5Lcreate_soft is for use only if the target object is in the
 * current file. If the desired target object is in a different file
 * from the new link, use H5Lcreate_external to create an external
 * link.</p>
 *
 * <p>lcpl_id and lapl_id are the link creation and access property
 * lists associated with the new link.</p>

 * <p>Soft links and external links are also known as symbolic links
 * as they use a name to point to an object; hard links employ an
 * object's address in the file.</p>
 *
 * <p>Unlike hard links, a soft link in an HDF5 file is allowed to
 * dangle, meaning that the target object need not exist at the time
 * that the link is created.</p>
 *
 * <p>The HDF5 library does not keep a count of soft links as it does
 * of hard links.</p>
 *
 * @param cur_name Path to the target object, which is not required
 * to exist.
 * @param dst_loc The file or group identifier for the new link.
 * @param dst_name The name of the new link.
 * @param lcpl_id Link creation property list identifier.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Lcreate_soft(const char *cur_name, hid_t dst_loc,
		      const char *dst_name, 
		      hid_t lcpl_id, hid_t lapl_id);

/**
 * <p>H5Lcreate_ud creates a link of user-defined type link_type named
 * link_name at the location specified in link_loc_id with
 * user-specified data udata.</p>
 *
 * <p>link_name is interpreted relative to link_loc_id.</p>
 *
 * <p>The link class of the new link, link_type, must already be
 * registered with the library. Such user-defined link classes are
 * registered with H5Lregister.</p>
 *
 * <p>The H5L_type_t, the ENUM specifying valid link classes, is
 * defined in H5Lpublic.h. Valid values without registered
 * user-defined link types include the following:</p>
 <ul>
    <li>H5L_LINK_HARD</li>
    <li>H5L_LINK_SOFT</li>
    <li>H5L_LINK_EXTERNAL</li>
 </ul>
 * <p>Registering a user-defined link type with H5Lregister adds a
 * user-defined symbol to the H5L_type_t ENUM; that user-defined
 * symbol is then passed in the link_type parameter of H5Lcreate_ud
 * and will be returned by H5Lget_info.</p>
 *
 * <p>The format of the information pointed to by udata is defined by
 * the user. udata_size specifies the size of the udata buffer. udata
 * may be NULL if udata_size is zero (0).</p>
 *
 * <p>The property lists specified by lcpl_id and lapl_id specify
 * properties used to create and access the link.</p>
 *
 * <p>Note:<br/> Though the external link type is included in the HDF5
 * Library distribution, it is implemented as a user-defined link
 * type. This was done, in part, to provide a model for implementation
 * of other user-defined links.</p>
 *
 * @param link_loc_id Link location identifier.
 * @param link_name Link name.
 * @param link_type User-defined link class.
 * @param udata User-supplied link information.
 * @param udata_size Size of udata.
 * @param lcpl_id Link creation property list identifier.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Lcreate_ud(hid_t link_loc_id, const char *link_name,
		    H5L_type_t link_type, const void *udata, 
		    size_t udata_size,
		    hid_t lcpl_id, hid_t lapl_id);

/**
 * Removes a link from a group.
 *
 * <p>H5Ldelete removes the link specified by name from the location
 * loc_id.</p>
 *
 * <p>If the link being removed is a hard ink, H5Ldelete also
 * decrements the link count for the object to which name
 * points. Unless there is a duplicate hard link in that group, this
 * action removes the object to which name points from the group that
 * previously contained it.</p>
 *
 * <p>Object headers keep track of how many hard links refer to an
 * object; when the hard link count, also referred to as the reference
 * count, reaches zero, the object can be removed from the file. The
 * file space associated will then be released, i.e., identified in
 * memory as freespace. Objects which are open are not removed until
 * all identifiers to the object are closed.</p>
 *
 * <p>Note that space identified as freespace is available for re-use
 * only as long as the file remains open; once a file has been closed,
 * the HDF5 library loses track of freespace. See "Freespace
 * Management" in "Performace Analysis and Issues" for
 * further details.</p>
 *
 * <p>Warning:<br/> Exercise caution in the use of H5Ldelete; if the
 * link being removed is on the only path leading to an HDF5 object,
 * that object may become permanently inaccessible in the file.</p>
 *
 * @param loc_id Identifier of the file or group containing the object.
 * @param name Name of the link to delete.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Ldelete(hid_t loc_id, const char *name, hid_t lapl_id);

/**
 * Removes the nth link in a group.
 *
 * <p>H5Ldelete_by_idx removes the nth link in a group according to
 * the specified order, order, in the specified index, index.</p>
 *
 * <p>If loc_id specifies the group in which the link resides,
 * group_name can be a dot (.). </p>
 *
 * @param loc_id File or group identifier specifying location of
 * subject group.
 * @param group_name Name of subject group.
 * @param index_field Index or field which determines the order.
 * @param order Order within field or index.
 * @param n Link for which to retrieve information.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long {hsize_t n}
herr_t H5Ldelete_by_idx(hid_t loc_id, const char *group_name,
			H5_index_t idx_type, H5_iter_order_t order, 
			hsize_t n, hid_t lapl_id);
%clear hsize_t n;

/**
 * Check if a link with a particular name exists in a group.
 *
 * <p>H5Lexists allows an application to determine if a link with a
 * particular name exists in a group. The link could be of any type,
 * just the presence of a link with that name is checked.</p>
 *
 * @param loc_id Identifier of the file or group to query.
 * @param name The name of the link to check.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns TRUE or FALSE if successful; otherwise returns a
 * negative value.
 */
htri_t H5Lexists(hid_t loc_id, const char *name, hid_t lapl_id);

/**
 * Returns information about a link.
 *
 * <p>H5Lget_info returns information about the specified link through
 * the link_buff argument.</p>
 *
 * <p>A file or group identifier, link_loc_id, specifies the location
 * of the link. A link name, link_name, interpreted relative to
 * loc_id, specifies the link being queried.</p>
 *
 * <p>lapl_id is the link access property list associated with the
 * link link_name. In the general case, when default link access
 * properties are acceptable, this can be passed in as H5P_DEFAULT. An
 * example of a situation that requires a non-default link access
 * property list is when the link is an external link; an external
 * link may require that a link prefix be set in a link access
 * property list (see H5Pset_elink_prefix).</p>
 *
 * <p>H5Lget_info returns information about link_name in the data
 * structure H5L_info_t, which is described below and defined in
 * H5Lpublic.h. This structure is returned in the buffer
 * link_buff.</p> <code> typedef struct { H5L_type_t type; hbool_t
 * corder_valid; int64_t corder; H5T_cset_t cset; union { haddr_t
 * address; size_t val_size; } u; } H5L_info_t; </code>
 *
 * <p>In the above struct, type specifies the link class. Valid values
 * include the following:</p>
 <dl>
    <dt>H5L_LINK_HARD</dt>
    <dd>Hard link</dd>
    <dt>H5L_LINK_SOFT</dt>
    <dd>Soft link</dd>
    <dt>H5L_LINK_EXTERNAL</dt>
    <dd>External link</dd>
    <dt>H5L_LINK_ERROR</dt>
    <dd>Error</dd>
 </dl>
 * <p>There will be additional valid values if user-defined links have
 * been registered.</p>
 *
 * <p>corder specifies the link's creation order position while
 * corder_valid indicates whether the value in corder is valid.</p>
 *
 * <p>If corder_valid is TRUE, the value in corder is known to be
 * valid; if corder_valid is FALSE, the value in corder is presumed to
 * be invalid;</p>
 *
 * <p>corder starts at zero (0) and is incremented by one (1) as new
 * links are created. But higher-numbered entries are not adjusted
 * when a lower-numbered link is deleted; the deleted link's creation
 * order position is simply left vacant. In such situations, the value
 * of corder for the last link created will be larger than the number
 * of links remaining in the group.</p>
 *
 * <p>cset specifies the character set in which the link name is
 * encoded. Valid values include the following:</p>
 <dl>
    <dt>H5T_CSET_ASCII</dt>
    <dd>US ASCII</dd>
    <dt>H5T_CSET_UTF8</dt>
    <dd>UTF-8 Unicode encoding</dd>
 </dl>
 *
 * <p>address and link_len are returned for hard and symbolic links,
 * respectively. Symbolic links include soft and external links and
 * some user-defined links.</p>
 *
 * <p>If the link is a hard link, address specifies the file address
 * that the link points to.</p>
 *
 * <p>If the link is a symbolic link, val_size will be the length of
 * the link value, e.g., the length of the name of the pointed-to
 * object with a null terminator. </p>
 *
 * @param loc_id File or group identifier.
 * @param name Name of the link for which information is being sought.
 * @param linfo (Output) Buffer in which link information is returned.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful, with the fields
 * of link_buff (if non-null) initialized. Otherwise returns a
 * negative value.
 */
herr_t H5Lget_info(hid_t loc_id, const char *name,
		   H5L_info_t *linfo /*out*/, hid_t lapl_id);

/**
 * Retrieves metadata for a link in a group, according to the order
 * within a field or index.
 *
 * <p>H5Lget_info_by_idx returns the metadata for a link in a group
 * according to a specified field or index and a specified order.</p>
 *
 * <p>The link for which information is to be returned is specified by
 * index_field, order, and n as follows:</p>
 <ul>
    <li>index_field specifies the field by which the links in
    group_name are ordered. The links may be indexed on this field, in
    which case operations seeking specific links are likely to
    complete more quickly.</li>
    <li>order specifies the order in which the links are to be
    referenced for the purposes of this function.</li>
    <li>n specifies the position of the subject link. Note that this
    count is zero-based; 0 (zero) indicates that the function will
    return the value of the first link; if n is 5, the function will
    return the value of the sixth link; etc. </li>
 </ul>
 * <p>For example, assume that index_field, order, and n are
 * H5_INDEX_NAME, H5_ITER_DEC, and 5, respectively. H5_INDEX_NAME
 * indicates that the links are accessed in alpha-numeric order by
 * their names. H5_ITER_DEC specifies that the list be traversed in
 * reverse order, or in decremented order. And 5 specifies that this
 * call to the function will return the metadata for the 6th link (n +
 * 1) from the end.</p>
 *
 * <p>See H5Literate for a list of valid values and further discussion
 * regarding index_field and order.</p>
 *
 * <p>If loc_id specifies the group in which the link resides,
 * group_name can be a dot (.). </p>
 *
 * @param loc_id File or group identifier specifying location of
 * subject group.
 * @param group_name Name of subject group.
 * @param index_field Index or field which determines the order.
 * @param order Order within field or index.
 * @param n Link for which to retrieve information.
 * @param link_val (Output) Buffer in which link value is returned.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long {hsize_t n}
herr_t H5Lget_info_by_idx(hid_t loc_id, const char *group_name,
			  H5_index_t idx_type, H5_iter_order_t order, 
			  hsize_t n, H5L_info_t *linfo /*out*/, 
			  hid_t lapl_id);
%clear hsize_t n;

/**
 * Retrieves name of the nth link in a group, according to the order
 * within a specified field or index.
 *
 * <p>H5Lget_name_by_idx retrieves the name of the nth link in a
 * group, according to the specified order, order, within a specified
 * field or index, index_field.</p>
 *
 * <p>If loc_id specifies the group in which the link resides,
 * group_name can be a dot (.).</p>
 *
 * <p>The size in bytes of name is specified in size. If size is
 * unknown, it can be determined via an initial H5Lget_name_by_idx
 * call with name set to NULL; the function's return value will be the
 * size of the name. </p>
 *
 * @param loc_id File or group identifier specifying location of
 * subject group.
 * @param group_name Name of subject group.
 * @param index_field Index or field which determines the order.
 * @param order Order within field or index.
 * @param n Link for which to retrieve information.
 * @param name (Output) Buffer in which link value is returned.
 * @param size Size in bytes of name.
 * @param lapl_id Link access property list.
 *
 * @return Returns the size of the link name if successful; otherwise
 * returns a negative value.
 */
%apply long long {hsize_t n}
%apply signed char OUTPUT[] {char* name}
ssize_t H5Lget_name_by_idx(hid_t loc_id, const char *group_name,
    H5_index_t idx_type, H5_iter_order_t order, hsize_t n,
    char *name /*out*/, size_t size, hid_t lapl_id);
%clear char* name;
%clear hsize_t n;

/**
 * Returns the value of a symbolic link.
 *
 * <p>H5Lget_val returns the link value of the link link_name.</p>
 *
 * <p>The parameter link_loc_id is a file or group identifier.</p>
 *
 * <p>link_name identifies a symbolic link and is defined relative to
 * link_loc_id. Symbolic links include soft and external links and
 * some user-defined links.</p>
 *
 * <p>The link value is returned in the buffer linkval_buff. For soft
 * links, this is the path to which the link points, including the
 * null terminator; for external and user-defined links, it is the
 * link buffer.</p>
 *
 * <p>size is the size of linkval_buff and should be the size of the
 * link value being returned. This size value can be determined
 * through a call to H5Lget_info; it is returned in the link_len field
 * of the H5L_info_t struct.</p>
 *
 * <p>If size is smaller than the size of the returned value, then the
 * string stored in linkval_buff will be truncated to size bytes. For
 * soft links, this means that the value will not be null
 * terminated.</p>
 *
 * <p>In the case of external links, the target file and object names
 * are extracted from linkval_buff by calling H5Lunpack_elink_val.</p>
 *
 * <p>The link class of link_name can be determined with a call to
 * H5Lget_info.</p>
 *
 * <p>lapl_id specifies the link access property list associated with
 * the link link_name. In the general case, when default link access
 * properties are acceptable, this can be passed in as H5P_DEFAULT. An
 * example of a situation that requires a non-default link access
 * property list is when the link is an external link; an external
 * link may require that a link prefix be set in a link access
 * property list (see H5Pset_elink_prefix).</p>
 *
 * <p>This function should be used only after H5Lget_info has been
 * called to verify that link_name is a symbolic link. This can be
 * determined from the link_type field of the H5L_info_t struct. </p>
 *
 * @param link_loc_id File or group identifier.
 * @param link_name Link whose value is to be returned.
 * @param linkval_buff The buffer to hold the returned link value.
 * @param size Maximum number of characters of link value to be returned.
 * @param lapl_id List access property list identifier.
 *
 * @return Returns a non-negative value, with the link value in
 * linkval_buff, if successful. Otherwise returns a negative value.
 */
%apply void* BUFF {void* buf}
herr_t H5Lget_val(hid_t loc_id, const char *name, void *buf/*out*/,
		  size_t size, hid_t lapl_id);
%clear void* buf;

/**
 * Retrieves value of the nth link in a group, according to the order
 * within an index.
 *
 * <p>H5Lget_val_by_idx retrieves the value of the nth link in a
 * group, according to the specified order, order, within an index,
 * index.</p>
 <ul>
    <li>For hard links, the value is the address of the object pointed to.</li>
    <li>For soft links, the value is the path name of the object
    pointed to.</li>
    <li>For external links, this is a compound value containing file
    and path name information; to use this external link information,
    it must first be decoded with H5Lunpack_elink_val</li>
    <li>For user-defined links, this value will be described in the
    definition of the user-defined link type. </li>
 </ul>
 *
 * <p>If loc_id specifies the group in which the link resides,
 * group_name can be a dot (.).</p>
 *
 * <p>The size in bytes of group_name is specified in size. If size is
 * unknown, it can be determined via an initial H5Lget_val_by_idx call
 * with size set to NULL; size will be returned with the actual size
 * of group_name. </p>
 *
 * @param loc_id File or group identifier specifying location of subject group.
 * @param group_name Name of subject group.
 * @param index_type Type of index; valid values include:
 <dl>
    <dt>NAME</dt>
    <dd>Indexed by name</dd>
    <dt>CORDER</dt>
    <dd>Indexed by creation order</dd>
 </dl>
 * @param order Order within field or index; valid values include:
 <dl>
    <dt>H5_ITER_INC</dt>
    <dd>Iterate in increasing order</dd>
    <dt>H5_ITER_DEC</dt>
    <dd>Iterate in decreasing order</dd>
    <dt>H5_ITER_NATIVE</dt>
    <dd>Iterate in fastest order</dd>
 </dl>
 * @param n Link for which to retrieve information.
 * @param link_val (Output) Pointer to buffer in which link value is returned.
 * @param size Size in bytes of group_name.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long {hsize_t n}
%apply void* BUFF {void* buf}
herr_t H5Lget_val_by_idx(hid_t loc_id, const char *group_name,
			 H5_index_t idx_type, H5_iter_order_t order, hsize_t n,
			 void *buf/*out*/, size_t size, hid_t lapl_id);
%clear hsize_t n;
%clear void* buf;

/**
 * Determines whether a class of user-defined links is registered.
 *
 * <p>H5Lis_registered tests whether a user-defined link class is
 * currently registered, either by the HDF5 Library or by the user
 * through the use of H5Lregister.</p>
 *
 * <p>A link class must be registered to create new links of that type
 * or to traverse exisitng links of that type. </p>
 *
 * @param cls_id User-defined link class identifier.
 *
 * @return Returns a positive value if the link class has been
 * registered and zero if it is unregistered. Otherwise returns a
 * negative value; this may mean that the identifier is not a valid
 * user-defined class identifier.
 */
htri_t H5Lis_registered(H5L_type_t cls_id);

/*nodoc*
 * Iterates through links in a group.
 *
 * <p>H5Literate iterates through the links in a group, specified by
 * group_id, in the order of the specified index, index_type, using a
 * user-defined callback routine op. H5Literate does not recursively
 * follow links into subgroups of the specified group.</p>
 *
 * <p>Three parameters are used to manage progress of the iteration:
 * index_type, order, and idx.</p>
 *
 * <p>index_type specifies the index to be used. If the links have not
 * been indexed by the index type, they will first be sorted by that
 * index then the iteration will begin; if the links have been so
 * indexed, the sorting step will be unnecesary, so the iteration may
 * begin more quickly. Valid values include the following:</p>
 <dl>
    <dt>H5_INDEX_NAME</dt>
    <dd>Alpha-numeric index on name</dd>
    <dt>H5_INDEX_CRT_ORDER</dt>
    <dd>Index on creation order</dd>
 </dl>
 * <p>order specifies the order in which objects are to be inspected
 * along the index specified in index_type. Valid values include the
 * following:</p>
 <dl>
    <dt>H5_ITER_INC</dt>
    <dd>Increasing order</dd>
    <dt>H5_ITER_DEC</dt>
    <dd>Decreasing order</dd>
    <dt>H5_ITER_NATIVE</dt>
    <dd>Fastest available order</dd>
 </dl>
 *
 * <p>idx allows an interrupted iteration to be resumed; it is passed
 * in by the application with a starting point and returned by the
 * library with the point at which the iteration stopped.</p>
 *
 * <p>As mentioned above, H5Literate is not recursive. In particular,
 * if a member of group_id is found to be a group, call it subgroup_a,
 * H5Literate does not examine the members of subgroup_a. When
 * recursive iteration is required, the application can do either of
 * the following:</p>
 <ul>
    <li>Use one of the following recursive routines instead of H5Literate:<br/>
    <ul>
       <li>H5Lvisit</li>
       <li>H5Lvisit_by_name</li>
       <li>H5Ovisit</li>
       <li>H5Ovisit_by_name</li>
    </ul>
    </li>
    <li>Handle the recursion manually, explicitly calling H5Literate
    on discovered subgroups. </li>
 </ul>
 * <p>H5Literate is the same as H5Giterate, except that H5Giterate
 * always proceeds in alphanumeric order. </p>
 *
 * @param group_id Identifier specifying subject group.
 * @param index_type Type of index which determines the order.
 * @param order Order within index.
 * @param idx (Input) Iteration position at which to start. (Output)
 * Position at which an interrupted iteration may be restarted.
 * @param op Callback function passing data regarding the link to the
 * calling application.
 * @param op_data User-defined pointer to data required by the
 * application for its processing of the link.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Literate(hid_t grp_id, H5_index_t idx_type,
//    H5_iter_order_t order, hsize_t *idx, H5L_iterate_t op, void *op_data);


/*nodoc*
 * Iterates through links in a group specified by name.
 *
 * <p>H5Literate_by_name iterates through the links in a group,
 * specified by loc_id and group_name, in the order of the specified
 * index, index_type, using a user-defined callback routine
 * op. H5Literate_by_name does not recursively follow links into
 * subgroups of the specified group.</p>
 *
 * <p>index_type specifies the index to be used. If the links have not
 * been indexed by the index type, they will first be sorted by that
 * index then the iteration will begin; if the links have been so
 * indexed, the sorting step will be unnecesary, so the iteration may
 * begin more quickly. Valid values include the following:</p>
 <dl>
    <dt>H5_INDEX_NAME</dt>
    <dd>Alpha-numeric index on name</dd>
    <dt>H5_INDEX_CRT_ORDER</dt>
    <dd>Index on creation order</dd>
 </dl>
 *
 * <p>order specifies the order in which objects are to be inspected
 * along the index specified in index_type. Valid values include the
 * following:</p>
 <dl>
    <dt>H5_ITER_INC</dt>
    <dd>Increasing order</dd>
    <dt>H5_ITER_DEC</dt>
    <dd>Decreasing order</dd>
    <dt>H5_ITER_NATIVE</dt>
    <dd>Fastest available order</dd>
 </dl>
 *
 * <p>idx allows an interrupted iteration to be resumed; it is passed
 * in by the application with a starting point and returned by the
 * library with the point at which the iteration stopped.</p>
 *
 * <p>H5Literate_by_name is not recursive. In particular, if a member
 * of group_name is found to be a group, call it subgroup_a,
 * H5Literate_by_name does not examine the members of subgroup_a. When
 * recursive iteration is required, the application must handle the
 * recursion, explicitly calling H5Literate_by_name on discovered
 * subgroups.</p>
 *
 * <p>H5Literate_by_name is the same as H5Giterate, except that
 * H5Giterate always proceeds in alphanumeric order. </p>
 *
 * @param loc_id File or group identifier specifying location of
 * subject group.
 * @param group_name Name of subject group.
 * @param index_type Type of index which determines the order.
 * @param order Order within index.
 * @param idx (Input) Iteration position at which to start. (Output)
 * Position at which an interrupted iteration may be restarted.
 * @param op Callback function passing data regarding the link to the
 * calling application.
 * @param op_data User-defined pointer to data required by the
 * application for its processing of the link.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Literate_by_name(hid_t loc_id, const char *group_name,
//    H5_index_t idx_type, H5_iter_order_t order, hsize_t *idx,
//    H5L_iterate_t op, void *op_data, hid_t lapl_id);


/**
 * Renames a link within an HDF5 file.
 *
 * <p>H5Lmove renames a link within an HDF5 file. The original link,
 * src_name, is removed from the group graph and the new link,
 * dest_name, is inserted; this change is accomplished as an atomic
 * operation.</p>
 *
 * <p>src_loc_id and src_name identify the existing link. src_loc_id
 * is either a file or group identifier; src_name is the path to the
 * link and is interpreted relative to src_loc_id.</p>
 *
 * <p>dest_loc_id and dest_name identify the new link. dest_loc_id is
 * either a file or group identifier; dest_name is the path to the
 * link and is interpreted relative to dest_loc_id.</p>
 *
 * <p>lcpl and lapl are the link creation and link access property
 * lists, respectively, associated with the new link, dest_name.</p>
 *
 * <p>Through these property lists, several properties are available
 * to govern the behavior of H5Lmove. The property controlling
 * creation of missing intermediate groups is set in the link creation
 * property list with H5Pset_create_intermediate_group; H5Lmove
 * ignores any other properties in the link creation property
 * list. Properties controlling character encoding, link traversals,
 * and external link prefixes are set in the link access property list
 * with H5Pset_char_encoding, H5Pset_nlinks, and H5Pset_elink_prefix,
 * respectively.</p>
 *
 * <p>Warning:<br/> Exercise care in moving links as it is possible to
 * render data in a file inaccessible with H5Lmove. If the link being
 * moved is on the only path leading to an HDF5 object, that object
 * may become permanently inaccessible in the file.</p>
 *
 * @param src_loc Original file or group identifier.
 * @param src_name Original link name.
 * @param dst_loc Destination file or group identifier.
 * @param dst_name New link name.
 * @param lcpl_id Link creation property list identifier to be
 * associated with the new link.
 * @param lapl_id Link access property list identifier to be
 * associated with the new link.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Lmove(hid_t src_loc, const char *src_name, hid_t dst_loc,
	       const char *dst_name, hid_t lcpl_id, hid_t lapl_id);

/**
 * Registers user-defined link class or changes behavior of existing class.
 *
 * <p>H5Lregister registers a class of user-defined links, or changes
 * the behavior of an existing class.</p>
 *
 * <p>The struct H5L_class_t is defined in H5Lpublic.h as follows:</p>
 <code>
  typedef struct H5L_class_t {
      int version;                    //!< Version number of this struct.
      H5L_type_t class_id;            //!< Link class identifier.
      const char *comment;            //!< Comment for debugging.
      H5L_create_func_t create_func;  //!< Callback during link creation.
      H5L_move_func_t move_func;      //!< Callback after moving link.
      H5L_copy_func_t copy_func;      //!< Callback after copying link.
      H5L_traverse_func_t trav_func;  //!< The main traversal function.
      H5L_delete_func_t del_func;     //!< Callback for link deletion.
      H5L_query_func_t query_func;    //!< Callback for queries.
  } H5L_class_t;
 </code>
 *
 * <p>The link class passed in will override any existing link class
 * for the specified link class identifier class_id. The class
 * definition must include at least a H5L_class_t version (which
 * should be H5L_LINK_CLASS_T_VERS), a link class identifier, and a
 * traversal function, trav_func.</p>
 *
 * <p>Valid values of class_id include the following (defined in
 * H5Lpublic.h):</p>
 <dl>
    <dt>H5L_TYPE_HARD</dt>
    <dd>Hard link</dd>
    <dt>H5L_TYPE_SOFT</dt>
    <dd>Soft link</dd>
    <dt>H5L_TYPE_EXTERNAL</dt>
    <dd>External link</dd>
 </dl>
 *
 * <p>class_id must be a value between H5L_LINK_UD_MIN and
 * H5L_LINK_UD_MAX, Note that as distributed with the HDF5 Library,
 * the external link class is implemented as an example user-defined
 * link class and H5L_LINK_EXTERNAL equals H5L_LINK_UD_MIN. Therefore,
 * class_id should not equal H5L_LINK_UD_MIN unless you intend to
 * overwrite or modify the behavior of external links. To
 * summarize:</p>
 <ul>
     <li>H5L_TYPE_ERROR indicates that an error has occurred.</li>
     <li>H5L_TYPE_MAX is the maximum allowed value for a link type
     identifier.</li>
     <li>H5L_TYPE_UD_MIN equals H5L_TYPE_EXTERNAL.</li>
     <li>H5L_TYPE_UD_MAX equals H5L_TYPE_MAX.</li>
     <li>H5L_TYPE_HARD and H5L_TYPE_SOFT reside in the reserved space
     below H5L_TYPE_UD_MIN.</li>
 </ul>
 *
 * <p>Note:<br/> If you plan to distribute files with a new
 * user-defined link class, please contact the Help Desk at The HDF
 * Group to help prevent collisions between class_id values.</p>
 *
 * @param link_class Struct describing user-defined link class.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Lregister(const H5L_class_t *cls);

/**
 * Decodes external link information.
 *
 * <p>H5Lunpack_elink_val decodes the external link information
 * returned by H5Lget_val in the ext_linkval buffer.</p>
 *
 * <p>ext_linkval should be the buffer set by H5Lget_val and will
 * consist of two NULL-terminated strings, the filename and object
 * path, one after the other.</p>
 *
 * <p>Given this buffer, H5Lunpack_elink_val creates pointers to the
 * filename and object path within the buffer and returns them in
 * filename and obj_path, unless they are passed in as NULL.</p>
 *
 * <p>H5Lunpack_elink_val requires that ext_linkval contain a
 * concatenated pair of null-terminated strings, so use of this
 * function on strings that are not external link udata buffers may
 * result in a segmentation fault. This failure can be avoided by
 * adhering to the following procedure:</p>
 <ol>
    <li>First call H5Lget_info to get the link type and the size of
    the link value.</li>
    <li>Verify that the link is an external link, i.e., that its link
    type is H5L_LINK_EXTERNAL.</li>
    <li>Then call H5Lget_val to get the link value.</li>
    <li>Then call H5Lunpack_elink_val to unpack that value.</li>
 </ol>
 *
 * @param ext_linkval Buffer containing external link information.
 * @param link_size Size, in bytes, of the ext_linkval buffer.
 * @param flags (Output) External link flags, packed as a bitmap 
 * (Reserved as a bitmap for flags; no flags are currently defined, 
 * so the only valid value is 0.)
 * @param filename (Output) Returned filename.
 * @pram obj_path (Output) Returned object path, relative to filename.
 */
%apply void* BUFF {const void* ext_linkval}
%apply unsigned int* OUTPUT {unsigned* flags}
%apply char** STRING_OUT {const char** filename}
%apply char** STRING_OUT {const char** obj_path}
herr_t H5Lunpack_elink_val(const void *ext_linkval/*in*/, size_t link_size,
			   unsigned *flags, const char **filename/*out*/, 
			   const char **obj_path /*out*/);
%clear const void* ext_linkval;
%clear unsigned* flags;
%clear const char** filename;
%clear const char** obj_path;

/**
 * Unregisters a class of user-defined links.
 *
 * <p>H5Lunregister unregisters a class of user-defined links,
 * preventing them from being traversed, queried, moved, etc.</p>
 *
 * <p>A link class can be re-registered using H5Lregister. </p>
 *
 * @param link_cls_id User-defined link class identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Lunregister(H5L_type_t id);

/**
 * Recursively visits all links starting from a specified group.
 *
 * <p>H5Lvisit is a recursive iteration function to visit all links in
 * and below a group in an HDF5 file, thus providing a mechanism for
 * an application to perform a common set of operations across all of
 * those links or a dynamically selected subset. For non-recursive
 * iteration across the members of a group, see H5Literate.</p>
 *
 * <p>The group serving as the root of the iteration is specified by
 * it's group identifier, group_id</p>
 *
 * <p>Two parameters are used to establish the iteration: index_type
 * and order.</p>
 *
 * <p>index_type specifies the index to be used. If the links have not
 * been indexed by the index type, they will first be sorted by that
 * index then the iteration will begin; if the links have been so
 * indexed, the sorting step will be unnecesary, so the iteration may
 * begin more quickly. Valid values include the following:</p>
 <dl>
    <dt>H5_INDEX_NAME</dt>
    <dd>Alpha-numeric index on name</dd>
    <dt>H5_INDEX_CRT_ORDER</dt>
    <dd>Index on creation order</dd>
 </dl>
 *
 * <p>Note that the index type passed in index_type is a best effort
 * setting. If the application passes in a value indicating iteration
 * in creation order and a group is encountered that was not tracked
 * in creation order, that group will be iterated over in
 * alpha-numeric order by name, or name order. (Name order is the
 * native order used by the HDF5 Library and is always available.)</p>
 *
 * <p>order specifies the order in which objects are to be inspected
 * along the index specified in index_type. Valid values include the
 * following:</p>
 <dl>
    <dt>H5_ITER_INC</dt>
    <dd>Increasing order</dd>
    <dt>H5_ITER_DEC</dt>
    <dd>Decreasing order</dd>
    <dt>H5_ITER_NATIVE</dt>
    <dd>Fastest available order</dd>
 </dl>
 *
 * <p>TODO Document H5L_iterate_t and H5L_info_t.</p>
 *
 * <p>The H5Lvisit op_data parameter is a user-defined pointer to the
 * data required to process links in the course of the iteration. This
 * pointer is passed back to each step of the iteration in the
 * callback function's op_data parameter. </p>
 *
 * <p>H5Lvisit and H5Ovisit are companion functions: one for examining
 * and operating on links; the other for examining and operating on
 * the objects that those links point to. Both functions ensure that
 * by the time the function completes successfully, every link or
 * object below the specified point in the file has been presented to
 * the application for whatever processing the application
 * requires.</p>
 *
 * @param group_id Identifier of the group at which the recursive
 * iteration begins.
 * @param index_type Iteration type of index.
 * @param order Order in which index is traversed.
 * @param op Callback function passing data regarding the link to the
 * calling application.
 * @param op_data User-defined pointer to data required by the
 * application for its processing of the link
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Lvisit(hid_t grp_id, H5_index_t idx_type, H5_iter_order_t order,
//    H5L_iterate_t op, void *op_data);


/**
 * Recursively visits all links starting from a specified group.
 *
 * <p>H5Lvisit_by_name is a recursive iteration function to visit all
 * links in and below a group in an HDF5 file, thus providing a
 * mechanism for an application to perform a common set of operations
 * across all of those links or a dynamically selected subset. For
 * non-recursive iteration across the members of a group, see
 * H5Literate.</p>
 *
 * <p>The group serving as the root of the iteration is specified by
 * the loc_id / group_name parameter pair. loc_id specifies a file or
 * group; group_name specifies either a group in the file (with an
 * absolute name based in the file's root group) or a group relative
 * to loc_id. If loc_id fully specifies the group that is to serve as
 * the root of the iteration, group_name should be '.' (a dot). (Note
 * that when loc_id fully specifies the the group that is to serve as
 * the root of the iteration, the user may wish to consider using
 * H5Lvisit instead of H5Lvisit_by_name.)</p>
 *
 * <p>Two parameters are used to establish the iteration: index_type
 * and order.</p>
 *
 * <p>index_type specifies the index to be used. If the links have not
 * been indexed by the index type, they will first be sorted by that
 * index then the iteration will begin; if the links have been so
 * indexed, the sorting step will be unnecesary, so the iteration may
 * begin more quickly. Valid values include the following:</p>
 <dl>
    <dt>H5_INDEX_NAME</dt>
    <dd>Alpha-numeric index on name</dd>
    <dt>H5_INDEX_CRT_ORDER</dt>
    <dd>Index on creation order</dd>
 </dl>
 *
 * <p>Note that the index type passed in index_type is a best effort
 * setting. If the application passes in a value indicating iteration
 * in creation order and a group is encountered that was not tracked
 * in creation order, that group will be iterated over in
 * alpha-numeric order by name, or name order. (Name order is the
 * native order used by the HDF5 Library and is always available.)</p>
 *
 * <p>order specifies the order in which objects are to be inspected
 * along the index specified in index_type. Valid values include the
 * following:</p>
 <dl>
    <dt>H5_ITER_INC</dt>
    <dd>Increasing order</dd>
    <dt>H5_ITER_DEC</dt>
    <dd>Decreasing order</dd>
    <dt>H5_ITER_NATIVE</dt>
    <dd>Fastest available order</dd>
 </dl>
 *
 * <p>TODO Document H5L_iterate_t and H5L_info_t.</p>
 *
 * <p>The H5Lvisit_by_name op_data parameter is a user-defined pointer
 * to the data required to process links in the course of the
 * iteration. This pointer is passed back to each step of the
 * iteration in the callback function's op_data parameter.</p>
 *
 * <p>lapl_id is a link access property list. In the general case,
 * when default link access properties are acceptable, this can be
 * passed in as H5P_DEFAULT. An example of a situation that requires a
 * non-default link access property list is when the link is an
 * external link; an external link may require that a link prefix be
 * set in a link access property list (see H5Pset_elink_prefix).</p>
 *
 * <p>H5Lvisit_by_name and H5Ovisit_by_name are companion functions:
 * one for examining and operating on links; the other for examining
 * and operating on the objects that those links point to. Both
 * functions ensure that by the time the function completes
 * successfully, every link or object below the specified point in the
 * file has been presented to the application for whatever processing
 * the application requires. </p>
 *
 * @param loc_id Identifier of a file or group.
 * @param name Name of the group, generally relative to loc_id, that
 * will serve as root of the iteration.
 * @param index_type Iteration type of index.
 * @param order Order in which index is traversed.
 * @param op Callback function passing data regarding the link to the
 * calling application.
 * @param op_data User-defined pointer to data required by the
 * application for its processing of the link.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Lvisit_by_name(hid_t loc_id, const char *group_name,
//    H5_index_t idx_type, H5_iter_order_t order, H5L_iterate_t op,
//    void *op_data, hid_t lapl_id);
%typemap(jtype)  (jobjectArray jname) "String[]"
%typemap(jstype)  (jobjectArray jname) "String[]"
%typemap(jtype)   jintArray jtype "int[]"
%typemap(jstype) jintArray jtype "permafrost.hdf.libhdf.IdentifierType[]"
%typemap(javain, 
	 pre="    int[] tmp$javainput = {-1};",
	 post="      $javainput[0] = permafrost.hdf.libhdf.IdentifierType.swigToEnum(tmp$javainput[0]);") jintArray jtype "tmp$javainput"
%native (H5LJopen_by_idx) hid_t H5LJopen_by_idx (
	hid_t jgrp_id, 
	H5_index_t jidx_type,
	H5_iter_order_t jorder,
	jlong jidx,
	hid_t jlapl_id,
	jobjectArray jname,
	jintArray jtype
);
%{
SWIGEXPORT jlong JNICALL Java_permafrost_hdf_libhdf_LinkLibJNI_H5LJopen_1by_1idx(
	JNIEnv *jenv, 
	jclass jcls, 
	jint jgrp_id, 
	jint jidx_type,
	jint jorder,
	jlong jidx,
	jint jlapl_id,
        jobjectArray jname,
	jintArray jtype
) {
  jint jresult = 0 ;
  hid_t grp_id;
  H5_index_t idx_type = H5_INDEX_UNKNOWN;
  H5_iter_order_t order = H5_ITER_UNKNOWN;
  hsize_t idx = 0;
  hid_t lapl_id;
  JHDFL_iter_t* op_data;
  herr_t result;
  
  (void)jenv;
  (void)jcls;
  grp_id = (hid_t)jgrp_id; 
  idx_type = (H5_index_t) jidx_type;
  order = (H5_iter_order_t) jorder;
  idx = (hsize_t) jidx;
  lapl_id = (hid_t) jlapl_id;


  op_data = malloc(sizeof(JHDFL_iter_t));
  if (!op_data) return(-1);

  op_data->jenv = jenv;
  op_data->lapl_id = lapl_id;
  
  result = (herr_t)H5Literate(grp_id, idx_type, order, &idx, &JHDFL_visitAndOpen, op_data);

  if (result >= 0) {
    jresult = (jint) op_data->idChild;
    (*jenv)->SetObjectArrayElement(jenv, jname, 0, op_data->jstrChild);
    (*jenv)->SetIntArrayRegion(jenv, jtype, 0, 1, (jint*)&(op_data->typeChild));
  } else {
    jresult = (jint)result; 
  }

  free(op_data);

  return jresult;
}
 %}
