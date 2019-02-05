package fr.miage.matthieu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PersonneServiceApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(PersonneServiceApplication.class, args);
    }
}
