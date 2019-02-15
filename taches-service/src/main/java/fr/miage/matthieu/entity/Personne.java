package fr.miage.matthieu.entity;

import java.util.Date;

public class Personne {

    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private Date date_naissance;
    private String commune;
    private Long codepostal;

    Personne()
    {
        // pour JPA ! OBLIGATOIRE !!!
    }

    public Personne(String nom)
    {
        this.nom = nom;
    }

    public Personne(String nom, String prenom, String email, String mdp, Date date_naissance, String commune, Long codepostal)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.date_naissance = date_naissance;
        this.commune = commune;
        this.codepostal = codepostal;
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
