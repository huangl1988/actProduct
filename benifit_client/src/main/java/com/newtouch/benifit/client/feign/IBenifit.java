package com.newtouch.benifit.client.feign;

import com.newtouch.entity.AwardInfo;
import com.newtouch.entity.Benifit;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by steven on 2018/4/8.
 */
public interface IBenifit {
    @RequestLine("GET /benfits")
    @Headers("Content-Type: application/json")
    List<Benifit> getBenifits(String userId);
    @RequestLine("GET /awards")
    @Headers("Content-Type: application/json")
    List<AwardInfo> getAwardsById(List<Long> list);
}
