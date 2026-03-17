package com.example.mongo;

import com.example.domain.Article;
import com.example.domain.IDAOArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DAOArticleMongo implements IDAOArticle {

    @Autowired
    ArticleMongoRepository articleMongoRepository;

    @Override
    public Article getId(String id){
        ArticleMongo articleMongo = articleMongoRepository.findById(id).orElse(null);

        if (articleMongo == null) {
            return null;
        }
        else {
            Article article = new Article();
            article.id = articleMongo.id;
            article.title = articleMongo.title;
            article.description = articleMongo.description;

            return article;
        }
    }


    @Override
    public List<Article> getAll(){
        List<ArticleMongo> articlesMongo = articleMongoRepository.findAll();
        List<Article> articles = new ArrayList<Article>();

        for (ArticleMongo articleMongo : articlesMongo) {
            Article article = new Article();
            article.id = articleMongo.id;
            article.title = articleMongo.title;
            article.description = articleMongo.description;
            articles.add(article);
        }
        return articles;
    }

    @Override
    public boolean deleteArticle(String id) {
        ArticleMongo articleMongo = articleMongoRepository.findById(id).orElse(null);

        if (articleMongo == null) {
            return false;
        } else {
            articleMongoRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public Article saveArticle(Article article) {
        List<ArticleMongo> articlesMongo = articleMongoRepository.findAll();

        if (article.id == null){
            String articleId = UUID.randomUUID().toString();
            for (ArticleMongo articleMongoBDD : articlesMongo){
                if (Objects.equals(article.title, articleMongoBDD.title)){
                    return null;
                }
                else {
                    continue;
                }
            }
            ArticleMongo newArticleMongo = new ArticleMongo();
            newArticleMongo.id = articleId;
            newArticleMongo.title = article.title;
            newArticleMongo.description = article.description;
            articleMongoRepository.save(newArticleMongo);

            Article articleCreated = new Article();
            articleCreated.id = newArticleMongo.id;
            articleCreated.title = newArticleMongo.title;
            articleCreated.description = newArticleMongo.description;

            return articleCreated;
        }
        else {
            ArticleMongo articleMongo = articleMongoRepository.findById(article.id).orElse(null);
            for (ArticleMongo articleMongoBDD : articlesMongo){
                if (Objects.equals(article.title, articleMongoBDD.title)){
                    return null;
                }
                else {
                    continue;
                }
            }

            articleMongo.title = article.title;
            articleMongo.description = article.description;

            articleMongoRepository.save(articleMongo);

            return article;
        }
    }
}
