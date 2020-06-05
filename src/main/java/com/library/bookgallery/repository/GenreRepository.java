package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Genre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Collection;

public interface GenreRepository extends ReactiveCrudRepository<Genre, String>, CustomGenreRepository {
    Flux<Genre> findByIdIn(Collection<String> genresId);
    Mono<Genre> findByName(String name);
}
