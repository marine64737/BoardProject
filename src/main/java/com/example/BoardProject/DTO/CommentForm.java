package com.example.BoardProject.DTO;

import com.example.BoardProject.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentForm {
    private Long id;
    private String nickname;
    private String comment;
    private String commentdate;
    private Long article_id;

    public static CommentForm createCommentForm(Comment comment){
        return new CommentForm(
                comment.getId(),
                comment.getNickname(),
                comment.getComment(),
                comment.getCommentdate(),
                comment.getArticle().getId()
                );
    }
}
