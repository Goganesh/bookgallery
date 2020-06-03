package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CustomAuthorRepositoryImpl implements CustomAuthorRepository {

    private final ReactiveMongoTemplate template;
    private final BookRepository bookRepository;

    @SneakyThrows
    @Override
    public Mono<Void> deleteAuthorWithBooksById(String id) {
        return deleteAuthorWithBooksByAuthor(template.findById(id, Author.class));
    }

    @Override
    public Mono<Void> deleteAuthorWithBooksByAuthor(Mono<Author> author) {
        return template.remove(author).then(bookRepository.deleteByAuthor(author));
    }

    @SneakyThrows
    @Override
    public Mono<Author> updateAuthorWithBooksByAuthor(Author author) {
        Flux<Book> books = bookRepository.findByAuthorId(author.getId()).map(book ->
                new Book(book.getId(), book.getName(), author, book.getGenres())
        );

        return bookRepository.saveAll(books).then(template.save(author));
    }
}
