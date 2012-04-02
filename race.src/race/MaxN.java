/**
 * 
 */
package race;

import java.util.Arrays;

/**
 * 
 * 
 * @author yangwm Jun 5, 2011 12:27:01 AM
 */
public class MaxN {

    /**
     * 
     */
    public static long[] merge(long left[], long right[], int n) {
        long[] result = new long[n];

        int leftPos = left.length - 1;
        int rightPos = right.length - 1;
        int resultPos = 0;
        while (leftPos > -1 && rightPos > -1 && resultPos < result.length) {
            /* Merge sorted sublists */
            if (left[leftPos] >= right[rightPos]) {
                result[resultPos++] = left[leftPos--];
            } else {
                result[resultPos++] = right[rightPos--];
            }
        }
        
        return result;
    }
    
    /**
     * 
     */
    public static long[] merge2(long left[], long right[], int n) {
        long[] result = new long[n];

        int leftPos = 0;
        int rightPos = 0;
        int resultPos = 0;
        while (leftPos < left.length && rightPos < right.length && resultPos < result.length) {
            /* Merge sorted sublists */
            if (left[leftPos] >= right[rightPos]) {
                result[resultPos++] = left[leftPos++];
            } else {
                result[resultPos++] = right[rightPos++];
            }
        }
        
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        long[] left = new long[] { 3, 5, 5, 6, 7, 8, 9, 11, 18, 20 };
        long[] right = new long[] { 2, 5, 5, 5, 7, 9, 11, 18, 18, 22 };
        System.out.println("------MaxNPrimitiveAlgorithm Array merge n-------");
        long[] result = merge(left, right, left.length);
        System.out.println(Arrays.toString(result));
        
        left = new long[] { 20, 18, 11, 9, 8, 7, 6, 5, 5, 3 };
        right = new long[] { 22, 18, 18, 11, 9, 7, 5, 5, 5, 2 };
        System.out.println("------MaxNPrimitiveAlgorithm Array merge2 n-------");
        result = merge2(left, right, left.length);
        System.out.println(Arrays.toString(result));
    }

}
