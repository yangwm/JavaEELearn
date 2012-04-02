/**
 * 
 */
package race;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author yangwm Jun 8, 2011 10:01:42 AM
 */
public class InitDataMain implements Runnable {
    public static AtomicInteger count = new AtomicInteger();
    public static AtomicInteger errorCount = new AtomicInteger();
    private static List<Integer> elapseTime = new Vector<Integer>();
    
    // type: 0 - insert, 1 - getMulti
    private static int testType = 0;
    private static int maxReadId = 500;
    private static String writeOffset = "";
    private static int multiSetSize = 200;
    
    Map<String, long[]> map = new HashMap<String, long[]>();
    private Random rand = new Random();
    
    public InitDataMain() {
        initData();
    }

    private final long[] getRandom200() {
        long[] _values = new long[200];
        for (int i = 0; i < 200; i++)
            _values[i] = rand.nextInt(Integer.MAX_VALUE);
        
        Arrays.sort(_values);
        //System.out.println(Arrays.toString(_values));
        return _values; //VectorNioUtil.toBytes(_values, 200);
        
    }
    public void initData() {
        try {
            Map<String, long[]> tempMap = new HashMap<String, long[]>();
            for (int i = 0; i < maxReadId; i++) {
                tempMap.put(writeOffset + i, getRandom200());
                //System.out.println(tempMap.get(writeOffset + i));
            }
            map = tempMap;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public long[] getResult200() {
        long[] values = map.get(String.valueOf(rand.nextInt(maxReadId)));
        long[] result = Arrays.copyOf(values, values.length);
        for (int i = 1; i < multiSetSize; i++) {
            values = map.get(String.valueOf(rand.nextInt(maxReadId)));
            result = MaxN.merge(result, values, 200);
        }
        return result;
    }
    
    public void run() {
        while (true) {
            long n1 = System.nanoTime();

            long[] result = getResult200();
            count.incrementAndGet();
            //System.out.println(Arrays.toString(result));
            
            long n2 = System.nanoTime();
            elapseTime.add((int)(n2 - n1) / (1000 * 1000));
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        int thread = 4;
        System.out.println("Thread: " + thread);
        
        for (int i = 0; i < thread; i++) {
            InitDataMain test = new InitDataMain();
            Thread t = new Thread(test);
            t.start();
        }

        TestUtil.printStat(count, errorCount, elapseTime);
    }

}

/*
Thread: 4
16:15:58,105 [main] INFO  [TestUtil.printStat(117)]  - 
OK /ERR: 1492/0 (Uptime: 0, 0)
AVG/CUR/MAX RPS: 1492000/0/0
<    1ms 69.2% 69.2% (1034)
<    3ms 94.0% 24.8% (371)
<    5ms 94.9% 0.8% (13)
<   10ms 97.1% 2.2% (33)
<   50ms 100.0% 2.8% (43)
HEAP:  38.84MB of 417.81 MB (9.3%) used

16:16:01,120 [main] INFO  [TestUtil.printStat(117)]  - 
OK /ERR: 13295/0 (Uptime: 3, 0)
AVG/CUR/MAX RPS: 4402/3909/3909

16:16:04,121 [main] INFO  [TestUtil.printStat(117)]  - 
OK /ERR: 24851/0 (Uptime: 6, 0)
AVG/CUR/MAX RPS: 4127/3850/3909

...
...
*/

