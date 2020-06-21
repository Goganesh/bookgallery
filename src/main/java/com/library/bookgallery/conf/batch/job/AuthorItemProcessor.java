package com.library.bookgallery.conf.batch.job;

import com.library.bookgallery.domain.Author;
import org.springframework.batch.item.ItemProcessor;

public class AuthorItemProcessor implements ItemProcessor<Author, Author> {
    @Override
    public Author process(Author author) throws Exception {
        System.out.println("author is " + author);
        return new Author(author.getName());
    }
}
