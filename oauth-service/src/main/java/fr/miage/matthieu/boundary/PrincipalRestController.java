package fr.miage.matthieu.boundary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PrincipalRestController {

    @GetMapping(value = "/user")
    public Principal principal(Principal principal) {
        return principal;
    }
}
