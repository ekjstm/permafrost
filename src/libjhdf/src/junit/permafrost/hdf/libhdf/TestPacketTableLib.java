/**
 *
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;

import javolution.io.Struct;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TODO add type documentation
 *
 */
public class TestPacketTableLib {

    /** The message logger. */
    private static final Logger logger = Logger.getLogger(TestPacketTableLib.class);

    private int fid;
    private int pktId;
    
    private class PacketStruct extends Struct {
        public Signed32 int32 = new Signed32();
        public Signed8 int8 = new Signed8();
        public Float64 float64 = new Float64();

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof PacketStruct)) return false;
            if (this == obj) return true;
            
            PacketStruct other = (PacketStruct) obj;
            return (
                    (this.int32.get() == other.int32.get()) &&
                    (this.int8.get() == other.int8.get()) &&
                    (this.float64.get() - other.float64.get() < 1e-10)
            );
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
        assertTrue("Invalid file access property list.", plFAccess >= 0);
        PropertiesLib.H5Pset_fapl_core(plFAccess, 4096, 0);
        this.fid = FileLib.H5Fcreate("test.h5", 
                (FileLibConstants.H5F_ACC_TRUNC | FileLibConstants.H5F_ACC_DEBUG),  
                PropertiesLibConstants.H5P_DEFAULT, 
                PropertiesLibConstants.H5P_DEFAULT
        );
        assertTrue("Invalid file identifier.", this.fid >= 0);
                
        this.pktId = this.createPacketType();
        assertTrue("Invalid packet type identifier.", this.pktId >= 0);
    }
    
    @After
    public void tearDown() throws Exception {   
        try {
            if (this.pktId > 0) {
                DatatypeLib.H5Tclose(this.pktId);
                this.pktId = 0;
            }
        } catch (Exception ex) {
            logger.error("Error closing packet type.", ex);
        }
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
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTcreate_fl(int, java.lang.String, int, java.math.BigInteger, int)}.
     */
    @Test
    public void testH5PTcreate_fl() {      
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTopen(int, java.lang.String)}.
     */
    @Test
    public void testH5PTopen() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
        
        
        tbId = PacketTableLib.H5PTopen(this.fid, dsetName);
        assertTrue(tbId > 0);
        
        closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTclose(int)}.
     */
    @Test
    public void testH5PTclose() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTappend(int, long, java.nio.Buffer)}.
     */
    @Test
    public void testH5PTappend() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        PacketStruct pkt1 = new PacketStruct();
        PacketStruct pkt2 = new PacketStruct();
        PacketStruct pkt3 = new PacketStruct();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        pkt1.setByteBuffer(buffer, 0);
        pkt1.int32.set(1); 
        pkt1.int8.set((byte) 2);
        pkt1.float64.set(3.0);
        pkt2.setByteBuffer(buffer, pkt1.size());
        pkt2.int32.set(4); 
        pkt2.int8.set((byte) 5);
        pkt2.float64.set(6.0);
        pkt3.setByteBuffer(buffer, pkt1.size());
        pkt3.int32.set(7); 
        pkt3.int8.set((byte) 8);
        pkt3.float64.set(9.0);
        
        int writeStatus = PacketTableLib.H5PTappend(tbId, 3, buffer);
        assertTrue(writeStatus >= 0);
               
        long[] npackets = new long[1];
        int getStatus = PacketTableLib.H5PTget_num_packets(tbId, npackets);
        assertTrue(getStatus >= 0);
        assertEquals(3, npackets[0]);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
        
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTget_next(int, long, java.nio.Buffer)}.
     */
    @Test
    public void testH5PTget_next() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        PacketStruct pkt1 = new PacketStruct();
        PacketStruct pkt2 = new PacketStruct();
        PacketStruct pkt3 = new PacketStruct();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        pkt1.setByteBuffer(buffer, 0);
        pkt1.int32.set(1); 
        pkt1.int8.set((byte) 2);
        pkt1.float64.set(3.0);
        pkt2.setByteBuffer(buffer, pkt1.size());
        pkt2.int32.set(4); 
        pkt2.int8.set((byte) 5);
        pkt2.float64.set(6.0);
        pkt3.setByteBuffer(buffer, pkt2.size()+pkt2.getByteBufferPosition());
        pkt3.int32.set(7); 
        pkt3.int8.set((byte) 8);
        pkt3.float64.set(9.0);
        
        int writeStatus = PacketTableLib.H5PTappend(tbId, 3, buffer);
        assertTrue(writeStatus >= 0);
        
        int idxStatus = PacketTableLib.H5PTcreate_index(tbId);
        assertTrue(idxStatus >= 0);
        
        PacketStruct xpkt1 = new PacketStruct();
        PacketStruct xpkt2 = new PacketStruct();
        PacketStruct xpkt3 = new PacketStruct();
        ByteBuffer xbuffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        xpkt1.setByteBuffer(xbuffer, 0);       
        xpkt2.setByteBuffer(xbuffer, pkt1.size());       
        xpkt3.setByteBuffer(xbuffer, pkt2.size()+pkt2.getByteBufferPosition());

        int getStatus = PacketTableLib.H5PTget_next(tbId, 3, xbuffer);        
        assertTrue(getStatus >= 0);
        
        assertEquals(pkt1, xpkt1);
        assertEquals(pkt2, xpkt2);
        assertEquals(pkt3, xpkt3);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTread_packets(int, java.math.BigInteger, long, java.nio.Buffer)}.
     */
    @Test
    public void testH5PTread_packets() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        PacketStruct pkt1 = new PacketStruct();
        PacketStruct pkt2 = new PacketStruct();
        PacketStruct pkt3 = new PacketStruct();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        pkt1.setByteBuffer(buffer, 0);
        pkt1.int32.set(1); 
        pkt1.int8.set((byte) 2);
        pkt1.float64.set(3.0);
        pkt2.setByteBuffer(buffer, pkt1.size());
        pkt2.int32.set(4); 
        pkt2.int8.set((byte) 5);
        pkt2.float64.set(6.0);
        pkt3.setByteBuffer(buffer, pkt2.size()+pkt2.getByteBufferPosition());
        pkt3.int32.set(7); 
        pkt3.int8.set((byte) 8);
        pkt3.float64.set(9.0);
        
        int writeStatus = PacketTableLib.H5PTappend(tbId, 3, buffer);
        assertTrue(writeStatus >= 0);
        
        int idxStatus = PacketTableLib.H5PTcreate_index(tbId);
        assertTrue(idxStatus >= 0);
        
        PacketStruct xpkt2 = new PacketStruct();
        PacketStruct xpkt3 = new PacketStruct();
        ByteBuffer xbuffer = ByteBuffer.allocateDirect(pkt1.size()*2);
        xpkt2.setByteBuffer(xbuffer, 0);       
        xpkt3.setByteBuffer(xbuffer, pkt1.size());       
        
        int getStatus = PacketTableLib.H5PTread_packets(tbId, 1, 2, xbuffer); 
        assertTrue(getStatus >= 0);
        
        assertEquals(pkt2, xpkt2);
        assertEquals(pkt3, xpkt3);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTget_num_packets(int, long[])}.
     */
    @Test
    public void testH5PTget_num_packets() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        PacketStruct pkt1 = new PacketStruct();
        PacketStruct pkt2 = new PacketStruct();
        PacketStruct pkt3 = new PacketStruct();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        pkt1.setByteBuffer(buffer, 0);
        pkt1.int32.set(1); 
        pkt1.int8.set((byte) 2);
        pkt1.float64.set(3.0);
        pkt2.setByteBuffer(buffer, pkt1.size());
        pkt2.int32.set(4); 
        pkt2.int8.set((byte) 5);
        pkt2.float64.set(6.0);
        pkt3.setByteBuffer(buffer, pkt1.size());
        pkt3.int32.set(7); 
        pkt3.int8.set((byte) 8);
        pkt3.float64.set(9.0);
        
        int writeStatus = PacketTableLib.H5PTappend(tbId, 3, buffer);
        assertTrue(writeStatus >= 0);
               
        long[] npackets = new long[1];
        int getStatus = PacketTableLib.H5PTget_num_packets(tbId, npackets);
        assertTrue(getStatus >= 0);
        assertEquals(3, npackets[0]);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTis_valid(int)}.
     */
    @Test
    public void testH5PTis_valid() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        int valid = PacketTableLib.H5PTis_valid(tbId);
        assertTrue(valid >= 0);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
        
        valid = PacketTableLib.H5PTis_valid(tbId);
        assertFalse(valid >= 0);
       
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTcreate_index(int)}.
     */
    @Test
    public void testH5PTcreate_index() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        PacketStruct pkt1 = new PacketStruct();
        PacketStruct pkt2 = new PacketStruct();
        PacketStruct pkt3 = new PacketStruct();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        pkt1.setByteBuffer(buffer, 0);
        pkt1.int32.set(1); 
        pkt1.int8.set((byte) 2);
        pkt1.float64.set(3.0);
        pkt2.setByteBuffer(buffer, pkt1.size());
        pkt2.int32.set(4); 
        pkt2.int8.set((byte) 5);
        pkt2.float64.set(6.0);
        pkt3.setByteBuffer(buffer, pkt2.size()+pkt2.getByteBufferPosition());
        pkt3.int32.set(7); 
        pkt3.int8.set((byte) 8);
        pkt3.float64.set(9.0);
        
        int writeStatus = PacketTableLib.H5PTappend(tbId, 3, buffer);
        assertTrue(writeStatus >= 0);
        
        int idxStatus = PacketTableLib.H5PTcreate_index(tbId);
        assertTrue(idxStatus >= 0);
        
        PacketStruct xpkt1 = new PacketStruct();
        PacketStruct xpkt2 = new PacketStruct();
        PacketStruct xpkt3 = new PacketStruct();
        ByteBuffer xbuffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        xpkt1.setByteBuffer(xbuffer, 0);       
        xpkt2.setByteBuffer(xbuffer, pkt1.size());       
        xpkt3.setByteBuffer(xbuffer, pkt2.size()+pkt2.getByteBufferPosition());

        int getStatus = PacketTableLib.H5PTget_next(tbId, 3, xbuffer);        
        assertTrue(getStatus >= 0);
        
        assertEquals(pkt1, xpkt1);
        assertEquals(pkt2, xpkt2);
        assertEquals(pkt3, xpkt3);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTset_index(int, java.math.BigInteger)}.
     */
    @Test
    public void testH5PTset_index() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        PacketStruct pkt1 = new PacketStruct();
        PacketStruct pkt2 = new PacketStruct();
        PacketStruct pkt3 = new PacketStruct();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        pkt1.setByteBuffer(buffer, 0);
        pkt1.int32.set(1); 
        pkt1.int8.set((byte) 2);
        pkt1.float64.set(3.0);
        pkt2.setByteBuffer(buffer, pkt1.size());
        pkt2.int32.set(4); 
        pkt2.int8.set((byte) 5);
        pkt2.float64.set(6.0);
        pkt3.setByteBuffer(buffer, pkt2.size()+pkt2.getByteBufferPosition());
        pkt3.int32.set(7); 
        pkt3.int8.set((byte) 8);
        pkt3.float64.set(9.0);
        
        int writeStatus = PacketTableLib.H5PTappend(tbId, 3, buffer);
        assertTrue(writeStatus >= 0);
        
        int idxStatus = PacketTableLib.H5PTcreate_index(tbId);
        assertTrue(idxStatus >= 0);
        int setStatus = PacketTableLib.H5PTset_index(tbId, 1);
        assertTrue(setStatus >= 0);
        
        PacketStruct xpkt2 = new PacketStruct();
        PacketStruct xpkt3 = new PacketStruct();
        ByteBuffer xbuffer = ByteBuffer.allocateDirect(pkt1.size()*2);
        xpkt2.setByteBuffer(xbuffer, 0);       
        xpkt3.setByteBuffer(xbuffer, pkt1.size());       
        
        int getStatus = PacketTableLib.H5PTget_next(tbId, 2, xbuffer); 
        assertTrue(getStatus >= 0);
        
        assertEquals(pkt2, xpkt2);
        assertEquals(pkt3, xpkt3);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.PacketTableLib#H5PTget_index(int, long[])}.
     */
    @Test
    public void testH5PTget_index() {
        String dsetName = "pktTable";
        final int chunkSize = 64;                
        
        int tbId = PacketTableLib.H5PTcreate_fl(
                this.fid, 
                dsetName, 
                this.pktId, 
                chunkSize, 
                0
        );
        assertTrue(tbId > 0);
        
        PacketStruct pkt1 = new PacketStruct();
        PacketStruct pkt2 = new PacketStruct();
        PacketStruct pkt3 = new PacketStruct();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pkt1.size()*3);
        pkt1.setByteBuffer(buffer, 0);
        pkt1.int32.set(1); 
        pkt1.int8.set((byte) 2);
        pkt1.float64.set(3.0);
        pkt2.setByteBuffer(buffer, pkt1.size());
        pkt2.int32.set(4); 
        pkt2.int8.set((byte) 5);
        pkt2.float64.set(6.0);
        pkt3.setByteBuffer(buffer, pkt2.size()+pkt2.getByteBufferPosition());
        pkt3.int32.set(7); 
        pkt3.int8.set((byte) 8);
        pkt3.float64.set(9.0);
        
        int writeStatus = PacketTableLib.H5PTappend(tbId, 3, buffer);
        assertTrue(writeStatus >= 0);
        
        int idxStatus = PacketTableLib.H5PTcreate_index(tbId);
        assertTrue(idxStatus >= 0);
        
        final long newIdx = 1;
        int setStatus = PacketTableLib.H5PTset_index(tbId, newIdx);
        assertTrue(setStatus >= 0);
        
        long[] idx = new long[1];
        int getStatus = PacketTableLib.H5PTget_index(tbId, idx);
        assertTrue(getStatus >= 0);
        
        assertEquals(newIdx, idx[0]);
        
        int closeStatus = PacketTableLib.H5PTclose(tbId);
        assertTrue(closeStatus >= 0);
    }
    
    private int createPacketType() {
        PacketStruct struct = new PacketStruct();      
        struct.setByteBuffer(ByteBuffer.allocateDirect(struct.size()), 0);      
        
        int idStruct = DatatypeLib.H5Tcreate(DatatypeClassType.H5T_COMPOUND, struct.size());
        assertTrue(idStruct >= 0);
        final String intName = "int32";
        int insertStatus = DatatypeLib.H5Tinsert(
                idStruct, 
                intName, 
                struct.int32.offset(), 
                DatatypeLib.getH5T_STD_I32BE()
        );
        assertTrue(insertStatus >= 0);
        final String charName = "int8";
        insertStatus = DatatypeLib.H5Tinsert(
                idStruct, 
                charName, 
                struct.int8.offset(), 
                DatatypeLib.getH5T_STD_I8BE()
        );
        assertTrue(insertStatus >= 0);
        final String floatName = "float64";
        insertStatus = DatatypeLib.H5Tinsert(
                idStruct, 
                floatName, 
                struct.float64.offset(), 
                DatatypeLib.getH5T_IEEE_F64BE()
        );
        assertTrue(insertStatus >= 0);
        
        return (idStruct);
    }

}
