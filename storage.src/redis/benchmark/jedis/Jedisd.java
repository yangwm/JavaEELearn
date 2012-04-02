package redis.benchmark.jedis;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.pool.impl.GenericObjectPool;

import redis.benchmark.BaseTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

public class Jedisd extends BaseTest {
    
    public static final String host = "10.210.74.77";
    public static final int port = 6380;
    
    public static void main(String[] args) throws Exception {
        GenericObjectPool.Config cfg = new GenericObjectPool.Config();
        cfg.maxActive = 100;
        JedisPool pool = new JedisPool(cfg, host, port);
        
        System.out.println("Jedis startup");
        //warmUp(pool);

        for (int i = 0; i < THREADS.length; i++) {
            for (int j = 0; j < BYTES.length; j++) {
                int repeats = getReapts(i);
                test(pool, BYTES[j], THREADS[i], repeats, true);
            }
        }
        
        pool.destroy();
    }

	private static void warmUp(Pool<Jedis> pool)
			throws Exception {
		test(pool, 100, 100, 10000, false);
		System.out.println("warm up");
	}

	public static void test(Pool<Jedis> pool, int length,
			int threads, int repeats, boolean print) throws Exception {
		//pool.flushAll();
		AtomicLong miss = new AtomicLong(0);
		AtomicLong fail = new AtomicLong(0);
		AtomicLong hit = new AtomicLong(0);
		CyclicBarrier barrier = new CyclicBarrier(threads + 1);

		for (int i = 0; i < threads; i++) {
			new ReadWriteThread(pool, repeats, barrier, i * repeats,
					length, miss, fail, hit).start();
		}
		barrier.await();
		long start = System.nanoTime();
		barrier.await();
		if (print) {
			long duration = System.nanoTime() - start;
			long total = repeats * threads;
			printResult(length, threads, repeats, miss, fail, hit, duration,
					total);
		}
	}
	
}
