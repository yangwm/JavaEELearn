/**
 * 
 */
package comet.first;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.util.ajax.Continuation;
import org.mortbay.util.ajax.ContinuationSupport;

/**
 * Streaming Comet 
 * 
 * @author yangwm Sep 26, 2010 11:47:02 AM 
 */
public class JettyCometStreaming extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8098872819945693191L;
	
	private static final int TIMEOUT = 3 * 1000;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// handle the time request
		
		// get the jetty continuation
		Continuation continuation = ContinuationSupport.getContinuation(request, null);
		
		// set the header
		response.setContentType("text/html");
		
		response.getWriter().print("service begin " + new Date() + ", ");
		response.getWriter().flush();

		// write time periodically
		while (response.getWriter().checkError() == false) { // validity of the response's connection 
		    response.getWriter().print("first... ");
		    response.getWriter().flush();
			
            /*
             * 每次被唤醒后恢复执行两次while循环开始 到con.suspend之前的代码 ，
             * 因为con.suspend() equivalence con.reset() if con.isPending() is true 
             * 所以timeout之后重新从service开始执行--> con.suspend() --> con.suspend之后的代码 --> 继续while循环开始到con.suspend之前的代码，
             */
			continuation.suspend(TIMEOUT); // suspend the response, if timeout may be resumed to true
			
			response.getWriter().print("end!");
			response.getWriter().flush();
		}
		// ...
	}
	
}

/*
service begin Fri Oct 15 15:20:37 CST 2010, first... service begin Fri Oct 15 15:20:40 CST 2010, first... service begin Fri Oct 15 15:20:43 CST 2010, first... ^C

*/

