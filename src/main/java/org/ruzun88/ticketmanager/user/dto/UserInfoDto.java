package org.ruzun88.ticketmanager.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoDto {
  private String email;
  private String password;
  private String auth;
}