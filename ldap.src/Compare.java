/*
 * @(#)Compare.java	1.2 99/07/26
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


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

/* 
 * Use search() to compare values against values contained in entry
 * "cn=Barbara Jensen, ou=Product Development, o=Ace Industry, c=US".
 * We test to see if (1) the value "person" is one of the values in the
 * objectclass attribute (it is), and if (2) the value "xyzzy" is in the
 * objectlass attribute (it isn't, or at least, it shouldn't be).
 *
 * [equivalent to compare.c in Netscape SDK]
 *
 */
class Compare {

public static void main(String[] args) {

    Hashtable env = new Hashtable(5, 0.75f);
    /*
     * Specify the initial context implementation to use.
     * This could also be set by using the -D option to the java program.
     * For example,
     *    java -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory \
     *       Compare
     */
    env.put(Context.INITIAL_CONTEXT_FACTORY, Env.INITCTX);

    /* Specify host and port to use for directory service */
    env.put(Context.PROVIDER_URL, Env.MY_SERVICE);

    DirContext ctx = null;
    SearchControls ctls = new SearchControls();
    ctls.setSearchScope(SearchControls.OBJECT_SCOPE);
    ctls.setReturningAttributes(new String[0]);  // do not return any attrs

    try {
        /* get a handle to an Initial DirContext */
        ctx = new InitialDirContext(env);
    } catch (NamingException e) {
        System.err.println("Cannot get initial context.");
        return;
    }

    try {
        NamingEnumeration results = 
            ctx.search(Env.ENTRYDN, "objectclass=person", ctls);

        if (results != null && results.hasMoreElements()) {
            System.out.println(
                "The value \"person\" is contained in the objectclass attribute.");
        } else {
            System.out.println(
          "The value \"person\" is not contained in the objectclass attribute." );
        }
    } catch (NamingException e) {
        System.err.println("Comparison of value person failed.");
    }

    try {
        NamingEnumeration results = 
            ctx.search(Env.ENTRYDN, "objectclass=xyzzy", ctls);
        
        if (results != null && results.hasMoreElements()) {
            System.out.println(
                "The value \"xyzzy\" is contained in the objectclass attribute.");
        } else {
            System.out.println(
          "The value \"xyzzy\" is not contained in the objectclass attribute." );
        }
    } catch (NamingException e) {
        System.err.println("Comparison of value xyzzy failed.");
    }
}
}
