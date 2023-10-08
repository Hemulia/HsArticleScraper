package com.example.demo.component;

import com.example.demo.model.Article;
import com.example.demo.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class ScheduledScraperTask {
    private final ArticleScraper articleScraper;

    private final ArticleRepo articleRepo;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ScheduledScraperTask(
            ArticleScraper articleScraper, ArticleRepo articleRepo) {
        this.articleScraper = articleScraper;
        this.articleRepo = articleRepo;
    }

    @Scheduled(fixedRate = 60000)
    public void scrapeArticlesTask() throws Exception {
        logger.info("Scheduled task started.");
        List<Article> scrapedArticles = articleScraper.scrapeAndSaveArticles();

        // Loop through scraped articles and check if they already exist, if not,
        // save them
        for (Article scrapedArticle : scrapedArticles) {
            Article existingArticle =
                    articleRepo.findByLink(scrapedArticle.getLink());
            if (existingArticle == null) {
                articleRepo.save(scrapedArticle);
            }
        }

        logger.info("Scheduled task completed.");
    }
}

