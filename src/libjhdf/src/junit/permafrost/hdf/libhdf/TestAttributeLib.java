/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class TestAttributeLib {
    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestAttributeLib.class);

    private int fid;
    private int sid;
    private String ds_name;
    
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
        
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        this.ds_name = "dataset";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, ds_name, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        this.sid = DatasetLib.H5Dopen2(this.fid, this.ds_name, PropertiesLib.H5P_DEFAULT);
    }
    
    @After
    public void tearDown() throws Exception {
        try {
            if (this.sid > 0) {
                DatasetLib.H5Dclose(this.sid);
                this.sid = 0;
            }        
        } catch (Exception ex) {
            logger.error("Error closing dataset", ex);
        }
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
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aclose(int)}.
     */
    @Test
    public final void testH5Aclose() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Acreate1(int, java.lang.String, int, int, int)}.
     */
    @Test
    @SuppressWarnings("deprecation")
    public final void testH5Acreate1() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate1(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Acreate2(int, java.lang.String, int, int, int, int)}.
     */
    @Test
    public final void testH5Acreate2() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Acreate_by_name(int, java.lang.String, java.lang.String, int, int, int, int, int)}.
     */
    @Test
    public final void testH5Acreate_by_name() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Adelete(int, java.lang.String)}.
     */
    @Test
    public final void testH5Adelete() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_exists";
        int exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
        
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertTrue(exists >= 0);
        
        int deleteStatus = AttributeLib.H5Adelete(this.sid, att_name);
        assertTrue(deleteStatus >= 0);
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Adelete_by_name(int, java.lang.String, java.lang.String, int)}.
     */
    @Test
    public final void testH5Adelete_by_name() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_exists";
        int exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
        
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertTrue(exists >= 0);
        
        int deleteStatus = AttributeLib.H5Adelete_by_name(this.fid, this.ds_name, att_name, PropertiesLib.H5P_DEFAULT);
        assertTrue(deleteStatus >= 0);
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Adelete_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.SWIGTYPE_p_H5_iter_order_t, java.math.BigInteger, int)}.
     */
    @Test
    public final void testH5Adelete_by_idx() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "zzzzz_att_exists";
        int exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
        
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertTrue(exists >= 0);
        
        int deleteStatus = AttributeLib.H5Adelete_by_idx(
                this.fid, 
                this.ds_name, 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_DEC, 
                0,
                PropertiesLib.H5P_DEFAULT);
        assertTrue(deleteStatus >= 0);
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aexists(int, java.lang.String)}.
     */
    @Test
    public final void testH5Aexists() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_exists";
        int exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
        
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertTrue(exists > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aexists_by_name(int, java.lang.String, java.lang.String, int)}.
     */
    @Test
    public final void testH5Aexists_by_name() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_exists";
        int exists = AttributeLib.H5Aexists_by_name(this.fid, this.ds_name, att_name, PropertiesLib.H5P_DEFAULT);
        assertFalse(exists > 0);
        
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        
        exists = AttributeLib.H5Aexists_by_name(this.fid, this.ds_name, att_name, PropertiesLib.H5P_DEFAULT);
        assertTrue(exists >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_create_plist(int)}.
     */
    @Test
    public final void testH5Aget_create_plist() {       
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.getH5P_ATTRIBUTE_CREATE_LIST(), 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        assertTrue(PropertiesLib.H5Pequal(PropertiesLib.getH5P_ATTRIBUTE_CREATE_LIST(), AttributeLib.H5Aget_create_plist(atid)) > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_info(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5A_info_t)}.
     */
    @Test
    public final void testH5Aget_info() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        AttributeInfo ainfo = new AttributeInfo();
        int getStatus = AttributeLib.H5Aget_info(atid, ainfo);
        assertTrue(getStatus >=0);
        assertEquals(DatatypeCharsetType.H5T_CSET_ASCII, ainfo.getCset());
        assertEquals(128, ainfo.getData_size());
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_info_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.SWIGTYPE_p_H5_iter_order_t, java.math.BigInteger, permafrost.hdf.libhdf.SWIGTYPE_p_H5A_info_t, int)}.
     */
    @Test
    public final void testH5Aget_info_by_idx() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "zzzzzzzzz_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        AttributeInfo ainfo = new AttributeInfo();
        int getStatus = AttributeLib.H5Aget_info_by_idx(
                this.fid, 
                this.ds_name, 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_DEC, 
                0, 
                ainfo, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >=0);
        assertEquals(DatatypeCharsetType.H5T_CSET_ASCII, ainfo.getCset());
        assertEquals(128, ainfo.getData_size());
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_info_by_name(int, java.lang.String, java.lang.String, permafrost.hdf.libhdf.SWIGTYPE_p_H5A_info_t, int)}.
     */
    @Test
    public final void testH5Aget_info_by_name() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        AttributeInfo ainfo = new AttributeInfo();
        int getStatus = AttributeLib.H5Aget_info_by_name(this.fid, this.ds_name, att_name, ainfo, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >=0);
        assertEquals(DatatypeCharsetType.H5T_CSET_ASCII, ainfo.getCset());
        assertEquals(128, ainfo.getData_size());
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_name(int, long, byte[])}.
     */
    @Test
    public final void testH5Aget_name() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_namexxx";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        byte[] buf = new byte[64];
        int getLen = AttributeLib.H5Aget_name(atid, buf.length, buf);
        assertTrue(getLen >= 0);
        assertEquals(att_name, new String(buf, 0, getLen));
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_name_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.SWIGTYPE_p_H5_iter_order_t, java.math.BigInteger, java.lang.String, long, int)}.
     */
    @Test
    public final void testH5Aget_name_by_idx() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "zzzzzzzzz_att_by_namexxxy";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        byte[] buf = new byte[64];
        int getLen = AttributeLib.H5Aget_name_by_idx(
                this.fid,
                this.ds_name, 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_DEC, 
                0, 
                buf, 
                buf.length, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(getLen >= 0);
        assertEquals(att_name, new String(buf, 0, getLen));
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_num_attrs(int)}.
     */   
    @Test
    @SuppressWarnings("deprecation")
    public final void testH5Aget_num_attrs() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_exists";
        int exists = AttributeLib.H5Aget_num_attrs(this.sid);
        assertEquals(0, exists);
        
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid > 0);
        
        exists = AttributeLib.H5Aget_num_attrs(this.sid);
        assertEquals(1, exists);
                
        int atid2 = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name+"2", 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid2 > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = AttributeLib.H5Aclose(atid2);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        
        exists = AttributeLib.H5Aget_num_attrs(this.sid);
        assertEquals(2, exists);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_space(int)}.
     */
    @Test
    public final void testH5Aget_space() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int edsid = AttributeLib.H5Aget_space(atid);
        assertTrue(DataspaceLib.H5Sextent_equal(dsid, edsid) > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(edsid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);       
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_storage_size(int)}.
     */
    @Test
    public final void testH5Aget_storage_size() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        long size = AttributeLib.H5Aget_storage_size(atid);
        assertEquals(128, size);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aget_type(int)}.
     */
    @Test
    public final void testH5Aget_type() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int type = AttributeLib.H5Aget_type(atid);
        assertTrue(DatatypeLib.H5Tequal(DatatypeLib.getH5T_NATIVE_FLOAT(), type) > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aopen(int, java.lang.String, int)}.
     */
    @Test
    public final void testH5Aopen() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        atid = 0;
        
        atid = AttributeLib.H5Aopen(this.sid, att_name, PropertiesLib.H5P_DEFAULT);
        
        int type = AttributeLib.H5Aget_type(atid);
        assertTrue(DatatypeLib.H5Tequal(DatatypeLib.getH5T_NATIVE_FLOAT(), type) > 0);
        
        closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aopen_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.SWIGTYPE_p_H5_iter_order_t, java.math.BigInteger, int, int)}.
     */
    @Test
    public final void testH5Aopen_by_idx() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        atid = 0;
        
        atid = AttributeLib.H5Aopen_by_idx(
                this.fid, 
                this.ds_name, 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_DEC, 
                0, 
                PropertiesLib.H5P_DEFAULT,
                PropertiesLib.H5P_DEFAULT);             
        
        byte[] buf = new byte[64];
        int getLen = AttributeLib.H5Aget_name(atid, buf.length, buf);
        assertTrue(getLen >= 0);
        assertEquals(att_name, new String(buf, 0, getLen));
        
        closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aopen_by_name(int, java.lang.String, java.lang.String, int, int)}.
     */
    @Test
    public final void testH5Aopen_by_name() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        atid = 0;
        
        atid = AttributeLib.H5Aopen_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        
        int type = AttributeLib.H5Aget_type(atid);
        assertTrue(DatatypeLib.H5Tequal(DatatypeLib.getH5T_NATIVE_FLOAT(), type) > 0);
        
        closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aopen_idx(int, long)}.
     */
    @Test
    @SuppressWarnings("deprecation")   
    public final void testH5Aopen_idx() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        atid = 0;
        
        atid = AttributeLib.H5Aopen_idx(this.sid, 0);             
        
        byte[] buf = new byte[64];
        int getLen = AttributeLib.H5Aget_name(atid, buf.length, buf);
        assertTrue(getLen >= 0);
        assertEquals(att_name, new String(buf, 0, getLen));
        
        closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aopen_name(int, java.lang.String)}.
     */
    @Test
    @SuppressWarnings("deprecation")   
    public final void testH5Aopen_name() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        atid = 0;
        
        atid = AttributeLib.H5Aopen_name(this.sid, att_name);
        
        int type = AttributeLib.H5Aget_type(atid);
        assertTrue(DatatypeLib.H5Tequal(DatatypeLib.getH5T_NATIVE_FLOAT(), type) > 0);
        
        closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Aread(int, int, java.nio.Buffer)}.
     */
    @Test
    public final void testH5Aread() {
        int size = 32;
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{size}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                this.sid, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        FloatBuffer buf = ByteBuffer.allocateDirect(32*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        float n = 0.0f;
        while(buf.remaining() > 0) {
            buf.put(n);
            n++;
        }
        int writeStatus = AttributeLib.H5Awrite(atid, DatatypeLib.getH5T_NATIVE_FLOAT(), buf);
        assertTrue(writeStatus >= 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0);
        atid = -1;
        
        atid = AttributeLib.H5Aopen(this.sid, att_name, PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);
        
        FloatBuffer read = ByteBuffer.allocateDirect(32*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        int readStatus = AttributeLib.H5Aread(atid, DatatypeLib.getH5T_NATIVE_FLOAT(), read);
        assertTrue(readStatus >= 0);
        
        buf.rewind();
        read.rewind();
        while(buf.remaining() > 0) {
            assertEquals(buf.get(), read.get(), 10-14);
        }
        closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Arename(int, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testH5Arename() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_exists";
        String new_name = "att_new";
        int exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
        exists = AttributeLib.H5Aexists(this.sid, new_name);
        assertFalse(exists > 0);
        
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertTrue(exists >= 0);
        
        int renameStatus = AttributeLib.H5Arename(this.sid, att_name, new_name);
        assertTrue(renameStatus >= 0);
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
        exists = AttributeLib.H5Aexists(this.sid, new_name);
        assertTrue(exists > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.AttributeLib#H5Arename_by_name(int, java.lang.String, java.lang.String, java.lang.String, int)}.
     */
    @Test
    public final void testH5Arename_by_name() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_exists";
        String new_name = "att_new";
        int exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
        exists = AttributeLib.H5Aexists(this.sid, new_name);
        assertFalse(exists > 0);
        
        int atid = AttributeLib.H5Acreate_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid > 0);
        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
        
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertTrue(exists >= 0);
        
       
        int renameStatus = AttributeLib.H5Arename_by_name(
                this.fid, 
                this.ds_name, 
                att_name, 
                new_name, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(renameStatus >= 0);
        exists = AttributeLib.H5Aexists(this.sid, att_name);
        assertFalse(exists > 0);
        exists = AttributeLib.H5Aexists(this.sid, new_name);
        assertTrue(exists > 0);
    }



}
