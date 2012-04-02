/**
 * 
 */
package comet.second;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.util.ajax.Continuation;
import org.mortbay.util.ajax.ContinuationSupport;

/**
 * Long Polling Comet 
 * 
 * @author yangwm Sep 28, 2010 11:47:02 AM 
 */
public class JettyCometLongPolling extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8098872819945693191L;
	
	private static final int TIMEOUT = 30 * 1000;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// handle the time request
		
		// get the jetty continuation
		Continuation continuation = ContinuationSupport.getContinuation(request, null);
		JettyContinuationManager.add("yangwm", continuation); // id is yangwm 
		
		// set the header
		response.setContentType("text/html");
		
		// write continuation wait resume 
		boolean resumed = continuation.suspend(TIMEOUT); // suspend the response, if timeout may be resumed to true
		if(resumed) {
			System.out.println(continuation + ", get data: " + continuation.getObject());
			System.out.flush();
			
			response.getWriter().write(continuation + ", get data: " + continuation.getObject());
			response.getWriter().flush();
		} else {
			System.out.println(continuation + ", timeout");
			System.out.flush();
			
			response.getWriter().write(continuation + ", timeout");
			response.getWriter().flush();
			
			JettyContinuationManager.clear("yangwm"); // id is yangwm 
		}
		
		// ...
	}
	
}
