package com.newtouch.act.service.mapper;

import com.newtouch.act.service.entity.ActivityTaskPojo;

import java.util.List;

/**
 * Created by steven on 2018/4/17.
 */
public interface IActivityTask {
    List<ActivityTaskPojo> getTaskList(Long actId);
}
