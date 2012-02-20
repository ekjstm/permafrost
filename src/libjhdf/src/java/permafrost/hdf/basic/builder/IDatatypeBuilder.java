/**
 *
 */
package permafrost.hdf.basic.builder;

import java.nio.ByteOrder;
import java.util.List;

import javolution.io.Struct;
import permafrost.hdf.basic.IDatatype;
import permafrost.hdf.libhdf.DatatypeClassType;
import permafrost.hdf.libhdf.DatatypeSigningType;

/**
 * Builder for creating new {@link IDatatype} objects.
 *
 */
public interface IDatatypeBuilder extends IBuilder<IDatatype> {
    
    /**
     * Indicates how integral types should represent sign data.
     * 
     * @param signing How integral types should represent sign data.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder signing(DatatypeSigningType signing);
    
    /**
     * Indicates the size of the type, in bytes.
     * 
     * @param bytes The size of the type, in bytes.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder size(int bytes);
    
    /**
     * Indicates the byte order of the type.
     * 
     * @param order The byte order of the type.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder byteOrder(ByteOrder order);
    
    /**
     * Indicates the class of the datatype.
     * 
     * @param type The class of the datatype.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder classType(DatatypeClassType type);
    
    /**
     * Sets the builder for a signed, 8-bit integer.
     *   
     * @return This builder.
     */
    public IDatatypeBuilder setInt8();
    
    /**
     * Sets the builder for a signed, 16-bit integer.
     *   
     * @return This builder.
     */
    public IDatatypeBuilder setInt16();
    
    /**
     * Sets the builder for a signed, 32-bit integer.
     *   
     * @return This builder.
     */
    public IDatatypeBuilder setInt32();
    
    /**
     * Sets the builder for a signed, 64-bit integer.
     *   
     * @return This builder.
     */
    public IDatatypeBuilder setInt64();
    
    /**
     * Sets the builder for a 32-bit float.
     *   
     * @return This builder.
     */
    public IDatatypeBuilder setFloat32();
    
    /**
     * Sets the builder for a 64-bit float.
     *   
     * @return This builder.
     */
    public IDatatypeBuilder setFloat64();
    
    /**
     * Sets the builder using an example javolution {@link Struct}.
     * 
     * @param struct The example object for the datatype.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder setStruct(Struct struct);
    
    
    /**
     * Sets the builder for an 8-bit enum.
     * 
     * @param values The values of the enum.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder setEnum8(Enum<?>[] values);
    
    /**
     * Sets the builder for an 16-bit enum.
     * 
     * @param values The values of the enum.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder setEnum16(List<? extends Enum<?>> values);
    
    /**
     * Sets the builder for an 32-bit enum.
     * 
     * @param values The values of the enum.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder setEnum32(List<? extends Enum<?>> values);
    
    /**
     * Sets the builder for an 64-bit enum.
     * 
     * @param values The values of the enum.
     * 
     * @return This builder.
     */
    public IDatatypeBuilder setEnum64(List<? extends Enum<?>> values);
    
}
