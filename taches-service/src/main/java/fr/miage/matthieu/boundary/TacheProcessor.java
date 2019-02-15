package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Personne;
import fr.miage.matthieu.entity.Detail;
import fr.miage.matthieu.entity.Tache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TacheProcessor implements ResourceProcessor<Resource<? extends Tache>> {

    @Autowired
    PersonneClient client;

    @Override
    public Resource<Detail> process(Resource<? extends Tache> resource) {
        Tache tache = resource.getContent();
        List<Personne> personnes = tache
                .getParticipantsId()
                .stream()
                .map(client::get)
                .collect(Collectors.toList());
        return new Resource<>(new Detail(tache, personnes), resource.getLinks());
    }
}
