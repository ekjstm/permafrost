/**
 *
 */
package permafrost.hdf.basic.impl.types;

import java.nio.ByteOrder;

import permafrost.hdf.libhdf.DatatypeByteOrderType;
import permafrost.hdf.libhdf.DatatypeLib;

/**
 * Implementation of datatypes composed of a single element. 
 *
 */
public class AtomicType extends Datatype {

    /**
     * Creates a new AtomicType object.
     *
     * @param hid
     */
    public AtomicType(int hid) {
        super(hid);        
    }

    /**
     * Creates a new AtomicType object.
     *
     * @param hid
     * @param name
     */
    public AtomicType(int hid, String name) {
        super(hid, name);        
    }
    
    /**
     * Gets the byte order of the data type.
     * 
     * @return The byte order of the datatype.
     */
    public DatatypeByteOrderType getByteOrderType() {
        assert this.isInitialized() : "Native reference not initialized";
        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(this.hid);
        assert order.swigValue() >= 0 : "Untrapped error in native code.";
        return (order);
    }
    
    /**
     * Gets the byte order of the data type.
     * 
     * @return The byte order of the datatype.
     */
    public ByteOrder getByteOrder() {
        DatatypeByteOrderType order = this.getByteOrderType();
        switch (order) {
        case H5T_ORDER_BE:
            return (ByteOrder.BIG_ENDIAN);
        case H5T_ORDER_LE:
            return (ByteOrder.LITTLE_ENDIAN);
        default:
            throw new IllegalStateException("Byte order is not big or little endian.");
        }
    }

    
}
