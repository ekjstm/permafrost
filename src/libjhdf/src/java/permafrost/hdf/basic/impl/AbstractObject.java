/**
 *
 */
package permafrost.hdf.basic.impl;

import java.util.Iterator;

import permafrost.hdf.basic.IAttribute;
import permafrost.hdf.basic.IHObject;
import permafrost.hdf.basic.impl.types.DatatypeFactory;
import permafrost.hdf.libhdf.IdentifierType;
import permafrost.hdf.libhdf.ObjectLib;
import permafrost.hdf.libhdf.PropertiesLib;
import permafrost.hdf.libhdf.StringUtils;

/**
 * Abstract base class for HDF5 objects.
 *
 */
public abstract class AbstractObject extends AbstractResource implements IHObject {

    /** Default size of buffer for comment read. */
    private static final int SZ_DEFAULT_COMMENT = 256;

    /**
     * Creates a new AbstractObject object.
     *
     * @param hid The native resource id.
     */
    protected AbstractObject(int hid) {
       super(hid);
    }

    /**
     * Creates a new AbstractObject object.
     *
     * @param hid The native resource id.
     * @param name The name of the group. 
     */
    public AbstractObject(int hid, String name) {
        super(hid, name);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IHObject#getComment()
     */
    @Override
    public String getComment() {
        assert this.isInitialized() : "Native reference not initialized";
        byte[] buffer = new byte[AbstractObject.SZ_DEFAULT_COMMENT];        
        int size = ObjectLib.H5Oget_comment(this.hid, buffer, buffer.length);
        assert size >= 0 : "Untrapped error in native code.";
        if (size == 0) {
            return null;
        }
        if (size >= buffer.length) {
            buffer = new byte[size+1];
            size = ObjectLib.H5Oget_comment(this.hid, buffer, buffer.length);
            assert size > 0 : "Untrapped error in native code.";
        }
        String comment = StringUtils.fromNullTerm(buffer);
        return (comment);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IHObject#getParent()
     */
    @Override
    public IHObject getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see permafrost.hdf.basic.IHObject#setComment(java.lang.String)
     */
    @Override
    public void setComment(String comment) {
        assert this.isInitialized() : "Native reference not initialized";
        int status = ObjectLib.H5Oset_comment(this.hid, comment);
        assert status >= 0 : "Untrapped error in native code.";
    }
    
    
    
    
    protected static AbstractObject newObject(int hid, String localName, IdentifierType type) {
        switch (type) {
        case H5I_DATASET:
            return (new Dataset(hid, localName));
        case H5I_DATATYPE:
            DatatypeFactory.getInstance().newObject(hid, localName);
        case H5I_FILE:
            return (new File(hid));
        case H5I_GROUP:
            return (new Group(hid, localName));
        case H5I_BADID:
            throw new IllegalArgumentException("Illegal object type for new object: " + IdentifierType.H5I_BADID);
        default:
            throw new UnsupportedOperationException("unsupported object type for new object: " + type);
        }
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IHObject#getAttributes()
     */
    @Override
    public Iterator<IAttribute> getAttributes() {
       return (new AttributeIterator(this));
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IHObject#getAttribute(java.lang.String)
     */
    @Override
    public Attribute getAttribute(String name) {
        Attribute attr = Attribute.open(this, name, PropertiesLib.H5P_DEFAULT);
        return (attr);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IHObject#attributeBuilder()
     */
    @Override
    public AttributeBuilder attributeBuilder() {
        return (new AttributeBuilder(this));
    }
    
    
    

}
