/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class TestLinkLib {

    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestAttributeLib.class);

    private String fileName = "test.h5";
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
        /* Set the file driver to use compact/dense storage. */
        PropertiesLib.H5Pset_libver_bounds(plFAccess, FileLibVersionType.H5F_LIBVER_LATEST, FileLibVersionType.H5F_LIBVER_LATEST);
        assertTrue("Invalid file access property list.", plFAccess >= 0);
//        PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
        this.fid = FileLib.H5Fcreate(this.fileName, 
                (FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG),  
                PropertiesLibConstants.H5P_DEFAULT, 
                plFAccess
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
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lcopy(int, java.lang.String, int, java.lang.String, int, int)}.
     */
    @Test
    public final void testH5Lcopy() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "groupx";
        int linkStatus = LinkLib.H5Lcopy(
                gid, 
                groupName, 
                this.fid, 
                lnkName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );       
        assertTrue(linkStatus >= 0);
        
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info_by_name(this.fid, lnkName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());                             
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lcreate_external(java.lang.String, java.lang.String, int, java.lang.String, int, int)}.
     */
    @Test
    public final void testH5Lcreate_external() {
        int plFAccess = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_FILE_ACCESS_CLASS());
        /* Set the file driver to use compact/dense storage. */
        PropertiesLib.H5Pset_libver_bounds(plFAccess, FileLibVersionType.H5F_LIBVER_LATEST, FileLibVersionType.H5F_LIBVER_LATEST);
        assertTrue("Invalid file access property list.", plFAccess >= 0);
