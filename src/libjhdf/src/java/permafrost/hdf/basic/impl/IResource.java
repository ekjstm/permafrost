/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.basic.IHResource;

/**
 * Objects representing HDF5 resources should implement this interface.
 *
 */
interface IResource extends IDisposable, IHResource {
    
    /** 
     * Gets the object id of the HDF5 object. 
     *
     * @return The object id of the HDF5 object. 
     */
    public int getHId();
}
