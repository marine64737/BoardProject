package com.example.BoardProject.api;

import com.example.BoardProject.DTO.CommentForm;
import com.example.BoardProject.Entity.Comment;
import com.example.BoardProject.Repository.CommentRepository;
import com.example.BoardProject.Service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
public class CommentApiController {
    @Autowired
    CommentService commentService;
    @GetMapping("/api/comments/all")
    public ResponseEntity<List<CommentForm>> index(){ // 모든 댓글 표시
        List<CommentForm> commentFormList = commentService.index();
        return (commentFormList != null) ?
                ResponseEntity.ok(commentFormList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/api/{id}/comments")
    public ResponseEntity<List<CommentForm>> view(@PathVariable("id") Long articleId){ // id에 해당하는 게시물의 댓글 표시
        List<CommentForm> commentFormList = commentService.view(articleId);
        return (commentFormList != null) ?
                ResponseEntity.ok(commentFormList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/api/{id}/comments")
    public ResponseEntity<CommentForm> create(@AuthenticationPrincipal UserDetails userDetails, // id에 해당하는 게시물에 댓글 작성
                                              @PathVariable("id") Long articleId,
                                              @RequestBody CommentForm form){ // form 안에서 댓글 id 자동 생성
        CommentForm comment = commentService.create(form, articleId, userDetails.getUsername());
        log.info(form.toString());
        log.info(articleId.toString());
        return (comment != null) ?
                ResponseEntity.ok(comment) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PatchMapping("/api/{id}/comments")
    public ResponseEntity<Comment> modify(@PathVariable("id") Long articleId, // id에 해당하는 게시물의 댓글 수정
                                          @RequestBody CommentForm form){ // form 내에서 수정할 댓글 id 자동 반환
        Comment comment = commentService.update(form, articleId);
        return (comment != null) ?
                ResponseEntity.ok(comment) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Comment> delete(@PathVariable Long id){ // id에 해당하는 댓글 삭제
        Comment comment = commentService.delete(id);
        return (comment != null) ?
                ResponseEntity.ok(comment) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
