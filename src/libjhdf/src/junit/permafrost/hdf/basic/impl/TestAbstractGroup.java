/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.basic.IFile;
import permafrost.hdf.basic.IGroup;
import permafrost.hdf.basic.builder.IGroupBuilder;
import permafrost.hdf.libhdf.H5;

/**
 * Tests for the {@link AbstractGroup} class.
 *
 */
public class TestAbstractGroup {
    
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

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AbstractGroup#getChild(java.lang.String)}.
     */
    @Test
    public void testGetChild() throws Exception {
        FileBuilder fbldr = new FileBuilder();        
        fbldr.name(TEST_FILE).truncate();
        IFile file = fbldr.build();
        IGroupBuilder gbldr = file.groupBuilder();
        final String childName = "group1";
        gbldr.setName(childName);
        IGroup group = gbldr.build();
        group.dispose();
        file.dispose();
        
        fbldr.open();
        file = fbldr.build();
        Group xGroup = (Group) file.getChild(childName);
        assertNotNull(xGroup);
        assertTrue(xGroup.isInitialized());
        xGroup.dispose();
        file.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AbstractGroup#groupBuilder()}.
     */
    @Test
    public void testGroupBuilder() throws Exception {                
        FileBuilder fbldr = new FileBuilder();        
        fbldr.name(TEST_FILE).truncate();
        IFile file = fbldr.build();
        IGroupBuilder gbldr = file.groupBuilder();
        final String childName = "group1";
        gbldr.setName(childName);
        IGroup group = gbldr.build();
        group.dispose();
        file.dispose();
        
        fbldr.open();
        file = fbldr.build();
        Group xGroup = (Group) file.getChild(childName);
        assertNotNull(xGroup);
        assertTrue(xGroup.isInitialized());
        xGroup.dispose();
        file.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AbstractObject#getComment()}.
     */
    @Test
    public void testGetComment() throws Exception{
        FileBuilder fbldr = new FileBuilder();        
        fbldr.name(TEST_FILE).truncate();
        IFile file = fbldr.build();
        
        final String fileComment = "file comment";
        file.setComment(fileComment);
        IGroupBuilder gbldr = file.groupBuilder();
        final String childName = "group1";
        gbldr.setName(childName);
        IGroup group = gbldr.build();
        final String groupComment = "group comment";
        group.setComment(groupComment);
        group.dispose();
        file.dispose();
        
        fbldr.open();
        file = fbldr.build();
        String xFileComment = file.getComment();
        Group xGroup = (Group) file.getChild(childName);        
        assertEquals(fileComment, xFileComment);
        String xGroupComment = xGroup.getComment();
        assertEquals(groupComment, xGroupComment);
        xGroup.dispose();
        file.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AbstractObject#getParent()}.
     */
    @Test
    public void testGetParent() {
        fail("Not yet implemented"); // TODO
    }
       

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AbstractResource#dispose()}.
     */
    @Test
    public void testDispose() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AbstractResource#getLocalName()}.
     */
    @Test
    public void testGetLocalName() throws Exception {
        FileBuilder fbldr = new FileBuilder();        
        fbldr.name(TEST_FILE).truncate();
        IFile file = fbldr.build();
        String xLocalName = file.getLocalName();
        
        IGroupBuilder gbldr = file.groupBuilder();
        final String childName = "group1";
        gbldr.setName(childName);
        IGroup group = gbldr.build();
        group.dispose();
        file.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AbstractResource#getPath()}.
     */
    @Test
    public void testGetPath() {
        fail("Not yet implemented"); // TODO
    }

}
