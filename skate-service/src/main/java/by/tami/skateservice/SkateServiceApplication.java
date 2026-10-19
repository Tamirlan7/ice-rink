package by.tami.skateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SkateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkateServiceApplication.class, args);
    }

}
