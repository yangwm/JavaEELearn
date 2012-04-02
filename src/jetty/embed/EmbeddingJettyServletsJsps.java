/**
 * 
 */
package jetty.embed;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Embedding Jetty Servlets and Jsps
 * 
 * test url:
 * http://127.0.0.1:8080/admin
 * http://127.0.0.1:8080/servlets/cust
 * http://127.0.0.1:8080/servlets/user
 * 
 * @author yangwm Oct 22, 2010 4:15:07 PM
 */
public class EmbeddingJettyServletsJsps {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // assumes that this directory contains .html and .jsp files
        // This is just a directory within your source tree, and can be exported
        // as part of your normal .jar
        final String WEBAPPDIR = "jetty/embed/yanwmapp";

        final Server server = new Server(8080);

        final String CONTEXTPATH = "/admin";

        // for localhost:port/admin/index.html and whatever else is in the webapp directory
        final URL warUrl = EmbeddingJettyServletsJsps.class.getClassLoader().getResource(WEBAPPDIR);
        final String warUrlString = warUrl.toExternalForm();
        server.setHandler(new WebAppContext(warUrlString, CONTEXTPATH));

        String whatever = "see you!";
        
        // for localhost:port/servlets/cust, etc.
        final Context context = new Context(server, "/servlets", Context.SESSIONS);
        context.addServlet(new ServletHolder(new CustomerServlet(whatever)), "/cust");
        context.addServlet(new ServletHolder(new UserServlet(whatever)), "/user");

        server.start();

    }
}

/**
 * Customer Servlet
 * 
 * @author yangwm Oct 22, 2010 4:25:16 PM
 */
class CustomerServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 2837266747158675619L;

    private final String name;

    public CustomerServlet(String name) {
        this.name = name;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>CustomerServlet, " + name + "</h1>");
        response.getWriter().flush();
    }

}

/**
 * User Servlet
 * 
 * @author yangwm Oct 22, 2010 4:25:16 PM
 */
class UserServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1185356478162452319L;

    private final String name;

    public UserServlet(String name) {
        this.name = name;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>UserServlet, " + name + "</h1>");
        response.getWriter().flush();
    }

}
