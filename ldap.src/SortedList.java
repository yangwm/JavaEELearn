/*
 * Copyright (c) 1999.  Sun Microsystems. All rights reserved.
 *
 * Displays the contents of a named context, sorted by attrID.
 *     java SortedList attrID
 */
 
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.sun.jndi.ldap.ctl.SortControl;

class SortedList {
    static void printNameList(String msg, NamingEnumeration nl) {
	System.out.println(msg);
	if (nl == null) 
	    System.out.println("No items in name list");
	else {
	    try {
		while (nl.hasMore())
		    System.out.println(nl.next());
	    } catch (NamingException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {
	Hashtable env = new Hashtable(5, 0.75f);
	/*
         * Specify the initial context implementation to use.
	 * This could also be set by using the -D option to the java program.
	 * For example,
	 *   java -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory \
	 *     Search
	 */
	env.put(Context.INITIAL_CONTEXT_FACTORY, Env.INITCTX);

	/* Specify host and port to use for directory service */
	env.put(Context.PROVIDER_URL, Env.MY_SERVICE);

	try {
	    // Get a handle to an LdapContext for setting controls
	    LdapContext ctx = new InitialLdapContext(env, null);

	    if (args.length != 1) {
		System.err.println("usage: java SortedList key");
		System.exit(-1);
	    }

	    String attrID = args[0];

	    // Create "critical" sort control using attrID supplied
	    Control sortCtl = 
		new SortControl(new String[]{attrID}, Control.CRITICAL);

	    // Set request control to contain SortControl
	    ctx.setRequestControls(new Control[]{sortCtl});

	    // Do list as before
	    printNameList(Env.MY_SEARCHBASE, 
		ctx.list(Env.MY_SEARCHBASE));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
