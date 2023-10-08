package com.example.demo.component;

import com.example.demo.HsJavaScraperApplication;
import com.example.demo.model.Article;
import com.example.demo.repo.ArticleRepo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ArticleScraper
        implements CommandLineRunner { // Implement CommandLineRunner

    private final ArticleRepo articleRepo;
    List<Article> articles = new ArrayList<>();

    @Autowired
    public ArticleScraper(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Scrape and save articles
            List<Article> scrapedArticles = scrapeAndSaveArticles();

            // Save the scraped articles to the database
            articleRepo.saveAll(scrapedArticles);

        } catch (Exception e) {
            log.error("Error occurred while scraping and saving articles: {}",
                    e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    public List<Article> scrapeAndSaveArticles() throws Exception {
        // Clear the existing list of articles
        articles.clear();

        // Connect to the website using Jsoup and retrieve the articles
        String url = "https://www.hs.fi/";
        Document doc = Jsoup.connect(url).get();
        // Selecting the section that hold all the articles
        Element articlesContainer =
                doc.select("div.relative.mt-8.md\\:mt-16.lg\\:px-8.bg-main").first();

        if (articlesContainer != null) {
            Elements articleElements = articlesContainer.select("a.block");

            int titleCount = 0;

            // Loop through the articles one by one
            for (Element articleElement : articleElements) {
                // Limit the number of articles to a maximum of 10
                if (titleCount >= 10) {
                    break;
                }

                String link = "", text = "";
                Element linkElement = articleElement.parent();

                link = linkElement.select("a.block").attr("href");

                // Filter out possible ads and non-article links (all articles include
                // "art" in the link)
                if (link != null && !link.contains("mainos") && link.contains("art")) {
                    String title =
                            articleElement
                                    .select(
                                            "span.storylogo-container span, span:not(.storylogo-container)")
                                    .text();

                    // Remove unnecessary characters or words from the title
                    if (title.charAt(0) == '|') {
                        title = title.substring(1);
                    } else if (title.contains("Tilaajille")) {
                        title = title.substring(0, title.lastIndexOf(" "));
                    }
                    titleCount++;

                    // Ensure articles are saved with valid links
                    if (!link.isEmpty()) {
                        String fullLink = "https://www.hs.fi" + link;

                        // Check if the article with the same fullLink already exists in the
                        // database
                        Article existingArticle = articleRepo.findByLink(fullLink);

                        if (existingArticle == null) {
                            Document articleDoc = Jsoup.connect(fullLink).get();
                            text = articleDoc.select("section.article-body p").text();

                            // Save only the first 200 words of the article's text
                            String[] words = text.split("\\s+");

                            int maxWordCount = 200;

                            int wordCount = 0; // Initialize the word count

                            StringBuilder output = new StringBuilder();

                            for (String word : words) {
                                // Append the word to the output
                                output.append(word).append(" ");

                                // Increment the word count
                                wordCount++;

                                // Check if the maximum word count has been reached
                                if (wordCount >= maxWordCount) {
                                    break;
                                }
                            }

                            text = output.toString();

                            log.info("Scraped Title: {}", title);
                            log.info("Scraped Link: {}", fullLink);
                            log.info("Scraped Text: {}", text);

                            Article article = new Article();
                            article.setTitle(title);
                            article.setLink(fullLink);
                            article.setText(text);
                            article.setDeleted(false); // Mark the article as not deleted
                            article.setRetrievalTime(LocalDateTime.now());

                            // Save the article directly to the database
                            articleRepo.save(article);

                            // Add the article to the list
                            articles.add(article);
                        } else {
                            log.warn("Article already exists or empty link for article: {}",
                                    title);
                        }
                    }
                }
            }
        }

        return articles; // Return the list of scraped articles
    }
}



