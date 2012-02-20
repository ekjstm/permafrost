/* Values for the H5D_LAYOUT property */
%rename H5D_layout_t DatasetLayoutType;
typedef enum H5D_layout_t {
    H5D_LAYOUT_ERROR	= -1,

    H5D_COMPACT		= 0,	/*raw data is very small		     */
    H5D_CONTIGUOUS	= 1,	/*the default				     */
    H5D_CHUNKED		= 2,	/*slow and fancy			     */
    H5D_NLAYOUTS	= 3	/*this one must be last!		     */
} H5D_layout_t;

/* Values for the space allocation time property */
%rename H5D_alloc_time_t DatasetAllocationTimeType;
typedef enum H5D_alloc_time_t {
    H5D_ALLOC_TIME_ERROR	=-1,
    H5D_ALLOC_TIME_DEFAULT  	=0,
    H5D_ALLOC_TIME_EARLY	=1,
    H5D_ALLOC_TIME_LATE	=2,
    H5D_ALLOC_TIME_INCR	=3
} H5D_alloc_time_t;
HENUM_OUTPUT_TYPEMAP(H5D_ATIME_ENUM,  DatasetAllocationTimeType);

/* Values for the status of space allocation */
%rename H5D_space_status_t DatasetAllocationSpaceType;
typedef enum H5D_space_status_t {
    H5D_SPACE_STATUS_ERROR	=-1,
    H5D_SPACE_STATUS_NOT_ALLOCATED	=0,
    H5D_SPACE_STATUS_PART_ALLOCATED	=1,
    H5D_SPACE_STATUS_ALLOCATED		=2
} H5D_space_status_t;
HENUM_OUTPUT_TYPEMAP(H5D_SPACE_ENUM, DatasetAllocationSpaceType);

/* Values for time of writing fill value property */
%rename H5D_fill_time_t DatasetFillTimeType;
typedef enum H5D_fill_time_t {
    H5D_FILL_TIME_ERROR	=-1,
    H5D_FILL_TIME_ALLOC =0,
    H5D_FILL_TIME_NEVER	=1,
    H5D_FILL_TIME_IFSET	=2
} H5D_fill_time_t;
HENUM_OUTPUT_TYPEMAP(H5D_FTIME_ENUM, DatasetFillTimeType);

/* Values for fill value status */
%rename H5D_fill_value_t DatasetFillValueType;
typedef enum H5D_fill_value_t {
    H5D_FILL_VALUE_ERROR        =-1,
    H5D_FILL_VALUE_UNDEFINED    =0,
    H5D_FILL_VALUE_DEFAULT      =1,
    H5D_FILL_VALUE_USER_DEFINED =2
} H5D_fill_value_t;
HENUM_OUTPUT_TYPEMAP(H5D_FILL_ENUM, DatasetFillValueType);