package com.library.bookgallery.batch.processor;

import com.library.bookgallery.batch.model.AuthorMongo;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.service.AuthorService;
import org.springframework.batch.item.ItemProcessor;

public class AuthorItemProcessor implements ItemProcessor<AuthorMongo, Author> {

    private AuthorService authorService;

    @Override
    public Author process(AuthorMongo authorMongo) throws Exception {
        String name = authorMongo.getName();
        /*if (authorService.findByName(name) == null) {
            return null;
        }*/

        return new Author(name);
    }
}
