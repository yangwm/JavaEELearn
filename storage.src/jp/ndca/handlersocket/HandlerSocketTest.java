package jp.ndca.handlersocket;

import java.io.IOException;
import java.util.List;

import jp.ndca.handlersocket.HandlerSocket;

import org.junit.Before;
import org.junit.Test;

public class HandlerSocketTest {
        private String host;
        private int port;
        private String id = "1";
        private String db = "hstest";
        private String table ="hstest_table1";
        private String fieldList = "k,v";
        private String index ="PRIMARY";
        private int size = 10000;
        private int loop = 100;
        private boolean verbose = false;

        @Before
        public void setUp() throws Exception {
                this.host = System.getProperty("host");
                this.port = Integer.parseInt(System.getProperty("port"));
                if(System.getProperty("id") != null)
                        this.id = System.getProperty("id");
                if(System.getProperty("db") != null)
                        this.db = System.getProperty("db");
                if(System.getProperty("table") != null)
                        this.table = System.getProperty("table");
                if(System.getProperty("fieldList") != null)
                        this.fieldList = System.getProperty("fieldList");
                if(System.getProperty("index") != null)
                        this.index = System.getProperty("index");
                if(System.getProperty("size") != null)
                        this.size = Integer.parseInt(System.getProperty("size"));
                if(System.getProperty("loop") != null)
                        this.loop = Integer.parseInt(System.getProperty("loop"));
                if(System.getProperty("verbose") != null)
                        this.verbose = System.getProperty("verbose").equalsIgnoreCase("true");
                
                System.out.println("----- setUp -----");
                System.out.println("host = " + host);
                System.out.println("port = " + port);
                System.out.println("id = " + id);
                System.out.println("db = " + db);
                System.out.println("table = " + table);
                System.out.println("fieldList = " + fieldList);
                System.out.println("index = " + index);
                System.out.println("size = " + size);
                System.out.println("loop = " + loop);
                System.out.println("verbose = " + verbose);
                System.out.println();
        }
        
        @Test
        public void testAsciiController() throws IOException{
                long start = System.currentTimeMillis();
                testHandlerSocketInsertAscii();
                System.out.println("process time:" + (System.currentTimeMillis() - start) + "ms");
                System.out.println();
                start = System.currentTimeMillis();
                
                testHandlerSocketFind();
                System.out.println("process time:" + (System.currentTimeMillis() - start) + "ms");
                System.out.println();
                start = System.currentTimeMillis();
                
                testHandlerSocketUpdateAscii();
                System.out.println("process time:" + (System.currentTimeMillis() - start) + "ms");
                System.out.println();
                start = System.currentTimeMillis();
                
                testHandlerSocketDelete();
                System.out.println("process time:" + (System.currentTimeMillis() - start) + "ms");
                System.out.println();
                start = System.currentTimeMillis();
        }
        
        @Test
        public void testKanaController() throws IOException{
                long start = System.currentTimeMillis();
                testHandlerSocketInsertKana();
                System.out.println("process time:" + (System.currentTimeMillis() - start) + "ms");
                System.out.println();
                start = System.currentTimeMillis();
                
                testHandlerSocketFind();
                System.out.println("process time:" + (System.currentTimeMillis() - start) + "ms");
                System.out.println();
                start = System.currentTimeMillis();
                
                testHandlerSocketUpdateKana();
                System.out.println("process time:" + (System.currentTimeMillis() - start) + "ms");
                System.out.println();
                start = System.currentTimeMillis();
                
                testHandlerSocketDelete();
                System.out.println("process time:" + (System.currentTimeMillis() - start) + "ms");
                System.out.println();
                start = System.currentTimeMillis();
        }
        
