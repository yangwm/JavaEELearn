package race;

import java.nio.ByteBuffer;


/**
 * ʹ�� NIO ����ת�� long[] �� byte[]
 * @author tim
 *
 */
public class VectorNioUtil {
	protected static ThreadLocal<ByteBuffer> localBuffer = new ThreadLocal<ByteBuffer>() {
		protected ByteBuffer initialValue() {
			return ByteBuffer.allocate(200 * 8);
		}
	};
	protected static ThreadLocal<long[]> localLongBuffer = new ThreadLocal<long[]>() {
		protected long[] initialValue() {
			return new long[200 * 8];
		}
	};	
	
	public static final byte[] toBytes(long[] src, int len) {
		if (src == null)
			return null;
		byte[] results = new byte[200 * 8];
		try {
			ByteBuffer bb = localBuffer.get();
			bb.clear();
			for (int i = 0; i < len; i++)
				bb.putLong(src[i]);
			bb.rewind();
			bb.get(results);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return results;
	}

	public static final ByteBuffer toByteBuffer(long[] ids) {
		if (ids == null)
			return null;
		
		ByteBuffer bb = localBuffer.get();
		bb.reset();
		for (int i = 0; i < ids.length; i++)
			bb.putLong(ids[i]);
		
		return bb;
	}
	
	public static final long[] toLong(byte[] bytes) {
		long[] results = localLongBuffer.get();
		ByteBuffer bb = localBuffer.get();
		bb.reset();
		bb.put(bytes);
		for (int i = 0; i < bytes.length / 8; i++)
			results[i] = bb.getLong();
		return results;
	}

	/**
	 * 
	 * @param bb
	 * @param len the length of ByteBuffer(byte[].length) 
	 * @return
	 */
	public static final long[] toLong(ByteBuffer bb, int len) {
		long[] results = localLongBuffer.get();
		for (int i = 0; i < len / 8; i++)
			results[i] = bb.getLong();
		return results;
	}
}
