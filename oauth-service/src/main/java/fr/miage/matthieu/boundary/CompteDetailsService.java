package fr.miage.matthieu.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompteDetailsService implements UserDetailsService {

    private final CompteRepository cpr;

    @Autowired
    public CompteDetailsService(CompteRepository cpr) {
        this.cpr = cpr;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.cpr.findByUsername(username)
                .map(account -> new User(
                        account.getUsername(),
                        account.getMdp(),
                        account.isActive(),
                        account.isActive(),
                        account.isActive(),
                        account.isActive(),
                        AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN")
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Pb!"));
    }
}
