package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Etat;
import fr.miage.matthieu.entity.Tache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="taches", path = "taches")
public interface TacheRessource extends CrudRepository<Tache,String> {

    Iterable<Tache> findByEtat(Etat etat);
}
