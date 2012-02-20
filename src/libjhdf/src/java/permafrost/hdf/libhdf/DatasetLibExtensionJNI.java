/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * Native functions for {@link ObjectLibExtension}.
 *
 */
class DatasetLibExtensionJNI {

    /**
     * Reads a dataset as a byte array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_char(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            byte[] buf  
    );

    /**
     * Reads a dataset as a 2D byte array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_char_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            byte[][] buf  
    );

    /**
     * Reads a dataset as a double array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_double(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            double[] buf  
    );

    /**
     * Reads a dataset as a 2D double array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_double_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            double[][] buf  
    );

    /**
     * Reads a dataset as a float array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_float(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            float[] buf  
    );

    /**
     * Reads a dataset as a 2D float array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_float_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            float[][] buf  
    );

    /**
     * Reads a dataset as an int array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_int(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            int[] buf  
    );

    /**
     * Reads a dataset as a 2D int array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_int_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            int[][] buf  
    );

    /**
     * Reads a dataset as a 2D long array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_long_long_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            long[][] buf  
    );

    /**
     * Reads a dataset as a long array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_long_long(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            long[] buf  
    );

    /**
     * Reads a dataset as a short array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_short(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            short[] buf  
    );

    /**
     * Reads a dataset as a 2D short array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDread_short_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            short[][] buf  
    );

    /**
     * Writes a dataset as a byte array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to write from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_char(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            byte[] buf  
    );


    /**
     * Writes a dataset as a 2D byte array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to read from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_char_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            byte[][] buf  
    );

    /**
     * Writes a dataset as a double array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to read from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_double(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            double[] buf  
    );

    /**
     * Writes a dataset as a 2D double array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to read from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_double_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            double[][] buf  
    );

    /**
     * Writes a dataset as a float array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to write from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_float(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            float[] buf  
    );

    /**
     * Writes a dataset as a 2D float array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to read from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_float_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            float[][] buf  
    );


    /**
     * Writes a dataset as an int array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to write from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_int(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            int[] buf  
    );

    /**
     * Writes a dataset as a 2D int array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to read from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_int_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            int[][] buf  
    );

    /**
     * Writes a dataset as a 2D long array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to read from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_long_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            long[][] buf  
    );

    /**
     * Writes a dataset as a long array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to write from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_long_long(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            long[] buf  
    );

    /**
     * Writes a dataset as a short array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to write from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_short(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            short[] buf  
    );

    /**
     * Writes a dataset as a 2D short array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to read from.
     * @param file_space_id The file dataspace to write to.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static final native int JHDwrite_short_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            short[][] buf  
    );

}
