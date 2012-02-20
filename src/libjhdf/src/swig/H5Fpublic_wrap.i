%module FileLib
%include "typemaps.i"
%include "various.i"
%import "H5types.i"
%import "strings.i"

%header %{
#include "H5Fpublic.h"
#define SWIG_NOARRAYS 1;
%}
%include "arrays_java.i"
%include "H5Ftypes.i"

/**
* Terminates access to an HDF5 file.
*
* <p>H5Fclose terminates access to an HDF5 file by flushing all data
* to storage and terminating access to the file through file_id.</p>
*
* <p>If this is the last file identifier open for the file and no
* other access identifier is open (e.g., a dataset identifier, group
* identifier, or shared datatype identifier), the file will be fully
* closed and access will end.</p>
*
* <p>Delayed close:<br/> Note the following deviation from the
* above-described behavior. If H5Fclose is called for a file but one
* or more objects within the file remain open, those objects will
* remain accessible until they are individually closed. Thus, if the
* dataset data_sample is open when H5Fclose is called for the file
* containing it, data_sample will remain open and accessible
* (including writable) until it is explicitely closed. The file will
* be automatically closed once all objects in the file have been
* closed.</p>
*
* <p>Be warned, however, that there are circumstances where it is not
* possible to delay closing a file. For example, an MPI-IO file close
* is a collective call; all of the processes that opened the file must
* close it collectively. The file cannot be closed at some time in the
* future by each process in an independent fashion. Another example is
* that an application using an AFS token-based file access privilage
* may destroy its AFS token after H5Fclose has returned
* successfully. This would make any future access to the file, or any
* object within it, illegal.</p>
*
* <p>In such situations, applications must close all open objects in a
* file before calling H5Fclose. It is generally recommended to do so
* in all cases. </p>
*
* @param file_id Identifier of a file to terminate access to.
*
* @return Returns a non-negative value if successful; otherwise
* returns a negative value.
*/
herr_t H5Fclose (hid_t file_id);

/**
 * Creates HDF5 files.
 *
 * <p>H5Fcreate is the primary function for creating HDF5 files.</p>
 *
 * <p>The flags parameter determines whether an existing file will be
 * overwritten. All newly created files are opened for both reading
 * and writing. All flags may be combined with the bit-wise OR
 * operator (`|') to change the behavior of the H5Fcreate call.</p>
 *
 * <p>The more complex behaviors of file creation and access are
 * controlled through the file-creation and file-access property
 * lists. The value of H5P_DEFAULT for a property list value indicates
 * that the library should use the default values for the appropriate
 * property list.</p>
 *
 * <p>The return value is a file identifier for the newly-created
 * file; this file identifier should be closed by calling H5Fclose
 * when it is no longer needed.</p>
 *
 * <p>Special case -- File creation in the case of an already-open
 * file:<br/> If a file being created is already opened, by either a
 * previous H5Fopen or H5Fcreate call, the HDF5 library may or may not
 * detect that the open file and the new file are the same physical
 * file. (See H5Fopen regarding the limitations in detecting the
 * re-opening of an already-open file.)</p>
 *
 * <p>If the library detects that the file is already opened,
 * H5Fcreate will return a failure, regardless of the use of
 * H5F_ACC_TRUNC.</p>
 *
 * <p>If the library does not detect that the file is already opened
 * and H5F_ACC_TRUNC is not used, H5Fcreate will return a failure
 * because the file already exists. Note that this is correct
 * behavior.</p>
 *
 * <p>But if the library does not detect that the file is already
 * opened and H5F_ACC_TRUNC is used, H5Fcreate will truncate the
 * existing file and return a valid file identifier. Such a truncation
 * of a currently-opened file will almost certainly result in
 * errors. While unlikely, the HDF5 library may not be able to detect,
 * and thus report, such errors.</p>
 *
 * <p>Applications should avoid calling H5Fcreate with an already
 * opened file.</p>
 *
 * @param filename Name of the file to access.
 * @param flags File access flags. Allowable values are:<br/>
  <dl> 
     <dt>H5F_ACC_TRUNC</dt>
     <dd>Truncate file, if it already exists, erasing all data
     previously stored in the file.</dd>
     <dt>H5F_ACC_EXCL</dt>
     <dd>Fail if file already exists.</dd>
  </dl>
  <ul>
  <li>H5F_ACC_TRUNC and H5F_ACC_EXCL are mutually exclusive; use
  exactly one.</li>
  <li>An additional flag, H5F_ACC_DEBUG, prints debug
  information. This flag is used only by HDF5 library developers; it
  is neither tested nor supported for use in applications.</li>
  </ul>
 * @param fcpl_id File creation property list identifier, used when
 * modifying default file meta-data. Use H5P_DEFAULT for default file
 * creation properties.
 * @param fapl_id File access property list identifier. If parallel
 * file access is desired, this is a collective call according to the
 * communicator stored in the access_id. Use H5P_DEFAULT for default
 * file access properties.
 *
 * @return Returns a file identifier if successful; otherwise returns
 * a negative value.
 */
