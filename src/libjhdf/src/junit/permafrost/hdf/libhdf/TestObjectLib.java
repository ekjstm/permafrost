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
 *
 */
public class TestObjectLib {

    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestAttributeLib.class);

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
        this.fid = FileLib.H5Fcreate("test.h5", 
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
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oclose(int)}.
     */
    @Test
    public final void testH5Oclose() {
        int idI32 = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_NATIVE_INT32());
        assertTrue(idI32 >= 0);
        int closeStatus = ObjectLib.H5Oclose(idI32);
        assertTrue (closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Ocopy(int, java.lang.String, int, java.lang.String, int, int)}.
     */
    @Test
    public final void testH5Ocopy() {
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
        
        String srcName = "groupx";
        int gid3 = GroupLib.H5Gcreate2(
                gid, 
                srcName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid3 >= 0);
        
        String dst_name = "groupy";
        int moveStatus = ObjectLib.H5Ocopy(gid, srcName, gid2, dst_name, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT);
        assertTrue(moveStatus >= 0);
        
        byte[] name = new byte[dst_name.length()+1];
        int getStatus = GroupLib.H5Gget_objname_by_idx(gid2, 0, name, name.length);
        assertEquals(name.length-1, getStatus);
        assertEquals(dst_name, new String(name, 0, getStatus));
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid3);
        assertTrue(closeStatus >= 0);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Odecr_refcount(int)}.
     */
    @Test
    public final void testH5Odecr_refcount() {
        final String groupName = "groupTest";
        final int groupId = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        ObjectInfo info = new ObjectInfo();
        int status = ObjectLib.H5Oget_info(groupId, info);
        assertTrue(status >= 0);
        assertEquals(1, info.getRc());
        
        status = ObjectLib.H5Oincr_refcount(groupId);
        assertTrue(status >= 0);
        
        status = ObjectLib.H5Oget_info(groupId, info);
        assertTrue(status >= 0);
        assertEquals(2, info.getRc());
      
        status = ObjectLib.H5Odecr_refcount(groupId);
        assertTrue(status >= 0);
        
        status = ObjectLib.H5Oget_info(groupId, info);
        assertTrue(status >= 0);
        assertEquals(1, info.getRc());
        
        info.delete();
        GroupLib.H5Gclose(groupId);                
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oget_comment(int, java.lang.String, long)}.
     */
    @Test
    public final void testH5Oget_comment() {
        String groupName = "group";        
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);        

        String comment = "This is a comment string.";
        int setStatus = ObjectLib.H5Oset_comment(gid, comment);
        assertTrue(setStatus >= 0);

        byte[] buf = new byte[comment.length()+1];
        int getStatus = ObjectLib.H5Oget_comment(gid, buf, buf.length);
        assertTrue(getStatus >= 0);
        assertEquals(comment, StringUtils.fromNullTerm(buf));
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oget_comment_by_name(int, java.lang.String, java.lang.String, long, int)}.
     */
    @Test
    public final void testH5Oget_comment_by_name() {
        String groupName = "group";        
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);        

        String comment = "This is a comment string.";
        int setStatus = ObjectLib.H5Oset_comment(gid, comment);
        assertTrue(setStatus >= 0);

        byte[] buf = new byte[comment.length()+1];
        int getStatus = ObjectLib.H5Oget_comment_by_name(
                this.fid, 
                groupName, 
                buf, 
                buf.length, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(getStatus >= 0);
        assertEquals(comment, StringUtils.fromNullTerm(buf));
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oget_info(int, permafrost.hdf.libhdf.ObjectInfo)}.
     */
    @Test
    public final void testH5Oget_info() {
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                gid, 
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
        
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info(gid, info);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());
        assertEquals(1, info.getNum_attrs());
        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oget_info_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.IteratorOrderType, java.math.BigInteger, permafrost.hdf.libhdf.ObjectInfo, int)}.
     */
    @Test
    public final void testH5Oget_info_by_idx() {
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                gid, 
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
        
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info_by_idx(
                this.fid,
                ".", 
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_INC, 
                0, 
                info, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());
        assertEquals(1, info.getNum_attrs());
        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oget_info_by_name(int, java.lang.String, permafrost.hdf.libhdf.ObjectInfo, int)}.
     */
    @Test
    public final void testH5Oget_info_by_name() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int dsid = DataspaceLib.H5Screate_simple(1, new long[]{32}, new long[]{64});
        assertTrue(dsid >= 0);
        
        String att_name = "att_by_name";
        int atid = AttributeLib.H5Acreate2(
                gid, 
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
        
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info_by_name(this.fid, groupName, info, PropertiesLib.H5P_DEFAULT);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());
        assertEquals(1, info.getNum_attrs());
        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oincr_refcount(int)}.
     */
    @Test
    public final void testH5Oincr_refcount() {
        final String groupName = "groupTest";
        final int groupId = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        ObjectInfo info = new ObjectInfo();
        int status = ObjectLib.H5Oget_info(groupId, info);
        assertTrue(status >= 0);
        assertEquals(1, info.getRc());
        
        status = ObjectLib.H5Oincr_refcount(groupId);
        assertTrue(status >= 0);
        
        status = ObjectLib.H5Oget_info(groupId, info);
        assertTrue(status >= 0);
        assertEquals(2, info.getRc());
      
        status = ObjectLib.H5Odecr_refcount(groupId);
        assertTrue(status >= 0);
        
        status = ObjectLib.H5Oget_info(groupId, info);
        assertTrue(status >= 0);
        assertEquals(1, info.getRc());
        
        info.delete();
        GroupLib.H5Gclose(groupId);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Olink(int, int, java.lang.String, int, int)}.
     */
    @Test
    public final void testH5Olink() {
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
        int linkStatus = ObjectLib.H5Olink(
                gid2, 
                this.fid,
                lnkName,                 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);        
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
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oopen(int, java.lang.String, int)}.
     */
    @Test
    public final void testH5Oopen() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        gid = 0;
        
        gid = ObjectLib.H5Oopen(this.fid, groupName, PropertiesLib.H5P_DEFAULT);
                     
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info(gid, info);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());
        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oopen_by_addr(int, int)}.
     */
    @Test
    public final void testH5Oopen_by_addr() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        gid = 0;
        
        LinkInfo linfo = new LinkInfo();
        int getStatus = LinkLib.H5Lget_info(this.fid, groupName, linfo, PropertiesLib.H5P_DEFAULT);
        
        gid = ObjectLib.H5Oopen_by_addr(this.fid, linfo.getU().getAddress());
        assertTrue(gid >= 0);
                     
        ObjectInfo info = new ObjectInfo();
        getStatus = ObjectLib.H5Oget_info(gid, info);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());
        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oopen_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.IteratorOrderType, java.math.BigInteger, int)}.
     */
    @Test
    public final void testH5Oopen_by_idx() {
        String groupName = "group";
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        gid = 0;
        
        gid = ObjectLib.H5Oopen_by_idx(
                this.fid,
                ".",
                IndexType.H5_INDEX_NAME, 
                IteratorOrderType.H5_ITER_INC,
                0, 
                PropertiesLib.H5P_DEFAULT
        );
                     
        ObjectInfo info = new ObjectInfo();
        int getStatus = ObjectLib.H5Oget_info(gid, info);
        assertTrue(getStatus >= 0);
        assertEquals(ObjectType.H5O_TYPE_GROUP, info.getType());
        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.ObjectLib#H5Oset_comment_by_name(int, java.lang.String, java.lang.String, int)}.
     */
    @Test
    public final void testH5Oset_comment_by_name() {
        String groupName = "group";        
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                groupName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);        

        String comment = "This is a comment string.";
        int setStatus = ObjectLib.H5Oset_comment_by_name(
                this.fid, 
                groupName, 
                comment, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(setStatus >= 0);

        byte[] buf = new byte[comment.length()+1];
        int getStatus = ObjectLib.H5Oget_comment(gid, buf, buf.length);
        assertTrue(getStatus >= 0);
        assertEquals(comment, StringUtils.fromNullTerm(buf));
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

}
