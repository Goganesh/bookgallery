package com.library.bookgallery.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookMongo {
    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "author")
    private AuthorMongo author;
    @Field(name = "genres")
    private List<GenreMongo> genres;

}
