package com.library.bookgallery.controller.page;

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
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@AllArgsConstructor
public class BookPageController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/books")
    public String getBooks(){
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBook() {
        return "book";
    }

    @PostMapping("/books")
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
}
