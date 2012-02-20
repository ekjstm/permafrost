/**
 * 
 */
package permafrost.hdf.libhdf;


/**
 *
 */
public class AttributeHandler {
	
	/**
	 * 
	 */
	private AttributeHandler() {
		super();		
	}
	
	public static int runHandler(ListingCallbackHandler handler, int gid) {
		return (AttributeHandler.doRunHandler(handler, "handle", gid));
	}

	private final static native int doRunHandler(Object handler, String methodName, int locId);
}
