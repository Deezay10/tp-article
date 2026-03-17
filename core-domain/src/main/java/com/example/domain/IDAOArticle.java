package com.example.domain;

import java.util.List;

public interface IDAOArticle {
    public Article getArticle(String id);
    public List<Article> getAllArticles();
    public boolean deleteArticle(String id);
    public Article saveArticle(Article article);
}
