/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.basic.builder.IBuilderFactory;
import permafrost.hdf.basic.builder.IFileBuilder;

/**
 * Factory class for builder objects.
 *
 */
public class BuilderFactory implements IBuilderFactory {

    /**
     * Creates a new BuilderFactory object.
     *
     */
    public BuilderFactory() {
       super();
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilderFactory#newFileBuilder()
     */
    @Override
    public IFileBuilder newFileBuilder() {
       return (new FileBuilder());
    }

}
