/**
 *
 */
package permafrost.hdf.libhdf;

/**
 * LibJHDF extension functions for {@link DatasetLib}.
 *
 */
public class DatasetLibExtension {

    /**
     * Reads a dataset as a byte array.
     * 
     * @param loc_id The id of the dataset to read.
     * @param mem_space_id The memory dataspace to read to.
     * @param file_space_id The file dataspace to read from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read to.
     * 
     * @return Returns a non-negative value if successful; otherwise returns a negative value.
     */
    public static int JHDread_char(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            byte[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_char(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDread_char_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            byte[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_char_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDread_double(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            double[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_double(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDread_double_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            double[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_double_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDread_float(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            float[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_float(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDread_float_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            float[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_float_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDread_int(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            int[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_int(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDread_int_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            int[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_int_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDread_long_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            long[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_long_long_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDread_long_long(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            long[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_long_long(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDread_short(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            short[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_short(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDread_short_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            short[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDread_short_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

    /**
     * Writes a dataset as a byte array.
     * 
     * @param loc_id The id of the dataset to write.
     * @param mem_space_id The memory dataspace to write to.
     * @param file_space_id The file dataspace to write from.
     * @param plist_id The dataset access property list.
     * @param buf The buffer to read from.
     * 
     * @return Returns a non-negative value if successful; 
     * otherwise returns a negative value.
     */
    public static int JHDwrite_char(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            byte[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_char(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDwrite_char_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            byte[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_char_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

    /**
     * Writes a dataset as a double array.
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
    public static int JHDwrite_double(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            double[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_double(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDwrite_double_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            double[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_double_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDwrite_float(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            float[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_float(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDwrite_float_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            float[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_float_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDwrite_int(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            int[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_int(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDwrite_int_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            int[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_int_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDwrite_long_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            long[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_long_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

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
    public static int JHDwrite_long_long(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            long[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_long_long(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDwrite_short(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            short[] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_short(
                loc_id, 
                mem_space_id, 
                file_space_id, 
                plist_id, 
                buf)
        );
    }

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
    public static int JHDwrite_short_2d(
            int loc_id,  
            int mem_space_id, 
            int file_space_id, 
            int plist_id,
            short[][] buf  
    ) {
        return (DatasetLibExtensionJNI.JHDwrite_short_2d(
                loc_id,
                mem_space_id, 
                file_space_id,
                plist_id,
                buf)
        );
    }

}
