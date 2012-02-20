/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.basic.IFile;
import permafrost.hdf.libhdf.H5;

/**
 * Tests the {@link FileBuilder} class.
 *
 */
public class TestFileBuilder {

    /** The event logger. */
    private static final Logger logger = Logger.getLogger(TestFileBuilder.class);
    
    /** */
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

   

    /**
     * Test method for {@link permafrost.hdf.basic.impl.FileBuilder#build()}.
     */
    @Test   
    public void testOpenNonExistant() {
        java.io.File jFile = new java.io.File(TEST_FILE);
        if (jFile.exists()) {
            assertTrue(jFile.delete());
        }
        FileBuilder bldr = new FileBuilder();
        bldr.name(TEST_FILE).open();
        try {
            IFile file = bldr.build();
            file.dispose();
        } catch (FileNotFoundException e) {
            // Okay
            return;
        } catch (IOException e) {
            String msg = "Unexpected IOException " + e.getMessage();
            logger.error(msg, e);
            fail(msg);
        }
        fail("Expected FileNotFoundException");
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.FileBuilder#build()}.
     */
    @Test   
    public void testCreate() throws Exception {
        java.io.File jFile = new java.io.File(TEST_FILE);
        if (jFile.exists()) {
            assertTrue(jFile.delete());
        }
        FileBuilder bldr = new FileBuilder();
        bldr.name(TEST_FILE).create();       
        
        File file = (File) bldr.build();
        assertTrue(file.getHId() > 0);
        file.dispose();
        
        assertTrue(jFile.exists());
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.FileBuilder#build()}.
     */
    @Test   
    public void testTruncate() throws Exception {
        java.io.File jFile = new java.io.File(TEST_FILE);
        if (jFile.exists()) {
            assertTrue(jFile.delete());
        }
        FileBuilder bldr = new FileBuilder();
        bldr.name(TEST_FILE).create();       
        
        File file = (File) bldr.build();
        assertTrue(file.getHId() > 0);
        String comment = "This is the first pass file.";
        file.setComment(comment);
        String xComment = file.getComment();
        assertEquals(comment, xComment);
        file.dispose();
        
        assertTrue(jFile.exists());
        
        bldr.truncate();
        file = (File) bldr.build();
        String yComment = file.getComment();
        assertNull(yComment);
        file.dispose();        
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.FileBuilder#build()}.
     */
    @Test   
    public void testBackingStore() throws Exception {
        java.io.File jFile = new java.io.File(TEST_FILE);
        if (jFile.exists()) {
            assertTrue(jFile.delete());
        }
        FileBuilder bldr = new FileBuilder();
        bldr.name(TEST_FILE).backingStore(false).create();       
        
        File file = (File) bldr.build();
        assertTrue(file.getHId() > 0);
        String comment = "This is the first pass file.";
        file.setComment(comment);
        String xComment = file.getComment();
        assertEquals(comment, xComment);        
        
        assertFalse(jFile.exists());
       
    }

}
