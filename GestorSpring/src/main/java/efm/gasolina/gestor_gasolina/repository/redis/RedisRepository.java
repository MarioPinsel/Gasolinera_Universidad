package efm.gasolina.gestor_gasolina.repository.redis;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository implements RedisInterface {

    private RedisTemplate<Object, Object> redisTemplate;

    public RedisRepository(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;

        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setHashKeySerializer(new StringRedisSerializer());        
        Jackson2JsonRedisSerializer<Object> jackson = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();        
        jackson.setObjectMapper(objectMapper);

        this.redisTemplate.setValueSerializer(jackson);
        this.redisTemplate.setHashValueSerializer(jackson);
    }

    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void saveWithTTL(String key, Object value, Integer ttl) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(ttl));
    }

    @Override
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
