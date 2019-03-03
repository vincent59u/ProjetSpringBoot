package fr.miage.matthieu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.miage.matthieu.constraint.ConsistentDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
//@ConsistentDate
public class Tache {

    @Id
    private String id;

    @NotNull @Size(min=2, max=30)
    private String nom;

    @NotNull
    private String responsable_id;

    @ElementCollection @JsonProperty("participants_id")
    private Set<String> participantsId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date_debut;

    @DateTimeFormat(pattern="yyyy-MM-dd") @Future
    private Date date_echeance;

    private Etat etat;

    Tache()
    {
        // pour JPA ! OBLIGATOIRE !!!
    }

    public Tache(String nom, String responsable_id, Date date_debut, Date date_echeance)
    {
        this.setNom(nom);
        this.setResponsable_id(responsable_id);
        this.setDate_debut(date_debut);
        this.setDate_echeance(date_echeance);
        this.setEtat(etat);
    }

    public Tache(Tache tache)
    {
        this.setId(tache.getId());
        this.setNom(tache.getNom());
        this.setResponsable_id(tache.getResponsable_id());
        this.setParticipantsId(tache.getParticipantsId());
        this.setDate_debut(tache.getDate_debut());
        this.setDate_echeance(tache.getDate_echeance());
        this.setEtat(tache.getEtat());
    }

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

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(Date date_echeance) {
        this.date_echeance = date_echeance;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }
}
