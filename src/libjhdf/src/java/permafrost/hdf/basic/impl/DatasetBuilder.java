/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.basic.IDataset;
import permafrost.hdf.basic.builder.IDatasetBuilder;
import permafrost.hdf.basic.builder.IDatatypeBuilder;
import permafrost.hdf.basic.impl.types.Datatype;
import permafrost.hdf.libhdf.PropertiesLib;

/**
 * Implementation of {@link IDatasetBuilder}.
 *
 */
public class DatasetBuilder extends AbstractBuilder<IDataset> implements IDatasetBuilder {

    private DataspaceBuilder spaceBldr = new DataspaceBuilder();
    
    private DatatypeBuilder typeBldr = new DatatypeBuilder();
    
    private long[] chunkSize;    
    private boolean compress;
    
    /**
     * Creates a new DatasetBuilder object.
     *
     */
    public DatasetBuilder() {
        super();        
    }

    /**
     * Creates a new DatasetBuilder object.
     *
     * @param parent
     */
    public DatasetBuilder(AbstractGroup parent) {
        super(parent);       
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatasetBuilder#getDataspaceBuilder()
     */
    @Override
    public DataspaceBuilder getDataspaceBuilder() {
        return (this.spaceBldr);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatasetBuilder#getDatatypeBuilder()
     */
    @Override
    public IDatatypeBuilder getDatatypeBuilder() {
        return (this.typeBldr);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatasetBuilder#setChunkSize(long[])
     */
    @Override
    public DatasetBuilder setChunkSize(long[] chunk) {
        this.chunkSize = new long[chunk.length];
        System.arraycopy(chunk, 0, this.chunkSize, 0, this.chunkSize.length);
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatasetBuilder#setCompression(boolean)
     */
    @Override
    public DatasetBuilder setCompression(boolean compress) {
        this.compress = compress;
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#build()
     */
    @Override
    public Dataset build() {
        if (!this.isValid()) {
            throw new IllegalStateException("The builder is not in a valid state.");
        }
        Dataspace space = this.spaceBldr.build();
        Datatype datatype = this.typeBldr.build();
        PropertyList lcpl = PropertyList.Default;
        PropertyList dcpl = PropertyList.Default;
        PropertyList dapl = PropertyList.Default;
        if (this.chunkSize != null) {
            int dcplId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
            assert dcplId > 0 : "Untrapped error in native code.";
            dcpl = new PropertyList(dcplId);    
            int status = PropertiesLib.H5Pset_chunk(dcplId, this.chunkSize.length, this.chunkSize);
            assert status >= 0 : "Untrapped error in native code.";
        }
        if (this.compress) {
            int status = PropertiesLib.H5Pset_shuffle(dcpl.getHId());
            assert status >= 0 : "Untrapped error in native code.";
            status = PropertiesLib.H5Pset_deflate(dcpl.getHId(), 9);
            assert status >= 0 : "Untrapped error in native code.";
        }
        
        Dataset set = Dataset.create(
                (AbstractGroup) this.parent, 
                this.name, 
                space, 
                datatype,
                lcpl, 
                dcpl, 
                dapl
        );
        return (set);
    }

   

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#name(java.lang.String)
     */
    @Override
    public DatasetBuilder setName(String name) {
        super.setName(name);
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#valid()
     */
    @Override
    public boolean isValid() {
        boolean valid = super.isValid();
        valid = valid ? this.spaceBldr.isValid() : valid;
        valid = valid ? this.typeBldr.isValid() : valid;
        valid = valid ? this.chunkSize == null || 
                (this.chunkSize != null && this.spaceBldr.getRank() == this.chunkSize.length) 
                : valid;
        return (valid);
    }

}