hid_t  H5Fcreate (const char *filename, unsigned flags,
		  hid_t fcpl_id, hid_t fapl_id);

/**
 * Flushes all buffers associated with a file to disk.
 *
 * <p>H5Fflush causes all buffers associated with a file to be
 * immediately flushed to disk without removing the data from the
 * cache.</p>
 *
 * <p>object_id can be any object associated with the file, including
 * the file itself, a dataset, a group, an attribute, or a named data
 * type.</p>
 *
 * <p>scope specifies whether the scope of the flushing action is
 * global or local. Valid values are</p>
 <dd>
 <dt>H5F_SCOPE_GLOBAL</dt>
 <dd>Flushes the entire virtual file (including files opened by H5Fmount).</dd>
 <dt>H5F_SCOPE_LOCAL</dt>
 <dd>Flushes only the specified file.</dd>
 *
 <p>Note:<br/> HDF5 does not possess full control over
 buffering. H5Fflush flushes the internal HDF5 buffers then asks the
 operating system (the OS) to flush the system buffers for the open
 files. After that, the OS is responsible for ensuring that the data
 is actually flushed to disk.</p>
 *
 * @param object_id Identifier of object used to identify the file.
 * @param scope Specifies the scope of the flushing action.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Fflush(hid_t object_id, H5F_scope_t scope);

/**
 * Returns an identifier for the file access property list.
 *
 * <p>H5Fget_access_plist returns the file access property list
 * identifier of the specified file.</p>
 *
 * @param file_id Identifier of file to get access property list for.
 *
 * @return Returns a file access property list identifier if
 * successful; otherwise returns a negative value.
 */
hid_t  H5Fget_access_plist (hid_t file_id);

/**
 * Returns a file creation property list identifier.
 *
 * <p>H5Fget_create_plist returns a file creation property list
 * identifier identifying the creation properties used to create this
 * file. This function is useful for duplicating properties when
 * creating another file.</p>
 *
 * <p>The creation property list identifier should be released with
 * H5Pclose.</p>
 *
 * @param file_id Identifier of file to get creation property list for.
 *
 * @return Returns a file creation property list identifier if
 * successful; otherwise returns a negative value.
 */
hid_t  H5Fget_create_plist (hid_t file_id);

/**
 * Returns the size of an HDF5 file.
 *
 * <p>H5Fget_filesize returns the size of the HDF5 file specified by
 * file_id.</p>
 *
 * <p>The returned size is that of the entire file, as opposed to only
 * the HDF5 portion of the file. I.e., size includes the user block,
 * if any, the HDF5 portion of the file, and any data that may have
 * been appended beyond the data written through the HDF5
 * Library. </p>
 *
 * @param file_id Identifier of a currently-open HDF5 file.
 * @param size (Output) Size of the file, in bytes.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply unsigned long* OUTPUT {hsize_t* size}
herr_t H5Fget_filesize(hid_t file_id, hsize_t *size);

/**
 * Returns the amount of free space in a file.
 *
 * <p>Given the identifier of an open file, file_id, H5Fget_freespace
 * returns the amount of space that is unused by any objects in the
 * file.</p>
 *
 * <p>Currently, the HDF5 library only tracks free space in a file
 * from a file open or create until that file is closed, so this
 * routine will only report the free space that has been created
 * during that interval.</p>
 *
 * @param file_id Identifier of a currently-open HDF5 file.
 *
 * @return Returns the amount of free space in the file if successful;
 * otherwise returns a negative value.
 */
