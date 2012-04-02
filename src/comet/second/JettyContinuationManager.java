package comet.second;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.mortbay.util.ajax.Continuation;

/**
 * Continuation Manager
 * 
 * @author yangwm Sep 28, 2010 11:47:02 AM 
 */
public class JettyContinuationManager {
	
	private static ConcurrentMap<String, Continuation> map = new ConcurrentHashMap<String, Continuation>();
	
	public static void add(String key, Continuation continuation) {
		map.put(key, continuation);
	}
	
	public static void clear(String key) {
		map.remove(key);
	}
	
	public static Continuation get(String key){
		return map.get(key);
	}

}
