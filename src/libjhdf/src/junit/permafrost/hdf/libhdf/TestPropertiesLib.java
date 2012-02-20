/**
 *
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Tests {@link PropertiesLib}.
 *
 */
public class TestPropertiesLib {
    
    
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
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pall_filters_avail(int)}.
     */
    @Test
    public void testH5Pall_filters_avail() {
        
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunks = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(plistId, chunks.length, chunks);
        status = PropertiesLib.H5Pset_filter(
                plistId, 
                FilterLib.H5Z_FILTER_DEFLATE, 
                FilterLib.H5Z_FILTER_CONFIG_DECODE_ENABLED & FilterLib.H5Z_FILTER_CONFIG_ENCODE_ENABLED, 
                0, 
                new long[0]);
        assertTrue(status >= 0);
        int filtersOkay = PropertiesLib.H5Pall_filters_avail(plistId);
        assertTrue(filtersOkay > 0);
        
        status = PropertiesLib.H5Pset_filter(
                plistId, 
                FilterLib.H5Z_FILTER_RESERVED, 
                0, 
                0, 
                new long[0]);
        assertTrue(status >= 0);
        filtersOkay = PropertiesLib.H5Pall_filters_avail(plistId);
        assertTrue(filtersOkay == 0);
        PropertiesLib.H5Pclose(plistId);
        
        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pclose(int)}.
     */
    @Test
    public void testH5Pclose() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        int status = PropertiesLib.H5Pclose(plistId);
        assertTrue(status >= 0);
        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pclose_class(int)}.
     */
    @Test
    public void testH5Pclose_class() {
       int clsId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_CLASS());
       String name = PropertiesLib.H5Pget_class_name(clsId);
       assertNotNull(name);
       int status = PropertiesLib.H5Pclose_class(clsId);
       assertTrue(status >= 0);       
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pcopy(int)}.
     */
    @Test
    public void testH5Pcopy() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunks = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(plistId, chunks.length, chunks);
        status = PropertiesLib.H5Pset_filter(
                plistId, 
                FilterLib.H5Z_FILTER_DEFLATE, 
                FilterLib.H5Z_FILTER_CONFIG_DECODE_ENABLED & FilterLib.H5Z_FILTER_CONFIG_ENCODE_ENABLED, 
                0, 
                new long[0]);
        assertTrue(status >= 0);
        
        long[] xchunks = new long[chunks.length];
        int plistId2 = PropertiesLib.H5Pcopy(plistId);
        PropertiesLib.H5Pget_chunk(plistId2, xchunks.length, xchunks);
        
        PropertiesLib.H5Pclose(plistId);
        PropertiesLib.H5Pclose(plistId2);
        
        assertArrayEquals(chunks, xchunks);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pcopy_prop(int, int, java.lang.String)}.
     */
    @Test
    public void testH5Pcopy_prop() {
        int plistId1 = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_STRING_CREATE_CLASS());
        int plistId2 = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_GROUP_CREATE_CLASS());
        
        final String propName = "character_encoding";        
        int exists = PropertiesLib.H5Pexist(plistId2, propName);
        assertFalse(exists > 0);
        
        int status = PropertiesLib.H5Pcopy_prop(plistId2, plistId1, propName);        
        exists = PropertiesLib.H5Pexist(plistId2, propName);
        
        PropertiesLib.H5Pclose(plistId1);
        PropertiesLib.H5Pclose(plistId2);
        assertTrue(status >= 0);
        assertTrue(exists > 0);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pcreate(int)}.
     */
    @Test
    public void testH5Pcreate() {
        int clsId = PropertiesLib.getH5P_ATTRIBUTE_CREATE_CLASS();
        int listId = PropertiesLib.H5Pcreate(clsId);
        assertTrue(listId >= 0);
        
        int xclsId = PropertiesLib.H5Pget_class(listId);
        int equal = PropertiesLib.H5Pequal(clsId, xclsId);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(equal > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pequal(int, int)}.
     */
    @Test
    public void testH5Pequal() {
        int clsId = PropertiesLib.getH5P_ATTRIBUTE_CREATE_CLASS();
        int listId = PropertiesLib.H5Pcreate(clsId);
        assertTrue(listId >= 0);
        
        int xclsId = PropertiesLib.H5Pget_class(listId);
        int equalCls = PropertiesLib.H5Pequal(clsId, xclsId);
        
        int xlistId = PropertiesLib.H5Pcopy(listId);
        int equalLst = PropertiesLib.H5Pequal(listId, xlistId);
        
        PropertiesLib.H5Pclose(listId);
        PropertiesLib.H5Pclose(xlistId);
        
        assertTrue(equalCls > 0);
        assertTrue(equalLst > 0);
        
        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pexist(int, java.lang.String)}.
     */
    @Test
    public void testH5Pexist() {
        int plistId = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_STRING_CREATE_CLASS());    
      
        final String propName = "character_encoding";        
        int exists = PropertiesLib.H5Pexist(plistId, propName);
        assertTrue(exists > 0);
       
        PropertiesLib.H5Pclose(plistId);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pfill_value_defined(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5D_fill_value_t)}.
     */
    @Test
    public void testH5Pfill_value_defined() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        DatasetFillValueType[] fill = new DatasetFillValueType[1]; 
        int status = PropertiesLib.H5Pfill_value_defined(plistId, fill);
        assertTrue(status >= 0);
        assertEquals(DatasetFillValueType.H5D_FILL_VALUE_DEFAULT, fill[0]);
        
        int dblId = DatatypeLib.getH5T_IEEE_F64BE();
        DoubleBuffer buff = ByteBuffer.allocateDirect(8).order(ByteOrder.BIG_ENDIAN).asDoubleBuffer();
        buff.put(1.2);
        status = PropertiesLib.H5Pset_fill_value(plistId, dblId, buff);
        assertTrue(status >= 0);
        
        status = PropertiesLib.H5Pfill_value_defined(plistId, fill);        
        PropertiesLib.H5Pclose(plistId);
        
        assertTrue(status >= 0);
        assertEquals(DatasetFillValueType.H5D_FILL_VALUE_USER_DEFINED, fill[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget(int, java.lang.String, java.nio.Buffer)}.
     */
    @Test
    public void testH5Pget() {
        int plistId = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_STRING_CREATE_CLASS());
        DatatypeCharsetType type = DatatypeCharsetType.H5T_CSET_UTF8;        
        int status = PropertiesLib.H5Pset_char_encoding(plistId, type);     
        assertTrue(status >= 0);        
      
        final String propName = "character_encoding";        
        int exists = PropertiesLib.H5Pexist(plistId, propName);
        assertTrue(exists > 0);
        
        long[] size = new long[1];
        status = PropertiesLib.H5Pget_size(plistId, propName, size);
        assertTrue(status >= 0);        
        assertTrue(size[0] > 0);
        
        ByteBuffer buffer = ByteBuffer.allocateDirect((int) size[0]).order(ByteOrder.nativeOrder());
        status = PropertiesLib.H5Pget(plistId, propName, buffer);
                  
        PropertiesLib.H5Pclose(plistId);
        assertTrue(status >= 0);
        
        DatatypeCharsetType xtype = DatatypeCharsetType.swigToEnum(buffer.getInt());
        assertEquals(type, xtype);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_alignment(int, long[], long[])}.
     */
    @Test
    public void testH5Pget_alignment() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        long[] threshold = new long[1];
        long[] alignment = new long[1];
        int status = PropertiesLib.H5Pget_alignment(plistId, threshold, alignment);
        assertTrue(status >= 0);
        
        assertTrue(threshold[0] > 0);
        assertTrue(alignment[0] > 0);
        
        PropertiesLib.H5Pclose(plistId);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_alloc_time(int, permafrost.hdf.libhdf.DatasetAllocationTimeType[])}.
     */
    @Test
    public void testH5Pget_alloc_time() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        DatasetAllocationTimeType[] alloc = new DatasetAllocationTimeType[1]; 
        int status = PropertiesLib.H5Pget_alloc_time(plistId, alloc);
        assertTrue(status >= 0);
        assertEquals(DatasetAllocationTimeType.H5D_ALLOC_TIME_LATE, alloc[0]);
                
        status = PropertiesLib.H5Pset_alloc_time(plistId, DatasetAllocationTimeType.H5D_ALLOC_TIME_EARLY);
        assertTrue(status >= 0);
        
        status = PropertiesLib.H5Pget_alloc_time(plistId, alloc);
        PropertiesLib.H5Pclose(plistId);
        
        assertTrue(status >= 0);
        assertEquals(DatasetAllocationTimeType.H5D_ALLOC_TIME_EARLY, alloc[0]);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_attr_creation_order(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_attr_creation_order() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        
        long[] flags = new long[1];
        int status = PropertiesLib.H5Pget_attr_creation_order(plistId, flags);
        assertTrue(status >= 0);
        assertEquals(0, flags[0]);
        
        flags[0] = PropertiesLib.H5P_CRT_ORDER_INDEXED & PropertiesLib.H5P_CRT_ORDER_TRACKED;
        status = PropertiesLib.H5Pset_attr_creation_order(plistId, flags[0]);
        assertTrue(status >= 0);
        
        long[] xflags = new long[1];
        status = PropertiesLib.H5Pget_attr_creation_order(plistId, xflags);
        assertTrue(status >= 0);
                
        PropertiesLib.H5Pclose(plistId);
        assertEquals(flags[0], xflags[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_attr_phase_change(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_attr_phase_change() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        
        long[] max_compact = new long[1];
        long[] min_dense = new long[1];
        int status = PropertiesLib.H5Pget_attr_phase_change(plistId, max_compact, min_dense);
        assertTrue(status >= 0);
        assertEquals(8, max_compact[0]);
        assertEquals(6, min_dense[0]);
        
        PropertiesLib.H5Pclose(plistId);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_btree_ratios(int, double[], double[], double[])}.
     */
    @Test
    public void testH5Pget_btree_ratios() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        double[] left = new double[1];
        double[] middle = new double[1];
        double[] right = new double[1];
        int status = PropertiesLib.H5Pget_btree_ratios(plistId, left, middle, right);
        assertTrue(status >= 0);        
        
        assertEquals(0.1, left[0], 1e-9);
        assertEquals(0.5, middle[0], 1e-9);
        assertEquals(0.9, right[0], 1e-9);
        
        PropertiesLib.H5Pclose(plistId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_buffer(int, permafrost.hdf.libhdf.SWIGTYPE_p_p_void, permafrost.hdf.libhdf.SWIGTYPE_p_p_void)}.
     */
    @Test
    public void testH5Pget_buffer() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_cache(int, int[], long[], long[], double[])}.
     */
    @Test
    public void testH5Pget_cache() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int[] mdc_nelmts = new int[1]; 
        long[] rdcc_nelmts = new long[1]; 
        long[] rdcc_nbytes = new long[1]; 
        double[] rdcc_w0 = new double[1];
        
        int status = PropertiesLib.H5Pget_cache(
                plistId, 
                mdc_nelmts, 
                rdcc_nelmts, 
                rdcc_nbytes, 
                rdcc_w0
        );
        assertTrue(status >= 0);       
        
        assertTrue(mdc_nelmts[0] == 0);
        assertTrue(rdcc_nelmts[0] > 0);
        assertTrue(rdcc_nbytes[0] > 0);
        assertEquals(0.75, rdcc_w0[0], 1e-9);
        
        PropertiesLib.H5Pclose(plistId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_char_encoding(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5T_cset_t)}.
     */
    @Test
    public void testH5Pget_char_encoding() {
        int plistId = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_STRING_CREATE_CLASS());
        DatatypeCharsetType[] type = new DatatypeCharsetType[1];
        int status = PropertiesLib.H5Pget_char_encoding(plistId, type);       
        assertTrue(status >= 0);        
        assertEquals(DatatypeCharsetType.H5T_CSET_ASCII, type[0]);
        
        type[0] = DatatypeCharsetType.H5T_CSET_UTF8;        
        status = PropertiesLib.H5Pset_char_encoding(plistId, type[0]);     
        assertTrue(status >= 0);        
      
        DatatypeCharsetType[] xtype = new DatatypeCharsetType[1];
        status = PropertiesLib.H5Pget_char_encoding(plistId, xtype);       
        assertTrue(status >= 0);        
        assertEquals(type[0], xtype[0]);
        
        PropertiesLib.H5Pclose(plistId);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_chunk(int, int, long[])}.
     */
    @Test
    public void testH5Pget_chunk() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunks = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(plistId, chunks.length, chunks);        
        assertTrue(status >= 0);
        
        long[] xchunks = new long[chunks.length];       
        PropertiesLib.H5Pget_chunk(plistId, xchunks.length, xchunks);
        
        PropertiesLib.H5Pclose(plistId);
        
        assertArrayEquals(chunks, xchunks);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_class(int)}.
     */
    @Test
    public void testH5Pget_class() {
        int clsId = PropertiesLib.getH5P_ATTRIBUTE_CREATE_CLASS();
        int listId = PropertiesLib.H5Pcreate(clsId);
        assertTrue(listId >= 0);
        
        int xclsId = PropertiesLib.H5Pget_class(listId);
        int equal = PropertiesLib.H5Pequal(clsId, xclsId);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(equal > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_class_name(int)}.
     */
    @Test
    public void testH5Pget_class_name() {
        int clsId = PropertiesLib.getH5P_ATTRIBUTE_CREATE_CLASS();
        String name = PropertiesLib.H5Pget_class_name(clsId);
        assertNotNull(name);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_class_parent(int)}.
     */
    @Test
    public void testH5Pget_class_parent() {
        int clsId = PropertiesLib.getH5P_DATASET_CREATE_CLASS();
        int parentId = PropertiesLib.H5Pget_class_parent(clsId);
        assertTrue(parentId > 0);
        int equal = PropertiesLib.H5Pequal(PropertiesLib.getH5P_OBJECT_CREATE_CLASS(), parentId);
        assertTrue(equal > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_copy_object(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_copy_object() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_OBJECT_COPY_LIST());
        long[] flags = new long[1];
        int status = PropertiesLib.H5Pget_copy_object(listId, flags);
        assertEquals(0, status);
        
        flags[0] = ObjectLib.H5O_COPY_SHALLOW_HIERARCHY_FLAG & ObjectLib.H5O_COPY_WITHOUT_ATTR_FLAG;
        status = PropertiesLib.H5Pset_copy_object(listId, flags[0]);
        
        long[] xflags = new long[1];
        status = PropertiesLib.H5Pget_copy_object(listId, xflags);
        PropertiesLib.H5Pclose(listId);
        assertEquals(0, status);
        assertEquals(flags[0], xflags[0]);      
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_create_intermediate_group(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_create_intermediate_group() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_CREATE_LIST());
        long[] flags = new long[1];
        int status = PropertiesLib.H5Pget_create_intermediate_group(listId, flags);
        assertEquals(0, status);
        assertEquals(0, flags[0]);
        
        flags[0] = 1;
        status = PropertiesLib.H5Pset_create_intermediate_group(listId, flags[0]);
        
        long[] xflags = new long[1];
        status = PropertiesLib.H5Pget_create_intermediate_group(listId, xflags);
        PropertiesLib.H5Pclose(listId);
        assertEquals(0, status);
        assertEquals(flags[0], xflags[0]);    
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_data_transform(int, java.lang.String, long)}.
     */
    @Test
    public void testH5Pget_data_transform() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        String expression = "x+1";
        int status = PropertiesLib.H5Pset_data_transform(listId, expression);
        assertTrue(status >= 0);
        
        byte[] xexpression = new byte[expression.length()+1];
        int size = PropertiesLib.H5Pget_data_transform(listId, xexpression, xexpression.length);
        PropertiesLib.H5Pclose(listId);
        
        assertEquals(size+1, xexpression.length);
        assertEquals(expression, StringUtils.fromNullTerm(xexpression));
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_driver(int)}.
     */
    @Test
    public void testH5Pget_driver() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int driverId = PropertiesLib.H5Pget_driver(plistId);
        assertTrue(driverId > 0);
        PropertiesLib.H5Pclose(plistId);   
    }
   

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_edc_check(int)}.
     */
    @Test
    public void testH5Pget_edc_check() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        FilterErrorDetectionType type = PropertiesLib.H5Pget_edc_check(listId);
        assertEquals(FilterErrorDetectionType.H5Z_ENABLE_EDC, type);
        int status = PropertiesLib.H5Pset_edc_check(
                listId, 
                FilterErrorDetectionType.H5Z_DISABLE_EDC
        );
        assertTrue(status >= 0);
        type = PropertiesLib.H5Pget_edc_check(listId);
        assertEquals(FilterErrorDetectionType.H5Z_DISABLE_EDC, type);
        PropertiesLib.H5Pclose(listId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_elink_prefix(int, java.lang.String, long)}.
     */
    @Test
    public void testH5Pget_elink_prefix() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_ACCESS_LIST());
        String prefix = "/external";
        int status = PropertiesLib.H5Pset_elink_prefix(listId, prefix);
        assertTrue(status >= 0);
        
        byte[] xprefix = new byte[prefix.length()+1];
        int size = PropertiesLib.H5Pget_elink_prefix(listId, xprefix, xprefix.length);
        
        PropertiesLib.H5Pclose(listId);        
        assertTrue(size > 0);
        assertEquals(prefix, StringUtils.fromNullTerm(xprefix));        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_est_link_info(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_est_link_info() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        long[] nEntries = new long[1];
        long[] nameLen = new long[1];
        int status = PropertiesLib.H5Pget_est_link_info(listId, nEntries, nameLen);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertTrue(nEntries[0] > 0);
        assertTrue(nameLen[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_external(int, long, long, byte[], long[], long[])}.
     */
    @Test
    public void testH5Pget_external() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        String name = "external";
        long offset = 1;
        long size = 2048;
        int status = PropertiesLib.H5Pset_external(listId, name, offset, size);
        assertTrue(status >= 0);
        
        byte[] xname = new byte[name.length()+1];
        long[] xoffset = new long[1];
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_external(listId, 0, xname.length, xname, xoffset, xsize);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertEquals(name, StringUtils.fromNullTerm(xname));
        assertEquals(offset, xoffset[0]);
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_external_count(int)}.
     */
    @Test
    public void testH5Pget_external_count() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        int count = PropertiesLib.H5Pget_external_count(listId);
        assertEquals(0, count);
        
        String name = "external";
        long offset = 1;
        long size = 2048;
        int status = PropertiesLib.H5Pset_external(listId, name, offset, size);
        assertTrue(status >= 0);
        count = PropertiesLib.H5Pget_external_count(listId);
        assertEquals(1, count);

        PropertiesLib.H5Pclose(listId);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_family_offset(int, int[])}.
     */
    @Test
    public void testH5Pget_family_offset() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int listId2 = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        long memb_size = 4096;
        
        int status = PropertiesLib.H5Pset_fapl_family(listId, memb_size, listId2);
        assertTrue(status >= 0);
        
        long offset = 16l;
        status = PropertiesLib.H5Pset_family_offset(listId, offset);
        
        long[] xoffset = new long[1];
        status = PropertiesLib.H5Pget_family_offset(listId, xoffset);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertEquals(offset, xoffset[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_fapl_core(int, permafrost.hdf.libhdf.SWIGTYPE_p_size_t, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_fapl_core() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        long increment = 4096;
        long backing_store = 1;
        int status = PropertiesLib.H5Pset_fapl_core(listId, increment, backing_store);
        assertTrue(status >= 0);
        
        long[] xincrement = new long[1];
        long[] xbacking_store = new long[1];
        status = PropertiesLib.H5Pget_fapl_core(listId, xincrement, xbacking_store);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertEquals(increment, xincrement[0]);
        assertEquals(backing_store, xbacking_store[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_fapl_family(int, long[], int[])}.
     */
    @Test
    public void testH5Pget_fapl_family() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int listId2 = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        long memb_size = 4096;
        
        int status = PropertiesLib.H5Pset_fapl_family(listId, memb_size, listId2);
        assertTrue(status >= 0);
        
        long[] xmemb_size = new long[1];
        int[] xListId = new int[1];
        status = PropertiesLib.H5Pget_fapl_family(listId, xmemb_size, xListId);
        int equals = PropertiesLib.H5Pequal(listId2, xListId[0]);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertEquals(memb_size, xmemb_size[0]);
        assertTrue(equals > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_fapl_multi(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5FD_mem_t, int[], permafrost.hdf.libhdf.SWIGTYPE_p_p_char, long[], long[])}.
     */
    @Test
    public void testH5Pget_fapl_multi() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int listId2 = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());        
        
        FileDriverAllocationType[] membMap = new FileDriverAllocationType[FileDriverAllocationType.H5FD_MEM_NTYPES.swigValue()];
        for (int n=0; n<membMap.length; n++) membMap[n] = FileDriverAllocationType.H5FD_MEM_DEFAULT;
        membMap[0] = FileDriverAllocationType.H5FD_MEM_SUPER;
        membMap[1] = FileDriverAllocationType.H5FD_MEM_BTREE;
        
        int[] membFAPL = new int[membMap.length];
        for (int n=0; n<membFAPL.length; n++) membFAPL[n] = listId2;
        
        String[] membName = new String[membMap.length];
        for (int n=0; n<membName.length; n++) membName[n] = "%s-X.h5";
        membName[0] = "%s-s.h5";
        membName[1] = "%s-b.h5";
        
        long[] membOff = new long[membMap.length];
        for (int n=0; n<membOff.length; n++) membOff[n] = -1l;
        
        long relax = 1;
        int status = PropertiesLib.H5Pset_fapl_multi(
                listId, 
                membMap,
                membFAPL, 
                membName,
                membOff,
                relax);
        assertTrue(status >= 0);
               
        FileDriverAllocationType[] xmembMap = new FileDriverAllocationType[membMap.length];
        int[] xmembFAPL = new int[membFAPL.length];
        byte[][] xmembName = new byte[membName.length][8];
        long[] xmembOff = new long[membOff.length];
        long[] xrelax = new long[1];
        status = PropertiesLib.H5Pget_fapl_multi(
                listId, 
                xmembMap, 
                xmembFAPL, 
                xmembName, 
                xmembOff, 
                xrelax
        );
        
        PropertiesLib.H5Pclose(listId);
        PropertiesLib.H5Pclose(listId2);
        assertTrue(status >= 0);
        assertArrayEquals(membMap, xmembMap);   
        for(int n=0; n<membName.length; n++) {
            assertEquals(membName[n], StringUtils.fromNullTerm(xmembName[n]));
        }
        assertEquals(relax, xrelax[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_fclose_degree(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5F_close_degree_t)}.
     */
    @Test
    public void testH5Pget_fclose_degree() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        FileCloseBehaviorType[] degree = new FileCloseBehaviorType[1];
        int status = PropertiesLib.H5Pget_fclose_degree(plistId, degree);
        assertTrue(status >= 0);        
        assertEquals(FileCloseBehaviorType.H5F_CLOSE_DEFAULT, degree[0]);
        
        
        status = PropertiesLib.H5Pset_fclose_degree(plistId, FileCloseBehaviorType.H5F_CLOSE_STRONG);
        assertTrue(status >= 0);       
        
        FileCloseBehaviorType[] xdegree = new FileCloseBehaviorType[1];
        status = PropertiesLib.H5Pget_fclose_degree(plistId, xdegree);
        assertTrue(status >= 0);        
        assertEquals(FileCloseBehaviorType.H5F_CLOSE_STRONG, xdegree[0]);
        
        PropertiesLib.H5Pclose(plistId);   
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_fill_time(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5D_fill_time_t)}.
     */
    @Test
    public void testH5Pget_fill_time() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        DatasetFillTimeType[] alloc = new DatasetFillTimeType[1]; 
        int status = PropertiesLib.H5Pget_fill_time(plistId, alloc);
        assertTrue(status >= 0);
        assertEquals(DatasetFillTimeType.H5D_FILL_TIME_IFSET, alloc[0]);
                
        status = PropertiesLib.H5Pset_fill_time(plistId, DatasetFillTimeType.H5D_FILL_TIME_ALLOC);
        assertTrue(status >= 0);
        
        status = PropertiesLib.H5Pget_fill_time(plistId, alloc);
        PropertiesLib.H5Pclose(plistId);
        
        assertTrue(status >= 0);
        assertEquals(DatasetFillTimeType.H5D_FILL_TIME_ALLOC, alloc[0]); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_fill_value(int, int, java.nio.Buffer)}.
     */
    @Test
    public void testH5Pget_fill_value() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        int typeId = DatatypeLib.getH5T_STD_I32BE();
        ByteBuffer value = ByteBuffer.allocateDirect(4).order(ByteOrder.BIG_ENDIAN);
        int status = PropertiesLib.H5Pget_fill_value(plistId, typeId, value);
        assertTrue(status >= 0);
        assertEquals(0, value.getInt(0));
        
        value.putInt(0, 8);
        status = PropertiesLib.H5Pset_fill_value(plistId, typeId, value);
        assertTrue(status >= 0);
        
        ByteBuffer xvalue = ByteBuffer.allocateDirect(4).order(ByteOrder.BIG_ENDIAN);
        status = PropertiesLib.H5Pget_fill_value(plistId, typeId, xvalue);
        assertTrue(status >= 0);
        assertEquals(8, xvalue.getInt(0));
        
        PropertiesLib.H5Pclose(plistId);              
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_filter1(int, long, int[], int[], long[], long, java.lang.String)}.
     */
    @Test
    @SuppressWarnings("deprecation")    
    public void testH5Pget_filter1() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_fletcher32(listId);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_deflate(listId, 9);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        int filterId = PropertiesLib.H5Pget_filter1(
                listId,
                1,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name
        );
        PropertiesLib.H5Pclose(listId);    
        
        assertEquals(FilterLib.H5Z_FILTER_DEFLATE, filterId);
        assertTrue((flags[0] & FilterLib.H5Z_FLAG_OPTIONAL) != 0);
        assertEquals("deflate", StringUtils.fromNullTerm(name));
        assertEquals(1, szcdata[0]);
        assertEquals(1, szcdata[0]);
        assertEquals(9, cdata[0]);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_filter2(int, long, int[], int[], long[], long, java.lang.String, int[])}.
     */
    @Test
    public void testH5Pget_filter2() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_fletcher32(listId);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_deflate(listId, 9);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                1,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        
        assertEquals(FilterLib.H5Z_FILTER_DEFLATE, filterId);
        assertTrue((flags[0] & FilterLib.H5Z_FLAG_OPTIONAL) != 0);
        assertEquals("deflate", StringUtils.fromNullTerm(name));
        assertEquals(1, szcdata[0]);
        assertEquals(1, szcdata[0]);
        assertEquals(9, cdata[0]);     
        assertTrue(
                (cfg[0] & FilterLib.H5Z_FILTER_CONFIG_DECODE_ENABLED) > 0 && 
                (cfg[0] & FilterLib.H5Z_FILTER_CONFIG_ENCODE_ENABLED) > 0
        );
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_filter_by_id1(int, int, int[], int[], long[], long, java.lang.String)}.
     */
    @Test
    @SuppressWarnings("deprecation")   
    public void testH5Pget_filter_by_id1() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_fletcher32(listId);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_deflate(listId, 9);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        status = PropertiesLib.H5Pget_filter_by_id1(
                listId,
                FilterLib.H5Z_FILTER_DEFLATE,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name
        );
        PropertiesLib.H5Pclose(listId);    
        
        assertTrue(status >= 0);
        assertTrue((flags[0] & FilterLib.H5Z_FLAG_OPTIONAL) != 0);
        assertEquals("deflate", StringUtils.fromNullTerm(name));
        assertEquals(1, szcdata[0]);
        assertEquals(1, szcdata[0]);
        assertEquals(9, cdata[0]);             
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_filter_by_id2(int, int, int[], int[], long[], long, java.lang.String, int[])}.
     */
    @Test
    public void testH5Pget_filter_by_id2() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_fletcher32(listId);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_deflate(listId, 9);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        status = PropertiesLib.H5Pget_filter_by_id2(
                listId,
                FilterLib.H5Z_FILTER_DEFLATE,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        
        assertTrue(status >= 0);
        assertTrue((flags[0] & FilterLib.H5Z_FLAG_OPTIONAL) != 0);
        assertEquals("deflate", StringUtils.fromNullTerm(name));
        assertEquals(1, szcdata[0]);
        assertEquals(1, szcdata[0]);
        assertEquals(9, cdata[0]);     
        assertTrue(
                (cfg[0] & FilterLib.H5Z_FILTER_CONFIG_DECODE_ENABLED) > 0 && 
                (cfg[0] & FilterLib.H5Z_FILTER_CONFIG_ENCODE_ENABLED) > 0
        );
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_gc_references(int, long[])}.
     */
    @Test
    public void testH5Pget_gc_references() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        int status = PropertiesLib.H5Pset_gc_references(listId, 1);
        assertTrue(status >= 0);
        
        long[] gc = new long[1];
        status = PropertiesLib.H5Pget_gc_references(listId, gc);
        PropertiesLib.H5Pclose(listId);            
        assertTrue(status >= 0);
        
        assertEquals(1, gc[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_hyper_vector_size(int, permafrost.hdf.libhdf.SWIGTYPE_p_size_t)}.
     */
    @Test
    public void testH5Pget_hyper_vector_size() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        
        long[] size = new long[1];
        int status = PropertiesLib.H5Pget_hyper_vector_size(listId, size);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(status >= 0);        
        assertTrue(size[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_istore_k(int, long[])}.
     */
    @Test
    public void testH5Pget_istore_k() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());
        
        long[] k = new long[1];
        int status = PropertiesLib.H5Pget_istore_k(listId, k);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(status >= 0);        
        assertTrue(k[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_layout(int)}.
     */
    @Test
    public void testH5Pget_layout() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        
        long[] chunk = new long[]{64, 64};
        PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        DatasetLayoutType type = PropertiesLib.H5Pget_layout(listId);
        PropertiesLib.H5Pclose(listId);
        
        assertEquals(DatasetLayoutType.H5D_CHUNKED, type);               
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_libver_bounds(int, permafrost.hdf.libhdf.FileLibVersionType[], permafrost.hdf.libhdf.FileLibVersionType[])}.
     */
    @Test
    public void testH5Pget_libver_bounds() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
                
        FileLibVersionType[] low = new FileLibVersionType[1];
        FileLibVersionType[] high = new FileLibVersionType[1];
        int status = PropertiesLib.H5Pget_libver_bounds(listId, low, high);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(status >= 0);
        assertEquals(FileLibVersionType.H5F_LIBVER_EARLIEST, low[0]);
        assertEquals(FileLibVersionType.H5F_LIBVER_LATEST, high[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_link_creation_order(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_link_creation_order() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        
        long flags = PropertiesLib.H5P_CRT_ORDER_INDEXED & PropertiesLib.H5P_CRT_ORDER_TRACKED;
        int status = PropertiesLib.H5Pset_link_creation_order(listId, flags);
        assertTrue(status >= 0);     
        
        long[] xflags = new long[1];
        status = PropertiesLib.H5Pget_link_creation_order(listId, xflags);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(flags, xflags[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_link_phase_change(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_link_phase_change() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
                
        long[] maxCompact = new long[1];
        long[] minDense = new long[1];
        int status = PropertiesLib.H5Pget_link_phase_change(listId, maxCompact, minDense);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertTrue(maxCompact[0] > 0);
        assertTrue(minDense[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_local_heap_size_hint(int, permafrost.hdf.libhdf.SWIGTYPE_p_size_t)}.
     */
    @Test
    public void testH5Pget_local_heap_size_hint() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        
        long hint = 128;
        int status = PropertiesLib.H5Pset_local_heap_size_hint(listId, hint);
        assertTrue(status >= 0);      
        
        long[] xhint = new long[1];
        status = PropertiesLib.H5Pget_local_heap_size_hint(listId, xhint);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(hint, xhint[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_mdc_config(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5AC_cache_config_t)}.
     */
    @Test
    public void testH5Pget_mdc_config() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_meta_block_size(int, long[])}.
     */
    @Test
    public void testH5Pget_meta_block_size() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        long[] size = new long[1];
        int status = PropertiesLib.H5Pget_meta_block_size(listId, size);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertTrue(size[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_multi_type(int, permafrost.hdf.libhdf.FileDriverAllocationType[])}.
     */
    @Test
    public void testH5Pget_multi_type() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        FileDriverAllocationType[] type = new FileDriverAllocationType[1];
        int status = PropertiesLib.H5Pget_multi_type(listId, type);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(FileDriverAllocationType.H5FD_MEM_DEFAULT, type[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_nfilters(int)}.
     */
    @Test
    public void testH5Pget_nfilters() {
        int listId = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_DATASET_CREATE_CLASS());
        
        int nfilters = PropertiesLib.H5Pget_nfilters(listId);
        assertEquals(0, nfilters);
        
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);        
        status = PropertiesLib.H5Pset_fletcher32(listId);
        assertTrue(status >= 0);
        status = PropertiesLib.H5Pset_shuffle(listId);                
       
        nfilters = PropertiesLib.H5Pget_nfilters(listId);
        assertEquals(2, nfilters);                 
        PropertiesLib.H5Pclose(listId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_nlinks(int, permafrost.hdf.libhdf.SWIGTYPE_p_size_t)}.
     */
    @Test
    public void testH5Pget_nlinks() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_ACCESS_LIST());
        
        long[] nlinks = new long[1];
        int status = PropertiesLib.H5Pget_nlinks(listId, nlinks);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertTrue(nlinks[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_nprops(int, long[])}.
     */
    @Test
    public void testH5Pget_nprops() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_ACCESS_LIST());
        
        long[] nprops = new long[1];
        int status = PropertiesLib.H5Pget_nprops(listId, nprops);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertTrue(nprops[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_preserve(int)}.
     */
    @Test
    public void testH5Pget_preserve() {        
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        
        int status = PropertiesLib.H5Pset_preserve(listId, 1);
        assertTrue(status >= 0);
        int xpreserve = PropertiesLib.H5Pget_preserve(listId);
        PropertiesLib.H5Pclose(listId);                

        assertTrue("testH5get_preserve will fail for HDF5 <= 1.8.3", xpreserve > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_obj_track_times(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_obj_track_times() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        
        long track = 0;
        int status = PropertiesLib.H5Pset_obj_track_times(listId, track);
        assertTrue(status >= 0);
        
        long[] xtrack = new long[1];
        status = PropertiesLib.H5Pget_obj_track_times(listId, xtrack);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(status >= 0);
        assertEquals(track, xtrack[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_shared_mesg_index(int, long, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_shared_mesg_index() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());
        
        int status = PropertiesLib.H5Pset_shared_mesg_nindexes(listId, 1);
        assertTrue(status >= 0);
        
        long flags = ObjectLib.H5O_SHMESG_ATTR_FLAG;
        long size = 8;
        status = PropertiesLib.H5Pset_shared_mesg_index(listId, 0, flags, size);
        assertTrue(status >= 0);
        
        long[] xflags = new long[1];
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_shared_mesg_index(listId, 0, xflags, xsize);
        assertTrue(status >= 0);
        
        PropertiesLib.H5Pclose(listId);      
        assertEquals(flags, xflags[0]);
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_shared_mesg_nindexes(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_shared_mesg_nindexes() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());
        
        long size = 2;
        int status = PropertiesLib.H5Pset_shared_mesg_nindexes(listId, size);
        assertTrue(status >= 0);
        
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_shared_mesg_nindexes(listId, xsize);
        PropertiesLib.H5Pclose(listId);      
        
        assertTrue(status >= 0);
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_shared_mesg_phase_change(int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int, permafrost.hdf.libhdf.SWIGTYPE_p_unsigned_int)}.
     */
    @Test
    public void testH5Pget_shared_mesg_phase_change() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());
        
        long[] maxList = new long[1];
        long[] minBTree = new long[1];
        int status = PropertiesLib.H5Pget_shared_mesg_phase_change(listId, maxList, minBTree);
        PropertiesLib.H5Pclose(listId);      
        
        assertTrue(status >= 0);
        assertTrue(maxList[0] > 0);
        assertTrue(minBTree[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_sieve_buf_size(int, long[])}.
     */
    @Test
    public void testH5Pget_sieve_buf_size() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        long[] size = new long[1];
        int status = PropertiesLib.H5Pget_sieve_buf_size(listId, size);
        PropertiesLib.H5Pclose(listId);      
        
        assertTrue(status >= 0);
        assertTrue(size[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_size(int, byte[], long[])}.
     */
    @Test
    public void testH5Pget_size() {
        int plistId = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_STRING_CREATE_CLASS());       
      
        final String propName = "character_encoding";        
        int exists = PropertiesLib.H5Pexist(plistId, propName);
        assertTrue(exists > 0);
        
        long[] size = new long[1];
        int status = PropertiesLib.H5Pget_size(plistId, propName, size);
        
        PropertiesLib.H5Pclose(plistId);
        assertTrue(status >= 0);
        assertTrue(status >= 0);        
        assertEquals(4, size[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_sizes(int, long[], long[])}.
     */
    @Test
    public void testH5Pget_sizes() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());    
        
        long[] addr = new long[1];
        long[] size = new long[1];
        int status = PropertiesLib.H5Pget_sizes(listId, addr, size);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertTrue(addr[0] >= 0);
        assertTrue(size[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_small_data_block_size(int, long[])}.
     */
    @Test
    public void testH5Pget_small_data_block_size() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());    
        
        long[] size = new long[1];
        int status = PropertiesLib.H5Pget_small_data_block_size(listId, size);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertTrue(size[0] > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_sym_k(int, long[], long[])}.
     */
    @Test
    public void testH5Pget_sym_k() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());    
        
        long[] ik = new long[1];
        long[] lk = new long[1];
        int status = PropertiesLib.H5Pget_sym_k(listId, ik, lk);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertTrue(ik[0] > 0);
        assertTrue(lk[0] > 0);
    }
   

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_userblock(int, long[])}.
     */
    @Test
    public void testH5Pget_userblock() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());    
        
        long size = 512;
        int status = PropertiesLib.H5Pset_userblock(listId, size);
        assertTrue(status >= 0);     
        
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_userblock(listId, xsize);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pget_version(int, long[], long[], long[], long[])}.
     */
    @Test
    public void testH5Pget_version() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());    
        
        long[] sblock = new long[1];
        long[] freelist = new long[1];
        long[] stab = new long[1];
        long[] shhdr = new long[1];
        
        int status = PropertiesLib.H5Pget_version(listId, sblock, freelist, stab, shhdr);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertTrue(sblock[0] >= 0);
        assertTrue(freelist[0] >= 0);
        assertTrue(stab[0] >= 0);
        assertTrue(shhdr[0] >= 0);
    }
    

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pisa_class(int, int)}.
     */
    @Test
    public void testH5Pisa_class() {
        int listId = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_FILE_CREATE_CLASS());        
        
        int status = PropertiesLib.H5Pisa_class(listId, PropertiesLib.getH5P_FILE_CREATE_CLASS());
        PropertiesLib.H5Pclose(listId);
        assertTrue(status > 0);
        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pmodify_filter(int, int, long, long, long[])}.
     */
    @Test
    public void testH5Pmodify_filter() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);
        
        long compress = 9;
        status = PropertiesLib.H5Pset_deflate(listId, compress);
        assertTrue(status >= 0);
        
        long xcompress = 5;
        long[] data = new long[]{xcompress}; 
        status = PropertiesLib.H5Pmodify_filter(listId, FilterLib.H5Z_FILTER_DEFLATE, 0, data.length, data);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                0,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);
        assertTrue(filterId >= 0);
        assertEquals(xcompress, cdata[0]);        
        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Premove(int, byte[])}.
     */
    @Test
    public void testH5Premove() {
        int plistId1 = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_STRING_CREATE_CLASS());
        int plistId2 = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_GROUP_CREATE_CLASS());
        
        final String propName = "character_encoding";        
        int exists = PropertiesLib.H5Pexist(plistId2, propName);
        assertFalse(exists > 0);
        
        int status = PropertiesLib.H5Pcopy_prop(plistId2, plistId1, propName);        
        exists = PropertiesLib.H5Pexist(plistId2, propName);
        assertTrue(status >= 0);
        assertTrue(exists > 0);
        
        status = PropertiesLib.H5Premove(plistId2, propName);
        exists = PropertiesLib.H5Pexist(plistId2, propName);        
        
        PropertiesLib.H5Pclose(plistId1);
        PropertiesLib.H5Pclose(plistId2);
        assertTrue(status >= 0);
        assertTrue(exists == 0);       
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Premove_filter(int, int)}.
     */
    @Test
    public void testH5Premove_filter() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());        
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);        
        
        int nfilters = PropertiesLib.H5Pget_nfilters(listId);
        assertEquals(0, nfilters);
        
        status = PropertiesLib.H5Pset_deflate(listId, 9);
        assertTrue(status >= 0);
        
        nfilters = PropertiesLib.H5Pget_nfilters(listId);
        assertEquals(1, nfilters);
        
        status = PropertiesLib.H5Premove_filter(listId, FilterLib.H5Z_FILTER_DEFLATE);
        assertTrue(status >= 0);
        
        nfilters = PropertiesLib.H5Pget_nfilters(listId);        
        PropertiesLib.H5Pclose(listId);
        assertEquals(0, nfilters);
        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset(int, byte[], java.nio.Buffer)}.
     */
    @Test
    public void testH5Pset() {
        int plistId = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_STRING_CREATE_CLASS());
        
        final String propName = "character_encoding";
        final DatatypeCharsetType type = DatatypeCharsetType.H5T_CSET_UTF8;
        ByteBuffer buffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
        buffer.putInt(type.swigValue());
        int status = PropertiesLib.H5Pset(plistId, propName, buffer);             
        assertTrue(status >= 0);        

        DatatypeCharsetType[] xtype = new DatatypeCharsetType[1];
        status = PropertiesLib.H5Pget_char_encoding(plistId, xtype);
        
        PropertiesLib.H5Pclose(plistId);
        assertTrue(status >= 0);     
        assertEquals(type, xtype[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_alignment(int, java.math.BigInteger, java.math.BigInteger)}.
     */
    @Test
    public void testH5Pset_alignment() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        long threshold = 2;
        long alignment = 2;
        int status = PropertiesLib.H5Pset_alignment(plistId, threshold, alignment);
        assertTrue(status >= 0);
        
        long[] xthreshold = new long[1];
        long[] xalignment = new long[1];
        status = PropertiesLib.H5Pget_alignment(plistId, xthreshold, xalignment);
        PropertiesLib.H5Pclose(plistId);
        
        assertTrue(status >= 0);        
        assertEquals(threshold, xthreshold[0]);
        assertEquals(alignment, xalignment[0]);
        
                
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_alloc_time(int, permafrost.hdf.libhdf.DatasetAllocationTimeType)}.
     */
    @Test
    public void testH5Pset_alloc_time() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        DatasetAllocationTimeType[] alloc = new DatasetAllocationTimeType[1]; 
        int status = PropertiesLib.H5Pget_alloc_time(plistId, alloc);
        assertTrue(status >= 0);
        assertEquals(DatasetAllocationTimeType.H5D_ALLOC_TIME_LATE, alloc[0]);
                
        status = PropertiesLib.H5Pset_alloc_time(plistId, DatasetAllocationTimeType.H5D_ALLOC_TIME_EARLY);
        assertTrue(status >= 0);
        
        status = PropertiesLib.H5Pget_alloc_time(plistId, alloc);
        PropertiesLib.H5Pclose(plistId);
        
        assertTrue(status >= 0);
        assertEquals(DatasetAllocationTimeType.H5D_ALLOC_TIME_EARLY, alloc[0]);        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_attr_creation_order(int, long)}.
     */
    @Test
    public void testH5Pset_attr_creation_order() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        
        long[] flags = new long[1];
        int status = PropertiesLib.H5Pget_attr_creation_order(plistId, flags);
        assertTrue(status >= 0);
        assertEquals(0, flags[0]);
        
        flags[0] = PropertiesLib.H5P_CRT_ORDER_INDEXED & PropertiesLib.H5P_CRT_ORDER_TRACKED;
        status = PropertiesLib.H5Pset_attr_creation_order(plistId, flags[0]);
        assertTrue(status >= 0);
        
        long[] xflags = new long[1];
        status = PropertiesLib.H5Pget_attr_creation_order(plistId, xflags);
        assertTrue(status >= 0);
                
        PropertiesLib.H5Pclose(plistId);
        assertEquals(flags[0], xflags[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_attr_phase_change(int, long, long)}.
     */
    @Test
    public void testH5Pset_attr_phase_change() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        
        long[] max_compact = new long[1];
        long[] min_dense = new long[1];
        int status = PropertiesLib.H5Pget_attr_phase_change(plistId, max_compact, min_dense);
        assertTrue(status >= 0);
        assertEquals(8, max_compact[0]);
        assertEquals(6, min_dense[0]);
        
        PropertiesLib.H5Pclose(plistId);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_btree_ratios(int, double, double, double)}.
     */
    @Test
    public void testH5Pset_btree_ratios() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        
        double left = 0.3;
        double middle = 0.4;
        double right = 0.8;
        int status = PropertiesLib.H5Pset_btree_ratios(plistId, left, middle, right);
        assertTrue(status >= 0);     
        
        double[] xleft = new double[1];
        double[] xmiddle = new double[1];
        double[] xright = new double[1];
        status = PropertiesLib.H5Pget_btree_ratios(plistId, xleft, xmiddle, xright);
                           
        PropertiesLib.H5Pclose(plistId);
        assertTrue(status >= 0);
        assertEquals(left, xleft[0], 1e-9);
        assertEquals(middle, xmiddle[0], 1e-9);
        assertEquals(right, xright[0], 1e-9);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_buffer(int, long, java.nio.Buffer, java.nio.Buffer)}.
     */
    @Test
    public void testH5Pset_buffer() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_cache(int, int, long, long, double)}.
     */
    @Test
    public void testH5Pset_cache() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        int mdc_nelmts = 1;
        long rdcc_nelmts = 512;
        long rdcc_nbytes = 512;
        double rdcc_w0 = 0.8;
        int status = PropertiesLib.H5Pset_cache(
                plistId, 
                mdc_nelmts, 
                rdcc_nelmts, 
                rdcc_nbytes, 
                rdcc_w0
        );
        assertTrue(status >= 0);     
        
        int[] xmdc_nelmts = new int[1]; // 0
        long[] xrdcc_nelmts = new long[1]; //521
        long[] xrdcc_nbytes = new long[1];  // 1048576
        double[] xrdcc_w0 = new double[1]; // 0.75        
        status = PropertiesLib.H5Pget_cache(
                plistId, 
                xmdc_nelmts, 
                xrdcc_nelmts, 
                xrdcc_nbytes, 
                xrdcc_w0
        );
        PropertiesLib.H5Pclose(plistId);
        
        assertTrue(status >= 0);               
