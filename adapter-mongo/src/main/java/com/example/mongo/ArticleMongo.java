package com.example.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//On paramètre la collection articles
@Document(collection = "articles")
public class ArticleMongo {

    //On met en id le paramètre id
    @Id
    public String id;

    public String title;

    public String description;
}
