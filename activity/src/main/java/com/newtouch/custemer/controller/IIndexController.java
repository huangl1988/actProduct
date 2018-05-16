package com.newtouch.custemer.controller;

import com.newtouch.response.BaseResponse;

/**
 * Created by steven on 2018/2/26.
 */
public interface IIndexController {

    /**
     * 获取进行中的活动
     * @param userId
     * @return
     */
    public BaseResponse getActivities(String userId);

    /**
     * 活动详情
     * @param id
     * @return
     */
    public BaseResponse detailsActivity(Long id);

    /**
     * 抢
     * @param commodyCode
     * @param
     * @return
     */
    public BaseResponse rushActivity(Long id,String commodyCode,String orderNo);

    /**
     * All
     * @param commodyCode
     * @param
     * @return
     */
    public BaseResponse generalActivity(String commodyCode,Long id,String orderNo);



}
