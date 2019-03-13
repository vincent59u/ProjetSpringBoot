package fr.miage.matthieu.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("configuration")
public class ApplicationPropertiesConfiguration {

    private String URL_OAUTH;
    private String CLIENT_ID;
    private String SECRET;
    private String RESOURCE_ID_OAUTH_SERVER;

    public String getURL_OAUTH() {
        return URL_OAUTH;
    }

    public void setURL_OAUTH(String URL_OAUTH) {
        this.URL_OAUTH = URL_OAUTH;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public String getSECRET() {
        return SECRET;
    }

    public void setSECRET(String SECRET) {
        this.SECRET = SECRET;
    }

    public String getRESOURCE_ID_OAUTH_SERVER() {
        return RESOURCE_ID_OAUTH_SERVER;
    }

    public void setRESOURCE_ID_OAUTH_SERVER(String RESOURCE_ID_OAUTH_SERVER) {
        this.RESOURCE_ID_OAUTH_SERVER = RESOURCE_ID_OAUTH_SERVER;
    }
}
