/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.basic.DataspaceExtent;
import permafrost.hdf.basic.impl.types.DatatypeFactory;
import permafrost.hdf.basic.impl.types.IntegerType;
import permafrost.hdf.libhdf.DataspaceLib;

/**
 * TODO add type documentation
 *
 */
public class TestAttributeBuilder extends BaseTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        super.setUp(true);
    }
    

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AttributeBuilder#isValid()}.
     */
    @Test
    public void testValid() {
        AttributeBuilder bldr = this.testFile.attributeBuilder();
        assertFalse(bldr.isValid());
        
        String atName = "at_valid";
        bldr.setName(atName);
        assertTrue(bldr.isValid());
        
        Attribute att = bldr.build();
        assertEquals(atName, att.getLocalName());
        att.dispose();
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AttributeBuilder#extents(long[])}.
     */
    @Test
    public void testExtents() {
        AttributeBuilder bldr = this.testFile.attributeBuilder();
        bldr.setName("att_extent");
        
        long[] extents = new long[]{16, 16};
        bldr.extents(extents);        
        
        Attribute att = bldr.build();
        long[] nElts = att.getExtent(null);
        att.dispose();
        
        assertArrayEquals(nElts, extents);
        
    }

    /**
     * Test method for {@link permafrost.hdf.basic.impl.AttributeBuilder#maxExtents(long[])}.
     */
    @Test
    public void testMaxExtents() {
        AttributeBuilder bldr = this.testFile.attributeBuilder();
        bldr.setName("att_extent");
        
        long[] extents = new long[]{16, 16};
        bldr.extents(extents);        
        
        long[] maxExtents = new long[]{DataspaceLib.H5S_UNLIMITED, DataspaceLib.H5S_UNLIMITED};
        bldr.maxExtents(maxExtents);
        
        Attribute att = bldr.build();
        Dataspace space = att.getDataspace();
        DataspaceExtent spaceExtents = space.getExtents();
        space.dispose();
        att.dispose();
        
        assertArrayEquals(spaceExtents.extents, extents);
        assertArrayEquals(spaceExtents.maxExtents, maxExtents);
    }



//    /**
//     * Test method for {@link permafrost.hdf.basic.impl.AttributeBuilder#datatype(permafrost.hdf.basic.IDatatype)}.
//     */
//    @Test
//    public void testDatatype() {
//        AttributeBuilder bldr = this.testFile.attributeBuilder();
//        bldr.name("att_extent");
//                
//        IntegerType dType = DatatypeFactory.getInstance().newBEInt16();
//        bldr.datatype(dType);
//        
//        Attribute att = bldr.build();
//        long[] eltSize = new long[1];
//        att.getExtent(eltSize);
//        att.dispose();
//        
//        assertEquals(Short.SIZE/8, eltSize[0]);
//    }

}
