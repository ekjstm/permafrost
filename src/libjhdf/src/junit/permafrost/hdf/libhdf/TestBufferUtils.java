/**
 * 
 */
package permafrost.hdf.libhdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.junit.Test;

/**
 * TODO Class Docs
 *
 */
public class TestBufferUtils {

	

	/**
	 * Test method for {@link permafrost.hdf.libhdf.BufferUtils#packUChar(java.nio.ShortBuffer, boolean)}.
	 */
	@Test
	public final void testPackUChar() {		
		ShortBuffer buffer = ShortBuffer.allocate(1);
		
		/* Put value that would cause overflow on signed type. 
		 * Check that packing it does overflow. */
		buffer.put((short) (Byte.MAX_VALUE + 1));
		ByteBuffer sbuffer = BufferUtils.packUChar(buffer, false);
		assertTrue(sbuffer.get(0) < 0);
		assertEquals(Byte.MIN_VALUE, sbuffer.get(0));
		
		/* Put value that would cause overflow on signed type.
		 * Check that packing it does overflow. */
		buffer.put(0, (short) (0xFF));
		sbuffer = BufferUtils.packUChar(buffer, false);
		assertTrue(sbuffer.get(0) < 0);
		assertEquals(-1, sbuffer.get(0));
	}

	/**
	 * Test method for {@link permafrost.hdf.libhdf.BufferUtils#unpackUChar(java.nio.ByteBuffer, boolean)}.
	 */
	@Test
	public final void testUnpackUChar() {
		ByteBuffer buffer = ByteBuffer.allocate(1);
		ShortBuffer sbuffer = ShortBuffer.allocate(1);
		
		/* Put overflowed value in signed type.
		 * Check that result is not overflowed. */
		buffer.put((byte) (Byte.MAX_VALUE + 1));
		BufferUtils.unpackUChar(buffer, sbuffer);
		assertTrue(sbuffer.get(0) > 0);
		assertEquals((short) (Byte.MAX_VALUE + 1), sbuffer.get(0));
		
	      /* Put overflowed value in signed type.
         * Check that result is not overflowed. */
		buffer.put(0, (byte) 0xFF);
		BufferUtils.unpackUChar(buffer, sbuffer);
		assertTrue(sbuffer.get(0) > 0);
		assertEquals((short) 0xFF, sbuffer.get(0));
	}
	
	   /**
     * Test method for {@link permafrost.hdf.libhdf.BufferUtils#unpackUChar(java.nio.ByteBuffer, boolean)}.
     */
    @Test
    public final void testUnpackUCharLittleEndian() {
        ByteBuffer buffer = ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer sbuffer = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        buffer.put((byte) (Byte.MAX_VALUE + 1));
        BufferUtils.unpackUChar(buffer, sbuffer);
        assertTrue(sbuffer.get(0) > 0);
        assertEquals((short) (Byte.MAX_VALUE + 1), sbuffer.get(0));
        
        buffer.put(0, (byte) 0xFF);
        BufferUtils.unpackUChar(buffer, sbuffer);
        assertTrue(sbuffer.get(0) > 0);
        assertEquals((short) 0xFF, sbuffer.get(0));
    }
	
