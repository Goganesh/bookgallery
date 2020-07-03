package com.library.bookgallery.controller.rest;

import com.library.bookgallery.controller.dto.AuthorDto;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.conf.integration.AuthorGate;
import com.library.bookgallery.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AuthorRestController {

    private final AuthorGate authorGate;

    @GetMapping("/api/authors")
    public List<AuthorDto> getAuthors(){
        List<Author> authors = authorGate.findAllAuthor();
        List<AuthorDto> authorsDto = authors
                .stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
        return authorsDto;
    }

    @GetMapping("/api/authors/{id}")
    public AuthorDto getAuthor(@PathVariable(value = "id") Long id) {
        Author author = authorGate.findAuthorById(id);
        AuthorDto authorDto = AuthorDto.toDto(author);
        return authorDto;
    }

    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable(value = "id") Long id) {
        authorGate.deleteAuthorById(id);
        //authorGate.process(MessageBuilder.withPayload(id).setHeader("customer","author_delete").build());
    }

    @PostMapping("/authors")
    public void saveAuthor(@RequestBody AuthorDto authorDto) {
        Author author = AuthorDto.toAuthor(authorDto);
        authorGate.saveAuthor(author);
        //authorGate.process(MessageBuilder.withPayload(author).setHeader("customer","author_save").build());
    }

}
