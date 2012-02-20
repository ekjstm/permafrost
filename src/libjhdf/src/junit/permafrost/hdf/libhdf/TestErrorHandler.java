/**
 *
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the JHDF error handler.
 *
 */
public class TestErrorHandler {

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

    /**
     * Test method for {@link permafrost.hdf.libhdf.NativeErrorHandler#setLogHandler(java.lang.Object, java.lang.String)}.
     */
    @Test
    public void testSetLogHandler() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.NativeErrorHandler#setThrowingHandler()}.
     */
    @Test
    public void testSetThrowingHandler() {
        NativeErrorHandler.getInstance().enable();
        try { 
            this.extraFrame2();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }   
        
    }
    
    private void extraFrame2() {
        this.extraFrame1();
    }
    
    private void extraFrame1() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);                
        status = PropertiesLib.H5Pset_szip(listId, FilterLib.H5_SZIP_NN_OPTION_MASK, 32);
        fail ("Expected exception");          
    }
    
    

}
