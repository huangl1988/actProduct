package cn.newtouch.feign.impl;

import cn.newtouch.feign.IRulesEng;
import cn.newtouch.feign.ITransCore;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by steven on 2018/4/3.
 */
@Component("coreFeignClient")
@PropertySource(value = "classpath:core_feign.properties")
public class FeignClient {

    @Value("core.trans.feign.url")
    private String url;

    @Value("core.rules.feign.url")
    private String ruleUrl;

    @Bean
    public ITransCore builderTrans(){
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(ITransCore.class, url);
    }

    @Bean
    public IRulesEng builderIRu(){
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(IRulesEng.class, url);
    }


}
