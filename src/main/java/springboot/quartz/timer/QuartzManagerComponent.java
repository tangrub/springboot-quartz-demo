package springboot.quartz.timer;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by tangrubei on 2017/2/10.
 */

public class QuartzManagerComponent implements ApplicationListener<ContextRefreshedEvent> {


    private Scheduler scheduler;


    private final String groupName = "groupExecute";

//    注入工厂类
    @Autowired
    private SchedulerFactory schedulerFactory;


//    系统启动时执行
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
//            初始化容器
            scheduler = schedulerFactory.getScheduler();
//            启动容器
            scheduler.start();
//            初始job上下文和注册任务，这里可以改为从数据库或者配置里获取任务，动态加载
            Map datamap = new HashMap();
            datamap.put("data","my job time");
            this.addJob("myjob","0/5 * * * * ?",MyJob.class,datamap);
//
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


//  新增任务
    public void addJob(String jobName, String jobTime, Class jobClassz, Map<String, Object> dataMap) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(jobClassz).withIdentity(jobName, groupName).build();
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
        if (dataMap != null) {
            dataMap.forEach((k, v) -> {
                job.getJobDataMap().put(k, v);
            });
        }
        scheduler.scheduleJob(job, trigger);
    }

//  删除任务
    public void deleteJob(String jobName) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        if (triggerKey != null) {
//            暂停定时器
            scheduler.pauseTrigger(triggerKey);
//            删除定时器
            scheduler.unscheduleJob(triggerKey);
            JobKey jobKey = JobKey.jobKey(jobName, groupName);
//            删除任务
            scheduler.deleteJob(jobKey);
        }
    }

//  更新任务
    public void updateJob(String jobName, String jobTime, Class classz, Map<String, Object> dataMap) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        this.deleteJob(jobName);
        this.addJob(jobName, jobTime, classz, dataMap);
    }


}
