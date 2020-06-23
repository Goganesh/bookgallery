package com.library.bookgallery.batch.processor;

import com.library.bookgallery.batch.model.BookMongo;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.service.AuthorService;
import com.library.bookgallery.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookItemProcessor implements ItemProcessor<BookMongo, Book> {

    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Book process(BookMongo bookMongo) throws Exception {
        String name = bookMongo.getName();
        String authorName = bookMongo.getAuthor().getName();
        List<String> genresName = bookMongo.getGenres().stream()
                .map(genre -> genre.getName())
                .collect(Collectors.toList());

        Author author = authorService.findByName(authorName);
        List<Genre> genres = genresName.stream()
                .map(genre-> genreService.findByName(genre))
                .collect(Collectors.toList());

        return new Book(name, author, genres);
    }
}
