%module LiteLib
%include "typemaps.i"
%import "H5types.i"
%import "H5Ttypes.i"
%include "buffers.i"

%header %{
#include <errno.h>
#include "hdf5.h"
#include "H5LTpublic.h"

/* #define SWIG_NOARRAYS 1 */
%}
/* %include "arrays_java.i" */
%include "harrays.i"


/*-------------------------------------------------------------------------
 *
 * Make dataset functions
 *
 *-------------------------------------------------------------------------
 */

// FIXME why isn't there a typemap for BigInteger[]?
%apply long long INPUT[] {const hsize_t* dims}
%apply void* BUFF {const void* buffer}
/**
 * Creates and writes a dataset of a type type_Id.
 *
 * <p>H5LTmake_dataset  creates and writes a dataset named dset_name 
 * attached to the object specified by the identifier loc_id. 
 * The parameter type_id can be any valid HDF5 predefined native datatype, 
 * for example H5T_NATIVE_INT.</p>
 *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create. 
 * @param rank Number of dimensions of dataspace.
 * @param dims An array of the size of each dimension. 
 * @param type_id Identifier of the datatype to use when creating the dataset.
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTmake_dataset(hid_t loc_id, const char* dset_name, 
				  int rank, const hsize_t *dims, hid_t type_id,
				  const void* buffer);
%clear const void* buffer;

%apply signed char INPUT[] {const char* buffer};
/**
 * Creates and writes a dataset of type H5T_NATIVE_CHAR.
 *
 * <p>H5LTmake_dataset_char creates and writes a dataset named dset_name 
 * attached to the object specified by the identifier loc_id. The HDF5 
 * datatype is H5T_NATIVE_CHAR.</p>
 *
  *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create. 
 * @param rank Number of dimensions of dataspace.
 * @param dims An array of the size of each dimension. 
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value. 
 */
herr_t H5LTmake_dataset_char(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims,
				       const char* buffer);
%clear const char* buffer;

%apply char* BUFF {const char* buffer}
%rename(H5LTmake_dataset_char_direct) H5LTmake_dataset_char;
/**
 * Creates and writes a dataset of type H5T_NATIVE_CHAR.
 *
 * @see H5LTmake_dataset_char
 */
herr_t H5LTmake_dataset_char(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims,
				       const char* buffer);
%clear const char* buffer;


%apply short INPUT[] {const short* buffer}
/**
 * Creates and writes a dataset of type H5T_NATIVE_SHORT.
 *
 * <p>H5LTmake_dataset_short creates and writes a dataset named dset_name 
 * attached to the object specified by the identifier loc_id. The HDF5 
 * datatype is H5T_NATIVE_SHORT.</p>
 *
  *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create. 
 * @param rank Number of dimensions of dataspace.
 * @param dims An array of the size of each dimension. 
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value. 
 */
herr_t H5LTmake_dataset_short(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims, 
				       const short* buffer);
%clear const short* buffer; 

%apply short* BUFF {const short* buffer}
%rename(H5LTmake_dataset_short_direct) H5LTmake_dataset_short;
/**
 * Creates and writes a dataset of type H5T_NATIVE_SHORT.
 *
 * @see H5LTmake_dataset_short
 */
herr_t H5LTmake_dataset_short(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims,
				       const short* buffer);
%clear const short* buffer;


%apply int INPUT[] {const int* buffer}
/**
 * Creates and writes a dataset of type H5T_NATIVE_INT.
 *
 * <p>H5LTmake_dataset_int creates and writes a dataset named dset_name 
 * attached to the object specified by the identifier loc_id. The HDF5 
 * datatype is H5T_NATIVE_INT.</p>
 *
  *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create. 
 * @param rank Number of dimensions of dataspace.
 * @param dims An array of the size of each dimension. 
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value. 
 */
herr_t H5LTmake_dataset_int(hid_t loc_id, const char* dset_name,
				     int rank, const hsize_t *dims,
				     const int* buffer);
%clear const int* buffer;

%apply int* BUFF {const int* buffer}
%rename(H5LTmake_dataset_int_direct) H5LTmake_dataset_int;
/**
 * Creates and writes a dataset of type H5T_NATIVE_INT.
 *
 * @see H5LTmake_dataset_int
 */
herr_t H5LTmake_dataset_int(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims,
				       const int* buffer);
%clear const int* buffer;


