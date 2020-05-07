package com.library.bookgallery.controller;

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

@Controller
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/books")
    public String getBooks(Model model){
        List<BookDto> booksDto = bookService.findAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("books", booksDto);
        return "books";
    }

    @GetMapping("/book")
    public String getEmptyBook(Model model) {
        BookDto emptyBookDto = new BookDto();
        emptyBookDto.setAuthorDto(new AuthorDto());
        emptyBookDto.setGenresDto(new ArrayList<>());
        List<AuthorDto> authorsDto = authorService.findAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
        List<GenreDto> genresDto = genreService.findAll().stream().map(GenreDto::toDto).collect(Collectors.toList());

        model.addAttribute("book", emptyBookDto);
        model.addAttribute("authors", authorsDto);
        model.addAttribute("genres", genresDto);

        return "book";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable(value = "id") Long id, Model model) {
        BookDto bookDto = BookDto.toDto(bookService.findById(id));
        List<AuthorDto> authorsDto = authorService.findAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
        List<GenreDto> genresDto = genreService.findAll().stream().map(GenreDto::toDto).collect(Collectors.toList());

        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authorsDto);
        model.addAttribute("genres", genresDto);
        return "book";
    }

    @PostMapping("/book")
    public String saveBook(@RequestParam("id") Long id
            , @RequestParam("name") String name
            , @RequestParam("author") Long authorId
            , @RequestParam("genres") List<Long> genresId) {
        Author author = authorService.findById(authorId);
        List<Genre> genres = genreService.findByIdIn(genresId);
        Book book;
        if (id.longValue() == -1){
            book = new Book(name, author, genres);
        } else {
            book = new Book(id, name, author, genres);
        }
        Book saved = bookService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable(value = "id") Long id) {
        Book bookForDelete = bookService.findById(id);
        bookService.delete(bookForDelete);
        return "redirect:/books";
    }
}
