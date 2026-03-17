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

    @GetMapping("/articles/{id}")
    public ResponseEntity<ApiResponse<Article>> getId(@PathVariable String id){

        Article article = articleService.showArticle(id);

        ApiResponse<Article> response = new ApiResponse<>(
                2002,
                "La liste des articles a été récupérée avec succès",
                article);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles")
        public ResponseEntity<ApiResponse<List<Article>>> getAll(){

            List<Article> articles = articleService.showAllArticles();

            ApiResponse<List<Article>> response = new ApiResponse<>(
                2002,
                "La liste des articles a été récupérée avec succès",
                articles);

            return ResponseEntity.ok(response);
        }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteArticle(@PathVariable String id){
        boolean result = articleService.showBoolean(id);

        if (result == false){
            ApiResponse<Boolean> response = new ApiResponse<>(
                    7001,
                    "L'article n'existe pas",
                    false
            );

            return ResponseEntity.ok(response);
        }

        else {
            ApiResponse<Boolean> response = new ApiResponse<>(
                    2002,
                    "L'article a été supprimé avec succès",
                    true
            );
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/articles/save")
    public ResponseEntity<ApiResponse<Article>> saveArticle(@RequestBody Article article) {
        Article articleUpdated = articleService.showArticleUpdated(article);
        if (articleUpdated == null){
            ApiResponse<Article> response = new ApiResponse<>(
                    7006,
                    "Le titre est déjà utilisé",
                    articleUpdated
            );

            return ResponseEntity.ok(response);

        } else if (articleUpdated.id == article.id) {
            ApiResponse<Article> response = new ApiResponse<>(
                    2003,
                    "Article modifié avec succès",
                    articleUpdated
            );

            return ResponseEntity.ok(response);
        }
        else {
            ApiResponse<Article> response = new ApiResponse<>(
                    2002,
                    "Article créé avec succès",
                    articleUpdated
            );

            return ResponseEntity.ok(response);
        }

    }
}
