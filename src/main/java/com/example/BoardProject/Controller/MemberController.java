package com.example.BoardProject.Controller;

import com.example.BoardProject.DTO.MemberForm;
import com.example.BoardProject.Entity.Member;
import com.example.BoardProject.Repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("error_id", "");
        model.addAttribute("error_password", "");
        return "Member/signup";
    }
    @PostMapping("/join_completed")
    public String join_complete(@Validated MemberForm memberForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            memberForm.setUserId(null);
            memberForm.setPassword(null);
            if (memberForm.getUserId() == null){
                model.addAttribute("error_id", "아이디를 입력하세요.");
            }
            if (memberForm.getPassword() == null){
                model.addAttribute("error_password", "비밀번호를 입력하세요.");
            }
            return "/Member/signup";
        }
        else if (memberRepository.isMember(memberForm.getUserId()) > 0){
            model.addAttribute("error_id", "이미 존재하는 아이디입니다.");
            model.addAttribute("error_password", "");
            return "/Member/signup";
        }
        Member member = Member.toEntity(memberForm);
        memberRepository.save(member);
        return "redirect:/";
    }
}
