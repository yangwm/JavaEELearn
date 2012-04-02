/**
 * 
 */
package test.three;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import dbutilsi.Book;

/**
 * 
 * 
 * @author yangwm Jun 8, 2010 5:56:05 PM
 */
public class BookDao {

    /**
     * create by yangwm in Feb 4, 2010 1:27:41 PM
     * @param args
     */
    public static void main(String[] args) {
        Book book = new Book();
        book.setId(0);
        RowBounds rowBounds = new RowBounds(0, 10);

        SqlSession sqlSession = null;
        try {
            sqlSession = SqlMapUtil.getSqlMaper().openSession();
            //System.out.println(EntityUtil.toString(((SqlMapClientImpl)sqlMapClient).getSqlExecutor()));
            
            List<Book> bookList = (List<Book>) sqlSession.selectList("selectBookList", book, rowBounds);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
