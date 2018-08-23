package net.mfinance.security.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring boot app.
 *
 * @author mf
 */
@SpringBootApplication
public class Application {

    @Bean
    public JwtAuthenticationConfig jwtAuthenticationConfig() {
        return new JwtAuthenticationConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
