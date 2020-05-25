package com.library.bookgallery.controller.rest;

import com.library.bookgallery.controller.dto.GenreDto;
import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/api/genres")
    public List<GenreDto> getGenres(){
        List<GenreDto> genresDto = genreService.findAll()
                .stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
        return genresDto;
    }

    @GetMapping("/api/genres/{id}")
    public GenreDto getGenre(@PathVariable(value = "id") Long id) {
        GenreDto genreDto = GenreDto.toDto(genreService.findById(id));
        return genreDto;
    }

    @DeleteMapping("/genres/{id}")
    public void deleteGenre(@PathVariable(value = "id") Long id) {
        genreService.deleteById(id);
    }
}
