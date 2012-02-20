/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.libhdf.LiteLib;

/**
 * Test cases for {@link Attribute}.
 *
 */
public class TestAttribute extends BaseTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.setUp(true);
    }
    

    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getBytes()}.
     */
    @Test
    public void testGetBytes() {        
        int nBytes = 16;      
        byte[] atValue = new byte[nBytes];        
        for (byte n=1; n<nBytes; n++) {
            atValue[n] = n;
        }
        String atName = "att_char";
        int createStatus = LiteLib.H5LTset_attribute_char(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue, 
                atValue.length
        );        
        assertTrue(createStatus >= 0);

        ArrayList<Long> dims = new ArrayList<Long>(1);
        Attribute attr = this.testFile.getAttribute(atName);
        byte[] readValue = attr.getBytes(dims);
        
        assertEquals(atValue.length, readValue.length);
        for (int n=0; n<nBytes; n++) {
            assertEquals(atValue[n], readValue[n]);
        }       
        assertEquals(1, dims.size());
        assertEquals(atValue.length, dims.get(0));
        attr.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getDoubles()}.
     */
    @Test
    public void testGetDoubles() {
        int nElements = 16;      
        double[] atValue = new double[nElements];        
        for (int n=1; n<nElements; n++) {
            atValue[n] = n;
        }
        String atName = "att_double";
        int createStatus = LiteLib.H5LTset_attribute_double(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue, 
                atValue.length
        );        
        assertTrue(createStatus >= 0);

        ArrayList<Long> dims = new ArrayList<Long>(1);
        Attribute attr = this.testFile.getAttribute(atName);
        double[] readValue = attr.getDoubles(dims);
        
        assertEquals(atValue.length, readValue.length);
        for (int n=0; n<nElements; n++) {
            assertEquals(atValue[n], readValue[n], 10e-14);
        }       
        assertEquals(1, dims.size());
        assertEquals(atValue.length, dims.get(0));
        attr.dispose();
    }
    
    

    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getFloats()}.
     */
    @Test
    public void testGetFloats() {
        int nElements = 16;      
        float[] atValue = new float[nElements];        
        for (int n=1; n<nElements; n++) {
            atValue[n] = n;
        }
        String atName = "att_float";
        int createStatus = LiteLib.H5LTset_attribute_float(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue, 
                atValue.length
        );        
        assertTrue(createStatus >= 0);

        ArrayList<Long> dims = new ArrayList<Long>(1);
        Attribute attr = this.testFile.getAttribute(atName);
        float[] readValue = attr.getFloats(dims);
        
        assertEquals(atValue.length, readValue.length);
        for (int n=0; n<nElements; n++) {
            assertEquals(atValue[n], readValue[n], 10e-14);
        }       
        assertEquals(1, dims.size());
        assertEquals(atValue.length, dims.get(0));
        attr.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getInts()}.
     */
    @Test
    public void testGetInts() {
        int nElements = 16;      
        int[] atValue = new int[nElements];        
        for (int n=1; n<nElements; n++) {
            atValue[n] = n;
        }
        String atName = "att_int";
        int createStatus = LiteLib.H5LTset_attribute_int(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue, 
                atValue.length
        );        
        assertTrue(createStatus >= 0);

        Attribute attr = this.testFile.getAttribute(atName);
        ArrayList<Long> dims = new ArrayList<Long>(1); 
        int[] readValue = attr.getInts(dims);
        
        assertEquals(atValue.length, readValue.length);
        for (int n=0; n<nElements; n++) {
            assertEquals(atValue[n], readValue[n]);
        }       
        assertEquals(1, dims.size());
        assertEquals(atValue.length, dims.get(0));
        attr.dispose();
    }
    

    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getShorts()}.
     */
    @Test
    public void testGetShorts() {
        int nElements = 16;      
        short[] atValue = new short[nElements];        
        for (short n=1; n<nElements; n++) {
            atValue[n] = n;
        }
        String atName = "att_short";
        int createStatus = LiteLib.H5LTset_attribute_short(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue, 
                atValue.length
        );        
        assertTrue(createStatus >= 0);

        ArrayList<Long> dims = new ArrayList<Long>(1);
        Attribute attr = this.testFile.getAttribute(atName);
        short[] readValue = attr.getShorts(dims);
        
        assertEquals(atValue.length, readValue.length);
        for (int n=0; n<nElements; n++) {
            assertEquals(atValue[n], readValue[n]);
        }       
        assertEquals(1, dims.size());
        assertEquals(atValue.length, dims.get(0));
        attr.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getString()}.
     */
    @Test
    public void testGetString() {
        String atValue = "abcdefghijklmnop";
        String atName = "att_string";
        int createStatus = LiteLib.H5LTset_attribute_string(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue
        );        
        assertTrue(createStatus >= 0);

        Attribute attr = this.testFile.getAttribute(atName);
        String readValue = attr.getString();
        
        assertEquals(atValue.length(), readValue.length());
        assertEquals(atValue, readValue);       
        attr.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getBytes(java.nio.ByteBuffer)}.
     */
    @Test
    public void testGetBytesDirect() {
        int nElements = 16;      
        byte[] atValue = new byte[nElements];        
        for (byte n=1; n<nElements; n++) {
            atValue[n] = n;
        }
        String atName = "att_bytes";
        int createStatus = LiteLib.H5LTset_attribute_char(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue, 
                atValue.length
        );        
        assertTrue(createStatus >= 0);

        ArrayList<Long> dims = new ArrayList<Long>(1);
        Attribute attr = this.testFile.getAttribute(atName);
        ByteBuffer buf = ByteBuffer.allocateDirect(nElements);
        ByteBuffer readValue = attr.getBytes(buf, dims);        
       
        for (int n=0; n<nElements; n++) {
            assertEquals(atValue[n], readValue.get());
        }       
        attr.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getShorts()}.
     */
    @Test
    public void testGetUShortsAsInts() {
        int nElements = 16;      
        int[] atValue = new int[nElements];        
        for (short n=0; n<nElements; n++) {
            atValue[n] = Short.MAX_VALUE+n-8;
        }
        String atName = "att_short";
        int createStatus = LiteLib.H5LTset_attribute_ushort(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue, 
                atValue.length
        );        
        assertTrue(createStatus >= 0);

        Attribute attr = this.testFile.getAttribute(atName);
        ArrayList<Long> dims = new ArrayList<Long>(1);
        int[] readValue = attr.getInts(dims);
        
        assertEquals(atValue.length, readValue.length);
        for (int n=0; n<nElements; n++) {
            assertEquals(atValue[n], readValue[n]);
        }       
        assertEquals(1, dims.size());
        assertEquals(atValue.length, dims.get(0));
        attr.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getShorts()}.
     */
    @Test
    public void testGetShortsAsBytes() {
        int nElements = 16;      
        short[] atValue = new short[nElements];        
        for (short n=0; n<nElements; n++) {
            atValue[n] = n;
        }
        String atName = "att_short";
        int createStatus = LiteLib.H5LTset_attribute_short(
                this.testFile.getHId(), 
                "." , 
                atName, 
                atValue, 
                atValue.length
        );        
        assertTrue(createStatus >= 0);

        Attribute attr =  this.testFile.getAttribute(atName);
        ArrayList<Long> dims = new ArrayList<Long>(1);
        byte[] readValue = attr.getBytes(dims);
        
        assertEquals(atValue.length, readValue.length);
        for (int n=0; n<nElements; n++) {
            assertEquals(atValue[n], readValue[n]);
        }       
        assertEquals(1, dims.size());
        assertEquals(atValue.length, dims.get(0));
        attr.dispose();
    }
    
    /**
     * Test method for {@link permafrost.hdf.basic.impl.Attribute#getShorts()}.
     */
    @Test
    public void testSetBytes() {
        fail("Not implemented");
//        int nElements = 16;      
//        byte[] atValue = new byte[nElements];        
//        for (byte n=1; n<nElements; n++) {
//            atValue[n] = n;
//        }        
//        
//        Attribute attr = (Attribute) this.testFile.getAttribute(atName);
//        ArrayList<Long> dims = new ArrayList<Long>(1);
//        byte[] readValue = attr.getBytes(dims);
//        
//        assertEquals(atValue.length, readValue.length);
//        for (int n=0; n<nElements; n++) {
//            assertEquals(atValue[n], readValue[n]);
//        }       
//        assertEquals(1, dims.size());
//        assertEquals(atValue.length, dims.get(0));
//        attr.dispose();
    }

}
