/**
 * 
 */
package redis.jredis;

import java.util.List;

import org.jredis.ClientRuntimeException;
import org.jredis.JRedis;
import org.jredis.ProviderException;
import org.jredis.RedisException;
import org.jredis.ri.alphazero.JRedisService;
import org.jredis.ri.alphazero.support.Convert;
import org.jredis.ri.alphazero.support.DefaultCodec;

/**
 * 
 * @author yangwm Nov 24, 2010 5:13:10 PM
 */
public class JRedisStudy {
    public static final String host = "10.210.74.77";
    public static final int port = 6380;
//    public static final String host = "192.168.18.128";
//    public static final int port = 6379;

    public static void main(String[] args) {
        try {
            // key in your DB 0. 
            JRedis  jredis = new JRedisService(host, port, "password", 0, 5);
            jredis.ping();
            
            jredis.set("yangwmLongTest", -9223372036854775808L); // Long.MIN_VALUE
            jredis.set("yangwmLongTest3", 9223372036854775807L); // Long.MAX_VALUE
            
            List<byte[]> datas = jredis.mget("yangwmLongTest", "yangwmLongTest2", "yangwmLongTest3");
            System.out.println("datas.get(0):" + 
                    (datas.get(0) != null ? Convert.toLong(datas.get(0)) : datas.get(0)));
            System.out.println("datas.get(1):" + 
                    (datas.get(1) != null ? Convert.toLong(datas.get(1)) : datas.get(1)));
            System.out.println("datas.get(2):" + 
                    (datas.get(2) != null ? Convert.toLong(datas.get(2)) : datas.get(2)));

            jredis.rpush("yangwmListTest", "c");
            jredis.rpush("yangwmListTest", "c++");
            jredis.rpush("yangwmListTest", "java");
            jredis.rpush("yangwmListTest", "c++");
            
            datas = jredis.lrange("yangwmListTest", 0, 100);
            System.out.println("datas:" + 
                    (datas != null ? DefaultCodec.toStr(datas) : datas));
            
            jredis.lrem("yangwmListTest", "c++", 0);
            datas = jredis.lrange("yangwmListTest", 0, 100);
            System.out.println("datas:" + 
                    (datas != null ? DefaultCodec.toStr(datas) : datas));
            jredis.del("yangwmListTest");
            
            jredis.quit();
        } catch (RedisException error) {
            error.printStackTrace();
        } catch (ProviderException bug) {
            System.out.format("Oh no, an 'un-documented feature':  %s\nKindly report it.", bug.getMessage());
        } catch (ClientRuntimeException problem) {
            System.out.format("%s\n", problem.getMessage());
        }
    }
}

/*
datas.get(0):1
datas.get(1):null
datas.get(2):3
datas:[c, c++, java, c++]
datas:[c, java]
[INFO] JREDIS - HeartbeatJinn thread < [Synchronous Connection <host: /10.218.23.155, port: 6380, db: 0>] heartbeat> started.

*/

