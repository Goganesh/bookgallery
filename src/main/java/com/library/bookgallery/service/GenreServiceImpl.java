package com.library.bookgallery.service;

import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.exception.NotFoundException;
import com.library.bookgallery.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findByIdIn(List<Long> genresId) {
        return genreRepository.findByIdIn(genresId);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(long id) {
        return genreRepository.findById(id).orElseThrow(()-> new NotFoundException("Object not found in database"));
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }
}
