%module IdentifierLib
%include "typemaps.i"
%include "various.i"
%include "H5types.i"
%include "H5Itypes.i"

%header %{
#include <errno.h>
#include "H5Ipublic.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"

/*nodoc*
 * Deletes all IDs of the given type
 *
 * <p>H5Iclear_type deletes all IDs of the type identified by the
 * argument type.</p>
 *
 * <p>The type's free function is first called on all of these IDs to
 * free their memory, then they are removed from the type.</p>
 *
 * <p>If the force flag is set to false, only those IDs whose
 * reference counts are equal to 1 will be deleted, and all other IDs
 * will be entirely unchanged. If the force flag is true, all IDs of
 * this type will be deleted. </p>
 *
 * @param type Identifier of ID type which is to be cleared of IDs.
 * @param force Whether or not to force deletion of all IDs.
 *
 * @return Returns non-negative on success, negative on failure.
 */ 
 //herr_t H5Iclear_type(H5I_type_t type, hbool_t force);

/**
 * Decrements the reference count for an object.
 *
 * <p>H5Idec_ref decrements the reference count of the object
 * identified by obj_id.</p>
 *
 * <p>The reference count for an object ID is attached to the
 * information about an object in memory and has no relation to the
 * number of links to an object on disk.</p>
 *
 * <p>The reference count for a newly created object will be
 * one. Reference counts for objects may be explicitly modified with
 * this function or with H5Iinc_ref. When an object ID's reference
 * count reaches zero, the object will be closed. Calling an object
 * ID's 'close' function decrements the reference count for the ID
 * which normally closes the object, but if the reference count for
 * the ID has been incremented with H5Iinc_ref, the object will only
 * be closed when the reference count reaches zero with further calls
 * to this function or the object ID's 'close' function.</p>
 *
 * <p>If the object ID was created by a collective parallel call (such
 * as H5Dcreate, H5Gopen, etc.), the reference count should be
 * modified by all the processes which have copies of the
 * ID. Generally this means that group, dataset, attribute, file and
 * named datatype IDs should be modified by all the processes and that
 * all other types of IDs are safe to modify by individual
 * processes.</p>
 *
 * <p>This function is of particular value when an application is
 * maintaining multiple copies of an object ID. The object ID can be
 * incremented when a copy is made. Each copy of the ID can then be
 * safely closed or decremented and the HDF5 object will be closed
 * when the reference count for that that object drops to zero. </p>
 *
 * @param obj_id Object identifier whose reference count will be modified.
 *
 * @return Returns a non-negative reference count of the object ID
 * after decrementing it, if successful; otherwise a negative value is
 * returned.
 */
int H5Idec_ref(hid_t obj_id);

/*nodoc*
 * Decrements the reference count on an ID type.
 *
 * <p>H5Idec_type_ref decrements the reference count on an ID
 * type. The reference count is used by the library to indicate when
 * an ID type can be destroyed. If the reference count reaches zero,
 * this function will destroy it.</p>
 *
 * <p>The type parameter is the identifier for the ID type whose
 * reference count is to be decremented. This identifier must have
 * been created by a call to H5Iregister_type. </p>
 *
 * @param type The identifier of the type whose reference count is to
 * be decremented
 *
 * @return Returns the current reference count on success, negative on failure.
 */
//int H5Idec_type_ref(H5I_type_t type);

/*nodoc*
 * Removes the type type and all IDs within that type.
 *
 * <p>H5Idestroy_type deletes an entire ID type. All IDs of this type
 * are destroyed and no new IDs of this type can be registered.</p>
 *
 * <p>The type's free function is called on all of the IDs which are
 * deleted by this function, freeing their memory. In addition, all
 * memory used by this type's hash table is freed.</p>
 *
 * <p>Since the H5I_type_t values of destroyed ID types are reused
 * when new types are registered, it is a good idea to set the
 * variable holding the value of the destroyed type to
 * H5I_UNINIT. </p>
 *
 * @param type Identifier of ID type which is to be destroyed.
 *
 * @return Returns non-negative on success, negative on failure.
 */
//herr_t H5Idestroy_type(H5I_type_t type);

/**
 * Retrieves an identifier for the file containing the specified object.
 *
 * <p>H5Iget_file_id returns the identifier of the file associated
 * with the object referenced by obj_id.</p>
 *
 * <p>obj_id can be a file, group, dataset, named datatype, or
 * attribute identifier.</p>
 *
 * <p>Note that the HDF5 Library permits an application to close a
 * file while objects within the file remain open. If the file
 * containing the object obj_id is still open, H5Iget_file_id will
 * retrieve the existing file identifier. If there is no existing file
 * identifier for the file, i.e., the file has been closed,
 * H5Iget_file_id will reopen the file and return a new file
 * identifier. In either case, the file identifier must eventually be
 * released using H5Fclose. </p>
 *
 * @param obj_id Identifier of the object whose associated file
 * identifier will be returned.
 *
 * @return Returns a file identifier on success, negative on failure. 
 */
hid_t H5Iget_file_id(hid_t obj_id);

/**
 * Retrieves a name of an object based on the object identifier.
 *
 * <p>H5Iget_name retrieves a name for the object identified by obj_id.</p>
 *
 * <p>Up to size characters of the name are returned in name;
 * additional characters, if any, are not returned to the user
 * application.</p>
 *
 * <p>If the length of the name, which determines the required value
 * of size, is unknown, a preliminary H5Iget_name call can be
 * made. The return value of this call will be the size of the object
 * name. That value can then be assigned to size for a second
 * H5Iget_name call, which will retrieve the actual name.</p>
 *
 * <p>If the object identified by obj_id is an attribute, as
 * determined via H5Iget_type, H5Iget_name retrieves the name of the
 * object to which that attribute is attached. To retrieve the name of
 * the attribute itself, use H5Aget_name.</p>
 *
 * <p>If there is no name associated with the object identifier or if
 * the name is NULL, H5Iget_name returns 0 (zero).</p>
 *
 * <p>Note that an object in an HDF5 file may have multiple paths if
 * there are multiple links pointing to it. This function may return
 * any one of these paths. When possible, H5Iget_name returns the path
 * with which the object was opened. </p>
 *
 * @param obj_id Identifier of the object. This identifier can refer
 * to a group, dataset, or named datatype.
 * @param name (Output) A name associated with the identifier.
 * @param size The size of the name buffer.
 *
 * @return Returns the length of the name if successful, returning 0
 * (zero) if no name is associated with the identifier. Otherwise
 * returns a negative value.
 */
%apply signed char OUTPUT[] {char* name}
ssize_t H5Iget_name(hid_t obj_id, char *name, size_t size);
%clear char* name;

/**
 * Retrieves the reference count for an object.
 *
 * <p>H5Iget_ref retrieves the reference count of the object
 * identified by obj_id.</p>
 *
 * <p>The reference count for an object ID is attached to the
 * information about an object in memory and has no relation to the
 * number of links to an object on disk.</p>
 *
 * <p>This function can also be used to check if an object ID is still
 * valid. A non-negative return value from this function indicates
 * that the ID is still valid. </p>
 *
 * @param obj_id Object identifier whose reference count will be retrieved.
 *
 * @return Returns a non-negative current reference count of the
 * object ID if successful; otherwise a negative value is returned.
 */
int H5Iget_ref(hid_t obj_id);

/**
 * Retrieves the type of an object.
 *
 * <p>H5Iget_type retrieves the type of the object identified by
 * obj_id.</p>
 *
 * <p>Valid types returned by the function are</p>
 <dl>
    <dt>H5I_FILE</dt>
    <dd>File</dd>
    <dt>H5I_GROUP</dt>
    <dd>Group</dd>
    <dt>H5I_DATATYPE</dt>
    <dd>Datatype</dd>
    <dt>H5I_DATASPACE</dt>
    <dd>Dataspace</dd>
    <dt>H5I_DATASET</dt>
    <dd>Dataset</dd>
    <dt>H5I_ATTR</dt>
    <dd>Attribute</dd>
 </dl>
 *
 * <p>If no valid type can be determined or the identifier submitted
 * is invalid, the function returns</p>
 * <dl><dt>H5I_BADID</dt><dd>Invalid identifier</dd></dl>
 *
 * <p>This function is of particular value in determining the type of
 * object closing function (H5Dclose, H5Gclose, etc.) to call after a
 * call to H5Rdereference. </p>
 *
 * @param  obj_id Object identifier whose type is to be determined.
 */
H5I_type_t H5Iget_type(hid_t obj_id);

/*nodoc*
 * Retrieves the reference count on an ID type.
 *
 * <p>H5Iget_type_ref retrieves the reference count on an ID type. The
 * reference count is used by the library to indicate when an ID type
 * can be destroyed.</p>
 *
 * <p>The type parameter is the identifier for the ID type whose
 * reference count is to be retrieved. This identifier must have been
 * created by a call to H5Iregister_type. </p>
 *
 * @param type The identifier of the type whose reference count is to
 * be retrieved.
 *
 * @return Returns the current reference count on success, negative on
 * failure.
 */
//int H5Iget_type_ref(H5I_type_t type);

/**
 * Increments the reference count for an object.
 *
 * <p>H5Iinc_ref increments the reference count of the object
 * identified by obj_id.</p>
 *
 * <p>The reference count for an object ID is attached to the
 * information about an object in memory and has no relation to the
 * number of links to an object on disk.</p>
 *
 * <p>The reference count for a newly created object will be
 * one. Reference counts for objects may be explicitly modified with
 * this function or with H5Idec_ref. When an object ID's reference
 * count reaches zero, the object will be closed. Calling an object
 * ID's 'close' function decrements the reference count for the ID
 * which normally closes the object, but if the reference count for
 * the ID has been incremented with this function, the object will
 * only be closed when the reference count reaches zero with further
 * calls to H5Idec_ref or the object ID's 'close' function.</p>
 *
 * <p>If the object ID was created by a collective parallel call (such
 * as H5Dcreate, H5Gopen, etc.), the reference count should be
 * modified by all the processes which have copies of the
 * ID. Generally this means that group, dataset, attribute, file and
 * named datatype IDs should be modified by all the processes and that
 * all other types of IDs are safe to modify by individual
 * processes.</p>
 *
 * <p>This function is of particular value when an application is
 * maintaining multiple copies of an object ID. The object ID can be
 * incremented when a copy is made. Each copy of the ID can then be
 * safely closed or decremented and the HDF5 object will be closed
 * when the reference count for that that object drops to zero. </p>
 *
 * @param obj_id Object identifier whose reference count will be modified.
 *
 * @return Returns a non-negative reference count of the object ID
 * after incrementing it if successful; otherwise a negative value is
 * returned.
 */
int H5Iinc_ref(hid_t obj_id);

/**
 * Increments the reference count on an ID type.
 *
 * <p>H5Iinc_type_ref increments the reference count on an ID
 * type. The reference count is used by the library to indicate when
 * an ID type can be destroyed.</p>
 *
 * <p>The type parameter is the identifier for the ID type whose
 * reference count is to be incremented. This identifier must have
 * been created by a call to H5Iregister_type. </p>
 *
 * @param type The identifier of the type whose reference count is to
 * be incremented.
 *
 * @return Returns the current reference count on success, negative on
 * failure.
 */
//int H5Iinc_type_ref(H5I_type_t type);

/*nodoc*
 * Returns the number of identifiers in a given identifier type.
 *
 * <p>H5Inmembers returns the number of identifiers of the identifier
 * type specified in type.</p>
 *
 * <p>The number of identifiers is returned in num_members. If no
 * identifiers of this type have been registered, the type does not
 * exist, or it has been destroyed, num_members is returned with the
 * value 0. </p>
 *
 * @param type Identifier for the identifier type whose member count
 * will be retrieved.
 * @param num_members (Output) Number of identifiers of the specified
 * identifier type.
 *
 * @return Returns a non-negative value on success; otherwise returns
 * negative value.
 */
//%apply long long* OUTPUT {hsize_t* num_members}
//herr_t H5Inmembers(H5I_type_t type, hsize_t *num_members);
//%clear hsize_t* num_members;

/*nodoc*
 * Verifies an object id is of a given type.
 *
 * <p>H5Iobject_verify returns a pointer to the memory referenced by
 * id after verifying that id is of type id_type. This function is
 * analogous to dereferencing a pointer in C with type checking.</p>
 *
 * <p>H5Iregister(H5I_type_t type, void *object) takes an H5I_type_t
 * and a void pointer to an object, returning an hid_t of that
 * type. This hid_t can then be passed to H5Iobject_verify along with
 * its type to retrieve the object.</p>
 *
 * <p>H5Iobject_verify does not change the ID it is called on in any
 * way (as opposed to H5Iremove_verify, which removes the ID from its
 * type's hash table). </p>
 *
 * @param obj_id Object id to be dereferenced.
 * @param type Object type to which obj_id should belong.
 *
 * @return Pointer to the object referenced by id on success, NULL on
 * failure.
 */
//void* H5Iobject_verify(hid_t obj_id, H5I_type_t id_type);

/*nodoc*
 * Creates and returns a new ID.
 *
 * <p>H5Iregister allocates space for a new ID and returns an
 * identifier for it.</p>
 *
 * <p>The type parameter is the identifier for the ID type to which
 * this new ID will belong. This identifier must have been created by
 * a call to H5Iregister_type.</p>
 *
 * <p>The object parameter is a pointer to the memory which the new ID
 * will be a reference to. This pointer will be stored by the library
 * and returned to you via a call to H5Iobject_verify. </p>
 *
 * @param type The identifier of the type to which the new ID will belong.
 * @param object Pointer to memory for the library to store.
 *
 * @return Returns the new ID on success, negative on failure.
 */
//hid_t H5Iregister(H5I_type_t type, void *object);

/*nodoc*
 * Creates and returns a new object id type.
 *
 * <p>H5Iregister_type allocates space for a new ID type and returns
 * an identifier for it.</p>
 *
 * <p>The hash_size parameter indicates the minimum size of the hash
 * table used to store IDs in the new type.</p>
 *
 * <p>The reserved parameter indicates the number of IDs in this new
 * type to be reserved. Reserved IDs are valid IDs which are not
 * associated with any storage within the library.</p>
 *
 * <p>The free_func parameter is a function pointer to a function
 * which returns an herr_t and accepts a void *. The purpose of this
 * function is to deallocate memory for a single ID. It will be called
 * by H5Iclear_type and H5Idestroy_type on each ID. This function is
 * NOT called by H5Iremove_verify. The void * will be the same pointer
 * which was passed in to the H5Iregister function. The free_func
 * function should return 0 on success and -1 on failure. </p>
 *
 * @param hash_size Size of the hash table (in entries) used to store
 * IDs for the new type.
 * @param reserved Number of reserved IDs for the new type.
 * @param free_func Function used to deallocate space for a single ID.
 *
 * @return Returns the type identifier on success, negative on failure.
 */
//H5I_type_t H5Iregister_type(size_t hash_size, unsigned int reserved, 
//			    H5I_free_t free_func);

/*nodoc*
 * Removes an object id of a given type from internal storage.
 *
 * <p>H5Iremove_verify first ensures that id belongs to id_type. If
 * so, it removes id from internal storage and returns the pointer to
 * the memory it referred to. This pointer is the same pointer that
 * was placed in storage by H5Iregister. If id does not belong to
 * id_type, then NULL is returned.</p>
 *
 * <p>The id parameter is the ID which is to be removed from internal
 * storage. Note: this function does NOT deallocate the memory that id
 * refers to. The pointer returned by H5Iregister must be deallocated
 * by the user to avoid memory leaks.</p>
 *
 * <p>The type parameter is the identifier for the ID type which id is
 * supposed to belong to. This identifier must have been created by a
 * call to H5Iregister_type. </p>
 *
 * @param obj_id The ID to be removed from internal storage.
 * @param type The identifier of the type whose reference count is to
 * be retrieved.
 *
 * @return Returns a pointer to the memory referred to by id on
 * success, NULL on failure.
 */
//void* H5Iremove_verify(hid_t obj_id, H5I_type_t id_type);

/*nodoc*
 * Finds the memory referred to by an ID within the given ID type such
 * that some criterion is satisfied.
 *
 * <p>H5Isearch searches through a give ID type to find an object that
 * satisfies the criteria defined by func. If such an object is found,
 * the pointer to the memory containing this object is
 * returned. Otherwise, NULL is returned. To do this, func is called
 * on every member of type. The first member to satisfy func is
 * returned.</p>
 *
 * <p>The type parameter is the identifier for the ID type which is to
 * be searched. This identifier must have been created by a call to
 * H5Iregister_type.</p>
 *
 * <p>The parameter func is a function pointer to a function which
 * takes three parameters. The first parameter is a void *. It will be
 * a pointer the object to be tested. This is the same object that was
 * placed in storage using H5Iregister. The second parameter is a
 * hid_t. It is the ID of the object to be tested. The last parameter
 * is a void *. This is the key parameter and can be used however the
 * user finds helpful. Or it can simply be ignored if it is not
 * needed. func returns 0 if the object it is testing does not pass
 * its criteria. A non-zero value should be returned if the object
 * does pass its criteria.</p>
 *
 * <p>The key parameter will be passed to the search function as a
 * parameter. It can be used to further define the search at
 * run-time. </p>
 *
 * @param type The identifier of the type to be searched.
 * @param func The function defining the search criteria.
 * @param key A key for the search function.
 *
 * @return Returns a pointer to the object which satisfies the search
 * function on success, NULL on failure.
 */
//void* H5Isearch(H5I_type_t type, H5I_search_func_t func, void *key);

/**
 * Determines whether an identifier type is registered.
 *
 * <p>H5Itype_exists determines whether the given identifier type,
 * type, is registered with the library.</p>
 *
 * @param type Identifier type.
 *
 * @return Returns 1 if the type is registered and 0 if not. Returns a
 * negative value on failure.
 */
htri_t H5Itype_exists(H5I_type_t type);

