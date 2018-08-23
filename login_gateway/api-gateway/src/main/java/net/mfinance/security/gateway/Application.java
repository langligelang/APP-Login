package net.mfinance.security.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Spring boot app.
 *
 * @author mf
 */
@SpringBootApplication
@EnableZuulProxy
public class Application {


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
