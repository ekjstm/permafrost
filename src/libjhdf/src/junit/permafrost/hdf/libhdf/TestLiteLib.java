/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class TestLiteLib {

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
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset(int, java.lang.String, int, long[], int, java.nio.Buffer)}.
     */
    @Test
    public final void testH5LTmake_dataset() {
        int nBytes = 16, bSize = 2;
        ShortBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        for (short n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_make_direct";
        int createStatus = LiteLib.H5LTmake_dataset(this.fid, dsName, 1, new long[]{nBytes/bSize}, DatatypeLib.getH5T_NATIVE_INT16(), dsValue);        
        assertTrue(createStatus >= 0);

        ShortBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        LiteLib.H5LTread_dataset(this.fid, dsName, DatatypeLib.getH5T_NATIVE_INT16(), readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n));
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_char(int, java.lang.String, int, long[], java.nio.ShortBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_char() {
        int nBytes = 16, bSize = 2;
        byte[] dsValue = new byte[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_char_make";
        int createStatus = LiteLib.H5LTmake_dataset_char(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        byte[] readValue = new byte[dsValue.length];
        LiteLib.H5LTread_dataset_char(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n]);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_char_direct(int, java.lang.String, int, long[], java.lang.String)}.
     */
    @Test
    public final void testH5LTmake_dataset_char_direct() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_byte_make_direct";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        ByteBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder());
        LiteLib.H5LTread_dataset_char_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n));
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_short(int, java.lang.String, int, long[], java.nio.ShortBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_short() {
        int nBytes = 16, bSize = 2;
        short[] dsValue = new short[nBytes/bSize];        
        for (short n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_short_make";
        int createStatus = LiteLib.H5LTmake_dataset_short(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        short[] readValue = new short[dsValue.length];
        LiteLib.H5LTread_dataset_short(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n]);
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_short(int, java.lang.String, int, long[], java.nio.ShortBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_short_2D() {
        int nBytes = 16, bSize = 2;
        short[] dsValue = new short[nBytes/bSize*nBytes/bSize];        
        for (short n=0; n<nBytes/bSize; n++) {
        	for (short m=0; m<nBytes/bSize-1; m++) {
        		dsValue[n*m+m] = (short) (m+1);
        	}
        }
        String dsName = "ds_short_make";
        int createStatus = LiteLib.H5LTmake_dataset_short(this.fid, dsName, 2, new long[]{nBytes/bSize, nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        short[] readValue = new short[dsValue.length];
        LiteLib.H5LTread_dataset_short(this.fid, dsName, readValue);
        for (int n=0; n<dsValue.length; n++) {
            assertEquals(dsValue[n], readValue[n]);
        }
    }
    
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_short_direct(int, java.lang.String, int, long[], java.nio.ShortBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_short_direct() {
    	int nBytes = 16, bSize = 2;
    	ShortBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
    	for (short n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_short_make_direct";
        int createStatus = LiteLib.H5LTmake_dataset_short_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        ShortBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        LiteLib.H5LTread_dataset_short_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
        	assertEquals(dsValue.get(n), readValue.get(n));
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_int(int, java.lang.String, int, long[], java.nio.ShortBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_int() {
        int nBytes = 16, bSize = 4;
        int[] dsValue = new int[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_int_make";
        int createStatus = LiteLib.H5LTmake_dataset_int(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        int[] readValue = new int[dsValue.length];
        LiteLib.H5LTread_dataset_int(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n]);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_int_direct(int, java.lang.String, int, long[], java.nio.IntBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_int_direct() {
    	int nBytes = 32, bSize = 4;
    	IntBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
    	for (int n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_int_make_direct";
        int createStatus = LiteLib.H5LTmake_dataset_int_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        IntBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
        LiteLib.H5LTread_dataset_int_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
        	assertEquals(dsValue.get(n), readValue.get(n));
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_long(int, java.lang.String, int, long[], java.nio.ShortBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_long() {
        int nBytes = 16, bSize = 8;
        int[] dsValue = new int[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_long_make";
        int createStatus = LiteLib.H5LTmake_dataset_long(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        int[] readValue = new int[dsValue.length];
        LiteLib.H5LTread_dataset_long(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n]);
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_long_direct(int, java.lang.String, int, long[], java.nio.LongBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_long_direct() {
    	int nBytes = 64, bSize = 8;
    	IntBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
    	for (int n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_long_make_direct";
        int createStatus = LiteLib.H5LTmake_dataset_long_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        IntBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).order(ByteOrder.nativeOrder()).asIntBuffer();
        LiteLib.H5LTread_dataset_long_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
        	assertEquals(dsValue.get(n), readValue.get(n));
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_float(int, java.lang.String, int, long[], java.nio.ShortBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_float() {
        int nBytes = 16, bSize = 8;
        float[] dsValue = new float[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_float_make";
        int createStatus = LiteLib.H5LTmake_dataset_float(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        float[] readValue = new float[dsValue.length];
        LiteLib.H5LTread_dataset_float(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n], 10e-14);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_float_direct(int, java.lang.String, int, long[], java.nio.FloatBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_float_direct() {
    	int nBytes = 32, bSize = 4;
    	FloatBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asFloatBuffer();
    	for (float n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_float_make_direct";
        int createStatus = LiteLib.H5LTmake_dataset_float_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        FloatBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asFloatBuffer();
        LiteLib.H5LTread_dataset_float_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
        	assertEquals(dsValue.get(n), readValue.get(n), 10e-14);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_double(int, java.lang.String, int, long[], java.nio.ShortBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_double() {
        int nBytes = 16, bSize = 16;
        double[] dsValue = new double[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_double_make";
        int createStatus = LiteLib.H5LTmake_dataset_double(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        double[] readValue = new double[dsValue.length];
        LiteLib.H5LTread_dataset_double(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n], 10e-14);
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_double_direct(int, java.lang.String, int, long[], java.nio.DoubleBuffer)}.
     */
    @Test
    public final void testH5LTmake_dataset_double_direct() {
    	int nBytes = 64, bSize = 8;
    	DoubleBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asDoubleBuffer();
    	for (double n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_double_make_direct";
        int createStatus = LiteLib.H5LTmake_dataset_double_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        DoubleBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        LiteLib.H5LTread_dataset_double_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
        	assertEquals(dsValue.get(n), readValue.get(n), 10e-14);
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTmake_dataset_string(int, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testH5LTmake_dataset_string() {
        String dsValue = "abcdefghijklmnopqrstuvwxyz";
        String dsName = "ds_string_make";
        int createStatus = LiteLib.H5LTmake_dataset_string(this.fid, dsName, dsValue);        
        assertTrue(createStatus >= 0);

        ByteBuffer readValue = ByteBuffer.allocateDirect(dsValue.length()).order(ByteOrder.nativeOrder());   
        LiteLib.H5LTread_dataset_string(this.fid, dsName, readValue);
        CharBuffer readValueBuff = Charset.defaultCharset().decode(readValue);
        CharBuffer dsValueBuff = CharBuffer.wrap(dsValue);
        for (int n=0; n<dsValue.length(); n++) {
            assertEquals(dsValueBuff.get(n), readValueBuff.get(n));
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset(int, java.lang.String, int, permafrost.hdf.libhdf.SWIGTYPE_p_void)}.
     */
    @Test
    public final void testH5LTread_dataset() {
        int nBytes = 32, bSize = 4;
        FloatBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (short n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_read_direct";
        int createStatus = LiteLib.H5LTmake_dataset(this.fid, dsName, 1, new long[]{nBytes/bSize}, DatatypeLib.getH5T_NATIVE_FLOAT(), dsValue);        
        assertTrue(createStatus >= 0);

        FloatBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asFloatBuffer();
        LiteLib.H5LTread_dataset(this.fid, dsName, DatatypeLib.getH5T_NATIVE_FLOAT(), readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n), 10e-14);
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_char(int, java.lang.String, byte[])}.
     */
    @Test
    public final void testH5LTread_dataset_char() {
        int nBytes = 8, bSize = 1;
        byte[] dsValue = new byte[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_char";
        int createStatus = LiteLib.H5LTmake_dataset_char(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
              
        byte[] readValue = new byte[nBytes/bSize];
        LiteLib.H5LTread_dataset_char(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n]);
        } 
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_char_direct(int, java.lang.String, byte[])}.
     */
    @Test
    public final void testH5LTread_dataset_char_direct() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_byte_read";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        ByteBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder());
        LiteLib.H5LTread_dataset_char_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n));
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_short(int, java.lang.String, short[])}.
     */
    @Test
    public final void testH5LTread_dataset_short() {
        int nBytes = 16, bSize = 2;
        short[] dsValue = new short[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_short";
        int createStatus = LiteLib.H5LTmake_dataset_short(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
              
        short[] readValue = new short[nBytes/bSize];
        LiteLib.H5LTread_dataset_short(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n]);
        } 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_short_direct(int, java.lang.String, short[])}.
     */
    @Test
    public final void testH5LTread_dataset_short_direct() {
        int nBytes = 16, bSize = 2;
        ShortBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        for (short n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_short_read";
        int createStatus = LiteLib.H5LTmake_dataset_short_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        ShortBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        LiteLib.H5LTread_dataset_short_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n));
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_int(int, java.lang.String, int[])}.
     */
    @Test
    public final void testH5LTread_dataset_int() {
        int nBytes = 32, bSize = 4;
        int[] dsValue = new int[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_int";
        int createStatus = LiteLib.H5LTmake_dataset_int(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
              
        int[] readValue = new int[nBytes/bSize];
        LiteLib.H5LTread_dataset_int(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n]);
        } 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_int_direct(int, java.lang.String, int[])}.
     */
    @Test
    public final void testH5LTread_dataset_int_direct() {
        int nBytes = 32, bSize = 4;
        IntBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
        for (int n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_int_read";
        int createStatus = LiteLib.H5LTmake_dataset_int_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        IntBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
        LiteLib.H5LTread_dataset_int_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n));
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_long(int, java.lang.String, int[])}.
     */
    @Test
    public final void testH5LTread_dataset_long() {
        int nBytes = 32, bSize = 4;
        int[] dsValue = new int[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_long";
        int createStatus = LiteLib.H5LTmake_dataset_long(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
              
        int[] readValue = new int[nBytes/bSize];
        LiteLib.H5LTread_dataset_long(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n]);
        } 
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_long_direct(int, java.lang.String, int[])}.
     */
    @Test
    public final void testH5LTread_dataset_long_direct() {
        int nBytes = 64, bSize = 8;
        IntBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
        for (int n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_long_read";
        int createStatus = LiteLib.H5LTmake_dataset_long_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        IntBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).order(ByteOrder.nativeOrder()).asIntBuffer();
        LiteLib.H5LTread_dataset_long_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n));
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_float(int, java.lang.String, float[])}.
     */
    @Test
    public final void testH5LTread_dataset_float() {
        int nBytes = 32, bSize = 4;
        float[] dsValue = new float[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_float";
        int createStatus = LiteLib.H5LTmake_dataset_float(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
              
        float[] readValue = new float[nBytes/bSize];
        LiteLib.H5LTread_dataset_float(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n], 10e-14);
        } 
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_float_direct(int, java.lang.String, float[])}.
     */
    @Test
    public final void testH5LTread_dataset_float_direct() {
        int nBytes = 32, bSize = 4;
        FloatBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (float n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_float_read";
        int createStatus = LiteLib.H5LTmake_dataset_float_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        FloatBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asFloatBuffer();
        LiteLib.H5LTread_dataset_float_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n), 10e-14);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_double(int, java.lang.String, double[])}.
     */
    @Test
    public final void testH5LTread_dataset_double() {
        int nBytes = 64, bSize = 8;
        double[] dsValue = new double[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_double";
        int createStatus = LiteLib.H5LTmake_dataset_double(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
              
        double[] readValue = new double[nBytes/bSize];
        LiteLib.H5LTread_dataset_double(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue[n], readValue[n], 10e-14);
        } 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_double_direct(int, java.lang.String, double[])}.
     */
    @Test
    public final void testH5LTread_dataset_double_direct() {
        int nBytes = 64, bSize = 8;
        DoubleBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        for (double n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_double_read";
        int createStatus = LiteLib.H5LTmake_dataset_double_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        DoubleBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        LiteLib.H5LTread_dataset_double_direct(this.fid, dsName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(dsValue.get(n), readValue.get(n), 10e-14);
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTread_dataset_string(int, java.lang.String, byte[])}.
     */
    @Test
    public final void testH5LTread_dataset_string() {
        String dsValue = "abcdefghijklmnopqrstuvwxyz";
        String dsName = "ds_string_read";
        int createStatus = LiteLib.H5LTmake_dataset_string(this.fid, dsName, dsValue);        
        assertTrue(createStatus >= 0);

        ByteBuffer readValue = ByteBuffer.allocateDirect(dsValue.length()).order(ByteOrder.nativeOrder());
        LiteLib.H5LTread_dataset_string(this.fid, dsName, readValue);
        CharBuffer readValueBuff = Charset.defaultCharset().decode(readValue);
        CharBuffer dsValueBuff = CharBuffer.wrap(dsValue);
        for (int n=0; n<dsValue.length(); n++) {
            assertEquals(dsValueBuff.get(n), readValueBuff.get(n));
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTget_dataset_ndims(int, java.lang.String, int[])}.
     */
    @Test
    public final void testH5LTget_dataset_ndims() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_byte_ndims";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        int[] dims = new int[1];
        int queryStatus = LiteLib.H5LTget_dataset_ndims(this.fid, dsName, dims);
        assertTrue(queryStatus >= 0);
        assertTrue(dims[0] == 1);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTget_dataset_info(int, java.lang.String, long[], permafrost.hdf.libhdf.SWIGTYPE_p_H5T_class_t, long[])}.
     */
    @Test
    public final void testH5LTget_dataset_info_char() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_byte_info";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        long[] dims = new long[2];
        long[] size = new long[1];
        DatatypeClassType[] type = new DatatypeClassType[1];
        int queryStatus = LiteLib.H5LTget_dataset_info(this.fid, dsName, dims, type, size);
        assertTrue(queryStatus >= 0);
        
        assertEquals(nBytes/bSize, dims[0]);
        assertEquals(DatatypeClassType.H5T_INTEGER, type[0]);
        assertEquals(bSize, size[0]);
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTget_dataset_info(int, java.lang.String, long[], permafrost.hdf.libhdf.SWIGTYPE_p_H5T_class_t, long[])}.
     */
    @Test
    public final void testH5LTget_dataset_info_double() {
        int nBytes = 64, bSize = 8;
        DoubleBuffer dsValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        for (double n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_double_read";
        int createStatus = LiteLib.H5LTmake_dataset_double_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);

        long[] dims = new long[2];
        long[] size = new long[1];
        DatatypeClassType[] type = new DatatypeClassType[1];
        int queryStatus = LiteLib.H5LTget_dataset_info(this.fid, dsName, dims, type, size);
        assertTrue(queryStatus >= 0);
        
        assertEquals(nBytes/bSize, dims[0]);
        assertEquals(DatatypeClassType.H5T_FLOAT, type[0]);
        assertEquals(bSize, size[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTfind_dataset(int, java.lang.String)}.
     */
    @Test
    public final void testH5LTfind_dataset() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_byte_find";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        int dsExists = LiteLib.H5LTfind_dataset(this.fid, dsName);
        assertTrue(dsExists == 1);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_string(int, java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testH5LTset_attribute_string() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        String atName = "att_string";
        String atValue = "abcdefghijklmnopqrstuvwxyz";
        createStatus = LiteLib.H5LTset_attribute_string(this.fid, dsName, atName, atValue);
        assertTrue(createStatus >= 0);
        
        byte[] buffer = new byte[256];
        LiteLib.H5LTget_attribute_string(this.fid, dsName, atName, buffer);        
        CharBuffer atValueBuff = CharBuffer.wrap(atValue);
        CharBuffer atReadBuff = CharBuffer.wrap(new String(buffer));
        for (int n=0; n<atValue.length(); n++) {
            assertEquals(atValueBuff.get(n), atReadBuff.get(n));
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_char(int, java.lang.String, java.lang.String, java.lang.String, long)}.
     */
    @Test
    public final void testH5LTset_attribute_char() {
    	int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 16;
        bSize = 2;
        byte[] atValue = new byte[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_char";
        createStatus = LiteLib.H5LTset_attribute_char(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        byte[] readValue = new byte[atValue.length];
        LiteLib.H5LTget_attribute_char(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }       
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_char_direct(int, java.lang.String, java.lang.String, java.lang.String, long)}.
     */
    @Test
    public final void testH5LTset_attribute_char_direct() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 8;
        bSize = 1;
        ByteBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder());
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue.put(n);
        }
        String atName = "att_char_direct";
        createStatus = LiteLib.H5LTset_attribute_char_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
        assertTrue(createStatus >= 0);

        ByteBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder());
        LiteLib.H5LTget_attribute_char_direct(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue.get(n), readValue.get(n));
        }  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_uchar(int, java.lang.String, java.lang.String, java.nio.ByteBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_uchar() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 16;
        bSize = 2;
        short[] atValue = new short[nBytes/bSize];        
        for (short n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_uchar";
        createStatus = LiteLib.H5LTset_attribute_uchar(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        short[] readValue = new short[atValue.length];
        LiteLib.H5LTget_attribute_uchar(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_uchar_direct(int, java.lang.String, java.lang.String, java.lang.String, long)}.
     */
    @Test
    public final void testH5LTset_attribute_uchar_direct() {
        int nBytes = 8, bSize = 2;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 8;
        bSize = 2;
        ShortBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        for (short n=1; n<nBytes/bSize; n++) {
            atValue.put(n);
        }
        String atName = "att_uchar_direct";
        createStatus = LiteLib.H5LTset_attribute_uchar_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
        assertTrue(createStatus >= 0);

        ShortBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        LiteLib.H5LTget_attribute_uchar_direct(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue.get(n), readValue.get(n));
        }  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_short(int, java.lang.String, java.lang.String, java.nio.ShortBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_short() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 16;
        bSize = 2;
        short[] atValue = new short[nBytes/bSize];        
        for (short n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_short";
        createStatus = LiteLib.H5LTset_attribute_short(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        short[] readValue = new short[atValue.length];
        LiteLib.H5LTget_attribute_short(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_short_direct(int, java.lang.String, java.lang.String, java.lang.String, long)}.
     */
    @Test
    public final void testH5LTset_attribute_short_direct() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 16;
        bSize = 2;
        ShortBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue.put(n);
        }
        String atName = "att_short_direct";
        createStatus = LiteLib.H5LTset_attribute_short_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
        assertTrue(createStatus >= 0);

        ShortBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asShortBuffer();
        LiteLib.H5LTget_attribute_short_direct(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue.get(n), readValue.get(n));
        }  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_ushort(int, java.lang.String, java.lang.String, java.nio.IntBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_ushort() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 16;
        bSize = 2;
        int[] atValue = new int[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_ushort";
        createStatus = LiteLib.H5LTset_attribute_ushort(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        int[] readValue = new int[atValue.length];
        LiteLib.H5LTget_attribute_ushort(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_ushort_direct(int, java.lang.String, java.lang.String, java.nio.IntBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_ushort_direct() {
    	int nBytes = 8, bSize = 2;
    	ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
    	for (byte n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_attr_string";
    	int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
    	assertTrue(createStatus >= 0);
    	
    	nBytes = 16;
    	bSize = 4;
    	IntBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
    	for (int n=1; n<nBytes/bSize; n++) {
    		atValue.put(n);
    	}
    	String atName = "att_ushort_direct";
    	createStatus = LiteLib.H5LTset_attribute_ushort_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
    	assertTrue(createStatus >= 0);
    	
    	IntBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
    	LiteLib.H5LTget_attribute_ushort_direct(this.fid, dsName, atName, readValue);
    	for (int n=0; n<nBytes/bSize; n++) {
    		assertEquals(atValue.get(n), readValue.get(n));
    	}  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_int(int, java.lang.String, java.lang.String, java.nio.IntBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_int() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 32;
        bSize = 4;
        int[] atValue = new int[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_ushort";
        createStatus = LiteLib.H5LTset_attribute_int(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        int[] readValue = new int[atValue.length];
        LiteLib.H5LTget_attribute_int(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_int(int, java.lang.String, java.lang.String, java.nio.LongBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_int_direct() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 32;
        bSize = 4;
        IntBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue.put(n);
        }
        String atName = "att_int_direct";
        createStatus = LiteLib.H5LTset_attribute_int_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
        assertTrue(createStatus >= 0);

        IntBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
        LiteLib.H5LTget_attribute_int_direct(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue.get(n), readValue.get(n));
        }  
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_uint(int, java.lang.String, java.lang.String, java.nio.LongBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_uint() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 32;
        bSize = 4;
        long[] atValue = new long[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_uint";
        createStatus = LiteLib.H5LTset_attribute_uint(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        long[] readValue = new long[atValue.length];
        LiteLib.H5LTget_attribute_uint(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_uint_direct(int, java.lang.String, java.lang.String, java.nio.LongBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_uint_direct() {
    	int nBytes = 8, bSize = 2;
    	ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
    	for (byte n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_attr_string";
    	int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
    	assertTrue(createStatus >= 0);
    	
    	nBytes = 32;
    	bSize = 8;
    	LongBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asLongBuffer();
    	for (int n=1; n<nBytes/bSize; n++) {
    		atValue.put(n);
    	}
    	String atName = "att_uint_direct";
    	createStatus = LiteLib.H5LTset_attribute_uint_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
    	assertTrue(createStatus >= 0);
    	
    	LongBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asLongBuffer();
    	LiteLib.H5LTget_attribute_uint_direct(this.fid, dsName, atName, readValue);
    	for (int n=0; n<nBytes/bSize; n++) {
    		assertEquals(atValue.get(n), readValue.get(n));
    	} 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_long(int, java.lang.String, java.lang.String, java.nio.LongBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_long() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 32;
        bSize = 4;
        int[] atValue = new int[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_long";
        createStatus = LiteLib.H5LTset_attribute_long(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        int[] readValue = new int[atValue.length];
        LiteLib.H5LTget_attribute_long(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_long_direct(int, java.lang.String, java.lang.String, java.nio.LongBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_long_direct() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 32;
        bSize = 4;
        IntBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue.put(n);
        }
        String atName = "att_long_direct";
        createStatus = LiteLib.H5LTset_attribute_long_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
        assertTrue(createStatus >= 0);

        IntBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asIntBuffer();
        LiteLib.H5LTget_attribute_long_direct(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue.get(n), readValue.get(n));
        }  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_ulong(int, java.lang.String, java.lang.String, java.nio.LongBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_ulong() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 32;
        bSize = 4;
        long[] atValue = new long[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_ulong";
        createStatus = LiteLib.H5LTset_attribute_ulong(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        long[] readValue = new long[atValue.length];
        LiteLib.H5LTget_attribute_ulong(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_ulong_direct(int, java.lang.String, java.lang.String, java.nio.LongBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_ulong_direct() {
    	int nBytes = 8, bSize = 2;
    	ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
    	for (byte n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_attr_string";
    	int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
    	assertTrue(createStatus >= 0);
    	
    	nBytes = 32;
    	bSize = 8;
    	LongBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asLongBuffer();
    	for (int n=1; n<nBytes/bSize; n++) {
    		atValue.put(n);
    	}
    	String atName = "att_uint_direct";
    	createStatus = LiteLib.H5LTset_attribute_ulong_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
    	assertTrue(createStatus >= 0);
    	
    	LongBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asLongBuffer();
    	LiteLib.H5LTget_attribute_ulong_direct(this.fid, dsName, atName, readValue);
    	for (int n=0; n<nBytes/bSize; n++) {
    		assertEquals(atValue.get(n), readValue.get(n));
    	}
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_float(int, java.lang.String, java.lang.String, java.nio.FloatBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_float() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 32;
        bSize = 4;
        float[] atValue = new float[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_float";
        createStatus = LiteLib.H5LTset_attribute_float(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        float[] readValue = new float[atValue.length];
        LiteLib.H5LTget_attribute_float(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n], 10e-14);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_float_direct(int, java.lang.String, java.lang.String, java.nio.FloatBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_float_direct() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 32;
        bSize = 4;
        FloatBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue.put(n);
        }
        String atName = "att_float_direct";
        createStatus = LiteLib.H5LTset_attribute_float_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
        assertTrue(createStatus >= 0);

        FloatBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asFloatBuffer();
        LiteLib.H5LTget_attribute_float_direct(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue.get(n), readValue.get(n), 10e-14);
        }  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_double(int, java.lang.String, java.lang.String, java.nio.DoubleBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_double() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 64;
        bSize = 8;
        double[] atValue = new double[nBytes/bSize];        
        for (int n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_double";
        createStatus = LiteLib.H5LTset_attribute_double(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        double[] readValue = new double[atValue.length];
        LiteLib.H5LTget_attribute_double(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n], 10e-14);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTset_attribute_double_direct(int, java.lang.String, java.lang.String, java.nio.DoubleBuffer, long)}.
     */
    @Test
    public final void testH5LTset_attribute_double_direct() {
        int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 64;
        bSize = 8;
        DoubleBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue.put(n);
        }
        String atName = "att_double_direct";
        createStatus = LiteLib.H5LTset_attribute_double_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
        assertTrue(createStatus >= 0);

        DoubleBuffer readValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        LiteLib.H5LTget_attribute_double_direct(this.fid, dsName, atName, readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue.get(n), readValue.get(n), 10e-14);
        }  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTget_attribute(int, java.lang.String, java.lang.String, int, permafrost.hdf.libhdf.SWIGTYPE_p_void)}.
     */
    @Test
    public final void testH5LTget_attribute() {
    	int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 16;
        bSize = 2;
        byte[] atValue = new byte[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_char";
        createStatus = LiteLib.H5LTset_attribute_char(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        byte[] readValue = new byte[atValue.length];
        LiteLib.H5LTget_attribute(this.fid, dsName, atName, DatatypeLib.getH5T_NATIVE_INT8(), readValue);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue[n], readValue[n]);
        }       
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTget_attribute_short_direct(int, java.lang.String, java.lang.String, java.lang.String, long)}.
     */
    @Test
    public final void testH5LTget_attribute_char_as_short_direct() {
        int nBytes = 8, bSize = 2;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 4;
        bSize = 1;
        ByteBuffer atValue = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder());
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue.put(n);
        }
        String atName = "att_char_direct";
        createStatus = LiteLib.H5LTset_attribute_char_direct(this.fid, dsName, atName, atValue, nBytes/bSize);        
        assertTrue(createStatus >= 0);

        ShortBuffer readValue = ByteBuffer.allocateDirect(nBytes*2).order(ByteOrder.nativeOrder()).asShortBuffer();
        createStatus = LiteLib.H5LTget_attribute_short_direct(this.fid, dsName, atName, readValue);
        assertTrue(createStatus >= 0);
        for (int n=0; n<nBytes/bSize; n++) {
            assertEquals(atValue.get(n), readValue.get(n));
        }  
    }


    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTget_attribute_ndims(int, java.lang.String, java.lang.String, int[])}.
     */
    @Test
    public final void testH5LTget_attribute_ndims() {
    	int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 16;
        bSize = 2;
        byte[] atValue = new byte[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_char";
        createStatus = LiteLib.H5LTset_attribute_char(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);
        
        int[] rank = new int[1];
        createStatus = LiteLib.H5LTget_attribute_ndims(this.fid, dsName, atName, rank);
        assertTrue(createStatus >= 0);
        
        assertEquals(1, rank[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTget_attribute_info(int, java.lang.String, java.lang.String, long[], permafrost.hdf.libhdf.SWIGTYPE_p_H5T_class_t, long[])}.
     */
    @Test
    public final void testH5LTget_attribute_info_char() {
    	int nBytes = 8, bSize = 1;
        ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
        for (byte n=1; n<nBytes/bSize; n++) {
            dsValue.put(n);
        }
        String dsName = "ds_attr_string";
        int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
        assertTrue(createStatus >= 0);
        
        nBytes = 16;
        bSize = 2;
        byte[] atValue = new byte[nBytes/bSize];        
        for (byte n=1; n<nBytes/bSize; n++) {
            atValue[n] = n;
        }
        String atName = "att_char";
        createStatus = LiteLib.H5LTset_attribute_char(this.fid, dsName, atName, atValue, atValue.length);        
        assertTrue(createStatus >= 0);

        
        long[] dims = new long[2];
		DatatypeClassType[] type_class = new DatatypeClassType[1];
		long[] type_size = new long[1];
		createStatus = LiteLib.H5LTget_attribute_info(this.fid, dsName, atName, dims, type_class, type_size);
        assertTrue(createStatus >= 0);
        
        assertEquals(atValue.length, dims[0]);
        assertEquals(DatatypeClassType.H5T_INTEGER, type_class[0]);
        assertEquals(1, type_size[0]);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.LiteLib#H5LTget_attribute_info(int, java.lang.String, java.lang.String, long[], permafrost.hdf.libhdf.SWIGTYPE_p_H5T_class_t, long[])}.
     */
    @Test
    public final void testH5LTget_attribute_info_double() {
    	int nBytes = 8, bSize = 1;
    	ByteBuffer dsValue = ByteBuffer.allocateDirect(nBytes);
    	for (byte n=1; n<nBytes/bSize; n++) {
    		dsValue.put(n);
    	}
    	String dsName = "ds_attr_string";
    	int createStatus = LiteLib.H5LTmake_dataset_char_direct(this.fid, dsName, 1, new long[]{nBytes/bSize}, dsValue);        
    	assertTrue(createStatus >= 0);
    	
    	nBytes = 64;
    	bSize = 8;
    	double[] atValue = new double[nBytes/bSize];        
    	for (int n=1; n<nBytes/bSize; n++) {
    		atValue[n] = n;
    	}
    	String atName = "att_double";
    	createStatus = LiteLib.H5LTset_attribute_double(this.fid, dsName, atName, atValue, atValue.length);        
    	assertTrue(createStatus >= 0);
    	
    	long[] dims = new long[2];
    	DatatypeClassType[] type_class = new DatatypeClassType[1];
    	long[] type_size = new long[1];
    	createStatus = LiteLib.H5LTget_attribute_info(this.fid, dsName, atName, dims, type_class, type_size);
    	assertTrue(createStatus >= 0);
    	
    	assertEquals(atValue.length, dims[0]);
    	assertEquals(DatatypeClassType.H5T_FLOAT, type_class[0]);
    	assertEquals(8, type_size[0]);        
    }
    
   
}
