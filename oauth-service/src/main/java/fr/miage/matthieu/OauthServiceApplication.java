package fr.miage.matthieu;

import fr.miage.matthieu.boundary.CompteRepository;
import fr.miage.matthieu.entity.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@SpringBootApplication
public class OauthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }
}

@Component
class CompteCLR implements CommandLineRunner {

    private final CompteRepository cpr;
    private final PasswordEncoder pe;

    @Autowired
    public CompteCLR(CompteRepository cpr, PasswordEncoder pe) {
        this.cpr = cpr;
        this.pe = pe;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream.of("matthieu," + pe.encode("sesame"))
                .map(x -> x.split(","))
                .forEach(tpl -> cpr.save(new Compte(tpl[0], tpl[1], true)));
    }
}