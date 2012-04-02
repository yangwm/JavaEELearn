
package test.three.dialect;

/**
 * 数据库分页Dialect
 * 
 * @author yangwm Jun 8, 2010 4:36:58 PM
 */
public abstract class Dialect {
	
    public boolean supportsLimit(){
    	return false;
    }

    public boolean supportsLimitOffset() {
    	return supportsLimit();
    }
    
    /**
     * 将sql变成分页sql语句 
     * 
     * @param sql
     * @param offset
     * @param limit
     * @return
     */
    public abstract String getLimitString(String sql, int offset, int limit);

}