        public void testHandlerSocketInsertAscii() throws IOException{
                System.out.println("----- testHandlerSocketInsertAscii -----");
                if(verbose)
                        System.out.println(id + "/" + db + "/" + table + "/" + index + "/" + fieldList);
                
                HandlerSocket hs = new HandlerSocket();
                try{
                        System.out.println("open connection.");
                        hs.open(host, port);
                        System.out.println("open index.");
                        hs.command().openIndex(id, db, table, index, fieldList);
                        System.out.println("operations.");
                        for(int i = 0 ; i <= size ; i++){
                                if(i != 0 && i % loop == 0){
                                        System.out.println("\tcommand size : " + hs.getCommandSize() + "byte");
                                        List<HandlerSocketResult> results = hs.execute();
                                        System.out.println("\tresult size : " + hs.getCurrentResponseSize() + "byte");
                                        if(verbose){
                                                System.out.println("result.");
                                                for(HandlerSocketResult result : results){
                                                        System.out.println("\t" + result.toString());
                                                }
                                        }
                                        if(i == size)
                                                break;
                                }
                                String[] keys = new String[]{String.valueOf(i), "v" + String.valueOf(i)};
                                hs.command().insert(id, keys);
                        }
                        
        
                }finally{
                        System.out.println("close connection.");
                        hs.close();
                }
                
                System.out.println("");         
        }
        
        public void testHandlerSocketInsertKana() throws IOException{
                System.out.println("----- testHandlerSocketInsertKana -----");
                char base = 0x3041;
                if(verbose)
                        System.out.println(id + "/" + db + "/" + table + "/" + index + "/" + fieldList);
                
                HandlerSocket hs = new HandlerSocket();
                try{
                        System.out.println("open connection.");
                        hs.open(host, port);
                        System.out.println("open index.");
                        hs.command().openIndex(id, db, table, index, fieldList);
                        System.out.println("operations.");
                        for(int i = 0 ; i <= size ; i++){
                                if(i != 0 && i % loop == 0){
                                        System.out.println("\tcommand size : " + hs.getCommandSize() + "byte");
                                        List<HandlerSocketResult> results = hs.execute();
                                        System.out.println("\tresult size : " + hs.getCurrentResponseSize() + "byte");
                                        if(verbose){
                                                System.out.println("result.");
                                                for(HandlerSocketResult result : results){
                                                        System.out.println("\t" + result.toString());
                                                }
                                        }
                                        if(i == size)
                                                break;
                                }
                                String[] keys = new String[]{"" + (char)(base + i), "v" + (char)(base + i)};
                                hs.command().insert(id, keys);
                        }
                        
        
                }finally{
                        System.out.println("close connection.");
                        hs.close();
                }
                
                System.out.println("");         
        }
        
//      @Test
        public void testHandlerSocketFind() throws IOException{
                System.out.println("----- testHandlerSocketFind -----");
                if(verbose)
                        System.out.println(id + "/" + db + "/" + table + "/" + index + "/" + fieldList);
                
                HandlerSocket hs = new HandlerSocket();
                try{
                        System.out.println("open connection.");
                        hs.open(host, port);
                        System.out.println("open index.");
                        hs.command().openIndex(id, db, table, index, fieldList);
                        System.out.println("operations.");
                        for(int i = 0 ; i <= size ; i++){
                                if(i != 0 && i % loop == 0){
                                        System.out.println("\tcommand size : " + hs.getCommandSize() + "byte");
                                        List<HandlerSocketResult> results = hs.execute();
                                        System.out.println("\tresult size : " + hs.getCurrentResponseSize() + "byte");
                                        if(verbose){
                                                System.out.println("result.");
                                                for(HandlerSocketResult result : results){
                                                        System.out.println("\t" + result.toString());
                                                }
                                        }
                                        if(i == size)
                                                break;
                                }
                                String[] keys = new String[]{String.valueOf(i)};
                                hs.command().find(id, keys);

                        }
        
                }finally{
                        System.out.println("close connection.");
                        hs.close();
                }
                
                System.out.println("");
        }
        
