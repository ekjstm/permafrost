%include "henums.i"

/* These are the various classes of datatypes */
/* If this goes over 16 types (0-15), the file format will need to change) */
%rename H5T_class_t DatatypeClassType;
typedef enum H5T_class_t {
    H5T_NO_CLASS         = -1,  /*error                                      */
    H5T_INTEGER          = 0,   /*integer types                              */
    H5T_FLOAT            = 1,   /*floating-point types                       */
    H5T_TIME             = 2,   /*date and time types                        */
    H5T_STRING           = 3,   /*character string types                     */
    H5T_BITFIELD         = 4,   /*bit field types                            */
    H5T_OPAQUE           = 5,   /*opaque types                               */
    H5T_COMPOUND         = 6,   /*compound types                             */
    H5T_REFERENCE        = 7,   /*reference types                            */
    H5T_ENUM		 = 8,	/*enumeration types                          */
    H5T_VLEN		 = 9,	/*Variable-Length types                      */
    H5T_ARRAY	         = 10,	/*Array types                                */

    H5T_NCLASSES                /*this must be last                          */
} H5T_class_t;
HENUM_OUTPUT_TYPEMAP(H5T_CLASS_ENUM, DatatypeClassType);

/* Byte orders */
%rename H5T_order_t DatatypeByteOrderType;
typedef enum H5T_order_t {
    H5T_ORDER_ERROR      = -1,  /*error                                      */
    H5T_ORDER_LE         = 0,   /*little endian                              */
    H5T_ORDER_BE         = 1,   /*bit endian                                 */
    H5T_ORDER_VAX        = 2,   /*VAX mixed endian                           */
    H5T_ORDER_NONE       = 3    /*no particular order (strings, bits,..)     */
    /*H5T_ORDER_NONE must be last */
} H5T_order_t;
HENUM_OUTPUT_TYPEMAP(H5T_ORDER_ENUM, DatatypeByteOrderType);

/* Types of integer sign schemes */
%rename H5T_sign_t DatatypeSigningType;
typedef enum H5T_sign_t {
    H5T_SGN_ERROR        = -1,  /*error                                      */
    H5T_SGN_NONE         = 0,   /*this is an unsigned type                   */
    H5T_SGN_2            = 1,   /*two's complement                           */
    H5T_NSGN             = 2    /*this must be last!                         */
} H5T_sign_t;
HENUM_OUTPUT_TYPEMAP(H5T_SIGN_ENUM, DatatypeSigningType);

/* Floating-point normalization schemes */
%rename H5T_norm_t DatatypeNormalizationType;
typedef enum H5T_norm_t {
    H5T_NORM_ERROR       = -1,  /*error                                      */
    H5T_NORM_IMPLIED     = 0,   /*msb of mantissa isn't stored, always 1     */
    H5T_NORM_MSBSET      = 1,   /*msb of mantissa is always 1                */
    H5T_NORM_NONE        = 2    /*not normalized                             */
    /*H5T_NORM_NONE must be last */
} H5T_norm_t;
HENUM_OUTPUT_TYPEMAP(H5T_NORM_ENUM, DatatypeNormalizationType);

/*
 * Character set to use for text strings.  Do not change these values since
 * they appear in HDF5 files!
 */
%rename H5T_cset_t DatatypeCharsetType;
typedef enum H5T_cset_t {
    H5T_CSET_ERROR       = -1,  /*error                                      */
    H5T_CSET_ASCII       = 0,   /*US ASCII                                   */
    H5T_CSET_UTF8        = 1,   /*UTF-8 Unicode encoding		     */
    H5T_CSET_RESERVED_2  = 2,   /*reserved for later use		     */
    H5T_CSET_RESERVED_3  = 3,   /*reserved for later use		     */
    H5T_CSET_RESERVED_4  = 4,   /*reserved for later use		     */
    H5T_CSET_RESERVED_5  = 5,   /*reserved for later use		     */
    H5T_CSET_RESERVED_6  = 6,   /*reserved for later use		     */
    H5T_CSET_RESERVED_7  = 7,   /*reserved for later use		     */
    H5T_CSET_RESERVED_8  = 8,   /*reserved for later use		     */
    H5T_CSET_RESERVED_9  = 9,   /*reserved for later use		     */
    H5T_CSET_RESERVED_10 = 10,  /*reserved for later use		     */
    H5T_CSET_RESERVED_11 = 11,  /*reserved for later use		     */
    H5T_CSET_RESERVED_12 = 12,  /*reserved for later use		     */
    H5T_CSET_RESERVED_13 = 13,  /*reserved for later use		     */
    H5T_CSET_RESERVED_14 = 14,  /*reserved for later use		     */
    H5T_CSET_RESERVED_15 = 15   /*reserved for later use		     */
} H5T_cset_t;
HENUM_OUTPUT_TYPEMAP(H5T_CSET_ENUM, DatatypeCharsetType);

/*
 * Type of padding to use in character strings.  Do not change these values
 * since they appear in HDF5 files!
 */
