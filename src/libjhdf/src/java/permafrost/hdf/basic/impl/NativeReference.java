/**
 *
 */
package permafrost.hdf.basic.impl;

import java.lang.ref.WeakReference;


/**
 * Reference used by {@link Cleaner} to dispose of unused resources. 
 *
 */
public abstract class NativeReference extends WeakReference<IResource> implements IDisposable{

    protected int hid;

    /**
     * Creates a new NativeReference object.
     *
     * @param referent
     */
    public NativeReference(IResource referent) {
        super(referent, Cleaner.getInstance().getRefQueue());
        this.hid = referent.getHId();
        Cleaner.getInstance().register(this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.IDisposable#dispose()
     */
    @Override
    public void dispose() {
        Cleaner.getInstance().unregister(this);
        this.doDispose();
    }
    
    /**
     * Subclasses should override this method
     * with resource specific disposal code.
     */
    protected abstract void doDispose();

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder xStr = new StringBuilder();
        xStr.append(this.getClass().getSimpleName());
        xStr.append("[");
        xStr.append(this.hid);
        xStr.append("]");
        return (xStr.toString());
    }
    
    

}
