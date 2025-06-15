package com.example.BoardProject.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LoginUserAdvice {
    @ModelAttribute("user")
    public UserDetails loginUser(@AuthenticationPrincipal UserDetails user){
        return  user;
    }
}
