package com.library.bookgallery.controller.page;

import com.library.bookgallery.controller.dto.AuthorDto;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AuthorPageController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String getAuthors(){
        return "authors";
    }

    @GetMapping("/authors/{id}")
    public String getAuthor() {
        return "author";
    }

    @PostMapping("/authors")
    public String saveAuthor(@ModelAttribute AuthorDto authorDto) {
        System.out.println(authorDto);
        Author saved = authorService.save(AuthorDto.toAuthor(authorDto));
        return "redirect:/authors";
    }
}
