%module DatatypeLib
%include "typemaps.i"
%include "buffers.i"

%import "H5types.i"
%include "H5Ttypes.i"
%include "void.i"

%header %{
#include "H5Tpublic.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"

/* All datatype conversion functions are... */
//typedef herr_t (*H5T_conv_t) (hid_t src_id, hid_t dst_id, H5T_cdata_t *cdata,
//      size_t nelmts, size_t buf_stride, size_t bkg_stride, void *buf,
//      void *bkg, hid_t dset_xfer_plist);

/*
 * If an error occurs during a data type conversion then the function
 * registered with H5Tset_overflow() is called.  It's arguments are the
 * source and destination data types, a buffer which has the source value,
 * and a buffer to receive an optional result for the overflow conversion.
 * If the overflow handler chooses a value for the result it should return
 * non-negative; otherwise the hdf5 library will choose an appropriate
 * result.
 */
//typedef herr_t (*H5T_overflow_t)(hid_t src_id, hid_t dst_id,	
//			 void *src_buf, void *dst_buf);

/*
 * The IEEE floating point types in various byte orders.
 */

%immutable;
%rename H5T_IEEE_F32BE_g H5T_IEEE_F32BE;
%rename H5T_IEEE_F32LE_g H5T_IEEE_F32LE;
%rename H5T_IEEE_F64BE_g H5T_IEEE_F64BE;
%rename H5T_IEEE_F64LE_g H5T_IEEE_F64LE;
extern hid_t H5T_IEEE_F32BE_g;
extern hid_t H5T_IEEE_F32LE_g;
extern hid_t H5T_IEEE_F64BE_g;
extern hid_t H5T_IEEE_F64LE_g;
%mutable;


/*
 * These are "standard" types.  For instance, signed (2's complement) and
 * unsigned integers of various sizes and byte orders.
 */
%immutable;
%rename H5T_STD_I8BE_g H5T_STD_I8BE;
%rename H5T_STD_I8LE_g H5T_STD_I8LE;
%rename H5T_STD_I16BE_g H5T_STD_I16BE;
%rename H5T_STD_I16LE_g H5T_STD_I16LE;
%rename H5T_STD_I32BE_g H5T_STD_I32BE;
%rename H5T_STD_I32LE_g H5T_STD_I32LE;
%rename H5T_STD_I64BE_g H5T_STD_I64BE;
%rename H5T_STD_I64LE_g H5T_STD_I64LE;
%rename H5T_STD_U8BE_g H5T_STD_U8BE;
%rename H5T_STD_U8LE_g H5T_STD_U8LE;
%rename H5T_STD_U16BE_g H5T_STD_U16BE;
%rename H5T_STD_U16LE_g H5T_STD_U16LE;
%rename H5T_STD_U32BE_g H5T_STD_U32BE;
%rename H5T_STD_U32LE_g H5T_STD_U32LE;
%rename H5T_STD_U64BE_g H5T_STD_U64BE;
%rename H5T_STD_U64LE_g H5T_STD_U64LE;
%rename H5T_STD_B8BE_g H5T_STD_B8BE;
%rename H5T_STD_B8LE_g H5T_STD_B8LE;
%rename H5T_STD_B16BE_g H5T_STD_B16BE;
%rename H5T_STD_B16LE_g H5T_STD_B16LE;
%rename H5T_STD_B32BE_g H5T_STD_B32BE;
%rename H5T_STD_B32LE_g H5T_STD_B32LE;
%rename H5T_STD_B64BE_g H5T_STD_B64BE;
%rename H5T_STD_B64LE_g H5T_STD_B64LE;
%rename H5T_STD_REF_OBJ_g H5T_STD_REF_OBJ;
%rename H5T_STD_REF_DSETREG_g H5T_STD_REF_DSETREG;
extern hid_t H5T_STD_I8BE_g;
extern hid_t H5T_STD_I8LE_g;
extern hid_t H5T_STD_I16BE_g;
extern hid_t H5T_STD_I16LE_g;
extern hid_t H5T_STD_I32BE_g;
extern hid_t H5T_STD_I32LE_g;
extern hid_t H5T_STD_I64BE_g;
extern hid_t H5T_STD_I64LE_g;
extern hid_t H5T_STD_U8BE_g;
extern hid_t H5T_STD_U8LE_g;
extern hid_t H5T_STD_U16BE_g;
extern hid_t H5T_STD_U16LE_g;
extern hid_t H5T_STD_U32BE_g;
extern hid_t H5T_STD_U32LE_g;
extern hid_t H5T_STD_U64BE_g;
extern hid_t H5T_STD_U64LE_g;
extern hid_t H5T_STD_B8BE_g;
extern hid_t H5T_STD_B8LE_g;
extern hid_t H5T_STD_B16BE_g;
extern hid_t H5T_STD_B16LE_g;
extern hid_t H5T_STD_B32BE_g;
extern hid_t H5T_STD_B32LE_g;
extern hid_t H5T_STD_B64BE_g;
extern hid_t H5T_STD_B64LE_g;
extern hid_t H5T_STD_REF_OBJ_g;
extern hid_t H5T_STD_REF_DSETREG_g;
%mutable;

/*
 * Types which are particular to Unix.
 */
%immutable;
%rename H5T_UNIX_D32BE_g H5T_UNIX_D32BE;
%rename H5T_UNIX_D32LE_g H5T_UNIX_D32LE;
%rename H5T_UNIX_D64BE_g H5T_UNIX_D64BE;
%rename H5T_UNIX_D64LE_g H5T_UNIX_D64LE;
extern hid_t H5T_UNIX_D32BE_g;
extern hid_t H5T_UNIX_D32LE_g;
extern hid_t H5T_UNIX_D64BE_g;
extern hid_t H5T_UNIX_D64LE_g;
%mutable;

/*
 * Types particular to the C language.  String types use `bytes' instead
 * of `bits' as their size.
 */
%immutable;
%rename H5T_C_S1_g  H5T_C_S1;
extern hid_t H5T_C_S1_g;
%mutable;

/*
 * Types particular to Fortran.
 */
%immutable;
%rename H5T_FORTRAN_S1_g H5T_FORTRAN_S1;
extern hid_t H5T_FORTRAN_S1_g;
%mutable;

%immutable;
%rename H5T_NATIVE_SCHAR_g H5T_NATIVE_SCHAR;
%rename H5T_NATIVE_UCHAR_g H5T_NATIVE_UCHAR;
%rename H5T_NATIVE_SHORT_g H5T_NATIVE_SHORT;
%rename H5T_NATIVE_USHORT_g H5T_NATIVE_USHORT;
%rename H5T_NATIVE_INT_g H5T_NATIVE_INT;
%rename H5T_NATIVE_UINT_g H5T_NATIVE_UINT;
%rename H5T_NATIVE_LONG_g H5T_NATIVE_LONG;
%rename H5T_NATIVE_ULONG_g H5T_NATIVE_ULONG;
%rename H5T_NATIVE_LLONG_g H5T_NATIVE_LLONG;
%rename H5T_NATIVE_ULLONG_g H5T_NATIVE_ULLONG;
%rename H5T_NATIVE_FLOAT_g H5T_NATIVE_FLOAT;
%rename H5T_NATIVE_DOUBLE_g H5T_NATIVE_DOUBLE;
%rename H5T_NATIVE_LDOUBLE_g H5T_NATIVE_LDOUBLE;
%rename H5T_NATIVE_B8_g H5T_NATIVE_B8;
%rename H5T_NATIVE_B16_g H5T_NATIVE_B16;
%rename H5T_NATIVE_B32_g H5T_NATIVE_B32;
%rename H5T_NATIVE_B64_g H5T_NATIVE_B64;
%rename H5T_NATIVE_OPAQUE_g H5T_NATIVE_OPAQUE;
%rename H5T_NATIVE_HADDR_g H5T_NATIVE_HADDR;
%rename H5T_NATIVE_HSIZE_g H5T_NATIVE_HSIZE;
%rename H5T_NATIVE_HSSIZE_g H5T_NATIVE_HSSIZE;
%rename H5T_NATIVE_HERR_g H5T_NATIVE_HERR;
%rename H5T_NATIVE_HBOOL_g H5T_NATIVE_HBOOL;
extern hid_t H5T_NATIVE_SCHAR_g;
extern hid_t H5T_NATIVE_UCHAR_g;
extern hid_t H5T_NATIVE_SHORT_g;
extern hid_t H5T_NATIVE_USHORT_g;
extern hid_t H5T_NATIVE_INT_g;
extern hid_t H5T_NATIVE_UINT_g;
extern hid_t H5T_NATIVE_LONG_g;
extern hid_t H5T_NATIVE_ULONG_g;
extern hid_t H5T_NATIVE_LLONG_g;
extern hid_t H5T_NATIVE_ULLONG_g;
extern hid_t H5T_NATIVE_FLOAT_g;
extern hid_t H5T_NATIVE_DOUBLE_g;
extern hid_t H5T_NATIVE_LDOUBLE_g;
extern hid_t H5T_NATIVE_B8_g;
extern hid_t H5T_NATIVE_B16_g;
extern hid_t H5T_NATIVE_B32_g;
extern hid_t H5T_NATIVE_B64_g;
extern hid_t H5T_NATIVE_OPAQUE_g;
extern hid_t H5T_NATIVE_HADDR_g;
extern hid_t H5T_NATIVE_HSIZE_g;
extern hid_t H5T_NATIVE_HSSIZE_g;
extern hid_t H5T_NATIVE_HERR_g;
extern hid_t H5T_NATIVE_HBOOL_g;
%mutable;

%immutable;
%rename H5T_NATIVE_INT8_g H5T_NATIVE_INT8;
%rename H5T_NATIVE_UINT8_g H5T_NATIVE_UINT8;
%rename H5T_NATIVE_INT_LEAST8_g H5T_NATIVE_INT_LEAST8;
%rename H5T_NATIVE_UINT_LEAST8_g H5T_NATIVE_UINT_LEAST8;
%rename H5T_NATIVE_INT_FAST8_g H5T_NATIVE_INT_FAST8;
%rename H5T_NATIVE_UINT_FAST8_g H5T_NATIVE_UINT_FAST8;
extern hid_t H5T_NATIVE_INT8_g;
extern hid_t H5T_NATIVE_UINT8_g;
extern hid_t H5T_NATIVE_INT_LEAST8_g;
extern hid_t H5T_NATIVE_UINT_LEAST8_g;
extern hid_t H5T_NATIVE_INT_FAST8_g;
extern hid_t H5T_NATIVE_UINT_FAST8_g;
%mutable;

%immutable;
%rename H5T_NATIVE_INT16_g H5T_NATIVE_INT16;
%rename H5T_NATIVE_UINT16_g H5T_NATIVE_UINT16;
%rename H5T_NATIVE_INT_LEAST16_g H5T_NATIVE_INT_LEAST16;
%rename H5T_NATIVE_UINT_LEAST16_g H5T_NATIVE_UINT_LEAST16;
%rename H5T_NATIVE_INT_FAST16_g H5T_NATIVE_INT_FAST16;
%rename H5T_NATIVE_UINT_FAST16_g H5T_NATIVE_UINT_FAST16;
extern hid_t H5T_NATIVE_INT16_g;
extern hid_t H5T_NATIVE_UINT16_g;
extern hid_t H5T_NATIVE_INT_LEAST16_g;
extern hid_t H5T_NATIVE_UINT_LEAST16_g;
extern hid_t H5T_NATIVE_INT_FAST16_g;
extern hid_t H5T_NATIVE_UINT_FAST16_g;
%mutable;

%immutable;
%rename H5T_NATIVE_INT32_g H5T_NATIVE_INT32;
%rename H5T_NATIVE_UINT32_g H5T_NATIVE_UINT32;
%rename H5T_NATIVE_INT_LEAST32_g H5T_NATIVE_INT_LEAST32;
%rename H5T_NATIVE_UINT_LEAST32_g H5T_NATIVE_UINT_LEAST32;
%rename H5T_NATIVE_INT_FAST32_g H5T_NATIVE_INT_FAST32;
%rename H5T_NATIVE_UINT_FAST32_g H5T_NATIVE_UINT_FAST32;
extern hid_t H5T_NATIVE_INT32_g;
extern hid_t H5T_NATIVE_UINT32_g;
extern hid_t H5T_NATIVE_INT_LEAST32_g;
extern hid_t H5T_NATIVE_UINT_LEAST32_g;
extern hid_t H5T_NATIVE_INT_FAST32_g;
extern hid_t H5T_NATIVE_UINT_FAST32_g;
%mutable;

%immutable;
%rename H5T_NATIVE_INT64_g H5T_NATIVE_INT64;
%rename H5T_NATIVE_UINT64_g H5T_NATIVE_UINT64;
%rename H5T_NATIVE_INT_LEAST64_g H5T_NATIVE_INT_LEAST64;
%rename H5T_NATIVE_UINT_LEAST64_g H5T_NATIVE_UINT_LEAST64;
%rename H5T_NATIVE_INT_FAST64_g H5T_NATIVE_INT_FAST64;
%rename H5T_NATIVE_UINT_FAST64_g H5T_NATIVE_UINT_FAST64;
extern hid_t H5T_NATIVE_INT64_g;
extern hid_t H5T_NATIVE_UINT64_g;
extern hid_t H5T_NATIVE_INT_LEAST64_g;
extern hid_t H5T_NATIVE_UINT_LEAST64_g;
extern hid_t H5T_NATIVE_INT_FAST64_g;
extern hid_t H5T_NATIVE_UINT_FAST64_g;
%mutable;


/**
 * Creates an array datatype object.
 *
 * <p>H5Tarray_create1 creates a new array datatype object.</p>
 *
 * <p>base_type_id is the datatype of every element of the array,
 * i.e., of the number at each position in the array.</p>
 *
 * <p>rank is the number of dimensions and the size of each dimension
 * is specified in the array dims. The value of rank is currently
 * limited to H5S_MAX_RANK and must be greater than 0 (zero). All
 * dimension sizes specified in dims must be greater than 0
 * (zero).</p>
 *
 * <p>The array perm is designed to contain the dimension permutation,
 * i.e. C versus FORTRAN array order.  (The parameter perm is
 * currently unused and is not yet implemented.) </p>
 *
 * @param base_type_id Datatype identifier for the array base datatype.
 * @param rank Rank of the array.
 * @param dims Size of each array dimension.
 * @param perm Dimension permutation.
 *
 * @return Returns a valid datatype identifier if successful;
 * otherwise returns a negative value.
 *
 * @ingroup tArray
 *
 * @deprecated
 */
%apply long long INPUT[] {const hsize_t dim[]}
%apply int INPUT[] {const int perm[]}
%javamethodmodifiers H5Tarray_create1 "@Deprecated\n   public"
hid_t H5Tarray_create1(hid_t base_id, int ndims, const hsize_t dim[/*ndims*/], 
		       const int perm[/*ndims*/]);
%clear const hsize_t dim[];
%clear const int perm[];

/**
 * Creates an array datatype object.
 *
 * <p>H5Tarray_create2 creates a new array datatype object.</p>
 *
 * <p>base_type_id is the datatype of every element of the array,
 * i.e., of the number at each position in the array.</p>
 *
 * <p>rank is the number of dimensions and the size of each dimension
 * is specified in the array dims. The value of rank is currently
 * limited to H5S_MAX_RANK and must be greater than 0 (zero). All
 * dimension sizes specified in dims must be greater than 0
 * (zero). </p>
 *
 * @param base_type_id Datatype identifier for the array base datatype.
 * @param rank Rank of the array.
 * @param dims Size of each array dimension.
 *
 * @return Returns a valid datatype identifier if successful;
 * otherwise returns a negative value.
 *
 * @ingroup tArray
 */
%apply long long INPUT[] {const hsize_t dim[]}
hid_t H5Tarray_create2(hid_t base_id, unsigned ndims, 
		       const hsize_t dim[/*ndims*/]);
%clear const hsize_t dim[];
/**
 * Releases a datatype.
 *
 * <p>H5Tclose releases a datatype. Further access through the
 * datatype identifier is illegal. Failure to release a datatype with
 * this call will result in resource leaks.</p>
 *
 * @param dtype_id Identifier of datatype to release.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tType
 */
herr_t H5Tclose(hid_t dtype_id);

/**
 * Commits a transient datatype to a file, creating a new named datatype.
 *
 * <p>H5Tcommit1 commits the transient datatype (not immutable) to a
 * file, turning it into a named datatype.</p>
 *
 * <p>The datatype dtype_id is committed as a named datatype at the
 * location loc_id, which is either a file or group identifier, with
 * the name name.</p>
 *
 * <p>name can be a relative path based at loc_id or an absolute path
 * from the root of the file. If any of the groups specified in that
 * path do not already exist, the dataset must be created with
 * H5Tcommit_anon and linked into the file structure with H5Llink.</p>
 *
 * <p>As is the case for any object in a group, the length of the name
 * of a named datatype is not limited.</p>
 *
 * <p>See H5Tcommit_anon for a discussion of the differences between
 * H5Tcommit and H5Tcommit_anon. </p>
 *
 * @param loc_id A file or group identifier.
 * @param name A datatype name.
 * @param dtype_id A datatype identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tType
 *
 * @deprecated
 */
%javamethodmodifiers H5Tcommit1 "@Deprecated\n   public"
herr_t H5Tcommit1(hid_t loc_id, const char *name, hid_t dtype_id);

/**
 * Commits a transient datatype, linking it into the file and creating
 * a new named datatype.
 *
 * <p>H5Tcommit2 saves a transient datatype as an immutable named
 * datatype in a file. The datatype specified by dtype_id is committed
 * to the file with the name name at the location specified by loc_id
 * and with the datatype creation and access property lists tcpl_id
 * and tapl_id, respectively.</p>
 *
 * <p>loc_id may be a file identifier, or a group identifier within
 * that file. name may be either an absolute path in the file or a
 * relative path from loc_id naming the newly-commited datatype.</p>
 *
 * <p>The link creation property list, lcpl_id, governs creation of
 * the link(s) by which the new named datatype is accessed and the
 * creation of any intermediate groups that may be missing.</p>
 *
 * <p>Once commited, this datatype may be used to define the datatype
 * of any other dataset or attribute in the file. </p>
 *
 * @param loc_id Location identifier.
 * @param name Name given to committed datatype.
 * @param dtype_id Identifier of datatype to be committed.
 * @param lcpl_id Link creation property list.
 * @param tcpl_id Datatype creation property list.
 * @param tapl_id Datatype access property list.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tType
 */
herr_t H5Tcommit2(hid_t loc_id, const char *name, hid_t dtype_id, 
		  hid_t lcpl_id, hid_t tcpl_id, hid_t tapl_id);

/**
 * Commits a transient datatype to a file, creating a new named
 * datatype, but does not link it into the file structure.
 *
 * <p>H5Tcommit_anon commits a transient datatype (not immutable) to a
 * file, turning it into a named datatype with the specified creation
 * and property lists. With default property lists, H5P_DEFAULT,
 * H5Tcommit_anon provides similar functionality to that of H5Tcommit,
 * with the differences described below.</p>
 *
 * <p>The datatype access property list identifier, tapl_id, is
 * provided for future functionality and is not used at this
 * time. This parameter should always be passed as the value
 * H5P_DEFAULT.</p>
 *
 * <p>Note that H5Tcommit_anon does not link this newly-committed
 * datatype into the file. After the H5Tcommit_anon call, the datatype
 * identifier dtype_id must be linked into the HDF5 file structure
 * with H5Llink or it will be deleted from the file when the file is
 * closed.</p>
 *
 * <p>The differences between this function and H5Tcommit are as
 * follows:</p>
 <ul>
    <li>H5Tcommit_anon explicitly includes property lists, which
    provides for greater control of the creation process and of the
    properties of the new named datatype. H5Tcommit always uses
    default properties.</li>
    <li>H5Tcommit_anon neither provides the new named datatype's name
    nor links it into the HDF5 file structure; those actions must be
    performed separately through a call to H5Llink, which offers
    greater control over linking. </li>
 </ul>
 *
 * @param loc_id A file or group identifier specifying the file in
 * which the new named datatype is to be created.
 * @param dtype_id A datatype identifier.
 * @param tcpl_id A datatype creation property list identifier. 
 * @param tapl_id A datatype access property list
 * identifier. Currently unused; should always be passed as the value
 * H5P_DEFAULT.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tType
 */
herr_t H5Tcommit_anon(hid_t loc_id, hid_t dtype_id, 
		      hid_t tcpl_id, hid_t tapl_id);

/**
 * Determines whether a datatype is a named type or a transient type.
 *
 * <p>H5Tcommitted queries a type to determine whether the type
 * specified by the dtype_id identifier is a named type or a transient
 * type. If this function returns a positive value, then the type is
 * named (that is, it has been committed, perhaps by some other
 * application). Datasets which return committed datatypes with
 * H5Dget_type() are able to share the datatype with other datasets in
 * the same file.</p>
 *
 * @param dtype_id Datatype identifier.
 *
 * @return When successful, returns a positive value, for TRUE, if the
 * datatype has been committed, or 0 (zero), for FALSE, if the
 * datatype has not been committed. Otherwise returns a negative
 * value.
 *
 * @ingroup tType
 */
htri_t H5Tcommitted(hid_t dtype_id);

/**
 * Check whether the library's default conversion is hard conversion.
 *
 * <p>H5Tcompiler_conv finds out whether the library's conversion
 * function from type src_id to type dst_id is a compiler (hard)
 * conversion. A compiler conversion uses compiler's casting; a
 * library (soft) conversion uses the library's own conversion
 * function.</p>
 *
 * @param src_id Identifier for the source datatype.
 * @param dst_id Identifier for the destination datatype.
 *
 * @return Returns TRUE for compiler conversion, FALSE for library
 * conversion, FAIL for the function's failure.
 *
 * @ingroup tConvert
 */
htri_t H5Tcompiler_conv(hid_t src_id, hid_t dst_id);


/**
 * Converts data from between specified datatypes.
 *
 * <p>H5Tconvert converts nelmts elements from the type specified by
 * the src_id identifier to type dst_id. The source elements are
 * packed in buf and on return the destination will be packed in
 * buf. That is, the conversion is performed in place. The optional
 * background buffer is an array of nelmts values of destination type
 * which are merged with the converted values to fill in cracks (for
 * instance, background might be an array of structs with the a and b
 * fields already initialized and the conversion of buf supplies the c
 * and d field values).</p>
 *
 * <p>The parameter plist_id contains the dataset transfer property
 * list identifier which is passed to the conversion functions. As of
 * Release 1.2, this parameter is only used to pass along the
 * variable-length datatype custom allocation information. </p>
 *
 * @param src_id Identifier for the source datatype.
 * @param dst_id Identifier for the destination datatype.
 * @param nelmts Size of array buf.
 * @param buf (Input/Output) Array containing pre- and post-conversion
 * values.
 * @param background Optional background buffer.
 * @param plist_id Dataset transfer property list identifier.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tConvert
 */
%apply void* BUFF {void* buf};
%apply void* BUFF {void* background};
herr_t H5Tconvert(hid_t src_id, hid_t dst_id, size_t nelmts,
		  void *buf, void *background, hid_t plist_id);
%clear void* background;
%clear void* buf;

/**
 * Copies an existing datatype.
 *
 * <p>H5Tcopy copies an existing datatype. The returned type is always
 * transient and unlocked.</p>
 *
 * <p>The dtype_id argument can be either a datatype identifier, a
 * predefined datatype (defined in H5Tpublic.h), or a dataset
 * identifier. If dtype_id is a dataset identifier instead of a
 * datatype identifier, then this function returns a transient,
 * modifiable datatype which is a copy of the dataset's datatype.</p>
 *
 * <p>The datatype identifier returned should be released with
 * H5Tclose or resource leaks will occur.</p>
 *
 * @param dtype_id Identifier of datatype to copy. Can be a datatype
 * identifier, a predefined datatype (defined in H5Tpublic.h), or a
 * dataset identifier.
 *
 * @return Returns a datatype identifier if successful; otherwise
 * returns a negative value
 *
 * @ingroup tType
 */
hid_t H5Tcopy(hid_t dtype_id);


/**
 * Creates a new datatype.
 *
 * <p>H5Tcreate creates a new datatype of the specified class with the
 * specified number of bytes.</p>
 *
 * <p>The following datatype classes are supported with this
 * function:</p>
 <ul>
    <li>H5T_COMPOUND</li>
    <li>H5T_OPAQUE</li>
    <li>H5T_ENUM </li>
 </ul>
 *
 * <p>Use H5Tcopy to create integer or floating-point datatypes.</p>
 *
 * <p>The datatype identifier returned from this function should be
 * released with H5Tclose or resource leaks will result. </p>
 *
 * @param type Class of datatype to create.
 * @param size The number of bytes in the datatype to create.
 *
 * @return Returns datatype identifier if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tType
 */
hid_t H5Tcreate(H5T_class_t type, size_t size);


/**
 * Decode a binary object description of data type and return a new
 * object handle.
 *
 * <p>Given an object description of data type in binary in a buffer,
 * H5Tdecode reconstructs the HDF5 data type object and returns a new
 * object handle for it. The binary description of the object is
 * encoded by H5Tencode. User is responsible for passing in the right
 * buffer.</p>
 *
 * @param buf Buffer for the data type object to be decoded.
 *
 * @return Returns an object ID(non-negative) if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tConvert
 */
%apply void* BUFF {const void* buf}
hid_t H5Tdecode(const void *buf);
%clear const void* buf;

/**
 * Determines whether a datatype contains any datatypes of the given
 * datatype class.
 *
 * <p>H5Tdetect_class determines whether the datatype specified in
 * dtype_id contains any datatypes of the datatype class specified in
 * dtype_class.</p>
 *
 * <p>This function is useful primarily in recursively examining all
 * the fields and/or base types of compound, array, and
 * variable-length datatypes.</p>
 *
 * <p>Valid class identifiers are as defined in H5Tget_class. </p>
 *
 * @param dtype_id Datatype identifier.
 * @param dtype_class Datatype class.
 *
 * @return Returns TRUE or FALSE if successful; otherwise returns a
 * negative value.
 *
 * @ingroup tType
 */
htri_t H5Tdetect_class(hid_t dtype_id, H5T_class_t dtype_class);


/**
 * Encode a data type object description into a binary buffer.
 *
 * <p>Given data type ID, H5Tencode converts a data type description
 * into binary form in a buffer. Using this binary form in the buffer,
 * a data type object can be reconstructed using H5Tdecode to return a
 * new object handle (hid_t) for this data type.</p>
 *
 * <p>A preliminary H5Tencode call can be made to find out the size of
 * the buffer needed. This value is returned as nalloc. That value can
 * then be assigned to nalloc for a second H5Tencode call, which will
 * retrieve the actual encoded object.</p>
 *
 * <p>If the library finds out nalloc is not big enough for the
 * object, it simply returns the size of the buffer needed through
 * nalloc without encoding the provided buffer. </p>
 *
 * @param obj_id Identifier of the object to be encoded.
 * @param buf (Input/Output) Buffer for the object to be encoded
 * into. If the provided buffer is NULL, only the size of buffer
 * needed is returned through nalloc.
 * @param nalloc (Input/Output) The size of the allocated buffer. The
 * size of the buffer needed is returned.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tConvert
 */
%apply void* BUFF {void* buf}
%apply unsigned int INOUT[] {size_t* nalloc}
herr_t H5Tencode(hid_t obj_id, void *buf, size_t *nalloc);
%clear size_t* nalloc;
%clear void* buf;

/**
 * Creates a new enumeration datatype.
 *
 * <p>H5Tenum_create creates a new enumeration datatype based on the
 * specified base datatype, parent_id, which must be an integer
 * type.</p>
 *
 * @param base_id Datatype identifier for the base datatype.
 *
 * @return Returns the datatype identifier for the new enumeration
 * datatype if successful; otherwise returns a negative value.
 *
 * @ingroup tEnum
 */
hid_t H5Tenum_create(hid_t base_id);

/**
 * Inserts a new enumeration datatype member.
 *
 * <p>H5Tenum_insert inserts a new enumeration datatype member into an
 * enumeration datatype.</p>
 *
 * <p>dtype_id is the enumeration datatype, name is the name of the
 * new member, and value points to the value of the new member.</p>
 *
 * <p>name and value must both be unique within dtype_id.</p>
 *
 * <p>value points to data which is of the datatype defined when the
 * enumeration datatype was created. </p>
 *
 * @param dtype_id Datatype identifier for the enumeration datatype.
 * @param name Name of the new member.
 * @param value Pointer to the value of the new member.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tEnum
 * @ingroup tCompound
 */
%apply long* INPUT {const void* value}
herr_t H5Tenum_insert(hid_t dtype_id, const char *name, const void *value);
%clear const void* value;

%rename(H5Tenum_insert_direct) H5Tenum_insert;
/**
 * Inserts a new enumeration datatype member.
 *
 * @see H5Tenum_insert
 */
%apply void* BUFF {const void* value}
herr_t H5Tenum_insert(hid_t dtype_id, const char *name, const void *value);
%clear const void* value;


/**
 * Returns the symbol name corresponding to a specified member of an
 * enumeration datatype.
 *
 * <p>H5Tenum_nameof finds the symbol name that corresponds to the
 * specified value of the enumeration datatype dtype_id.</p>
 *
 * <p>At most size characters of the symbol name are copied into the
 * name buffer. If the entire symbol name and null terminator do not
 * fit in the name buffer, then as many characters as possible are
 * copied (not null terminated) and the function fails. </p>
 *
 * @param dtype_id Enumeration datatype identifier.
 * @param value Value of the enumeration datatype.
 * @param name (Output) Buffer for output of the symbol name.
 * @param size Anticipated size of the symbol name, in bytes (characters).
 *
 * @return Returns a non-negative value if successful. Otherwise
 * returns a negative value and, if size allows it, the first
 * character of name is set to NULL.
 *
 * @ingroup tEnum
 */
%apply long* VOID_IN {const void* value}
%apply signed char OUTPUT[] {char* oname}
herr_t H5Tenum_nameof(hid_t dtype_id, const void *value, char *oname/*out*/, size_t size);
%clear char* oname;
%clear const void* value;

%rename(H5Tenum_nameof_direct) H5Tenum_nameof;
/**
 * Returns the symbol name corresponding to a specified member of an
 * enumeration datatype.
 *
 * @see H5Tenum_nameof
 */
%apply void* BUFF {const void* value}
%apply signed char OUTPUT[] {char* oname}
herr_t H5Tenum_nameof(hid_t dtype_id, const void *value, char *oname/*out*/, size_t size);
%clear char* oname;
%clear const void* value;

/**
 * Returns the value corresponding to a specified member of an
 * enumeration datatype.
 *
 * <p>H5Tenum_valueof finds the value that corresponds to the
 * specified name of the enumeration datatype dtype_id.</p>
 *
 * <p>The value argument should be at least as large as the value of
 * H5Tget_size(type) in order to hold the result. </p>
 *
 * @param dtype_id Enumeration datatype identifier.
 * @param name Symbol name of the enumeration datatype.
 * @param value (Output) Buffer for output of the value of the
 * enumeration datatype.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tEnum
 */
%apply long* VOID_OUT {void* value}
herr_t H5Tenum_valueof(hid_t dtype_id, const char *name, void *value/*out*/);
%clear void* value;

%rename(H5Tenum_valueof_direct) H5Tenum_valueof;
/**
 * Returns the value corresponding to a specified member of an
 * enumeration datatype.
 *
 * @see H5Tenum_valueof
 */
%apply void* BUFF {void* value}
herr_t H5Tenum_valueof(hid_t dtype_id, const char *name, void *value/*out*/);
%clear void* value;

/**
 * Determines whether two datatype identifiers refer to the same
 * datatype.
 *
 * <p>H5Tequal determines whether two datatype identifiers refer to
 * the same datatype.</p>
 *
 * @param dtype1_id Identifier of datatype to compare.
 * @param dtype2_id Identifier of datatype to compare.
 *
 * @return When successful, returns a positive value, for TRUE, if the
 * datatype identifiers refer to the same datatype, or 0 (zero), for
 * FALSE. Otherwise returns a negative value.
 *
 * @ingroup tType
 */
htri_t H5Tequal(hid_t dtype1_id, hid_t dtype2_id);

/**
 * Finds a conversion function.
 *
 * <p>H5Tfind finds a conversion function that can handle a conversion
 * from type src_id to type dst_id. The pcdata argument is a pointer
 * to a pointer to type conversion data which was created and
 * initialized by the soft type conversion function of this path when
 * the conversion function was installed on the path.</p>
 *
 * @param src_id Identifier for the source datatype.
 * @param dst_id Identifier for the destination datatype.
 * @param pcdata (Output) Pointer to type conversion data.
 *
 * @return Returns a pointer to a suitable conversion function if
 * successful. Otherwise returns NULL.
 *
 * @ingroup tConvert
 */
//H5T_conv_t H5Tfind(hid_t src_id, hid_t dst_id, H5T_cdata_t **pcdata);

/**
 * Retrieves sizes of array dimensions.
 *
 * <p>H5Tget_array_dims1 returns the sizes of the dimensions and the
 * dimension permutations of the specified array datatype object.</p>
 *
 * <p>The sizes of the dimensions are returned in the array dims.</p>
 *
 * <p>The dimension permutations, i.e., C versus FORTRAN array order,
 * are returned in the array perm. (Not implemented at this time.)
 * </p>
 *
 * @param adtype_id Datatype identifier of array object.
 * @param dims (Output) Sizes of array dimensions.
 * @param perm (Output) Dimension permutations.
 *
 * @return Returns the non-negative number of dimensions of the array
 * type if successful; otherwise returns a negative value.
 *
 * @deprecated
 * 
 * @ingroup tArray
 */
/* FIXME this map truncates hsize_t[]} */
%apply long long OUTPUT[] {hsize_t dims[]}
%apply int OUTPUT[] {int perm[]}
%javamethodmodifiers H5Tget_array_dims1 "@Deprecated\n   public"
int H5Tget_array_dims1(hid_t type_id, hsize_t dims[], int perm[]);
%clear int perm[];
%clear hsize_t dims[];

/**
 * Retrieves sizes of array dimensions.
 *
 * <p>H5Tget_array_dims2 returns the sizes of the dimensions and the
 * dimension permutations of the specified array datatype object.</p>
 *
 * <p>The sizes of the dimensions are returned in the array dims. </p>
 *
 * @param adtype_id Datatype identifier of array object.
 * @param dims (Output) Sizes of array dimensions.
 *
 * @return Returns the non-negative number of dimensions of the array
 * type if successful; otherwise returns a negative value.
 *
 * @ingroup tArray
 */
%apply long long OUTPUT[] {hsize_t dims[]}
int H5Tget_array_dims2(hid_t type_id, hsize_t dims[]);
%clear hsize_t dims[];

/**
 * Returns the rank of an array datatype.
 *
 * <p>H5Tget_array_ndims returns the rank, the number of dimensions,
 * of an array datatype object.</p>
 *
 * @param adtype_id Datatype identifier of array object.
 *
 * @return Returns the rank of the array if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tArray
 */
int H5Tget_array_ndims(hid_t adtype_id);

/**
 * Returns the datatype class identifier.
 *
 * <p>H5Tget_class returns the datatype class identifier.</p>
 *
 * <p>Valid class identifiers, as defined in H5Tpublic.h, are:</p>
 <ul>
    <li>H5T_INTEGER</li>
    <li>H5T_FLOAT</li>
    <li>H5T_STRING</li>
    <li>H5T_BITFIELD</li>
    <li>H5T_OPAQUE</li>
    <li>H5T_COMPOUND</li>
    <li>H5T_REFERENCE</li>
    <li>H5T_ENUM</li>
    <li>H5T_VLEN</li>
    <li>H5T_ARRAY</li>
 </ul>
 *
 * <p>Note that the library returns H5T_STRING for both fixed-length
 * and variable-length strings.</p>
 *
 * <p>Unsupported datatype: The time datatype class, H5T_TIME, is not
 * supported. If H5T_TIME is used, the resulting data will be readable
 * and modifiable only on the originating computing platform; it will
 * not be portable to other platforms. </p>
 *
 * @param dtype_id Identifier of datatype to query.
 *
 * @return Returns datatype class identifier if successful; otherwise
 * H5T_NO_CLASS (-1).
 *
 * @ingroup tType
 */
H5T_class_t H5Tget_class(hid_t dtype_id);

/**
 * Returns a copy of a datatype creation property list.
 *
 * <p>H5Tget_create_plist returns a property list identifier for the
 * datatype creation property list associated with the datatype
 * specified by dtype_id.</p>
 *
 * <p>The creation property list identifier should be released with
 * H5Pclose. </p>
 *
 * @param dtype_id Datatype identifier.
 *
 * @return Returns a datatype property list identifier if successful;
 * otherwise returns a negative value.
 *
 * @ingroup tType
 */
hid_t H5Tget_create_plist(hid_t dtype_id);

/**
 * Retrieves the character set type of a string datatype.
 *
 * <p>H5Tget_cset retrieves the character set type of a string
 * datatype. Valid character set types are:</p>
 <dl>
   <dt>H5T_CSET_ASCII</dt>
   <dd>Character set is US ASCII.</dd>
   <dt>H5T_CSET_UTF8</dt>
   <dd>Character set is UTF8.</dd>
 </dl>
 *
 * @param dtype_id Identifier of datatype to query.
 *
 * @return Returns a valid character set type if successful; otherwise
 * H5T_CSET_ERROR (-1).
 *
 * @ingroup tAtomic
 */
H5T_cset_t H5Tget_cset(hid_t dtype_id);

/**
 * Retrieves the exponent bias of a floating-point type.
 *
 * <p>H5Tget_ebias retrieves the exponent bias of a floating-point
 * type.</p>
 *
 * @param dtype_id Identifier of datatype to query.
 *
 * @return Returns the bias if successful; otherwise 0.
 */
size_t H5Tget_ebias(hid_t dtype_id);

/**
 * Retrieves floating point datatype bit field information.
 *
 * <p>H5Tget_fields retrieves information about the locations of the
 * various bit fields of a floating point datatype. The field
 * positions are bit positions in the significant region of the
 * datatype. Bits are numbered with the least significant bit number
 * zero. Any (or even all) of the arguments can be null pointers.</p>
 *
 * @param dtype_id Identifier of datatype to query.
 * @param spos (Output) Pointer to location to return floating-point sign bit.
 * @param epos (Output) Pointer to location to return exponent bit-position.
 * @param esize (Output) Pointer to location to return size of
 * exponent in bits.
 * @param mpos (Output) Pointer to location to return mantissa bit-position.
 * @param msize (Output) Pointer to location to return size of
 * mantissa in bits.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
%apply unsigned int* OUTPUT {size_t* spos}
%apply unsigned int* OUTPUT {size_t* epos}
%apply unsigned int* OUTPUT {size_t* esize}
%apply unsigned int* OUTPUT {size_t* mpos}
%apply unsigned int* OUTPUT {size_t* msize}
herr_t H5Tget_fields(hid_t type_id, size_t *spos/*out*/, 
		     size_t *epos/*out*/, size_t *esize/*out*/, 
		     size_t *mpos/*out*/, size_t *msize/*out*/);
