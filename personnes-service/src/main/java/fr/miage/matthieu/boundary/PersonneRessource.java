package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Personne;
import org.springframework.data.repository.CrudRepository;

public interface PersonneRessource extends CrudRepository<Personne,String> {

}