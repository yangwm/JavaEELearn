/**
 * 
 */
package view.freemarker.util;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * FreeMarker工具类 
 * 
 * @author yangwm May 9, 2010 1:02:44 PM
 */
public class FreeMarkerUtil {
    
    public final static String DEFAULT_ENCODE = "UTF-8";
    public final static String DEFAULT_PATH = "/view/freemarker/templates/";

    /**
     * 从上面指定的模板目录中加载对应的模板文件
     * 
     * @param fileName
     * @return
     */
    public static Template getTemplate(String fileName) {
        return getTemplate(fileName, null);
    }
    
    /**
     * 从上面指定的模板目录中加载对应的模板文件
     * 
     * @param fileName
     * @param locale
     * @return
     */
    public static Template getTemplate(String fileName, Locale locale) {
        return getTemplate(getConfiguration(DEFAULT_PATH), fileName, locale);
    }
    
    /**
     * 从上面指定的模板目录中加载对应的模板文件
     * 
     * @param cfg
     * @return
     */
    public static Template getTemplate(Configuration cfg, String fileName, Locale locale) {
        Template template = null;
        try {
            if (locale != null) {
                template = cfg.getTemplate(fileName, locale);
            } else {
                template = cfg.getTemplate(fileName);
            }
            template.setEncoding(DEFAULT_ENCODE);
            //template.setNumberFormat(numberFormat)
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return template;
    }

    
    /**
     * 创建配置, 指定模板存放的路径
     * 
     * @return
     */
    public static Configuration getConfiguration(String path) {
        Configuration cfg = null;
        try {
            cfg = new Configuration();
            cfg.setClassForTemplateLoading(FreeMarkerUtil.class, path);
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setEncoding(Locale.getDefault(), DEFAULT_ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cfg;
    }

    
    /**
     * 将生成的内容打印到控制台中
     * 
     * @param template
     * @param templateModel
     */
    public static void process(Template template, Object templateModel) {
        try {
            Writer out = new OutputStreamWriter(System.out);
            //Writer out = new FileWriter("d:/user.html");
            template.process(templateModel, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
