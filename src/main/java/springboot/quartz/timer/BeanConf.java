package springboot.quartz.timer;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tangrubei on 2018/3/30.
 */
@Configuration
public class BeanConf {

    @Bean
    public SchedulerFactory schedulerFactory() {
        return new StdSchedulerFactory();

    }
}
