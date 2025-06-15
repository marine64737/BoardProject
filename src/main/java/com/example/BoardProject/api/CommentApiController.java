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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
public class CommentApiController {
    @Autowired
    CommentService commentService;
    @GetMapping("/api/comments/all")
    public ResponseEntity<List<CommentForm>> index(){
        List<CommentForm> commentFormList = commentService.index();
        return (commentFormList != null) ?
                ResponseEntity.ok(commentFormList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/api/{id}/comments")
    public ResponseEntity<List<CommentForm>> view(@PathVariable("id") Long articleId){
        List<CommentForm> commentFormList = commentService.view(articleId);
        return (commentFormList != null) ?
                ResponseEntity.ok(commentFormList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/api/{id}/comments")
    public ResponseEntity<CommentForm> create(@AuthenticationPrincipal Principal principal, @PathVariable("id") Long articleId, @RequestBody CommentForm form){
        CommentForm comment = commentService.create(form, articleId, principal.getName());
        log.info(form.toString());
        log.info(articleId.toString());
        return (comment != null) ?
                ResponseEntity.ok(comment) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PatchMapping("/api/{id}/comments")
    public ResponseEntity<Comment> modify(@AuthenticationPrincipal Principal principal, @PathVariable("id") Long articleId, @RequestBody CommentForm form){
        Comment comment = commentService.update(form, articleId, principal.getName());
        return (comment != null) ?
                ResponseEntity.ok(comment) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Comment> delete(@PathVariable Long id){
        Comment comment = commentService.delete(id);
        return (comment != null) ?
                ResponseEntity.ok(comment) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
