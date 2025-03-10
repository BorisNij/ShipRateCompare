package net.bnijik.shipratecompare;

import com.sun.tools.javac.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class ShipTimeIntegrationApplication {
    private static final String INDEX_HTML_PATH = "static/index.html";
    private static Logger LOG = LoggerFactory.getLogger(ShipTimeIntegrationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShipTimeIntegrationApplication.class, args);
    }

//    @Bean
////    @Profile("sandbox")
//    public CommandLineRunner runrunner() {
//        return args -> {
//            try {
//                File htmlFile = getIndexHtmlFile();
//                openFileInDefaultBrowser(htmlFile);
//            } catch (URISyntaxException | IOException e) {
//                LOG.error("Failed to open index page: {}", e.getMessage());
//            }
//        };
//    }

    private File getIndexHtmlFile() throws URISyntaxException {
        URL resource = Main.class.getClassLoader().getResource(INDEX_HTML_PATH);
        if (resource == null) {
            throw new IllegalStateException("index.html file not found in resources");
        }
        return new File(resource.toURI());
    }

    private void openFileInDefaultBrowser(File file) throws IOException {
        if (!Desktop.isDesktopSupported()) {
            throw new IllegalStateException("Desktop is not supported on this platform");
        }
        Desktop.getDesktop().browse(file.toURI());
    }
}
