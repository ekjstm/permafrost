/**
 *
 */
package permafrost.hdf.basic;

import permafrost.hdf.libhdf.DatatypeClassType;

/**
 * Represents data types.
 *
 */
public interface IDatatype {

    public DatatypeClassType getClassType();
    
    /**
     * Gets the size of the Datatype in bytes.
     * 
     * @return Size of the Datatype in bytes.
     */
    public long getSize();   
        
}
