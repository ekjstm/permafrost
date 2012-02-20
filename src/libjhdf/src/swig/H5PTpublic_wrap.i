%module PacketTableLib
%include "typemaps.i"
%import "H5types.i"
%include "buffers.i"

%header %{
#include <errno.h>
#include "hdf5.h"
#include "H5PTpublic.h"
#define SWIG_NOARRAYS 1
%}
%include "arrays_java.i"

/*-------------------------------------------------------------------------
 *
 * Create/Open/Close functions
 *
 *-------------------------------------------------------------------------
 */
/**
 * Creates a packet table to store fixed-length packets.
 *
 * <p>H5PTcreate_fl creates and opens a packet table named dset_name 
 * attached to the object specified by the identifier loc_id. It should 
 * be closed with H5PTclose.</p>
 *
 * @param loc_id Identifier of the file or group to create the table within.
 * @param dset_name The name of the dataset to create.
 * @param dtype_id The datatype of a packet.
 * @param chunk_size The packet table uses HDF5 chunked storage to allow it 
 * to grow. This value allows the user to set the size of a chunk. 
 * The chunk size affects performance.
 * @param compression The deflate compression level; 0 - 9.

 * @return Returns an identifier for the new packet table, or H5I_BADID on error.
 */
%apply long long {hsize_t chunk_size}
hid_t H5PTcreate_fl (hid_t loc_id, const char* dset_name, hid_t dtype_id, hsize_t chunk_size, int compression);
%clear hsize_t chunk_size;

/*nodoc*
 * Creates a packet table to store variable-length packets.
 *
 * <p>H5PTcreate_vl creates and opens a dataset named dset_name attached 
 * to the object specified by the identifier loc_id. This dataset can then 
 * be written to as a packet table. It should be closed with H5PTclose.</p>
 *
 * @param loc_id Identifier of the file or group to create the table within.
 * @param dset_name The name of the dataset to create.
 * @param chunk_size The packet table uses HDF5 chunked storage to allow it 
 * to grow. This value allows the user to set the size of a chunk. 
 * The chunk size affects performance. 
 *
 * @return Returns an identifier for the new packet table, or H5I_BADID on error.
 */
//hid_t H5PTcreate_vl(hid_t loc_id, const char* dset_name, hsize_t chunk_size);

/** 
 * Opens an existing packet table.
 *
 * <p>H5PTopen opens an existing packet table in the file or group specified 
 * by loc_id. dset_name is the name of the packet table and is used to 
 * identify it in the file. This function is used to open both fixed-length 
 * packet tables and variable-length packet tables. The packet table should 
 * later be closed with H5PTclose.</p>
 *
 * @param loc_id Identifier of the file or group within which the packet table can be found.
 * @param dset_name The name of the packet table to open.
 * 
 * @return Returns an identifier for the packet table, or H5I_BADID on error.
 */
hid_t H5PTopen(hid_t loc_id, const char* dset_name);

/**
 * Closes an open packet table.
 * 
 * <p>H5PTclose ends access to a packet table specified by dataset_id.</p>
 *
 * @param table_id Identifier of packet table to be closed.
 * 
 * @return Returns a non-negative value if successful, otherwise returns a negative value.
 */
herr_t H5PTclose(hid_t table_id);


/*-------------------------------------------------------------------------
 *
 * Write functions
 *
 *-------------------------------------------------------------------------
 */
%apply void* BUFF {const void* data}
/**
 * Appends packets to the end of a packet table.
 * 
 * <p>H5PTappend writes nrecords packets to the end of a packet table 
 * specified by table_id. data  is a buffer containing the data to be 
 * written. For a packet table holding fixed-length packets, this data 
 * should be in the packet table's datatype. For a variable-length packet 
 * table, the data should be in the form of hvl_t structs.</p>
 *
 * @param table_id Identifier of packet table to which packets should be appended.
 * @param nrecords Number of packets to be appended. 
 * @param data Buffer holding data to write.
 *
 * @return Returns a non-negative value if successful, otherwise returns a negative value.
 */
herr_t H5PTappend(hid_t table_id, size_t nrecords, const void* data);

/*-------------------------------------------------------------------------
 *
 * Read functions
 *
 *-------------------------------------------------------------------------
 */

%apply void* BUFF {void* data}
/**
 * Reads packets from a packet table starting at the current index.
 *
 * <p>H5PTread_packets reads nrecords packets starting with the 
 * "current index" from a packet table specified by table_id. The packet 
 * table's index is set and reset with H5PTset_index and H5PTcreate_index. 
 * data is a buffer into which the data should be read.</p>
 *
 * <p>For a packet table holding variable-length records, the data returned 
 * in the buffer will be in form of a hvl_t struct containing the length 
 * of the data and a pointer to it in memory. The memory used by this data 
 * must be freed using H5PTfree_vlen_readbuff.</p>
 *
 * @param table_id Identifier of packet table to read from.
 * @param nrecords Number of packets to be read.
 * @parma data (Output) Buffer into which to read data.
 */
