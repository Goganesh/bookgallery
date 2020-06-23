package com.library.bookgallery.batch.writer;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AuthorJpaIterWriter extends JpaItemWriter<Author> {

    private final AuthorService authorService;

    @Override
    public void write(List<? extends Author> items) {
        List<? extends Author> trueItems = items.stream()
                .filter(author -> authorService.findByName(author.getName()) == null)
                .collect(Collectors.toList());
        super.write(trueItems);
    }
}
