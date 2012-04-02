/**
 * 
 */
package distributed.mina;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 基于Mina实现的客户端
 * 
 * @author yangwm Aug 3, 2010 3:29:06 PM
 */
public class ClientMina {

    public static void main(String[] args) throws Exception {
        int port = 9527;

        SocketConnector ioConnector = new NioSocketConnector();
        ioConnector.getSessionConfig().setTcpNoDelay(true);
        ioConnector.getFilterChain().addLast("stringserialize",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", port);

        IoHandler handler = new IoHandlerAdapter() {

            public void messageReceived(IoSession session, Object message) throws Exception {
                System.out.println(message);
            }

        };
        ioConnector.setHandler(handler);
        ConnectFuture connectFuture = ioConnector.connect(socketAddress);
        connectFuture.awaitUninterruptibly();

        IoSession session = connectFuture.getSession();
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String command = systemIn.readLine();
            session.write(command);
            if (command == null || "quit".equalsIgnoreCase(command.trim())
                    || "serverquit".equalsIgnoreCase(command.trim())) {
                System.out.println("Client quit!");
                // Wait until the chat ends.
                session.getCloseFuture().awaitUninterruptibly();
                System.exit(0);
            }
        }
    }

}

/*
minabc
Server response：minabc
mina yangwm
Server response：mina yangwm
serverquit
Client quit!

*/
