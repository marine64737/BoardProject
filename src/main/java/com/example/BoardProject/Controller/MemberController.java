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

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("error_login_id", "");
        model.addAttribute("error_login_password", "");
        return "Member/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberForm memberForm, BindingResult bindingResult, Model model){
        Member member = memberRepository.findByUserId(memberForm.getUsername()).orElse(null);
        if (bindingResult.hasErrors()) {
            log.info("number 1");
            memberForm.setPassword(null);
            if (memberForm.getUsername() == null) {
                log.info("number 2");
                model.addAttribute("error_login_id", "아이디를 입력하세요.");
            }
            if (memberForm.getPassword() == null) {
                log.info("number 3");
                model.addAttribute("error_login_password", "비밀번호를 입력하세요.");
            }
            return "Member/login";
        }
        else {
            if (member == null) {
                log.info("number 4");
                memberForm.setPassword(null);
                model.addAttribute("error_login_id", "아이디를 다시 확인해주세요.");
                return "Member/login";
            }
            else if (passwordEncoder.matches(memberForm.getPassword(), member.getPassword())) {
                log.info(member.toString());
                log.info("number 5");
                memberForm.setPassword(null);
                model.addAttribute("error_login_password", "비밀번호를 다시 확인해주세요.");
                return "Member/login";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("error_id", "");
        model.addAttribute("error_password", "");
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
