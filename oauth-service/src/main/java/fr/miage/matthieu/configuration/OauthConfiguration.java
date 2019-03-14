package fr.miage.matthieu.configuration;

import fr.miage.matthieu.boundary.CompteDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OauthConfiguration extends AuthorizationServerConfigurerAdapter {

    private String resourceId = "oauth";

    @Autowired
    @Qualifier(value="passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier(value="authServerAuthenticationManager")
    private AuthenticationManager authenticationManager;

    //@Autowired
    //private CustomUserDetailsService customUserDetailsService;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("matthieu");
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception{
        client.inMemory()
                .withClient("8c3a4ad0-7ad0-4acc-b462-38329eee5c14")
                .secret(passwordEncoder.encode("72dbb01f-2f80-4381-8232-85b43e49eb2a"))
                .authorizedGrantTypes("password", "client_credentials","authorization_code", "refresh_token")
                .scopes("read", "write")
                //.authorities("ADMIN")
                .resourceIds(resourceId)
                .refreshTokenValiditySeconds(5000)
                .accessTokenValiditySeconds(5000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter());
        //.userDetailsService(customUserDetailsService);
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }
}