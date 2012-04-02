package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Quartz测试工作类
 * 
 * @author yangwm in Apr 7, 2009 4:12:52 PM
 */
public class TestJob implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        System.out.println("★★★★★★★★★★★");
    }

}
