package com.library.bookgallery.controller.page;

import com.library.bookgallery.controller.dto.GenreDto;
import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class GenrePageController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public String getGenres(){
        return "genres";
    }

    @GetMapping("/genres/{id}")
    public String getGenre() {
        return "genre";
    }

    @PostMapping("/genres")
    public String saveGenre(@ModelAttribute GenreDto genreDto) {
        Genre saved = genreService.save(GenreDto.toGenre(genreDto));
        return "redirect:/genres";
    }
}
