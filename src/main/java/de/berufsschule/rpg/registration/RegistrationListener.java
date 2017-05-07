package de.berufsschule.rpg.registration;

import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.services.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements
    ApplicationListener<OnRegistrationCompleteEvent> {

  @Autowired
  private UserService service;

  @Autowired
  private JavaMailSender mailSender;

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) {
    User user = event.getUser();
    String token = UUID.randomUUID().toString();
    service.createVerificationToken(user, token);

    String recipientAddress = user.getEmail();
    String subject = "Registration Confirmation";
    String confirmationUrl
        = event.getAppUrl() + "/registration/confirm?token=" + token;
    String message = "yeah";

    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message + "\n" + "http://localhost:8080" + confirmationUrl);
    mailSender.send(email);
  }
}