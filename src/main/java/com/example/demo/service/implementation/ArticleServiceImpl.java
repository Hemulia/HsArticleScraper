package com.example.demo.service.implementation;

import com.example.demo.component.ArticleScraper;
import com.example.demo.model.Article;
import com.example.demo.repo.ArticleRepo;
import com.example.demo.service.ArticleService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepo articleRepo;
    private final ArticleScraper articleScraper;
    @Override
    public Article findById(Long id) {
        // Find an article by its ID in the repository
        Optional<Article> articleOptional = articleRepo.findById(id);
        return articleOptional.orElse(
                null); // Return the found article or null if not found
    }

    @Override
    public List<Article> list(int limit) {
        // Return non-deleted articles with pagination
        return articleRepo.findByDeletedFalse(PageRequest.of(0, limit));
    }

    // Method for deleting an article by its ID
    public String deleteArticle(Long id) {
        log.info("Deleting article by ID: {}", id); // Log the deletion operation
        articleRepo.deleteById(id); // Delete the article from the repository
        return "Article deleted"; // Return a confirmation message
    }
}