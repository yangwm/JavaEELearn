/**
 * 
 */
package redis.jedis;

import java.util.Date;
import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author yangwm Nov 24, 2010 5:13:10 PM
 */
public class JedisStudy {
    //public static final String host = "10.210.74.77";
    //public static final int port = 6380;
    public static final String host = "127.0.0.1";
    public static final int port = 6379;

    public static void main(String[] args) {
        try {
            // key in your DB 0. 
            //Jedis jedis = new Jedis(host, port);
            GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();

            poolConfig.maxActive = 10;

            poolConfig.maxIdle = 5; // when return obj,the obj may be
            // destroy if current idle > maxIdle

            poolConfig.minIdle = 5;// this may have no effect,cause the
                                                // evictor is disabled by default
            if (true) {
                // test 1/4 objs per run
                poolConfig.numTestsPerEvictionRun = -4;
                poolConfig.timeBetweenEvictionRunsMillis = 30 * 1000;
                poolConfig.testWhileIdle = true;
                // 1 hour
                poolConfig.softMinEvictableIdleTimeMillis = 3600 * 1000;
                poolConfig.minEvictableIdleTimeMillis = -1;
            }

            poolConfig.maxWait = 500;
            poolConfig.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

            poolConfig.lifo = false;
            
            JedisPool pool = new JedisPool(poolConfig, host, port, 2000);
            //ShardedJedisPool pool = new ShardedJedisPool(cfg, host, port);
            
            for (int i = 0; i < 100; i++) {
                Jedis jedis = pool.getResource();
                jedis.ping();
                
                Thread.sleep(10 * 1000L);
                System.out.println("sleep over date:" + new Date());
                
                //jedis.set("yangwmLongTest".getBytes(), -9223372036854775808L);
                jedis.set("yangwmLongTest", String.valueOf(-9223372036854775808L)); // Long.MIN_VALUE
                jedis.set("yangwmLongTest3", String.valueOf(9223372036854775807L)); // Long.MAX_VALUE
                
                System.out.println("jedisSet over date:" + new Date());
                
                List<String> datas = jedis.mget("yangwmLongTest", "yangwmLongTest2", "yangwmLongTest3");
                System.out.println("datas.get(0):" + 
                        (datas.get(0) != null ? datas.get(0) : datas.get(0)));
                System.out.println("datas.get(1):" + 
                        (datas.get(1) != null ? datas.get(0) : datas.get(1)));
                System.out.println("datas.get(2):" + 
                        (datas.get(2) != null ? datas.get(0) : datas.get(2)));

                jedis.rpush("yangwmListTest", "c");
                jedis.rpush("yangwmListTest", "c++");
                jedis.rpush("yangwmListTest", "java");
                jedis.rpush("yangwmListTest", "c++");
                
                datas = jedis.lrange("yangwmListTest", 0, 100);
                System.out.println("datas:" + 
                        (datas != null ? datas : datas));
                
                jedis.lrem("yangwmListTest", 0, "c++");
                datas = jedis.lrange("yangwmListTest", 0, 100);
                System.out.println("datas:" + 
                        (datas != null ? datas : datas));
                jedis.del("yangwmListTest");
                
                //jedis.quit();
                pool.returnResource(jedis);
            }
            
            pool.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
yangwm@yangwuming:~/Tools$ sudo sh controlport.sh resume 6379
yangwm@yangwuming:~/Tools$ sudo sh controlport.sh block 6379


datas.get(0):1
datas.get(1):null
datas.get(2):3
datas:[c, c++, java, c++]
datas:[c, java]
[INFO] JREDIS - HeartbeatJinn thread < [Synchronous Connection <host: /10.218.23.155, port: 6380, db: 0>] heartbeat> started.

*/

