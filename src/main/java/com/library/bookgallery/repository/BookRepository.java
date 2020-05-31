package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {
    Flux<Book> findByAuthor(Author author);
    void deleteByAuthor(Author author);
    void deleteByGenres(Genre genre);
    Mono<Book> findByName(String name);
}
