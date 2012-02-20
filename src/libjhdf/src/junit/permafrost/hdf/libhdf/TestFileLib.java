/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Implements unit tests for methods permafrost.hdf.libhdf.FileLib.
 *
 */
public class TestFileLib {
	
	static final String testDir = "./target";
	
	/**
	 * Loads the native DLL before tests are run.
	 * 
	 * @throws java.lang.Exception if the DLL fails to load.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			System.loadLibrary("libjhdf");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("   EPIC FAIL\nCannot load HDF native library:\n   " + e.getMessage() + "\n   EPIC FAIL");     
			throw (e);
		}
	}


	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fclose(int)}.
	 * 
	 * Opens a new test file and then closes it.
	 */
	@Test
	public final void testH5Fclose() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fcreate(java.lang.String, long, int, int)}.
	 * 
	 * Opens a new test file and then closes it. 
	 */
	@Test
	public final void testH5Fcreate() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5Fcreate.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}
	
	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fcreate(java.lang.String, long, int, int)}.
	 * 
	 * Creates a new test file, closes it, then attempts to open it with the file creation flag.
	 * Tests the error handling system.
	 * FIXME Error handling system tests should be separated from the other tests.
	 */
	//@Test (expected=java.io.IOException.class)
	public final void testH5FcreateEXCL() {
		/* Create a test filename and dummy file. */
		String testFilename = testDir + File.separator + "testH5FcreateEXCL.h5";
		File testFile = new File(testFilename);
		if (!testFile.exists()) {
			try {
				testFile.createNewFile();
			} catch (IOException e) {
				fail("Unable to create test file: " + e.getMessage());
			}
		}
		
		/* Try to create a file in exclusive mode. (This fails.)*/
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_EXCL | 
						FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);
		
		/* Execution should never reach here if error handler is installed. */
		assertFalse("Positive return value from wrapper function.", fid >= 0);		
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fflush(int, permafrost.hdf.libhdf.FileScopeType)}.
	 */
	@Test
	public final void testH5Fflush() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Flush the file buffers. */
		int flushStatus = FileLib.H5Fflush(fid, FileScopeType.H5F_SCOPE_GLOBAL);
		assertTrue("Negative return value from wrapper function.", flushStatus >= 0);	
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_access_plist(int)}.
	 */
	@Test
	public final void testH5Fget_access_plist() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Get the file access property list. */
		int plid = FileLib.H5Fget_access_plist(fid);
		assertTrue("Negative return value from wrapper function.", plid >= 0);
		
		/* Check the class of the list instance. */
		int classId = PropertiesLib.H5Pget_class(plid);
		assertTrue("Negative return value from wrapper function.", classId >= 0);
		int equalStatus = PropertiesLib.H5Pequal(PropertiesLib.getH5P_FILE_ACCESS_CLASS(), classId);
		assertTrue("Negative return value from wrapper function.", equalStatus >= 0);
		assertEquals("Property list class type", 1, equalStatus);
		
		/* Close everything down. */
		int plCloseStatus = PropertiesLib.H5Pclose(plid);
		assertTrue("Negative return value from wrapper function.", plCloseStatus >= 0);
		
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_create_plist(int)}.
	 */
	@Test
	public final void testH5Fget_create_plist() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Get the file creation property list. */
		int plid = FileLib.H5Fget_create_plist(fid);
		assertTrue("Negative return value from wrapper function.", plid >= 0);
		
		/* Check the class of the list instance. */
		int classId = PropertiesLib.H5Pget_class(plid);
		assertTrue("Negative return value from wrapper function.", classId >= 0);
		int equalStatus = PropertiesLib.H5Pequal(PropertiesLib.getH5P_FILE_CREATE_CLASS(), classId);
		assertTrue("Negative return value from wrapper function.", equalStatus >= 0);
		assertEquals("Property list class type", 1, equalStatus);
		
		/* Close everything down. */
		int plCloseStatus = PropertiesLib.H5Pclose(plid);
		assertTrue("Negative return value from wrapper function.", plCloseStatus >= 0);
		
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_filesize(int, long[])}.
	 */
	@Test
	public final void testH5Fget_filesize() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Get ths size of the file. */
		long[] size = new long[1];
		int sizeStatus = FileLib.H5Fget_filesize(fid, size);
		assertTrue("Negative return value from wrapper function.", sizeStatus >= 0);
		assertTrue("File size", size[0] > 0);
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_freespace(int)}.
	 */
	@Test
	public final void testH5Fget_freespace() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assert(delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Get the freespace in the b-tree. */
		long size = FileLib.H5Fget_freespace(fid);
		assertTrue("Negative return value from wrapper function.", size >= 0);
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_info(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5F_info_t)}.
	 */
	@Test
	public final void testH5Fget_info() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Get the file info. */
		FileInfo info = new FileInfo();
		int infoStatus = FileLib.H5Fget_info(fid, info);
		assertTrue("Negative return value from wrapper function.", infoStatus >= 0);
		
		/* Check the file info. */
		BigInteger sbExtSize = info.getSuper_ext_size();
		assertNotNull(sbExtSize);
		FileSharedObjectHeaderMessage sohm = info.getSohm();
		assertNotNull(sohm);
		BigInteger sohmHdrSize = sohm.getHdr_size();
		assertNotNull(sohmHdrSize);
		IndexHeapInfo heapInfo =  sohm.getMsgs_info();
		BigInteger heapSize = heapInfo.getHeap_size();
		assertNotNull(heapSize);
		BigInteger indexSize = heapInfo.getIndex_size();
		assertNotNull(indexSize);
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_intent(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
	 */
	@Test
	public final void testH5Fget_intent() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assert(delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
				
		/* Get the read/write status of the file. */
		long[] intent = new long[1];
		int intentStatus = FileLib.H5Fget_intent(fid, intent);
		assertTrue("Negative return value from wrapper function.", intentStatus >= 0);		
		assertEquals("File read/write intent.", FileLibConstants.H5F_ACC_RDWR, intent[0]);
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}
	
	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_intent(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
	 */
	@Test
	public final void testH5Fget_intentRDONLY() {		
		/* Create a test file. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (!testFile.exists()) {
			int fid = FileLib.H5Fcreate(testFilename, 
					(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
					PropertiesLibConstants.H5P_DEFAULT, 
					PropertiesLibConstants.H5P_DEFAULT);			
			assertTrue("Negative return value from wrapper function.", fid >= 0);
			
			/* Close it down. */
			int closeStatus = FileLib.H5Fclose(fid);
			assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		}		
		assertTrue("Created test file.", testFile.exists());
		
		/* Open the test file read-only. */
		int fid = FileLib.H5Fopen(testFilename, 
				(FileLibConstants.H5F_ACC_RDONLY | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
				
		/* Check the read/write status of the file. */
		long[] intent = new long[1];
		int intentStatus = FileLib.H5Fget_intent(fid, intent);
		assertTrue("Negative return value from wrapper function.", intentStatus >= 0);		
		assertEquals("File read/write intent.", FileLibConstants.H5F_ACC_RDONLY, intent[0]);
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_mdc_config(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5AC_cache_config_t)}.
	 */
	@Test
	public final void testH5Fget_mdc_config() {
		fail("Not yet implemented"); // TODO test H5Fget_mdc_config
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_mdc_hit_rate(int, permafrost.hdf.libhdf.SWIGTYPE_p_double)}.
	 */
	@Test
	public final void testH5Fget_mdc_hit_rate() {
		fail("Not yet implemented"); // TODO test H5Fget_mdc_hit_rate
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_mdc_size(int, permafrost.hdf.libhdf.SWIGTYPE_p_size_t, permafrost.hdf.libhdf.SWIGTYPE_p_size_t, permafrost.hdf.libhdf.SWIGTYPE_p_size_t, permafrost.hdf.libhdf.SWIGTYPE_p_int)}.
	 */
	@Test
	public final void testH5Fget_mdc_size() {
		fail("Not yet implemented"); // TODO test H5Fget_mdc_size
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_name(int, byte[], long)}.
	 */
	@Test
	public final void testH5Fget_name() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assert(delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
				
		/* Get the name of the file. */
		int size = 256;
		byte[] name = new byte[size];
		int actualSize = FileLib.H5Fget_name(fid, name);
		assertTrue("Negative return value from wrapper function.", actualSize >= 0);		
		
		/* Check the file name. */
		String fname = new String(name, 0, actualSize);
		assertEquals("Filename length", fname.length(), actualSize);
		assertEquals("File name", testFilename, fname);
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_obj_count(int, long)}.
	 */
	@Test
	public final void testH5Fget_obj_count() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assert(delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
				
		/* Get the number of open file objects in the file. */
		int fileCount = FileLib.H5Fget_obj_count(fid, FileLibConstants.H5F_OBJ_FILE);
		assertTrue("Negative return value from wrapper function.", fileCount >= 0);		
		assertEquals("File count.", 1, fileCount);
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_obj_ids(int, long, int, int[])}.
	 */
	@Test
	public final void testH5Fget_obj_ids() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assert(delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
				
		/* Get the ids of all open objects in the file. */
		int size = 256;
		int[] ids = new int[size];
		int fileCount = FileLib.H5Fget_obj_ids(fid, FileLibConstants.H5F_OBJ_ALL, 
				size, ids);
		assertTrue("Negative return value from wrapper function.", fileCount >= 0);		
		assertEquals("File count.", 1, fileCount);
		assertEquals("File id.", fid, ids[0]);
		
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fget_vfd_handle(int, int, permafrost.hdf.libhdf.SWIGTYPE_p_p_void)}.
	 */
	@Test
	public final void testH5Fget_vfd_handle() {
		fail("Not yet implemented"); // TODO test H5Fget_vfd_handle
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fis_hdf5(java.lang.String)}.
	 */
	@Test
	public final void testH5Fis_hdf5() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assert(delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
				
		/* Close it down. */
		int closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		/* Check if the test file is HDF5 format. */
		int isH5 = FileLib.H5Fis_hdf5(testFilename);
		assertTrue("Negative return value from wrapper function.", isH5 >= 0);		
		assertEquals("File is HDF 5.", 1, isH5);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fmount(int, byte[], int, int)}.
	 */
	@Test
	public final void testH5Fmount() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Create a second test filename. */
		String subFilename = testDir + File.separator + "testH5Fmount.h5";
		File subFile = new File(subFilename);
		if (subFile.exists() && subFile.isFile()) {
			boolean delStatus = subFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a second test file. */
		int sfid = FileLib.H5Fcreate(subFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", sfid >= 0);		
		assertTrue("Created test file.", subFile.exists());
		
		/* Create a group in the first file. */
		int gid = GroupLib.H5Gcreate2(fid, "mnt", 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);				
		assertTrue("Negative return value from wrapper function.", gid >= 0);
		
		/* Mount the second file on the group in the first. */
		int mntStatus = FileLib.H5Fmount(fid, "mnt", sfid, PropertiesLibConstants.H5P_DEFAULT);
		assertTrue("Negative return value from wrapper function.", mntStatus >= 0);	
		
		/* Verify that there are new two open files in the first file's space. */
		int size = 256;
		int[] ids = new int[size];
		int fileCount = FileLib.H5Fget_obj_ids(fid, FileLibConstants.H5F_OBJ_ALL, 
				size, ids);
		assertTrue("Negative return value from wrapper function.", fileCount >= 0);		
		assertEquals("File count.", 2, fileCount);

		/* Close everything down. */
		int closeStatus = GroupLib.H5Gclose(gid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		closeStatus = FileLib.H5Fclose(sfid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);

		closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fopen(java.lang.String, long, int)}.
	 */
	@Test
	public final void testH5Fopen() {
		/* Create a test filename */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assert(delStatus == true);
		}
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Put some data in it. */
		String groupName = "mnt";
		int gid = GroupLib.H5Gcreate2(fid, groupName, 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);
		assertTrue("Negative return value from wrapper function.", gid >= 0);

		/* Close it out. */
		int closeStatus = GroupLib.H5Gclose(gid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		/* Reopen the file. */
		fid = FileLib.H5Fopen(testFilename, 
				(FileLibConstants.H5F_ACC_RDONLY | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		
		/* Check that the data is still there. */
		gid = GroupLib.H5Gopen2(fid, groupName, PropertiesLibConstants.H5P_DEFAULT);
		assertTrue("Negative return value from wrapper function.", gid >= 0);		
				
		/* Close it back down. */
		closeStatus = GroupLib.H5Gclose(gid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Freopen(int)}.
	 */
	@Test
	public final void testH5Freopen() {
		/* Create a test filename */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assert(delStatus == true);
		}
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Put some data in it. */
		String groupName = "mnt";
		int gid = GroupLib.H5Gcreate2(fid, groupName, 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);
		assertTrue("Negative return value from wrapper function.", gid >= 0);

		
		/* Reopen the file. */
		int fid2 = FileLib.H5Freopen(fid);		
		assertTrue("Negative return value from wrapper function.", fid2 >= 0);		
		
		/* Check that the data is still there. */
		int gid2 = GroupLib.H5Gopen2(fid, groupName, PropertiesLibConstants.H5P_DEFAULT);
		assertTrue("Negative return value from wrapper function.", gid2 >= 0);		
		
		
		/* Close it back down. */
		int closeStatus = GroupLib.H5Gclose(gid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		closeStatus = GroupLib.H5Gclose(gid2);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		closeStatus = FileLib.H5Fclose(fid2);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);

	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Freset_mdc_hit_rate_stats(int)}.
	 */
	@Test
	public final void testH5Freset_mdc_hit_rate_stats() {
		fail("Not yet implemented"); // TODO test H5Freset_mdc_hit_rate_stats
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Fset_mdc_config(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5AC_cache_config_t)}.
	 */
	@Test
	public final void testH5Fset_mdc_config() {
		fail("Not yet implemented"); // TODO test H5Fset_mdc_config
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.FileLib#H5Funmount(int, byte[])}.
	 */
	@Test
	public final void testH5Funmount() {
		/* Create a test filename. */
		String testFilename = testDir + File.separator + "testH5FileLib.h5";
		File testFile = new File(testFilename);
		if (testFile.exists() && testFile.isFile()) {
			boolean delStatus = testFile.delete();
			assertTrue("Cannot delete test file", delStatus == true);
		}
		
		/* Create a test file. */
		int fid = FileLib.H5Fcreate(testFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", fid >= 0);		
		assertTrue("Created test file.", testFile.exists());
		
		/* Create a second test filename. */
		String subFilename = testDir + File.separator + "testH5Fmount.h5";
		File subFile = new File(subFilename);
		if (subFile.exists() && subFile.isFile()) {
			boolean delStatus = subFile.delete();
			assert(delStatus == true);
		}
		
		/* Create a second test file. */
		int sfid = FileLib.H5Fcreate(subFilename, 
				(FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG), 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);		
		assertTrue("Negative return value from wrapper function.", sfid >= 0);		
		assertTrue("Created test file.", subFile.exists());
		
		/* Create a group in the first file. */
		int gid = GroupLib.H5Gcreate2(fid, "mnt", 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT, 
				PropertiesLibConstants.H5P_DEFAULT);				
		assertTrue("Negative return value from wrapper function.", gid >= 0);
		
		/* Mount the second file on the group in the first. */
		int mntStatus = FileLib.H5Fmount(fid, "mnt", sfid, PropertiesLibConstants.H5P_DEFAULT);
		assertTrue("Negative return value from wrapper function.", mntStatus >= 0);	
		
		/* Verify that there are new two open files in the first file's space. */
		int size = 256;
		int[] ids = new int[size];
		int fileCount = FileLib.H5Fget_obj_ids(fid, FileLibConstants.H5F_OBJ_ALL, 
				size, ids);
		assertTrue("Negative return value from wrapper function.", fileCount >= 0);		
		assertEquals("File count.", 2, fileCount);

		int umntStatus = FileLib.H5Funmount(fid,"mnt");
		assertTrue("Negative return value from wrapper function.", umntStatus >= 0);
		
		/* Close everything down. */
		int closeStatus = GroupLib.H5Gclose(gid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);
		
		closeStatus = FileLib.H5Fclose(sfid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);

		closeStatus = FileLib.H5Fclose(fid);
		assertTrue("Negative return value from wrapper function.", closeStatus >= 0);

	}

}
