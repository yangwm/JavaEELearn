/**
 * 
 */
package handlersocket.hs4j;

import java.net.InetSocketAddress;
import java.sql.ResultSet;

import com.google.code.hs4j.FindOperator;
import com.google.code.hs4j.HSClient;
import com.google.code.hs4j.IndexSession;
import com.google.code.hs4j.ModifyStatement;
import com.google.code.hs4j.impl.HSClientImpl;

/**
 * http://code.google.com/p/hs4j/
 * http://www.cxyclub.cn/n/11267/
 * 
 * @author yangwm Nov 15, 2011 12:22:29 AM
 */
public class HSClientSample {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        HSClient hsClient = new HSClientImpl("10.75.18.136", 9999);
        hsClient.setOpTimeout(1000);
        System.out.println("healConnectionInterval:" + hsClient.getHealConnectionInterval()
                + ", isAllowAutoReconnect:" + hsClient.isAllowAutoReconnect()
                + ", opTimeout:" + hsClient);

        /*
         * 
         */
        long id = 999L;
        //long id = 149999999L; 没有的key值，将没有改变数据  

        String db = "counter";
        String table = "count";
        String[] columns = new String[]{ "count" };
        IndexSession writeIndexSession = hsClient.openIndexSession(db, table, "PRIMARY", columns);

        String[] keys = { String.valueOf(id) };
        String[] values = { String.valueOf(id % 100)};
        int result = writeIndexSession.update(keys, values, FindOperator.EQ);

        /* 不推荐使用方式 
        int indexId = 1;
        IndexSession indexSession = hsClient.openIndexSession(indexId, db, table, "PRIMARY", columns);
        int result = hsClient.update(indexId, keys, values, FindOperator.EQ);
        */

        db = "counter";
        table = "count";
        columns = new String[]{ "count", "count1" };
        IndexSession readIndexSession = hsClient.openIndexSession(db, table, "PRIMARY", columns);
        
        keys = new String[] { String.valueOf(id) };
        ResultSet rs = readIndexSession.find(keys, FindOperator.LE, 3, 0);
        int count = 0;
        int count1 = 0;
        if (rs.next()){
            count = rs.getInt("count");
            count1 = rs.getInt("count1");
        }
        System.out.println(id + "'s after update count:" + count + ", " + count1);
        hsClient.shutdown();
    }
    
    static class Incr {
        /**
         * @param args
         * @throws Exception 
         */
        public static void main(String[] args) throws Exception {
            //HSClient hsClient = new HSClientImpl(new InetSocketAddress("10.75.18.136", 9999));
            HSClient hsClient = new HSClientImpl(new InetSocketAddress("10.75.18.136", 9999), 2);
            System.out.println("healConnectionInterval:" + hsClient.getHealConnectionInterval()
                    + ", isAllowAutoReconnect:" + hsClient.isAllowAutoReconnect());

            /*
             * 
             */
            long id = 999L;
            //long id = 149999999L; // 没有的key值，将没有改变数据  

            String db = "counter";
            String table = "count";
            String[] wcolumns = new String[]{ "count" };
            IndexSession writeIndexSession = hsClient.openIndexSession(db, table, "PRIMARY", wcolumns);
            
            String[] rcolumns = new String[]{ "count", "count1" };
            IndexSession readIndexSession = hsClient.openIndexSession(db, table, "PRIMARY", rcolumns);
            
            String[] keys = new String[] { String.valueOf(id) };
            ResultSet rs = readIndexSession.find(keys);
            int count = 0;
            int count1 = 0;
            if (rs.next()){
                System.out.println(id + "'s have column ");
                count = rs.getInt("count");
                count1 = rs.getInt("count1");
            }
            System.out.println(id + "'s before update count:" + count + ", " + count1);
            
            keys = new String[] { String.valueOf(id) };
            String[] values = { String.valueOf(id % 100)};
            ModifyStatement stmt = writeIndexSession.createStatement();
            stmt.setInt(1, 10);
            int result = stmt.incr(keys, FindOperator.EQ);
            System.out.println(id + "'s incr result=" + result); // 149999999's incr result=0 or 999's incr result=1 
            
            keys = new String[] { String.valueOf(id) };
            rs = readIndexSession.find(keys);
            count = 0;
            count1 = 0;
            if (rs.next()){
                System.out.println(id + "'s have column ");
                count = rs.getInt("count");
                count1 = rs.getInt("count1");
            }
            System.out.println(id + "'s after update count:" + count + ", " + count1);
            
            hsClient.shutdown();
        }
    }

    static class Notice {

        /**
         * @param args
         */
        public static void main(String[] args) throws Exception {
            HSClient hsClient = new HSClientImpl("10.75.21.160", 9999);
            hsClient.setOpTimeout(1000);
            System.out.println("healConnectionInterval:" + hsClient.getHealConnectionInterval()
                    + ", isAllowAutoReconnect:" + hsClient.isAllowAutoReconnect()
                    + ", opTimeout:" + hsClient);

            /*
             * 
             */
            long id = 2L;
            //long id = 149999999L; 没有的key值，将没有改变数据  

            String db = "test";
            String table = "tnotice_1000";
            String index = "PRIMARY";
            String[] columns = new String[]{ "content" };
            IndexSession writeIndexSession = hsClient.openIndexSession(db, table, index, columns);

            String[] values = { "HSClientTest sabc" };
            boolean result = writeIndexSession.insert(values);
            System.out.println(id + ", result:" + result);
            
            /*
            String[] keys = { String.valueOf(id) };
            String[] values = { String.valueOf(id % 100) };
            int result = writeIndexSession.update(keys, values, FindOperator.EQ);
            */
            
            /*ModifyStatement stmt = writeIndexSession.createStatement();
            stmt.setBytes(1, String.valueOf(id % 100).getBytes());
            int result = stmt.update(keys, FindOperator.EQ);*/

            /* 不推荐使用方式 
            int indexId = 1;
            IndexSession indexSession = hsClient.openIndexSession(indexId, db, table, "PRIMARY", columns);
            int result = hsClient.update(indexId, keys, values, FindOperator.EQ);
            */
            
            IndexSession readIndexSession = hsClient.openIndexSession(db, table, index, columns);
            
            String[] keys = { String.valueOf(id) };
            ResultSet rs = readIndexSession.find(keys, FindOperator.LE, 3, 0);
            byte[] contentBytes = null;
            while (rs.next()) {
                contentBytes = rs.getBytes("content");
                System.out.println(id + "'s after insert content:" + new String(contentBytes));
            }
            hsClient.shutdown();
        }

    }
}
