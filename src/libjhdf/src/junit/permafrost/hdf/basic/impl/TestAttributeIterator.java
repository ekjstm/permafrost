/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.libhdf.LiteLib;

/**
 * Tests {@link AttributeIterator}.
 *
 */
public class TestAttributeIterator extends BaseTest {

    private static final boolean USE_DISK_FILE = true;
    
    private int fid;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.setUp(USE_DISK_FILE);;
        this.fid = this.testFile.getHId();
    }
    
       

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#next()}.
     */
    @Test
    public void testNext() {
        String attName1 = "aatt1";
        String attData1 = "A Attribute data A1";
        int status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName1, attData1);
        assertTrue(status >= 0);
        
        String attName2 = "batt2";
        String attData2 = "B Attribute data B2";
        status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName2, attData2);
        assertTrue(status >= 0);
        
        String attName3 = "catt3";
        String attData3 = "C Attribute data C3";
        status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName3, attData3);
        assertTrue(status >= 0);
        
        AttributeIterator iter = new AttributeIterator(this.testFile);
        
        assertTrue(iter.hasNext());
        Attribute a1 = iter.next();
        
        assertEquals(attName1, a1.getLocalName());
        a1.dispose();
        
        assertTrue(iter.hasNext());
        Attribute a2 = iter.next();
        assertEquals(attName2, a2.getLocalName());
        a2.dispose();
        
        assertTrue(iter.hasNext());
        Attribute a3 = iter.next();
        assertEquals(attName3, a3.getLocalName());
        a3.dispose();
        
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
        String attName1 = "aatt1";
        String attData1 = "A Attribute data A1";
        int status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName1, attData1);
        assertTrue(status >= 0);
        
        String attName2 = "batt2";
        String attData2 = "B Attribute data B2";
        status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName2, attData2);
        assertTrue(status >= 0);
        
        String attName3 = "catt3";
        String attData3 = "C Attribute data C3";
        status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName3, attData3);
        assertTrue(status >= 0);
        
        AttributeIterator iter = new AttributeIterator(this.testFile);
        assertFalse(iter.hasPrevious());
        assertTrue(iter.hasNext());
        
        Attribute a1 = iter.next();
        a1.dispose();
        
        assertTrue(iter.hasNext());
        Attribute a2 = iter.next();
        a2.dispose();
        
        assertTrue(iter.hasNext());
        Attribute a3 = iter.next();
        a3.dispose();      
        
        assertFalse(iter.hasNext());
        assertTrue(iter.hasPrevious());
        a3 = iter.previous();
        assertEquals(attName3, a3.getLocalName());
        a3.dispose();  
        
        assertTrue(iter.hasPrevious());
        a2 = iter.previous();      
        assertEquals(attName2, a2.getLocalName());
        a2.dispose(); 
        
        assertTrue(iter.hasPrevious());
        a1 =iter.previous();      
        assertEquals(attName1, a1.getLocalName());
        a1.dispose();
        
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
        AttributeIterator iter = new AttributeIterator(this.testFile);
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
        AttributeIterator iter = new AttributeIterator(this.testFile);
        assertFalse(iter.hasNext());
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#hasNext()}.
     */
    @Test
    public void testHasNextNext() {
        String attName1 = "aatt1";
        String attData1 = "A Attribute data A1";
        int status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName1, attData1);
        assertTrue(status >= 0);
                       
        AttributeIterator iter = new AttributeIterator(this.testFile);
        assertFalse(iter.hasPrevious());
        assertTrue(iter.hasNext());
        iter.next().dispose();
        assertFalse(iter.hasNext());
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#hasPrevious()}.
     */
    @Test
    public void testHasPreviousNoPrevious() {
        AttributeIterator iter = new AttributeIterator(this.testFile);
        assertFalse(iter.hasPrevious());
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.GroupIterator#hasPrevious()}.
     */
    @Test
    public void testHasPreviousPrevious() {
        String attName1 = "aatt1";
        String attData1 = "A Attribute data A1";
        int status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName1, attData1);
        assertTrue(status >= 0);
                       
        AttributeIterator iter = new AttributeIterator(this.testFile);
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
        String attName1 = "aatt1";
        String attData1 = "A Attribute data A1";
        int status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName1, attData1);
        assertTrue(status >= 0);
        
        String attName2 = "batt2";
        String attData2 = "B Attribute data B2";
        status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName2, attData2);
        assertTrue(status >= 0);
        
        String attName3 = "catt3";
        String attData3 = "C Attribute data C3";
        status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName3, attData3);
        assertTrue(status >= 0);
        
        AttributeIterator iter = new AttributeIterator(this.testFile);               
        
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
        String attName1 = "aatt1";
        String attData1 = "A Attribute data A1";
        int status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName1, attData1);
        assertTrue(status >= 0);
        
        String attName2 = "batt2";
        String attData2 = "B Attribute data B2";
        status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName2, attData2);
        assertTrue(status >= 0);
        
        String attName3 = "catt3";
        String attData3 = "C Attribute data C3";
        status = LiteLib.H5LTset_attribute_string(this.fid, ".", attName3, attData3);
        assertTrue(status >= 0);
        
        AttributeIterator iter = new AttributeIterator(this.testFile);
        
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
