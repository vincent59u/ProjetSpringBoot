package fr.miage.matthieu.entity;

import org.springframework.hateoas.core.Relation;

import java.util.List;

@Relation(collectionRelation="tache")
public class Detail extends Tache {

    private final List<Personne> participants;

    public Detail(Tache tache, List<Personne> participants) {
        super(tache);
        this.participants = participants;
    }

    public List<Personne> getTaches() {
        return participants;
    }
}