//        assertEquals(mdc_nelmts, xmdc_nelmts[0]);
        assertEquals(rdcc_nelmts, xrdcc_nelmts[0]);
        assertEquals(rdcc_nbytes, xrdcc_nbytes[0]);
        assertEquals(rdcc_w0, xrdcc_w0[0], 1e-9);                
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_char_encoding(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5T_cset_t)}.
     */
    @Test
    public void testH5Pset_char_encoding() {
        int plistId = PropertiesLib.H5Pcreate(PropertiesLib.getH5P_STRING_CREATE_CLASS());
        DatatypeCharsetType[] type = new DatatypeCharsetType[1];
        int status = PropertiesLib.H5Pget_char_encoding(plistId, type);       
        assertTrue(status >= 0);        
        assertEquals(DatatypeCharsetType.H5T_CSET_ASCII, type[0]);
        
        type[0] = DatatypeCharsetType.H5T_CSET_UTF8;        
        status = PropertiesLib.H5Pset_char_encoding(plistId, type[0]);     
        assertTrue(status >= 0);        
      
        DatatypeCharsetType[] xtype = new DatatypeCharsetType[1];
        status = PropertiesLib.H5Pget_char_encoding(plistId, xtype);       
        assertTrue(status >= 0);        
        assertEquals(type[0], xtype[0]);
        
        PropertiesLib.H5Pclose(plistId);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_chunk(int, int, long[])}.
     */
    @Test
    public void testH5Pset_chunk() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunks = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(plistId, chunks.length, chunks);        
        assertTrue(status >= 0);
        
        long[] xchunks = new long[chunks.length];       
        PropertiesLib.H5Pget_chunk(plistId, xchunks.length, xchunks);
        
        PropertiesLib.H5Pclose(plistId);
        
        assertArrayEquals(chunks, xchunks);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_copy_object(int, long)}.
     */
    @Test
    public void testH5Pset_copy_object() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_OBJECT_COPY_LIST());
        long[] flags = new long[1];
        int status = PropertiesLib.H5Pget_copy_object(listId, flags);
        assertEquals(0, status);
        
        flags[0] = ObjectLib.H5O_COPY_SHALLOW_HIERARCHY_FLAG & ObjectLib.H5O_COPY_WITHOUT_ATTR_FLAG;
        status = PropertiesLib.H5Pset_copy_object(listId, flags[0]);
        
        long[] xflags = new long[1];
        status = PropertiesLib.H5Pget_copy_object(listId, xflags);
        PropertiesLib.H5Pclose(listId);
        assertEquals(0, status);
        assertEquals(flags[0], xflags[0]);   
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_create_intermediate_group(int, long)}.
     */
    @Test
    public void testH5Pset_create_intermediate_group() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_CREATE_LIST());
        long[] flags = new long[1];
        int status = PropertiesLib.H5Pget_create_intermediate_group(listId, flags);
        assertEquals(0, status);
        assertEquals(0, flags[0]);
        
        flags[0] = 1;
        status = PropertiesLib.H5Pset_create_intermediate_group(listId, flags[0]);
        
        long[] xflags = new long[1];
        status = PropertiesLib.H5Pget_create_intermediate_group(listId, xflags);
        PropertiesLib.H5Pclose(listId);
        assertEquals(0, status);
        assertEquals(flags[0], xflags[0]); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_data_transform(int, java.lang.String)}.
     */
    @Test
    public void testH5Pset_data_transform() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        String expression = "x+1";
        int status = PropertiesLib.H5Pset_data_transform(listId, expression);
        assertTrue(status >= 0);
        
        byte[] xexpression = new byte[expression.length()+1];
        int size = PropertiesLib.H5Pget_data_transform(listId, xexpression, xexpression.length);
        PropertiesLib.H5Pclose(listId);
        
        assertEquals(size+1, xexpression.length);
        assertEquals(expression, StringUtils.fromNullTerm(xexpression));
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_deflate(int, long)}.
     */
    @Test
    public void testH5Pset_deflate() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);        
        status = PropertiesLib.H5Pset_deflate(listId, 9);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                0,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        assertTrue(filterId >= 0);       
        assertEquals("deflate", StringUtils.fromNullTerm(name));        
    }
    

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_edc_check(int, permafrost.hdf.libhdf.FilterErrorDetectionType)}.
     */
    @Test
    public void testH5Pset_edc_check() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        FilterErrorDetectionType type = PropertiesLib.H5Pget_edc_check(listId);
        assertEquals(FilterErrorDetectionType.H5Z_ENABLE_EDC, type);
        int status = PropertiesLib.H5Pset_edc_check(
                listId, 
                FilterErrorDetectionType.H5Z_DISABLE_EDC
        );
        assertTrue(status >= 0);
        type = PropertiesLib.H5Pget_edc_check(listId);
        assertEquals(FilterErrorDetectionType.H5Z_DISABLE_EDC, type);
        PropertiesLib.H5Pclose(listId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_elink_prefix(int, java.lang.String)}.
     */
    @Test
    public void testH5Pset_elink_prefix() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_ACCESS_LIST());
        String prefix = "/external";
        int status = PropertiesLib.H5Pset_elink_prefix(listId, prefix);
        assertTrue(status >= 0);
        
        byte[] xprefix = new byte[prefix.length()+1];
        int size = PropertiesLib.H5Pget_elink_prefix(listId, xprefix, xprefix.length);
        
        PropertiesLib.H5Pclose(listId);        
        assertTrue(size > 0);
        assertEquals(prefix, StringUtils.fromNullTerm(xprefix));        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_est_link_info(int, long, long)}.
     */
    @Test
    public void testH5Pset_est_link_info() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        
        long nEntries = 16;
        long nameLen = 32;
        int status = PropertiesLib.H5Pset_est_link_info(listId, nEntries, nameLen);
        assertTrue(status >= 0);
        
        long[] xnEntries = new long[1];
        long[] xnameLen = new long[1];
        status = PropertiesLib.H5Pget_est_link_info(listId, xnEntries, xnameLen);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertEquals(nEntries, xnEntries[0]);
        assertEquals(nameLen, xnameLen[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_external(int, byte[], long, java.math.BigInteger)}.
     */
    @Test
    public void testH5Pset_external() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        String name = "external";
        long offset = 1;
        long size = 2048;
        int status = PropertiesLib.H5Pset_external(listId, name, offset, size);
        assertTrue(status >= 0);
        
        byte[] xname = new byte[name.length()+1];
        long[] xoffset = new long[1];
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_external(listId, 0, xname.length, xname, xoffset, xsize);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertEquals(name, StringUtils.fromNullTerm(xname));
        assertEquals(offset, xoffset[0]);
        assertEquals(size, xsize[0]);    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_family_offset(int, java.math.BigInteger)}.
     */
    @Test
    public void testH5Pset_family_offset() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int listId2 = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        long memb_size = 4096;
        
        int status = PropertiesLib.H5Pset_fapl_family(listId, memb_size, listId2);
        assertTrue(status >= 0);
        
        long offset = 16l;
        status = PropertiesLib.H5Pset_family_offset(listId, offset);
        
        long[] xoffset = new long[1];
        status = PropertiesLib.H5Pget_family_offset(listId, xoffset);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertEquals(offset, xoffset[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fapl_core(int, long, long)}.
     */
    @Test
    public void testH5Pset_fapl_core() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        long increment = 4096;
        long backing_store = 1;
        PropertiesLib.H5Pset_fapl_core(listId, increment, backing_store);
        
        long[] xincrement = new long[1];
        long[] xbacking_store = new long[1];
        int status = PropertiesLib.H5Pget_fapl_core(listId, xincrement, xbacking_store);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertEquals(increment, xincrement[0]);
        assertEquals(backing_store, xbacking_store[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fapl_family(int, java.math.BigInteger, int)}.
     */
    @Test
    public void testH5Pset_fapl_family() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int listId2 = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        long memb_size = 4096;
        
        int status = PropertiesLib.H5Pset_fapl_family(listId, memb_size, listId2);
        assertTrue(status >= 0);
        
        long[] xmemb_size = new long[1];
        int[] xListId = new int[1];
        status = PropertiesLib.H5Pget_fapl_family(listId, xmemb_size, xListId);
        int equals = PropertiesLib.H5Pequal(listId2, xListId[0]);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertEquals(memb_size, xmemb_size[0]);
        assertTrue(equals > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fapl_log(int, java.lang.String, long, long)}.
     */
    @Test
    public void testH5Pset_fapl_log() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int driverId = PropertiesLib.H5Pget_driver(listId);        
        int status = PropertiesLib.H5Pset_fapl_log(listId, "fapl.log", PropertiesLib.H5FD_LOG_ALL, 2048);
        assertTrue (status >= 0);
        int xdriverId = PropertiesLib.H5Pget_driver(listId);
        assertFalse(driverId == xdriverId);
        PropertiesLib.H5Pclose(listId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fapl_multi(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5FD_mem_t, int, permafrost.hdf.libhdf.SWIGTYPE_p_p_char, long, long)}.
     */
    @Test
    public void testH5Pset_fapl_multi() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int listId2 = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());        
        
        FileDriverAllocationType[] membMap = new FileDriverAllocationType[FileDriverAllocationType.H5FD_MEM_NTYPES.swigValue()];
        for (int n=0; n<membMap.length; n++) membMap[n] = FileDriverAllocationType.H5FD_MEM_DEFAULT;
        membMap[0] = FileDriverAllocationType.H5FD_MEM_SUPER;
        membMap[1] = FileDriverAllocationType.H5FD_MEM_BTREE;
        
        int[] membFAPL = new int[membMap.length];
        for (int n=0; n<membFAPL.length; n++) membFAPL[n] = listId2;
        
        String[] membName = new String[membMap.length];
        for (int n=0; n<membName.length; n++) membName[n] = "%s-X.h5";
        membName[0] = "%s-s.h5";
        membName[1] = "%s-b.h5";
        
        long[] membOff = new long[membMap.length];
        for (int n=0; n<membOff.length; n++) membOff[n] = -1l;
        
        long relax = 1;
        int status = PropertiesLib.H5Pset_fapl_multi(
                listId, 
                membMap,
                membFAPL, 
                membName,
                membOff,
                relax);
        assertTrue(status >= 0);
               
        FileDriverAllocationType[] xmembMap = new FileDriverAllocationType[membMap.length];
        int[] xmembFAPL = new int[membFAPL.length];
        byte[][] xmembName = new byte[membName.length][8];
        long[] xmembOff = new long[membOff.length];
        long[] xrelax = new long[1];
        status = PropertiesLib.H5Pget_fapl_multi(
                listId, 
                xmembMap, 
                xmembFAPL, 
                xmembName, 
                xmembOff, 
                xrelax
        );
        
        PropertiesLib.H5Pclose(listId);
        PropertiesLib.H5Pclose(listId2);
        assertTrue(status >= 0);
        assertArrayEquals(membMap, xmembMap);   
        for(int n=0; n<membName.length; n++) {
            assertEquals(membName[n], StringUtils.fromNullTerm(xmembName[n]));
        }
        assertEquals(relax, xrelax[0]);

    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fapl_sec2(int)}.
     */
    @Test
    public void testH5Pset_fapl_sec2() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int driverId = PropertiesLib.H5Pget_driver(listId);   
        int status = PropertiesLib.H5Pset_fapl_sec2(listId);        
        assertTrue (status >= 0);
        int xdriverId = PropertiesLib.H5Pget_driver(listId);
        assertFalse(driverId == xdriverId);
        PropertiesLib.H5Pclose(listId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fapl_split(int, java.lang.String, int, java.lang.String, int)}.
     */
    @Test
    public void testH5Pset_fapl_split() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int driverId = PropertiesLib.H5Pget_driver(listId);   
        int metaLId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int dataLId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        String meta = ".meta";
        String data = ".data";
        int status = PropertiesLib.H5Pset_fapl_split(listId, meta, metaLId, data, dataLId);
        assertTrue (status >= 0);
        int xdriverId = PropertiesLib.H5Pget_driver(listId);
        assertFalse(driverId == xdriverId);
        PropertiesLib.H5Pclose(listId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fapl_stdio(int)}.
     */
    @Test
    public void testH5Pset_fapl_stdio() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        int driverId = PropertiesLib.H5Pget_driver(listId);   
        int status = PropertiesLib.H5Pset_fapl_stdio(listId);
        assertTrue (status >= 0);
        int xdriverId = PropertiesLib.H5Pget_driver(listId);
        assertFalse(driverId == xdriverId);
        PropertiesLib.H5Pclose(listId);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fclose_degree(int, permafrost.hdf.libhdf.FileCloseBehaviorType)}.
     */
    @Test
    public void testH5Pset_fclose_degree() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        FileCloseBehaviorType[] degree = new FileCloseBehaviorType[1];
        int status = PropertiesLib.H5Pget_fclose_degree(plistId, degree);
        assertTrue(status >= 0);        
        assertEquals(FileCloseBehaviorType.H5F_CLOSE_DEFAULT, degree[0]);
        
        
        status = PropertiesLib.H5Pset_fclose_degree(plistId, FileCloseBehaviorType.H5F_CLOSE_STRONG);
        assertTrue(status >= 0);       
        
        FileCloseBehaviorType[] xdegree = new FileCloseBehaviorType[1];
        status = PropertiesLib.H5Pget_fclose_degree(plistId, xdegree);
        assertTrue(status >= 0);        
        assertEquals(FileCloseBehaviorType.H5F_CLOSE_STRONG, xdegree[0]);
        
        PropertiesLib.H5Pclose(plistId);   
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fill_time(int, permafrost.hdf.libhdf.DatasetFillTimeType)}.
     */
    @Test
    public void testH5Pset_fill_time() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        DatasetFillTimeType[] alloc = new DatasetFillTimeType[1]; 
        int status = PropertiesLib.H5Pget_fill_time(plistId, alloc);
        assertTrue(status >= 0);
        assertEquals(DatasetFillTimeType.H5D_FILL_TIME_IFSET, alloc[0]);
                
        status = PropertiesLib.H5Pset_fill_time(plistId, DatasetFillTimeType.H5D_FILL_TIME_ALLOC);
        assertTrue(status >= 0);
        
        status = PropertiesLib.H5Pget_fill_time(plistId, alloc);
        PropertiesLib.H5Pclose(plistId);
        
        assertTrue(status >= 0);
        assertEquals(DatasetFillTimeType.H5D_FILL_TIME_ALLOC, alloc[0]); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fill_value(int, int, java.nio.Buffer)}.
     */
    @Test
    public void testH5Pset_fill_value() {
        int plistId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        int typeId = DatatypeLib.getH5T_STD_I32BE();
        ByteBuffer value = ByteBuffer.allocateDirect(4).order(ByteOrder.BIG_ENDIAN);
        int status = PropertiesLib.H5Pget_fill_value(plistId, typeId, value);
        assertTrue(status >= 0);
        assertEquals(0, value.getInt(0));
        
        value.putInt(0, 8);
        status = PropertiesLib.H5Pset_fill_value(plistId, typeId, value);
        assertTrue(status >= 0);
        
        ByteBuffer xvalue = ByteBuffer.allocateDirect(4).order(ByteOrder.BIG_ENDIAN);
        status = PropertiesLib.H5Pget_fill_value(plistId, typeId, xvalue);
        assertTrue(status >= 0);
        assertEquals(8, xvalue.getInt(0));
        
        PropertiesLib.H5Pclose(plistId);  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_filter(int, int, long, long, long[])}.
     */
    @Test
    public void testH5Pset_filter() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);
        long[] level = new long[]{9};
        status = PropertiesLib.H5Pset_filter(
                listId, 
                FilterLib.H5Z_FILTER_DEFLATE, 
                FilterLib.H5Z_FLAG_MANDATORY, 
                1, 
                level);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                0,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        assertTrue(filterId >= 0);       
        assertEquals("deflate", StringUtils.fromNullTerm(name));
        assertEquals(level[0], cdata[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_fletcher32(int)}.
     */
    @Test
    public void testH5Pset_fletcher32() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);        
        status = PropertiesLib.H5Pset_fletcher32(listId);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                0,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        assertTrue(filterId >= 0);       
        assertEquals("fletcher32", StringUtils.fromNullTerm(name));    
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_gc_references(int, long)}.
     */
    @Test
    public void testH5Pset_gc_references() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        int status = PropertiesLib.H5Pset_gc_references(listId, 1);
        assertTrue(status >= 0);
        
        long[] gc = new long[1];
        status = PropertiesLib.H5Pget_gc_references(listId, gc);
        PropertiesLib.H5Pclose(listId);            
        assertTrue(status >= 0);
        
        assertEquals(1, gc[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_hyper_vector_size(int, long)}.
     */
    @Test
    public void testH5Pset_hyper_vector_size() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        
        long size = 2048;
        PropertiesLib.H5Pset_hyper_vector_size(listId, size);
        
        long[] xsize = new long[1];
        int status = PropertiesLib.H5Pget_hyper_vector_size(listId, xsize);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(status >= 0);        
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_istore_k(int, long)}.
     */
    @Test
    public void testH5Pset_istore_k() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());
        
        long k = 64;
        PropertiesLib.H5Pset_istore_k(listId, k);
        
        long[] xk = new long[1];
        int status = PropertiesLib.H5Pget_istore_k(listId, xk);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(status >= 0);        
        assertEquals(k, xk[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_layout(int, permafrost.hdf.libhdf.DatasetLayoutType)}.
     */
    @Test
    public void testH5Pset_layout() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        
        PropertiesLib.H5Pset_layout(listId, DatasetLayoutType.H5D_CHUNKED);
        DatasetLayoutType type = PropertiesLib.H5Pget_layout(listId);
        PropertiesLib.H5Pclose(listId);
        
        assertEquals(DatasetLayoutType.H5D_CHUNKED, type); 
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_libver_bounds(int, permafrost.hdf.libhdf.FileLibVersionType, permafrost.hdf.libhdf.FileLibVersionType)}.
     */
    @Test
    public void testH5Pset_libver_bounds() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        int status = PropertiesLib.H5Pset_libver_bounds(
                listId, 
                FileLibVersionType.H5F_LIBVER_LATEST, 
                FileLibVersionType.H5F_LIBVER_LATEST
        );
        assertTrue(status >= 0);
        
        FileLibVersionType[] low = new FileLibVersionType[1];
        FileLibVersionType[] high = new FileLibVersionType[1];
        status = PropertiesLib.H5Pget_libver_bounds(listId, low, high);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(status >= 0);
        assertEquals(FileLibVersionType.H5F_LIBVER_LATEST, low[0]);
        assertEquals(FileLibVersionType.H5F_LIBVER_LATEST, high[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_link_creation_order(int, long)}.
     */
    @Test
    public void testH5Pset_link_creation_order() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        
        long flags = PropertiesLib.H5P_CRT_ORDER_INDEXED & PropertiesLib.H5P_CRT_ORDER_TRACKED;
        int status = PropertiesLib.H5Pset_link_creation_order(listId, flags);
        assertTrue(status >= 0);     
        
        long[] xflags = new long[1];
        status = PropertiesLib.H5Pget_link_creation_order(listId, xflags);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(flags, xflags[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_link_phase_change(int, long, long)}.
     */
    @Test
    public void testH5Pset_link_phase_change() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        
        long maxCompact = 10;
        long minDense = 8;
        int status = PropertiesLib.H5Pset_link_phase_change(listId, maxCompact, minDense);
        assertTrue(status >= 0);       
        
        long[] xmaxCompact = new long[1];
        long[] xminDense = new long[1];
        status = PropertiesLib.H5Pget_link_phase_change(listId, xmaxCompact, xminDense);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(maxCompact, xmaxCompact[0]);
        assertEquals(minDense, xminDense[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_local_heap_size_hint(int, long)}.
     */
    @Test
    public void testH5Pset_local_heap_size_hint() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        
        long hint = 128;
        int status = PropertiesLib.H5Pset_local_heap_size_hint(listId, hint);
        assertTrue(status >= 0);      
        
        long[] xhint = new long[1];
        status = PropertiesLib.H5Pget_local_heap_size_hint(listId, xhint);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(hint, xhint[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_mdc_config(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5AC_cache_config_t)}.
     */
    @Test
    public void testH5Pset_mdc_config() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_meta_block_size(int, java.math.BigInteger)}.
     */
    @Test
    public void testH5Pset_meta_block_size() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        long size = 4096;
        int status = PropertiesLib.H5Pset_meta_block_size(listId, size);
        
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_meta_block_size(listId, xsize);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_multi_type(int, java.lang.Enum)}.
     */
    @Test
    public void testH5Pset_multi_type() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        FileDriverAllocationType type = FileDriverAllocationType.H5FD_MEM_BTREE;
        int status = PropertiesLib.H5Pset_multi_type(listId, type);
        assertTrue(status >= 0);     
        
        FileDriverAllocationType[] xtype = new FileDriverAllocationType[1];
        status = PropertiesLib.H5Pget_multi_type(listId, xtype);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(type, xtype[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_nbit(int)}.
     */
    @Test
    public void testH5Pset_nbit() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);        
        status = PropertiesLib.H5Pset_nbit(listId);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                0,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        assertTrue(filterId >= 0);       
        assertEquals("nbit", StringUtils.fromNullTerm(name));     
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_nlinks(int, long)}.
     */
    @Test
    public void testH5Pset_nlinks() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_LINK_ACCESS_LIST());
        
        long nlinks = 64;
        int status = PropertiesLib.H5Pset_nlinks(listId, nlinks);
        assertTrue(status >= 0);
        
        long[] xnlinks = new long[1];
        status = PropertiesLib.H5Pget_nlinks(listId, xnlinks);
        PropertiesLib.H5Pclose(listId);        
        
        assertTrue(status >= 0);       
        assertEquals(nlinks, xnlinks[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_preserve(int, long)}.
     */
    @Test
    public void testH5Pset_preserve() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_XFER_LIST());
        
        int status = PropertiesLib.H5Pset_preserve(listId, 1);
        assertTrue(status >= 0);
        int xpreserve = PropertiesLib.H5Pget_preserve(listId);
        PropertiesLib.H5Pclose(listId);
          
        assertTrue("testH5set_preserve will fail for HDF5 <= 1.8.3", xpreserve > 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_obj_track_times(int, long)}.
     */
    @Test
    public void testH5Pset_obj_track_times() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_GROUP_CREATE_LIST());
        
        long track = 0;
        int status = PropertiesLib.H5Pset_obj_track_times(listId, track);
        assertTrue(status >= 0);
        
        long[] xtrack = new long[1];
        status = PropertiesLib.H5Pget_obj_track_times(listId, xtrack);
        PropertiesLib.H5Pclose(listId);
        
        assertTrue(status >= 0);
        assertEquals(track, xtrack[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_scaleoffset(int, permafrost.hdf.libhdf.SWIGTYPE_p_H5Z_SO_scale_type_t, int)}.
     */
    @Test
    public void testH5Pset_scaleoffset() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);        
        status = PropertiesLib.H5Pset_scaleoffset(listId, FilterScaleOffsetType.H5Z_SO_FLOAT_ESCALE, 1);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                0,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        assertTrue(filterId >= 0);       
        assertEquals("scaleoffset", StringUtils.fromNullTerm(name));        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_shared_mesg_index(int, long, long, long)}.
     */
    @Test
    public void testH5Pset_shared_mesg_index() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());
        
        int status = PropertiesLib.H5Pset_shared_mesg_nindexes(listId, 1);
        assertTrue(status >= 0);
        
        long flags = ObjectLib.H5O_SHMESG_ATTR_FLAG;
        long size = 8;
        status = PropertiesLib.H5Pset_shared_mesg_index(listId, 0, flags, size);
        assertTrue(status >= 0);
        
        long[] xflags = new long[1];
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_shared_mesg_index(listId, 0, xflags, xsize);
        assertTrue(status >= 0);
        
        PropertiesLib.H5Pclose(listId);      
        assertEquals(flags, xflags[0]);
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_shared_mesg_nindexes(int, long)}.
     */
    @Test
    public void testH5Pset_shared_mesg_nindexes() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());
        
        long size = 2;
        int status = PropertiesLib.H5Pset_shared_mesg_nindexes(listId, size);
        assertTrue(status >= 0);
        
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_shared_mesg_nindexes(listId, xsize);
        PropertiesLib.H5Pclose(listId);      
        
        assertTrue(status >= 0);
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_shared_mesg_phase_change(int, long, long)}.
     */
    @Test
    public void testH5Pset_shared_mesg_phase_change() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());
        
        long maxList = 60;
        long minBTree = 50;
        int status = PropertiesLib.H5Pset_shared_mesg_phase_change(listId, maxList, minBTree);
        assertTrue(status >= 0);

        long[] xmaxList = new long[1];
        long[] xminBTree = new long[1];
        status = PropertiesLib.H5Pget_shared_mesg_phase_change(listId, xmaxList, xminBTree);
        PropertiesLib.H5Pclose(listId);      
        
        assertTrue(status >= 0);               
        assertEquals(maxList, xmaxList[0]);
        assertEquals(minBTree, xminBTree[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_shuffle(int)}.
     */
    @Test
    public void testH5Pset_shuffle() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);        
        status = PropertiesLib.H5Pset_shuffle(listId);
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                0,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        assertTrue(filterId >= 0);       
        assertEquals("shuffle", StringUtils.fromNullTerm(name));  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_sieve_buf_size(int, long)}.
     */
    @Test
    public void testH5Pset_sieve_buf_size() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());
        
        long size = 2048;
        int status = PropertiesLib.H5Pset_sieve_buf_size(listId, size);
        assertTrue(status >= 0);
        
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_sieve_buf_size(listId, xsize);
        PropertiesLib.H5Pclose(listId);      
        
        assertTrue(status >= 0);
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_sizes(int, long, long)}.
     */
    @Test
    public void testH5Pset_sizes() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());    
        
        long addr = 16;
        long size = 16;
        int status = PropertiesLib.H5Pset_sizes(listId, addr, size);
        assertTrue(status >= 0);
        
        long[] xaddr = new long[1];
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_sizes(listId, xaddr, xsize);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);
        assertEquals(addr, xaddr[0]);
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_small_data_block_size(int, java.math.BigInteger)}.
     */
    @Test
    public void testH5Pset_small_data_block_size() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_ACCESS_LIST());    
        
        long size = 4096;
        int status = PropertiesLib.H5Pset_small_data_block_size(listId, size);
        assertTrue(status >= 0);
        
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_small_data_block_size(listId, xsize);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertEquals(size, xsize[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_sym_k(int, long, long)}.
     */
    @Test
    public void testH5Pset_sym_k() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());    
        
        long ik = 32;
        long lk = 8;
        int status = PropertiesLib.H5Pset_sym_k(listId, ik, lk);
        assertTrue(status >= 0);
        
        long[] xik = new long[1];
        long[] xlk = new long[1];
        status = PropertiesLib.H5Pget_sym_k(listId, xik, xlk);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertEquals(ik, xik[0]);
        assertEquals(lk, xlk[0]);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_szip(int, long, long)}.
     */
    @Test
    public void testH5Pset_szip() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_DATASET_CREATE_LIST());
        long[] chunk = new long[]{64, 64};
        int status = PropertiesLib.H5Pset_chunk(listId, chunk.length, chunk);
        assertTrue(status >= 0);        
        status = PropertiesLib.H5Pset_szip(listId, FilterLib.H5_SZIP_NN_OPTION_MASK, 32);
        if (status < 0) {
            PropertiesLib.H5Pclose(listId); 
            fail("Is szip encoder enabled?");  
        }
        
        assertTrue(status >= 0);
        
        long[] flags = new long[1];        
        long[] cdata = new long[64];
        int[] szcdata = new int[]{cdata.length};
        byte[] name = new byte[24];
        long[] cfg = new long[1];
        int filterId = PropertiesLib.H5Pget_filter2(
                listId,
                0,
                flags, 
                szcdata, 
                cdata, 
                name.length, 
                name,
                cfg
        );
        PropertiesLib.H5Pclose(listId);    
        assertTrue(filterId >= 0);       
        assertEquals("szip", StringUtils.fromNullTerm(name));  
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PropertiesLib#H5Pset_userblock(int, java.math.BigInteger)}.
     */
    @Test
    public void testH5Pset_userblock() {
        int listId = PropertiesLib.H5Pcopy(PropertiesLib.getH5P_FILE_CREATE_LIST());    
        
        long size = 512;
        int status = PropertiesLib.H5Pset_userblock(listId, size);
        assertTrue(status >= 0);     
        
        long[] xsize = new long[1];
        status = PropertiesLib.H5Pget_userblock(listId, xsize);
        
        PropertiesLib.H5Pclose(listId);
        assertTrue(status >= 0);        
        assertEquals(size, xsize[0]);
    }
       

    
}
