package com.example.BoardProject.Config;

import com.example.BoardProject.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.rmi.registry.Registry;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private MemberService memberService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.authorizeRequests(A).anyRequest().hasRole("USER").and().formLogin();
//        return http.build();
        http
                .userDetailsService(memberService) // 회원 관련 인증, 인가 설정
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/join", "/join/**", "/login",
                                "/css/**", "/js/**", "/h2-console/**", "/board/**", "/board/*/view",
                                "/board", "/api/**", "/api", "/uploadFile",
                                "/upload", "/upload/**", "/uploadFileToPost").permitAll() // 해당 페이지들은 로그인 없이도 접근 가능
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form // 로그인 폼 커스터마이징
                        .loginPage("/login") // 로그인 페이지
                        .loginProcessingUrl("/login") // 로그인 성공 시 반환 페이지(흔히 링크만 존재하고 redirect 사용)
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 이동을 원하는 페이지 커스터마이징(없으면 기존 페이지 이동)
                        .failureUrl("/login?error") // 로그인 실패 시 반환 페이지
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 페이지
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 반환 페이지
                )
                .csrf(AbstractHttpConfigurer::disable); // CSRF 비활성화(해당 기능 추후 학습 예정)


        return  http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    } // 비밀번호 암호화 함수
}
