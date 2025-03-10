package net.bnijik.shipratecompare;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class ShipTimeIntegrationApplication {
    private static final String INDEX_HTML_PATH = "static/index.html";
    private static Logger LOG = LoggerFactory.getLogger(ShipTimeIntegrationApplication.class);

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    public static void main(String[] args) {
        SpringApplication.run(ShipTimeIntegrationApplication.class, args);
    }

    private File getIndexHtmlFile(String filePath) throws URISyntaxException {
        URL resource = ShipTimeIntegrationApplication.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            LOG.error("index.html file not found in {}", filePath);
            throw new IllegalStateException("index.html file not found in " + filePath);
        }
        return new File(resource.toURI());
    }

    private void tryOpeningDefaultBrowser(File file) {
        try {
            final URI uri = file.toURI();
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            } else {
                tryRunningOsFallBack(uri);
            }
        } catch (IOException e) {
            LOG.error("Failed to open index page: {}", e.getMessage());
        }
    }

    private void tryRunningOsFallBack(URI uri) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        if (os.contains("win")) {
            runtime.exec("cmd /c start " + uri.toString());
        } else if (os.contains("mac")) {
            runtime.exec("open " + uri.toString());
        } else if (os.contains("nix") || os.contains("nux")) {
            runtime.exec("xdg-open " + uri.toString());
        } else {
            askUserToOpenPage(getLocalServerUrl());
        }
    }

    private void askUserToOpenPage(String ourUrl) {
        LOG.info("Could not open default browser. Asking user to open file manually...");
        System.out.printf("Please open the following file manually: %s/index.html\n", ourUrl);
    }

    private String getLocalServerUrl() {
        TomcatWebServer tomcatWebServer = (TomcatWebServer) webServerAppCtxt.getWebServer();
        Connector connector = tomcatWebServer.getTomcat().getConnector();
        String scheme = connector.getScheme();
        String host = connector.getProperty("address").toString();
        int port = connector.getPort();
        return String.format("%s://%s:%d", scheme, host, port);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> tryOpeningDefaultBrowser(getIndexHtmlFile(INDEX_HTML_PATH));
    }
}
