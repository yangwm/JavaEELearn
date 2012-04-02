/**
 * 
 */
package dppexample;

import java.util.HashSet;

/**
 * @author yangwm in Jan 22, 2010 10:24:00 AM
 */
public class User extends BaseBusinessOBJ {

    private static final long serialVersionUID = 1L;
    
    //用户ID
    private String uid;

    //用户类型  0:内部用户/1:外部用户
    private String userType;

    //密码
    private String userPassword;

    //用户 姓
    private String sn;

    //性别
    private String gender;

    //邮箱
    private String email;

    //出生日期
    private String birthday;

    //电话
    private String telephoneNumber;
    
    //身份证号
    private String idCardNum;

    //手机
    private String mobile;

    //帐号开始生效时间
    private String startTime;

    //帐号结束生效时间
    private String endTime;

    //用户状态 0-未激活 1-正常/锁定 锁定状态根据锁定时间进行判断 3-已注销
    private Integer status;

    //密码修改日期
    private String passwordModifiedDate;

    //照片
    private String photo;

    //所属用户组
    private HashSet memberOf;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPasswordModifiedDate() {
        return passwordModifiedDate;
    }

    public void setPasswordModifiedDate(String passwordModifiedDate) {
        this.passwordModifiedDate = passwordModifiedDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public HashSet getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(HashSet memberOf) {
        this.memberOf = memberOf;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
}
