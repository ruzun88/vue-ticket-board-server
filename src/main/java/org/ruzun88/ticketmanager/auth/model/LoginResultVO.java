package org.ruzun88.ticketmanager.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultVO {
    private Boolean result; // 로그인 결과
    private String message; // 메시지 or Code
    private String email;   // email 주소, ID 대용
    private String token;   // JWT Token

    @JsonIgnore
    public LoginResultVO getNoSuchUser() {
        return new LoginResultVO(false, "No Such User", "", "");
    }
    @JsonIgnore
    public LoginResultVO getPasswordMismatchUser(String email) {
        return new LoginResultVO(false, "Password Invalid", email, "");
    }
    @JsonIgnore
    public LoginResultVO getLoginSuccessUser(String email, String token) {
        return new LoginResultVO(true, "Login Success", email, token);
    }
}
