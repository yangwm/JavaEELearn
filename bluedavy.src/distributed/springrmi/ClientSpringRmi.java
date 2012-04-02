/**
 * 
 */
package distributed.springrmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基于Spring RMI实现的客户端
 * 
 * @author yangwm Aug 3, 2010 2:48:56 PM 
 */
public class ClientSpringRmi {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("distributed/springrmi/client.xml");
        BusinessSpringRmi business = (BusinessSpringRmi) ac.getBean("businessService");
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String command = systemIn.readLine();
            try {
                System.out.println(business.echo(command));
            } catch (Exception e) {
                // IGNORE
            }
            if (command == null || "quit".equalsIgnoreCase(command.trim())
                    || "serverquit".equalsIgnoreCase(command.trim())) {
                System.out.println("Client quit!");
                System.exit(0);
            }
        }
    }

}
/*
springrmi abc
Server response：springrmi abc
serverquit
Client quit!

*/
