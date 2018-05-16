package com.newtouch.benifit.client.feign.impl;

import com.newtouch.benifit.client.feign.IBenifit;
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
@Component("benifitFeignClient")
@PropertySource(value = "classpath:benfit_client.properties")
public class FeignClient {

        @Value("client.benfit.url")
        private String activityUrl;

        @Bean
        public IBenifit getActivityFeign(){
            return Feign.builder()
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .target(IBenifit.class, activityUrl);
        }





}
