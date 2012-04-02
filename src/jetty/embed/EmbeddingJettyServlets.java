/**
 * 
 */
package jetty.embed;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

/**
 * Embedding Jetty Servlets
 * 
 * @author yangwm Oct 22, 2010 4:15:07 PM
 */
public class EmbeddingJettyServlets {
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        Context root = new Context(server,"/", Context.SESSIONS);
        root.addServlet(new ServletHolder(new HelloServlet("yangwm")), "/*");
        server.start();
    }
}

/**
 * Hello Servlet 
 * 
 * @author yangwm Oct 22, 2010 4:25:16 PM
 */
class HelloServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = -4096467407538768286L;

    private final String name;
    
    public HelloServlet(String name) {
        this.name = name;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>HelloServlet, " + name + "</h1>");
        response.getWriter().flush();
    }
    
}
