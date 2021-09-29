package org.ruzun88.ticketmanager.user.controller;

import lombok.RequiredArgsConstructor;
import org.ruzun88.ticketmanager.user.dto.UserInfoDto;
import org.ruzun88.ticketmanager.user.model.UserInfo;
import org.ruzun88.ticketmanager.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<UserInfoDto> login(@RequestBody UserInfo userInfo) {
    UserInfoDto loadedUser;
    try {
      loadedUser = userService.loginUser(userInfo);
    } catch (UsernameNotFoundException e1) {
      return new ResponseEntity<>(new UserInfoDto(), HttpStatus.FORBIDDEN);
    } catch (BadCredentialsException e2) {
      return new ResponseEntity<>(new UserInfoDto(userInfo.getEmail(), null, null), HttpStatus.FORBIDDEN);
    }

    return new ResponseEntity<>(loadedUser, HttpStatus.OK);
  }

  @PostMapping("/user")
  public ResponseEntity<Long> signup(UserInfoDto infoDto) { // 회원 추가
    Long id = userService.save(infoDto);
    return new ResponseEntity<>(id, HttpStatus.CREATED);
  }
}