package com.library.bookgallery.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.library.bookgallery.controller.rest.json.BookDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = BookDeserializer.class)
public class Book {
    @Id
    private String id;

    @Field
    private String name;

    @Field
    private Author author;

    @Field
    private List<Genre> genres;


    public Book(String id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Book(String name, Author author, List<Genre> genres) {
        this.name = name;
        this.author = author;
        this.genres = genres;
    }
}