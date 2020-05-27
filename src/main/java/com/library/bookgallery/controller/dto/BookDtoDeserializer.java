package com.library.bookgallery.controller.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.service.AuthorService;
import com.library.bookgallery.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookDtoDeserializer extends JsonDeserializer<BookDto> {

    private final GenreService genreService;
    private final AuthorService authorService;

    @Override
    public BookDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        long id = node.get("id").asLong();
        String name = node.get("name").asText();
        long authorId = node.get("author").asLong();

        String genresJson = node.findValue("genres").toString();
        Long genresId[] = objectMapper.readValue(genresJson, Long[].class);

        BookDto bookDto;
        AuthorDto authorDto = AuthorDto.toDto(authorService.findById(authorId));
        List<GenreDto> genresDto = genreService.findByIdIn(Arrays.asList(genresId))
                .stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
        return new BookDto(id, name, authorDto, genresDto);
    }
}
