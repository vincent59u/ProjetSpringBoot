package fr.miage.matthieu.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Personne implements Serializable {

    @Id
    private String id;

    @NotNull @Size(min = 2, max = 20)
    private String nom, prenom;

    @NotNull @Email
    private String email;

    @NotNull @Size(min = 8)
    private String mdp;

    @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @Past
    private Date date_naissance;

    private String commune;
    private Long codepostal;

    Personne()
    {
        // pour JPA ! OBLIGATOIRE !!!
    }

    public Personne(String nom, String prenom, String email, String mdp, Date date_naissance, String commune, Long codepostal)
    {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setMdp(mdp);
        this.setDate_naissance(date_naissance);
        this.setCommune(commune);
        this.setCodepostal(codepostal);
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

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
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
