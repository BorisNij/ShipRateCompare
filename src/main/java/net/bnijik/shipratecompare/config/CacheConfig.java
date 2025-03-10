package net.bnijik.shipratecompare.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${cache.rates-endpoint.requests-cached:1000}")
    private long cacheSize;

    @Value("${cache.rates-endpoint.expiry-minutes:15}")
    private long expiryMinutes;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("rates");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                                         .maximumSize(cacheSize)
                                         .expireAfterWrite(expiryMinutes, TimeUnit.MINUTES));
        return cacheManager;
    }
}
