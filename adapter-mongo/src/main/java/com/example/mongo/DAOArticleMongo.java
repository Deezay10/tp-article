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

    //On crée la fonction qui renvoie l'article correspondant à l'id
    @Override
    public Article getId(String id){

        //On cherche l'article correspondant à l'id si il n'existe pas, on renvoie null
        ArticleMongo articleMongo = articleMongoRepository.findById(id).orElse(null);

        // Si article est null, il n'existe pas
        if (articleMongo == null) {

            //On renvoie null
            return null;
        }
        else {

            //On crée un nouvel article en java pour pouvoir mettre les informations de l'article en Mongo dedans
            Article article = new Article();
            article.id = articleMongo.id;
            article.title = articleMongo.title;
            article.description = articleMongo.description;

            //On renvoie l'article
            return article;
        }
    }

    //On crée la fonction qui renvoie la liste de tout les articles
    @Override
    public List<Article> getAll(){

        //On prend tous les articles de la base de données
        List<ArticleMongo> articlesMongo = articleMongoRepository.findAll();

        //On crée une liste d'Article qui va récupérer tout les articles après transformation
        List<Article> articles = new ArrayList<Article>();

        //Pour tout les articles en Mongo dans la liste des articles de la base de données
        for (ArticleMongo articleMongo : articlesMongo) {

            //On crée un nouvel article en java pour pouvoir mettre les informations de l'article en Mongo dedans
            Article article = new Article();
            article.id = articleMongo.id;
            article.title = articleMongo.title;
            article.description = articleMongo.description;

            //On ajoute l'article dans la liste des articles
            articles.add(article);
        }

        //On renvoie la liste des articles complètes
        return articles;
    }

    //On crée la fonction qui renvoie un boolean (true si l'article a été supprimé sinon false)
    @Override
    public boolean deleteArticle(String id) {

        //On cherche l'article correspondant à l'id si il n'existe pas, on renvoie null
        ArticleMongo articleMongo = articleMongoRepository.findById(id).orElse(null);

        // Si article est null, il n'existe pas
        if (articleMongo == null) {

            //On renvoie false
            return false;
        } else {

            //On supprime l'article
            articleMongoRepository.deleteById(id);

            //On renvoie true
            return true;
        }
    }

    //On crée la fonction qui renvoie l'article modifié/crée
    @Override
    public Article saveArticle(Article article) {

        //On récupère tout les articles de la base de données
        List<ArticleMongo> articlesMongo = articleMongoRepository.findAll();

        //Si l'article donné n'a pas d'id cela signifie qu'il faut qu'il soit crée
        if (article.id == null){

            //On génère un id en uuid
            String articleId = UUID.randomUUID().toString();

            //Pour tout les articles de la base de données
            for (ArticleMongo articleMongoBDD : articlesMongo){

                //Si le titre de l'article donnée est égale à un titre d'un article de la base de données cela signifie que le titre est déjà dedans
                if (Objects.equals(article.title, articleMongoBDD.title)){

                    //On retourne null
                    return null;
                }
                else {

                    //On continue avec le prochain article
                    continue;
                }
            }

            //On crée un article en Mongo qui va prendre les informations de l'article donné avec le nouvel id pour l'ajouter à la base de données
            ArticleMongo newArticleMongo = new ArticleMongo();
            newArticleMongo.id = articleId;
            newArticleMongo.title = article.title;
            newArticleMongo.description = article.description;
            articleMongoRepository.save(newArticleMongo);

            //On crée un nouvel article en java pour pouvoir mettre les informations de l'article en Mongo dedans
            Article articleCreated = new Article();
            articleCreated.id = newArticleMongo.id;
            articleCreated.title = newArticleMongo.title;
            articleCreated.description = newArticleMongo.description;

            //On retourne l'article crée
            return articleCreated;
        }
        else {

            //On récupère tout les articles de la base de données
            ArticleMongo articleMongo = articleMongoRepository.findById(article.id).orElse(null);

            //Pour tout les articles de la base de données
            for (ArticleMongo articleMongoBDD : articlesMongo){

                //Si le titre de l'article donnée est égale à un titre d'un article de la base de données cela signifie que le titre est déjà dedans
                if (Objects.equals(article.title, articleMongoBDD.title)){

                    //On retourne null
                    return null;
                }
                else {

                    //On continue avec le prochain article
                    continue;
                }
            }

            //On met à jour l'article dans la base de données
            articleMongo.title = article.title;
            articleMongo.description = article.description;
            articleMongoRepository.save(articleMongo);

            //On crée un nouvel article en java pour pouvoir mettre les informations de l'article modifé en Mongo dedans
            Article articleUpdated = new Article();
            articleUpdated.id = articleMongo.id;
            articleUpdated.title = articleMongo.title;
            articleUpdated.description = articleMongo.description;

            //On retourne l'article modifié
            return articleUpdated;
        }
    }
}
