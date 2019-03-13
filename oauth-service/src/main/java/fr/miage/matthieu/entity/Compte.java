package fr.miage.matthieu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Compte {

    @Id
    @GeneratedValue
    private Long id;
    private String username, mdp;
    private boolean active;

    Compte()
    {
        // Obligatoire pour JPA
    }

    public Compte(String username, String mdp, boolean active)
    {
        this.setUsername(username);
        this.setMdp(mdp);
        this.setActive(active);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
