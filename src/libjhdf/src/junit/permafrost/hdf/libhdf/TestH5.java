package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestH5 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			System.loadLibrary("libjhdf");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("   EPIC FAIL\nCannot load HDF native library:\n   " + e.getMessage() + "\n   EPIC FAIL");     
			throw (e);
		}
	}


	@Test
	public final void testH5check_version() {
		fail("Not yet implemented"); // TODO test H5check_version
	}

	@Test
	public final void testH5close() {
		fail("Not yet implemented"); // TODO test H5close
	}
	
	@Test
	public final void testH5dont_atexit() {
		fail("Not yet implemented"); // TODO test H5dont_atexit	
	}

	@Test
	public final void testH5garbage_collect() {
		int status = H5.H5garbage_collect();
		
		assertTrue("Negative return value from wrapper function.", status >= 0);
	}

	@Test
	public final void testH5get_libversion() {
		int[] major = new int[1];
		int[] minor = new int[1];
		int[] rel = new int[1];
		
		int status = H5.H5get_libversion(major, minor, rel);
		
		assertTrue("Negative return value from wrapper function.", status >= 0);
		
		assertEquals("Native lib version major", 1, major[0]);
		assertEquals("Native lib version minor", 8, minor[0]);
		assertEquals("Native lib version release", 0, rel[0]);		
	}

	@Test
	public final void testH5open() {
		int status = H5.H5open();		
		assertTrue("Negative return value from wrapper function.", status >= 0);
	}

	@Test
	public final void testH5set_free_list_limits() {
		fail("Not yet implemented"); // TODO test H5set_free_list_limits
	}
	
	
}
