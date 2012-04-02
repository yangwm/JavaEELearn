/**
 * 
 */
package redis.jedis;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class JedisLoad implements Runnable {
    private static AtomicInteger count = new AtomicInteger();
    private static AtomicInteger errorCount = new AtomicInteger();

    private int fromUid;
    private int toUid;
    private static int testType = 1;
    private static String value1 = "asdfasdfxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxsaaaaaaaaaaaaaaadfsasdfasdfxxxxxxxxxxxxxxxxxxxxxxxxxxxxxasdwdd";
    private static List<Integer> elapseTime = new Vector<Integer>();
    private static String redisServer = "10.210.74.77";
    private static int redisPort = 6380;

    public JedisLoad(int from, int to) {
        this.fromUid = from;
        this.toUid = to;
    }

    public void run() {
        Jedis jedis = new Jedis(redisServer, redisPort);
        for (int i = fromUid; i < toUid; i++) {
            runJdbc(jedis, i);
        }
    }

    public void runJdbc(Jedis jedis, int key2) {
        long n1 = System.nanoTime();
        String key = String.valueOf(key2);
        try {
            if (testType == 1) {
                jedis.set(key, value1);
            } else {
                jedis.get(key);
            }
            count.incrementAndGet();
        } catch (JedisException error) {
            /*
             * if (error.getCommand() == Command.PING) { System.out.format(
             * "I'll need that password!  Try again with password as command line arg for this program.\n"
             * ); }
             */
            errorCount.incrementAndGet();
            /*
             * } catch (ClientRuntimeException problem) {
             * errorCount.incrementAndGet(); System.out.format("%s\n",
             * problem.getMessage());
             */
        }
        long n2 = System.nanoTime();
        // in ms
        elapseTime.add((int) (n2 - n1) / 1000000);
    }

    public static int getCount() {
        return count.intValue();
    }

    public static void main(String[] args) {
        int threads = 1;
        int range = 10 * 1000; // 10 * 1000 * 1000;
        if (args != null) {
            if (args.length > 0) {
                threads = Integer.parseInt(args[0]);
                testType = Integer.parseInt(args[1]);
            }

            if (args.length >= 3) {
                redisServer = args[2];
            }
            if (args.length >= 4) {
                redisPort = Integer.parseInt(args[3]);
            }

            if (args.length >= 5) {
                value1 = args[4];
            }
            if (args.length >= 6) {
                range = Integer.parseInt(args[5]);
                System.out.println("range: " + range);
            }
        }

        System.out.println("Len: " + value1.length());
        System.out.println("Threads " + threads);
        System.out.println("TestType " + testType);
        for (int i = 0; i < threads; i++) {
            new Thread(new JedisLoad(i * range, (i + 1) * range)).start();
        }

        for (int i = 0; i < 1000; i++) {
            try { Thread.sleep(5 * 1000); } catch (InterruptedException e) { } 
            long totalTime = 0L;
            for (int j = 0; j < elapseTime.size(); j++) {
                totalTime += elapseTime.get(i);
            }
            
            System.out.println("count: " + count + ", errorCount: " + errorCount 
                    + ", totalTime:" + totalTime + ", consume Time:" + totalTime/elapseTime.size());
        }
    }
}
