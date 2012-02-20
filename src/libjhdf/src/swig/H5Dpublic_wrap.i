%module DatasetLib
%include "typemaps.i"
%include "arrays_java.i"

%import "H5types.i"
%include "buffers.i"
%include "henums.i"
%include "strings.i"
%include "H5Dtypes.i"

%header %{
#include "H5Dpublic.h"
#include "H5Spublic.h"
#define HARR_NOARRAYS 1
%}

%include "harrays.i"


/* Define the operator function pointer for H5Diterate() */
//typedef herr_t (*H5D_operator_t)(void *elem, hid_t type_id, unsigned ndim,
//				 const hsize_t *point, void *operator_data);

/**
 * Creates a dataset at the specified location.
 *
 * <p>H5Dcreate1 creates a data set with a name, name, in the file or
 * in the group specified by the identifier loc_id.</p>
 *
 * <p>name can be a relative path based at loc_id or an absolute path
 * from the root of the file. If any of the groups specified in that
 * path do not already exist, the dataset must be created with
 * H5Dcreate_anon and linked into the file structure with H5Llink.</p>
 *
 * <p>The dataset's datatype and dataspace are specified by type_id
 * and space_id, respectively. These are the datatype and dataspace of
 * the dataset as it will exist in the file, which may differ from the
 * datatype and dataspace in application memory.</p>
 *
 * <p>Names within a group are unique: H5Dcreate1 will return an error
 * if a link with the name specified in name already exists at the
 * location specified in loc_id.</p>
 *
 * <p>As is the case for any object in a group, the length of a
 * dataset name is not limited.</p>
 *
 * <p>create_plist_id is an H5P_DATASET_CREATE property list created
 * with H5Pcreate1 and initialized with various property list
 * functions described in "H5P: Property List Interface."</p>
 *
 * <p>H5Dcreate and H5Dcreate_anon return an error if the dataset's
 * datatype includes a variable-length (VL) datatype and the fill
 * value is undefined, i.e., set to NULL in the dataset creation
 * property list. Such a VL datatype may be directly included,
 * indirectly included as part of a compound or array datatype, or
 * indirectly included as part of a nested compound or array
 * datatype.</p>
 *
 * <p>H5Dcreate and H5Dcreate_anon return a dataset identifier for
 * success or a negative value for failure. The dataset identifier
 * should eventually be closed by calling H5Dclose to release
 * resources it uses.</p>
 *
 *
 * <p>See H5Dcreate_anon for discussion of the differences between
 * H5Dcreate and H5Dcreate_anon.</p>
 *
 * <p>Fill values and space allocation:<br/> The HDF5 library provides
 * flexible means of specifying a fill value, of specifying when space
 * will be allocated for a dataset, and of specifying when fill values
 * will be written to a dataset. For further information on these
 * topics, see the document Fill Value and Dataset Storage Allocation
 * Issues in HDF5 and the descriptions of the following HDF5 functions
 * in this HDF5 Reference Manual:</p>
   <ul>
      <li>H5Dfill</li>
      <li>H5Pset_fill_value</li>
      <li>H5Pget_fill_value</li>
      <li>H5Pfill_value_defined</li>
      <li>H5Pset_fill_time</li>
      <li>H5Pget_fill_time</li>
      <li>H5Pset_alloc_time</li>
      <li>H5Pget_alloc_time</li>
 </ul>
 * <p>This information is also included in the "HDF5 Datasets" chapter
 * of the new HDF5 User's Guide, which is being prepared for
 * release.</p>
 * <p>Note:<br/> H5Dcreate and H5Dcreate_anon can fail if there has
 * been an error in setting up an element of the dataset creation
 * property list. In such cases, each item in the property list must
 * be examined to ensure that the setup satisfies all required
 * conditions. This problem is most likely to occur with the use of
 * filters.</p>
 *
 * <p>For example, either function will fail without a meaningful
 * explanation if the following conditions exist simultaneously:</p>
 <ul>
    <li>SZIP compression is being used on the dataset.</li>
    <li>The SZIP parameter pixels_per_block is set to an inappropriate
    value.</li>
 </ul>
 * <p>In such a case, one would refer to the description of
 * H5Pset_szip, looking for any conditions or requirements that might
 * affect the local computing environment.</p>
 *
 * @param loc_id Identifier of the file or group within which to
 * create the dataset.
 * @param name The name of the dataset to create.
 * @param type_id Identifier of the datatype to use when creating the dataset.
 * @param space_id Identifier of the dataspace to use when creating
 * the dataset.
 * @param create_plist_id Identifier of the set creation property list.
 * 
 * @return Returns a dataset identifier if successful; otherwise
 * returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5Dcreate1 "@Deprecated\n   public"
hid_t H5Dcreate1(hid_t file_id, const char *name, 
		 hid_t type_id, hid_t space_id, 
		 hid_t plist_id);

/**
 * Creates a new dataset and links it into the file.
 *
 * <p>H5Dcreate2 creates a new dataset named name at the location
 * specified by loc_id, and associates constant and initial persistent
 * properties with that dataset, including dtype_id, the datatype of
 * each data element as stored in the file; space_id, the dataspace of
 * the dataset; and other initial properties as defined in the dataset
 * creation property and access property lists, dcpl_id and dapl_id,
 * respectively. Once created, the dataset is opened for access.</p>
 *
 * <p>loc_id may be a file identifier, or a group identifier within
 * that file. name may be either an absolute path in the file or a
 * relative path from loc_id naming the dataset.</p>
 *
 * <p>The link creation property list, lcpl_id, governs creation of
 * the link(s) by which the new dataset is accessed and the creation
 * of any intermediate groups that may be missing.</p>
 *
 * <p>The datatype and dataspace properties and the dataset creation
 * and access property lists are attached to the dataset, so the
 * caller may derive new datatypes, dataspaces, and creation and
 * access properties from the old ones and reuse them in calls to
 * create additional datasets.</p>
 * 
 * <p>Once created, the dataset is ready to receive raw
 * data. Immediately attempting to read raw data from the dataset will
 * probably return the fill value.</p>
 *
 * <p>To conserve and release resources, the dataset should be closed
 * when access is no longer required.</p>
 *
 * @param loc_id Location identifier.
 * @param name Dataset name.
 * @param type_id Datatype identifier.
 * @param space_id Dataspace identifier.
 * @param lcpl_id Link creation property list.
 * @param dcpl_id Dataset creation property list.
 * @param dapl_id Dataset access property list.
 *
 * @return Returns a dataset identifier if successful; otherwise
 * returns a negative value.
 */
