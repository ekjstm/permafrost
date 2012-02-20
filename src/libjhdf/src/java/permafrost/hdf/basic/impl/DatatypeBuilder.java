/**
 *
 */
package permafrost.hdf.basic.impl;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import javolution.io.Struct;
import javolution.io.Struct.Bool;
import javolution.io.Struct.Float32;
import javolution.io.Struct.Float64;
import javolution.io.Struct.Signed16;
import javolution.io.Struct.Signed32;
import javolution.io.Struct.Signed64;
import javolution.io.Struct.Signed8;
import javolution.io.Struct.Unsigned16;
import javolution.io.Struct.Unsigned32;
import javolution.io.Struct.Unsigned8;
import permafrost.hdf.basic.IDatatype;
import permafrost.hdf.basic.builder.IDatatypeBuilder;
import permafrost.hdf.basic.impl.types.Datatype;
import permafrost.hdf.basic.impl.types.DatatypeFactory;
import permafrost.hdf.basic.impl.types.EnumType;
import permafrost.hdf.basic.impl.types.FloatType;
import permafrost.hdf.basic.impl.types.IntegerType;
import permafrost.hdf.basic.impl.types.StructType;
import permafrost.hdf.libhdf.DatatypeByteOrderType;
import permafrost.hdf.libhdf.DatatypeClassType;
import permafrost.hdf.libhdf.DatatypeLib;
import permafrost.hdf.libhdf.DatatypeLibRTConstants;
import permafrost.hdf.libhdf.DatatypeSigningType;

/**
 * Builder for creating datatype objects.
 *
 */