%apply long INPUT[] {const long* buffer}
/**
 * Creates and writes a dataset of type H5T_NATIVE_LONG.
 *
 * <p>H5LTmake_dataset_long creates and writes a dataset named dset_name 
 * attached to the object specified by the identifier loc_id. The HDF5 
 * datatype is H5T_NATIVE_LONG.</p>
 *
 *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create. 
 * @param rank Number of dimensions of dataspace.
 * @param dims An array of the size of each dimension. 
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value. 
 */
herr_t H5LTmake_dataset_long(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims,
				       const long* buffer );
%clear const long* buffer;

%apply long* BUFF {const long* buffer}
%rename(H5LTmake_dataset_long_direct) H5LTmake_dataset_long;
/**
 * Creates and writes a dataset of type H5T_NATIVE_LONG.
 *
 * @see H5LTmake_dataset_long
 */
herr_t H5LTmake_dataset_long(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims,
				       const long* buffer);
%clear const long* buffer;


%apply float INPUT[] {const float* buffer}
/**
 * Creates and writes a dataset of type H5T_NATIVE_FLOAT.
 *
 * <p>H5LTmake_dataset_float creates and writes a dataset named dset_name 
 * attached to the object specified by the identifier loc_id. The HDF5 
 * datatype is H5T_NATIVE_FLOAT.</p>
 *
  *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create. 
 * @param rank Number of dimensions of dataspace.
 * @param dims An array of the size of each dimension. 
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value. 
 */
herr_t H5LTmake_dataset_float(hid_t loc_id, const char* dset_name,
                               int rank, const hsize_t *dims,
				       const float *buffer);
%clear const float* buffer;

%apply float* BUFF {const float* buffer}
%rename(H5LTmake_dataset_float_direct) H5LTmake_dataset_float;
/**
 * Creates and writes a dataset of type H5T_NATIVE_FLOAT.
 *
 * @see H5LTmake_dataset_float
 */
herr_t H5LTmake_dataset_float(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims,
				       const float* buffer);
%clear const float* buffer;


%apply double INPUT[] {const double* buffer}
/**
 * Creates and writes a dataset of type H5T_NATIVE_DOUBLE.
 *
 * <p>H5LTmake_dataset_double creates and writes a dataset named dset_name 
 * attached to the object specified by the identifier loc_id. The HDF5 
 * datatype is H5T_NATIVE_DOUBLE.</p>
 *
  *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create. 
 * @param rank Number of dimensions of dataspace.
 * @param dims An array of the size of each dimension. 
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value. 
 */
herr_t H5LTmake_dataset_double(hid_t loc_id, const char* dset_name,
					int rank, const hsize_t *dims,
					const double *buffer );
%clear const double* buffer;

%apply double* BUFF {const double* buffer}
%rename(H5LTmake_dataset_double_direct) H5LTmake_dataset_double;
/**
 * Creates and writes a dataset of type H5T_NATIVE_DOUBLE.
 *
 * @see H5LTmake_dataset_double
 */
herr_t H5LTmake_dataset_double(hid_t loc_id, const char* dset_name,
				       int rank, const hsize_t *dims,
				       const double* buffer);
%clear const double* buffer;

%apply signed char* {const char* buffer};
/**
 * Creates and writes a dataset of type H5T_C_S1.
 *
 * <p>H5LTmake_dataset_string  creates and writes a dataset named dset_name 
 * attached to the object specified by the identifier loc_id. The HDF5 
 * datatype is H5T_C_S1.</p>
 *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create.
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTmake_dataset_string(hid_t loc_id, const char* dset_name,
			       const char *buffer);
%clear const char* buffer;

%apply char* BUFF {const char* buffer}
%rename(H5LTmake_dataset_string_direct) H5LTmake_dataset_string;
/**
 * Creates and writes a dataset of type H5T_NATIVE_DOUBLE.
 *
 * @see H5LTmake_dataset_string
 */
herr_t H5LTmake_dataset_string(hid_t loc_id, const char* dset_name,
			       const char *buffer);
%clear const char* buffer;


/*-------------------------------------------------------------------------
 *
 * Read dataset functions
 *
 *-------------------------------------------------------------------------
 */

