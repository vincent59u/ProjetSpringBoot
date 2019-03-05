package fr.miage.matthieu.boundary;

import fr.miage.matthieu.entity.Etat;
import fr.miage.matthieu.entity.Personne;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/taches", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Tache.class)
public class TacheRepresentation {

    @Autowired
    TacheProcessor tacheProcessor;

    @Autowired
    PersonneClient personneClient;

    private final TacheRessource tr;

    public TacheRepresentation(TacheRessource tr) {
        this.tr = tr;
    }

    // GET all
    @GetMapping
    public ResponseEntity<?> getAllTaches(@RequestParam(value="etat", required = false) String etat)
    {
        Iterable<Tache> allTaches;
        if(etat == null){
            allTaches = tr.findAll();
        }else{
            try {
                Etat e = Etat.valueOf(etat);
                allTaches = tr.findByEtat(e);
                if (((Collection<?>) allTaches).size() == 0) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } catch (IllegalArgumentException e){
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
        return new ResponseEntity<>(tacheToResource(allTaches), HttpStatus.OK);
    }

    // GET one
    @GetMapping(value = "/{tacheId}")
    public ResponseEntity<?> getTache(@PathVariable("tacheId") String id)
    {
        return Optional.ofNullable(tr.findById(id))
                .filter(Optional::isPresent)
                .map(i -> new ResponseEntity<>(tacheToResource(i.get(), false), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //GET responsable d'une tache
    @GetMapping(value = "{tacheId}/responsable")
    public ResponseEntity<?> getResponsableFromTache(@PathVariable("tacheId") String id)
    {
        Optional<Tache> tacheOptional = tr.findById(id);

        if (!tacheOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(personneClient.get(tacheOptional.get().getResponsable_id()), HttpStatus.OK);
    }

    // GET all participants d'une tache
    @GetMapping(value = "{tacheId}/participants")
    public ResponseEntity<?> getParticipantsFromTache(@PathVariable("tacheId") String id)
    {
        Optional<Tache> tacheOptional = tr.findById(id);

        if (!tacheOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Personne> participants = new ArrayList<>();
        tacheOptional.get().getParticipantsId().forEach(participantId -> participants.add(personneClient.get(participantId)));
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    // GET un participant d'une tache
    @GetMapping(value = "{tacheId}/participant/{participantId}")
    public ResponseEntity<?> getParticipantFromTache(@PathVariable("tacheId") String tacheId,
                                                     @PathVariable("participantId") String participantId)
    {
        Optional<Tache> tacheOptional = tr.findById(tacheId);
        Personne participant = personneClient.get(participantId);

        if (!tacheOptional.isPresent() || participant == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    // POST
    @PostMapping
    public ResponseEntity<?> addTache(@RequestBody @Valid Tache tache)
    {
        tache.setId(UUID.randomUUID().toString());

        Personne resp = personneClient.get(tache.getResponsable_id());
        if (resp.getNom().equals("indisponible")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AtomicBoolean valid = new AtomicBoolean(true);
        tache.getParticipantsId().forEach(participant_id -> {
            if (personneClient.get(participant_id).getNom().equals("indisponible")){
                valid.set(false);
            }
        });

        if (!valid.get()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (tache.getParticipantsId() == null || tache.getParticipantsId().size() == 0){
            tache.setEtat(Etat.CREEE);
        }else{
            tache.setEtat(Etat.EN_COURS);
        }

        Tache saved = tr.save(tache);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(linkTo(TacheRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }

    //POST participant to tache
    @PostMapping(value = "/{tacheId}/participant/{participantId}")
    public ResponseEntity<?> addParticipantToTache(@PathVariable("tacheId") String tache_id,
                                                   @PathVariable("participantId") String participant_id)
    {
        Optional<Tache> tacheOptional = tr.findById(tache_id);
        Personne participant = personneClient.get(participant_id);

        if (!tacheOptional.isPresent() || participant.getNom().equals("indisponible")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (tacheOptional.get().getEtat() != Etat.ACHEVEE) {
            if (tacheOptional.get().getParticipantsId().contains(participant_id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            tacheOptional.get().getParticipantsId().add(participant_id);

            if (tacheOptional.get().getParticipantsId().size() == 1){
                tacheOptional.get().setEtat(Etat.EN_COURS);
            }
            Tache saved = tr.save(tacheOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // PUT
    @PutMapping(value = "/{tacheId}")
    public ResponseEntity<?> updateTache(@RequestBody @Valid Tache tache,
                                            @PathVariable("tacheId") String tacheId)
    {
        Optional<Tache> body = Optional.ofNullable(tache);
        if (!body.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!tr.existsById(tacheId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (body.get().getEtat() != Etat.ACHEVEE) {
            Optional<Tache> origine = tr.findById(tacheId);
            tache.setId(tacheId);
            tache.setResponsable_id(origine.get().getResponsable_id());
            tache.setParticipantsId(origine.get().getParticipantsId());
            Tache result = tr.save(tache);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // DELETE
    @DeleteMapping(value = "/{tacheId}")
    public ResponseEntity<?> deleteTache(@PathVariable("tacheId") String id)
    {
        Optional<Tache> tache = tr.findById(id);
        if (tache.isPresent()) {
            tache.get().setEtat(Etat.ACHEVEE);
            tr.save(tache.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE
    @DeleteMapping(value = "/{tacheId}/participant/{participantId}")
    public ResponseEntity<?> deleteParticipantToTache(@PathVariable("tacheId") String id,
                                                      @PathVariable("participantId") String participant_id)
    {
        Optional<Tache> tacheOptional = tr.findById(id);
        Personne personne = personneClient.get(participant_id);

        if (!tacheOptional.isPresent() || personne == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (tacheOptional.get().getEtat() != Etat.ACHEVEE) {
            if (!tacheOptional.get().getParticipantsId().contains(participant_id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            tacheOptional.get().getParticipantsId().remove(participant_id);
            Tache saved = tr.save(tacheOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private Resources<Resource> tacheToResource(Iterable<Tache> taches)
    {
        List<Link> links = new ArrayList<>();

        links.add(linkTo(TacheRepresentation.class).withSelfRel());
        links.add(linkTo(methodOn(TacheRepresentation.class).getAllTaches("CREEE")).withRel("CREEE"));
        links.add(linkTo(methodOn(TacheRepresentation.class).getAllTaches("EN_COURS")).withRel("EN_COURS"));
        links.add(linkTo(methodOn(TacheRepresentation.class).getAllTaches("ACHEVEE")).withRel("ACHEVEE"));
        links.add(linkTo(methodOn(TacheRepresentation.class).getAllTaches("ARCHIVEE")).withRel("ARCHIVEE"));

        List<Resource<Tache>> tacheRessources = new ArrayList();
        taches.forEach(tache
                -> tacheRessources.add(tacheToResource(tache, false)));
        return new Resources(tacheRessources, links);
    }

    private Resource tacheToResource(Tache tache, Boolean collection)
    {
        Link selfLink = linkTo(TacheRepresentation.class)
                .slash(tache.getId())
                .withSelfRel();
        if (collection) {
            Link collectionLink = linkTo(methodOn(TacheRepresentation.class))
                    .withSelfRel();
            Resource<? extends Tache> test = new Resource<>(tache, selfLink, collectionLink);
            return tacheProcessor.process(test);
        } else {
            Resource<? extends Tache> test = new Resource<>(tache, selfLink);
            return tacheProcessor.process(test);
        }
    }
}
