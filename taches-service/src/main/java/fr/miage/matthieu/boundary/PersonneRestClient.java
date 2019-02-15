package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Personne;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("http://personnes-service")
public interface PersonneRestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/personnes/{id}")
    Personne get(@PathVariable("id") String id);
}
