/**
 *
 */
package permafrost.hdf.basic.builder;

import permafrost.hdf.basic.IDataset;
import permafrost.hdf.basic.impl.Dataset;

/**
 * Builder for creating new {@link Dataset} objects.
 *
 */
public interface IDatasetBuilder extends IObjectBuilder<IDataset> {

    /**
     * Gets a builder for the dataspace.
     * 
     * @return Builder for the dataspace associated with the dataset.
     */
    public IDataspaceBuilder getDataspaceBuilder();
    
    /**
     * Gets a builder for the datatype.
     * 
     * @return Builder for the datatype associated with the dataset.
     */
    public IDatatypeBuilder getDatatypeBuilder();
    
    /**
     * Sets the chunk size of the dataset.
     * 
     * @param chunk The size of a chunk.
     * 
     * @return The builder.
     */
    public IDatasetBuilder setChunkSize(long[] chunk);
    
    /**
     * Enables compression of the dataset.
     * 
     * @return The builder.
     */
    public IDatasetBuilder setCompression(boolean compress);
    
    
}
