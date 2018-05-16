package cn.newtouch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by steven on 2018/4/3.
 */
@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(1500l);
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMinIdle(50);
        jedisPoolConfig.setBlockWhenExhausted(false);
        jedisPoolConfig.setMaxTotal(500);
        jedisPoolConfig.setEvictorShutdownTimeoutMillis(1500);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);
        jedisPoolConfig.setTestOnBorrow(false);
        //在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(false);
        jedisPoolConfig.setNumTestsPerEvictionRun(1024);
        return jedisPoolConfig;
    }
    @Bean
    public Set<String> getHostAndPortSet(){

        HashSet<String> hashSet = new HashSet<>();
        Optional.ofNullable(redisHost.split(",")).ifPresent(hostAndPortSets->{
            Arrays.stream(hostAndPortSets).forEach(hostAndPortSet->{
                hashSet.add(hostAndPortSet);
            });
        });
        return hashSet;
    }

    @Bean
    public JedisSentinelPool jedisSentinelPool(Set<String> set,JedisPoolConfig jedisPoolConfig ){
        return new JedisSentinelPool("my-master",set,jedisPoolConfig,10,"");
    }


}
