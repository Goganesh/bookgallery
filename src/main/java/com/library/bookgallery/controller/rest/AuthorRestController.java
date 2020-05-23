package com.library.bookgallery.controller.rest;

import com.library.bookgallery.controller.dto.AuthorDto;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public List<AuthorDto> getAuthors(){
        List<AuthorDto> authorsDto = authorService.findAll()
                .stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
        return authorsDto;
    }

    @GetMapping("/api/authors/{id}")
    public AuthorDto getAuthor(@PathVariable(value = "id") Long id) {
        AuthorDto authorDto = AuthorDto.toDto(authorService.findById(id));
        return authorDto;
    }

    @DeleteMapping("/authors/{id}")
    public List<AuthorDto> deleteAuthor(@PathVariable(value = "id") Long id) {

        Author authorForDelete = authorService.findById(id);
        authorService.delete(authorForDelete);

        List<AuthorDto> authorsDto = authorService.findAll()
                .stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
        return authorsDto;
    }
}