%clear size_t* spos;
%clear size_t* epos;
%clear size_t* esize;
%clear size_t* mpos;
%clear size_t* msize;

/**
 * Retrieves the internal padding type for unused bits in
 * floating-point datatypes.
 *
 * <p>H5Tget_inpad retrieves the internal padding type for unused bits
 * in floating-point datatypes. Valid padding types are:</p>
 <dl>
    <dt>H5T_PAD_ZERO (0)</dt>
    <dd>Set background to zeros. </dd>
    <dt>H5T_PAD_ONE (1)</dt>
    <dd>Set background to ones. </dd>
    <dt>H5T_PAD_BACKGROUND (2)</dt>
    <dd>Leave background alone. </dd>
 </dl>
 *
 * @param dtype_id Identifier of datatype to query.
 *
 * @return Returns a valid padding type if successful; otherwise
 * H5T_PAD_ERROR (-1).
 *
 * @ingroup tAtomic
 */
H5T_pad_t H5Tget_inpad(hid_t dtype_id);

/**
 * Returns datatype class of compound datatype member.
 *
 * <p>Given a compound datatype, cdtype_id, the function
 * H5Tget_member_class returns the datatype class of the compound
 * datatype member specified by member_no.</p>
 *
 * <p>Valid class identifiers are as defined in H5Tget_class. </p>
 *
 * @param cdtype_id Datatype identifier of compound object.
 * @param membno Compound object member number.
 *
 * @return Returns the datatype class, a non-negative value, if
 * successful; otherwise returns a negative value.
 *
 * @ingroup tCompound
 */
