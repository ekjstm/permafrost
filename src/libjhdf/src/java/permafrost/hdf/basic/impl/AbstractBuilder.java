/**
 *
 */
package permafrost.hdf.basic.impl;



/**
 * Abstract base class for object builders.
 *
 */
public abstract class AbstractBuilder<T> {

    /** The parent of the object the builder is creating. */
    protected final AbstractObject parent;
    
    /** The name of the new object. */
    protected String name;
    
    /**
     * Creates a new AbstractBuilder object.
     *
     */
    protected AbstractBuilder() {
        super();
        this.parent = null;
    }
    
        
    /**
     * Creates a new AbstractBuilder object.
     *
     * @param parent The parent of the object the builder is creating.
     */
    protected AbstractBuilder(AbstractObject parent) {
        super();
        this.parent = parent;
    }       

    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#valid()
     */
    public boolean isValid() {
        boolean valid = true;
        valid = valid ? this.name != null && !this.name.isEmpty() : valid;
        valid = valid ? this.parent != null : valid;
        return (valid);
    }
    
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IObjectBuilder#getName()
     */
    public String getName() {
        return (this.name);
    }
    
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IObjectBuilder#setName(java.lang.String)
     */   
    public AbstractBuilder<T> setName(String name) {
        if (name == null || name.isEmpty()) {
            String err = "Object name must be not be null or empty.";
            throw new IllegalArgumentException(err);
        }
        this.name = name;  
        return (this);
    }
        

}
