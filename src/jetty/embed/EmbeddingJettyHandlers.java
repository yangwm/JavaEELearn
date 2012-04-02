/**
 * 
 */
package jetty.embed;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

/**
 * Embedding Jetty Handlers
 * 
 * @author yangwm Oct 22, 2010 4:12:25 PM
 */
public class EmbeddingJettyHandlers {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Handler handler = new AbstractHandler() {
            public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
                    throws IOException, ServletException {
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("<h1>Hello, yangwm. Jetty Handlers</h1>");
                ((Request) request).setHandled(true);
            }
        };

        Server server = new Server(8080);
        server.setHandler(handler);
        server.start();
    }

}
