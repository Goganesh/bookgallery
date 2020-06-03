package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {
    Flux<Book> findByAuthor(Author author);
    Flux<Book> findByGenres(Genre genre);
    Flux<Book> findByAuthorId(String id);
    Flux<Book> findByGenresId(String id);
    Mono<Void> deleteByAuthor(Mono<Author> author);
    Mono<Void> deleteByGenres(Mono<Genre> genre);
    Mono<Book> findByName(String name);
}
