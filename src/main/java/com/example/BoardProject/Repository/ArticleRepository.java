package com.example.BoardProject.Repository;

import com.example.BoardProject.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
