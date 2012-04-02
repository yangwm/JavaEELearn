/**
 * 
 */
package race.springrmi.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import race.MaxN;
import race.springrmi.BusinessSpringRmi;

/**
 * 业务功能实现类
 * 
 * @author yangwm Jul 16, 2010 10:07:42 AM
 */
public class BusinessSpringRmiImpl implements BusinessSpringRmi {
    
    private static int maxReadId = 5000000;
    private static int multiSetSize = 200;
    
    private int begin = 0;
    Map<String, long[]> map = new HashMap<String, long[]>();
    private Random rand = new Random();
    
    public int getBegin() {
        return begin;
    }
    public void setBegin(int begin) {
        this.begin = begin;
    }

    public BusinessSpringRmiImpl() {
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
            for (int i = begin; i < maxReadId; i += 3) {
                tempMap.put(String.valueOf(i), getRandom200());
                //System.out.println(tempMap.get(String.valueOf(i)));
                
                if (i % (maxReadId / 30) == 0) {
                    System.out.println(i + " ok");
                }
            }
            map = tempMap;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public long[] getResult(String[] keys) {
        //System.out.println("getResult200 keys:" + Arrays.toString(keys));
        int keysLength = keys.length;
        
        long[] result = map.get(keys[0]);
        for (int i = 1; i < keysLength; i++) {
            long[] values = map.get(keys[i]);
            result = MaxN.merge(result, values, 200);
        }
        //System.out.println("getResult200 result:" + Arrays.toString(result));
        return result;
    }

//    public long[] getResult(String[] keys) {
//        //System.out.println("getResult200 keys:" + Arrays.toString(keys));
//        int keysLength = keys.length;
//        
//        long[] values = map.get(keys[0]);
//        long[] result = Arrays.copyOf(values, values.length);
//        for (int i = 1; i < keysLength; i++) {
//            values = map.get(keys[i]);
//            result = MaxN.merge(result, values, 200);
//        }
//        //System.out.println("getResult200 result:" + Arrays.toString(result));
//        return result;
//    }
    
}
