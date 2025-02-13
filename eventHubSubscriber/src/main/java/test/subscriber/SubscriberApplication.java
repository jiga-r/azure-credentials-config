package test.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class SubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriberApplication.class);
    }
}
