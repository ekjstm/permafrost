package permafrost.hdf.libhdf;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDataspaceLib {
	
    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestTableLib.class);

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
    	//	        PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
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
	
	@Test
	public void testH5Sclose() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);
		
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Scopy() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);
				
		int copyId = DataspaceLib.H5Scopy(spaceId);
		assertTrue(copyId >= 0);
		
		long[] xdims = new long[2];
		long[] xmaxdims = new long[2];
		int ndims = DataspaceLib.H5Sget_simple_extent_dims(copyId, xdims, xmaxdims);
		assertEquals(2, ndims);
		
		assertArrayEquals(dims, xdims);
		assertArrayEquals(maxdims, xmaxdims);
		
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
		closeStatus = DataspaceLib.H5Sclose(copyId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Screate() {
		int spaceId = DataspaceLib.H5Screate(DataspaceClassType.H5S_SIMPLE);
		
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int setStatus = DataspaceLib.H5Sset_extent_simple(spaceId, dims.length, dims, maxdims);
		assertTrue(setStatus >= 0);
		
		long[] xdims = new long[2];
		long[] xmaxdims = new long[2];
		int ndims = DataspaceLib.H5Sget_simple_extent_dims(spaceId, xdims, xmaxdims);
		assertEquals(2, ndims);
		
		assertArrayEquals(dims, xdims);
		assertArrayEquals(maxdims, xmaxdims);
		
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Screate_simple() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);
		
		long[] xdims = new long[2];
		long[] xmaxdims = new long[2];
		int ndims = DataspaceLib.H5Sget_simple_extent_dims(spaceId, xdims, xmaxdims);
		assertEquals(2, ndims);
		
		assertArrayEquals(dims, xdims);
		assertArrayEquals(maxdims, xmaxdims);
		
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sdecode() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(1);		
		long[] capacity  = new long[]{buffer.capacity()};
		int encStatus = DataspaceLib.H5Sencode(spaceId, buffer, capacity);		
		assertTrue(encStatus >= 0);
		buffer = ByteBuffer.allocateDirect((int) capacity[0]);
		
		encStatus = DataspaceLib.H5Sencode(spaceId, buffer, capacity);
		assertTrue(encStatus >= 0);
		
		int copyId = DataspaceLib.H5Sdecode(buffer);
		
		long[] xdims = new long[2];
		long[] xmaxdims = new long[2];
		int ndims = DataspaceLib.H5Sget_simple_extent_dims(copyId, xdims, xmaxdims);
		assertEquals(2, ndims);
		
		assertArrayEquals(dims, xdims);
		assertArrayEquals(maxdims, xmaxdims);
		
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
		closeStatus = DataspaceLib.H5Sclose(copyId);
		assertTrue(closeStatus >= 0);
	}	

	@Test
	public void testH5Sextent_copy() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int srcId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(srcId >= 0);
		
		int destId = DataspaceLib.H5Screate(DataspaceClassType.H5S_SIMPLE);
		
		int setStatus = DataspaceLib.H5Sextent_copy(destId, srcId);
		assertTrue(setStatus >= 0);
		
		long[] xdims = new long[2];
		long[] xmaxdims = new long[2];
		int ndims = DataspaceLib.H5Sget_simple_extent_dims(destId, xdims, xmaxdims);
		assertEquals(2, ndims);
		
		assertArrayEquals(dims, xdims);
		assertArrayEquals(maxdims, xmaxdims);
		
		int closeStatus = DataspaceLib.H5Sclose(srcId);
		assertTrue(closeStatus >= 0);
		closeStatus = DataspaceLib.H5Sclose(destId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sextent_equal() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId1 = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId1 >= 0);
		
		int spaceId2 = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId2 >= 0);
		
		int isEqual = DataspaceLib.H5Sextent_equal(spaceId1, spaceId2);
		assertEquals(1, isEqual);
		
		int closeStatus = DataspaceLib.H5Sclose(spaceId1);
		assertTrue(closeStatus >= 0);
		closeStatus = DataspaceLib.H5Sclose(spaceId2);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_select_bounds() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);

		int selectStatus = DataspaceLib.H5Sselect_all(spaceId);
		assertTrue(selectStatus >= 0);
		
		long[] start = new long[2];
		long[] end = new long[2];
		int getStatus = DataspaceLib.H5Sget_select_bounds(spaceId, start, end);
		assertTrue(getStatus >= 0);
		
		assertArrayEquals(new long[]{0, 0}, start);
		assertArrayEquals(new long[]{2, 2}, end);
					
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_select_elem_npoints() {
	    long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);

		long[][] selection = new long[][]{{1,1},{2,2},{3,3}};
		int selectStatus = DataspaceLib.H5Sselect_elements(
				spaceId, 
				DataspaceSelectionOPType.H5S_SELECT_SET, 
				3, 
				selection
		);
		assertTrue(selectStatus >= 0);
		
		int nelem = DataspaceLib.H5Sget_select_elem_npoints(spaceId);
		assertEquals(selection.length, nelem);			
					
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_select_elem_pointlist() {
	    long[] dims = new long[]{8, 8};
        long[] maxdims = new long[] {8, 8};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);
        
        long[][] coords = new long[][] {
                {0,0},
                {1,1},
                {2,2},
                {3,3}
        };
        int selectStatus = DataspaceLib.H5Sselect_elements(
                spaceId, 
                DataspaceSelectionOPType.H5S_SELECT_SET, 
                coords.length, 
                coords
        );
        assertTrue(selectStatus >= 0);
        
        ByteBuffer xbuffer = ByteBuffer.allocateDirect(4*2*8).order(ByteOrder.nativeOrder());
        LongBuffer buffer = xbuffer.asLongBuffer();
        int getStatus = DataspaceLib.H5Sget_select_elem_pointlist(
                spaceId, 
                0, 
                4, 
                buffer
        );
        assertTrue(getStatus >= 0);
        
        long[] coord00 = new long[2];
        buffer.get(coord00);
        assertArrayEquals(coords[0], coord00);
        
        long[] coord01 = new long[2];
        buffer.get(coord01);
        assertArrayEquals(coords[1], coord01);
        
        long[] coord10 = new long[2];
        buffer.get(coord10);
        assertArrayEquals(coords[2], coord10);
        
        long[] coord11 = new long[2];
        buffer.get(coord11);
        assertArrayEquals(coords[3], coord11);
        
        int closeStatus = DataspaceLib.H5Sclose(spaceId);
        assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_select_hyper_blocklist() {
	    long[] dims = new long[]{8, 8};
        long[] maxdims = new long[] {8, 8};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);

        long[] blkStart = new long[]{0, 0};
        long[] blkStride = new long[]{2, 2};
        long[] blkCount = new long[]{2, 4};
        long[] blkSize = new long[]{1, 1};
        int selectStatus = DataspaceLib.H5Sselect_hyperslab(
                spaceId, 
                DataspaceSelectionOPType.H5S_SELECT_SET, 
                blkStart, 
                blkStride, 
                blkCount, 
                blkSize
        );
        assertTrue(selectStatus >= 0);

        LongBuffer buffer = ByteBuffer.allocateDirect(8*8*4).order(ByteOrder.nativeOrder()).asLongBuffer();
        int getStatus = DataspaceLib.H5Sget_select_hyper_blocklist(
                spaceId, 
                0, 
                8, 
                buffer
        );
        assertTrue(getStatus >= 0);                                    
        long[] blocks = new long[16];
        buffer.get(blocks);
        long[] blkUpper = new long[2];
        long[] blkLower = new long[2];
        int offset=0;       
            for (int n=0; n<blkCount[0]; n++) {
                for (int m=0; (m<blkCount[1] && offset <blocks.length); m++, offset+=4) {
                blkUpper[0] = blkStart[0]+blkStride[0]*n;
                blkUpper[1] = blkStart[1]+blkStride[1]*m;
                blkLower[0] = blkStart[0]+blkStride[0]*n;
                blkLower[1] = blkStart[1]+blkStride[1]*m;
                assertArrayEquals(blkUpper, new long[]{blocks[offset], blocks[offset+1]});
                assertArrayEquals(blkLower, new long[]{blocks[offset+2], blocks[offset+3]});
            }
        }
        
        int closeStatus = DataspaceLib.H5Sclose(spaceId);
        assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_select_hyper_nblocks() {
	    long[] dims = new long[]{8, 8};
	    long[] maxdims = new long[] {8, 8};
	    int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
	    assertTrue(spaceId >= 0);

	    int selectStatus = DataspaceLib.H5Sselect_hyperslab(
	            spaceId, 
	            DataspaceSelectionOPType.H5S_SELECT_SET, 
	            new long[]{0, 0}, 
	            new long[]{2, 2}, 
	            new long[]{2, 4}, 
	            new long[]{1, 1}
	    );
	    assertTrue(selectStatus >= 0);

	    int nBlocks = DataspaceLib.H5Sget_select_hyper_nblocks(spaceId);
	    assertEquals(nBlocks, 8);                            

	    int closeStatus = DataspaceLib.H5Sclose(spaceId);
	    assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_select_npoints() {
	    long[] dims = new long[]{3, 3};
        long[] maxdims = new long[] {4, 4};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);

        int selectStatus = DataspaceLib.H5Sselect_all(spaceId);
        assertTrue(selectStatus >= 0);
        
        int npoints = DataspaceLib.H5Sget_select_npoints(spaceId);
        assertEquals(dims[0]*dims[1], npoints);
                    
        int closeStatus = DataspaceLib.H5Sclose(spaceId);
        assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_select_type() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);

		int selectStatus = DataspaceLib.H5Sselect_all(spaceId);
		assertTrue(selectStatus >= 0);
		
		DataspaceSelectionType type = DataspaceLib.H5Sget_select_type(spaceId);
		assertEquals(DataspaceSelectionType.H5S_SEL_ALL, type);
					
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_simple_extent_dims() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);
		
		long[] xdims = new long[2];
		long[] xmaxdims = new long[2];
		int ndims = DataspaceLib.H5Sget_simple_extent_dims(spaceId, xdims, xmaxdims);
		assertEquals(2, ndims);
		
		assertArrayEquals(dims, xdims);
		assertArrayEquals(maxdims, xmaxdims);
		
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_simple_extent_ndims() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);

		int ndims = DataspaceLib.H5Sget_simple_extent_ndims(spaceId);
		assertEquals(2, ndims);
					
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_simple_extent_npoints() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);

		int npoints = DataspaceLib.H5Sget_simple_extent_npoints(spaceId);
		assertEquals(dims[0]*dims[1], npoints);
					
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sget_simple_extent_type() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);

		DataspaceClassType type = DataspaceLib.H5Sget_simple_extent_type(spaceId);
		assertEquals(DataspaceClassType.H5S_SIMPLE, type);
					
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sis_simple() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);

		int isSimple = DataspaceLib.H5Sis_simple(spaceId);
		assertEquals(1, isSimple);
					
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Soffset_simple() {
	    long[] dims = new long[]{3, 3};
        long[] maxdims = new long[] {4, 4};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);       
        
        int setStatus = DataspaceLib.H5Soffset_simple(spaceId, new long[]{1,1});
        assertTrue(setStatus >= 0);
        
        int selectStatus = DataspaceLib.H5Sselect_all(spaceId);
        assertTrue(selectStatus >= 0);   
        
        long[] start = new long[2];
        long[] end = new long[2];
        int getStatus = DataspaceLib.H5Sget_select_bounds(spaceId, start, end);
        assertTrue(getStatus >= 0);
        
        assertArrayEquals(new long[]{0, 0}, start);
        assertArrayEquals(new long[]{2, 2}, end);
                    
        int closeStatus = DataspaceLib.H5Sclose(spaceId);
        assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sselect_all() {
		long[] dims = new long[]{3, 3};
		long[] maxdims = new long[] {4, 4};
		int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
		assertTrue(spaceId >= 0);

		int selectStatus = DataspaceLib.H5Sselect_all(spaceId);
		assertTrue(selectStatus >= 0);
		
		long[] start = new long[2];
		long[] end = new long[2];
		int getStatus = DataspaceLib.H5Sget_select_bounds(spaceId, start, end);
		assertTrue(getStatus >= 0);
		
		assertArrayEquals(new long[]{0, 0}, start);
		assertArrayEquals(new long[]{2, 2}, end);
					
		int closeStatus = DataspaceLib.H5Sclose(spaceId);
		assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sselect_elements() {
	    long[] dims = new long[]{8, 8};
	    long[] maxdims = new long[] {8, 8};
	    int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
	    assertTrue(spaceId >= 0);
	    
	    long[][] coords = new long[][] {
	            {0,0},
	            {1,1},
	            {2,2},
	            {3,3}
	    };
	    int selectStatus = DataspaceLib.H5Sselect_elements(
	            spaceId, 
	            DataspaceSelectionOPType.H5S_SELECT_SET, 
	            coords.length, 
	            coords
	    );
	    assertTrue(selectStatus >= 0);
	    
	    int size = DataspaceLib.H5Sget_select_elem_npoints(spaceId);
	    assertTrue(size == coords.length);
	    
	    int closeStatus = DataspaceLib.H5Sclose(spaceId);
	    assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sselect_hyperslab() {
	    long[] dims = new long[]{8, 8};
        long[] maxdims = new long[] {8, 8};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);

        int selectStatus = DataspaceLib.H5Sselect_hyperslab(
                spaceId, 
                DataspaceSelectionOPType.H5S_SELECT_SET, 
                new long[]{0, 0}, 
                new long[]{2, 2}, 
                new long[]{4, 4}, 
                new long[]{1, 1}
        );
        assertTrue(selectStatus >= 0);
        
        int nBlocks = DataspaceLib.H5Sget_select_hyper_nblocks(spaceId);
        assertEquals(nBlocks, 16);                            
        
        int closeStatus = DataspaceLib.H5Sclose(spaceId);
        assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sselect_none() {
	    long[] dims = new long[]{3, 3};
        long[] maxdims = new long[] {4, 4};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);

        int selectStatus = DataspaceLib.H5Sselect_none(spaceId);
        assertTrue(selectStatus >= 0);
        
        DataspaceSelectionType type = DataspaceLib.H5Sget_select_type(spaceId);
        assertEquals(DataspaceSelectionType.H5S_SEL_NONE, type);
                            
        int closeStatus = DataspaceLib.H5Sclose(spaceId);
        assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sselect_valid() {
	    long[] dims = new long[]{3, 3};
        long[] maxdims = new long[] {4, 4};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);

        long[][] selection = new long[][]{{5,5},{6,6},{7,7}};
        int selectStatus = DataspaceLib.H5Sselect_elements(
                spaceId, 
                DataspaceSelectionOPType.H5S_SELECT_SET, 
                3, 
                selection
        );
        assertTrue(selectStatus >= 0);
        
        int validStatus = DataspaceLib.H5Sselect_valid(spaceId);
        assertTrue(validStatus == 0);
        
        selection = new long[][]{{1,1},{2,2},{3,3}};
        selectStatus = DataspaceLib.H5Sselect_elements(
                spaceId, 
                DataspaceSelectionOPType.H5S_SELECT_SET, 
                3, 
                selection
        );
        assertTrue(selectStatus >= 0);
        
        validStatus = DataspaceLib.H5Sselect_valid(spaceId);
        assertTrue(validStatus > 0);
                    
        int closeStatus = DataspaceLib.H5Sclose(spaceId);
        assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sset_extent_none() {
	    long[] dims = new long[]{3, 3};
        long[] maxdims = new long[] {4, 4};
        int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
        assertTrue(spaceId >= 0);

        DataspaceClassType type = DataspaceLib.H5Sget_simple_extent_type(spaceId);
        assertEquals(DataspaceClassType.H5S_SIMPLE, type);
        
        int setId = DataspaceLib.H5Sset_extent_none(spaceId);
        assertTrue(setId >= 0);
                    
        type = DataspaceLib.H5Sget_simple_extent_type(spaceId);
        assertEquals(DataspaceClassType.H5S_NO_CLASS, type);
        
        int closeStatus = DataspaceLib.H5Sclose(spaceId);
        assertTrue(closeStatus >= 0);
	}

	@Test
	public void testH5Sset_extent_simple() {
	    long[] dims = new long[]{3, 3};
	    long[] maxdims = new long[] {4, 4};
	    int spaceId = DataspaceLib.H5Screate_simple(dims.length, dims, maxdims);
	    assertTrue(spaceId >= 0);

	    DataspaceClassType type = DataspaceLib.H5Sget_simple_extent_type(spaceId);
	    assertEquals(DataspaceClassType.H5S_SIMPLE, type);

	    int setId = DataspaceLib.H5Sset_extent_simple(spaceId, maxdims.length, maxdims, maxdims);
	    assertTrue(setId >= 0);

	    long[] xdims = new long[2];
        long[] xmaxdims = new long[2];
        int ndims = DataspaceLib.H5Sget_simple_extent_dims(spaceId, xdims, xmaxdims);
        assertEquals(2, ndims);
        
        assertArrayEquals(maxdims, xdims);
        assertArrayEquals(maxdims, xmaxdims);

	    int closeStatus = DataspaceLib.H5Sclose(spaceId);
	    assertTrue(closeStatus >= 0);
	}

}
