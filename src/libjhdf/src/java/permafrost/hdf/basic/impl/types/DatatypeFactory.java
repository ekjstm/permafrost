/**
 *
 */
package permafrost.hdf.basic.impl.types;

import permafrost.hdf.basic.IDatatypeFactory;
import permafrost.hdf.libhdf.DatatypeClassType;
import permafrost.hdf.libhdf.DatatypeLib;

/**
 * TODO add type documentation
 *
 */
public class DatatypeFactory implements IDatatypeFactory {

    private static DatatypeFactory instance;
    
    
    /**
     * Creates a new DatatypeFactory object.
     *
     */
    private DatatypeFactory() {
       super();
    }

    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEFloat32()
     */
    @Override
    public FloatType newBEFloat32() {
        int nid = DatatypeLib.getH5T_IEEE_F32BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyFloatType(nid));
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEFloat64()
     */
    @Override
    public FloatType newBEFloat64() {
        int nid = DatatypeLib.getH5T_IEEE_F64BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyFloatType(nid));
    }
    
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEInt16()
     */
    @Override
    public IntegerType newBEInt16() {
        int nid = DatatypeLib.getH5T_STD_I16BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEInt32()
     */
    @Override
    public IntegerType newBEInt32() {
        int nid = DatatypeLib.getH5T_STD_I32BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }
        

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEInt64()
     */
    @Override
    public IntegerType newBEInt64() {
        int nid = DatatypeLib.getH5T_STD_I64BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEInt8()
     */
    @Override
    public IntegerType newBEInt8() {
        int nid = DatatypeLib.getH5T_STD_I8BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEUInt16()
     */
    @Override
    public IntegerType newBEUInt16() {
        int nid = DatatypeLib.getH5T_STD_U16BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }
    

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEUInt32()
     */
    @Override
    public IntegerType newBEUInt32() {
        int nid = DatatypeLib.getH5T_STD_U32BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newBEUInt8()
     */
    @Override
    public IntegerType newBEUInt8() {
        int nid = DatatypeLib.getH5T_STD_U8BE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }
    
    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEFloat32()
     */
    @Override
    public FloatType newLEFloat32() {
        int nid = DatatypeLib.getH5T_IEEE_F32LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyFloatType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEFloat64()
     */
    @Override
    public FloatType newLEFloat64() {
        int nid = DatatypeLib.getH5T_IEEE_F64LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyFloatType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEInt16()
     */
    @Override
    public IntegerType newLEInt16() {
        int nid = DatatypeLib.getH5T_STD_I16LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEInt32()
     */
    @Override
    public IntegerType newLEInt32() {
        int nid = DatatypeLib.getH5T_STD_I32LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEInt64()
     */
    @Override
    public IntegerType newLEInt64() {
        int nid = DatatypeLib.getH5T_STD_I64LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEInt8()
     */
    @Override
    public IntegerType newLEInt8() {
        int nid = DatatypeLib.getH5T_STD_I8LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEUInt16()
     */
    @Override
    public IntegerType newLEUInt16() {
        int nid = DatatypeLib.getH5T_STD_U16LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEUInt8()
     */
    @Override
    public IntegerType newLEUInt8() {
        int nid = DatatypeLib.getH5T_STD_U8LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newLEUnit32()
     */
    @Override
    public IntegerType newLEUInt32() {
        int nid = DatatypeLib.getH5T_STD_U32LE();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newNativeBool8()
     */
    @Override
    public IntegerType newNativeBool8() {
        int nid = DatatypeLib.getH5T_NATIVE_B8();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newNativeInt16()
     */
    @Override
    public IntegerType newNativeInt16() {
        int nid = DatatypeLib.getH5T_NATIVE_INT16();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newNativeInt32()
     */
    @Override
    public IntegerType newNativeInt32() {
        int nid = DatatypeLib.getH5T_NATIVE_INT32();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /*
     * (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#getNativeInt8()
     */
    @Override
    public IntegerType newNativeInt8() {
        int nid = DatatypeLib.getH5T_NATIVE_INT8();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newNativeUInt16()
     */
    @Override
    public IntegerType newNativeUInt16() {
        int nid = DatatypeLib.getH5T_NATIVE_UINT16();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newNativeUInt32()
     */
    @Override
    public IntegerType newNativeUInt32() {
        int nid = DatatypeLib.getH5T_NATIVE_UINT32();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDatatypeFactory#newNativeUInt8()
     */
    @Override
    public IntegerType newNativeUInt8() {
        int nid = DatatypeLib.getH5T_NATIVE_UINT8();
        assert nid > 0 : "Untrapped error in native code.";
        return (this.copyIntegerType(nid));
    }


    public Datatype newObject(int did) {
        DatatypeClassType type = DatatypeLib.H5Tget_class(did);
        switch (type) {
        case H5T_INTEGER:
            return (new IntegerType(did));
        case H5T_FLOAT:
            return (new FloatType(did));
        case H5T_COMPOUND:
            return (new StructType(did));
        case H5T_ENUM:
            return (new EnumType(did));
        default:
            throw new UnsupportedOperationException("unsupported type for new datatype: " + type);    
        }       
    }


    public Datatype newObject(int did, String localName) {
        DatatypeClassType type = DatatypeLib.H5Tget_class(did);
        switch (type) {
        case H5T_INTEGER:
            return (new IntegerType(did, localName));
        case H5T_FLOAT:
            return (new FloatType(did, localName));
        case H5T_COMPOUND:
            return (new StructType(did, localName));
        case H5T_ENUM:
            return (new EnumType(did, localName));
        default:
            throw new UnsupportedOperationException("unsupported type for new datatype: " + type);    
        }       
    }


    /**
     * Copies the given datatype and creates a new FloatType object.
     * 
     * @param nid Native reference to the datatype to copy.
     * 
     * @return New FloatType object associated with a copy of the given datatype.
     */
    private FloatType copyFloatType(int nid) {
        int did = DatatypeLib.H5Tcopy(nid);
        assert did > 0 : "Untrapped error in native code.";
        FloatType type = new FloatType(did);        
        return (type);
    }


    /**
     * Copies the given datatype and creates a new IntegerType object.
     * 
     * @param nid Native reference to the datatype to copy.
     * 
     * @return New IntegerType object associated with a copy of the given datatype.
     */
    private IntegerType copyIntegerType(int nid) {
        int did = DatatypeLib.H5Tcopy(nid);
        assert did > 0 : "Untrapped error in native code.";
        IntegerType type = new IntegerType(did);        
        return (type);
    }


    public static DatatypeFactory getInstance() {
        if (DatatypeFactory.instance == null) {
            DatatypeFactory.instance = new DatatypeFactory();
        }
        return (DatatypeFactory.instance);
    }
}
