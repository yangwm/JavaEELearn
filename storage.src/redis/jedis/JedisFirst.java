/**
 * 
 */
package redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * Redis First example  
 * 
 * @author yangwm Nov 17, 2010 3:21:36 PM
 */
public class JedisFirst {
    public static final String host = "10.210.74.77";
    public static final int port = 6380;
//    public static final String host = "127.0.0.1";
//    public static final int port = 6379;

    public static void main(String[] args) {
        try {
            int timeout = 2000;
            // key in your DB 0. 
            Jedis jedis = new Jedis(host, port, timeout);
            //jedis.ping();
            System.out.println("client connect  timeout:" + timeout);
            jedis.connect();
            
            jedis.set("yangwmTest", "yangwTestValue123456");
            System.out.println(jedis.get("yangwmTest"));
            
            jedis.set("yangwm_unreadcount_s", "5");
            jedis.set("yangwm_unreadcount_b".getBytes(), String.valueOf(5).getBytes());
            jedis.incrBy("yangwm_unreadcount_i", 1);
            
            jedis.hset("yangwm", "unreadcount_s", "5");
            jedis.hset("yangwm".getBytes(), "unreadcount_b".getBytes(), String.valueOf(5).getBytes());
            jedis.hincrBy("yangwm", "unreadcount_i", 1);
            
            jedis.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}


