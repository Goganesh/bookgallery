package com.library.bookgallery.controller;

import com.library.bookgallery.controller.dto.GenreDto;
import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public String getGenres(Model model){
        List<GenreDto> genresDto = genreService.findAll()
                .stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("genres", genresDto);
        return "genres";
    }

    @GetMapping("/genre")
    public String getEmptyGenre(Model model) {
        GenreDto emptyGenreDto = new GenreDto();
        model.addAttribute("genre", emptyGenreDto);
        return "genre";
    }

    @GetMapping("/genre/{id}")
    public String getGenre(@PathVariable(value = "id") Long id, Model model) {
        GenreDto genreDto = GenreDto.toDto(genreService.findById(id));
        model.addAttribute("genre", genreDto);
        return "genre";
    }

    @PostMapping("/genre")
    public String saveGenre(@ModelAttribute GenreDto genreDto) {
        Genre saved = genreService.save(GenreDto.toGenre(genreDto));
        return "redirect:/genres";
    }

    @DeleteMapping("/genre/{id}")
    public String deleteGenre(@PathVariable(value = "id") Long id) {
        Genre genreForDelete = genreService.findById(id);
        genreService.delete(genreForDelete);
        return "redirect:/genres";
    }
}
