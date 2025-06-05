package com.example.BoardProject.Repository;

import com.example.BoardProject.DTO.CommentForm;
import com.example.BoardProject.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from comment where article_id = :articleId", nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);

    @Query(value = "select * from comment where nickname = :nickname", nativeQuery = true)
    List<Comment> findByNickname(String nickname);
}