//        PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
        String fileName2 = "test2.h5";
        int fid2 = FileLib.H5Fcreate(fileName2, 
                (FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG),  
                PropertiesLibConstants.H5P_DEFAULT, 
                plFAccess
        );
        assertTrue("Invalid file identifier.", fid2 >= 0);   
        
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        int lcpl = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_CREATE_LIST());
        int setStatus = PropertiesLib.H5Pset_create_intermediate_group(lcpl, 1);
        assertTrue(setStatus >= 0);
        
        String linkName = "/newgroup/newlink";
        int linkStatus = LinkLib.H5Lcreate_external(
                this.fileName, 
                "/"+groupName+"/"+groupName, 
                fid2, 
                linkName, 
                lcpl, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(linkStatus >= 0);
        
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info_by_name(fid2, linkName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());                             
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
        closeStatus = FileLib.H5Fclose(fid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lcreate_hard(int, java.lang.String, int, java.lang.String, int, int)}.
     */
    @Test
    public final void testH5Lcreate_hard() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "groupx";
        int linkStatus = LinkLib.H5Lcreate_hard(
                gid,
                groupName,
                this.fid,
                lnkName,
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );       
        assertTrue(linkStatus >= 0);
        
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info_by_name(this.fid, lnkName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());                             
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lcreate_soft(java.lang.String, int, java.lang.String, int, int)}.
     */
    @Test
    public final void testH5Lcreate_soft() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "groupx";
        int linkStatus = LinkLib.H5Lcreate_soft(
                "/"+groupName+"/"+groupName, 
                this.fid, 
                lnkName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
     
        assertTrue(linkStatus >= 0);
        
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info_by_name(this.fid, lnkName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());                             
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lcreate_ud(int, java.lang.String, permafrost.hdf.libhdf.H5L_type_t, permafrost.hdf.libhdf.SWIGTYPE_p_void, long, int, int)}.
     */
    @Test
    public final void testH5Lcreate_ud() {
        fail("Not yet implemented"); // TODO test H5Lcreate_ud
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Ldelete(int, java.lang.String, int)}.
     */
    @Test
    public final void testH5Ldelete() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "groupx";
        int linkStatus = LinkLib.H5Lcreate_hard(
                gid,
                groupName,
                this.fid,
                lnkName,
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );       
        assertTrue(linkStatus >= 0);
        
        int delStatus = LinkLib.H5Ldelete(this.fid, lnkName, PropertiesLib.H5P_DEFAULT);
        assertTrue(delStatus >= 0);
              
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info_by_name(gid, groupName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());         
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Ldelete_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.IteratorOrderType, java.math.BigInteger, int)}.
     */
    @Test
    public final void testH5Ldelete_by_idx() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "zzzgroupx";
        int linkStatus = LinkLib.H5Lcreate_hard(
                gid,
                groupName,
                this.fid,
                lnkName,
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );       
        assertTrue(linkStatus >= 0);
        
        int delStatus = LinkLib.H5Ldelete_by_idx(
                this.fid, 
                ".", 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_INC, 
                0, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(delStatus >= 0);
              
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info_by_name(gid, groupName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());         
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lexists(int, java.lang.String, int)}.
     */
    @Test
    public final void testH5Lexists() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "groupx";
        int linkStatus = LinkLib.H5Lcreate_hard(
                gid,
                groupName,
                this.fid,
                lnkName,
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );       
        assertTrue(linkStatus >= 0);
        
        int exists = LinkLib.H5Lexists(gid, groupName, PropertiesLib.H5P_DEFAULT);
        assertEquals(1, exists);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lget_info(int, java.lang.String, permafrost.hdf.libhdf.LinkInfo, int)}.
     */
    @Test
    public final void testH5Lget_info() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        LinkInfo info = new LinkInfo();
        int getStatus = LinkLib.H5Lget_info(this.fid, groupName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        
        assertEquals(LinkType.H5L_TYPE_HARD, info.getType());
        assertEquals(DatatypeCharsetType.H5T_CSET_ASCII, info.getCset());
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lget_info_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.IteratorOrderType, java.math.BigInteger, permafrost.hdf.libhdf.LinkInfo, int)}.
     */
    @Test
    public final void testH5Lget_info_by_idx() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        LinkInfo info = new LinkInfo();
        int getStatus = LinkLib.H5Lget_info_by_idx(
                this.fid, 
                ".", 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_INC, 
                0, 
                info, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(getStatus >= 0);
        
        assertEquals(LinkType.H5L_TYPE_HARD, info.getType());
        assertEquals(DatatypeCharsetType.H5T_CSET_ASCII, info.getCset());
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lget_name_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.IteratorOrderType, java.math.BigInteger, java.lang.String, long, int)}.
     */
    @Test
    public final void testH5Lget_name_by_idx() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        byte[] name = new byte[groupName.length()+1];
        int getStatus = LinkLib.H5Lget_name_by_idx(
                this.fid, 
                ".", 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_INC, 
                0,  
                name, 
                name.length, 
                PropertiesLib.H5P_DEFAULT
        );        
              
        assertTrue(getStatus >= 0);
        
        assertEquals(groupName, StringUtils.fromNullTerm(name));       
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lget_val(int, java.lang.String, permafrost.hdf.libhdf.SWIGTYPE_p_void, long, int)}.
     */
    @Test
    public final void testH5Lget_val() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "groupx";
        int linkStatus = LinkLib.H5Lcreate_soft(
                "/"+groupName+"/"+groupName, 
                this.fid, 
                lnkName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
     
        assertTrue(linkStatus >= 0);
        
        LinkInfo info = new LinkInfo();
        int getStatus = LinkLib.H5Lget_info(this.fid, lnkName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        
        ByteBuffer buffer = ByteBuffer.allocateDirect((int) info.getU().getVal_size()+1).order(ByteOrder.nativeOrder());
        LinkLib.H5Lget_val(
                this.fid, 
                lnkName, 
                buffer, 
                buffer.capacity(), 
                PropertiesLib.H5P_DEFAULT
        );
        
        assertEquals("/"+groupName+"/"+groupName, StringUtils.fromNullTerm(buffer));
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lget_val_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.IteratorOrderType, java.math.BigInteger, permafrost.hdf.libhdf.SWIGTYPE_p_void, long, int)}.
     */
    @Test
    public final void testH5Lget_val_by_idx() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "zzzzgroupx";
        int linkStatus = LinkLib.H5Lcreate_soft(
                "/"+groupName+"/"+groupName, 
                this.fid, 
                lnkName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );     
        assertTrue(linkStatus >= 0);
        
        LinkInfo info = new LinkInfo();
        int getStatus = LinkLib.H5Lget_info(this.fid, lnkName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        
        ByteBuffer buffer = ByteBuffer.allocateDirect((int) info.getU().getVal_size()+1).order(ByteOrder.nativeOrder());
        LinkLib.H5Lget_val_by_idx(
                this.fid, 
                ".", 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_DEC, 
                0, 
                buffer, 
                buffer.capacity(), 
                PropertiesLib.H5P_DEFAULT
                );
        
        assertEquals("/"+groupName+"/"+groupName, StringUtils.fromNullTerm(buffer));
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lis_registered(permafrost.hdf.libhdf.H5L_type_t)}.
     */
    @Test
    public final void testH5Lis_registered() {
        assertEquals(1, LinkLib.H5Lis_registered(LinkType.H5L_TYPE_EXTERNAL));
        assertEquals(0, LinkLib.H5Lis_registered(LinkType.H5L_TYPE_SOFT));
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lmove(int, java.lang.String, int, java.lang.String, int, int)}.
     */
    @Test
    public final void testH5Lmove() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        String lnkName = "zzzzgroupx";
        int linkStatus = LinkLib.H5Lcreate_soft(
                "/"+groupName+"/"+groupName, 
                this.fid, 
                lnkName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );     
        assertTrue(linkStatus >= 0);
        
        // TODO Test create intermediate group functionality
        int lcpl = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_CREATE_LIST());
