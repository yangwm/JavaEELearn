/**
 * 
 */
package distributed.cxf;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * 基于Cxf实现的客户端
 * 
 * @author yangwm Aug 4, 2010 4:03:46 PM
 */
public class ClientCxf {

    public static void main(String[] args) throws Exception {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(BusinessCxf.class);
        factory.setAddress("http://localhost:9527/BusinessService");
        BusinessCxf business = (BusinessCxf) factory.create();
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
Aug 4, 2010 4:38:33 PM org.apache.cxf.service.factory.ReflectionServiceFactoryBean buildServiceFromClass
INFO: Creating Service {http://cxf.distributed/}BusinessCxfService from class distributed.cxf.BusinessCxf
cxf yagwm
Server response：cxf yagwm
serverquit
Aug 4, 2010 4:38:52 PM org.apache.cxf.phase.PhaseInterceptorChain doDefaultLogging
WARNING: Interceptor for {http://cxf.distributed/}BusinessCxfService#{http://cxf.distributed/}echo has thrown exception, unwinding now
org.apache.cxf.interceptor.Fault: Could not send Message.
    at org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:64)
    at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:243)
    at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:487)
    at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:313)
    at org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:265)
    at org.apache.cxf.frontend.ClientProxy.invokeSync(ClientProxy.java:73)
    at org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:124)
    at $Proxy34.echo(Unknown Source)
    at distributed.cxf.ClientCxf.main(ClientCxf.java:27)
......
Client quit!

*/
