package overridetech.task231.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dadata")
public class DadataProperties {
    private String apiToken;
    private String secretKey;

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
