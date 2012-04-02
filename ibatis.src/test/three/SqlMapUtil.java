/**
 * 
 */
package test.three;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author yangwm in Feb 4, 2010 1:35:58 PM
 */
public class SqlMapUtil {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(SqlMapUtil.class);
    
    
    /**
     * 初始化SqlMapClient
     */
    public static SqlSessionFactory getSqlMaper() {
        SqlSessionFactory sqlMapper = null;
        try {
            String resource = "test/three/SqlMapConfig2.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlMapper = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            logger.error("io exception during initialization", e);
        } catch (Exception e) {
            logger.error("unexpected runtime exception during initialization", e);
        }
        return sqlMapper;
    }
}
