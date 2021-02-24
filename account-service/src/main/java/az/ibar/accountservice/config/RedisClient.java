package az.ibar.accountservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisClient {

    @Value("${redis.server.url}")
    String redisServer;

    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(redisServer);
        config.setUseThreadClassLoader(true);

        return Redisson.create(config);
    }
}