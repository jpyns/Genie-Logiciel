


















// package JP.Rania.projet.bookmanager.controller;

// import JP.Rania.projet.bookmanager.model.Collection;
// import JP.Rania.projet.bookmanager.repository.BookRepository;
// import JP.Rania.projet.bookmanager.repository.CollectionRepository;
// import JP.Rania.projet.bookmanager.service.DistanceService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/collections")
// public class CollectionController {

//     private final CollectionRepository collectionRepository;
//     private final BookRepository bookRepository;
//     private final DistanceService distanceService;

//     public CollectionController(CollectionRepository collectionRepository, BookRepository bookRepository, DistanceService distanceService) {
//         this.collectionRepository = collectionRepository;
//         this.bookRepository = bookRepository;
//         this.distanceService = distanceService;
//     }

//     // 📌 POST /collections → Créer une collection
//     @PostMapping
//     public ResponseEntity<Collection> createCollection(@RequestBody Collection collection) {
//         return ResponseEntity.ok(collectionRepository.save(collection));
//     }

//     // 📌 GET /collections → Lister toutes les collections
//     @GetMapping
//     public ResponseEntity<List<Collection>> getAllCollections() {
//         return ResponseEntity.ok(collectionRepository.findAll());
//     }

//     // 📌 GET /collections/{id} → Afficher une collection
//     @GetMapping("/{id}")
//     public ResponseEntity<Collection> getCollectionById(@PathVariable Long id) {
//         Optional<Collection> collection = collectionRepository.findById(id);
//         return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // 📌 PUT /collections/{id} → Modifier une collection
//     @PutMapping("/{id}")
//     public ResponseEntity<Collection> updateCollection(@PathVariable Long id, @RequestBody Collection collectionDetails) {
//         return collectionRepository.findById(id).map(collection -> {
//             collection.setName(collectionDetails.getName());
//             collection.setBookIds(collectionDetails.getBookIds());
//             return ResponseEntity.ok(collectionRepository.save(collection));
//         }).orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // 📌 DELETE /collections/{id} → Supprimer une collection
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
//         Optional<Collection> collection = collectionRepository.findById(id);
//         if (collection.isPresent()) {
//             collectionRepository.delete(collection.get());
//             return ResponseEntity.noContent().build();
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     // 📌 POST /collections/{id}/books/{bookId} → Ajouter un livre à une collection
//     @PostMapping("/{id}/books/{bookId}")
//     public ResponseEntity<Collection> addBookToCollection(@PathVariable Long id, @PathVariable Long bookId) {
//         Optional<Collection> optionalCollection = collectionRepository.findById(id);
//         if (optionalCollection.isPresent()) {
//             Collection collection = optionalCollection.get();
//             if (bookRepository.existsById(bookId)) {
//                 collection.getBookIds().add(bookId); // Ajouter l'ID du livre
//                 return ResponseEntity.ok(collectionRepository.save(collection)); // Sauvegarder
//             } else {
//                 return ResponseEntity.notFound().build(); // Livre introuvable
//             }
//         } else {
//             return ResponseEntity.notFound().build(); // Collection introuvable
//         }
//     }

//     // 📌 DELETE /collections/{id}/books/{bookId} → Supprimer un livre d’une collection
//     @DeleteMapping("/{id}/books/{bookId}")
//     public ResponseEntity<Collection> removeBookFromCollection(@PathVariable Long id, @PathVariable Long bookId) {
//         Optional<Collection> optionalCollection = collectionRepository.findById(id);
//         if (optionalCollection.isPresent()) {
//             Collection collection = optionalCollection.get();
//             collection.getBookIds().remove(bookId); // Supprimer l'ID du livre
//             return ResponseEntity.ok(collectionRepository.save(collection)); // Sauvegarder
//         } else {
//             return ResponseEntity.notFound().build(); // Collection introuvable
//         }
//     }

//     // 📌 GET /collections/{id1}/compare/{id2}/jaro-winkler → Comparer deux collections avec Jaro-Winkler
//     @GetMapping("/{id1}/compare/{id2}/jaro-winkler")
//     public ResponseEntity<Double> compareCollectionsJaroWinkler(@PathVariable Long id1, @PathVariable Long id2) {
//         Optional<Collection> collection1 = collectionRepository.findById(id1);
//         Optional<Collection> collection2 = collectionRepository.findById(id2);

//         if (collection1.isPresent() && collection2.isPresent()) {
//             double distance = distanceService.calculateJaroWinkler(
//                 collection1.get().getName(),
//                 collection2.get().getName()
//             );
//             return ResponseEntity.ok(distance);
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     // 📌 GET /collections/{id1}/compare/{id2}/jaccard → Comparer deux collections avec Jaccard
//     @GetMapping("/{id1}/compare/{id2}/jaccard")
//     public ResponseEntity<Double> compareCollectionsJaccard(@PathVariable Long id1, @PathVariable Long id2) {
//         Optional<Collection> collection1 = collectionRepository.findById(id1);
//         Optional<Collection> collection2 = collectionRepository.findById(id2);

//         if (collection1.isPresent() && collection2.isPresent()) {
//             double similarity = distanceService.calculateJaccard(
//                 collection1.get().getName(),
//                 collection2.get().getName()
//             );
//             return ResponseEntity.ok(similarity);
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }
