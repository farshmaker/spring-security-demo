package com.farshmaker.security_demo.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.farshmaker.security_demo.config.UserPermission.COURSE_READ;
import static com.farshmaker.security_demo.config.UserPermission.COURSE_WRITE;
import static com.farshmaker.security_demo.config.UserPermission.STUDENT_READ;
import static com.farshmaker.security_demo.config.UserPermission.STUDENT_WRITE;

public enum UserRole {
  STUDENT(),
  ADMIN(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE),
  ADMINTRAINEE(COURSE_READ, STUDENT_READ);

  private final Set<UserPermission> permissions;

  UserRole() {
    this.permissions = new HashSet<>();
  }

  UserRole(UserPermission... permissions) {
    this.permissions = Set.of(permissions);
  }

  public Set<UserPermission> getPermissions() {
    return permissions;
  }

  public Set<GrantedAuthority> getGrantedAuthorities() {
    final Set<GrantedAuthority> grantedAuthorities = getPermissions()
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toSet());

    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

    return grantedAuthorities;
  }
}
