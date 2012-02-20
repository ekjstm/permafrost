/**
 *
 */
package permafrost.hdf.basic.impl;

import java.nio.ByteBuffer;

import permafrost.hdf.basic.DataspaceExtent;
import permafrost.hdf.basic.IDataset;
import permafrost.hdf.basic.impl.types.Datatype;
import permafrost.hdf.libhdf.DatasetLib;
import permafrost.hdf.libhdf.DatasetLibExtension;
import permafrost.hdf.libhdf.DataspaceLibConstants;
import permafrost.hdf.libhdf.DatatypeLib;
import permafrost.hdf.libhdf.DatatypeLibRTConstants;
import permafrost.hdf.libhdf.PropertiesLib;

/**
 * Dataset implementation.
 *
 */
public class Dataset extends AbstractObject implements IDataset {

    /** Implements the native handle for Dataset. */
    private static final class DatasetReference extends NativeReference {

        /**
         * Creates a new DatasetReference object.
         *
         * @param referent The vm referent.
         */
        public DatasetReference(Dataset referent) {
            super(referent);
        }

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.NativeReference#doDispose()
         */
        @Override
        protected void doDispose() {
            if (this.hid > 0) {
                int status = DatasetLib.H5Dclose(this.hid);
                assert status >= 0 : "Untrapped error in native code.";
                this.hid = 0;
            }            
        }        
    }
    
    private Dataspace space = null;
    
    /**
     * Creates a new Dataset object.
     *
     * @param hid The native resource id.
     */
    public Dataset(int hid) {
        super(hid);        
        this.setReference(new DatasetReference(this));
    }

    /**
     * Creates a new Dataset object.
     *
     * @param hid The native resource id.
     * @param name The name of the group. 
     */
    public Dataset(int hid, String name) {
        super(hid, name);  
        this.setReference(new DatasetReference(this));
    }
    
    /*
     * (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getBytes()
     */
    @Override
    public byte[] getBytes() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        byte[] buf = new byte[(int) nElements]; 
        
        int status = DatasetLibExtension.JHDread_char(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return(buf);
    }
    
