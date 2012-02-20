/**
 *
 */
package permafrost.hdf.basic;

/**
 * TODO add type documentation
 *
 */
public interface IDatatypeFactory {
    /**
     * Gets a new datatype for an 16-bit, signed integer in the big endian byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newBEInt16();
    
    /**
     * Gets a new datatype for an 8-bit, signed integer in the big endian byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newBEInt8();
    
    /**
     * Gets a new datatype for an 8-bit, un-signed integer in the big endian byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newBEUInt16();
    
    /**
     * Gets a new datatype for an 8-bit, un-signed integer in the big endian byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newBEUInt8();
    
    /**
     * Gets a new datatype for an 16-bit, signed integer in the little endian byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newLEInt16();
    
    /**
     * Gets a new datatype for an 8-bit, signed integer in the little endian byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newLEInt8();
    
    /**
     * Gets a new datatype for an 8-bit, un-signed integer in the little endian byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newLEUInt16();
    
    
    /**
     * Gets a new datatype for an 8-bit, un-signed integer in the little endian byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newLEUInt8();
    
    /**
     * Gets a new datatype for an 8-bit boolean in the native byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newNativeBool8();
    
    /**
     * Gets a new datatype for an 16-bit, signed integer in the native byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newNativeInt16();
    
    /**
     * Gets a new datatype for an 8-bit, signed integer in the native byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newNativeInt8();
    
    /**
     * Gets a new datatype for an 16-bit, un-signed integer in the native byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newNativeUInt16();
    
    /**
     * Gets a new datatype for an 8-bit, un-signed integer in the native byte order.
     * 
     * @return New datatype object.
     */
    public IDatatype newNativeUInt8();
    
    public IDatatype newBEInt32();
    
    public IDatatype newLEInt32();
    
    public IDatatype newNativeInt32();
    
    public IDatatype newBEUInt32();
    
    public IDatatype newLEUInt32();
    
    public IDatatype newNativeUInt32();
    
    public IDatatype newBEInt64();
    
    public IDatatype newLEInt64();
    
    public IDatatype newBEFloat32();
    
    public IDatatype newLEFloat32();
    
    public IDatatype newBEFloat64();
    
    public IDatatype newLEFloat64();
    
}
