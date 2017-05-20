package de.berufsschule.rpg.registration;

import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.services.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements
    ApplicationListener<OnRegistrationCompleteEvent> {

  private UserService service;
  private JavaMailSender mailSender;

  @Value("${app-location}")
  private String location;

  @Autowired
  public RegistrationListener(UserService service,
      JavaMailSender mailSender) {
    this.service = service;
    this.mailSender = mailSender;
  }

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
    String message = "Click the link below to confirm your e-mail address.";

    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message + "\n" + location + confirmationUrl);
    mailSender.send(email);
  }
}