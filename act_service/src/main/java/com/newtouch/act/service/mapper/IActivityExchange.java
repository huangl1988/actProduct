package com.newtouch.act.service.mapper;

import com.newtouch.act.service.entity.ActivityExchangePojo;

import java.util.List;

/**
 * Created by steven on 2018/4/20.
 */
public interface IActivityExchange {


    List<ActivityExchangePojo> getExchangeInfoByTask(Long taskId);
}
