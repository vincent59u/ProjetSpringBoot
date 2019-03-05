package fr.miage.matthieu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fr.miage.matthieu.boundary.PersonneRessource;
import fr.miage.matthieu.entity.Personne;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonneServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonneRessource pr;

    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setupContext()
    {
        pr.deleteAll();
    }

    //Test pour un élément de la collection
    @Test
    public void getOneAPI() throws ParseException
    {
        Personne p1 = new Personne("VINCENT", "Matthieu", "matthieu.vincent@gmail.com", "motdepasse", SDF.parse("1995-03-31"), "Rupt-sur-Moselle", 88360L);
        p1.set_id(UUID.randomUUID().toString());
        pr.save(p1);

        ResponseEntity<String> response = restTemplate.getForEntity("/personnes/" + p1.get_id(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains(p1.get_id()));
        assertThat(response.getBody().contains("VINCENT"));
        assertThat(response.getBody().contains("Matthieu"));
    }

    //Test pour tous les éléments de la liste
    @Test
    public void getAllAPI() throws Exception
    {
        Personne p1 = new Personne("VINCENT", "Matthieu", "matthieu.vincent@gmail.com", "motdepasse", SDF.parse("1995-03-31"), "Rupt-sur-Moselle", 88360L);
        p1.set_id(UUID.randomUUID().toString());
        pr.save(p1);
        Personne p2 = new Personne("GASIOROWSKI", "Loic", "loic.gasiorowski@gmail.com", "motdepasse", SDF.parse("1995-07-31"), "Moyeuvre-grande", 57250L);
        p2.set_id(UUID.randomUUID().toString());
        pr.save(p2);

        ResponseEntity<String> response = restTemplate.getForEntity("/personnes", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains("VINCENT"));
        assertThat(response.getBody().contains("GASIOROWSKI"));
        List<String> liste = JsonPath.read(response.getBody(), "$..personnes..nom");
        assertThat(liste).asList().hasSize(2);
        assertThat(response.getBody().contains(p1.get_id()));
        assertThat(response.getBody().contains(p2.get_id()));
    }

    @Test
    public void notFoundAPI() throws Exception
    {
        ResponseEntity<String> response = restTemplate.getForEntity("/personnes/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void postAPI() throws Exception
    {
        Personne p1 = new Personne("VINCENT", "Matthieu", "matthieu.vincent@gmail.com", "motdepasse", SDF.parse("1995-03-31"), "Rupt-sur-Moselle", 88360L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(this.toJsonString(p1), headers);

        ResponseEntity<?> response = restTemplate.postForEntity("/personnes", entity, ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        response = restTemplate.getForEntity(location, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void putAPI() throws Exception
    {
        Personne p1 = new Personne("VINCENT", "Matthieu", "matthieu.vincent@gmail.com", "motdepasse", SDF.parse("1995-03-31"), "Rupt-sur-Moselle", 88360L);
        p1.set_id(UUID.randomUUID().toString());
        pr.save(p1);
        p1.setCommune("LAXOU");
        p1.setCodepostal(54520L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(this.toJsonString(p1), headers);
        restTemplate.put("/personnes/" + p1.get_id(), entity);

        ResponseEntity<String> response = restTemplate.getForEntity("/personnes/" + p1.get_id(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains(p1.getCommune()));
        String code = JsonPath.read(response.getBody(), "$.codepostal").toString();
        assertThat(code).isEqualTo(p1.getCodepostal().toString());
    }

    @Test
    public void deleteAPI() throws Exception
    {
        Personne p1 = new Personne("VINCENT", "Matthieu", "matthieu.vincent@gmail.com", "motdepasse", SDF.parse("1995-03-31"), "Rupt-sur-Moselle", 88360L);
        p1.set_id(UUID.randomUUID().toString());
        pr.save(p1);
        restTemplate.delete("/personnes/" + p1.get_id());

        ResponseEntity<?> response = restTemplate.getForEntity("/personnes/" + p1.get_id(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private String toJsonString(Object o) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(o);
    }
}
