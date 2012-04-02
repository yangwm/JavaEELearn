/**
 * 
 */
package dppexample;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.EntityUtil;

import com.ultrapower.dpp.DPPManager;
import com.ultrapower.dpp.dao.query.LdapPageQueryParameter;
import com.ultrapower.dpp.util.page.PageSupport;

import dppexample.util.DPPManagerUtil;

/**
 * @author yangwm in Jan 22, 2010 9:59:01 AM
 */
public class UserDao {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(UserDao.class);
            
    /**
     * create by yangwm in Jan 22, 2010 9:59:01 AM
     * @param args
     */
    public static void main(String[] args) {
        try {
            DPPManagerUtil.init();
            
            String sql = " uid=717* "; // " objectclass=inetorgperson "
            Class<?> cls = User.class;
            int pageSize = 5;
            int pageNum = 1; 
            
            LdapPageQueryParameter pageParam = new LdapPageQueryParameter(cls);
            pageParam.setSql(sql);
            pageParam.setPageSize(pageSize);
            pageParam.setPageNum(pageNum);
            
            PageSupport pageSupport = DPPManager.createLdapDao().search(pageParam);
            //PageSupport pageSupport = DPPManager.createLdapDao().search(sql, cls, pageSize, pageNum);
            logger.debug(EntityUtil.toString(pageSupport));
            logger.debug(EntityUtil.toString(pageSupport.getObjList()));
            logger.debug(pageSupport.getObjList().size());
            for (Object o : pageSupport.getObjList()) {
                logger.debug(EntityUtil.toString(o));
            }
            
            List<?> objList = DPPManager.createLdapDao().search(sql, cls);
            logger.debug(EntityUtil.toString(objList));
            logger.debug(objList.size());
            for (Object o : objList) {
                logger.debug(EntityUtil.toString(o));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DPPManagerUtil.destory();
        }
    }

}
