package redis.benchmark.jredis;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.jredis.JRedis;
import org.jredis.ri.alphazero.JRedisService;

import redis.benchmark.BaseTest;

public class JRedisd extends BaseTest {
    public static final String host = "10.210.74.77";
    public static final int port = 6380;
    
    public static void main(String[] args) throws Exception {
        JRedis jredis = new JRedisService(host, port, "password", 1, 10);
        System.out.println("JRedis startup");
        //warmUp(jredis);

        for (int i = 0; i < THREADS.length; i++) {
            for (int j = 0; j < BYTES.length; j++) {
                int repeats = getReapts(i);
                test(jredis, BYTES[j], THREADS[i], repeats, true);
            }
        }
        
        jredis.quit();
    }

	private static void warmUp(JRedis jredis)
			throws Exception {
		test(jredis, 100, 100, 10000, false);
		System.out.println("warm up");
	}

	public static void test(JRedis jredis, int length,
			int threads, int repeats, boolean print) throws Exception {
	    //jredis.flushdb();
		AtomicLong miss = new AtomicLong(0);
		AtomicLong fail = new AtomicLong(0);
		AtomicLong hit = new AtomicLong(0);
		CyclicBarrier barrier = new CyclicBarrier(threads + 1);

		for (int i = 0; i < threads; i++) {
			new ReadWriteThread(jredis, repeats, barrier, i * repeats,
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

/*
javac -classpath ".;..\lib\commons-logging-1.1.1.jar;..\lib\jredis-1.0-rc2.jar" redis/benchmark/jredis/JRedisd.java

java -classpath ".;..\lib\commons-logging-1.1.1.jar;..\lib\jredis-1.0-rc2.jar" redis.benchmark.jredis.JRedisd

*/

