package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Personne;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface PersonneRessource extends MongoRepository<Personne,String> {

}