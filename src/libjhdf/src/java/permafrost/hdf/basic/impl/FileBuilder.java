/**
 *
 */
package permafrost.hdf.basic.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import permafrost.hdf.basic.IFile;
import permafrost.hdf.basic.builder.IFileBuilder;
import permafrost.hdf.libhdf.FileCloseBehaviorType;
import permafrost.hdf.libhdf.FileLib;
import permafrost.hdf.libhdf.PropertiesLib;

/**
 * Builder for creating or opening a file.
 *
 */
public class FileBuilder extends AbstractBuilder<IFile> implements IFileBuilder {

    private static final long DEFAULT_MEMSTORE_INCREMENT = 4096;
    
    private boolean readOnly;
    private boolean openOnly = true;
    private boolean truncate;
    private boolean backingStore = true;
    private boolean cascadeClose = false;
    
    /**
     * Creates a new FileBuilder object.
     *
     */
    protected FileBuilder() {
        super();
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#build()
     */
    @Override
    public IFile build() throws FileNotFoundException, IOException {
        if (!this.isValid()) {
            throw new IllegalStateException("Builder arguments are not valid");
        }
        
        PropertyList fcpl = PropertyList.Default;
        PropertyList fapl = PropertyList.Default;     
        
        try {
            if (this.backingStore == false || this.cascadeClose == true) {
                int bfapl_id = PropertiesLib.getH5P_FILE_ACCESS_LIST();
                assert bfapl_id > 0  : "Untrapped error in native code.";
                int fapl_id = PropertiesLib.H5Pcopy(bfapl_id); 
                assert fapl_id > 0  : "Untrapped error in native code.";
                fapl = new PropertyList(fapl_id);
            }
            if (this.backingStore == false) {           
                int status = PropertiesLib.H5Pset_fapl_core(fapl.getHId(), DEFAULT_MEMSTORE_INCREMENT, 0);
                assert status >= 0  : "Untrapped error in native code.";
            }
            if (this.cascadeClose == true) {
                int status = PropertiesLib.H5Pset_fclose_degree(fapl.getHId(), FileCloseBehaviorType.H5F_CLOSE_STRONG);
                assert status >= 0  : "Untrapped error in native code.";
            }


            if (this.openOnly) {
                long flags = this.readOnly ? FileLib.H5F_ACC_RDONLY : FileLib.H5F_ACC_RDWR;            
                File file = File.open(this.name, flags, fapl);
                return (file);

            } else {
                // FIXME create is called any time truncate is not set. This leads to unexpected behavior. Open should be the default call.
                long flags = this.truncate ? FileLib.H5F_ACC_TRUNC : 0;
                File file = File.create(this.name, flags, fcpl, fapl);
                return (file);
            }
            
        } finally {
            fcpl.dispose();
            fapl.dispose();

        }
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IBuilder#name(java.lang.String)
     */
    @Override
    public IFileBuilder name(String xname) {
        String path = new java.io.File(xname).getAbsolutePath();
        super.setName(path);
       return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#willTruncate()
     */
    @Override
    public boolean willTruncate() {
        return (this.truncate);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#isOpenOnly()
     */
    @Override
    public boolean willOpen() {
       return (this.openOnly);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#isReadOnly()
     */
    @Override
    public boolean isReadOnly() {
        return (this.readOnly);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#setOpenOnly()
     */
    @Override
    public FileBuilder open() {
       this.openOnly = true;
       this.truncate = false;
       return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#readOnly()
     */
    @Override
    public FileBuilder readOnly() {
        this.readOnly = true;
        this.openOnly = true;
        this.truncate = false;
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#truncate()
     */
    @Override
    public FileBuilder truncate() {        
        this.truncate = true;
        this.openOnly = false;
        this.readOnly = false;
        return(this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#create()
     */
    @Override
    public IFileBuilder create() {
        this.openOnly = false;
        this.readOnly = false;
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#willCreate()
     */
    @Override
    public boolean willCreate() {
       return (!this.openOnly);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#hasBackingStore()
     */
    @Override
    public boolean hasBackingStore() {
        return (this.backingStore);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#setBackingStore(boolean)
     */
    @Override
    public IFileBuilder backingStore(boolean store) {
        this.backingStore = store;
        return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#cascadeOnClose(boolean)
     */
    @Override
    public IFileBuilder cascadeOnClose(boolean cascade) {
       this.cascadeClose = cascade;
       return (this);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.builder.IFileBuilder#willCascadeOnClose()
     */
    @Override
    public boolean willCascadeOnClose() {
        return (this.cascadeClose);
    }

    /* (non-Javadoc)
     * @see permafrost.hdf.basic.impl.AbstractBuilder#isValid()
     */
    @Override
    public boolean isValid() {
        boolean valid = true;
        valid = valid ? this.name != null && !this.name.isEmpty() : valid;
        return (valid);
    }

    
    
}
