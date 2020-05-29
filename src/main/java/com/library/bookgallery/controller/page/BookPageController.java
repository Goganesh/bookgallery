package com.library.bookgallery.controller.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class BookPageController {

    @GetMapping("/books")
    public String getBooks(){
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBook() {
        return "book";
    }

}
