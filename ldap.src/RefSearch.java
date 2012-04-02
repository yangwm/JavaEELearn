/*
 * Copyright (c) 1997.  Sun Microsystems. All rights reserved.
 * 
 * Search the directory for all people whose surname (last name) is
 * "Jensen".  Since the "sn" attribute is a caseignorestring (cis), case
 * is not significant when searching.
 *
 * Catch referrals and ask for confirmation to proceed.
 * 
 * NOTE: To exercise the referral handler in this program ensure
 *       that the target directory service returns a referral for
 *       the name specificed by Env.MY_SEARCHBASE.
 */

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.ReferralException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

class RefSearch {

public static void main(String[] args) throws IOException {

    Hashtable env = new Hashtable(5, 0.75f);
    /*
     * Specify the initial context implementation to use.
     * This could also be set by using the -D option to the java program.
     * For example,
     *   java -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory \
     *       RefSearch
     */
    env.put(Context.INITIAL_CONTEXT_FACTORY, Env.INITCTX);

    /* Specify host and port to use for directory service */
    env.put(Context.PROVIDER_URL, Env.MY_SERVICE);

    /* Throw exception when referral is encountered */
    env.put(Context.REFERRAL, "throw");

    try {
        /* get a handle to an Initial DirContext */
        DirContext ctx = new InitialDirContext(env);

        /* specify search controls to search subtree */
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        /* search for all entries with surname of Jensen */
        NamingEnumeration results;

        while (true) {
            try {
                results =
                    ctx.search(Env.MY_SEARCHBASE, Env.MY_FILTER, controls);

                /* for each entry print out its details */
                while (results != null && results.hasMore()) {
                    System.out.println((SearchResult) results.next());
                }
                break;

            } catch (ReferralException e) {

                if (! followReferral(e.getReferralInfo())) {
                    if (! e.skipReferral()) {
                        return;
                    }
                }
                /* point to the new context */
                ctx = (DirContext) e.getReferralContext();
            }
        }
    } catch (NamingException e) {
        System.err.println("RefSearch example failed.");
        e.printStackTrace();
    }
}

private static boolean followReferral(Object referralInfo) throws IOException {
    byte[] reply = new byte[4];

    System.out.println("Received referral: " + referralInfo);
    System.out.print("Follow it? [y/n]");
    System.in.read(reply);

    return (reply[0] != 'n');
}
}
