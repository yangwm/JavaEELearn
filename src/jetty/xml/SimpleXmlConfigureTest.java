package jetty.xml;

import java.net.URL;

import org.mortbay.xml.XmlConfiguration;

/**
 * simple jetty xml configure test
 * 
 * @author yangwm Oct 21, 2010 3:13:34 PM
 */
public class SimpleXmlConfigureTest {
    public static void main(String[] args) throws Exception {

        URL url = SimpleXmlConfigureTest.class.getClassLoader().getResource("simpleconfigure.xml");
        XmlConfiguration configuration = new XmlConfiguration(url);

        SimpleXmlConfigure simpleXmlConfigure = new SimpleXmlConfigure();
        configuration.configure(simpleXmlConfigure);
        //or SimpleXmlConfigure simpleXmlConfigure = (SimpleXmlConfigure) configuration.configure();
        System.out.println("configuration.getIdMap()=" + configuration.getIdMap());
        System.out.println("simpleXmlConfigure=" + simpleXmlConfigure.getPort());
    }

}
