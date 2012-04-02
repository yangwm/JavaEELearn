/**
 * 
 */
package dppexample.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DPPManager工具类测试
 * 
 * @author yangwm in Jan 29, 2010 3:54:18 PM
 */
public class DPPManagerUtilTest {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(DPPManagerUtilTest.class);

    /**
     * create by yangwm in Jan 29, 2010 3:51:02 PM
     * @param args
     */
    public static void main(String[] args) {
        try {
            logger.debug("DPPManagerUtilTest");
            DPPManagerUtil.init();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DPPManagerUtil.destory();
        }

    }

}