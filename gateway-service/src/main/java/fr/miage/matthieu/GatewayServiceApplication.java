package fr.miage.matthieu;

import fr.miage.matthieu.boundary.TacheChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.IntegrationComponentScan;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableBinding(TacheChannel.class)
@IntegrationComponentScan
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
