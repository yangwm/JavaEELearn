/**
 * 
 */
package distributed.springrmi.impl;

import distributed.springrmi.BusinessSpringRmi;

/**
 * 业务功能实现类
 * 
 * @author yangwm Jul 16, 2010 10:07:42 AM
 */
public class BusinessSpringRmiImpl implements BusinessSpringRmi {

    /*
     * (non-Javadoc)
     * 
     * @see distributed.springrmi.BusinessSpringRmi#echo(java.lang.String)
     */
    public String echo(String message) {
        System.out.println("Message from client: " + message);
        if ("quit".equalsIgnoreCase(message.toString())) {
            System.out.println("Client will be quit!");
        } else if ("serverquit".equalsIgnoreCase(message.toString())) {
            System.out.println("Server will be shutdown!");
            System.exit(0);
        }
        return "Server response：" + message;
    }

}