H5T_class_t H5Tget_member_class(hid_t cdtype_id, unsigned membno);

/**
 * Retrieves the index of a compound or enumeration datatype member.
 *
 * <p>H5Tget_member_index retrieves the index of a field of a compound
 * datatype or an element of an enumeration datatype.</p>
 *
 * <p>The name of the target field or element is specified in
 * name.</p>
 *
 * <p>Fields are stored in no particular order with index values of 0
 * through N-1, where N is the value returned by H5Tget_nmembers. </p>
 *
 * @param dtype_id Identifier of datatype to query.
 * @param name Name of the field or member whose index is to be
 * retrieved.
 *
 * @return Returns a valid field or member index if successful;
 * otherwise returns a negative value.
 *
 * @ingroup tCompound
 * @ingroup tEnum
 */
int H5Tget_member_index(hid_t dtype_id, const char *name);

/**
 * Retrieves the name of a compound or enumeration datatype member.
 *
 * <p>H5Tget_member_name retrieves the name of a field of a compound
 * datatype or an element of an enumeration datatype.</p>
 *
 * <p>The index of the target field or element is specified in
 * field_idx. Compound datatype fields and enumeration datatype
 * elements are stored in no particular order with index values of 0
 * through N-1, where N is the value returned by H5Tget_nmembers.</p>
 *
 * <p>A buffer to receive the name of the field is allocated with
 * malloc() and the caller is responsible for freeing the memory
 * used. </p>
 *
 * @param dtype_id Identifier of datatype to query.
 * @param membno Zero-based index of the field or element whose name
 * is to be retrieved.
 *
 * @return Returns a valid pointer to a string allocated with malloc()
 * if successful; otherwise returns NULL.
 *
 * @ingroup tCompound
 * @ingroup tEnum
 */
