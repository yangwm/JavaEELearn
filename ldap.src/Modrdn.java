/*
 * @(#)Modrdn.java	1.2 99/07/26
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
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/*
 * Modify the RDN (relative distinguished name) of an entry.  In this
 * example, we change the dn "cn=Jacques Smith, o=Ace Industry, c=US"
 * to "cn=Jacques M Smith, o=Ace Industry, c=US".
 *
 * Since it is an error to either (1) attempt to modrdn an entry which
 * does not exist, or (2) modrdn an entry where the destination name
 * already exists, we take some steps, for this example, to make sure
 * we'll succeed.  We (1) add "cn=Jacques Smith" (if the entry exists,
 * we just ignore the error, and (2) delete "cn=Jacques M Smith" (if the
 * entry doesn't exist, we ignore the error).
 *
 * After renaming, we add back the attribute "Jacques Smith" into the cn
 * attribute.
 *
 * [based on modrdn.c of Netscape  SDK]
 */
class Modrdn {

public static void main(String[] args) {

    /* Values we'll use in creating the entry */
    Attribute objClasses = new BasicAttribute("objectclass");
    objClasses.add("top");
    objClasses.add("person");
    objClasses.add("organizationalPerson");
    objClasses.add("inetOrgPerson");

    Attribute cn = new BasicAttribute("cn", "Jacques Smith");
    Attribute sn = new BasicAttribute("sn", "Smith");
    Attribute givenNames = new BasicAttribute("givenname", "Jacques");

    /* Specify the DN we're adding */
    String dn = "cn=Jacques Smith, " + Env.MY_MODBASE;
    /* the destination DN */
    String ndn = "cn=Jacques M Smith, " + Env.MY_MODBASE;
    /* the new RDN */
    String nrdn = "cn=Jacques M Smith";

    Hashtable env = new Hashtable(5, 0.75f);
    /*
     * Specify the initial context implementation to use.
     * This could also be set by using the -D option to the java program.
     * For example,
     *   java -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory \
     *       Modrdn
     */
    env.put(Context.INITIAL_CONTEXT_FACTORY, Env.INITCTX);

    /* Specify host and port to use for directory service */
    env.put(Context.PROVIDER_URL, Env.MY_SERVICE);

    /* specify authentication information */
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, Env.MGR_DN);
    env.put(Context.SECURITY_CREDENTIALS, Env.MGR_PW);

    DirContext ctx = null;

    try {
        /* get a handle to an Initial DirContext */
        ctx = new InitialDirContext(env);
        Attributes orig = new BasicAttributes();
        orig.put(objClasses);
        orig.put(cn);
        orig.put(sn);
        orig.put(givenNames);

        /* Add the entry */
        ctx.createSubcontext(dn, orig);
        System.out.println( "Added entry " + dn + ".");

    } catch (NameAlreadyBoundException e) {
        /* If entry exists already, fine.  Ignore this error. */
        System.out.println("Entry " + dn + " already exists, no need to add");
    } catch (NamingException e) {
        System.err.println("Modrdn: problem adding entry." + e);
        System.exit(1);
    }

    try {
        /* Delete the destination entry, for this example */
        ctx.destroySubcontext(ndn);
        System.out.println( "Deleted entry " + ndn + "." );

    } catch (NameNotFoundException e) {
        /* If entry does not exist, fine.  Ignore this error. */
        System.out.println( "Entry " + ndn + " is not in the directory.  " +
                    "No need to delete.");
    } catch (NamingException e) {
        System.err.println("Modrdn: problem deleting entry." + e);
        System.exit(1);
    }


    /* Do the modrdn operation */
    try {
        ctx.rename(dn, ndn);
        System.out.println("The modrdn operation was successful.  Entry " +
                           dn + " has been changed to " + ndn + ".");
    } catch (NamingException e) {
        System.err.println("Modify operation failed." + e);
    }
}
}
