package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
