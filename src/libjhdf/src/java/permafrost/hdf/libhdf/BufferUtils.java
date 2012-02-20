/**
 * 
 */
package permafrost.hdf.libhdf;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * Static methods used to pass unsigned data to and from native code.
 */
class BufferUtils {
	
    /**
     * Not used.
     */
    private BufferUtils() {
        super();
    }
    
    /**
     * Packs a ShortBuffer to unsigned char format, discarding the high byte.
     * 
     * <p>Returns new ByteBuffer with the same byte order as sbuffer.</p>
     * 
     * @param sbuffer ShortBuffer to pack.
     * @param direct If the new buffer should be allocated direct.
     * 
     * @return New ByteBuffer with the values of sbuffer.
     */
    public static ByteBuffer packUChar(ShortBuffer sbuffer, boolean direct) {
    	ByteBuffer bbuffer;
    	if (direct) {
    		bbuffer = ByteBuffer.allocateDirect(sbuffer.capacity());
    	} else {
    		bbuffer = ByteBuffer.allocate(sbuffer.capacity());
    	}
        bbuffer.order(sbuffer.order());
        sbuffer.rewind();
        while (sbuffer.position() < sbuffer.limit()) {
            bbuffer.put((byte) sbuffer.get());
        }
        bbuffer.flip();
        sbuffer.flip();
        return (bbuffer);
    }
    
    /**
     * Packs a IntBuffer to unsigned short format, discarding the two high bytes.
     * 
     * <p>Returns new ByteBuffer with the same byte order as ibuffer.</p>
     * 
     * @param ibuffer IntBuffer to pack.
     * @param direct If the new buffer should be allocated direct.
     * 
     * @return New ByteBuffer with the values of ibuffer.
     */
    public static ByteBuffer packUShort(IntBuffer ibuffer, boolean direct) {
    	ByteBuffer bbuffer;
    	if (direct) {
    		bbuffer = ByteBuffer.allocateDirect(ibuffer.capacity()*2);
    	} else {
    		bbuffer = ByteBuffer.allocate(ibuffer.capacity()*2);
    	}
        bbuffer.order(ibuffer.order());
        ibuffer.rewind();
        while (ibuffer.position() < ibuffer.limit()) {
            bbuffer.putShort((short) ibuffer.get());
        }
        bbuffer.flip();
        ibuffer.rewind();
        return (bbuffer);
    }
    
    /**
     * Packs a LongBuffer to unsigned int format, discarding the four high bytes.
     * 
     * <p>Returns new ByteBuffer with the same byte order as lbuffer.</p>
     * 
     * @param lbuffer LongBuffer to pack.
     * @param direct If the new buffer should be allocated direct.
     * 
     * @return New ByteBuffer with the values of lbuffer.
     */
    public static ByteBuffer packUInt(LongBuffer lbuffer, boolean direct) {
        ByteBuffer bbuffer;
        if (direct) {
            bbuffer = ByteBuffer.allocateDirect(lbuffer.capacity()*4);
        } else {
            bbuffer = ByteBuffer.allocate(lbuffer.capacity()*4);
        }
        bbuffer.order(lbuffer.order());
        lbuffer.rewind();
        while (lbuffer.position() < lbuffer.limit()) {
            bbuffer.putInt((int) lbuffer.get());
        }
        bbuffer.flip();
        return (bbuffer);
    }
    
    /**
     * Packs a CharBuffer to unsigned char format, discarding the high byte.
     * 
     * <p>Returns new ByteBuffer with the same byte order as cbuffer.</p>
     * 
     * @param cbuffer CharBuffer to pack.
     * @param direct If the new buffer should be allocated direct.
     * 
     * @return New ByteBuffer with the values of cbuffer.
     */
    public static ByteBuffer packUTF8(CharBuffer cbuffer, boolean direct) {
        ByteBuffer bbuffer;
        if (direct) {
            bbuffer = ByteBuffer.allocateDirect(cbuffer.capacity());
        } else {
            bbuffer = ByteBuffer.allocate(cbuffer.capacity());
        }
        bbuffer.order(cbuffer.order());
        cbuffer.rewind();
        while (cbuffer.position() < cbuffer.limit()) {
            bbuffer.put((byte) cbuffer.get());
        }
        bbuffer.flip();
        return (bbuffer);
    }
    
    /**
     * Unpacks a ByteBuffer from unsigned char to short format.
     * 
     * @param bbuffer ByteBuffer to unpack.
     * @param sbuffer ShortBuffer to unpack to.
     */
    public static void unpackUChar(ByteBuffer bbuffer, ShortBuffer sbuffer) {     
        bbuffer.rewind();
        sbuffer.rewind();
        while (bbuffer.position() < bbuffer.limit()) {        	
        	sbuffer.put((short) (bbuffer.get() & 0xFF));
        }
        sbuffer.flip();
    }
    
    /**
     * Unpacks a ByteBuffer from unsigned short to int format.
     * 
     * 
     * @param bbuffer ByteBuffer to unpack.
     * @param ibuffer IntBuffer to unpack to.
     */
    public static void unpackUShort(ByteBuffer bbuffer, IntBuffer ibuffer) {     
        bbuffer.rewind();
        ibuffer.rewind();
        while (bbuffer.position() < bbuffer.limit()) {
        	ibuffer.put(bbuffer.getShort() & 0xFFFF);                    
        }
        ibuffer.flip();
    }
    
    /**
     * Unpacks a ByteBuffer from unsigned char to Java char format.
     * 
     * @param bbuffer ByteBuffer to unpack.
     * @param cbuffer CharBuffer to unpack to.
     */
    public static void unpackUTF8(ByteBuffer bbuffer, CharBuffer cbuffer) {     
        bbuffer.rewind();
        cbuffer.rewind();
        while (bbuffer.position() < bbuffer.limit()) {           
                cbuffer.put((char) (bbuffer.get() & 0xFF));
        }
        cbuffer.flip();
    }
    
    /**
     * Unpacks a ByteBuffer from unsigned int to long format.
     * 
     * 
     * @param bbuffer ByteBuffer to unpack.
     * @param lbuffer LongBuffer to unpack to.
     */
    public static void unpackUInt(ByteBuffer bbuffer, LongBuffer lbuffer) {     
        bbuffer.rewind();
        lbuffer.rewind();
        while (bbuffer.position() < bbuffer.limit()) {         
        	lbuffer.put(bbuffer.getInt() & 0xFFFFFFFFl);        	
        }
        lbuffer.flip();
    }
}
