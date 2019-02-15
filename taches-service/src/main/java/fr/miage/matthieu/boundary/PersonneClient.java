package fr.miage.matthieu.boundary;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import fr.miage.matthieu.entity.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonneClient {

    @Autowired
    PersonneRestClient restClient;

    @HystrixCommand(fallbackMethod = "fallback")
    public Personne get(String id) {
        return restClient.get(id);
    }

    public Personne fallback(String id) {
        return new Personne("indisponible");
    }
}
