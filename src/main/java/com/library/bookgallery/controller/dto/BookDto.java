package com.library.bookgallery.controller.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.library.bookgallery.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = BookDtoDeserializer.class)
public class BookDto {

    private long id = -1;
    private String name;
    @JsonProperty("author")
    private AuthorDto authorDto;
    @JsonProperty("genres")
    private List<GenreDto> genresDto;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName()
                , AuthorDto.toDto(book.getAuthor())
                ,book.getGenres().stream().map(GenreDto::toDto).collect(Collectors.toList())
        );
    }
    public static Book toBook(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getName()
                , AuthorDto.toAuthor(bookDto.authorDto)
                , bookDto.genresDto.stream().map(GenreDto::toGenre).collect(Collectors.toList()));
    }


}
