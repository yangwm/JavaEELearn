/**
 * 
 */
package redis.jredis;

import org.jredis.JRedis;
import org.jredis.ri.alphazero.JRedisPipelineService;

/**
 * 
 * @author yangwm Dec 26, 2010 10:43:49 PM
 */
public class JRedisPipelineStudy {
    public static final String host = "10.210.74.77";
    public static final int port = 6380;
//    public static final String host = "192.168.18.128";
//    public static final int port = 6379;

    public static void main(String[] args) {
        try {
            // key in your DB 0. 
            JRedis jredis = new JRedisPipelineService(host, port, "password", 0);
            //JRedis jredis = new JRedisPipeline(host, port, "password", 0);
            jredis.ping();
            
            jredis.set("foo", "bar");
            jredis.set("foo1", "bar1");
            jredis.set("foo2", "bar2");
            byte[] result = jredis.get("foo");
            System.out.println(new String(result));
            
            jredis.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
