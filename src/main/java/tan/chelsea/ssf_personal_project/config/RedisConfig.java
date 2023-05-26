package tan.chelsea.ssf_personal_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// holds configuration (factory methods) for creating objects
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUser;

    @Value("$(spring.data.redis.password)")
    private String redisPassword;
    
    @Bean
    // Lifecycle of object. When scope is singleton, only a single instance is created and shared.
    @Scope("singleton")
    public RedisTemplate<String, Object> getRedisTemplate(){
        // condigure database. Config values are injected from the application properties file
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);

        // if got password and username
        if (!redisUser.isEmpty() && !redisPassword.isEmpty()){
            config.setUsername(redisUser);
            config.setPassword(redisPassword);
        }

        config.setDatabase(0);

        // create client and factory
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();

        // setting up connection with redis database
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        // initialize and validate connection factory
        jedisFac.afterPropertiesSet();

        // create template with client
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // associate to redis connection
        redisTemplate.setConnectionFactory(jedisFac);

        // set the list key and hash key serialization type to String
        // redisTemplate.setKeySerializer(new StringRedisSerializer()); // key for list
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // enable redis to store java object in the value column
        // enabling java object as values in Redis
        RedisSerializer<Object> objSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        // redisTemplate.setValueSerializer(objSerializer); // value for list
        redisTemplate.setHashValueSerializer(objSerializer); // value for hash

        return redisTemplate;
    }
}
