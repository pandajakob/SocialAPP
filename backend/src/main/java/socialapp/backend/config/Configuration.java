package socialapp.backend.config;


import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;

import java.util.List;

@ConfigurationProperties(prefix = "security")
@Component
public class Configuration {

    @NotBlank
    private String adminEmail;

    @NotBlank
    private String adminPassword;

    @NotBlank
    private List<String> allowedOrigins;

    private String JWTName = "auth";

    @NotBlank
    private String googleMapsApiKey;

    private long tokenValiditySeconds = 3600;


    public long getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(long tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    public void setJWTName(String JWTName) {
        this.JWTName = JWTName;
    }

    public String getJWTName() {
        return JWTName;
    }

    public Email getAdminEmail() {
        return new Email(adminEmail);
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public void setGoogleMapsApiKey(String googleMapsApiKey) {
        this.googleMapsApiKey = googleMapsApiKey;
    }

    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }
}