hssize_t H5Fget_freespace(hid_t file_id);

/**
 * Returns global information for a file.
 *
 * <p>H5Fget_info returns global information for the file associated
 * with the object identifier obj_id in the H5F_info_t struct named
 * file_info.</p>
 *
 * <p>obj_id is an identifier for any object in the file of
 * interest.</p>
 *
 * <p>An H5F_info_t struct is defined as follows (in H5Fpublic.h):</br>
 <code>
  typedef struct H5F_info_t {
     hsize_t           super_ext_size; 
     struct {
        hsize_t       hdr_size;      
        H5_ih_info_t  msgs_info;      
    } sohm;
 } H5F_info_t; 
 </code>
 * super_ext_size is the size of the superblock extension.</p>
 *
 * <p>The sohm sub-struct contains shared object header message
 * information: hdr_size is the size of shared of object header
 * messages. msgs_info is a H5_ih_info_t struct containing the
 * cumulative shared object header message index size and heap size; an
 * H5_ih_info_t struct is defined as follows (in H5public.h):<br/>
 <code>
 typedef struct H5_ih_info_t {
    hsize_t     index_size;     
    hsize_t     heap_size;
 } H5_ih_info_t;
 </code>
 * index_size is the summed size of all of the shared of object header
 * indexes. Each index might be either a B-tree or a list. heap_size
 * is the size of the heap.</p>
 *
 * @param obj_id Object identifier for any object in the file.
 * @param file_info (Output) Struct containing global file information.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Fget_info(hid_t obj_id, H5F_info_t *file_info);

/**
 * Determines the read/write or read-only status of a file.
 *
 * <p>Given the identifier of an open file, file_id, H5Fget_intent
 * retrieves the "intended access mode" flag passed with H5Fopen when
 * the file was opened.</p>
 *
 * <p>The value of the flag is returned in intent. Valid values are as
 * follows:<br/>
 <dl> 
   <dt>H5F_ACC_RDWR</dt>
   <dd>File was opened with read/write access.</dd>
   <dt>H5F_ACC_RDONLY</dt>
   <dd>File was opened with read-only access.</dd>
 </dl>
 *
 * <p>The function will not return an error if intent is NULL; it will
 * simply do nothing.</p>
 *
 * @param file_id File identifier for a currently-open HDF5 file.
 * @param intent (Output) Intended access mode flag, as originally
 * passed with H5Fopen.
 *
 * @return Returns the amount of free space in the file if successful;
 * otherwise returns a negative value.
 */
%apply unsigned int* OUTPUT {unsigned int* intent}
herr_t H5Fget_intent(hid_t file_id, unsigned int* intent);

