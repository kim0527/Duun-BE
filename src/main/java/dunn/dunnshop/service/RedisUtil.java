package dunn.dunnshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtil {
    private final StringRedisTemplate redisTemplate;

    // 키(key)에 해당하는 데이터를 Redis 에서 가져오는 메서드
    private String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    // 키(key)에 값을 저장하고, 설정한 시간(duration)이 지나면 데이터가 유효하지 않도록 설정하는 메서드
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public boolean hasCode(String code){
        return getData(code) != null;
    }

    public boolean hasEmail(String code, String email){
        return getData(code).equals(email);
    }
}