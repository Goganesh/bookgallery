package com.library.bookgallery.controller.rest;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("RestController для работы с авторами")
@WebMvcTest(AuthorRestController.class)
class AuthorRestControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DisplayName("должен вернуть JSON со всеми авторами")
    public void shouldReturnJsonWithAllAuthors() throws Exception {
        Author expectedAuthor1 = new Author(1, "Alexandre Dumas");
        Author expectedAuthor2 = new Author(2, "Ernest Hemingway");

        Mockito.when(authorService.findAll()).thenReturn(Arrays.asList(expectedAuthor1, expectedAuthor2));

        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Alexandre Dumas")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Ernest Hemingway")));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )@Test
    @DisplayName("должен вернуть JSON c автором")
    public void shouldReturnJsonWithAuthorById() throws Exception {
        Author expectedAuthor1 = new Author(1, "Alexandre Dumas");

        Mockito.when(authorService.findById(1)).thenReturn(expectedAuthor1);

        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Alexandre Dumas")));
    }

}