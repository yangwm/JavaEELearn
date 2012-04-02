/*
 * Copyright (c) 1997.  Sun Microsystems. All rights reserved.
 * 
 * Environment properties for ldap example programs.
 * [equivalent to examples.h in Netscape's SDK.]
 */


//"ldap://127.0.0.1:20000/", "", "cn=admin", "12345678"
public class Env {

/*
 * Initial context implementation to use.
 */
public static String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";

/*
 * Host name and port number of LDAP server
 */
public static String MY_SERVICE = "ldap://localhost:20000";

/*
 * DN of directory manager entry.  This entry should have write access to
 * the entire directory.
 */
public static String MGR_DN = "cn=Directory Manager, o=Ace Industry, c=US";

/*
 * Password for manager DN.
 */
public static String MGR_PW = "secret99";

/*
 * Subtree to search
 */
public static String MY_SEARCHBASE = "o=Ace Industry, c=US";

/*
 * Subtree to modify
 */
public static String MY_MODBASE = "o=Ace Industry, c=US";

/* 
 * Filter to use when searching.  This one searches for all entries with the
 * surname (last name) of "Jensen".
 */
public static String MY_FILTER = "(sn=Jensen)";

/*
 * Entry to retrieve
 */
public static String ENTRYDN = "cn=Barbara Jensen, ou=Product Development, o=Ace Industry, c=US";
};

