/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.ByteOrder;

import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.basic.DataspaceExtent;
import permafrost.hdf.basic.impl.types.FloatType;
import permafrost.hdf.basic.impl.types.IntegerType;
import permafrost.hdf.libhdf.DatatypeClassType;
import permafrost.hdf.libhdf.DatatypeSigningType;
import permafrost.hdf.libhdf.NativeErrorHandler;

/**
 * Test cases for the {@link DatatypeBuilder} class.
 *
 */
public class TestDatatypeBuilder extends BaseTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.setUp(true);
        NativeErrorHandler.getInstance().disable();
    }
    
    @Test
    public void testBuildSimple() {
        DatatypeBuilder bldr = new DatatypeBuilder(this.testFile);
        bldr.setFloat32();
        FloatType type = (FloatType) bldr.build();
        assertEquals(4, type.getSize());
        assertEquals(32, type.getPrecision());
        type.dispose();
    }
    
    @Test
    public void testBuildCustom() {
        DatatypeBuilder bldr = new DatatypeBuilder(this.testFile);
        bldr.setFloat32();
        final int size = 4;
        final ByteOrder order = ByteOrder.BIG_ENDIAN;
        final DatatypeClassType dtype = DatatypeClassType.H5T_INTEGER;
        final DatatypeSigningType sign = DatatypeSigningType.H5T_SGN_NONE;
        
        bldr.size(size);        
        bldr.byteOrder(order);        
        bldr.classType(dtype);       
        bldr.signing(sign);
        IntegerType type = (IntegerType) bldr.build();
        
        assertEquals(size, type.getSize());
        assertEquals(order, type.getByteOrder());
        assertEquals(dtype, type.getClassType());
        assertEquals(sign, type.getSigning());
                
        type.dispose();
    }
    
    
}
