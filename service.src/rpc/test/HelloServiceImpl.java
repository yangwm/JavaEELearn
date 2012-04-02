/**
 * 
 */
package rpc.test;

/**
 * HelloServiceImpl
 * 
 * @author william.liangf
 */
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        System.out.println("HelloServiceImpl from client name: " + name);
        return "Hello" + name;
    }
}
