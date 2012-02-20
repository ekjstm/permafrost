package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteOrder;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestDatatypeBuiltins {

	 private static DatatypeByteOrderType nativeOrder;
	    
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
	        ByteOrder order = ByteOrder.nativeOrder();
	        if (order == ByteOrder.LITTLE_ENDIAN) {
	            nativeOrder = DatatypeByteOrderType.H5T_ORDER_LE;
	        } else {
	            nativeOrder = DatatypeByteOrderType.H5T_ORDER_BE;
	        }
	    }
	    
	    /**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_IEEE_F32BE()}.
		 */
		@Test
		public void testGetH5T_IEEE_F32BE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_FLOAT;
			long eBits = 32;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
			
			int tid = DatatypeLib.getH5T_IEEE_F32BE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);		
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_IEEE_F32LE()}.
		 */
		@Test
		public void testGetH5T_IEEE_F32LE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_FLOAT;
			long eBits = 32;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
			
			int tid = DatatypeLib.getH5T_IEEE_F32LE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_IEEE_F64BE()}.
		 */
		@Test
		public void testGetH5T_IEEE_F64BE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_FLOAT;
			long eBits = 64;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
			
			int tid = DatatypeLib.getH5T_IEEE_F64BE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_IEEE_F64LE()}.
		 */
		@Test
		public void testGetH5T_IEEE_F64LE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_FLOAT;
			long eBits = 64;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
			
			int tid = DatatypeLib.getH5T_IEEE_F64LE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_I8BE()}.
		 */
		@Test
		public void testGetH5T_STD_I8BE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 8;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
			
			int tid = DatatypeLib.getH5T_STD_I8BE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_I8LE()}.
		 */
		@Test
		public void testGetH5T_STD_I8LE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 8;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
			
			int tid = DatatypeLib.getH5T_STD_I8LE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_I16BE()}.
		 */
		@Test
		public void testGetH5T_STD_I16BE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 16;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
			
			int tid = DatatypeLib.getH5T_STD_I16BE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_I16LE()}.
		 */
		@Test
		public void testGetH5T_STD_I16LE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 16;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
			
			int tid = DatatypeLib.getH5T_STD_I16LE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_I32BE()}.
		 */
		@Test
		public void testGetH5T_STD_I32BE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 32;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
			
			int tid = DatatypeLib.getH5T_STD_I32BE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_I32LE()}.
		 */
		@Test
		public void testGetH5T_STD_I32LE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 32;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
			
			int tid = DatatypeLib.getH5T_STD_I32LE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_I64BE()}.
		 */
		@Test
		public void testGetH5T_STD_I64BE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 64;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
			
			int tid = DatatypeLib.getH5T_STD_I64BE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_I64LE()}.
		 */
		@Test
		public void testGetH5T_STD_I64LE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 64;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
			
			int tid = DatatypeLib.getH5T_STD_I64LE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_U8BE()}.
		 */
		@Test
		public void testGetH5T_STD_U8BE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 8;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;
			
			int tid = DatatypeLib.getH5T_STD_U8BE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_U8LE()}.
		 */
		@Test
		public void testGetH5T_STD_U8LE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 8;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;
			
			int tid = DatatypeLib.getH5T_STD_U8LE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_U16BE()}.
		 */
		@Test
		public void testGetH5T_STD_U16BE() {
			DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
			long eBits = 16;
			DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
			DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;
			
			int tid = DatatypeLib.getH5T_STD_U16BE();
			assertTrue(tid >= 0);
			DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
			assertEquals(eClass, type);
			long precision = DatatypeLib.H5Tget_precision(tid);
			assertEquals(eBits, precision);
			DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
			assertEquals(eOrder, order);
			DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
			assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_U16LE()}.
		 */
		@Test
		public void testGetH5T_STD_U16LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 16;
		    DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_STD_U16LE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_U32BE()}.
		 */
		@Test
		public void testGetH5T_STD_U32BE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 32;
		    DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_STD_U32BE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_U32LE()}.
		 */
		@Test
		public void testGetH5T_STD_U32LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 32;
		    DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_STD_U32LE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_U64BE()}.
		 */
		@Test
		public void testGetH5T_STD_U64BE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 64;
		    DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_STD_U64BE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_U64LE()}.
		 */
		@Test
		public void testGetH5T_STD_U64LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 64;
		    DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_STD_U64LE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_B8BE()}.
		 */
		@Test
		public void testGetH5T_STD_B8BE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
		    long eBits = 8;
		    DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;

		    int tid = DatatypeLib.getH5T_STD_B8BE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_B8LE()}.
		 */
		@Test
		public void testGetH5T_STD_B8LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 8;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;

	        int tid = DatatypeLib.getH5T_STD_B8LE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_B16BE()}.
		 */
		@Test
		public void testGetH5T_STD_B16BE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 16;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;

	        int tid = DatatypeLib.getH5T_STD_B16BE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_B16LE()}.
		 */
		@Test
		public void testGetH5T_STD_B16LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 16;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;

	        int tid = DatatypeLib.getH5T_STD_B16LE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_B32BE()}.
		 */
		@Test
		public void testGetH5T_STD_B32BE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;

	        int tid = DatatypeLib.getH5T_STD_B32BE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_B32LE()}.
		 */
		@Test
		public void testGetH5T_STD_B32LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;

	        int tid = DatatypeLib.getH5T_STD_B32LE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_B64BE()}.
		 */
		@Test
		public void testGetH5T_STD_B64BE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;

	        int tid = DatatypeLib.getH5T_STD_B64BE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_B64LE()}.
		 */
		@Test
		public void testGetH5T_STD_B64LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;

	        int tid = DatatypeLib.getH5T_STD_B64LE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_REF_OBJ()}.
		 */
		@Test
		public void testGetH5T_STD_REF_OBJ() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_REFERENCE;

		    int tid = DatatypeLib.getH5T_STD_REF_OBJ();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_STD_REF_DSETREG()}.
		 */
		@Test
		public void testGetH5T_STD_REF_DSETREG() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_REFERENCE;

		    int tid = DatatypeLib.getH5T_STD_REF_DSETREG();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_UNIX_D32BE()}.
		 */
		@Test
		public void testGetH5T_UNIX_D32BE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_TIME;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;
	        
	        int tid = DatatypeLib.getH5T_UNIX_D32BE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_UNIX_D32LE()}.
		 */
		@Test
		public void testGetH5T_UNIX_D32LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_TIME;
		    long eBits = 32;
		    DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;

		    int tid = DatatypeLib.getH5T_UNIX_D32LE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_UNIX_D64BE()}.
		 */
		@Test
		public void testGetH5T_UNIX_D64BE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_TIME;
		    long eBits = 64;
		    DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_BE;

		    int tid = DatatypeLib.getH5T_UNIX_D64BE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_UNIX_D64LE()}.
		 */
		@Test
		public void testGetH5T_UNIX_D64LE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_TIME;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = DatatypeByteOrderType.H5T_ORDER_LE;

	        int tid = DatatypeLib.getH5T_UNIX_D64LE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_C_S1()}.
		 */
		@Test
		public void testGetH5T_C_S1() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_STRING;
		    DatatypeStringType eStr = DatatypeStringType.H5T_STR_NULLTERM;
		    DatatypeCharsetType eCSet = DatatypeCharsetType.H5T_CSET_ASCII;

		    int tid = DatatypeLib.getH5T_C_S1();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    DatatypeStringType str = DatatypeLib.H5Tget_strpad(tid);
	        assertEquals(eStr, str);
	        DatatypeCharsetType cset = DatatypeLib.H5Tget_cset(tid);
	        assertEquals(eCSet, cset);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_FORTRAN_S1()}.
		 */
		@Test
		public void testGetH5T_FORTRAN_S1() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_STRING;
		    DatatypeStringType eStr = DatatypeStringType.H5T_STR_SPACEPAD;
		    DatatypeCharsetType eCSet = DatatypeCharsetType.H5T_CSET_ASCII;

		    int tid = DatatypeLib.getH5T_FORTRAN_S1();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    DatatypeStringType str = DatatypeLib.H5Tget_strpad(tid);
		    assertEquals(eStr, str);
		    DatatypeCharsetType cset = DatatypeLib.H5Tget_cset(tid);
		    assertEquals(eCSet, cset);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_SCHAR()}.
		 */
		@Test
		public void testGetH5T_NATIVE_SCHAR() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 8;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_SCHAR();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UCHAR()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UCHAR() {
		      DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		        long eBits = 8;
		        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;
		        
		        int tid = DatatypeLib.getH5T_NATIVE_UCHAR();
		        assertTrue(tid >= 0);
		        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		        assertEquals(eClass, type);
		        long precision = DatatypeLib.H5Tget_precision(tid);
		        assertTrue(precision >= eBits);
		        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		        assertEquals(eOrder, order);
		        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_SHORT()}.
		 */
		@Test
		public void testGetH5T_NATIVE_SHORT() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 16;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_SHORT();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_USHORT()}.
		 */
		@Test
		public void testGetH5T_NATIVE_USHORT() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 16;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_USHORT();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_INT();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_UINT();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_LONG()}.
		 */
		@Test
		public void testGetH5T_NATIVE_LONG() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_LONG();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_ULONG()}.
		 */
		@Test
		public void testGetH5T_NATIVE_ULONG() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_ULONG();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_LLONG()}.
		 */
		@Test
		public void testGetH5T_NATIVE_LLONG() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_LLONG();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_ULLONG()}.
		 */
		@Test
		public void testGetH5T_NATIVE_ULLONG() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;
	        
	        int tid = DatatypeLib.getH5T_NATIVE_ULLONG();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_FLOAT()}.
		 */
		@Test
		public void testGetH5T_NATIVE_FLOAT() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_FLOAT;
		    long eBits = 32;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;

		    int tid = DatatypeLib.getH5T_NATIVE_FLOAT();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);        
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_DOUBLE()}.
		 */
		@Test
		public void testGetH5T_NATIVE_DOUBLE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_FLOAT;
		    long eBits = 64;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;

		    int tid = DatatypeLib.getH5T_NATIVE_DOUBLE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);   
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_LDOUBLE()}.
		 */
		@Test
		public void testGetH5T_NATIVE_LDOUBLE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_FLOAT;
		    long eBits = 64;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;

		    int tid = DatatypeLib.getH5T_NATIVE_LDOUBLE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order); 
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_B8()}.
		 */
		@Test
		public void testGetH5T_NATIVE_B8() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 8;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;

	        int tid = DatatypeLib.getH5T_NATIVE_B8();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_B16()}.
		 */
		@Test
		public void testGetH5T_NATIVE_B16() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 16;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;

	        int tid = DatatypeLib.getH5T_NATIVE_B16();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_B32()}.
		 */
		@Test
		public void testGetH5T_NATIVE_B32() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;

	        int tid = DatatypeLib.getH5T_NATIVE_B32();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_B64()}.
		 */
		@Test
		public void testGetH5T_NATIVE_B64() {
	        DatatypeClassType eClass = DatatypeClassType.H5T_BITFIELD;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;

	        int tid = DatatypeLib.getH5T_NATIVE_B64();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_OPAQUE()}.
		 */
		@Test
		public void testGetH5T_NATIVE_OPAQUE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_OPAQUE;

	        int tid = DatatypeLib.getH5T_NATIVE_OPAQUE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_HADDR()}.
		 */
		@Test
		public void testGetH5T_NATIVE_HADDR() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_HADDR();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_HSIZE()}.
		 */
		@Test
		public void testGetH5T_NATIVE_HSIZE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_HSIZE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_HSSIZE()}.
		 */
		@Test
		public void testGetH5T_NATIVE_HSSIZE() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

	        int tid = DatatypeLib.getH5T_NATIVE_HSSIZE();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_HERR()}.
		 */
		@Test
		public void testGetH5T_NATIVE_HERR() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

	        int tid = DatatypeLib.getH5T_NATIVE_HERR();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_HBOOL()}.
		 */
		@Test
		public void testGetH5T_NATIVE_HBOOL() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_HSIZE();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT8()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT8() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 8;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

		    int tid = DatatypeLib.getH5T_NATIVE_INT8();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT8()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT8() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 8;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_UINT8();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT_LEAST8()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT_LEAST8() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 8;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

		    int tid = DatatypeLib.getH5T_NATIVE_INT_LEAST8();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT_LEAST8()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT_LEAST8() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 8;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_UINT_LEAST8();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT_FAST8()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT_FAST8() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 8;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

		    int tid = DatatypeLib.getH5T_NATIVE_INT_FAST8();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT_FAST8()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT_FAST8() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 8;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

	        int tid = DatatypeLib.getH5T_NATIVE_UINT_FAST8();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT16()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT16() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 16;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

	        int tid = DatatypeLib.getH5T_NATIVE_INT16();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT16()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT16() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 16;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

	        int tid = DatatypeLib.getH5T_NATIVE_UINT16();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT_LEAST16()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT_LEAST16() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 16;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

		    int tid = DatatypeLib.getH5T_NATIVE_INT_LEAST16();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT_LEAST16()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT_LEAST16() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 16;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_UINT_LEAST16();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT_FAST16()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT_FAST16() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 16;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

		    int tid = DatatypeLib.getH5T_NATIVE_INT_FAST16();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT_FAST16()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT_FAST16() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 8;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_INT_FAST16();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT32()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT32() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

	        int tid = DatatypeLib.getH5T_NATIVE_INT32();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT32()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT32() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

	        int tid = DatatypeLib.getH5T_NATIVE_UINT32();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT_LEAST32()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT_LEAST32() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

	        int tid = DatatypeLib.getH5T_NATIVE_INT_LEAST32();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT_LEAST32()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT_LEAST32() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 32;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_UINT_LEAST32();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT_FAST32()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT_FAST32() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 32;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

		    int tid = DatatypeLib.getH5T_NATIVE_INT_FAST32();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT_FAST32()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT_FAST32() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 32;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

	        int tid = DatatypeLib.getH5T_NATIVE_UINT_FAST32();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT64()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT64() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

	        int tid = DatatypeLib.getH5T_NATIVE_INT64();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertEquals(eBits, precision);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT64()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT64() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 64;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

		    int tid = DatatypeLib.getH5T_NATIVE_UINT64();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertEquals(eBits, precision);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT_LEAST64()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT_LEAST64() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 64;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

		    int tid = DatatypeLib.getH5T_NATIVE_INT_LEAST64();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT_LEAST64()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT_LEAST64() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

	        int tid = DatatypeLib.getH5T_NATIVE_UINT_LEAST64();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_INT_FAST64()}.
		 */
		@Test
		public void testGetH5T_NATIVE_INT_FAST64() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
		    long eBits = 64;
		    DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
		    DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_2;

		    int tid = DatatypeLib.getH5T_NATIVE_INT_FAST64();
		    assertTrue(tid >= 0);
		    DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
		    assertEquals(eClass, type);
		    long precision = DatatypeLib.H5Tget_precision(tid);
		    assertTrue(precision >= eBits);
		    DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
		    assertEquals(eOrder, order);
		    DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
		    assertEquals(eSign, sign);
		}

		/**
		 * Test method for {@link permafrost.hdf.libhdf.DatatypeLib#getH5T_NATIVE_UINT_FAST64()}.
		 */
		@Test
		public void testGetH5T_NATIVE_UINT_FAST64() {
		    DatatypeClassType eClass = DatatypeClassType.H5T_INTEGER;
	        long eBits = 64;
	        DatatypeByteOrderType eOrder = TestDatatypeBuiltins.nativeOrder;
	        DatatypeSigningType eSign = DatatypeSigningType.H5T_SGN_NONE;

	        int tid = DatatypeLib.getH5T_NATIVE_UINT_FAST64();
	        assertTrue(tid >= 0);
	        DatatypeClassType type = DatatypeLib.H5Tget_class(tid);
	        assertEquals(eClass, type);
	        long precision = DatatypeLib.H5Tget_precision(tid);
	        assertTrue(precision >= eBits);
	        DatatypeByteOrderType order = DatatypeLib.H5Tget_order(tid);
	        assertEquals(eOrder, order);
	        DatatypeSigningType sign = DatatypeLib.H5Tget_sign(tid);
	        assertEquals(eSign, sign);
		}

	
}
