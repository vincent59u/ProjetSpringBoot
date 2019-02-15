package fr.miage.matthieu;

import fr.miage.matthieu.boundary.TacheChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableBinding(TacheChannel.class)
public class TacheServiceApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(TacheServiceApplication.class, args);
    }
}
