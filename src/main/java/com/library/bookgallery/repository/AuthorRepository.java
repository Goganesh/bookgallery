package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String>, CustomAuthorRepository {
    Mono<Author> findByName(String name);
}
