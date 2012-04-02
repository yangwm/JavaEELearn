/**
 * 
 */
package dppexample;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwm in Jan 22, 2010 10:26:27 AM
 */
public class BaseBusinessOBJ {

    private static final long serialVersionUID = 1L;

    private String province;

    private String city;
    
    private String cityInfo;

    private String createUserKey;

    private String createDate;

    private String modifyDate;

    private String modifyUserKey;

    private String id;

    private String name;

    private String description;

    private List errorMessageSet = new ArrayList();

    /**
     * @roseuid 4642B9A500CB
     */
    public BaseBusinessOBJ() {

    }

    public boolean equals(Object o) {
        if (!(o instanceof BaseBusinessOBJ)) {
            return false;
        } else {
            BaseBusinessOBJ obj = (BaseBusinessOBJ) o;
            if (obj.getId().equals(this.getId())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public int hashCode() {
        if (this.id==null) return 0;
        
        return this.id.hashCode();
    }

    public String getCreateUserKey() {
        return createUserKey;
    }

    public void setCreateUserKey(String createUserKey) {
        this.createUserKey = createUserKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getErrorMessageSet() {
        return errorMessageSet;
    }

    public void setErrorMessageSet(List errorMessageSet) {
        this.errorMessageSet = errorMessageSet;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUserKey() {
        return modifyUserKey;
    }

    public void setModifyUserKey(String modifyUserKey) {
        this.modifyUserKey = modifyUserKey;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        if (city != null && city.length()>4) {
            this.cityInfo = city.substring(3, city.indexOf(","));
        }
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityInfo() {
        if (city != null && city.length()>4) {
            return city.substring(3, city.indexOf(","));
        } else {
            return "";
        }
        
    }
}