//        int setStatus = PropertiesLib.H5Pset_create_intermediate_group(lcpl, 1);
//        assertTrue(setStatus >= 0);
        
        String dst_name = "newlink";
        int moveStatus = LinkLib.H5Lmove(this.fid, lnkName, this.fid, dst_name, lcpl, PropertiesLib.H5P_DEFAULT);
        assertTrue(moveStatus >= 0);
        
        int lnk = ObjectLib.H5Oopen(this.fid, dst_name, PropertiesLib.H5P_DEFAULT);
        assertTrue(lnk >= 0);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0); 
        closeStatus = GroupLib.H5Gclose(lnk);
        assertTrue(closeStatus >= 0); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lunpack_elink_val(permafrost.hdf.libhdf.SWIGTYPE_p_void, long, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int, permafrost.hdf.libhdf.SWIGTYPE_p_p_char, permafrost.hdf.libhdf.SWIGTYPE_p_p_char)}.
     */
    @Test
    public final void testH5Lunpack_elink_val() {
        int plFAccess = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_FILE_ACCESS_CLASS());
        /* Set the file driver to use compact/dense storage. */
        PropertiesLib.H5Pset_libver_bounds(plFAccess, FileLibVersionType.H5F_LIBVER_LATEST, FileLibVersionType.H5F_LIBVER_LATEST);
        assertTrue("Invalid file access property list.", plFAccess >= 0);
