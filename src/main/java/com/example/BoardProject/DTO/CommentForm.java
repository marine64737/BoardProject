package com.example.BoardProject.DTO;

import com.example.BoardProject.Entity.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentForm {
    private Long id;
    private String nickname;
    private String comment;
    private String commentdate;
    private Long articleId;

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
