/**
 *
 */
package permafrost.hdf.basic.builder;

import permafrost.hdf.basic.IAttribute;
import permafrost.hdf.basic.IDatatype;
import permafrost.hdf.libhdf.DataspaceLibConstants;

/**
 * Builder interface for creating new {@link IAttribute} objects. 
 *
 */
public interface IAttributeBuilder extends IBuilder<IAttribute> {
    
    /**
     * Sets the basic extents of the dataspace.
     * 
     * @param extents The size of each dimension of the dataspace.
     * 
     * @return This builder.
     */
    public IAttributeBuilder extents(long[] extents);
    
    /**
     * Sets the maximum extents of the dataspace.
     * 
     * @param maxExtents The maximum size of each dimension of the dataspace.
     * If the dataspace is unbounded in a dimension, the value 
     * {@link DataspaceLibConstants#H5S_UNLIMITED} should be used
     * 
     * @return This builder.
     */
    public IAttributeBuilder maxExtents(long[] maxExtents);
       
    /**
     * Sets the type of data represented by an individual element 
     * of the dataspace.
     *  
     * @param type The datatype of the dataspace.
     * 
     * @return This builder.
     */
    public IAttributeBuilder datatype(IDatatype type);
    
    
}
