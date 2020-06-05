package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import reactor.core.publisher.Mono;

public interface CustomAuthorRepository {
    Mono<Void> deleteAuthorWithBooksById(String id);
    Mono<Void> deleteAuthorWithBooksByAuthor(Mono<Author> author);
    Mono<Author> updateAuthorWithBooksByAuthor(Author author);
}
