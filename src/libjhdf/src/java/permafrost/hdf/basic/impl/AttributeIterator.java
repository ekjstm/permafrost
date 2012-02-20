/**
 *
 */
package permafrost.hdf.basic.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import permafrost.hdf.basic.IAttribute;
import permafrost.hdf.libhdf.AttributeLibExtension;
import permafrost.hdf.libhdf.IndexType;
import permafrost.hdf.libhdf.IteratorOrderType;
import permafrost.hdf.libhdf.ObjectInfo;
import permafrost.hdf.libhdf.ObjectLib;
import permafrost.hdf.libhdf.PropertiesLib;


/**
 * TODO add type documentation
 *
 */
public class AttributeIterator implements Iterator<IAttribute> {

    /** The parent group to iterate over. */
    private final AbstractObject parent;
    
    /** The number of child attributes in the parent. */
    private final int nAtts;
    
    /** The sorting criteria for the iterator. */
    private final IndexType indexType;
    
    /** The iteration order for the iterator. */
    private final IteratorOrderType iterOrder;
    
    private int aapl_id = PropertiesLib.H5P_DEFAULT; // TODO use an accessor.
    private int lapl_id = PropertiesLib.H5P_DEFAULT;
    
    
    /** The current index of the iterator. (Returned by nextIndex()) */
    private int idx;
    
    
    
    /**
     * Creates a new AttributeIterator object.
     *
     */
    public AttributeIterator(AbstractObject parent) {
        super();
        this.parent = parent;
        this.indexType  = IndexType.H5_INDEX_NAME;
        this.iterOrder = IteratorOrderType.H5_ITER_INC;
        ObjectInfo oinfo = new ObjectInfo();
        int status = ObjectLib.H5Oget_info(this.parent.getHId(), oinfo);
        assert status >= 0 : "Untrapped error in native code.";
        this.nAtts = (int) oinfo.getNum_attrs();
        this.idx = 0;
        oinfo.delete();
    }



    /* (non-Javadoc)
     * @see java.util.ListIterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return (this.nAtts > 0 && this.idx < this.nAtts); 
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#hasPrevious()
     */
    public boolean hasPrevious() {
        return (this.nAtts > 0 && this.idx > 0);
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#next()
     */
    @Override
    public Attribute next() {
        if (!this.hasNext()) throw new NoSuchElementException();
        String[] name = new String[1];
        int hid = AttributeLibExtension.JHAopen_by_idx(
                this.parent.getHId(), 
                ".", 
                this.indexType, 
                this.iterOrder,
                this.idx,
                this.aapl_id, 
                this.lapl_id,
                name
        );       
        assert hid >= 0 : "Untrapped error in native code.";
        String[] tokens = name[0].split("/");
        Attribute next = new Attribute(hid, tokens[tokens.length-1], this.parent); 
        this.idx ++;
        return (next);
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#nextIndex()
     */
    public int nextIndex() {
        return (this.idx);
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#previous()
     */
    public Attribute previous() {
       if (!this.hasPrevious()) throw new NoSuchElementException();
        this.idx--;
        if (!this.hasNext()) throw new NoSuchElementException();
        String[] name = new String[1];
        int hid = AttributeLibExtension.JHAopen_by_idx(
                this.parent.getHId(), 
                ".", 
                this.indexType, 
                this.iterOrder,
                this.idx,
                this.aapl_id, 
                this.lapl_id,
                name
        );       
        assert hid >= 0 : "Untrapped error in native code.";
        String[] tokens = name[0].split("/");
        Attribute next = new Attribute(hid, tokens[tokens.length-1], this.parent);        
        return (next);
        
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#previousIndex()
     */
    public int previousIndex() {
        return (this.hasPrevious() ? this.idx-1 : -1);
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#remove()
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation not implemented.");
    }


}