hid_t H5Dcreate2(hid_t loc_id, const char *name, 
		 hid_t type_id, hid_t space_id, 
		 hid_t lcpl_id, hid_t dcpl_id, hid_t dapl_id);
/**
 * Creates a dataset in a file without linking it into the file structure.
 *
 * <p>H5Dcreate_anon creates a dataset in the file specified by loc_id.</p>
 *
 * <p>loc_id may be a file identifier or a group identifier within
 * that file.</p>
 *
 * <p>The dataset's datatype and dataspace are specified by type_id
 * and space_id, respectively. These are the datatype and dataspace of
 * the dataset as it will exist in the file, which may differ from the
 * datatype and dataspace in application memory.</p>
 *
 * <p>Dataset creation properties are specified in the dataset
 * creation property list dcpl_id. Dataset access properties are
 * specified in the dataset access property list dapl_id.</p>
 *
 * <p>H5Dcreate_anon returns a new dataset identifier. Using this
 * identifier, the new dataset must be linked into the HDF5 file
 * structure with H5Llink or it will be deleted from the file when the
 * file is closed.</p>
 *
 * <p>See H5Dcreate for further details and considerations on the use
 * of H5Dcreate and H5Dcreate_anon.</p>
 *
 * <p>The differences between this function and H5Dcreate are as follows:</p>
 <ul>
    <li>H5Dcreate_anon explicitly includes a dataset access property
    list. H5Dcreate always uses default dataset access properties.</li>
    <li>H5Dcreate_anon neither provides the new dataset's name nor
    links it into the HDF5 file structure; those actions must be
    performed separately through a call to H5Llink, which offers
    greater control over linking.</li>
 </ul>
 *
 * @param loc_id Identifier of the file or group within which to
 * create the dataset.
 * @param type_id Identifier of the datatype to use when creating the dataset.
 * @param space_id Identifier of the dataspace to use when creating
 * the dataset.
 * @param dcpl_id Dataset creation property list identifier.
 * @param dapl_id Dataset access property list identifier.
 *
 * @return Returns a dataset identifier if successful; otherwise
 * returns a negative value.
 */
