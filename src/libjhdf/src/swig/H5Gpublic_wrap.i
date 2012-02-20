%module GroupLib
%include "typemaps.i"
%include "various.i"
%import "H5types.i"
%import "H5Otypes.i"
%include "H5Gtypes.i"

%header %{
#include "H5Gpublic.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"


//typedef herr_t (*H5G_iterate_t)(hid_t group, const char *name,
//				void *op_data);

herr_t H5Gclose(hid_t group_id);

/**
 * Creates a new empty group and links it to a location in the file.
 *
 * <p>H5Gcreate1 creates a new group with the specified name at the
 * specified location, loc_id. The location is identified by a file or
 * group identifier. The name, name, must not already be taken by some
 * other object and all parent groups must already exist.</p>
 *
 * <p>name can be a relative path based at loc_id or an absolute path
 * from the root of the file. If any of the groups specified in that
 * path do not already exist, the group must be created with
 * H5Gcreate_anon and linked into the file structure with H5Llink.</p>
 *
 * <p>The length of a group name, or of the name of any object within
 * a group, is not limited.</p>
 *
 * <p>size_hint is a hint for the number of bytes to reserve to store
 * the names which will be eventually added to the new group. Passing
 * a value of zero for size_hint is usually adequate since the library
 * is able to dynamically resize the name heap, but a correct hint may
 * result in better performance. If a non-positive value is supplied
 * for size_hint, then a default size is chosen.</p>
 *
 * <p>The return value is a group identifier for the open group. This
 * group identifier should be closed by calling H5Gclose when it is no
 * longer needed.</p>
 *
 * <p>See H5Gcreate_anon for a discussion of the differences between
 * H5Gcreate1 and H5Gcreate_anon. </p>
 *
 * @param loc_id File or group identifier.
 * @param name Absolute or relative name of the new group.
 * @param size_hint Optional parameter indicating the number of bytes
 * to reserve for the names that will appear in the group. A
 * conservative estimate could result in multiple system-level I/O
 * requests to read the group name heap; a liberal estimate could
 * result in a single large I/O request even when the group has just a
 * few names. HDF5 stores each name with a null terminator.
 *
 * @return Returns a valid group identifier for the open group if
 * successful; otherwise returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5Gcreate1 "@Deprecated\n   public"
hid_t H5Gcreate1(hid_t loc_id, const char *name, size_t size_hint);

/**
 * Creates a new empty group and links it into the file.
 *
 * <p>H5Gcreate2 creates a new group named name at the location
 * specified by loc_id with the group creation and access properties
 * spceified in gcpl_id and gapl_id, respectively.</p>
 *
 * <p>loc_id may be a file identifier, or a group identifier within
 * that file. name may be either an absolute path in the file or a
 * relative path from loc_id naming the dataset.</p>
 *
 * <p>The link creation property list, lcpl_id, governs creation of
 * the link(s) by which the new dataset is accessed and the creation
 * of any intermediate groups that may be missing.</p>
 *
 * <p>To conserve and release resources, the group should be closed
 * when access is no longer required. </p>
 *
 * @param loc_id File or group identifier.
 * @param name Absolute or relative name of the new group.
 * @param lcpl_id Property list for link creation.
 * @param gcpl_id Property list for group creation.
 * @param gapl_id Property list for group access.
 * 
 * @return Returns a group identifier if successful; otherwise returns
 * a negative value.
 */
hid_t H5Gcreate2(hid_t loc_id, const char *name, hid_t lcpl_id, hid_t gcpl_id, hid_t gapl_id);


/**
 * Creates a new empty group without linking it into the file structure.
 *
 * <p>H5Gcreate_anon creates a new empty group in the file specified
 * by loc_id. With default settings, H5Gcreate_anon provides similar
 * functionality to that provided by H5Gcreate, with the differences
 * described below.</p>
 *
 * <p>The new group's creation and access properties are specified in
 * gcpl_id and gapl_id, respectively.</p>
 *
 * <p>H5Gcreate_anon returns a new group identifier. This identifier
 * must be linked into the HDF5 file structure with H5Llink or it will
 * be deleted from the file when the file is closed.</p>
 *
 * <p>The differences between this function and H5Gcreate1 are as follows:</p>
 <ul>
    <li>H5Gcreate1 does not provide for the use of custom property
    lists; H5Gcreate1 always uses default properties.</li>
    <li>H5Gcreate_anon neither provides the new group's name nor links
    it into the HDF5 file structure; those actions must be performed
    separately through a call to H5Llink, which offers greater control
    over linking.</li>
    <li>H5Gcreate_anon does not directly provide a hint mechanism for
    the group's heap size. Comparable information can be included in
    the group creation property list gcpl_id through a
    H5Pset_local_heap_size_hint call. </li>
 </ul>
 *
 * @param loc_id File or group identifier specifying the file in which
 * the new group is to be created
 * @param gcpl_id Group creation property list
 * identifier. (H5P_DEFAULT for the default property list)
 * @param gapl_id Group access property list identifier. (H5P_DEFAULT
 * for the default property list)
 *
 * @return Returns a new group identifier if successful; otherwise
 * returns a negative value.
 */
hid_t H5Gcreate_anon(hid_t loc_id, hid_t gcpl_id, hid_t gapl_id);

/**
 * Retrieves comment for specified object.
 *
 * <p>H5Gget_comment retrieves the comment for the the object
 * specified by loc_id and name. The comment is returned in the buffer
 * comment.</p>
 *
 * <p>loc_id can specify any object in the file. name can be one of
 * the following:</p>
 <ul>
    <li>The name of the object relative to loc_id</li>
    <li>An absolute name of the object, starting from /, the file's
    root group</li>
    <li>A dot (.), if loc_id fully specifies the object</li>
 </ul>
 * <p>At most bufsize characters, including a null terminator, are
 * returned in comment. The returned value is not null terminated if
 * the comment is longer than the supplied buffer. If the size of the
 * comment is unknown, a preliminary H5Gget_comment call will return
 * the size of the comment, including space for the null
 * terminator.</p>
 *
 * <p>If an object does not have a comment, the empty string is
 * returned in comment. </p>
 *
 * @param loc_id Identifier of the file, group, dataset, or named datatype.
 * @param name Name of the object in loc_id whose comment is to be
 * retrieved. name must be '.' (dot) if loc_id fully specifies the
 * object for which the associated comment is to be retrieved.
 * @param bufsize Anticipated required size of the comment buffer.
 * @param comment (Output) The comment.
 *
 * @return Returns the number of characters in the comment, counting
 * the null terminator, if successful; the value returned may be
 * larger than bufsize. Otherwise returns a negative value.
 * @deprecated
 */
%apply signed char OUTPUT[] {char* buf}
%javamethodmodifiers H5Gget_comment "@Deprecated\n   public"
int H5Gget_comment(hid_t loc_id, const char *name, size_t bufsize, char *buf/*out*/);
%clear char* buf;

/**
 * Gets a group creation property list identifier.
 *
 * <p>H5Gget_create_plist returns an identifier for the group creation
 * property list associated with the group specified by group_id.</p>
 *
 * <p>The creation property list identifier should be released with
 * H5Pclose.</p>
 *
 * @param group_id Identifier of the group.
 *
 * @return Returns an identifier for the group's creation property
 * list if successful. Otherwise returns a negative value.
 */
hid_t H5Gget_create_plist(hid_t group_id);

/**
 * Retrieves information about a group.
 *
 * <p>H5Gget_info retrieves information about the group specified by
 * group_id. The information is returned in the ginfo struct.</p>
 *
 * <p>ginfo is an H5G_info_t struct and is defined (in
 * H5Gpublic.h) as follows:</p>
 <dl>
    <dt>H5G_storage_type_t storage_type</dt>
    <dd>Type of storage for links in group
    <dl>
       <dt>H5G_STORAGE_TYPE_COMPACT</dt>
       <dd>Compact storage</dd>
       <dt>H5G_STORAGE_TYPE_DENSE</dt>
       <dd>Indexed storage</dd>
       <dt>H5G_STORAGE_TYPE_SYMBOL_TABLE</dt>
       <dd>Symbol tables, the original HDF5 structure.</dd>
    </dl>
    <dt>hsize_t nlinks</dt>
    <dd>Number of links in group.</dd>
    <dt>int64_t max_corder</dt>
    <dd>Current maximum creation order value for group</dd>
 </dl>
 *
 * @param loc_id Group identifier.
 * @param ginfo (Output) Struct in which group information is returned.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Gget_info(hid_t loc_id, H5G_info_t *ginfo);

%apply long long {hsize_t n}
/**
 * Retrieves information about a group, according to the group's
 * position within an index.
 *
 * <p>H5Gget_info_by_idx retrieves the same imformation about a group
 * as retrieved by the function H5Gget_info, immediately above, but
 * the means of identifying the group differs; the group is identified
 * by position in an index rather than by name.</p>
 *
 * <p>loc_id and group_name specify the group containing the group for
 * which information is sought. The groups in group_name are indexed
 * by index_type; the group for which information is retrieved is
 * identified in that index by index order, order, and index position,
 * n.</p>
 *
 * <p>If loc_id specifies the group containing the group for which
 * information is queried, group_name can be a dot (.).</p>
 *
 * <p>Valid values for index_type are as follows:</p>
 <dl>
    <dt>H5_INDEX_NAME</dt>
    <dd>An alpha-numeric index by group name</dd>
    <dt>H5_INDEX_CRT_ORDER</dt>
    <dd>An index by creation order</dd>
 </dl>
 * <p>The order in which the index is to be examined, as specified by
 * order, can be one of the following:</p>
 <dl>
    <dt>H5_ITER_INC</dt>
    <dd>The count is from beginning of the index, i.e., top-down.</dd>
    <dt>H5_ITER_DEC</dt>
    <dd>The count is from the end of the index, i.e., bottom-up.</dd>
    <dt>H5_ITER_NATIVE</dt>
    <dd>HDF5 counts through the index in the fastest-available
    order. No information is provided as to the order, but HDF5
    ensures that no element in the index will be overlooked.</dd>
 </dl>
 *
 * @param loc_id File or group identifier.
 * @param group_name Name of group containing group for which
 * information is to be retrieved.
 * @param index_type Index type.
 * @param order Order of iteration in the index.
 * @param n Position in the index of the group for which information
 * is retrieved.
 * @param group_info (Output) Struct in which group information is returned.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Gget_info_by_idx(hid_t loc_id, const char *group_name,
			  H5_index_t idx_type, H5_iter_order_t order,
			  hsize_t n, H5G_info_t *ginfo, hid_t lapl_id);
%clear hsize_t n;

/**
 * Retrieves information about a group specified by path name.
 *
 * <p>H5Gget_info_by_name retrieves information about the group
 * name located in the file or group specified by loc_id. The
 * information is returned in the ginfo struct.</p>
 *
 * <p>If loc_id specifies the group for which information is queried,
 * group_name can be a dot (.). </p>
 *
 * @param loc_id File or group identifier.
 * @param name Name of group for which information is to be retrieved.
 * @param ginfo (Output) Struct in which group information is returned.
 * @param lapl_id Link access property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Gget_info_by_name(hid_t loc_id, const char *name, 
			   H5G_info_t *ginfo, hid_t lapl_id);

/**
 * Returns the name of the object that the symbolic link points to.
 *
 * <p>H5Gget_linkval returns size characters of the name of the object
 * that the symbolic link name points to.</p>
 *
 * <p>The parameter loc_id is a file or group identifier.</p>
 *
 * <p>The parameter name must be a symbolic link pointing to the
 * desired object and must be defined relative to loc_id.</p>
 *
 * <p>If size is smaller than the size of the returned object name,
 * then the name stored in the buffer buf will not be null
 * terminated.</p>
 *
 * <p>This function fails if name is not a symbolic link. The presence
 * of a symbolic link can be tested by passing zero for size and NULL
 * for value.</p>
 *
 * <p>This function should be used only after H5Lget_info (or the
 * deprecated function H5Gget_objinfo) has been called to verify that
 * name is a symbolic link. </p></p>
 *
 * @param loc_id Identifier of the file or group.
 * @param name Symbolic link to the object whose name is to be returned.
 * @param size Maximum number of characters of value to be returned.
 * @param buf (Output) A buffer to hold the name of the object being sought.
 *
 * @return Returns a non-negative value, with the link value in value,
 * if successful. Otherwise returns a negative value.
 * @deprecated
 */
%apply signed char OUTPUT[] {char* buf};
%javamethodmodifiers H5Gget_linkval "@Deprecated\n   public"
herr_t H5Gget_linkval(hid_t loc_id, const char *name, \
		      size_t size, char *buf);

/**
 * Returns number of objects in the group specified by its identifier.
 *
 * <p>H5Gget_num_objs returns number of objects in a group. Group is
 * specified by its identifier loc_id. If a file identifier is passed
 * in, then the number of objects in the root group is returned.</p>
 *
 * @param loc_id Identifier of the group or the file.
 * @param num_objs (Output) Number of objects in the group.
 *
 * @return Returns positive value if successful; otherwise returns a
 * negative value.
 * @deprecated
 */
%apply long long* OUTPUT {hsize_t* num_objs};
%javamethodmodifiers H5Gget_num_objs "@Deprecated\n   public"
herr_t H5Gget_num_objs(hid_t loc_id, hsize_t* num_objs);

/**
 * Returns information about an object.
 *
 * <p>H5Gget_objinfo returns information about the specified object
 * through the statbuf argument.</p>
 *
 * <p>A file or group identifier, loc_id, and an object name, name,
 * relative to loc_id, are commonly used to specify the
 * object. However, if the object identifier is already known to the
 * application, an alternative approach is to use that identifier,
 * obj_id, in place of loc_id, and a dot (.) in place of name. Thus,
 * the alternative versions of the first portion of an H5Gget_objinfo
 * call would be as follows:<br/>
 <code>
 H5Gget_objinfo (loc_id name  ...)
 H5Gget_objinfo (obj_id .     ...)
 </code></p>
 * <p>If the object is a symbolic link and follow_link is zero (0),
 * then the information returned describes the link itself; otherwise
 * the link is followed and the information returned describes the
 * object to which the link points. If follow_link is non-zero but the
 * final symbolic link is dangling (does not point to anything), then
 * an error is returned. The statbuf fields are undefined for an
 * error. The existence of an object can be tested by calling this
 * function with a null statbuf.</p>
 *
 * <p>H5Gget_objinfo fills in the following data structure (defined in
 * H5Gpublic.h):<br/>
 <code>
 typedef struct H5G_stat_t {
    unsigned long fileno;
    haddr_t objno;
    unsigned nlink;
    H5G_obj_t type;
    time_t mtime; 
    size_t linklen;
    H5O_stat_t ohdr;
 } H5G_stat_t
 </code>           
 * where H5O_stat_t (defined in H5Opublic.h) is:<br/>
 <code>
 typedef struct H5O_stat_t {
    hsize_t size;
    hsize_t free;
    unsigned nmesgs;
    unsigned nchunks;
 } H5O_stat_t
 </code></p>
 *
 * <p>The fileno and objno fields contain four values which uniquely
 * identify an object among those HDF5 files which are open: if all
 * four values are the same between two objects, then the two objects
 * are the same (provided both files are still open).</p>
 <ul>
    <li>Note that if a file is closed and re-opened, the value in
    fileno will change.</li>
    <li>If a VFL driver either does not or cannot detect that two
    H5Fopen calls referencing the same file actually open the same
    file, each will get a different fileno. </li>
 </ul>
 * <p>The nlink field is the number of hard links to the object or
 * zero when information is being returned about a symbolic link
 * (symbolic links do not have hard links but all other objects always
 * have at least one).</p>
 *
 * <p>The type field contains the type of the object, one of
 * H5G_GROUP, H5G_DATASET, H5G_LINK, or H5G_TYPE.</p>
 *
 * <p>The mtime field contains the modification time.</p>
 *
 * <p>If information is being returned about a symbolic link then
 * linklen will be the length of the link value (the name of the
 * pointed-to object with the null terminator); otherwise linklen will
 * be zero.</p>
 *
 * <p>The fields in the H5O_stat_t struct contain information about the object header for the object queried:</p>
 <dl>
    <dt>size</dt>
    <dd>The total size of all the object header information in the
    file (for all chunks).</dd>
    <dt>free</dt>
    <dd>The size of unused space in the object header.</dd>
    <dt>nmesgs</dt>
    <dd>The number of object header messages.</dd>
    <dt>nchunks</dt>
    <dd>The number of chunks the object header is broken up into.</dd>
 </dl>
 * <p>Other fields may be added to this structure in the future.</p>
 * <p>Note:<br/>
 * Some systems will be able to record the time accurately but unable
 * to retrieve the correct time; such systems (e.g., Irix64) will
 * report an mtime value of 0 (zero).</p>
 *
 * @param loc_id File or group identifier.
 * @param name Name of the object for which status is being sought. If
 * the preceding parameter is the object's direct identifier, i.e.,
 * the obj_id, this parameter should be a dot (.).
 * @param follow_link Link flag.
 * @param statbuf (Output) Buffer in which to return information about
 * the object.
 *
 * @return Returns a non-negative value if successful, with the fields
 * of statbuf (if non-null) initialized. Otherwise returns a negative
 * value.
 * @deprecated
 */
%javamethodmodifiers H5Gget_objinfo "@Deprecated\n   public"
herr_t H5Gget_objinfo(hid_t loc_id, const char *name,
		      hbool_t follow_link, H5G_stat_t* statbuf);

/**
 * Returns a name of an object specified by an index.
 *
 * <p>H5Gget_objname_by_idx returns a name of the object specified by
 * the index idx in the group loc_id.</p>
 *
 * <p>The group is specified by a group identifier loc_id. If
 * preferred, a file identifier may be passed in loc_id; that file's
 * root group will be assumed.</p>
 *
 * <p>idx is the transient index used to iterate through the objects
 * in the group. The value of idx is any nonnegative number less than
 * the total number of objects in the group, which is returned by the
 * function H5Gget_num_objs. Note that this is a transient index; an
 * object may have a different index each time a group is opened.</p>
 *
 * <p>The object name is returned in the user-specified buffer
 * name.</p>
 *
 * <p>If the size of the provided buffer name is less or equal the
 * actual object name length, the object name is truncated to max_size
 * - 1 characters.</p>
 *
 * <p>Note that if the size of the object's name is unkown, a
 * preliminary call to H5Gget_objname_by_idx with name set to NULL
 * will return the length of the object's name. A second call to
 * H5Gget_objname_by_idx can then be used to retrieve the actual
 * name.</p>
 *
 * @param loc_id Group or file identifier.
 * @param idx Transient index identifying object.
 * @param name (Output) Pointer to user-provided buffer the object name.
 * @param size Name length.
 *
 * @return Returns the size of the object name if successful, or 0 if
 * no name is associated with the group identifier. Otherwise returns
 * a negative value.
 * @deprecated
 */
%apply long long {hsize_t idx}
%apply char* BYTE {char* name};
%javamethodmodifiers H5Gget_objnam_by_idx "@Deprecated\n   public"
ssize_t H5Gget_objname_by_idx(hid_t loc_id, hsize_t idx, char* name, size_t size);
%clear char* name;
%clear hsize_t idx;

/**
 * Returns the type of an object specified by an index.
 *
 * <p>H5Gget_objtype_by_idx returns the type of the object specified
 * by the index idx in the group loc_id.</p>
 *
 * <p>The group is specified by a group identifier loc_id. If
 * preferred, a file identifier may be passed in loc_id; that file's
 * root group will be assumed.</p>
 *
 * <p>idx is the transient index used to iterate through the objects
 * in the group. This parameter is described in more detail in the
 * discussion of H5Gget_objname_by_idx.</p>
 *
 * <p>The object type is returned as the function return value:</p>
 <dl>
    <dt>H5G_LINK</dt>
    <dd>Object is a symbolic link.</dd>
    <dt>H5G_GROUP</dt>
    <dd>Object is a group.</dd>
    <dt>H5G_DATASET</dt>
    <dd>Object is a dataset.</dd>
    <dt>H5G_TYPE</dt>
    <dd>Object is a named datatype.</dd>
 </dl>
 *
 * @param loc_id Group or file identifier.
 * @param idx Transient index identifying object. 
 * 
 * @return Returns the type of the object if successful. Otherwise
 * returns a negative value.
 * @deprecated
 */
%apply long long {hsize_t idx}
%javamethodmodifiers H5Gget_objtype_by_idx "@Deprecated\n   public"
H5G_obj_t H5Gget_objtype_by_idx(hid_t loc_id, hsize_t idx);

%clear hsize_t idx;

//%apply int* INOUT {int* idx}
//herr_t H5Giterate(hid_t loc_id, const char *name, int *idx,
//			  H5G_iterate_t op, void *op_data);

/**
 * Creates a link of the specified type from new_name to current_name.
 *
 * <p>H5Glink creates a new name for an object that has some current
 * name, possibly one of many names it currently has.</p>
 *
 * <p>If link_type is H5G_LINK_HARD, then cur_name must specify
 * the name of an existing object and both names are interpreted
 * relative to loc_id, which is either a file identifier or a group
 * identifier.</p>
 *
 * <p>If link_type is H5G_LINK_SOFT, then current_name can be anything
 * and is interpreted at lookup time relative to the group which
 * contains the final component of new_name. For instance, if
 * current_name is ./foo, new_name is ./x/y/bar, and a request is made
 * for ./x/y/bar, then the actual object looked up is
 * ./x/y/./foo. </p>
 *
 * @param loc_id File or group identifier.
 * @param type Link type. Possible values are H5G_LINK_HARD and
 * H5G_LINK_SOFT.
 * @param cur_name Name of the existing object if link is a hard
 * link. Can be anything for the soft link.
 * @param new_name New name for the object.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5Glink "@Deprecated\n   public"
herr_t H5Glink(hid_t loc_id, H5G_link_t type, 
	       const char* cur_name, const char* new_name);

/**
 * Creates a link of the specified type from new_name to current_name.
 *
 * <p>H5Glink2 creates a new name for an object that has some current
 * name, possibly one of many names it currently has.</p>
 *
 * <p>If link_type is H5G_LINK_HARD, then cur_name must specify
 * the name of an existing object. In this case, cur_name and
 * new_name are interpreted relative to src_loc and new_loc,
 * respectively, which are either file or group identifiers.</p>
 *
 * <p>If type is H5G_LINK_SOFT, then current_name can be anything
 * and is interpreted at lookup time relative to the group which
 * contains the final component of new_name. For instance, if
 * current_name is ./foo, new_name is ./x/y/bar, and a request is made
 * for ./x/y/bar, then the actual object looked up is
 * ./x/y/./foo. </p>
 *
 * @param src_loc The file or group identifier for the original object.
 * @param cur_name Name of the existing object if link is a hard
 * link. Can be anything for the soft link.
 * @param type Link type. Possible values are H5G_LINK_HARD and
 * H5G_LINK_SOFT.
 * @param dst_loc The file or group identifier for the new link.
 * @param new_name New name for the object.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5Glink2 "@Deprecated\n   public"
herr_t H5Glink2(hid_t src_loc, const char *cur_name, H5G_link_t type,
		hid_t dst_loc, const char *new_name);

/**
 * Renames an object within an HDF5 file.
 *
 * <p>H5Gmove renames an object within an HDF5 file. The original
 * name, src_name, is unlinked from the group graph and the new name,
 * dst_name, is inserted as an atomic operation. Both names are
 * interpreted relative to loc_id, which is either a file or a group
 * identifier.</p>
 *
 * <p>Warning:</br> Exercise care in moving groups as it is possible
 * to render data in a file inaccessible with H5Gmove. See The Group
 * Interface in the HDF5 User's Guide.</p>
 *
 * @param src_loc File or group identifier.
 * @param src_name Object's original name.
 * @param dst_name Object's new name.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5Gmove "@Deprecated\n   public"
herr_t H5Gmove(hid_t src_loc, const char *src_name, const char *dst_name);

/**
 * Renames an object within an HDF5 file.
 *
 * <p>H5Gmove2 renames an object within an HDF5 file. The original
 * name, src_name, is unlinked from the group graph and the new name,
 * dst_name, is inserted as an atomic operation.</p>
 *
 * <p>src_name and dst_name are interpreted relative to src_name and
 * dst_name, respectively, which are either file or group
 * identifiers. </p>
 *
 * <p>Warning:</br> Exercise care in moving groups as it is possible
 * to render data in a file inaccessible with H5Gmove. See The Group
 * Interface in the HDF5 User's Guide.</p>
 *
 * @param src_loc Original file or group identifier.
 * @param src_name Object's original name.
 * @param dst_loc Destination file or group identifier.
 * @param dst_name Object's new name.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5Gmove2 "@Deprecated\n   public"
herr_t H5Gmove2(hid_t src_loc, const char *src_name, 
		hid_t dst_loc, const char *dst_name);


/**
 * Opens an existing group for modification and returns a group
 * identifier for that group.
 *
 * <p>H5Gopen1 opens an existing group with the specified name at the
 * specified location, loc_id.</p>
 *
 * <p>The location is identified by a file or group identifier</p>
 *
 * <p>H5Gopen1 returns a group identifier for the group that was
 * opened. This group identifier should be released by calling
 * H5Gclose when it is no longer needed. </p>
 *
 * @param loc_id File or group identifier within which group is to be open.
 * @param name Name of group to open.
 *
 * @return Returns a valid group identifier if successful; otherwise
 * returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5Gopen1 "@Deprecated\n   public"
hid_t H5Gopen1(hid_t loc_id, const char *name);

/**
 * Opens an existing group with a group access property list.
 *
 * <p>H5Gopen2 opens an existing group, name, at the location
 * specified by loc_id.</p>
 *
 * <p>With default settings, H5Gopen2 provides similar functionality
 * to that provided by H5Gopen1. The only difference is that H5Gopen2
 * can provide a group access property list, gapl_id.</p>
 *
 * <p>H5Gopen2 returns a group identifier for the group that was
 * opened. This group identifier should be released by calling
 * H5Gclose when it is no longer needed. </p>
 *
 * @param loc_id File or group identifier specifying the location of
 * the group to be opened
 * @param name Name of the group to open,
 * @param gapl_id Group access property list identifier (H5P_DEFAULT
 * for the default property list)
 *
 * @return Returns a group identifier if successful; otherwise returns
 * a negative value.
 */
hid_t H5Gopen2(hid_t loc_id, const char *name, hid_t gapl_id);

/**    
 * Sets a comment for specified object.
 *
 * H5Gset_comment sets the comment for the object specified by loc_id
 * and name to comment. Any previously existing comment is
 * overwritten.
 *
 * <p>loc_id can specify any object in the file. name can be one of
 * the following:</p>
 <ul>
    <li>The name of the object relative to loc_id.</li>
    <li>An absolute name of the object, starting from /, the file's
    root group</li>
    <li>A dot (.), if loc_id fully specifies the object</li>
 </ul>
 * <p>If comment is the empty string or a null pointer, the comment
 * message is removed from the object.</p>
 *
 * <p>Comments should be relatively short, null-terminated, ASCII
 * strings.</p>
 *
 * <p>Comments can be attached to any object that has an object
 * header, e.g., datasets, groups, and named datatypes, but not
 * symbolic links. </p>
 *
 * @param loc_id Identifier of the file, group, dataset, or named datatype.
 * @param name Name of the object whose comment is to be set or reset. 
 * @param comment The new comment.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5set_comment "@Deprecated\n   public"
herr_t H5Gset_comment(hid_t loc_id, const char *name, const char *comment);

/**
 * Removes the link to an object from a group.
 *
 * <p>H5Gunlink removes the object specified by name from the group
 * graph and decrements the link count for the object to which name
 * points. This action eliminates any association between name and the
 * object to which name pointed.</p>
 *
 * <p>Object headers keep track of how many hard links refer to an
 * object; when the link count reaches zero, the object can be removed
 * from the file. Objects which are open are not removed until all
 * identifiers to the object are closed.</p>
 *
 * <p>If the link count reaches zero, all file space associated with
 * the object will be released, i.e., identified in memory as
 * freespace. If the any object identifier is open for the object, the
 * space will not be released until after the object identifier is
 * closed.</p>
 *
 * <p>Note that space identified as freespace is available for re-use
 * only as long as the file remains open; once a file has been closed,
 * the HDF5 library loses track of freespace. See "Freespace
 * Management" in the HDF5 User's Guide for further details. </p>
 *
 * <p>Warning:<br/> Exercise care in unlinking groups as it is
 * possible to render data in a file inaccessible with H5Gunlink. See
 * The Group Interface in the HDF5 User's Guide.</p>
 *
 * @param loc_id Identifier of the file or group containing the object.
 * @param name Name of the object to unlink.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%javamethodmodifiers H5Gunlink "@Deprecated\n   public"
herr_t H5Gunlink(hid_t loc_id, const char *name);






