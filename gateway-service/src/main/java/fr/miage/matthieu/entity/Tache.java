package fr.miage.matthieu.entity;

import org.springframework.hateoas.core.Relation;

import java.util.Date;
import java.util.Set;

@Relation(collectionRelation = "taches")
public class Tache {

    private String id;
    private String nom;
    private String responsable_id;
    private Set<String> participantsId;
    private Date date_echeance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getResponsable_id() {
        return responsable_id;
    }

    public void setResponsable_id(String responsable_id) {
        this.responsable_id = responsable_id;
    }

    public Set<String> getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(Set<String> participantsId) {
        this.participantsId = participantsId;
    }

    public Date getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(Date date_echeance) {
        this.date_echeance = date_echeance;
    }
}
