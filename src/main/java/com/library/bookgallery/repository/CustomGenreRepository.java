package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Genre;
import reactor.core.publisher.Mono;

public interface CustomGenreRepository {
    Mono<Void> deleteGenreWithBooksById(String id);
    Mono<Void> deleteGenreWithBooksByGenre(Genre genre);
    Mono<Genre> updateGenreWithBooksByGenre(Genre genre);
}
