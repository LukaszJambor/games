package com.example2.demo.cronjob.lendAdditionalCosts;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class LendAdditionalCostsConfiguration {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Bean
    public Scheduler lendAdditionalCostsScheduler() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.scheduleJob(jobDetails(), getTrigger());
        return scheduler;
    }

    private JobDetail jobDetails(){
        return JobBuilder.newJob(LendAdditionalCostsJob.class).build();
    }

    private Trigger getTrigger() {
        return TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 * ? * MON,TUE,WED,THU,FRI,SAT *"))
                .build();
    }
}