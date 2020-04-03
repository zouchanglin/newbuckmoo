package live.lslm.newbuckmoo.catchs;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class CustomRedisTemplate {
    @Bean
    public RedisTemplate<Object, PayResponse> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, PayResponse> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //是自己制定序列化
        FastJsonRedisSerializer<PayResponse> serializer = new FastJsonRedisSerializer<>(PayResponse.class);
        template.setDefaultSerializer(serializer);
        return template;
    }
}