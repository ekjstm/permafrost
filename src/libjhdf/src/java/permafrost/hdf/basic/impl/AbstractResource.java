/**
 *
 */
package permafrost.hdf.basic.impl;


/**
 * Abstract base class for HDF5 resources.
 *
 */
public abstract class AbstractResource implements IResource {
    
    protected NativeReference ref;
    protected final int hid;
    
    private String localName;

    
    /**
     * Creates a new AbstractResource object.
     *
     * @param hid The native resource id.
     */
    protected AbstractResource(int hid) {
        super();
        this.hid = hid;
    }
       

    /**
     * Creates a new AbstractResource object.
     *
     * @param hid The native resource id.
     * @param name The name of the resource. 
     */
    public AbstractResource(int hid, String name) {
        this(hid);
        this.localName = name;
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.impl.IResource#getHId()
     */
    @Override
    public int getHId() {
        return (this.hid);
    }
    
    protected void setReference(NativeReference ref) {
        assert this.ref == null;
        this.ref = ref;
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.impl.IDisposable#dispose()
     */
    @Override
    public void dispose() {
        if (this.ref != null) {
            this.ref.dispose();
            this.ref = null;
        }
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IHResource#getLocalName()
     */
    @Override
    public String getLocalName() {
        return (this.localName);
    }

    /**
     * Sets the local name of the resource.
     * 
     * @param localName The local name of the resource.
     */
    protected void setLocalName(String localName) {
        this.localName = localName;
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IHResource#getPath()
     */
    @Override
    public String getPath() {
        // TODO Auto-generated method stub
        return null;
    }
    
    protected boolean isInitialized() {
        return (this.hid > 0 && this.ref != null);
    }
    

}
