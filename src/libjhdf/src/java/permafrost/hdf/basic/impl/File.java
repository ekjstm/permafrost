/**
 *
 */
package permafrost.hdf.basic.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import permafrost.hdf.basic.IFile;
import permafrost.hdf.libhdf.FileLib;

/**
 * Implementation of the HDF5 file object.
 *
 */
public class File extends AbstractGroup implements IFile {

    /** Implements the native handle for File. */
    private static final class FileReference extends NativeReference {

        /**
         * Creates a new FileReference object.
         *
         * @param referent The vm referent.
         */
        public FileReference(File referent) {
            super(referent);
        }

        /* (non-Javadoc)
         * @see permafrost.hdf.basic.impl.NativeReference#doDispose()
         */
        @Override
        protected void doDispose() {
            if (this.hid > 0) {
                int status = FileLib.H5Fclose(this.hid);
                assert status >= 0 : "Untrapped error in native code.";
                this.hid = 0;
            }            
        }        
    }
    
    /**
     * Creates a new File object.
     *
     * @param hid The native resource id.
     */
    protected File(int hid) {
        super(hid);
        this.setReference(new FileReference(this));
    }
    
    protected static File open(String path) throws FileNotFoundException {
        java.io.File jFile = new java.io.File(path);
        if (!jFile.canRead()) {
            throw new FileNotFoundException(path);
        }
        long flags = 0;
        if (!jFile.canWrite()) {
            flags &= FileLib.H5F_ACC_RDONLY;
        } else {
            flags &= FileLib.H5F_ACC_RDWR;
        }
        return (File.open(path, flags, PropertyList.Default));
    }
    
    protected static File open(String path, long flags, PropertyList fapl) throws FileNotFoundException {
        java.io.File jFile = new java.io.File(path);
        if (!jFile.canRead()) {
            throw new FileNotFoundException(path);
        }
        int fid = FileLib.H5Fopen(path, flags, fapl.getHId());
        assert fid > 0 : "Untrapped error in native code.";
        File file = new File(fid);  
        return (file);
    }
    
    
    protected static File create(String path) throws IOException {
        java.io.File jFile = new java.io.File(path).getCanonicalFile();
        java.io.File jParent = jFile.getParentFile();
        if (!jParent.canWrite()) {
            String err = String.format("Cannot access '%s'.", jParent.getAbsolutePath());
            throw new IOException(err);
        }
        long flags = 0;
        return (File.create(path, flags, PropertyList.Default, PropertyList.Default));
    }
    
    protected static File create(String path, long flags, PropertyList fcpl, PropertyList fapl) throws IOException {
        java.io.File jFile = new java.io.File(path).getCanonicalFile();
        java.io.File jParent = jFile.getParentFile();
        if (!jParent.canWrite()) {
            String err = String.format("Cannot access '%s'.", jParent.getAbsolutePath());
            throw new IOException(err);
        }
        int fid = FileLib.H5Fcreate(
                path, 
                flags, 
                fcpl.getHId(), 
                fapl.getHId()
        );
        assert fid > 0 : "Untrapped error in native code.";
        File file = new File(fid);       
        return (file);
    }
    

}
