package com.example.BoardProject.Service;

import com.example.BoardProject.DTO.MemberForm;
import com.example.BoardProject.Entity.Member;
import com.example.BoardProject.Repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
public class MemberService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String join_complete(@Validated MemberForm memberForm, // 가입 완료 시 반환 페이지
                                BindingResult bindingResult,
                                Model model){
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

    // 로그인 성공 시 회원 반환용으로
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//        Member member = memberRepository.findByUserId(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return (member == null) ?
//                null :
//                new User(member.getUsername(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
//    }

}
