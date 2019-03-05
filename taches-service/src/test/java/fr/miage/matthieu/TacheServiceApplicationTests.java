package fr.miage.matthieu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fr.miage.matthieu.boundary.TacheRessource;
import fr.miage.matthieu.entity.Etat;
import fr.miage.matthieu.entity.Tache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TacheServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TacheRessource tr;

    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setupContext()
    {
        tr.deleteAll();
    }

    /**
     * Test sur une tache qui n'a pas de participant
     * L'état doit donc etre 'CREEE'
     * @throws ParseException
     */
    @Test
    public void getOneAPISansParticipant() throws ParseException
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        tr.save(t1);

        ResponseEntity<String> response = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains("Tache1"));
        assertThat(response.getBody().contains("425e7701-02c6-4de3-9333-a2459eece1c8"));
        assertThat(response.getBody().contains("CREEE"));
    }

    /**
     * Test sur une tache qui possède au moins un participant
     * L'état doit donc etre 'EN_COURS'
     * @throws ParseException
     */
    @Test
    public void getOneAPIAvecParticipant() throws ParseException
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        Set<String> participant = new HashSet<>();
        participant.add("participant1");
        t1.setParticipantsId(participant);
        tr.save(t1);

        ResponseEntity<String> response = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains("Tache1"));
        assertThat(response.getBody().contains("425e7701-02c6-4de3-9333-a2459eece1c8"));
        assertThat(response.getBody().contains("EN_COURS"));
    }

    /**
     * Test le GET pour toutes les taches
     * @throws ParseException
     */
    @Test
    public void getAllAPI() throws ParseException
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        tr.save(t1);
        Tache t2 = new Tache("Tache2", "e60fe9dd-24c5-41b3-8379-ab7172c2ad16", new Date(), SDF.parse("2025-01-01"));
        t2.setId(UUID.randomUUID().toString());
        Set<String> participant = new HashSet<>();
        participant.add("de7f2052-4961-4b4f-938c-3cd12clz9f82");
        t2.setParticipantsId(participant);
        tr.save(t2);

        ResponseEntity<String> response = restTemplate.getForEntity("/taches", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains("Tache1"));
        assertThat(response.getBody().contains("Tache2"));
        assertThat(response.getBody().contains("CREEE"));
        assertThat(response.getBody().contains("EN_COURS"));
        List<String> listeNom = JsonPath.read(response.getBody(), "$._embedded.taches[*].nom");
        assertThat(listeNom).asList().hasSize(2);
        List<String> listeEtat = JsonPath.read(response.getBody(), "$._embedded.taches[*].etat");
        assertThat(listeEtat).asList().hasSize(2);
    }

    /**
     * Test que l'API return NOT_FOUND pour un élément qui n'existe pas
     */
    @Test
    public void notFoundAPI()
    {
        ResponseEntity<String> response = restTemplate.getForEntity("/taches/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Test du POST d'une tache avec un id responsable existant
     * @throws Exception
     */
    @Test
    public void postAPIAvecResponsable() throws Exception
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(this.toJsonString(t1), headers);

        ResponseEntity<?> response = restTemplate.postForEntity("/taches", entity, ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        response = restTemplate.getForEntity(location, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test du POST d'une tache avec un id responsable qui n'existe pas
     * @throws Exception
     */
    @Test
    public void postAPISansResponsable() throws Exception
    {
        Tache t1 = new Tache("Tache1", "id_qui_nexiste_pas", new Date(), SDF.parse("2025-01-01"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(this.toJsonString(t1), headers);

        ResponseEntity<?> response = restTemplate.postForEntity("/taches", entity, ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Test du POST d'un participant existant sur une tache
     * @throws Exception
     */
    @Test
    public void postAPIParticipantExistant() throws Exception
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        tr.save(t1);

        ResponseEntity<String> resp = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(resp.getBody().contains("CREEE"));
        ResponseEntity<?> response = restTemplate.postForEntity("/taches/" + t1.getId() + "/participant/de7f2052-4961-4b4f-938c-3cd12clz9f82", null, ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        resp = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(resp.getBody().contains("de7f2052-4961-4b4f-938c-3cd12clz9f82"));
        assertThat(resp.getBody().contains("EN_COURS"));
    }

    /**
     * Test du POST d'un participant non existant sur une tache
     * @throws Exception
     */
    @Test
    public void postAPIParticipantNonExistant() throws Exception
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        tr.save(t1);

        ResponseEntity<String> resp = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(resp.getBody().contains("CREEE"));
        ResponseEntity<?> response = restTemplate.postForEntity("/taches/" + t1.getId() + "/participant/id_participant_non_existant", null, ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(!resp.getBody().contains("id_participant_non_existant"));
    }

    /**
     * Test du POST d'un participant sur une tache qui est achevee
     * @throws Exception
     */
    @Test
    public void postAPIParticipantTacheAchevee() throws Exception
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        t1.setEtat(Etat.ACHEVEE);
        tr.save(t1);

        ResponseEntity<?> response = restTemplate.postForEntity("/taches/" + t1.getId() + "/participant/de7f2052-4961-4b4f-938c-3cd12clz9f82", null, ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    /**
     * Test du PUT sur une tache
     * @throws Exception
     */
    @Test
    public void putAPI() throws Exception
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        tr.save(t1);
        t1.setNom("Super tache");
        t1.setResponsable_id("1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(this.toJsonString(t1), headers);
        restTemplate.put("/taches/" + t1.getId(), entity);

        ResponseEntity<String> response = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains(t1.getNom()));
        String responsable_id = JsonPath.read(response.getBody(), "$.responsable_id");
        assertThat(responsable_id).isEqualTo(t1.getResponsable_id());
    }

    /**
     * Test du PUT pour une tache n'es plus modifiable si elle est achevée
     * @throws Exception
     */
    @Test
    public void putAPITacheAchevee() throws Exception
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        t1.setEtat(Etat.ACHEVEE);
        tr.save(t1);
        t1.setNom("Super tache");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(this.toJsonString(t1), headers);
        ResponseEntity<String> response = restTemplate.exchange("/taches/" + t1.getId(), HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    /**
     * Test du DELETE qui termine une tache
     * @throws ParseException
     */
    @Test
    public void deleteAPI() throws ParseException
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        tr.save(t1);
        restTemplate.delete("/taches/" + t1.getId());

        ResponseEntity<String> response = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains("Tache1"));
        assertThat(response.getBody().contains("ACHEVEE"));
    }

    /**
     * Test du DELETE pour un participant a une tache
     * @throws ParseException
     */
    @Test
    public void deleteAPIParticipant() throws ParseException
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        Set<String> participant = new HashSet<>();
        participant.add("de7f2052-4961-4b4f-938c-3cd12clz9f82");
        t1.setParticipantsId(participant);
        tr.save(t1);

        ResponseEntity<String> response = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String p = JsonPath.read(response.getBody(), "$.participants_id[0]");
        assertThat(p.equals("de7f2052-4961-4b4f-938c-3cd12clz9f82"));

        restTemplate.delete("/taches/" + t1.getId() + "/participant/de7f2052-4961-4b4f-938c-3cd12clz9f82");

        response = restTemplate.getForEntity("/taches/" + t1.getId(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<String> participants = JsonPath.read(response.getBody(), "$.participants_id");
        assertThat(participants.isEmpty());
    }

    /**
     * Test du DELETE pour un participant a une tache qui est achevee
     * @throws ParseException
     */
    @Test
    public void deleteAPIParticipantTacheAchevee() throws ParseException
    {
        Tache t1 = new Tache("Tache1", "425e7701-02c6-4de3-9333-a2459eece1c8", new Date(), SDF.parse("2025-01-01"));
        t1.setId(UUID.randomUUID().toString());
        Set<String> participant = new HashSet<>();
        participant.add("de7f2052-4961-4b4f-938c-3cd12clz9f82");
        t1.setParticipantsId(participant);
        t1.setEtat(Etat.ACHEVEE);
        tr.save(t1);

        ResponseEntity<String> response = restTemplate.exchange("/taches/" + t1.getId() + "/participant/de7f2052-4961-4b4f-938c-3cd12clz9f82", HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private String toJsonString(Object o) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(o);
    }
}