%apply void* BUFF {void* buffer}
/**
 * Reads a dataset from disk.
 *
 * <p>H5LTread_dataset reads a dataset named dset_name attached to the 
 * object specified by the identifier loc_id.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset within.
 * @param dset_name The name of the dataset to read.
 * @param type_id Identifier of the datatype to use when reading the dataset.
 * @param buffer (Output) Buffer for data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTread_dataset(hid_t loc_id, const char* dset_name,
			hid_t type_id, void* buffer);

%clear void* buffer;

%apply signed char OUTPUT[] {char* buffer}
/**
 * Reads a dataset of type H5T_NATIVE_CHAR from disk.
 *
 * <p>H5LTread_dataset_char reads a dataset named dset_name attached 
 * to the object specified by the identifier loc_id. The HDF5 datatype 
 * is H5T_NATIVE_CHAR.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset within.
 * @param dset_name The name of the dataset to read.
 * @param buffer (Output) Buffer for data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTread_dataset_char(hid_t loc_id, const char* dset_name, char* buffer);
%clear char* buffer;

%apply char* BUFF {char* buffer}
%rename(H5LTread_dataset_char_direct) H5LTread_dataset_char;
/**
 *  Reads a dataset of type H5T_NATIVE_CHAR from disk.
 *
 * @see H5LTread_dataset_char
 */
herr_t H5LTread_dataset_char(hid_t loc_id, const char* dset_name, char* buffer);
%clear char* buffer;


%apply short OUTPUT[] {short* buffer}
/**
 * Reads a dataset of type H5T_NATIVE_SHORT from disk.
 *
 * <p>H5LTread_dataset_short reads a dataset named dset_name attached 
 * to the object specified by the identifier loc_id. The HDF5 datatype 
 * is H5T_NATIVE_SHORT.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset within.
 * @param dset_name The name of the dataset to read.
 * @param buffer (Output) Buffer for data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTread_dataset_short(hid_t loc_id, const char* dset_name, short* buffer);
%clear short* buffer;

%apply short* BUFF {short* buffer}
%rename(H5LTread_dataset_short_direct) H5LTread_dataset_short;
/**
 *  Reads a dataset of type H5T_NATIVE_SHORT from disk.
 *
 * @see H5LTread_dataset_short
 */
herr_t H5LTread_dataset_short(hid_t loc_id, const char* dset_name, short* buffer);
%clear short* buffer;


%apply int OUTPUT[] {int* buffer}
/**
 * Reads a dataset of type H5T_NATIVE_INT from disk.
 *
 * <p>H5LTread_dataset_int reads a dataset named dset_name attached 
 * to the object specified by the identifier loc_id. The HDF5 datatype 
 * is H5T_NATIVE_INT.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset within.
 * @param dset_name The name of the dataset to read.
 * @param buffer (Output) Buffer for data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTread_dataset_int(hid_t loc_id, const char* dset_name, int* buffer);
%clear int* buffer;

%apply int* BUFF {int* buffer}
%rename(H5LTread_dataset_int_direct) H5LTread_dataset_int;
/**
 *  Reads a dataset of type H5T_NATIVE_INT from disk.
 *
 * @see H5LTread_dataset_int
 */
herr_t H5LTread_dataset_int(hid_t loc_id, const char* dset_name, int* buffer);
%clear int* buffer;


%apply long OUTPUT[] {long* buffer}
/**
 * Reads a dataset of type H5T_NATIVE_LONG from disk.
 *
 * <p>H5LTread_dataset_long reads a dataset named dset_name attached 
 * to the object specified by the identifier loc_id. The HDF5 datatype 
 * is H5T_NATIVE_LONG.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset within.
 * @param dset_name The name of the dataset to read.
 * @param buffer (Output) Buffer for data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTread_dataset_long(hid_t loc_id, const char* dset_name, long* buffer);
%clear long* buffer;

%apply long* BUFF {long* buffer}
%rename(H5LTread_dataset_long_direct) H5LTread_dataset_long;
/**
 *  Reads a dataset of type H5T_NATIVE_LONG from disk.
 *
 * @see H5LTread_dataset_long
 */
herr_t H5LTread_dataset_long(hid_t loc_id, const char* dset_name, long* buffer);
%clear long* buffer;


%apply float OUTPUT[] {float* buffer}
/**
 * Reads a dataset of type H5T_NATIVE_FLOAT from disk.
 *
 * <p>H5LTread_dataset_float reads a dataset named dset_name attached 
 * to the object specified by the identifier loc_id. The HDF5 datatype 
 * is H5T_NATIVE_FLOAT.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset within.
 * @param dset_name The name of the dataset to read.
 * @param buffer (Output) Buffer for data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTread_dataset_float(hid_t loc_id, const char *dset_name, float* buffer);
%clear float* buffer;

%apply float* BUFF {float* buffer}
%rename(H5LTread_dataset_float_direct) H5LTread_dataset_float;
/**
 *  Reads a dataset of type H5T_NATIVE_FLOAT from disk.
 *
 * @see H5LTread_dataset_float
 */