hid_t H5Dcreate_anon(hid_t loc_id, hid_t type_id, hid_t space_id,
    hid_t dcpl_id, hid_t dapl_id);

/**
 * Closes the specified dataset.
 *
 * <p>H5Dclose ends access to a dataset specified by dset_id and
 * releases resources used by it. Further use of the dataset
 * identifier is illegal in calls to the dataset API.</p>
 * 
 * @param dset_id Identifier of the dataset to close access to.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Dclose(hid_t dset_id);

herr_t H5Ddebug(hid_t dset_id);

/**
 * Extends a dataset.
 *
 * <p>H5Dextend verifies that the dataset is at least of size size,
 * extending it if necessary. The dimensionality of size is the same
 * as that of the dataspace of the dataset being changed.</p>
 *
 * <p>This function can be applied to the following datasets:</p>
 <ul>
    <li>Any dataset with unlimited dimensions.</li>
    <li>A dataset with fixed dimensions if the current dimension sizes
    are less than the maximum sizes set with maxdims (see
    H5Screate_simple)</li>
 </ul>
 * <p>Space on disk is immediately allocated for the new dataset
 * extent if the dataset's space allocation time is set to
 * H5D_ALLOC_TIME_EARLY. Fill values will be written to the dataset if
 * the dataset's fill time is set to H5D_FILL_TIME_IFSET or
 * H5D_FILL_TIME_ALLOC. (See H5Pset_fill_time and
 * H5Pset_alloc_time.)</p>
 *
 * <p>This function ensures that the dataset dimensions are of at
 * least the sizes specified in size. The function H5Dset_extent must
 * be used if the dataset dimension sizes are are to be reduced. </p>
 *
 * @param dataset_id Identifier of the dataset.
 * @param size Array containing the new magnitude of each dimension.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long INPUT[] {hsize_t* size}
%javamethodmodifiers H5Dextend "@Deprecated\n   public"
herr_t H5Dextend(hid_t dset_id, const hsize_t *size);
%clear hsize_t* size;

/**
 * Fills dataspace elements with a fill value in a memory buffer.
 *
 * <p>H5Dfill explicitly fills the dataspace selection in memory,
 * space_id, with the fill value specified in fill. If fill is NULL, a
 * fill value of 0 (zero) is used.</p>
 * 
 * <p>fill_type_id specifies the datatype of the fill value. buf
 * specifies the buffer in which the dataspace elements will be
 * written. buf_type_id specifies the datatype of those data
 * elements.</p>
 *
 * <p>Note that if the fill value datatype differs from the memory
 * buffer datatype, the fill value will be converted to the memory
 * buffer datatype before filling the selection. </p>
 *
 * <p>Note:</br> Applications sometimes write data only to portions of
 * an allocated dataset. It is often useful in such cases to fill the
 * unused space with a known fill value. See H5Pset_fill_value for
 * further discussion. Other related functions include
 * H5Pget_fill_value, H5Pfill_value_defined, H5Pset_fill_time,
 * H5Pget_fill_time, H5Dcreate, and H5Dcreate_anon.</p>
 *
 * @param fill Pointer to the fill value to be used.
 * @param fill_type_id Fill value datatype identifier.
 * @param buf (Input/Output) Pointer to the memory buffer containing the
 * selection to be filled.
 * @param buf_type_id Datatype of dataspace elements to be filled.
 * @param space_id Dataspace describing memory buffer and containing
 * the selection to be filled.
 * 
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply void* BUFF {void* fill}
%apply void* BUFF {void* buf}
herr_t H5Dfill(const void *fill, hid_t fill_type, void *buf,
               hid_t buf_type, hid_t space);

/**
 * Returns an identifier for a copy of the dataset creation property
 * list for a dataset.
 *
 * <p>H5Dget_create_plist returns an identifier for a copy of the
 * dataset creation property list associated with the dataset
 * specified by dset_id.</p>
 *
 * <p>The creation property list identifier should be released with
 * H5Pclose.</p>
 *
 * @param dset_id Identifier of the dataset to query.
 * 
 * @return Returns a dataset creation property list identifier if
 * successful; otherwise returns a negative value.
 */