/**
 * Obtain current metadata cache configuration for target file.
 *
 * <p>H5Fget_mdc_config loads the current metadata cache configuration
 * into the instance of H5AC_cache_config_t pointed to by the
 * config_ptr parameter.</p>
 *
 * <p>Note that the version field of *config_ptr must be initialized
 * --this allows the library to support old versions of the
 * H5AC_cache_config_t structure.</p>
 *
 * <p>See the overview of the metadata cache in the special topics
 * section of the user manual for details on metadata cache
 * configuration. If you haven't read and understood that
 * documentation, the results of this call will not make much
 * sense.</p>
 *
 * @param file_id Identifier of the target file.
 * @param config_ptr (Input/Output) Pointer to the instance of
 * H5AC_cache_config_t in which the current metadata cache
 * configuration is to be reported. The fields of this structure are
 * discussed below:<br/>
 *
 * *** TODO document the H5AC_cache_config_t struct.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Fget_mdc_config(hid_t file_id,	H5AC_cache_config_t* config_ptr);

/**
 * Obtain target file's metadata cache hit rate.
 *
 * <p>H5Fget_mdc_hit_rate queries the metadata cache of the target
 * file to obtain its hit rate (cache hits / (cache hits + cache
 * misses)) since the last time hit rate statistics were reset. If the
 * cache has not been accessed since the last time the hit rate stats
 * were reset, the hit rate is defined to be 0.0.</p>
 *
 * <p>The hit rate stats can be reset either manually (via
 * H5Freset_mdc_hit_rate_stats()), or automatically. If the cache's
 * adaptive resize code is enabled, the hit rate stats will be reset
 * once per epoch. If they are reset manually as well, the cache may
 * behave oddly.</p>
 *
 * <p>See the overview of the metadata cache in the special topics
 * section of the user manual for details on the metadata cache and
 * its adaptive resize algorithms.</p>
 *
 * @param file_id Identifier of the target file.
 * @param hit_rate_ptr (Ouput) Pointer to the double in which the hit
 * rate is returned. Note that *hit_rate_ptr is undefined if the API
 * call fails.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply double* OUTPUT {double* hit_rate_ptr}
herr_t H5Fget_mdc_hit_rate(hid_t file_id, double* hit_rate_ptr);
%clear double* hit_rate_ptr;

/**
 * Obtain current metadata cache size data for specified file.
 *
 * <p>H5Fget_mdc_size queries the metadata cache of the target file
 * for the desired size information, and returns this information in
 * the locations indicated by the pointer parameters. If any pointer
 * parameter is NULL, the associated data is not returned.</p>
 *
 * <p>If the API call fails, the values returned via the pointer
 * parameters are undefined.</p>
 *
 * <p>If adaptive cache resizing is enabled, the cache maximum size
 * and minimum clean size may change at the end of each epoch. Current
 * size and current number of entries can change on each cache
 * access.</p>
 *
 * <p>Current size can exceed maximum size under certain
 * conditions. See the overview of the metadata cache in the special
 * topics section of the user manual for a discussion of this. </p>
 *
 * @param file_id Identifier of the target file.
 * @param max_size_ptr (Output) Pointer to the location in which the
 * current cache maximum size is to be returned, or NULL if this datum
 * is not desired.
 * @param min_clean_size_ptr (Output) Pointer to the location in which
 * the current cache minimum clean size is to be returned, or NULL if
 * that datum is not desired.
 * @param cur_size_ptr (Output) Pointer to the location in which the
 * current cache size is to be returned, or NULL if that datum is not
 * desired.
 * @param cur_num_entries_ptr (Output) Pointer to the location in
 * which the current number of entries in the cache is to be returned,
 * or NULL if that datum is not desired.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */ 
%apply unsigned int* OUTPUT {size_t* max_size_ptr}
%apply unsigned int* OUTPUT {size_t* min_clean_size_ptr}
%apply unsigned int* OUTPUT {size_t* cur_size_ptr}
%apply int* OUTPUT {int* cur_num_entries_ptr}
herr_t H5Fget_mdc_size(hid_t file_id, size_t* max_size_ptr, 
		       size_t* min_clean_size_ptr, size_t* cur_size_ptr,
		       int* cur_num_entries_ptr);
%clear size_t* max_size_ptr;
%clear size_t* min_clean_size_ptr;
%clear size_t* cur_size_ptr;
%clear int* cur_num_entries_ptr;

/**
 * Retrieves name of file to which object belongs.
 *
 * <p>H5Fget_name retrieves the name of the file to which the object
 * obj_id belongs. The object can be a group, dataset, attribute, or
 * named datatype.</p>
 *
 * <p>Up to size characters of the filename are returned in name;
 * additional characters, if any, are not returned to the user
 * application.</p>
 *
 * <p>If the length of the name, which determines the required value
 * of size, is unknown, a preliminary H5Fget_name call can be made by
 * setting name to NULL. The return value of this call will be the
 * size of the filename; that value can then be assigned to size for a
 * second H5Fget_name call, which will retrieve the actual name.</p>

 * <p>If an error occurs, the buffer pointed to by name is unchanged
 * and the function returns a negative value. </p>
 *
 * @param obj_id Identifier of the object for which the associated
 * filename is sought. The object can be a group, dataset, attribute,
 * or named datatype.
 * @param name (Output) Buffer to contain the returned filename.
 * @param size Size, in bytes, of the name buffer.
 *
 * @return Returns the length of the filename if successful; otherwise
 * returns a negative value.
 */
