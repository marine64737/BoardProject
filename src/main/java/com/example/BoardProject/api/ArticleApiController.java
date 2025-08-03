package com.example.BoardProject.api;

import com.example.BoardProject.DTO.ArticleForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Repository.ArticleRepository;
import com.example.BoardProject.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/api/")
    public ResponseEntity<List<Article>> index(){ // 게시물 목록 표시
        List<Article> articleList = articleService.index();
        return (articleList != null) ?
                ResponseEntity.ok(articleList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/api/{id}")
    public ResponseEntity<Article> show(@PathVariable Long id){ // 게시물 열람
        Article article = articleService.show(id);
        return (article != null) ?
                ResponseEntity.ok(article) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping("/api/new")
    public ResponseEntity<Article> create(@AuthenticationPrincipal UserDetails userDetails, // 게시물 신규 작성
                                          @RequestBody ArticleForm form, String filename){
        Article saved = articleService.create(form, userDetails.getUsername(), filename);
        return (saved != null) ?
                ResponseEntity.ok(saved):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PatchMapping("/api/{id}")
    public ResponseEntity<Article> update(@AuthenticationPrincipal UserDetails userDetails, // 게시물 수정
                                          @PathVariable Long id,
                                          @RequestBody ArticleForm form, String filename){
        Article updated = articleService.update(id, form, userDetails.getUsername(), filename);
        return (updated != null) ?
                ResponseEntity.ok(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @DeleteMapping("api/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){ // 게시물 삭제
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.ok(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    @PostMapping("/api/tranaction") // Tranaction 기능 테스트용(튜토리얼)
//    public ResponseEntity<List<Article>> tranaction(@AuthenticationPrincipal Principal principal, @RequestBody List<ArticleForm> forms){
//        List<Article> articleList = articleService.transaction(forms, principal.getName());
//        return  (articleList != null) ?
//                ResponseEntity.ok(articleList) :
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
}
