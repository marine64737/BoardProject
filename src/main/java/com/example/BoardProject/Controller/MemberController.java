package com.example.BoardProject.Controller;

import com.example.BoardProject.DTO.MemberForm;
import com.example.BoardProject.Entity.Member;
import com.example.BoardProject.Repository.MemberRepository;
import com.example.BoardProject.Service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MemberService memberService;

//    @GetMapping("/login")
//    public String login(Model model){
//        model.addAttribute("error_login_id", "");
//        model.addAttribute("error_login_password", "");
//        return "Member/login";
//    }
@GetMapping("/login")
public String loginPage(@RequestParam(value = "error", required = false) String error,
//                        @RequestParam String username, @RequestParam String password,
                        Model model) {
    if (error != null) { // 주석 처리의 이유는 아래 함수에 나와 있는데, ID, 비밀번호의 오류 처리가 의도대로 되지 않아 임시 주석 처리.
//        if (username == null){
//            model.addAttribute("id_error", "아이디를 입력해주세요.");
//        }
//        if (password == null){
//            model.addAttribute("password_error", "아이디를 입력해주세요.");
//        }
//        Member member = memberRepository.findByUserId(username).orElse(null);
//        if (member == null){
//            model.addAttribute("id_error", "아이디를 다시 입력해주세요.");
//        } else if (passwordEncoder.matches(password, member.getPassword())) {
//            model.addAttribute("password_error", "비밀번호를 다시 입력해주세요.");
//        }
        model.addAttribute("loginError", "Invalid ID or password"); // error만 받아서 ID, 비밀번호 오류 통합 관리
    }
    return "Member/login";
}

// Login 실패 시 ID 오류 시, 비밀번호 오류 시를 나눠서 에러 메시지를 띄우기 위해 만든 함수지만
// Spring Security에서 username form을 받지 않고 ID, 비밀번호를 통합적으로 관리하고 있어 현재로선 의미 없는 함수.
// 추후 Spring Security룰 다듬어 에러 핸들링이 가능해지면 다시 사용할 가능성 있음.

//    @PostMapping("/login")
//    public String login_complete(@RequestParam String username, @RequestParam String password, Model model){
//        boolean hasError = false;
//        log.info("Number 1");
//        if (username == null || username.trim().isEmpty()) {
//            log.info("Number 2");
//            model.addAttribute("error_login_id", "아이디를 입력하세요.");
//            return "Member/login";
//        }
//        if (password == null || password.trim().isEmpty()) {
//            log.info("Number 3");
//            model.addAttribute("error_login_password", "비밀번호를 입력하세요.");
//            return "Member/login";
//        }
//        Member member = memberRepository.findByUserId(username).orElse(null);
//        if (member == null){
//            log.info("Number 4");
//            model.addAttribute("error_login_id", "아이디를 다시 입력하세요.");
//            return "Member/login";
//        }
//        else if (passwordEncoder.matches(password, member.getPassword())){
//            log.info("Number 5");
//            model.addAttribute("error_login_password", "비밀번호를 다시 입력하세요.");
//            return "Member/login";
//        }
//        log.info("Number 6");
//        return "redirect:/";
//    }

    @GetMapping("/join")
    public String join(Model model){
        return "Member/signup";
    } // 회원 가입
    @PostMapping("/join/completed")
    public String join_complete(@Validated MemberForm memberForm, // 가입 완료 시 반환 페이지
                                BindingResult bindingResult, Model model){
        memberService.join_complete(memberForm, bindingResult, model);
        return "redirect:/";
    }
}
