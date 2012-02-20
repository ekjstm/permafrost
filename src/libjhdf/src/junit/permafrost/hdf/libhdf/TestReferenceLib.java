/**
 *
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests {@link ReferenceLib}.
 *
 */
public class TestReferenceLib {

    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestReferenceLib.class);

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
     * Test method for {@link permafrost.hdf.libhdf.ReferenceLib#H5Rcreate(permafrost.hdf.libhdf.SWIGTYPE_p_void, int, java.lang.String, permafrost.hdf.libhdf.ReferenceType, int)}.
     */
    @Test
    public void testH5Rcreate() {
        String groupName = "agroup1";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT,
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(gid > 0);
        int status = GroupLib.H5Gclose(gid);
        assertTrue(status >= 0);
        
        Reference ref = new Reference();
        status = ReferenceLib.H5Rcreate(
                ref, 
                this.fid,
                groupName, 
                ReferenceType.H5R_OBJECT, 
                ReferenceLib.JHR_INVALID_SPACE
        );
        assertTrue(status >= 0);
        
        gid = ReferenceLib.H5Rdereference(this.fid, ReferenceType.H5R_OBJECT, ref);
        byte[] name = new byte[groupName.length()+2]; // null-term + one byte for leading '/'
        int read = IdentifierLib.H5Iget_name(gid, name, name.length);
        assert(read <= name.length);
        assertEquals("/"+groupName, StringUtils.fromNullTerm(name));
        
        status = GroupLib.H5Gclose(gid);
        ref.delete();
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ReferenceLib#H5Rdereference(int, permafrost.hdf.libhdf.ReferenceType, permafrost.hdf.libhdf.SWIGTYPE_p_void)}.
     */
    @Test
    public void testH5Rdereference() {
        String dsName = "adataset1";
        String dsValue = "abcdefghijklmnop";
        long[] regionDim = {4};
        int status = LiteLib.H5LTmake_dataset_string(this.fid, dsName, dsValue);
        
        assertTrue(status >= 0);
        int sid = DataspaceLib.H5Screate_simple(1, regionDim, regionDim);
        assertTrue(sid > 0);
        
        Reference ref = new Reference();
        status = ReferenceLib.H5Rcreate(
                ref, 
                this.fid,
                dsName, 
                ReferenceType.H5R_DATASET_REGION, 
                sid
        );
        assertTrue(status >= 0);
        
        int xsid = ReferenceLib.H5Rdereference(
                this.fid, 
                ReferenceType.H5R_DATASET_REGION,
                ref
        );        
        
        status = DatasetLib.H5Dclose(xsid);
        assertTrue(status >= 0);
        ref.delete();
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ReferenceLib#H5Rget_name(int, permafrost.hdf.libhdf.ReferenceType, permafrost.hdf.libhdf.SWIGTYPE_p_void, byte[], long)}.
     */
    @Test
    public void testH5Rget_name() {
        String groupName = "agroup1";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT,
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(gid > 0);
        int status = GroupLib.H5Gclose(gid);
        assertTrue(status >= 0);
        
        Reference ref = new Reference();
        status = ReferenceLib.H5Rcreate(
                ref, 
                this.fid,
                groupName, 
                ReferenceType.H5R_OBJECT, 
                ReferenceLib.JHR_INVALID_SPACE
        );
        assertTrue(status >= 0);
        
        byte[] name = new byte[groupName.length() +1];
        int chars = ReferenceLib.H5Rget_name(
               this.fid,
               ReferenceType.H5R_OBJECT, 
               ref, 
               name,
               name.length);
        if (chars >= name.length) {
            name = new byte[chars+1]; 
            chars = ReferenceLib.H5Rget_name(
                    this.fid,
                    ReferenceType.H5R_OBJECT, 
                    ref, 
                    name,
                    name.length);
        }
        assertEquals(name.length, chars+1);
        assertEquals("/"+groupName, StringUtils.fromNullTerm(name));
        ref.delete();
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ReferenceLib#H5Rget_obj_type1(int, permafrost.hdf.libhdf.ReferenceType, permafrost.hdf.libhdf.SWIGTYPE_p_void)}.
     */
    @Test
    @SuppressWarnings("deprecation")  
    public void testH5Rget_obj_type1() {
        String groupName = "agroup1";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT,
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(gid > 0);
        int status = GroupLib.H5Gclose(gid);
        assertTrue(status >= 0);
        
        Reference ref = new Reference();
        status = ReferenceLib.H5Rcreate(
                ref, 
                this.fid,
                groupName, 
                ReferenceType.H5R_OBJECT, 
                ReferenceLib.JHR_INVALID_SPACE
        );
        assertTrue(status >= 0);
               
        GroupObjectType type = ReferenceLib.H5Rget_obj_type1(this.fid, ReferenceType.H5R_OBJECT, ref);
        assertEquals(GroupObjectType.H5G_GROUP, type);
        ref.delete();
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ReferenceLib#H5Rget_obj_type2(int, permafrost.hdf.libhdf.ReferenceType, permafrost.hdf.libhdf.SWIGTYPE_p_void, permafrost.hdf.libhdf.SWIGTYPE_p_H5O_type_t)}.
     */
    @Test
    public void testH5Rget_obj_type2() {
        String dsName = "adataset1";
        String dsValue = "abcdefghijklmnop";
        long[] regionDim = {4};
        int status = LiteLib.H5LTmake_dataset_string(this.fid, dsName, dsValue);
        
        assertTrue(status >= 0);
        int sid = DataspaceLib.H5Screate_simple(1, regionDim, regionDim);
        assertTrue(sid > 0);
        
        Reference ref = new Reference();
        status = ReferenceLib.H5Rcreate(
                ref, 
                this.fid,
                dsName, 
                ReferenceType.H5R_DATASET_REGION, 
                sid
        );
        assertTrue(status >= 0);
        
        ObjectType[] type = new ObjectType[1];
        status = ReferenceLib.H5Rget_obj_type2(
                this.fid, 
                ReferenceType.H5R_DATASET_REGION, 
                ref, 
                type
        );
        assertTrue(status >= 0);
        assertEquals(ObjectType.H5O_TYPE_DATASET, type[0]);
        status = DataspaceLib.H5Sclose(sid);
        assertTrue(status >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ReferenceLib#H5Rget_region(int, permafrost.hdf.libhdf.ReferenceType, permafrost.hdf.libhdf.SWIGTYPE_p_void)}.
     */
    @Test
    public void testH5Rget_region() {
        String dsName = "adataset1";
        String dsValue = "abcdefghijklmnop";
        int status = LiteLib.H5LTmake_dataset_char(
                this.fid, 
                dsName, 
                1,
                new long[]{dsValue.length()}, 
                dsValue.getBytes()
        );        
        assertTrue(status >= 0);
        int sid = DataspaceLib.H5Screate_simple(
                1,
                new long[]{dsValue.length()}, 
                new long[]{dsValue.length()}
        );
        assertTrue(sid > 0);
        status = DataspaceLib.H5Sselect_elements(
                sid, 
                DataspaceSelectionOPType.H5S_SELECT_SET, 
                4, 
                new long[][]{{0},{1},{2},{3}}
        );
        assertTrue(status >= 0);
        
        Reference ref = new Reference();
        status = ReferenceLib.H5Rcreate(
                ref, 
                this.fid,
                dsName, 
                ReferenceType.H5R_DATASET_REGION, 
                sid
        );
        assertTrue(status >= 0);
        
        int did = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);
        assertTrue(did > 0);
        int xsid = ReferenceLib.H5Rget_region(did, ReferenceType.H5R_DATASET_REGION, ref);
        assertTrue(xsid > 0);
        
        int size = DataspaceLib.H5Sget_select_elem_npoints(xsid);
        assertEquals(4, size);
        long[] start = new long[1];
        long[] end = new long[1];
        status = DataspaceLib.H5Sget_select_bounds(xsid, start, end);
        assertTrue(status >= 0);
        assertEquals(0, start[0]);
        assertEquals(3, end[0]);
        status = DataspaceLib.H5Sclose(sid);
        assertTrue(status >= 0);   
        status = DataspaceLib.H5Sclose(xsid);
        assertTrue(status >= 0);   
    }

}
