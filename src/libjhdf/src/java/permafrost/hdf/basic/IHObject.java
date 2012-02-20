/**
 *
 */
package permafrost.hdf.basic;

import java.util.Iterator;

import permafrost.hdf.basic.builder.IAttributeBuilder;

/**
 * Interface for abstract HDF objects.
 *
 */
public interface IHObject extends IHResource {
    public IHObject getParent();
    
    /**
     * Gets the comment associated with the object.
     * 
     * @return The comment associated with the object, or null if no comment exists. 
     */
    public String getComment();
    
    /**
     * Sets the comment associated with the object.
     * 
     * @param comment The comment to associate with the object.
     */
    public void setComment(String comment);
    
    /**
     * Gets an iterator over the attributes of the objects.
     * 
     * @return Iterator over the attributes of the objects.
     */
    public Iterator<IAttribute> getAttributes();
    
    /**
     * Gets an attribute by name.
     * 
     * @param name The name of the attribute.
     * 
     * @return The attribute, if it exists.
     */
    public IAttribute getAttribute(String name);
    
    /**
     * Creates a new builder for creating attributes of this object.
     * 
     * @return A new attribute builder for creating attributes.
     */
    public IAttributeBuilder attributeBuilder();
}
