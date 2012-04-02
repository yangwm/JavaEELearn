/**
 * 
 */
package view.freemarker.test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import json.ext.JsonUtil;
import view.freemarker.ext.json.JsonHash;
import view.freemarker.model.User;
import view.freemarker.util.FreeMarkerUtil;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

/**
 * 
 * 
 * @author yangwm May 7, 2010 5:19:25 PM
 */
public class UserTest {
    
    public static TemplateModel makeSimpleHashModel() {
        User user = new User();
        user.setUserId(1015);
        user.setUserName("yangwm");
        user.setUserEmail("jxfzywm@163.com");
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("userId", user.getUserId());
        root.put("userName", user.getUserName());
        root.put("userEmail", user.getUserEmail());
        
        TemplateModel tm = new SimpleHash(root);
        return tm;
    }
    

    public static TemplateModel makeJsonHashModel(){
        //String jsonStr = "{\"userId\":1015,\"userName\":\"yangwm\",\"userEmail\":\"jxfzywm@163.com\"}";

        User user = new User();
        user.setUserId(1015);
        user.setUserName("yangwm");
        user.setUserEmail("jxfzywm@163.com");
        
        String jsonStr = user.toString();
        TemplateModel tm = new JsonHash(JsonUtil.toJSONObject(jsonStr));
        return tm;
    }


    static class SimpleHashCase {
        /**
         * @param args
         */
        public static void main(String[] args) throws Exception {
            Template template = FreeMarkerUtil.getTemplate("user.ftl", Locale.ENGLISH);
            
            TemplateModel templateModel = makeSimpleHashModel();
            
            FreeMarkerUtil.process(template, templateModel);
        }
    }
    
    static class JsonHashCase {
        /**
         * @param args
         */
        public static void main(String[] args) throws Exception {
            Template template = FreeMarkerUtil.getTemplate("user.ftl", Locale.CHINA);
            
            TemplateModel templateModel = makeJsonHashModel();
            
            FreeMarkerUtil.process(template, templateModel);
        }
    }


}

/*
SimpleHashCase:

<html>
  <head>
    <title>Welcome!</title>
  </head>
  <body>
    <h1>Welcome yangwm!, jxfzywm@163.com</h1>
    <h1>your id is 1,015</h1>
  </body>
</html>

*/


/*
JsonHashCase:

<html>
  <head>
    <title>Welcome!</title>
  </head>
  <body>
    <h1>欢迎 yangwm!, jxfzywm@163.com</h1>
    <h1>您的唯一标识是 1015</h1>
  </body>
</html>

*/
