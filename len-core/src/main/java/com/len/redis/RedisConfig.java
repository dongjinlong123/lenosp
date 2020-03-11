package com.len.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * 默认的redis 是采用jdk中的序列化方式，在redis工具上现实的不是明文
 * 因为redis在注入redisTemplate时，使用了注解@ConditionalOnMissingBean(name = "redisTemplate")
 * 可以自行注入，并设置序列化方法
 */
@Configuration
public class RedisConfig {
    @Bean
    @ConditionalOnClass(RedisOperations.class) //如果存在RedisOperations.class这个类，表示引入了redis
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
