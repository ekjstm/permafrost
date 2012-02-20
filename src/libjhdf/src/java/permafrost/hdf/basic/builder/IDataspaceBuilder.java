/**
 *
 */
package permafrost.hdf.basic.builder;

import permafrost.hdf.basic.IDataspace;
import permafrost.hdf.basic.impl.Dataspace;

/**
 * Builder for creating new {@link Dataspace} objects.
 *
 */
public interface IDataspaceBuilder extends IBuilder<IDataspace> {
    
    /**
     * Sets the dimensions of a simple dataspace.
     * 
     * @param dims The dimensions of a simple dataspace.
     * 
     * @return The builder.
     */
    public IDataspaceBuilder setSimpleDims(long[] dims);
    
    /**
     * Sets the maximum dimensions of a dataspace.
     * 
     * @param maxdims The maximum dimensions of a simple dataspace.
     * 
     * @return The builder.
     */
    public IDataspaceBuilder setSimpleMaxDims(long[] maxdims);
    
    
}
