package org.example.backend;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiBooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiBooksApplication.class, args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Recommendations computation...");
                Process process = Runtime.getRuntime().exec(
                    "python " + Paths.get("").toAbsolutePath().toString()
                    + "\\recommendation-system\\RecommendationModel.py");
                process.waitFor();
            }
            catch (IOException execFailed) {
                System.out.println("Error: " + execFailed.getMessage());
            }
            catch (InterruptedException waitFailed) {
                System.out.println("Error: " + waitFailed.getMessage());
            }
        }));
    }
}
