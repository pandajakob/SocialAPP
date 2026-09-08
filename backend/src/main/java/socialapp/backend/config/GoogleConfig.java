package socialapp.backend.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "google")
@Component
public class GoogleConfig {
    @NotBlank
    private String googleMapsApiKey;

    private String googleMapsApiBaseUrl = "https://geocode.googleapis.com";

    public String getGoogleMapsApiBaseUrl() {
        return googleMapsApiBaseUrl;
    }

    public void setGoogleMapsApiBaseUrl(String googleMapsApiBaseUrl) {
        this.googleMapsApiBaseUrl = googleMapsApiBaseUrl;
    }

    public void setGoogleMapsApiKey(String googleMapsApiKey) {
        this.googleMapsApiKey = googleMapsApiKey;
    }

    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }
}
