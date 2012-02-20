/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.libhdf.DatasetLib;
import permafrost.hdf.libhdf.DataspaceLib;
import permafrost.hdf.libhdf.DatatypeLibRTConstants;
import permafrost.hdf.libhdf.LiteLib;
import permafrost.hdf.libhdf.NativeErrorHandler;
import permafrost.hdf.libhdf.PropertiesLib;

/**
 * Test cases for {@link Dataset}.
 *
 */
public class TestDataset extends BaseTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.setUp(true);
        NativeErrorHandler.getInstance().disable();
    }
    

    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getBytesDirect()}.
     */
    @Test
    public void testGetBytesDirect() {        
        int nBytes = 16;      
        byte[] dsValue = new byte[nBytes];        
        for (byte n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_char";
        int createStatus = LiteLib.H5LTmake_dataset_char(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);        
        ByteBuffer buf = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder());
        dset.getBytesDirect(buf);
        
        assertEquals(dsValue.length, buf.limit());
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], buf.get());
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getBytes()}.
     */
    @Test
    public void testGetBytes() {        
        int nBytes = 16;      
        byte[] dsValue = new byte[nBytes];        
        for (byte n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_char";
        int createStatus = LiteLib.H5LTmake_dataset_char(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        byte[] buf = dset.getBytes();
        
        assertEquals(dsValue.length, buf.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], buf[n]);
        }               
        dset.dispose();
    }
    
   
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getShorts()}.
     */
    @Test
    public void testGetShorts() {        
        int nBytes = 16;      
        short[] dsValue = new short[nBytes];        
        for (byte n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_short";
        int createStatus = LiteLib.H5LTmake_dataset_short(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        short[] buf = dset.getShorts();
        
        assertEquals(dsValue.length, buf.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], buf[n]);
        }               
        dset.dispose();
    }
    
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getInts()}.
     */
    @Test
    public void testGetInts() {        
        int nBytes = 16;      
        int[] dsValue = new int[nBytes];        
        for (int n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_int";
        int createStatus = LiteLib.H5LTmake_dataset_int(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        int[] buf = dset.getInts();
        
        assertEquals(dsValue.length, buf.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], buf[n]);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getLongs()}.
     */
    @Test
    public void testGetLongs() {        
        int nBytes = 16;      
        long[] dsValue = new long[nBytes];        
        for (int n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_long";
        int sid = DataspaceLib.H5Screate_simple(1, new long[]{dsValue.length}, new long[]{dsValue.length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT64, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);
        
        ByteBuffer buf = ByteBuffer.allocateDirect(Long.SIZE/8*nBytes).order(ByteOrder.nativeOrder());
        buf.asLongBuffer().put(dsValue);
        int writeStatus = DatasetLib.H5Dwrite(
                did, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT64,
                DataspaceLib.H5S_ALL, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                buf
        );
        assertTrue(writeStatus >= 0);
        
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);
        
        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        long[] read = dset.getLongs();
        
        assertEquals(dsValue.length, read.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], read[n]);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getFloats()}.
     */
    @Test
    public void testGetFloats() {        
        int nBytes = 16;      
        float[] dsValue = new float[nBytes];        
        for (int n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_float";
        int createStatus = LiteLib.H5LTmake_dataset_float(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        float[] buf = dset.getFloats();
        
        assertEquals(dsValue.length, buf.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], buf[n], 10e-16);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getDoubles()}.
     */
    @Test
    public void testGetDoubles() {        
        int nBytes = 16;      
        double[] dsValue = new double[nBytes];        
        for (int n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_double";
        int createStatus = LiteLib.H5LTmake_dataset_double(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        double[] buf = dset.getDoubles();
        
        assertEquals(dsValue.length, buf.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], buf[n], 10e-16);
        }               
        dset.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#setBytesDirect()}.
     */
    @Test
    public void testSetBytesDirect() {        
        int nBytes = 16;      
        byte[] dsValue = new byte[nBytes];        
        for (byte n=1; n<nBytes; n++) {
            dsValue[n] = 0;
        }
        String dsName = "ds_char";
        int createStatus = LiteLib.H5LTmake_dataset_char(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);
        
        for (byte n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        Dataset dset = (Dataset) this.testFile.getChild(dsName);        
        ByteBuffer buf = ByteBuffer.allocateDirect(nBytes).order(ByteOrder.nativeOrder());
        buf.put(dsValue);
        dset.setBytesDirect(buf);
        
        byte[] read = dset.getBytes();
        
        assertEquals(dsValue.length, read.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], read[n]);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#setBytes()}.
     */
    @Test
    public void testSetBytes() {        
        int nBytes = 16;      
        byte[] dsValue = new byte[nBytes];        
        for (byte n=1; n<nBytes; n++) {
            dsValue[n] = 0;
        }
        String dsName = "ds_char";
        int createStatus = LiteLib.H5LTmake_dataset_char(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);
        
        for (byte n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        Dataset dset = (Dataset) this.testFile.getChild(dsName);               
        dset.setBytes(dsValue);
        
        byte[] read = dset.getBytes();
        
        assertEquals(dsValue.length, read.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], read[n]);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#setShorts()}.
     */
    @Test
    public void testSetShorts() {        
        int nBytes = 16;      
        short[] dsValue = new short[nBytes];        
        for (short n=1; n<nBytes; n++) {
            dsValue[n] = 0;
        }
        String dsName = "ds_short";
        int createStatus = LiteLib.H5LTmake_dataset_short(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);
        
        for (short n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        Dataset dset = (Dataset) this.testFile.getChild(dsName);               
        dset.setShorts(dsValue);
        
        short[] read = dset.getShorts();
        
        assertEquals(dsValue.length, read.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], read[n]);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#setInts()}.
     */
    @Test
    public void testSetInts() {        
        int nBytes = 16;      
        int[] dsValue = new int[nBytes];        
        for (short n=1; n<nBytes; n++) {
            dsValue[n] = 0;
        }
        String dsName = "ds_int";
        int createStatus = LiteLib.H5LTmake_dataset_int(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);
        
        for (int n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        Dataset dset = (Dataset) this.testFile.getChild(dsName);               
        dset.setInts(dsValue);
        
        int[] read = dset.getInts();
        
        assertEquals(dsValue.length, read.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], read[n]);
        }               
        dset.dispose();
    }
  
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#setLongs(long[])}.
     */
    @Test
    public void testSetLongs() {        
        int nBytes = 16;      
        long[] dsValue = new long[nBytes];        
        for (int n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        String dsName = "ds_long";
        int sid = DataspaceLib.H5Screate_simple(1, new long[]{dsValue.length}, new long[]{dsValue.length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT64, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);   
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);
        
        Dataset dset = (Dataset) this.testFile.getChild(dsName);   
        dset.setLongs(dsValue);
        long[] read = dset.getLongs();
        
        assertEquals(dsValue.length, read.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], read[n]);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#setFloats(float[])}
     */
    @Test
    public void testSetFloats() {        
        int nBytes = 16;      
        float[] dsValue = new float[nBytes];        
        for (short n=1; n<nBytes; n++) {
            dsValue[n] = 0;
        }
        String dsName = "ds_float";
        int createStatus = LiteLib.H5LTmake_dataset_float(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);
        
        for (int n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        Dataset dset = (Dataset) this.testFile.getChild(dsName);               
        dset.setFloats(dsValue);
        
        float[] read = dset.getFloats();
        
        assertEquals(dsValue.length, read.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], read[n], 10e-16);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#setDoubles(double[])}
     */
    @Test
    public void testSetDoubles() {        
        int nBytes = 16;      
        double[] dsValue = new double[nBytes];        
        for (short n=1; n<nBytes; n++) {
            dsValue[n] = 0;
        }
        String dsName = "ds_double";
        int createStatus = LiteLib.H5LTmake_dataset_double(
                this.testFile.getHId(),             
                dsName, 
                1,
                new long[]{dsValue.length},
                dsValue
        );        
        assertTrue(createStatus >= 0);
        
        for (int n=1; n<nBytes; n++) {
            dsValue[n] = n;
        }
        Dataset dset = (Dataset) this.testFile.getChild(dsName);               
        dset.setDoubles(dsValue);
        
        double[] read = dset.getDoubles();
        
        assertEquals(dsValue.length, read.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(dsValue[n], read[n], 10e-16);
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getBytes2d()}.
     */
    @Test
    public void testGetBytes2d() {        
        int nBytes = 16;      
        byte[][] dsValue = new byte[nBytes][nBytes];        
        for (byte n=0; n<nBytes; n++) {
            for (byte m=0; m<nBytes; m++) {
                dsValue[n][m] = (byte) (n*m);
            }
        }
        String dsName = "ds_char";
        int sid = DataspaceLib.H5Screate_simple(2, new long[]{dsValue.length, dsValue[0].length}, new long[]{dsValue.length, dsValue[0].length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT8, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);
        
        ByteBuffer buf = ByteBuffer.allocateDirect(nBytes*nBytes).order(ByteOrder.nativeOrder());
        for (int n=0; n<dsValue.length; n++) {
            buf.put(dsValue[n]);
        }
        int writeStatus = DatasetLib.H5Dwrite(
                did, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT8,
                DataspaceLib.H5S_ALL, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                buf
        );
        assertTrue(writeStatus >= 0);
        
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        byte[][] read = dset.getBytes2d();
        
        assertEquals(dsValue.length, read.length);
        assertEquals(dsValue[0].length, read[0].length);
        for (int n=0; n<nBytes; n++) {
            for (int m=0; m<nBytes; m++) {
                assertEquals(dsValue[n][m], read[n][m]);
            }
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getShort2d()}.
     */
    @Test
    public void testGetShorts2d() {        
        int nBytes = 16;      
        short[][] dsValue = new short[nBytes][nBytes];        
        for (byte n=0; n<nBytes; n++) {
            for (byte m=0; m<nBytes; m++) {
                dsValue[n][m] = (byte) (n*m);
            }
        }
        String dsName = "ds_short";
        int sid = DataspaceLib.H5Screate_simple(2, new long[]{dsValue.length, dsValue[0].length}, new long[]{dsValue.length, dsValue[0].length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT16, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);
        
        ByteBuffer buf = ByteBuffer.allocateDirect(Short.SIZE/8*nBytes*nBytes).order(ByteOrder.nativeOrder());
        ShortBuffer sbuf = buf.asShortBuffer();
        for (int n=0; n<dsValue.length; n++) {
            sbuf.put(dsValue[n]);
        }
        int writeStatus = DatasetLib.H5Dwrite(
                did, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT16,
                DataspaceLib.H5S_ALL, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                buf
        );
        assertTrue(writeStatus >= 0);
        
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        short[][] read = dset.getShorts2d();
        
        assertEquals(dsValue.length, read.length);
        assertEquals(dsValue[0].length, read[0].length);
        for (int n=0; n<nBytes; n++) {
            for (int m=0; m<nBytes; m++) {
                assertEquals(dsValue[n][m], read[n][m]);
            }
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getInts2d()}.
     */
    @Test
    public void testGetInts2d() {        
        int nBytes = 16;      
        int[][] dsValue = new int[nBytes][nBytes];        
        for (byte n=0; n<nBytes; n++) {
            for (byte m=0; m<nBytes; m++) {
                dsValue[n][m] = (byte) (n*m);
            }
        }
        String dsName = "ds_int";
        int sid = DataspaceLib.H5Screate_simple(2, new long[]{dsValue.length, dsValue[0].length}, new long[]{dsValue.length, dsValue[0].length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT32, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);
        
        ByteBuffer buf = ByteBuffer.allocateDirect(Integer.SIZE/8*nBytes*nBytes).order(ByteOrder.nativeOrder());
        IntBuffer sbuf = buf.asIntBuffer();
        for (int n=0; n<dsValue.length; n++) {
            sbuf.put(dsValue[n]);
        }
        int writeStatus = DatasetLib.H5Dwrite(
                did, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT32,
                DataspaceLib.H5S_ALL, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                buf
        );
        assertTrue(writeStatus >= 0);
        
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        int[][] read = dset.getInts2d();
        
        assertEquals(dsValue.length, read.length);
        assertEquals(dsValue[0].length, read[0].length);
        for (int n=0; n<nBytes; n++) {
            for (int m=0; m<nBytes; m++) {
                assertEquals(dsValue[n][m], read[n][m]);
            }
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getLongs2d()}.
     */
    @Test
    public void testGetLongs2d() {        
        int nBytes = 16;      
        long[][] dsValue = new long[nBytes][nBytes];        
        for (byte n=0; n<nBytes; n++) {
            for (byte m=0; m<nBytes; m++) {
                dsValue[n][m] = (byte) (n*m);
            }
        }
        String dsName = "ds_long";
        int sid = DataspaceLib.H5Screate_simple(2, new long[]{dsValue.length, dsValue[0].length}, new long[]{dsValue.length, dsValue[0].length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT64, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);
        
        ByteBuffer buf = ByteBuffer.allocateDirect(Long.SIZE/8*nBytes*nBytes).order(ByteOrder.nativeOrder());
        LongBuffer sbuf = buf.asLongBuffer();
        for (int n=0; n<dsValue.length; n++) {
            sbuf.put(dsValue[n]);
        }
        int writeStatus = DatasetLib.H5Dwrite(
                did, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT64,
                DataspaceLib.H5S_ALL, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                buf
        );
        assertTrue(writeStatus >= 0);
        
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        long[][] read = dset.getLongs2d();
        
        assertEquals(dsValue.length, read.length);
        assertEquals(dsValue[0].length, read[0].length);
        for (int n=0; n<nBytes; n++) {
            for (int m=0; m<nBytes; m++) {
                assertEquals(dsValue[n][m], read[n][m]);
            }
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getFloats2d()}.
     */
    @Test
    public void testGetFloats2D() {        
        int nBytes = 16;      
        float[][] dsValue = new float[nBytes][nBytes];        
        for (byte n=0; n<nBytes; n++) {
            for (byte m=0; m<nBytes; m++) {
                dsValue[n][m] = (byte) (n*m);
            }
        }
        String dsName = "ds_float";
        int sid = DataspaceLib.H5Screate_simple(2, new long[]{dsValue.length, dsValue[0].length}, new long[]{dsValue.length, dsValue[0].length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_FLOAT, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);
        
        ByteBuffer buf = ByteBuffer.allocateDirect(Float.SIZE/8*nBytes*nBytes).order(ByteOrder.nativeOrder());
        FloatBuffer sbuf = buf.asFloatBuffer();
        for (int n=0; n<dsValue.length; n++) {
            sbuf.put(dsValue[n]);
        }
        int writeStatus = DatasetLib.H5Dwrite(
                did, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_FLOAT,
                DataspaceLib.H5S_ALL, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                buf
        );
        assertTrue(writeStatus >= 0);
        
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        float[][] read = dset.getFloats2d();
        
        assertEquals(dsValue.length, read.length);
        assertEquals(dsValue[0].length, read[0].length);
        for (int n=0; n<nBytes; n++) {
            for (int m=0; m<nBytes; m++) {
                assertEquals(dsValue[n][m], read[n][m], 10e-16);
            }
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getDoubles2d()}.
     */
    @Test
    public void testGetDoubles2D() {        
        int nBytes = 16;      
        double[][] dsValue = new double[nBytes][nBytes];        
        for (byte n=0; n<nBytes; n++) {
            for (byte m=0; m<nBytes; m++) {
                dsValue[n][m] = (byte) (n*m);
            }
        }
        String dsName = "ds_double";
        int sid = DataspaceLib.H5Screate_simple(2, new long[]{dsValue.length, dsValue[0].length}, new long[]{dsValue.length, dsValue[0].length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_DOUBLE, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);
        
        ByteBuffer buf = ByteBuffer.allocateDirect(Double.SIZE/8*nBytes*nBytes).order(ByteOrder.nativeOrder());
        DoubleBuffer sbuf = buf.asDoubleBuffer();
        for (int n=0; n<dsValue.length; n++) {
            sbuf.put(dsValue[n]);
        }
        int writeStatus = DatasetLib.H5Dwrite(
                did, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_DOUBLE,
                DataspaceLib.H5S_ALL, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                buf
        );
        assertTrue(writeStatus >= 0);
        
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);

        Dataset dset = (Dataset) this.testFile.getChild(dsName);                
        double[][] read = dset.getDoubles2d();
        
        assertEquals(dsValue.length, read.length);
        assertEquals(dsValue[0].length, read[0].length);
        for (int n=0; n<nBytes; n++) {
            for (int m=0; m<nBytes; m++) {
                assertEquals(dsValue[n][m], read[n][m], 10e-16);
            }
        }               
        dset.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Dataset#getBytes2d()}.
     */
    @Test
    public void testSetBytes2d() {        
        int nBytes = 16;      
        byte[][] dsValue = new byte[nBytes][nBytes];        
        for (byte n=0; n<nBytes; n++) {
            for (byte m=0; m<nBytes; m++) {
                dsValue[n][m] = (byte) (n*m+1);
            }
        }
        String dsName = "ds_char";
        int sid = DataspaceLib.H5Screate_simple(2, new long[]{dsValue.length, dsValue[0].length}, new long[]{dsValue.length, dsValue[0].length});
        assertTrue(sid > 0);
        int did = DatasetLib.H5Dcreate2(
                this.testFile.getHId(), 
                dsName, 
                DatatypeLibRTConstants.getInstance().H5T_NATIVE_INT8, 
                sid, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT, 
                PropertiesLib.H5P_DEFAULT
        );                
        assertTrue(did > 0);                       
        DatasetLib.H5Dclose(did);
        DataspaceLib.H5Sclose(sid);
                     
        Dataset dset = (Dataset) this.testFile.getChild(dsName);
        dset.setBytes2d(dsValue);
        
        byte[][] read = dset.getBytes2d();
        
        assertEquals(dsValue.length, read.length);
        assertEquals(dsValue[0].length, read[0].length);
        for (int n=0; n<nBytes; n++) {
            for (int m=0; m<nBytes; m++) {
                assertEquals(dsValue[n][m], read[n][m]);
            }
        }               
        dset.dispose();
    }
        
}
