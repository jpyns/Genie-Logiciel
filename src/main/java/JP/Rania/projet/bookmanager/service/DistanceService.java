package JP.Rania.projet.bookmanager.service;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    private final JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();
    private final JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();

    // Méthode pour calculer la distance Jaro-Winkler entre deux chaînes
    public double calculateJaroWinkler(String string1, String string2) {
        return jaroWinklerDistance.apply(string1, string2);
    }

    // Méthode pour calculer la similarité Jaccard entre deux chaînes
    public double calculateJaccard(String string1, String string2) {
        return jaccardSimilarity.apply(string1, string2);
    }
}
