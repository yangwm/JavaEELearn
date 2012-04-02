/**
 * 
 */
package redis.jedis;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * 
 * @author yangwm Dec 26, 2010 10:43:49 PM
 */
public class JedisPipelineStudy {
    public static final String host = "10.210.74.77";
    public static final int port = 6380;
    //public static final String host = "192.168.18.128";
    //public static final int port = 6379;

    public static void main(String[] args) {
        try {
            // key in your DB 0. 
            Jedis jedis = new Jedis(host, port);
            jedis.ping();
            
            /* before 1.5.3 
            Pipeline p = jedis.pipelined();
            p.set("foo", "bar");
            p.get("foo");
            List<Object> results = p.execute();
            for (Object obj : results) {
                System.out.println(new String((byte[])obj));
            }
            */
            
            Pipeline p = jedis.pipelined();
            p.set("fool", "bar"); 
//            p.zadd("foo", 1, "barowitch");  p.zadd("foo", 0, "barinsky"); p.zadd("foo", 0, "barikoviev");
            Response<String> pipeString = p.get("fool");
//            Response<Set<String>> sose = p.zrange("foo", 0, -1);
            p.sync(); 

//            int soseSize = sose.get().size();
//            Set<String> setBack = sose.get();
            System.out.println("pipeString:" + pipeString.get());
//            System.out.println("setBack:" + setBack);
            
            jedis.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
