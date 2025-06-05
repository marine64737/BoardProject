package com.example.BoardProject.Service;

import com.example.BoardProject.DTO.ArticleForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    public List<Article> index(){
        List<Article> articleList = articleRepository.findAll();
        return articleList;
    }
    public Article create(ArticleForm form){
        Article article = Article.toEntity(form);
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
    public Article update(Long id, ArticleForm form){
        Article article = articleRepository.findById(id).orElse(null);
        Article target = Article.toEntity(form);
        if (article == null || id != target.getId()){
            return null;
        }
        article.patch(target);
        Article updated = articleRepository.save(article);
        return updated;
    }
    public Article delete(Long id){
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null){
            return null;
        }
        articleRepository.delete(article);
        return article;
    }
    @Transactional
    public List<Article> transaction(List<ArticleForm> forms){
        List<Article> articleList = forms.stream()
                .map(form -> Article.toEntity(form))
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
