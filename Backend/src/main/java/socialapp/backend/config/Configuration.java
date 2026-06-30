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
        System.out.println("Allowed Origins: " + this.allowedOrigins);
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
}
