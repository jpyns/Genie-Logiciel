package JP.Rania.projet.bookmanager.service;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.stereotype.Service;
import JP.Rania.projet.bookmanager.model.Collection;
import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.repository.CollectionRepository;

@Service
public class DistanceService {

    private final BookService bookService;
    private final CollectionRepository collectionRepository;
    private final JaroWinklerDistance jaroWinklerDistance;
    private final JaccardSimilarity jaccardSimilarity;

    public DistanceService(BookService bookService, CollectionRepository collectionRepository) {
        this.bookService = bookService;
        this.collectionRepository = collectionRepository;
        this.jaroWinklerDistance = new JaroWinklerDistance();
        this.jaccardSimilarity = new JaccardSimilarity();
    }
    // Méthode pour calculer la distance Jaro-Winkler entre deux chaînes
    public double calculateJaroWinkler(String string1, String string2) {
        return jaroWinklerDistance.apply(string1, string2);
    }

    // Méthode pour calculer la similarité Jaccard entre deux chaînes
    public double calculateJaccard(String string1, String string2) {
        return jaccardSimilarity.apply(string1, string2);
    }

    public Collection calculateDistancesCollection(Collection collection) {
        int size = collection.getBookIds().size();
        double totalJaroWinkler = 0, totalJaccard = 0;
        int count = 0;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Long bookId1 = collection.getBookIds().get(i);
                Long bookId2 = collection.getBookIds().get(j);

                Book book1 = bookService.getBookById(bookId1).orElse(null);;
                Book book2 = bookService.getBookById(bookId2).orElse(null);;
                
                if (book1 == null || book2 == null) {
                    continue;
                }

                // Calcul des distances Jaro-Winkler et Jaccard
                double jaroWinkler =  calculateJaroWinkler(book1.getTitle(), book2.getTitle()) + calculateJaroWinkler(book1.getAuthor(), book2.getAuthor());
                
                double jaccard = calculateJaccard(book1.getTitle(), book2.getTitle()) + calculateJaccard(book1.getAuthor(), book2.getAuthor());

                // Ajouter aux totaux
                totalJaroWinkler += jaroWinkler;
                totalJaccard += jaccard;
                count++;
            }
        }

        // Calculer la moyenne des distances
        if (count > 0) {
            collection.setJaroWinklerDistance(totalJaroWinkler / count);
            collection.setJaccardDistance(totalJaccard / count);
        } else {
            collection.setJaroWinklerDistance(0);
            collection.setJaccardDistance(0);;
        }

        return collectionRepository.save(collection);
    }



}
