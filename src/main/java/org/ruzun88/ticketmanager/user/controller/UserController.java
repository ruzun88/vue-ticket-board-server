package org.ruzun88.ticketmanager.user.controller;

import lombok.RequiredArgsConstructor;
import org.ruzun88.ticketmanager.auth.JwtTokenProvider;
import org.ruzun88.ticketmanager.auth.model.LoginResultVO;
import org.ruzun88.ticketmanager.user.dto.UserInfoDto;
import org.ruzun88.ticketmanager.user.model.UserInfo;
import org.ruzun88.ticketmanager.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/login")
  public ResponseEntity<LoginResultVO> login(@RequestBody UserInfo userInfo) {
    UserInfoDto loadedUser;
    try {
      loadedUser = userService.loginUser(userInfo);
    } catch (UsernameNotFoundException e1) {
      return new ResponseEntity<>(new LoginResultVO().getNoSuchUser(), HttpStatus.FORBIDDEN);
    } catch (BadCredentialsException e2) {
      return new ResponseEntity<>(new LoginResultVO().getPasswordMismatchUser(userInfo.getEmail()), HttpStatus.FORBIDDEN);
    }
    LoginResultVO ret = new LoginResultVO().getLoginSuccessUser(userInfo.getEmail(), jwtTokenProvider.createToken(loadedUser.getEmail(), loadedUser.getRole()));
    return new ResponseEntity<>(ret, HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<Long> signup(UserInfoDto infoDto) { // 회원 추가
    Long id = userService.save(infoDto);
    return new ResponseEntity<>(id, HttpStatus.CREATED);
  }

  @GetMapping("/test")
  public String test(HttpServletRequest request) {
    return "test";
  }
}