/**
 * 
 */
package race;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * 
 * @author yangwm Jun 5, 2011 1:03:05 AM
 */
public class MaxNBench {
    
    private static Random rand = new Random();
    
    private static final long[] getRandom200() {
        long[] _values = new long[200];
        for (int i = 0; i < 200; i++) {
            _values[i] = rand.nextInt(Integer.MAX_VALUE);
        }
        Arrays.sort(_values);
        return _values;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        long beginTime = System.nanoTime();
        long[] result = getRandom200();
        for (int i = 0; i < 2000; i++) {
            long[] values = getRandom200();
            result = MaxN.merge(result, values, result.length);
        }
        long cosumeTime = System.nanoTime() - beginTime;
        System.out.println("MaxNPrimitiveAlgorithm Array max n cosume time " + (cosumeTime/1000000));
        System.out.println("result:" + Arrays.toString(result));
    }
    
}
