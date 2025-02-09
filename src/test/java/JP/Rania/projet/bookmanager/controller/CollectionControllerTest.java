package JP.Rania.projet.bookmanager.controller;

import JP.Rania.projet.bookmanager.model.Collection;
import JP.Rania.projet.bookmanager.repository.BookRepository;
import JP.Rania.projet.bookmanager.repository.CollectionRepository;
import JP.Rania.projet.bookmanager.service.DistanceService;
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

class CollectionControllerTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private CollectionController collectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetAllCollections() {
        // Données de test
        List<Collection> collections = new ArrayList<>();
        collections.add(new Collection(7L, "Romanesque (recueil de contes)", new ArrayList<>(), 0.0, 0.0));
        collections.add(new Collection(8L, "Romanesque (roman)", new ArrayList<>(), 0.0, 0.0));
        when(collectionRepository.findAll()).thenReturn(collections);

        // Appel de la méthode
        ResponseEntity<List<Collection>> response = collectionController.getAllCollections();

        // Vérifications
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(collections, response.getBody());
        verify(collectionRepository, times(1)).findAll();
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetCollectionById_Found() {
        // Données de test
        Collection collection = new Collection(8L, "Romanesque (roman)", new ArrayList<>(), 0.0, 0.0);
        when(collectionRepository.findById(8L)).thenReturn(Optional.of(collection));

        // Appel de la méthode
        ResponseEntity<Collection> response = collectionController.getCollectionById(8L);

        // Vérifications
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(collection, response.getBody());
        verify(collectionRepository, times(1)).findById(8L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetCollectionById_NotFound() {
        when(collectionRepository.findById(99L)).thenReturn(Optional.empty());

        // Appel de la méthode
        ResponseEntity<Collection> response = collectionController.getCollectionById(99L);

        // Vérifications
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(collectionRepository, times(1)).findById(99L);
    }

    @SuppressWarnings({ "deprecation", "null" })
    // @Test
    // void testAddBookToCollection_BookFound() {
    //     // Données de test
    //     Collection collection = new Collection(7L, "Romanesque (recueil de contes)", new ArrayList<>(), 0.0, 0.0);
    //     when(collectionRepository.findById(7L)).thenReturn(Optional.of(collection));
    //     when(bookRepository.existsById(1L)).thenReturn(true);

    //     // Appel de la méthode
    //     ResponseEntity<Collection> response = collectionController.addBookToCollection(7L, 1L);

    //     // Vérifications
    //     assertNotNull(response);
    //     assertEquals(200, response.getStatusCodeValue());
    //     assertTrue(response.getBody().getBookIds().contains(1L));
    //     verify(collectionRepository, times(1)).save(collection);
    // }





    @Test
    void testAddBookToCollection_BookFound() {
        // Données de test
        Collection collection = new Collection(7L, "Romanesque (recueil de contes)", new ArrayList<>(), 0.0, 0.0);
        Collection updatedCollection = new Collection(7L, "Romanesque (recueil de contes)", new ArrayList<>(List.of(1L)), 0.0, 0.0);
    
        when(collectionRepository.findById(7L)).thenReturn(Optional.of(collection));
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(collectionRepository.save(collection)).thenReturn(updatedCollection);
    
        // Appel de la méthode
        ResponseEntity<Collection> response = collectionController.addBookToCollection(7L, 1L);
    
        // Vérifications
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getBookIds().contains(1L));
        verify(collectionRepository, times(1)).save(collection);
    }
    








    @SuppressWarnings("deprecation")
    @Test
    void testAddBookToCollection_BookNotFound() {
        // Données de test
        Collection collection = new Collection(7L, "Romanesque (recueil de contes)", new ArrayList<>(), 0.0, 0.0);
        when(collectionRepository.findById(7L)).thenReturn(Optional.of(collection));
        when(bookRepository.existsById(99L)).thenReturn(false);

        // Appel de la méthode
        ResponseEntity<Collection> response = collectionController.addBookToCollection(7L, 99L);

        // Vérifications
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(collectionRepository, never()).save(collection);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testCompareCollectionsJaroWinkler() {
        // Données de test
        Collection collection1 = new Collection(7L, "Romanesque (recueil de contes)", new ArrayList<>(), 0.0, 0.0);
        Collection collection2 = new Collection(8L, "Romanesque (roman)", new ArrayList<>(), 0.0, 0.0);
        when(collectionRepository.findById(7L)).thenReturn(Optional.of(collection1));
        when(collectionRepository.findById(8L)).thenReturn(Optional.of(collection2));
        when(distanceService.calculateJaroWinkler(collection1.getName(), collection2.getName())).thenReturn(0.85);

        // Appel de la méthode
        ResponseEntity<Double> response = collectionController.compareCollectionsJaroWinkler(7L, 8L);

        // Vérifications
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0.85, response.getBody());
    }

    @SuppressWarnings("deprecation")
    @Test
    void testCompareCollectionsJaccard() {
        // Données de test
        Collection collection1 = new Collection(7L, "Romanesque (recueil de contes)", new ArrayList<>(), 0.0, 0.0);
        Collection collection2 = new Collection(8L, "Romanesque (roman)", new ArrayList<>(), 0.0, 0.0);
        when(collectionRepository.findById(7L)).thenReturn(Optional.of(collection1));
        when(collectionRepository.findById(8L)).thenReturn(Optional.of(collection2));
        when(distanceService.calculateJaccard(collection1.getName(), collection2.getName())).thenReturn(0.67);

        // Appel de la méthode
        ResponseEntity<Double> response = collectionController.compareCollectionsJaccard(7L, 8L);

        // Vérifications
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0.67, response.getBody());
    }
}
