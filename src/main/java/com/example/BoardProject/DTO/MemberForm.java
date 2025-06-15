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
public class MemberForm {
    private Long id;
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
