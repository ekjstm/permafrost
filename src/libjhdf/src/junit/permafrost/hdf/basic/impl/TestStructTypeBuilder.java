/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertEquals;
import javolution.io.Struct;

import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.basic.impl.types.StructType;
import permafrost.hdf.libhdf.DatatypeLib;

/**
 * Tests for the {@link StructTypeBuilder} class.
 *
 */
public class TestStructTypeBuilder extends BaseTest {

    private class PaddedStruct extends Struct {
        public Signed32 int32 = new Signed32();
        public Signed8 int8 = new Signed8();
        public Float64 float64 = new Float64();             
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.setUp(true);
    }
    
    @Test
    public void testBuild() {
        PaddedStruct struct = new PaddedStruct();
        StructTypeBuilder bldr = new StructTypeBuilder();
        bldr.struct(struct);
        StructType type = bldr.build();
        
        long size = DatatypeLib.H5Tget_size(type.getHId());
        assertEquals(struct.size(), size);
        
        int nmembers = DatatypeLib.H5Tget_nmembers(type.getHId());
        assertEquals(3, nmembers);
        
        String name0 = DatatypeLib.H5Tget_member_name(type.getHId(), 0);
        assertEquals("int32", name0);
        
        String name1 = DatatypeLib.H5Tget_member_name(type.getHId(), 1);
        assertEquals("int8", name1);
        
        String name2 = DatatypeLib.H5Tget_member_name(type.getHId(), 2);
        assertEquals("float64", name2);
        
        type.dispose();
    }
    
}
