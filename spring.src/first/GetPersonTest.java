/**
 * 
 */
package first;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * concurrent get bean test will be java.lang.NullPointerException
 * 
 * @see https://jira.springsource.org/browse/
 * @author yangwm Jun 12, 2010 1:31:50 PM 
 */
public class GetPersonTest {
    
    /** get spring bean container applicationContext **/
    private static final ApplicationContext context = new ClassPathXmlApplicationContext("first/first.xml");

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1000; i++) {
            new Thread(new PersonRun(i)).start();
        }
    }

    /**
     * concurrent get bean test runnable
     * 
     * @author yangwm Jun 15, 2010 3:40:18 PM 
     */
    static class PersonRun implements Runnable {
        int id;

        public PersonRun(int id) {
            this.id = id;
        }
        
        public void run() {
            System.out.println("Thread " + id + " start..." + System.currentTimeMillis());
            try {
                Person people = (Person) context.getBean("person");
                System.out.println(Thread.currentThread().getName() + " people.toString()=" + people.toString());
            } catch (Exception e) {
                System.err.println(Thread.currentThread().getName() + "-----------------------------------------");
                e.printStackTrace();
                System.exit(0);
            }
            System.out.println("Thread " + id + " end..." + System.currentTimeMillis());
        }
        
    }
    
}


