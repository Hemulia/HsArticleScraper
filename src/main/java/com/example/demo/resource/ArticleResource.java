package com.example.demo.resource;

import static org.springframework.http.ResponseEntity.ok;

import com.example.demo.component.ArticleScraper;
import com.example.demo.model.Article;
import com.example.demo.repo.ArticleRepo;
import com.example.demo.service.implementation.ArticleServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleResource {
    private final ArticleServiceImpl articleService;
    private final ArticleRepo articleRepo;
    private ArticleScraper articleScraper;

    @Autowired
    public void RefreshController(ArticleScraper articleScraper) {
        this.articleScraper =
                articleScraper; // Constructor-based injection for ArticleScraper
    }

    @GetMapping("/list")
    public ResponseEntity<List<Article>> listArticles(
            @RequestParam(defaultValue = "10") int limit) {
        // Get a list of articles from the articleService with the limit of sending
        // max 10 articles
        List<Article> articles = articleService.list(limit);
        return ok(articles); // Return a response with the list of articles and a
        // HTTP 200 status code
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") Long id) {
        try {
            Article article = articleRepo.findById(id).orElse(null);
            if (article != null) {
                article.setDeleted(true);
                articleRepo.save(article); // Update the deleted flag
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh-articles")
    public ResponseEntity<String> refreshArticles() {
        try {
            // Trigger the scrapeAndSaveArticles method of the ArticleScraper
            articleRepo.deleteAll();
            articleScraper.scrapeAndSaveArticles();
            return ResponseEntity.ok("Articles refreshed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    "Error refreshing articles: " + e.getMessage());
            // Return an error message with HTTP 500 status and an error description
            // on failure
        }
    }
}
