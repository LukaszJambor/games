package com.example2.demo.queue;

//import com.example2.demo.services.EmailSenderService;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;

import java.util.Map;

//@Component
public class RegistrationEmailReceiver {

//    private static final String ACTIVATE = "activate";
//    private EmailSenderService emailSenderService;
//    private TemplateEngine templateEngine;

//    public RegistrationEmailReceiver(EmailSenderService emailSenderService, TemplateEngine templateEngine) {
//        this.emailSenderService = emailSenderService;
//        this.templateEngine = templateEngine;
//    }

//    @JmsListener(destination = "register.email.queue")
//    public void receive(Map<String, String> map) {
//        Context context = new Context();
//        context.setVariable("hash", map.get("hash"));
//        String body = templateEngine.process("emailActivation", context);
//        emailSenderService.send(map.get("email"), ACTIVATE, body);
//    }
}