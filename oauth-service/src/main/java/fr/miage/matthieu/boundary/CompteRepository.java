package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {

    Optional<Compte> findByUsername(String username);
}
