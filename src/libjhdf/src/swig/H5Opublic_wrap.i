%module ObjectLib
%include "typemaps.i"
%include "H5types.i"
%include "H5Otypes.i"

%header %{
#include <errno.h>
#include "H5public.h"
#include "H5Opublic.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"

/**
 * Closes an object in an HDF5 file.
 *
 * <p>H5Oclose closes the group, dataset, or named datatype specified
 * by object_id.</p>
 *
 * <p>This function is the companion to H5Oopen, and has the same
 * effect as calling H5Gclose, H5Dclose, or H5Tclose.</p>
 *
 * <p>H5Oclose is not used to close a dataspace, attribute, property
 * list, or file.</p>
 *
 * @param object_id Object identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Oclose(hid_t object_id);

/**
 * Copies an object in an HDF5 file.
 *
 * <p>H5Ocopy copies the group, dataset or named datatype specified by
 * src_name from the file or group specified by src_loc_id to the
 * destination location dst_loc_id.</p>
 *
 * <p>The destination location, as specified in dst_loc_id, may be a
 * group in the current file or a location in a different file. If
 * dst_loc_id is a file identifier, the copy will be placed in that
 * file's root group.</p>
 *
 * <p>The new copy will be created with the name dst_name. dst_name
 * must not pre-exist in the destination location; if dst_name already
 * exists at the location dst_loc_id, H5Ocopy will fail.</p>
 *
 * <p>The new copy of the object is created with the creation property
 * lists specified by ocp_plist_id and lcpl_id.</p>
 *
 * <p>Several flags are available to govern the behavior of
 * H5Ocopy. These flags are set in the creation property list
 * cplist_id with H5Pset_copy_object and
 * H5Pset_create_intermediate_group. All of the available flags are
 * described at H5Pset_copy_object. </p>
 *
 * @param src_loc_id Object identifier indicating the location of the
 * source object to be copied.
 * @param src_name Name of the source object to be copied.
 * @param dst_loc_id Location identifier specifying the destination.
 * @param dst_name Name to be assigned to the new copy.
 * @param ocpypl_id Object copy property list.
 * @param lcpl_id Link creation property list for the new hard link.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Ocopy(hid_t src_loc_id, const char *src_name, 
	       hid_t dst_loc_id, const char *dst_name, 
	       hid_t ocpypl_id, hid_t lcpl_id);

/**
 * Decrements an object reference count.
 *
 * <p>H5Odecr_refcount decrements the hard link reference count for an
 * object. It should be used any time a user-defined link that
 * references an object by address is deleted. In general,
 * H5Oincr_refcount will have been used previously, when the link was
 * created.</p>
 *
 * <p>An object's reference count is the number of hard links in the
 * file that point to that object. See the "Programming Model"
 * section of the "HDF5 Groups" chapter in the HDF5 User's Guide
 * for a more complete discussion of reference counts.</p>
 *
 * <p>If a user application needs to determine an object's reference
 * count, an H5Gget_objinfo call is required; the reference count is
 * returned in the nlink field of the H5G_stat_t struct. </p>
 *
 * <p>Warning:<br/> This function must be used with care! Improper use
 * can lead to inaccessible data, wasted space in the file, or file
 * corruption.</p>
 *
 * @param object_id Object identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Odecr_refcount(hid_t object_id);

/**
 * Retrieves comment for specified object.
 *
 * <p>H5Oget_comment retrieves the comment for the specified object in
 * the buffer comment.</p>
 *
 * <p>The target object is specified by an identifier, object_id.</p>
 *
 * <p>The size in bytes of the comment, including the NULL terminator,
 * is specified in bufsize. If bufsize is unknown, a preliminary
 * H5Oget_comment call with the pointer comment set to NULL will
 * return the size of the comment without the NULL terminator.</p>
 *
 * <p>If bufsize is set to a smaller value than described above, only
 * bufsize bytes of the comment, without a NULL terminator, are
 * returned in comment.</p>
 *
 * <p>If an object does not have a comment, the empty string is
 * returned in comment. </p>
 *
 * @param object_id Identifier for the target object.
 * @param comment (Output) The comment.
 * @param bufsize Anticipated required size of the comment buffer.
 *
 * @return Upon success, returns the number of characters in the
 * comment, not including the NULL terminator, or zero (0) if the
 * object has no comment. The value returned may be larger than
 * bufsize. Otherwise returns a negative value.
 */
%apply signed char OUTPUT[] {char* comment}
ssize_t H5Oget_comment(hid_t obj_id, char *comment, size_t bufsize);
%clear char* comment;

/**
 * Retrieves comment for specified object.
 *
 * <p>H5Oget_comment_by_name retrieves the comment for an object in
 * the buffer comment.</p>
 *
 * <p>The target object is specified by loc_id and name. loc_id can
 * specify any object in the file. name can be one of the
 * following:</p>
 <ul>
    <li>The name of the object relative to loc_id</li>
    <li>An absolute name of the object, starting from /, the file's
    root group</li>
    <li>A dot (.), if loc_id fully specifies the object</li>
 </ul>
 *
 * <p>The size in bytes of the comment, including the NULL terminator,
 * is specified in bufsize. If bufsize is unknown, a preliminary
 * H5Oget_comment_by_name call with the pointer comment set to NULL
 * will return the size of the comment without the NULL
 * terminator.</p>
 *
 * <p>If bufsize is set to a smaller value than described above, only
 * bufsize bytes of the comment, without a NULL terminator, are
 * returned in comment.</p>
 *
 * <p>If an object does not have a comment, the empty string is
 * returned in comment.</p>
 *
 * <p>lapl_id contains a link access property list identifier. A link
 * access propety list can come into play when traversing links to
 * access an object. </p>
 *
 * @param loc_id Identifier of a file, group, dataset, or named datatype.
 * @param name Name of the object whose comment is to be retrieved,
 * specified as a path relative to loc_id.
 * @param comment The comment.
 * @param bufsize Anticipated required size of the comment buffer.
 * @param lapl_id Link access property list identifier.
 *
 * @return Upon success, returns the number of characters in the
 * comment, not including the NULL terminator, or zero (0) if the
 * object has no comment. The value returned may be larger than
 * bufsize. Otherwise returns a negative value.
 */
%apply signed char OUTPUT[] {char* comment}
ssize_t H5Oget_comment_by_name(hid_t loc_id, const char *name, 
			       char *comment, size_t bufsize, 
			       hid_t lapl_id);
%clear char* comment;

/**
 * Retrieves the metadata for an object specified by an identifier.
 *
 * <p>H5Oget_info specifies an object by its identifier, object_id,
 * and retrieves the metadata describing that object in the struct
 * object_info</p>
 *
 * <p>TODO document H5O_info_t type.</p>
 *
 * @param loc_id Identifier for target object.
 * @param oinfo (Output) Buffer in which to return object information.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Oget_info(hid_t loc_id, H5O_info_t *oinfo);

/**
 * Retrieves the metadata for an object, identifying the object by an
 * index position.
 *
 * <p>H5Oget_info_by_idx specifies a location, loc_id; a group name,
 * group_name; an index by which obects in that group are tracked,
 * index_field; the order by which the index is to be traversed,
 * order; and an object's position n within that index and retrieves
 * the metadata describing that object in the struct object_info</p>
 *
 * <p>object_info, in which the object information is returned, is a
 * struct of type H5O_info_t. This struct type is described in the
 * H5Oget_info function entry.</p>
 *
 * <p>If loc_id fully specifies the group in which the object resides,
 * group_name can be a dot (.).</p>
 *
 * <p>The link access property list, lapl_id, is not currently used;
 * it should be passed in as NULL. </p>
 *
 * @param loc_id File or group identifier specifying location of group
 * in which object is located.
 * @param group_name Name of group in which object is located.
 * @param index_field Index or field that determines the order.
 * @param order Order within field or index.
 * @param n Object for which information is to be returned.
 * @param object_info (Output) Buffer in which to return object information.
 * @param lapl_id Link access property list (Not currently used; pass as NULL.)
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long {hsize_t n}
herr_t H5Oget_info_by_idx(hid_t loc_id, const char *group_name,
			  H5_index_t idx_type, H5_iter_order_t order, 
			  hsize_t n, H5O_info_t *oinfo, hid_t lapl_id);
%clear hsize_t n;

/**
 * Retrieves the metadata for an object, identifying the object by
 * location and relative name.
 *
 * <p>H5Oget_info_by_name specifies an object's location and name,
 * loc_id and name, respectively, and retrieves the metadata
 * describing that object in the struct object_info.</p>
 *
 * <p>The link access property list, lapl_id, is not currently used;
 * it should be passed in as NULL.</p>
 *
 * @param loc_id File or group identifier specifying location of group
 * in which object is located.
 * @param name Name of group, relative to loc_id.
 * @param oinfo (Output) Buffer in which to return object information.
 * @param lapl_id Link access property list (Not currently used; pass as NULL.)
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Oget_info_by_name(hid_t loc_id, const char *name, 
			   H5O_info_t *oinfo, hid_t lapl_id);

/**
 * Increments an object reference count.
 *
 * <p>H5Oincr_refcount increments the hard link reference count for an
 * object. It should be used any time a user-defined link that
 * references an object by address is added. When the link is deleted,
 * H5Odecr_refcount should be used.</p>
 *
 * <p>An object's reference count is the number of hard links in the
 * file that point to that object. See the "Programming Model"
 * section of the "HDF5 Groups" chapter in the HDF5 User's Guide
 * for a more complete discussion of reference counts.</p>
 *
 * <p>If a user application needs to determine an object's reference
 * count, an H5Gget_objinfo call is required; the reference count is
 * returned in the nlink field of the H5G_stat_t struct.</p>
 *
 * <p>Warning:<br/> This function must be used with care! Improper use
 * can lead to inaccessible data, wasted space in the file, or file
 * corruption.
 *
 * @param object_id Object identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Oincr_refcount(hid_t object_id);

/**
 * Creates a hard link to an object in an HDF5 file.
 *
 * <p>H5Olink creates a new hard link to an object in an HDF5
 * file.</p>
 *
 * <p>new_loc_id and new_name specify the location and name of the new
 * link while object_id identifies the object that the link points
 * to.</p>
 *
 * <p>H5Olink is designed for two purposes:</p>
 <ol>
    <li>To create the first hard link to an object that has just been
    created with one of the H5*create_anon functions or with
    H5Tcommit_anon.</li>
    <li>To add additional structure to an existing file so that, for
    example, an object can be shared among multiple groups.</li>
 </ol>
 *
 * <p>lcpl and lapl are the link creation and access property lists
 * associated with the new link. </p>
 *
 <p>Example:<br/> Suppose that an application must create the group C
 with the path /A/B01/C but may not know at run time whether the
 groups A and B01 exist. The following code ensures that those groups
 are created if they are missing:</p>
 <code>
 hid_t lcpl_id = H5Pcreate(H5P_LINK_CREATE);   // Creates a link creation
                                               // property list (LCPL).

 int status = H5Pset_create_intermediate_group(lcpl_id, TRUE);
                                               // Sets "create missing
                                               // intermediate groups"
                                               // property in that
                                               // LCPL.

 hid_t gid = H5Gcreate_anon(file_id, H5P_DEFAULT, H5P_DEFAULT);
                                               // Creates a group
                                               // without linking it
                                               // into the file
                                               // structure.

 status = H5Olink(obj_id, file_id, "/A/B01/C", lcpl_id, H5P_DEFAULT);
                                               // Links group into
                                               // file structure.
 </code>
 *
 * <p>Note that unless the object is intended to be temporary, the
 * H5Olink call is mandatory if an object created with one of the
 * H5*create_anon functions (or with H5Tcommit_anon) is to be retained
 * in the file; without an H5Olink call, the object will not be linked
 * into the HDF5 file structure and will be deleted when the file is
 * closed. </p>
 *
 * @param obj_id  Object to be linked.
 * @param new_loc_id File or group identifier specifying location at
 * which object is to be linked.
 * @param new_name Name of link to be created, relative to
 * new_loc_id.
 * @param lcpl_id Link creation property list identifier.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Olink(hid_t obj_id, hid_t new_loc_id, const char *new_name, 
	       hid_t lcpl_id, hid_t lapl_id);

/**
 * Opens an object in an HDF5 file by location identifier and path name.
 *
 * <p>H5Oopen opens a group, dataset, or named datatype specified by a
 * location, loc_id, and a path name, name, in an HDF5 file.</p>
 *
 * <p>This function opens the object in the same manner as H5Gopen,
 * H5Topen, and H5Dopen. However, H5Oopen does not require the type of
 * object to be known beforehand. This can be useful with user-defined
 * links, for instance, when only a path may be known. H5Oopen cannot
 * be used to open a dataspace, attribute, property list, or file.</p>
 *
 * <p>Once an object of unknown type has been opened with H5Oopen, the
 * type of that object can be determined by means of an H5Iget_type
 * call.</p>
 *
 * <p>loc_id can be either a file or group identifier. name must be
 * the path to that object relative to loc_id.</p>
 *
 * <p>lapl_id is the link access property list associated with the
 * link pointing to the object. If default link access properties are
 * appropriate, this can be passed in as H5P_DEFAULT.</p>
 *
 * <p>When it is no longer needed, the opened object should be closed
 * with H5Oclose, H5Gclose, H5Tclose, or H5Dclose.</p>
 *
 * @param loc_id File or group identifier.
 * @param name Path to the object, relative to loc_id.
 * @param lapl_id Access property list identifier for the link
 * pointing to the object.
 *
 * @return Returns an object identifier for the opened object if
 * successful; otherwise returns a negative value.
 */
hid_t H5Oopen(hid_t loc_id, const char *name, hid_t lapl_id);

/**
 * Opens an object using its address within an HDF5 file.
 *
 * <p>H5Oopen_by_addr opens a group, dataset, or named datatype using
 * its address within an HDF5 file, addr. The resulting opened object
 * is identical to an object opened with H5Oopen and should be closed
 * with H5Oclose or an object-type-specific closing function (such as
 * H5Gclose) when no longer needed.</p>
 *
 * <p>loc_id can be either the file identifier or a group identifier
 * in the file. In either case, the HDF5 Library uses the identifier
 * only to identify the file.</p>
 *
 * <p>The object's address within the file, addr, is the byte offset
 * of the first byte of the object header from the beginning of the
 * HDF5 file space, i.e., from the beginning of the super block (see
 * the "HDF5 Storage Model" section of the "The HDF5 Data Model and
 * File Structure" chapter of the HDF5 User' Guide).</p>
 *
 * <p>addr can be obtained via either of two function
 * calls. H5Gget_objinfo returns the object's address in the objno
 * field of the H5G_stat_t struct; H5Lget_linkinfo returns the address
 * in the address field of the H5L_linkinfo_t struct.</p>
 *
 * <p>Warning:<br/> This function must be used with care! Improper use
 * can lead to inaccessible data, wasted space in the file, or file
 * corruption.</p>
 *
 * <p>This function is dangerous if called on an invalid address. The
 * risk can be safely overcome by retrieving the object address with
 * H5Gget_objinfo or H5Lget_linkinfo immediately before calling
 * H5Oopen_by_addr. The immediacy of the operation can be important;
 * if time has elapsed and the object has been deleted from the file,
 * the address will be invalid and file corruption can result.</p>
 *
 * <p>The address of the HDF5 file on a physical device has no effect
 * on H5Oopen_by_addr, nor does the use of any file driver. As stated
 * above, the object address is its offset within the HDF5 file;
 * HDF5's file drivers will transparently map this to an address on a
 * storage device. </p>
 *
 * @param loc_id File or group identifier.
 * @param addr Object's address in the file.
 *
 * @return Returns an object identifier for the opened object if
 * successful; otherwise returns a negative value.
 */
hid_t H5Oopen_by_addr(hid_t loc_id, haddr_t addr);

/**
 * Open the nth object in a group.
 *
 * <p>TODO add missing doc for H5O_open_by_idx</p>
 *
 * @param loc_id File or group identifier specifying location of group
 * in which object is located.
 * @param group_name Name of group in which object is located.
 * @param index_field Index or field that determines the order.
 * @param order Order within field or index.
 * @param n Object to open.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long {hsize_t n}
hid_t H5Oopen_by_idx(hid_t loc_id, const char *group_name,
		     H5_index_t idx_type, H5_iter_order_t order, hsize_t n, 
		     hid_t lapl_id);
%clear hsize_t n;

/**
 * Sets comment for specified object.
 * <p>H5Oset_comment sets the comment for the specified object to the
 * contents of comment. Any previously existing comment is
 * overwritten.</p>
 *
 * <p>The target object is specified by an identifier, object_id.</p>
 *
 * <p>If comment is the empty string or a null pointer, any existing
 * comment message is removed from the object.</p>
 *
 * <p>Comments should be relatively short, null-terminated, ASCII
 * strings.</p>
 *
 * <p>Comments can be attached to any object that has an object
 * header, e.g., datasets, groups, and named datatypes, but not
 * symbolic links. </p>
 *
 * @param obj_id Identifier of the target object.
 * @param comment The new comment.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Oset_comment(hid_t obj_id, const char *comment);

/**
 * Sets comment for specified object.
 *
 * <p>H5Oset_comment_by_name sets the comment for the specified object
 * to the contents of comment. Any previously existing comment is
 * overwritten.</p>
 *
 * <p>The target object is specified by loc_id and name. loc_id can
 * specify any object in the file. name can be one of the
 * following:</p>
 <ol>
    <li>The name of the object relative to loc_id</li>
    <li>An absolute name of the object, starting from /, the file's
    root group</li>
    <li>A dot (.), if loc_id fully specifies the object</li>
 </ol>
 *
 * <p>If comment is the empty string or a null pointer, any existing
 * comment message is removed from the object.</p>
 *
 * <p>Comments should be relatively short, null-terminated, ASCII
 * strings.</p>
 *
 * <p>Comments can be attached to any object that has an object
 * header, e.g., datasets, groups, and named datatypes, but not
 * symbolic links.</p>
 *
 * <p>lapl_id contains a link access property list identifier. A link
 * access propety list can come into play when traversing links to
 * access an object. </p>
 *
 * @param loc_id Identifier of a file, group, dataset, or named datatype.
 * @param name Name of the object whose comment is to be set or reset,
 * specified as a path relative to loc_id.
 * @param comment The new comment.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Oset_comment_by_name(hid_t loc_id, const char *name, 
			      const char *comment, hid_t lapl_id);

/**
 * Recursively visits all objects starting from a specified object.
 *
 * <p>H5Ovisit is a recursive iteration function to visit the object
 * object_id and, if object_id is a group, all objects in and below it
 * in an HDF5 file, thus providing a mechanism for an application to
 * perform a common set of operations across all of those objects or a
 * dynamically selected subset. For non-recursive iteration across the
 * members of a group, see H5Literate.</p>
 *
 * <p>The group serving as the root of the iteration is specified by
 * its group identifier, group_id.</p>
 *
 * <p>Two parameters are used to establish the iteration: index_type
 * and order.</p>
 *
 * <p>index_type specifies the index to be used. If the links in a
 * group have not been indexed by the index type, they will first be
 * sorted by that index then the iteration will begin; if the links
 * have been so indexed, the sorting step will be unnecesary, so the
 * iteration may begin more quickly. Valid values include the
 * following:</p>
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
 * <p>TODO document H5O_iterate_t and H5O_info_t.</p>
 *
 * <p>The H5Ovisit op_data parameter is a user-defined pointer to the
 * data required to process objects in the course of the
 * iteration. This pointer is passed back to each step of the
 * iteration in the callback function's op_data parameter.</p>
 *
 * <p>H5Lvisit and H5Ovisit are companion functions: one for examining
 * and operating on links; the other for examining and operating on
 * the objects that those links point to. Both functions ensure that
 * by the time the function completes successfully, every link or
 * object below the specified point in the file has been presented to
 * the application for whatever processing the application
 * requires. </p>
 *
 * @param object_id Identifier of the object at which the recursive
 * iteration begins.
 * @param index_type Type of index.
 * @param order Order in which index is traversed.
 * @param op Callback function passing data regarding the object to
 * the calling application.
 * @param op_data User-defined pointer to data required by the
 * application for its processing of the object.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Ovisit(hid_t obj_id, H5_index_t idx_type, 
//		H5_iter_order_t order, H5O_iterate_t op, void *op_data);

/**
 * Recursively visits all objects starting from a specified object.
 *
 * <p>H5Ovisit_by_name is a recursive iteration function to visit the
 * object specified by the loc_id / object_name parameter pair and, if
 * that object is a group, all objects in and below it in an HDF5
 * file, thus providing a mechanism for an application to perform a
 * common set of operations across all of those objects or a
 * dynamically selected subset. For non-recursive iteration across the
 * members of a group, see H5Literate.</p>
 *
 * <p>The object serving as the root of the iteration is specified by
 * the loc_id / object_name parameter pair. loc_id specifies a file or
 * an object in a file; object_name specifies either an object in the
 * file (with an absolute name based in the file's root group) or an
 * object name relative to loc_id. If loc_id fully specifies the
 * object that is to serve as the root of the iteration, object_name
 * should be '.' (a dot). (Note that when loc_id fully specifies the
 * the object that is to serve as the root of the iteration, the user
 * may wish to consider using H5Ovisit instead of
 * H5Ovisit_by_name.)</p>
 *
 * <p>Two parameters are used to establish the iteration: index_type
 * and order.</p>
 *
 * <p>index_type specifies the index to be used. If the links in
 * a group have not been indexed by the index type, they will first be
 * sorted by that index then the iteration will begin; if the links
 * have been so indexed, the sorting step will be unnecesary, so the
 * iteration may begin more quickly. Valid values include the
 * following:</p>
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
 * <p>The H5Ovisit_by_name op_data parameter is a user-defined pointer
 * to the data required to process objects in the course of the
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
 * @param object_name Name of the object, relative to
 * loc_id, that will serve as root of the iteration.
 * @param index_type Type of index.
 * @param order Order in which index is traversed.
 * @param op Callback function passing data regarding the object to
 * the calling application.
 * @param op_data User-defined pointer to data required by the
 * application for its processing of the object.
 * @param lapl_id Link access property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Ovisit_by_name(hid_t loc_id, const char *obj_name,  
//			H5_index_t idx_type, H5_iter_order_t order,
//			H5O_iterate_t op, void *op_data, hid_t lapl_id);


