package com.example.BoardProject.Controller;

import com.example.BoardProject.DTO.ArticleForm;
import com.example.BoardProject.DTO.CommentForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Repository.ArticleRepository;
import com.example.BoardProject.Service.ArticleService;
import com.example.BoardProject.Service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;

    public static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam(defaultValue = "0") int page){ // 프로젝트 메인 페이지 반환(게시물 목록)
        articleService.index(userDetails, model, page);
        return "board/index";
    }

    @GetMapping("/board/new")
    public String post(){
        return "board/new";
    } // 게시물 작성 페이지 반환

    @PostMapping("/")
    public String create(@AuthenticationPrincipal UserDetails userDetails, // 게시물 작성 후 페이지 반환
                         ArticleForm form, Model model,
                         @RequestParam("filename") MultipartFile file,
                         @RequestParam(defaultValue = "0") int page) throws IOException {
        articleService.create(userDetails, form, model, file, page);
        return "board/index";
    }

    @GetMapping("/board/{id}/view")
    public String view(@AuthenticationPrincipal UserDetails userDetails, // 목록에서 게시물 상세 보기
                       @PathVariable Long id, Model model){
        articleService.view(userDetails, id, model);
        return "board/view";
    }

    @GetMapping("/board/{id}/modify")
    public String modify(@AuthenticationPrincipal UserDetails userDetails, // 게시물 수정
                         @PathVariable Long id, Model model){
        articleService.modify(userDetails, id, model);
        return "board/modify";
    }

    @PostMapping("/board/{id}/view")
    public String modified(@AuthenticationPrincipal UserDetails userDetails, // 게시물 수정 후 상세 보기
                           @PathVariable Long id, ArticleForm form, Model model,
                           @RequestParam("filename") MultipartFile file) throws IOException {
        articleService.modified(userDetails, id, form, model, file);
        return "board/view";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Long id){ // 게시물 삭제
        articleService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, // 게시물 작성 시 파일 업로드
                             RedirectAttributes redirectAttributes) throws IOException{
        articleService.uploadFile(file,redirectAttributes);
        return "redirect:/";
    }
}
