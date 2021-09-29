package org.ruzun88.ticketmanager.user.model;

import lombok.*;
import org.ruzun88.ticketmanager.user.dto.UserInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
public class UserInfo implements UserDetails {

  @Id
  @Column(name = "code")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long code;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "auth")
  private String auth;

  @Builder
  public UserInfo(String email, String password) {
    this.email = email;
    this.password = password;
    this.auth = "USER"; // 관리자는 일반 사용자로 생성 후, DB에서 변경
  }

  // 사용자의 권한을 콜렉션 형태로 반환
  // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> roles = new HashSet<>();

    for (String role : auth.split(",")) {
      roles.add(new SimpleGrantedAuthority(role));
    }

    return roles;
  }

  // 사용자의 id를 반환 (unique한 값)
  @Override
  public String getUsername() {
    return email;
  }

  // 사용자의 password를 반환
  @Override
  public String getPassword() {
    return password;
  }

  // 계정 만료 여부 반환
  @Override
  public boolean isAccountNonExpired() {
    // 만료되었는지 확인하는 로직
    return true; // true -> 만료되지 않았음
  }

  // 계정 잠금 여부 반환
  @Override
  public boolean isAccountNonLocked() {
    // 계정 잠금되었는지 확인하는 로직
    return true; // true -> 잠금되지 않았음
  }

  // 패스워드의 만료 여부 반환
  @Override
  public boolean isCredentialsNonExpired() {
    // 패스워드가 만료되었는지 확인하는 로직
    return true; // true -> 만료되지 않았음
  }

  // 계정 사용 가능 여부 반환
  @Override
  public boolean isEnabled() {
    // 계정이 사용 가능한지 확인하는 로직
    return true; // true -> 사용 가능
  }

  public UserInfoDto toResponse() {
    return new UserInfoDto(this.email, "", this.auth);
  }
}