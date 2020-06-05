package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository для работы с авторами")
@DataMongoTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @DisplayName("сохранять автора и возвращать моно")
    @Test
    void shouldSaveAuthorAndReturnMono() {
        String expectedAuthorName = "Georgy Basiladze";
        Author author = new Author(expectedAuthorName);
        Mono<Author> authorMono = repository.save(author);

        StepVerifier
                .create(authorMono)
                .expectNext(author)
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращать автора по имени")
    @Test
    void shouldReturnAuthorByName() {
        String expectedAuthorName = "Alexandre Dumas";
        Mono<Author> actualAuthor = repository.findByName(expectedAuthorName);

        StepVerifier
                .create(actualAuthor)
                .assertNext(author -> assertEquals(expectedAuthorName, author.getName()));
    }
}