herr_t H5LTread_dataset_float(hid_t loc_id, const char* dset_name, float* buffer);
%clear float* buffer;


%apply double OUTPUT[] {double* buffer}
/**
 * Reads a dataset of type H5T_NATIVE_DOUBLE from disk.
 *
 * <p>H5LTread_dataset_double reads a dataset named dset_name attached 
 * to the object specified by the identifier loc_id. The HDF5 datatype 
 * is H5T_NATIVE_DOUBLE.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset within.
 * @param dset_name The name of the dataset to read.
 * @param buffer (Output) Buffer for data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTread_dataset_double(hid_t loc_id, const char* dset_name, double* buffer);
%clear double* buffer;

%apply double* BUFF {double* buffer}
%rename(H5LTread_dataset_double_direct) H5LTread_dataset_double;
/**
 *  Reads a dataset of type H5T_NATIVE_DOUBLE from disk.
 *
 * @see H5LTread_dataset_double
 */
herr_t H5LTread_dataset_double(hid_t loc_id, const char* dset_name, double* buffer);
%clear double* buffer;


%apply char* BUFF {char* buffer}
/**
 * Reads a dataset of type H5T_C_S1 from disk.
 *
 * <p>H5LTread_dataset_string reads a dataset named dset_name attached 
 * to the object specified by the identifier loc_id. The HDF5 datatype 
 * is H5T_C_S1.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset within.
 * @param dset_name The name of the dataset to read.
 * @param buffer (Output) Buffer for data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTread_dataset_string(hid_t loc_id, const char* dset_name, char* buffer);
%clear char* buffer;

/*-------------------------------------------------------------------------
 *
 * Query dataset functions
 *
 *-------------------------------------------------------------------------
 */

%apply int* OUTPUT {int* rank}
/**
 * Gets the dimensionality of a dataset.
 * 
 * <p>H5LTget_dataset_ndims gets the dimensionality of a dataset 
 * named dset_name  exists attached to the object loc_id.</p>
 *
 * @param loc_id Identifier of the object to locate the dataset within.
 * @param dset_name The dataset name.
 * @param rank (Output) The dimensionality of the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_dataset_ndims(hid_t loc_id, const char* dset_name, int* rank);
%clear int* rank;

%apply long long OUTPUT[] {hsize_t* dims}
%apply H5T_CLASS_ENUM* OUTPUT {H5T_class_t* type_class} 
%apply unsigned long OUTPUT[] {size_t* type_size}
/**
 * Gets information about a dataset.
 * 
 * <p>H5LTget_dataset_info gets information about a dataset named dset_name  
 * which exists attached to the object loc_id. A value of NULL is valid 
 * for the last 3 parameters, in wich case information is not returned.</p>
 *
 * @param loc_id Identifier of the object to locate the dataset within.
 * @param dset_name The dataset name.
 * @param dims (Output) The dimensions of the dataset.
 * @param class_id (Output) The class identifier. To a list of the HDF5 class types please refer to the Datatype Interface API help.
 * @param type_size (Output) The size of the datatype in bytes.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_dataset_info(hid_t loc_id, const char* dset_name,
			    hsize_t *dims, H5T_class_t* type_class, size_t *type_size);

/**
 * Inquires if a dataset exists.
 *
 * <p>H5LTfind_dataset inquires if a dataset named dset_name 
 * exists attached to the object loc_id.</p>
 *
 * @param loc_id Identifier of the object to locate the dataset within.
 * @param name The dataset name.
 * 
 * @return Returns 1 if the dataset exists, returns 0 otherwise.
 */
herr_t H5LTfind_dataset(hid_t loc_id, const char* name);



/*-------------------------------------------------------------------------
 *
 * Set attribute functions
 *
 *-------------------------------------------------------------------------
 */

/**
 * Creates and writes a string attribute.
 *
 * <p>H5LTset_attribute_string creates and writes a string attribute 
 * named attr_name and attaches it to the object specified by the 
 * name obj_name. If the attribute already exists, it is overwritten.</p>
 *
 * @param loc_id Identifier of the object (dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param attr_data Buffer with data to be written to the attribute.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_string( hid_t loc_id, const char* obj_name,
                                 const char* attr_name, const char* attr_data);

%apply signed char INPUT[] {const char* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_CHAR.
 *
 * <p>H5LTset_attribute_char creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_CHAR.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_char(hid_t loc_id, const char* obj_name, 
				       const char* attr_name, 
				       const char* buffer, size_t size);
%clear const char* buffer;

%apply char* BUFF {const char* buffer}
%rename(H5LTset_attribute_char_direct) H5LTset_attribute_char;
/**
 * Creates and writes an attribute of type H5T_NATIVE_CHAR.
 *
 * @see H5LTset_attribute_char
 */
