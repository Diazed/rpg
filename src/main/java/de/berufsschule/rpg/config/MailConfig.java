package de.berufsschule.rpg.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

  @Bean
  public JavaMailSenderImpl mailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    Properties mailProperties = new Properties();

    mailProperties.put("mail.smtp.auth", true);
    mailProperties.put("mail.smtp.starttls.enable", false);
    mailProperties.put("mail.smtp.starttls.required", true);
    mailProperties.put("mail.smtp.socketFactory.port", 465);
    mailProperties.put("mail.smtp.debug", true);
    mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

    javaMailSender.setJavaMailProperties(mailProperties);
    javaMailSender.setProtocol("smtp");
    javaMailSender.setHost("smtp.gmail.com");
    javaMailSender.setUsername("rpg.web.app@gmail.com");
    javaMailSender.setPassword("SpringBoot");
    javaMailSender.setPort(465);

    return javaMailSender;
  }

}
