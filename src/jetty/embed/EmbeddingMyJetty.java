/**
 * 
 */
package jetty.embed;

import java.io.FileInputStream;

import org.mortbay.jetty.Server;
import org.mortbay.xml.XmlConfiguration;

/**
 * 
 * @author yangwm Oct 22, 2010 4:28:10 PM
 */
public class EmbeddingMyJetty {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        //XmlConfiguration configuration = new XmlConfiguration(new File("myJetty.xml").toURL());
        XmlConfiguration configuration = new XmlConfiguration(new FileInputStream("myJetty.xml"));
        configuration.configure(server);
        server.start();
    }

}
