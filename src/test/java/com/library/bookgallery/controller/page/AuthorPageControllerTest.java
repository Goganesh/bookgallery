package com.library.bookgallery.controller.page;

import com.library.bookgallery.controller.page.AuthorPageController;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("PageController для работы с авторами")
@WebMvcTest(AuthorPageController.class)
class AuthorPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("должен вернуть страницу с автором по id")
    public void shouldReturnAuthorPage() throws Exception {
        Mockito.when(authorService.findById(1)).thenReturn(new Author(1, "Alexandre Dumas"));

        this.mvc.perform(MockMvcRequestBuilders
                .get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("author"));
    }

    @Test
    @DisplayName("должен вернуть страницу со всеми авторами")
    public void shouldReturnPageWithAllAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1, "Alexandre Dumas"));
        authors.add(new Author(2, "Ernest Hemingway"));

        Mockito.when(authorService.findAll()).thenReturn(authors);

        this.mvc.perform(MockMvcRequestBuilders
                .get("/authors"))
                .andExpect(status().isOk())
                .andExpect(view().name("authors"));
    }

}