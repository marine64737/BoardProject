package com.example.BoardProject.Repository;

import com.example.BoardProject.DTO.CommentForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("ArticleId Test Success")
    void findByArticleId_1() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article article = new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment a = new Comment(1L, "Park", "굿 윌 헌팅", clock, article);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment b = new Comment(2L, "Kim", "아이 엠 샘", clock, article);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment c = new Comment(3L, "Choi", "쇼생크 탈출", clock, article);
        List<Comment> expected = Arrays.asList(a, b, c);
        List<CommentForm> commentFormList = commentRepository.findByArticleId(4L);
        assertEquals(expected.toString(), commentFormList.toString());
    }
    @Test
    @DisplayName("ArticleId Test Fail 1")
    void findByArticleId_2() {
        List<Comment> expected = new ArrayList<>();
        List<CommentForm> commentFormList = commentRepository.findByArticleId(9L);
        assertEquals(expected, commentFormList);
    }
    @Test
    @DisplayName("ArticleId Test Fail 2")
    void findByArticleId_3() {
        List<Comment> expected = new ArrayList<>();
        List<CommentForm> commentFormList = commentRepository.findByArticleId(999L);
        assertEquals(expected, commentFormList);
    }
    @Test
    @DisplayName("ArticleId Test Fail 3")
    void findByArticleId_4() {
        List<Comment> expected = new ArrayList<>();
        List<CommentForm> commentFormList = commentRepository.findByArticleId(-1L);
        assertEquals(expected, commentFormList);
    }

    @Test
    @DisplayName("Nickname Test Success")
    void findByNickname_1() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article firstArticle = new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article secondArticle = new Article(5L,"당신의 소울 푸드는?", "댓글 ㄱㄱ", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article thirdArticle = new Article(6L,"당신의 취미는?", "댓글 ㄱㄱㄱ", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment a = new Comment(1L, "Park", "굿 윌 헌팅", clock, firstArticle);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment b = new Comment(4L, "Park", "치킨", clock, secondArticle);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment c = new Comment(7L, "Park", "조깅", clock, thirdArticle);
        List<Comment> expected = Arrays.asList(a, b, c);
        List<CommentForm> commentFormList = commentRepository.findByNickname("Park");
        assertEquals(expected.toString(), commentFormList.toString());
    }
    @Test
    @DisplayName("Nickname Test Success 2")
    void findByNickname_2() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article firstArticle = new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article secondArticle = new Article(5L,"당신의 소울 푸드는?", "댓글 ㄱㄱ", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article thirdArticle = new Article(6L,"당신의 취미는?", "댓글 ㄱㄱㄱ", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment a = new Comment(2L, "Kim", "아이 엠 샘", clock, firstArticle);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment b = new Comment(5L, "Kim", "샤부샤부", clock, secondArticle);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Comment c = new Comment(8L, "Kim", "유튜브 시청", clock, thirdArticle);
        List<Comment> expected = Arrays.asList(a, b, c);
        List<CommentForm> commentFormList = commentRepository.findByNickname("Kim");
        assertEquals(expected.toString(), commentFormList.toString());
    }
    @Test
    @DisplayName("Nickname Test Fail 1")
    void findByNickname_3() {
        List<Comment> expected = new ArrayList<>();
        List<CommentForm> commentFormList = commentRepository.findByNickname(null);
        assertEquals(expected.toString(), commentFormList.toString());
    }
    @Test
    @DisplayName("Nickname Test Fail 2")
    void findByNickname_4() {
        List<Comment> expected = new ArrayList<>();
        List<CommentForm> commentFormList = commentRepository.findByNickname("");
        assertEquals(expected.toString(), commentFormList.toString());
    }
}