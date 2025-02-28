package JP.Rania.projet.bookmanager.controller;

import JP.Rania.projet.bookmanager.model.Collection;
import JP.Rania.projet.bookmanager.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping
    public Collection createCollection(@RequestParam String name, @RequestBody List<Long> bookIds) {
        Collection newCollection = collectionService.createCollection(name, bookIds);
        if (newCollection == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "collection non créée");
        }
        return newCollection;
    }

    @GetMapping
    public Iterable<Collection> getAllCollections() {
        return collectionService.getAllCollections();
    }

    @GetMapping("/{id}")
    public Collection getCollectionById(@PathVariable Long id) {
        return collectionService.getCollectionById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "collection non trouvée"));
    }

    @DeleteMapping("/{id}")
    public String deleteCollection(@PathVariable Long id) {
        boolean deleted = collectionService.deleteCollection(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "collection non trouvée");
        }
        return "Collection " + id + " supprimée";
    }

    @PostMapping("/{id}/duplicate")
    public Collection duplicateCollection(@PathVariable Long id, @RequestParam String newName) {
        Collection duplicatedCollection = collectionService.duplicateCollection(id, newName);
        if (duplicatedCollection == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "collectioin à dupliquer non trouvée");
        }
        return duplicatedCollection;
    }

    @PostMapping("/{collectionId}/books/{bookId}")
    public String addBookToCollection(@PathVariable Long collectionId, @PathVariable Long bookId) {
        boolean added = collectionService.addBookToCollection(collectionId, bookId);
        if (!added) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection ou livre non trouvé");
        }
        return "livrre ajouté à la collection";
    }

    @DeleteMapping("/{collectionId}/books/{bookId}")
    public String removeBookFromCollection(@PathVariable Long collectionId, @PathVariable Long bookId) {
        boolean removed = collectionService.removeBookFromCollection(collectionId, bookId);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livre ou collection non trouvé");
        }
        return "livre supprimé de la collection";
    }

    @PutMapping("/{id}/sort")
    public Collection sortCollection(@PathVariable Long id, @RequestParam String sortBy) {
        Collection sortedCollection = collectionService.sortCollection(id, sortBy);
        if (sortedCollection == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection à trier non trouvée");
        }
        return sortedCollection;
    }

    @GetMapping("/compare")
    public Boolean compareCollections(@RequestParam Long collectionId1, @RequestParam Long collectionId2) {
        return collectionService.compareCollections(collectionId1, collectionId2);
    }
}



