package com.newtouch.act.service.es.impl;

import com.newtouch.act.service.entity.ActivityPojo;
import com.newtouch.act.service.mapper.IActivityMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by steven on 2018/4/20.
 */
@Component("activityMapperEsImpl")
public class ActivityMapperImpl implements IActivityMapper {

    @Override
    public List<ActivityPojo> getActivityPojos(String time, Long actId) {



        return null;
    }
}
