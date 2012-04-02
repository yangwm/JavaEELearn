/**
 * 
 */
package redis.jredis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jredis.JRedis;
import org.jredis.RedisException;
import org.jredis.ri.alphazero.JRedisClient;

/**
 * 
 * @author yangwm Jan 11, 2011 1:43:28 PM
 */
public class JRedisTimeOutTest {

    /**
     * @param args
     * @throws RedisException 
     */
    public static void main(String[] args) {
        List<JRedis> list = new ArrayList<JRedis>();
        
        String hostStr = ".mars.grid.sina.com.cn"; 
        try {
            for (int i = 0; i < 2; i++) {
                int port = 7100;
                String host = "rm" + port + hostStr;
                System.out.println("new connection host:" + host + ", port:" + port);
                JRedisClient  jredis = new JRedisClient(host, port, "password", 0);
                list.add(jredis);
            }
            for (int i = 0; i < 18; i++) {
                int port = 7100;
                String host = "rs" + port + hostStr;
                System.out.println("new connection host:" + host + ", port:" + port);
                JRedisClient jredis = new JRedisClient(host, port, "password", 0);
                list.add(jredis);
            }
            
            for (int port = 7201; port <= 7216; port++) {
                String host = "rm" + port + hostStr;
                System.out.println("new connection host:" + host + ", port:" + port);
                JRedisClient  jredis = new JRedisClient(host, port, "password", 0);
                list.add(jredis);
            }
            
            for (int port = 7301; port <= 7316; port++) {
                String host = "rm" + port + hostStr;
                System.out.println("new connection host:" + host + ", port:" + port);
                JRedisClient  jredis = new JRedisClient(host, port, "password", 0);
                list.add(jredis);
            }

            /*
             *  test body ------------------------
             */
            int minutes = 10;
            for (int i = 0; i < 1000; i++) {
                System.out.println("begin time:" + new Date());
                for (int j = 0; j < list.size(); j++) {
                    try {
                        JRedis jredis = list.get(j);
                        System.out.println("ping... i:" + i + ", j:" + j);
                        jredis.ping();
                    } catch (RedisException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
                /* 
                 * sleep 
                 */
                try {
                    Thread.sleep(minutes * 60 * 1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                minutes *= 10;
            }
        } finally {
            for (JRedis jredis : list) {
                jredis.quit();
            }
        }
    }

}

/*
javac -classpath ".:commons-logging-1.1.1.jar:jredis-1.0-rc2.jar" JRedisTimeOutTest.java

java -classpath ".:commons-logging-1.1.1.jar:jredis-1.0-rc2.jar" JRedisTimeOutTest

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

