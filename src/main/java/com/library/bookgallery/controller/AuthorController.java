package com.library.bookgallery.controller;

import com.library.bookgallery.controller.dto.AuthorDto;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String getAuthors(Model model){
        List<AuthorDto> authorsDto = authorService.findAll()
                .stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("authors", authorsDto);
        return "authors";
    }

    @GetMapping("/author")
    public String getEmptyAuthor(Model model) {
        AuthorDto emptyAuthorDto = new AuthorDto();
        model.addAttribute("author", emptyAuthorDto);
        return "author";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable(value = "id") Long id, Model model) {
        AuthorDto authorDto = AuthorDto.toDto(authorService.findById(id));
        model.addAttribute("author", authorDto);
        return "author";
    }


    @PostMapping("/author")
    public String saveAuthor(@ModelAttribute AuthorDto authorDto) {
        Author saved = authorService.save(AuthorDto.toAuthor(authorDto));
        return "redirect:/authors";
    }

    @DeleteMapping("/author/{id}")
    public String deleteAuthor(@PathVariable(value = "id") Long id) {
        Author authorForDelete = authorService.findById(id);
        authorService.delete(authorForDelete);
        return "redirect:/authors";
    }
}
