package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository для работы с книгами")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать все книги и связанные сущности по имени")
    @Test
    void shouldReturnAllBooks() {
        int expectedSize = 5;
        int actualSize = repository.findAll().size();

        assertEquals(expectedSize, actualSize);
    }


    @DisplayName("возвращать книгу и связанные сущности по имени")
    @Test
    void shouldReturnBookByName() {
        Book expectedBook = em.find(Book.class, 1L);
        Book actualBook = repository.findByName("The Three Musketeers");

        assertEquals(expectedBook, actualBook);
        assertNotNull(actualBook.getAuthor());
        assertNotNull(actualBook.getGenres());
    }

    @DisplayName("возвращать книги по автору")
    @Test
    void getAllBooksByAuthor() {
        int expectedSize = 2;
        Author expectedAuthor = em.find(Author.class, 1L);
        int actualSize = repository.findByAuthor(expectedAuthor).size();

        assertEquals(expectedSize, actualSize);
    }
}