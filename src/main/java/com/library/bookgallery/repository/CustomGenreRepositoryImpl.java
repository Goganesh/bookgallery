package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
public class CustomGenreRepositoryImpl implements CustomGenreRepository {

    private final ReactiveMongoTemplate template;
    private final BookRepository bookRepository;

    @SneakyThrows
    @Override
    public Mono<Void> deleteGenreWithBooksById(String id) {
        return  deleteGenreWithBooksByGenre(template.findById(id, Genre.class).toFuture().get());
    }

    @Override
    public Mono<Void> deleteGenreWithBooksByGenre(Genre genre) {
        template.remove(genre).subscribe();
        return bookRepository.deleteByGenres(genre);
    }

    @SneakyThrows
    @Override
    public Mono<Genre> updateGenreWithBooksByGenre(Genre genre) {
        List<Book> books = bookRepository.findByGenresId(genre.getId()).collectList().toFuture().get();
        for(Book book : books) {
            int size = book.getGenres().size();
            for(int i = 0; i < size; i++) {
                if (book.getGenres().get(i).getId().equals(genre.getId())){
                    book.getGenres().set(i, genre);
                }
            }
        }
        bookRepository.saveAll(books).subscribe();

        return template.save(genre);
    }
}
