package com.library.bookgallery.controller.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AuthorPageController {

    @GetMapping("/authors")
    public String getAuthors(){
        return "authors";
    }

    @GetMapping("/authors/{id}")
    public String getAuthor() {
        return "author";
    }

}
