package com.library.bookgallery.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorMongo {
    @Id
    private String id;
    @Field(name = "name")
    private String name;
}
