/*
 * Copyright (c) 1997.  Sun Microsystems. All rights reserved.
 * 
 * Performs a subtree search using the paged-results LDAP control.
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
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.sun.jndi.ldap.ctl.PagedResultsControl;
import com.sun.jndi.ldap.ctl.PagedResultsResponseControl;

class PagedSearch {

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

	if (args.length != 1) {

	    System.err.println("usage: java PagedSearch <size-of-page>");
	    System.err.println("");
	    System.err.println("where <size-of-page> is the number of entries to return per page.");
	    System.exit(-1);
	}
	int pageSize = Integer.parseInt(args[0]);
	byte[] cookie = null;
	
    /*
     * setup paged results control
     */
    ctx.setRequestControls(new Control[]{
	new PagedResultsControl(pageSize) });

        /* perform the search */
	do {

            NamingEnumeration results
		= ctx.search(Env.MY_SEARCHBASE, Env.MY_FILTER, constraints);

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
                             System.out.println(attrId + ": " +
				vals.nextElement()))
                                ;
                    }
                }
                System.out.println();
            }
	    // examine the response controls
	    cookie = parseControls(ctx.getResponseControls());

            // reset the paged results control
            ctx.setRequestControls(new Control[]{
		new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });

	} while ((cookie != null) && (cookie.length != 0));

	ctx.close();

    } catch (NamingException e) {
        System.err.println("PagedSearch failed.");
        e.printStackTrace();
    } catch (java.io.IOException e) {
        System.err.println("Sorted-View failed.");
        e.printStackTrace();
    }
}


    static byte[] parseControls(Control[] controls) throws NamingException {

	byte[] cookie = null;

	if (controls != null) {

	    for (int i = 0; i < controls.length; i++) {

		if (controls[i] instanceof PagedResultsResponseControl) {
		    PagedResultsResponseControl prrc =
			(PagedResultsResponseControl)controls[i];

		    int resultSize = prrc.getResultSize();

		    if (resultSize != 0) {
		        System.out.println("***************** END-OF-PAGE " +
			    "(result size: " + resultSize +
			    ") *****************\n");
		    } else {
		        System.out.println("***************** END-OF-PAGE " +
			    "(result size: unknown) ***************\n");
		    }

		    cookie = prrc.getCookie();
		}
	    }
	}

	return (cookie == null) ? new byte[0] : cookie;
    }
}
