/**
 * 
 */
package dppexample.util;

import com.ultrapower.dpp.DPPManager;

/**
 * @author yangwm in Jan 22, 2010 10:06:27 AM
 */
public class DPPManagerUtil {

    /**
     * 
     * create by yangwm in Jan 22, 2010 10:12:54 AM
     */
    public static void init(){
        String dppconfigDir = "D:/study/tempProject/JavaLearn/dpp.src/dppconfig/";
        init(dppconfigDir + "LDAPConfig.xml", dppconfigDir + "mapping");
    }
    
    /**
     * 
     * create by yangwm in Jan 22, 2010 10:09:04 AM
     * @param dppConfigPath
     * @param dppMappingPath
     */
    public static void init(String dppConfigPath, String dppMappingPath){
        DPPManager.setLdapServerConfigPath(dppConfigPath);
        DPPManager.setMappingFolderPath(dppMappingPath);
        DPPManager.initConfig();
    }

    /**
     * 
     * create by yangwm in Jan 22, 2010 10:09:08 AM
     */
    public static void destory(){
        DPPManager.clear();
    }
    
}
