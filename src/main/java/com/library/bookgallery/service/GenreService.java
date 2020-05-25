package com.library.bookgallery.service;

import com.library.bookgallery.domain.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreService {
    List<Genre> findAll();
    Genre findById(long id);
    Genre save(Genre genre);
    void deleteById(long id);
    List<Genre> findByIdIn(List<Long> genresId);
}
