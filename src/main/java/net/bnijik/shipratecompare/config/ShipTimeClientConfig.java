package net.bnijik.shipratecompare.config;

import net.bnijik.shipratecompare.client.ShipTimeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ShipTimeClientConfig {
    private final ShipTimeClientLogger shipTimeClientLogger;

    public static final String API_PATH = "/rest";
    public static final String RATE_RESOURCE = "/rates";
    public static final String SHIPMENT_RESOURCE = "/shipments";

    @Autowired
    public ShipTimeClientConfig(ShipTimeClientLogger shipTimeClientLogger) {
        this.shipTimeClientLogger = shipTimeClientLogger;
    }

    @Bean
    public RestClient.Builder loggingShiTimeClientBuilder(@Value("${shiptime.base-url}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).requestInterceptor(shipTimeClientLogger);
    }

    @Bean
    @Profile("devlocal")
    public RestClient loggingShipTimeClientNoAuth(RestClient.Builder loggingShiTimeClientBuilder) {
        return loggingShiTimeClientBuilder.build();
    }

    @Bean
    @Profile("!devlocal")
    public RestClient loggingShipTimeClientBasicAuth(RestClient.Builder loggingShiTimeClientBuilder,
                                                     @Value("${shiptime.auth.username}") String username,
                                                     @Value("${shiptime.auth.password}") String password) {
        return loggingShiTimeClientBuilder.requestInterceptor(new BasicAuthenticationInterceptor(username, password))
                .build();
    }

    @Bean
    public ShipTimeClient shipTimeClient(RestClient shipTimeRestClient) {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(shipTimeRestClient))
                .build()
                .createClient(ShipTimeClient.class);
    }
}