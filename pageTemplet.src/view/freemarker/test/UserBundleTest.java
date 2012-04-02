/**
 * 
 */
package view.freemarker.test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import json.ext.JsonUtil;
import view.freemarker.ext.json.JsonHash;
import view.freemarker.model.User;
import view.freemarker.util.FreeMarkerUtil;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

/**
 * 
 * 
 * @author yangwm May 14, 2010 10:45:12 AM
 */
public class UserBundleTest {
    
    public static Object makeSimpleMapModel(Locale locale) {
        User user = new User();
        user.setUserId(1015);
        user.setUserName("yangwm");
        user.setUserEmail("jxfzywm@163.com");

        ResourceBundle resourceBundle = ResourceBundle.getBundle("view/freemarker/templates/freemarkerResources", locale);
        //System.out.println(locale + ", " + resourceBundle.getLocale());
        ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("user", user);
        root.put("bundle", resourceBundleModel);
        
        return root;
    }
    

    public static Object makeJsonMapModel(Locale locale){
        String jsonStr = "{\"userId\":1015,\"userName\":\"yangwm\",\"userEmail\":\"jxfzywm@163.com\"}";

        /*User user = new User();
        user.setUserId(1015);
        user.setUserName("yangwm");
        user.setUserEmail("jxfzywm@163.com");
        
        String jsonStr = user.toString();*/
        TemplateModel userModel = new JsonHash(JsonUtil.toJSONObject(jsonStr));
        
        ResourceBundle resourceBundle = ResourceBundle.getBundle("view/freemarker/templates/freemarkerResources", locale);
        //System.out.println(locale + ", " + resourceBundle.getLocale());
        ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("user", userModel);
        root.put("bundle", resourceBundleModel);
        
        return root;
    }


    static class SimpleMapCase {
        /**
         * @param args
         */
        public static void main(String[] args) throws Exception {
            Template template = FreeMarkerUtil.getTemplate("userbundle.ftl");
            
            Object templateModel = makeSimpleMapModel(Locale.ENGLISH);
            
            FreeMarkerUtil.process(template, templateModel);
        }
    }
    
    static class JsonMapCase {
        /**
         * @param args
         */
        public static void main(String[] args) throws Exception {
            Template template = FreeMarkerUtil.getTemplate("userbundle.ftl");
            
            Object templateModel = makeJsonMapModel(Locale.CHINA);

            FreeMarkerUtil.process(template, templateModel);
        }
    }

}

/*
SimpleMapCase:

<html>
  <head>
    <title>Welcome!</title>
  </head>
  <body>
    <h1>欢迎(Bundle)  yangwm!, jxfzywm@163.com</h1>
    <h1>您的唯一标识是(Bundle) 1,015</h1>
  </body>
</html>


*/

/*
JsonMapCase:

<html>
  <head>
    <title>Welcome!</title>
  </head>
  <body>
    <h1>欢迎(Bundle)  yangwm!, jxfzywm@163.com</h1>
    <h1>您的唯一标识是(Bundle) 1015</h1>
  </body>
</html>


*/

