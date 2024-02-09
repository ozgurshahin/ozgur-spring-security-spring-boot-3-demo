package org.example.ozgurspringsecurityspringboot3demo.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    /**
     * Secret key for signing JWT
     */
    private String secretKey;
}
