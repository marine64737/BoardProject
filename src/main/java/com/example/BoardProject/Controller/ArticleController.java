package com.example.BoardProject.Controller;

import com.example.BoardProject.DTO.ArticleForm;
import com.example.BoardProject.DTO.CommentForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Repository.ArticleRepository;
import com.example.BoardProject.Service.ArticleService;
import com.example.BoardProject.Service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    @GetMapping("/")
    public String index(Model model){
        List<Article> articleList = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("articleList", articleList);
        return "board/index";
    }
    @GetMapping("/board/new")
    public String post(){
        return "board/new";
    }
    @PostMapping("/")
    public String create(ArticleForm form, Model model){
        Article board = Article.toEntity(form);
        log.info(board.toString());
        Article saved = articleRepository.save(board);
        log.info(saved.toString());
        List<Article> articleList = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        log.info(articleList.toString());
        model.addAttribute("articleList", articleList);;
        return "board/index";
    }
    @GetMapping("/board/{id}/view")
    public String view(@PathVariable Long id, Model model){
        Article board = articleRepository.findById(id).orElse(null);
        List<CommentForm> commentForms = commentService.viewByArticleId(id);
        model.addAttribute("post", board);
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
    public String modified(@PathVariable Long id, ArticleForm form, Model model){
        Article article = articleRepository.findById(id).orElse(null);
        Article target = article.patch(Article.createArticle(form));
        List<CommentForm> commentForms = commentService.viewByArticleId(id);
        log.info(article.toString());
        log.info(target.toString());
        Article saved = articleRepository.save(target);
        log.info(saved.toString());
        Model savedModel = model.addAttribute("post", target);
        model.addAttribute("commentForms", commentForms);
        log.info(savedModel.toString());
        return "board/view";
    }
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Long id){
        articleService.delete(id);
        return "redirect:/";
    }
}
