/**
 * 
 */
package test.official;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.EntityUtil;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

/**
 * @author yangwm in Feb 4, 2010 1:35:58 PM
 */
public class SqlMapClientUtil {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(SqlMapClientUtil.class);
    
    
    /**
     * 初始化SqlMapClient
     */
    public static SqlMapClient getSqlMapClient() {
        SqlMapClient sqlMapClient = null;
        try {
            String path = "test/SqlMapConfig.xml";
            Reader reader = Resources.getResourceAsReader(path);
            sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
            reader.close();
            logger.debug("Initial SqlMapClient succefully, sqlMapClient=" + sqlMapClient);
            logger.debug(EntityUtil.toString(((SqlMapClientImpl)sqlMapClient).getSqlExecutor()));
        } catch (IOException e) {
            logger.error("io exception during initialization", e);
        } catch (Exception e) {
            logger.error("unexpected runtime exception during initialization", e);
        }
        return sqlMapClient;
    }
}
