/**
 * 
 */
package permafrost.hdf.libhdf;


/**
 *
 */
public class GroupHandler {

	
	/**
	 * 
	 */
	private GroupHandler() {
		super();		
	}
	
	public static int runHandler(ListingCallbackHandler handler, int gid, String name) {
		return (GroupHandler.doRunHandler(handler, "handle", gid, name));
	}

	private final static native int doRunHandler(Object handler, String methodName, int locId, String groupName);
}
