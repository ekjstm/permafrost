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
import permafrost.hdf.basic.builder.IStructTypeBuilder;
import permafrost.hdf.basic.impl.types.Datatype;
import permafrost.hdf.basic.impl.types.IntegerType;
import permafrost.hdf.basic.impl.types.StructType;
import permafrost.hdf.libhdf.DatatypeClassType;
import permafrost.hdf.libhdf.DatatypeLib;
import permafrost.hdf.libhdf.DatatypeLibRTConstants;

/**
 * Builder class for compound struct types.
 *
 */
public class StructTypeBuilder extends AbstractBuilder<IDatatype> implements IStructTypeBuilder {
         
    
    public static class StructElement {
        private String name;        
        private int offset;
        private Datatype type;
        
        public StructElement() {
            super();
        }
        
        public StructElement(String name, int offset, IDatatype type) {
            super();
            this.name = name;
            this.offset = offset;
            this.type = (Datatype) type;
        }
        
        public StructElement name(String xname) {
            this.name = xname;
            return (this);
        }
                       
        public StructElement type(IDatatype xtype) {
            this.type = (Datatype) xtype;
            return (this);
        }
        
        public boolean valid() {
            boolean valid = true;
            
            valid = valid ? (this.name != null && !"".equals(this.name)) : valid;           
            valid = valid ? (this.type != null) : valid;
            
            return (valid);
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the offset
         */
        public int getOffset() {
            return offset;
        }

        /**
         * @return the type
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
    
    private List<StructElement> elements = new ArrayList<StructElement>(3);
        
    private int size = 0;
    
    /**
     * Creates a new StructTypeBuilder object.
     *
     */
    public StructTypeBuilder() {
       super();
      
    }

    /**
     * Creates a new StructTypeBuilder object.
     *
     * @param parent
     */
    public StructTypeBuilder(AbstractObject parent) {
        super(parent);        
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#build()
     */
    @Override
    public StructType build() {
        if (!this.isValid()) {
            throw new IllegalStateException("The builder is not in a valid state.");
        }
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

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#name(java.lang.String)
     */
    @Override
    public StructTypeBuilder setName(String name) {
        super.setName(name);
        return (this);
    }
    
    public StructTypeBuilder struct(Struct struct) {        
        this.size = struct.size();        
        
        Class<? extends Struct> klass = struct.getClass();
        ArrayList<StructElement> members = new ArrayList<StructElement>();
        try {
            this.getMembers(struct, klass, members);
            this.elements.addAll(members);
            
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't happen.", e);
        }        
        
        return (this);
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
            
            DatatypeLibRTConstants rtc = DatatypeLibRTConstants.getInstance();
            if (Unsigned8.class.isAssignableFrom(fklass)) {                    
                int offset = ((Unsigned8) field.get(struct)).offset();
                members.add(new StructElement(fname, offset, new IntegerType(rtc.H5T_NATIVE_UINT8)));
                        
            } else if (Signed8.class.isAssignableFrom(fklass)) {
                int offset = ((Signed8) field.get(struct)).offset();
                members.add(new StructElement(fname, offset, new IntegerType(rtc.H5T_NATIVE_INT8)));
                
            } else if (Bool.class.isAssignableFrom(fklass)) {
                int offset = ((Bool) field.get(struct)).offset();
                members.add(new StructElement(fname, offset, new IntegerType(rtc.H5T_NATIVE_B8)));
                
            } else if (Unsigned16.class.isAssignableFrom(fklass)) {
                int offset = ((Unsigned16) field.get(struct)).offset();
                IntegerType dtype = new IntegerType(
                        ByteOrder.BIG_ENDIAN.equals(forder) ? rtc.H5T_STD_U16BE : rtc.H5T_STD_U16LE
                );
                members.add(new StructElement(fname, offset, dtype));
                
            } else if (Signed16.class.isAssignableFrom(fklass)) {
                int offset = ((Signed16) field.get(struct)).offset();
                IntegerType dtype = new IntegerType(
                        ByteOrder.BIG_ENDIAN.equals(forder) ? rtc.H5T_STD_I16BE : rtc.H5T_STD_I16LE
                );
                members.add(new StructElement(fname, offset, dtype));
                
            } else if (Unsigned32.class.isAssignableFrom(fklass)) {
                int offset = ((Unsigned32) field.get(struct)).offset();
                IntegerType dtype = new IntegerType(
                        ByteOrder.BIG_ENDIAN.equals(forder) ? rtc.H5T_STD_U32BE : rtc.H5T_STD_U32LE
                );
                members.add(new StructElement(fname, offset, dtype));
                
            } else if (Signed32.class.isAssignableFrom(fklass)) {
                int offset = ((Signed32) field.get(struct)).offset();
                IntegerType dtype = new IntegerType(
                        ByteOrder.BIG_ENDIAN.equals(forder) ? rtc.H5T_STD_I32BE : rtc.H5T_STD_I32LE
                );
                members.add(new StructElement(fname, offset, dtype));                            
                
            } else if (Signed64.class.isAssignableFrom(fklass)) {
                int offset = ((Signed64) field.get(struct)).offset();
                IntegerType dtype = new IntegerType(
                        ByteOrder.BIG_ENDIAN.equals(forder) ? rtc.H5T_STD_I64BE : rtc.H5T_STD_I64LE
                );
                members.add(new StructElement(fname, offset, dtype));
                
            } else if (Float32.class.isAssignableFrom(fklass)) {
                    int offset = ((Float32) field.get(struct)).offset();
                    IntegerType dtype = new IntegerType(
                            ByteOrder.BIG_ENDIAN.equals(forder) ? rtc.H5T_IEEE_F32BE : rtc.H5T_IEEE_F32LE
                    );
                    members.add(new StructElement(fname, offset, dtype));
                    
            } else if (Float64.class.isAssignableFrom(fklass)) {
                int offset = ((Float64) field.get(struct)).offset();
                IntegerType dtype = new IntegerType(
                        ByteOrder.BIG_ENDIAN.equals(forder) ? rtc.H5T_IEEE_F64BE : rtc.H5T_IEEE_F64LE
                );
                members.add(new StructElement(fname, offset, dtype));
                
            } else {                
                throw new UnsupportedOperationException("Unsupported struct member type: " + fklass.getSimpleName());
            }
        }
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.impl.AbstractBuilder#valid()
     */
    @Override
    public boolean isValid() {
        boolean valid = true;
        // If parent is not null, name must be set
        valid = valid ? (this.parent == null) || (this.name != null && !this.name.isEmpty()) : valid;
        // Struct must not be empty
        valid = valid ? !this.elements.isEmpty() : valid;
        valid = valid ? this.size != 0 : valid;
        return (valid);
    }
}
