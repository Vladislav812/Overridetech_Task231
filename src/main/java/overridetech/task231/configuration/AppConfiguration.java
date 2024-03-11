package overridetech.task231.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "secrettokens")
public class AppConfiguration {
    private String APIKey;
    private String secretKey;

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
