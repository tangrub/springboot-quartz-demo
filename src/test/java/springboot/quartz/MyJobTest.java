package springboot.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by tangrubei on 2018/3/30.
 */
public class MyJobTest {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
//        创建容器工厂类
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        创建容器
        Scheduler scheduler = schedulerFactory.getScheduler();
//        启动容器
        scheduler.start();
//        创建一个可执行的工作任务
        JobDetail jobDetail = JobBuilder.newJob(Myjob.class).withIdentity("myfirstJob", "mygroup").build();
//        创建一个触发器
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myfirstJobTriger", "mygroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();
//        设置任务的执行的上下文
        jobDetail.getJobDataMap().put("data", "my first job");
//        讲任务和定时器注册到容器中
        scheduler.scheduleJob(jobDetail, trigger);
    }

}