package com.library.bookgallery.data;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.repository.AuthorRepository;
import com.library.bookgallery.repository.BookRepository;
import com.library.bookgallery.repository.GenreRepository;
import com.mongodb.reactivestreams.client.MongoClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class InitMongoDataBase implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        dropAllData();
        insertAuthors();
        insertGenres();
        insertBooks();
    }

    private void dropAllData(){
        authorRepository.deleteAll().block();
        genreRepository.deleteAll().block();
        bookRepository.deleteAll().block();
    }

    private void insertAuthors(){
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Alexandre Dumas"));
        authors.add(new Author("Ernest Hemingway"));
        authors.add(new Author("Samerset Moem"));
        authors.add(new Author("Alexander Pushkin"));
        authors.add(new Author("Barbara Sher"));
        authorRepository.saveAll(authors).subscribe();
    }

    public void insertGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Drama"));
        genres.add(new Genre("Novel"));
        genres.add(new Genre("Comedy"));
        genres.add(new Genre("Thriller"));
        genres.add(new Genre("Psychology"));
        genres.add(new Genre("Poetry"));

        genreRepository.saveAll(genres).subscribe();
    }

    public void insertBooks() {
        List<Book> books = new ArrayList<>();
        List<Genre> genres;

        Book book1 = new Book();
        book1.setName("The Three Musketeers");
        book1.setAuthor(authorRepository.findByName("Alexandre Dumas").block());
        genres = new ArrayList<>();
        genres.add(genreRepository.findByName("Novel").block());
        genres.add(genreRepository.findByName("Comedy").block());
        book1.setGenres(genres);
        books.add(book1);

        Book book2 = new Book();
        book2.setName("The Count of Monte Cristo");
        book2.setAuthor(authorRepository.findByName("Alexandre Dumas").block());
        genres = new ArrayList<>();
        genres.add(genreRepository.findByName("Novel").block());
        genres.add(genreRepository.findByName("Drama").block());
        book2.setGenres(genres);
        books.add(book2);

        Book book3 = new Book();
        book3.setName("The Old Man and the Sea");
        book3.setAuthor(authorRepository.findByName("Ernest Hemingway").block());
        genres = new ArrayList<>();
        genres.add(genreRepository.findByName("Drama").block());
        book3.setGenres(genres);
        books.add(book3);

        Book book4 = new Book();
        book4.setName("Wishcraft");
        book4.setAuthor(authorRepository.findByName("Barbara Sher").block());
        genres = new ArrayList<>();
        genres.add(genreRepository.findByName("Psychology").block());
        book4.setGenres(genres);
        books.add(book4);

        Book book5 = new Book();
        book5.setName("Ruslan and Ludmila");
        book5.setAuthor(authorRepository.findByName("Alexander Pushkin").block());
        genres = new ArrayList<>();
        genres.add(genreRepository.findByName("Poetry").block());
        book5.setGenres(genres);
        books.add(book5);

        bookRepository.saveAll(books).subscribe();
    }

}
