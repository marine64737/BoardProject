package com.example.BoardProject.Service;

import com.example.BoardProject.DTO.CommentForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Entity.Comment;
import com.example.BoardProject.Repository.ArticleRepository;
import com.example.BoardProject.Repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;

    public List<CommentForm> index(){
        List<Comment> commentList = commentRepository.findAll();
        List<CommentForm> comments = new ArrayList<CommentForm>();
        for (int i=0; i< commentList.size(); i++){
            Comment c = commentList.get(i);
            CommentForm f = CommentForm.createCommentForm(c);
            comments.add(f);
        }
        return comments;
    }
    public List<CommentForm> view(Long articleId){
        List<Comment> commentList = commentRepository.findByArticleId(articleId);
        List<CommentForm> commentFormList = new ArrayList<CommentForm>();
        for (int i=0; i< commentList.size(); i++){
            Comment comment = commentList.get(i);
            CommentForm form = CommentForm.createCommentForm(comment);
            commentFormList.add(form);
        }
        return commentFormList;
    }
    @Transactional
    public CommentForm create(CommentForm form, Long articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("Failed to create a comment! "+ "There is no article!"));
        Comment comment = Comment.createComment(form, article);
        Comment saved = commentRepository.save(comment);
        return CommentForm.createCommentForm(saved);
    }
    @Transactional
    public Comment update(CommentForm form, Long articleId){
        Article article = articleRepository.findById(articleId).orElse(null);
        Comment target = Comment.modifyComment(form, article);
        Comment comment = commentRepository.findById(form.getId()).orElse(null);
        comment.patch(target);
        Comment updated = commentRepository.save(comment);
        return updated;
    }
    @Transactional
    public Comment delete(Long id){
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null){
            throw new IllegalArgumentException("Fail to delete comment! There is no comment!");
        }
        commentRepository.delete(comment);
        return comment;
    }
    public List<CommentForm> viewByArticleId(Long articleId){
        List<Comment> commentList = commentRepository.findByArticleId(articleId);
        List<CommentForm> commentFormList = new ArrayList<CommentForm>();
        for (int i=0; i< commentList.size(); i++){
            Comment comment = commentList.get(i);
            CommentForm form = CommentForm.createCommentForm(comment);
            commentFormList.add(form);
        }
        return commentFormList;
    }
    public List<CommentForm> viewByNickname(String nickname){
        List<Comment> commentList = commentRepository.findByNickname(nickname);
        List<CommentForm> commentFormList = new ArrayList<CommentForm>();
        for (int i=0; i< commentList.size(); i++){
            Comment comment = commentList.get(i);
            CommentForm form = CommentForm.createCommentForm(comment);
            commentFormList.add(form);
        }
        return commentFormList;
    }

}
