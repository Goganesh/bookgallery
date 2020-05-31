package com.library.bookgallery.controller.page;

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
import java.util.ArrayList;
import java.util.List;

@DisplayName("PageController для работы с авторами")
@WebFluxTest(AuthorPageController.class)
class AuthorPageControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("должен вернуть страницу с автором по id")
    public void shouldReturnAuthorPage() throws Exception {
        Mockito.when(authorRepository.findById("1")).thenReturn(Mono.just(new Author("1", "Alexandre Dumas")));

        this.webTestClient.get()
                .uri("/authors/1")
                .accept(MediaType.TEXT_HTML)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("должен вернуть страницу со всеми авторами")
    public void shouldReturnPageWithAllAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("1", "Alexandre Dumas"));
        authors.add(new Author("2", "Ernest Hemingway"));
        Flux<Author> authorFlux = Flux.fromIterable(authors);

        Mockito.when(authorRepository.findAll()).thenReturn(authorFlux);

        this.webTestClient.get()
                .uri("/authors/1")
                .accept(MediaType.TEXT_HTML)
                .exchange()
                .expectStatus().isOk();
    }

}