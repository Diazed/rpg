package de.berufsschule.rpg.localization;

import de.berufsschule.rpg.domain.model.User;
import de.berufsschule.rpg.services.UserService;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Component
public class LocalizationResolver implements LocaleResolver {

  private final SessionLocaleResolver sessionLocaleResolver;
  @Autowired
  private UserService userService;

  public LocalizationResolver() {
    sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
  }

  @Override
  public Locale resolveLocale(HttpServletRequest httpServletRequest) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() != "anonymousUser") {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      User user = userService.findByEmail(userDetails.getUsername());
      if (user.getPrefLocale() == null) {
        return Locale.ENGLISH;
      }
      return user.getPrefLocale();
    } else {

      return sessionLocaleResolver.resolveLocale(httpServletRequest);
    }
  }

  @Override
  public void setLocale(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Locale locale) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() != "anonymousUser") {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      User user = userService.findByEmail(userDetails.getUsername());
      user.setPrefLocale(locale);
      userService.editUser(user);
    } else {
      sessionLocaleResolver.setLocale(httpServletRequest, httpServletResponse, locale);
    }
  }
}
