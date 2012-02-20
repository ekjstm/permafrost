/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertTrue;

import org.junit.After;

import permafrost.hdf.libhdf.H5;

/**
 * Base class for test cases.
 *
 */
public class BaseTest {
    
    public File testFile; 
    
    public void setUp(boolean useFile) throws Exception {
        try {
            System.loadLibrary("libjhdf");
            H5.H5open();
        } catch (UnsatisfiedLinkError e) {
            System.err.println("   EPIC FAIL\nCannot load HDF native library:\n   " + e.getMessage() + "\n   EPIC FAIL");     
            throw (e);
        }
        String strTestFile = this.getClass().getSimpleName() + ".h5";
        
        FileBuilder bldr = new FileBuilder();
        bldr.name(strTestFile);
        bldr.create();
        bldr.cascadeOnClose(true);
        
        if (useFile) {            
            java.io.File jFile = new java.io.File(strTestFile);
            if (jFile.exists()) {
                assertTrue("Cannot delete test file: " + jFile, jFile.delete());
            }            
        } else {
            bldr.backingStore(false);
        }
        
        this.testFile = (File) bldr.build();
    }
    
    @After
    public void tearDown() throws Exception {
        if (this.testFile != null) {
            this.testFile.dispose();
        }
    }
    
    
}
