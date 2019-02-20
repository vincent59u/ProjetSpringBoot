package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Detail;
import fr.miage.matthieu.entity.Tache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/taches", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Tache.class)
public class TacheRepresentation {

    @Autowired
    TacheProcessor tacheProcessor;

    private final TacheRessource tr;

    public TacheRepresentation(TacheRessource tr) {
        this.tr = tr;
    }

    // GET all
    @GetMapping
    public ResponseEntity<?> getAllTaches() {
        Iterable<Tache> allTaches = tr.findAll();
        return new ResponseEntity<>(tacheToResource(allTaches), HttpStatus.OK);
    }

    // GET one
    @GetMapping(value = "/{tacheId}")
    public ResponseEntity<?> getTache(@PathVariable("tacheId") String id) {
        return Optional.ofNullable(tr.findById(id))
                .filter(Optional::isPresent)
                .map(i -> new ResponseEntity<>(tacheToResource(i.get(), true), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST
    @PostMapping
    public ResponseEntity<?> addTache(@RequestBody @Valid Tache tache) {
        tache.setId(UUID.randomUUID().toString());
        Tache saved = tr.save(tache);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(linkTo(TacheRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping(value = "/{tacheId}")
    public ResponseEntity<?> updateTache(@RequestBody @Valid Tache tache,
                                            @PathVariable("tacheId") String tacheId) {
        Optional<Tache> body = Optional.ofNullable(tache);
        if (!body.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!tr.existsById(tacheId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tache.setId(tacheId);
        Tache result = tr.save(tache);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping(value = "/{tacheId}")
    public ResponseEntity<?> deleteTache(@PathVariable("tacheId") String id) {
        Optional<Tache> tache = tr.findById(id);
        if (tache.isPresent()) {
            tr.delete(tache.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Resources<Resource> tacheToResource(Iterable<Tache> taches) {
        Link selfLink = linkTo(methodOn(TacheRepresentation.class).getAllTaches()).withSelfRel();
        List<Resource<Tache>> tacheRessources = new ArrayList();
        taches.forEach(tache
                -> tacheRessources.add(tacheToResource(tache, false)));
        return new Resources(tacheRessources, selfLink);
    }

    private Resource tacheToResource(Tache tache, Boolean collection) {
        Link selfLink = linkTo(TacheRepresentation.class)
                .slash(tache.getId())
                .withSelfRel();
        if (collection) {
            Link collectionLink = linkTo(methodOn(TacheRepresentation.class).getAllTaches())
                    .withSelfRel();
            Resource<? extends Tache> test = new Resource<>(tache, selfLink, collectionLink);
            return tacheProcessor.process(test);
        } else {
            Resource<? extends Tache> test = new Resource<>(tache, selfLink);
            return tacheProcessor.process(test);
        }
    }
}
