/**
 * 
 */
package handlersocket.ndca;

import java.io.IOException;
import java.util.List;

import jp.ndca.handlersocket.HandlerSocket;
import jp.ndca.handlersocket.HandlerSocketResult;

/**
 * 
 * @author yangwm Dec 17, 2011 5:45:56 PM
 */
public class NdcaHandlerSocketSample {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        HandlerSocket hs = new HandlerSocket();
        hs.open("10.75.18.136", 9999);
        
        
        long id = 999L;
        //long id = 149999999L; // 没有的key值，将没有改变数据  

        String indexId = "1";
        String db = "counter";
        String table = "count";
        String index = "PRIMARY";
        String columns = "count,count1";
        
        System.out.println("open index.");
        hs.command().openIndex(indexId, db, table, index, columns);
        List<HandlerSocketResult> results = hs.execute();
        System.out.println("result.");
        for(HandlerSocketResult result : results){
                System.out.println("\t" + result.toString());
        }

        String[] keys = new String[]{String.valueOf(id)};
        hs.command().find(indexId, keys, "<=", "3", "0");
        results = hs.execute();
        System.out.println("result.");
        for(HandlerSocketResult result : results){
                System.out.println("\t" + result.toString());
        }
        
        //hs.close();
    }

    static class Notice {

        /**
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            HandlerSocket hs = new HandlerSocket();
            hs.open("10.75.21.160", 9999);
            
            
            long id = 2L;
            //long id = 149999999L; // 没有的key值，将没有改变数据  

            String indexId = "1";
            String db = "test";
            String table = "tnotice_1000";
            String index = "PRIMARY";
            String columns = "content";
            
            System.out.println("open index.");
            hs.command().openIndex(indexId, db, table, index, columns);
            List<HandlerSocketResult> results = hs.execute();
            System.out.println("result.");
            for(HandlerSocketResult result : results){
                    System.out.println("\t" + result.toString());
            }
            
            String[] keys = { "NdcaHandlerSocketSample abc" };
            hs.command().insert(indexId, keys);
            results = hs.execute();
            System.out.println("result.");
            for(HandlerSocketResult result : results){
                    System.out.println("\t" + result.toString());
            }
            
            keys = new String[]{String.valueOf(id)};
            hs.command().find(indexId, keys, "<=", "3", "0");
            results = hs.execute();
            System.out.println("result.");
            for(HandlerSocketResult result : results){
                    System.out.println("\t" + result.toString());
            }
        }

    }
    
}
