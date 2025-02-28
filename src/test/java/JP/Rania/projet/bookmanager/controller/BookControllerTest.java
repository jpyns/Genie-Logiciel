package JP.Rania.projet.bookmanager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Titre", "Auteur", 2024, "Genre", "Pays");
    }

    @Test
    void testCreateBook() throws Exception {
        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(book.getId()));
    }

    @Test
    void testGetBookById_Found() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()));
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBook_Found() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book 1 supprimé"));
    }

    @Test
    void testDeleteBook_NotFound() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(false);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateBook_Found() throws Exception {
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(Optional.of(book));

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()));
    }

    @Test
    void testUpdateBook_NotFound() throws Exception {
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound());
    }
}