herr_t H5LTset_attribute_char(hid_t loc_id, const char* obj_name,
					const char* attr_name,
					const char* buffer, size_t size);
%clear const unsigned char* buffer;


%apply unsigned char INPUT[] {const unsigned char* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_UCHAR.
 *
 * <p>H5LTset_attribute_uchar creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_UCHAR.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_uchar(hid_t loc_id, const char* obj_name,
					const char* attr_name,
					const unsigned char* buffer, size_t size);
%clear const unsigned char* buffer;

%apply unsigned char* INBUFF {const unsigned char* buffer}
%rename(H5LTset_attribute_uchar_direct) H5LTset_attribute_uchar;
/**
 * Creates and writes an attribute of type H5T_NATIVE_UCHAR.
 *
 * @see H5LTset_attribute_uchar
 */
herr_t H5LTset_attribute_uchar(hid_t loc_id, const char* obj_name,
					const char* attr_name,
					const unsigned char* buffer, size_t size);
%clear const unsigned char* buffer;


%apply short INPUT[] {const short* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_SHORT.
 *
 * <p>H5LTset_attribute_short creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_SHORT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_short(hid_t loc_id, const char* obj_name,
					const char* attr_name,
					const short* buffer, size_t size);
%clear const short* buffer;

%apply short* BUFF {const short* buffer}
%rename(H5LTset_attribute_short_direct) H5LTset_attribute_short;
/**
 * Creates and writes an attribute of type H5T_NATIVE_SHORT.
 *
 * @see H5LTset_attribute_short
 */
herr_t H5LTset_attribute_short(hid_t loc_id, const char* obj_name,
					const char* attr_name,
					const short* buffer, size_t size);
%clear const short* buffer;


%apply unsigned short INPUT[] {const unsigned short* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_USHORT.
 *
 * <p>H5LTset_attribute_ushort creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_USHORT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_ushort(hid_t loc_id, const char *obj_name,
					  const char *attr_name,
					  const unsigned short *buffer, size_t size);
%clear const unsigned short* buffer;

%apply unsigned short* INBUFF {const unsigned short* buffer}
%rename(H5LTset_attribute_ushort_direct) H5LTset_attribute_ushort;
/**
 * Creates and writes an attribute of type H5T_NATIVE_USHORT.
 *
 * @see H5LTset_attribute_ushort
 */
herr_t H5LTset_attribute_ushort(hid_t loc_id, const char *obj_name,
					  const char *attr_name,
					  const unsigned short *buffer, size_t size);
%clear const unsigned short* buffer;


%apply int INPUT[] {const int* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_INT.
 *
 * <p>H5LTset_attribute_int creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_INT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_int(hid_t loc_id, const char *obj_name,
				      const char *attr_name,
				      const int *buffer, size_t size);
%clear const int* buffer;

%apply int* BUFF {const int* buffer}
%rename(H5LTset_attribute_int_direct) H5LTset_attribute_int;
/**
 * Creates and writes an attribute of type H5T_NATIVE_INT.
 *
 * @see H5LTset_attribute_int
 */
herr_t H5LTset_attribute_int(hid_t loc_id, const char *obj_name,
					  const char *attr_name,
					  const int *buffer, size_t size);
%clear const int* buffer;


%apply unsigned int INPUT[] {const unsigned int* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_UINT.
 *
 * <p>H5LTset_attribute_uint creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_UINT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_uint(hid_t loc_id, const char *obj_name,
					const char *attr_name,
					const unsigned int *buffer, size_t size);
%clear const unsigned int* buffer;

%apply unsigned int* INBUFF {const unsigned int* buffer}
%rename(H5LTset_attribute_uint_direct) H5LTset_attribute_uint;
/**
 * Creates and writes an attribute of type H5T_NATIVE_UINT.
 *
 * @see H5LTset_attribute_uint
 */
herr_t H5LTset_attribute_uint(hid_t loc_id, const char *obj_name,
					  const char *attr_name,
					  const unsigned int *buffer, size_t size);
%clear const unsigned int* buffer;

%apply long INPUT[] {const long* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_LONG.
 *
 * <p>H5LTset_attribute_long creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_LONG.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_long(hid_t loc_id, const char *obj_name,
					const char *attr_name, 
					const long *buffer, size_t size);
%clear const long* buffer;

%apply long* BUFF {const long* buffer}
%rename(H5LTset_attribute_long_direct) H5LTset_attribute_long;
/**
 * Creates and writes an attribute of type H5T_NATIVE_LONG.
 *
 * @see H5LTset_attribute_long
 */
herr_t H5LTset_attribute_long(hid_t loc_id, const char *obj_name,
					  const char *attr_name,
					  const long *buffer, size_t size);
%clear const long* buffer;

%apply unsigned long INPUT[] {const unsigned long* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_ULONG.
 *
 * <p>H5LTset_attribute_ulong creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_ULONG.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_ulong(hid_t loc_id, const char *obj_name,
					 const char *attr_name,
					 const unsigned long *buffer, size_t size);
%clear const unsigned long* buffer;

%apply unsigned long* INBUFF {const unsigned long* buffer}
%rename(H5LTset_attribute_ulong_direct) H5LTset_attribute_ulong;
/**
 * Creates and writes an attribute of type H5T_NATIVE_ULONG.
 *
 * @see H5LTset_attribute_ulong
 */
herr_t H5LTset_attribute_ulong(hid_t loc_id, const char *obj_name,
					  const char *attr_name,
					  const unsigned long *buffer, size_t size);
%clear const long* buffer;


%apply float INPUT[] {const float* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_FLOAT.
 *
 * <p>H5LTset_attribute_float creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_FLOAT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_float(hid_t loc_id, const char *obj_name,
					const char *attr_name,
					const float *buffer, size_t size);
%clear const float* buffer;

%apply float* BUFF {const float* buffer}
%rename(H5LTset_attribute_float_direct) H5LTset_attribute_float;
/**
 * Creates and writes an attribute of type H5T_NATIVE_FLOAT.
 *
 * @see H5LTset_attribute_float
 */
herr_t H5LTset_attribute_float(hid_t loc_id, const char *obj_name,
					  const char *attr_name,
					  const float *buffer, size_t size);
%clear const float* buffer;

%apply double INPUT[] {const double* buffer}
/**
 * Creates and writes an attribute of type H5T_NATIVE_DOUBLE.
 *
 * <p>H5LTset_attribute_double creates and writes a numerical attribute 
 * named attr_name and attaches it to the object specified by the name 
 * obj_name. The attribute has a dimensionality of 1. The HDF5 datatype 
 * of the attribute is H5T_NATIVE_DOUBLE.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to create the attribute within.
 * @param obj_name The name of the object to attach the attribute.
 * @param attr_name The attribute name.
 * @param buffer Buffer with data to be written to the attribute.
 * @param size The size of the 1D array (one in the case of a scalar attribute). This value is used by H5Screate_simple to create the dataspace.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTset_attribute_double(hid_t loc_id, const char *obj_name,
					 const char *attr_name,
					 const double *buffer, size_t size );
%clear const double* buffer;

%apply double* BUFF {const double* buffer}
%rename(H5LTset_attribute_double_direct) H5LTset_attribute_double;
/**
 * Creates and writes an attribute of type H5T_NATIVE_DOUBLE.
 *
 * @see H5LTset_attribute_double
 */
herr_t H5LTset_attribute_double(hid_t loc_id, const char *obj_name,
					  const char *attr_name,
					  const double *buffer, size_t size);
%clear const double* buffer;

/*-------------------------------------------------------------------------
 *
 * Get attribute functions
 *
 *-------------------------------------------------------------------------
 */

%apply signed char OUTPUT[] {void* data}
/**
 * Reads an attribute from disk.
 *
 * <p>H5LTget_attribute reads an attribute named attr_name 
 * with the memory type mem_type_id. </p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @param attr_name The attribute name.
 * @param mem_type_id Identifier of the memory datatype.
 * @param data (Output) Buffer with data.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute(hid_t loc_id, const char *obj_name,
				  const char *attr_name,
				  hid_t mem_type_id, void *data );
%clear void* data;

%apply void* BUFF {void* data}
%rename(H5LTget_attribute_direct) H5LTget_attribute;
/**
 *  Reads an attribute from disk.
 *
 * @see H5LTget_attribute
 */
herr_t H5LTget_attribute(hid_t loc_id, const char *obj_name,
				  const char *attr_name,
				  hid_t mem_type_id, void *data );
%clear void* data;

%apply signed char OUTPUT[] {char* data}
/**
 * Reads a string attribute from disk.
 *
 * <p>H5LTget_attribute_string reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_string(hid_t loc_id, const char *obj_name,
				const char *attr_name, char* data);
%clear char* data;

%apply signed char OUTPUT[] {char* data}
/**
 * Reads an attribute with type H5T_NATIVE_CHAR from disk.
 *
 * <p>H5LTget_attribute_char reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_CHAR.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_char(hid_t loc_id, const char *obj_name,
			      const char *attr_name, char* data);
%clear char* data;

%apply char* BUFF {char* data}
%rename(H5LTget_attribute_char_direct) H5LTget_attribute_char;
/**
 * Reads an attribute with type H5T_NATIVE_CHAR from disk.
 *
 * @see H5LTget_attribute_char
 */
herr_t H5LTget_attribute_char(hid_t loc_id, const char *obj_name,
			      const char *attr_name, char* data);
%clear char* data;


%apply unsigned char OUTPUT[] {unsigned char* data}
/**
 * Reads an attribute with type H5T_NATIVE_UCHAR from disk.
 *
 * <p>H5LTget_attribute_uchar reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_UCHAR.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_uchar(hid_t loc_id, const char *obj_name,
			       const char *attr_name, unsigned char* data);
%clear unsigned char* data;

%apply unsigned char* OUTBUFF {unsigned char* data}
%rename(H5LTget_attribute_uchar_direct) H5LTget_attribute_uchar;
/**
 * Reads an attribute with type H5T_NATIVE_UCHAR from disk.
 *
 * @see H5LTget_attribute_uchar
 */
herr_t H5LTget_attribute_uchar(hid_t loc_id, const char *obj_name,
			      const char *attr_name, unsigned char* data);
%clear unsigned char* data;

%apply short OUTPUT[] {short* data}
/**
 * Reads an attribute with type H5T_NATIVE_SHORT from disk.
 *
 * <p>H5LTget_attribute_short reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_SHORT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_short(hid_t loc_id, const char *obj_name,
			       const char *attr_name, short* data);
%clear short* data;

%apply short* BUFF {short* data}
%rename(H5LTget_attribute_short_direct) H5LTget_attribute_short;
/**
 * Reads an attribute with type H5T_NATIVE_SHORT from disk.
 *
 * @see H5LTget_attribute_short
 */
herr_t H5LTget_attribute_short(hid_t loc_id, const char *obj_name,
			      const char *attr_name, short* data);
%clear short* data;


%apply unsigned short OUTPUT[] {unsigned short* data}
/**
 * Reads an attribute with type H5T_NATIVE_USHORT from disk.
 *
 * <p>H5LTget_attribute_ushort reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_USHORT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_ushort(hid_t loc_id, const char *obj_name,
				const char *attr_name, unsigned short* data);
%clear unsigned short* data;

%apply unsigned short* OUTBUFF {unsigned short* data}
%rename(H5LTget_attribute_ushort_direct) H5LTget_attribute_ushort;
/**
 * Reads an attribute with type H5T_NATIVE_USHORT from disk.
 *
 * @see H5LTget_attribute_ushort
 */
herr_t H5LTget_attribute_ushort(hid_t loc_id, const char *obj_name,
			      const char *attr_name, unsigned short* data);
%clear unsigned short* data;


%apply int OUTPUT[] {int* data}
/**
 * Reads an attribute with type H5T_NATIVE_INT from disk.
 *
 * <p>H5LTget_attribute_int reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_INT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_int(hid_t loc_id, const char *obj_name,
			     const char *attr_name, int* data);
%clear int* data;

%apply int* BUFF {int* data}
%rename(H5LTget_attribute_int_direct) H5LTget_attribute_int;
/**
 * Reads an attribute with type H5T_NATIVE_INT from disk.
 *
 * @see H5LTget_attribute_int
 */
herr_t H5LTget_attribute_int(hid_t loc_id, const char *obj_name,
			      const char *attr_name, int* data);
%clear int* data;



%apply unsigned int OUTPUT[] {unsigned int* data}
/**
 * Reads an attribute with type H5T_NATIVE_UINT from disk.
 *
 * <p>H5LTget_attribute_uint reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_UINT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_uint(hid_t loc_id, const char *obj_name,
			      const char *attr_name, unsigned int* data);
%clear unsigned int* data;

%apply unsigned int* OUTBUFF {unsigned int* data}
%rename(H5LTget_attribute_uint_direct) H5LTget_attribute_uint;
/**
 * Reads an attribute with type H5T_NATIVE_UINT from disk.
 *
 * @see H5LTget_attribute_uint
 */
herr_t H5LTget_attribute_uint(hid_t loc_id, const char *obj_name,
			      const char *attr_name, unsigned int* data);
%clear unsigned int* data;

%apply long OUTPUT[] {long* data}
/**
 * Reads an attribute with type H5T_NATIVE_LONG from disk.
 *
 * <p>H5LTget_attribute_long reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_LONG.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_long(hid_t loc_id, const char *obj_name,
			      const char *attr_name, long* data);
%clear long* data;

%apply long* BUFF {long* data}
%rename(H5LTget_attribute_long_direct) H5LTget_attribute_long;
/**
 * Reads an attribute with type H5T_NATIVE_LONG from disk.
 *
 * @see H5LTget_attribute_long
 */
herr_t H5LTget_attribute_long(hid_t loc_id, const char *obj_name,
			      const char *attr_name, long* data);
%clear long* data;

%apply unsigned long OUTPUT[] {unsigned long* data}
/**
 * Reads an attribute with type H5T_NATIVE_ULONG from disk.
 *
 * <p>H5LTget_attribute_ulong reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_ULONG.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_ulong(hid_t loc_id, const char *obj_name,
			       const char *attr_name, unsigned long* data);
%clear unsigned long* data;

%apply unsigned long* OUTBUFF {unsigned long* data}
%rename(H5LTget_attribute_ulong_direct) H5LTget_attribute_ulong;
/**
 * Reads an attribute with type H5T_NATIVE_ULONG from disk.
 *
 * @see H5LTget_attribute_ulong
 */
herr_t H5LTget_attribute_ulong(hid_t loc_id, const char *obj_name,
			      const char *attr_name, unsigned long* data);
%clear unsigned long* data;


%apply float OUTPUT[] {float* data}
/**
 * Reads an attribute with type H5T_NATIVE_FLOAT from disk.
 *
 * <p>H5LTget_attribute_float reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_FLOAT.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_float(hid_t loc_id, const char *obj_name,
			       const char *attr_name, float* data);
%clear float* data;

%apply float* BUFF {float* data}
%rename(H5LTget_attribute_float_direct) H5LTget_attribute_float;
/**
 * Reads an attribute with type H5T_NATIVE_FLOAT from disk.
 *
 * @see H5LTget_attribute_float
 */
herr_t H5LTget_attribute_float(hid_t loc_id, const char *obj_name,
			      const char *attr_name, float* data);
%clear float* data;

%apply double OUTPUT[] {double* data}
/**
 * Reads an attribute with type H5T_NATIVE_DOUBLE from disk.
 *
 * <p>H5LTget_attribute_double reads an attribute named attr_name  
 * that is attached to the object specified by the name obj_name. 
 * The HDF5 datatype of the attribute is H5T_NATIVE_DOUBLE.</p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @parma data (Output) Buffer with data.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_double(hid_t loc_id, const char *obj_name,
				const char *attr_name, double* data);
%clear double* data;

%apply double* BUFF {double* data}
%rename(H5LTget_attribute_double_direct) H5LTget_attribute_double;
/**
 * Reads an attribute with type H5T_NATIVE_DOUBLE from disk.
 *
 * @see H5LTget_attribute_double
 */
herr_t H5LTget_attribute_double(hid_t loc_id, const char *obj_name,
			      const char *attr_name, double* data);
%clear double* data;

/*-------------------------------------------------------------------------
 *
 * Query attribute functions
 *
 *-------------------------------------------------------------------------
 */

%apply int* OUTPUT {int* rank}
/**
 * Gets the dimensionality of an attribute.
 *
 * <p>H5LTget_attribute_ndims gets the dimensionality of an attribute 
 * named attr_name that is  attached to the object specified by the name obj_name. </p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @parma attr_name The attribute name.
 * @param rank (Output) The dimensionality of the attribute.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5LTget_attribute_ndims(hid_t loc_id, const char *obj_name,
                                const char *attr_name, int *rank );

/**
 * Gets information about an attribute.
 *
 * <p>H5LTget_attribute_info gets information about an attribute named attr_name 
 * attached to the object specified by the name obj_name. </p>
 *
 * @param loc_id Identifier of the object ( dataset or group) to read the attribute from.
 * @param obj_name The name of the object that the attribute is attached to.
 * @param attr_name The attribute name.
 * @param dims (Output) The dimensions of the attribute.
 * @param type_class (Output) The class identifier. For a list of the HDF5 class types please refer to the Datatype Interface API help.
 * @param type_size (Output) The size of the datatype in bytes.
 */
herr_t H5LTget_attribute_info(hid_t loc_id, const char *obj_name,
                               const char *attr_name,
                               hsize_t *dims, H5T_class_t *type_class, size_t *type_size);














