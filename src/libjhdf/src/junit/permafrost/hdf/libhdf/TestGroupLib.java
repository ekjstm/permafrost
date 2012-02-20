/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class TestGroupLib {

    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestGroupLib.class);

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
        NativeErrorHandler.getInstance().disable();
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
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gclose(int)}.
     */
    @Test
    public final void testH5Gclose() {
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gcreate1(int, java.lang.String, long)}.
     */    
    @Test
    @SuppressWarnings("deprecation")
    public final void testH5Gcreate1() {        
        int gid = GroupLib.H5Gcreate1(this.fid, "group", 0);
        assertTrue(gid >= 0);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gcreate2(int, java.lang.String, int, int, int)}.
     */
    @Test
    public final void testH5Gcreate2() {
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gcreate_anon(int, int, int)}.
     */
    @Test
    public final void testH5Gcreate_anon() {
    	int gid = GroupLib.H5Gcreate_anon(
    			this.fid, 
    			PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_comment(int, java.lang.String, long, byte[])}.
     */
    @Test
    @SuppressWarnings("deprecation")	
    public final void testH5Gget_comment() {
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

        String comment = "This is a comment string.";
        int setStatus = GroupLib.H5Gset_comment(this.fid, groupName, comment);
        assertTrue(setStatus >= 0);

        byte[] buf = new byte[comment.length()+1];
        int getStatus = GroupLib.H5Gget_comment(this.fid, groupName, buf.length, buf);
        assertTrue(getStatus >= 0);        
        assertEquals(comment, StringUtils.fromNullTerm(buf));
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_create_plist(int)}.
     */
    @Test
    public final void testH5Gget_create_plist() {
        int gcplid = PropertiesLib.getH5P_GROUP_CREATE_LIST();
    	int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                gcplid, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int plistid = GroupLib.H5Gget_create_plist(gid);
        assertTrue(plistid >= 0);        
        assertTrue(PropertiesLib.H5Pequal(gcplid, plistid) >0);
        
        int closeStatus = PropertiesLib.H5Pclose(plistid);        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_info(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5G_info_t)}.
     */
    @Test
    public final void testH5Gget_info() {    	
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        GroupInfo info = new GroupInfo();
        int getStatus = GroupLib.H5Gget_info(gid, info);
        assertTrue(getStatus >= 0);
        assertEquals(1, info.getNlinks());
        assertEquals(GroupStoreageType.H5G_STORAGE_TYPE_COMPACT, info.getStorage_type());
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_info_by_idx(int, java.lang.String, permafrost.hdf.libhdf.IndexType, permafrost.hdf.libhdf.IteratorOrderType, java.math.BigInteger, permafrost.hdf.libhdf.SWIGTYPE_p_H5G_info_t, int)}.
     */
    @Test
    public final void testH5Gget_info_by_idx() {
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        GroupInfo info = new GroupInfo();
        int getStatus = GroupLib.H5Gget_info_by_idx(
        		this.fid, 
        		"group",
        		IndexType.H5_INDEX_NAME, 
        		IteratorOrderType.H5_ITER_DEC, 
        		0, 
        		info, 
        		PropertiesLib.H5P_DEFAULT);   
        assertTrue(getStatus >= 0);
        //assertEquals(1, info.getNlinks());
        assertEquals(GroupStoreageType.H5G_STORAGE_TYPE_COMPACT, info.getStorage_type());
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_info_by_name(int, java.lang.String, permafrost.hdf.libhdf.SWIGTYPE_p_H5G_info_t, int)}.
     */
    @Test
    public final void testH5Gget_info_by_name() {
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        
        GroupInfo info = new GroupInfo();
        int getStatus = GroupLib.H5Gget_info_by_name(
                this.fid, 
                "group",
                info, 
                PropertiesLib.H5P_DEFAULT);   
        assertTrue(getStatus >= 0);
        assertEquals(1, info.getNlinks());
        assertEquals(GroupStoreageType.H5G_STORAGE_TYPE_COMPACT, info.getStorage_type());
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_linkval(int, java.lang.String, long, byte[])}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Gget_linkval() {
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
		int linkStatus = GroupLib.H5Glink2(
				gid, 
				groupName, 
				GroupLinkType.H5G_LINK_SOFT, 
				this.fid, 
				lnkName);
		assertTrue(linkStatus >= 0);
		
		byte[] name = new byte[2*groupName.length() + 1]; 
		int getStatus = GroupLib.H5Gget_linkval(this.fid, lnkName, name.length, name);
		assertTrue(getStatus >= 0);
		
		assertEquals(groupName, StringUtils.fromNullTerm(name));
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_num_objs(int, long[])}.
     */
    @Test
    @SuppressWarnings("deprecation")  
    public final void testH5Gget_num_objs() {
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);
        

        long[] num_objs = new long[1];
        int getStatus = GroupLib.H5Gget_num_objs(gid, num_objs);               
        assertTrue(getStatus >= 0);
        assertEquals(1, num_objs[0]);
       
       int gid3 = GroupLib.H5Gcreate2(
               gid, 
               "groupx", 
               PropertiesLib.H5P_DEFAULT, 
               PropertiesLib.H5P_DEFAULT, 
               PropertiesLib.H5P_DEFAULT);
       assertTrue(gid2 >= 0);
       
       getStatus = GroupLib.H5Gget_num_objs(gid, num_objs);               
       assertTrue(getStatus >= 0);
       assertEquals(2, num_objs[0]);
       
       int closeStatus = GroupLib.H5Gclose(gid);
       assertTrue(closeStatus >= 0);
       closeStatus = GroupLib.H5Gclose(gid2);
       assertTrue(closeStatus >= 0);
       closeStatus = GroupLib.H5Gclose(gid3);
       assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_objinfo(int, java.lang.String, long, permafrost.hdf.libhdf.GroupStatus)}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Gget_objinfo() {
        int gid = GroupLib.H5Gcreate2(
                this.fid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid >= 0);
        
        int gid2 = GroupLib.H5Gcreate2(
                gid, 
                "group", 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        assertTrue(gid2 >= 0);        

        GroupStatus status = new GroupStatus();
        int getStatus = GroupLib.H5Gget_objinfo(this.fid, "group", 0, status);              
        assertTrue(getStatus >= 0);             
       
       int closeStatus = GroupLib.H5Gclose(gid);
       assertTrue(closeStatus >= 0);
       closeStatus = GroupLib.H5Gclose(gid2);
       assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_objname_by_idx(int, java.math.BigInteger, byte[], long)}.
     */
    @Test
    public final void testH5Gget_objname_by_idx() {
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
                
        byte[] name = new byte[groupName.length()+1];
        int getStatus = GroupLib.H5Gget_objname_by_idx(gid, 0, name, name.length);
        assertEquals(name.length-1, getStatus);
        assertEquals(groupName, new String(name, 0, groupName.length()));
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gget_objtype_by_idx(int, java.math.BigInteger)}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Gget_objtype_by_idx() {
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
        
        GroupObjectType type = GroupLib.H5Gget_objtype_by_idx(gid, 0);
        assertEquals(GroupObjectType.H5G_GROUP, type);
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Glink(int, permafrost.hdf.libhdf.GroupLinkType, java.lang.String, java.lang.String)}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Glink() {
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
        
        String lnkName = "/groupx";
		int linkStatus = GroupLib.H5Glink(
				gid, 
				GroupLinkType.H5G_LINK_SOFT, 
				groupName, 
				lnkName);
		assertTrue(linkStatus >= 0);
		
        GroupInfo info = new GroupInfo();
        int getStatus = GroupLib.H5Gget_info_by_name(
                this.fid, 
                lnkName,
                info, 
                PropertiesLib.H5P_DEFAULT);   
        assertTrue(getStatus >= 0);
        assertEquals(GroupStoreageType.H5G_STORAGE_TYPE_COMPACT, info.getStorage_type());                     
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Glink2(int, java.lang.String, permafrost.hdf.libhdf.GroupLinkType, int, java.lang.String)}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Glink2() {
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
		int linkStatus = GroupLib.H5Glink2(
				gid, 
				groupName, 
				GroupLinkType.H5G_LINK_SOFT, 
				this.fid, 
				lnkName);
		assertTrue(linkStatus >= 0);
		
        GroupInfo info = new GroupInfo();
        int getStatus = GroupLib.H5Gget_info_by_name(
                this.fid, 
                lnkName,
                info, 
                PropertiesLib.H5P_DEFAULT);   
        assertTrue(getStatus >= 0);
        assertEquals(GroupStoreageType.H5G_STORAGE_TYPE_COMPACT, info.getStorage_type());                     
              
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gmove(int, java.lang.String, java.lang.String)}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Gmove() {
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
        
        String dst_name = "groupx";
		int moveStatus = GroupLib.H5Gmove(gid, groupName, dst_name);
        assertTrue(moveStatus >= 0);
        
        byte[] name = new byte[dst_name.length()+1];
        int getStatus = GroupLib.H5Gget_objname_by_idx(gid, 0, name, name.length);
        assertEquals(name.length-1, getStatus);
        assertEquals(dst_name, new String(name, 0, getStatus));
        
        int closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
        closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gmove2(int, java.lang.String, int, java.lang.String)}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Gmove2() {
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
		int moveStatus = GroupLib.H5Gmove2(gid, srcName, gid2, dst_name);
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
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gopen1(int, java.lang.String)}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Gopen1() {
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
        
        gid = GroupLib.H5Gopen1(this.fid, groupName);
                     
        GroupInfo info = new GroupInfo();
        int getStatus = GroupLib.H5Gget_info(gid, info);
        assertTrue(getStatus >= 0);
        assertEquals(GroupStoreageType.H5G_STORAGE_TYPE_COMPACT, info.getStorage_type());
        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);

    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gopen2(int, java.lang.String, int)}.
     */
    @Test
    public final void testH5Gopen2() {
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
        
        gid = GroupLib.H5Gopen2(this.fid, groupName, PropertiesLib.H5P_DEFAULT);
                     
        GroupInfo info = new GroupInfo();
        int getStatus = GroupLib.H5Gget_info(gid, info);
        assertTrue(getStatus >= 0);
        assertEquals(GroupStoreageType.H5G_STORAGE_TYPE_COMPACT, info.getStorage_type());
        
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);
    }


    /**
     * Test method for {@link permafrost.hdf.libhdf.GroupLib#H5Gunlink(int, java.lang.String)}.
     */
	@Test
    @SuppressWarnings("deprecation")
    public final void testH5Gunlink() {
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
        
        int closeStatus = GroupLib.H5Gclose(gid2);
        assertTrue(closeStatus >= 0);
		
        int unlinkStatus = GroupLib.H5Gunlink(gid, groupName);
        assertTrue(unlinkStatus >= 0);
        
        gid2 = GroupLib.H5Gopen2(gid, groupName, PropertiesLib.H5P_DEFAULT);
        assertFalse(gid2 >= 0);
          
        closeStatus = GroupLib.H5Gclose(gid);
        assertTrue(closeStatus >= 0);

    }

}
