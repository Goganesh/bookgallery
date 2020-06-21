package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByIdIn(Collection<Long> genresId);
    Genre findByName(String name);
}
