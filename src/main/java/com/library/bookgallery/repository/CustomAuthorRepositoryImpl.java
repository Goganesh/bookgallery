package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CustomAuthorRepositoryImpl implements CustomAuthorRepository {

    private final ReactiveMongoTemplate template;
    private final BookRepository bookRepository;

    @SneakyThrows
    @Override
    public Mono<Void> deleteAuthorWithBooksById(String id) {
        return deleteAuthorWithBooksByAuthor(template.findById(id, Author.class).toFuture().get());
    }

    @Override
    public Mono<Void> deleteAuthorWithBooksByAuthor(Author author) {
        template.remove(author).subscribe();
        return bookRepository.deleteByAuthor(author);
    }

    @SneakyThrows
    @Override
    public Mono<Author> updateAuthorWithBooksByAuthor(Author author) {
        List<Book> books = bookRepository.findByAuthorId(author.getId()).collectList().toFuture().get();
        books.forEach(book -> book.setAuthor(author));
        bookRepository.saveAll(books).subscribe();

        return template.save(author);
    }
}
