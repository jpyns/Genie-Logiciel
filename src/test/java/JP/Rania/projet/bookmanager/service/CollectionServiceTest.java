package JP.Rania.projet.bookmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import JP.Rania.projet.bookmanager.model.Collection;
import JP.Rania.projet.bookmanager.repository.CollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private DistanceService distanceService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private CollectionService collectionService;

    private Collection collection;

    @BeforeEach
    void setUp() {
        collection = new Collection(1L, "CollectionTest", Arrays.asList(1L, 2L), 0, 0);
    }

    @Test
    void testCreateCollection() {
        when(collectionRepository.save(any(Collection.class))).thenReturn(collection);

        Collection createdCollection = collectionService.createCollection("CollectionTest", Arrays.asList(1L, 2L));
        assertNotNull(createdCollection);
        assertEquals("CollectionTest", createdCollection.getName());
    }

    @Test
    void testGetAllCollections() {
        when(collectionRepository.findAll()).thenReturn(Arrays.asList(collection));

        Iterable<Collection> collections = collectionService.getAllCollections();
        assertNotNull(collections);
    }

    @Test
    void testGetCollectionById_Found() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));

        Optional<Collection> foundCollection = collectionService.getCollectionById(1L);
        assertTrue(foundCollection.isPresent());
    }

    @Test
    void testGetCollectionById_NotFound() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Collection> foundCollection = collectionService.getCollectionById(1L);
        assertFalse(foundCollection.isPresent());
    }

    @Test
    void testDeleteCollection_Found() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));

        boolean result = collectionService.deleteCollection(1L);
        assertTrue(result);
    }

    @Test
    void testDeleteCollection_NotFound() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = collectionService.deleteCollection(1L);
        assertFalse(result);
    }
}
