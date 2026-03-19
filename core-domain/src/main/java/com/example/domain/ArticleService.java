package com.example.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleService {

    @Autowired
    IDAOArticle daoArticle;

    //Fonction qui montre l'article correspondant à l'id
    public Article showArticle(String id){

        //On appelle la fonction getId
        Article article = daoArticle.getId(id);

        //On retourne l'article correspondant à l'id
        return article;
    }

    //Fonction qui montre la liste des articles
    public List<Article> showAllArticles(){

        //On appelle la fonction getAll
        List<Article> articles = daoArticle.getAll();

        //On retourne la liste des articles
        return articles;
    }

    //Fonction qui montre le boolean (true si l'article a été supprimé sinon false)
    public boolean showBoolean(String id){

        //On appelle la fonction deleteArticle
        boolean result = daoArticle.deleteArticle(id);

        //On retourne le boolean
        return result;
    }

    //Fonction qui montre l'article modifié/crée
    public Article showArticleUpdated(Article article){

        //On appelle la fonction saveArticle
        Article articleUpdated = daoArticle.saveArticle(article);

        //On retourne l'article modifié/crée
        return articleUpdated;
    }
}
