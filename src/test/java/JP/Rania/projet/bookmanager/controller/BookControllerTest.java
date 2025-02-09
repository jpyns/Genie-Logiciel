package JP.Rania.projet.bookmanager.controller;

import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetAllBooks() {
        // Données de test mises à jour
        List<Book> books = new ArrayList<>();
        books.add(new Book(8L, "Hans Christian Andersen ", "Contes ", 1837, "Romanesque (recueil de contes) ", "Danemark "));
        books.add(new Book(9L, "Jane Austen ", "Orgueil et Préjugés ", 1813, "Romanesque (roman) ", "Royaume-Uni "));
        books.add(new Book(10L, "Honoré de Balzac ", "Le Père Goriot ", 1835, "Romanesque (roman) ", "France "));
        when(bookRepository.findAll()).thenReturn(books);

        // Appel de la méthode
        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        // Vérifications
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(books, response.getBody());
        verify(bookRepository, times(1)).findAll();
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetBookById_Found() {
        // Données de test mises à jour
        Book book = new Book(9L, "Jane Austen ", "Orgueil et Préjugés ", 1813, "Romanesque (roman) ", "Royaume-Uni ");
        when(bookRepository.findById(9L)).thenReturn(Optional.of(book));

        // Appel de la méthode
        ResponseEntity<Book> response = bookController.getBookById(9L);

        // Vérifications
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
        verify(bookRepository, times(1)).findById(9L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetBookById_NotFound() {
        // Configuration pour un livre non trouvé
        when(bookRepository.findById(11L)).thenReturn(Optional.empty());

        // Appel de la méthode
        ResponseEntity<Book> response = bookController.getBookById(11L);

        // Vérifications
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(bookRepository, times(1)).findById(11L);
    }
}


