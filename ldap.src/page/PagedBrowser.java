
package page;

import java.io.IOException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.LdapContextUtil;

import com.sun.jndi.ldap.ctl.VirtualListViewControl;

public class PagedBrowser {
	private static final Log logger = LogFactory.getLog(PagedBrowser.class);

    public static void main(String[] args) {
    	String baseDn = "ou=AuthProcess,dc=4a,dc=4adomain";
    	String filter = "(a4-bid=*)";
    	String attribute = "a4-bid";
    	String[] returnedAtts = {attribute};
    	
        LdapContext mCtx = null;
        try{
            mCtx = LdapContextUtil.getLdapContext();
            
            boolean b = true;
            int counter = 0;
            int listSize = 0;
            int i = 0;
            int nTargetOffset = 1;
            int nPageSize = 20;
            while (b) {
                VirtualListViewControl vctl = new VirtualListViewControl(nTargetOffset, 0, 0, nPageSize-1, Control.CRITICAL);
                SortControl sctl = new SortControl(new String[] { attribute },Control.CRITICAL);
               
                mCtx.setRequestControls(new Control[] { sctl, vctl });

                SearchControls constraints = new SearchControls();
                constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
                if (returnedAtts != null) {
	   	            constraints.setReturningAttributes(returnedAtts);
	   	        }

                NamingEnumeration<SearchResult> results = mCtx.search(baseDn, filter, constraints);
                
                int subcounter = 0;
                if (results != null) {
                	System.out.println("-----------NEW PAGE NO." + (++i) + "-----------");
                    while (results.hasMoreElements()) {
                    	counter++;
                        subcounter++;
                        
                        SearchResult si = results.nextElement();
                        System.out.println(si.getName());
                    }
                    System.out.println(filter + " returned "+ subcounter);
                    if (subcounter == 0){
                        b = false;
                    }
                } else{
                    System.out.println(filter+ " did not match with any!!!");
                }

                nTargetOffset += subcounter; // nPageSize; //subcounter;
            }
            
            System.out.println(filter + " total returned "+ counter);
        } catch (NamingException e) {
            e.printStackTrace();
        	logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } finally {
            LdapContextUtil.close(mCtx);
        }
           
    }

}
