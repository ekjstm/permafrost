/**
 * 
 */
package permafrost.hdf.libhdf;

import org.apache.log4j.Logger;




/**
 * Error handler serves as a placeholder for methods than control the 
 * error callback for errors in native libhdf code. 
 */
public final class NativeErrorHandler {

    /** The event logger. */
    private static final Logger logger = Logger.getLogger(NativeErrorHandler.class);   
    
    /** Singleton instance. */
    private static volatile NativeErrorHandler INSTANCE;
    
	/**
	 * Unused
	 */
	private NativeErrorHandler() {
		super();		
	}

	/**
     * Sets the handler that will be called by the error callback.
     *  
     * <p>The handler will invoke <code>handler.fcn()</code> once for each
     * frame in the native library stack. <code>fcn</code> must have signature
     * </p>
     * <code>public void fcn (java.lang.String msg)</code>
     *  
     * <p>This method will override any other handler that may 
     * be currently installed.</p>
     *  
     * @param handler The java object that will handle the callback.
     * @param fcn The name of the method that will be invoked by the callback.
	 */
	public final static native void setLogHandler(Object handler, String fcn);
    
    /**
     * Sets the error callback to raise an exception from the native code.
     *
     * <p>This handler is the the libjdfh default handler. It is intended to 
     * reduce the burden of return value checking by raising errors using the
     * Java exception mechanism instead.</p>
     * 
     * <p>Since the exception is raised within native code. Thus, this handler 
     * has a potentially large performance impact in the event that an exception 
     * is actually raised.</p>
     */
	public native void enable();
	
	/**
	 * Sets the error callback to only log errors in the native code.
	 * 
	 * <p>This handler allows libjhdf operations to complete without raising
	 * an exception. However, errors will be logged. 
	 * </p>
	 */
	public void disable() {
	    this.disable(this);
	}
	
	/**
	 * 
	 */
	private native void disable(Object handler); 
			
    
    /**
     * Callback to log error messages from native code.
     * 
     * @param msg The error message to log.
     */
    @SuppressWarnings("unused")
    private void error(String msg) {
        logger.error(msg);
    }
    
    /**
     * Gets the NativeErrorHandler singleton instance.
     * 
     * @return The NativeErrorHandler singleton.
     */
    public static NativeErrorHandler getInstance() {
        NativeErrorHandler instance = NativeErrorHandler.INSTANCE;
        if (instance == null) {
            synchronized(NativeErrorHandler.class) {
                instance = NativeErrorHandler.INSTANCE;
                if (instance == null) {
                    instance = NativeErrorHandler.INSTANCE = new NativeErrorHandler();                   
                }
            }
        } 
        return (instance);       
    }
}