hid_t H5Dget_create_plist(hid_t dset_id);

/**
 * Returns dataset address in file.
 *
 * <p>H5Dget_offset returns the address in the file of the dataset
 * dset_id. That address is expressed as the offset in bytes from the
 * beginning of the file.</p>
 *
 * @param dset_id Dataset identifier.
 *
 * @return Returns the offset in bytes; otherwise returns HADDR_UNDEF,
 * a negative value.
 */
%apply long long {haddr_t}
haddr_t H5Dget_offset(hid_t dset_id);
%clear haddr_t;

/**
 * Returns an identifier for a copy of the dataspace for a dataset.
 *
 * <p>H5Dget_space returns an identifier for a copy of the dataspace
 * for a dataset. The dataspace identifier should be released with the
 * H5Sclose function.</p>
 *
 * @param dset_id Identifier of the dataset to query.
 *
 * @return Returns a dataspace identifier if successful; otherwise
 * returns a negative value.
 */
hid_t H5Dget_space(hid_t dset_id);

/**
 * Determines whether space has been allocated for a dataset.
 *
 * <p>H5Dget_space_status determines whether space has been allocated
 * for the dataset dset_id.</p>
 *
 * <p>Space allocation status is returned in status, which will have
 * one of the following values:</p>
 <dl>
    <dt>H5D_SPACE_STATUS_NOT_ALLOCATED</dt>
    <dd>Space has not been allocated for this dataset.</dd>
    <dt>H5D_SPACE_STATUS_ALLOCATED</dt>
    <dd>Space has been allocated for this dataset.</dd>
    <dt>H5D_SPACE_STATUS_PART_ALLOCATED</dt>
    <dd>Space has been partially allocated for this dataset. (Used only
    for datasets with chunked storage.)</dd>
 </dl>
 *
 * @param dset_id Identifier of the dataset to query.
 * @param status (Output) Space allocation status.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply H5D_SPACE_ENUM* OUTPUT {H5D_space_status_t* allocation}
herr_t H5Dget_space_status(hid_t dset_id, H5D_space_status_t *allocation);

/**
 * Returns the amount of storage required for a dataset.
 *
 * <p>H5Dget_storage_size returns the amount of storage that is
 * required for the specified dataset, dataset_id. For chunked
 * datasets, this is the number of allocated chunks times the chunk
 * size. The return value may be zero if no data has been stored.</p>
 *
 * @param dset_id Identifier of the dataset to query.
 *
 * @return Returns the amount of storage space allocated for the
 * dataset, not counting meta data; otherwise returns 0 (zero).
 */
%apply long long {hsize_t}
hsize_t H5Dget_storage_size(hid_t dset_id);
%clear hsize_t;

/**
 * Returns an identifier for a copy of the datatype for a dataset.
 *
 * <p>H5Dget_type returns an identifier for a copy of the datatype for
 * a dataset. The datatype should be released with the H5Tclose
 * function.</p>
 *
 * <p>If a dataset has a named datatype, then an identifier to the
 * opened datatype is returned. Otherwise, the returned datatype is
 * read-only. If atomization of the datatype fails, then the datatype
 * is closed.</p>
 *
 * @param dset_id Identifier of the dataset to query.
 *
 * @return Returns a datatype identifier if successful; otherwise
 * returns a negative value.
 */
hid_t H5Dget_type(hid_t dset_id);

//herr_t H5Diterate(void *buf, hid_t type_id, hid_t space_id,
//                  H5D_operator_t op, void *operator_data);

/**
 * Opens an existing dataset.
 *
 * <p>H5Dopen1 opens an existing dataset for access in the file or
 * group specified in loc_id. name is a dataset name and is used to
 * identify the dataset in the file.</p>
 *
 * @param loc_id Identifier of the file or group within which the
 * dataset to be accessed will be found.
 * @param name The name of the dataset to access.
 *
 * @return Returns a dataset identifier if successful; otherwise
 * returns a negative value.
 * @deprecated
 */
%javamethodmodifiers H5Dopen1 "@Deprecated\n   public"
hid_t H5Dopen1(hid_t loc_id, const char *name);

