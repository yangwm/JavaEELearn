/*
 *
 * @(#)Search.java	1.2 99/07/26
 * 
 * Copyright 1997, 1998, 1999 Sun Microsystems, Inc. All Rights
 * Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free,
 * license to use, modify and redistribute this software in source and
 * binary code form, provided that i) this copyright notice and license
 * appear on all copies of the software; and ii) Licensee does not utilize
 * the software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE SOFTWARE
 * OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line
 * control of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and warrants
 * that it will not use or redistribute the Software for such purposes. 
 */
 
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/* 
 * Search the directory for all people whose surname (last name) is
 * "Jensen".  Since the "sn" attribute is a caseignorestring (cis), case
 * is not significant when searching.
 *
 * [equivalent to search.c in Netscape's SDK.]
 *
 */

class Search {

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
        /* get a handle to an Initial DirContext */
        DirContext ctx = new InitialDirContext(env);

        /* specify search constraints to search subtree */
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

        /* search for all entries with surname of Jensen */
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
                         System.out.println(attrId + ": " + vals.nextElement()))
                            ;
                }
            }
            System.out.println();
        }
    } catch (NamingException e) {
        System.err.println("Search example failed.");
        e.printStackTrace();
    }
}
}
