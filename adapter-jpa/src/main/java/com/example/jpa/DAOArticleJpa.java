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

    //On crée la fonction qui renvoie l'article correspondant à l'id
    @Override
    public Article getId(String id){

        //On cherche l'article correspondant à l'id si il n'existe pas, on renvoie null
        ArticleJpa articleJpa = articleJpaRepository.findById(id).orElse(null);

        // Si article est null, il n'existe pas
        if (articleJpa == null) {

            //On renvoie null
            return null;
        }
        else {

            //On crée un nouvel article en java pour pouvoir mettre les informations de l'article en jpa dedans
            Article article = new Article();
            article.id = articleJpa.id;
            article.title = articleJpa.title;
            article.description = articleJpa.description;

            //On renvoie l'article
            return article;
        }
    }

    //On crée la fonction qui renvoie la liste de tout les articles
    @Override
    public List<Article> getAll(){

        //On prend tout les articles de la base de données
        List<ArticleJpa> articlesJpa = articleJpaRepository.findAll();

        //On crée une liste d'Article qui va récupérer tout les articles après transformation
        List<Article> articles = new ArrayList<Article>();

        //Pour tout les articles en Jpa dans la liste des articles de la base de données
        for (ArticleJpa articleJpa : articlesJpa) {

            //On crée un nouvel article en java pour pouvoir mettre les informations de l'article en jpa dedans
            Article article = new Article();
            article.id = articleJpa.id;
            article.title = articleJpa.title;
            article.description = articleJpa.description;

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
        ArticleJpa articleJpa = articleJpaRepository.findById(id).orElse(null);

        // Si article est null, il n'existe pas
        if (articleJpa == null) {

            //On renvoie false
            return false;


        } else {

            //On supprime l'article
            articleJpaRepository.deleteById(id);

            //On renvoie true
            return true;
        }
    }

    //On crée la fonction qui renvoie l'article modifié/crée
    @Override
    public Article saveArticle(Article article) {

        //On récupère tout les articles de la base de données
        List<ArticleJpa> articlesJpa = articleJpaRepository.findAll();

        //Si l'article donné n'a pas d'id cela signifie qu'il faut qu'il soit crée
        if (article.id == null){

            //On génère un id en uuid
            String articleId = UUID.randomUUID().toString();

            //Pour tout les articles de la base de données
            for (ArticleJpa articleJpaBDD : articlesJpa){

                //Si le titre de l'article donnée est égale à un titre d'un article de la base de données cela signifie que le titre est déjà dedans
                if (Objects.equals(article.title, articleJpaBDD.title)){

                    //On retourne null
                    return null;
                }
                else {

                    //On continue avec le prochain article
                    continue;
                }
            }

            //On crée un article en Jpa qui va prendre les informations de l'article donné avec le nouvel id pour l'ajouter à la base de données
            ArticleJpa newArticleJpa = new ArticleJpa();
            newArticleJpa.id = articleId;
            newArticleJpa.title = article.title;
            newArticleJpa.description = article.description;
            articleJpaRepository.save(newArticleJpa);

            //On crée un nouvel article en java pour pouvoir mettre les informations de l'article en jpa dedans
            Article articleCreated = new Article();
            articleCreated.id = newArticleJpa.id;
            articleCreated.title = newArticleJpa.title;
            articleCreated.description = newArticleJpa.description;


            //On retourne l'article crée
            return articleCreated;
        }
        else {

            //On récupère tout les articles de la base de données
            ArticleJpa articleJpa = articleJpaRepository.findById(article.id).orElse(null);

            //Pour tout les articles de la base de données
            for (ArticleJpa articleJpaBDD : articlesJpa){

                //Si le titre de l'article donnée est égale à un titre d'un article de la base de données cela signifie que le titre est déjà dedans
                if (Objects.equals(article.title, articleJpaBDD.title)){

                    //On retourne null
                    return null;
                }
                else {

                    //On continue avec le prochain article
                    continue;
                }
            }

            //On met à jour l'article dans la base de données
            articleJpa.title = article.title;
            articleJpa.description = article.description;
            articleJpaRepository.save(articleJpa);

            //On crée un nouvel article en java pour pouvoir mettre les informations de l'article modifé en jpa dedans
            Article articleUpdated = new Article();
            articleUpdated.id = articleJpa.id;
            articleUpdated.title = articleJpa.title;
            articleUpdated.description = articleJpa.description;

            //On retourne l'article modifié
            return articleUpdated;
        }
    }
}
