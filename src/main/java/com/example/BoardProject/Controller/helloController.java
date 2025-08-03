package com.example.BoardProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class helloController { // Spring boot project 초반 작성 시 웹 반환 작동 여부 확인용으로 사용
    @GetMapping("/hi")
    public String hello(Model model){
        String name = "Sangho";
        model.addAttribute("name", name);
        return "hello";
    }
}
