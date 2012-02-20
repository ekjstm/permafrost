/**
 *
 */
package permafrost.hdf.basic.impl.types;

/**
 * Implementation of compound, heterogeneous datatypes. 
 *
 */
public class StructType extends Datatype {

    /**
     * Creates a new StructType object.
     *
     * @param hid The native resource id.
     */
    public StructType(int hid) {
        super(hid);       
    }

    /**
     * Creates a new StructType object.
     *
     * @param hid The native resource id.
     * @param name The name of the group. 
     */
    public StructType(int hid, String name) {
        super(hid, name);        
    }
    
    

}