/**
 * Opens an existing dataset.
 *
 * <p>H5Dopen2 opens the existing dataset specified by a location
 * identifier and name, loc_id and name, respectively.</p>
 *
 * <p>The dataset access property list, dapl_id, provides information
 * regarding access to the dataset.</p>
 *
 * <p>To conserve and release resources, the dataset should be closed
 * when access is no longer required.</p>
 *
 * @param loc_id Location identifier.
 * @param name Dataset name.
 * @param dapl_id Dataset access property list.
 *
 * @return Returns a dataset identifier if successful; otherwise
 * returns a negative value.
 */
hid_t H5Dopen2(hid_t loc_id, const char *name, hid_t dapl_id);

/**
 * Reads raw data from a dataset into a buffer.
 *
 * <p>H5Dread reads a (partial) dataset, specified by its identifier
 * dataset_id, from the file into an application memory buffer
 * buf. Data transfer properties are defined by the argument
 * xfer_plist_id. The memory datatype of the (partial) dataset is
 * identified by the identifier mem_type_id. The part of the dataset
 * to read is defined by mem_space_id and file_space_id.</p>
 *
 * <p>file_space_id is used to specify only the selection within the
 * file dataset's dataspace. Any dataspace specified in file_space_id
 * is ignored by the library and the dataset's dataspace is always
 * used. file_space_id can be the constant H5S_ALL. which indicates
 * that the entire file dataspace, as defined by the current
 * dimensions of the dataset, is to be selected.</p>
 *
 * <p>mem_space_id is used to specify both the memory dataspace and
 * the selection within that dataspace. mem_space_id can be the
 * constant H5S_ALL, in which case the file dataspace is used for the
 * memory dataspace and the selection defined with file_space_id is
 * used for the selection within that dataspace.</p>
 *
 * <p>If raw data storage space has not been allocated for the dataset
 * and a fill value has been defined, the returned buffer buf is
 * filled with the fill value.</p>
 *
 * <p>The behavior of the library for the various combinations of
 * valid dataspace identifiers and H5S_ALL for the mem_space_id and
 * the file_space_id parameters is described below: </p>
 *
 <table border=0>
 <tr><th>mem_space_id</th><th>file_space_id</th><th>Behavior</th></tr>	
 <tr valign="top">
    <td>valid dataspace identifier</td>
    <td>valid dataspace identifier</td>
    <td>mem_space_idspecifies the memory dataspace and the selection
    within it. file_space_id specifies the selection within the file
    dataset's dataspace.</td>
 </tr>
 <tr valign="top">
    <td><code>H5S_ALL</code></td>
    <td>valid dataspace identifier</td>
    <td>The file dataset's dataspace is used for the memory dataspace
    and the selection specified with <code>file_space_id</code>
    specifies the selection within it. The combination of the file
    dataset's dataspace and the selection from file_space_id is used
    for memory also.</td>
 </tr>
 <tr valign="top">
    <td>valid dataspace identifier</td>
    <td><code>H5S_ALL</code></td>
    <td>mem_space_id specifies the memory dataspace and the selection
    within it. The selection within the file dataset's dataspace is
    set to the "all" selection.</td>
 </tr>
 <tr valign="top">
    <td><code>H5S_ALL</code></td>
    <td><code>H5S_ALL</code></td>
    <td>The file dataset's dataspace is used for the memory dataspace
    and the selection within the memory dataspace is set to the "all"
    selection. The selection within the file dataset's dataspace is
    set to the "all" selection.</td>
 </tr>
 </table>
 *
 * <p>Setting an H5S_ALL selection indicates that the entire
 * dataspace, as defined by the current dimensions of a dataspace,
 * will be selected. The number of elements selected in the memory
 * dataspace must match the number of elements selected in the file
 * dataspace.</p>
 *
 * <p>xfer_plist_id can be the constant H5P_DEFAULT. in which case the
 * default data transfer properties are used.</p>
 *
 * <p>Data is automatically converted from the file datatype and
 * dataspace to the memory datatype and dataspace at the time of the
 * read. See the Data Conversion section of The Data Type Interface
 * (H5T) in the HDF5 User's Guide for a discussion of data conversion,
 * including the range of conversions currently supported by the HDF5
 * libraries. </p>
 *
 * @param dataset_id Identifier of the dataset read from.
 * @param mem_type_id Identifier of the memory datatype.
 * @param mem_space_id Identifier of the memory dataspace.
 * @param file_space_id Identifier of the dataset's dataspace in the file.
 * @param xfer_plist_id Identifier of a transfer property list for
 * this I/O operation.
 * @param buf (Output) Buffer to receive data read from file.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply void* BUFF {void* buf}
herr_t H5Dread(hid_t dset_id, hid_t mem_type_id, hid_t mem_space_id,
	       hid_t file_space_id, hid_t plist_id, void *buf/*out*/);
