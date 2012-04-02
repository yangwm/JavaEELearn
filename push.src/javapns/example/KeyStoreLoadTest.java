/**
 * 
 */
package javapns.example;

import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * 
 * 
 * @author yangwm Oct 10, 2010 4:07:27 PM
 */
public class KeyStoreLoadTest {
    
    private static String certificate = "D:/study/tempProjects/yangwmProject/JavaEELearn/push.src/javapns/example/certificate2.p12";
    private static String passwd = "123";

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12"); // KeyStore.getInstance("PKCS12", "BC");
        char[] pwd = passwd.toCharArray(); 
        
        ks.load(new FileInputStream(certificate), pwd);
    }

}
