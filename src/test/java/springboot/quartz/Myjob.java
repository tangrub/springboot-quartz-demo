package springboot.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Created by tangrubei on 2018/3/30.
 */
public class Myjob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
//        获取上下文内容
        String data = (String) context.getMergedJobDataMap().get("data");
//        do something
        System.out.println(data);

    }
}