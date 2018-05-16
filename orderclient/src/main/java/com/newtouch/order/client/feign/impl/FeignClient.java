package com.newtouch.order.client.feign.impl;

import com.newtouch.order.client.feign.IOrder;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by steven on 2018/4/8.
 */
@Component("orderFeignClient")
@PropertySource("classpath:order_client.properties")
public class FeignClient {

    @Value("${order.url}")
    private String url;

    @Bean
    public IOrder build(){
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(IOrder.class,url);
    }


}
