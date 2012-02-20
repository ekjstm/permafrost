/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class TestDatasetLib {

    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestDatasetLib.class);

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
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dcreate1(int, java.lang.String, int, int, int)}.
     */
    @Test
    @SuppressWarnings("deprecation")   
    public final void testH5Dcreate1() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int setId = DatasetLib.H5Dcreate1(this.fid, dsName, typeId, spaceId, PropertiesLib.H5P_DEFAULT);
        DatasetLib.H5Dclose(setId);
        
        setId = DatasetLib.H5Dopen1(this.fid, dsName);
        
        int xtypeId = DatasetLib.H5Dget_type(setId);        
        assertTrue(DatatypeLib.H5Tequal(typeId, xtypeId) > 0);
        DatatypeLib.H5Tclose(xtypeId);
        
        int xspaceId = DatasetLib.H5Dget_space(setId);
        assertTrue(DataspaceLib.H5Sextent_equal(spaceId, xspaceId) > 0);
        DataspaceLib.H5Sclose(xspaceId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dcreate2(int, java.lang.String, int, int, int, int, int)}.
     */
    @Test
    public final void testH5Dcreate2() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int setId = DatasetLib.H5Dcreate2(this.fid, dsName, typeId, spaceId, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT);
        DatasetLib.H5Dclose(setId);
        
        setId = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);
        
        int xtypeId = DatasetLib.H5Dget_type(setId);        
        assertTrue(DatatypeLib.H5Tequal(typeId, xtypeId) > 0);
        DatatypeLib.H5Tclose(xtypeId);
        
        int xspaceId = DatasetLib.H5Dget_space(setId);
        assertTrue(DataspaceLib.H5Sextent_equal(spaceId, xspaceId) > 0);
        DataspaceLib.H5Sclose(xspaceId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dcreate_anon(int, int, int, int, int)}.
     */
    @Test
    public final void testH5Dcreate_anon() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32anon";
        int setId = DatasetLib.H5Dcreate_anon(
                this.fid, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        int createStatus = LinkLib.H5Lcreate_hard(
                setId, 
                ".", 
                this.fid, 
                dsName, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(createStatus >= 0);
        DatasetLib.H5Dclose(setId);
        
        setId = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);
        
        int xtypeId = DatasetLib.H5Dget_type(setId);        
        assertTrue(DatatypeLib.H5Tequal(typeId, xtypeId) > 0);
        DatatypeLib.H5Tclose(xtypeId);
        
        int xspaceId = DatasetLib.H5Dget_space(setId);
        assertTrue(DataspaceLib.H5Sextent_equal(spaceId, xspaceId) > 0);
        DataspaceLib.H5Sclose(xspaceId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dclose(int)}.
     */
    @Test
    public final void testH5Dclose() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int setId = DatasetLib.H5Dcreate2(this.fid, dsName, typeId, spaceId, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT);
        DataspaceLib.H5Sclose(spaceId);
        int closeStatus = DatasetLib.H5Dclose(setId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Ddebug(int)}.
     */
    @Test
    public final void testH5Ddebug() {
        fail("Not yet implemented"); // TODO Not sure how to test H5Debug
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dextend(int, java.math.BigInteger)}.
     */   
    @Test
    @SuppressWarnings("deprecation")
    public final void testH5Dextend() {
        long[] dims = new long[] {3, 3};
        long[] maxdims = new long[] {4, 4};
        long[] chunks = new long[]{1, 1};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        assertTrue(typeId > 0);
        final String dsName = "3x3BE32";
        int dcpl = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        PropertiesLib.H5Pset_chunk(dcpl, dims.length, chunks);
        int setId = DatasetLib.H5Dcreate2(
                this.fid, 
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                dcpl, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(setId > 0);
        
        int extendStatus = DatasetLib.H5Dextend(setId, maxdims);
        assertTrue(extendStatus >= 0);
        DataspaceLib.H5Sclose(spaceId);
        spaceId = DatasetLib.H5Dget_space(setId);
        
        long[] xdims = new long[2];
        long[] xmaxdims = new long[2];
        DataspaceLib.H5Sget_simple_extent_dims(spaceId, xdims, xmaxdims);
        assertArrayEquals(maxdims, xdims);
        assertArrayEquals(maxdims, xmaxdims);        
        
        PropertiesLib.H5Pclose(dcpl);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dfill(java.nio.Buffer, int, java.nio.Buffer, int, int)}.
     */
    @Test
    public final void testH5Dfill() {
        fail("Not yet implemented"); // TODO Need better API docs to test H5Dfill
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dget_create_plist(int)}.
     */
    @Test
    public final void testH5Dget_create_plist() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";       
        int dcpl = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        int setId = DatasetLib.H5Dcreate2(
                this.fid, 
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                dcpl, 
                PropertiesLib.H5P_DEFAULT
        );
        
        int plistId = DatasetLib.H5Dget_create_plist(setId);
        assertTrue(PropertiesLib.H5Pequal(dcpl, plistId) > 0);
        int classId = PropertiesLib.H5Pget_class(plistId);
        assertTrue("Negative return value from wrapper function.", classId >= 0);
        assertTrue(PropertiesLib.H5Pequal(PropertiesLib.getH5P_DATASET_CREATE_CLASS(), classId) > 0);
        
        PropertiesLib.H5Pclose(dcpl);
        PropertiesLib.H5Pclose(plistId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dget_offset(int)}.
     */
    @Test
    public final void testH5Dget_offset() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);       
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int defListId = PropertiesLib.getH5P_DATASET_CREATE_LIST();
        int listId = PropertiesLib.H5Pcopy(defListId);
        PropertiesLib.H5Pset_alloc_time(listId, DatasetAllocationTimeType.H5D_ALLOC_TIME_EARLY);
        int setId = DatasetLib.H5Dcreate2(
                this.fid, 
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                listId, 
                PropertiesLib.H5P_DEFAULT
        );
        long offset = DatasetLib.H5Dget_offset(setId);
        assertTrue(offset > 0);
        
        PropertiesLib.H5Pclose(listId);
        DataspaceLib.H5Sclose(spaceId);        
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dget_space(int)}.
     */
    @Test
    public final void testH5Dget_space() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int setId = DatasetLib.H5Dcreate2(this.fid, dsName, typeId, spaceId, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT);
        DatasetLib.H5Dclose(setId);
        
        setId = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);     
        
        int xspaceId = DatasetLib.H5Dget_space(setId);
        assertTrue(DataspaceLib.H5Sextent_equal(spaceId, xspaceId) > 0);
        DataspaceLib.H5Sclose(xspaceId);
        
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dget_space_status(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5D_space_status_t)}.
     */
    @Test
    public final void testH5Dget_space_status() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        
        int defListId = PropertiesLib.getH5P_DATASET_CREATE_LIST();
        int listId = PropertiesLib.H5Pcopy(defListId);
        PropertiesLib.H5Pset_alloc_time(listId, DatasetAllocationTimeType.H5D_ALLOC_TIME_EARLY);
        int setId = DatasetLib.H5Dcreate2(
                this.fid, 
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                listId, 
                PropertiesLib.H5P_DEFAULT
        );
       
        DatasetAllocationSpaceType[] allocation = new DatasetAllocationSpaceType[1];
        int getStatus = DatasetLib.H5Dget_space_status(setId, allocation);
        assertTrue(getStatus >= 0);
        assertEquals(DatasetAllocationSpaceType.H5D_SPACE_STATUS_ALLOCATED, allocation[0]);
        
        PropertiesLib.H5Pclose(listId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dget_storage_size(int)}.
     */
    @Test
    public final void testH5Dget_storage_size() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int defListId = PropertiesLib.getH5P_DATASET_CREATE_LIST();
        int listId = PropertiesLib.H5Pcopy(defListId);
        PropertiesLib.H5Pset_alloc_time(listId, DatasetAllocationTimeType.H5D_ALLOC_TIME_EARLY);
        int setId = DatasetLib.H5Dcreate2(
                this.fid, 
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                listId, 
                PropertiesLib.H5P_DEFAULT
        );
        
        long size = DatasetLib.H5Dget_storage_size(setId);
        assertEquals(4*4*4, size);
        
        PropertiesLib.H5Pclose(listId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dget_type(int)}.
     */
    @Test
    public final void testH5Dget_type() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int setId = DatasetLib.H5Dcreate2(this.fid, dsName, typeId, spaceId, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT);
        DatasetLib.H5Dclose(setId);
        
        setId = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);
        
        int xtypeId = DatasetLib.H5Dget_type(setId);        
        assertTrue(DatatypeLib.H5Tequal(typeId, xtypeId) > 0);
        
        DatatypeLib.H5Tclose(xtypeId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dopen1(int, java.lang.String)}.
     */
    @Test
    @SuppressWarnings("deprecation")    
    public final void testH5Dopen1() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int setId = DatasetLib.H5Dcreate2(this.fid, dsName, typeId, spaceId, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT);
        DatasetLib.H5Dclose(setId);
        
        setId = DatasetLib.H5Dopen1(this.fid, dsName);
        
        int xtypeId = DatasetLib.H5Dget_type(setId);        
        assertTrue(DatatypeLib.H5Tequal(typeId, xtypeId) > 0);
        DatatypeLib.H5Tclose(xtypeId);
        
        int xspaceId = DatasetLib.H5Dget_space(setId);
        assertTrue(DataspaceLib.H5Sextent_equal(spaceId, xspaceId) > 0);
        DataspaceLib.H5Sclose(xspaceId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dopen2(int, java.lang.String, int)}.
     */
    @Test
    public final void testH5Dopen2() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int setId = DatasetLib.H5Dcreate2(this.fid, dsName, typeId, spaceId, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT);
        DatasetLib.H5Dclose(setId);
        
        setId = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);
        
        int xtypeId = DatasetLib.H5Dget_type(setId);        
        assertTrue(DatatypeLib.H5Tequal(typeId, xtypeId) > 0);
        DatatypeLib.H5Tclose(xtypeId);
        
        int xspaceId = DatasetLib.H5Dget_space(setId);
        assertTrue(DataspaceLib.H5Sextent_equal(spaceId, xspaceId) > 0);
        DataspaceLib.H5Sclose(xspaceId);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dread(int, int, int, int, int, java.nio.Buffer)}.
     */
    @Test
    public final void testH5Dread() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I64BE();
        final String dsName = "4x4BE64";
        int setId = DatasetLib.H5Dcreate2(this.fid, dsName, typeId, spaceId, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT, PropertiesLib.H5P_DEFAULT);
        
        LongBuffer data = ByteBuffer.allocateDirect(4*4*8).order(ByteOrder.BIG_ENDIAN).asLongBuffer();
        data.put(new long[]{3,4,5,12});
        
        int writeStatus = DatasetLib.H5Dwrite(
                setId, 
                typeId, 
                DataspaceLib.H5S_ALL, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                data
        );
        assertTrue(writeStatus >= 0);
        DatasetLib.H5Dclose(setId);
        setId = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);
        LongBuffer read = ByteBuffer.allocateDirect(4*4*8).order(ByteOrder.BIG_ENDIAN).asLongBuffer();
        
        int readStatus = DatasetLib.H5Dread(
                setId, 
                typeId, 
                DataspaceLib.H5S_ALL,
                DataspaceLib.H5S_ALL, 
                PropertiesLib.H5P_DEFAULT,
                read);
        assertTrue(readStatus >= 0);
        data.rewind();
        for (int n=0; n<data.limit(); n++) {
            assertEquals(data.get(), read.get());
        }
      
        DatasetLib.H5Dclose(setId);
        DataspaceLib.H5Sclose(spaceId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dset_extent(int, java.math.BigInteger)}.
     */
    @Test
    public final void testH5Dset_extent() {
        long[] dims = new long[] {3, 3};
        long[] maxdims = new long[] {4, 4};
        long[] chunks = new long[]{1, 1};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int dcpl = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        PropertiesLib.H5Pset_chunk(dcpl, dims.length, chunks);
        final int setId = DatasetLib.H5Dcreate2(
                this.fid, 
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                dcpl,
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(setId >= 0);
        int xtypeId = DatasetLib.H5Dget_type(setId);        
        assertTrue(DatatypeLib.H5Tequal(typeId, xtypeId) > 0);
        DatatypeLib.H5Tclose(xtypeId);
        
        int xspaceId = DatasetLib.H5Dget_space(setId);
        assertTrue(DataspaceLib.H5Sextent_equal(spaceId, xspaceId) > 0);
        DataspaceLib.H5Sclose(xspaceId);
        
        long[] xdims = new long[2];
        long[] xmaxdims = new long[2];
        DataspaceLib.H5Sget_simple_extent_dims(spaceId, xdims, xmaxdims);
        assertArrayEquals(dims, xdims);
        assertArrayEquals(maxdims, xmaxdims);        
        int setStatus = DatasetLib.H5Dset_extent(setId, maxdims);        
        assertTrue(setStatus >= 0);
        
        DataspaceLib.H5Sclose(spaceId);
        spaceId = DatasetLib.H5Dget_space(setId);
        DataspaceLib.H5Sget_simple_extent_dims(spaceId, xdims, xmaxdims);
        assertArrayEquals(maxdims, xdims);
        assertArrayEquals(maxdims, xmaxdims);        
        
        PropertiesLib.H5Pclose(dcpl);
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dvlen_get_buf_size(int, int, int, java.math.BigInteger[])}.
     */
    @Test
    public final void testH5Dvlen_get_buf_size() {
        long[] dims = new long[] {4};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, dims);
        assertTrue(spaceId >= 0);
        
        int dcpl = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        
        int typeId = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_C_S1());
        int setStatus = DatatypeLib.H5Tset_size(typeId, DatatypeLib.H5T_VARIABLE);
        assertTrue(setStatus >= 0);
        
        final String dsName = "4x4STR";
        int setId = DatasetLib.H5Dcreate2(
                this.fid,
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                dcpl,
                PropertiesLib.H5P_DEFAULT
        );
      
        String[] data ={ 
                "abcde",
                "fghij",
                "klmno",
                "pqrst"
        };
        
        int writeStatus = DatasetLib.H5Dwrite_strings(
                setId, 
                typeId, 
                DataspaceLib.H5S_ALL, 
                DataspaceLib.H5S_ALL, 
                PropertiesLib.H5P_DEFAULT, 
                data
        );
        assertTrue(writeStatus >= 0);
        
        long[] size = new long[1];
        int getStatus = DatasetLib.H5Dvlen_get_buf_size(setId, typeId, spaceId, size);
        assertTrue(getStatus >= 0);
        assertEquals(24, size[0]); // 4*(5+1) bytes;
        
        DataspaceLib.H5Sclose(spaceId);
        DatasetLib.H5Dclose(setId);
    }
   

    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dwrite(int, int, int, int, int, java.nio.Buffer)}.
     */
    @Test
    public final void testH5Dwrite() {
        long[] maxdims = new long[] {4, 4};
        final int spaceId = DataspaceLib.H5Screate_simple(maxdims.length, maxdims, maxdims);
        assertTrue(spaceId >= 0);
        
        final int typeId = DatatypeLib.getH5T_STD_I32BE();
        final String dsName = "4x4BE32";
        int setId = DatasetLib.H5Dcreate2(
                this.fid, 
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT);
        
        IntBuffer data = ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        data.put(new int[]{
                3,4,5,12, 
                20,60,720,43200,
                720,60,20,12,
                5,4,3,4});
        
        int writeStatus = DatasetLib.H5Dwrite(
                setId, 
                typeId, 
                DataspaceLib.H5S_ALL, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                data
        );
        assertTrue(writeStatus >= 0);
        DatasetLib.H5Dclose(setId);
        setId = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);
        IntBuffer read = ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        
        int readStatus = DatasetLib.H5Dread(
                setId, 
                typeId, 
                DataspaceLib.H5S_ALL,
                DataspaceLib.H5S_ALL, 
                PropertiesLib.H5P_DEFAULT,
                read);
        assertTrue(readStatus >= 0);
        data.rewind();
        for (int n=0; n<data.limit(); n++) {
            assertEquals(data.get(), read.get());
        }
      
        DatasetLib.H5Dclose(setId);
        DataspaceLib.H5Sclose(spaceId);
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.DatasetLib#H5Dwrite(int, int, int, int, int, java.nio.Buffer)}.
     */
    @Test
    public final void testH5Dwrite_strings() {
        long[] dims = new long[] {4};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, dims);
        assertTrue(spaceId >= 0);
        
        int dcpl = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        
        int typeId = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_C_S1());
        int setStatus = DatatypeLib.H5Tset_size(typeId, DatatypeLib.H5T_VARIABLE);
        assertTrue(setStatus >= 0);
        
        final String dsName = "4x4STR";
        int setId = DatasetLib.H5Dcreate2(
                this.fid,
                dsName, 
                typeId, 
                spaceId, 
                PropertiesLib.H5P_DEFAULT, 
                dcpl,
                PropertiesLib.H5P_DEFAULT
        );
      
        String[] data ={ 
                "abcde",
                "fghij",
                "klmno",
                "pqrst"
        };
        
        int writeStatus = DatasetLib.H5Dwrite_strings(
                setId, 
                typeId, 
                DataspaceLib.H5S_ALL, 
                DataspaceLib.H5S_ALL, 
                PropertiesLib.H5P_DEFAULT, 
                data
        );
        assertTrue(writeStatus >= 0);
        
        long[] size = new long[1];
        int getStatus = DatasetLib.H5Dvlen_get_buf_size(setId, typeId, spaceId, size);
        assertTrue(getStatus >= 0);
        assertEquals(24, size[0]); // 24 = 4*(5+1) bytes;
        
        DatasetLib.H5Dclose(setId);
        setId = DatasetLib.H5Dopen2(this.fid, dsName, PropertiesLib.H5P_DEFAULT);
//        ByteBuffer read = ByteBuffer.allocateDirect((int) size[0]).order(ByteOrder.BIG_ENDIAN);
        String[] read = new String[data.length];
        
        int readStatus = DatasetLib.H5Dread_strings(
                setId, 
                typeId, 
                spaceId,
                DataspaceLib.H5S_ALL, 
                PropertiesLib.H5P_DEFAULT,
                read);
        assertTrue(readStatus >= 0);
//        byte[] tmp = new byte[(int) size[0]];
//        read.get(tmp);
//        String str = new String(tmp);
        DatasetLib.H5Dclose(setId);
        DataspaceLib.H5Sclose(spaceId);
        
        assertArrayEquals(data, read);
    }

    
    public static void main(String[] args) throws Exception {        
        TestDatasetLib test = new TestDatasetLib();
        TestDatasetLib.setUpBeforeClass();
//        DatasetLibJNI.TestCall();
        test.setUp();
        test.testH5Dvlen_get_buf_size();
        test.tearDown();
    }
}