%clear void* buf;


/**
 * Changes the sizes of a dataset's dimensions.
 *
 * <p>H5Dset_extent sets the dimensions of the dataset dset_id to the
 * sizes specified in size.</p>
 *
 * <p>The dimensionality of size must be the same as that of the
 * dataset's current dataspace.</p>
 *
 * <p>This function can be applied to the following datasets:</p>
 <ul>
  <li>Any dataset with unlimited dimensions</li>
  <li>A dataset with fixed dimensions if the new dimension sizes are
  less than the maximum sizes set with maxdims (see
  H5Screate_simple)</li>
 *
 * <p>Space on disk is immediately allocated for the new dataset
 * extent if the dataset's space allocation time is set to
 * H5D_ALLOC_TIME_EARLY. Fill values will be written to the dataset if
 * the dataset's fill time is set to H5D_FILL_TIME_IFSET or
 * H5D_FILL_TIME_ALLOC. (See H5Pset_fill_time and H5Pset_alloc_time.)
 *
 * <p>Note:<br/> If the sizes specified in size are smaller than the
 * dataset's current dimension sizes, H5Dset_extent will reduce the
 * dataset's dimension sizes to the specified values. It is the user's
 * responsibility to ensure that valuable data is not lost;
 * H5Dset_extent does not check.</p>
 *
 * <p>If it is necessary to ensure that current dimension sizes are
 * not reduced, the function H5Dextend can be used. </p>
 *
 * @param dset_id Dataset identifier.
 * @param size Array containing the new magnitude of each dimension of
 * the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long INPUT[] {const hsize_t* size}
herr_t H5Dset_extent(hid_t dset_id, const hsize_t *size);
%clear hsize_t* size;

/**
 * Determines the number of bytes required to store VL data.
 *
 * <p>H5Dvlen_get_buf_size determines the number of bytes required to
 * store the VL data from the dataset, using the space_id for the
 * selection in the dataset on disk and the type_id for the memory
 * representation of the VL data in memory.</p>
 *
 * <p>size is returned with the number of bytes required to store the
 * VL data in memory. </p>
 *
 * @param dataset_id Identifier of the dataset to query.
 * @param type_id Datatype identifier.
 * @param space_id Dataspace identifier.
 * @param size (Output) The size in bytes of the memory buffer
 * required to store the VL data.
 *
 * @return Returns non-negative value if successful; otherwise returns
 * a negative value.
 */
%apply  long long* OUTPUT {hsize_t* size}
herr_t H5Dvlen_get_buf_size(hid_t dataset_id, hid_t type_id, hid_t space_id, hsize_t *size);
%clear hsize_t* size;

/*nodoc*
 * Reclaims VL datatype memory buffers.
 *
 * <p>H5Dvlen_reclaim reclaims memory buffers created to store VL
 * datatypes.</p>
 *
 * <p>The type_id must be the datatype stored in the buffer. The
 * space_id describes the selection for the memory buffer to free the
 * VL datatypes within. The plist_id is the dataset transfer property
 * list which was used for the I/O transfer to create the buffer. And
 * buf is the pointer to the buffer to be reclaimed.</p>
 *
 * <p>The VL structures (hvl_t) in the user's buffer are modified to
 * zero out the VL information after the memory has been
 * reclaimed.</p>
 *
 * <p>If nested VL datatypes were used to create the buffer, this
 * routine frees them from the bottom up, releasing all the memory
 * without creating memory leaks. </p>
 *
 * @param type_id Identifier of the datatype.
 * @param space_id Identifier of the dataspace.
 * @param plist_id Identifier of the property list used to create the buffer.
 * @param buf Pointer to the buffer to be reclaimed.
 *
 * @return Returns non-negative value if successful; otherwise returns
 * a negative value.
 */
