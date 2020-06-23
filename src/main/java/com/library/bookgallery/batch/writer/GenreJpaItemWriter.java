package com.library.bookgallery.batch.writer;

import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GenreJpaItemWriter extends JpaItemWriter<Genre> {

    private final GenreService genreService;

    @Override
    public void write(List<? extends Genre> items) {
        List<? extends Genre> trueItems = items.stream()
                .filter(genre -> genreService.findByName(genre.getName()) == null)
                .collect(Collectors.toList());
        super.write(trueItems);
    }
}
