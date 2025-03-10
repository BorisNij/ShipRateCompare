package net.bnijik.shipratecompare;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.net.InetAddress;

@SpringBootApplication
public class ShipTimeIntegrationApplication {

    private static int ourPort;

    public static void main(String[] args) {
        SpringApplication.run(ShipTimeIntegrationApplication.class, args);
    }

    private void askUserToOpenPage(String ourUrl) {
        System.out.printf("Please open the following link in a browser: %s/index.html\n", ourUrl);
    }

    @EventListener
    public void ourPort(ServletWebServerInitializedEvent event) {
        ourPort = event.getWebServer().getPort();
    }

    private String ourUrl() {
        return InetAddress.getLoopbackAddress().getHostName();
    }

    @Bean
    public CommandLineRunner run() {
        return args -> askUserToOpenPage(ourUrl() + ":" + ourPort);
    }
}
