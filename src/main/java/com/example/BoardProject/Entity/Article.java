package com.example.BoardProject.Entity;

import com.example.BoardProject.DTO.ArticleForm;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String postdate;

    public Article patch(Article article){
        if (article.title != null){
            this.title = article.title;;
        }
        if (article.content != null){
            this.content = article.content;
        }
        return this;
    }
    public static Article toEntity(ArticleForm form){
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return new Article(form.getId(), form.getTitle(), form.getContent(), clock);
    }
}
