package springboot.quartz.timer;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//import com.nonobank.apps.service.TestCaseService;

/**
 * Created by tangrubei on 2017/2/10.
 */
public class MyJob implements Job {


    @Override
    public void execute(JobExecutionContext context){
        String data = (String) context.getMergedJobDataMap().get("data");
        System.out.println(data);


    }


}
