package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.AUTO;

@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String link;

    @Column(length = 2000)
    private String text;

    private LocalDateTime retrievalTime;

    @Column(name = "is_deleted")
    private Boolean deleted;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRetrievalTime(LocalDateTime retrievalTime) {
        this.retrievalTime = retrievalTime;
    }

    public Article(String title, String link, String text) {
        this.title = title;
        this.link = link;
        this.text = text;
        this.retrievalTime = LocalDateTime.now(); // Set the retrieval time to the current time
    }

}
