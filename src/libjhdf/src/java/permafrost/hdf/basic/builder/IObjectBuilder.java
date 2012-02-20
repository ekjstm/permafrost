/**
 *
 */
package permafrost.hdf.basic.builder;

import permafrost.hdf.basic.IHObject;

/**
 * TODO add type documentation
 *
 */
public interface IObjectBuilder<T extends IHObject> extends IBuilder<T> {

    /**
     * Sets the name of the object to be built.
     * 
     * @param name The name of the object to be built.
     * 
     * @return This builder.
     */
    public IBuilder<T> setName(String name);
    
    /**
     * Gets the name of the object to be built.
     * 
     * @return The name of the object to be built.
     */
    public String getName();
    
}
