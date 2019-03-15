package fr.miage.matthieu.boundary;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import fr.miage.matthieu.entity.Tache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/taches")
public class TacheGateway {

    private final TacheReader tacheReader;
    private final TacheWriter tacheWriter;

    @Autowired
    public TacheGateway(TacheReader tacheReader, TacheWriter tacheWriter) {
        this.tacheReader = tacheReader;
        this.tacheWriter = tacheWriter;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET, value = "/noms")
    public Resources<Resource<Tache>> noms() {
        Collection<Tache> content = this.tacheReader.read().getContent();
        List<Resource<Tache>> listeTaches = new ArrayList<>();
        content.forEach(t -> listeTaches.add(new Resource<>(t)));
        return new Resources<>(listeTaches);
    }

    public Resources<Resource<Tache>> fallback() {
        Tache t = new Tache();
        t.setNom("unavailable");
        List<Resource<Tache>> listeTaches = new ArrayList<>();
        listeTaches.add(new Resource<>(t));
        return new Resources<>(listeTaches);
    }

    @PostMapping
    public void write(@RequestBody Tache tache) {
        this.tacheWriter.write(tache);
    }

}