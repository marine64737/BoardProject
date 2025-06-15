package com.example.BoardProject.Service;

import com.example.BoardProject.DTO.ArticleForm;
import com.example.BoardProject.DTO.CommentForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Entity.Comment;
import com.example.BoardProject.Repository.ArticleRepository;
import com.example.BoardProject.Repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;
    public List<Article> index(){
        List<Article> articleList = articleRepository.findAll();
        return articleList;
    }
    public Article create(ArticleForm form, String username){
        Article article = Article.toEntity(form, username);
        if (article.getId() != null){
            return null;
        }
        Article saved = articleRepository.save(article);
        return saved;
    }
    public Article show(Long id){
        Article article = articleRepository.findById(id).orElse(null);
        return article;
    }
    public Article update(Long id, ArticleForm form, String username){
        Article article = articleRepository.findById(id).orElse(null);
        Article target = Article.toEntity(form, username);
        if (article == null || id != target.getId()){
            return null;
        }
        article.patch(target);
        Article updated = articleRepository.save(article);
        return updated;
    }
    public Article delete(Long id){
        List<Comment> c = commentRepository.findByArticleId(id);
        if (!c.isEmpty()) {
            List<Comment> comments = commentRepository.findByArticleId(id);
            for (Comment comment : comments) {
                commentRepository.delete(comment);
            }
        }
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null){
            return null;
        }
        articleRepository.delete(article);
        return article;
    }
    @Transactional
    public List<Article> transaction(List<ArticleForm> forms, String username){
        List<Article> articleList = forms.stream()
                .map(form -> Article.toEntity(form, username))
                .collect(Collectors.toList());
        log.info(articleList.toString());
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        log.info(articleList.toString());
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("Failed to pay!"));
        return articleList;
    }
}
