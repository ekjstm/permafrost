/**
 *
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests {@link FilterLib}.
 *
 */
public class TestFilterLib {

    
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
     * Test method for {@link permafrost.hdf.libhdf.FilterLib#H5Zfilter_avail(int)}.
     */
    @Test
    public void testH5Zfilter_avail() {
        int deflateOkay = FilterLib.H5Zfilter_avail(FilterLib.H5Z_FILTER_DEFLATE);
        assertTrue(deflateOkay > 0);
        int shuffleOkay = FilterLib.H5Zfilter_avail(FilterLib.H5Z_FILTER_SHUFFLE);
        assertTrue(shuffleOkay > 0);
        int fletcherOkay = FilterLib.H5Zfilter_avail(FilterLib.H5Z_FILTER_FLETCHER32);
        assertTrue(fletcherOkay > 0);
        int szipOkay = FilterLib.H5Zfilter_avail(FilterLib.H5Z_FILTER_SZIP);
        assertTrue(szipOkay > 0);
        
        int errorOkay = FilterLib.H5Zfilter_avail(FilterLib.H5Z_FILTER_RESERVED);
        assertFalse(errorOkay > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.FilterLib#H5Zget_filter_info(int, int[])}.
     */
    @Test
    public void testH5Zget_filter_info() {
        int config[] = new int[1];
        int getStatus = FilterLib.H5Zget_filter_info(FilterLib.H5Z_FILTER_DEFLATE, config);
        assertTrue(getStatus >= 0);
        
        assertTrue((config[0] & FilterLib.H5Z_FILTER_CONFIG_DECODE_ENABLED) > 0);
        assertTrue((config[0] & FilterLib.H5Z_FILTER_CONFIG_ENCODE_ENABLED) > 0);
    }

}
