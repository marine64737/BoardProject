package com.example.BoardProject.Entity;

import com.example.BoardProject.DTO.CommentForm;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nickname;
    @Column
    private String comment;
    @Column
    private String commentdate;
    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    public Comment patch(Comment comment){
        if (comment.getComment() != null){
            this.comment = comment.getComment();
        }
        return this;
    }

    public static Comment createComment(CommentForm form, Article article){
        if (form.getId() != null)
            throw new IllegalArgumentException("Fail to create comment! There should be no id!");
        if (form.getArticle_id() != article.getId())
            throw new IllegalArgumentException("Fail to create comment! The id of article is wrong!");
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return new Comment(form.getId(), form.getNickname(), form.getComment(), clock, article);
    }
    public static Comment modifyComment(CommentForm form, Article article){
        if (form.getId() == null)
            throw new IllegalArgumentException("Fail to create comment! There should be id!");
        if (form.getArticle_id() != article.getId())
            throw new IllegalArgumentException("Fail to create comment! The id of article is wrong!");
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return new Comment(form.getId(), form.getNickname(), form.getComment(), clock, article);
    }
}
