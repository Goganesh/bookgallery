package com.library.bookgallery.service;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import java.util.List;


public interface BookService {
    List<Book> findAll();
    Book findById(long id);
    Book save(Book book);
    void deleteById(long id);
    Book findByNameAndAuthor(String name, Author author);
}