herr_t H5PTget_next(hid_t table_id, size_t nrecords, void* data);

/**
 * Reads a number of packets from a packet table.
 *
 * <p>H5PTread_packets reads nrecords packets starting at packet number 
 * start from a packet table specified by table_id. data is a buffer into 
 * which the data should be read.</p>
 *
 * <p>For a packet table holding variable-length records, the data returned 
 * in the buffer will be in form of hvl_t structs, each containing the 
 * length of the data and a pointer to it in memory. The memory used 
 * by this data must be freed using H5PTfree_vlen_readbuff.</p>
 *
 * @param table_id Identifier of packet table to read from.
 * @param start Packet to start reading from.
 * @param nrecords Number of packets to be read.
 * @param data (Output) Buffer into which to read data.
 *
 * @return Returns a non-negative value if successful, otherwise returns a negative value.
 */
%apply long long {hsize_t start}
herr_t H5PTread_packets(hid_t table_id, hsize_t start, size_t nrecords, void* data);
%clear hsize_t start;

/*-------------------------------------------------------------------------
 *
 * Inquiry functions
 *
 *-------------------------------------------------------------------------
 */
%apply long long[] {hsize_t* nrecords}
/**
 * Returns the number of packets in a packet table.
 *
 * <p>H5PTget_num_packets returns by reference the number of packets 
 * in a packet table specified by table_id.</p>
 * 
 * @param table_id Identifier of packet table to query. 
 * @param nrecords (Output) Number of packets in packet table.
 * 
 * @return Returns a non-negative value if successful, otherwise returns a negative value.
 */
herr_t H5PTget_num_packets(hid_t table_id, hsize_t* nrecords);

/**
 * Determines whether an indentifier points to a packet table.
 *
 * <p>H5PTis_valid returns a non-negative value if table_id corresponds 
 * to an open packet table, and returns a negative value otherwise. </p>
 * 
 * @param table_id Identifier to query.
 *
 * @return Returns a non-negative value if table_id is a valid packet table, otherwise returns a negative value.
 */
herr_t H5PTis_valid(hid_t table_id);

/**
 * Determines whether a packet table contains variable-length or fixed-length packets.
 *
 * <p>H5PTis_varlen returns 1 (TRUE) if table_id is a packet table 
 * containing variable-length records. It returns 0 (FALSE) if table_id 
 * is a packet table containing fixed-length records. If table_id is not a 
 * packet table, a negative value is returned.</p>
 *
 * @param table_id Packet table to query.
 *
 * @return Returns 1 for a variable-length packet table, 0 for fixed-length, or a negative value on error.
 */
//herr_t H5PTis_varlen(hid_t table_id);


/*-------------------------------------------------------------------------
 *
 * Packet Table "current index" functions
 *
 *-------------------------------------------------------------------------
 */
/**
 * Resets a packet table's index to the first packet.
 *
 * <p>Each packet table keeps an index of the "current" packet so that 
 * get_next can iterate through the packets in order. H5PTcreate_index 
 * initializes a packet table's index, and should be called before using 
 * get_next. The index must be initialized every time a packet table is 
 * created or opened; this information is lost when the packet table is 
 * closed. </p>
 *
 * @param table_id Identifier of packet table whose index should be initialized.
 * 
 * @return Returns a non-negative value if successful, otherwise returns a negative value.
 */
herr_t H5PTcreate_index(hid_t table_id);

/**
 * Sets a packet table's index.
 *
 * <p>Each packet table keeps an index of the "current" packet so that 
 * get_next can iterate through the packets in order. H5PTset_index 
 * sets this index to point to a user-specified packet (the packets 
 * are zero-indexed). </p>
 *
 * @param table_id Identifier of packet table whose index is to be set.
 * @param index The packet to which the index should point.
 *
 * @return Returns a non-negative value if successful, otherwise returns a negative value.
 */
%apply long long {hsize_t pt_index}
herr_t H5PTset_index(hid_t table_id, hsize_t pt_index);
%clear hsize_t pt_index;

%apply long long[] {hsize_t* pt_index}
herr_t H5PTget_index(hid_t table_id, hsize_t* pt_index);

/*-------------------------------------------------------------------------
 *
 * Memory Management functions
 *
 *-------------------------------------------------------------------------
 */
/**
 * Releases memory allocated in the process of reading variable-length packets.
 *
 * <p>When variable-length packets are read, memory is automatically 
 * allocated to hold them, and must be freed. H5PTfree_vlen_readbuff 
 * frees this memory, and should be called whenever packets are read 
 * from a variable-length packet table.</p>
 *
 * @param table_id Packet table whose memory should be freed.
 * @param bufflen Size of buff.
 * @param buff Buffer that was used to read in variable-length packets.
 * 
 * @return Returns a non-negative value on success or a negative value on error.
 */
//herr_t  H5PTfree_vlen_readbuff( hid_t table_id, size_t bufflen, void* buff );
