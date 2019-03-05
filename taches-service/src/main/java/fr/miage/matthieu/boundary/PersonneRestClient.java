package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Personne;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "personnes-service", path = "/personnes")
public interface PersonneRestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{personneId}")
    Personne get(@PathVariable("personneId") String id);
}
