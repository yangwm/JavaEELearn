/**
 * 
 */
package comet.notice;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.util.ajax.Continuation;
import org.mortbay.util.ajax.ContinuationSupport;

/**
 * Streaming Comet Notify
 * 
 * @author yangwm Oct 15, 2010 9:39:37 AM
 */
public class JettyCometNotify extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8098872819945693191L;

    private static final int TIMEOUT = 3 * 1000;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // handle the time request

        // get the jetty continuation
        Continuation continuation = ContinuationSupport.getContinuation(request, null);
        
        /*
         * because con.suspend() equivalence con.reset() if con.isPending() is true 
         * so if con.isPending() is ture, call con.reset() set pending status to false 
         */
        if (continuation != null && continuation.isPending()) { // continuation.isResumed() 
            continuation.reset();
        }

        // set the header
        response.setContentType("text/html");

        // write time periodically
        boolean noError = true;
        while (noError) {
            try {
                long ms = System.currentTimeMillis();
                Date date = new Date(ms);
                int i = new Random().nextInt(5);
                System.out.println(continuation + ", date is " + date + ", ms is " + ms + ", i is " + i);
                System.out.flush();
    
                if ((ms + i) % 5 != 0) {
                    response.getOutputStream().println(continuation + ", date is " + date + ", ms is " + ms + ", i is " + i);
                    response.getOutputStream().flush(); // will throws IOException(org.mortbay.jetty.EofException) if connection is invalid 
                } else {
                    boolean resumed = continuation.suspend(TIMEOUT); // suspend the response, if timeout may be resumed to true
                    System.out.println(continuation + ", date is " + date + ", ms is " + ms + ", i is " + i + " Timeout... resumed is" + resumed);
                    System.out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                noError = false;
            }
        }
        // ...
    }

}

/*
2010-10-14 22:10:47.984:INFO::Started SelectChannelConnector@0.0.0.0:8080
RetryContinuation@22292701,new, date is Thu Oct 14 22:10:52 CST 2010, ms is 1287065452796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:10:55 CST 2010, ms is 1287065455796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701, date is Thu Oct 14 22:10:55 CST 2010, ms is 1287065455796, i is 1 Timeout...
RetryContinuation@22292701, date is Thu Oct 14 22:10:55 CST 2010, ms is 1287065455796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 0resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 0resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 0resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 1 Timeout...
RetryContinuation@22292701, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:11:01 CST 2010, ms is 1287065461796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701, date is Thu Oct 14 22:11:01 CST 2010, ms is 1287065461796, i is 1 Timeout...
RetryContinuation@22292701, date is Thu Oct 14 22:11:01 CST 2010, ms is 1287065461796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:11:04 CST 2010, ms is 1287065464796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701, date is Thu Oct 14 22:11:04 CST 2010, ms is 1287065464796, i is 1 Timeout...
RetryContinuation@22292701, date is Thu Oct 14 22:11:04 CST 2010, ms is 1287065464812, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:11:07 CST 2010, ms is 1287065467796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701, date is Thu Oct 14 22:11:07 CST 2010, ms is 1287065467796, i is 1 Timeout...
RetryContinuation@22292701, date is Thu Oct 14 22:11:07 CST 2010, ms is 1287065467796, i is 0resp is org.mortbay.jetty.HttpConnection$1@1980630 false
RetryContinuation@22292701, date is Thu Oct 14 22:11:07 CST 2010, ms is 1287065467796, i is 0resp is org.mortbay.jetty.HttpConnection$1@1980630 true
RetryContinuation@22292701, date is Thu Oct 14 22:11:07 CST 2010, ms is 1287065467796, i is 0resp is org.mortbay.jetty.HttpConnection$1@1980630 true
RetryContinuation@22292701, date is Thu Oct 14 22:11:07 CST 2010, ms is 1287065467796, i is 1resp is org.mortbay.jetty.HttpConnection$1@1980630 true
2010-10-14 22:11:08.234:INFO::Shutdown hook executing
2010-10-14 22:11:08.250:INFO::Graceful shutdown SelectChannelConnector@0.0.0.0:8080



D:\>curl http://127.0.0.1:8080/testcomet/notify
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 0
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 0
RetryContinuation@22292701,pending,expired, date is Thu Oct 14 22:10:58 CST 2010, ms is 1287065458796, i is 0
^C
D:\>

*/
