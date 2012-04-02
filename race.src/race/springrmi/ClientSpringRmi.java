/**
 * 
 */
package race.springrmi;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import race.MaxN;
import race.TestUtil;

/**
 * 基于Spring RMI实现的客户端
 * 
 * @author yangwm Aug 3, 2010 2:48:56 PM 
 */
public class ClientSpringRmi implements Runnable {
    public static AtomicInteger count = new AtomicInteger();
    public static AtomicInteger errorCount = new AtomicInteger();
    private static List<Integer> elapseTime = new Vector<Integer>();
    
    private static int maxReadId = 5000000;
    private static int multiSetSize = 200;
    private Random rand = new Random();;
    
    ApplicationContext ac = new ClassPathXmlApplicationContext("race/springrmi/client.xml");
    BusinessSpringRmi businessService = (BusinessSpringRmi) ac.getBean("businessService");
    BusinessSpringRmi businessService2 = (BusinessSpringRmi) ac.getBean("businessService2");
    BusinessSpringRmi businessService3 = (BusinessSpringRmi) ac.getBean("businessService3");
    
    public void run() {
        while (true) {
            String[] keys = new String[multiSetSize];
            String[] keys2 = new String[multiSetSize];
            String[] keys3 = new String[multiSetSize];
            int keysIdx = 0;
            int keys2Idx = 0;
            int keys3Idx = 0;
            
            for (int i = 0; i < multiSetSize; i++) {
                int value = rand.nextInt(maxReadId);
                int num = value % 3;
                if (num == 0) {
                    keys[keysIdx++] = String.valueOf(value);
                } else if (num == 1) {
                    keys2[keys2Idx++] = String.valueOf(value);
                } else if (num == 2) {
                    keys3[keys3Idx++] = String.valueOf(value);
                }
            }
            
            long n1 = System.nanoTime();

            try {
                long[] result = null;
                if (keysIdx > 0) {
                    result = businessService.getResult(Arrays.copyOf(keys, keysIdx));
                }
                long[] result2 = null;
                if (keys2Idx > 0) {
                    result2 = businessService2.getResult(Arrays.copyOf(keys2, keys2Idx));
                }
                long[] result3 = null;
                if (keys3Idx > 0) {
                    result3 = businessService3.getResult(Arrays.copyOf(keys3, keys3Idx));
                }
                result = MaxN.merge2(result, result2, 200);
                result = MaxN.merge2(result, result3, 200);
                
                count.incrementAndGet();
                //System.out.println(Arrays.toString(result));
            } catch (RemoteException e) {
                errorCount.incrementAndGet();
            }
            
            long n2 = System.nanoTime();
            // in ms
            elapseTime.add((int)(n2 - n1) / 1000000);
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        long[] result = new ClientSpringRmi().business.getResult200();
//        System.out.println(Arrays.toString(result));
        int thread = 4;
        System.out.println("Thread: " + thread);
        
        for (int i = 0; i < thread; i++) {
            ClientSpringRmi test = new ClientSpringRmi();
            Thread t = new Thread(test);
            t.start();
        }

        TestUtil.printStat(count, errorCount, elapseTime);
    }

}
/*
springrmi abc
Server response：springrmi abc
serverquit
Client quit!

*/
