package fr.miage.matthieu;

import fr.miage.matthieu.boundary.TacheChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableBinding(TacheChannel.class)
@EnableSwagger2
public class TacheServiceApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(TacheServiceApplication.class, args);
    }

    @Bean
    public Docket swaggerIntervenantAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("fr.miage.matthieu"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Taches-service",
                "Documentation de l'API taches-service",
                "V1.0",
                "",
                new Contact("Matthieu VINCENT", "https://github.com/vincent59u", "contact.matthieu.vincent@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
