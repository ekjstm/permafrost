/**
 *
 */
package permafrost.hdf.basic.impl;



import java.nio.ByteBuffer;
import java.util.List;

import permafrost.hdf.basic.IAttribute;
import permafrost.hdf.basic.impl.types.Datatype;
import permafrost.hdf.libhdf.AttributeLib;
import permafrost.hdf.libhdf.DatatypeClassType;
import permafrost.hdf.libhdf.LiteLib;
import permafrost.hdf.libhdf.StringUtils;

/**
 * IAttribute implementation.
 *
 */
public class Attribute extends AbstractResource implements IAttribute {

    /** Implements the native handle for Attribute. */
    private static final class AttributeReference extends NativeReference {

        /**
         * Creates a new AttributeReference object.
         *
         * @param referent The vm referent.
         */
        public AttributeReference(Attribute referent) {
            super(referent);
        }

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.NativeReference#doDispose()
         */
        @Override
        protected void doDispose() {
            if (this.hid > 0) {
                int status = AttributeLib.H5Aclose(this.hid);
                assert status >= 0 : "Untrapped error in native code.";
                this.hid = 0;
            }            
        }        
    }
    
    private AbstractObject parent;
    
    /**
     * Creates a new Attribute object.
     *
     * @param hid The native resource id.
     */
    public Attribute(int hid) {
        super(hid);
        this.setReference(new AttributeReference(this));
    }

    /**
     * Creates a new Attribute object.
     *
     * @param hid The native resource id.
     * @param name The name of the group.
     */
    public Attribute(int hid, String name, AbstractObject parent) {
        super(hid, name);
        this.setReference(new AttributeReference(this));
        this.parent = parent;
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getBytes(java.nio.ByteBuffer,java.util.List)
     */
    @Override
    public ByteBuffer getBytes(ByteBuffer buf, List<Long> dims) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        long[] dSize = new long[1];        
        long nElements = this.getDSSizeInfo(dims, dSize);
        long size = nElements*dSize[0];
        if (buf.capacity() < size) throw new IllegalArgumentException("Insufficient buffer capacity to read dataset.");      
        buf.clear();        
        
        int status = LiteLib.H5LTget_attribute_char_direct(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                buf);        
        assert status >= 0 : "Untrapped error in native code.";
        
        buf.limit((int) size);
        return (buf);
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getBytes()
     */
    @Override
    public byte[] getBytes(List<Long> dims) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int nElements = this.getDSSizeInfo(dims, null);
        byte[] data = new byte[nElements];
        
        int status = LiteLib.H5LTget_attribute_char(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data);
        assert status >= 0 : "Untrapped error in native code.";
        return (data);
    }
    
    /*
     * (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getDataspace()
     */
    @Override
    public Dataspace getDataspace() {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int sid = AttributeLib.H5Aget_space(this.hid);
        assert sid > 0 : "Untrapped error in native code.";        
        Dataspace space = new Dataspace(sid);
        return (space);
    }
    

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getDoubles()
     */
    @Override
    public double[] getDoubles(List<Long> dims) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int nElements = this.getDSSizeInfo(dims, null);
        double[] data = new double[nElements];
        
        int status = LiteLib.H5LTget_attribute_double(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data);
        assert status >= 0 : "Untrapped error in native code.";
        return (data);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getExtent(long[])
     */
    @Override
    public long[] getExtent(long[] eltSize) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
                
        int[] nDims = new int[1];
        int status = LiteLib.H5LTget_attribute_ndims(  
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                nDims
        );
        assert status >= 0 : "Untrapped error in native code.";        
        
        long[] xdims = new long[nDims[0] > 0 ? nDims[0] : 1];        
        DatatypeClassType[] dType = new DatatypeClassType[1];
        long[] dSize = new long[1];
        status = LiteLib.H5LTget_attribute_info(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                xdims, 
                dType, 
                dSize
        );
        assert status >= 0 : "Untrapped error in native code.";
        
        if (eltSize != null && eltSize.length > 0) {
            eltSize[0] = dSize[0];
        }
        if (DatatypeClassType.H5T_STRING == dType[0]) {
            return (dSize);
        }
        return (xdims);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getFloats()
     */
    @Override
    public float[] getFloats(List<Long> dims) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int nElements = this.getDSSizeInfo(dims, null);
        float[] data = new float[nElements];
        
        int status = LiteLib.H5LTget_attribute_float(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data);
        assert status >= 0 : "Untrapped error in native code.";
        return (data);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getInts(java.util.List)
     */
    @Override
    public int[] getInts(List<Long> dims) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int nElements = this.getDSSizeInfo(dims, null);        
        int[] data = new int[nElements];        
        
