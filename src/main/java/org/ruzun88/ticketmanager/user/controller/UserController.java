package org.ruzun88.ticketmanager.user.controller;

import lombok.RequiredArgsConstructor;
import org.ruzun88.ticketmanager.user.dto.UserInfoDto;
import org.ruzun88.ticketmanager.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

  private final UserService userService;

  @PostMapping("/user")
  public ResponseEntity<Long> signup(UserInfoDto infoDto) { // 회원 추가
    Long id = userService.save(infoDto);
    return new ResponseEntity<>(id, HttpStatus.CREATED);
  }
}