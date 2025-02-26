    package JP.Rania.projet.bookmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collections")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    public List<Long> bookIds; // Liste des ID des livres appartenant à cette collection

    public double jaroWinklerDistance;
    public double jaccardDistance;
}
