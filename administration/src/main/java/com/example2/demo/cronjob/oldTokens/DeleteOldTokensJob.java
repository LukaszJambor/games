package com.example2.demo.cronjob.oldTokens;

import dao.HashRepository;
import model.UserTokenEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DeleteOldTokensJob implements Job {

    @Autowired
    private HashRepository hashRepository;

    @Value("${activation.token.delay.in.hours}")
    private String hours;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<UserTokenEntity> activationEntityByCreationTimestampBefore = hashRepository.findActivationEntityByCreationTimestampBeforeAndActivationTimestampIsNull(LocalDateTime.now().minusHours(Long.valueOf(hours)));
        hashRepository.deleteAll(activationEntityByCreationTimestampBefore);
    }
}