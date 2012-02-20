/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.basic.IGroup;
import permafrost.hdf.basic.builder.IGroupBuilder;

/**
 * Builder for creating a new IGroup.
 *
 */
public class GroupBuilder extends AbstractBuilder<IGroup> implements IGroupBuilder {
    
    /**
     * Creates a new GroupBuilder object.
     *
     * @param parent The parent of the object the builder is creating.
     */
    protected GroupBuilder(AbstractGroup parent) {
        super(parent);
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#name(java.lang.String)
     */
    @Override
    public GroupBuilder setName(String name) {
        super.setName(name);
        return (this);
    }
        
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#build()
     */
    @Override
    public Group build() {
        if (!this.isValid()) {
            throw new IllegalStateException("The builder is not in a valid state.");
        }
        Group group = Group.create(this.parent.getHId(), this.name);
        return (group);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.impl.builder.AbstractBuilder#valid()
     */
    @Override
    public boolean isValid() {       
        return super.isValid();
    }
    
    
}
