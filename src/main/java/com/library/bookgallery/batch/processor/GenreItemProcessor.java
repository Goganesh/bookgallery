package com.library.bookgallery.batch.processor;

import com.library.bookgallery.batch.model.GenreMongo;
import com.library.bookgallery.domain.Genre;
import org.springframework.batch.item.ItemProcessor;

public class GenreItemProcessor implements ItemProcessor<GenreMongo, Genre> {

    @Override
    public Genre process(GenreMongo genreMongo) throws Exception {
        String name = genreMongo.getName();
        return new Genre(name);
    }
}
