package com.library.bookgallery.controller.dto;

import com.library.bookgallery.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private long id = -1;
    private String name;

    @Override
    public String toString() { return name; }
    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
    public static Genre toGenre(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }
}
