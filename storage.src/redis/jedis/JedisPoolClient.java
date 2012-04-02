/**
 * 
 */
package redis.jedis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author yangwm Apr 30, 2011 11:44:45 PM
 */
public class JedisPoolClient {
    
    String host;
    int port;
    String password;
    int database;
    int connCount;

    private void init() {
        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxActive(connCount);
        cfg.setMaxIdle(connCount);
        cfg.setMaxIdle(10);
        cfg.setMaxWait(500);
        cfg.lifo = false;
        
        JedisPool pool = new JedisPool(cfg, host, port);
    }
    
    public JedisPoolClient() {
        // 
    }
    
}
