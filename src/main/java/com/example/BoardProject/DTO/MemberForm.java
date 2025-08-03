package com.example.BoardProject.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberForm { // NotBlank는 구현했으나 메시지는 다음과 같이 나오지 않음. Spring Security 커스터마이징 문제 같아 리팩토링 고려 중.
    private Long id;
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
