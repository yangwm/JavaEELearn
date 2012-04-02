/*
 * Copyright (c) 1997.  Sun Microsystems. All rights reserved.
 * 
 * Performs a subtree search using the virtual-list-view and
 * server-side sort LDAP controls.
 */

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.HasControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import javax.naming.ldap.SortResponseControl;

import com.sun.jndi.ldap.ctl.VirtualListViewControl;
import com.sun.jndi.ldap.ctl.VirtualListViewResponseControl;

class SortedView {

public static void main(String[] args) {

    Hashtable env = new Hashtable(5, 0.75f);
    /*
     * Specify the initial context implementation to use.
     * This could also be set by using the -D option to the java program.
     * For example,
     *   java -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory \
     *     Search
     */
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

    /* Specify host and port to use for directory service */
    env.put(Context.PROVIDER_URL, Env.MY_SERVICE);

    try {
        /* get a handle to an InitialLdapContext */
        LdapContext ctx = new InitialLdapContext(env, null);

        /* specify search constraints to search subtree */
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        constraints.setReturningAttributes(new String[]{ "dn" });

	if (args.length != 3) {

	    System.err.println("usage: java SortedView <sort-by> <percentage-mark> <size-of-view>");
	    System.err.println("");
	    System.err.println("where <sort-by> is the attribute ID to sort by,");
	    System.err.println("  and <percentage-mark> is a number in the range 0 to 100,");
	    System.err.println("  and <size-of-view> is the number of entries to return.");
	    System.err.println("");
	    System.err.println("example: to view 20 entries at the 50% mark sorted by the 'uid' attribute:");
	    System.err.println("   java SortedView uid 50 20");
	    System.exit(-1);
	}
	
	/*
         * Sort and retrieve entries at a specific position in the list.
         */
        ctx.setRequestControls(new Control[]{
		new SortControl(new String[]{ args[0] }, Control.CRITICAL),
		new VirtualListViewControl(Integer.parseInt(args[1]),
					   Integer.parseInt(args[2]),
					   Control.CRITICAL),
	});

        /* perform the search */
        NamingEnumeration results
            = ctx.search(Env.MY_SEARCHBASE, "("+args[0]+"=*)", constraints);

        /* for each entry print out name + all attrs and values */
        while (results != null && results.hasMore()) {
            SearchResult si = (SearchResult)results.next();

            /* print its name */
            System.out.println("name: " + si.getName());

            Attributes attrs = si.getAttributes();
            if (attrs == null) {
                System.out.println("No attributes");
            } else {
                /* print each attribute */
                for (NamingEnumeration ae = attrs.getAll();
                     ae.hasMoreElements();) {
                    Attribute attr = (Attribute)ae.next();
                    String attrId = attr.getID();

                    /* print each value */
                    for (Enumeration vals = attr.getAll();
                         vals.hasMoreElements();
                         System.out.println(attrId + ": " + vals.nextElement()))
                            ;
                }
            }
	    // examine the response controls (if any)
	    if (si instanceof HasControls) {
		parseControls(((HasControls)si).getControls());
	    }
            System.out.println();
        }
	// examine the response controls (if any)
	parseControls(ctx.getResponseControls());

	ctx.close();

    } catch (NamingException e) {
        System.err.println("Sorted-View failed.");
        e.printStackTrace();
    } catch (java.io.IOException e) {
        System.err.println("Sorted-View failed.");
        e.printStackTrace();
    }
}


    static void parseControls(Control[] controls) throws NamingException {

	if (controls != null) {

	    for (int i = 0; i < controls.length; i++) {

		if (controls[i] instanceof SortResponseControl) {
		    SortResponseControl src = (SortResponseControl)controls[i];
		    if (src.isSorted()) {
			System.out.println("Sorted-Search completed successfully");
		    } else {
			System.out.println("Sorted-Search did not complete successfully: error (" + src.getResultCode() + ") on attribute '" + src.getAttributeID() + "'");
			throw src.getException();
		    }
		} else 
		    if (controls[i] instanceof VirtualListViewResponseControl) {

		    int rc;
		    VirtualListViewResponseControl vlvrc =
			(VirtualListViewResponseControl)controls[i];

		    if ((rc = vlvrc.getResultCode()) == 0) {
			System.out.println("Sorted-View completed successfully");
		    } else {
			System.out.println("Sorted-View did not complete successfully: error " + rc);
		    }

		} else {
		    Control c = controls[i];
			if (c.isCritical()) {
			    System.out.println("Received critical control: "+ c.getID());
			} else {
			    System.out.println("Received non-critical control: "+ c.getID());
			}
		}
	    }
	}

    }
}
