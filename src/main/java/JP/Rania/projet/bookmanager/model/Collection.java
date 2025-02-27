package JP.Rania.projet.bookmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotEmpty(message = "nom de collection manquant")
    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "collection_books", joinColumns = @JoinColumn(name = "collection_id"))
    @Column(name = "book_id")
    private List<Long> bookIds;

    @Column(name = "jaro_winkler_distance")
    private double jaroWinklerDistance;

    @Column(name = "jaccard_distance")
    private double jaccardDistance;

    public Collection() {
    }

    public Collection(Long id, String name, List<Long> bookIds, double jaroWinklerDistance, double jaccardDistance) {
        this.id = id;
        this.name = name;
        this.bookIds = bookIds;
        this.jaroWinklerDistance = jaroWinklerDistance;
        this.jaccardDistance = jaccardDistance;
    }

    public Collection(String name, List<Long> bookIds, double jaroWinklerDistance, double jaccardDistance) {
        this(null, name, bookIds, jaroWinklerDistance, jaccardDistance);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }

    public double getJaroWinklerDistance() {
        return jaroWinklerDistance;
    }

    public void setJaroWinklerDistance(double jaroWinklerDistance) {
        this.jaroWinklerDistance = jaroWinklerDistance;
    }

    public double getJaccardDistance() {
        return jaccardDistance;
    }

    public void setJaccardDistance(double jaccardDistance) {
        this.jaccardDistance = jaccardDistance;
    }
}
