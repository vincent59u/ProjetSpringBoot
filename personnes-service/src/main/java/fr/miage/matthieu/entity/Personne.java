package fr.miage.matthieu.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Personne implements Serializable {

    @Id
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String commune;
    private Long codepostal;

    Personne()
    {
        // pour JPA ! OBLIGATOIRE !!!
    }

    public Personne(String nom, String prenom, String email, String mdp, String commune, Long codepostal)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.commune = commune;
        this.codepostal = codepostal;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public Long getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(Long codepostal) {
        this.codepostal = codepostal;
    }
}
