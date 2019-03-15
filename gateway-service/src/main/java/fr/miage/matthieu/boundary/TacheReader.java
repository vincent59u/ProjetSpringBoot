package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Tache;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("taches-service")
public interface TacheReader {

    @RequestMapping(method = RequestMethod.GET, value = "/taches")
    Resources<Tache> read();
}
