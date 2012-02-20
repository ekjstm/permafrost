/**
 *
 */
package permafrost.hdf.basic.impl.types;

import permafrost.hdf.libhdf.DatatypeLib;

/**
 * Implementation of floating point datatypes. 
 *
 */
public class FloatType extends AtomicType {

    /**
     * Creates a new FloatType object.
     *
     * @param hid
     */
    public FloatType(int hid) {
        super(hid);        
    }

    /**
     * Creates a new FloatType object.
     *
     * @param hid
     * @param name
     */
    public FloatType(int hid, String name) {
        super(hid, name);       
    }

    /**
     * Gets the precision of the Datatype in bits.
     * 
     * @return The precision of the Datatype in bits.
     */
    public long getPrecision() {
        assert this.isInitialized() : "Native reference not initialized";
        long bits = DatatypeLib.H5Tget_precision(this.hid);
        assert bits >= 0 : "Untrapped error in native code.";
        return (bits);
    }
}
