package page;

import java.io.IOException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.LdapContextUtil;

import com.sun.jndi.ldap.ctl.PagedResultsControl;
import com.sun.jndi.ldap.ctl.PagedResultsResponseControl;

public class ListIncrementally {
    private static final Log logger = LogFactory.getLog(ListIncrementally.class);

    void listIncrementally() {
        LdapContext ctx = null;
        try {
            String baseDn = "ou=AuthProcess,dc=4a,dc=4adomain";
            String filter = "(objectClass=top)";
            String attribute = "a4-bid";
            String[] returnedAtts = {attribute};
            
            boolean supportsPagedResults = true;
            int pageSize = 10;
            byte[] cookie = new byte[0];

            ctx = LdapContextUtil.getLdapContext();
     
            System.out.println("Ok, we are authenticated----------------------");
            
            if (supportsPagedResults) {
                ctx.setRequestControls(new Control[] {new PagedResultsControl(pageSize, cookie, Control.NONCRITICAL)});
            }
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);        
            constraints.setReturningAttributes(returnedAtts);
            
            int counter = 0;
            do {
//                    NamingEnumeration results  = ctx.search("", searchCondition , constraints);
                NamingEnumeration<SearchResult> results  = ctx.search(baseDn, filter , constraints);
                   
                if (results != null) {
                    int subcounter = 0; 
                    while (results.hasMoreElements()) {
                        subcounter++;
                        SearchResult si = (SearchResult)results.nextElement();
                        counter++;
                        System.out.println(si.getName());
                    }
                    System.out.println(filter + " returned " + subcounter );
                } else {
                    System.out.println(filter + " did not match with any!!!");
                }
                    
                if (supportsPagedResults) {
                    cookie =((PagedResultsResponseControl)ctx.getResponseControls()[0]).getCookie();
                }
 
                if((cookie != null)&&(supportsPagedResults)) { 
                    System.out.println("--------NEW PAGE----------");
                    ctx.setRequestControls(new Control[] {new PagedResultsControl(pageSize, cookie, Control.CRITICAL)});
                } 
            } while(cookie != null);
            
            System.out.println("Returned overall:" + counter );
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

    public static void main(String[] args) {
        new ListIncrementally().listIncrementally();
    }
}
