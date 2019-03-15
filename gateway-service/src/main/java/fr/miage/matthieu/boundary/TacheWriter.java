package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Tache;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface TacheWriter {

    @Gateway(requestChannel = "output")
    void write(Tache tache);
}
