package com.example.app;

import com.example.domain.Article;
import com.example.domain.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleRestController{

    @Autowired
    ArticleService articleService;

    //Lien pour obtenir l'article avec l'id donné
    @GetMapping("/articles/{id}")
    public ResponseEntity<ApiResponse<Article>> getId(@PathVariable String id){

        //On appelle la fonction qui récupère l'article avec l'id correspondant
        Article article = articleService.showArticle(id);

        //On crée le Json qu'on va renvoyer
        ApiResponse<Article> response = new ApiResponse<>(
                2002,
                "La liste des articles a été récupérée avec succès",
                article);

        //On renvoie le Json
        return ResponseEntity.ok(response);
    }

    //Lien pour obtenir la liste de tous les articles
    @GetMapping("/articles")
    public ResponseEntity<ApiResponse<List<Article>>> getAll(){

        //On appelle la fonction qui récupère la liste de tous les articles
        List<Article> articles = articleService.showAllArticles();

        //On crée le Json qu'on va renvoyer
        ApiResponse<List<Article>> response = new ApiResponse<>(
                2002,
                "La liste des articles a été récupérée avec succès",
                articles);

        //On renvoie le Json
        return ResponseEntity.ok(response);

    }

    //Lien pour supprimer l'article correspondant à l'id
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteArticle(@PathVariable String id){

        //On appelle la fonction qui supprime l'article et qui renvoie un boolean (true si article supprimé sinon false)
        boolean result = articleService.showBoolean(id);

        //Si la fonction a renvoyé false
        if (result == false){

            //On crée le Json qu'on va renvoyer
            ApiResponse<Boolean> response = new ApiResponse<>(
                    7001,
                    "L'article n'existe pas",
                    false
            );

            //On renvoie le Json
            return ResponseEntity.ok(response);
        }

        else {

            //On crée le Json qu'on va renvoyer
            ApiResponse<Boolean> response = new ApiResponse<>(
                    2002,
                    "L'article a été supprimé avec succès",
                    true
            );

            //On renvoie le Json
            return ResponseEntity.ok(response);
        }
    }

    //Lien pour save l'article envoyé
    @PostMapping("/articles/save")
    public ResponseEntity<ApiResponse<Article>> saveArticle(@RequestBody Article article) {

        //On appelle la fonction qui save l'article envoyé et renvoie l'article
        Article articleUpdated = articleService.showArticleUpdated(article);

        //Si l'article est null cela signifie que le titre est déjà utilisé
        if (articleUpdated == null){

            //On crée le Json qu'on va renvoyer
            ApiResponse<Article> response = new ApiResponse<>(
                    7006,
                    "Le titre est déjà utilisé",
                    articleUpdated
            );

            //On renvoie le Json
            return ResponseEntity.ok(response);
        }
        //Sinon si l'article renvoyé par la fonction a le même id que l'article envoyé cela signifie que l'article a été modifié
        else if (articleUpdated.id == article.id) {

            //On crée le Json qu'on va renvoyer
            ApiResponse<Article> response = new ApiResponse<>(
                    2003,
                    "Article modifié avec succès",
                    articleUpdated
            );

            //On renvoie le Json
            return ResponseEntity.ok(response);
        }

        else {
            //On crée le Json qu'on va renvoyer
            ApiResponse<Article> response = new ApiResponse<>(
                    2002,
                    "Article créé avec succès",
                    articleUpdated
            );

            //On renvoie le Json
            return ResponseEntity.ok(response);
        }

    }
}
