/**
 *
 */
package permafrost.hdf.basic.impl.types;

import permafrost.hdf.libhdf.DatatypeLib;
import permafrost.hdf.libhdf.DatatypeSigningType;

/**
 * Implementation of integer datatypes. 
 *
 */
public class IntegerType extends AtomicType {

    /**
     * Creates a new IntegerType object.
     *
     * @param hid
     */
    public IntegerType(int hid) {
        super(hid);
    }

    /**
     * Creates a new IntegerType object.
     *
     * @param hid
     * @param name
     */
    public IntegerType(int hid, String name) {
        super(hid, name);       
    }
    
    public DatatypeSigningType getSigning() {
        assert this.isInitialized() : "Native reference not initialized";
        DatatypeSigningType type = DatatypeLib.H5Tget_sign(this.hid);
        assert type.swigValue() >= 0 : "Untrapped error in native code.";
        return (type);
    }
    
    public long getPrecision() {
        assert this.isInitialized() : "Native reference not initialized";
        long bits = DatatypeLib.H5Tget_precision(this.hid);
        assert bits >= 0 : "Untrapped error in native code.";
        return (bits);
    }

}
