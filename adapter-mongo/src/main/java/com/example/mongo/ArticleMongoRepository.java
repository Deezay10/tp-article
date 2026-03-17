package com.example.mongo;

import com.example.domain.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleMongoRepository extends MongoRepository<ArticleMongo, String> {
}
