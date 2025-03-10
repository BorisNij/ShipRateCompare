package net.bnijik.shipratecompare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class ShipTimeIntegrationApplication {
    private static final String INDEX_HTML_PATH = "static/index.html";
    private static Logger LOG = LoggerFactory.getLogger(ShipTimeIntegrationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShipTimeIntegrationApplication.class, args);
    }

    private void openFileInDefaultBrowser(String filePath) {
        try {
            URI uri = ShipTimeIntegrationApplication.class.getClassLoader().getResource(filePath).toURI();
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            } else {
                String os = System.getProperty("os.name").toLowerCase();
                Runtime runtime = Runtime.getRuntime();
                if (os.contains("win")) {
                    runtime.exec("cmd /c start " + uri.toString());
                } else if (os.contains("mac")) {
                    runtime.exec("open " + uri.toString());
                } else if (os.contains("nix") || os.contains("nux")) {
                    runtime.exec("xdg-open " + uri.toString());
                } else {
                    throw new IllegalStateException("Unsupported operating system: " + os);
                }
            }
        } catch (URISyntaxException | IOException e) {
            // Handle exceptions appropriately (logging, rethrowing, etc.).
            e.printStackTrace();
        }
    }

    @Bean
    public CommandLineRunner run() {
        return args -> openFileInDefaultBrowser(INDEX_HTML_PATH);
    }
}
