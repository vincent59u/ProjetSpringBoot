package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Personne;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/personnes", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Personne.class)
public class PersonneRepresentation {

    private final PersonneRessource pr;

    public PersonneRepresentation(PersonneRessource pr) {
        this.pr = pr;
    }

    // GET all
    @GetMapping
    public ResponseEntity<?> getAllPersonnes() {
        Iterable<Personne> allPersonnes = pr.findAll();
        return new ResponseEntity<>(personneToResource(allPersonnes), HttpStatus.OK);
    }

    // GET one
    @GetMapping(value = "/{personneId}")
    public ResponseEntity<?> getPersonne(@PathVariable("personneId") String id) {
        return Optional.ofNullable(pr.findById(id))
                .filter(Optional::isPresent)
                .map(i -> new ResponseEntity<>(personneToResource(i.get(), true), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST
    @PostMapping
    public ResponseEntity<?> addPersonne(@RequestBody @Valid Personne personne) {
        personne.set_id(UUID.randomUUID().toString());
        Personne saved = pr.save(personne);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(linkTo(PersonneRepresentation.class).slash(saved.get_id()).toUri());
        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping(value = "/{personneId}")
    public ResponseEntity<?> updatePersonne(@RequestBody @Valid Personne personne,
                                               @PathVariable("personneId") String personneId) {
        Optional<Personne> body = Optional.ofNullable(personne);
        if (!body.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!pr.existsById(personneId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        personne.set_id(personneId);
        Personne result = pr.save(personne);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping(value = "/{personneId}")
    public ResponseEntity<?> deletePersonne(@PathVariable("personneId") String id) {
        Optional<Personne> personne = pr.findById(id);
        if (personne.isPresent()) {
            pr.delete(personne.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Resources<Resource<Personne>> personneToResource(Iterable<Personne> personnes) {
        Link selfLink = linkTo(methodOn(PersonneRepresentation.class).getAllPersonnes()).withSelfRel();
        List<Resource<Personne>> personneRessources = new ArrayList();
        personnes.forEach(personne
                -> personneRessources.add(personneToResource(personne, false)));
        return new Resources<>(personneRessources, selfLink);
    }

    private Resource<Personne> personneToResource(Personne personne, Boolean collection) {
        Link selfLink = linkTo(PersonneRepresentation.class)
                .slash(personne.get_id())
                .withSelfRel();
        if (collection) {
            Link collectionLink = linkTo(methodOn(PersonneRepresentation.class).getAllPersonnes())
                    .withSelfRel();
            return new Resource<>(personne, selfLink, collectionLink);
        } else {
            return new Resource<>(personne, selfLink);
        }
    }
}
