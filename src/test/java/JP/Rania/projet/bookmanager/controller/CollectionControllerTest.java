package JP.Rania.projet.bookmanager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import JP.Rania.projet.bookmanager.model.Collection;
import JP.Rania.projet.bookmanager.service.CollectionService;
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

@WebMvcTest(CollectionController.class)
class CollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollectionService collectionService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Collection collection;

    @BeforeEach
    void setUp() {
        collection = new Collection(1L, "CollectionTest", Arrays.asList(1L, 2L), 0, 0);
    }

    @Test
    void testCreateCollection() throws Exception {
        when(collectionService.createCollection(anyString(), anyList())).thenReturn(collection);

        mockMvc.perform(post("/collections")
                .param("name", "CollectionTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(1L, 2L))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(collection.getId()))
                .andExpect(jsonPath("$.name").value(collection.getName()));
    }

    @Test
    void testGetAllCollections() throws Exception {
        when(collectionService.getAllCollections()).thenReturn(Arrays.asList(collection));

        mockMvc.perform(get("/collections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(collection.getId()));
    }

    @Test
    void testGetCollectionById_Found() throws Exception {
        when(collectionService.getCollectionById(1L)).thenReturn(Optional.of(collection));

        mockMvc.perform(get("/collections/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(collection.getId()));
    }

    @Test
    void testGetCollectionById_NotFound() throws Exception {
        when(collectionService.getCollectionById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/collections/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCollection_Found() throws Exception {
        when(collectionService.deleteCollection(1L)).thenReturn(true);

        mockMvc.perform(delete("/collections/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Collection 1 supprimée"));
    }

    @Test
    void testDeleteCollection_NotFound() throws Exception {
        when(collectionService.deleteCollection(1L)).thenReturn(false);

        mockMvc.perform(delete("/collections/1"))
                .andExpect(status().isNotFound());
    }
}
