/**
 * 
 */
package jetty.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * simple jetty xml configure 
 * 
 * @author yangwm Oct 21, 2010 3:14:15 PM
 */
public class SimpleXmlConfigure {

    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
    
    public static void printString(Object x) {
        System.out.println(x);
    }

    public static Map<Object, Object> staticPrint() {
        return new HashMap<Object, Object>();
    }

    public void doAdd() {
        System.out.println("doStringAdd()");
    }
    
    public void doAdd(String host, int port) {
        System.out.println("doStringAdd(String " + host + ", int " + port + ")");
    }


}
