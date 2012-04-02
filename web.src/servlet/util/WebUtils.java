/**
 * 
 */
package servlet.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Web Utils
 * 
 * @author yangwm in Jan 15, 2010 11:30:04 AM
 */
public class WebUtils {
    
    /**
     * get web project real path 
     * 
     * @param request - HttpServletRequest
     * @return String - the web project real path, eg: C:/Tomcat/webapps/TestProject
     */
    public static String getRealPath(HttpServletRequest request) {
        return getRealPath(request, "/");
    }
    
    /**
     * get web project real path 
     * 
     * @param request - HttpServletRequest
     * @return String - the web project real path, eg: C:/Tomcat/webapps/TestProject/WEB-INF/test.properties
     */
    public static String getRealPath(HttpServletRequest request, String path) {
        //return request.getRealPath("/WEB-INF/test.properties");
        return request.getSession().getServletContext().getRealPath(path);
    }
    
    /**
     * Get the base path of this request.
     *
     * @param request - HttpServletRequest
     * @return String - the base path, eg: http://www.abc.com:8000/someApp/
     */
    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path + "/";
        return basePath;
    }

    /**
     * Get the current page's full url path of this request. 
     *
     * @param request - HttpServletRequest
     * @return String - the full url path, eg: http://www.abc.com:8000/someApp/index.jsp?param=abc
     */
    public static String getFullRequestURL(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String qString = request.getQueryString();

        if (qString != null) {
            url.append('?');
            url.append(qString);
        }

        return url.toString();
    }

    /**
     * Get the current page's full uri path of this request. 
     *
     * @param request - HttpServletRequest
     * @return String - the full uri path, eg: /someApp/index.jsp?param=abc
     */
    public static String getFullRequestURI(HttpServletRequest request) {
        StringBuffer url = new StringBuffer(request.getRequestURI());
        String qString = request.getQueryString();

        if (qString != null) {
            url.append('?');
            url.append(qString);
        }

        return url.toString();
    }

}
