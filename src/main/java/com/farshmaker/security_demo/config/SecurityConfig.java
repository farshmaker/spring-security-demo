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

import static com.farshmaker.security_demo.config.UserPermission.*;
import static com.farshmaker.security_demo.config.UserRole.*;
import static org.springframework.http.HttpMethod.*;

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
        .csrf().disable()
        .httpBasic().and()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/api/**").hasRole(STUDENT.name())
        .antMatchers(DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
        .antMatchers(POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
        .antMatchers(PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
        .antMatchers(GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
        .anyRequest()
        .authenticated();

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
        .username("mike")
        .password(passwordEncoder.encode("password123"))
//        .roles(ADMIN.name())
        .authorities(ADMIN.getGrantedAuthorities())
        .build();

    final UserDetails tomUser = User.builder()
        .username("tom")
        .password(passwordEncoder.encode("password321"))
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
