/**
 * 
 */
package race.springrmi;

import java.rmi.RemoteException;

/**
 * 业务功能对外暴露的接口
 * 
 * @author yangwm Jul 16, 2010 10:04:21 AM
 */
public interface BusinessSpringRmi {
    
    
    /**
     * @return
     * @throws RemoteException
     */
    public long[] getResult(String[] keys) throws RemoteException;
    
}
