package flight_booking.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private static final String REDISSON_HOST_PREFIX = "redis://";

    @Bean
    @ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true", matchIfMissing = true)
    public RedissonClient redissonClient() {
        Config config = new Config();
        // Netty 스레드 설정은 Config 객체에 직접 적용
        config.setNettyThreads(2);  // 여기에 설정

        config.useSingleServer()
            .setAddress(REDISSON_HOST_PREFIX + redisHost + ":" + redisPort)
            .setConnectionMinimumIdleSize(1)
            .setConnectionPoolSize(2);
        return Redisson.create(config);
    }
}