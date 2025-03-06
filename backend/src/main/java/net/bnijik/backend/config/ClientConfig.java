package net.bnijik.backend.config;

import net.bnijik.backend.client.ShipTimeRateClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    private static final Logger log = LoggerFactory.getLogger(ClientConfig.class);
    private final ClientLoggerRequestInterceptor clientLoggerInterceptor;

    public ClientConfig(ClientLoggerRequestInterceptor clientLoggerInterceptor) {
        this.clientLoggerInterceptor = clientLoggerInterceptor;
    }


    @Bean
    public ShipTimeRateClient shipTimeBasicAuthClient(@Value("${shiptime.base-url}") String baseUrl,
                                                      @Value("${shiptime.auth.username}") String username,
                                                      @Value("${shiptime.auth.password}") String password) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor(clientLoggerInterceptor)
                .defaultHeaders(headers -> headers.setBasicAuth(username, password))
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ShipTimeRateClient.class);
    }

}