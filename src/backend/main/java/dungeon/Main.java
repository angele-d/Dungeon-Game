package dungeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    /** 
     * Main method to start the Spring Boot application.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Le serveur est lancé sur http://localhost:8080");
    }
}