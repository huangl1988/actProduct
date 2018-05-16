package com.newtouch.act.client.feign;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by steven on 2018/4/9.
 */
@Component
@PropertySource("classpath:act_client.properties")
public class FeignClient {

    @Value("${act.url}")
    private String activityUrl;

    @Bean
    public IActivityFeign getActivityFeign1(){
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(IActivityFeign.class, activityUrl);
    }
}
