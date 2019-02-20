package fr.miage.matthieu.entity;

import org.springframework.hateoas.core.Relation;

import java.util.List;

@Relation(collectionRelation="taches")
public class Detail extends Tache {

    private final Personne responsable;
    private final List<Personne> participants;

    public Detail(Tache tache, Personne responsable, List<Personne> participants)
    {
        super(tache);
        this.responsable = responsable;
        this.participants = participants;
    }

    public List<Personne> getParticipants()
    {
        return participants;
    }

    public Personne getResponsable()
    {
        return responsable;
    }
}