        public void testHandlerSocketUpdateAscii() throws IOException{
                System.out.println("----- testHandlerSocketUpdateAscii -----");
                if(verbose)
                        System.out.println(id + "/" + db + "/" + table + "/" + index + "/" + fieldList);
                
                HandlerSocket hs = new HandlerSocket();
                try{
                        System.out.println("open connection.");
                        hs.open(host, port);
                        System.out.println("open index.");
                        hs.command().openIndex(id, db, table, index, fieldList);
                        System.out.println("operations.");
                        for(int i = 0 ; i <= size ; i++){
                                if(i != 0 && i % loop == 0){
                                        System.out.println("\tcommand size : " + hs.getCommandSize() + "byte");
                                        List<HandlerSocketResult> results = hs.execute();
                                        System.out.println("\tresult size : " + hs.getCurrentResponseSize() + "byte");
                                        if(verbose){
                                                System.out.println("result.");
                                                for(HandlerSocketResult result : results){
                                                        System.out.println("\t" + result.toString());
                                                }
                                        }
                                        if(i == size)
                                                break;
                                }
                                String[] keys = new String[]{String.valueOf(i)};
                                String[] values = new String[]{String.valueOf(i), "v" + String.valueOf(i + size)};
                                hs.command().findModifyUpdate(id, keys, "=", "1", "0", values);

                        }
        
                }finally{
                        System.out.println("close connection.");
                        hs.close();
                }
                
                System.out.println("");         
        }
        
        public void testHandlerSocketUpdateKana() throws IOException{
                System.out.println("----- testHandlerSocketUpdateKana -----");
                char base = 0x3041;
                if(verbose)
                        System.out.println(id + "/" + db + "/" + table + "/" + index + "/" + fieldList);
                
                HandlerSocket hs = new HandlerSocket();
                try{
                        System.out.println("open connection.");
                        hs.open(host, port);
                        System.out.println("open index.");
                        hs.command().openIndex(id, db, table, index, fieldList);
                        System.out.println("operations.");
                        for(int i = 0 ; i <= size ; i++){
                                if(i != 0 && i % loop == 0){
                                        System.out.println("\tcommand size : " + hs.getCommandSize() + "byte");
                                        List<HandlerSocketResult> results = hs.execute();
                                        System.out.println("\tresult size : " + hs.getCurrentResponseSize() + "byte");
                                        if(verbose){
                                                System.out.println("result.");
                                                for(HandlerSocketResult result : results){
                                                        System.out.println("\t" + result.toString());
                                                }
                                        }
                                        if(i == size)
                                                break;
                                }
                                String[] keys = new String[]{String.valueOf(i)};
                                String[] values = new String[]{"" + (char)(base + i), "v" + (char)(base + i + size)};
                                hs.command().findModifyUpdate(id, keys, "=", "1", "0", values);

                        }
        
                }finally{
                        System.out.println("close connection.");
                        hs.close();
                }
                
                System.out.println("");         
        }

        public void testHandlerSocketDelete() throws IOException{
                System.out.println("----- testHandlerSocketDelete -----");
                if(verbose)
                        System.out.println(id + "/" + db + "/" + table + "/" + index + "/" + fieldList);
                
                HandlerSocket hs = new HandlerSocket();
                try{
                        System.out.println("open connection.");
                        hs.open(host, port);
                        System.out.println("open index.");
                        hs.command().openIndex(id, db, table, index, fieldList);
                        System.out.println("operations.");
                        for(int i = 0 ; i <= size ; i++){
                                if(i != 0 && i % loop == 0){
                                        System.out.println("\tcommand size : " + hs.getCommandSize() + "byte");
                                        List<HandlerSocketResult> results = hs.execute();
                                        System.out.println("\tresult size : " + hs.getCurrentResponseSize() + "byte");
                                        if(verbose){
                                                System.out.println("result.");
                                                for(HandlerSocketResult result : results){
                                                        System.out.println("\t" + result.toString());
                                                }
                                        }
                                        if(i == size)
                                                break;
                                }
                                String[] keys = new String[]{String.valueOf(i)};
                                hs.command().findModifyDelete(id, keys,  "=", "1", "0");

                        }
                        
        
                }finally{
                        System.out.println("close connection.");
                        hs.close();
                }
                
                System.out.println("");         
        }
}
