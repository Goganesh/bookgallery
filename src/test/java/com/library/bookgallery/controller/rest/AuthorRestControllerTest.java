package com.library.bookgallery.controller.rest;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.repository.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import static org.hamcrest.Matchers.*;

@DisplayName("RestController для работы с авторами")
@WebFluxTest(AuthorRestController.class)
class AuthorRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("должен вернуть JSON со всеми автором")
    public void shouldReturnJsonWithAllAuthors() throws Exception {
        Author expectedAuthor1 = new Author("1", "Alexandre Dumas");
        Author expectedAuthor2 = new Author("2", "Ernest Hemingway");
        Flux<Author> authorFlux = Flux.fromIterable(Arrays.asList(expectedAuthor1, expectedAuthor2));

        Mockito.when(authorRepository.findAll()).thenReturn(authorFlux);

        this.webTestClient.get()
                .uri("/api/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Author.class)
                .contains(expectedAuthor1, expectedAuthor2);
    }

    @Test
    @DisplayName("должен вернуть JSON c автором")
    public void shouldReturnJsonWithAuthorById() throws Exception {
        Author expectedAuthor1 = new Author("1", "Alexandre Dumas");
        Mockito.when(authorRepository.findById("1")).thenReturn(Mono.just(expectedAuthor1));

        this.webTestClient.get()
                .uri("/api/authors/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Author.class)
                .value(author -> expectedAuthor1.getId(), equalTo("1"));
    }

}