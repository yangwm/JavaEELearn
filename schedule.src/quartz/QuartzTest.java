package quartz;

/**
 * Quartz测试类
 * 
 * @author yangwm in Apr 7, 2009 4:12:13 PM
 */
public class QuartzTest {

    /** 
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TestJob job = new TestJob();
        String job_name ="11";
        try {
            System.out.println("【系统启动】");
            QuartzManager.addJob(job_name,job,"0/5 * * * * ?");
            
            Thread.sleep(10000);
            System.out.println("【修改时间】");
            QuartzManager.modifyJob(job_name,"0/10 * * * * ?");
            Thread.sleep(20000);
            System.out.println("【移除定时】");
            QuartzManager.removeJob(job_name);
            Thread.sleep(10000);
            
            System.out.println("\n【添加定时任务】");
            QuartzManager.addJob(job_name,job,"0/5 * * * * ?");
            
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
【系统启动】
22:10:33,656 [main] INFO  [SimpleThreadPool.initialize(258)]  - Job execution threads will use class loader of thread: main
22:10:33,687 [main] INFO  [SchedulerSignalerImpl.<init>(63)]  - Initialized Scheduler Signaller of type: class org.quartz.core.SchedulerSignalerImpl
22:10:33,687 [main] INFO  [QuartzScheduler.<init>(215)]  - Quartz Scheduler v.1.6.5 created.
22:10:33,703 [main] INFO  [RAMJobStore.initialize(141)]  - RAMJobStore initialized.
22:10:33,703 [main] INFO  [StdSchedulerFactory.instantiate(1229)]  - Quartz scheduler 'DefaultQuartzScheduler' initialized from default resource file in Quartz package: 'quartz.properties'
22:10:33,703 [main] INFO  [StdSchedulerFactory.instantiate(1233)]  - Quartz scheduler version: 1.6.5
22:10:33,734 [main] INFO  [QuartzScheduler.start(461)]  - Scheduler DefaultQuartzScheduler_$_NON_CLUSTERED started.
22:10:35,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:10:35,015 [DefaultQuartzScheduler_Worker-1] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:10:40,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:10:40,000 [DefaultQuartzScheduler_Worker-2] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
【修改时间】
22:10:45,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:10:45,000 [DefaultQuartzScheduler_Worker-3] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:10:50,015 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:10:50,015 [DefaultQuartzScheduler_Worker-4] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:10:55,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:10:55,000 [DefaultQuartzScheduler_Worker-5] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:11:00,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:11:00,000 [DefaultQuartzScheduler_Worker-6] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
【移除定时】

【添加定时任务】
22:11:13,734 [main] INFO  [QuartzScheduler.start(461)]  - Scheduler DefaultQuartzScheduler_$_NON_CLUSTERED started.
22:11:15,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:11:15,000 [DefaultQuartzScheduler_Worker-7] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:11:20,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:11:20,000 [DefaultQuartzScheduler_Worker-8] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:11:25,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:11:25,000 [DefaultQuartzScheduler_Worker-9] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:11:30,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:11:30,000 [DefaultQuartzScheduler_Worker-10] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:11:35,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:11:35,000 [DefaultQuartzScheduler_Worker-1] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
22:11:40,000 [DefaultQuartzScheduler_QuartzSchedulerThread] DEBUG [SimpleJobFactory.newJob(50)]  - Producing instance of Job 'group1.11', class=ams.util.quartz.TestJob
22:11:40,000 [DefaultQuartzScheduler_Worker-2] DEBUG [JobRunShell.run(201)]  - Calling execute on job group1.11
★★★★★★★★★★★
......
*/