public class DatatypeBuilder extends AbstractBuilder<IDatatype> implements
        IDatatypeBuilder {
    
    /**
     * Represents an individual element of a struct.
     *
     */
    private static class StructElement {
        /** The name of the struct element. */
        private final String name;        
        /** The offset of the struct element. */
        private final int offset;
        /** The data type of the struct element. */        
        private final Datatype type;
               
        /**
         * Creates a new StructElement object.
         *
         * @param name The name of the struct element.
         * @param offset The offset of the struct element.
         * @param type The data type of the struct element.
         */
        public StructElement(String name, int offset, IDatatype type) {
            super();
            this.name = name;
            this.offset = offset;
            this.type = (Datatype) type;
        }
               
        /**
         * Returns true if the element is valid.
         * 
         * @return True if the element is valid; otherwise, false.
         */
        public boolean valid() {
            boolean valid = true;
            
            valid = valid ? (this.name != null && !"".equals(this.name)) : valid;           
            valid = valid ? (this.type != null) : valid;
            
            return (valid);
        }

        /**
         * Gets the name of the struct element.
         * 
         * @return The name of the struct element.
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the offset of the struct element.
         * 
         * @return The offset of the struct element.
         */
        public int getOffset() {
            return offset;
        }

        /**
         * Gets the data type of the struct element.
         * 
         * @return The data type of the struct element.
         */
        public Datatype getType() {
            return type;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder xStr = new StringBuilder();
            xStr.append(this.name);
            xStr.append("@");
            xStr.append(this.offset);
            return (xStr.toString());
        }
    }
    
    /** Signing type used by builder. */
    private DatatypeSigningType signing = DatatypeSigningType.H5T_SGN_2;

    /** Byte order used by builder. */
    private DatatypeByteOrderType order = DatatypeByteOrderType.H5T_ORDER_BE;
    
    /** Data type used by builder. */
    private DatatypeClassType type = DatatypeClassType.H5T_INTEGER;
    
    /** Size of type, in bits. */
    private int size = 8;
    
    /** List of struct members used by builder. */
    private List<StructElement> elements = new ArrayList<StructElement>(0);
    
    /** List os enum members used by builder. */
    private List<Enum<?>> enumElements = new ArrayList<Enum<?>>(0);
    
    /**
     * Creates a new DatatypeBuilder object.
     *
     */
    public DatatypeBuilder() {
        super();
    }

    /**
     * Creates a new DatatypeBuilder object.
     *
     * @param parent
     */
    public DatatypeBuilder(AbstractObject parent) {
        super(parent);        
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#byteOrder(permafrost.hdf.libhdf.DatatypeByteOrderType)
     */
    @Override
    public DatatypeBuilder byteOrder(ByteOrder xorder) {
       if (ByteOrder.BIG_ENDIAN.equals(xorder)) {
           this.order = DatatypeByteOrderType.H5T_ORDER_BE;   
       } else {
           this.order = DatatypeByteOrderType.H5T_ORDER_LE;
       }       
        
       return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#signing(permafrost.hdf.libhdf.DatatypeSigningType)
     */
    @Override
    public DatatypeBuilder signing(DatatypeSigningType xsigning) {
        this.signing = xsigning;
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#size(int)
     */
    @Override
    public DatatypeBuilder size(int bytes) {
        this.size = bytes * 8;
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#type(permafrost.hdf.libhdf.DatatypeClassType)
     */
    @Override
    public DatatypeBuilder classType(DatatypeClassType xtype) {
        this.type = xtype;
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#build()
     */
    @Override
    public Datatype build() {
        DatatypeLibRTConstants rtc = DatatypeLibRTConstants.getInstance();
        switch (this.type) {
        case H5T_INTEGER:
            switch (this.size) {
            case 8:
                switch (this.order) {
                case H5T_ORDER_BE:
                    switch (this.signing) {
                    case H5T_SGN_NONE:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_U8BE)));
                    case H5T_SGN_2:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_I8BE)));
                    default:
                        throw new IllegalArgumentException("Illegal sign type for integral datatype: " + this.signing);
                    } // End BE Int 8
                case H5T_ORDER_LE:
                    switch (this.signing) {
                    case H5T_SGN_NONE:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_U8LE)));
                    case H5T_SGN_2:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_I8LE)));
                    default:
                        throw new IllegalArgumentException("Illegal sign type for integral datatype: " + this.signing);
                    } // End LE Int 8
                default:
                    throw new IllegalArgumentException("Illegal byte order for integral datatype: " + this.order);
                } // end of case 8
            case 16:
                switch (this.order) {
                case H5T_ORDER_BE:
                    switch (this.signing) {
                    case H5T_SGN_NONE:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_U16BE)));
                    case H5T_SGN_2:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_I16BE)));
                    default:
                        throw new IllegalArgumentException("Illegal sign type for integral datatype: " + this.signing);
                    } // End BE Int 16
                case H5T_ORDER_LE:
                    switch (this.signing) {
                    case H5T_SGN_NONE:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_U16LE)));
                    case H5T_SGN_2:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_I16LE)));
                    default:
                        throw new IllegalArgumentException("Illegal sign type for integral datatype: " + this.signing);
                    } // End LE Int 16
                default:
                    throw new IllegalArgumentException("Illegal byte order for integral datatype: " + this.order);
                } // end of case 16
            case 32:
                switch (this.order) {
                case H5T_ORDER_BE:
                    switch (this.signing) {
                    case H5T_SGN_NONE:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_U32BE)));
                    case H5T_SGN_2:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_I32BE)));
                    default:
                        throw new IllegalArgumentException("Illegal sign type for integral datatype: " + this.signing);
                    } // End BE Int 32
                case H5T_ORDER_LE:
                    switch (this.signing) {
                    case H5T_SGN_NONE:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_U32LE)));
                    case H5T_SGN_2:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_I32LE)));
                    default:
                        throw new IllegalArgumentException("Illegal sign type for integral datatype: " + this.signing);
                    } // End LE Int 32
                default:
                    throw new IllegalArgumentException("Illegal byte order for integral datatype: " + this.order);
                } // end of case 32                
            case 64:
                switch (this.order) {
                case H5T_ORDER_BE:
                    switch (this.signing) {
                    case H5T_SGN_NONE:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_U64BE)));
                    case H5T_SGN_2:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_I64BE)));
                    default:
                        throw new IllegalArgumentException("Illegal sign type for integral datatype: " + this.signing);
                    } // End BE Int 64
                case H5T_ORDER_LE:
                    switch (this.signing) {
                    case H5T_SGN_NONE:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_U64LE)));
                    case H5T_SGN_2:
                        return (new IntegerType(DatatypeLib.H5Tcopy(rtc.H5T_STD_I64LE)));
                    default:
                        throw new IllegalArgumentException("Illegal sign type for integral datatype: " + this.signing);
                    } // End LE Int 64
                default:
                    throw new IllegalArgumentException("Illegal byte order for integral datatype: " + this.order);
                } // end of case 64                
                default:
                    throw new IllegalArgumentException("Illegal size for integral datatype: " + this.size);                
            } // End H5T_Integer
            
        case H5T_FLOAT:
            switch (this.size) {
            case 32:
                switch (this.order) {
                case H5T_ORDER_BE:                 
                    return (new FloatType(DatatypeLib.H5Tcopy(rtc.H5T_IEEE_F32BE)));                    
                case H5T_ORDER_LE:                    
                    return (new FloatType(DatatypeLib.H5Tcopy(rtc.H5T_IEEE_F32LE)));
                default:
                    throw new IllegalArgumentException("Illegal byte order for float datatype: " + this.order);
                } // End Float 32
            case 64:
                switch (this.order) {
                case H5T_ORDER_BE:                 
                    return (new FloatType(DatatypeLib.H5Tcopy(rtc.H5T_IEEE_F64BE)));                    
                case H5T_ORDER_LE:                    
                    return (new FloatType(DatatypeLib.H5Tcopy(rtc.H5T_IEEE_F64LE)));
                default:
                    throw new IllegalArgumentException("Illegal byte order for float datatype: " + this.order);
                } // End Float 32
            default:
                throw new IllegalArgumentException("Illegal size for float datatype: " + this.size);
            }
            
        case H5T_COMPOUND:
            return (this.defineStruct());
            
        case H5T_ENUM:
            return (this.defineEnum());
            
            default:
                throw new UnsupportedOperationException("Unsupported type for new datatype: " + this.type);
        }
        
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#name(java.lang.String)
     */
    @Override
    public DatatypeBuilder setName(String name) {
        super.setName(name);
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.impl.AbstractBuilder#valid()
     */
    @Override
    public boolean isValid() {
        boolean valid = true;
        
        valid = valid ? (this.order != null && 
                this.order != DatatypeByteOrderType.H5T_ORDER_ERROR && 
                this.order != DatatypeByteOrderType.H5T_ORDER_NONE) : valid;
        
        valid = valid ? (this.signing != null && 
                this.signing != DatatypeSigningType.H5T_SGN_ERROR &&
                this.signing != DatatypeSigningType.H5T_SGN_NONE
        ) : valid ;
        
        valid = valid ? (this.type != null &&
                this.type != DatatypeClassType.H5T_NO_CLASS &&
                this.type != DatatypeClassType.H5T_NCLASSES
        ) : valid;
        
        if (valid && this.type == DatatypeClassType.H5T_INTEGER) {
            valid = (this.size == 8 || this.size == 16 || this.size == 32 || this.size == 64);
            
        } else if (valid && this.type == DatatypeClassType.H5T_FLOAT) {
            valid = (this.size == 32 || this.size == 64);
        }
        
        return (valid);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#getFloat32()
     */
    @Override
    public DatatypeBuilder setFloat32() {
        this.size = 32;
        this.type = DatatypeClassType.H5T_FLOAT;
        this.order = this.nativeOrder();
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#getFloat64()
     */
    @Override
    public DatatypeBuilder setFloat64() {
        this.size = 64;
        this.type = DatatypeClassType.H5T_FLOAT;
        this.order = this.nativeOrder();
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#getInt16()
     */
    @Override
    public DatatypeBuilder setInt16() {
        this.size = 16;
        this.type = DatatypeClassType.H5T_INTEGER;
        this.signing = DatatypeSigningType.H5T_SGN_2;
        this.order = this.nativeOrder();
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#getInt32()
     */
    @Override
    public DatatypeBuilder setInt32() {
        this.size = 32;
        this.type = DatatypeClassType.H5T_INTEGER;
        this.signing = DatatypeSigningType.H5T_SGN_2;
        this.order = this.nativeOrder();
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#getInt64()
     */
    @Override
    public DatatypeBuilder setInt64() {
        this.size = 64;
        this.type = DatatypeClassType.H5T_INTEGER;
        this.signing = DatatypeSigningType.H5T_SGN_2;
        this.order = this.nativeOrder();
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#getInt8()
     */
    @Override
    public DatatypeBuilder setInt8() {
        this.size = 8;
        this.type = DatatypeClassType.H5T_INTEGER;
        this.signing = DatatypeSigningType.H5T_SGN_2;
        this.order = this.nativeOrder();
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#setStruct(javolution.io.Struct)
     */
    @Override
    public DatatypeBuilder setStruct(Struct struct) {        
        this.type = DatatypeClassType.H5T_COMPOUND;
        this.size = struct.size();
        
        Class<? extends Struct> klass = struct.getClass();
        ArrayList<StructElement> members = new ArrayList<StructElement>();
        try {
            this.getMembers(struct, klass, members);            
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't happen.", e);
        }        
        this.elements.addAll(members);
        
        return (this);
    }
    
    
   
    
    

    /**
     * Defines a struct data type.
     * 
     * @return New struct data type.
     */
    private StructType defineStruct() {
        int cid = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_COMPOUND, this.size);
        assert cid > 0 : "Untrapped error in native code.";
        StructType dType = new StructType(cid);
        
        for (StructElement elt : this.elements) {
            int status = DatatypeLib.H5Tinsert(
                    cid, 
                    elt.getName(),
                    elt.getOffset(), 
                    elt.getType().getHId()
            );
            assert status >= 0 : "Untrapped error in native code.";                    
        }
        
        return (dType);
    }
    
    /**
     * Converts the members of the given struct to a list of StructElements. 
     * 
     * @param struct The struct to convert.
     * @param members The list of members.
     * 
     * @throws IllegalAccessException If there is an error accessing a Struct member. 
     * (This can't happen) 
     */
    private void getMembers(
            Struct struct,        
            Class<?> klass,
            List<StructElement> members
    ) throws IllegalAccessException {
        
        if (klass.equals(Struct.class)) return;
        this.getMembers(struct, klass.getSuperclass(), members);
        
        Class<?> memberKlass = Unsigned8.class.getSuperclass();
        Field[] fields = klass.getDeclaredFields();
        for (Field field : fields) {            
            field.setAccessible(true);
            Class<?> fklass = field.getType();
            if (!memberKlass.isAssignableFrom(fklass)) continue; 
                
            String fname = field.getName();
            ByteOrder forder = struct.byteOrder();            
            
            DatatypeFactory dtf = DatatypeFactory.getInstance();
            if (Unsigned8.class.isAssignableFrom(fklass)) {                    
                int offset = ((Unsigned8) field.get(struct)).offset();
                IntegerType itype = dtf.newNativeUInt8();                
                members.add(new StructElement(fname, offset, itype));
                        
            } else if (Signed8.class.isAssignableFrom(fklass)) {
                int offset = ((Signed8) field.get(struct)).offset();
                IntegerType itype = dtf.newNativeInt8();
                members.add(new StructElement(fname, offset, itype));
                
            } else if (Bool.class.isAssignableFrom(fklass)) {
                int offset = ((Bool) field.get(struct)).offset();
                IntegerType itype = dtf.newNativeBool8();
                members.add(new StructElement(fname, offset, itype));
                
            } else if (Unsigned16.class.isAssignableFrom(fklass)) {
                int offset = ((Unsigned16) field.get(struct)).offset();
                IntegerType itype = ByteOrder.BIG_ENDIAN.equals(forder) ? dtf.newBEUInt16() : dtf.newLEUInt16();               
                members.add(new StructElement(fname, offset, itype));
                
            } else if (Signed16.class.isAssignableFrom(fklass)) {
                int offset = ((Signed16) field.get(struct)).offset();
                IntegerType itype  = ByteOrder.BIG_ENDIAN.equals(forder) ? dtf.newBEInt16() : dtf.newLEInt16();
                members.add(new StructElement(fname, offset, itype));
                
            } else if (Unsigned32.class.isAssignableFrom(fklass)) {
                int offset = ((Unsigned32) field.get(struct)).offset();
                IntegerType itype = ByteOrder.BIG_ENDIAN.equals(forder) ? dtf.newBEUInt32() : dtf.newLEUInt32();                       
                members.add(new StructElement(fname, offset, itype));
                
            } else if (Signed32.class.isAssignableFrom(fklass)) {
                int offset = ((Signed32) field.get(struct)).offset();
                IntegerType itype = ByteOrder.BIG_ENDIAN.equals(forder) ? dtf.newBEInt32() : dtf.newLEInt32();
                members.add(new StructElement(fname, offset, itype));                            
                
            } else if (Signed64.class.isAssignableFrom(fklass)) {
                int offset = ((Signed64) field.get(struct)).offset();
                IntegerType itype =  ByteOrder.BIG_ENDIAN.equals(forder) ? dtf.newBEInt64() : dtf.newLEInt64();
                members.add(new StructElement(fname, offset, itype));
                
            } else if (Float32.class.isAssignableFrom(fklass)) {
                int offset = ((Float32) field.get(struct)).offset();
                FloatType ftype = ByteOrder.BIG_ENDIAN.equals(forder) ? dtf.newBEFloat32() : dtf.newLEFloat32();                
                members.add(new StructElement(fname, offset, ftype));
                    
            } else if (Float64.class.isAssignableFrom(fklass)) {
                int offset = ((Float64) field.get(struct)).offset();
                FloatType ftype = ByteOrder.BIG_ENDIAN.equals(forder) ? dtf.newBEFloat64() : dtf.newLEFloat64();
                members.add(new StructElement(fname, offset, ftype));
                
            } else {                
                throw new UnsupportedOperationException("Unsupported struct member type: " + fklass.getSimpleName());
            }
        }
    }
    
    private DatatypeByteOrderType nativeOrder() {
        ByteOrder nativeOrder = ByteOrder.nativeOrder();
        if (ByteOrder.BIG_ENDIAN.equals(nativeOrder)) {
            return DatatypeByteOrderType.H5T_ORDER_BE;
        } else {
            return DatatypeByteOrderType.H5T_ORDER_LE;
        }
    }
           

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#setEnum16(java.util.List)
     */
    @Override
    public IDatatypeBuilder setEnum16(List<? extends Enum<?>> values) {
        this.type = DatatypeClassType.H5T_ENUM;
        this.size = 16;
        this.enumElements.clear();
        this.enumElements.addAll(values);
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#setEnum32(java.util.List)
     */
    @Override
    public IDatatypeBuilder setEnum32(List<? extends Enum<?>> values) {
        this.type = DatatypeClassType.H5T_ENUM;
        this.size = 32;
        this.enumElements.clear();
        this.enumElements.addAll(values);
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#setEnum64(java.util.List)
     */
    @Override
    public IDatatypeBuilder setEnum64(List<? extends Enum<?>> values) {
        this.type = DatatypeClassType.H5T_ENUM;
        this.size = 64;
        this.enumElements.clear();
        this.enumElements.addAll(values);
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IDatatypeBuilder#setEnum8(java.util.List)
     */
    @Override
    public IDatatypeBuilder setEnum8(Enum<?>[] values) {
        this.type = DatatypeClassType.H5T_ENUM;
        this.size = 8;
        this.enumElements.clear();
        for (Enum<?> eknum : values) {
            this.enumElements.add(eknum);
        }
        return (this);
    }
    
    /**
     * Defines an Enum data type.
     * 
     *  @return A new Enum data type.
     */
    private EnumType defineEnum() {
        int eid = DatatypeLib.H5Tenum_create(DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT8);
//        int eid = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_ENUM, this.size);
        assert eid > 0 : "Untrapped error in native code.";   
        EnumType dType = new EnumType(eid);
 
        for (Enum<?> elt : this.enumElements) {
            int status = DatatypeLib.H5Tenum_insert(eid, elt.toString(), elt.ordinal());                    
            assert status >= 0 : "Untrapped error in native code.";                    
        }
        
        return (dType);
    }
    
}
