package de.berufsschule.rpg.registration;

import de.berufsschule.rpg.domain.model.User;
import de.berufsschule.rpg.services.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {


  private UserService userService;

  @Autowired
  public MyUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  private static List<GrantedAuthority> getAuthorities(List<String> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }

  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {

    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;
    try {
      User user = userService.findByEmail(email);
      if (user == null) {
        throw new UsernameNotFoundException(
            "No user found with username: " + email);
      }

      return new org.springframework.security.core.userdetails.User(
          user.getEmail(),
          user.getPassword(),
          user.isEnabled(),
          accountNonExpired,
          credentialsNonExpired,
          accountNonLocked,
          getAuthorities(user.getRoles()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
