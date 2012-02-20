/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.libhdf.GroupLib;
import permafrost.hdf.libhdf.H5;
import permafrost.hdf.libhdf.LiteLib;
import permafrost.hdf.libhdf.PropertiesLib;

/**
 * TODO add type documentation
 *
 */
public class TestGroupIterator {

    /** */
    private static final String TEST_FILE = "test.h5";
    
    private File file;
    private int fid;
    
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
        FileBuilder bldr = new FileBuilder();
        this.file = (File) bldr.name(TEST_FILE).create().build();
        this.fid = this.file.getHId();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        if (this.file != null) this.file.dispose();
    }
       

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#next()}.
     */
    @Test
    public void testNext() {
        String groupName1 = "agroup1";
        int gid1 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName1, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid1 >= 0);
        GroupLib.H5Gclose(gid1);
        
        String groupName2 = "bdset1";
        int status = LiteLib.H5LTmake_dataset_string(this.fid, groupName2, "This is some data.");
        assertTrue(status >= 0);              
        
        String groupName3 = "cgroup3";
        int gid3 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName3, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid3 >= 0);
        GroupLib.H5Gclose(gid3);
        
        GroupIterator iter = new GroupIterator(this.file);
        
        assertTrue(iter.hasNext());
        Group g1 = (Group) iter.next();
        assertEquals(groupName1, g1.getLocalName());
        g1.dispose();
        
        assertTrue(iter.hasNext());
        Dataset g2 = (Dataset) iter.next();
        assertEquals(groupName2, g2.getLocalName());
        g2.dispose();
        
        assertTrue(iter.hasNext());
        Group g3 = (Group) iter.next();
        assertEquals(groupName3, g3.getLocalName());
        g3.dispose();
        
        assertFalse(iter.hasNext());
        try {
            iter.next();
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException ex) {
            // okay
        }
       
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#previous()}.
     */
    @Test
    public void testPrevious() {
        String groupName1 = "agroup1";
        int gid1 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName1, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid1 >= 0);
        GroupLib.H5Gclose(gid1);
        
        String groupName2 = "bgroup2";
        int gid2 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName2, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        GroupLib.H5Gclose(gid2);
        
        String groupName3 = "cgroup3";
        int gid3 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName3, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid3 >= 0);
        GroupLib.H5Gclose(gid3);
        
        GroupIterator iter = new GroupIterator(this.file);
        assertFalse(iter.hasPrevious());
        assertTrue(iter.hasNext());
        Group g1 = (Group) iter.next();
        g1.dispose();
        
        assertTrue(iter.hasNext());
        Group g2 = (Group) iter.next();        
        g2.dispose();
        
        assertTrue(iter.hasNext());
        Group g3 = (Group) iter.next();        
        g3.dispose();        
        
        assertFalse(iter.hasNext());
        assertTrue(iter.hasPrevious());
        g3 = (Group) iter.previous();      
        assertEquals(groupName3, g3.getLocalName());
        g3.dispose();  
        
        assertTrue(iter.hasPrevious());
        g2 = (Group) iter.previous();      
        assertEquals(groupName2, g2.getLocalName());
        g2.dispose(); 
        
        assertTrue(iter.hasPrevious());
        g1 = (Group) iter.previous();      
        assertEquals(groupName1, g1.getLocalName());
        g1.dispose();
        
        assertFalse(iter.hasPrevious());
        try {
            iter.previous();
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException ex) {
            // Okay
        }
        
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#remove()}.
     */
    @Test
    public void testRemove() {
        GroupIterator iter = new GroupIterator(this.file);
        try {
            iter.remove();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Okay
        }
        
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#hasNext()}.
     */
    @Test
    public void testHasNextNoNext() {
        GroupIterator iter = new GroupIterator(this.file);
        assertFalse(iter.hasNext());
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#hasNext()}.
     */
    @Test
    public void testHasNextNext() {
        String groupName1 = "agroup1";
        int gid1 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName1, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid1 >= 0);
        GroupLib.H5Gclose(gid1);
        
        GroupIterator iter = new GroupIterator(this.file);
        assertTrue(iter.hasNext());
        iter.next().dispose();
        assertFalse(iter.hasNext());
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#hasPrevious()}.
     */
    @Test
    public void testHasPreviousNoPrevious() {
        GroupIterator iter = new GroupIterator(this.file);
        assertFalse(iter.hasPrevious());
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#hasPrevious()}.
     */
    @Test
    public void testHasPreviousPrevious() {
        String groupName1 = "agroup1";
        int gid1 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName1, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid1 >= 0);
        GroupLib.H5Gclose(gid1);
        
        GroupIterator iter = new GroupIterator(this.file);
        iter.next().dispose();
        assertTrue(iter.hasPrevious());
        iter.previous().dispose();
        assertFalse(iter.hasPrevious());        
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#nextIndex()}.
     */
    @Test
    public void testNextIndex() {
        String groupName1 = "agroup1";
        int gid1 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName1, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid1 >= 0);
        GroupLib.H5Gclose(gid1);
        
        String groupName2 = "bgroup2";
        int gid2 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName2, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        GroupLib.H5Gclose(gid2);
        
        String groupName3 = "cgroup3";
        int gid3 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName3, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid3 >= 0);
        GroupLib.H5Gclose(gid3);
        
        GroupIterator iter = new GroupIterator(this.file);
        
        assertTrue(iter.hasNext());
        assertEquals(0, iter.nextIndex());
        iter.next().dispose();
        
        assertTrue(iter.hasNext());
        assertEquals(1, iter.nextIndex());
        iter.next().dispose();
        
        assertTrue(iter.hasNext());
        assertEquals(2, iter.nextIndex());
        iter.next().dispose();
        
        assertFalse(iter.hasNext());
        assertEquals(3, iter.nextIndex());        
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#previousIndex()}.
     */
    @Test
    public void testPreviousIndex() {
        String groupName1 = "agroup1";
        int gid1 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName1, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid1 >= 0);
        GroupLib.H5Gclose(gid1);
        
        String groupName2 = "bgroup2";
        int gid2 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName2, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        GroupLib.H5Gclose(gid2);
        
        String groupName3 = "cgroup3";
        int gid3 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName3, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid3 >= 0);
        GroupLib.H5Gclose(gid3);
        
        GroupIterator iter = new GroupIterator(this.file);
        
        assertTrue(iter.hasNext());
        iter.next().dispose();
        
        assertTrue(iter.hasNext());
        iter.next().dispose();
        
        assertTrue(iter.hasNext());
        iter.next().dispose();
        
        assertTrue(iter.hasPrevious());
        assertEquals(2, iter.previousIndex());
        iter.previous().dispose();
        
        assertTrue(iter.hasPrevious());
        assertEquals(1, iter.previousIndex());
        iter.previous().dispose();
        
        assertTrue(iter.hasPrevious());
        assertEquals(0, iter.previousIndex());
        iter.previous().dispose();
        
        assertFalse(iter.hasPrevious());
        assertEquals(-1, iter.previousIndex());        
        
    }

}
