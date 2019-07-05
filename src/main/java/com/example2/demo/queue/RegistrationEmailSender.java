package com.example2.demo.queue;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RegistrationEmailSender {

    private JmsTemplate jmsTemplate;

    public RegistrationEmailSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(Map<String, String> map) {
        jmsTemplate.convertAndSend("register.email.queue", map);
    }
}