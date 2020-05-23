package com.library.bookgallery.controller.rest;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<BookDto> deleteBook(@PathVariable(value = "id") Long id) {
        Book bookForDelete = bookService.findById(id);
        bookService.delete(bookForDelete);

        List<BookDto> booksDto = bookService.findAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        return booksDto;
    }
}
