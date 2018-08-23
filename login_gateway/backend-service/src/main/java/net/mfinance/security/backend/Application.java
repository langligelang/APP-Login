package net.mfinance.security.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring boot app.
 *
 * @author mf 2017/10/18
 */
@SpringBootApplication
public class Application {

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
