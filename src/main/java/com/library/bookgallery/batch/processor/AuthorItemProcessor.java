package com.library.bookgallery.batch.processor;

import com.library.bookgallery.batch.model.AuthorMongo;
import com.library.bookgallery.domain.Author;
import org.springframework.batch.item.ItemProcessor;

public class AuthorItemProcessor implements ItemProcessor<AuthorMongo, Author> {

    @Override
    public Author process(AuthorMongo authorMongo) throws Exception {
        String name = authorMongo.getName();
        return new Author(name);
    }
}
