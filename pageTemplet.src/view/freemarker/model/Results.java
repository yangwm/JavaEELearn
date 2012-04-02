/**
 * 
 */
package view.freemarker.model;

import java.util.List;

import org.apache.commons.dbutils.PageArgument;

import json.ext.BaseInfo;


/**
 * 
 * 
 * @author yangwm May 13, 2010 1:20:56 PM
 */
public class Results extends BaseInfo {
    
    private List<User> userList;
    private User[] userArray;
    private PageArgument pageArgument;
    
    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public User[] getUserArray() {
        return userArray;
    }
    public void setUserArray(User[] userArray) {
        this.userArray = userArray;
    }
    public PageArgument getPageArgument() {
        return pageArgument;
    }
    public void setPageArgument(PageArgument pageArgument) {
        this.pageArgument = pageArgument;
    }

    /*
    public static String getJSONString(PageArgument pageArgument, List beanList) {
        JSONArray jsonArray = new JSONArray();
        for (int idx = 0; idx < beanList.size(); idx ++) {
            try {
                jsonArray.put(new JSONHelper().getJSONObjectFromBean(beanList.get(idx)));
            } catch(Exception exp) { // IllegalAccessException | java.lang.reflect.InvocationTargetException |
            }
        }
        JSONObject jsonObject = new JSONObject();
        String jsonText = "{}";
        try {
            jsonObject.put("userList", jsonArray);
            jsonObject.put("pageArgument", new JSONHelper().getJSONObjectFromBean(pageArgument));
            jsonText = jsonObject.toString();
        } catch (Exception exp) {
        }

        return jsonText;
    }
    */
    
}
