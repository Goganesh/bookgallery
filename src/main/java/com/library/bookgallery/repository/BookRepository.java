package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph("book-author-genres-entity-graph")
    List<Book> findByAuthor(Author author);

    @EntityGraph("book-author-genres-entity-graph")
    Book findByName(String name);

    @Override
    @Query("select distinct b from Book b join fetch b.author join fetch b.genres")
    List<Book> findAll();

    Book findByNameAndAuthor(String name, Author author);
}
