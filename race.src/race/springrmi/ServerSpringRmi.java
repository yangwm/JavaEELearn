/**
 * 
 */
package race.springrmi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基于Spring RMI实现的服务器端
 * 
 * @author yangwm Aug 3, 2010 2:49:01 PM 
 */
public class ServerSpringRmi {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("distributed/springrmi/server.xml");
        System.out.println("Server has been started");
    }

}
/*
Server has been started
Message from client: springrmi abc
Message from client: serverquit
Server will be shutdown!

*/

/*
java -Xms64M -Xmx64M -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime jvm.heap.HeapTest

*/
