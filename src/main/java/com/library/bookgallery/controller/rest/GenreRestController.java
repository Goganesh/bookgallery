package com.library.bookgallery.controller.rest;

import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class GenreRestController {

    private final GenreRepository genreRepository;

    @GetMapping("/api/genres")
    public Flux<Genre> getGenres(){
        return genreRepository.findAll();
    }

    @GetMapping("/api/genres/{id}")
    public Mono<Genre> getGenre(@PathVariable(value = "id") String id) {
        return genreRepository.findById(id);
    }

    @DeleteMapping("/genres/{id}")
    public Mono<Void> deleteGenre(@PathVariable(value = "id") String id) {
        return genreRepository.deleteGenreWithBooksById(id);
    }

    @PostMapping("/genres")
    public Mono<Genre> saveGenre(@RequestBody Genre genre) {
        if (genre.getId().equals("-1")) {
            genre.setId(null);
            return genreRepository.save(genre);
        }
        return genreRepository.updateGenreWithBooksByGenre(genre);
    }
}