%newobject H5Tget_member_name;
char* H5Tget_member_name(hid_t dtype_id, unsigned membno);

/**
 * Retrieves the offset of a field of a compound datatype.
 *
 * <p>H5Tget_member_offset retrieves the byte offset of the beginning
 * of a field within a compound datatype with respect to the beginning
 * of the compound data type datum.</p>
 *
 * @param dtype_id Identifier of datatype to query.
 * @param membno Number of the field whose offset is requested.
 * 
 * @return Returns the byte offset of the field if successful;
 * otherwise returns 0 (zero). Note that zero is a valid offset and
 * that this function will fail only if a call to
 * H5Tget_member_class() fails with the same arguments.
 *
 * @ingroup tCompound
 */
size_t H5Tget_member_offset(hid_t dtype_id, unsigned membno);

/**
 * Returns the datatype of the specified member.
 *
 * <p>H5Tget_member_type returns the datatype of the specified
 * member. The caller should invoke H5Tclose() to release resources
 * associated with the type.</p>
 *
 * @param dtype_id Identifier of datatype to query.
 * @param membno Field index (0-based) of the field type to retrieve.
 *
 * @return Returns the identifier of a copy of the datatype of the
 * field if successful; otherwise returns a negative value.
 *
 * @ingroup tCompound
 */