//%apply void* BUFF {void* buf}
//herr_t H5Dvlen_reclaim(hid_t type_id, hid_t space_id, hid_t plist_id, void *buf);

/**
 * Writes raw data from a buffer to a dataset.
 *
 * <p>H5Dwrite writes a (partial) dataset, specified by its identifier
 * dataset_id, from the application memory buffer buf into the
 * file. Data transfer properties are defined by the argument
 * xfer_plist_id. The memory datatype of the (partial) dataset is
 * identified by the identifier mem_type_id. The part of the dataset
 * to write is defined by mem_space_id and file_space_id.</p>
 *
 * <p>file_space_id is used to specify only the selection within the
 * file dataset's dataspace. Any dataspace specified in file_space_id
 * is ignored by the library and the dataset's dataspace is always
 * used. file_space_id can be the constant H5S_ALL. which indicates
 * that the entire file dataspace, as defined by the current
 * dimensions of the dataset, is to be selected.</p>
 *
 * <p>mem_space_id is used to specify both the memory dataspace and
 * the selection within that dataspace. mem_space_id can be the
 * constant H5S_ALL, in which case the file dataspace is used for the
 * memory dataspace and the selection defined with file_space_id is
 * used for the selection within that dataspace.</p>
 *
 * <p>The behavior of the library for the various combinations of
 * valid dataspace IDs and H5S_ALL for the mem_space_id and the
 * file_space_id parameters is described below: </p>
 *
 <table border=0>
 <tr><th>mem_space_id</th><th>file_space_id</th><th>Behavior</th></tr>	
 <tr valign="top">
    <td>valid dataspace identifier</td>
    <td>valid dataspace identifier</td>
    <td>mem_space_idspecifies the memory dataspace and the selection
    within it. file_space_id specifies the selection within the file
    dataset's dataspace.</td>
 </tr>
 <tr valign="top">
    <td><code>H5S_ALL</code></td>
    <td>valid dataspace identifier</td>
    <td>The file dataset's dataspace is used for the memory dataspace
    and the selection specified with <code>file_space_id</code>
    specifies the selection within it. The combination of the file
    dataset's dataspace and the selection from file_space_id is used
    for memory also.</td>
 </tr>
 <tr valign="top">
    <td>valid dataspace identifier</td>
    <td><code>H5S_ALL</code></td>
    <td>mem_space_id specifies the memory dataspace and the selection
    within it. The selection within the file dataset's dataspace is
    set to the "all" selection.</td>
 </tr>
 <tr valign="top">
    <td><code>H5S_ALL</code></td>
    <td><code>H5S_ALL</code></td>
    <td>The file dataset's dataspace is used for the memory dataspace
    and the selection within the memory dataspace is set to the "all"
    selection. The selection within the file dataset's dataspace is
    set to the "all" selection.</td>
 </tr>
 </table>
 *
 * <p>Setting an "all" selection indicates that the entire dataspace,
 * as defined by the current dimensions of a dataspace, will be
 * selected. The number of elements selected in the memory dataspace
 * must match the number of elements selected in the file dataspace.</p>
 *
 * <p>xfer_plist_id can be the constant H5P_DEFAULT. in which case the
 * default data transfer properties are used.</p>
 *
 * <p>Writing to an dataset will fail if the HDF5 file was not opened
 * with write access permissions.</p>
 *
 * <p>Data is automatically converted from the memory datatype and
 * dataspace to the file datatype and dataspace at the time of the
 * write. See the Data Conversion section of The Data Type Interface
 * (H5T) in the HDF5 User's Guide for a discussion of data conversion,
 * including the range of conversions currently supported by the HDF5
 * libraries.</p>
 *
 * <p>If the dataset's space allocation time is set to
 * H5D_ALLOC_TIME_LATE or H5D_ALLOC_TIME_INCR and the space for the
 * dataset has not yet been allocated, that space is allocated when
 * the first raw data is written to the dataset. Unused space in the
 * dataset will be written with fill values at the same time if the
 * dataset's fill time is set to H5D_FILL_TIME_IFSET or
 * H5D_FILL_TIME_ALLOC. (Also see H5Pset_fill_time and
 * H5Pset_alloc_time.)</p>
 *
 * <p>If a dataset's storage layout is 'compact', care must be taken
 * when writing data to the dataset in parallel. A compact dataset's
 * raw data is cached in memory and may be flushed to the file from
 * any of the parallel processes, so parallel applications should
 * always attempt to write identical data to the dataset from all
 * processes. </p>
 *
 * @param dataset_id Identifier of the dataset to write to.
 * @param mem_type_id Identifier of the memory datatype.
 * @param mem_space_id Identifier of the memory dataspace.
 * @param file_space_id Identifier of the dataset's dataspace in the file.
 * @param xfer_plist_id Identifier of a transfer property list for
 * this I/O operation.
 * @param buf Buffer with data to be written to the file.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply void* BUFF {void* buf}
herr_t H5Dwrite(hid_t dset_id, hid_t mem_type_id, hid_t mem_space_id,
		hid_t file_space_id, hid_t plist_id, const void *buf);
%clear void* buf;


/**
 * Writes a dataset of string type.
 *
 * @see H5Dwrite
 */
