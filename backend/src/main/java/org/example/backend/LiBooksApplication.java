package org.example.backend;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiBooksApplication {

    /**
     * Main method to run the Spring Boot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(LiBooksApplication.class, args);

        Runtime.getRuntime().addShutdownHook(
            new Thread(() -> handleShutdown()));
    }

    /**
     * Handles the shutdown process by loading recommendations.
     */
    private static void handleShutdown() {
        
        String errorMessage = "Error: Failed to load recommendations.";

        try {
            System.out.println("Recommendations loading...");
            Process process = Runtime.getRuntime().exec(
                "python " + Paths.get("").toAbsolutePath().toString()
                + "\\recommendation-system\\RecommendationModel.py");
            process.waitFor();
        }
        catch (IOException execFailed) {
            System.out.println(errorMessage + execFailed.getMessage());
        }
        catch (InterruptedException waitFailed) {
            System.out.println(errorMessage + waitFailed.getMessage());
        }
    }
}
