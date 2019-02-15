package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Etat;
import fr.miage.matthieu.entity.Tache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TacheMessageProcessor {

    private final TacheRessource tr;

    @Autowired
    public TacheMessageProcessor(TacheRessource tr) {
        this.tr = tr;
    }

    @StreamListener("input")
    public void onMessage(Message<Tache> msg) {
        Tache t = new Tache(msg.getPayload());
        t.setEtat(Etat.CREEE);
        t.setParticipantsId(Collections.emptySet());
        this.tr.save(t);
    }
}
