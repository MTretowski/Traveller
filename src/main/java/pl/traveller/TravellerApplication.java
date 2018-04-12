package pl.traveller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TravellerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TravellerApplication.class, args);
    }
}
