package com.example.demo.service;

import com.example.demo.model.Article;

import java.util.Collection;

public interface ArticleService {

    // Find an article by its ID
    Article findById(Long id);

    // Retrieve a collection (list) of articles with a specified limit
    Collection<Article> list(int limit);

    // Delete an article by its ID and return a status message
    String deleteArticle(Long id);

}
