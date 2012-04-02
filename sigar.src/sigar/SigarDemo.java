/**
 * 
 */
package sigar;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * sigar简单实用样例
 * 
 * @author yangwm in Mar 16, 2010 5:28:16 PM
 */
public class SigarDemo {

    /**
     * create by yangwm in Mar 16, 2010 5:28:21 PM
     * @param args
     * @throws SigarException 
     */
    public static void main(String[] args) throws SigarException {
        Sigar sigar = new Sigar(); 

        CpuPerc cpuCerc = sigar.getCpuPerc(); 
        System.out.println("*****当前CPU使用情况*****"); 
        System.out.println("总使用率: " + cpuCerc.getCombined() * 100 + "%"); 
        System.out.println("用户使用率(user): " + cpuCerc.getUser() * 100 + "%"); 
        System.out.println("系统使用率(sys): " + cpuCerc.getSys() * 100 + "%"); 
        System.out.println("优先进程占用(nice): " + cpuCerc.getNice() * 100 + "%"); 
        System.out.println("当前空闲率(idel): " + cpuCerc.getIdle() * 100 + "%"); 
        
        Mem mem = sigar.getMem(); 
        System.out.println("*****当前内存使用情况*****"); 
        System.out.println("内存总量：" + mem.getTotal() / 1024 / 1024 + "M"); 
        System.out.println("已使用内存：" + mem.getUsed() /1024 / 1024 + "M"); 
        System.out.println("剩余内存：" + mem.getFree() / 1024 / 1024 + "M"); 
    }

}

/*
*****当前CPU使用情况*****
总使用率: 1.6%
用户使用率(user): 0.0%
系统使用率(sys): 1.6%
优先进程占用(nice): 0.0%
当前空闲率(idel): 98.4%
*****当前内存使用情况*****
内存总量：2038M
已使用内存：1379M
剩余内存：658M

*/
