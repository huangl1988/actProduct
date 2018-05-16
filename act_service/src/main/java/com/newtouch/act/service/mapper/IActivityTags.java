package com.newtouch.act.service.mapper;

import com.newtouch.act.service.entity.ActivityTagPojo;

import java.util.List;

/**
 * Created by steven on 2018/4/17.
 */
public interface IActivityTags {

    List<ActivityTagPojo> getActivityTags(Long actId);



}
