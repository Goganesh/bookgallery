package com.library.bookgallery.controller.rest;

import com.library.bookgallery.domain.Book;
import com.library.bookgallery.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class BookRestController {

    private final BookRepository bookRepository;

    @GetMapping("/api/books")
    public Flux<Book> getBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Mono<Book> getBook(@PathVariable(value = "id") String id) {
        return bookRepository.findById(id);
    }

    @DeleteMapping("/books/{id}")
    public Mono<Void> deleteBook(@PathVariable(value = "id") String id) {
        return bookRepository.deleteById(id);
    }

    @SneakyThrows
    @PostMapping("/books")
    public Mono<Book> saveBook(@RequestBody Book book) {
        System.out.println(book);
        if (book.getId().equals("-1")) {
            book.setId(null);
        }
        return bookRepository.save(book);
    }
}
