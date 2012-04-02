/**
 * 
 */
package redis.jredis;

import java.util.Arrays;
import java.util.List;

import org.jredis.ClientRuntimeException;
import org.jredis.JRedis;
import org.jredis.ProviderException;
import org.jredis.RedisException;
import org.jredis.ri.alphazero.JRedisService;
import org.jredis.ri.alphazero.support.Convert;

/**
 * Redis First example  
 * 
 * @author yangwm Nov 17, 2010 3:21:36 PM
 */
public class JRedisFirst {
//    public static final String host = "10.210.74.77";
//    public static final int port = 6380;
    public static final String host = "127.0.0.1";
    public static final int port = 6379;

    public static void main(String[] args) {
        try {
            // key in your DB 0. 
            JRedis  jredis = new JRedisService(host, port, null, 0, 2);
            jredis.ping();

            jredis.rpush("yangwm_test_list", 1);
            jredis.rpush("yangwm_test_list", 2);
            List<byte[]> testList = jredis.lrange("yangwm_test_list", 0, 10);
            int testListSize = (testList == null) ? 0 : testList.size();
            long[] tests = new long[testListSize];
            for (int i = 0; i < testListSize; i++) {
                tests[i] = Convert.toLong(testList.get(i));
            }
            System.out.println(Arrays.toString(tests));
            
            jredis.set("yangwmTest", "yangwTestValue123456");
            System.out.println(new String(jredis.get("yangwmTest")));
            
            jredis.set("yangwm_unreadcount_s", "5");
            jredis.set("yangwm_unreadcount_b", 5);
            jredis.incr("yangwm_unreadcount_i");
            
            //jredis.hset("yangwm", "unreadcount_s", "5");
            //jredis.hset("yangwm", "unreadcount_b", 5);
            //jredis.hincrBy("yangwm", "unreadcount_i", 1);
            
            jredis.quit();
        } catch (RedisException error) {
            error.printStackTrace();
        } catch (ProviderException bug) {
            System.out.format("Oh no, an 'un-documented feature':  %s\nKindly report it.", bug.getMessage());
            bug.printStackTrace();
        } catch (ClientRuntimeException problem) {
            System.out.format("%s\n", problem.getMessage());
            problem.printStackTrace();
        }
    }
    
}

/*
[root@vm10080064 test]# java -classpath ".:commons-logging-1.1.1.jar:jredis-1.0-rc2.jar" JRedisFirst
Jan 11, 2011 3:30:10 PM org.jredis.ri.alphazero.support.Log log
INFO: WARNING: heartbeat is disabled.
Jan 11, 2011 3:30:10 PM org.jredis.ri.alphazero.support.Log log
INFO: WARNING: heartbeat is disabled.
Jan 11, 2011 3:30:40 PM org.jredis.ri.alphazero.support.Log _error
SEVERE: -1 read count in readLine() while reading response line.
Jan 11, 2011 3:30:40 PM org.jredis.ri.alphazero.support.Log _error
SEVERE: PROBLEM: serviceRequest() -- ClientRuntimeException  => Unexpected EOF (read -1) in readLine.  Command: RPUSH
Jan 11, 2011 3:30:40 PM org.jredis.ri.alphazero.support.Log log
INFO: RedisConnection - reconnecting
Jan 11, 2011 3:30:40 PM org.jredis.ri.alphazero.support.Log log
INFO: WARNING: heartbeat is disabled.
Exception on [RPUSH] => Connection re-established but last request not processed:  Unexpected EOF (read -1) in readLine.  Command: RPUSH
        at org.jredis.ri.alphazero.JRedisService.serviceRequest(JRedisService.java:201)
        at org.jredis.ri.alphazero.JRedisSupport.rpush(JRedisSupport.java:249)
        at org.jredis.ri.alphazero.JRedisSupport.rpush(JRedisSupport.java:258)
        at JRedisFirst.main(JRedisFirst.java:38)
[root@vm10080064 test]#



*/



