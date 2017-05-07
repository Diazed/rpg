package de.berufsschule.rpg.registration;

import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.repositories.UserRepository;
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

  @Autowired
  private UserService userService;

  private static List<GrantedAuthority> getAuthorities(List<String> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }

  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {

    User user = userService.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(
          "No user found with username: " + email);
    }
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;
    return new org.springframework.security.core.userdetails.User
        (user.getEmail(),
            user.getPassword().toLowerCase(), enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked,
            getAuthorities(user.getRoles()));
  }
}
