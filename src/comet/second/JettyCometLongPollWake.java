/**
 * 
 */
package comet.second;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.util.ajax.Continuation;

/**
 * Long Poll Wake Up Comet 
 * 
 * @author yangwm Sep 28, 2010 11:47:02 AM 
 */
public class JettyCometLongPollWake extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8098872819945693191L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// handle the time request
		
		Continuation continuation = JettyContinuationManager.get("yangwm"); // id is yangwm 
		
		System.out.println(continuation + ", resume: ");
		response.getWriter().write(continuation + ", resume: ");
		response.getWriter().flush();
		
		// wake up any long poll 
		if (continuation != null) {
		    continuation.setObject(new Date() + ", it works!");
		    continuation.resume();
		}

		// ...
	}
	
}
