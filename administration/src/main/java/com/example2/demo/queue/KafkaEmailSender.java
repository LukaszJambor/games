package com.example2.demo.queue;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaEmailSender {

    private KafkaTemplate<String, Map> kafkaTemplate;

    public void send(Map<String, String> map) {
        kafkaTemplate.send("registerEmail", map);
    }
}
