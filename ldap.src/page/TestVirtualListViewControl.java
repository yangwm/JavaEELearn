package page;

import java.io.IOException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.HasControls;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.EntityUtil;
import util.LdapContextUtil;

import com.sun.jndi.ldap.ctl.VirtualListViewControl;
import com.sun.jndi.ldap.ctl.VirtualListViewResponseControl;

/**
 * 
 * @author yangwm in Jan 15, 2009 10:00:05 PM
 */
public class TestVirtualListViewControl {
    private static final Log logger = LogFactory.getLog(TestVirtualListViewControl.class);

    /**
     * create by yangwm in Jan 10, 2009 2:55:30 PM
     * @param args
     */
    public static void main(String... args) {
        String baseDn = "ou=user,dc=4a,dc=4adomain";     // SunOne LDAP  
        String filter = "(objectclass=*)";                      // SunOne LDAP  
        String attribute = "a4-bid";
        String[] returnedAtts = {attribute};
        
        //String name = "ou=caspres,o=chinalife";           // Nevoll eDictory 
        //String filter = "(objectclass=*)";                // Nevoll eDictory 

        LdapContext ctx = null;
        try {
            // Open an LDAP association
            ctx = LdapContextUtil.getLdapContext();
                
            // Activate paged results
            int pageSize = 20; // 20 entries per page
            int targetOffset = 1;
            int totalSize = 0;;
            byte[] cookie = null;
            
            do {
                logger.debug("targetOffset=" + targetOffset);
                
                // Activate or Re-activate paged results
                VirtualListViewControl vctl = new VirtualListViewControl(targetOffset, 0, 0, pageSize-1, Control.CRITICAL);
                //VirtualListViewControl vctl = new VirtualListViewControl(targetOffset, pageSize, 0, pageSize-1, Control.CRITICAL);
                SortControl sctl = new SortControl(new String[] { attribute },Control.CRITICAL);
                ctx.setRequestControls(new Control[] { sctl, vctl });
                
                // SearchControls 
                SearchControls constraints = new SearchControls();
                constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
                if (returnedAtts != null) {
                    constraints.setReturningAttributes(returnedAtts);
                }
                
                //logger.debug("Perform the search");
                // Perform the search
                NamingEnumeration<SearchResult> results = ctx.search(baseDn, filter, constraints);
                
                // Iterate over a batch of search results
                int inx = 0;
                while (results != null && results.hasMore()) {
                    // Display an entry
                    SearchResult entry = results.next();
                    logger.debug((++inx) + ": " + entry.getName());
                    Attributes atts = entry.getAttributes();
                    logger.debug(atts);
        
                    // Handle the entry's response controls (if any)
                    if (entry instanceof HasControls) {
                        // ((HasControls)entry).getControls();
                    }
                }
                
                // Examine the paged results control response 
                Control[] controls = ctx.getResponseControls();
                //logger.debug(controls);
                if (controls != null) {
                    //logger.debug(controls.length);
                    for (int i = 0; i < controls.length; i++) {
                        //logger.debug(controls[i]);
                        if(controls[i] instanceof VirtualListViewResponseControl) {
                            VirtualListViewResponseControl vlvrc = (VirtualListViewResponseControl)controls[i];
                            totalSize += vlvrc.getListSize();
                            logger.debug(EntityUtil.toString(vlvrc));
                        } else {
                            // Handle other response controls (if any)
                        }
                    }
                }
                
                targetOffset += pageSize;
            } while ( targetOffset < totalSize );
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
