/**
 * 
 */
package comet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>
 * A ServletContextListner that can be used to initialize comet system.
 * </p>
 * 
 * @author yangwm Oct 28, 2010 2:38:24 PM
 */
public class InitializerListener implements ServletContextListener {
    
    private boolean performShutdown = true;

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {

        System.out.println("comet Initializer Servlet loaded, shutdown...");
        if (performShutdown == false) {
            return;
        }

        try {
            // destroy your resource 
            System.out.println("comet Initializer Servlet loaded, shutdown successful.");
        } catch (Exception e) {
            System.err.println("comet Initializer failed to shutdown cleanly: " + e.toString());
            e.printStackTrace();
        }

    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("Ra Initializer Servlet loaded, initializing...");
        ServletContext servletContext = sce.getServletContext();

        try {
            String shutdownOnUnload = servletContext.getInitParameter("shutdown-on-unload");
            System.out.println("shutdownOnUnload=" + shutdownOnUnload);
            if (shutdownOnUnload != null) {
                performShutdown = Boolean.valueOf(shutdownOnUnload).booleanValue();
            }

            // initialize your resource 
            System.out.println("comet Initializer Servlet loaded, initializing successful.");
        } catch (Exception e) {
            System.err.println("comet Initializer failed to initialize cleanly: " + e.toString());
            e.printStackTrace();
        }
    }

}
