/**
 *
 */
package permafrost.hdf.basic;

import java.util.Iterator;

import permafrost.hdf.basic.builder.IGroupBuilder;

/**
 * Interface for abstract group objects.
 *
 */
public interface IGroup extends IHObject, Iterable<IHObject> {

    /**
     * Gets an iterator over the child objects in the group.
     * 
     * @return An iterator over the child objects in the group.
     */
    public Iterator<IHObject> getChildren();
    
    
    public IHObject getChild(String name);
    
    /**
     * Creates a new builder for creating group children of this group.
     * 
     * @return A new group builder for creating group children.
     */
    public IGroupBuilder groupBuilder();
    
    /**
     * Gets an iterator over the child objects in the group.
     * 
     * Equivalent to {@link #getChildren()}. 
     * 
     * @return An iterator over the child objects in the group.
     */
    Iterator<IHObject> iterator();
}