        int status = LiteLib.H5LTget_attribute_int(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data);
        assert status >= 0 : "Untrapped error in native code.";
        return (data);
    }    

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getParent()
     */
    @Override
    public AbstractObject getParent() {
       return (this.parent);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getShorts()
     */
    @Override
    public short[] getShorts(List<Long> dims) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int nElements = this.getDSSizeInfo(dims, null);
        short[] data = new short[nElements];
        
        int status = LiteLib.H5LTget_attribute_short(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data);
        assert status >= 0 : "Untrapped error in native code.";
        return (data);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#getString()
     */
    @Override
    public String getString() {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int nElements = this.getDSSizeInfo(null, null);
        byte[] data = new byte[nElements+1];
        
        int status = LiteLib.H5LTget_attribute_string(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data);
        assert status >= 0 : "Untrapped error in native code.";
        String value = StringUtils.fromNullTerm(data);        
        return (value);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#setBytes(byte[])
     */
    @Override
    public void setBytes(byte[] data) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int status = LiteLib.H5LTset_attribute_char(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data, 
                data.length
        );
        assert status >= 0 : "Untrapped error in native code.";        
    }
    

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#setBytes(java.nio.ByteBuffer)
     */
    @Override
    public void setBytes(ByteBuffer data) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int status = LiteLib.H5LTset_attribute_char_direct( // FIXME this invalidates the currently open attribute reference. Reimplement using H5Awrite, may need new wrappers
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data, 
                data.limit()
        );
        assert status >= 0 : "Untrapped error in native code.";      
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#setDoubles(double[])
     */
    @Override
    public void setDoubles(double[] data) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int status = LiteLib.H5LTset_attribute_double(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data, 
                data.length
        );
        assert status >= 0 : "Untrapped error in native code.";   
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#setFloats(float[])
     */
    @Override
    public void setFloats(float[] data) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int status = LiteLib.H5LTset_attribute_float(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data, 
                data.length
        );
        assert status >= 0 : "Untrapped error in native code.";   
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#setInts(int[])
     */
    @Override
    public void setInts(int[] data) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int status = LiteLib.H5LTset_attribute_int(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data, 
                data.length
        );
        assert status >= 0 : "Untrapped error in native code.";   
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#setShorts(short[])
     */
    @Override
    public void setShorts(short[] data) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int status = LiteLib.H5LTset_attribute_short(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data, 
                data.length
        );
        assert status >= 0 : "Untrapped error in native code.";   
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IAttribute#setString(java.lang.String)
     */
    @Override
    public void setString(String data) {
        assert this.parent != null && this.parent.isInitialized() : "Native reference not initialized in parent object.";
        assert this.isInitialized() : "Native reference not initialized";
        
        int status = LiteLib.H5LTset_attribute_string(
                this.parent.getHId(), 
                ".", 
                this.getLocalName(), 
                data
        );
        assert status >= 0 : "Untrapped error in native code.";   
    }

    /**
     * Sets the parent object for this attribute.
     * 
     * @param parent The object that owns this attribute.
     */
    protected void setParent(AbstractObject parent) {
        this.parent = parent;
    }  
    

    /**
     * Gets size info about the current attribute.
     * 
     * @param dims (Output) The size of each individual 
     * dimension of the dataset.
     * @param eltSize (Output) If non-null, returns the size, 
     * in bytes, of an individual element of the data set.
     * 
     * @return The total number of elements in the dataset.
     */
    private int getDSSizeInfo(List<Long> dims, long[] eltSize) {       
        long[] xdims = this.getExtent(eltSize);     
       
        int nElements = 1;
        if (dims != null) dims.clear();                
        for (int n=0; n<xdims.length; n++) {
            nElements *= xdims[n];
            if (dims != null) dims.add(Long.valueOf(xdims[n]));
        }
        
        return (nElements);        
    }

    /**
     * Opens an existing attribute.
     * 
     * @param parent The parent object of the attribute.
     * @param name The name of the attribute to open.
     * 
     * @return A new Group proxy for the given path.
     */
    protected static Attribute create(
            AbstractObject parent, 
            String name,
            Datatype dType,
            Dataspace dSpace,
            PropertyList acpl,
            PropertyList aapl) {
        assert parent != null && parent.isInitialized() : "Native reference not initialized in parent object.";
        assert dType != null && dType.isInitialized() : "Native reference not initialized in datatype object.";
        assert dSpace != null && dSpace.isInitialized() : "Native reference not initialized in dataspace object.";
        assert acpl != null && acpl.isInitialized() : "Native reference not initialized in property list object.";
        assert aapl != null && aapl.isInitialized() : "Native reference not initialized in property list object.";
        
        int aid = AttributeLib.H5Acreate2(
                parent.getHId(), 
                name, 
                dType.getHId(), 
                dSpace.getHId(), 
                acpl.getHId(), 
                aapl.getHId()
        );
        assert aid > 0 : "Untrapped error in native code.";
        Attribute att = new Attribute(aid, name, parent);
        return (att);
    }
    
    /**
     * Opens an existing attribute.
     * 
     * @param parent The parent object of the attribute.
     * @param name The name of the attribute to open.
     * 
     * @return A new Group proxy for the given path.
     */
    protected static Attribute open(AbstractObject parent, String name, int aapl_id) {
        assert parent != null && parent.isInitialized() : "Native reference not initialized in parent object.";
        int aid = AttributeLib.H5Aopen(parent.getHId(), name, aapl_id);
        assert aid > 0 : "Untrapped error in native code.";
        Attribute att = new Attribute(aid, name, parent);
        return (att);
    }

    

}
