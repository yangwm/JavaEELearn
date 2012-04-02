/**
 * 
 */
package view.freemarker.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import json.ext.JsonUtil;

import org.apache.commons.dbutils.PageArgument;

import view.freemarker.ext.json.JsonHash;
import view.freemarker.model.Results;
import view.freemarker.model.User;
import view.freemarker.util.FreeMarkerUtil;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

/**
 * 
 * 
 * @author yangwm May 13, 2010 1:01:31 PM
 */
public class ResultsTest {
    
    public static TemplateModel makeSimpleHashModel() {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 2; i++) {
            User user = new User();
            user.setUserId(1015 + i);
            user.setUserName("yangwm" + i);
            user.setUserEmail("jxfzywm" + i + "@163.com");
            userList.add(user);
        }
        PageArgument pageArgument = new PageArgument();
        pageArgument.setCurPage(2);
        pageArgument.setPageSize(10);
        pageArgument.setTotalPage(5);
        pageArgument.setTotalRow(48);
    
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("userList", userList);
        root.put("userArray", userList.toArray());
        root.put("pageArgument", pageArgument);
        
        TemplateModel tm = new SimpleHash(root);
        return tm;
    }
    

    public static TemplateModel makeJsonHashModel(){
        //String jsonStr = "{\"userList\":[{\"userId\":1015,\"userName\":\"yangwm0\",\"userEmail\":\"jxfzywm0@163.com\"},{\"userId\":1016,\"userName\":\"yangwm1\",\"userEmail\":\"jxfzywm1@163.com\"}],\"userArray\":[{\"userId\":1015,\"userName\":\"yangwm0\",\"userEmail\":\"jxfzywm0@163.com\"},{\"userId\":1016,\"userName\":\"yangwm1\",\"userEmail\":\"jxfzywm1@163.com\"}],\"pageArgument\":{\"curPage\":2,\"pageSize\":10,\"totalRow\":48,\"totalPage\":5}}";
        
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 2; i++) {
            User user = new User();
            user.setUserId(1015 + i);
            user.setUserName("yangwm" + i);
            user.setUserEmail("jxfzywm" + i + "@163.com");
            userList.add(user);
        }
        PageArgument pageArgument = new PageArgument();
        pageArgument.setCurPage(2);
        pageArgument.setPageSize(10);
        pageArgument.setTotalPage(5);
        pageArgument.setTotalRow(48);
        
        Results results = new Results();
        results.setUserList(userList);
        results.setUserArray(userList.toArray(new User[]{}));
        results.setPageArgument(pageArgument);
        
        String jsonStr = results.toString();
        System.out.println(jsonStr);
        TemplateModel tm = new JsonHash(JsonUtil.toJSONObject(jsonStr));
        return tm;
    }

    static class SimpleHashCase {
        /**
         * @param args
         */
        public static void main(String[] args) throws Exception {
            Template template = FreeMarkerUtil.getTemplate("results.ftl");
            
            TemplateModel templateModel = makeSimpleHashModel();
            
            FreeMarkerUtil.process(template, templateModel);
        }
    }
    
    static class JsonHashCase {
        /**
         * @param args
         */
        public static void main(String[] args) throws Exception {
            Template template = FreeMarkerUtil.getTemplate("results.ftl");
            
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
    <table>
        <h1>userList yangwm0!, jxfzywm0@163.com, your id is 1,015</h1>
    <h1>userList yangwm1!, jxfzywm1@163.com, your id is 1,016</h1>
    <h1>userArray yangwm0!, jxfzywm0@163.com, your id is 1,015</h1>
    <h1>userArray yangwm1!, jxfzywm1@163.com, your id is 1,016</h1>
    </table>
    <table>
    <h1>curPage:2!, pageSize:10, totalRow:48, totalPage:5</h1>
    </table>
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
    <table>
        <h1>userList yangwm0!, jxfzywm0@163.com, your id is 1015</h1>
    <h1>userList yangwm1!, jxfzywm1@163.com, your id is 1016</h1>
    <h1>userArray yangwm0!, jxfzywm0@163.com, your id is 1015</h1>
    <h1>userArray yangwm1!, jxfzywm1@163.com, your id is 1016</h1>
    </table>
    <table>
    <h1>curPage:2!, pageSize:10, totalRow:48, totalPage:5</h1>
    </table>
  </body>
</html>

*/
