package com.example.BoardProject.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberForm {
    private Long id;
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String userId;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
