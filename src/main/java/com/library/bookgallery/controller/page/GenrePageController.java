package com.library.bookgallery.controller.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class GenrePageController {

    @GetMapping("/genres")
    public String getGenres(){
        return "genres";
    }

    @GetMapping("/genres/{id}")
    public String getGenre() {
        return "genre";
    }

}
