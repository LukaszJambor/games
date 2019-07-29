package com.example2.demo.cronjob.oldTokens;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class DeleteOldTokensConfiguration {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Bean
    public Scheduler deleteOldTokensScheduler() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.scheduleJob(jobDetails(), getTrigger());
        return scheduler;
    }

    private JobDetail jobDetails() {
        return JobBuilder.newJob(DeleteOldTokensJob.class).build();
    }

    private Trigger getTrigger() {
        return TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .build();
    }
}