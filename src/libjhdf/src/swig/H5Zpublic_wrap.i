%module FilterLib
%include "typemaps.i"
%include "H5types.i"
%include "H5Ztypes.i"

%header %{
#include "hdf5.h"
#include "H5Zpublic.h"
%}

/* Filter callback function definition */
//typedef H5Z_cb_return_t (*H5Z_filter_func_t)(H5Z_filter_t filter, void* buf,
//                                size_t buf_size, void* op_data);


/*
 * Before a dataset gets created, the "can_apply" callbacks for any filters used
 * in the dataset creation property list are called
 * with the dataset's dataset creation property list, the dataset's datatype and
 * a dataspace describing a chunk (for chunked dataset storage).
 *
 * The "can_apply" callback must determine if the combination of the dataset
 * creation property list setting, the datatype and the dataspace represent a
 * valid combination to apply this filter to.  For example, some cases of
 * invalid combinations may involve the filter not operating correctly on
 * certain datatypes (or certain datatype sizes), or certain sizes of the chunk
 * dataspace.
 *
 * The "can_apply" callback can be the NULL pointer, in which case, the library
 * will assume that it can apply to any combination of dataset creation
 * property list values, datatypes and dataspaces.
 *
 * The "can_apply" callback returns positive a valid combination, zero for an
 * invalid combination and negative for an error.
 */
//typedef herr_t (*H5Z_can_apply_func_t)(hid_t dcpl_id, hid_t type_id, hid_t space_id);

/*
 * After the "can_apply" callbacks are checked for new datasets, the "set_local"
 * callbacks for any filters used in the dataset creation property list are
 * called.  These callbacks receive the dataset's private copy of the dataset
 * creation property list passed in to H5Dcreate (i.e. not the actual property
 * list passed in to H5Dcreate) and the datatype ID passed in to H5Dcreate
 * (which is not copied and should not be modified) and a dataspace describing
 * the chunk (for chunked dataset storage) (which should also not be modified).
 *
 * The "set_local" callback must set any parameters that are specific to this
 * dataset, based on the combination of the dataset creation property list
 * values, the datatype and the dataspace.  For example, some filters perform
 * different actions based on different datatypes (or datatype sizes) or
 * different number of dimensions or dataspace sizes.
 *
 * The "set_local" callback can be the NULL pointer, in which case, the library
 * will assume that there are no dataset-specific settings for this filter.
 *
 * The "set_local" callback must return non-negative on success and negative
 * for an error.
 */
//typedef herr_t (*H5Z_set_local_func_t)(hid_t dcpl_id, hid_t type_id, hid_t space_id);

/*
 * A filter gets definition flags and invocation flags (defined above), the
 * client data array and size defined when the filter was added to the
 * pipeline, the size in bytes of the data on which to operate, and pointers
 * to a buffer and its allocated size.
 *
 * The filter should store the result in the supplied buffer if possible,
 * otherwise it can allocate a new buffer, freeing the original.  The
 * allocated size of the new buffer should be returned through the BUF_SIZE
 * pointer and the new buffer through the BUF pointer.
 *
 * The return value from the filter is the number of bytes in the output
 * buffer. If an error occurs then the function should return zero and leave
 * all pointer arguments unchanged.
 */
//typedef size_t (*H5Z_func_t)(unsigned int flags, size_t cd_nelmts,
//			     const unsigned int cd_values[], size_t nbytes,
//			     size_t *buf_size, void **buf);


/**
 * Determines whether a filter is available.
 *
 * <p>H5Zfilter_avail determines whether the filter specified in
 * filter is available to the application.</p>
 *
 * @param filter Filter identifier. 
 *
 * @return Returns a Boolean value (TRUE/FALSE) if successful;
 * otherwise returns a negative value.
 */
htri_t H5Zfilter_avail(H5Z_filter_t id);

/**
 * Retrieves information about a filter.
 *
 * <p>H5Zget_filter_info retrieves information about a filter. At
 * present, this means that the function retrieves a filter's
 * configuration flags, indicating whether the filter is configured to
 * decode data, to encode data, neither, or both.</p>
 *
 * <p>If filter_config is not set to NULL prior to the function call,
 * the returned parameter contains a bit field specifying the
 * available filter configuration. The configuration flag values can
 * then be determined through a series of bitwise AND operations, as
 * described below.</p>
 *
 * <p>Valid filter configuration flags include the following:</p>
 <dl> 
    <dt>H5Z_FILTER_CONFIG_ENCODE_ENABLED</dt>
    <dd>Encoding is enabled for this filter</dd>
    <dt>H5Z_FILTER_CONFIG_DECODE_ENABLED</dt>
    <dd>Decoding is enabled for this filter</dt>
 </dl>
 * <p>(These flags are defined in the HDF5 Library source code file
 * H5Zpublic.h.)</p>
 *
 * <p>A bitwise AND of the returned filter_config and a valid filter
 * configuration flag will reveal whether the related configuration
 * option is available. For example, if the value of
 * <code>H5Z_FILTER_CONFIG_ENCODE_ENABLED & filter_config</code>
 * <p>is true, i.e., greater than 0 (zero), the queried filter is
 * configured to encode data; if the value is FALSE, i.e., equal to 0
 * (zero), the filter is not so configured.</p>
 *
 * <p>If a filter is not encode-enabled, the corresponding H5Pset_*
 * function will return an error if the filter is added to a dataset
 * creation property list (which is required if the filter is to be
 * used to encode that dataset). For example, if the
 * H5Z_FILTER_CONFIG_ENCODE_ENABLED flag is not returned for the SZIP
 * filter, H5Z_FILTER_SZIP, a call to H5Pset_szip will fail.</p>
 *
 * <p>If a filter is not decode-enabled, the application will not be
 * able to read an existing file encoded with that filter.</p>
 *
 * <p>This function should be called, and the returned filter_config
 * analyzed, before calling any other function, such as H5Pset_szip,
 * that might require a particular filter configuration. </p>
 *
 * @param filter Identifier of the filter to query.
 * @param filter_config (Output) A bit field encoding the returned
 * filter information.
 *
 * @return Returns a non-negative value on success, a negative value
 * on failure.
 */
%apply long* OUTPUT {unsigned int* filter_config_flags}
herr_t H5Zget_filter_info(H5Z_filter_t filter, unsigned int *filter_config_flags);

/**
 * Registers new filter.
 *
 * <p>H5Zregister registers a new filter with the HDF5 library.</p>
 *
 * <p>Making a new filter available to an application is a two-step
 * process. The first step is to write the three filter callback
 * functions described below: can_apply_func, set_local_func, and
 * filter_func. This call to H5Zregister, registering the filter with
 * the library, is the second step. The can_apply_func and
 * set_local_func fields can be set to NULL if they are not required
 * for the filter being registered. </p>
 *
 * <p>H5Zregister accepts a single parameter, the filter_class data
 * structure.</p>
 *
 * TODO document H5Z_class_t
 *
 * @param filter_class Struct containing filter-definition information.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Zregister(const H5Z_class_t *cls);

/**
 * Unregisters a filter.
 *
 * <p>H5Zunregister unregisters the filter specified in filter.  </p>
 *
 * <p>After a call to H5Zunregister, the filter specified in filter
 * will no longer be available to the application. </p>
 *
 * @param filter Identifier of the filter to be unregistered.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Zunregister(H5Z_filter_t id);

