/**
 * 
 */
package test.three.dialect;


/**
 * 
 * 
 * @author yangwm Jun 8, 2010 4:38:01 PM
 */
public class OracleDialect extends Dialect {
    
    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset() {
        return true;
    }
    
    public String getLimitString(String sql, int offset, int limit) {
        StringBuilder result = new StringBuilder();
        result.append("select ").append("*")
        .append("\nfrom (select ").append("temp.*").append(", rownum row_num ")
        .append("\n      from (").append(sql).append(") temp where rownum <= ")
            .append(offset + limit)
        .append("\n) where row_num > ").append(offset);

        return result.toString();
    }

}

