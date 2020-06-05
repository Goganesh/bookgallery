package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomGenreRepositoryImpl implements CustomGenreRepository {

    private final ReactiveMongoTemplate template;
    private final BookRepository bookRepository;

    @SneakyThrows
    @Override
    public Mono<Void> deleteGenreWithBooksById(String id) {
        return  deleteGenreWithBooksByGenre(template.findById(id, Genre.class));
    }

    @Override
    public Mono<Void> deleteGenreWithBooksByGenre(Mono<Genre> genre) {
        return template.remove(genre).then(bookRepository.deleteByGenres(genre));
    }

    @SneakyThrows
    @Override
    public Mono<Genre> updateGenreWithBooksByGenre(Genre genre) {
        Flux<Book> books = bookRepository.findByGenresId(genre.getId()).map(book->
                new Book (book.getId(),
                        book.getName(),
                        book.getAuthor(),
                        book.getGenres()
                                .stream()
                                .filter(g -> g.getId().equals(genre.getId()))
                                .map(g1 -> genre)
                                .collect(Collectors.toList()))
        );

        return bookRepository.saveAll(books).then(template.save(genre));
    }
}