hid_t H5Tget_member_type(hid_t dtype_id, unsigned membno);

/**
 * Returns the value of an enumeration or compound datatype member.
 *
 * <p>H5Tget_member_value returns the value of the enumeration
 * datatype member memb_no.</p>
 *
 * <p>The member value is returned in a user-supplied buffer pointed
 * to by value. </p>
 *
 * @param dtype_id  Datatype identifier for the datatype.
 * @param membno Number of the enumeration datatype member.
 * @param value (Output) Pointer to a buffer for output of the value
 * of the enumeration datatype member.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tCompound
 * @ingroup tEnum
 */
%apply long* VOID_OUT {void* value}
herr_t H5Tget_member_value(hid_t type_id, unsigned membno, void *value/*out*/);
%clear void* value;

%rename(H5Tget_member_value_direct) H5Tget_member_value;
/**
 * Returns the value of an enumeration or compound datatype member.
 *
 * @see H5Tget_member_value
 */
%apply void* BUFF {void* value}
herr_t H5Tget_member_value(hid_t type_id, unsigned membno, void *value/*out*/);
%clear void* value;

/**
 * Returns the native datatype of a specified datatype.
 *
 * <p>H5Tget_native_type returns the equivalent native datatype for
 * the datatype specified in dtype_id.</p>
 *
 * <p>H5Tget_native_type is a high-level function designed primarily
 * to facilitate use of the H5Dread function, for which users
 * otherwise must undertake a multi-step process to determine the
 * native datatype of a dataset prior to reading it into memory. It
 * can be used not only to determine the native datatype for atomic
 * datatypes, but also to determine the native datatypes of the
 * individual components of a compound datatype, an enumerated
 * datatype, an array datatype, or a variable-length datatype.</p>
 *
 * <p>H5Tget_native_type selects the matching native datatype from the
 * following list: </p>
 <ul>
    <li>H5T_NATIVE_CHAR</li>
    <li>H5T_NATIVE_SHORT</li>
    <li>H5T_NATIVE_INT</li>
    <li>H5T_NATIVE_LONG</li>
    <li>H5T_NATIVE_LLONG</li>
    <li>H5T_NATIVE_UCHAR</li>
    <li>H5T_NATIVE_USHORT</li>
    <li>H5T_NATIVE_UINT</li>
    <li>H5T_NATIVE_ULONG</li>
    <li>H5T_NATIVE_ULLONG</li>
    <li>H5T_NATIVE_FLOAT</li>
    <li>H5T_NATIVE_DOUBLE</li>
    <li>H5T_NATIVE_LDOUBLE</li>
 </ul>
 *
 * <p>The direction parameter indicates the order in which the library
 * searches for a native datatype match. Valid values for direction
 * are as follows:</p>
 <dl>
    <dt>H5T_DIR_ASCEND</dt>
    <dd>Searches the above list in ascending size of the datatype,
    i.e., from top to bottom. (Default)</dd>
    <dt>H5T_DIR_DESCEND</dt>
    <dd>Searches the above list in descending size of the datatype,
    i.e., from bottom to top.</dd>
 </dl>
 *
 * <p>H5Tget_native_type is designed primarily for use with integer
 * and floating point datatypes. String, time, bitfield, opaque, and
 * reference datatypes are returned as a copy of dtype_id.</p>
 *
 * <p>The identifier returned by H5Tget_native_type should eventually
 * be closed by calling H5Tclose to release resources. </p>
 *
 * @param dtype_id Datatype identifier for the dataset datatype.
 * @param direction Direction of search.
 *
 * @return Returns the native datatype identifier for the specified
 * dataset datatype if successful; otherwise returns a negative value.
 *
 * @ingroup tType
 */
