package com.library.bookgallery.controller.rest;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class AuthorRestController {

    private final ApplicationEventPublisher publisher;
    private final AuthorRepository authorRepository;

    @GetMapping("/api/authors")
    public Flux<Author> getAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping("/api/authors/{id}")
    public Mono<Author> getAuthor(@PathVariable(value = "id") String id) {
        return authorRepository.findById(id);
    }

    @DeleteMapping("/authors/{id}")
    public  Mono<Void> deleteAuthor(@PathVariable(value = "id") String id) {
        return authorRepository.deleteById(id);
    }

    @PostMapping("/authors")
    public Mono<Author> saveAuthor(@RequestBody Author author) {
        if (author.getId().equals("-1")) {
            author.setId(null);
        }
        return authorRepository.save(author);
    }

}
