package com.example.BoardProject.DTO;

import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Entity.Comment;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ArticleForm {
    private Long id;
    private String title;
    private String content;
    private String postdate;
    private String username;
    private String filename;

    public static ArticleForm createArticleForm(Article article){ // Entity -> DTO로 반환
        return (article != null) ?
                new ArticleForm(
                        article.getId(),
                        article.getTitle(),
                        article.getContent(),
                        article.getPostdate(),
                        article.getUsername(),
                        article.getFilename()
                ) : null;
    }

    public ArticleForm patch(ArticleForm articleForm){
        if (articleForm.title != null){
            this.title = articleForm.title;;
        }
        if (articleForm.content != null){
            this.content = articleForm.content;
        }
        return this;
    }
}