    /*
         * (non-Javadoc)
         * @see permafrost.hdf.basic.IDataset#getBytes2d()
         */
       @Override
        public byte[][] getBytes2d() {
            assert this.isInitialized() : "Native reference not initialized";        
            
            DataspaceExtent extent = this.getDataspace().getExtents();
            if (extent.extents.length != 2) {
                throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
            }
            byte[][] buf = new byte[(int) extent.extents[0]][(int) extent.extents[1]]; 
            
            int status = DatasetLibExtension.JHDread_char_2d(
                    this.hid, 
                    DataspaceLibConstants.H5S_ALL, 
                    this.space.getHId(), 
                    PropertiesLib.H5P_DEFAULT,
                    buf
            );
            assert status >= 0 : "Untrapped error in native code.";
          
            return(buf);
        }
        

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getBytesDirect(java.nio.ByteBuffer)
     */
    @Override
    public ByteBuffer getBytesDirect(ByteBuffer buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        if (buf.capacity() < nElements) throw new IllegalArgumentException("Insufficient buffer capacity to read dataset.");      
        buf.clear();     
        
        int status = DatasetLib.H5Dread(
                this.hid, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT8, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
        
        buf.limit((int) nElements);
        return(buf);
    }    
    
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getDataspace()
     */
    @Override
    public Dataspace getDataspace() {
       if (this.space != null) {
           return (this.space);
       }
       
       assert this.isInitialized() : "Native reference not initialized";
       int sid = DatasetLib.H5Dget_space(this.hid);
       assert sid > 0 : "Untrapped error in native code.";
       this.space = new Dataspace(sid);
       return (this.space);
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getDoubles()
     */
    @Override
    public double[] getDoubles() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        double[] buf = new double[(int) nElements]; 
        
        int status = DatasetLibExtension.JHDread_double(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return(buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getDoubles2d()
     */
    @Override
    public double[][] getDoubles2d() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        double[][] buf = new double[(int) extent.extents[0]][(int) extent.extents[1]]; 
        
        int status = DatasetLibExtension.JHDread_double_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return(buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getFloats()
     */
    @Override
    public float[] getFloats() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        float[] buf = new float[(int) nElements]; 
        
        int status = DatasetLibExtension.JHDread_float(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return(buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getFloats2d()
     */
    @Override
    public float[][] getFloats2d() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        float[][] buf = new float[(int) extent.extents[0]][(int) extent.extents[1]]; 
        
        int status = DatasetLibExtension.JHDread_float_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return (buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getInts()
     */
    @Override
    public int[] getInts() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        int[] buf = new int[(int) nElements]; 
        
        int status = DatasetLibExtension.JHDread_int(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return(buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getInts2d()
     */
    @Override
    public int[][] getInts2d() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        int[][] buf = new int[(int) extent.extents[0]][(int) extent.extents[1]]; 
        
        int status = DatasetLibExtension.JHDread_int_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return (buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getLongs()
     */
    @Override
    public long[] getLongs() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        long[] buf = new long[(int) nElements]; 
        
        int status = DatasetLibExtension.JHDread_long_long(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return(buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getLongs2d()
     */
    @Override
    public long[][] getLongs2d() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        long[][] buf = new long[(int) extent.extents[0]][(int) extent.extents[1]]; 
        
        int status = DatasetLibExtension.JHDread_long_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return (buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getShorts()
     */
    @Override
    public short[] getShorts() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        short[] buf = new short[(int) nElements]; 
        
        int status = DatasetLibExtension.JHDread_short(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return(buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#getShorts2d()
     */
    @Override
    public short[][] getShorts2d() {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        short[][] buf = new short[(int) extent.extents[0]][(int) extent.extents[1]]; 
        
        int status = DatasetLibExtension.JHDread_short_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
      
        return (buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setBytes(byte[])
     */
    @Override
    public void setBytes(byte[] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        if (buf.length < nElements) throw new IllegalArgumentException("Buffer size is smaller than current dataspace.");    
        
        int status = DatasetLibExtension.JHDwrite_char(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";      
    }
    
    /* (non-Javadoc)
 * @see permafrost.hdf.basic.IDataset#setBytes2d(byte[][])
 */
@Override
public void setBytes2d(byte[][] buf) {
    assert this.isInitialized() : "Native reference not initialized";        
    
    DataspaceExtent extent = this.getDataspace().getExtents();
    if (extent.extents.length != 2) {
        throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
    }
    if (buf.length < extent.extents[0]) throw new IllegalArgumentException("Size of buffer dimension one does not match size of current dataspace dimension one.");
    if (buf[0].length < extent.extents[1]) throw new IllegalArgumentException("Size of buffer dimension two does not match size of current dataspace dimension two."); 
    
    int status = DatasetLibExtension.JHDwrite_char_2d(
            this.hid, 
            DataspaceLibConstants.H5S_ALL, 
            this.space.getHId(), 
            PropertiesLib.H5P_DEFAULT,
            buf
    );
    assert status >= 0 : "Untrapped error in native code.";      
}
    

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setBytesDirect(java.nio.ByteBuffer)
     */
    @Override
    public ByteBuffer setBytesDirect(ByteBuffer buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        if (buf.capacity() < nElements) throw new IllegalArgumentException("Buffer size is smaller than current dataspace.");                       
        
        int status = DatasetLib.H5Dwrite(
                this.hid, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT8, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";
        buf.position((int) nElements-1);

        return(buf);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setDoubles(double[])
     */
    @Override
    public void setDoubles(double[] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        if (buf.length < nElements) throw new IllegalArgumentException("Buffer size is smaller than current dataspace.");    
        
        int status = DatasetLibExtension.JHDwrite_double(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";     
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setDoubles2d(double[][])
     */
    @Override
    public void setDoubles2d(double[][] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        if (buf.length < extent.extents[0]) throw new IllegalArgumentException("Size of buffer dimension one does not match size of current dataspace dimension one.");
        if (buf[0].length < extent.extents[1]) throw new IllegalArgumentException("Size of buffer dimension two does not match size of current dataspace dimension two."); 
        
        int status = DatasetLibExtension.JHDwrite_double_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";   
        
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setFloat2d(float[][])
     */
    @Override
    public void setFloat2d(float[][] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        if (buf.length < extent.extents[0]) throw new IllegalArgumentException("Size of buffer dimension one does not match size of current dataspace dimension one.");
        if (buf[0].length < extent.extents[1]) throw new IllegalArgumentException("Size of buffer dimension two does not match size of current dataspace dimension two."); 
        
        int status = DatasetLibExtension.JHDwrite_float_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";   
        
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setFloats(float[])
     */
    @Override
    public void setFloats(float[] buf) {
       assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        if (buf.length < nElements) throw new IllegalArgumentException("Buffer size is smaller than current dataspace.");    
        
        int status = DatasetLibExtension.JHDwrite_float(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";  
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setInts(int[])
     */
    @Override
    public void setInts(int[] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        if (buf.length < nElements) throw new IllegalArgumentException("Buffer size is smaller than current dataspace.");    
        
        int status = DatasetLibExtension.JHDwrite_int(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";  
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setInts2d(int[][])
     */
    @Override
    public void setInts2d(int[][] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        if (buf.length < extent.extents[0]) throw new IllegalArgumentException("Size of buffer dimension one does not match size of current dataspace dimension one.");
        if (buf[0].length < extent.extents[1]) throw new IllegalArgumentException("Size of buffer dimension two does not match size of current dataspace dimension two."); 
        
        int status = DatasetLibExtension.JHDwrite_int_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";   
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setLongs(long[])
     */
    @Override
    public void setLongs(long[] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        if (buf.length < nElements) throw new IllegalArgumentException("Buffer size is smaller than current dataspace.");    
        
        int status = DatasetLibExtension.JHDwrite_long_long(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";  
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setLongs2d(long[][])
     */
    @Override
    public void setLongs2d(long[][] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        if (buf.length < extent.extents[0]) throw new IllegalArgumentException("Size of buffer dimension one does not match size of current dataspace dimension one.");
        if (buf[0].length < extent.extents[1]) throw new IllegalArgumentException("Size of buffer dimension two does not match size of current dataspace dimension two."); 
        
        int status = DatasetLibExtension.JHDwrite_long_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";   
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setShorts(short[])
     */
    @Override
    public void setShorts(short[] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        long nElements = this.getDataspace().getExtents().numElements();        
        if (buf.length < nElements) throw new IllegalArgumentException("Buffer size is smaller than current dataspace.");    
        
        int status = DatasetLibExtension.JHDwrite_short(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";      
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setShorts2d(short[][])
     */
    @Override
    public void setShorts2d(short[][] buf) {
        assert this.isInitialized() : "Native reference not initialized";        
        
        DataspaceExtent extent = this.getDataspace().getExtents();
        if (extent.extents.length != 2) {
            throw new UnsupportedOperationException("Dataspace must be 2-dimensional.");
        }
        if (buf.length < extent.extents[0]) throw new IllegalArgumentException("Size of buffer dimension one does not match size of current dataspace dimension one.");
        if (buf[0].length < extent.extents[1]) throw new IllegalArgumentException("Size of buffer dimension two does not match size of current dataspace dimension two."); 
        
        int status = DatasetLibExtension.JHDwrite_short_2d(
                this.hid, 
                DataspaceLibConstants.H5S_ALL, 
                this.space.getHId(), 
                PropertiesLib.H5P_DEFAULT,
                buf
        );
        assert status >= 0 : "Untrapped error in native code.";   
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDataset#setExtent(long[])
     */
    @Override
    public void setExtent(long[] extent) {
        assert this.isInitialized() : "Native reference not initialized";
        int status = DatasetLib.H5Dset_extent(this.hid, extent);
        assert status >= 0 : "Untrapped error in native code."; 
        this.space = null;
        this.space = this.getDataspace();
    }
    
    public void getDatatype() {
        assert this.isInitialized() : "Native reference not initialized";
        int tid = DatasetLib.H5Dget_type(this.hid);
        assert tid > 0 : "Untrapped error in native code.";
        
    }
    
    /**
     * Opens an existing dataset.
     * 
     * @param parent The parent group.
     * @param name The name of the dataset to open.
     * 
     * @return A new dataset proxy for the given path.
     */
    public static Dataset open(AbstractGroup parent, String name) {
        return (Dataset.open(parent, name, PropertyList.Default));
    }

    /**
     * Opens an existing dataset.
     * 
     * @param  parent The parent group.
     * @param name The name of the dataset to open.
     * @param dapl The property list used to access the dataset.
     * 
     * @return A new dataset proxy for the given path.
     */
    public static Dataset open(AbstractGroup parent, String name, PropertyList dapl) { 
        assert parent.isInitialized() : "Native reference not initialized";      
        int sid = DatasetLib.H5Dopen2(parent.getHId(), name, dapl.getHId());
        assert sid > 0 : "Untrapped error in native code.";
        Dataset set = new Dataset(sid, name);
        return (set);
    }
    
    /**
     * Creates a new dataset.
     * 
     * @param parent The parent group.
     * @param name The name of the dataset to create.
     * @param space The dataspace of the new dataset.
     * @param type The datatype of the new dataset.
     * 
     * @return New dataset object.
     */
    public static Dataset create(
            AbstractGroup parent, 
            String name, 
            Dataspace space,
            Datatype type
    ) {
        return (Dataset.create(
                parent,
                name, 
                space, 
                type, 
                PropertyList.Default, 
                PropertyList.Default,
                PropertyList.Default
        ));
    }
    
    
    /**
     * Creates a new dataset.
     * 
     * @param parent The parent group.
     * @param name The name of the dataset to create.
     * @param space The dataspace of the new dataset.
     * @param type The datatype of the new dataset.
     * @param lcpl Link creation property list.
     * @param dcpl Dataset creation property list.
     * @param dapl Dataset access property list.
     * 
     * @return New dataset object.
     */
    public static Dataset create(
            AbstractGroup parent, 
            String name, 
            Dataspace space,
            Datatype type,
            PropertyList lcpl,
            PropertyList dcpl,
            PropertyList dapl
    ) {
      
        assert parent.isInitialized() : "Native reference not initialized";      
        assert space.isInitialized() : "Native reference not initialized";      
        assert type.isInitialized() : "Native reference not initialized";      
                
        int sid = DatasetLib.H5Dcreate2(
                parent.getHId(), 
                name, 
                type.getHId(), 
                space.getHId(), 
                lcpl.getHId(), 
                dcpl.getHId(), 
                dapl.getHId()
        );
        assert sid > 0 : "Untrapped error in native code.";
        Dataset set = new Dataset(sid, name);        
        return (set);
    }
    
}