	   /**
     * Test method for {@link permafrost.hdf.libhdf.BufferUtils#unpackUChar(java.nio.ByteBuffer, boolean)}.
     */
    @Test
    public final void testRoundTripUChar() {
        ShortBuffer sbuffer = ShortBuffer.allocate(1);        
        sbuffer.put((short) (Byte.MAX_VALUE + 1));
        ByteBuffer bbuffer = BufferUtils.packUChar(sbuffer, false);
        
        ShortBuffer result = ShortBuffer.allocate(1);
        BufferUtils.unpackUChar(bbuffer, result);
        assertEquals(Byte.MAX_VALUE + 1, result.get(0));
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.BufferUtils#packUShort(java.nio.IntBuffer, boolean)}.
     */
    @Test
    public final void testPackUShort() {     
        IntBuffer buffer = IntBuffer.allocate(1);
        
        /* Put value that would cause overflow on signed type. 
         * Check that packing it does overflow. */
        buffer.put(Short.MAX_VALUE + 1);
        ByteBuffer sbuffer = BufferUtils.packUShort(buffer, false);
        assertTrue(sbuffer.getShort(0) < 0);
        assertEquals(Short.MIN_VALUE, sbuffer.getShort(0));
        
        /* Put value that would cause overflow on signed type. 
         * Check that packing it does overflow. */
        buffer.put(0, 0xFFFF);
        sbuffer = BufferUtils.packUShort(buffer, false);
        assertTrue(sbuffer.getShort(0) < 0);
        assertEquals(-1, sbuffer.getShort(0));
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.BufferUtils#unpackUShort(java.nio.IntBuffer, boolean)}.
     */
    @Test
    public final void testUnpackUShort() {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        IntBuffer sbuffer = IntBuffer.allocate(1);
        
        /* Put overflowed value in signed type.
         * Check that result is not overflowed. */
        buffer.putShort((short) (Short.MAX_VALUE + 1));
        BufferUtils.unpackUShort(buffer, sbuffer);
        assertTrue(sbuffer.get(0) > 0);
        assertEquals(Short.MAX_VALUE + 1, sbuffer.get(0));
        
        /* Put overflowed value in signed type.
         * Check that result is not overflowed. */
        buffer.putShort(0, (short) 0xFFFF);
        BufferUtils.unpackUShort(buffer, sbuffer);
        assertTrue(sbuffer.get(0) > 0);
        assertEquals(0xFFFF, sbuffer.get(0));
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.BufferUtils#packUTF8(java.nio.CharBuffer, boolean)}.
     */
    @Test
    public final void testPackUTF8() {     
        CharBuffer buffer = CharBuffer.allocate(1);
        
        /* Put value that would cause overflow on signed type. 
         * Check that packing it does overflow. */
        buffer.put((char) (Byte.MAX_VALUE + 1));
        ByteBuffer sbuffer = BufferUtils.packUTF8(buffer, false);
        assertTrue(sbuffer.get(0) < 0);
        assertEquals(Byte.MIN_VALUE, sbuffer.get(0));
        
        /* Put value that would cause overflow on signed type. 
         * Check that packing it does overflow. */
        buffer.put(0, (char) 0xFF);
        sbuffer = BufferUtils.packUTF8(buffer, false);
        assertTrue(sbuffer.get(0) < 0);
        assertEquals(-1, sbuffer.get(0));
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.BufferUtils#unpackUTF8(java.nio.CharBuffer, boolean)}.
     */
    @Test
    public final void testUnpackUTF8() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        CharBuffer sbuffer = CharBuffer.allocate(1);
        
        /* Put overflowed value in signed type.
         * Check that result is not overflowed. */
        buffer.put((byte) (Byte.MAX_VALUE + 1));
        BufferUtils.unpackUTF8(buffer, sbuffer);
        assertTrue(sbuffer.get(0) > 0);
        assertEquals('\u0080', sbuffer.get(0));
        
        /* Put overflowed value in signed type.
         * Check that result is not overflowed. */
        buffer.put(0, (byte) 0xFF);
        BufferUtils.unpackUTF8(buffer, sbuffer);
        assertTrue(sbuffer.get(0) > 0);
        assertEquals('\u00FF', sbuffer.get(0));
    }
    
    /**
     * Test method for {@link permafrost.hdf.libhdf.BufferUtils#packUInt(java.nio.LongBuffer, boolean)}.
     */
    @Test
    public final void testPackUInt() {     
        LongBuffer buffer = LongBuffer.allocate(1);
        
        /* Put value that would cause overflow on signed type. 
         * Check that packing it does overflow. */
        buffer.put(Integer.MAX_VALUE + 1);
        ByteBuffer sbuffer = BufferUtils.packUInt(buffer, false);
        assertTrue(sbuffer.getInt(0) < 0);
        assertEquals(Integer.MIN_VALUE, sbuffer.getInt(0));
        
        /* Put value that would cause overflow on signed type. 
         * Check that packing it does overflow. */
        buffer.put(0, 0xFFFFFFFF);
        sbuffer = BufferUtils.packUInt(buffer, false);
        assertTrue(sbuffer.getInt(0) < 0);
        assertEquals(-1, sbuffer.getInt(0));
    }

    /**
     * Test method for {@link permafrost.hdf.libhdf.BufferUtils#unpackUInt(java.nio.LongBuffer, boolean)}.
     */
    @Test
    public final void testUnpackUInt() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        LongBuffer sbuffer = LongBuffer.allocate(1);
        
        /* Put overflowed value in signed type.
         * Check that result is not overflowed. */
        buffer.putInt(Integer.MAX_VALUE + 1);
        BufferUtils.unpackUInt(buffer, sbuffer);
        assertTrue(sbuffer.get(0) > 0);
        assertEquals(((long) Integer.MAX_VALUE) + 1, sbuffer.get(0));
        
        /* Put overflowed value in signed type.
         * Check that result is not overflowed. */
        buffer.putInt(0, 0xFFFFFFFF);
        BufferUtils.unpackUInt(buffer, sbuffer);
        assertTrue(sbuffer.get(0) > 0);
        assertEquals(0xFFFFFFFFl, sbuffer.get(0));
    }

}
