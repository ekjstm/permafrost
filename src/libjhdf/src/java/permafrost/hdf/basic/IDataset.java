/**
 *
 */
package permafrost.hdf.basic;

import java.nio.ByteBuffer;


/**
 * Interface for Dataset objects.
 *
 */
public interface IDataset extends IHObject {

    /**
     * Gets the dataset according to the current dataspace 
     * as a byte array.
     *    
     * @return The data in the current dataset and dataspace.
     */
    public byte[] getBytes(); 
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a 2d byte array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @return The data in the current dataset and dataspace.
     */
    public byte[][] getBytes2d();
    
    /**
     * Gets the dataset according to the current dataspace
     * as a ByteBuffer.
     * 
     * @param buf A direct byte buffer with enough capacity to 
     * hold the current dataspace.
     * 
     * @return The byte buffer.
     */
    public ByteBuffer getBytesDirect(ByteBuffer buf);
    
    /**
     * Gets the dataspace associated with the dataset.
     * 
     * @return The dataspace associated with the dataset.
     */
    public IDataspace getDataspace();
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a double array.
     *    
     * @return The data in the current dataset and dataspace.
     */
    public double[] getDoubles();
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a 2d double array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @return The data in the current dataset and dataspace.
     */
    public double[][] getDoubles2d();
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a float array.
     *    
     * @return The data in the current dataset and dataspace.
     */
    public float[] getFloats();

    /**
     * Gets the dataset according to the current dataspace 
     * as a 2d float array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @return The data in the current dataset and dataspace.
     */
    public float[][] getFloats2d();
    
    /**
     * Gets the dataset according to the current dataspace 
     * as an int array.
     *    
     * @return The data in the current dataset and dataspace.
     */
    public int[] getInts();
    
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a 2d int array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @return The data in the current dataset and dataspace.
     */
    public int[][] getInts2d();
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a long array.
     *    
     * @return The data in the current dataset and dataspace.
     */
    public long[] getLongs();
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a 2d long array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @return The data in the current dataset and dataspace.
     */
    public long[][] getLongs2d();
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a short array.
     *    
     * @return The data in the current dataset and dataspace.
     */
    public short[] getShorts();
    
    /**
     * Gets the dataset according to the current dataspace 
     * as a 2d short array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @return The data in the current dataset and dataspace.
     */
    public short[][] getShorts2d();
    
    /**
     * Sets the dataset according to the current dataspace
     * as a byte array.
     * 
     * @param buf The data to write to the dataset.
     */
    public void setBytes(byte[] buf);
    
    /**
     * Sets the dataset according to the current dataspace 
     * as a 2d byte array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @param The data to write to the dataset.
     */
    public void setBytes2d(byte[][] buf);
    
    /**
     * Sets the dataset according to the current dataspace
     * as a ByteBuffer.
     * 
     * @param buf A direct byte buffer.
     * 
     * @return The byte buffer.
     */
    public ByteBuffer setBytesDirect(ByteBuffer buf);
    
    /**
     * Sets the dataset according to the current dataspace
     * as a double array.
     * 
     * @param buf The data to write to the dataset.
     */
    public void setDoubles(double[] buf);
    
    /**
     * Sets the dataset according to the current dataspace 
     * as a 2d double array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @param The data to write to the dataset.
     */
    public void setDoubles2d(double[][] buf);
    
    /**
     * Sets the extent of the dataset.
     * 
     * @param extent The extent of the dataset.
     */
    public void setExtent(long[] extent);
    
    /**
     * Sets the dataset according to the current dataspace 
     * as a 2d float array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @param The data to write to the dataset.
     */
    public void setFloat2d(float[][] buf);
    
    /**
     * Sets the dataset according to the current dataspace
     * as a float array.
     * 
     * @param buf The data to write to the dataset.
     */
    public void setFloats(float[] buf);
    
    /**
     * Sets the dataset according to the current dataspace
     * as an int array.
     * 
     * @param buf The data to write to the dataset.
     */
    public void setInts(int[] buf);
    
    /**
     * Sets the dataset according to the current dataspace 
     * as a 2d int array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @param The data to write to the dataset.
     */
    public void setInts2d(int[][] buf);
    
    /**
     * Sets the dataset according to the current dataspace
     * as a long array.
     * 
     * @param buf The data to write to the dataset.
     */
    public void setLongs(long[] buf);
    
    /**
     * Sets the dataset according to the current dataspace 
     * as a 2d long array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @param The data to write to the dataset.
     */
    public void setLongs2d(long[][] buf);
    
    /**
     * Sets the dataset according to the current dataspace
     * as a short array.
     * 
     * @param buf The data to write to the dataset.
     */
    public void setShorts(short[] buf);
    
    /**
     * Sets the dataset according to the current dataspace 
     * as a 2d short array.
     * 
     * <p>The current dataspace must have rank two, or
     * an UnsupportedOperationException will be thrown.</p>
     *    
     * @param The data to write to the dataset.
     */
    public void setShorts2d(short[][] buf);
    
}
