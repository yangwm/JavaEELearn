/**
 * 
 */
package redis.jedis;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.pool.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

/**
 * 
 * @author yangwm Apr 26, 2011 2:37:30 PM
 */
public class JedisBench {

    public static final String host = "10.210.74.77";
    public static final int port = 6380;
    
    public static final int threadSize = 10;
    public static final int executeTimes = 1000;
    
    public static void main(String[] args) throws Exception {
        GenericObjectPool.Config cfg = new GenericObjectPool.Config();
        cfg.maxActive = 20;
        JedisPool pool = new JedisPool(cfg, host, port);
        
        System.out.println("Jedis startup");
        System.out.println("threadSize=" + threadSize + ",executeTime=" + executeTimes);
        test(pool);
        
        pool.destroy();
    }
    
    public static void test(final Pool<Jedis> pool) throws Exception {
        //pool.flushAll();
        //AtomicLong hit = new AtomicLong(0);
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(threadSize);

        for (int i = 0; i < threadSize; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = pool.getResource();
                    
                    try {
                        startLatch.await();
                        for (int j = 0; j < executeTimes; j++) {
                            String v = "" + j;
                            //jedis.set(v, v);
                            jedis.get(v);
                        }
                        endLatch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        long start = System.nanoTime();
        startLatch.countDown();
        endLatch.await();
        long consumeTime = (System.nanoTime() - start);
        long totalTimes = threadSize * executeTimes;
        
        System.out.println("total times=" + totalTimes + ", summary cosume=" + (consumeTime / 1000000) 
                + " ms , once cosume =" + (consumeTime / totalTimes / 1000) + " us");
    }
    
}

/*
javac -classpath ".:commons-pool-1.5.5.jar:jedis-1.5.2.jar" redis/jedis/JedisBench.java

java -classpath ".:commons-pool-1.5.5.jar:jedis-1.5.2.jar" redis.jedis.JedisBench


*/

