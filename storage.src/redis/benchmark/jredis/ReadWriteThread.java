package redis.benchmark.jredis;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.jredis.JRedis;

import redis.benchmark.BaseReadWriteThread;

public class ReadWriteThread extends BaseReadWriteThread {
    JRedis jredis;

	public ReadWriteThread(JRedis  jredis, int repeats,
			CyclicBarrier barrier, int offset, int length, AtomicLong miss,
			AtomicLong fail, AtomicLong hit) {
		super(repeats, barrier, offset, length, miss, fail, hit);
		this.jredis = jredis;
	}

	public boolean set(int i, String s) throws Exception {
	    jredis.set(String.valueOf(i), s);
		return true;
	}

	public String get(int n) throws Exception {
	    String result = new String(jredis.get(String.valueOf(n)));
		return result;
	}
	
}
