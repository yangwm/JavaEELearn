/**
 * 
 */
package distributed.cxf;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import distributed.cxf.impl.BusinessCxfImpl;

/**
 * 基于Cxf实现的服务器端
 * 
 * @author yangwm Aug 4, 2010 3:53:43 PM
 */
public class ServerCxf {

    public static void main(String[] args) throws Exception {
        BusinessCxf service = new BusinessCxfImpl();
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
        svrFactory.setServiceClass(BusinessCxf.class);
        svrFactory.setAddress("http://localhost:9527/BusinessService");
        svrFactory.setServiceBean(service);
        svrFactory.create();
    }

}

/*
Aug 4, 2010 4:38:19 PM org.apache.cxf.service.factory.ReflectionServiceFactoryBean buildServiceFromClass
INFO: Creating Service {http://cxf.distributed/}BusinessCxfService from class distributed.cxf.BusinessCxf
Aug 4, 2010 4:38:20 PM org.apache.cxf.endpoint.ServerImpl initDestination
INFO: Setting the server's publish address to be http://localhost:9527/BusinessService
0 [main] INFO org.mortbay.log - Logging to org.slf4j.impl.SimpleLogger(org.mortbay.log) via org.mortbay.log.Slf4jLog
15 [main] INFO org.mortbay.log - jetty-6.1.21
172 [main] INFO org.mortbay.log - Started SelectChannelConnector@localhost:9527
Message from client: cxf yagwm
Message from client: serverquit
Server will be shutdown!

*/
