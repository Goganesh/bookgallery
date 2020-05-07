package com.library.bookgallery.controller.dto;

import com.library.bookgallery.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private long id = -1;
    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
    public static Author toAuthor(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getName());
    }
}
