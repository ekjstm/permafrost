/**
 * 
 */
package permafrost.hdf.libhdf;

import java.util.LinkedList;
import java.util.List;

/**
 * Handles listing callbacks by accumulating a list of names.
 */
public class NameAccumulatingHandler implements ListingCallbackHandler {

	/** List of object names. */
	private LinkedList<String> names = new LinkedList<String>();
	
	/**
	 * Creates a new AccumulatingHandler object.
	 */
	public NameAccumulatingHandler() {
		super();		
	}

	/* (non-Javadoc)
	 * @see zonule.hdf.libhdf.ListingCallbackHandler#handle(int, java.lang.String)
	 */
	public void handle(int gid, String name) {
		this.names.add(name);

	}

	/**
	 * Gets the list of object names.
	 * 
	 * @return List of object names.
	 */
	public List<String> getNames() {
		return (this.names);
	}
}
