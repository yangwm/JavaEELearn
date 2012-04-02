
package page;

import java.io.IOException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.HasControls;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;
import javax.naming.ldap.SortControl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.LdapContextUtil;


/**
 * 
 * 
 * @author yangwm in Jan 14, 2009 4:38:35 PM
 */
public class TestPagedResultsControl {
	private static final Log logger = LogFactory.getLog(TestPagedResultsControl.class);

	/**
	 * create by yangwm in Jan 10, 2009 2:55:30 PM
	 * @param args
	 */
	public static void main(String... args) {
//        String baseDn = "ou=user,dc=4a,dc=4adomain"; 	// SunOne LDAP  // "ou=AuthProcess,dc=4a,dc=4adomain"
//    	String filter = "(objectclass=*)";						// SunOne LDAP  // "(objectclass=a4-ResAccount)"
//    	String attribute = "a4-bid";
//    	String[] returnedAtts = {attribute, "objectClass"};
    	
    	String baseDn = "ou=caspres,o=chinalife";			// Nevoll eDictory 
    	String filter = "(objectclass=*)";				// Nevoll eDictory 
    	String attribute = "cn";
    	String[] returnedAtts = {attribute, "objectClass"};

    	LdapContext ctx = null;
        try {
            // Open an LDAP association
	        ctx = LdapContextUtil.getLdapContext();
	        	
	   	    // Activate paged results
	   	    int pageSize = 5; // 5 entries per page
	   	    byte[] cookie = null;
	   	    int total;
	   	    int targetOffset = 1;
	   	    
            PagedResultsControl prctl = new PagedResultsControl(pageSize, cookie, Control.NONCRITICAL);
            SortControl sctl = new SortControl(new String[] { attribute }, Control.CRITICAL);
            ctx.setRequestControls(new Control[] { sctl, prctl });
//	   	    ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.NONCRITICAL) });
	   	
	   	    do {
	   	    	// SearchControls 
	   	    	SearchControls constraints = new SearchControls();
	   	    	constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
	   	    	//constraints.setReturningObjFlag(false);
	   	    	//constraints.setCountLimit(pageSize);
	   	    	if (returnedAtts != null) {
	   	            constraints.setReturningAttributes(returnedAtts);
	   	        }
	   	    	
	   	    	logger.debug("-------------------Perform the search---------------------");
	   	    	// Perform the search
	   	        NamingEnumeration<SearchResult> results = ctx.search(baseDn, filter, constraints);
	   	        
	   	        // Iterate over a batch of search results
	   	        int inx = 0;
	   	        while (results != null && results.hasMore()) {
	   	            // Display an entry
	   	            SearchResult entry = results.next();
	   	            logger.debug((++inx) + ": " + entry.getName());
	   	            //logger.debug(entry.getAttributes());
	   	
	   	            //logger.debug(entry);
	   	            // Handle the entry's response controls (if any)
	   	            if (entry instanceof HasControls) {
	   	                ((HasControls)entry).getControls();
	   	                logger.debug(((HasControls)entry).getControls());
	   	            }
	   	        }
	   	        
	   	        // Examine the paged results control response 
	   	        Control[] controls = ctx.getResponseControls();
	   	        //logger.debug(controls);
	   	        if (controls != null) {
	   	        	//logger.debug(controls.length);
	   	            for (int i = 0; i < controls.length; i++) {
	   	            	//logger.debug(controls[i]);
	   	                if (controls[i] instanceof PagedResultsResponseControl) {
	   	                    PagedResultsResponseControl prrc = (PagedResultsResponseControl)controls[i];
	   	                    total = prrc.getResultSize();
	   	                    cookie = prrc.getCookie();
	   	                    logger.debug(total);
	   	                    logger.debug(cookie);
	   	                } else {
	   	                    // Handle other response controls (if any)
	   	                }
	   	            }
	   	        }
	   	
	   	        // Re-activate paged results
                if ( cookie != null ) {
//                    prctl = new PagedResultsControl(pageSize, cookie, Control.NONCRITICAL);
//                    sctl = new SortControl(new String[] { attribute }, Control.CRITICAL);
//                    ctx.setRequestControls(new Control[] { sctl, prctl });
                    ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.NONCRITICAL) });
                }
	   	    } while ( false == true && cookie != null && cookie.length != 0 );
        } catch (NamingException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } finally {
        	// Close the LDAP association
            LdapContextUtil.close(ctx);
        }
	}

}
