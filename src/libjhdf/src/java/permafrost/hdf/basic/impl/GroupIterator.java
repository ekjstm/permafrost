/**
 *
 */
package permafrost.hdf.basic.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import permafrost.hdf.basic.IHObject;
import permafrost.hdf.libhdf.GroupInfo;
import permafrost.hdf.libhdf.GroupLib;
import permafrost.hdf.libhdf.IdentifierType;
import permafrost.hdf.libhdf.IndexType;
import permafrost.hdf.libhdf.IteratorOrderType;
import permafrost.hdf.libhdf.ObjectLibExtension;
import permafrost.hdf.libhdf.PropertiesLib;

/**
 * Iterator for collections of Groups.
 *
 */
public class GroupIterator implements Iterator<IHObject> {

    /** The parent group to iterate over. */
    private final AbstractGroup parent;
    
    /** The number of child groups in the parent. */
    private final int nGroups;
    
    /** The sorting criteria for the iterator. */
    private final IndexType indexType;
    
    /** The iteration order for the iterator. */
    private final IteratorOrderType iterOrder;
    
    /** The link accessor property list. */
    private int gaplId = PropertiesLib.H5P_DEFAULT; // TODO use a link accessor object.
    
    /** The current index of the iterator. (Returned by nextIndex()) */
    private int idx;
    
    
    
    
    /**
     * Creates a new GroupIterator object.
     *
     * @param parent The parent object.
     */
    public GroupIterator(AbstractGroup parent) {
        super();
        this.parent = parent;
        this.indexType  = IndexType.H5_INDEX_NAME;
        this.iterOrder = IteratorOrderType.H5_ITER_INC;
        GroupInfo ginfo = new GroupInfo();
        int status = GroupLib.H5Gget_info(this.parent.getHId(), ginfo);
        assert status >= 0 : "Untrapped error in native code.";
        this.nGroups = (int) ginfo.getNlinks();
        this.idx = 0;
        ginfo.delete();
    }   
    

    /* (non-Javadoc)
     * @see java.util.ListIterator#next()
     */
    @Override
    public AbstractObject next() {
        if (!this.hasNext()) throw new NoSuchElementException();
        String[] name = new String[1];
        IdentifierType[] type = new IdentifierType[1];
        int hid = ObjectLibExtension.JHOopen_by_idx(
                this.parent.getHId(), 
                ".", 
                this.indexType, 
                this.iterOrder,
                this.idx, 
                this.gaplId,
                type,
                name
        );       
        assert hid >= 0 : "Untrapped error in native code.";
        String[] tokens = name[0].split("/");
        AbstractObject next = AbstractObject.newObject(hid, tokens[tokens.length-1], type[0]); 
        this.idx ++;
        return (next);
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#previous()
     */
    public AbstractObject previous() {
        if (!this.hasPrevious()) throw new NoSuchElementException();
        this.idx--;
        String[] name = new String[1];
        IdentifierType[] type = new IdentifierType[1];
        int hid = ObjectLibExtension.JHOopen_by_idx(
                this.parent.getHId(), 
                ".", 
                this.indexType, 
                this.iterOrder,
                this.idx, 
                this.gaplId,
                type,
                name
        );
        assert hid >= 0 : "Untrapped error in native code.";
        String[] tokens = name[0].split("/");
        AbstractObject next = AbstractObject.newObject(hid, tokens[tokens.length-1], type[0]);       
        return (next);
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#remove()
     */
    @Override
    public void remove() {
       throw new UnsupportedOperationException("Operation not implemented.");
    }
    
    /* (non-Javadoc)
     * @see java.util.ListIterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return (this.nGroups > 0 && this.idx < this.nGroups); 
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#hasPrevious()
     */
    public boolean hasPrevious() {
        return (this.nGroups > 0 && this.idx > 0); 
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#nextIndex()
     */
    public int nextIndex() {
        return (this.idx);
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#previousIndex()
     */
    public int previousIndex() {
        return (this.hasPrevious() ? this.idx-1 : -1);
    }

}