%apply (char* CSTRING, int SIZE) {(char* name, size_t size)} 
ssize_t H5Fget_name(hid_t obj_id, char* name, size_t size);

/**
 * Returns the number of open object identifiers for an open file.
 *
 * <p>Given the identifier of an open file, file_id, and the desired
 * object types, types, H5Fget_obj_count returns the number of open
 * object identifiers for the file.</p>
 *
 * <p>To retrieve a count of open identifiers for open objects in all
 * HDF5 application files that are currently open, pass the value
 * H5F_OBJ_ALL in file_id.</p>
 *
 * <p>The types of objects to be counted are specified in types as
 * follows:<br/>
 * <dl>
    <dt>H5F_OBJ_FILE</dt>
    <dd>Files only</dd>
    <dt>H5F_OBJ_DATASET</dt>
    <dd>Datasets only</dd>
    <dt>H5F_OBJ_GROUP</dt>
    <dd>Groups only</dd>
    <dt>H5F_OBJ_DATATYPE</dt>
    <dd>Named datatypes only</dd>
    <dt>H5F_OBJ_ATTR</dt>
    <dd>Attributes only</dd>
    <dt>H5F_OBJ_ALL</dt>
    <dd>All of the above (I.e., H5F_OBJ_FILE | H5F_OBJ_DATASET |
    H5F_OBJ_GROUP | H5F_OBJ_DATATYPE | H5F_OBJ_ATTR )</dd>
    <dt>H5F_OBJ_LOCAL</dt>
    <dd>Restrict search to objects opened through current file
    identifier.</dd>
  </dl>
 *
 * Multiple object types can be combined with the logical OR operator
 * (|). For example, the expression (H5F_OBJ_DATASET|H5F_OBJ_GROUP)
 * would call for datasets and groups.</p>
 *
 * @param file_id Identifier of a currently-open HDF5 file or
 * H5F_OBJ_ALL for all currently-open HDF5 files.
 * @param types Type of object for which identifiers are to be returned.
 *
 * @return Returns the number of open objects if successful; otherwise
 * returns a negative value.
 */
int H5Fget_obj_count(hid_t file_id, unsigned types);

/**
 * Returns a list of open object identifiers.
 *
 * <p>Given the file identifier file_id and the type of objects to be
 * identified, types, H5Fget_obj_ids returns the list of identifiers
 * for all open HDF5 objects fitting the specified criteria.</p>
 *
 * <p>To retrieve identifiers for open objects in all HDF5 application
 * files that are currently open, pass the value H5F_OBJ_ALL in
 * file_id.</p>
 *
 * <p>The types of object identifiers to be retrieved are specified in
 * types using the codes listed for the same parameter in
 * H5Fget_obj_count</p>
 *
 * <p>To retrieve identifiers for all open objects, pass a negative
 * value for the max_objs.</p>
 *
 * @param file_id Identifier of a currently-open HDF5 file or
 * H5F_OBJ_ALL for all currently-open HDF5 files.
 * @param types Type of object for which identifiers are to be returned.
 * @param max_objs Maximum number of object identifiers to place into
 * obj_id_list.
 * @param obj_id_list (Output) Pointer to the returned list of open
 * object identifiers.
 *
 * @return Returns number of objects placed into obj_id_list if
 * successful; otherwise returns a negative value.
 */
%apply int[] {hid_t* obj_id_list}
int H5Fget_obj_ids(hid_t file_id, unsigned types, int max_objs, hid_t* obj_id_list);

