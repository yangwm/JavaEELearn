/**
 * 
 */
package test.three.dialect;


/**
 * 
 * 
 * @author yangwm Jun 8, 2010 4:35:19 PM
 */
public class PostgreSQLDialect extends Dialect{
    
    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset(){
        return true;
    }
    
    public String getLimitString(String sql, int offset, int limit) {
        StringBuilder result = new StringBuilder();
        result.append(sql).append(" limit ").append(limit).append(" offset ")
            .append(offset);

        return result.toString();
    }
}
