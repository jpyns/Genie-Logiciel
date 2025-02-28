package JP.Rania.projet.bookmanager.service;

import org.springframework.stereotype.Service;
import JP.Rania.projet.bookmanager.model.Collection;
import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.repository.CollectionRepository;
import java.util.ArrayList;
import java.util.List;


interface DistanceStrategy {
    double computeDistance(String s1, String s2);
}

// implémentations  de la stratégie
class JaroWinklerDistanceStrategy implements DistanceStrategy {
    @Override
    public double computeDistance(String s1, String s2) {
        return new org.apache.commons.text.similarity.JaroWinklerDistance().apply(s1, s2);
    }
}

class JaccardDistanceStrategy implements DistanceStrategy {
    @Override
    public double computeDistance(String s1, String s2) {
        return new org.apache.commons.text.similarity.JaccardSimilarity().apply(s1, s2);
    }
}

// classe de base pour le pattern composite
abstract class DistanceCalculator {
    protected DistanceStrategy strategy;
    
    public DistanceCalculator(DistanceStrategy strategy) {
        this.strategy = strategy;
    }
    
    public abstract double calculate(Book book1, Book book2);
}

// calculateur concret pour un champ spécifique 
class FieldDistanceCalculator extends DistanceCalculator {
    private final BookFieldExtractor fieldExtractor;
    
    public FieldDistanceCalculator(DistanceStrategy strategy, BookFieldExtractor fieldExtractor) {
        super(strategy);
        this.fieldExtractor = fieldExtractor;
    }
    
    @Override
    public double calculate(Book book1, Book book2) {
        String field1 = fieldExtractor.extract(book1);
        String field2 = fieldExtractor.extract(book2);
        return strategy.computeDistance(field1, field2);
    }
}


interface BookFieldExtractor {
    String extract(Book book);
}

// composite qui agrège plusieurs calculateurs
class CompositeDistanceCalculator extends DistanceCalculator {
    private final List<DistanceCalculator> calculators = new ArrayList<>();
    
    public CompositeDistanceCalculator(DistanceStrategy strategy) {
        super(strategy);
    }
    
    public void addCalculator(DistanceCalculator calculator) {
        calculators.add(calculator);
    }
    
    @Override
    public double calculate(Book book1, Book book2) {
        double totalDistance = 0.0;
        for (DistanceCalculator calculator : calculators) {
            totalDistance += calculator.calculate(book1, book2);
        }
        return totalDistance;
    }
}

@Service
public class DistanceService {
    private final BookService bookService;
    private final CollectionRepository collectionRepository;
    private final CompositeDistanceCalculator jaroWinklerCalculator;
    private final CompositeDistanceCalculator jaccardCalculator;

    public DistanceService(BookService bookService, CollectionRepository collectionRepository) {
        this.bookService = bookService;
        this.collectionRepository = collectionRepository;
        
        // stratégies
        DistanceStrategy jaroWinklerStrategy = new JaroWinklerDistanceStrategy();
        DistanceStrategy jaccardStrategy = new JaccardDistanceStrategy();
        
        // calculateurs composites
        this.jaroWinklerCalculator = new CompositeDistanceCalculator(jaroWinklerStrategy);
        this.jaccardCalculator = new CompositeDistanceCalculator(jaccardStrategy);
        
        // calculateurs pour les différents champs
        this.jaroWinklerCalculator.addCalculator(new FieldDistanceCalculator(
            jaroWinklerStrategy, book -> book.getTitle()));
        this.jaroWinklerCalculator.addCalculator(new FieldDistanceCalculator(
            jaroWinklerStrategy, book -> book.getAuthor()));
            
        this.jaccardCalculator.addCalculator(new FieldDistanceCalculator(
            jaccardStrategy, book -> book.getTitle()));
        this.jaccardCalculator.addCalculator(new FieldDistanceCalculator(
            jaccardStrategy, book -> book.getAuthor()));
    }
    
    // interface pour les calculs de distance
    public double calculateJaroWinkler(String string1, String string2) {
        return new JaroWinklerDistanceStrategy().computeDistance(string1, string2);
    }
    
    public double calculateJaccard(String string1, String string2) {
        return new JaccardDistanceStrategy().computeDistance(string1, string2);
    }

    public Collection calculateDistancesCollection(Collection collection) {
        int size = collection.getBookIds().size();
        double totalJaroWinkler = 0, totalJaccard = 0;
        int count = 0;
        
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Long bookId1 = collection.getBookIds().get(i);
                Long bookId2 = collection.getBookIds().get(j);
                Book book1 = bookService.getBookById(bookId1).orElse(null);
                Book book2 = bookService.getBookById(bookId2).orElse(null);
                
                if (book1 == null || book2 == null) {
                    continue;
                }
                
                // distances en utilisant les calculateurs composites
                double jaroWinkler = jaroWinklerCalculator.calculate(book1, book2);
                double jaccard = jaccardCalculator.calculate(book1, book2);
                
                // totaux
                totalJaroWinkler += jaroWinkler;
                totalJaccard += jaccard;
                count++;
            }
        }
        
        // moyenne des distances
        if (count > 0) {
            collection.setJaroWinklerDistance(totalJaroWinkler / count);
            collection.setJaccardDistance(totalJaccard / count);
        } else {
            collection.setJaroWinklerDistance(0);
            collection.setJaccardDistance(0);
        }
        
        return collectionRepository.save(collection);
    }
}