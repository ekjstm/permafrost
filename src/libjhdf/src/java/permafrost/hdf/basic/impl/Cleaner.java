/**
 *
 */
package permafrost.hdf.basic.impl;

import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;


/**
 * Cleaner disposes of IDisposables who have gone out of scope.
 *
 */
public final class Cleaner implements Runnable {
    
    /** The event logger. */
    private static final Logger logger = Logger.getLogger(Cleaner.class);
    
    /** Singleton instance. */
    private static volatile Cleaner INSTANCE;
    
    /** Thread control. */
    private AtomicBoolean running = new AtomicBoolean(false);
    
    /** Queue for disposable objects. */
    private ReferenceQueue<IDisposable> queue = new ReferenceQueue<IDisposable>();
    
    private HashSet<NativeReference> references = new HashSet<NativeReference>(); 
    
    
    /**
     * Creates a new Cleaner object.
     *
     */
    private Cleaner() {
        super();
    }


    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        this.running.set(true);
        while(this.running.get()) {
            IDisposable ref = null;
            try {
                ref = (IDisposable) this.queue.remove();               
            } catch (InterruptedException e) {
                /* Don't care. 
                 * Loop variable will break us out at the top 
                 * if the thread has been shut down.*/
                continue;
            }
            try {
                ref.dispose();
            } catch (Exception ex) {
                logger.error("Error in dispose method.", ex);
            }
        }
    }
    
    
    /**
     * Gets the reference queue the Cleaner listens on.
     * 
     * @return The reference queue the Cleaner listens on.
     */
    public ReferenceQueue<IDisposable> getRefQueue() {
        return (this.queue);
    }
    
    public boolean register(NativeReference ref) {
        synchronized(this.references) {
            return (this.references.add(ref));    
        }
        
    }
    
    public boolean unregister(NativeReference ref) {
        synchronized(this.references) {
            return (this.references.remove(ref));
        }
    }
    
    public int size() {
        synchronized(this.references) {
            return (this.references.size());
        }
    }
    
    /**
     * Stops the Cleaner.
     */
    public void stop() {        
        this.running.set(false);
    }
    
    /**
     * Returns true if the cleaner is running.
     * 
     * @return True if the cleaner is running; otherwise, false.
     */
    public boolean isRunning() {
        return (this.running.get());
    }
    
    /**
     * Gets the Cleaner singleton instance.
     * 
     * @return The Cleaner singleton.
     */
    public static Cleaner getInstance() {
        Cleaner instance = Cleaner.INSTANCE;
        if (instance == null) {
            synchronized(Cleaner.class) {
                instance = Cleaner.INSTANCE;
                if (instance == null) {
                    instance = Cleaner.INSTANCE = new Cleaner();
                    Thread thread = new Thread(instance);
                    thread.setName("LibJHDF-Cleaner");
                    thread.setDaemon(true);
                    thread.start();
                }
            }
        } 
        return (instance);       
    }
    
    
    
}