//        PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
        String fileName2 = "test2.h5";
        int fid2 = FileLib.H5Fcreate(fileName2, 
                (FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG),  
                PropertiesLibConstants.H5P_DEFAULT, 
                plFAccess
        );
        assertTrue("Invalid file identifier.", fid2 >= 0);   
        
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        int lcpl = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_CREATE_LIST());
        int setStatus = PropertiesLib.H5Pset_create_intermediate_group(lcpl, 1);
        assertTrue(setStatus >= 0);
        
        String linkName = "/newgroup/newlink";
        int linkStatus = LinkLib.H5Lcreate_external(
                this.fileName, 
                "/"+groupName+"/"+groupName, 
                fid2, 
                linkName, 
                lcpl, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(linkStatus >= 0);
        
        LinkInfo info = new LinkInfo();
        int getStatus = LinkLib.H5Lget_info(fid2, linkName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        
        ByteBuffer buffer = ByteBuffer.allocateDirect((int) info.getU().getVal_size()+1).order(ByteOrder.nativeOrder());
        LinkLib.H5Lget_val(
                fid2, 
                linkName, 
                buffer, 
                buffer.capacity(), 
                PropertiesLib.H5P_DEFAULT
        );      
        
        long[] flags = new long[1];
        String[] filename = new String[1];
        String[] obj_path = new String[1]; 
        int unpackStatus = LinkLib.H5Lunpack_elink_val(buffer, buffer.capacity(), flags, filename, obj_path);
        assertTrue(unpackStatus >= 0);
              
        assertEquals(this.fileName, filename[0]);
        assertEquals( "/"+groupName+"/"+groupName, obj_path[0]);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
        closeStatus = FileLib.H5Fclose(fid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LinkLib#H5Lopen_by_idx(int, IndexType, IteratorOrderType, long, int)
     */
    @Test
    public final void testH5Literate_next_idx() {
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String groupName0 = "aatribute1";
        int atid = AttributeLib.H5Acreate2(
                this.fid,             
                groupName0, 
                DatatypeLib.getH5T_NATIVE_FLOAT(), 
                dsid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(atid >= 0);        
        int closeStatus = AttributeLib.H5Aclose(atid);
        assertTrue(closeStatus >= 0);
        closeStatus = DataspaceLib.H5Sclose(dsid);
        assertTrue(closeStatus >= 0); 
                
        String groupName1 = "agroup1";
        int gid1 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName1, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid1 >= 0);
        closeStatus = GroupLib.H5Gclose(gid1);
        assertTrue(closeStatus >= 0);
        
        String groupName2 = "bdset2";
        closeStatus = LiteLib.H5LTmake_dataset_string(this.fid, groupName2, "Here is some data");       
        assertTrue(closeStatus >= 0); 
        
        String groupName3 = "cgroup3";
        int gid3 = GroupLib.H5Gcreate2(
                this.fid, 
                groupName3, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid3 >= 0);
        assertTrue(closeStatus >= 0); 
        closeStatus = GroupLib.H5Gclose(gid3);
        
        GroupInfo ginfo = new GroupInfo();
        int status = GroupLib.H5Gget_info(this.fid, ginfo);
        assertTrue(status >= 0);
        long nlinks = ginfo.getNlinks();
        assertEquals(3, nlinks);
        ginfo.delete();
        
        String[] xGroupName1 = new String[1];
        IdentifierType[] type1 = new IdentifierType[1];
        int xgid1 = LinkLib.H5LJopen_by_idx(
                this.fid, 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_INC, 
                0l,
                PropertiesLib.H5P_DEFAULT,
                xGroupName1,
                type1
                );
        assertEquals(IdentifierType.H5I_GROUP, IdentifierLib.H5Iget_type(xgid1));
        assertEquals(groupName1, xGroupName1[0]);
        
        
        String[] xGroupName2 = new String[1];
        IdentifierType[] type2 = new IdentifierType[1];
        int xgid2 = LinkLib.H5LJopen_by_idx(
                this.fid, 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_INC, 
                1l,
                PropertiesLib.H5P_DEFAULT,
                xGroupName2,
                type2
                );
        assertEquals(IdentifierType.H5I_DATASET, IdentifierLib.H5Iget_type(xgid2));
        assertEquals(groupName2, xGroupName2[0]);
        
        String[] xGroupName3 = new String[1];
        IdentifierType[] type3 = new IdentifierType[1];
        int xgid3 = LinkLib.H5LJopen_by_idx(
                this.fid, 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_INC, 
                2l,
                PropertiesLib.H5P_DEFAULT,
                xGroupName3,
                type3
                );        
        assertEquals(IdentifierType.H5I_GROUP, IdentifierLib.H5Iget_type(xgid3));
        assertEquals(groupName3, xGroupName3[0]);
               
        closeStatus = GroupLib.H5Gclose(xgid1);
        assertTrue(closeStatus >= 0);       
        closeStatus = DatasetLib.H5Dclose(xgid2);       
        assertTrue(closeStatus >= 0); 
        closeStatus = GroupLib.H5Gclose(xgid3);
        assertTrue(closeStatus >= 0); 
    }
       

}
