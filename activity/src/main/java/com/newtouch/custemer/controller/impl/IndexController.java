package com.newtouch.custemer.controller.impl;

import cn.newtouch.feign.ITransCore;
import com.newtouch.CallResult;
import com.newtouch.act.client.feign.IActivityFeign;
import com.newtouch.common.Constants;
import com.newtouch.custemer.controller.BaseController;
import com.newtouch.custemer.controller.IIndexController;
import com.newtouch.custemer.service.IndexService;
import com.newtouch.order.client.feign.IOrder;
import com.newtouch.request.OrderRequest;
import com.newtouch.response.*;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by steven on 2018/2/7.
 */
@RestController
@RequestMapping("")
public class IndexController extends BaseController  implements IIndexController {

    @Autowired
    IndexService indexService;

    @Autowired
    IActivityFeign activityFeign;

    @Autowired
    ITransCore transCore;

    private DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    IOrder order;


    /**
     * 根据userId查询能够看到的activity
     * @param userId
     * @return
     */
    @RequestMapping(value = "/activities",method= RequestMethod.GET)
    @Override
    public BaseResponse getActivities(String userId) {
        String localDate = dateTimeFormatter.format(LocalDate.now());
        //查询时间内的活动
        List<ActivityVo> retList =indexService.getActivities(userId,localDate);
        ResIndexActivities res = ResIndexActivities
                .builder()
                .activityVoList(retList)
                .build();
        return res;
    }

    @RequestMapping(value = "/activities/{id}",method= RequestMethod.GET)
    @Override
    public BaseResponse detailsActivity(@PathVariable("id") Long id) {

        return indexService.getDetailsActivity(id);
    }

    @RequestMapping(value = "/index/{userId}",method= RequestMethod.GET)
    public BaseResponse getOrderNo(@PathVariable("userId") Long userId) {

        return transCore.getOrderNo(userId);
    }


    @RequestMapping(value = "/activities/{id}/rush",method= RequestMethod.POST)
    @Override
    public BaseResponse rushActivity(@PathVariable Long id,String commodyCode,String orderNo) {

        CallResult callResult=transCore.checkOrderNo(orderNo);
        if(!Constants.SUCCESS.equals(callResult.getRespCode())){
            return CommonResponse.builder().message(Constants.PARAM_ERROR).code(Constants.PARAM_MESSAGE).build();
        }
        indexService.rushActivity(id,commodyCode,orderNo);
        return CommonResponse.builder().message(Constants.MESSAGE).code(Constants.SUCCESS).build();
    }
    @RequestMapping(value = "/activities/{id}/general",method= RequestMethod.POST)
    @Override
    public BaseResponse generalActivity(String commodyCode, @PathVariable  Long id,String orderNo) {

        CallResult callResult=transCore.checkOrderNo(orderNo);

        if(!Constants.SUCCESS.equals(callResult.getRespCode())){
            return CommonResponse.builder().message(Constants.PARAM_ERROR).code(Constants.PARAM_MESSAGE).build();
        }
        indexService.generalActivity(id,commodyCode,orderNo);
        return CommonResponse.builder().message(Constants.MESSAGE).code(Constants.SUCCESS).build();
    }

    @RequestMapping(value = "/activities/joins",method= RequestMethod.GET)
    public BaseResponse myJoinActivity(String userId) {

        var list =indexService.myJoinActivity(userId);
        BaseResponse baseResponse= ResIndexActivities.builder().activityVoList(list).build();
        baseResponse.setCode(Constants.SUCCESS);
        baseResponse.setMessage(Constants.SUCCESS);

        return baseResponse;
    }

    @RequestMapping(value = "/benifits",method= RequestMethod.GET)
    public BaseResponse benifits(String userId,String activityId) {
        var list =indexService.benifits(userId,activityId);
        ResBenifits baseResponse= ResBenifits.builder().benifitList(list).build();
        baseResponse.setCode(Constants.SUCCESS);
        baseResponse.setMessage(Constants.SUCCESS);
        return baseResponse;
    }

    @RequestMapping(value = "/order",method= RequestMethod.POST)
    public BaseResponse order(@RequestBody OrderRequest request) {

        BaseResponse response=activityFeign.isOrderOverRush(request.getOrderCommodys());
        if(!response.getCode().equals(Constants.SUCCESS)){
            return response;
        }
        response=indexService.createOrder(request);
        return response;
    }

    @RequestMapping(value = "/orders",method= RequestMethod.GET)
    public BaseResponse order(String userId,String orderId) {

        BaseResponse baseResponse = order.queryOrder(userId);
        return baseResponse;
    }


}
