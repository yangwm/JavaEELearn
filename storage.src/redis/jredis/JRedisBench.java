/**
 * 
 */
package redis.jredis;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jredis.ClientRuntimeException;
import org.jredis.JRedis;
import org.jredis.ProviderException;
import org.jredis.RedisException;
import org.jredis.connector.ConnectionSpec;
import org.jredis.ri.alphazero.JRedisClient;
import org.jredis.ri.alphazero.JRedisService;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;

/**
 * Note this program will set a (hopefully non-coliding!) key in your DB 13.
 * 
 * @author yangwm Nov 17, 2010 6:28:35 PM
 */
public class JRedisBench {
    public static final String key = "jredis::examples::HelloAgain::message";
    public static final String host = "10.210.74.77";
    public static final int port = 6380;

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName(host);
            
            // 1 - get the default connection spec
            //
            ConnectionSpec connectionSpec = DefaultConnectionSpec.newSpec();
            
            // 2 - customize it
            // here we're demonstrating the full set of parameters -- obviously you can just set what you need
            // but DO NOTE that the SO_PREF_XXX properties of TCP sockets must be defined as a set.  See
            // ConnectionSpec for javadoc details.
            //
            // Here we are spec'ing a connection that is NOT kept alive, and obviously is really keen on making sure
            // we connect as fast as possible.  This is a good connectionspec for disposable JRedisClients that are used
            // to issue a few commands and then discarded.  We're minimizing the connection overhead cost.
            
            connectionSpec
                // to be or not to be -- you decide
                //
//                .setSocketFlag(SocketFlag.SO_KEEP_ALIVE, false)             // DO NOT keep socket allive

                // connect retries on connection breaks
                //
                .setReconnectCnt(2)                                         // reconnect attempts set to 2 retries

                // TCP algorithm preferences
                //
//                .setSocketProperty(SocketProperty.SO_PREF_CONN_TIME, 0)     // connection time is highester pref
//                .setSocketProperty(SocketProperty.SO_PREF_LATENCY, 1)       // latency is 2nd pref
//                .setSocketProperty(SocketProperty.SO_PREF_BANDWIDTH, 2) // bandwith is 3rd pref
            
                // TCP buffer sizes -- more than likely your platform's default settings are quite large
                // but if you are itching to try your own settings, please do.  Remember:  connections
                // will use whatever is the larger value: you OS's TCP buffer sizes or your ConnectionSpecs
                // so you can NOT use these settings to shrink the SND and RCV buffer sizes.
                //
//                .setSocketProperty(SocketProperty.SO_RCVBUF, 1024 * 128)  // set RCV buffer to 128KB
//                .setSocketProperty(SocketProperty.SO_SNDBUF, 1024 * 128) // set SND buffer to 128KB
                
                // obviously we can still set the basic props as well ..
                //
                .setAddresses(new InetAddress[]{address})
                .setPort(port)
                .setDatabase(13);
            
            // finally - use it to create the JRedisClient instance
            //
            JRedis jredis = new JRedisClient(connectionSpec);
            new JRedisService(connectionSpec,10);
            jredis.ping();
            long begin = System.currentTimeMillis();
            
            /**/
            for (int i = 0; i < 100000; i++) {
                jredis.set("" + i, key + i);
            }
            for (int i = 100000 - 1; i > 100000 - 10; i--) {
                System.out.println(new String(jredis.get("" + i)));
            }
            
            
            /*
            for (int i = 100000 - 1; i >= 0; i--) {
                jredis.get("" + i);
            }
            */
            
            /*
            for (int i = 0; i < 100000; i++) {
                jredis.expire("" + i, 3); // set a time to live in seconds on a key 
            }
            */
            
            /*
            String listKey = key + "list";
            for (int i = 0; i < 100000; i++) {
                jredis.lpush(listKey, key + i);
            }
            
            for (byte[] v : jredis.lrange(listKey, 0, 10)) {
                System.out.println(new String(v));
            }
            for (byte[] v : jredis.lrange(listKey, 50000, 10)) {
                System.out.println(new String(v));
            }
            for (byte[] v : jredis.lrange(listKey, 99990, 10)) {
                System.out.println(new String(v));
            }
            */

            long end = System.currentTimeMillis();
            System.out.println("100000 times consume " + (end - begin) + " ms");
            jredis.quit();
        } catch (RedisException error) {
            error.printStackTrace();
        } catch (ProviderException bug) {
            System.out.format("Oh no, an 'un-documented feature':  %s\nKindly report it.", bug.getMessage());
        } catch (ClientRuntimeException problem) {
            System.out.format("%s\n", problem.getMessage());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}

/*
set 100000 times consume 73531 ms

get 100000 times consume 34047 ms

lpush 10000 times consume 7422 ms

lpush 100000 times consume 76593 ms

*/
