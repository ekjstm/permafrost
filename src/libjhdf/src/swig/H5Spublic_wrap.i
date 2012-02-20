%module DataspaceLib
%include "typemaps.i"

%include "H5types.i"
%include "H5Stypes.i"
%include "buffers.i"

%header %{
#include "H5Spublic.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"

%include "harrays2d.i"

/**
 * Releases and terminates access to a dataspace.
 *
 * <p>H5Sclose releases a dataspace. Further access through the
 * dataspace identifier is illegal. Failure to release a dataspace
 * with this call will result in resource leaks.</p>
 *
 * @param space_id Identifier of dataspace to release.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Sclose(hid_t space_id);

/**
 * Creates an exact copy of a dataspace.
 *
 * <p>H5Scopy creates a new dataspace which is an exact copy of the
 * dataspace identified by space_id. The dataspace identifier returned
 * from this function should be released with H5Sclose or resource
 * leaks will occur.</p>
 *
 * @param  space_id Identifier of dataspace to copy.
 *
 * @return Returns a dataspace identifier if successful; otherwise
 * returns a negative value.
 */
hid_t H5Scopy(hid_t space_id);

/**
 * Creates a new dataspace of a specified type.
 *
 * <p>H5Screate creates a new dataspace of a particular
 * type. Currently supported types are as follows:</p>
 <ul>
    <li>H5S_SCALAR</li>
    <li>H5S_SIMPLE</li>
    <li>H5S_NULL</li>
 </ul>
 * <p>Further dataspace types may be added later.</p>
 *
 * <p>A scalar dataspace, H5S_SCALAR, has a single element, though
 * that element may be of a complex datatype, such as a compound or
 * array datatype. A simple dataspace, H5S_SIMPLE, consists of a
 * regular array of elements. A null dataspace, H5S_NULL, has no data
 * elements. </p>
 *
 * @param type The type of dataspace to be created.
 *
 * @return Returns a dataspace identifier if successful; otherwise
 * returns a negative value.
 */
hid_t H5Screate(H5S_class_t type);

/**
 * Creates a new simple dataspace and opens it for access.
 *
 * <p>H5Screate_simple creates a new simple dataspace and opens it for
 * access.</p>
 *
 * <p>rank is the number of dimensions used in the dataspace.</p>
 *
 * <p>dims is an array specifying the size of each dimension of the
 * dataset while maxdims is an array specifying the upper limit on the
 * size of each dimension. maxdims may be the null pointer, in which
 * case the upper limit is the same as dims.</p>
 *
 * <p>If an element of maxdims is H5S_UNLIMITED, (-1), the maximum
 * size of the corresponding dimension is unlimited. Otherwise, no
 * element of maxdims should be smaller than the corresponding element
 * of dims.</p>
 *
 * <p>The dataspace identifier returned from this function must be
 * released with H5Sclose or resource leaks will occur. </p>
 *
 * @param rank Number of dimensions of dataspace. 
 * @param dims An array of the size of each dimension.
 * @param maxdims An array of the maximum size of each dimension.
 *
 * @return Returns a dataspace identifier if successful; otherwise
 * returns a negative value.
 */
%apply long long INPUT[] {const hsize_t dims[]}
%apply long long INPUT[] {const hsize_t maxdims[]}
hid_t H5Screate_simple(int rank, const hsize_t dims[],
			         const hsize_t maxdims[]);
%clear const hsize_t dims[];
%clear const hsize_t maxdims[];

/**
 * Decode a binary object description of data space and return a new
 * object handle.
 *
 * <p>Given an object description of data space in binary in a buffer,
 * H5Sdecode reconstructs the HDF5 data type object and returns a new
 * object handle for it. The binary description of the object is
 * encoded by H5Sencode. User is responsible for passing in the right
 * buffer. The types of data space we address in this function are
 * null, scalar, and simple space. For simple data space, the
 * information of selection, for example, hyperslab selection, is also
 * encoded and decoded. Complex data space has not been implemented in
 * the library.</p>
 *
 * @param buf Buffer for the data space object to be decoded.
 *
 * @return Returns an object ID(non-negative) if successful; otherwise
 * returns a negative value.
 */
%apply void* BUFF {const void* buf}
hid_t H5Sdecode(const void *buf);
%clear const void* buf;

/**
 * Encode a data space object description into a binary buffer.
 *
 * <p>Given the data space ID, H5Sencode converts a data space
 * description into binary form in a buffer. Using this binary form in
 * the buffer, a data space object can be reconstructed using
 * H5Sdecode to return a new object handle(hid_t) for this data
 * space.</p>
 *
 * <p>A preliminary H5Sencode call can be made to find out the size of
 * the buffer needed. This value is returned as nalloc. That value can
 * then be assigned to nalloc for a second H5Sencode call, which will
 * retrieve the actual encoded object.</p>
 *
 * <p>If the library finds out nalloc is not big enough for the
 * object, it simply returns the size of the buffer needed through
 * nalloc without encoding the provided buffer.</p>
 *
 * <p>The types of data space we address in this function are null,
 * scalar, and simple space. For simple data space, the information of
 * selection, for example, hyperslab selection, is also encoded and
 * decoded. Complex data space has not been implemented in the
 * library. </p>
 *
 * @param obj_id Identifier of the object to be encoded.
 * @param buf (Output) Buffer for the object to be encoded into. If
 * the provided buffer is NULL, only the size of buffer needed is
 * returned through nalloc.
 * @param nalloc (Input/Output) The size of the allocated
 * buffer. Returns the size of the buffer needed.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply void* BUFF {void* buf}
%apply unsigned int INOUT[] {size_t* nalloc}
herr_t H5Sencode(hid_t obj_id, void *buf, size_t *nalloc);
%clear void* buf;
%clear size_t* nalloc;

/**
 * Copies the extent of a dataspace.
 *
 * <p>H5Sextent_copy copies the extent from dst_id to
 * src_id. This action may change the type of the
 * dataspace.</p>
 *
 * @param dst_id The identifier for the dataspace to which the
 * extent is copied.
 * @param src_id The identifier for the dataspace from which
 * the extent is copied.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Sextent_copy(hid_t dst_id, hid_t src_id);

/**
 * Determines whether two dataspace extents are equal.
 *
 * <p>H5Sextent_equal determines whether the dataspace extents of two
 * dataspaces, space1_id and space2_id, are equal.</p>
 *
 * @param space1_id First dataspace identifier.
 * @param space2_id Second dataspace identifier.
 *
 * @return Returns TRUE if equal, FALSE if unequal, if successful;
 * otherwise returns a negative value.
 */
htri_t H5Sextent_equal(hid_t space1_id, hid_t space2_id);

/**
 * Gets the bounding box containing the current selection.
 *
 * <p>H5Sget_select_bounds retrieves the coordinates of the bounding
 * box containing the current selection and places them into
 * user-supplied buffers.</p>
 *
 * <p>The start and end buffers must be large enough to hold the
 * dataspace rank number of coordinates.</p>
 *
 * <p>The bounding box exactly contains the selection. I.e., if a
 * 2-dimensional element selection is currently defined as containing
 * the points (4,5), (6,8), and (10,7), then the bounding box will be
 * (4, 5), (10, 8).</p>
 *
 * <p>The bounding box calculation includes the current offset of the
 * selection within the dataspace extent.</p>
 *
 * <p>Calling this function on a none selection will return FAIL. </p>
 *
 * @param space_id Identifier of dataspace to query.
 * @param start (Output) Starting coordinates of the bounding box.
 * @param end (Output) Ending coordinates of the bounding box, i.e.,
 * the coordinates of the diagonally opposite corner.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long OUTPUT[] {hsize_t* start}
%apply long long OUTPUT[] {hsize_t* end}
herr_t H5Sget_select_bounds(hid_t space_id, hsize_t* start, hsize_t* end);
%clear hsize_t* start;
%clear hsize_t* end;

/**
 * Gets the number of element points in the current selection.
 *
 * <p>H5Sget_select_elem_npoints returns the number of element points
 * in the current dataspace selection.</p>
 *
 * @param space_id Identifier of dataspace to query.
 *
 * @return Returns the number of element points in the current
 * dataspace selection if successful. Otherwise returns a negative
 * value.
 */
hssize_t H5Sget_select_elem_npoints(hid_t space_id);

/**
 * Gets the list of element points currently selected.
 *
 * <p>H5Sget_select_elem_pointlist returns the list of element points
 * in the current dataspace selection. Starting with the startpoint-th
 * point in the list of points, numpoints points are put into the
 * user's buffer. If the user's buffer fills up before numpoints
 * points are inserted, the buffer will contain only as many points as
 * fit.</p>
 *
 * <p>The element point coordinates have the same dimensionality
 * (rank) as the dataspace they are located within. The list of
 * element points is formatted as follows:</p>
 *
 * <p>(c1, c2, c3, ..., cn), for the first coordinate C, followed by
 * the next coordinate, and the next, etc. until all of the selected
 * element points have been listed.</p>
 *
 * <p>The points are returned in the order they will be iterated
 * through when the selection is read/written from/to disk. </p>
 *
 * @param space_id Dataspace identifier of selection to query.
 * @param startpoint Element point to start with.
 * @param numpoints Number of element points to get.
 * @param buf (Output) List of element points selected.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long {hsize_t startpoint}
%apply long long {hsize_t numpoints}
%apply long long* BUFF {hsize_t* buf}
herr_t H5Sget_select_elem_pointlist(hid_t spaceid, hsize_t startpoint, hsize_t numpoints, hsize_t* buf);
%clear hsize_t startpoint;
%clear hsize_t numpoints;
%clear hsize_t* buf;

/**
 * Gets the list of hyperslab blocks currently selected.
 *
 * <p>H5Sget_select_hyper_blocklist returns a list of the hyperslab
 * blocks currently selected. Starting with the startblock-th block in
 * the list of blocks, numblocks blocks are put into the user's
 * buffer. If the user's buffer fills up before numblocks blocks are
 * inserted, the buffer will contain only as many blocks as fit.</p>
 *
 * <p>The block coordinates have the same dimensionality (rank) as the
 * dataspace they are located within. The list of blocks is formatted
 * as follows:</p>
 *
 * <p>(a1, a2, a3, ..., an), for the start coordinate A, immediately
 * followed by (b1, b2, b3, ..., bn) for the opposite corner
 * coordinate B, followed by the next start and opposite coordinates,
 * and the next pair, etc. until all of the selected blocks have been
 * listed.</p>
 *
 * <p>No guarantee is implied as the order in which blocks are
 * listed. </p>
 *
 * @param space_id Dataspace identifier of selection to query.
 * @param startblock Hyperslab block to start with.
 * @param numblocks Number of hyperslab blocks to get.
 * @param buf (Output) List of hyperslab blocks selected.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long {hsize_t startblock}
%apply long long {hsize_t numblocks}
%apply long long* BUFF {hsize_t* buf}
herr_t H5Sget_select_hyper_blocklist(hid_t space_id, hsize_t startblock, hsize_t numblocks, hsize_t *buf);
%clear hsize_t startblock;
%clear hsize_t numblocks;
%clear hsize_t* buf;

/**
 * Get number of hyperslab blocks.
 *
 * <p>H5Sget_select_hyper_nblocks returns the number of hyperslab
 * blocks in the current dataspace selection.</p>
 *
 * @param space_id Identifier of dataspace to query.
 *
 * @return Returns the number of hyperslab blocks in the current
 * dataspace selection if successful. Otherwise returns a negative
 * value.
 */
hssize_t H5Sget_select_hyper_nblocks(hid_t space_id);

/**
 * Determines the number of elements in a dataspace selection.
 *
 * <p>H5Sget_select_npoints determines the number of elements in the
 * current selection of a dataspace.</p>
 *
 * @param space_id Dataspace identifier.
 *
 * @return Returns the number of elements in the selection if
 * successful; otherwise returns a negative value.
 */
hssize_t H5Sget_select_npoints(hid_t space_id);

/**
 * Determines the type of the dataspace selection.
 *
 * <p>H5Sget_select_type retrieves the type of selection currently
 * defined for the dataspace space_id.</p>
 *
 * @param space_id Dataspace identifier.
 *
 * @return Returns the dataspace selection type, a value of the
 * enumerated datatype H5S_sel_type, if successful. Otherwise returns
 * a negative value.
 */
H5S_sel_type H5Sget_select_type(hid_t space_id);

/**
 * Retrieves dataspace dimension size and maximum size.
 *
 * <p>H5Sget_simple_extent_dims returns the size and maximum sizes of
 * each dimension of a dataspace through the dims and maxdims
 * parameters.</p>
 *
 * <p>Either or both of dims and maxdims may be NULL.</p>
 *
 * <p>If a value in the returned array maxdims is H5S_UNLIMITED (-1),
 * the maximum size of that dimension is unlimited. </p>
 *
 * @param space_id Identifier of the dataspace object to query.
 * @param dims (Output) Pointer to array to store the size of each dimension.
 * @param maxdims (Output) Pointer to array to store the maximum size
 * of each dimension.
 *
 * @return Returns the number of dimensions in the dataspace if
 * successful; otherwise returns a negative value.
 */
%apply long long OUTPUT[] {hsize_t dims[]}
%apply long long OUTPUT[] {hsize_t maxdims[]}
int H5Sget_simple_extent_dims(hid_t space_id, hsize_t dims[],
				              hsize_t maxdims[]);
%clear hsize_t dims[];
%clear hsize_t maxdims[];
/**
 * Determines the dimensionality of a dataspace.
 *
 * <p>H5Sget_simple_extent_ndims determines the dimensionality (or
 * rank) of a dataspace.</p>
 *
 * @param space_id Identifier of the dataspace.
 *
 * @return Returns the number of dimensions in the dataspace if
 * successful; otherwise returns a negative value.
 */
int H5Sget_simple_extent_ndims(hid_t space_id);

/**
 * Determines the number of elements in a dataspace.
 *
 * <p>H5Sget_simple_extent_npoints determines the number of elements
 * in a dataspace. For example, a simple 3-dimensional dataspace with
 * dimensions 2, 3, and 4 would have 24 elements.</p>
 *
 * @param space_id Identifier of the dataspace object to query.
 *
 * @return Returns the number of elements in the dataspace if
 * successful; otherwise returns 0.
 */
hssize_t H5Sget_simple_extent_npoints(hid_t space_id);

/**
 * Determine the current class of a dataspace.
 *
 * <p>H5Sget_simple_extent_type queries a dataspace to determine the
 * current class of a dataspace.</p>
 *
 * <p>The function returns a class name, one of the following:
 * H5S_SCALAR, H5S_SIMPLE, or H5S_NONE. </p>
 *
 * @param space_id Dataspace identifier.
 *
 * @return Returns a dataspace class name if successful; otherwise
 * H5S_NO_CLASS (-1).
 */
H5S_class_t H5Sget_simple_extent_type(hid_t space_id);

/**
 * Determines whether a dataspace is a simple dataspace.
 *
 * <p>H5Sis_simple determines whether a dataspace is a simple
 * dataspace. [Currently, all dataspace objects are simple dataspaces,
 * complex dataspace support will be added in the future]</p>
 *
 * @param space_id Identifier of the dataspace to query.
 *
 * @return When successful, returns a positive value, for TRUE, or 0
 * (zero), for FALSE. Otherwise returns a negative value.
 */
htri_t H5Sis_simple(hid_t space_id);

/**
 * Sets the offset of a simple dataspace.
 *
 * <p>H5Soffset_simple sets the offset of a simple dataspace
 * space_id. The offset array must be the same number of elements as
 * the number of dimensions for the dataspace. If the offset array is
 * set to NULL, the offset for the dataspace is reset to 0.</p>
 *
 * <p>This function allows the same shaped selection to be moved to
 * different locations within a dataspace without requiring it to be
 * redefined. </p>
 *
 * @param space_id The identifier for the dataspace object to reset.
 * @param offset The offset at which to position the selection.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long INPUT[] {const hssize_t* offset}
herr_t H5Soffset_simple(hid_t space_id, const hssize_t* offset);
%clear const hssize_t* offset;

/**
 * Selects the entire dataspace.
 *
 * <p>H5Sselect_all selects the entire extent of the dataspace
 * space_id.</p>
 *
 * <p>More specifically, H5Sselect_all selects the special
 * 5S_SELECT_ALL region for the dataspace space_id. H5S_SELECT_ALL
 * selects the entire dataspace for any dataspace it is applied
 * to. </p>
 *
 * @param space_id The identifier for the dataspace in which the
 * selection is being made.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Sselect_all(hid_t space_id);


/**
 * Selects array elements to be included in the selection for a dataspace.
 *
 * <p>H5Sselect_elements selects array elements to be included in the
 * selection for the space_id dataspace.</p>
 *
 * <p>The number of elements selected is set in the num_elements
 * parameter.</p>
 *
 * <p>The coord array is a two-dimensional array of size
 * dataspace_rank by num_elements containing a list of of zero-based
 * values specifying the coordinates in the dataset of the selected
 * elements. The order of the element coordinates in the coord array
 * specifies the order in which the array elements are iterated
 * through when I/O is performed. Duplicate coordinate locations are
 * not checked for.</p>
 *
 * <p>The selection operator op determines how the new selection is to
 * be combined with the previously existing selection for the
 * dataspace. The following operators are supported:</p>
 <dl>
    <dt>H5S_SELECT_SET</dt>
    <dd>Replaces the existing selection with the parameters from this
    call. Overlapping blocks are not supported with this
    operator. Adds the new selection to the existing selection.</dd>
    <dt>H5S_SELECT_APPEND</dt>
    <dd>Adds the new selection following the last element of the
    existing selection.</dd>
    <dt>H5S_SELECT_PREPEND</dt>
    <dd>Adds the new selection preceding the first element of the
    existing selection.</dd>
 </dl>
 *
 * @param space_id Identifier of the dataspace.
 * @param op Operator specifying how the new selection is to be
 * combined with the existing selection for the dataspace.
 * @param num_elements Number of elements to be selected.
 * @param coord A 2-dimensional array of 0-based values specifying the
 * coordinates of the elements being selected.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long CONTIG_IN[][] {const hsize_t* coord}
herr_t H5Sselect_elements(hid_t space_id, H5S_seloper_t op,
				          size_t num_elements,
				          const hsize_t *coord);

/**
 * Selects a hyperslab region to add to the current selected region.
 *
 * <p>H5Sselect_hyperslab selects a hyperslab region to add to the
 * current selected region for the dataspace specified by
 * space_id.</p>
 *
 * <p>The start, stride, count, and block arrays must be the same size
 * as the rank of the dataspace.</p>
 *
 * <p>The selection operator op determines how the new selection is to
 * be combined with the already existing selection for the
 * dataspace. The following operators are supported:</p>
 <dl>
    <dt>H5S_SELECT_SET</dt>
    <dd>Replaces the existing selection with the parameters from this
    call. Overlapping blocks are not supported with this
    operator.</dd>
    <dt>H5S_SELECT_OR</dt>
    <dd>Adds the new selection to the existing selection.  (Binary
    OR)</dd>
    <dt>H5S_SELECT_AND</dt>
    <dd>Retains only the overlapping portions of the new selection and
    the existing selection.  (Binary AND)</dd>
    <dt>H5S_SELECT_XOR</dt>
    <dd>Retains only the elements that are members of the new
    selection or the existing selection, excluding elements that are
    members of both selections.  (Binary exclusive-OR, XOR)</dd>
    <dt>H5S_SELECT_NOTB</dt>
    <dd>Retains only elements of the existing selection that are not
    in the new selection.</dd>
    <dt>H5S_SELECT_NOTA</dt>
    <dd>Retains only elements of the new selection that are not in the
    existing selection.</dd>
 </dl>
 *
 * <p>The start array determines the starting coordinates of the
 * hyperslab to select.</p>
 *
 * <p>The stride array chooses array locations from the dataspace with
 * each value in the stride array determining how many elements to
 * move in each dimension. Setting a value in the stride array to 1
 * moves to each element in that dimension of the dataspace; setting a
 * value of 2 in alocation in the stride array moves to every other
 * element in that dimension of the dataspace. In other words, the
 * stride determines the number of elements to move from the start
 * location in each dimension. Stride values of 0 are not allowed. If
 * the stride parameter is NULL, a contiguous hyperslab is selected
 * (as if each value in the stride array were set to all 1's).</p>
 *
 * <p>The count array determines how many blocks to select from the
 * dataspace, in each dimension.</p>
 *
 * <p>The block array determines the size of the element block
 * selected from the dataspace. If the block parameter is set to NULL,
 * the block size defaults to a single element in each dimension (as
 * if the block array were set to all 1's).</p>
 *
 * <p>For example, in a 2-dimensional dataspace, setting start to
 * [1,1], stride to [4,4], count to [3,7], and block to [2,2] selects
 * 21 2x2 blocks of array elements starting with location (1,1) and
 * selecting blocks at locations (1,1), (5,1), (9,1), (1,5), (5,5),
 * etc.</p>
 *
 * <p>Regions selected with this function call default to C order
 * iteration when I/O is performed. </p>
 *
 * @param space_id Identifier of dataspace selection to modify.
 * @param op Operation to perform on current selection.
 * @param start Offset of start of hyperslab.
 * @param count Number of blocks included in hyperslab.
 * @param stride Hyperslab stride.
 * @param block Size of block in hyperslab.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long INPUT[] {const hsize_t start[]}
%apply long long INPUT[] {const hsize_t stride[]}
%apply long long INPUT[] {const hsize_t count[]}
%apply long long INPUT[] {const hsize_t block[]}
herr_t H5Sselect_hyperslab(hid_t space_id, H5S_seloper_t op,
				   const hsize_t start[],
				   const hsize_t stride[],
				   const hsize_t count[],
				   const hsize_t block[]);
%clear const hsize_t start[];
%clear const hsize_t stride[];
%clear const hsize_t count[];
%clear const hsize_t block[];

/**
 * Resets the selection region to include no elements.
 *
 * <p>H5Sselect_none resets the selection region for the dataspace
 * space_id to include no elements.</p>
 *
 * @param space_id The identifier for the dataspace in which the
 * selection is being reset.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Sselect_none(hid_t space_id);

/**
 * Verifies that the selection is within the extent of the dataspace.
 *
 * <p>H5Sselect_valid verifies that the selection for the dataspace
 * space_id is within the extent of the dataspace if the current
 * offset for the dataspace is used.</p>
 *
 * @param space_id The identifier for the dataspace being queried.
 *
 * @return Returns a positive value, for TRUE, if the selection is
 * contained within the extent or 0 (zero), for FALSE, if it is
 * not. Returns a negative value on error conditions such as the
 * selection or extent not being defined.
 */
htri_t H5Sselect_valid(hid_t space_id);

/**
 * Removes the extent from a dataspace.
 *
 * <p>H5Sset_extent_none removes the extent from a dataspace and
 * sets the type to H5S_NO_CLASS.</p>
 *
 * @param space_id The identifier for the dataspace from which the
 * extent is to be removed.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Sset_extent_none(hid_t space_id);

/**
 * Sets or resets the size of an existing dataspace.
 *
 * <p>H5Sset_extent_simple sets or resets the size of an existing
 * dataspace.</p>
 *
 * <p>rank is the dimensionality, or number of dimensions, of the
 * dataspace.</p>
 *
 * <p>current_size is an array of size rank which contains the new
 * size of each dimension in the dataspace. maximum_size is an array
 * of size rank which contains the maximum size of each dimension in
 * the dataspace.</p>
 *
 * <p>Any previous extent is removed from the dataspace, the dataspace
 * type is set to H5S_SIMPLE, and the extent is set as specified. </p>
 *
 * @param space_id Dataspace identifier.
 * @param rank Rank, or dimensionality, of the dataspace.
 * @param current_size Array containing current size of dataspace.
 * @param maximum_size Array containing maximum size of dataspace.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply long long INPUT[] {const hsize_t dims[]}
%apply long long INPUT[] {const hsize_t max[]}
herr_t H5Sset_extent_simple(hid_t space_id, int rank,
				            const hsize_t dims[],
				            const hsize_t max[]);
%clear const hsize_t dims[];
%clear const hsize_t max[];















