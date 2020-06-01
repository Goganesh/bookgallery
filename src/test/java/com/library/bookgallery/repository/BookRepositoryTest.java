package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository для работы с книгами")
@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @DisplayName("возвращать книги по автору")
    @Test
    void getAllBooksByAuthor() {
        Author author1 = new Author("Lev Tolsoy");
        Author author2 = new Author("Some guy");

        Book book1 = new Book();
        book1.setName("War and Peace");
        book1.setAuthor(author1);
        Book book2 = new Book();
        book2.setName("Game of throne");
        book2.setAuthor(author2);
        Book book3 = new Book();
        book3.setName("Anna Karenina");
        book3.setAuthor(author1);

        repository.saveAll(Arrays.asList(book1, book2, book3)).subscribe();

        StepVerifier.create(
                repository.findByAuthor(author1)
        )
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}