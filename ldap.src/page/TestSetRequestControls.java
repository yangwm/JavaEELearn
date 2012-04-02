
package page;

import java.io.IOException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.LdapContextUtil;

import com.sun.jndi.ldap.ctl.SortControl;
import com.sun.jndi.ldap.ctl.VirtualListViewControl;

/**
 * Demonstrates how to set context request controls. Also demonstrates
 * the use of the com.sun.jndi.ldap.ctl.SortControl and
 * com.sun.jndi.ldap.ctl.VirtualListViewControl.
 *
 * usage: java SetRequest
 */
class TestSetRequestControls {
    private static final Log logger = LogFactory.getLog(TestSetRequestControls.class);
    
    public static void main(String[] args) {
		String baseDn = "ou=AuthProcess,dc=4a,dc=4adomain";
    	String filter = "(objectclass=*)";
    	String attribute = "a4-bid";

		LdapContext ctx = null;
		try {
		    // Create initial context with no connection request controls
		    ctx = LdapContextUtil.getLdapContext();
	
		    // Create critical Sort that sorts based on CN
		    Control[] ctxCtls = new Control[]{
			new SortControl(new String[]{attribute}, Control.CRITICAL)
		    };
	
		    // Set context's request controls to be ctxCtls
		    ctx.setRequestControls(ctxCtls);
	
		    /*// Perform list, will sort by attribute
		    NamingEnumeration<NameClassPair> answer1 = ctx.list("");
	
		    // Enumerate answers
		    System.out.println("---->sort by " + attribute);
		    while (answer1.hasMore()) {
		    	System.out.println(((NameClassPair)answer1.next()).getName());
		    }*/
	
		    // Perform search, will still sort by cn
		    // because context request controls are still in effect
		    NamingEnumeration<SearchResult> answer = ctx.search(baseDn, filter, null);
	
		    // Enumerate answers
		    System.out.println("---->sort " + baseDn + " by " + attribute);
		    while (answer.hasMore()) {
		    	System.out.println(((SearchResult)answer.next()).getName());
		    }
	
		    // Context request controls remains set until replaced
		    // Replace controls with Sort+VirtualListView
		    VirtualListViewControl vctl = new VirtualListViewControl(0, 0/*10*/, 0, 9, Control.CRITICAL);
	   	    SortControl sctl = new SortControl(new String[] { attribute },Control.CRITICAL);
	   	    ctx.setRequestControls(new Control[] { sctl, vctl });
	
		    // Perform search, sort by telephonenumber, return first 10 answers
		    answer = ctx.search(baseDn, filter, null);
	
		    // Enumerate answers (first page only)
		    System.out.println("---->sort by " + attribute + ", list size = 10");
		    int count = 0;
		    while (answer.hasMore()) {
				System.out.print(count++ + ": ");
				System.out.println(answer.next());
		    }
	
		} catch (NamingException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } finally {
			// Close when no longer needed
		    LdapContextUtil.close(ctx);
		}
    }
}

