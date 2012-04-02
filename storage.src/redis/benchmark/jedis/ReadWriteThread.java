package redis.benchmark.jedis;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import redis.benchmark.BaseReadWriteThread;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

public class ReadWriteThread extends BaseReadWriteThread {
    Jedis jedis;

	public ReadWriteThread(Pool<Jedis> pool, int repeats,
			CyclicBarrier barrier, int offset, int length, AtomicLong miss,
			AtomicLong fail, AtomicLong hit) {
		super(repeats, barrier, offset, length, miss, fail, hit);
		Jedis jedis = pool.getResource();
		this.jedis = jedis;
	}

	public boolean set(int i, String s) throws Exception {
	    jedis.set(String.valueOf(i), s);
		return true;
	}

	public String get(int n) throws Exception {
	    String result = jedis.get(String.valueOf(n));
		return result;
	}
	
}
