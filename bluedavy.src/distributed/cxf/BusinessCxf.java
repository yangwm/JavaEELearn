/**
 * 
 */
package distributed.cxf;

import java.rmi.RemoteException;

import javax.jws.WebService;

/**
 * 业务功能对外暴露的接口
 * 
 * @author yangwm Jul 16, 2010 10:04:21 AM
 */
@WebService
public interface BusinessCxf {
    
    
    /**
     * 显示客户端提供的信息，并返回
     * 
     * @param message
     * @return
     * @throws RemoteException
     */
    public String echo(String message) throws RemoteException;
    
}
