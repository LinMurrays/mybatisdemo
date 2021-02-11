package com.boc.database.mybatisdemo.cachePackage.config;

import com.boc.database.mybatisdemo.dataNconfig.bean.Department;
import com.boc.database.mybatisdemo.dataNconfig.bean.Employee;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
public class MyRedisConfig {

    /**
     * 这个方法注释掉也可以用，在web部分，但是下面的缓存管理器一定要配置，但是测试类中要用到这个类一定要注册
     */
    @Bean
    public RedisTemplate<Object, Employee> empRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置默认的序列化方式
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
        return template;
    }

    /**
     * 这个方法注释掉也可以用，在web部分，但是下面的缓存管理器一定要配置，但是测试类中要用到这个类一定要注册
     */
    @Bean
    public RedisTemplate<Object, Department> deptRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Department> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置默认的序列化方式
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
        return template;
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        //初始化json的序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);
        //设置 value 的序列化方式为 jackson2JsonRedisSerializer
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

        //设置默认超过期时间是100秒
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(100));

        //初始化RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);

        //设置白名单---非常重要********
        /*
        使用fastjson的时候：序列化时将class信息写入，反解析的时候，
        fastjson默认情况下会开启autoType的检查，相当于一个白名单检查，
        如果序列化信息中的类路径不在autoType中，
        反解析就会报com.alibaba.fastjson.JSONException: autoType is not support的异常
        可参考 https://blog.csdn.net/u012240455/article/details/80538540
         */
        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        return cacheManager;
    }

    /***********************************************************/

//这个方法也是旧版本，springboot1.x版本的方法
//    @Bean
//    public RedisTemplate<Object, Employee> empRedisTemplate(
//            RedisConnectionFactory redisConnectionFactory)
//            throws UnknownHostException {
//        RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
//        template.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer<Employee> ser = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
//        template.setDefaultSerializer(ser);
//        return template;
//    }

    //这个方法也是旧版本，springboot1.x版本的方法
    //CacheManagerCustomizers可以来定制缓存的一些规则
//    @Primary  //将某个缓存管理器作为默认的
//    @Bean
//    public RedisCacheManager employeeCacheManager(RedisTemplate<Object, Employee> empRedisTemplate) {
//        // RedisCacheManager cacheManager = new RedisCacheManager(empRedisTemplate);
//        //key多了一个前缀
//        //cacheManager.setUsePrefix(true);
//        // return cacheManager;
//    }

//
//    @Bean
//    public RedisCacheManager deptCacheManager(RedisTemplate<Object, Department> deptRedisTemplate){
//        RedisCacheManager cacheManager = new RedisCacheManager(deptRedisTemplate);
//        //key多了一个前缀
//
//        //使用前缀，默认会将CacheName作为key的前缀
//        cacheManager.setUsePrefix(true);
//
//        return cacheManager;
//    }


    //    @Bean
//    public RedisTemplate<Object, Department> deptRedisTemplate(
//            RedisConnectionFactory redisConnectionFactory)
//            throws UnknownHostException {
//        RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
//        template.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer<Department> ser = new Jackson2JsonRedisSerializer<Department>(Department.class);
//        template.setDefaultSerializer(ser);
//        return template;
//    }


}
