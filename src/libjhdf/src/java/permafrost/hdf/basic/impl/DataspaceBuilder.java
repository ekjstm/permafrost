/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.basic.IDataspace;
import permafrost.hdf.basic.builder.IDataspaceBuilder;

/**
 * Implementation of {@link IDataspaceBuilder}.
 *
 */
public class DataspaceBuilder extends AbstractBuilder<IDataspace> implements IDataspaceBuilder {

    
    private long[] dims;
    private long[] maxDims;
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#build()
     */
    @Override
    public Dataspace build() {
        if (!this.isValid()) {
            throw new IllegalStateException("The builder is not in a valid state.");
        }
        Dataspace space = Dataspace.create(this.dims, this.maxDims);
        return (space);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#name(java.lang.String)
     */
    @Override
    public  DataspaceBuilder setName(String name) {
        super.setName(name);
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDataspaceBuilder#setSimpleDims(long[])
     */
    @Override
    public DataspaceBuilder setSimpleDims(long[] xdims) {
        this.dims = new long[xdims.length];
        System.arraycopy(xdims, 0, this.dims, 0, this.dims.length);
        if (this.maxDims == null) {
            this.maxDims = new long[xdims.length];
            System.arraycopy(xdims, 0, this.maxDims, 0, this.maxDims.length);
        }
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDataspaceBuilder#setSimpleMaxDims(long[])
     */
    @Override
    public DataspaceBuilder setSimpleMaxDims(long[] xMaxDims) {
        this.maxDims = new long[xMaxDims.length];
        System.arraycopy(xMaxDims, 0, this.maxDims, 0, this.maxDims.length);
        if (this.dims == null) {
            this.dims = new long[xMaxDims.length];
            System.arraycopy(xMaxDims, 0, this.dims, 0, this.dims.length);
        }
        return (this);
    }
    
    /**
     * Gets the rank of the dataspace.
     * 
     * @return The number of dimensions in the dataspace.
     */
    public int getRank() {
        if (this.dims != null) {
            return (this.dims.length);
        } else {
            return 0;
        }
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.impl.AbstractBuilder#valid()
     */
    @Override
    public boolean isValid() {
        boolean valid = true;
        // If parent is not null, name must be set
        valid = valid ? (this.parent == null) || (this.name != null && !this.name.isEmpty()) : valid;
        // dims cardinality > 0
        valid = valid ? this.dims != null && this.dims.length > 0 : valid;
        // Maxdims same cardinality as dims
        valid = valid ? this.maxDims != null &&
                this.maxDims.length > 0 &&
                this.dims.length == this.maxDims.length 
                : valid;
        return (valid);
    }

    
    
    
}
