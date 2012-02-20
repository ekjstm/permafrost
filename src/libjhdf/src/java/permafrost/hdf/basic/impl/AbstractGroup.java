/**
 *
 */
package permafrost.hdf.basic.impl;

import java.util.Iterator;

import permafrost.hdf.basic.IGroup;
import permafrost.hdf.basic.IHObject;
import permafrost.hdf.basic.builder.IGroupBuilder;
import permafrost.hdf.libhdf.ObjectInfo;
import permafrost.hdf.libhdf.ObjectLib;
import permafrost.hdf.libhdf.ObjectType;
import permafrost.hdf.libhdf.PropertiesLib;

/**
 * Abstract base class for object groups.
 *
 */
public class AbstractGroup extends AbstractObject implements IGroup {

    /**
     * Creates a new AbstractGroup object.
     *
     * @param hid The native resource id.
     */
    protected AbstractGroup(int hid) {
       super(hid);
    }

    /**
     * Creates a new AbstractGroup object.
     *
     * @param hid The native resource id.
     * @param name The name of the group. 
     */
    public AbstractGroup(int hid, String name) {
        super(hid, name);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IGroup#getChild(java.lang.String)
     */
    @Override
    public IHObject getChild(String name) {
        assert this.isInitialized() : "Native reference not initialized";
        ObjectInfo oinfo = new ObjectInfo();
        int status = ObjectLib.H5Oget_info_by_name(this.hid, name, oinfo, PropertiesLib.H5P_DEFAULT);
        assert status >= 0 : "Untrapped error in native code.";
        ObjectType type = oinfo.getType();
        switch (type) {
        case H5O_TYPE_GROUP:
            return Group.open(this.hid, name);
        case H5O_TYPE_DATASET:    
            return Dataset.open(this, name);
        case H5O_TYPE_NAMED_DATATYPE:
        default:
            String err = String.format(
                    "%s '%s/%s'", 
                    "Unable to determine type of child object at", 
                    this.getPath(),
                    name
            );
            throw new IllegalStateException(err);
        }
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IGroup#getChildren()
     */
    @Override
    public Iterator<IHObject> getChildren() {
        return (new GroupIterator(this));
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IGroup#builder()
     */
    @Override
    public IGroupBuilder groupBuilder() {
        return (new GroupBuilder(this));
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IGroup#iterator()
     */
    @Override
    public Iterator<IHObject> iterator() {
       return (new GroupIterator(this));
    }

}