%rename(H5Dwrite_strings) H5Dwrite;
%apply void* HSTRARR_INPUT {void* buf}
herr_t H5Dwrite(hid_t dset_id, hid_t mem_type_id, hid_t mem_space_id,
		hid_t file_space_id, hid_t plist_id, const void *buf);
%clear void* buf;



/**
 * Reads a dataset of string type.
 *
 * @see H5Dread
 */
%typemap(jtype) jobjectArray JBUFF "String[]"
%typemap(jstype) jobjectArray JBUFF "String[]"
%apply jobjectArray JBUFF {jobjectArray jbuff}
%native (H5Dread_strings) int H5Dread_strings(
	hid_t jdset_id, hid_t jmem_type_id, 
	hid_t jmem_space_id, hid_t jfile_space_id, 
	hid_t jplist_id, jobjectArray jbuff/*out*/);
%{
SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibJNI_H5Dread_1strings(
	JNIEnv *jenv, jclass jcls, 
	jint jdset_id, jint jmem_type_id, 
	jint jmem_space_id, jint jfile_space_id, 
	jint jplist_id, jobjectArray jbuff/*out*/
	) {
  
		jint jresult = 0 ;
		herr_t result;

		hid_t dset_id ;
		hid_t mem_type_id ;
		hid_t mem_space_id ;
		hid_t file_space_id ;
		hid_t plist_id ;

		void *cbuff = (void *) 0 ;
		jint jsize;
		hsize_t csize;
		int n;
		char* cword;
		jstring jword;
		
		dset_id = (hid_t) jdset_id; 
		mem_type_id = (hid_t) jmem_type_id; 
		mem_space_id = (hid_t) jmem_space_id; 
		file_space_id = (hid_t) jfile_space_id; 
		plist_id = (hid_t) jplist_id; 

		jsize = (*jenv)->GetArrayLength(jenv, jbuff);
		if (jsize < 1) {
			SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Array must have non-zero width.");		
			return(-1);
		}
		
		csize = H5Sget_select_npoints(mem_space_id);
		if (csize < 0) {
			SWIG_JavaThrowException(jenv, SWIG_JavaRuntimeException, "Cannot get size of datatype.");	
			return (-1);
		}
		cbuff = calloc((size_t)csize+1, sizeof(char*)); // +1 to guarrantee null termination.
		
		result = (herr_t)H5Dread(dset_id, mem_type_id, mem_space_id, file_space_id, plist_id, cbuff);
		jresult = (jint)result; 
		
		if (result < 0) {
		   	H5Dvlen_reclaim(mem_type_id, mem_space_id, plist_id, cbuff);
			free(cbuff);
			return jresult;			
		}
		
		cword = ((char**) cbuff)[0];
		for (n=0; n<jsize; n++) {
			jword = (*jenv)->NewStringUTF(jenv, cword);
			(*jenv)->SetObjectArrayElement(jenv, jbuff, n, jword);
			cword = ((char**) cbuff)[n+1];
		}
	   	H5Dvlen_reclaim(mem_type_id, mem_space_id, plist_id, cbuff);	
		free(cbuff);	
		return jresult;
}
  %}

%clear jobjectArray jbuff;
