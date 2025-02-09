package JP.Rania.projet.bookmanager.repository;


import JP.Rania.projet.bookmanager.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
}