%rename H5T_str_t DatatypeStringType;
typedef enum H5T_str_t {
    H5T_STR_ERROR        = -1,  /*error                                      */
    H5T_STR_NULLTERM     = 0,   /*null terminate like in C                   */
    H5T_STR_NULLPAD      = 1,   /*pad with nulls                             */
    H5T_STR_SPACEPAD     = 2,   /*pad with spaces like in Fortran            */
    H5T_STR_RESERVED_3   = 3,   /*reserved for later use		     */
    H5T_STR_RESERVED_4   = 4,   /*reserved for later use		     */
    H5T_STR_RESERVED_5   = 5,   /*reserved for later use		     */
    H5T_STR_RESERVED_6   = 6,   /*reserved for later use		     */
    H5T_STR_RESERVED_7   = 7,   /*reserved for later use		     */
    H5T_STR_RESERVED_8   = 8,   /*reserved for later use		     */
    H5T_STR_RESERVED_9   = 9,   /*reserved for later use		     */
    H5T_STR_RESERVED_10  = 10,  /*reserved for later use		     */
    H5T_STR_RESERVED_11  = 11,  /*reserved for later use		     */
    H5T_STR_RESERVED_12  = 12,  /*reserved for later use		     */
    H5T_STR_RESERVED_13  = 13,  /*reserved for later use		     */
    H5T_STR_RESERVED_14  = 14,  /*reserved for later use		     */
    H5T_STR_RESERVED_15  = 15   /*reserved for later use		     */
} H5T_str_t;
#define H5T_NSTR H5T_STR_RESERVED_3		/*num H5T_str_t types actually defined	     */
HENUM_OUTPUT_TYPEMAP(H5T_STR_ENUM, DatatypeStringType);

/* Type of padding to use in other atomic types */
%rename H5T_pad_t DatatypeBitPadType;
typedef enum H5T_pad_t {
    H5T_PAD_ERROR        = -1,  /*error                                      */
    H5T_PAD_ZERO         = 0,   /*always set to zero                         */
    H5T_PAD_ONE          = 1,   /*always set to one                          */
    H5T_PAD_BACKGROUND   = 2,   /*set to background value                    */
    H5T_NPAD             = 3    /*THIS MUST BE LAST                          */
} H5T_pad_t;
HENUM_OUTPUT_TYPEMAP(H5T_PAD_ENUM, DatatypeBitPadType);

/* Commands sent to conversion functions */
%rename H5T_cmd_t DatatypeConversionCommandType;
typedef enum H5T_cmd_t {
    H5T_CONV_INIT	= 0,	/*query and/or initialize private data	     */
    H5T_CONV_CONV	= 1, 	/*convert data from source to dest datatype */
    H5T_CONV_FREE	= 2	/*function is being removed from path	     */
} H5T_cmd_t;
HENUM_OUTPUT_TYPEMAP(H5T_CMD_ENUM, DatatypeConversionCommandType);

/* How is the `bkg' buffer used by the conversion function? */
%rename H5T_bkg_t DatatypeConversionBufferType;
typedef enum H5T_bkg_t {
    H5T_BKG_NO		= 0, 	/*background buffer is not needed, send NULL */
    H5T_BKG_TEMP	= 1,	/*bkg buffer used as temp storage only       */
    H5T_BKG_YES		= 2	/*init bkg buf with data before conversion   */
} H5T_bkg_t;
HENUM_OUTPUT_TYPEMAP(H5T_BKG_ENUM, DatatypeConversionBufferType);

/* Type conversion client data */
%rename H5T_cdata_t DatatypeConversion;
typedef struct H5T_cdata_t {
    H5T_cmd_t		command;/*what should the conversion function do?    */
    H5T_bkg_t		need_bkg;/*is the background buffer needed?	     */
    hbool_t		recalc;	/*recalculate private data		     */
    void		*priv;	/*private data				     */
} H5T_cdata_t;

/* Conversion function persistence */
%rename H5T_pers_t DatatypeConversionPersistenceType;
typedef enum H5T_pers_t {
    H5T_PERS_DONTCARE	= -1, 	/*wild card				     */
    H5T_PERS_HARD	= 0,	/*hard conversion function		     */
    H5T_PERS_SOFT	= 1 	/*soft conversion function		     */
} H5T_pers_t;
HENUM_OUTPUT_TYPEMAP(H5T_PERS_ENUM, DatatypeConversionPersistenceType);

/* The order to retrieve atomic native datatype */
%rename H5T_direction_t DatatypeConversionDirectionType;
typedef enum H5T_direction_t {
    H5T_DIR_DEFAULT     = 0,    /*default direction is inscendent            */
    H5T_DIR_ASCEND      = 1,    /*in inscendent order                        */
    H5T_DIR_DESCEND     = 2     /*in descendent order                        */
} H5T_direction_t;
HENUM_OUTPUT_TYPEMAP(H5T_PERS_ENUM, DatatypeConversionDirectionType);

/* Variable Length Datatype struct in memory */
/* (This is only used for VL sequences, not VL strings, which are stored in char *'s) */
%rename hvl_t DatatypeVariableLength;
typedef struct {
    size_t len; /* Length of VL data (in base type units) */
    void *p;    /* Pointer to VL data */
} hvl_t;

/* Variable Length String information */
#define H5T_VARIABLE    -1  /* Indicate that a string is variable length (null-terminated in C, instead of fixed length) */

/* Opaque information */
#define H5T_OPAQUE_TAG_MAX      256     /* Maximum length of an opaque tag */
                                        /* This could be raised without too much difficulty */
