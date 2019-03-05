package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Personne;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "personnes", path = "personnes")
public interface PersonneRessource extends MongoRepository<Personne,String> {

}