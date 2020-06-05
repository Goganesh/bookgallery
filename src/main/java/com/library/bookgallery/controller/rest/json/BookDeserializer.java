package com.library.bookgallery.controller.rest.json;

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
import com.library.bookgallery.repository.AuthorRepository;
import com.library.bookgallery.repository.GenreRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookDeserializer extends JsonDeserializer<Book> {

    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @SneakyThrows
    @Override
    public Book deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        String id = node.get("id").asText();
        String name = node.get("name").asText();
        String authorId = node.get("author").asText();

        String genresJson = node.findValue("genres").toString();
        String genresId[] = objectMapper.readValue(genresJson, String[].class);

        Author author = authorRepository.findById(authorId).toFuture().get();
        List<Genre> genres = genreRepository.findByIdIn(Arrays.asList(genresId)).collectList().toFuture().get();

        return new Book(id, name, author, genres);
    }
}
