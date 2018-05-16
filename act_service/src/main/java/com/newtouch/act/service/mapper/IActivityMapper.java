package com.newtouch.act.service.mapper;

import com.newtouch.act.service.entity.ActivityPojo;

import java.util.List;

/**
 * Created by steven on 2018/4/9.
 */
public interface IActivityMapper {

    public List<ActivityPojo> getActivityPojos(String time, Long actId);


    int updateActivity(Long id, String commodyCode, String orderNo);
}
