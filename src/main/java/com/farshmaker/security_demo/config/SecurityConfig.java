package com.farshmaker.security_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

import static com.farshmaker.security_demo.config.UserRole.ADMIN;
import static com.farshmaker.security_demo.config.UserRole.ADMINTRAINEE;
import static com.farshmaker.security_demo.config.UserRole.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public SecurityConfig(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
//        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/api/**").hasRole(STUDENT.name())
        .anyRequest()
        .authenticated()
        .and()
        .formLogin().loginPage("/login").permitAll()
        .defaultSuccessUrl("/courses", false)
        .and()
        .rememberMe()
        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
        .key("securedkey");

    ;

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    final UserDetails annaSmithUser = User.builder()
        .username("annasmith")
        .password(passwordEncoder.encode("password"))
//        .roles(STUDENT.name())
        .authorities(STUDENT.getGrantedAuthorities())
        .build();

    final UserDetails mikeUser = User.builder()
        .username("linda")
        .password(passwordEncoder.encode("password123"))
//        .roles(ADMIN.name())
        .authorities(ADMIN.getGrantedAuthorities())
        .build();

    final UserDetails tomUser = User.builder()
        .username("tom")
        .password(passwordEncoder.encode("password123"))
//        .roles(ADMINTRAINEE.name())
        .authorities(ADMINTRAINEE.getGrantedAuthorities())
        .build();

    return new InMemoryUserDetailsManager(
        annaSmithUser,
        mikeUser,
        tomUser
    );
  }
}
