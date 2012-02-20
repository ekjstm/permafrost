/**
 *
 */
package permafrost.hdf.basic;

import permafrost.hdf.basic.impl.IDisposable;

/**
 * TODO add type documentation
 *
 */
public interface IHResource extends IDisposable {
    public String getLocalName();
    public String getPath();
}
