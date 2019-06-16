package com.len.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件配置
 */
@Configuration
public class SmsConfig {
    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private Integer port;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Bean("mailSender")
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.getJavaMailProperties().put("mail.smtps.auth","true");
        jms.setProtocol(protocol);
        jms.setHost(host);
        jms.setPort(port);
        jms.setUsername(username);
        jms.setPassword(password);
        return jms;
    }

}
