package org.ruzun88.ticketmanager.user.service;

import lombok.RequiredArgsConstructor;
import org.ruzun88.ticketmanager.user.dto.UserInfoDto;
import org.ruzun88.ticketmanager.user.model.UserInfo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ruzun88.ticketmanager.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * 회원정보 저장
   *
   * @param infoDto 회원정보가 들어있는 DTO
   * @return 저장되는 회원의 PK
   */
  public Long save(UserInfoDto infoDto) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    infoDto.setPassword(encoder.encode(infoDto.getPassword()));

    return userRepository.save(UserInfo.builder()
            .email(infoDto.getEmail())
            .password(infoDto.getPassword()).build()).getCode();
  }

  /**
   * Spring Security 필수 메소드 구현
   *
   * @param email 이메일
   * @return UserDetails
   * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
   */
  @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
  public UserInfo loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException((email)));
  }

  public UserInfoDto loginUser(UserInfo userInfo) {
    UserInfo loadedUser = this.loadUserByUsername(userInfo.getEmail());
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (!encoder.matches(userInfo.getPassword(), loadedUser.getPassword())) {
      throw new BadCredentialsException("password not matched");
    }
    return loadedUser.toResponse();
  }
}