/**
 *
 */
package permafrost.hdf.basic;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Interface for Attribute objects.
 *
 */
public interface IAttribute extends IHResource {

    /**
     * Gets the entire attribute dataset as a byte buffer.
     * 
     * @param buf The byte buffer to use for data transfer.
     * buf must be a direct allocated buffer with 
     * @param dims (Output) The size of each individual dimension 
     * of the dataset.
     * 
     * @return A view of the given ByteBuffer.
     */
    public ByteBuffer getBytes(ByteBuffer buf, List<Long> dims);
    
    /**
     * Gets the entire attribute dataset as a byte array.
     * 
     * @param dims (Output) The size of each individual dimension 
     * of the dataset.
     * 
     * @return The dataset as a byte array.
     */
    public byte[] getBytes(List<Long> dims);
    
    /**
     * Gets the dataspace associated with the attribute.
     * 
     * @return The dataspace associated with the attribute
     */
    public IDataspace getDataspace();    
    
    /**
     * Gets the entire attribute dataset as a double array.
     * 
     * @param dims (Output) The size of each individual dimension 
     * of the dataset.
     * 
     * @return The dataset as a double array.
     */
    public double[] getDoubles(List<Long> dims);
    
    /**
     * Gets the extent of the attribute data set.
     * 
     * @param eltSize (Output) If non-null, returns the size 
     * in bytes of an individual element of the data set.
     * 
     * @return The size of each individual dimension of the dataset.
     */
    public long[] getExtent(long[] eltSize);    
    
    /**
     * Gets the entire attribute dataset as a float array.
     * 
     * @param dims (Output) The size of each individual dimension 
     * of the dataset.
     * 
     * @return The dataset as a float array.
     */
    public float[] getFloats(List<Long> dims);
    
    /**
     * Gets the entire attribute dataset as an int array.
     * 
     * @param dims (Output) The size of each individual dimension 
     * of the dataset.
     * 
     * @return The dataset as an int array.
     */
    public int[] getInts(List<Long> dims);
        
    
    /**
     * Gets the parent object for the attribute.
     * 
     * @return The parent object for the attribute.
     */
    public IHObject getParent();
    
    /**
     * Gets the entire attribute dataset as a short array.
     * 
     * @param dims (Output) The size of each individual dimension 
     * of the dataset.
     * 
     * @return The dataset as a short array.
     */
    public short[] getShorts(List<Long> dims); 
    
    /**
     * Gets the entire attribute dataset as a string.
     * 
     * @return The dataset as a string.
     */
    public String getString();
    
    /**
     * Sets the attribute dataset to the given 
     * one-dimensional value. Any existing dataset
     * will be overwritten.
     * 
     * @param data The new dataset value.
     */
    public void setBytes(byte[] data);
    
    /**
     * Sets the attribute dataset to the given 
     * one-dimensional value. Any existing dataset
     * will be overwritten.
     * 
     * @param data The new dataset value.
     */
    public void setBytes(ByteBuffer data);
    
    /**
     * Sets the attribute dataset to the given 
     * one-dimensional value. Any existing dataset
     * will be overwritten.
     * 
     * @param data The new dataset value.
     */
    public void setDoubles(double[] data);
        
    
    /**
     * Sets the attribute dataset to the given 
     * one-dimensional value. Any existing dataset
     * will be overwritten.
     * 
     * @param data The new dataset value.
     */
    public void setFloats(float[] data);
    
    /**
     * Sets the attribute dataset to the given 
     * one-dimensional value. Any existing dataset
     * will be overwritten.
     * 
     * @param data The new dataset value.
     */
    public void setInts(int[] data);
    
    /**
     * Sets the attribute dataset to the given 
     * one-dimensional value. Any existing dataset
     * will be overwritten.
     * 
     * @param data The new dataset value.
     */
    public void setShorts(short[] data);
    
    /**
     * Sets the attribute dataset to the given 
     * one-dimensional value. Any existing dataset
     * will be overwritten.
     * 
     * @param data The new dataset value.
     */
    public void setString(String data);
    
}
