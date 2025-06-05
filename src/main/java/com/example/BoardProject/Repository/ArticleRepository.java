package com.example.BoardProject.Repository;

import com.example.BoardProject.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
