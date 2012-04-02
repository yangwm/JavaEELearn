package comet.time;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.nio.SelectChannelConnector.RetryContinuation;
import org.mortbay.util.ajax.Continuation;
import org.mortbay.util.ajax.ContinuationSupport;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

/**
 * long polling 
 * http://www.ibm.com/developerworks/cn/web/wa-cometjava/
 * 
 * @author yangwm
 */
public class JettyTimerServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6344134349351484323L;
    private static final Integer TIMEOUT = 10 * 1000;

    public void begin(HttpServletRequest request, HttpServletResponse response, Continuation continuation) throws IOException, ServletException {
        request.setAttribute("comet.time", Boolean.TRUE);
        request.setAttribute("comet.time.timeout", TIMEOUT);
        log("Begin for session: " + request.getSession(true).getId());
        
        //response
        String time = new Date().toString();
        response.getWriter().write(time);
        response.getWriter().flush();
        log("response.getWriter().write:" + time);
        
        //continuation.complete();
    }

    public void end(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        synchronized (request) {
            request.removeAttribute("comet.time");

            Continuation continuation = ContinuationSupport.getContinuation(request, request);
            if (continuation.isPending()) {
                continuation.resume();
            }
        }
        log("End for session: " + request.getSession(true).getId());
    }

    public void error(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log("Error for session: " + request.getSession(true).getId());
        end(request, response);
    }

    public boolean read(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	// TODO: wrap response so we can reset timeout on writes.
    	
    	response.setContentType("text/html;charset=utf-8");

        Continuation continuation = ContinuationSupport.getContinuation(request, request);

        if (!continuation.isPending()) {
            begin(request, response, continuation);
        }

        Integer timeout = (Integer) request.getAttribute("comet.time.timeout");
        boolean resumed = continuation.suspend(timeout == null ? 10000 : timeout.intValue());

        if (!resumed) {
            error(request, response);
        }
    }

    public void setTimeout(HttpServletRequest request, HttpServletResponse response, int timeout) throws IOException, ServletException,
            UnsupportedOperationException {
        request.setAttribute("comet.time.timeout", new Integer(timeout));
    }

}


