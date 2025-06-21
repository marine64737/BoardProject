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
    ArticleRepository articleRepository;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;

    public static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int page){

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Article> pageResult = articleRepository.findAll(pageable);
        model.addAttribute("articles", pageResult.getContent());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("currentPage", page+1);
        if (pageResult.hasNext()) {
            model.addAttribute("nextPage", page+1);
        }
        if (pageResult.hasPrevious()) {
            model.addAttribute("prevPage", page-1);
        }
        return "board/index";
    }
    @GetMapping("/board/new")
    public String post(){
        return "board/new";
    }
    @PostMapping("/")
    public String create(@AuthenticationPrincipal UserDetails userDetails, ArticleForm form, Model model,
                         @RequestParam("filename") MultipartFile file, @RequestParam(defaultValue = "0") int page) throws IOException {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));


        String filename = file.getOriginalFilename();
        String uploadDir = System.getProperty("user.dir") + "/upload/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String path = uploadDir+filename;
        file.transferTo(new File(path));
        Article article = Article.toEntity(form, userDetails.getUsername(), filename);
        article.setFilename(filename);
        Article saved = articleRepository.save(article);
        Page<Article> pageResult = articleRepository.findAll(pageable);
        if (pageResult.hasNext()) {
            model.addAttribute("nextPage", page+1);
        }
        if (pageResult.hasPrevious()) {
            model.addAttribute("prevPage", page-1);
        }
        model.addAttribute("articles", pageResult.getContent());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("currentPage", page+1);

        return "board/index";
    }
    @GetMapping("/board/{id}/view")
    public String view(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, Model model){
        Article board = articleRepository.findById(id).orElse(null);
        List<CommentForm> commentForms = commentService.viewByArticleId(id);
        model.addAttribute("post", board);
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        model.addAttribute("commentForms", commentForms);
        return "board/view";
    }
    @GetMapping("/board/{id}/modify")
    public String modify(@PathVariable Long id, Model model){
        Article article = articleRepository.findById(id).orElse(null);
        log.info(article.toString());
        Model saved = model.addAttribute("post", article);
        log.info(saved.toString());
        return "board/modify";
    }
    @PostMapping("/board/{id}/view")
    public String modified(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, ArticleForm form, Model model, @RequestParam("filename") MultipartFile file){
        Article article = articleRepository.findById(id).orElse(null);
        Article target = article.patch(Article.createArticle(form, userDetails.getUsername(), file.getOriginalFilename()));
        List<CommentForm> commentForms = commentService.viewByArticleId(id);
        log.info(article.toString());
        log.info(target.toString());
        Article saved = articleRepository.save(target);
        log.info(saved.toString());
        Model savedModel = model.addAttribute("post", target);
        model.addAttribute("commentForms", commentForms);
        model.addAttribute("new-comment-nickname", userDetails.getUsername());
        log.info(savedModel.toString());
        return "board/view";
    }
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Long id){
        articleService.delete(id);
        return "redirect:/";
    }
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes) throws IOException{
        String filename = file.getOriginalFilename();
        String uploadDir = System.getProperty("user.dir") + "/upload/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String path = uploadDir+filename;
        file.transferTo(new File(path));
        redirectAttributes.addFlashAttribute("file", filename);
        return "redirect:/";
    }
}
