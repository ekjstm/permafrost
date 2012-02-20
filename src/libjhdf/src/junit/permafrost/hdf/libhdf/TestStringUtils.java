package permafrost.hdf.libhdf;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

public class TestStringUtils {

	@Test
	public void testFromNullTermArray() {
		 byte[] group = new byte[] {103, 114, 111, 117, 112, 0};
		 String str = StringUtils.fromNullTerm(group);
		 assertEquals("group", str);
	}

	@Test
    public void testFromNullTermBuffer() {
         byte[] group = new byte[] {103, 114, 111, 117, 112, 0};
         ByteBuffer buffer = ByteBuffer.wrap(group);
         String str = StringUtils.fromNullTerm(buffer);
         assertEquals("group", str);
    }
}
