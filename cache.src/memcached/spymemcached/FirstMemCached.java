/**
 * 
 */
package memcached.spymemcached;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

/**
 * MemCached Basic usage
 * 
 * @author yangwm Oct 7, 2010 11:42:22 PM
 */
public class FirstMemCached {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        MemcachedClient c = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));

        // Store a value (async) for one hour
        c.set("yangwmKey", 3600, "testMemcached");
        // Retrieve a value.
        Object myObject = c.get("yangwmKey");
        System.out.println(myObject);
    }

}
