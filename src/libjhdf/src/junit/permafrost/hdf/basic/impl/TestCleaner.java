/**
 *
 */
package permafrost.hdf.basic.impl;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.libhdf.H5;

/**
 * Tests the {@link Cleaner} class.
 *
 */
public class TestCleaner {

    /** The event logger. */
    private static final Logger logger = Logger.getLogger(TestCleaner.class);
    
    /** Filename for test data. */
    private static final String TEST_FILE = "test.h5";



    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            System.loadLibrary("libjhdf");
            H5.H5open();
        } catch (UnsatisfiedLinkError e) {
            System.err.println("   EPIC FAIL\nCannot load HDF native library:\n   " + e.getMessage() + "\n   EPIC FAIL");     
            throw (e);
        }
        java.io.File jFile = new java.io.File(TEST_FILE);
        if (jFile.exists()) {
            assertTrue(jFile.delete());
        }
    }

   

    @Test
    public void testFileGC() throws Exception {    
        java.io.File jFile = new java.io.File(TEST_FILE);
        assertFalse(jFile.exists());
        Cleaner cleaner = Cleaner.getInstance();
        Thread.sleep(5);
        assertTrue(Cleaner.getInstance().isRunning());
        assertEquals(0, cleaner.size());
        try {
            this.createFile1();
        } catch (IOException e) {
           fail("Unexpected exception" + e.getMessage());
        }                
        System.gc();
        Thread.sleep(5);    
        assertEquals(0, cleaner.size());
        assertTrue(jFile.exists());
    }
    
    private void createFile1() throws IOException {
        File file = File.create(TestCleaner.TEST_FILE);  
        Cleaner cleaner = Cleaner.getInstance();
        assertEquals(1, cleaner.size());
        logger.debug(file.getLocalName());
    }
    
    @Test
    public void testFileDispose() throws Exception {    
        java.io.File jFile = new java.io.File(TEST_FILE);
        assertFalse(jFile.exists());
        Cleaner cleaner = Cleaner.getInstance();
        Thread.sleep(5);
        assertTrue(Cleaner.getInstance().isRunning());
        assertEquals(0, cleaner.size());
        try {
            this.createFile2();
        } catch (IOException e) {
           fail("Unexpected exception" + e.getMessage());
        }                
        System.gc();
        Thread.sleep(5);    
        assertEquals(0, cleaner.size());
        assertTrue(jFile.exists());
    }
    
    private void createFile2() throws IOException {
        File file = File.create(TestCleaner.TEST_FILE);  
        file.dispose();
        Cleaner cleaner = Cleaner.getInstance();
        assertEquals(0, cleaner.size());        
    }
    
    
   
    
}
