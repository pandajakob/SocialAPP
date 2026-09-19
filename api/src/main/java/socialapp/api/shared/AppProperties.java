package socialapp.api.shared;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {

    @NotNull
    private boolean devMode;

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public boolean isDevMode() {
        return devMode;
    }
}
