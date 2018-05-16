package com.newtouch.act.client.feign;

import com.newtouch.entity.Activity;
import com.newtouch.response.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

/**
 * Created by steven on 2018/4/9.
 */
    public interface IActivityFeign {

        @RequestLine("GET /activities")
        @Headers("Content-Type: application/json")
        List<Activity> getActivityInUsing(Map<String,String> map);

        @RequestLine("POST /activities/{id}/rush")
        @Headers("Content-Type: application/json")
        BaseResponse rushActivity(@Param("id") Long id,Map<String,String> map);

        @RequestLine("POST /activities/{id}/join")
        @Headers("Content-Type: application/json")
        BaseResponse generalActivity(@Param("id") Long id,Map<String,String> map);

        @RequestLine("POST /activities/orders/pre")
        @Headers("Content-Type: application/json")
        BaseResponse isOrderOverRush(Map<Long, Integer> orderCommodys);

        @RequestLine("GET /activities/my")
        @Headers("Content-Type: application/json")
        List<Activity> getMyJoinActivity(String orderNo);


}
