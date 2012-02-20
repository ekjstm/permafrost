/**
 *
 */
package permafrost.hdf.basic.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import javolution.io.Struct;
import javolution.io.Struct.Float64;
import javolution.io.Struct.Signed32;
import javolution.io.Struct.Signed8;

import org.junit.Before;
import org.junit.Test;

import permafrost.hdf.basic.DataspaceExtent;
import permafrost.hdf.libhdf.NativeErrorHandler;

/**
 * Test cases for the {@link DatasetBuilder} class.
 *
 */
public class TestDatasetBuilder extends BaseTest {

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
        NativeErrorHandler.getInstance().disable();
    }
    
    @Test
    public void testBuildSimple() {
        DatasetBuilder bldr = new DatasetBuilder(this.testFile);
        String dsName = "ds_simple";
        long[] dims = new long[]{16, 16, 16};
        bldr.setName(dsName);
        bldr.getDataspaceBuilder().setSimpleDims(dims);
        bldr.getDatatypeBuilder().setInt32();
        Dataset dset = bldr.build();
        
        DataspaceExtent extents = dset.getDataspace().getExtents();
        assertEquals(dims.length, extents.rank());
        assertArrayEquals(dims, extents.extents);
        assertArrayEquals(dims, extents.maxExtents);
        
        dset.dispose();
    }
    
    @Test
    public void testBuildChunked() {
        DatasetBuilder bldr = new DatasetBuilder(this.testFile);
        String dsName = "ds_chunked";
        long[] dims = new long[]{16, 16, 16};
        bldr.setName(dsName);
        long[] maxDims = new long[]{64, 64, 64};
        bldr.getDataspaceBuilder().setSimpleMaxDims(maxDims);
        bldr.getDataspaceBuilder().setSimpleDims(dims);
        bldr.getDatatypeBuilder().setInt32();
        long[] chunk = new long[]{4, 4, 4};
        bldr.setChunkSize(chunk);
        Dataset dset = bldr.build();
        
        DataspaceExtent extents = dset.getDataspace().getExtents();
        assertEquals(dims.length, extents.rank());
        assertArrayEquals(dims, extents.extents);
        assertArrayEquals(maxDims, extents.maxExtents);
        
        long[] newDims = new long[]{32, 32, 32};
        dset.setExtent(newDims);
        DataspaceExtent newExtents = dset.getDataspace().getExtents();
        assertEquals(dims.length, newExtents.rank());
        assertArrayEquals(newDims, newExtents.extents);
        assertArrayEquals(maxDims, newExtents.maxExtents);                
        
        dset.dispose();
    }     
    
    @Test
    public void testBuildStruct() throws InterruptedException {
        DatasetBuilder bldr = new DatasetBuilder(this.testFile);
        String dsName = "ds_struct";
        long[] dims = new long[]{16, 16, 16};
        bldr.setName(dsName);
        bldr.getDataspaceBuilder().setSimpleDims(dims);
        bldr.getDatatypeBuilder().setStruct(new PaddedStruct());
        Dataset dset = bldr.build();
        
        // TODO add assertions
        
        dset.dispose();
    }
    
    private enum Values {
        V1,
        V2,
        V3          
      };
//    
//      @Test
//      public void testBuildSimple2() {
//          DatasetBuilder bldr = new DatasetBuilder(this.testFile);
//          String dsName = "ds_simple";
//          long[] dims = new long[]{16, 16, 16};
//          bldr.setName(dsName);
//          bldr.getDataspaceBuilder().setSimpleDims(dims);
//          bldr.getDatatypeBuilder().setInt32();
//          Dataset dset = bldr.build();
//          
//          DataspaceExtent extents = dset.getDataspace().getExtents();
//          assertEquals(dims.length, extents.rank());
//          assertArrayEquals(dims, extents.extents);
//          assertArrayEquals(dims, extents.maxExtents);
//          
//          dset.dispose();
//      }
      
    @Test
    public void testBuildEnum() {
        
        DatasetBuilder bldr = new DatasetBuilder(this.testFile);
        String dsName = "ds_enum";
        long[] dims = new long[]{16, 16, 16};
        bldr.setName(dsName);
        bldr.getDataspaceBuilder().setSimpleDims(dims);
        bldr.getDatatypeBuilder().setEnum8(Values.values());
        Dataset dset = bldr.build();
        
        // TODO add assertions
        
        dset.dispose();
    }
}
