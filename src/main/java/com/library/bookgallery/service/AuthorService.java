package com.library.bookgallery.service;

import com.library.bookgallery.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
    Author findById(long id);
    Author save(Author author);
    boolean deleteById(long id);
    Author findByName(String name);
}
