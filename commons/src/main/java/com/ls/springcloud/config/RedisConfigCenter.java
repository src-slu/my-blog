package com.ls.springcloud.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName RedisConfig
 * @Description redis配置类
 * @Author lushuai
 * @Date 2019/12/4 14:39
 */
@Configuration(value = "RedisConfig")
@EnableCaching
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class RedisConfigCenter extends CachingConfigurerSupport {

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        /**
         * 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
         * 默认使用jdk自带序列化
         */
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        /**
         * 指定序列化的域，field，get，set，以及修饰符范围，any表示全部
         */
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        /**
         * 指定序列化输入的类型，类必须是非final修饰的，例：String,Integer等，会抛出异常
         */
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jsonRedisSerializer.setObjectMapper(objectMapper);

        /**
         * 使用json序列化值
         * 使用StringRedisSerializer序列化key
         */
        template.setValueSerializer(jsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());

        /**
         * 设置hash key和value的序列化模式
         */
        template.setHashValueSerializer(jsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        return template;
    }

    /**
     * hash类型的数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForHash();
    }

    /**
     * 字符串类型数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations valueOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForValue();
    }

    /**
     * list类型数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations listOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForList();
    }

    /**
     * set类型数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations setOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForSet();
    }

    /**
     * zSet类型数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations zSetOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForZSet();
    }
}
