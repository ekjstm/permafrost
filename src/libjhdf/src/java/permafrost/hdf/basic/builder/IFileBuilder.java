/**
 *
 */
package permafrost.hdf.basic.builder;

import java.io.FileNotFoundException;
import java.io.IOException;

import permafrost.hdf.basic.IFile;

/**
 * Interface implemented by builders that create IFile objects.
 *
 */
public interface IFileBuilder {
    /**
     * Creates an object based on the current builder parameters.
     * 
     * @return A new object based on the current builder parameters.
     * 
     * @throws FileNotFoundException If the file requested cannot be found.
     * @throws IOException If the file requested cannot be opened. 
     */
    public IFile build() throws FileNotFoundException, IOException;
    
    
    /**
     * Indicates that the file does not exist and should be created.
     * 
     * @return This builder.
     */
    public IFileBuilder create();    
    
    /**
     * Gets the name of the object to be built.
     * 
     * @return The name of the object to be built.
     */
    public String getName();
    
    /**
     * Indicates if the read-only flag is set.
     * 
     * @return True, if the read-only flag is set.
     */
    public boolean isReadOnly();
    
    /**
     * Sets the name of the object to be built.
     * 
     * @param name The name of the object to be built.
     * 
     * @return This builder.
     */
    public IFileBuilder name(String name);            
    
    /**
     * Indicates that the file must already exist and should not be created.
     * 
     */
    public IFileBuilder open();
    
    /**
     * Indicates that the file should be opened in read-only mode.
     * 
     * @param readOnly If the file should be opened in read-only mode.
     */
    public IFileBuilder readOnly();
    
    /**
     * Indicates that if the filename already exists, it should be truncated.
     *  
     * @param truncate If existing files should be truncated.
     */
    public IFileBuilder truncate();
    
    /**
     * Returns true, if the current builder parameters are valid.
     * 
     * @return True, if the current builder parameters are valid; otherwise, False.
     */
    public boolean isValid();
    
    /**
     * Indicates if the create-only flag is set.
     * 
     * @return True, if the create-only flag is set.
     */
    public boolean willCreate();    
    
    /**
     * Indicates if the open-only flag is set.
     * 
     * @return True, if the open-only flag is set.
     */
    public boolean willOpen();    
    
    /**
     * Indicates if the truncate flag is set.
     * 
     * @return True, if the truncate flag is set.
     */
    public boolean willTruncate();
    
    /**
     * Indicates if the file object should have an on-disk backing store.
     * 
     * <p>Default behavior is to use an on disk store.</p>
     * 
     * @param store If the file object should have an on-disk backing store.
     */
    public IFileBuilder backingStore(boolean store);
       
    /**
     * Indicates if the backing store flag is set.
     * 
     * @return True if the file object will have an on-disk backing store; otherwise, False.
     */
    public boolean hasBackingStore();
    
    /**
     * Indicates that child objects should be closed when the file is closed.
     * 
     * <p>Default behavior is to wait for children to be closed.</p>
     * 
     * @param cascade If children of the file object should be closed when the file object is closed.
     */
    public IFileBuilder cascadeOnClose(boolean cascade);
    
    /**
     * Indicates if child objects will be forced close when the file is closed.
     * 
     * @return True, if child objects will be closed when the file object is closed.
     */
    public boolean willCascadeOnClose();
   
}
