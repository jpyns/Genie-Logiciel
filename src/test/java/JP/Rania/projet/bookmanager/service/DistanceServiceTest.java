package JP.Rania.projet.bookmanager.service;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.*;

class DistanceServiceTest {

    // private DistanceService distanceService;

    // @BeforeEach
    // void setUp() {
    //     distanceService = new DistanceService();
    // }

    // @Test
    // void testCalculateJaroWinkler() {
    //     // Cas 1 : Chaînes identiques → Distance maximale (1.0)
    //     assertEquals(1.0, distanceService.calculateJaroWinkler("hello", "hello"), 0.001);

    //     // Cas 2 : Chaînes complètement différentes → Distance faible
    //     double distance = distanceService.calculateJaroWinkler("hello", "world");
    //     assertTrue(distance >= 0.0 && distance <= 1.0);

    //     // Cas 3 : Chaînes partiellement similaires → Distance intermédiaire
    //     assertTrue(distanceService.calculateJaroWinkler("hello", "helo") > 0.8);
    // }

    // @Test
    // void testCalculateJaccard() {
    //     // Cas 1 : Chaînes identiques → Similarité maximale (1.0)
    //     assertEquals(1.0, distanceService.calculateJaccard("hello", "hello"), 0.001);

    //     // Cas 2 : Chaînes complètement différentes → Similarité faible
    //     double similarity = distanceService.calculateJaccard("hello", "world");
    //     assertTrue(similarity >= 0.0 && similarity <= 1.0);

    //     // Cas 3 : Chaînes partiellement similaires → Similarité intermédiaire
    //     assertTrue(distanceService.calculateJaccard("hello", "helo") > 0.2);
    // }

    // @Test
    // void testCalculateJaroWinkler_WithEmptyStrings() {
    //     // Comparaison de chaînes vides
    //     assertEquals(0.0, distanceService.calculateJaroWinkler("", ""), 0.001);
    //     assertEquals(0.0, distanceService.calculateJaroWinkler("hello", ""), 0.001);
    // }

    // @Test
    // void testCalculateJaccard_WithEmptyStrings() {
    //     // Comparaison de chaînes vides : Jaccard retourne 1.0 si les deux chaînes sont vides
    //     assertEquals(1.0, distanceService.calculateJaccard("", ""), 0.001);
    //     assertEquals(0.0, distanceService.calculateJaccard("hello", ""), 0.001);
    // }

    // @Test
    // void testCalculateJaroWinkler_NullInput() {
    //     // Attendu : IllegalArgumentException au lieu de NullPointerException
    //     assertThrows(IllegalArgumentException.class, () -> distanceService.calculateJaroWinkler(null, "test"));
    //     assertThrows(IllegalArgumentException.class, () -> distanceService.calculateJaroWinkler("test", null));
    // }

    // @Test
    // void testCalculateJaccard_NullInput() {
    //     // Attendu : IllegalArgumentException au lieu de NullPointerException
    //     assertThrows(IllegalArgumentException.class, () -> distanceService.calculateJaccard(null, "test"));
    //     assertThrows(IllegalArgumentException.class, () -> distanceService.calculateJaccard("test", null));
    // }
}
