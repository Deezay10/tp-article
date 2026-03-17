package com.example.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleService {

    @Autowired
    IDAOArticle daoArticle;

    public Article showArticle(String id){
        Article article = daoArticle.getId(id);

        return article;
    }

    public List<Article> showAllArticles(){
        List<Article> articles = daoArticle.getAll();

        return articles;
    }

    public boolean showBoolean(String id){
        boolean result = daoArticle.deleteArticle(id);

        return result;
    }

    public Article showArticleUpdated(Article article){
        Article articleUpdated = daoArticle.saveArticle(article);

        return articleUpdated;
    }
}
