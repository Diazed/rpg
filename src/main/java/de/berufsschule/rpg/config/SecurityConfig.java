package de.berufsschule.rpg.config;

import de.berufsschule.rpg.registration.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  private MyUserDetailsService userDetailsService;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public SecurityConfig(MyUserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  public SecurityConfig(boolean disableDefaults,
      MyUserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    super(disableDefaults);
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/", "/home/", "/register", "/logout",
                "/registration/confirm", "/favicon.ico", "/pictures/**", "/messages/**")
                .permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .permitAll();
    }

  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.userDetailsService(userDetailsService);
    auth.authenticationProvider(authProvider());
    }


  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

}

