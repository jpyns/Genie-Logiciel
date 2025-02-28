package JP.Rania.projet.bookmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.model.Collection;
import JP.Rania.projet.bookmanager.repository.CollectionRepository;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DistanceServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private CollectionRepository collectionRepository;

    @InjectMocks
    private DistanceService distanceService;

    private Collection collection;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book(1L, "Book1", "Author1", 2024, "Genre1", "Country1");
        book2 = new Book(2L, "Book2", "Author2", 2023, "Genre2", "Country2");
        collection = new Collection(1L, "CollectionTest", Arrays.asList(1L, 2L), 0, 0);
    }

    @Test
    void testCalculateJaroWinkler() {
        double similarity = distanceService.calculateJaroWinkler("hello", "helo");
        assertTrue(similarity > 0 && similarity <= 1);
    }

    @Test
    void testCalculateJaccard() {
        double similarity = distanceService.calculateJaccard("hello", "helo");
        assertTrue(similarity >= 0 && similarity <= 1);
    }

    @Test
    void testCalculateDistancesCollection() {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book1));
        when(bookService.getBookById(2L)).thenReturn(Optional.of(book2));
        when(collectionRepository.save(any(Collection.class))).thenReturn(collection);

        Collection updatedCollection = distanceService.calculateDistancesCollection(collection);

        assertNotNull(updatedCollection);
        assertTrue(updatedCollection.getJaroWinklerDistance() >= 0);
        assertTrue(updatedCollection.getJaccardDistance() >= 0);
    }
}

