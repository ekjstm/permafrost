/**
 *
 */
package permafrost.hdf.basic.builder;

/**
 * Interface implemented by abstract builders.
 *
 */
public interface IBuilder<T> {
    
    
    /**
     * Returns true, if the current builder parameters are valid.
     * 
     * @return True, if the current builder parameters are valid; otherwise, False.
     */
    public boolean isValid();    
    
    /**
     * Creates an object based on the current builder parameters.
     * 
     * @return A new object based on the current builder parameters.
     */
    public T build();
}
