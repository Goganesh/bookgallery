package com.library.bookgallery.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "genres")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    private String id;
    @Field
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}