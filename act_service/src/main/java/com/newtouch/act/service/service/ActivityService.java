package com.newtouch.act.service.service;

import com.newtouch.entity.Activity;
import com.newtouch.response.BaseResponse;

import java.util.List;

/**
 * Created by steven on 2018/4/16.
 */
public interface ActivityService {

    public List<Activity> getActivities(String time,Long actId);


    BaseResponse rush(Long id, String commodyCode, String orderNo);

    BaseResponse generalActivity(Long id, String commodyCode, String orderNo);
}
