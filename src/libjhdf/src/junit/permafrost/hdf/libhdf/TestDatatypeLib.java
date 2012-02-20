/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javolution.io.Struct;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class TestDatatypeLib {

	private static enum TestEnum {
		True,
		False,
		FILE_NOT_FOUND;
	}
	
	private class TestStruct extends Struct {
		public Signed32 int32 = new Signed32();
		public Float64 float64 = new Float64();				
	}
	
	private class TestPaddedStruct extends Struct {
	        public Signed32 int32 = new Signed32();
	        public Signed8 int8 = new Signed8();
	        public Float64 float64 = new Float64();             
	}

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
        if (this.fid > 0) {
            FileLib.H5Fclose(this.fid);
            this.fid = 0;
        }
    }
	
	
	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tarray_create1(int, int, long[], int[])}.
	 */
	
    @Test
    @SuppressWarnings("deprecation")
	public void testH5Tarray_create1() {
    	final long eSize = 8;
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create1(tid, 1, new long[]{eSize}, new int[1]);
		assertTrue(atid >= 0);
		
		long size = DatatypeLib.H5Tget_size(atid);
		assertEquals(8, size);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tarray_create2(int, long, long[])}.
	 */
	@Test
	public void testH5Tarray_create2() {
    	final long eSize = 8;
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, 1,new long[]{eSize});
		assertTrue(atid >= 0);
		
		long size = DatatypeLib.H5Tget_size(atid);
		assertEquals(8, size);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}


	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tcommit1(int, java.lang.String, int)}.
	 */
	@Test
	@SuppressWarnings("deprecation")	
	public void testH5Tcommit1() {
    	final long eSize = 8;
    	final String name = "array_type";
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, 1,new long[]{eSize});
		assertTrue(atid >= 0);
		
		int commitStatus = DatatypeLib.H5Tcommit1(this.fid, name, atid);
		assertTrue(commitStatus >= 0);		
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
		atid = 0;
		
		closeStatus = FileLib.H5Fclose(this.fid);
		assertTrue(closeStatus >= 0);
		this.fid = 0;
		
		int plFAccess = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_FILE_ACCESS_CLASS());
		assertTrue("Invalid file access property list.", plFAccess >= 0);
		PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
		this.fid = FileLib.H5Fopen("test.h5", 
				(FileLibConstants.H5F_ACC_RDWR | FileLibConstants.H5F_ACC_DEBUG),  
				PropertiesLibConstants.H5P_DEFAULT
		);
		assertTrue("Invalid file identifier.", this.fid >= 0);
		
		atid = DatatypeLib.H5Topen2(this.fid, name, PropertiesLib.H5P_DEFAULT);
		
		long size = DatatypeLib.H5Tget_size(atid);
		assertEquals(8, size);
		
		closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tcommit2(int, java.lang.String, int, int, int, int)}.
	 */
	@Test
	public void testH5Tcommit2() {
    	final long eSize = 8;
    	final String name = "array_type";
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, 1,new long[]{eSize});
		assertTrue(atid >= 0);
		
		int commitStatus = DatatypeLib.H5Tcommit2(
				this.fid, 
				name, 
				atid, 
				PropertiesLib.H5P_DEFAULT, 
				PropertiesLib.H5P_DEFAULT, 
				PropertiesLib.H5P_DEFAULT
		);
		assertTrue(commitStatus >= 0);		
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
		atid = 0;
		
		closeStatus = FileLib.H5Fclose(this.fid);
		assertTrue(closeStatus >= 0);
		this.fid = 0;
		
		int plFAccess = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_FILE_ACCESS_CLASS());
		assertTrue("Invalid file access property list.", plFAccess >= 0);
		PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
		this.fid = FileLib.H5Fopen("test.h5", 
				(FileLibConstants.H5F_ACC_RDWR | FileLibConstants.H5F_ACC_DEBUG),  
				PropertiesLibConstants.H5P_DEFAULT
		);
		assertTrue("Invalid file identifier.", this.fid >= 0);
		
		atid = DatatypeLib.H5Topen2(this.fid, name, PropertiesLib.H5P_DEFAULT);
		
		long size = DatatypeLib.H5Tget_size(atid);
		assertEquals(8, size);
		
		closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tcommit_anon(int, int, int, int)}.
	 */
	@Test
	public void testH5Tcommit_anon() {
		final long eSize = 8;
    	final String name = "array_type";
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, 1,new long[]{eSize});
		assertTrue(atid >= 0);
		
		int commitStatus = DatatypeLib.H5Tcommit_anon(
				this.fid, 
				atid, 
				PropertiesLib.H5P_DEFAULT, 
				PropertiesLib.H5P_DEFAULT
		);
		assertTrue(commitStatus >= 0);		
		
		commitStatus = LinkLib.H5Lcreate_hard(
				atid,
				".",
				this.fid,
				name,
				PropertiesLib.H5P_DEFAULT,
				PropertiesLib.H5P_DEFAULT
		);
		assertTrue(commitStatus >= 0);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
		atid = 0;
		
		closeStatus = FileLib.H5Fclose(this.fid);
		assertTrue(closeStatus >= 0);
		this.fid = 0;
		
		int plFAccess = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_FILE_ACCESS_CLASS());
		assertTrue("Invalid file access property list.", plFAccess >= 0);
		PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
		this.fid = FileLib.H5Fopen("test.h5", 
				(FileLibConstants.H5F_ACC_RDWR | FileLibConstants.H5F_ACC_DEBUG),  
				PropertiesLibConstants.H5P_DEFAULT
		);
		assertTrue("Invalid file identifier.", this.fid >= 0);
		
		atid = DatatypeLib.H5Topen2(this.fid, name, PropertiesLib.H5P_DEFAULT);
		
		long size = DatatypeLib.H5Tget_size(atid);
		assertEquals(8, size);
		
		closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tcommitted(int)}.
	 */
	@Test
	public void testH5Tcommitted() {
    	final long eSize = 8;
    	final String name = "array_type";
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, 1,new long[]{eSize});
		assertTrue(atid >= 0);
		
		int committed = DatatypeLib.H5Tcommitted(atid);
		assertEquals(0, committed);
		
		int commitStatus = DatatypeLib.H5Tcommit2(
				this.fid, 
				name, 
				atid, 
				PropertiesLib.H5P_DEFAULT, 
				PropertiesLib.H5P_DEFAULT, 
				PropertiesLib.H5P_DEFAULT
		);
		assertTrue(commitStatus >= 0);		
		
		committed = DatatypeLib.H5Tcommitted(atid);
		assertEquals(1, committed);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);

	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tcompiler_conv(int, int)}.
	 */
	@Test
	public void testH5Tcompiler_conv() {
		int schar_id = DatatypeLib.getH5T_NATIVE_SCHAR();
		int uchar_id = DatatypeLib.getH5T_NATIVE_UCHAR();
		int convStatus = DatatypeLib.H5Tcompiler_conv(schar_id, uchar_id);
		assertEquals(1, convStatus);
		
		int le_id = DatatypeLib.getH5T_STD_B64LE();
		int be_id = DatatypeLib.getH5T_STD_B64LE();
		convStatus = DatatypeLib.H5Tcompiler_conv(le_id, be_id);
		assertEquals(0, convStatus);		
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tconvert(int, int, long, permafrost.hdf.libhdf.SWIGTYPE_p_void, permafrost.hdf.libhdf.SWIGTYPE_p_void, int)}.
	 */
	@Test
	public void testH5Tconvert() {
		int idI32BE = DatatypeLib.getH5T_STD_I32BE();
		int idI32LE = DatatypeLib.getH5T_STD_I32LE();
		
		final int size = 8;
		ByteBuffer buffer = ByteBuffer.allocateDirect(size*4).order(ByteOrder.BIG_ENDIAN);
		for (int n=0; n<size; n++) {
			buffer.putInt(n);
		}		
		ByteBuffer background = ByteBuffer.allocateDirect(1);
		int convertStatus = DatatypeLib.H5Tconvert(
				idI32BE, 
				idI32LE, 
				size, 
				buffer, 
				background, 
				PropertiesLib.H5P_DEFAULT
		);
		assertTrue(convertStatus >= 0);
		buffer.rewind();
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int n=0; n<size; n++) {			
			assertEquals(n, buffer.getInt(n*4));
		}				
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tcopy(int)}.
	 */
	@Test
	public void testH5Tcopy() {
		DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		long eBits = 32;
		DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
		DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
		
		int tid = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_STD_I32LE());
		assertTrue(tid >= 0);
		DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		assertEquals(eClass, type);
		long precision = DatatypeLib.H5Tget_precision(tid);
		assertEquals(eBits, precision);
		DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		assertEquals(eOrder, order);
		DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		assertEquals(eSign, sign);
		
		int closeStatus = DatatypeLib.H5Tclose(tid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tcreate(permafrost.hdf.libhdf.DatatypeClassType, long)}.
	 */
	@Test
	public void testH5Tcreate() {
		int atid = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_OPAQUE, 8);		
		assertTrue(atid >= 0);
		
		long size = DatatypeLib.H5Tget_size(atid);
		assertEquals(8, size);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tdecode(permafrost.hdf.libhdf.SWIGTYPE_p_void)}.
	 */
	@Test
	public void testH5Tdecode() {
		final int eSize = 8;
		int atid = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_OPAQUE, eSize);		
		assertTrue(atid >= 0);
		final int size = 1;
		ByteBuffer buffer = ByteBuffer.allocateDirect(size);
		long[] nalloc = new long[] {size};
		int encStatus = DatatypeLib.H5Tencode(atid, buffer, nalloc);
		//assertFalse(encStatus >= 0); 
		assertTrue(nalloc[0] > size);
		
		buffer = ByteBuffer.allocateDirect((int) nalloc[0]);
		encStatus = DatatypeLib.H5Tencode(atid, buffer, nalloc);
		assertTrue(encStatus >= 0);				
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
		atid = 0;
		
		atid = DatatypeLib.H5Tdecode(buffer);
		long tSize = DatatypeLib.H5Tget_size(atid);
		assertEquals(eSize, tSize);
		
		closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tdetect_class(int, permafrost.hdf.libhdf.DatatypeClassType)}.
	 */
	@Test
	public void testH5Tdetect_class() {
		final long eSize = 8;
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, 1,new long[]{eSize});
		assertTrue(atid >= 0);
		
		int hasBits = DatatypeLib.H5Tdetect_class(atid, DatatypeClassType.H5T_BITFIELD);
		assertTrue(hasBits > 0);
		
		int hasFloats = DatatypeLib.H5Tdetect_class(atid, DatatypeClassType.H5T_FLOAT);
		assertFalse(hasFloats > 0);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}


	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tenum_create(int)}.
	 */
	@Test
	public void testH5Tenum_create() {		
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_NATIVE_INT());
		assertTrue(idEnum >= 0);
		DatatypeClassType type = DatatypeLib.H5Tget_class(idEnum);
		assertEquals(DatatypeClassType.H5T_ENUM, type);
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tenum_insert(int, java.lang.String, permafrost.hdf.libhdf.SWIGTYPE_p_void)}.
	 */
	@Test
	public void testH5Tenum_insert() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_NATIVE_INT());
		assertTrue(idEnum >= 0);
		for (TestEnum value : TestEnum.values()) {
			int insertStatus = DatatypeLib.H5Tenum_insert(
					idEnum, 
					value.name(), 
					value.ordinal()
			);
			assertTrue(insertStatus >= 0);
		}
		
		int size = DatatypeLib.H5Tget_nmembers(idEnum);
		assertEquals(TestEnum.values().length, size);
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);
	}
	
	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tenum_insert_direct(int, java.lang.String, permafrost.hdf.libhdf.SWIGTYPE_p_void)}.
	 */
	@Test
	public void testH5Tenum_insert_direct() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_STD_I64BE());
		assertTrue(idEnum >= 0);
		ByteBuffer buff = ByteBuffer.allocateDirect(64);
		
		for (TestEnum value : TestEnum.values()) {
			buff.putLong(0, value.ordinal());
			int insertStatus = DatatypeLib.H5Tenum_insert_direct(idEnum, value.name(), buff);
			assertTrue(insertStatus >= 0);
		}
		
		int size = DatatypeLib.H5Tget_nmembers(idEnum);
		assertEquals(TestEnum.values().length, size);
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tenum_nameof(int, long[], byte[], long)}.
	 */
	@Test
	public void testH5Tenum_nameof() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_NATIVE_INT());
		assertTrue(idEnum >= 0);
		for (TestEnum value : TestEnum.values()) {
			int insertStatus = DatatypeLib.H5Tenum_insert(
					idEnum, 
					value.name(), 
					value.ordinal()
			);
			assertTrue(insertStatus >= 0);
		}
		
		final int length = TestEnum.FILE_NOT_FOUND.name().length()+1;
		byte[] name = new byte[length];
		for (TestEnum value : TestEnum.values()) {			
			int status = DatatypeLib.H5Tenum_nameof(idEnum, value.ordinal(), name, length);
			assertTrue(status >= 0);
			assertEquals(value.name(), StringUtils.fromNullTerm(name));
		}
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);
	}
	
	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tenum_nameof_direct(int, long[], byte[], long)}.
	 */
	@Test
	public void testH5Tenum_nameof_direct() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_STD_I64BE());
		assertTrue(idEnum >= 0);
		ByteBuffer buff = ByteBuffer.allocateDirect(64);
		
		for (TestEnum value : TestEnum.values()) {
			buff.putLong(0, value.ordinal());
			int insertStatus = DatatypeLib.H5Tenum_insert_direct(idEnum, value.name(), buff);
			assertTrue(insertStatus >= 0);
		}
		
		final int length = TestEnum.FILE_NOT_FOUND.name().length()+1;
		byte[] name = new byte[length];
		
		for (TestEnum value : TestEnum.values()) {			
			buff.putLong(0, value.ordinal());
			int status = DatatypeLib.H5Tenum_nameof_direct(idEnum, buff, name, length);
			assertTrue(status >= 0);
			assertEquals(value.name(), StringUtils.fromNullTerm(name));
		}
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tenum_valueof(int, java.lang.String, long[])}.
	 */
	@Test
	public void testH5Tenum_valueof() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_NATIVE_INT());
		assertTrue(idEnum >= 0);
		for (TestEnum value : TestEnum.values()) {
			int insertStatus = DatatypeLib.H5Tenum_insert(
					idEnum, 
					value.name(), 
					value.ordinal()
			);
			assertTrue(insertStatus >= 0);
		}
		
		int[] ordinal = new int[1];
		for (TestEnum value : TestEnum.values()) {			
			int status = DatatypeLib.H5Tenum_valueof(idEnum, value.name(), ordinal);
			assertTrue(status >= 0);
			assertEquals(value.ordinal(), ordinal[0]);
		}
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tenum_valueof_direct(int, java.lang.String, long[])}.
	 */
	@Test
	public void testH5Tenum_valueof_direct() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_STD_I64BE());
		assertTrue(idEnum >= 0);
		ByteBuffer buff = ByteBuffer.allocateDirect(64);
		
		for (TestEnum value : TestEnum.values()) {
			buff.putLong(0, value.ordinal());
			int insertStatus = DatatypeLib.H5Tenum_insert_direct(idEnum, value.name(), buff);
			assertTrue(insertStatus >= 0);
		}
		
		for (TestEnum value : TestEnum.values()) {						
			int status = DatatypeLib.H5Tenum_valueof_direct(idEnum, value.name(), buff);
			assertTrue(status >= 0);
			assertEquals(value.ordinal(), buff.getLong(0));
		}
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);		
	}
	
	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tequal(int, int)}.
	 */
	@Test
	public void testH5Tequal() {
		int idBuiltin = DatatypeLib.getH5T_STD_I32LE();
		int idCopy = DatatypeLib.H5Tcopy(idBuiltin);
		int equal = DatatypeLib.H5Tequal(idBuiltin, idCopy);
		assertEquals(1, equal);
		
		int closeStatus = DatatypeLib.H5Tclose(idCopy);
		assertTrue(closeStatus >= 0);
	}


	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_array_dims1(int, long[], int[])}.
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void testH5Tget_array_dims1() {
		final long eSize = 8;
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, 2, new long[]{eSize, eSize});
		assertTrue(atid >= 0);
		
		long[] dims = new long[2];
		int[] perm = new int[2];
		int getStatus = DatatypeLib.H5Tget_array_dims1(atid, dims, perm);
		assertTrue(getStatus >= 0);
		assertEquals(eSize, dims[0]);
		assertEquals(eSize, dims[1]);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_array_dims2(int, long[])}.
	 */
	@Test
	public void testH5Tget_array_dims2() {
		final long eSize = 8;
	    int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, 2, new long[]{eSize, eSize});
		assertTrue(atid >= 0);
		
		long[] dims = new long[2];
		int getStatus = DatatypeLib.H5Tget_array_dims2(atid, dims);
		assertTrue(getStatus >= 0);
		assertEquals(eSize, dims[0]);
		assertEquals(eSize, dims[1]);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_array_ndims(int)}.
	 */
	@Test
	public void testH5Tget_array_ndims() {
		final long eSize = 8;
	    final int eDims = 2;
		int tid = DatatypeLib.getH5T_NATIVE_B8();	    
		int atid = DatatypeLib.H5Tarray_create2(tid, eDims, new long[]{eSize, eSize});
		assertTrue(atid >= 0);
		
		int dims = DatatypeLib.H5Tget_array_ndims(atid);
		assertEquals(eDims, dims);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_class(int)}.
	 */
	@Test
	public void testH5Tget_class() {
		DatatypeClassType type = DatatypeLib.H5Tget_class(DatatypeLib.getH5T_NATIVE_INT());
		assertEquals(DatatypeClassType.H5T_INTEGER, type);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_create_plist(int)}.
	 */
	@Test
	public void testH5Tget_create_plist() {
		int eIdCList = PropertiesLib.getH5P_DATATYPE_CREATE_LIST();
		assertTrue(eIdCList >= 0);
		int atid = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_OPAQUE, 8);		
		assertTrue(atid >= 0);
		
		int idCList = DatatypeLib.H5Tget_create_plist(atid);
		assertTrue(idCList >= 0);
		int equal = PropertiesLib.H5Pequal(eIdCList, idCList);
		assertEquals(1, equal);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_cset(int)}.
	 */
	@Test
	public void testH5Tget_cset() {
		DatatypeCharsetType eCSet = DatatypeCharsetType.H5T_CSET_UTF8;
		int tid = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_C_S1());
	    assertTrue(tid >= 0);
	    
	    int setId = DatatypeLib.H5Tset_cset(tid, eCSet);
	    assertTrue(setId >= 0);
	    
        DatatypeCharsetType cset = DatatypeLib.H5Tget_cset(tid);
        assertEquals(eCSet, cset);
        
		int closeStatus = DatatypeLib.H5Tclose(tid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_ebias(int)}.
	 */
	@Test
	public void testH5Tget_ebias() {
		int tid = DatatypeLib.getH5T_IEEE_F32LE();
		long ebias = DatatypeLib.H5Tget_ebias(tid);
		assertEquals(127, ebias);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_fields(int, int[], int[], int[], int[], int[])}.
	 */
	@Test
	public void testH5Tget_fields() {
		int tid = DatatypeLib.getH5T_IEEE_F32BE();
		long[] spos = new long[1];
		long[] epos = new long[1];
		long[] esize = new long[1];
		long[] mpos = new long[1];
		long[] msize = new long[1];
		int getStatus = DatatypeLib.H5Tget_fields(tid, spos, epos, esize, mpos, msize);
		assertTrue(getStatus >= 0);
		
		assertEquals(31, spos[0]);
		assertEquals(23, epos[0]);
		assertEquals(8, esize[0]);
		assertEquals(0, mpos[0]);
		assertEquals(23, msize[0]);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_inpad(int)}.
	 */
	@Test
	public void testH5Tget_inpad() {
		int tid = DatatypeLib.getH5T_IEEE_F32BE();
		DatatypeBitPadType padding = DatatypeLib.H5Tget_inpad(tid);
		assertEquals(DatatypeBitPadType.H5T_PAD_ZERO, padding);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_member_class(int, long)}.
	 */
	@Test
	public void testH5Tget_member_class() {
		TestStruct test = new TestStruct();
		test.setByteBuffer(ByteBuffer.allocateDirect(test.size()), 0);		
		int idStruct = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_COMPOUND, test.size());
		assertTrue(idStruct >= 0);
		final String intName = "int32";
		int insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				intName, 
				test.int32.offset(), 
				DatatypeLib.getH5T_STD_I32BE()
		);
		assertTrue(insertStatus >= 0);
		final String floatName = "float64";
		insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				floatName, 
				test.float64.offset(), 
				DatatypeLib.getH5T_IEEE_F64BE()
		);
		assertTrue(insertStatus >= 0);
		
		DatatypeClassType type = DatatypeLib.H5Tget_member_class(idStruct, 0);
		assertEquals(DatatypeClassType.H5T_INTEGER, type);
		type = DatatypeLib.H5Tget_member_class(idStruct, 1);
		assertEquals(DatatypeClassType.H5T_FLOAT, type);
		
		int closeStatus = DatatypeLib.H5Tclose(idStruct);
		assertTrue(closeStatus >= 0);		
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_member_index(int, java.lang.String)}.
	 */
	@Test
	public void testH5Tget_member_index() {
		TestStruct test = new TestStruct();
		test.setByteBuffer(ByteBuffer.allocateDirect(test.size()), 0);		
		int idStruct = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_COMPOUND, test.size());
		assertTrue(idStruct >= 0);
		
		final String intName = "int32";
		int insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				intName, 
				test.int32.offset(), 
				DatatypeLib.getH5T_STD_I32BE()
		);
		assertTrue(insertStatus >= 0);
		final String floatName = "float64";
		insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				floatName, 
				test.float64.offset(), 
				DatatypeLib.getH5T_IEEE_F64BE()
		);
		assertTrue(insertStatus >= 0);
		
		int idx = DatatypeLib.H5Tget_member_index(idStruct, intName);
		assertEquals(0, idx);
		idx = DatatypeLib.H5Tget_member_index(idStruct, floatName);
		assertEquals(1, idx);
				
		int closeStatus = DatatypeLib.H5Tclose(idStruct);
		assertTrue(closeStatus >= 0);	
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_member_name(int, long)}.
	 */
	@Test
	public void testH5Tget_member_name() {
		TestStruct test = new TestStruct();
		test.setByteBuffer(ByteBuffer.allocateDirect(test.size()), 0);		
		int idStruct = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_COMPOUND, test.size());
		assertTrue(idStruct >= 0);
		
		final String intName = "int32";
		int insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				intName, 
				test.int32.offset(), 
				DatatypeLib.getH5T_STD_I32BE()
		);
		assertTrue(insertStatus >= 0);
		final String floatName = "float64";
		insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				floatName, 
				test.float64.offset(), 
				DatatypeLib.getH5T_IEEE_F64BE()
		);
		assertTrue(insertStatus >= 0);
		
		String name = DatatypeLib.H5Tget_member_name(idStruct, 0);
		assertEquals(intName, name);
		name = DatatypeLib.H5Tget_member_name(idStruct, 1);
		assertEquals(floatName, name);
				
		int closeStatus = DatatypeLib.H5Tclose(idStruct);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_member_offset(int, long)}.
	 */
	@Test
	public void testH5Tget_member_offset() {
		TestStruct test = new TestStruct();
		test.setByteBuffer(ByteBuffer.allocateDirect(test.size()), 0);		
		int idStruct = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_COMPOUND, test.size());
		assertTrue(idStruct >= 0);
		
		final String intName = "int32";
		int insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				intName, 
				test.int32.offset(), 
				DatatypeLib.getH5T_STD_I32BE()
		);
		assertTrue(insertStatus >= 0);
		final String floatName = "float64";
		insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				floatName, 
				test.float64.offset(), 
				DatatypeLib.getH5T_IEEE_F64BE()
		);
		assertTrue(insertStatus >= 0);
		
		long offset = DatatypeLib.H5Tget_member_offset(idStruct, 0);
		assertEquals(test.int32.offset(), offset);
		offset = DatatypeLib.H5Tget_member_offset(idStruct, 1);
		assertEquals(test.float64.offset(), offset);
				
		int closeStatus = DatatypeLib.H5Tclose(idStruct);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_member_type(int, long)}.
	 */
	@Test
	public void testH5Tget_member_type() {
		TestStruct test = new TestStruct();
		test.setByteBuffer(ByteBuffer.allocateDirect(test.size()), 0);		
		int idStruct = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_COMPOUND, test.size());
		assertTrue(idStruct >= 0);
		
		final String intName = "int32";
		int insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				intName, 
				test.int32.offset(), 
				DatatypeLib.getH5T_STD_I32BE()
		);
		assertTrue(insertStatus >= 0);
		final String floatName = "float64";
		insertStatus = DatatypeLib.H5Tinsert(
				idStruct, 
				floatName, 
				test.float64.offset(), 
				DatatypeLib.getH5T_IEEE_F64BE()
		);
		assertTrue(insertStatus >= 0);
		
		int itid = DatatypeLib.H5Tget_member_type(idStruct, 0);
		assertEquals(1, DatatypeLib.H5Tequal(DatatypeLib.getH5T_STD_I32BE(), itid));
		int ftid = DatatypeLib.H5Tget_member_type(idStruct, 1);
		assertEquals(1, DatatypeLib.H5Tequal(DatatypeLib.getH5T_IEEE_F64BE(), ftid));
		
		int closeStatus = DatatypeLib.H5Tclose(itid);
		assertTrue(closeStatus >= 0);
		closeStatus = DatatypeLib.H5Tclose(ftid);
		assertTrue(closeStatus >= 0);
		closeStatus = DatatypeLib.H5Tclose(idStruct);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_member_value(int, long, long[])}.
	 */
	@Test
	public void testH5Tget_member_value() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_NATIVE_INT());
		assertTrue(idEnum >= 0);
		for (TestEnum value : TestEnum.values()) {
			int insertStatus = DatatypeLib.H5Tenum_insert(
					idEnum, 
					value.name(), 
					value.ordinal()
			);
			assertTrue(insertStatus >= 0);
		}
		
		int[] valuel = new int[1];			
		for (TestEnum value : TestEnum.values()) {			
			int status = DatatypeLib.H5Tget_member_value(idEnum, value.ordinal(), valuel);
			assertTrue(status >= 0);
			assertEquals(value.ordinal(), valuel[0]);
		}
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_member_value_direcct(int, long, long[])}.
	 */
	@Test
	public void testH5Tget_member_value_direct() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_STD_I64BE());
		assertTrue(idEnum >= 0);
		ByteBuffer buff = ByteBuffer.allocateDirect(64);
		
		for (TestEnum value : TestEnum.values()) {
			buff.putLong(0, value.ordinal());
			int insertStatus = DatatypeLib.H5Tenum_insert_direct(idEnum, value.name(), buff);
			assertTrue(insertStatus >= 0);
		}
		
		for (TestEnum value : TestEnum.values()) {			
			int status = DatatypeLib.H5Tget_member_value_direct(idEnum, value.ordinal(), buff);
			assertTrue(status >= 0);
			assertEquals(value.ordinal(), buff.getLong(0));
		}
		
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);		
	}
	
	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_native_type(int, permafrost.hdf.libhdf.DatatypeConversionDirectionType)}.
	 */
	@Test
	public void testH5Tget_native_type() {
		int idI32BE = DatatypeLib.getH5T_STD_I32BE();
		int idNative = DatatypeLib.H5Tget_native_type(idI32BE, DatatypeConversionDirectionType.H5T_DIR_DEFAULT);
		assertTrue(idNative >= 0);
		
		assertEquals(1, DatatypeLib.H5Tequal(idNative, DatatypeLib.getH5T_NATIVE_INT32()));
		int closeStatus = DatatypeLib.H5Tclose(idNative);
		assertTrue(closeStatus >= 0);		
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_nmembers(int)}.
	 */
	@Test
	public void testH5Tget_nmembers() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_NATIVE_INT());
		assertTrue(idEnum >= 0);
		for (TestEnum value : TestEnum.values()) {
			int insertStatus = DatatypeLib.H5Tenum_insert(
					idEnum, 
					value.name(), 
					value.ordinal()
			);
			assertTrue(insertStatus >= 0);
		}
		
		int size = DatatypeLib.H5Tget_nmembers(idEnum);
		assertEquals(TestEnum.values().length, size);
		int closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);		
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_norm(int)}.
	 */
	@Test
	public void testH5Tget_norm() {
		int tid = DatatypeLib.getH5T_IEEE_F32LE();
		DatatypeNormalizationType type = DatatypeLib.H5Tget_norm(tid);
		assertEquals(DatatypeNormalizationType.H5T_NORM_IMPLIED, type);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_offset(int)}.
	 */
	@Test
	public void testH5Tget_offset() {
		int tid = DatatypeLib.getH5T_IEEE_F32LE();
		int offset = DatatypeLib.H5Tget_offset(tid);
		assertEquals(0, offset);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_order(int)}.
	 */
	@Test
	public void testH5Tget_order() {
		DatatypeByteOrderType order = DatatypeLib.H5Tget_order(DatatypeLib.getH5T_IEEE_F32LE());
		assertEquals(DatatypeByteOrderType.H5T_ORDER_LE, order);
		
		order = DatatypeLib.H5Tget_order(DatatypeLib.getH5T_IEEE_F32BE());
		assertEquals(DatatypeByteOrderType.H5T_ORDER_BE, order);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_pad(int, permafrost.hdf.libhdf.DatatypeBitPadType[], permafrost.hdf.libhdf.DatatypeBitPadType[])}.
	 */
	@Test
	public void testH5Tget_pad() {
		DatatypeBitPadType msb[] = new DatatypeBitPadType[1];
		DatatypeBitPadType lsb[] = new DatatypeBitPadType[1];
		int status = DatatypeLib.H5Tget_pad(DatatypeLib.getH5T_IEEE_F32LE(), lsb, msb);
		assertTrue(status >= 0);
		assertEquals(DatatypeBitPadType.H5T_PAD_ZERO, lsb[0]);
		assertEquals(DatatypeBitPadType.H5T_PAD_ZERO, msb[0]);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_precision(int)}.
	 */
	@Test
	public void testH5Tget_precision() {
		long precision = DatatypeLib.H5Tget_precision(DatatypeLib.getH5T_STD_I8BE());
		assertEquals(8, precision);
		precision = DatatypeLib.H5Tget_precision(DatatypeLib.getH5T_STD_I16BE());
		assertEquals(16, precision);
		precision = DatatypeLib.H5Tget_precision(DatatypeLib.getH5T_STD_I32BE());
		assertEquals(32, precision);
		precision = DatatypeLib.H5Tget_precision(DatatypeLib.getH5T_STD_I64BE());
		assertEquals(64, precision);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_sign(int)}.
	 */
	@Test
	public void testH5Tget_sign() {
		DatatypeSigningType sign = DatatypeLib.H5Tget_sign(DatatypeLib.getH5T_STD_I32BE());
		assertEquals(DatatypeSigningType.H5T_SGN_2, sign);
		sign = DatatypeLib.H5Tget_sign(DatatypeLib.getH5T_STD_U32BE());
		assertEquals(DatatypeSigningType.H5T_SGN_NONE, sign);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_size(int)}.
	 */
	@Test
	public void testH5Tget_size() {
		long size = DatatypeLib.H5Tget_size(DatatypeLib.getH5T_STD_I8BE());
		assertEquals(1, size);
		size = DatatypeLib.H5Tget_size(DatatypeLib.getH5T_STD_I16BE());
		assertEquals(2, size);
		size = DatatypeLib.H5Tget_size(DatatypeLib.getH5T_STD_I32BE());
		assertEquals(4, size);
		size = DatatypeLib.H5Tget_size(DatatypeLib.getH5T_STD_I64BE());
		assertEquals(8, size);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_strpad(int)}.
	 */
	@Test
	public void testH5Tget_strpad() {
		DatatypeStringType str = DatatypeLib.H5Tget_strpad(DatatypeLib.getH5T_C_S1());
		assertEquals(DatatypeStringType.H5T_STR_NULLTERM, str);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_super(int)}.
	 */
	@Test
	public void testH5Tget_super() {
		int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_NATIVE_INT());
		assertTrue(idEnum >= 0);
		int idNative = DatatypeLib.H5Tget_super(idEnum);
		assertEquals(1, DatatypeLib.H5Tequal(idNative, DatatypeLib.getH5T_NATIVE_INT32()));
		
		int closeStatus = DatatypeLib.H5Tclose(idNative);
		assertTrue(closeStatus >= 0);
		closeStatus = DatatypeLib.H5Tclose(idEnum);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tget_tag(int)}.
	 */
	@Test
	public void testH5Tget_tag() {
		int atid = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_OPAQUE, 8);		
		assertTrue(atid >= 0);
		
		final String eTag = "this is a 8-bit type";
		int setStatus = DatatypeLib.H5Tset_tag(atid, eTag);
		assertTrue(setStatus >= 0);
		
		String tag = DatatypeLib.H5Tget_tag(atid);
		assertEquals(eTag, tag);
		
		int closeStatus = DatatypeLib.H5Tclose(atid);
		assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tinsert(int, java.lang.String, long, int)}.
	 */
	@Test
	public void testH5Tinsert() {
	    int idEnum = DatatypeLib.H5Tenum_create(DatatypeLib.getH5T_NATIVE_INT());
	    assertTrue(idEnum >= 0);
	    for (TestEnum value : TestEnum.values()) {
	        int insertStatus = DatatypeLib.H5Tenum_insert(
	                idEnum, 
	                value.name(), 
	                value.ordinal()
	        );
	        assertTrue(insertStatus >= 0);
	    }

	    int size = DatatypeLib.H5Tget_nmembers(idEnum);
	    assertEquals(TestEnum.values().length, size);
	    int closeStatus = DatatypeLib.H5Tclose(idEnum);
	    assertTrue(closeStatus >= 0);   
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tis_variable_str(int)}.
	 */
	@Test
	public void testH5Tis_variable_str() {
	    int idChar = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_C_S1());
		int varLen = DatatypeLib.H5Tis_variable_str(idChar);
		assertEquals(0, varLen);
		
		int setStatus = DatatypeLib.H5Tset_size(idChar, DatatypeLib.H5T_VARIABLE);
		assertTrue(setStatus >= 0);		
		varLen = DatatypeLib.H5Tis_variable_str(idChar);
        assertEquals(1, varLen);
        
        int closeStatus = DatatypeLib.H5Tclose(idChar);
        assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tlock(int)}.
	 */
	@Test
	public void testH5Tlock() {
	    DatatypeCharsetType eCSet = DatatypeCharsetType.H5T_CSET_UTF8;
	    int tid = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_C_S1());
	    assertTrue(tid >= 0);

	    int lockStatus = DatatypeLib.H5Tlock(tid);
	    assertTrue(lockStatus >= 0);
	    int setStatus = DatatypeLib.H5Tset_cset(tid, eCSet);
	    assertTrue(setStatus < 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Topen1(int, java.lang.String)}.
	 */
	@Test
	@SuppressWarnings("deprecation")   
	public void testH5Topen1() {
	    final long eSize = 8;
        final String name = "array_type";
        int tid = DatatypeLib.getH5T_NATIVE_B8();       
        int atid = DatatypeLib.H5Tarray_create2(tid, 1,new long[]{eSize});
        assertTrue(atid >= 0);
        
        int commitStatus = DatatypeLib.H5Tcommit2(
                this.fid, 
                name, 
                atid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );
        assertTrue(commitStatus >= 0);      
        
        int closeStatus = DatatypeLib.H5Tclose(atid);
        assertTrue(closeStatus >= 0);
        atid = 0;               
        
        atid = DatatypeLib.H5Topen1(this.fid, name);
        
        long size = DatatypeLib.H5Tget_size(atid);
        assertEquals(8, size);
        
        closeStatus = DatatypeLib.H5Tclose(atid);
        assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Topen2(int, java.lang.String, int)}.
	 */
	@Test
	public void testH5Topen2() {
	      final long eSize = 8;
	        final String name = "array_type";
	        int tid = DatatypeLib.getH5T_NATIVE_B8();       
	        int atid = DatatypeLib.H5Tarray_create2(tid, 1,new long[]{eSize});
	        assertTrue(atid >= 0);
	        
	        int commitStatus = DatatypeLib.H5Tcommit2(
	                this.fid, 
	                name, 
	                atid, 
	                PropertiesLib.H5P_DEFAULT, 
	                PropertiesLib.H5P_DEFAULT, 
	                PropertiesLib.H5P_DEFAULT
	        );
	        assertTrue(commitStatus >= 0);      
	        
	        int closeStatus = DatatypeLib.H5Tclose(atid);
	        assertTrue(closeStatus >= 0);
	        atid = 0;               
	        
	        atid = DatatypeLib.H5Topen2(this.fid, name, PropertiesLib.H5P_DEFAULT);
	        
	        long size = DatatypeLib.H5Tget_size(atid);
	        assertEquals(8, size);
	        
	        closeStatus = DatatypeLib.H5Tclose(atid);
	        assertTrue(closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tpack(int)}.
	 */
	@Test
	public void testH5Tpack() {
	    TestPaddedStruct test = new TestPaddedStruct();
	    assertFalse(test.isPacked());
	    test.setByteBuffer(ByteBuffer.allocateDirect(test.size()), 0);      
	    int idStruct = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_COMPOUND, test.size());
	    assertTrue(idStruct >= 0);
	    final String intName = "int32";
	    int insertStatus = DatatypeLib.H5Tinsert(
	            idStruct, 
	            intName, 
	            test.int32.offset(), 
	            DatatypeLib.getH5T_STD_I32BE()
	    );
	    assertTrue(insertStatus >= 0);
	    final String charName = "int8";
	    insertStatus = DatatypeLib.H5Tinsert(
	            idStruct, 
	            charName, 
	            test.int8.offset(), 
	            DatatypeLib.getH5T_STD_I8BE()
	    );
	    assertTrue(insertStatus >= 0);
	    final String floatName = "float64";
	    insertStatus = DatatypeLib.H5Tinsert(
	            idStruct, 
	            floatName, 
	            test.float64.offset(), 
	            DatatypeLib.getH5T_IEEE_F64BE()
	    );
	    assertTrue(insertStatus >= 0);

	    long paddedSize = DatatypeLib.H5Tget_size(idStruct);
	    int packStatus = DatatypeLib.H5Tpack(idStruct);
	    assertTrue(packStatus >= 0);
	    long packedSize = DatatypeLib.H5Tget_size(idStruct);
	    assertTrue(packedSize < paddedSize);

	    int closeStatus = DatatypeLib.H5Tclose(idStruct);
	    assertTrue (closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_ebias(int, long)}.
	 */
	@Test
	public void testH5Tset_ebias() {
		fail("Not yet implemented"); // TODO test H5Tset_ebias
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_fields(int, long, long, long, long, long)}.
	 */
	@Test
	public void testH5Tset_fields() {
		fail("Not yet implemented"); // TODO test H5Tset_fields
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_inpad(int, permafrost.hdf.libhdf.DatatypeBitPadType)}.
	 */
	@Test
	public void testH5Tset_inpad() {
		fail("Not yet implemented"); // TODO test H5Tset_inpad
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_norm(int, permafrost.hdf.libhdf.DatatypeNormalizationType)}.
	 */
	@Test
	public void testH5Tset_norm() {
		fail("Not yet implemented"); // TODO test H5Tset_norm
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_offset(int, long)}.
	 */
	@Test
	public void testH5Tset_offset() {
		fail("Not yet implemented"); // TODO test H5Tset_offset
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_order(int, permafrost.hdf.libhdf.DatatypeByteOrderType)}.
	 */
	@Test
	public void testH5Tset_order() {
	    int idLE = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_IEEE_F32LE());
	    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(idLE);
        assertEquals(DatatypeByteOrderType.H5T_ORDER_LE, order);
        
        int setStatus = DatatypeLib.H5Tset_order(idLE, DatatypeByteOrderType.H5T_ORDER_BE);
        assertTrue(setStatus >= 0);
        order = DatatypeLib.H5Tget_order(idLE);
        assertEquals(DatatypeByteOrderType.H5T_ORDER_BE, order);
        
        int closeStatus = DatatypeLib.H5Tclose(idLE);
        assertTrue (closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_pad(int, permafrost.hdf.libhdf.DatatypeBitPadType, permafrost.hdf.libhdf.DatatypeBitPadType)}.
	 */
	@Test
	public void testH5Tset_pad() {
		fail("Not yet implemented"); // TODO test H5Tset_pad
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_precision(int, long)}.
	 */
	@Test
	public void testH5Tset_precision() {
		fail("Not yet implemented"); // TODO test H5Tset_precision
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_sign(int, permafrost.hdf.libhdf.DatatypeSigningType)}.
	 */
	@Test
	public void testH5Tset_sign() {
	    int idLE = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_STD_I32LE());
        DatatypeSigningType signing = DatatypeLib.H5Tget_sign(idLE);
        assertEquals(DatatypeSigningType.H5T_SGN_2, signing);
        
        int setStatus = DatatypeLib.H5Tset_sign(idLE, DatatypeSigningType.H5T_SGN_NONE);
        assertTrue(setStatus >= 0);
        signing = DatatypeLib.H5Tget_sign(idLE);
        assertEquals(DatatypeSigningType.H5T_SGN_NONE, signing);
        
        int closeStatus = DatatypeLib.H5Tclose(idLE);
        assertTrue (closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_size(int, long)}.
	 */
	@Test
	public void testH5Tset_size() {
		fail("Not yet implemented"); // TODO test H5Tset_size
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tset_strpad(int, permafrost.hdf.libhdf.DatatypeStringType)}.
	 */
	@Test
	public void testH5Tset_strpad() {
	    int idLE = DatatypeLib.H5Tcopy(DatatypeLib.getH5T_C_S1());
	    DatatypeStringType pad = DatatypeLib.H5Tget_strpad(idLE);
        assertEquals(DatatypeStringType.H5T_STR_NULLTERM, pad);
        
        int setStatus = DatatypeLib.H5Tset_strpad(idLE, DatatypeStringType.H5T_STR_NULLPAD);
        assertTrue(setStatus >= 0);
        pad = DatatypeLib.H5Tget_strpad(idLE);
        assertEquals(DatatypeStringType.H5T_STR_NULLPAD, pad);
        
        int closeStatus = DatatypeLib.H5Tclose(idLE);
        assertTrue (closeStatus >= 0);
	}


	/**
	 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#H5Tvlen_create(int)}.
	 */
	@Test
	public void testH5Tvlen_create() {
	    int idVLen = DatatypeLib.H5Tvlen_create(DatatypeLib.getH5T_NATIVE_INT32());
	    assertTrue (idVLen >= 0);
	    
	   DatatypeClassType type = DatatypeLib.H5Tget_class(idVLen);
	   assertEquals(DatatypeClassType.H5T_VLEN, type);	   
	    
	   int closeStatus = DatatypeLib.H5Tclose(idVLen);
	   assertTrue (closeStatus >= 0);
	}

}
