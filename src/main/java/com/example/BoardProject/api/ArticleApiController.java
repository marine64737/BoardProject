package com.example.BoardProject.api;

import com.example.BoardProject.DTO.ArticleForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Repository.ArticleRepository;
import com.example.BoardProject.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/api/")
    public ResponseEntity<List<Article>> index(){
        List<Article> articleList = articleService.index();
        return (articleList != null) ?
                ResponseEntity.ok(articleList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/api/{id}")
    public ResponseEntity<Article> show(@PathVariable Long id){
        Article article = articleService.show(id);
        return (article != null) ?
                ResponseEntity.ok(article) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping("/api/new")
    public ResponseEntity<Article> create(@RequestBody ArticleForm form){
        Article saved = articleService.create(form);
        return (saved != null) ?
                ResponseEntity.ok(saved):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PatchMapping("/api/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm form){
        Article updated = articleService.update(id, form);
        return (updated != null) ?
                ResponseEntity.ok(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @DeleteMapping("api/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.ok(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/api/tranaction")
    public ResponseEntity<List<Article>> tranaction(@RequestBody List<ArticleForm> forms){
        List<Article> articleList = articleService.transaction(forms);
        return  (articleList != null) ?
                ResponseEntity.ok(articleList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