hid_t H5Tget_native_type(hid_t dtype_id, H5T_direction_t direction);

/**
 * Retrieves the number of elements in a compound or enumeration datatype.
 *
 * <p>H5Tget_nmembers retrieves the number of fields in a compound
 * datatype or the number of members of an enumeration datatype.</p>
 *
 * @param type_id Identifier of datatype to query.
 *
 * @return Returns the number of elements if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tCompound
 * @ingroup tEnum
 */
int H5Tget_nmembers(hid_t type_id);

/**
 * Retrieves mantissa normalization of a floating-point datatype.
 *
 * <p>H5Tget_norm retrieves the mantissa normalization of a
 * floating-point datatype. Valid normalization types are:</p>
 <dl>
    <dt>H5T_NORM_IMPLIED (0)</dt>
    <dd>MSB of mantissa is not stored, always 1 </dd>
    <dt>H5T_NORM_MSBSET (1)</dt>
    <dd>MSB of mantissa is always 1 </dd>
    <dt>H5T_NORM_NONE (2)</dt>
    <dd>Mantissa is not normalized </dd>
 </dl>
 *
 * @param dtype_id Identifier of datatype to query.
 *
 * @return Returns a valid normalization type if successful; otherwise
 * H5T_NORM_ERROR (-1).
 *
 * @ingroup tAtomic
 */
H5T_norm_t H5Tget_norm(hid_t dtype_id);

/**
 * Retrieves the bit offset of the first significant bit.
 *
 * <p>H5Tget_offset retrieves the bit offset of the first significant
 * bit. The significant bits of an atomic datum can be offset from the
 * beginning of the memory for that datum by an amount of padding. The
 * `offset' property specifies the number of bits of padding that
 * appear to the "right of" the value. That is, if we have a 32-bit
 * datum with 16-bits of precision having the value 0x1122 then it
 * will be laid out in memory as (from small byte address toward
 * larger byte addresses): </p>
 <table align="center" border="1" cellpadding="4" width="80%">
 <tbody>
   <tr align="center">
      <th width="20%">Byte Position</th>
      <th width="20%">Big-Endian Offset=0</th>
      <th width="20%">Big-Endian Offset=16</th>
      <th width="20%">Little-Endian Offset=0</th>
      <th width="20%">Little-Endian Offset=16</th>
   </tr>  
   <tr align="center">
      <td>0:</td>
      <td>[ pad]</td>
      <td>[0x11]</td>
      <td>[0x22]</td>
      <td>[ pad]</td>
   </tr>
   <tr align="center">
      <td>1:</td>
      <td>[ pad]</td>
      <td>[0x22]</td>
      <td>[0x11]</td>
      <td>[ pad]</td>
   </tr>
   <tr align="center">
      <td>2:</td>
      <td>[0x11]</td>
      <td>[ pad]</td>
      <td>[ pad]</td>
      <td>[0x22]</td>
   </tr>
   <tr align="center">
      <td>3:</td>
      <td>[0x22]</td>
      <td>[ pad]</td>
      <td>[ pad]</td>
      <td>[0x11]</td>
   </tr>
 </tbody>
 </table>
 *
 * @param type_id Identifier of datatype to query.
 * 
 * @return Returns an offset value if successful; otherwise returns a
 * negative value.
 *
 * @ingroup tAtomic
 */
int H5Tget_offset(hid_t type_id);

/**
 * Returns the byte order of an atomic datatype.
 *
 * <p>H5Tget_order returns the byte order of an atomic datatype.</p>
 *
 * <p>Possible return values are:</p>
 <dl>
    <dt>H5T_ORDER_LE (0)</dt>
    <dd>Little endian byte ordering (default). </dd>
    <dt>H5T_ORDER_BE (1)</dt>
    <dd>Big endian byte ordering. </dd>
    <dt>H5T_ORDER_VAX (2)</dt>
    <dd>VAX mixed byte ordering (not currently supported). </dd>
 </dl>
 *
 * @param dtype_id Identifier of datatype to query.
 *
 * @return Returns a byte order constant if successful; otherwise
 * H5T_ORDER_ERROR (-1).
 *
 * @ingroup tAtomic
 */
H5T_order_t H5Tget_order(hid_t dtype_id);

/**
 * Retrieves the padding type of the least and most-significant bit padding.
 *
 * <p>H5Tget_pad retrieves the padding type of the least and
 * most-significant bit padding. Valid types are:</p>
 <dl>
    <dt>H5T_PAD_ZERO (0)</dt>
    <dd>Set background to zeros. </dd>
    <dt>H5T_PAD_ONE (1)</dt>
    <dd>Set background to ones. </dd>
    <dt>H5T_PAD_BACKGROUND (2)</dt>
    <dd>Leave background alone. </dd>
 </dl>
 *
 * @param type_id Identifier of datatype to query.
 * @param lsb (Output) Pointer to location to return least-significant
 * bit padding type.
 * @param msb (Output) Pointer to location to return most-significant
 * bit padding type.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
%apply H5T_PAD_ENUM* OUTPUT {H5T_pad_t* lsb}
%apply H5T_PAD_ENUM* OUTPUT {H5T_pad_t* msb}
herr_t H5Tget_pad(hid_t type_id, H5T_pad_t *lsb/*out*/, H5T_pad_t *msb/*out*/);

/**
 * Returns the precision of an atomic datatype.
 *
 * <p>H5Tget_precision returns the precision of an atomic
 * datatype. The precision is the number of significant bits which,
 * unless padding is present, is 8 times larger than the value
 * returned by H5Tget_size.</p>
 *
 * @param dtype_id Identifier of datatype to query.
 *
 * @return Returns the number of significant bits if successful;
 * otherwise 0.
 *
 * @ingroup tAtomic
 */
size_t H5Tget_precision(hid_t dtype_id);

/**
 * Retrieves the sign type for an integer type.
 *
 * <p>H5Tget_sign retrieves the sign type for an integer type. Valid
 * types are:</p>
 <dl>
    <dt>H5T_SGN_NONE (0)</dt>
    <dd>Unsigned integer type.</dd>
    <dt>H5T_SGN_2 (1)</dt>
    <dd>Two's complement signed integer type. </dd>
 </dl>
 *
 * @param type_id Identifier of datatype to query.
 *
 * @return Returns a valid sign type if successful; otherwise
 * H5T_SGN_ERROR (-1).
 *
 * @ingroup tAtomic
 */
H5T_sign_t H5Tget_sign(hid_t type_id);

/**
 * Returns the size of a datatype.
 *
 * <p>H5Tget_size returns the size of a datatype in bytes.</p>
 *
 * @param type_id Identifier of datatype to query.
 *
 * @return Returns the size of the datatype in bytes if successful;
 * otherwise 0.
 *
 * @ingroup tType
 */
size_t H5Tget_size(hid_t type_id);

/**
 * Retrieves the storage mechanism for a string datatype.
 *
 * <p>H5Tget_strpad retrieves the storage mechanism for a string
 * datatype, as defined in H5Tset_strpad.</p>
 *
 * @param type_id Identifier of datatype to query.
 *
 * @return Returns a valid string storage mechanism if successful;
 * otherwise H5T_STR_ERROR (-1).
 *
 * @ingroup tAtomic
 */
H5T_str_t H5Tget_strpad(hid_t type_id);

/**
 * Returns the base datatype from which a datatype is derived.
 *
 * <p>H5Tget_super returns the base datatype from which the datatype
 * dtype_id is derived.</p>
 *
 * <p>In the case of an enumeration type, the return value is an
 * integer type. </p>
 *
 * @param type_id Datatype identifier for the derived datatype.
 *
 * @return Returns the datatype identifier for the base datatype if
 * successful; otherwise returns a negative value.
 *
 * @ingroup tType
 */
hid_t H5Tget_super(hid_t type_id);

/**
 * Gets the tag associated with an opaque datatype.
 *
 * <p>H5Tget_tag returns the tag associated with the opaque datatype
 * dtype_id.</p>
 *
 * <p>The tag is returned via a pointer to an allocated string, which
 * the caller must free. </p>
 *
 * @param dtype_id Datatype identifier for the opaque datatype.
 *
 * @return Returns a pointer to an allocated string if successful;
 * otherwise returns NULL.
 *
 * @ingroup tOpaque
 */
