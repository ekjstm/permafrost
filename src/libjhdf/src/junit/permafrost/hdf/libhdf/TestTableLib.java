/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javolution.io.Struct;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class TestTableLib {

    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestTableLib.class);

    private int fid;
    
    private class TestPaddedStruct extends Struct {
        public Signed32 int32 = new Signed32();
        public Signed8 int8 = new Signed8();
        public Float64 float64 = new Float64();
        
        /* (non-Javadoc)
         * @see javolution.io.Struct#byteOrder()
         */
        @Override
        public ByteOrder byteOrder() {
            return (ByteOrder.nativeOrder());
        }   
        
        public long[] getOffsets() {
        	return (new long[]{this.int32.offset(), this.int8.offset(), this.float64.offset()});
        }
        
        public long[] getSizes() {
        	return (new long[]{4, 1, 8});
        }
    }
    
    private class TestSmallStruct extends Struct {
        public Signed32 int32 = new Signed32();       
        public Float64 float64 = new Float64();
        
        /* (non-Javadoc)
         * @see javolution.io.Struct#byteOrder()
         */
        @Override
        public ByteOrder byteOrder() {
            return (ByteOrder.nativeOrder());
        }   
        
        public long[] getOffsets() {
        	return (new long[]{this.int32.offset(), this.float64.offset()});
        }
        
        public long[] getSizes() {
        	return (new long[]{4, 8});
        }
    }
    
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
        /* Set the file driver to use compact/dense storage. */
        PropertiesLib.H5Pset_libver_bounds(plFAccess, FileLibVersionType.H5F_LIBVER_LATEST, FileLibVersionType.H5F_LIBVER_LATEST);
        assertTrue("Invalid file access property list.", plFAccess >= 0);
