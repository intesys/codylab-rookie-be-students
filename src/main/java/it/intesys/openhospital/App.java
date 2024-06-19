package it.intesys.openhospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.Instant;

@SpringBootApplication
public class App {
    public static void main(String[] args){
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }
}
