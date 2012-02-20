package permafrost.hdf.libhdf;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestImageLib {

	private static final short[] image = { 
		000, 000, 000, 010, 010, 000, 000, 000, 
		000, 001, 001, 020, 020, 001, 001, 000,
		000, 001, 001, 050, 050, 001, 001, 000,
		010, 020, 050, 100, 100, 050, 020, 010,
		010, 020, 050, 100, 100, 050, 020, 010,
		000, 001, 001, 050, 050, 001, 001, 000,
		000, 001, 001, 020, 020, 001, 001, 000,
		000, 000, 000, 010, 010, 000, 000, 000
	};	
	private static final int img_height = 8;
	private static final int img_width = 8;
	private static final int pal_entries = 256;
	private static final int pal_size = pal_entries*3;
	private static final short[] palette = new short[pal_size];
	static {
	    for (int i=0, n=0; i<pal_size; i+=3, n++) {
	        palette[i]= (short) n;      /* red */
	        palette[i+1] = (short) 0;      /* green */
	        palette[i+2] = (short) (255-n);  /* blue */
	    }
	}
	
	
	
    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestImageLib.class);

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
//        PropertiesLib.H5Pset_libver_bounds(plFAccess, FileLibVersionType.H5F_LIBVER_LATEST, FileLibVersionType.H5F_LIBVER_LATEST);
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
	
	
	@Test
	public void testH5IMmake_image_8bit() {
		final String imgName = "img8";
		int createStatus = ImageLib.H5IMmake_image_8bit(
				this.fid, 
				imgName, 
				TestImageLib.img_width,
				TestImageLib.img_height, 
				TestImageLib.image);
		assertTrue(createStatus >= 0);
		
		short[] buffer = new short[TestImageLib.img_width*TestImageLib.img_height]; 
		int readStatus = LiteLib.H5LTread_dataset_short(this.fid, imgName, buffer);
		assertTrue(readStatus >= 0);
		
		assertArrayEquals(TestImageLib.image, buffer);
	}

	@Test
	public void testH5IMmake_image_8bit_direct() {
		final String imgName = "img8";
		ShortBuffer image = ByteBuffer.allocateDirect(TestImageLib.img_width*TestImageLib.img_height*2).order(ByteOrder.nativeOrder()).asShortBuffer();
		image.put(TestImageLib.image);
		int createStatus = ImageLib.H5IMmake_image_8bit_direct(
				this.fid, 
				imgName, 
				TestImageLib.img_width,
				TestImageLib.img_height, 
				image);
		assertTrue(createStatus >= 0);
		
		ShortBuffer buffer = ByteBuffer.allocateDirect(TestImageLib.img_width*TestImageLib.img_height*2).order(ByteOrder.nativeOrder()).asShortBuffer(); 
		int readStatus = LiteLib.H5LTread_dataset_short_direct(this.fid, imgName, buffer);
		assertTrue(readStatus >= 0);
		
		short[] array = new short[TestImageLib.img_width*TestImageLib.img_height];
		buffer.get(array);
		assertArrayEquals(TestImageLib.image, array);
	}

	@Test
	public void testH5IMmake_image_24bit() {
	    int nPx= TestImageLib.img_width*TestImageLib.img_height;
	    short[] rgb = new short[nPx*3];
	    System.arraycopy(TestImageLib.image, 0, rgb, 0, nPx);
	    System.arraycopy(TestImageLib.image, 0, rgb, nPx, nPx);
	    System.arraycopy(TestImageLib.image, 0, rgb, 2*nPx, nPx);
	    
	    final String imgName = "img24";
        int createStatus = ImageLib.H5IMmake_image_24bit(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                ImageLib.INTERLACE_PLANE,
                rgb);
        assertTrue(createStatus >= 0);
        
        short[] buffer = new short[nPx*3]; 
        int readStatus = LiteLib.H5LTread_dataset_short(this.fid, imgName, buffer);
        assertTrue(readStatus >= 0);
        
        assertArrayEquals(rgb, buffer);
	}

	@Test
	public void testH5IMmake_image_24bit_direct() {
	    int nPx = TestImageLib.img_width*TestImageLib.img_height;
	    ShortBuffer rgb = ByteBuffer.allocateDirect(3*nPx*2).order(ByteOrder.nativeOrder()).asShortBuffer();
	    rgb.put(TestImageLib.image);
	    rgb.put(TestImageLib.image);
	    rgb.put(TestImageLib.image);

        final String imgName = "img24";
        int createStatus = ImageLib.H5IMmake_image_24bit_direct(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                ImageLib.INTERLACE_PLANE,
                rgb);
        assertTrue(createStatus >= 0);
        
        short[] buffer = new short[nPx*3]; 
        int readStatus = LiteLib.H5LTread_dataset_short(this.fid, imgName, buffer);
        assertTrue(readStatus >= 0);
        
        rgb.rewind();
        short[] argb = new short[nPx*3];
        rgb.get(argb);
        assertArrayEquals(argb, buffer);
	}

	@Test
	public void testH5IMget_image_info() {
	    int nPx= TestImageLib.img_width*TestImageLib.img_height;
        short[] rgb = new short[nPx*3];
        System.arraycopy(TestImageLib.image, 0, rgb, 0, nPx);
        System.arraycopy(TestImageLib.image, 0, rgb, nPx, nPx);
        System.arraycopy(TestImageLib.image, 0, rgb, 2*nPx, nPx);
        
        final String imgName = "img24";
        int createStatus = ImageLib.H5IMmake_image_24bit(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                ImageLib.INTERLACE_PLANE,
                rgb);
        assertTrue(createStatus >= 0);		
		
		long[] width = new long[1];
		long[] height = new long[1];
		long[] planes = new long[1];
		byte[] interlace = new byte[16];
		long[] npals = new long[1];
		int getStatus = ImageLib.H5IMget_image_info(
				this.fid, 
				imgName, 
				width, 
				height,
				planes, 
				interlace, 
				npals
		);
		assertTrue(getStatus >= 0);
		
		assertEquals(TestImageLib.img_width, width[0]);
		assertEquals(TestImageLib.img_height, height[0]);
		assertEquals(3, planes[0]);
		assertEquals(0, npals[0]);
		assertEquals(ImageLib.INTERLACE_PLANE, StringUtils.fromNullTerm(interlace));
	}

	@Test
	public void testH5IMread_image() {
	    int nPx= TestImageLib.img_width*TestImageLib.img_height;
        short[] rgb = new short[nPx*3];
        System.arraycopy(TestImageLib.image, 0, rgb, 0, nPx);
        System.arraycopy(TestImageLib.image, 0, rgb, nPx, nPx);
        System.arraycopy(TestImageLib.image, 0, rgb, 2*nPx, nPx);
        
        final String imgName = "img24";
        int createStatus = ImageLib.H5IMmake_image_24bit(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                new String(ImageLib.INTERLACE_PIXEL),
                rgb);
        assertTrue(createStatus >= 0);
        
        short[] buffer = new short[nPx*3]; 
        int readStatus = ImageLib.H5IMread_image(this.fid, imgName, buffer);
        assertTrue(readStatus >= 0);
        
        assertArrayEquals(rgb, buffer);
	}

	@Test
	public void testH5IMread_image_direct() {
	    int nPx = TestImageLib.img_width*TestImageLib.img_height;
        ShortBuffer rgb = ByteBuffer.allocateDirect(3*nPx*2).order(ByteOrder.nativeOrder()).asShortBuffer();
        rgb.put(TestImageLib.image);
        rgb.put(TestImageLib.image);
        rgb.put(TestImageLib.image);

        final String imgName = "img24";
        int createStatus = ImageLib.H5IMmake_image_24bit_direct(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                ImageLib.INTERLACE_PLANE,
                rgb);
        assertTrue(createStatus >= 0);
        
        ShortBuffer buffer = ByteBuffer.allocateDirect(3*nPx*2).order(ByteOrder.nativeOrder()).asShortBuffer();
        int readStatus = ImageLib.H5IMread_image_direct(this.fid, imgName, buffer);
        assertTrue(readStatus >= 0);
        
        rgb.rewind();
        short[] argb = new short[nPx*3];
        rgb.get(argb);
        buffer.rewind();
        short[] abuffer = new short[nPx*3];
        buffer.get(abuffer);
        assertArrayEquals(argb, abuffer);
	}

	@Test
	public void testH5IMmake_palette() {
	    String palName = "palette";
		int makeStatus = ImageLib.H5IMmake_palette(this.fid, palName, new long[]{TestImageLib.pal_entries, 3}, TestImageLib.palette);
		assertTrue(makeStatus >= 0);
		
		short[] buffer = new short[TestImageLib.pal_size]; 
        int readStatus = LiteLib.H5LTread_dataset_short(this.fid, palName, buffer);
        assertTrue(readStatus >= 0);
        
        assertArrayEquals(TestImageLib.palette, buffer);
	}

	@Test
	public void testH5IMlink_palette() {
	    final String imgName = "img8";
        int createStatus = ImageLib.H5IMmake_image_8bit(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                TestImageLib.image);
        assertTrue(createStatus >= 0);
        
        String palName = "palette";
        int makeStatus = ImageLib.H5IMmake_palette(this.fid, palName, new long[]{TestImageLib.pal_entries, 3}, TestImageLib.palette);
        assertTrue(makeStatus >= 0);
        
        int linkStatus = ImageLib.H5IMlink_palette(this.fid, imgName, palName);
        assertTrue(linkStatus >= 0);
        
        long[] width = new long[1];
        long[] height = new long[1];
        long[] planes = new long[1];
        byte[] interlace = new byte[16];
        long[] npals = new long[1];
        int getStatus = ImageLib.H5IMget_image_info(
                this.fid, 
                imgName, 
                width, 
                height,
                planes, 
                interlace, 
                npals
        );
        assertTrue(getStatus >= 0);
        
        assertEquals(1, npals[0]);
	}

	@Test
	public void testH5IMunlink_palette() {
	    final String imgName = "img8";
        int createStatus = ImageLib.H5IMmake_image_8bit(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                TestImageLib.image);
        assertTrue(createStatus >= 0);
        
        String palName = "palette";
        int makeStatus = ImageLib.H5IMmake_palette(this.fid, palName, new long[]{TestImageLib.pal_entries, 3}, TestImageLib.palette);
        assertTrue(makeStatus >= 0);
        
        int linkStatus = ImageLib.H5IMlink_palette(this.fid, imgName, palName);
        assertTrue(linkStatus >= 0);
        
        long[] width = new long[1];
        long[] height = new long[1];
        long[] planes = new long[1];
        byte[] interlace = new byte[16];
        long[] npals = new long[1];
        int getStatus = ImageLib.H5IMget_image_info(
                this.fid, 
                imgName, 
                width, 
                height,
                planes, 
                interlace, 
                npals
        );
        assertTrue(getStatus >= 0);        
        assertEquals(1, npals[0]);
        
        linkStatus = ImageLib.H5IMunlink_palette(this.fid, imgName, palName);
        assertTrue(linkStatus >= 0);
        
        getStatus = ImageLib.H5IMget_image_info(
                this.fid, 
                imgName, 
                width, 
                height,
                planes, 
                interlace, 
                npals
        );
        assertTrue(getStatus >= 0);        
        assertEquals(0, npals[0]);
	}

	@Test
	public void testH5IMget_npalettes() {
	    final String imgName = "img8";
        int createStatus = ImageLib.H5IMmake_image_8bit(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                TestImageLib.image);
        assertTrue(createStatus >= 0);
        
        String palName = "palette";
        int makeStatus = ImageLib.H5IMmake_palette(this.fid, palName, new long[]{TestImageLib.pal_entries, 3}, TestImageLib.palette);
        assertTrue(makeStatus >= 0);
        
        int linkStatus = ImageLib.H5IMlink_palette(this.fid, imgName, palName);
        assertTrue(linkStatus >= 0);
        
        long[] npals = new long[1];
        int getStatus = ImageLib.H5IMget_npalettes(this.fid, imgName, npals);
        assertTrue(getStatus >= 0);
        
        assertEquals(1, npals[0]);
	}

	@Test
	public void testH5IMget_palette_info() {
	    final String imgName = "img8";
        int createStatus = ImageLib.H5IMmake_image_8bit(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                TestImageLib.image);
        assertTrue(createStatus >= 0);
        
        String palName = "palette";
        long[] palDims = new long[]{TestImageLib.pal_entries, 3};
        int makeStatus = ImageLib.H5IMmake_palette(this.fid, palName, palDims, TestImageLib.palette);
        assertTrue(makeStatus >= 0);
        
        int linkStatus = ImageLib.H5IMlink_palette(this.fid, imgName, palName);
        assertTrue(linkStatus >= 0);
        
        long[] pal_dims = new long[2];
        int getStatus = ImageLib.H5IMget_palette_info(this.fid, imgName, 0, pal_dims);
        assertTrue(getStatus >= 0);
//        assertArrayEquals(palDims, pal_dims);
	}

	@Test
	public void testH5IMget_palette() {
	    final String imgName = "img8";
        int createStatus = ImageLib.H5IMmake_image_8bit(
                this.fid, 
                imgName, 
                TestImageLib.img_width,
                TestImageLib.img_height, 
                TestImageLib.image);
        assertTrue(createStatus >= 0);
        
        String palName = "palette";
        int makeStatus = ImageLib.H5IMmake_palette(this.fid, palName, new long[]{TestImageLib.pal_entries, 3}, TestImageLib.palette);
        assertTrue(makeStatus >= 0);
        
        int linkStatus = ImageLib.H5IMlink_palette(this.fid, imgName, palName);
        assertTrue(linkStatus >= 0);
        
        short[] buffer = new short[TestImageLib.pal_size]; 
        int readStatus = ImageLib.H5IMget_palette(this.fid, imgName, 0, buffer);
        assertTrue(readStatus >= 0);
        
        assertArrayEquals(TestImageLib.palette, buffer);
	}

	@Test
	public void testH5IMis_image() {
		final String imgName = "img8";
		int createStatus = ImageLib.H5IMmake_image_8bit(
				this.fid, 
				imgName, 
				TestImageLib.img_width,
				TestImageLib.img_height, 
				TestImageLib.image);
		assertTrue(createStatus >= 0);
		
		int isImage = ImageLib.H5IMis_image(this.fid, imgName);
		assertEquals(1, isImage);
	}

	@Test
	public void testH5IMis_palette() {
	    String palName = "palette";
        int makeStatus = ImageLib.H5IMmake_palette(this.fid, palName, new long[]{TestImageLib.pal_entries, 3}, TestImageLib.palette);
        assertTrue(makeStatus >= 0);
        
        int isPalette = ImageLib.H5IMis_palette(this.fid, palName);
        assertEquals(1, isPalette);
	}

}
