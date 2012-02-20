%module TableLib
%include "typemaps.i"
%include "various.i"
%import "H5types.i"
%include "buffers.i"
%include "strings.i"

%header %{
#include <errno.h>
#include "hdf5.h"
#include "H5TBpublic.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"


/**
 * <p>The HDF5 Table API defines a standard storage for HDF5 datasets 
 * that are intended to be interpreted as tables. A table is defined 
 * as a collection of records whose values are stored in fixed-length 
 * fields. All records have the same structure and all values in each 
 * field have the same data type.</p>
 */


/*-------------------------------------------------------------------------
 *
 * Create functions
 *
 *-------------------------------------------------------------------------
 */
/**
 * Creates and writes a table.
 *
 * <p>H5TBmake_table creates and writes a dataset named table_name 
 * attached to the object specified by the identifier loc_id.</p>
 *
 * @param table_title The title of the table.
 * @param loc_id Identifier of the file or group to create the table within.
 * @param table_name The name of the dataset to create.  
 * @param nfields The number of fields.
 * @param nrecords The number of records. 
 * @param type_size The size in bytes of the structure associated with the table. This value is obtained with sizeof.
 * @param field_names An array containing the names of the fields.
 * @param field_offset An array containing the offsets of the fields.
 * @param field_types An array containing the type of the fields.
 * @param chunk_size The chunk size.
 * @param fill_data Fill values data.
 * @param compress Flag that turns compression on or off.
 * @param data Buffer with data to be written to the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t nfields}
%apply long long {hsize_t nrecords}
%apply char** STRING_ARRAY {const char* field_names[]}
%apply unsigned long INPUT[] {const size_t* field_offset}
%apply unsigned long INPUT[] {const hid_t* field_types}
%apply long long {hsize_t chunk_size}
%apply void* BUFF {void* fill_data}
%apply void* BUFF {const void* data}
herr_t H5TBmake_table(const char* table_title,
		      hid_t loc_id,
		      const char *dset_name,
		      hsize_t nfields,
		      hsize_t nrecords,
		      size_t type_size,
		      const char* field_names[],
		      const size_t* field_offset,
		      const hid_t *field_types,
		      hsize_t chunk_size,
		      void* fill_data,
		      int compress,
		      const void* data);
%clear hsize_t nfields;
%clear hsize_t nrecords;
%clear const char* field_names[];
%clear const size_t* field_offset;
%clear const hid_t* field_types;
%clear hsize_t chunk_size;
%clear void* fill_data;
%clear const void* data;

/*-------------------------------------------------------------------------
 *
 * Write functions
 *
 *-------------------------------------------------------------------------
 */

/**
 * Adds records to the end of the table.
 *
 * <p>H5TBappend_records adds records to the end of the table named 
 * dset_name attached to the object specified by the identifier loc_id. 
 * The dataset is extended to hold the new records.</p>
 *
 * @param loc_id Identifier of the file or group where the table is located.
 * @param dset_name The name of the dataset to append.
 * @param nrecords The number of records to append.
 * @param type_size The size of the structure type, as calculated by sizeof().
 * @param field_offset An array containing the offsets of the fields. These offsets can be calculated with the HOFFSET macro.
 * @param data Buffer with data to be written to the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t nrecords}
%apply unsigned long INPUT[] {const size_t* field_offset}
%apply unsigned long INPUT[] {const size_t* dst_sizes}
%apply void* BUFF {const void* data}
herr_t H5TBappend_records(hid_t loc_id,
			  const char* dset_name,
			  hsize_t nrecords,
			  size_t type_size,
			  const size_t* field_offset,
			  const size_t* dst_sizes,
			  const void* data);
%clear hsize_t nrecords;
%clear const size_t* field_offset;
%clear const size_t* dst_sizes;
%clear const void* data;

/**
 * Overwrites specific records in a table.
 *
 * <p>H5TBwrite_records overwrites records starting at the zero index 
 * position start of the table named table_name attached to the object 
 * specified by the identifier loc_id.</p>
 *
 * @param loc_id Identifier of the file or group where the table is located.
 * @param table_name The name of the dataset to overwrite.
 * @param start The zero index record to start writing. 
 * @param nrecords The number of records to write.
 * @param type_size The size of the structure type, as calculated by sizeof().
 * @param field_offset An array containing the offsets of the fields. These offsets can be calculated with the HOFFSET macro.
 * @param data Buffer with data to be written to the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t start}
%apply long long {hsize_t nrecords}
%apply unsigned long INPUT[] {const size_t* field_offset}
%apply unsigned long INPUT[] {const size_t* dst_sizes}
%apply void* BUFF {const void* data}
herr_t H5TBwrite_records(hid_t loc_id,
			 const char* dset_name,
			 hsize_t start,
			 hsize_t nrecords,
			 size_t type_size,
			 const size_t* field_offset,
			 const size_t* dst_sizes,
			 const void* data);
%clear hsize_t start;
%clear hsize_t nrecords;
%clear const size_t* field_offset;
%clear const size_t* dst_sizes;
%clear const void* data;

/**
 * Overwrites specific fields by name in specific records in a table.
 *
 * <p>H5TBwrite_fields_name overwrites one or several fields contained 
 * in the buffer field_names from a dataset named table_name attached 
 * to the object specified by the identifier loc_id.</p>
 *
 * @param loc_id Identifier of the file or group where the table is located.
 * @param table_name The name of the dataset to overwrite.
 * @param field_names The names of the fields to write.
 * @param start The zero based index record to start writing. 
 * @param nrecords The number of records to write.
 * @param type_size The size of the structure type, as calculated by sizeof().
 * @param field_offset An array containing the offsets of the fields. These offsets can be calculated with the HOFFSET macro.
 * @param data Buffer with data to be written to the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t start}
%apply long long {hsize_t nrecords}
%apply unsigned long INPUT[] {const size_t* field_offset}
%apply unsigned long INPUT[] {const size_t* dst_sizes}
%apply void* BUFF {const void* data}
herr_t H5TBwrite_fields_name(hid_t loc_id,
			     const char* dset_name,
			     const char* field_names,
			     hsize_t start,
			     hsize_t nrecords,
			     size_t type_size,
			     const size_t* field_offset,
			     const size_t* dst_sizes,
			     const void* data);
%clear hsize_t start;
%clear hsize_t nrecords;
%clear const size_t* field_offset;
%clear const size_t* dst_sizes;
%clear const void* data;

/**
 * Overwrites specific fields by index in specific records in a table.
 *
 * <p>H5TBwrite_fields_index overwrites one or several fields contained 
 * in the buffer field_index  from a dataset named table_name attached 
 * to the object specified by the identifier loc_id. </p>
 *
 * @param loc_id Identifier of the file or group where the table is located.
 * @param table_name The name of the dataset to overwrite.
 * @param nfields The number of fields to overwrite. This parameter is also the size of the field_index array.
 * @param field_index The indexes of the fields to write.
 * @param start The zero based index record to start writing. 
 * @param nrecords The number of records to write.
 * @param type_size The size of the structure type, as calculated by sizeof().
 * @param field_offset An array containing the offsets of the fields. These offsets can be calculated with the HOFFSET macro.
 * @param data Buffer with data to be written to the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t nfields}
%apply int INPUT[] {const int* field_index}
%apply long long {hsize_t start}
%apply long long {hsize_t nrecords}
%apply unsigned long INPUT[] {const size_t* field_offset}
%apply unsigned long INPUT[] {const size_t* dst_sizes}
%apply void* BUFF {const void* data}
herr_t H5TBwrite_fields_index(hid_t loc_id,
			      const char* dset_name,
			      hsize_t nfields,
			      const int* field_index,
			      hsize_t start,
			      hsize_t nrecords,
			      size_t type_size,
			      const size_t* field_offset,
			      const size_t* dst_sizes,
			      const void* data);
%clear hsize_t nfields;
%clear const int* field_index;
%clear hsize_t start;
%clear hsize_t nrecords;
%clear const size_t* field_offset;
%clear const size_t* dst_sizes;
%clear const void* data;

/*-------------------------------------------------------------------------
 *
 * Read functions
 *
 *-------------------------------------------------------------------------
 */

/**
 * Reads data from a table.
 *
 * <p>H5TBread_table reads a table named table_name attached to the 
 * object specified by the identifier loc_id. </p>
 *
 * @param loc_id Identifier of the file or group to read the table from.
 * @param table_name The name of the dataset to read.
 * @param dst_size The size of the structure type, as calculated by sizeof().
 * @param dst_offset An array containing the offsets of the fields. These offsets can be calculated with the HOFFSET macro.
 * @param dst_sizes An array containing the sizes of the fields. These sizes can be calculated with the sizeof() macro.
 * @param dst_buf (Output) Buffer with data read from table. 
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */ 
%apply unsigned int INPUT[] {const size_t* dst_offset}
%apply unsigned int INPUT[] {const size_t* dst_sizes}
%apply void* BUFF {void* dst_buf}
herr_t H5TBread_table(hid_t loc_id,
		      const char* dset_name,
		      size_t dst_size,
		      const size_t* dst_offset,
		      const size_t* dst_sizes,
		      void* dst_buf);

/**
 * Reads one or several fields, identified by name, from specific records.
 *
 * <p>H5TBread_fields_name reads the fields identified by  field_names  
 * from a dataset named table_name attached to the object specified by 
 * the identifier loc_id. </p>
 * 
 * @param loc_id Identifier of the file or group to read the table within.
 * @param table_name The name of the dataset to read.
 * @param field_names A comma-seperated array containing the names of the fields to read.
 * @param start The start record to read from.
 * @param nrecords The number of records to read.
 * @param type_size The size in bytes of the structure associated with the table. This value is obtained with sizeof().
 * @param field_offset An array containing the offsets of the fields.
 * @param dst_sizes An array containing the size in bytes of the fields.
 * @param data (Output) Buffer with data read from table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t start}
%apply long long {hsize_t nrecords}
%apply unsigned long INPUT[] {const size_t* field_offset}
%apply unsigned long INPUT[] {const hid_t* dst_sizes}
%apply void* BUFF {void* data}
herr_t H5TBread_fields_name(hid_t loc_id,
			    const char* dset_name,
			    const char* field_names,
			    hsize_t start,
			    hsize_t nrecords,
			    size_t type_size,
			    const size_t* field_offset,
			    const size_t* dst_sizes,
			    void* data);
%clear hsize_t start;
%clear hsize_t nrecords;
%clear const size_t* field_offset;
%clear const hid_t* dst_sizes;
%clear void* data;

/**
 * Reads one or several fields, identified by index, from specific records.
 *
 * <p>H5TBread_fields_index reads the fields identified by  field_index  
 * from a dataset named table_name attached to the object specified by the 
 * identifier loc_id. </p>
 *
 * @param loc_id Identifier of the file or group to read the table within.
 * @param table_name The name of the dataset to read.
 * @param nfields The number of fields to read. This parameter is also the size of the field_index array.
 * @param field_index The indexes of the fields to read.
 * @param start The start record to read from.
 * @param nrecords The number of records to read.
 * @param  type_size The size in bytes of the structure associated with the table. This value is obtained with sizeof().
 * @param field_offset An array containing the offsets of the fields.
 * @param dst_sizes An array containing the size in bytes of the fields.
 * @param data (Output) Buffer with data read from the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t nfields}
%apply int INPUT[] {const int* field_index}
%apply long long {hsize_t start}
%apply long long {hsize_t nrecords}
%apply unsigned long INPUT[] {const size_t* field_offset}
%apply unsigned long INPUT[] {const size_t dst_sizes}
%apply void* BUFF {void* data}
herr_t H5TBread_fields_index(hid_t loc_id,
			     const char* dset_name,
			     hsize_t nfields,
			     const int* field_index,
			     hsize_t start,
			     hsize_t nrecords,
			     size_t type_size,
			     const size_t* field_offset,
			     const size_t* dst_sizes,
			     void* data);
%clear hsize_t nfields;
%clear const int* field_index;
%clear hsize_t start;
%clear hsize_t nrecords;
%clear const size_t* field_offset;
%clear const size_t dst_sizes;
%clear void* data;

/**
 * Reads records.
 *
 * <p>H5TBread_records reads some records identified from a dataset named 
 * table_name attached to the object specified by the identifier loc_id.</p>
 *
 * @param loc_id Identifier of the file or group to read the table within.
 * @param table_name The name of the dataset to read.
 * @param start The start record to read from.
 * @param nrecords The number of records to read.
 * @param type_size The size of the structure type, as calculated by sizeof().
 * @param field_offset An array containing the offsets of the fields. These offsets can be calculated with the HOFFSET macro.
 * @param dst_sizes An array containing the size in bytes of the fields.
 * @param data (Output) Buffer with data read from the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t start}
%apply long long {hsize_t nrecords}
%apply unsigned int INPUT[] {const size_t dst_offset}
%apply unsigned int INPUT[] {const size_t dst_sizes}
%apply void* BUFF {void* data}
herr_t H5TBread_records(hid_t loc_id,
			const char* dset_name,
			hsize_t start,
			hsize_t nrecords,
			size_t type_size,
			const size_t* dst_offset,
			const size_t* dst_sizes,
			void *data);
%clear hsize_t start;
%clear hsize_t nrecords;
%clear const size_t dst_offset;
%clear const size_t dst_sizes;
%clear void* data;

/*-------------------------------------------------------------------------
 *
 * Inquiry functions
 *
 *-------------------------------------------------------------------------
 */

/**
 * Gets the dimensions of a table.
 *
 * <p>H5TBget_dimensions retrieves the table dimensions from a dataset named 
 * table_name attached to the object specified by the identifier loc_id. </p>
 *
 * @param loc_id Identifier of the file or group to read the table from.
 * @param table_name The name of the dataset to read.
 * @param nfields (Output) The number of fields.
 * @param nrecords (Output) The number of records.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long OUTPUT[] {hsize_t* nfields}
%apply  long long OUTPUT[] {hsize_t* nrecords}
herr_t H5TBget_table_info (hid_t loc_id,
			   const char* dset_name,
			   hsize_t* nfields,
			   hsize_t* nrecords);
%clear hsize_t* nfields;
%clear hsize_t* nrecords;

/**
 * Gets information about a table.
 *
 * <p>H5TBget_field_info gets information about a dataset named table_name 
 * attached to the object specified by the identifier loc_id. </p>
 *
 * @param loc_id Identifier of the file or group to read the table within.
 * @param table_name The name of the dataset to read.
 * @param field_names (Output) An array containing the names of the fields.
 * @param field_sizes (Output) An array containing the size of the fields.
 * @param field_offsets (Output) An array containing the offsets of the fields.
 * @param type_size (Output) The size of the HDF5 datatype associated with the table. More specifically, the size in bytes of the HDF5 compound datatype used to define a row, or record, in the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply char** HSTRARR_OUTPUT {char* field_names[]}
%apply unsigned int OUTPUT[] {size_t* field_sizes}
%apply unsigned int OUTPUT[] {size_t* field_offsets}
%apply unsigned int OUTPUT[] {size_t* type_size}
herr_t H5TBget_field_info(hid_t loc_id,
			  const char* dset_name,
			  char* field_names[],
			  size_t* field_sizes,
			  size_t* field_offsets,
			  size_t* type_size);
%clear char* field_names[];
%clear size_t* field_sizes;
%clear size_t* field_offsets;
%clear size_t* type_size;


/*-------------------------------------------------------------------------
 *
 * Manipulation functions
 *
 *-------------------------------------------------------------------------
 */

/**
 * Delete specified records from a table.
 *
 * <p>H5TBdelete_record deletes records from middle of table 
 * ("pulling up" all the records after it).</p>
 * 
 * @param loc_id dentifier of the file or group in which the table is located.
 * @param dset_name The name of the dataset.
 * @param start The start record to delete from.
 * @param nrecords The number of records to delete.
 * 
 * @returns Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t start}
%apply long long {hsize_t nrecords}
herr_t H5TBdelete_record(hid_t loc_id,
			 const char* dset_name,
			 hsize_t start,
			 hsize_t nrecords);
%clear hsize_t start;
%clear hsize_t nrecords;

/**
 * Insert records into a table at a specified location. 
 *
 * <p>H5TBinsert_record inserts records into the middle of the table 
 * ("pushing down" all the records after it).</p>
 *
 * @param loc_id Identifier of the file or group in which the table is located.
 * @param dset_name The name of the dataset.
 * @param start The position to insert.
 * @param nrecords The number of records to insert.
 * @param data Buffer with data to write to the table.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t start}
%apply long long {hsize_t nrecords}
%apply unsigned int INPUT[] {const size_t dst_offset}
%apply unsigned int INPUT[] {const size_t dst_sizes}
%apply void* BUFF {void* data}
herr_t H5TBinsert_record(hid_t loc_id,
			 const char* dset_name,
			 hsize_t start,
			 hsize_t nrecords,
			 size_t dst_size,
			 const size_t* dst_offset,
			 const size_t* dst_sizes,
			 void* data);
%clear hsize_t start;
%clear hsize_t nrecords;
%clear const size_t dst_offset;
%clear const size_t dst_sizes;
%clear void* data;

/**
 * Concatenates one table with another.
 *
 * <p>H5TBadd_records_from adds records from a dataset named dset_name1 
 * to a dataset named dset_name2. Both tables are attached to the object 
 * specified by the identifier loc_id. </p>
 *
 * @param loc_id Identifier of the file or group in which the table is located.
 * @param dset_name1 The name of the dataset to read records from.
 * @param start1 The position to start read records from in the source table.
 * @param nrecords The number of records to read from the source table.
 * @param dset_name2 The name of the dataset to write the records to.
 * @param start2 The position to start writing the records in the second table.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value. 
 */
%apply long long {hsize_t start1}
%apply long long {hsize_t nrecords}
%apply long long {hsize_t start2}
herr_t H5TBadd_records_from(hid_t loc_id,
			    const char* dset_name1,
			    hsize_t start1,
			    hsize_t nrecords,
			    const char* dset_name2,
			    hsize_t start2);
%clear hsize_t start1;
%clear hsize_t nrecords;
%clear hsize_t start2;

/**
 * Concatenates two tables into a third, new, table.
 *
 * <p>H5TBcombine_tables combines records from two datasets named dset_name1  
 * and dset_name2, to a new table named dset_name3. These tables can be 
 * located on different files, identified by loc_id1 and loc_id2 
 * (identifiers obtained with H5Fcreate). They can also be located on 
 * the same file. In this case one uses the same identifier for both 
 * parameters loc_id1 and loc_id2. If two files are used, the third table 
 * is written on the first file.</p>
 *
 * @param loc_id1 Identifier of the file or group in which the first table is located. The destination table will be a child of this object.
 * @param dset_name1 The name of the first source table to be combined.
 * @param loc_id2 Identifier of the file or group in which the second table is located.
 * @param dset_name2 The name of the second source table to be combined.
 * @param dset_name3 The name of the destination table to be created.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5TBcombine_tables(hid_t loc_id1,
			  const char* dset_name1,
			  hid_t loc_id2,
			  const char* dset_name2,
			  const char* dset_name3);

/**
 * Inserts a new field into a table.
 *
 * <p>H5TBinsert_field inserts a new field named field_name into the table 
 * dset_name. Note: this function requires the table to be re-created and 
 * rewritten in its entirety, and this can result in some unused space in 
 * the file, and can also take a great deal of time if the table is large. </p>
 *
 * @param loc_id Identifier of the file or group in which the table is located.
 * @param dset_name The name of the table.
 * @param field_name The name of the field to insert.
 * @param field_type The data type of the field.
 * @param position The zero based index position where to insert the field.
 * @param fill_data Fill value data for the field. This parameter can be NULL.
 * @param data Buffer with data to be written to the table.
 */
%apply long long {hsize_t position}
%apply void* BUFF {const void* fill_data}
%apply void* BUFF {const void* data}
herr_t H5TBinsert_field(hid_t loc_id,
			const char* dset_name,
			const char* field_name,
			hid_t field_type,
			hsize_t position,
			const void* fill_data,
			const void* data);
%clear hsize_t position;
%clear const void* fill_data;
%clear const void* data;

/**
 * Deletes a specified field from a table.
 *
 * <p>H5TBdelete_field deletes a  field named field_name from the table 
 * dset_name. Note: this function requires the table to be re-created and 
 * rewritten in its entirety, and this can result in some unused space in 
 * the file, and can also take a great deal of time if the table is large.</p>
 *
 * @param loc_id Identifier of the file or group in which the table is located.
 * @param dset_name The name of the table.
 * @param field_name The name of the field to delete.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5TBdelete_field(hid_t loc_id,
			const char* dset_name,
			const char* field_name);


/*-------------------------------------------------------------------------
 *
 * Table attribute functions
 *
 *-------------------------------------------------------------------------
 */
%apply signed char OUTPUT[] {char* table_title}
herr_t H5TBAget_title(hid_t loc_id, char* table_title );
%clear char* table_title;

%apply char* BUFF {unsigned char* dst_buf}
herr_t H5TBAget_fill(hid_t loc_id,
		     const char* dset_name,
		     hid_t dset_id,
		     unsigned char* dst_buf );









