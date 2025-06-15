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
    if (error != null) {
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
        model.addAttribute("loginError", "Invalid ID or password");
    }
    return "Member/login";
}

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
    }
    @PostMapping("/join/completed")
    public String join_complete(@Validated MemberForm memberForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            memberForm.setUsername(null);
            memberForm.setPassword(null);
            if (memberForm.getUsername() == null){
                model.addAttribute("error_id", "아이디를 입력하세요.");
            }
            if (memberForm.getPassword() == null){
                model.addAttribute("error_password", "비밀번호를 입력하세요.");
            }
            return "Member/signup";
        }
        else if (memberRepository.isMember(memberForm.getUsername()) > 0){
            model.addAttribute("error_id", "이미 존재하는 아이디입니다.");
            model.addAttribute("error_password", "");
            return "Member/signup";
        }
        String encodedPw = passwordEncoder.encode(memberForm.getPassword());
        Member member = Member.toEntity(memberForm, encodedPw);
        memberRepository.save(member);
        return "redirect:/";
    }
}
