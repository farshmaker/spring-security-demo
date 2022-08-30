package com.farshmaker.security_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public SecurityConfig(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .httpBasic().and()
        .authorizeRequests()
        .antMatchers("/")
        .permitAll()
        .anyRequest()
        .authenticated();

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    final UserDetails annaSmithUser = User.builder()
        .username("annasmith")
        .password(passwordEncoder.encode("password"))
        .roles("STUDENT")
        .build();

    return new InMemoryUserDetailsManager(annaSmithUser);
  }
}
