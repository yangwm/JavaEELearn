/**
 * 
 */
package test.official;

import test.SystemLogQueryPara;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author yangwm in Feb 4, 2010 1:27:30 PM
 */
public class SystemLogTest {

    /**
     * create by yangwm in Feb 4, 2010 1:27:41 PM
     * @param args
     */
    public static void main(String[] args) {
        SystemLogQueryPara sysLogPara = new SystemLogQueryPara();
        sysLogPara.setStartRowNum(1);
        sysLogPara.setEndRowNum(10);
        sysLogPara.setBeginTime("2009-12-22 13:03:25");
        sysLogPara.setEndTime("2009-12-23 13:03:25");
        
        SqlMapClient sqlMapClient = null;
        try{
            sqlMapClient = SqlMapClientUtil.getSqlMapClient();
            //System.out.println(EntityUtil.toString(((SqlMapClientImpl)sqlMapClient).getSqlExecutor()));
            sqlMapClient.queryForObject("SystemLogNum", sysLogPara);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // sqlMapClient 
        }
    }

}

/*
16:46:04,437 [main] DEBUG [test.official.SqlMapClientUtil.getSqlMapClient(36)]  - Initial SqlMapClient succefully, sqlMapClient=com.ibatis.sqlmap.engine.impl.SqlMapClientImpl@d02b51
16:46:05,453 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - Created connection 27535250.
16:46:05,453 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - {conn-100000} Connection
16:46:05,468 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - {conn-100000} Preparing Statement:       select count(0) from AMS_SYSTEM_LOG t            where                                                                                                                                                     t.OperTime >= ?                                     AND                                            t.OperTime <= ?                                                                             
16:46:05,656 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - {pstm-100001} Executing Statement:       select count(0) from AMS_SYSTEM_LOG t            where                                                                                                                                                     t.OperTime >= ?                                     AND                                            t.OperTime <= ?                                                                             
16:46:05,656 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - {pstm-100001} Parameters: [2009-12-22 13:03:25, 2009-12-23 13:03:25]
16:46:05,656 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - {pstm-100001} Types: [java.lang.String, java.lang.String]
16:46:05,703 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - {rset-100002} ResultSet
16:46:05,765 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - {rset-100002} Header: [COUNT(0)]
16:46:05,765 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - {rset-100002} Result: [344]
16:46:05,765 [main] DEBUG [com.ibatis.common.logging.jakarta.JakartaCommonsLoggingImpl.debug(27)]  - Returned connection 27535250 to pool.

*/