/**
 * Returns pointer to the file handle from the virtual file driver.
 *
 * <p>Given the file identifier file_id and the file access property
 * list fapl_id, H5Fget_vfd_handle returns a pointer to the file
 * handle from the low-level file driver currently being used by the
 * HDF5 library for file I/O. </p>
 *
 * <p>Notes:<br/> Users are not supposed to modify any file through
 * this file handle.</p>
 *
 * <p>This file handle is dynamic and is valid only while the file
 * remains open; it will be invalid if the file is closed and reopened
 * or opened during a subsequent session.</p>
 *
 * @param file_id Identifier of the file to be queried.
 * @param fapl_id File access property list identifier. For most
 * drivers, the value will be H5P_DEFAULT. For the FAMILY or MULTI
 * drivers, this value should be defined through the property list
 * functions: H5Pset_family_offset for the FAMILY driver and
 * H5Pset_multi_type for the MULTI driver.
 * @param file_handle (Output) Pointer to the file handle being used
 * by the low-level virtual file driver.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Fget_vfd_handle(hid_t file_id, hid_t fapl_id, void** file_handle);

/**
 * Determines whether a file is in the HDF5 format.
 *
 * <p>H5Fis_hdf5 determines whether a file is in the HDF5 format.</p>
 *
 * @param filename File name to check format.
 *
 * @return When successful, returns a positive value, for TRUE, or 0
 * (zero), for FALSE. Otherwise returns a negative value.
 */
htri_t H5Fis_hdf5 (const char *filename);

/**
 * Mounts a HDF5 file at a given mount point in an other HDF5 file.
 *
 * <p>H5Fmount mounts the file specified by child_id onto the group
 * specified by loc_id and name using the mount properties plist_id.</p>
 *
 * <p>Note that loc_id is either a file or group identifier and name
 * is relative to loc_id. </p>
 *
 * @param loc_id Identifier for of file or group in which name is defined.
 * @param name Name of the group onto which the file specified by
 * child_id is to be mounted.
 * @param child_id Identifier of the file to be mounted.
 * @param fapl_id Identifier of the property list to be used.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Fmount(hid_t loc_id, const char *name, hid_t child_id, hid_t fapl_id);

/**
 * Opens an existing HDF5 file.
 *
 * <p>H5Fopen opens an existing file and is the primary function for
 * accessing existing HDF5 files.</p>
 *
 * <p>Note that H5Fopen does not create a file if it does not already
 * exist. See H5Fcreate.</p>
 *
 * <p>The parameter access_id is a file access property list
 * identifier or H5P_DEFAULT if the default I/O access parameters are
 * to be used</p>
 *
 * <p>The flags argument determines whether writing to an existing
 * file will be allowed. The file is opened with read and write
 * permission if flags is set to H5F_ACC_RDWR. All flags may be
 * combined with the bit-wise OR operator (`|') to change the behavior
 * of the file open call. More complex behaviors of file access are
 * controlled through the file-access property list.</p>
 *
 * <p>The return value is a file identifier for the open file; this
 * file identifier should be closed by calling H5Fclose when it is no
 * longer needed.</p>
 *
 * <p>Special case -- Multiple opens:<br/> A file can often be opened
 * with a new H5Fopen call without closing an already-open identifier
 * established in a previous H5Fopen or H5Fcreate call. Each such
 * H5Fopen call will return a unique identifier and the file can be
 * accessed through any of these identifiers as long as the identifier
 * remains valid. In such multiply-opened cases, all the open calls
 * should use the same flags argument.</p>
 *
 * <p>In some cases, such as files on a local Unix file system, the
 * HDF5 library can detect that a file is multiply opened and will
 * maintain coherent access among the file identifiers.</p>
 *
 * <p>But in many other cases, such as parallel file systems or
 * networked file systems, it is not always possible to detect
 * multiple opens of the same physical file. In such cases, HDF5 will
 * treat the file identifiers as though they are accessing different
 * files and will be unable to maintain coherent access. Errors are
 * likely to result in these cases. While unlikely, the HDF5 library
 * may not be able to detect, and thus report, such errors.</p>
 *
 * <p>It is generally recommended that applications avoid multiple
 * opens of the same file. </p>
 *
 * @param filename Name of the file to access.
 * @param flags File access flags. Allowable values are:<br/>
  <dl> 
     <dt>H5F_ACC_RDRW</dt>
     <dd>Allow read and write access to file.</dd>
     <dt>H5F_ACC_RDONLY</dt>
     <dd>Allow read-only access to file.</dd>
  </dl>
  <ul>
  <li>H5F_ACC_RDWR and H5F_ACC_RDONLY are mutually exclusive; use
  exactly one.</li>
  <li>An additional flag, H5F_ACC_DEBUG, prints debug
  information. This flag is used only by HDF5 library developers; it
  is neither tested nor supported for use in applications.</li>
  </ul>
 * @param fapl_id Identifier for the file access properties list. If
 * parallel file access is desired, this is a collective call
 * according to the communicator stored in the access_id. Use
 * H5P_DEFAULT for default file access properties.
 *
 * @return Returns a file identifier if successful; otherwise returns
 * a negative value.
 */
