package redis.jredis;


import org.apache.log4j.Logger;
import org.jredis.JRedis;
import org.jredis.RedisException;
import org.jredis.ri.alphazero.JRedisService;
import org.jredis.ri.alphazero.support.DefaultCodec;

/**
 * Redis Test example  
 * 
 * @author yangwm Nov 17, 2010 3:21:36 PM
 */
public class JRedisReconnectTest {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(JRedisReconnectTest.class);
    
    public static final String host = "10.210.74.77";
    public static final int port = 6379;

    
    public static void main(String[] args) throws InterruptedException, RedisException {
        log.info("begin");
        
        JRedis jredis = new JRedisService(host, port, "password", 13, 2);
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(3000);
            byte[] result = jredis.get("yangwm_test");
            log.info("result:" + (result != null ? DefaultCodec.toStr(result) : result));
        }
        
        jredis.quit();
        log.info("end");
    }
    
}

/*


*/

