package com.example.demo.repo;

import com.example.demo.model.Article;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    Article findByLink(String link);
    List<Article> findByDeletedFalse(PageRequest of);
}
