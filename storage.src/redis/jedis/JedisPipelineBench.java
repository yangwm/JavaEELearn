package redis.jedis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 
 * 
 * @author yangwm Jul 24, 2011 3:05:41 PM
 */
public class JedisPipelineBench {
    public static final String host = "10.210.74.77";
    public static final int port = 6380;
    //public static final String host = "192.168.18.128";
    //public static final int port = 6379;

    public static void main(String[] args) {
        boolean isPipeline = true;
        
        try {
            // key in your DB 0. 
            Jedis jedis = new Jedis(host, port);
            jedis.ping();
            
            int i = 0;
            for (i = 0; i < Integer.MAX_VALUE; i++) {
                int temp = (i % 100000);
                
                if (isPipeline) {
                    Pipeline p = jedis.pipelined();
                    p.hincrBy("foo" + temp, "bar", 1L);
                } else {
                    jedis.hincrBy("foo" + temp, "bar", 1L);
                }
                
                if (temp == 0) {
                    Thread.sleep(1);
                    System.out.println("JedisPipelineBench isPipeline:" + isPipeline + ",  i:" + i);
                    jedis.disconnect();
                    jedis.connect();
                }
            }

            jedis.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
