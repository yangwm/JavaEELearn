/**
 * 
 */
package view.freemarker.model;

import json.ext.BaseInfo;

/**
 * 
 * 
 * @author yangwm May 7, 2010 5:16:19 PM
 */
public class User extends BaseInfo {
    
    private long userId;
    private String userName;
    private String userEmail ;
    
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    
}
