package JP.Rania.projet.bookmanager.service;

import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.model.Collection;
import JP.Rania.projet.bookmanager.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final DistanceService distanceService;
    private final BookService bookService;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository,DistanceService distanceService, BookService bookService) {
        this.collectionRepository = collectionRepository;
        this.distanceService = distanceService;
        this.bookService = bookService;
    }

    // créer collecion à partir list d'identifiants 
    public Collection createCollection(String name, List<Long> bookIds) {

        //nouvelle collection
        Collection collection = new Collection();
        collection.setName(name);
        collection.setBookIds(bookIds);

        //distances 
        distanceService.calculateDistancesCollection(collection);

        //sauvegarde de la collection
        return collectionRepository.save(collection);
    }

    //sauvegarder ue collection
    public Collection saveCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    //dupliquer collection en changeant le nom
    public Collection duplicateCollection(Long collectionId, String newName) {
        Optional<Collection> existingCollection = collectionRepository.findById(collectionId);
        if (existingCollection.isPresent()) {
            Collection newCollection = createCollection(newName, existingCollection.get().getBookIds());
            return collectionRepository.save(newCollection);
        }
        return null;
    }

    //l’affichage de toutes les collections
    public Iterable<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    //l’affichage d’une collection
    public Optional<Collection> getCollectionById(Long id) {
        return collectionRepository.findById(id);
    }

    //la suppression d’un livre d’une collection
    public boolean deleteCollection(Long id) {
        Optional<Collection> collection = getCollectionById(id);
        if (collection.isPresent()) {
            collectionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Ajouter un livre à une collection
    public boolean addBookFromCollection(Long collectionId, Long bookId) {
        Optional<Collection> collectionOpt = collectionRepository.findById(collectionId);
        if (collectionOpt.isPresent()) {
            Collection collection = collectionOpt.get();
            
            //supprimer l'id 
            collection.getBookIds().add(bookId);
            
            // recalculer les distances 
            distanceService.calculateDistancesCollection(collection); 
            
            //sauvegarder
            collectionRepository.save(collection);
            
            return true;
        }
        return false;
    }


    // supprimer un livre d'une collection
    public boolean removeBookFromCollection(Long collectionId, Long bookId) {
        Optional<Collection> collectionOpt = collectionRepository.findById(collectionId);
        if (collectionOpt.isPresent()) {
            Collection collection = collectionOpt.get();
            
            //supprimer l'id 
            collection.getBookIds().remove(bookId);
            
            // recalculer les distances 
            distanceService.calculateDistancesCollection(collection); 
            
            //sauvegarder
            collectionRepository.save(collection);
            
            return true;
        }
        return false;
    }
    

    //le tri des livres d’une collection selon : auteur, livre, année
    public Collection sortCollection(Long collectionId, String sortBy) {
        Optional<Collection> collectionOpt = collectionRepository.findById(collectionId);
        if (collectionOpt.isPresent()) {
            Collection collection = collectionOpt.get();
            List<Book> books = bookService.getBooksByIds(collection.getBookIds());

            Comparator<Book> comparator;
            switch (sortBy.toLowerCase()) {
                case "author":
                    comparator = Comparator.comparing(Book::getAuthor);
                    break;
                case "title":
                    comparator = Comparator.comparing(Book::getTitle);
                    break;
                case "year":
                    comparator = Comparator.comparingInt(Book::getYear);
                    break;
                default:
                    return null;
            }
            books.sort(comparator);
            List<Long> sortedBookIds = books.stream()
                .map(Book::getId) // Récupérer les IDs des livres triés
                .collect(Collectors.toList());
            collection.setBookIds(sortedBookIds);
            return collectionRepository.save(collection);
        }
        return null;
    }

    //comparer deux collections suivant : nombre de livres, distances
    public boolean compareCollections(Long collectionId1, Long collectionId2) {
        Optional<Collection> collectionOpt1 = collectionRepository.findById(collectionId1);
        Optional<Collection> collectionOpt2 = collectionRepository.findById(collectionId2);

        if (collectionOpt1.isPresent() && collectionOpt2.isPresent()) {
            Collection collection1 = collectionOpt1.get();
            Collection collection2 = collectionOpt2.get();

            //comparer le nombre de livres
            boolean sameNumberOfBooks = collection1.getBookIds().size() == collection2.getBookIds().size();

            //comparer les distances
            boolean sameDistances = collection1.getJaroWinklerDistance() == collection2.getJaroWinklerDistance() &&
                    collection1.getJaccardDistance() == collection2.getJaccardDistance();

            return sameNumberOfBooks && sameDistances;
        }
        return false;
    }

}
    

    





