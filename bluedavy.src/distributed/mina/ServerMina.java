/**
 * 
 */
package distributed.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 基于Mina实现的服务器端
 * 
 * @author yangwm Aug 3, 2010 3:17:32 PM
 */
public class ServerMina {
    public static void main(String[] args) throws Exception {
        int port = 9527;
        final IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("stringserialize",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        IoHandler handler = new IoHandlerAdapter() {

            public void messageReceived(IoSession session, Object message) throws Exception {
                if ("quit".equalsIgnoreCase(message.toString())) {
                    System.out.println("Client will be quit!");
                    
                    session.close(true);
                } else if ("serverquit".equalsIgnoreCase(message.toString())) {
                    System.out.println("Server has been shutdown!");
                    
                    session.close(true);
                    //acceptor.unbind();
                    System.exit(0);
                }
                System.out.println("Message from client: " + message);
                session.write("Server response：" + message);
            }

        };
        acceptor.setHandler(handler);

        acceptor.bind(new InetSocketAddress(port));
        System.out.println("Server listen on port: " + port);
    }

}

/*
Message from client: quit
Message from client: minabc
Message from client: mina yangwm
Server has been shutdown!

*/
