package de.berufsschule.rpg.registration;

import de.berufsschule.rpg.domain.model.User;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

  private String appUrl;
  private Locale locale;
  private User user;

  public OnRegistrationCompleteEvent(
      User user, Locale locale, String appUrl) {
    super(user);

    this.user = user;
    this.locale = locale;
    this.appUrl = appUrl;
  }
}
