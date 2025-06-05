package com.example.BoardProject.Service;

import com.example.BoardProject.DTO.ArticleForm;
import com.example.BoardProject.Entity.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void index() {
        //1. 예상 데이터
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article a = new Article(1L, "aaaa", "1111", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article b = new Article(2L, "bbbb", "2222", clock);
        now = LocalDateTime.now();
        clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article c = new Article(3L, "cccc", "3333", clock);
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));
        //2. 실제 데이터
        List<Article> articleList = articleService.index();
        //3. 비교 및 검증
        assertEquals(expected.toString(), articleList.toString());
    }

    @Test
    @Transactional
    void create_success() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article expected = new Article(4L, "Hello", "world", clock);
        //2. 실제 데이터
        ArticleForm articleForm = new ArticleForm(null, "Hello", "world", clock);
        Article created = articleService.create(articleForm);
        //3. 비교 및 검증
        assertEquals(expected.toString(), created.toString());
    }
    @Test
    void create_fail() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article expected = null;
        //2. 실제 데이터
        ArticleForm articleForm = new ArticleForm(4L, "Hello", "world", clock);
        Article created = articleService.create(articleForm);
        //3. 비교 및 검증
        assertEquals(expected, created);
    }

    @Test
    void show_success() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article expected = new Article(2L, "bbbb", "2222", clock);
        Article showed = articleService.show(2L);
        assertEquals(expected.toString(), showed.toString());
    }
    @Test
    void show_fail() {
        Article expected = null;
        Article showed = articleService.show(4L);
        assertEquals(expected, showed);
    }

    @Test
    @Transactional
    void update_success_1() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article expected = new Article(2L, "bbcc", "2233", clock);
        ArticleForm form = new ArticleForm(2L, "bbcc", "2233", clock);
        Article updated = articleService.update(2L, form);
        assertEquals(expected.toString(), updated.toString());
    }
    @Test
    @Transactional
    void update_success_2() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article expected = new Article(2L, "bbcc", "2222", clock);
        ArticleForm form = new ArticleForm(2L, "bbcc", null, clock);
        Article updated = articleService.update(2L, form);
        assertEquals(expected.toString(), updated.toString());
    }
    @Test
    void update_fail() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article expected = null;
        ArticleForm form = new ArticleForm(2L, "bbcc", "2233", clock);
        Article updated = articleService.update(4L, form);
        assertEquals(expected, updated);
    }

    @Test
    @Transactional
    void delete_success() {
        LocalDateTime now = LocalDateTime.now();
        String clock = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Article expected = new Article(2L, "bbbb", "2222", clock);;
        Article deleted = articleService.delete(2L);
        assertEquals(expected.toString(), deleted.toString());
    }
    @Test
    void delete_fail() {
        Article expected = null;
        Article deleted = articleService.delete(4L);
        assertEquals(expected, deleted);
    }
}