//        PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
        this.fid = FileLib.H5Fcreate("test.h5", 
                (FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG),  
                PropertiesLibConstants.H5P_DEFAULT, 
                plFAccess
        );
        assertTrue("Invalid file identifier.", this.fid >= 0);     
    }
    
    @After
    public void tearDown() throws Exception {
        try {
            if (this.fid > 0) {
                FileLib.H5Fclose(this.fid);
                this.fid = 0;
            }
        } catch (Exception ex) {
            logger.error("Error closing file", ex);
        }
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBmake_table(java.lang.String, int, java.lang.String, java.math.BigInteger, java.math.BigInteger, long, java.lang.String[], long[], long[], java.math.BigInteger, java.nio.Buffer, int, java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBmake_table() {
       String title = "table title";
       String tabName = "table";       
       TestPaddedStruct fill = new TestPaddedStruct();
       fill.setByteBuffer(ByteBuffer.allocateDirect(fill.size()).order(ByteOrder.nativeOrder()), 0);   
       String[] fieldNames = new String[]{"int32", "int8", "float64"};
       long[] fieldOffsets = new long[]{
               fill.int32.offset(),
               fill.int8.offset(),
               fill.float64.offset()
       };
       long[] fieldTypes = new long[]{
               DatatypeLib.getH5T_STD_I32BE(),
               DatatypeLib.getH5T_STD_I8BE(),
               DatatypeLib.getH5T_IEEE_F64BE()
       };
       
       ByteBuffer data = ByteBuffer.allocateDirect(fill.size()*3).order(ByteOrder.nativeOrder());
       TestPaddedStruct data1 = new TestPaddedStruct();
       TestPaddedStruct data2 = new TestPaddedStruct();
       TestPaddedStruct data3 = new TestPaddedStruct();
       data1.setByteBuffer(data, 0);
       data2.setByteBuffer(data, data2.size());
       data3.setByteBuffer(data, data3.size()*2);
       
       data1.int32.set(1);
       data1.int8.set((byte) 2);
       data1.float64.set(3.4);
       data2.int32.set(5);
       data2.int8.set((byte) 6);
       data2.float64.set(7.8);
       data3.int32.set(9);
       data3.int8.set((byte) 10);
       data3.float64.set(11.12);
       
       int createStatus = TableLib.H5TBmake_table(
               title, 
               this.fid, 
               tabName, 
               3, 
               3, 
               fill.size(), 
               fieldNames, 
               fieldOffsets, 
               fieldTypes, 
               192,
               fill.getByteBuffer(),
               0,
               data
       );
       assertTrue(createStatus >= 0);
       
       long[] nfields = new long[1];
       long[] nrecords = new long[1];
       int getStatus = TableLib.H5TBget_table_info(this.fid, tabName, nfields, nrecords);
       assertTrue(getStatus >= 0);
       
       assertEquals(3, nfields[0]);
       assertEquals(3, nrecords[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBappend_records(int, java.lang.String, java.math.BigInteger, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBappend_records() {
    	 String title = "table title";
    	 String tabName = "table";       
         TestPaddedStruct fill = new TestPaddedStruct();
         final int sizeofStruct = fill.size();
         
         fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
         String[] fieldNames = new String[]{"int32", "int8", "float64"};
         long[] fieldOffsets = new long[]{
                 fill.int32.offset(),
                 fill.int8.offset(),
                 fill.float64.offset()
         };
         long[] fieldTypes = new long[]{
                 DatatypeLib.getH5T_NATIVE_INT32(),
                 DatatypeLib.getH5T_NATIVE_INT8(),
                 DatatypeLib.getH5T_NATIVE_DOUBLE()
         };
         
         ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
         TestPaddedStruct data1 = new TestPaddedStruct();
         TestPaddedStruct data2 = new TestPaddedStruct();
         TestPaddedStruct data3 = new TestPaddedStruct();
         data1.setByteBuffer(data, 0);
         data2.setByteBuffer(data, data2.size());
         data3.setByteBuffer(data, data3.size()*2);
         
         data1.int32.set(1);
         data1.int8.set((byte) 2);
         data1.float64.set(3.4);
         data2.int32.set(5);
         data2.int8.set((byte) 6);
         data2.float64.set(7.8);
         data3.int32.set(9);
         data3.int8.set((byte) 10);
         data3.float64.set(11.12);
         
         int createStatus = TableLib.H5TBmake_table(
                 title, 
                 this.fid, 
                 tabName, 
                 3, 
                 3, 
                 sizeofStruct, 
                 fieldNames, 
                 fieldOffsets, 
                 fieldTypes, 
                 192,
                 fill.getByteBuffer(),
                 0,
                 data
         );
         assertTrue(createStatus >= 0);
         
         data1.int32.set(13);
         data1.int8.set((byte) 14);
         data1.float64.set(15.16);
         data2.int32.set(17);
         data2.int8.set((byte) 18);
         data2.float64.set(19.20);
         data3.int32.set(21);
         data3.int8.set((byte) 22);
         data3.float64.set(23.24);
         long[] fieldSizes = new long[]{4, 1, 8};
         
         int writeStatus = TableLib.H5TBappend_records(
        		 this.fid, 
        		 tabName, 
        		 3, 
        		 sizeofStruct, 
        		 fieldOffsets, 
        		 fieldSizes, 
        		 data
         );
         assertTrue(writeStatus >= 0);
         
         ByteBuffer buffer = ByteBuffer.allocateDirect(data.capacity()).order(ByteOrder.nativeOrder());
         int readStatus = TableLib.H5TBread_records(
        		 this.fid, 
        		 tabName, 
        		 3, 
        		 3, 
        		 sizeofStruct, 
        		 fieldOffsets, 
        		 fieldSizes, 
        		 buffer
         );              
         assertTrue(readStatus >= 0);
         
         TestPaddedStruct data1a = new TestPaddedStruct();
         TestPaddedStruct data2a = new TestPaddedStruct();
         TestPaddedStruct data3a = new TestPaddedStruct();
         data1a.setByteBuffer(buffer, 0);
         data2a.setByteBuffer(buffer, data2a.size());
         data3a.setByteBuffer(buffer, data3a.size()*2);
         
         assertEquals(data1.int32.get(), data1a.int32.get());
         assertEquals(data1.int8.get(), data1a.int8.get());
         assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
         assertEquals(data2.int32.get(), data2a.int32.get());
         assertEquals(data2.int8.get(), data2a.int8.get());
         assertEquals(data2.float64.get(), data2a.float64.get(), 10e-14);
         assertEquals(data3.int32.get(), data3a.int32.get());
         assertEquals(data3.int8.get(), data3a.int8.get());
         assertEquals(data3.float64.get(), data3a.float64.get(), 10e-14);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBwrite_records(int, java.lang.String, java.math.BigInteger, java.math.BigInteger, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBwrite_records() {
   	 String title = "table title";
	 String tabName = "table";       
     TestPaddedStruct fill = new TestPaddedStruct();
     final int sizeofStruct = fill.size();
     
     fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
     String[] fieldNames = new String[]{"int32", "int8", "float64"};
     long[] fieldOffsets = new long[]{
             fill.int32.offset(),
             fill.int8.offset(),
             fill.float64.offset()
     };
     long[] fieldTypes = new long[]{
             DatatypeLib.getH5T_NATIVE_INT32(),
             DatatypeLib.getH5T_NATIVE_INT8(),
             DatatypeLib.getH5T_NATIVE_DOUBLE()
     };
     
     ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
     TestPaddedStruct data1 = new TestPaddedStruct();
     TestPaddedStruct data2 = new TestPaddedStruct();
     TestPaddedStruct data3 = new TestPaddedStruct();
     data1.setByteBuffer(data, 0);
     data2.setByteBuffer(data, data2.size());
     data3.setByteBuffer(data, data3.size()*2);
     
     data1.int32.set(1);
     data1.int8.set((byte) 2);
     data1.float64.set(3.4);
     data2.int32.set(5);
     data2.int8.set((byte) 6);
     data2.float64.set(7.8);
     data3.int32.set(9);
     data3.int8.set((byte) 10);
     data3.float64.set(11.12);
     
     int createStatus = TableLib.H5TBmake_table(
             title, 
             this.fid, 
             tabName, 
             3, 
             3, 
             sizeofStruct, 
             fieldNames, 
             fieldOffsets, 
             fieldTypes, 
             192,
             fill.getByteBuffer(),
             0,
             data
     );
     assertTrue(createStatus >= 0);
     
     data1.int32.set(13);
     data1.int8.set((byte) 14);
     data1.float64.set(15.16);
     data2.int32.set(17);
     data2.int8.set((byte) 18);
     data2.float64.set(19.20);
     data3.int32.set(21);
     data3.int8.set((byte) 22);
     data3.float64.set(23.24);
     long[] fieldSizes = new long[]{4, 1, 8};
     
     int writeStatus = TableLib.H5TBwrite_records(
    		 this.fid, 
    		 tabName, 
    		 0, 
    		 3, 
    		 sizeofStruct, 
    		 fieldOffsets, 
    		 fieldSizes, 
    		 data
     );     
     assertTrue(writeStatus >= 0);
     
     ByteBuffer buffer = ByteBuffer.allocateDirect(data.capacity()).order(ByteOrder.nativeOrder());
     int readStatus = TableLib.H5TBread_records(
    		 this.fid, 
    		 tabName, 
    		 0, 
    		 3, 
    		 sizeofStruct, 
    		 fieldOffsets, 
    		 fieldSizes, 
    		 buffer
     );              
     assertTrue(readStatus >= 0);
     
     TestPaddedStruct data1a = new TestPaddedStruct();
     TestPaddedStruct data2a = new TestPaddedStruct();
     TestPaddedStruct data3a = new TestPaddedStruct();
     data1a.setByteBuffer(buffer, 0);
     data2a.setByteBuffer(buffer, data2a.size());
     data3a.setByteBuffer(buffer, data3a.size()*2);
     
     assertEquals(data1.int32.get(), data1a.int32.get());
     assertEquals(data1.int8.get(), data1a.int8.get());
     assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
     assertEquals(data2.int32.get(), data2a.int32.get());
     assertEquals(data2.int8.get(), data2a.int8.get());
     assertEquals(data2.float64.get(), data2a.float64.get(), 10e-14);
     assertEquals(data3.int32.get(), data3a.int32.get());
     assertEquals(data3.int8.get(), data3a.int8.get());
     assertEquals(data3.float64.get(), data3a.float64.get(), 10e-14);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBwrite_fields_name(int, java.lang.String, java.lang.String, java.math.BigInteger, java.math.BigInteger, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBwrite_fields_name() {
    	String title = "table title";
        String tabName = "table";       
        TestPaddedStruct fill = new TestPaddedStruct();
        int sizeofStruct = fill.size();
		fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "int8", "float64"};
        long[] fieldOffsets = new long[]{
                fill.int32.offset(),
                fill.int8.offset(),
                fill.float64.offset()
        };
        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_NATIVE_INT32(),
                DatatypeLib.getH5T_NATIVE_INT8(),
                DatatypeLib.getH5T_NATIVE_DOUBLE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
        TestPaddedStruct data1 = new TestPaddedStruct();
        TestPaddedStruct data2 = new TestPaddedStruct();
        TestPaddedStruct data3 = new TestPaddedStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.int8.set((byte) 2);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.int8.set((byte) 6);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.int8.set((byte) 10);
        data3.float64.set(11.12);
        
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                3, 
                3, 
                sizeofStruct, 
                fieldNames, 
                fieldOffsets, 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        
        long[] smallSizes = new long[]{4, 8};        
        TestSmallStruct small = new TestSmallStruct();
        int sizeofSmall = small.size();        
        ByteBuffer buffer = ByteBuffer.allocateDirect(sizeofSmall).order(ByteOrder.nativeOrder());
        small.setByteBuffer(buffer, 0);        
        long[] smallOffsets = new long[]{small.int32.offset(), small.float64.offset()};
        
        small.int32.set(13);
        small.float64.set(14.15);
       		          
        int writeStatus = TableLib.H5TBwrite_fields_name(
        		this.fid, 
        		tabName, 
        		"int32"+","+"float64", 
          		 1, 
          		 1, 
          		 sizeofSmall, 
          		 smallOffsets, 
          		 smallSizes, 
          		 buffer
        );
        assertTrue(writeStatus >= 0);
        
        long[] largeSizes = new long[]{4, 1, 8};        
        TestPaddedStruct large = new TestPaddedStruct();                
        ByteBuffer largeBuffer = ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder());
        large.setByteBuffer(largeBuffer, 0);                
        int readStatus = TableLib.H5TBread_records(
                this.fid,
                tabName, 
                1, 
                1,
                sizeofStruct,
                fieldOffsets, 
                largeSizes, 
                largeBuffer);
        assertTrue(readStatus >= 0);
        
        assertEquals(small.int32.get(), large.int32.get());
        assertEquals(data2.int8.get(), large.int8.get());
        assertEquals(small.float64.get(), large.float64.get(), 10e-14);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBwrite_fields_index(int, java.lang.String, java.math.BigInteger, int[], java.math.BigInteger, java.math.BigInteger, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBwrite_fields_index() {
    	String title = "table title";
        String tabName = "table";       
        TestPaddedStruct fill = new TestPaddedStruct();
        int sizeofStruct = fill.size();
		fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "int8", "float64"};
        long[] fieldOffsets = new long[]{
                fill.int32.offset(),
                fill.int8.offset(),
                fill.float64.offset()
        };
        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_NATIVE_INT32(),
                DatatypeLib.getH5T_NATIVE_INT8(),
                DatatypeLib.getH5T_NATIVE_DOUBLE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
        TestPaddedStruct data1 = new TestPaddedStruct();
        TestPaddedStruct data2 = new TestPaddedStruct();
        TestPaddedStruct data3 = new TestPaddedStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.int8.set((byte) 2);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.int8.set((byte) 6);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.int8.set((byte) 10);
        data3.float64.set(11.12);
        
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                3, 
                3, 
                sizeofStruct, 
                fieldNames, 
                fieldOffsets, 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        
        long[] smallSizes = new long[]{4, 8};        
        TestSmallStruct small = new TestSmallStruct();
        int sizeofSmall = small.size();        
        ByteBuffer buffer = ByteBuffer.allocateDirect(sizeofSmall).order(ByteOrder.nativeOrder());
        small.setByteBuffer(buffer, 0);        
        long[] smallOffsets = new long[]{small.int32.offset(), small.float64.offset()};
        
        small.int32.set(13);
        small.float64.set(14.15);
       		          
        int writeStatus = TableLib.H5TBwrite_fields_index(
        		this.fid, 
        		tabName, 
        		2, 
        		new int[]{0, 2}, 
        		1, 
        		1, 
        		sizeofSmall, 
        		smallOffsets, 
        		smallSizes, 
        		buffer
        );
        assertTrue(writeStatus >= 0);
        
        long[] largeSizes = new long[]{4, 1, 8};        
        TestPaddedStruct large = new TestPaddedStruct();                
        ByteBuffer largeBuffer = ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder());
        large.setByteBuffer(largeBuffer, 0);                
        int readStatus = TableLib.H5TBread_records(
                this.fid,
                tabName, 
                1, 
                1,
                sizeofStruct,
                fieldOffsets, 
                largeSizes, 
                largeBuffer);
        assertTrue(readStatus >= 0);
        
        assertEquals(small.int32.get(), large.int32.get());
        assertEquals(data2.int8.get(), large.int8.get());
        assertEquals(small.float64.get(), large.float64.get(), 10e-14);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBread_table(int, java.lang.String, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBread_table() {
        String title = "table title";
        String tabName = "table";       
        TestPaddedStruct fill = new TestPaddedStruct();
        final int sizeofStruct = fill.size();
        
        fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "int8", "float64"};
        long[] fieldOffsets = new long[]{
                fill.int32.offset(),
                fill.int8.offset(),
                fill.float64.offset()
        };
        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_NATIVE_INT32(),
                DatatypeLib.getH5T_NATIVE_INT8(),
                DatatypeLib.getH5T_NATIVE_DOUBLE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
        TestPaddedStruct data1 = new TestPaddedStruct();
        TestPaddedStruct data2 = new TestPaddedStruct();
        TestPaddedStruct data3 = new TestPaddedStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.int8.set((byte) 2);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.int8.set((byte) 6);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.int8.set((byte) 10);
        data3.float64.set(11.12);
        
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                3, 
                3, 
                sizeofStruct, 
                fieldNames, 
                fieldOffsets, 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        long[] fieldSizes = new long[]{4, 1, 8};
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.capacity()).order(ByteOrder.nativeOrder());
        int readStatus = TableLib.H5TBread_table(
               this.fid, 
               tabName, 
               sizeofStruct, 
               fieldOffsets, 
               fieldSizes, 
               buffer
        );
        assertTrue(readStatus >= 0);
        
        TestPaddedStruct data1a = new TestPaddedStruct();
        TestPaddedStruct data2a = new TestPaddedStruct();
        TestPaddedStruct data3a = new TestPaddedStruct();
        data1a.setByteBuffer(buffer, 0);
        data2a.setByteBuffer(buffer, data2a.size());
        data3a.setByteBuffer(buffer, data3a.size()*2);
        
        data1.getByteBuffer().rewind();
        data1a.getByteBuffer().rewind();
        byte[] wrote1 = new byte[sizeofStruct];
        data1.getByteBuffer().get(wrote1, 0, sizeofStruct);        
        byte[] read1 = new byte[sizeofStruct];
        data1a.getByteBuffer().get(read1, 0, sizeofStruct);
        assertArrayEquals(wrote1, read1);
        
        byte[] wrote2 = new byte[sizeofStruct];
        data2.getByteBuffer().get(wrote2, 0, sizeofStruct);        
        byte[] read2 = new byte[sizeofStruct];
        data2a.getByteBuffer().get(read2, 0, sizeofStruct);
        assertArrayEquals(wrote2, read2);
        
        byte[] wrote3 = new byte[sizeofStruct];
        data3.getByteBuffer().get(wrote3, 0, sizeofStruct);        
        byte[] read3 = new byte[sizeofStruct];
        data3a.getByteBuffer().get(read3, 0, sizeofStruct);
        assertArrayEquals(wrote3, read3);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBread_fields_name(int, java.lang.String, java.lang.String, java.math.BigInteger, java.math.BigInteger, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBread_fields_name() {
    	 String title = "table title";
         String tabName = "table";       
         TestPaddedStruct fill = new TestPaddedStruct();
         int sizeofStruct = fill.size();
		fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
         String[] fieldNames = new String[]{"int32", "int8", "float64"};
         long[] fieldOffsets = new long[]{
                 fill.int32.offset(),
                 fill.int8.offset(),
                 fill.float64.offset()
         };
         long[] fieldTypes = new long[]{
                 DatatypeLib.getH5T_NATIVE_INT32(),
                 DatatypeLib.getH5T_NATIVE_INT8(),
                 DatatypeLib.getH5T_NATIVE_DOUBLE()
         };
         
         ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
         TestPaddedStruct data1 = new TestPaddedStruct();
         TestPaddedStruct data2 = new TestPaddedStruct();
         TestPaddedStruct data3 = new TestPaddedStruct();
         data1.setByteBuffer(data, 0);
         data2.setByteBuffer(data, data2.size());
         data3.setByteBuffer(data, data3.size()*2);
         
         data1.int32.set(1);
         data1.int8.set((byte) 2);
         data1.float64.set(3.4);
         data2.int32.set(5);
         data2.int8.set((byte) 6);
         data2.float64.set(7.8);
         data3.int32.set(9);
         data3.int8.set((byte) 10);
         data3.float64.set(11.12);
         
         int createStatus = TableLib.H5TBmake_table(
                 title, 
                 this.fid, 
                 tabName, 
                 3, 
                 3, 
                 sizeofStruct, 
                 fieldNames, 
                 fieldOffsets, 
                 fieldTypes, 
                 192,
                 fill.getByteBuffer(),
                 0,
                 data
         );
         assertTrue(createStatus >= 0);
         
         
         long[] fieldSizes = new long[]{4, 8};
         
         TestSmallStruct small = new TestSmallStruct();
         int sizeofSmall = small.size();
         
         ByteBuffer buffer = ByteBuffer.allocateDirect(sizeofSmall*3).order(ByteOrder.nativeOrder());
         small.setByteBuffer(buffer, 0);
         long[] smallOffsets = new long[]{small.int32.offset(), small.float64.offset()};
        		          
         int readStatus = TableLib.H5TBread_fields_name(
        		 this.fid, 
        		 tabName, 
        		 "int32"+","+"float64", 
        		 0, 
        		 3, 
        		 sizeofSmall, 
        		 smallOffsets, 
        		 fieldSizes, 
        		 buffer
         );         
         assertTrue(readStatus >= 0);
         
         TestSmallStruct data1a = new TestSmallStruct();
         TestSmallStruct data2a = new TestSmallStruct();
         TestSmallStruct data3a = new TestSmallStruct();
         data1a.setByteBuffer(buffer, 0);
         data2a.setByteBuffer(buffer, data2a.size());
         data3a.setByteBuffer(buffer, data3a.size()*2);
         
         assertEquals(data1.int32.get(), data1a.int32.get());
         assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
         assertEquals(data2.int32.get(), data2a.int32.get());
         assertEquals(data2.float64.get(), data2a.float64.get(), 10e-14);
         assertEquals(data3.int32.get(), data3a.int32.get());
         assertEquals(data3.float64.get(), data3a.float64.get(), 10e-14);        		 
    }
    

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBread_fields_index(int, java.lang.String, java.math.BigInteger, int[], java.math.BigInteger, java.math.BigInteger, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBread_fields_index() {
    	 String title = "table title";
         String tabName = "table";       
         TestPaddedStruct fill = new TestPaddedStruct();
         int sizeofStruct = fill.size();
		fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
         String[] fieldNames = new String[]{"int32", "int8", "float64"};
         long[] fieldOffsets = new long[]{
                 fill.int32.offset(),
                 fill.int8.offset(),
                 fill.float64.offset()
         };
         long[] fieldTypes = new long[]{
                 DatatypeLib.getH5T_NATIVE_INT32(),
                 DatatypeLib.getH5T_NATIVE_INT8(),
                 DatatypeLib.getH5T_NATIVE_DOUBLE()
         };
         
         ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
         TestPaddedStruct data1 = new TestPaddedStruct();
         TestPaddedStruct data2 = new TestPaddedStruct();
         TestPaddedStruct data3 = new TestPaddedStruct();
         data1.setByteBuffer(data, 0);
         data2.setByteBuffer(data, data2.size());
         data3.setByteBuffer(data, data3.size()*2);
         
         data1.int32.set(1);
         data1.int8.set((byte) 2);
         data1.float64.set(3.4);
         data2.int32.set(5);
         data2.int8.set((byte) 6);
         data2.float64.set(7.8);
         data3.int32.set(9);
         data3.int8.set((byte) 10);
         data3.float64.set(11.12);
         
         int createStatus = TableLib.H5TBmake_table(
                 title, 
                 this.fid, 
                 tabName, 
                 3, 
                 3, 
                 sizeofStruct, 
                 fieldNames, 
                 fieldOffsets, 
                 fieldTypes, 
                 192,
                 fill.getByteBuffer(),
                 0,
                 data
         );
         assertTrue(createStatus >= 0);
         
         
         long[] fieldSizes = new long[]{4, 8};
         
         TestSmallStruct small = new TestSmallStruct();
         int sizeofSmall = small.size();
         
         ByteBuffer buffer = ByteBuffer.allocateDirect(sizeofSmall*3).order(ByteOrder.nativeOrder());
         small.setByteBuffer(buffer, 0);
         long[] smallOffsets = new long[]{small.int32.offset(), small.float64.offset()};
        		     
         int readStatus = TableLib.H5TBread_fields_index(
        		 this.fid, 
        		 tabName, 
        		 2, 
        		 new int[]{0, 2}, 
        		 0, 
        		 3, 
        		 sizeofSmall, 
        		 smallOffsets, 
        		 fieldSizes, 
        		 buffer
         );              
         assertTrue(readStatus >= 0);
         
         TestSmallStruct data1a = new TestSmallStruct();
         TestSmallStruct data2a = new TestSmallStruct();
         TestSmallStruct data3a = new TestSmallStruct();
         data1a.setByteBuffer(buffer, 0);
         data2a.setByteBuffer(buffer, data2a.size());
         data3a.setByteBuffer(buffer, data3a.size()*2);
         
         assertEquals(data1.int32.get(), data1a.int32.get());
         assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
         assertEquals(data2.int32.get(), data2a.int32.get());
         assertEquals(data2.float64.get(), data2a.float64.get(), 10e-14);
         assertEquals(data3.int32.get(), data3a.int32.get());
         assertEquals(data3.float64.get(), data3a.float64.get(), 10e-14);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBread_records(int, java.lang.String, java.math.BigInteger, java.math.BigInteger, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBread_records() {
        String title = "table title";
        String tabName = "table";       
        TestPaddedStruct fill = new TestPaddedStruct();
        final int sizeofStruct = fill.size();
        
        fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "int8", "float64"};
        long[] fieldOffsets = new long[]{
                fill.int32.offset(),
                fill.int8.offset(),
                fill.float64.offset()
        };
        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_NATIVE_INT32(),
                DatatypeLib.getH5T_NATIVE_INT8(),
                DatatypeLib.getH5T_NATIVE_DOUBLE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
        TestPaddedStruct data1 = new TestPaddedStruct();
        TestPaddedStruct data2 = new TestPaddedStruct();
        TestPaddedStruct data3 = new TestPaddedStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.int8.set((byte) 2);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.int8.set((byte) 6);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.int8.set((byte) 10);
        data3.float64.set(11.12);
        
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                3, 
                3, 
                sizeofStruct, 
                fieldNames, 
                fieldOffsets, 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        long[] fieldSizes = new long[]{4, 1, 8};
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(fill.getByteBuffer().capacity()).order(ByteOrder.nativeOrder());
        TestPaddedStruct read = new TestPaddedStruct();
        read.setByteBuffer(buffer, 0);
        int readStatus = TableLib.H5TBread_records(
                this.fid,
                tabName, 
                1, 
                1,
                sizeofStruct,
                fieldOffsets, 
                fieldSizes, 
                buffer);
        assertTrue(readStatus >= 0);
        
        assertEquals(5, read.int32.get());
        assertEquals(6, read.int8.get());
        assertEquals(7.8, read.float64.get(), 10e-14);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBget_table_info(int, java.lang.String, long[], long[])}.
     */
    @Test
    public final void testH5TBget_table_info() {
        String title = "table title";
        String tabName = "table";       
        TestPaddedStruct fill = new TestPaddedStruct();
        fill.setByteBuffer(ByteBuffer.allocateDirect(fill.size()).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "int8", "float64"};
        long[] fieldOffsets = new long[]{
                fill.int32.offset(),
                fill.int8.offset(),
                fill.float64.offset()
        };
        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_STD_I32BE(),
                DatatypeLib.getH5T_STD_I8BE(),
                DatatypeLib.getH5T_IEEE_F64BE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(fill.size()*3).order(ByteOrder.nativeOrder());
        TestPaddedStruct data1 = new TestPaddedStruct();
        TestPaddedStruct data2 = new TestPaddedStruct();
        TestPaddedStruct data3 = new TestPaddedStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.int8.set((byte) 2);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.int8.set((byte) 6);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.int8.set((byte) 10);
        data3.float64.set(11.12);
        
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                3, 
                3, 
                fill.size(), 
                fieldNames, 
                fieldOffsets, 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        long[] nfields = new long[1];
        long[] nrecords = new long[1];
        int getStatus = TableLib.H5TBget_table_info(this.fid, tabName, nfields, nrecords);
        assertTrue(getStatus >= 0);
        
        assertEquals(3, nfields[0]);
        assertEquals(3, nrecords[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBget_field_info(int, java.lang.String, java.lang.String[], long[], long[], long[])}.
     */
    @Test
    public final void testH5TBget_field_info() {
        String title = "table title";
        String tabName = "table";       
        TestPaddedStruct fill = new TestPaddedStruct();
        fill.setByteBuffer(ByteBuffer.allocateDirect(fill.size()).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "int8", "float64"};
        long[] fieldOffsets = new long[]{
                fill.int32.offset(),
                fill.int8.offset(),
                fill.float64.offset()
        };
        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_STD_I32BE(),
                DatatypeLib.getH5T_STD_I8BE(),
                DatatypeLib.getH5T_IEEE_F64BE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(fill.size()*3).order(ByteOrder.nativeOrder());
        TestPaddedStruct data1 = new TestPaddedStruct();
        TestPaddedStruct data2 = new TestPaddedStruct();
        TestPaddedStruct data3 = new TestPaddedStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.int8.set((byte) 2);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.int8.set((byte) 6);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.int8.set((byte) 10);
        data3.float64.set(11.12);
        
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                3, 
                3, 
                fill.size(), 
                fieldNames, 
                fieldOffsets, 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        byte[][] field_names = new byte[3][8];
        long[] field_sizes = new long[3];
        long[] field_offsets = new long[3];
        long[] type_size = new long[1];       
        int getStatus = TableLib.H5TBget_field_info(
                this.fid, 
                tabName,
                field_names, 
                field_sizes, 
                field_offsets, 
                type_size
        );
        assertTrue(getStatus >= 0);
        
        long[] fieldSizes = new long[]{4, 1, 8};
        
        for (int n=0; n<fieldNames.length; n++) {
        	assertEquals(fieldNames[n], StringUtils.fromNullTerm(field_names[n]));
        }
        assertArrayEquals(fieldSizes, field_sizes);
        assertArrayEquals(fieldOffsets, field_offsets);
        assertEquals(fill.size(), type_size[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBdelete_record(int, java.lang.String, java.math.BigInteger, java.math.BigInteger)}.
     */
    @Test
    public final void testH5TBdelete_record() {
    	 String title = "table title";
    	 String tabName = "table";       
         TestPaddedStruct fill = new TestPaddedStruct();
         final int sizeofStruct = fill.size();
         
         fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
         String[] fieldNames = new String[]{"int32", "int8", "float64"};
         long[] fieldOffsets = new long[]{
                 fill.int32.offset(),
                 fill.int8.offset(),
                 fill.float64.offset()
         };
         long[] fieldTypes = new long[]{
                 DatatypeLib.getH5T_NATIVE_INT32(),
                 DatatypeLib.getH5T_NATIVE_INT8(),
                 DatatypeLib.getH5T_NATIVE_DOUBLE()
         };
         
         ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
         TestPaddedStruct data1 = new TestPaddedStruct();
         TestPaddedStruct data2 = new TestPaddedStruct();
         TestPaddedStruct data3 = new TestPaddedStruct();
         data1.setByteBuffer(data, 0);
         data2.setByteBuffer(data, data2.size());
         data3.setByteBuffer(data, data3.size()*2);
         
         data1.int32.set(1);
         data1.int8.set((byte) 2);
         data1.float64.set(3.4);
         data2.int32.set(5);
         data2.int8.set((byte) 6);
         data2.float64.set(7.8);
         data3.int32.set(9);
         data3.int8.set((byte) 10);
         data3.float64.set(11.12);
         
         int createStatus = TableLib.H5TBmake_table(
                 title, 
                 this.fid, 
                 tabName, 
                 3, 
                 3, 
                 sizeofStruct, 
                 fieldNames, 
                 fieldOffsets, 
                 fieldTypes, 
                 192,
                 fill.getByteBuffer(),
                 0,
                 data
         );
         assertTrue(createStatus >= 0);         
        
         long[] fieldSizes = new long[]{4, 1, 8};         
         int writeStatus = TableLib.H5TBappend_records(
        		 this.fid, 
        		 tabName, 
        		 3, 
        		 sizeofStruct, 
        		 fieldOffsets, 
        		 fieldSizes, 
        		 data
         );
         assertTrue(writeStatus >= 0);
         
         int deleteStatus = TableLib.H5TBdelete_record(
        		 this.fid, 
        		 tabName, 
        		 1, 
        		 1
         );
         assertTrue(deleteStatus >= 0);
         
         ByteBuffer buffer = ByteBuffer.allocateDirect(sizeofStruct*2).order(ByteOrder.nativeOrder());
         int readStatus = TableLib.H5TBread_records(
        		 this.fid, 
        		 tabName, 
        		 0, 
        		 2, 
        		 sizeofStruct, 
        		 fieldOffsets, 
        		 fieldSizes, 
        		 buffer
         );              
         assertTrue(readStatus >= 0);
         
         TestPaddedStruct data1a = new TestPaddedStruct();
         TestPaddedStruct data2a = new TestPaddedStruct();
         data1a.setByteBuffer(buffer, 0);
         data2a.setByteBuffer(buffer, data2a.size());
         
         assertEquals(data1.int32.get(), data1a.int32.get());
         assertEquals(data1.int8.get(), data1a.int8.get());
         assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
         assertEquals(data3.int32.get(), data2a.int32.get());
         assertEquals(data3.int8.get(), data2a.int8.get());
         assertEquals(data3.float64.get(), data2a.float64.get(), 10e-14);
 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBinsert_record(int, java.lang.String, java.math.BigInteger, java.math.BigInteger, long, long[], long[], java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBinsert_record() {
    	 String title = "table title";
    	 String tabName = "table";       
         TestPaddedStruct fill = new TestPaddedStruct();
         final int sizeofStruct = fill.size();
         
         fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
         String[] fieldNames = new String[]{"int32", "int8", "float64"};
         long[] fieldOffsets = new long[]{
                 fill.int32.offset(),
                 fill.int8.offset(),
                 fill.float64.offset()
         };
         long[] fieldTypes = new long[]{
                 DatatypeLib.getH5T_NATIVE_INT32(),
                 DatatypeLib.getH5T_NATIVE_INT8(),
                 DatatypeLib.getH5T_NATIVE_DOUBLE()
         };
         
         ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
         TestPaddedStruct data1 = new TestPaddedStruct();
         TestPaddedStruct data2 = new TestPaddedStruct();
         TestPaddedStruct data3 = new TestPaddedStruct();
         data1.setByteBuffer(data, 0);
         data2.setByteBuffer(data, data2.size());
         data3.setByteBuffer(data, data3.size()*2);
         
         data1.int32.set(1);
         data1.int8.set((byte) 2);
         data1.float64.set(3.4);
         data2.int32.set(5);
         data2.int8.set((byte) 6);
         data2.float64.set(7.8);
         data3.int32.set(9);
         data3.int8.set((byte) 10);
         data3.float64.set(11.12);
         
         int createStatus = TableLib.H5TBmake_table(
                 title, 
                 this.fid, 
                 tabName, 
                 3, 
                 3, 
                 sizeofStruct, 
                 fieldNames, 
                 fieldOffsets, 
                 fieldTypes, 
                 192,
                 fill.getByteBuffer(),
                 0,
                 data
         );
         assertTrue(createStatus >= 0);
         
         ByteBuffer insertBuffer = ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder());
         TestPaddedStruct insert = new TestPaddedStruct();
         insert.setByteBuffer(insertBuffer, 0);
         insert.int32.set(13);
         insert.int8.set((byte) 14);
         insert.float64.set(15.16);
         long[] fieldSizes = new long[]{4, 1, 8};
         
         int writeStatus = TableLib.H5TBinsert_record(
        		 this.fid, 
        		 tabName, 
        		 0, 
        		 1, 
        		 sizeofStruct, 
        		 fieldOffsets, 
        		 fieldSizes, 
        		 insertBuffer
         );
         assertTrue(writeStatus >= 0);
         
         ByteBuffer buffer = ByteBuffer.allocateDirect(sizeofStruct*4).order(ByteOrder.nativeOrder());
         int readStatus = TableLib.H5TBread_records(
        		 this.fid, 
        		 tabName, 
        		 0, 
        		 4, 
        		 sizeofStruct, 
        		 fieldOffsets, 
        		 fieldSizes, 
        		 buffer
         );              
         assertTrue(readStatus >= 0);
         
         TestPaddedStruct data1a = new TestPaddedStruct();
         TestPaddedStruct data2a = new TestPaddedStruct();
         TestPaddedStruct data3a = new TestPaddedStruct();
         TestPaddedStruct data4a = new TestPaddedStruct();
         data1a.setByteBuffer(buffer, 0);
         data2a.setByteBuffer(buffer, data2a.size());
         data3a.setByteBuffer(buffer, data3a.size()*2);
         data4a.setByteBuffer(buffer, data4a.size()*3);
         
         assertEquals(insert.int32.get(), data1a.int32.get());
         assertEquals(insert.int8.get(), data1a.int8.get());
         assertEquals(insert.float64.get(), data1a.float64.get(), 10e-14);
         assertEquals(data1.int32.get(), data2a.int32.get());
         assertEquals(data1.int8.get(), data2a.int8.get());
         assertEquals(data1.float64.get(), data2a.float64.get(), 10e-14);
         assertEquals(data2.int32.get(), data3a.int32.get());
         assertEquals(data2.int8.get(), data3a.int8.get());
         assertEquals(data2.float64.get(), data3a.float64.get(), 10e-14);
         assertEquals(data3.int32.get(), data4a.int32.get());
         assertEquals(data3.int8.get(), data4a.int8.get());
         assertEquals(data3.float64.get(), data4a.float64.get(), 10e-14);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBadd_records_from(int, java.lang.String, java.math.BigInteger, java.math.BigInteger, java.lang.String, java.math.BigInteger)}.
     */
    @Test
    public final void testH5TBadd_records_from() {
    	 String title = "table title";
    	 String tabName = "table";       
         TestPaddedStruct fill = new TestPaddedStruct();
         final int sizeofStruct = fill.size();
         
         fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
         String[] fieldNames = new String[]{"int32", "int8", "float64"};

         long[] fieldTypes = new long[]{
                 DatatypeLib.getH5T_NATIVE_INT32(),
                 DatatypeLib.getH5T_NATIVE_INT8(),
                 DatatypeLib.getH5T_NATIVE_DOUBLE()
         };
         
         ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
         TestPaddedStruct data1 = new TestPaddedStruct();
         TestPaddedStruct data2 = new TestPaddedStruct();
         TestPaddedStruct data3 = new TestPaddedStruct();
         data1.setByteBuffer(data, 0);
         data2.setByteBuffer(data, data2.size());
         data3.setByteBuffer(data, data3.size()*2);
         
         data1.int32.set(1);
         data1.int8.set((byte) 2);
         data1.float64.set(3.4);
         data2.int32.set(5);
         data2.int8.set((byte) 6);
         data2.float64.set(7.8);
         data3.int32.set(9);
         data3.int8.set((byte) 10);
         data3.float64.set(11.12);
         
         int createStatus = TableLib.H5TBmake_table(
                 title, 
                 this.fid, 
                 tabName, 
                 3, 
                 3, 
                 sizeofStruct, 
                 fieldNames, 
                 fill.getOffsets(), 
                 fieldTypes, 
                 192,
                 fill.getByteBuffer(),
                 0,
                 data
         );
         assertTrue(createStatus >= 0);
         
         ByteBuffer datax = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
         TestPaddedStruct datax1 = new TestPaddedStruct();
         TestPaddedStruct datax2 = new TestPaddedStruct();
         TestPaddedStruct datax3 = new TestPaddedStruct();
         datax1.setByteBuffer(datax, 0);
         datax2.setByteBuffer(datax, datax2.size());
         datax3.setByteBuffer(datax, datax3.size()*2);
         datax1.int32.set(13);
         datax1.int8.set((byte) 14);
         datax1.float64.set(15.16);
         datax2.int32.set(17);
         datax2.int8.set((byte) 18);
         datax2.float64.set(19.20);
         datax3.int32.set(21);
         datax3.int8.set((byte) 22);
         datax3.float64.set(23.24);
         
         String otherName = "otherTable";
         createStatus = TableLib.H5TBmake_table(
                 title, 
                 this.fid, 
                 otherName, 
                 3, 
                 3, 
                 sizeofStruct, 
                 fieldNames, 
                 fill.getOffsets(), 
                 fieldTypes, 
                 192,
                 fill.getByteBuffer(),
                 0,
                 datax
         );
         assertTrue(createStatus >= 0);
         
         /*
          * FIXME H5TBadd_records_from cannot insert records at the end of the destination table.
          */
         int copyStatus = TableLib.H5TBadd_records_from(
        		this.fid, 
        		otherName, 
        		0, 
        		3, 
        		tabName, 
        		2 /* 3 should be the end of the table, 
        		   * but we get an error in the native code. */ 
        );
        assertTrue(copyStatus >= 0);
         
         ByteBuffer buffer = ByteBuffer.allocateDirect(data.capacity()*2).order(ByteOrder.nativeOrder());
         int readStatus = TableLib.H5TBread_records(
        		 this.fid, 
        		 tabName, 
        		 0, 
        		 6, 
        		 sizeofStruct, 
        		 fill.getOffsets(), 
        		 fill.getSizes(), 
        		 buffer
         );              
         assertTrue(readStatus >= 0);
         
         TestPaddedStruct data1a = new TestPaddedStruct();
         TestPaddedStruct data2a = new TestPaddedStruct();
         TestPaddedStruct data3a = new TestPaddedStruct();
         TestPaddedStruct data4a = new TestPaddedStruct();
         TestPaddedStruct data5a = new TestPaddedStruct();
         TestPaddedStruct data6a = new TestPaddedStruct();
         data1a.setByteBuffer(buffer, 0);
         data2a.setByteBuffer(buffer, data2a.size());
         data3a.setByteBuffer(buffer, data3a.size()*2);
         data4a.setByteBuffer(buffer, data4a.size()*3);
         data5a.setByteBuffer(buffer, data5a.size()*4);
         data6a.setByteBuffer(buffer, data6a.size()*5);
         
         assertEquals(data1.int32.get(), data1a.int32.get());
         assertEquals(data1.int8.get(), data1a.int8.get());
         assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
         assertEquals(data2.int32.get(), data2a.int32.get());
         assertEquals(data2.int8.get(), data2a.int8.get());
         assertEquals(data2.float64.get(), data2a.float64.get(), 10e-14);
         assertEquals(datax1.int32.get(), data3a.int32.get());
         assertEquals(datax1.int8.get(), data3a.int8.get());
         assertEquals(datax1.float64.get(), data3a.float64.get(), 10e-14); 
         assertEquals(datax2.int32.get(), data4a.int32.get());
         assertEquals(datax2.int8.get(), data4a.int8.get());
         assertEquals(datax2.float64.get(), data4a.float64.get(), 10e-14);
         assertEquals(datax3.int32.get(), data5a.int32.get());
         assertEquals(datax3.int8.get(), data5a.int8.get());
         assertEquals(datax3.float64.get(), data5a.float64.get(), 10e-14);
         assertEquals(data3.int32.get(), data6a.int32.get());
         assertEquals(data3.int8.get(), data6a.int8.get());
         assertEquals(data3.float64.get(), data6a.float64.get(), 10e-14);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBcombine_tables(int, java.lang.String, int, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testH5TBcombine_tables() {
   	 String title = "table title";
	 String tabName = "table";       
     TestPaddedStruct fill = new TestPaddedStruct();
     final int sizeofStruct = fill.size();
     
     fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
     String[] fieldNames = new String[]{"int32", "int8", "float64"};

     long[] fieldTypes = new long[]{
             DatatypeLib.getH5T_NATIVE_INT32(),
             DatatypeLib.getH5T_NATIVE_INT8(),
             DatatypeLib.getH5T_NATIVE_DOUBLE()
     };
     
     ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
     TestPaddedStruct data1 = new TestPaddedStruct();
     TestPaddedStruct data2 = new TestPaddedStruct();
     TestPaddedStruct data3 = new TestPaddedStruct();
     data1.setByteBuffer(data, 0);
     data2.setByteBuffer(data, data2.size());
     data3.setByteBuffer(data, data3.size()*2);
     
     data1.int32.set(1);
     data1.int8.set((byte) 2);
     data1.float64.set(3.4);
     data2.int32.set(5);
     data2.int8.set((byte) 6);
     data2.float64.set(7.8);
     data3.int32.set(9);
     data3.int8.set((byte) 10);
     data3.float64.set(11.12);
     
     int createStatus = TableLib.H5TBmake_table(
             title, 
             this.fid, 
             tabName, 
             3, 
             3, 
             sizeofStruct, 
             fieldNames, 
             fill.getOffsets(), 
             fieldTypes, 
             192,
             fill.getByteBuffer(),
             0,
             data
     );
     assertTrue(createStatus >= 0);
     
     ByteBuffer datax = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
     TestPaddedStruct datax1 = new TestPaddedStruct();
     TestPaddedStruct datax2 = new TestPaddedStruct();
     TestPaddedStruct datax3 = new TestPaddedStruct();
     datax1.setByteBuffer(datax, 0);
     datax2.setByteBuffer(datax, datax2.size());
     datax3.setByteBuffer(datax, datax3.size()*2);
     datax1.int32.set(13);
     datax1.int8.set((byte) 14);
     datax1.float64.set(15.16);
     datax2.int32.set(17);
     datax2.int8.set((byte) 18);
     datax2.float64.set(19.20);
     datax3.int32.set(21);
     datax3.int8.set((byte) 22);
     datax3.float64.set(23.24);
     
     String otherName = "otherTable";
     createStatus = TableLib.H5TBmake_table(
             title, 
             this.fid, 
             otherName, 
             3, 
             3, 
             sizeofStruct, 
             fieldNames, 
             fill.getOffsets(), 
             fieldTypes, 
             192,
             fill.getByteBuffer(),
             0,
             datax
     );
     assertTrue(createStatus >= 0);
     
     String destName = "destTable";
     int copyStatus = TableLib.H5TBcombine_tables(
    		 this.fid, 
    		 tabName, 
    		 this.fid, 
    		 otherName, 
    		 destName);
    assertTrue(copyStatus >= 0);
     
     ByteBuffer buffer = ByteBuffer.allocateDirect(data.capacity()*2).order(ByteOrder.nativeOrder());
     int readStatus = TableLib.H5TBread_records(
    		 this.fid, 
    		 destName, 
    		 0, 
    		 6, 
    		 sizeofStruct, 
    		 fill.getOffsets(), 
    		 fill.getSizes(), 
    		 buffer
     );              
     assertTrue(readStatus >= 0);
     
     TestPaddedStruct data1a = new TestPaddedStruct();
     TestPaddedStruct data2a = new TestPaddedStruct();
     TestPaddedStruct data3a = new TestPaddedStruct();
     TestPaddedStruct data4a = new TestPaddedStruct();
     TestPaddedStruct data5a = new TestPaddedStruct();
     TestPaddedStruct data6a = new TestPaddedStruct();
     data1a.setByteBuffer(buffer, 0);
     data2a.setByteBuffer(buffer, data2a.size());
     data3a.setByteBuffer(buffer, data3a.size()*2);
     data4a.setByteBuffer(buffer, data4a.size()*3);
     data5a.setByteBuffer(buffer, data5a.size()*4);
     data6a.setByteBuffer(buffer, data6a.size()*5);
     
     assertEquals(data1.int32.get(), data1a.int32.get());
     assertEquals(data1.int8.get(), data1a.int8.get());
     assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
     assertEquals(data2.int32.get(), data2a.int32.get());
     assertEquals(data2.int8.get(), data2a.int8.get());
     assertEquals(data2.float64.get(), data2a.float64.get(), 10e-14);
     assertEquals(data3.int32.get(), data3a.int32.get());
     assertEquals(data3.int8.get(), data3a.int8.get());
     assertEquals(data3.float64.get(), data3a.float64.get(), 10e-14); 
     assertEquals(datax1.int32.get(), data4a.int32.get());
     assertEquals(datax1.int8.get(), data4a.int8.get());
     assertEquals(datax1.float64.get(), data4a.float64.get(), 10e-14);
     assertEquals(datax2.int32.get(), data5a.int32.get());
     assertEquals(datax2.int8.get(), data5a.int8.get());
     assertEquals(datax2.float64.get(), data5a.float64.get(), 10e-14);
     assertEquals(datax3.int32.get(), data6a.int32.get());
     assertEquals(datax3.int8.get(), data6a.int8.get());
     assertEquals(datax3.float64.get(), data6a.float64.get(), 10e-14);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBinsert_field(int, java.lang.String, java.lang.String, int, java.math.BigInteger, java.nio.Buffer, java.nio.Buffer)}.
     */
    @Test
    public final void testH5TBinsert_field() {
    	String title = "table title";
        String tabName = "table";       
        TestSmallStruct fill = new TestSmallStruct();
        int sizeofStruct = fill.size();
        fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "float64"};
        
        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_NATIVE_INT32(),
                DatatypeLib.getH5T_NATIVE_DOUBLE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
        TestSmallStruct data1 = new TestSmallStruct();
        TestSmallStruct data2 = new TestSmallStruct();
        TestSmallStruct data3 = new TestSmallStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.float64.set(11.12);
                
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                2, 
                3, 
                sizeofStruct, 
                fieldNames, 
                data1.getOffsets(), 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        ByteBuffer byteFill = ByteBuffer.allocateDirect(1).order(ByteOrder.nativeOrder());
        ByteBuffer byteData = ByteBuffer.allocateDirect(3).order(ByteOrder.nativeOrder());
        byteData.put((byte) 1);
        byteData.put((byte) 2);
        byteData.put((byte) 3);
       
        int writeStatus = TableLib.H5TBinsert_field(
       		 this.fid, 
       		 tabName, 
       		 "int8", 
       		 DatatypeLib.getH5T_NATIVE_INT8(), 
       		 1, 
       		 byteFill, 
       		 byteData
        );
        assertTrue(writeStatus >= 0);
        
        TestPaddedStruct large = new TestPaddedStruct();         
        ByteBuffer buffer = ByteBuffer.allocateDirect(large.size()*3).order(ByteOrder.nativeOrder());
        int readStatus = TableLib.H5TBread_table(
               this.fid, 
               tabName, 
               large.size(), 
               large.getOffsets(), 
               large.getSizes(), 
               buffer
        );
        assertTrue(readStatus >= 0);
        
        TestPaddedStruct data1a = new TestPaddedStruct();
        TestPaddedStruct data2a = new TestPaddedStruct();
        TestPaddedStruct data3a = new TestPaddedStruct();
        data1a.setByteBuffer(buffer, 0);
        data2a.setByteBuffer(buffer, data2a.size());
        data3a.setByteBuffer(buffer, data3a.size()*2);                 
        
        byteData.rewind();
        assertEquals(data1.int32.get(), data1a.int32.get());
        assertEquals(byteData.get(0), data1a.int8.get());
        assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
        assertEquals(data2.int32.get(), data2a.int32.get());
        assertEquals(byteData.get(1), data2a.int8.get());
        assertEquals(data2.float64.get(), data2a.float64.get(), 10e-14);
        assertEquals(data3.int32.get(), data3a.int32.get());
        assertEquals(byteData.get(2), data3a.int8.get());
        assertEquals(data3.float64.get(), data3a.float64.get(), 10e-14);   
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBdelete_field(int, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testH5TBdelete_field() {
    	String title = "table title";
        String tabName = "table";       
        TestPaddedStruct fill = new TestPaddedStruct();
        int sizeofStruct = fill.size();
		fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "int8", "float64"};

        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_NATIVE_INT32(),
                DatatypeLib.getH5T_NATIVE_INT8(),
                DatatypeLib.getH5T_NATIVE_DOUBLE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
        TestPaddedStruct data1 = new TestPaddedStruct();
        TestPaddedStruct data2 = new TestPaddedStruct();
        TestPaddedStruct data3 = new TestPaddedStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.int8.set((byte) 2);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.int8.set((byte) 6);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.int8.set((byte) 10);
        data3.float64.set(11.12);
        
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                3, 
                3, 
                sizeofStruct, 
                fieldNames, 
                fill.getOffsets(), 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        int deleteStatus = TableLib.H5TBdelete_field(this.fid, tabName, "int8");
        assertTrue(deleteStatus >= 0);
        
        TestSmallStruct small = new TestSmallStruct();
        int sizeofSmall = small.size();
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(sizeofSmall*3).order(ByteOrder.nativeOrder());
        small.setByteBuffer(buffer, 0);
       	          
        int readStatus = TableLib.H5TBread_table(
        		this.fid, 
        		tabName, 
        		sizeofSmall, 
        		small.getOffsets(), 
        		small.getSizes(), 
        		buffer
        );        
        assertTrue(readStatus >= 0);
        
        TestSmallStruct data1a = new TestSmallStruct();
        TestSmallStruct data2a = new TestSmallStruct();
        TestSmallStruct data3a = new TestSmallStruct();
        data1a.setByteBuffer(buffer, 0);
        data2a.setByteBuffer(buffer, data2a.size());
        data3a.setByteBuffer(buffer, data3a.size()*2);
        
        assertEquals(data1.int32.get(), data1a.int32.get());
        assertEquals(data1.float64.get(), data1a.float64.get(), 10e-14);
        assertEquals(data2.int32.get(), data2a.int32.get());
        assertEquals(data2.float64.get(), data2a.float64.get(), 10e-14);
        assertEquals(data3.int32.get(), data3a.int32.get());
        assertEquals(data3.float64.get(), data3a.float64.get(), 10e-14);     
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBAget_title(int, java.lang.String)}.
     */
    @Test
    public final void testH5TBAget_title() {
        String title = "table title";
        String tabName = "table";       
        TestSmallStruct fill = new TestSmallStruct();
        int sizeofStruct = fill.size();
        fill.setByteBuffer(ByteBuffer.allocateDirect(sizeofStruct).order(ByteOrder.nativeOrder()), 0);   
        String[] fieldNames = new String[]{"int32", "float64"};
        
        long[] fieldTypes = new long[]{
                DatatypeLib.getH5T_NATIVE_INT32(),
                DatatypeLib.getH5T_NATIVE_DOUBLE()
        };
        
        ByteBuffer data = ByteBuffer.allocateDirect(sizeofStruct*3).order(ByteOrder.nativeOrder());
        TestSmallStruct data1 = new TestSmallStruct();
        TestSmallStruct data2 = new TestSmallStruct();
        TestSmallStruct data3 = new TestSmallStruct();
        data1.setByteBuffer(data, 0);
        data2.setByteBuffer(data, data2.size());
        data3.setByteBuffer(data, data3.size()*2);
        
        data1.int32.set(1);
        data1.float64.set(3.4);
        data2.int32.set(5);
        data2.float64.set(7.8);
        data3.int32.set(9);
        data3.float64.set(11.12);
                
        int createStatus = TableLib.H5TBmake_table(
                title, 
                this.fid, 
                tabName, 
                2, 
                3, 
                sizeofStruct, 
                fieldNames, 
                data1.getOffsets(), 
                fieldTypes, 
                192,
                fill.getByteBuffer(),
                0,
                data
        );
        assertTrue(createStatus >= 0);
        
        int tabId = ObjectLib.H5Oopen(this.fid, tabName, PropertiesLib.H5P_DEFAULT);
        assertTrue(tabId >= 0);
        
        byte[] bTitle = new byte[title.length()+1];
        int getStatus = TableLib.H5TBAget_title(tabId, bTitle);
        assertTrue(getStatus >= 0);
        assertEquals(title, StringUtils.fromNullTerm(bTitle));
        
        int closeStatus =  ObjectLib.H5Oclose(tabId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.TableLib#H5TBAget_fill(int, java.lang.String, int, java.nio.ByteBuffer)}.
     */
    @Test
    public final void testH5TBAget_fill() {
    	fail("Not yet implemented"); // TODO test H5TBAget_fill
    }

}
