package redis.jredis;


import java.util.ArrayList;
import java.util.List;

import org.jredis.ClientRuntimeException;
import org.jredis.JRedis;
import org.jredis.ProviderException;
import org.jredis.RedisException;
import org.jredis.ri.alphazero.JRedisClient;

/**
 * Redis Test example  
 * 
 * @author yangwm Nov 17, 2010 3:21:36 PM
 */
public class JRedisTest {
    public static final String host = "10.210.74.77";
    public static final int port = 6380;

    public static void main(String[] args) {
        int i = 0;
        try {
            // key in your DB 0. 
            List<JRedis> list = new ArrayList<JRedis>();
            for (i = 0; i < 2000; i++) {
                JRedis  jredis = new JRedisClient(host, port, "password", 0);
                jredis.ping();
                list.add(jredis);
            }
            
            for (JRedis jredis : list) {
                jredis.quit();
            }
        } catch (RedisException error) {
            error.printStackTrace();
        } catch (ProviderException bug) {
            bug.printStackTrace();
        } catch (ClientRuntimeException problem) {
            problem.printStackTrace();
        }
        
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("client number=" + i);
    }
    
}

/*

javac -classpath ".:commons-logging-1.1.1.jar:jredis-1.0-rc2.jar" JRedisTest.java

java -classpath ".:commons-logging-1.1.1.jar:jredis-1.0-rc2.jar" JRedisTest


[root@TC_OpenAPI_mblog_14_117 redis-2.0.4]# ./redis-cli -h 10.73.11.210 -p 7100 info 
redis_version:2.0.4
redis_git_sha1:00000000
redis_git_dirty:0
arch_bits:64
multiplexing_api:epoll
process_id:22017
uptime_in_seconds:4042
uptime_in_days:0
connected_clients:9561
connected_slaves:0
blocked_clients:0
used_memory:6058736
used_memory_human:5.78M
changes_since_last_save:0
bgsave_in_progress:0
last_save_time:1294306603
bgrewriteaof_in_progress:0
total_connections_received:51105
total_commands_processed:397898
expired_keys:0
hash_max_zipmap_entries:64
hash_max_zipmap_value:512
pubsub_channels:0
pubsub_patterns:0
vm_enabled:0
role:master
[root@TC_OpenAPI_mblog_14_117 redis-2.0.4]#

*/

