package flight_booking.demo.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class CacheConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port),
//                LettuceClientConfiguration.builder()
//                        .commandTimeout(Duration.ofSeconds(10))
//                        .shutdownTimeout(Duration.ofSeconds(30))
//                        .build());
//    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 서버 정보 설정 (호스트, 포트)
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);

        // 커넥션 풀 설정 (예시: 최대 100개, 최소 10개, 최대 50개의 유휴 커넥션)
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(100);   // 최대 총 커넥션 수
        poolConfig.setMaxIdle(50);     // 최대 유휴 커넥션 수
        poolConfig.setMinIdle(10);     // 최소 유휴 커넥션 수

        // LettucePoolingClientConfiguration 생성: 커넥션 풀과 타임아웃 설정 적용
        LettucePoolingClientConfiguration poolingConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .commandTimeout(Duration.ofSeconds(10))  // Redis 명령 타임아웃 설정 (10초)
                .shutdownTimeout(Duration.ofSeconds(30))   // 셧다운 타임아웃 설정 (30초)
                .build();

        // RedisStandaloneConfiguration과 poolingConfig를 적용한 LettuceConnectionFactory 생성
        return new LettuceConnectionFactory(redisConfig, poolingConfig);
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Jackson ObjectMapper 설정 (JavaTimeModule 추가)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // ZonedDateTime 직렬화 지원

        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        ); // 역직렬화시 제네릭타입의 정보를 저장 -> page 기본생성자가 필요

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer())) // 키를 문자열로 직렬화
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(serializer)) // 값 직렬화를 JSON 방식으로 변경
                .entryTtl(Duration.ofHours(1L)); // 캐시 TTL 1시간 설정

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .withCacheConfiguration("flight-plan", redisCacheConfiguration)
                .build();
    }
}
