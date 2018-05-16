package com.newtouch.custemer.service;

import com.newtouch.entity.Benifit;
import com.newtouch.request.OrderRequest;
import com.newtouch.response.ActivityDetailsRes;
import com.newtouch.response.ActivityVo;
import com.newtouch.response.BaseResponse;

import java.util.List;

/**
 * Created by steven on 2018/2/27.
 */
public interface IndexService{

    List<ActivityVo> getActivities(String userId, String localDate);

    ActivityDetailsRes getDetailsActivity(Long id);

    void rushActivity(Long id, String commodyCode, String orderNo);

    void generalActivity(Long id, String commodyCode, String orderNo);


    List<ActivityVo> myJoinActivity(String orderNo);

    List<Benifit> benifits(String userId, String activityId);

    BaseResponse createOrder(OrderRequest request);
}
