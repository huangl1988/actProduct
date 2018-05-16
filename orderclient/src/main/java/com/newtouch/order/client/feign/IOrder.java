package com.newtouch.order.client.feign;

import com.newtouch.request.OrderRequest;
import com.newtouch.response.BaseResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Created by steven on 2018/4/8.
 */
public interface IOrder {

    @RequestLine("POST /orders")
    @Headers("Content-Type: application/json")
    BaseResponse createOrder(OrderRequest request);
    @RequestLine("GET /orders")
    @Headers("Content-Type: application/json")
    BaseResponse queryOrder(String userId);
}