%newobject H5Tget_tag;
char* H5Tget_tag(hid_t type);

/**
 * Adds a new member to a compound datatype.
 *
 * <p>H5Tinsert adds another member to the compound datatype
 * dtype_id. The new member has a name which must be unique within the
 * compound datatype. The offset argument defines the start of the
 * member in an instance of the compound datatype, and field_id is the
 * datatype identifier of the new member.</p>
 *
 * <p>Note: Members of a compound datatype do not have to be atomic
 * datatypes; a compound datatype can have a member which is a
 * compound datatype. </p>
 *
 * @param parent_id Identifier of compound datatype to modify.
 * @param name Name of the field to insert.
 * @param offset Offset in memory structure of the field to insert.
 * @param field_id Datatype identifier of the field to insert.
 * 
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tCompound
 */
herr_t H5Tinsert(hid_t parent_id, const char *name, 
		 size_t offset, hid_t field_id);

/**
 * Determines whether datatype is a variable-length string.
 *
 * <p>H5Tvlen_create determines whether the datatype identified in
 * dtype_id is a variable-length string.</p>
 *
 * <p>This function can be used to distinguish between fixed and
 * variable-length string datatypes. </p>
 *
 * @param dtype_id Datatype identifier.
 *
 * @return Returns TRUE or FALSE if successful; otherwise returns a
 * negative value.
 *
 * @ingroup tVLen
 */
htri_t H5Tis_variable_str(hid_t dtype_id);

/**
 * Locks a datatype.
 *
 * <p>H5Tlock locks the datatype specified by the dtype_id identifier,
 * making it read-only and non-destructible. This is normally done by
 * the library for predefined datatypes so the application does not
 * inadvertently change or delete a predefined type. Once a datatype
 * is locked it can never be unlocked.</p>
 *
 * @param type_id Identifier of datatype to lock.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Tlock(hid_t type_id);

/**
 * Opens a named datatype.
 *
 * <p>H5Topen1 opens a named datatype at the location specified by
 * loc_id and returns an identifier for the datatype. loc_id is either
 * a file or group identifier. The identifier should eventually be
 * closed by calling H5Tclose to release resources.</p>
 *
 * @param loc_id A file or group identifier.
 * @param name A datatype name, defined within the file or group
 * identified by loc_id.
 *
 * @return Returns a named datatype identifier if successful;
 * otherwise returns a negative value.
 *
 * @deprecated
 *
 * @ingroup tType
 */
%javamethodmodifiers H5Topen1 "@Deprecated\n   public"
hid_t H5Topen1(hid_t loc_id, const char *name);

/**
 * Opens a named datatype.
 *
 * <p>H5Topen2 opens a named datatype at the location specified by
 * loc_id and returns an identifier for the datatype. loc_id is either
 * a file or group identifier. The identifier should eventually be
 * closed by calling H5Tclose to release resources.</p>
 *
 * <p>The named datatype is opened with the dataype access property
 * list tapl_id. </p>
 *
 * @param loc_id A file or group identifier.
 * @param name A datatype name, defined within the file or group
 * identified by loc_id.
 * @param tapl_id Datatype access property list identifier.
 *
 * @return Returns a named datatype identifier if successful;
 * otherwise returns a negative value.
 *
 * @ingroup tType
 */
hid_t H5Topen2(hid_t loc_id, const char *name, hid_t tapl_id);

/**
 * Recursively removes padding from within a compound datatype.
 *
 * <p>H5Tpack recursively removes padding from within a compound
 * datatype to make it more efficient (space-wise) to store that
 * data.</p>
 *
 * @param type_id Identifier of datatype to modify.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tCompound
 */
herr_t H5Tpack(hid_t type_id);

/*nodoc*
 * Registers a conversion function.
 *
 * <p>H5Tregister registers a hard or soft conversion function for a
 * datatype conversion path.</p>
 *
 * <p>The parameter pers indicates whether a conversion function is
 * hard (H5T_PERS_HARD) or soft (H5T_PERS_SOFT).</p>
 *
 * <p>A conversion path can have only one hard function. When pers is
 * H5T_PERS_HARD, func replaces any previous hard function. If pers is
 * H5T_PERS_HARD and func is the null pointer, then any hard function
 * registered for this path is removed.</p>
 *
 * <p>When pers is H5T_PERS_SOFT, H5Tregister adds the function to the
 * end of the master soft list and replaces the soft function in all
 * applicable existing conversion paths. Soft functions are used when
 * determining which conversion function is appropriate for this
 * path.</p>
 *
 * <p>The name is used only for debugging and should be a short
 * identifier for the function.</p>
 *
 * <p>The path is specified by the source and destination datatypes
 * src_id and dst_id. For soft conversion functions, only the class of
 * these types is important. </p>
 *
 * <p>The H5T_conv_t parameters and the elements of the H5T_cdata_t
 * struct are described more fully in the "Data Conversion"
 * section of "The Datatype Interface (H5T)" in the HDF5 User's
 * Guide.</p>
 *
 * @param pers H5T_PERS_HARD for hard conversion functions;
 * H5T_PERS_SOFT for soft conversion functions.
 * @param name Name displayed in diagnostic output.
 * @param src_id Identifier of source datatype.
 * @param dst_id Identifier of destination datatype.
 * @param func Function to convert between source and destination
 * datatypes.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tConvert
 */
//herr_t H5Tregister(H5T_pers_t pers, const char *name, hid_t src_id,
//		   hid_t dst_id, H5T_conv_t func);

