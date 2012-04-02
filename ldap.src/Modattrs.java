/*
 * @(#)Modattrs.java	1.2 99/07/26
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



import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

/* 
 * Modify an entry:
 *  - replace any existing values in the "mail" attribute with "babs@ace.com"
 *  - add a new value to the "description" attribute
 *
 * [equivalent to modattrs.c in Netscape SDK]
 */
class Modattrs {

public static void main(String[] args) {

    Hashtable env = new Hashtable(5, 0.75f);
    /*
     * Specify the initial context implementation to use.
     * This could also be set by using the -D option to the java program.
     * For example,
     *   java -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory \
     *       Modattrs
     */
    env.put(Context.INITIAL_CONTEXT_FACTORY, Env.INITCTX);

    /* Specify host and port to use for directory service */
    env.put(Context.PROVIDER_URL, Env.MY_SERVICE);

    /* specify authentication information */
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, Env.MGR_DN);
    env.put(Context.SECURITY_CREDENTIALS, Env.MGR_PW);

    try {
        /* get a handle to an Initial DirContext */
        DirContext ctx = new InitialDirContext(env);

        /* construct the list of modifications to make */
        ModificationItem[] mods = new ModificationItem[2];

        Attribute mod0 = new BasicAttribute("mail", "babs@eng");
        // Update mail attribute
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);

        // Add another value to description attribute
        Attribute mod1 = 
            new BasicAttribute("description", 
                          "This entry was modified with the Modattrs program on " +
                          (new Date()).toString());
        mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);

         /* Delete the description attribute altogether */
        /*
        Attribute mod1 =  new BasicAttribute("description");
        mods[2] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod1);
	*/

        /* make the change */
        ctx.modifyAttributes(Env.ENTRYDN, mods);
        System.out.println( "modification was successful." );

    } catch (NamingException e) {
        System.err.println("modification failed. " + e);
    }
}
}

