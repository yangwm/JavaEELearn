package jp.ndca.handlersocket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MySQL pluginの一つであるHandlerSocket(http://github.com/ahiguti/HandlerSocket-Plugin-for-MySQL/)のJavaクライアント実装です。
 * 
 * @author moaikids
 *
 */
public class HandlerSocket {
        private static Log log = LogFactory.getLog(HandlerSocket.class);
        private static final byte TOKEN_SEPARATOR = 0x09;
        private static final byte COMMAND_TERMINATE = 0x0a;

        private static final int SOCKET_TIMEOUT = 30 * 1000;
        private static final int SOCKET_BUFFER_SIZE = 8 * 1024;
        private static final int EXECUTE_BUFFER_SIZE = 8 * 1024;
        
        private int timeout = SOCKET_TIMEOUT;
        private int sendBufferSize = SOCKET_BUFFER_SIZE;
        private int receiveBufferSize = SOCKET_BUFFER_SIZE;
        private int executeBufferSize = EXECUTE_BUFFER_SIZE;
        private boolean blocking = false;//Blockingモードで動作するかどうか。trueはBlocking/falseはNon-Blocking
        private boolean tcpNoDelay = true;
        private boolean reuseAddress = false;
        private boolean hardClose = false; //hard closeをするかどうか。falseの場合はデフォルト値（ソケットはすぐには破壊されない）。trueの場合は即廃棄。

        SocketChannel socket;
        Selector selector;
        BlockingQueue<byte[]> commandBuffer;
        Command command;
        int currentResultSize = 0;//直前に実行されたコマンドのレスポンスデータサイズ
        
        public HandlerSocket(){
                super();
                commandBuffer = new LinkedBlockingQueue<byte[]>();
                command = new Command();
        }
        
        public void clear(){
                this.commandBuffer.clear();
                this.currentResultSize = 0;
        }
        
        public Command command(){
                return command;
        }
        
        /**
         * 現在未発行のコマンドの総バイトサイズを返却します。
         * @return
         */
        public int getCommandSize(){
                int total = 0;
                for(byte[] b : commandBuffer){
                        total += b.length;
                }
                
                return total;
        }
        
        /**
         * 直前に実行されたコマンドのレスポンスデータサイズを返却します。
         * @return
         */
        public int getCurrentResponseSize(){
                return currentResultSize;
        }
        
        /**
         * HandlerSocketと接続します。
         * @param host
         * @param port
         * @throws IOException
         */
        public void open(String host, int port) throws IOException{
                open(InetAddress.getByName(host), port);
        }
        
        /**
         * HandlerSocketと接続します。
         * @param address
         * @param port
         * @throws IOException
         */
        public void open(InetAddress address, int port) throws IOException{
                if(socket != null && socket.isConnected()){
                        close();
                }
                
                selector = Selector.open();
                socket = SocketChannel.open();
                socket.configureBlocking(blocking);
                socket.socket().setReceiveBufferSize(receiveBufferSize);
                socket.socket().setSendBufferSize(sendBufferSize);
                socket.socket().setSoTimeout(timeout);
                socket.socket().setTcpNoDelay(tcpNoDelay);
                socket.socket().setReuseAddress(reuseAddress);
                if(hardClose){
                        socket.socket().setSoLinger(true, 0);
                }

                socket.connect(new InetSocketAddress(address, port));
                while(!socket.finishConnect()){}
        }
        
        public synchronized List<HandlerSocketResult> execute() throws IOException{
                //TODO コマンドが一つもない場合の処理はどうするか？今回は何もしないでnullを返す。
                if(commandBuffer.size() == 0)
                        return null;
                currentResultSize = 0;

                socket.register(selector, socket.validOps());
                List<HandlerSocketResult> results = new ArrayList<HandlerSocketResult>();
                //TODO HandlerSocketとの送受信をすべてインラインで記述するか？ひとまずインラインで。
                //TODO OutputStream数珠つなぎの影響で無駄なbufferコピーが発生してないか。調べて最適な形に。
                //TODO 送受信途中でエラーが発生した場合どうすれば良いか。フェールセーフな方式の検討。
                //TODO 一度に実行するコマンドの上限を設けるか？今は無制限。
                try{
                        boolean processComplete = false;
                        while(!processComplete && selector.select() > 0){
                                Iterator iterator = selector.selectedKeys().iterator();
                                while(iterator.hasNext()){
                                        SelectionKey key = (SelectionKey)iterator.next();
                                        iterator.remove();
                                        
                                        if(key.isWritable()){
                                                SocketChannel channel = (SocketChannel)key.channel();
                                                final ByteArrayOutputStream buf = new ByteArrayOutputStream();
                                                for(byte[] command ; (command = commandBuffer.poll()) != null ; ){
                                                        System.out.println("write command:" + new String(command));
                                                        buf.write(command);
                                                }
                                                channel.register(selector, SelectionKey.OP_READ);

                                                ByteBuffer wb = ByteBuffer.wrap(buf.toByteArray());
                                                while(wb.remaining()>0){
                                                        channel.write(wb);
                                                }
                                        }else if(key.isReadable()){
                                                SocketChannel channel = (SocketChannel)key.channel();
                                                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                                                int readSize = 0;
                                                
                                                ByteBuffer rb = ByteBuffer.allocate(executeBufferSize);
                                                rb.clear();
                                                for(int size = 0 ; (size = channel.read(rb)) > 0; ){
                                                        currentResultSize += size;
                                                        readSize += size;
                                                        rb.flip();
                                                        buffer.write(rb.array(), 0, size);
                                                        rb.position(0);
                                                        rb.clear();
                                                        if(size < executeBufferSize)
                                                                break;
                                                }
                                                
                                                if(log.isDebugEnabled()){
                                                    log.debug(readSize + " / " + buffer.toByteArray().length);
                                                    log.debug(new String(buffer.toByteArray()));
                                                }
                                                ResponseParser parser = new ResponseParser();
                                                results = parser.parse(buffer.toByteArray());
                                                
                                                processComplete = true;
                                                
                                                break;

                                        }
                                }
                        }
                }finally{
                        
                }
                
                return results;
        }
        
        /**
         * HandlerSocketとの接続を切断します。
         * @throws IOException
         */
        public void close() throws IOException{
                socket.socket().close();
                socket.close();
                try {
                        Iterator itr = selector.keys().iterator();
                        while(itr.hasNext()){
                                SelectionKey key = (SelectionKey)itr.next();
                                key.channel().close();
                                key.cancel();
                        }
                } catch(IOException e) { 
                        throw e;
                }
                selector.close();

        }
        
        public int getTimeout() {
                return timeout;
        }

        public void setTimeout(int timeout) {
                this.timeout = timeout;
        }

        public int getSendBufferSize() {
                return sendBufferSize;
        }

        public void setSendBufferSize(int sendBufferSize) {
                this.sendBufferSize = sendBufferSize;
        }

        public int getReceiveBufferSize() {
                return receiveBufferSize;
        }

        public void setReceiveBufferSize(int receiveBufferSize) {
                this.receiveBufferSize = receiveBufferSize;
        }

        public int getExecuteBufferSize() {
                return executeBufferSize;
        }

        public void setExecuteBufferSize(int executeBufferSize) {
                this.executeBufferSize = executeBufferSize;
        }

        public boolean isTcpNoDelay() {
                return tcpNoDelay;
        }

        public void setTcpNoDelay(boolean tcpNoDelay) {
                this.tcpNoDelay = tcpNoDelay;
        }

        public boolean isHardClose() {
                return hardClose;
        }

        public void setHardClose(boolean hardClose) {
                this.hardClose = hardClose;
        }

        public boolean isReuseAddress() {
                return reuseAddress;
        }

        public void setReuseAddress(boolean reuseAddress) {
                this.reuseAddress = reuseAddress;
        }



        /**
         * HanlerSocketのコマンドを実行します。実行したコマンドはqueueに格納され、HanlerSocket#execute時にまとめて実行されます。
         * @author moaikids
         *
         */
        public class Command{
                private static final String OPERATOR_OPEN_INDEX = "P";
                private static final String OPERATOR_INSERT = "+";
                private static final String OPERATOR_UPDATE = "U";
                private static final String OPERATOR_DELETE = "D";
                
                private static final String DEFAULT_ENCODING = "UTF-8";
                private String encoding = DEFAULT_ENCODING;
                
                public Command(){
                        super();
                }
                
                public Command(String encoding){
                        this();
                        this.encoding = encoding;
                }
                
                /**
                 * open_indexコマンドを実行します。
                 * @param id
                 * @param db
                 * @param table
                 * @param index
                 * @param fieldList
                 * @throws IOException
                 */
                public void openIndex(String id, String db, String table, String index, String fieldList) throws IOException{
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        DataOutputStream dos = new DataOutputStream(buffer);
                        
                        //header
                        writeToken(dos, OPERATOR_OPEN_INDEX);
                        writeTokenSeparator(dos);
                        //id
                        writeToken(dos, id);
                        writeTokenSeparator(dos);
                        //db
                        writeToken(dos, db);
                        writeTokenSeparator(dos);
                        //table
                        writeToken(dos, table);
                        writeTokenSeparator(dos);
                        //index
                        writeToken(dos, index);
                        writeTokenSeparator(dos);
                        //field list
                        writeToken(dos, fieldList);
                        writeCommandTerminate(dos);
                        
                        dos.flush();
                        
                        commandBuffer.add(buffer.toByteArray());
                }

                /**
                 * findコマンドを実行します。
                 * @param id
                 * @param keys
                 * @throws IOException
                 */
                public void find(String id, String[] keys) throws IOException{
                        find(id, keys , "=" , "1", "0");
                }
                
                /**
                 * findコマンドを実行します。
                 * @param id
                 * @param keys
                 * @param operator
                 * @param limit
                 * @param offset
                 * @throws IOException
                 */
                public void find(String id, String[] keys, String operator , String limit, String offset) throws IOException{
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        DataOutputStream dos = new DataOutputStream(buffer);
                        
                        //id
                        writeToken(dos, id);
                        writeTokenSeparator(dos);
                        //operator
                        writeToken(dos, operator);
                        writeTokenSeparator(dos);
                        //key nums
                        writeToken(dos, String.valueOf(keys.length));
                        writeTokenSeparator(dos);
                        for(String key : keys){
                                writeToken(dos, key);
                                writeTokenSeparator(dos);
                        }
                        //limit
                        writeToken(dos, limit);
                        writeTokenSeparator(dos);
                        //offset
                        writeToken(dos, offset);
                        writeCommandTerminate(dos);
                        
                        dos.flush();
                        
                        commandBuffer.add(buffer.toByteArray());
                }
                
                /**
                 * insertコマンドを実行します。
                 * @param id
                 * @param keys
                 * @throws IOException
                 */
                public void insert(String id, String[] keys) throws IOException{
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        DataOutputStream dos = new DataOutputStream(buffer);

                        //id
                        writeToken(dos, id);
                        writeTokenSeparator(dos);
                        //operator
                        writeToken(dos, OPERATOR_INSERT);
                        writeTokenSeparator(dos);
                        //key nums
                        writeToken(dos, String.valueOf(keys.length));
                        writeTokenSeparator(dos);
                        for(int i = 0 ; i < keys.length ; i++){
                                writeToken(dos, keys[i] == null ? null : keys[i].getBytes(encoding));
                                if(i == keys.length - 1){
                                        writeCommandTerminate(dos);
                                }else{
                                        writeTokenSeparator(dos);
                                }
                        }
                        
                        dos.flush();
                        
                        System.out.println("buffer.toByteArray():" + buffer.toByteArray());
                        commandBuffer.add(buffer.toByteArray());
                }
                
                /**
                 * find_modify(delete)を実行します。
                 * @param id
                 * @param keys
                 * @param operator
                 * @param limit
                 * @param offset
                 * @throws IOException
                 */
                public void findModifyDelete(String id, String[] keys, String operator , String limit, String offset) throws IOException{
                        findModify(id, keys, operator, limit, offset, OPERATOR_DELETE, new String[keys.length]);
                }
                
                /**
                 * find_modify(update)を実行します。
                 * @param id
                 * @param keys
                 * @param operator
                 * @param limit
                 * @param offset
                 * @param values
                 * @throws IOException
                 */
                public void findModifyUpdate(String id, String[] keys, String operator , String limit, String offset, String[] values) throws IOException{
                        findModify(id, keys, operator, limit, offset, OPERATOR_UPDATE, values);
                }
                
                /**
                 * 
                 * @param id
                 * @param keys
                 * @param operator
                 * @param limit
                 * @param offset
                 * @param modOperation
                 * @param values
                 * @throws IOException
                 */
                private void findModify(
                                String id, String[] keys, String operator , String limit, String offset, 
                                String modOperation, String[] values) throws IOException{
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        DataOutputStream dos = new DataOutputStream(buffer);

                        //id
                        writeToken(dos, id);
                        writeTokenSeparator(dos);
                        //operator
                        writeToken(dos, operator);
                        writeTokenSeparator(dos);
                        //key nums
                        writeToken(dos, String.valueOf(keys.length));
                        writeTokenSeparator(dos);
                        for(String key : keys){
                                writeToken(dos, key == null ? null : key.getBytes(encoding));
                                writeTokenSeparator(dos);
                        }
                        //limit
                        writeToken(dos, limit);
                        writeTokenSeparator(dos);
                        //offset
                        writeToken(dos, offset);
                        writeTokenSeparator(dos);
                        //modify operator
                        writeToken(dos, modOperation);
                        writeTokenSeparator(dos);
                        
                        //modify values
                        for(int i = 0 ; i < values.length ; i++){
                                writeToken(dos, values[i] == null ? null : values[i].getBytes(encoding));
                                if(i == values.length - 1){
                                        writeCommandTerminate(dos);
                                }else{
                                        writeTokenSeparator(dos);
                                }
                        }
                        
                        dos.flush();
                        
                        commandBuffer.add(buffer.toByteArray());
                }
                
                private void writeToken(DataOutputStream dos, String token) throws IOException{
                        if(token == null){
                                dos.writeByte(0x00);
                        }else{
                                for(char c : token.toCharArray()){
                                        if(c > 255){
                                                dos.writeChar(c);
                                        }else{
                                                dos.writeByte((byte)c);
                                                
                                        }
                                }
                        }
                }
                
                private void writeToken(DataOutputStream dos, byte[] token) throws IOException{
                        if(token == null){
                                dos.writeByte(0x00);
                        }else{
                                for(byte b : token){
                                        dos.writeByte(b);
                                }
                        }
                }
                
                private void writeTokenSeparator(DataOutputStream dos) throws IOException{
                        dos.writeByte(TOKEN_SEPARATOR);
                }
                
                private void writeCommandTerminate(DataOutputStream dos) throws IOException{
                        dos.writeByte(COMMAND_TERMINATE);
                }
        }
        
        class ResponseParser{
                private static final String DEFAULT_ENCODING = "UTF-8";
                private String encoding = DEFAULT_ENCODING;
                
                public ResponseParser(){
                        super();
                }
                public ResponseParser(String encoding){
                        this();
                        this.encoding = encoding;
                }
                
                public List<HandlerSocketResult> parse(byte[] data) throws UnsupportedEncodingException{
                        List<HandlerSocketResult> results = new ArrayList<HandlerSocketResult>();
                        //TODO 中途半端で終わったレスポンス内容は破棄しています。その実装で良いか確認。
                        System.out.println("data.length:" + data.length);
                        for(int i = 0 ; i < data.length ; i++){
                            System.out.println("data[" + i + "]:" + data[i]);
                        }
                        
                        for(int i = 0 ; i < data.length ; ){
                                int status = data[i] - 0x30 ; i++; if(i >= data.length) break;
                                if(data[i] != 0x09)
                                        throw new RuntimeException(); //TOOD
                                i++; if(i >= data.length) break;//0x09
                                int fieldNum = data[i] - 0x30 ; i++; if(i >= data.length) break;
                                
                                List<String> messages = new ArrayList<String>();
                                HandlerSocketResult result = new HandlerSocketResult();
                                System.out.println("data[i] == COMMAND_TERMINATE:" + (data[i] == COMMAND_TERMINATE));
                                if(data[i] == COMMAND_TERMINATE){
                                        result.setStatus(status);
                                        result.setFieldNum(fieldNum);
                                        result.setMessages(messages);

                                        results.add(result);
                                        i++;//0x09 or 0x0a

                                        continue;
                                }else{
                                        i++;//0x09 or 0x0a
                                }
                                
                                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                                while(true){
                                        if(data.length <= i)
                                                break;
                                        byte b = data[i];
                                        i++;
                                        if(b == COMMAND_TERMINATE){
                                                System.out.println("b == COMMAND_TERMINATE:" + (b == COMMAND_TERMINATE));
                                                messages.add(buf.toString());

                                                result.setStatus(status);
                                                result.setFieldNum(fieldNum);
                                                result.setMessages(messages);
                                                
                                                results.add(result);
                                                break;
                                        }
                                        if(b == TOKEN_SEPARATOR){
                                                System.out.println("b == TOKEN_SEPARATOR:" + (b == TOKEN_SEPARATOR));
                                                messages.add(new String(buf.toByteArray(), encoding));
                                                buf = new ByteArrayOutputStream();
                                                continue;
                                        }
                                        buf.write(b);
                                }
                                
                        }
                        
                        return results;
                }
                public String getEncoding() {
                        return encoding;
                }
                public void setEncoding(String encoding) {
                        this.encoding = encoding;
                }
                
                
        }
}
