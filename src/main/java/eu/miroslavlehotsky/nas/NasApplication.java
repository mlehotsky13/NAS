package eu.miroslavlehotsky.nas;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NasApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(NasApplication.class, args);
    }
}