/**
 * Sets character set to be used.
 *
 * <p>H5Tset_cset sets the character set to be used.</p>
 *
 * <p>HDF5 is able to distinguish between character sets of different
 * nationalities and to convert between them to the extent
 * possible. Valid character set types are:</p>
 <dl>
   <dt>H5T_CSET_ASCII</dt>
   <dd>Character set is US ASCII.</dd>
   <dt>H5T_CSET_UTF8</dt>
   <dd>Character set is UTF8.</dd>
 </dl>
 *
 * @param type_id Identifier of datatype to modify.
 * @param cset Character set type.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_cset(hid_t type_id, H5T_cset_t cset);

/**
 * Sets the exponent bias of a floating-point type.
 *
 * <p>H5Tset_ebias sets the exponent bias of a floating-point
 * type.</p>
 *
 * @param type_id Identifier of datatype to set.
 * @param ebias Exponent bias value.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_ebias(hid_t type_id, size_t ebias);


/**
 * Sets locations and sizes of floating point bit fields.
 *
 * <p>H5Tset_fields sets the locations and sizes of the various
 * floating-point bit fields. The field positions are bit positions in
 * the significant region of the datatype. Bits are numbered with the
 * least significant bit number zero.</p>
 *
 * <p>Fields are not allowed to extend beyond the number of bits of
 * precision, nor are they allowed to overlap with one another. </p>
 *
 * @param type_id Identifier of datatype to set.
 * @param spos Sign position, i.e., the bit offset of the
 * floating-point sign bit.
 * @param epos Exponent bit position.
 * @param esize Size of exponent in bits.
 * @param mpos Mantissa bit position.
 * @param msize Size of mantissa in bits.
 * 
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_fields(hid_t type_id, size_t spos, size_t epos,
		     size_t esize, size_t mpos, size_t msize);

/**
 * Fills unused internal floating point bits.
 *
 * <p>If any internal bits of a floating point type are unused (that
 * is, those significant bits which are not part of the sign,
 * exponent, or mantissa), then H5Tset_inpad will be filled according
 * to the value of the padding value property inpad. Valid padding
 * types are:</p>
 <dl>
    <dt>H5T_PAD_ZERO (0)</dt>
    <dd>Set background to zeros. </dd>
    <dt>H5T_PAD_ONE (1)</dt>
    <dd>Set background to ones. </dd>
    <dt>H5T_PAD_BACKGROUND (2)</dt>
    <dd>Leave background alone. </dd>
 </dl>
 *
 * @param type_id Identifier of datatype to modify.
 * @param pad Padding type.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_inpad(hid_t type_id, H5T_pad_t pad);

/**
 * Sets the mantissa normalization of a floating-point datatype.
 *
 * <p>H5Tset_norm sets the mantissa normalization of a floating-point
 * datatype. Valid normalization types are:</p>
 <dl>
    <dt>H5T_NORM_IMPLIED (0)</dt>
    <dd>MSB of mantissa is not stored, always 1 </dd>
    <dt>H5T_NORM_MSBSET (1)</dt>
    <dd>MSB of mantissa is always 1 </dd>
    <dt>H5T_NORM_NONE (2)</dt>
    <dd>Mantissa is not normalized </dd>
 </dl>
 *
 * @param type_id Identifier of datatype to set.
 * @param norm Mantissa normalization type.
 *
 * @return norm Mantissa normalization type.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_norm(hid_t type_id, H5T_norm_t norm);

/**
 * Sets the bit offset of the first significant bit.
 *
 * <p>H5Tset_offset sets the bit offset of the first significant
 * bit. The significant bits of an atomic datum can be offset from the
 * beginning of the memory for that datum by an amount of padding. The
 * `offset' property specifies the number of bits of padding that
 * appear to the "right of" the value. That is, if we have a 32-bit
 * datum with 16-bits of precision having the value 0x1122 then it
 * will be laid out in memory as (from small byte address toward
 * larger byte addresses): </p>
 <table align="center" border="1" cellpadding="4" width="80%">
 <tbody>
   <tr align="center">
      <th width="20%">Byte Position</th>
      <th width="20%">Big-Endian Offset=0</th>
      <th width="20%">Big-Endian Offset=16</th>
      <th width="20%">Little-Endian Offset=0</th>
      <th width="20%">Little-Endian Offset=16</th>
   </tr>  
   <tr align="center">
      <td>0:</td>
      <td>[ pad]</td>
      <td>[0x11]</td>
      <td>[0x22]</td>
      <td>[ pad]</td>
   </tr>
   <tr align="center">
      <td>1:</td>
      <td>[ pad]</td>
      <td>[0x22]</td>
      <td>[0x11]</td>
      <td>[ pad]</td>
   </tr>
   <tr align="center">
      <td>2:</td>
      <td>[0x11]</td>
      <td>[ pad]</td>
      <td>[ pad]</td>
      <td>[0x22]</td>
   </tr>
   <tr align="center">
      <td>3:</td>
      <td>[0x22]</td>
      <td>[ pad]</td>
      <td>[ pad]</td>
      <td>[0x11]</td>
   </tr>
 </tbody>
 </table>
 *
 * <p>If the offset is incremented then the total size is incremented
 * also if necessary to prevent significant bits of the value from
 * hanging over the edge of the datatype.</p>
 *
 * <p>The offset of an H5T_STRING cannot be set to anything but
 * zero. </p>
 *
 * @param type_id Identifier of datatype to set.
 * @param offset Offset of first significant bit.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_offset(hid_t type_id, size_t offset);

/**
 * Sets the byte ordering of an atomic datatype.
 *
 * <p>H5Tset_order sets the byte ordering of an atomic datatype. Byte
 * orderings currently supported are:</p>
 <dl>
    <dt>H5T_ORDER_LE (0)</dt>
    <dd>Little endian byte ordering (default). </dd>
    <dt>H5T_ORDER_BE (1)</dt>
    <dd>Big endian byte ordering. </dd>
    <dt>H5T_ORDER_VAX (2)</dt>
    <dd>VAX mixed byte ordering (not currently supported). </dd>
 </dl>
 *
 * @param type_id Identifier of datatype to set.
 * @param order Byte ordering constant.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_order(hid_t type_id, H5T_order_t order);

/**
 * Sets the least and most-significant bits padding types.
 *
 * <p>H5Tset_pad sets the least and most-significant bits padding
 * types.</p>
 <dl>
    <dt>H5T_PAD_ZERO (0)</dt>
    <dd>Set background to zeros. </dd>
    <dt>H5T_PAD_ONE (1)</dt>
    <dd>Set background to ones. </dd>
    <dt>H5T_PAD_BACKGROUND (2)</dt>
    <dd>Leave background alone. </dd>
 </dl>
 *
 * @param type_id Identifier of datatype to set.
 * @param lsb Padding type for least-significant bits.
 * @param msb Padding type for most-significant bits.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_pad(hid_t type_id, H5T_pad_t lsb, H5T_pad_t msb);

/**
 * Sets the precision of an atomic datatype.
 *
 * <p>H5Tset_precision sets the precision of an atomic datatype. The
 * precision is the number of significant bits which, unless padding
 * is present, is 8 times larger than the value returned by
 * H5Tget_size.</p>
 *
 * <p>If the precision is increased then the offset is decreased and
 * then the size is increased to insure that significant bits do not
 * "hang over" the edge of the datatype.</p>
 *
 * <p>Changing the precision of an H5T_STRING automatically changes
 * the size as well. The precision must be a multiple of 8.</p>
 *
 * <p>When decreasing the precision of a floating point type, set the
 * locations and sizes of the sign, mantissa, and exponent fields
 * first. </p>
 *
 * @param type_id Identifier of datatype to set.
 * @param prec Number of bits of precision for datatype.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_precision(hid_t type_id, size_t prec);

/**
 * Sets the sign property for an integer type.
 *
 * <p>H5Tset_sign sets the sign property for an integer type.</p>
 <dl>
    <dt>H5T_SGN_NONE (0)</dt>
    <dd>Unsigned integer type.</dd>
    <dt>H5T_SGN_2 (1)</dt>
    <dd>Two's complement signed integer type. </dd>
 </dl>
 * 
 * @param type_id Identifier of datatype to set.
 * @param sign Sign type.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_sign(hid_t type_id, H5T_sign_t sign);

/**
 * Sets the total size for an atomic datatype.
 *
 * <p>H5Tset_size sets the total size in bytes, size, for a
 * datatype. If the datatype is atomic and size is decreased so that
 * the significant bits of the datatype extend beyond the edge of the
 * new size, then the `offset' property is decreased toward zero. If
 * the `offset' becomes zero and the significant bits of the datatype
 * still hang over the edge of the new size, then the number of
 * significant bits is decreased. The size set for a string should
 * include space for the null-terminator character, otherwise it will
 * not be stored on (or retrieved from) disk. Adjusting the size of a
 * string automatically sets the precision to 8*size. A compound
 * datatype may increase or decrease in size as long as its member
 * field is not trailed. All datatypes must have a positive size.</p>
 *
 * @param type_id Identifier of datatype to change size.
 * @param size Size in bytes to modify datatype.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_size(hid_t type_id, size_t size);


/**
 * Defines the storage mechanism for character strings.
 *
 * <p>H5Tset_strpad defines the storage mechanism for the string.</p>
 *
 * <p>The method used to store character strings differs with the
 * programming language: C usually null terminates strings while
 * Fortran left-justifies and space-pads strings. </p>
 *
 * <p>Valid string padding values, as passed in the parameter strpad,
 * are as follows:</p>
 <dl>
   <dt>H5T_STR_NULLTERM (0)</dt>
   <dd>Null terminate (as C does)</dd>
   <dt>H5T_STR_NULLPAD (1)</dt>
   <dd>Pad with zeros </dd>
   <dt>H5T_STR_SPACEPAD (2)</dt>
   <dd>Pad with spaces (as FORTRAN does)</dd>
 </dl>
 *
 * <p>When converting from a longer string to a shorter string, the
 * behavior is as follows. If the short string is H5T_STR_NULLPAD or
 * H5T_STR_SPACEPAD, then the string is simply truncated. If the short
 * string is H5T_STR_NULLTERM, it is truncated and a null terminator
 * is appended.</p>
 *
 * <p>When converting from a shorter string to a longer string, the
 * long string is padded on the end by appending nulls or spaces. </p>
 *
 * @param type_id Identifier of datatype to modify.
 * @param strpad String padding type.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tAtomic
 */
herr_t H5Tset_strpad(hid_t type_id, H5T_str_t strpad);

/**
 * Tags an opaque datatype.
 *
 * <p>H5Tset_tag tags an opaque datatype dtype_id with a descriptive
 * ASCII identifier, tag.</p>
 *
 * <p>tag is intended to provide a concise description; the maximum
 * size is hard-coded in the HDF5 Library as 256 bytes
 * (H5T_OPAQUE_TAG_MAX). </p>
 *
 * @param type_id Datatype identifier for the opaque datatype to be tagged.
 * @param tag Descriptive ASCII string with which the opaque datatype
 * is to be tagged.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tOpaque
 */
herr_t H5Tset_tag(hid_t type_id, const char *tag);

/*nodoc*
 * Removes a conversion function.
 *
 * <p>H5Tunregister removes a conversion function matching certain
 * criteria, like soft or hard conversion, source and destination
 * types, and the conversion function.</p>
 *
 * <p>If a user is trying to remove a conversion function he
 * registered, all parameters can be used. If he is trying to remove a
 * library's default conversion function, there is no guarantee the
 * name and func parameters will match user's chosen values. Passing
 * in some values may cause this function to fail. A good practice is
 * to pass in NULL as their values.</p>
 *
 * <p>All parameters are optional. The missing parameters will be used
 * to generalize the searching criteria.</p>
 *
 * <p>The conversion function pointer type declaration is described in
 * H5Tregister. </p>
 *
 * @param pers H5T_PERS_HARD for hard conversion functions;
 * H5T_PERS_SOFT for soft conversion functions.
 * @param name Name displayed in diagnostic output.
 * @param src_id Identifier of source datatype.
 * @param dst_id Identifier of destination datatype.
 * @param func Function to convert between source and destination datatypes.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @ingroup tConvert
 */
//herr_t H5Tunregister(H5T_pers_t pers, const char *name, hid_t src_id,
//		     hid_t dst_id, H5T_conv_t func);

/**
 * Creates a new variable-length datatype.
 *
 * <p>H5Tvlen_create creates a new variable-length (VL) datatype.</p>
 *
 * <p>The base datatype will be the datatype that the sequence is
 * composed of, characters for character strings, vertex coordinates
 * for polygon lists, etc. The base type specified for the VL datatype
 * can be of any HDF5 datatype, including another VL datatype, a
 * compound datatype or an atomic datatype.</p>
 *
 * <p>When necessary, use H5Tget_super to determine the base type of
 * the VL datatype.</p>
 *
 * <p>The datatype identifier returned from this function should be
 * released with H5Tclose or resource leaks will result. </p>
 *
 * @param base_id Base type of datatype to create.
 *
 * @return Returns datatype identifier if successful; otherwise
 * returns a negative value.
 *
 * @ingroup VLen
 */
hid_t H5Tvlen_create(hid_t base_id);
