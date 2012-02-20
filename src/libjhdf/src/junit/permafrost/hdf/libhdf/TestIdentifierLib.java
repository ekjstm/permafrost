/**
 *
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 * Tests {@link IdentifierLib}.
 *
 */
public class TestIdentifierLib {

    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestIdentifierLib.class);

    private int fid;        
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            System.loadLibrary("libjhdf");
            H5.H5open();
        } catch (UnsatisfiedLinkError e) {
            System.err.println("   EPIC FAIL\nCannot load HDF native library:\n   " + e.getMessage() + "\n   EPIC FAIL");     
            throw (e);
        }
    }
    
    @Before
    public void setUp() throws Exception {
        int plFAccess = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_FILE_ACCESS_CLASS());
        assertTrue("Invalid file access property list.", plFAccess >= 0);
        PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
        this.fid = FileLib.H5Fcreate("test.h5", 
                (FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG),  
                PropertiesLibConstants.H5P_DEFAULT, 
                PropertiesLibConstants.H5P_DEFAULT
        );
        assertTrue("Invalid file identifier.", this.fid >= 0);               
    }
    
    @After
    public void tearDown() throws Exception {      
        try {
            if (this.fid > 0) {
                FileLib.H5Fclose(this.fid);
                this.fid = 0;
            }
        } catch (Exception ex) {
            logger.error("Error closing file", ex);
        }
    }


    /**
     * Test method for {@link permafrost.hdf.libhdf.IdentifierLib#H5Idec_ref(int)}.
     */
    @Test
    public void testH5Idec_ref() {
        final String groupName = "groupTest";
        final int groupId = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        
        int refCount = IdentifierLib.H5Iinc_ref(groupId);
        assertEquals(2, refCount);
        
        GroupLib.H5Gclose(groupId);
        refCount = IdentifierLib.H5Idec_ref(groupId);
        assertEquals(0, refCount);

    }


    /**
     * Test method for {@link permafrost.hdf.libhdf.IdentifierLib#H5Iget_file_id(int)}.
     */
    @Test
    public void testH5Iget_file_id() {
        final String groupName = "groupTest";
        final int groupId = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        
        int fileId = IdentifierLib.H5Iget_file_id(groupId);
        assertEquals(this.fid, fileId);
        
        GroupLib.H5Gclose(groupId);
        FileLib.H5Fclose(fileId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.IdentifierLib#H5Iget_name(int, byte[], long)}.
     */
    @Test
    public void testH5Iget_name() {
        final String groupName = "/groupTest";
        final int groupId = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );       
        
        byte[] buff = new byte[groupName.length()+1];
        int len = IdentifierLib.H5Iget_name(groupId, buff, buff.length);
        GroupLib.H5Gclose(groupId);
        
        assertEquals(buff.length, len+1);
        assertEquals(groupName, StringUtils.fromNullTerm(buff));       
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.IdentifierLib#H5Iget_ref(int)}.
     */
    @Test
    public void testH5Iget_ref() {
        final String groupName = "/groupTest";
        final int groupId = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );       
        
        int openCount = IdentifierLib.H5Iget_ref(groupId);
        GroupLib.H5Gclose(groupId);
        assertEquals(1, openCount);
        
        openCount = IdentifierLib.H5Iget_ref(groupId);
        assertTrue(openCount < 1);
        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.IdentifierLib#H5Iget_type(int)}.
     */
    @Test
    public void testH5Iget_type() {
        final String groupName = "/groupTest";
        final int groupId = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
       
                
        IdentifierType type = IdentifierLib.H5Iget_type(groupId);
        GroupLib.H5Gclose(groupId);
        
        assertEquals(IdentifierType.H5I_GROUP, type);
    }
   

    /**
     * Test method for {@link permafrost.hdf.libhdf.IdentifierLib#H5Iinc_ref(int)}.
     */
    @Test
    public void testH5Iinc_ref() {
        final String groupName = "groupTest";
        final int groupId = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        
        int refCount = IdentifierLib.H5Iinc_ref(groupId);
        assertEquals(2, refCount);
        
        GroupLib.H5Gclose(groupId);
        refCount = IdentifierLib.H5Idec_ref(groupId);
        assertEquals(0, refCount);
    }

   
    /**
     * Test method for {@link permafrost.hdf.libhdf.IdentifierLib#H5Itype_exists(permafrost.hdf.libhdf.IdentifierType)}.
     */
    @Test
    public void testH5Itype_exists() {             
        int exists = IdentifierLib.H5Itype_exists(IdentifierType.H5I_GROUP);
        assertTrue(exists > 0);
       
    }
    
   
}
