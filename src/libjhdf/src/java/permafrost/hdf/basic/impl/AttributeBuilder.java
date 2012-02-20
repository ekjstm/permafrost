/**
 *
 */
package permafrost.hdf.basic.impl;

import permafrost.hdf.basic.IAttribute;
import permafrost.hdf.basic.IDatatype;
import permafrost.hdf.basic.builder.IAttributeBuilder;
import permafrost.hdf.basic.impl.types.Datatype;
import permafrost.hdf.basic.impl.types.DatatypeFactory;

/**
 * Implementation of {@link IAttributeBuilder}.
 *
 */
public class AttributeBuilder extends AbstractBuilder<IAttribute> implements
        IAttributeBuilder {
    
    private Datatype datatype = DatatypeFactory.getInstance().newBEInt8();
    private long[] extents = {10l};
    private long[] maxExtents = {10l};
    private boolean extentsSet = false;
    private boolean maxExtentsSet = false;

    /**
     * Creates a new AttributeBuilder object.
     *
     */
    public AttributeBuilder() {
        super();
    }

    /**
     * Creates a new AttributeBuilder object.
     *
     * @param parent
     */
    public AttributeBuilder(AbstractObject parent) {
        super(parent);        
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IAttributeBuilder#extents(long[])
     */
    @Override
    public AttributeBuilder extents(long[] xExtents) {
        if (this.extents.length != xExtents.length) {
            this.extents = new long[xExtents.length];
        }
        System.arraycopy(xExtents, 0, this.extents, 0, xExtents.length);
        
        if (!this.maxExtentsSet) {
            if (this.maxExtents.length != this.extents.length) {
                this.maxExtents = new long[this.extents.length];
            }
            System.arraycopy(this.extents, 0, this.maxExtents, 0, this.extents.length);
        }
        this.extentsSet = true;
        
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IAttributeBuilder#maxExtents(long[])
     */
    @Override
    public AttributeBuilder maxExtents(long[] xmaxExtents) {        
        if (this.maxExtents.length != xmaxExtents.length) {
            this.maxExtents = new long[xmaxExtents.length];
        }
        System.arraycopy(xmaxExtents, 0, this.maxExtents, 0, xmaxExtents.length);
        
        if (!this.extentsSet) {
            if (this.extents.length != this.maxExtents.length) {
                this.extents = new long[this.maxExtents.length];
            }
            System.arraycopy(this.maxExtents, 0, this.extents, 0, this.maxExtents.length);
        }
        this.maxExtentsSet = true;
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#build()
     */
    @Override
    public Attribute build() {
        if (!this.isValid()) {
            throw new IllegalStateException("The builder is not in a valid state.");
        }
              
        Dataspace dSpace = Dataspace.create(this.extents, this.maxExtents);
        
        PropertyList acpl = PropertyList.Default;
        PropertyList aapl = PropertyList.Default;       

        Attribute att = Attribute.create(
                this.parent, 
                this.name, 
                this.datatype, 
                dSpace, 
                acpl, 
                aapl
        );
        return (att);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#name(java.lang.String)
     */
    @Override
    public AttributeBuilder setName(String localName) {
        super.setName(localName);
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.impl.AbstractBuilder#valid()
     */
    @Override
    public boolean isValid() {
        if (!super.isValid()) return (false);
        return (this.extents.length == this.maxExtents.length && this.datatype != null);
        
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IAttributeBuilder#datatype(permafrost.hdf.basic.IDatatype)
     */
    @Override
    public AttributeBuilder datatype(IDatatype type) {
        this.datatype = (Datatype) type;
        return (this);
    }
    
    

}
