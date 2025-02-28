package JP.Rania.projet.bookmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Titre", "Auteur", 2024, "Genre", "Pays");
    }

    @Test
    void testSaveBook() {
        when(bookRepository.save(book)).thenReturn(book);
        Book savedBook = bookService.saveBook(book);
        assertNotNull(savedBook);
        assertEquals(book.getId(), savedBook.getId());
    }

    @Test
    void testUpdateBook_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Optional<Book> updatedBook = bookService.updateBook(1L, book);
        assertTrue(updatedBook.isPresent());
    }

    @Test
    void testUpdateBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> updatedBook = bookService.updateBook(1L, book);
        assertFalse(updatedBook.isPresent());
    }

    @Test
    void testDeleteBook_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.deleteBook(1L);
        assertTrue(result);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = bookService.deleteBook(1L);
        assertFalse(result);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getAllBooks();
        assertFalse(books.isEmpty());
    }

    @Test
    void testGetBookById_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);
        assertTrue(foundBook.isPresent());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> foundBook = bookService.getBookById(1L);
        assertFalse(foundBook.isPresent());
    }
}
