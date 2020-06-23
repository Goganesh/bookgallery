package com.library.bookgallery.batch.writer;

import com.library.bookgallery.domain.Book;
import com.library.bookgallery.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookJpaItemWriter extends JpaItemWriter<Book> {

    private final BookService bookService;

    @Override
    public void write(List<? extends Book> items) {
        List<? extends Book> trueItems = items.stream()
                .filter(book -> bookService.findByNameAndAuthor(book.getName(), book.getAuthor()) == null)
                .collect(Collectors.toList());
        super.write(trueItems);
    }
}
