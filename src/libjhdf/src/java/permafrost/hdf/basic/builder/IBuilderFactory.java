/**
 *
 */
package permafrost.hdf.basic.builder;

/**
 * Runtime factory for builder classes.
 *
 */
public interface IBuilderFactory {

    /**
     * Creates a new file builder.
     * 
     * @return A new file builder.
     */
    public IFileBuilder newFileBuilder();
    
}
