package com.example.jpa;

import com.example.domain.Article;
import com.example.domain.IDAOArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class DAOArticleJpa implements IDAOArticle {

    @Autowired
    ArticleJpaRepository articleJpaRepository;

    @Override
    public Article getId(String id){
        ArticleJpa articleJpa = articleJpaRepository.findById(id).orElse(null);

        if (articleJpa == null) {
            return null;
        }
        else {
            Article article = new Article();
            article.id = articleJpa.id;
            article.title = articleJpa.title;
            article.description = articleJpa.description;

            return article;
        }
    }
    @Override
    public List<Article> getAll(){
        List<ArticleJpa> articlesJpa = articleJpaRepository.findAll();
        List<Article> articles = new ArrayList<Article>();

        for (ArticleJpa articleJpa : articlesJpa) {
            Article article = new Article();
            article.id = articleJpa.id;
            article.title = articleJpa.title;
            article.description = articleJpa.description;
            articles.add(article);
        }
        return articles;
    }

    @Override
    public boolean deleteArticle(String id) {
        ArticleJpa articleJpa = articleJpaRepository.findById(id).orElse(null);

        if (articleJpa == null) {
            return false;
        } else {
            articleJpaRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public Article saveArticle(Article article) {
        List<ArticleJpa> articlesJpa = articleJpaRepository.findAll();

        if (article.id == null){
            String articleId = UUID.randomUUID().toString();
            for (ArticleJpa articleJpaBDD : articlesJpa){
                if (Objects.equals(article.title, articleJpaBDD.title)){
                    return null;
                }
                else {
                    continue;
                }
            }
            ArticleJpa newArticleJpa = new ArticleJpa();
            newArticleJpa.id = articleId;
            newArticleJpa.title = article.title;
            newArticleJpa.description = article.description;
            articleJpaRepository.save(newArticleJpa);

            Article articleCreated = new Article();
            articleCreated.id = newArticleJpa.id;
            articleCreated.title = newArticleJpa.title;
            articleCreated.description = newArticleJpa.description;

            return articleCreated;
        }
        else {
            ArticleJpa articleJpa = articleJpaRepository.findById(article.id).orElse(null);
            for (ArticleJpa articleJpaBDD : articlesJpa){
                if (Objects.equals(article.title, articleJpaBDD.title)){
                    return null;
                }
                else {
                    continue;
                }
            }

            articleJpa.title = article.title;
            articleJpa.description = article.description;

            articleJpaRepository.save(articleJpa);

            return article;
        }
    }
}
