/**
 * 
 */
package test.three;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import test.SystemLog;
import test.SystemLogQueryPara;

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
        sysLogPara.setRowCount(10);
        //sysLogPara.setBeginTime("2009-12-22 13:03:25");
        //sysLogPara.setEndTime("2009-12-23 13:03:25");
        
        RowBounds rowBounds = new RowBounds(0, 10);

        SqlSession sqlSession = null;
        try {
            sqlSession = SqlMapUtil.getSqlMaper().openSession();
            //System.out.println(EntityUtil.toString(((SqlMapClientImpl)sqlMapClient).getSqlExecutor()));
            
            List<SystemLog> sysLogList = (List<SystemLog>) sqlSession.selectList("querySysLogList", sysLogPara, rowBounds);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

}

/*

*/

