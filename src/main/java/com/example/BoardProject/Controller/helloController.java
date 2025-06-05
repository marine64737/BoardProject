package com.example.BoardProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class helloController {
    @GetMapping("/hi")
    public String hello(Model model){
        String name = "Sangho";
        model.addAttribute("name", name);
        return "hello";
    }
}
