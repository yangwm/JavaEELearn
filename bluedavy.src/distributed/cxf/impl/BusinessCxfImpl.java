/**
 * 
 */
package distributed.cxf.impl;

import javax.jws.WebService;

import distributed.cxf.BusinessCxf;

/**
 * 业务功能实现类
 * 
 * @author yangwm Jul 16, 2010 10:07:42 AM
 */
@WebService(serviceName="BusinessService",endpointInterface="distributed.cxf.BusinessCxf")
public class BusinessCxfImpl implements BusinessCxf {

    /* (non-Javadoc)
     * @see distributed.cxf.BusinessCxf#echo(java.lang.String)
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
