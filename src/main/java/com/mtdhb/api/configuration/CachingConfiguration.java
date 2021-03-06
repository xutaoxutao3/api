package com.mtdhb.api.configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.mtdhb.api.cache.SignatureKeyGenerator;

/**
 * @author i@huangdenghe.com
 * @date 2018/05/30
 */
@Configuration
public class CachingConfiguration extends CachingConfigurerSupport {

    @Override
    public KeyGenerator keyGenerator() {
        return new SignatureKeyGenerator();
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("RECEIVING_CAROUSEL",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60L)));
        cacheConfigurations.put("USER_SESSION",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20L)));
        cacheConfigurations.put("COOKIE_RANK",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30L)));
        cacheConfigurations.put("RECEIVING_TREND",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30L)));
        cacheConfigurations.put("RECEIVING_PIE",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30L)));
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
                .withInitialCacheConfigurations(cacheConfigurations).transactionAware().build();
    }

}
