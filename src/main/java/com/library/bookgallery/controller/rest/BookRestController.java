package com.library.bookgallery.controller.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bookgallery.controller.dto.AuthorDto;
import com.library.bookgallery.controller.dto.BookDto;
import com.library.bookgallery.controller.dto.GenreDto;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.service.AuthorService;
import com.library.bookgallery.service.BookService;
import com.library.bookgallery.service.GenreService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class BookRestController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/api/books")
    public List<BookDto> getBooks(){
        List<BookDto> booksDto = bookService.findAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        return booksDto;
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable(value = "id") Long id) {
        BookDto bookDto = BookDto.toDto(bookService.findById(id));

        return bookDto;
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable(value = "id") Long id) {
        bookService.deleteById(id);
    }

    @SneakyThrows
    @PostMapping("/books")
    public void saveBook(@RequestBody BookDto bookDto) {
        Book saved = bookService.save(BookDto.toBook(bookDto));
    }
}
