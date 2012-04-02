
package util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * 
 * @author yangwm in Jan 15, 2009 10:00:12 PM
 */
public class LdapContextUtil {
    
    /**
     * 
     * create by yangwm in Jan 15, 2009 10:00:16 PM
     * @return
     */
    public static LdapContext getLdapContext() {
        //return getLdapContext("ldap://100.9.0.40:20000/", "", "cn=admin", "123456789"); // SunOne LDAP 40, ou=user,dc=4a,dc=4adomain consumes 140 millisecond  
        //return getLdapContext("ldap://100.9.0.40:20000/", "dc=4a,dc=4adomain", "cn=admin", "123456789"); // SunOne LDAP 40, ou=user consumes 141 millisecond  
        //return getLdapContext("ldap://100.10.0.150:20000/", "", "cn=admin", "12345678"); // SunOne LDAP localhost, ou=user,dc=4a,dc=4adomain consumes 9500 millisecond 
        //return getLdapContext("ldap://127.0.0.1:20000/", "dc=4a,dc=4adomain", "cn=admin", "12345678"); // SunOne LDAP localhost, ou=user consumes 9500 millisecond 
        return getLdapContext("ldap://10.253.129.15:389/", "", "cn=admin,o=novell", "novell"); // Nevoll eDictory 
    }
    
    /**
     * 
     * create by yangwm in Jan 15, 2009 10:00:20 PM
     * @param url
     * @param nc
     * @param managedn
     * @param password
     * @return
     */
    public static LdapContext getLdapContext(String url, String nc, String managedn, String password) {    
        // Set up environment for creating initial context
        Hashtable<String, String> env = new Hashtable<String, String>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url + nc);
        env.put(Context.AUTHORITATIVE, "true");
        //env.put("java.naming.ldap.version", "3");
        
        //env.put(LdapContext.CONTROL_FACTORIES, "com.sun.jndi.ldap.ctl.ResponseControlFactory");
        
        // specify authentication information, Required for using VLV control against DS 4.1
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, managedn);
        env.put(Context.SECURITY_CREDENTIALS, password);
        
        LdapContext ctx = null;
        try {
            // Create initial context with no connection request controls, actual connect
            ctx = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ctx;
    }
    
    /**
     * 
     * create by yangwm in Jan 15, 2009 10:00:24 PM
     * @param ctx
     */
    public static void close(LdapContext ctx) {
        if(ctx != null) { 
            try {
                ctx.close();
            } catch(NamingException e) {
                e.printStackTrace();
            }
        }
    }
    
}
