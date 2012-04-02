/**
 * 
 */
package jetty.embed;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

/**
 * 
 * @author yangwm Oct 22, 2010 5:09:53 PM
 */
public class EmbeddingMyJettyHandler extends AbstractHandler {
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello, yangwm. Embedding My Jetty Handler</h1>");
        ((Request) request).setHandled(true);
    }
}