hid_t  H5Fopen (const char *filename, unsigned flags,
		hid_t fapl_id);

/**
 * Returns a new identifier for a previously-opened HDF5 file.
 *
 * <p>H5Freopen returns a new file identifier for an already-open HDF5
 * file, as specified by file_id. Both identifiers share caches and
 * other information. The only difference between the identifiers is
 * that the new identifier is not mounted anywhere and no files are
 * mounted on it.</p>
 *
 * <p>Note that there is no circumstance under which H5Freopen can
 * actually open a closed file; the file must already be open and have
 * an active file_id. E.g., one cannot close a file with H5Fclose
 * (file_id) then use H5Freopen (file_id) to reopen it.</p>
 *
 * <p>The new file identifier should be closed by calling H5Fclose
 * when it is no longer needed. </p>
 *
 * @param file_id Identifier of a file for which an additional
 * identifier is required.
 *
 * @return Returns a new file identifier if successful; otherwise
 * returns a negative value.
 */
hid_t  H5Freopen(hid_t file_id);

/**
 * Reset hit rate statistics counters for the target file.
 *
 * <p>H5Freset_mdc_hit_rate_stats resets the hit rate statistics
 * counters in the metadata cache associated with the specified
 * file.</p>
 *
 * <p>If the adaptive cache resizing code is enabled, the hit rate
 * statistics are reset at the beginning of each epoch. This API call
 * allows you to do the same thing from your program.</p>
 *
 * <p>The adaptive cache resizing code may behave oddly if you use
 * this call when adaptive cache resizing is enabled. However, the
 * call should be useful if you choose to control metadata cache size
 * from your program.</p>
 *
 * <p>See the overview of the metadata cache in the special topics
 * section of the user manual for details of the metadata cache and
 * the adaptive cache resizing algorithms. If you haven't read,
 * understood, and thought about the material covered in that
 * documentation, you shouldn't be using this API call. </p>
 *
 * @param file_id Identifier of the target file.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Freset_mdc_hit_rate_stats(hid_t file_id);

/**
 * Attempt to configure metadata cache of target file.
 *
 * <p>H5Fset_mdc_config attempts to configure the file's metadata
 * cache according configuration supplied in config_ptr.</p>
 *
 * <p>See the overview of the metadata cache in the special topics
 * section of the user manual for details on what is being
 * configured. If you haven't read and understood that documentation,
 * you really shouldn't be using this API call. </p>
 *
 * @param file_id Identifier of the target file.
 * @param config_ptr Pointer to the instance of H5AC_cache_config_t
 * containing the desired configuration. TODO Document H5AC_cache_config_t.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Fset_mdc_config(hid_t file_id, H5AC_cache_config_t* config_ptr);

/**
 * Unmounts a HDF5 file from a given mount point.
 *
 * <p>Given a mount point, H5Funmount dissassociates the mount point's
 * file from the file mounted there. This function does not close
 * either file.</p>
 *
 * <p>The mount point can be either the group in the parent or the
 * root group of the mounted file (both groups have the same name). If
 * the mount point was opened before the mount then it is the group in
 * the parent; if it was opened after the mount then it is the root
 * group of the child.</p>
 *
 * <p>Note that loc_id is either a file or group identifier and name
 * is relative to loc_id. </p>
 *
 * @param loc_id File or group identifier for the location at which
 * the specified file is to be unmounted.
 * @param name Name of the mount point.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Funmount(hid_t loc_id, const char *name);






