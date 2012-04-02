/**
 * 
 */
package servlet.sample;

import javax.servlet.http.HttpServletRequest;

/**
 * servlet path sample 
 * 
 * @author yangwm in Jan 15, 2010 2:16:21 PM
 */
public class PathSample {

    /**
     * 
     * create by yangwm in Jan 15, 2010 2:24:21 PM
     * @param request
     */
    public static void path(HttpServletRequest request) {
        System.out.println("request.getContextPath() = " + request.getContextPath());
        System.out.println("request.getServletPath() = " + request.getServletPath());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        String realPath = request.getSession().getServletContext().getRealPath("/");
        System.out.println("request.getRealPath(\"/\") = " + realPath); 
    }
    
}

/*
request.getContextPath() = /TestProject
request.getServletPath() = /servlet/TestPath
request.getRequestURI() = /TestProject/servlet/TestPath
request.getRequestURL() = http://localhost:8080/TestProject/servlet/TestPath
request.getRealPath("/") = C:/Tomcat/webapps/TestProject

*/
