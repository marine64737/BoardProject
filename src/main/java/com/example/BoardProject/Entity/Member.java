package com.example.BoardProject.Entity;

import com.example.BoardProject.DTO.MemberForm;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String joineddate;

    public static Member toEntity(MemberForm form, String encodedPw) {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return new Member(form.getId(), form.getUsername(), encodedPw, clock);
    }
}
