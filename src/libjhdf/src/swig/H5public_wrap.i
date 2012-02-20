%module H5
%include "typemaps.i"
%include "H5types.i"

%header %{
#include "H5public.h"
%}

/**
 * Verifies that library versions are consistent.
 *
 * <p>H5check_version verifies that the arguments provided with the 
 * function call match the version numbers compiled into the library.</p>
 *
 * <p>H5check_version serves two slightly differing purposes.</p>
 *
 * <p>First, the function is intended to be called by the user to verify that 
 * the version of the header files compiled into an application matches the 
 * version of the HDF5 library being used. One may look at the H5check 
 * definition in the file H5public.h as an example.</p>
 *
 * <p>Due to the risks of data corruption or segmentation faults, 
 * H5check_version causes the application to abort if the version numbers 
 * do not match. The abort is achieved by means of a call to the standard C 
 * function abort().</p>
 *
 * <p>Note that H5check_version verifies only the major and minor version 
 * numbers and the release number; it does not verify the sub-release value 
 * as that should be an empty string for any official release. This means that 
 * any two incompatible library versions must have different 
 * {major,minor,release} numbers. (Notice the reverse is not necessarily 
 * true.)</p>
 *
 * <p>Secondarily, H5check_version verifies that the library version 
 * identifiers H5_VERS_MAJOR, H5_VERS_MINOR, H5_VERS_RELEASE, 
 * H5_VERS_SUBRELEASE, and H5_VERS_INFO are consistent. This is designed 
 * to catch source code inconsistencies, but does not generate the fatal 
 * error as in the first stage because this inconsistency does not cause 
 * errors in the data files. If this check reveals inconsistencies, the 
 * library issues a warning but the function does not fail.</p>
 *
 * @param majnum The major version of the library.
 * @param minnum The minor version of the library.
 * @param relnum The release number of the library.
 *
 * @return Returns a non-negative value if successful. Upon failure, this function causes the application to abort.
 */
herr_t H5check_version(unsigned majnum, unsigned minnum, unsigned relnum);

/**
 * Flushes all data to disk, closes file identifiers, and cleans up memory.
 *
 * <p>H5close flushes all data to disk, closes all file identifiers, and 
 * cleans up all memory used by the library. This function is generally 
 * called when the application calls exit(), but may be called earlier in 
 * event of an emergency shutdown or out of desire to free all resources 
 * used by the HDF5 library.</p>
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
 herr_t H5close(void);

/**
 * Instructs library not to install atexit cleanup routine.
 *
 * <p>H5dont_atexit indicates to the library that an atexit() cleanup routine 
 * should not be installed. The major purpose for this is in situations where 
 * the library is dynamically linked into an application and is un-linked 
 * from the application before exit() gets called. In those situations, a 
 * routine installed with atexit() would jump to a routine which was no longer 
 * in memory, causing errors.</p>
 * 
 * <p>In order to be effective, this routine must be called before any other 
 * HDF function calls, and must be called each time the library is 
 * loaded/linked into the application (the first time and after it's been 
 * un-loaded).</p>
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5dont_atexit(void);

/**
 * Garbage collects on all free-lists of all types.
 *
 * <p>H5garbage_collect walks through all the garbage collection routines of 
 * the library, freeing any unused memory.</p>
 *
 * <p>It is not required that H5garbage_collect be called at any particular 
 * time; it is only necessary in certain situations where the application 
 * has performed actions that cause the library to allocate many objects. The 
 * application should call H5garbage_collect if it eventually releases those 
 * objects and wants to reduce the memory used by the library from the peak 
 * usage required.</p>
 *
 * <p>The library automatically garbage collects all the free lists when the 
 * application ends.</p>
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5garbage_collect(void);

/**
 * Returns the HDF library release number.
 *
 * <p>H5get_libversion retrieves the major, minor, and release numbers of the 
 * version of the HDF library which is linked to the application.</p>
 *
 * @param majnum (Output) The major version of the library.
 * @param minnum (Output) The minor version of the library.
 * @param relnum (Output) The release number of the library.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply int *OUTPUT {unsigned* majnum}
%apply int *OUTPUT {unsigned* minnum}
%apply int *OUTPUT {unsigned* relnum}
herr_t H5get_libversion(unsigned* majnum, unsigned* minnum, 
			       unsigned* relnum);

/**
 * Initializes the HDF5 library.
 *
 * <p>H5open initializes the library.</p>
 *
 * <p>When the HDF5 Library is employed in a C application, this function is 
 * normally called automatically, but if you find that an HDF5 library 
 * function is failing inexplicably, try calling this function first. If you 
 * wish to eliminate this possibility, it is safe to routinely call H5open 
 * before an application starts working with the library as there are no 
 * damaging side-effects in calling it more than once. </p>
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5open(void);

/**
 * Sets free-list size limits.
 *
 * <dd>H5set_free_list_limits sets size limits on all types of free lists. 
 * The HDF5 library uses free lists internally to manage memory.
 *
 * <p>There are three types of free lists:</p>
 * <ul><li>Regular<br/> 
 *     free lists manage data structures containing atomic data.
 *     <li>Array<br/>
 *     free lists manage data structures containing array data.
 *     <li>Block<br/>
 *     free lists manage blocks of bytes.
 * </ul> 
 *
 * <p>These are global limits, but each limit applies only to free lists 
 * of the specified type. Therefore, if an application sets a 1Mb limit on 
 * each of the global lists, up to 3Mb of total storage might be allocated, 
 * 1Mb for each of the regular, array, and block type lists.</p>
 *
 * <p>Using a value of -1 for a limit means that no limit is set for the 
 * specified type of free list.</p>
 *
 * @param reg_global_lim The limit on all regular free list memory used.
 * @param reg_list_lim The limit on memory used in each regular free list.
 * @param arr_global_lim The limit on all array free list memory used.
 * @param arr_list_lim The limit on memory used in each array free list.
 * @param blk_global_lim The limit on all block free list memory used.
 * @param blk_list_lim The limit on memory used in each block free list.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5set_free_list_limits (int reg_global_lim, int reg_list_lim,
				      int arr_global_lim, int arr_list_lim, 
				      int blk_global_lim, int blk_list_lim);





